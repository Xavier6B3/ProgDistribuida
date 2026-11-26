package com.programacion.distribuida.books.clients;

import com.programacion.distribuida.books.dtos.AuthorDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "http://localhost:8070", configKey = "authors-api")

public interface AuthorRestClient {

  @GET
  @Path("/find/{isbn}")
  List<AuthorDto> findByBook(@PathParam("isbn") String isbn);
}
