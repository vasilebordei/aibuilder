package com.example.ai_code_generator_backend.services;

import com.example.ai_code_generator_backend.dtos.CodeFileRequestDTO;
import com.example.ai_code_generator_backend.dtos.CodeFileResponseDTO;
import com.example.ai_code_generator_backend.models.CodeFile;
import com.example.ai_code_generator_backend.models.CodeFileType;
import com.example.ai_code_generator_backend.models.Project;
import com.example.ai_code_generator_backend.repositories.CodeFileRepository;
import com.example.ai_code_generator_backend.repositories.ProjectRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeFileService {

    private final CodeFileRepository codeFileRepository;
    private final ProjectRepository projectRepository; // pentru a căuta proiectul

    // Metoda existentă:
    public CodeFileResponseDTO createCodeFile(Project project, CodeFileRequestDTO request) {
        CodeFile codeFile = CodeFile.builder()
                .fileName(request.fileName())
                .fileContent(request.fileContent())
                .fileType(CodeFileType.valueOf(request.fileType().toUpperCase()))
                .project(project)
                .build();
        CodeFile saved = codeFileRepository.save(codeFile);
        return mapToResponseDTO(saved);
    }

    // Noua metodă pentru Controller-ul separat:
    public CodeFileResponseDTO createCodeFile(Long projectId, CodeFileRequestDTO request) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isEmpty()) {
            throw new RuntimeException("Proiectul cu id-ul " + projectId + " nu a fost găsit.");
        }
        return createCodeFile(projectOpt.get(), request);
    }

    public Optional<CodeFileResponseDTO> getCodeFileById(Long id) {
        return codeFileRepository.findById(id).map(this::mapToResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<CodeFileResponseDTO> getCodeFilesByProjectId(Long projectId) {
        return codeFileRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public CodeFileResponseDTO updateCodeFile(Long id, CodeFileRequestDTO request) {
        Optional<CodeFile> optionalCodeFile = codeFileRepository.findById(id);
        if(optionalCodeFile.isPresent()){
            CodeFile codeFile = optionalCodeFile.get();
            codeFile.setFileName(request.fileName());
            codeFile.setFileContent(request.fileContent());
            codeFile.setFileType(CodeFileType.valueOf(request.fileType().toUpperCase()));
            CodeFile saved = codeFileRepository.save(codeFile);
            return mapToResponseDTO(saved);
        }
        return null;
    }

    public void deleteCodeFile(Long id) {
        codeFileRepository.deleteById(id);
    }

    private CodeFileResponseDTO mapToResponseDTO(CodeFile codeFile) {
        return new CodeFileResponseDTO(
                codeFile.getId(),
                codeFile.getFileName(),
                codeFile.getFileContent(),
                codeFile.getFileType().name()
        );
    }
}