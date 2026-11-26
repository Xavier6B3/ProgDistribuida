package com.programacion.distribuida.authors;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.ext.consul.ConsulClient;
import io.vertx.ext.consul.ConsulClientOptions;
import io.vertx.ext.consul.ServiceOptions;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class AuthorsLifecycle {

  @Inject
  @ConfigProperty(name = "consul.host", defaultValue = "localhost")
  String consulHost;

  @Inject
  @ConfigProperty(name = "consul.port", defaultValue = "8500")
  Integer consulPort;

  @Inject
  @ConfigProperty(name = "quarkus.http.port", defaultValue = "8070")
  Integer appPort;

  @Inject
  @ConfigProperty(name = "app.name")
  String appName;

  String serviceId;

  @PostConstruct
  void construct() {
    String ipAdress = "127.0.0.1";
    // Asigna al atributo de clase y separa con comas
    this.serviceId = "%s-%s:%d".formatted(appName, ipAdress, appPort);
  }

  void init(@Observes StartupEvent event, Vertx vertx) {
    System.out.println("AuthorsLifecuycle.init() called");

    try {
      ConsulClientOptions options = new ConsulClientOptions()
          .setHost(consulHost)
          .setPort(consulPort);
      ConsulClient client = ConsulClient.create(vertx, options);

      String ipAdress = "127.0.0.1";

      ServiceOptions serviceOptions = new ServiceOptions()
          .setName("app-authors")
          .setId(serviceId)
          .setAddress(ipAdress)
          .setPort(appPort);

      client.registerService(serviceOptions)
          .onSuccess(it -> System.out.println("Service registered successfully: " + serviceId))
          .onFailure(it -> System.out.println("Error registering service: " + it.getMessage()));

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void destroy(@Observes ShutdownEvent event, Vertx vertx) {
    System.out.println("AuthorsLifecuycle.destroy() called");

    try {
      ConsulClientOptions options = new ConsulClientOptions()
          .setHost(consulHost)
          .setPort(consulPort);
      ConsulClient client = ConsulClient.create(vertx, options);

      client.deregisterService(serviceId)
          .onSuccess(it -> System.out.println("Service deregistered successfully: " + serviceId))
          .onFailure(it -> System.out.println("Error deregistering service: " + it.getMessage()));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
//java -Dquarkus.http.port=8071 -jar app-authors/build/quarkus-app/quarkus-run.jar