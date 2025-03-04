package org.example.springmvcnum2.Repository;

import java.util.List;
import org.example.springmvcnum2.Models.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends CrudRepository<Store, Integer> {
  List<Store> findByNameContaining(String name);
}
