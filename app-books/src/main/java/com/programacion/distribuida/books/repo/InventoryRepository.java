package com.programacion.distribuida.books.repo;

import com.programacion.distribuida.books.db.Inventory;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class InventoryRepository implements PanacheRepositoryBase<Inventory, String> {

  public Optional<Inventory> findByBook(String bookIsbn) {
    return findByIdOptional(bookIsbn);
  }

}
