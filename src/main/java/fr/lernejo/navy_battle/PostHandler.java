package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.OutputStream;

public class PostHandler implements HttpHandler {
    final int port;

    public PostHandler(int intern_port){
        port = intern_port;
    }

    public int schema_value(String schema) {
        String default_schema = "{ \"$schema\": \"http://json-schema.org/schema#\", \"type\": \"object\", \"properties\": { \"id\": { \"type\": \"string\" }, \"url\": { \"type\": \"string\" }, \"message\": { \"type\": \"string\" } }, \"required\": [ \"id\", \"url\", \"message\"]}\"}";
        JSONTokener token_schema = new JSONTokener(default_schema);
        JSONObject object_schema = new JSONObject(token_schema);
        JSONTokener token = new JSONTokener(schema);
        JSONObject object = new JSONObject(token);
        Schema checker = SchemaLoader.load(object_schema);
        try {
            checker.validate(object);
            return 1;
        } catch (ValidationException e) {
            System.out.println("Schema not validated : " + e.getMessage());
            return 0;
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String request = "{ \"id\":\"0c575465-21f6-43c9-8a2d-bc64c3ae6241\", \"url\":\"http://localhost:" + port + "\", \"message\":\"I will Crush You !!\"}";
        String response = "empty";
        if (exchange.getRequestMethod().equals("POST")) {
            response = "{ \"id\":\"2aca7611-0ae4-49f3-bf63-75bef4769028\", \"url\":\"http://localhost:" + port + "\", \"message\":\"May the best code win\"}";
            if (schema_value(request) == 1)
                exchange.sendResponseHeaders(202, response.length());
            else
                exchange.sendResponseHeaders(400, 1);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            System.out.println("Game Start !");
        }
    }
}
