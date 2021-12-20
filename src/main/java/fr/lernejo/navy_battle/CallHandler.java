package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CallHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            String body = "OK";
            exchange.sendResponseHeaders(200, body.length());
            try (OutputStream os = exchange.getResponseBody()){
                os.write(body.getBytes());
            }
        } catch (IOException e) {
            System.out.println("Caught error : "+ e.getMessage());
        }
    }
}
