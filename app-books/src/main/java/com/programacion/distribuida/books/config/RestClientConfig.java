package com.programacion.distribuida.books.config;

import com.programacion.distribuida.books.clients.AuthorRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URL;

@ApplicationScoped
public class RestClientConfig {

  @Produces
  @ApplicationScoped
  public AuthorRestClient authorRestClient(@ConfigProperty(name = "ap.auhtor.url") URL baseUrl) {
    return RestClientBuilder.newBuilder()
        .baseUrl(baseUrl)
        .build(AuthorRestClient.class);
  }
}
