package com.freelance.becker.japy.adapter;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DynamicPortFinderTest {

    @Test
    void testFindPortThrowsExceptionWhenNoPortIsFound() throws IOException, InterruptedException {
        try (MockedStatic<HttpClient> staticHttpClient = mockStatic(HttpClient.class)) {
            HttpResponse<?> httpResponse = mock(HttpResponse.class);
            doReturn(404).when(httpResponse).statusCode();

            HttpClient httpClient = mock(HttpClient.class);
            doReturn(httpResponse).when(httpClient).send(any(), any());

            staticHttpClient.when(HttpClient::newHttpClient).thenReturn(httpClient);

            assertThrows(NoPythonServerPortFoundException.class, () -> new DynamicPortFinder("localhost").findPort());
        }
    }


    @Test
    void testFindPort() throws IOException, InterruptedException {
        try (MockedStatic<HttpClient> staticHttpClient = mockStatic(HttpClient.class)) {
            HttpResponse<?> httpResponse = mock(HttpResponse.class);
            doReturn(200).when(httpResponse).statusCode();

            HttpClient httpClient = mock(HttpClient.class);
            doReturn(httpResponse).when(httpClient).send(any(), any());

            staticHttpClient.when(HttpClient::newHttpClient).thenReturn(httpClient);

            int port = new DynamicPortFinder("localhost").findPort();

            assertEquals(49152, port);
        }
    }

}