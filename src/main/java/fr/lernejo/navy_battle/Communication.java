package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Communication {
    final int port;

    public Communication(int port){this.port=port;}

    public String POSTRequest(String adversaryUrl) throws IOException, InterruptedException{
        HttpClient MU = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(adversaryUrl + "/api/game/start")).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"I will crush you\"}")).build();
        HttpResponse<String> response = MU.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
