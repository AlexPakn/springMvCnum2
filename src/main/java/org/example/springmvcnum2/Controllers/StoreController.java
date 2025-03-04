package org.example.springmvcnum2.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.example.springmvcnum2.Exeptions.FileUploadException;
import org.example.springmvcnum2.Exeptions.StoreNotFoundException;
import org.example.springmvcnum2.Models.Store;
import org.example.springmvcnum2.Services.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/stores")
public class StoreController {
  private final StoreService storeService;

  public StoreController(StoreService storeService) {
    this.storeService = storeService;
  }

  @GetMapping
  public String listStores(@RequestParam(required = false) String search, Model model) {
    if (search != null && !search.isEmpty()) {
      model.addAttribute("stores", storeService.searchStoresByName(search));
    } else {
      model.addAttribute("stores", storeService.getAllStores());
    }
    model.addAttribute("search", search);
    return "store-list";
  }

  @GetMapping("/{id}")
  public String storeDetails(@PathVariable int id, Model model) {
    Optional<Store> store = storeService.getStoreById(id);
    if (store.isPresent()) {
      model.addAttribute("store", store.get());
      return "store-detail";
    }
    return "redirect:/stores";
  }

  @GetMapping("/new")
  public String newStoreForm(Model model) {
    model.addAttribute("store", new Store());
    return "store-form";
  }

  @PostMapping
  public String saveStore(@ModelAttribute Store store, @RequestParam("logoFile") MultipartFile file) {
    if (!file.isEmpty()) {
      try {
        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/logoFiles/");
        Files.createDirectories(uploadPath);
        file.transferTo(uploadPath.resolve(fileName));
        store.setLogo("logoFiles/" + fileName);  // Устанавливаем путь к файлу
      } catch (IOException e) {
        throw new FileUploadException("Ошибка при сохранении файла: " + file.getOriginalFilename());
      }
    }

    storeService.saveStore(store, store.getLogo());
    return "redirect:/stores";
  }


  @GetMapping("/{id}/edit")
  public String editStore(@PathVariable int id, Model model) {
    Optional<Store> store = storeService.getStoreById(id);
    if (store.isPresent()) {
      model.addAttribute("store", store.get());
      return "store-form";
    }
    return "redirect:/stores";
  }

  @PostMapping("/{id}")
  public String updateStore(@PathVariable int id, @ModelAttribute Store updatedStore,
      @RequestParam("logoFile") MultipartFile file) {
    Store store = storeService.getStoreById(id)
        .orElseThrow(() -> new StoreNotFoundException("Магазин с ID " + id + " не найден"));

    store.setName(updatedStore.getName());
    store.setAddress(updatedStore.getAddress());
    store.setPhone(updatedStore.getPhone());
    store.setEmail(updatedStore.getEmail());
    store.setWebsite(updatedStore.getWebsite());
    store.setCategory(updatedStore.getCategory());
    store.setDescription(updatedStore.getDescription());

    if (!file.isEmpty()) {
      try {
        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/logoFiles/");
        Files.createDirectories(uploadPath);
        file.transferTo(uploadPath.resolve(fileName));
        store.setLogo("logoFiles/" + fileName);
      } catch (IOException e) {
        throw new FileUploadException("Ошибка при сохранении файла: " + file.getOriginalFilename());
      }
    }

    storeService.saveStore(store, store.getLogo());
    return "redirect:/stores";
  }

  @GetMapping("/{id}/delete")
  public String deleteStore(@PathVariable int id) {
    storeService.deleteStore(id);
    return "redirect:/stores";
  }
}

