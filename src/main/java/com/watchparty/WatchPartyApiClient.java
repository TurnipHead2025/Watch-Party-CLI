package com.watchparty;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// Sends GET requests to the Watch Party API and extracts the values the CLI needs.

public class WatchPartyApiClient {
    private static final String API_BASE_URL = "http://localhost:8080";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WatchPartyApiClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public List<String> getProductions() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/productions"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        JsonNode page = objectMapper.readTree(response.body());
        List<String> titles = new ArrayList<>();

        for (JsonNode production : page.path("content")) {
            titles.add(production.path("title").asText());
        }
        return titles;
    }
    
    public String getBeatContent(long beatId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_BASE_URL + "/beats/" + beatId))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        JsonNode beat = objectMapper.readTree(response.body());
        return beat.path("content").asText();
        }

    public int getCurrentBeatIndex(long sessionId) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_BASE_URL + "/sessions/" + sessionId))
            .GET()
            .build();

    HttpResponse<String> response = httpClient.send(
            request,
            HttpResponse.BodyHandlers.ofString()
    );

    JsonNode session = objectMapper.readTree(response.body());
    return session.path("currentBeatIndex").asInt();
    }  
    
    public int getCurrentSceneIndex(long sessionId) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_BASE_URL + "/sessions/" + sessionId))
            .GET()
            .build();

    HttpResponse<String> response = httpClient.send(
            request,
            HttpResponse.BodyHandlers.ofString()
    );

    JsonNode session = objectMapper.readTree(response.body());
    return session.path("currentSceneIndex").asInt();
    }
}
