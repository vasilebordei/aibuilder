package com.example.ai_code_generator_backend.dtos;

import java.util.List;

public record ProjectRequestDTO(String name, String dbSchema, List<CodeFileRequestDTO> codeFiles) { }