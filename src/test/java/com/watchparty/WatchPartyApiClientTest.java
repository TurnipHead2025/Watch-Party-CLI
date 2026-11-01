package com.watchparty;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WatchPartyApiClientTest {
    @Test
    void getProductionsReturnsTitlesFromResponse() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(httpClient.send(any(), any(HttpResponse.BodyHandler.class)))
                .thenReturn(response);
        when(response.body()).thenReturn(
                "{\"content\":[{\"title\":\"Hamilton\"},{\"title\":\"Star Trek\"}]}"
        );

        WatchPartyApiClient apiClient = new WatchPartyApiClient(httpClient);

        assertEquals(
                List.of("Hamilton", "Star Trek"),
                apiClient.getProductions()
        );
    }

    @Test
    void getBeatContentReturnsContentFromResponse() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(httpClient.send(any(), any(HttpResponse.BodyHandler.class)))
                .thenReturn(response);
        when(response.body()).thenReturn(
                "{\"id\":7,\"content\":\"Fake beat text\"}"
        );

        WatchPartyApiClient apiClient = new WatchPartyApiClient(httpClient);

        assertEquals("Fake beat text", apiClient.getBeatContent(7));
    }

        @Test
    void getCurrentBeatIndexReturnsValueFromSessionResponse() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(httpClient.send(any(), any(HttpResponse.BodyHandler.class)))
                .thenReturn(response);
        when(response.body()).thenReturn(
                "{\"id\":1,\"currentSceneIndex\":2,\"currentBeatIndex\":4}"
        );

        WatchPartyApiClient apiClient = new WatchPartyApiClient(httpClient);

        assertEquals(4, apiClient.getCurrentBeatIndex(1));
    }

        @Test
    void getCurrentSceneIndexReturnsValueFromSessionResponse() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        HttpResponse<String> response = mock(HttpResponse.class);

        when(httpClient.send(any(), any(HttpResponse.BodyHandler.class)))
                .thenReturn(response);
        when(response.body()).thenReturn(
                "{\"id\":1,\"currentSceneIndex\":2,\"currentBeatIndex\":4}"
        );

        WatchPartyApiClient apiClient = new WatchPartyApiClient(httpClient);

        assertEquals(2, apiClient.getCurrentSceneIndex(1));
    }
}