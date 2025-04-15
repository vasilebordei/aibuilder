package com.example.ai_code_generator_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "code_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Lob
    private String fileContent;

    @Enumerated(EnumType.STRING)
    private CodeFileType fileType;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
