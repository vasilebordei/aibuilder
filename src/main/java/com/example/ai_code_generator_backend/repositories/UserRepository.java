package com.example.ai_code_generator_backend.repositories;

import com.example.ai_code_generator_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // No extra methods needed for basic CRUD operations
    Optional<User> findByUsername(String username);
}