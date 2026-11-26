package com.programacion.distribuida.web02;

import jakarta.ws.rs.SeBootstrap;
import java.net.URI;

public class Web02Main {
    public static void main(String[] args) throws InterruptedException {

        SeBootstrap.Configuration config = SeBootstrap.Configuration.builder()
                .host("0.0.0.0")
                .port(8080)
                .protocol("http")
                .build();

        SeBootstrap.start(MiAplicationRest.class, config)
                .thenAccept(instance -> {
                    System.out.println(instance);
                    URI uri = instance.configuration().baseUri();
                    System.out.println("Servidor iniciado en " + uri);
                })
                .exceptionally(t -> {
                    t.printStackTrace();
                    return null;
                });

        // Mantiene el hilo principal vivo para que el servidor embebido no se cierre
        Thread.currentThread().join();
    }
}