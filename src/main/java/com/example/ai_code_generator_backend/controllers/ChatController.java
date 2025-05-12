package com.example.ai_code_generator_backend.controllers;

import com.example.ai_code_generator_backend.dtos.ChatMessageRequestDTO;
import com.example.ai_code_generator_backend.dtos.ChatMessageResponseDTO;
import com.example.ai_code_generator_backend.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * Endpoint pentru a trimite un mesaj de la utilizator către chat-ul unui proiect,
     * iar AI-ul va răspunde.
     * Exemplu: POST /api/chats/project/{projectId}
     */
    @PostMapping("/project/{projectId}")
    public ResponseEntity<ChatMessageResponseDTO> addUserMessage(
            @PathVariable Long projectId,
            @RequestBody ChatMessageRequestDTO request) {
        ChatMessageResponseDTO response = chatService.addUserMessage(projectId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint pentru a obține întreaga conversație a unui proiect.
     * Exemplu: GET /api/chats/project/{projectId}
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ChatMessageResponseDTO>> getChatMessages(@PathVariable Long projectId) {
        List<ChatMessageResponseDTO> messages = chatService.getChatMessages(projectId);
        return ResponseEntity.ok(messages);
    }
}