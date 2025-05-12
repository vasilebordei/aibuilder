package com.example.ai_code_generator_backend.services;

import com.example.ai_code_generator_backend.models.Project;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    // Simulează un răspuns AI pe baza mesajului utilizatorului și a contextului proiectului.
    public String generateReply(String userMessage, Project project) {
        // Într-o implementare reală, aici ai apela un API (ex. OpenAI) pentru a genera un răspuns.
        return "Răspuns AI pentru mesajul: " + userMessage;
    }
}