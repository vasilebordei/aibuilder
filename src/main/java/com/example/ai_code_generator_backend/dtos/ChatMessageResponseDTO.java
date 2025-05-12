package com.example.ai_code_generator_backend.dtos;

import java.time.LocalDateTime;

public record ChatMessageResponseDTO(Long id, String sender, String message, LocalDateTime timestamp) { }