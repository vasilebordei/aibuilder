package com.example.ai_code_generator_backend.repositories;

import com.example.ai_code_generator_backend.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // No extra methods needed for basic CRUD operations
}