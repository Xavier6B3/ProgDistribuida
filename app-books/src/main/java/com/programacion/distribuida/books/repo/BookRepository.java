package com.programacion.distribuida.books.repo;

import com.programacion.distribuida.books.db.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class BookRepository implements PanacheRepositoryBase<Book, String> {

  public List<Book> findByAuthor(Integer authorId) {
    var sql = "select o.book from BookAuthor o where o.id.authorId = ?1";
    return this.find(sql, authorId).list();
  }

}
