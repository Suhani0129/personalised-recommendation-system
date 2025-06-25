package com.recommendation.ecommerce.services;

import com.recommendation.ecommerce.models.Product;
import com.recommendation.ecommerce.models.UserProductInteraction;
import com.recommendation.ecommerce.repositories.ProductRepository;
import com.recommendation.ecommerce.repositories.UserProductInteractionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final ProductRepository productRepository;
    private final UserProductInteractionRepository interactionRepository;

    public RecommendationService(ProductRepository productRepository, UserProductInteractionRepository interactionRepository) {
        this.productRepository = productRepository;
        this.interactionRepository = interactionRepository;
    }

    public List<Product> getRecommendationsForUser(Long userId) {
        // Fetch all interactions for the given user
        List<UserProductInteraction> userInteractions = interactionRepository.findByUserId(userId);

        // If no interactions, return random products
        if (userInteractions.isEmpty()) {
            List<Product> allProducts = productRepository.findAll();
            Collections.shuffle(allProducts); // Randomize the product list
            return allProducts.stream().limit(5).collect(Collectors.toList());
        }

        // Get product IDs the user has interacted with
        Set<Long> interactedProductIds = userInteractions.stream()
                .map(UserProductInteraction::getProductId)
                .collect(Collectors.toSet());

        // Find similar users based on shared product interactions
        List<Long> similarUserIds = interactionRepository.findAll().stream()
                .filter(interaction -> interactedProductIds.contains(interaction.getProductId()) && !interaction.getUserId().equals(userId))
                .map(UserProductInteraction::getUserId)
                .distinct()
                .collect(Collectors.toList());

        // Get product IDs interacted with by similar users
        Set<Long> similarUserProductIds = interactionRepository.findAll().stream()
                .filter(interaction -> similarUserIds.contains(interaction.getUserId()))
                .map(UserProductInteraction::getProductId)
                .collect(Collectors.toSet());

        // Exclude products the user has already interacted with
        similarUserProductIds.removeAll(interactedProductIds);

        // Fetch and return the recommended products
        return productRepository.findAll().stream()
                .filter(product -> similarUserProductIds.contains(product.getId()))
                .limit(5) // Limit to 5 recommendations
                .collect(Collectors.toList());
    }
}