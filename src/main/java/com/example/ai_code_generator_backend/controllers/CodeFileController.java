package com.example.ai_code_generator_backend.controllers;

import com.example.ai_code_generator_backend.dtos.CodeFileRequestDTO;
import com.example.ai_code_generator_backend.dtos.CodeFileResponseDTO;
import com.example.ai_code_generator_backend.models.CodeFile;
import com.example.ai_code_generator_backend.services.CodeFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/codefiles")
@RequiredArgsConstructor
public class CodeFileController {

    private final CodeFileService codeFileService;

    @PostMapping("/project/{projectId}")
    public ResponseEntity<CodeFileResponseDTO> addFileToProject(
            @PathVariable Long projectId,
            @RequestBody CodeFileRequestDTO request) {
        CodeFileResponseDTO response = codeFileService.createCodeFile(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<CodeFileResponseDTO>> getFilesByProject(@PathVariable Long projectId) {
        List<CodeFileResponseDTO> fileDTOs = codeFileService.getCodeFilesByProjectId(projectId);
        return ResponseEntity.ok(fileDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CodeFileResponseDTO> updateFile(
            @PathVariable Long id,
            @RequestBody CodeFileRequestDTO request) {
        CodeFileResponseDTO updated = codeFileService.updateCodeFile(id, request);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        codeFileService.deleteCodeFile(id);
        return ResponseEntity.noContent().build();
    }
}