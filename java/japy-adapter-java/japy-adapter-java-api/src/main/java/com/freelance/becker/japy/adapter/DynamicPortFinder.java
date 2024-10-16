package com.freelance.becker.japy.adapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DynamicPortFinder {

    private static final int START_PORT = 49152;
    private static final int END_PORT = 65535;

    private String host;
    private HttpClient httpClient;

    public DynamicPortFinder(String host) {
        this.host = host;
        this.httpClient = HttpClient.newHttpClient();
    }

    public int findPort() {

        for (int portToCheck = START_PORT; portToCheck < END_PORT; portToCheck++) {
            URI callUrl = URI.create(String.format("http://%s:%s/japy/is_alive", host, portToCheck));
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(callUrl)
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    return portToCheck;
                }
            } catch (IOException | InterruptedException ignored) {
            }
        }

        throw new NoPythonServerPortFoundException(START_PORT, END_PORT);
    }
}
