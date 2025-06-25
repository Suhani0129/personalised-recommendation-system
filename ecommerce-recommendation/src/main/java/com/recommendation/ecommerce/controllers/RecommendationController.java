package com.recommendation.ecommerce.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recommendation.ecommerce.models.Product;
import com.recommendation.ecommerce.repositories.ProductRepository;
import com.recommendation.ecommerce.services.RecommendationService;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final ProductRepository productRepository; // Inject ProductRepository

    public RecommendationController(RecommendationService recommendationService, ProductRepository productRepository) {
        this.recommendationService = recommendationService;
        this.productRepository = productRepository;
    }

    @GetMapping("/{userId}")
    public List<Product> getRecommendations(@PathVariable Long userId) {
        return recommendationService.getRecommendationsForUser(userId);
    }

    // Step 5: Add this endpoint
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // Use the injected repository
    }
}