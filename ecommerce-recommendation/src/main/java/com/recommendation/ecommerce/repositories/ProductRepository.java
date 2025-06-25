package com.recommendation.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recommendation.ecommerce.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}