package com.example.ai_code_generator_backend.controllers;

import com.example.ai_code_generator_backend.dtos.ProjectRequestDTO;
import com.example.ai_code_generator_backend.dtos.ProjectResponseDTO;
import com.example.ai_code_generator_backend.dtos.CodeFileResponseDTO;
import com.example.ai_code_generator_backend.models.Project;
import com.example.ai_code_generator_backend.models.User;
import com.example.ai_code_generator_backend.services.ProjectService;
import com.example.ai_code_generator_backend.services.CodeFileService;
import com.example.ai_code_generator_backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final CodeFileService codeFileService;
    private final UserService userService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(project -> {
                    List<CodeFileResponseDTO> fileDTOs = codeFileService.getCodeFilesByProjectId(project.getId());
                    ProjectResponseDTO responseDTO = new ProjectResponseDTO(
                            project.getId(),
                            project.getName(),
                            project.getDbSchema(),
                            fileDTOs
                    );
                    return ResponseEntity.ok(responseDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@RequestBody ProjectRequestDTO request, Principal principal) {
        // Obținem utilizatorul din token
        var userOpt = userService.getUserByUsername(principal.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User owner = userOpt.get();
        // Creăm proiectul (fără fișiere încă)
        Project project = new Project();
        project.setName(request.name());
        project.setDbSchema(request.dbSchema());
        project.setOwner(owner);
        Project createdProject = projectService.createProject(project);

        // Pentru fiecare fișier din cerere, creăm un fișier asociat proiectului
        List<CodeFileResponseDTO> fileDTOs = new ArrayList<>();
        request.codeFiles().forEach(fileRequest -> {
            CodeFileResponseDTO fileResponse = codeFileService.createCodeFile(createdProject, fileRequest);
            fileDTOs.add(fileResponse);
        });

        ProjectResponseDTO responseDTO = new ProjectResponseDTO(
                createdProject.getId(),
                createdProject.getName(),
                createdProject.getDbSchema(),
                fileDTOs
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @RequestBody ProjectRequestDTO request) {
        return projectService.getProjectById(id)
                .map(existing -> {
                    existing.setName(request.name());
                    existing.setDbSchema(request.dbSchema());
                    // Actualizarea fișierelor se poate face separat prin CodeFileController
                    Project updated = projectService.updateProject(existing);
                    List<CodeFileResponseDTO> fileDTOs = codeFileService.getCodeFilesByProjectId(updated.getId());
                    ProjectResponseDTO responseDTO = new ProjectResponseDTO(
                            updated.getId(),
                            updated.getName(),
                            updated.getDbSchema(),
                            fileDTOs
                    );
                    return ResponseEntity.ok(responseDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}