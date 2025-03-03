package org.example.springmvcnum2.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
    List<Store> stores;

    if (search != null && !search.isEmpty()) {
      stores = storeService.getAllStores().stream()
          .filter(store -> store.getName().toLowerCase().contains(search.toLowerCase()))
          .collect(Collectors.toList());
    } else {
      stores = storeService.getAllStores();  // Возвращает все магазины
    }

    model.addAttribute("stores", stores);
    model.addAttribute("search", search);  // Чтобы значение поля search оставалось в форме
    return "store-list";
  }

  @GetMapping("/new")
  public String showAddForm(Model model) {
    model.addAttribute("store", new Store());
    return "store-form";
  }

  @PostMapping
  public String saveStore(@ModelAttribute Store store, @RequestParam("logoFile") MultipartFile file) {
    if (!file.isEmpty()) {
      try {
        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/");
        Files.createDirectories(uploadPath);
        file.transferTo(uploadPath.resolve(fileName));
        store.setLogo("/uploads/" + fileName);
      } catch (IOException e) {
        throw new FileUploadException("Не удалось сохранить файл " + file.getOriginalFilename());
      }
    }
    storeService.addStore(store);
    return "redirect:/stores";
  }


  @GetMapping("/{id}")
  public String viewStore(@PathVariable int id, Model model) {
    Store store = storeService.getStoreById(id)
        .orElseThrow(() -> new StoreNotFoundException("Магазин с ID " + id + " не найден"));
    model.addAttribute("store", store);
    return "store-detail";
  }

  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable int id, Model model) {
    storeService.getStoreById(id).ifPresent(store -> model.addAttribute("store", store));
    return "store-form";
  }

  @PostMapping("/{id}")
  public String updateStore(@PathVariable int id, @ModelAttribute Store store, @RequestParam("logoFile") MultipartFile file) {
    if (!file.isEmpty()) {
      String fileName = file.getOriginalFilename();
      Path uploadPath = Paths.get("src/main/resources/static/logoFiles/");

      try {
        Files.createDirectories(uploadPath);
        file.transferTo(uploadPath.resolve(fileName));
        store.setLogo("/logoFiles/" + fileName);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    storeService.updateStore(id, store);
    return "redirect:/stores";
  }


  @GetMapping("/{id}/delete")
  public String deleteStore(@PathVariable int id) {
    storeService.deleteStore(id);
    return "redirect:/stores";
  }
}