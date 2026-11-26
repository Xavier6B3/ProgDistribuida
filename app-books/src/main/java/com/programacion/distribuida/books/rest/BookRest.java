package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.db.Book;
import com.programacion.distribuida.books.dtos.BookDto;
import com.programacion.distribuida.books.repo.BookRepository;
import com.programacion.distribuida.books.services.interfaces.BookService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookRest {

  final BookService bookService;
  final BookRepository bookRepository;

  @Inject
  public BookRest(BookService bookService, BookRepository bookRepository) {
    this.bookService = bookService;
    this.bookRepository = bookRepository;
  }

  @GET
  public List<BookDto> getAll() {
    return bookService.findAll();
  }

  @GET
  @Path("/{isbn}")
  public Response getById(@PathParam("isbn") String isbn) {
    return bookService.findByIdOptional(isbn)
            .map(Response::ok)
            .orElse(Response.status(Response.Status.NOT_FOUND))
            .build();
  }

  @GET
  @Path("/find/{authorId}")
  public List<Book> findByAuthor(@PathParam("authorId") Integer authorId) {
    return bookRepository.findByAuthor(authorId);
  }

}
