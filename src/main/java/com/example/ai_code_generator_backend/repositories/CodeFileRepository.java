package com.example.ai_code_generator_backend.repositories;

import com.example.ai_code_generator_backend.models.CodeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeFileRepository extends JpaRepository<CodeFile, Long> {
    List<CodeFile> findByProjectId(Long projectId);
}