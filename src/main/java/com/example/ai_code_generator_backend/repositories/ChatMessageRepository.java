package com.example.ai_code_generator_backend.repositories;

import com.example.ai_code_generator_backend.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByProjectIdOrderByTimestampAsc(Long projectId);
}