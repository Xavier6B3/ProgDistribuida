package com.programacion.distribuida.web03;

import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.http.media.jsonb.JsonbSupport;
import io.helidon.http.media.jsonp.JsonpSupport;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Web03Main {

    private static DbClient dbClient;

    public static void handleBooks(ServerRequest req, ServerResponse res) {
        var books = dbClient.execute()
                .createQuery("SELECT * FROM books")
                .execute()
                .map(row -> Book.builder()
                        .title(row.column("title").getString())
                        .isbn(row.column("isbn").getString())
                        .price(row.column("price").as(BigDecimal.class).map(BigDecimal::doubleValue).orElse(null))
                        .build()
                )
                .toList();

        //JSONP
        JsonBuilderFactory factory = Json.createBuilderFactory(Map.of());
        JsonObject jsonObject = factory.createObjectBuilder()
                .add("title", "El Quijote")
                .add("isbn", "978-84-376-0494-7")
                .build();

        //JSONB
        var book1 = Book.builder()
                .title("El Quijote")
                .isbn("978-84-376-0494-7")
                .price(15.99)
                .build();

        // Retorna la lista de libros obtenida de la base de datos como JSON
        res.send(books);
    }

    public static void main(String[] args) {
        Config config = Config.create();

        var configHttp = config.get("server");
        var configDb = config.get("db");

        dbClient = DbClient.builder()
                .config(configDb)
                .build();

        WebServer.builder()
                .config(configHttp)
                .mediaContext(media -> media
                        .addMediaSupport(JsonbSupport.create())
                        .addMediaSupport(JsonpSupport.create())
                )
                .routing(it -> it
                        .get("/", (req, res) -> res.send("Servidor web03 activo. Consulta los libros en: http://localhost:8080/books"))
                        .get("/books", Web03Main::handleBooks)
                )
                .build()
                .start();
    }
}
