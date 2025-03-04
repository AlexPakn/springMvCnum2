package org.example.springmvcnum2.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.example.springmvcnum2.Models.Store;
import org.example.springmvcnum2.Repository.StoreRepository;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
  private final StoreRepository storeRepository;

  public StoreService(StoreRepository storeRepository) {
    this.storeRepository = storeRepository;
  }

  public List<Store> getAllStores() {
    return (List<Store>) storeRepository.findAll();
  }

  public Optional<Store> getStoreById(int id) {
    return storeRepository.findById(id);
  }

  public void saveStore(Store store, String logoPath) {
    store.setLogo(logoPath); // Устанавливаем путь к файлу
    storeRepository.save(store);
  }

  public void deleteStore(int id) {
    storeRepository.deleteById(id);
  }

  public List<Store> searchStoresByName(String name) {
    return storeRepository.findByNameContaining(name);
  }
}
