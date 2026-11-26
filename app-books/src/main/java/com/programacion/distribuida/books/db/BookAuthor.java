package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books_authors")
@Getter
@Setter
@ToString
public class BookAuthor {

  @EmbeddedId
  private BookAuthorId id;

  @ManyToOne
  @JoinColumn(name = "books_isbn", insertable = false, updatable = false)
  @ToString.Exclude
  private Book book;

}
