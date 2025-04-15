package com.example.ai_code_generator_backend.dtos;

import java.util.List;

public record ProjectResponseDTO(Long id, String name, String dbSchema, List<CodeFileResponseDTO> codeFiles) { }