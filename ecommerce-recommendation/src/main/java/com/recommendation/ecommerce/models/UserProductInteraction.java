package com.recommendation.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_product_interaction") // Explicitly specify the table name to avoid conflicts
public class UserProductInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for the interaction
    private Long userId; // Foreign key to the User table
    private Long productId; // Foreign key to the Product table
    private int rating; // Rating or interaction score

    // Default constructor
    public UserProductInteraction() {
    }

    // Constructor with userId, productId, and rating
    public UserProductInteraction(Long userId, Long productId, int rating) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}