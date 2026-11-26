package com.programacion.distribuida.books.services.impl;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dtos.AuthorDto;
import com.programacion.distribuida.books.dtos.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import com.programacion.distribuida.books.services.interfaces.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class BookServiceImpl implements BookService {

  final BookRepository bookRepository;
  final AuthorRestClient authorRestClient;

  @Inject
  public BookServiceImpl(BookRepository bookRepository, @RestClient AuthorRestClient authorRestClient) {
    this.bookRepository = bookRepository;
    this.authorRestClient = authorRestClient;
  }



  private BookDto toDto(Book book) {
    var inventory = book.getInventory();
    return BookDto.builder()
        .isbn(book.getIsbn())
        .title(book.getTitle())
        .price(book.getPrice())
        .inventorySold(inventory != null ? inventory.getSold() : 0)
        .inventorySupplied(inventory != null ? inventory.getSupplied() : 0)
        .build();
  }

  @Override
  public List<BookDto> findAll() {
    return bookRepository.streamAll()
        .map(this::toDto)
        .peek(it -> {
          var authors = authorRestClient.findByBook(it.getIsbn());
          it.setAuthors(authors);
        })
        .toList();
  }

  @Override
  public Optional<BookDto> findByIdOptional(String isbn) {
    return bookRepository.findByIdOptional(isbn)
        .map(this::toDto)
        .map(it -> {
          List<AuthorDto> authors = authorRestClient.findByBook(isbn);
          it.setAuthors(authors);
          return it;
        });
  }
}
