package com.example.ai_code_generator_backend.services;

import com.example.ai_code_generator_backend.dtos.ChatMessageRequestDTO;
import com.example.ai_code_generator_backend.dtos.ChatMessageResponseDTO;
import com.example.ai_code_generator_backend.models.ChatMessage;
import com.example.ai_code_generator_backend.models.ChatMessageType;
import com.example.ai_code_generator_backend.models.Project;
import com.example.ai_code_generator_backend.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ProjectService projectService; // pentru a obține proiectul
    private final AIService aiService; // pentru generarea răspunsului AI

    @Transactional
    public ChatMessageResponseDTO addUserMessage(Long projectId, ChatMessageRequestDTO request) {
        // Căutăm proiectul după ID
        Project project = projectService.getProjectById(projectId)
                .orElseThrow(() -> new RuntimeException("Proiectul cu id-ul " + projectId + " nu a fost găsit."));

        // Salvăm mesajul utilizatorului
        ChatMessage userMessage = ChatMessage.builder()
                .sender(ChatMessageType.USER)
                .message(request.message())
                .project(project)
                .build();
        ChatMessage savedUserMessage = chatMessageRepository.save(userMessage);

        // Apelăm AI-ul pentru a genera răspunsul
        String aiReply = aiService.generateReply(request.message(), project);

        // Salvăm mesajul AI
        ChatMessage aiMessage = ChatMessage.builder()
                .sender(ChatMessageType.AI)
                .message(aiReply)
                .project(project)
                .build();
        ChatMessage savedAiMessage = chatMessageRepository.save(aiMessage);

        // Returnăm ultimul mesaj AI ca răspuns (sau întregul istoric, după preferință)
        return mapToResponseDTO(savedAiMessage);
    }

    public List<ChatMessageResponseDTO> getChatMessages(Long projectId) {
        List<ChatMessage> messages = chatMessageRepository.findByProjectIdOrderByTimestampAsc(projectId);
        return messages.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private ChatMessageResponseDTO mapToResponseDTO(ChatMessage chatMessage) {
        return new ChatMessageResponseDTO(
                chatMessage.getId(),
                chatMessage.getSender().name(),
                chatMessage.getMessage(),
                chatMessage.getTimestamp()
        );
    }
}