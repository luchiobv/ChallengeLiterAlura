package com.desafioConsumoApi.Books.finder.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsumAPI {
    private static final Logger logger = Logger.getLogger(ConsumAPI.class.getName());

    public String gettingData(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
                        response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            return json;
        } catch (IOException | InterruptedException e) {
            logger.log(Level.SEVERE, "Error while retrieving data from API", e);
            throw new RuntimeException("Error while retrieving data from API", e);
        }
    }
}
