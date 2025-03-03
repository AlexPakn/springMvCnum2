package org.example.springmvcnum2.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.example.springmvcnum2.Models.Store;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
  private final List<Store> stores = new ArrayList<>();
  private final AtomicInteger counter = new AtomicInteger(1);

  public StoreService() {
    // Добавляем тестовые магазины
    stores.add(new Store(counter.getAndIncrement(), "Магазин ABC", "ул. Центральная, 10",
        "+380501234567", "abc@mail.com", "http://abc-shop.com", "Продовольчий",
        "Продовольственный магазин","logoFiles/photo_2024-01-01_21-45-30.jpg"));
    stores.add(new Store(counter.getAndIncrement(), "Спорт Галактика", "пр. Победы, 25",
        "+380672345678", "sport@mail.com", "http://sport-galaxy.com",
        "Спортивный", "Спортивные товары","logoFiles/photo_2024-07-13_10-23-39.png"));
  }

  public List<Store> getAllStores() {
    return stores;
  }

  public void addStore(Store store) {
    store.setId(counter.getAndIncrement());
    stores.add(store);
  }

  public Optional<Store> getStoreById(int id) {
    return stores.stream().filter(s -> s.getId() == id).findFirst();
  }

  public void updateStore(int id, Store updatedStore) {
    getStoreById(id).ifPresent(store -> {
      store.setName(updatedStore.getName());
      store.setAddress(updatedStore.getAddress());
      store.setPhone(updatedStore.getPhone());
      store.setEmail(updatedStore.getEmail());
      store.setWebsite(updatedStore.getWebsite());
      store.setCategory(updatedStore.getCategory());
      store.setDescription(updatedStore.getDescription());
    });
  }

  public void deleteStore(int id) {
    stores.removeIf(store -> store.getId() == id);
  }
}