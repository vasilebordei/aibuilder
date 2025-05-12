package com.example.ai_code_generator_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatMessageType sender;

    @Lob
    private String message;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @PrePersist
    public void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}