package com.freelance.becker.japy.adapter;

import com.freelance.becker.japy.api.MethodReturnValue;
import com.freelance.becker.japy.api.PythonMethod;
import com.freelance.becker.japy.api.exception.PythonMethodCallException;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

class PythonHttpClient {

    private HttpClient httpClient;
    private URI callUrl;

    public PythonHttpClient(String serverAddress, int port) {
        this.httpClient = HttpClient.newHttpClient();
        this.callUrl = URI.create(String.format("http://%s:%s/japy/call_method", serverAddress, port));
    }

    private String pythonMethodToJson(PythonMethod pythonMethod) {
        Gson gson = new Gson();
        return gson.toJson(pythonMethod);
    }

    private MethodReturnValue jsonToMethodReturnType(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, MethodReturnValue.class);
    }

    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(callUrl)
                .POST(HttpRequest.BodyPublishers.ofString(pythonMethodToJson(pythonMethod)))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            handleIfErrorResponse(response);

            MethodReturnValue returnValue = jsonToMethodReturnType(response.body());
            if(returnValue.getReturnValue() == null){
                return Optional.empty();
            }
            return Optional.of(returnValue);
        } catch (IOException | InterruptedException e) {
            throw new PythonMethodCallException(e);
        }
    }

    private void handleIfErrorResponse(HttpResponse<String> response) throws PythonMethodCallException {
        if(response.statusCode() == 200){
            return;
        }

        ErrorResponseObject errorResponseObject = new Gson().fromJson(response.body(), ErrorResponseObject.class);

        String message = errorResponseObject.getError();

        if (response.statusCode() == 404){
            message += ("\n" + errorResponseObject.getExistingMethods());
        }

        throw new PythonMethodCallException(message);
    }

}
