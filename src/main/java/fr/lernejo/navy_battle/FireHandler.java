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

public class FireHandler implements HttpHandler {

    public int schema_value(String schema) {
        String default_schema = "{ \"$schema\": \"http://json-schema.org/schema#\",\"type\": \"object\",\"properties\": { \"consequence\": {\"type\": \"string,\"enum\": [\"miss\", \"hit\", \"sunk\"]},\"shipLeft\":{\"type\": \"boolean\"}},\"required\": [ \"consequences\",\"shipLeft\"]\"}";
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
        String response = "";
        if (exchange.getRequestMethod().equals("GET")) {
            response = "{\"consequence\": \"sunk\", \"shipLeft\": true}";
            if (schema_value(response) == 1)
                exchange.sendResponseHeaders(202, response.length());
            else
                exchange.sendResponseHeaders(400, 1);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
