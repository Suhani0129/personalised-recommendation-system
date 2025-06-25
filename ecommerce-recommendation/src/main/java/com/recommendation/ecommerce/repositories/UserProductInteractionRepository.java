package com.recommendation.ecommerce.repositories;

import com.recommendation.ecommerce.models.UserProductInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductInteractionRepository extends JpaRepository<UserProductInteraction, Long> {
    List<UserProductInteraction> findByUserId(Long userId);
}