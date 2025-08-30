package com.angel.javafx.app.javafxapp.services;

import com.angel.javafx.app.javafxapp.models.Product;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class ProductServiceWebClient implements ProductService {

    private final WebClient webClient;

    public ProductServiceWebClient() {
        String baseUrl = "http://localhost:8080";
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public List<Product> findAll() {
        return webClient
                .get()
                .retrieve()
                .bodyToFlux(Product.class)
                .collectList()
                .block();
    }

    @Override
    public Product save(Product product) {
        return webClient
                .post()
                .body(Mono.just(product), Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public void update(Product product) {
        webClient
                .put()
                .uri("/{id}", product.getId())
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public void delete(Product product) {
        webClient
                .delete()
                .uri("/{id}", product.getId())
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
