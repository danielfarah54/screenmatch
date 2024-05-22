package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FetchApi {
  public String getData(String address) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(address))
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response;
    try {
      response = client
          .send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return response.body();
  }
}
