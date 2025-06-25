package com.recommendation.ecommerce.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recommendation.ecommerce.models.Product;
import com.recommendation.ecommerce.repositories.ProductRepository;
import com.recommendation.ecommerce.repositories.UserProductInteractionRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserProductInteractionRepository interactionRepository;

    public DataLoader(ProductRepository productRepository, UserProductInteractionRepository interactionRepository) {
        this.productRepository = productRepository;
        this.interactionRepository = interactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Clear old interactions and products
        interactionRepository.deleteAll();
        productRepository.deleteAll();

        // Load products from JSON file
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/static/products.json");
        if (inputStream == null) {
            throw new RuntimeException("products.json file not found in /static directory");
        }
        List<Product> products = mapper.readValue(inputStream, new TypeReference<List<Product>>() {});
        productRepository.saveAll(products);

        System.out.println("DataLoader: Products have been successfully loaded. Count: " + products.size());
    }
}