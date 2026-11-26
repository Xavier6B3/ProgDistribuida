package com.programacion.distribuida.books.rest;

import com.programacion.distribuida.books.db.Inventory;
import com.programacion.distribuida.books.repo.InventoryRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryRest {

  final InventoryRepository inventoryRepository;

  @Inject
  public InventoryRest(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  @GET
  public List<Inventory> getAll() {
    return inventoryRepository.listAll();
  }

  @GET
  @Path("/{isbn}")
  public Response getByIsbn(@PathParam("isbn") String isbn) {
    return inventoryRepository.findByIdOptional(isbn)
            .map(Response::ok)
            .orElse(Response.status(Response.Status.NOT_FOUND))
            .build();
  }

}
