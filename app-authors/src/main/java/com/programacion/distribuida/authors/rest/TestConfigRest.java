package com.programacion.distribuida.authors.rest;

import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.Service;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@Path("/config")
public class TestConfigRest {
  @Inject
  @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
  Integer port;

  @Inject
  @ConfigProperty(name = "app.name")
  String diAppName;

  @Inject
  Vertx vetx;

  @GET
  public String test() {

    //punto de entrada a la configuracion
    Config config = ConfigProvider.getConfig();

    //Listar las fuentes de configuracion
    config.getConfigSources().forEach(it -> {
      System.out.printf("[%5d] %s%n", it.getOrdinal(), it.getName());
    });

    //recuperar un valor de configuracion
    String appName = config.getValue("app.name", String.class);
    Optional<String> appTitle = config.getOptionalValue("app.title", String.class);

    System.out.println("---------------------------");
    System.out.println("app.name = " + appName);
    System.out.println("port = " + port);
    System.out.println("App Name= " + diAppName);



    if(appTitle.isPresent()){
      System.out.println("app.title= " + appTitle.get());

    }else{
      System.out.println("app.title= --");
    }

    return "";
  }

  @GET
  @Path("/test2")
  public String test2() {
    ConsulClientOptions options = new ConsulClientOptions()
        .setHost("127.0.0.1")
        .setPort(8500);

    ConsulClient client = ConsulClient.create(vetx, options);
    var response = client.catalogServiceNodes("app-authors");

    response.onComplete(it -> {
      if (it.succeeded()) {
        System.out.println("found " + it.result().getList().size() + " services");

        for (Service service : it.result().getList()) {
          System.out.println("service node: " + service.getNode());
          System.out.println("service address: " + service.getAddress());
          System.out.println("service port: " + service.getPort());
        }
      }
    });
    return "";
  }
}
