package com.recommendation.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recommendation.ecommerce.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}