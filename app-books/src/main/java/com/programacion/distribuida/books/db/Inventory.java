package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@ToString
public class Inventory {

  @Id
  @Column(name = "book_isbn")
  private String bookIsbn;

  @Column(nullable = false)
  private Integer sold;

  @Column(nullable = false)
  private Integer supplied;

  @Version
  @Column(nullable = false)
  private Integer version;

  @OneToOne
  @JoinColumn(name = "book_isbn", insertable = false, updatable = false)
  @ToString.Exclude
  private Book book;

}
