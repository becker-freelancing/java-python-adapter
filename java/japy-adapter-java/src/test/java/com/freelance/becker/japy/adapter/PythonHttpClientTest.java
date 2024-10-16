package com.freelance.becker.japy.adapter;

import com.freelance.becker.japy.api.MethodReturnValue;
import com.freelance.becker.japy.api.PythonMethod;
import com.freelance.becker.japy.api.exception.PythonMethodCallException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PythonHttpClientTest {

    @Test
    void testCallMethodThrowsExceptionOnErrorRequest() throws IOException, InterruptedException, PythonMethodCallException {
        try (MockedStatic<HttpClient> staticHttpClient = mockStatic(HttpClient.class)) {
            HttpResponse httpResponse = mock(HttpResponse.class);
            doReturn(400).when(httpResponse).statusCode();
            doReturn("{\"error\": \"This is an Error\"}").when(httpResponse).body();

            HttpClient httpClient = mock(HttpClient.class);
            doReturn(httpResponse).when(httpClient).send(any(), any());

            staticHttpClient.when(() -> HttpClient.newHttpClient()).thenReturn(httpClient);

            PythonHttpClient pythonHttpClient = new PythonHttpClient("0.0.0.0", 0);

            PythonMethodCallException exception = assertThrows(PythonMethodCallException.class, () -> pythonHttpClient.callMethod(mock(PythonMethod.class)));
            assertEquals("This is an Error", exception.getMessage());
        }
    }

    @Test
    void testCallMethodThrowsExceptionWithExistingMethodsOnError404Request() throws IOException, InterruptedException, PythonMethodCallException {
        try (MockedStatic<HttpClient> staticHttpClient = mockStatic(HttpClient.class)) {
            HttpResponse httpResponse = mock(HttpResponse.class);
            doReturn(404).when(httpResponse).statusCode();
            doReturn("{\"error\": \"This is an Error\", \"existingMethods\": [\"m_1\", \"m_2\"]}").when(httpResponse).body();

            HttpClient httpClient = mock(HttpClient.class);
            doReturn(httpResponse).when(httpClient).send(any(), any());

            staticHttpClient.when(() -> HttpClient.newHttpClient()).thenReturn(httpClient);

            PythonHttpClient pythonHttpClient = new PythonHttpClient("0.0.0.0", 0);

            PythonMethodCallException exception = assertThrows(PythonMethodCallException.class, () -> pythonHttpClient.callMethod(mock(PythonMethod.class)));
            assertEquals("This is an Error\n[m_1, m_2]", exception.getMessage());
        }
    }

    @Test
    void testCallMethodReturnsEmptyOptionalOnNullReturn() throws IOException, InterruptedException, PythonMethodCallException {
        try (MockedStatic<HttpClient> staticHttpClient = mockStatic(HttpClient.class)) {
            HttpResponse httpResponse = mock(HttpResponse.class);
            doReturn(200).when(httpResponse).statusCode();
            doReturn("{\"returnValue\":null, \"className\":\"dummy\"}").when(httpResponse).body();

            HttpClient httpClient = mock(HttpClient.class);
            doReturn(httpResponse).when(httpClient).send(any(), any());

            staticHttpClient.when(() -> HttpClient.newHttpClient()).thenReturn(httpClient);

            PythonHttpClient pythonHttpClient = new PythonHttpClient("0.0.0.0", 0);

            Optional<MethodReturnValue> methodReturnValue =
                    pythonHttpClient.callMethod(mock(PythonMethod.class));

            assertTrue(methodReturnValue.isEmpty());
        }
    }

    @Test
    void testCallMethodReturnsReturnValueWithContent() throws IOException, InterruptedException, PythonMethodCallException {
        try (MockedStatic<HttpClient> staticHttpClient = mockStatic(HttpClient.class)) {
            HttpResponse httpResponse = mock(HttpResponse.class);
            doReturn(200).when(httpResponse).statusCode();
            doReturn("{\"returnValue\":\"Test\", \"className\":\"dummy\"}").when(httpResponse).body();

            HttpClient httpClient = mock(HttpClient.class);
            doReturn(httpResponse).when(httpClient).send(any(), any());

            staticHttpClient.when(HttpClient::newHttpClient).thenReturn(httpClient);

            PythonHttpClient pythonHttpClient = new PythonHttpClient("0.0.0.0", 0);

            Optional<MethodReturnValue> methodReturnValue =
                    pythonHttpClient.callMethod(mock(PythonMethod.class));

            assertTrue(methodReturnValue.isPresent());
            assertEquals("dummy", methodReturnValue.get().getClassName());
            assertEquals("Test", methodReturnValue.get().getReturnValue());
        }
    }

    @Test
    void testCallMethodThrowsExceptionOnIoException() throws IOException, InterruptedException, PythonMethodCallException {
        try (MockedStatic<HttpClient> staticHttpClient = mockStatic(HttpClient.class)) {
            HttpClient httpClient = mock(HttpClient.class);
            doThrow(IOException.class).when(httpClient).send(any(), any());

            staticHttpClient.when(() -> HttpClient.newHttpClient()).thenReturn(httpClient);

            PythonHttpClient pythonHttpClient = new PythonHttpClient("0.0.0.0", 0);

            PythonMethodCallException exception = assertThrows(PythonMethodCallException.class, () -> pythonHttpClient.callMethod(mock(PythonMethod.class)));
            assertInstanceOf(IOException.class, exception.getCause());
        }
    }


    @Test
    void testCallMethodThrowsExceptionOnInterruptedException() throws IOException, InterruptedException, PythonMethodCallException {
        try (MockedStatic<HttpClient> staticHttpClient = mockStatic(HttpClient.class)) {
            HttpClient httpClient = mock(HttpClient.class);
            doThrow(InterruptedException.class).when(httpClient).send(any(), any());

            staticHttpClient.when(() -> HttpClient.newHttpClient()).thenReturn(httpClient);

            PythonHttpClient pythonHttpClient = new PythonHttpClient("0.0.0.0", 0);

            PythonMethodCallException exception = assertThrows(PythonMethodCallException.class, () -> pythonHttpClient.callMethod(mock(PythonMethod.class)));
            assertInstanceOf(InterruptedException.class, exception.getCause());
        }
    }
}