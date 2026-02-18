package com.example.tp01dev;

import org.junit.jupiter.api.Test;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadTest {

    @Test
    void testConcurrentAccess() throws InterruptedException {
        int numberOfThreads = 20; // 20 utilisateurs simultanés
        int requestsPerThread = 5; // Chaque utilisateur fait 5 requêtes
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        AtomicInteger successCount = new AtomicInteger(0);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/votre-app/api/categories"))
                .GET()
                .build();

        for (int i = 0; i < numberOfThreads * requestsPerThread; i++) {
            executor.submit(() -> {
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == 200) {
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        System.out.println("Total de requêtes réussies : " + successCount.get());
        assertEquals(100, successCount.get(), "Toutes les requêtes devraient réussir");
    }
}