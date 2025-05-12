package com.example.ai_code_generator_backend.repositories;

import com.example.ai_code_generator_backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // No extra methods needed for basic CRUD operations
}