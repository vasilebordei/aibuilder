package com.example.ai_code_generator_backend.controllers;

import com.example.ai_code_generator_backend.services.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

  private final GeminiService geminiService;

  @Autowired
  public GeminiController(GeminiService geminiService) {
    this.geminiService = geminiService;
  }

  @GetMapping("/prompt")
  public String respondToPrompt(@RequestParam String prompt) {
    return geminiService.getGeminiResponse(prompt);
  }

  // Handle text and image input via POST request
  @PostMapping(path = "/sendMessageWithImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> sendMessageWithImage(@RequestParam String prompt,
                                                     @RequestParam MultipartFile image) {
    if (image.isEmpty()) {
      return new ResponseEntity<>("No image uploaded", HttpStatus.BAD_REQUEST);
    }

    try {
      // Pass the text prompt and image to the service layer
      String response = geminiService.getGeminiResponseWithImage(prompt, image);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Error processing the image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      // Catch any other exceptions for better error handling
      return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // Handle text and PDF input via POST request
  @PostMapping(path = "/sendMessageWithPdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> sendMessageWithPdf(@RequestParam String prompt,
                                                   @RequestParam MultipartFile pdf) {
    if (pdf.isEmpty()) {
      return new ResponseEntity<>("No PDF file uploaded", HttpStatus.BAD_REQUEST);
    }

    try {
      // Pass the text prompt and PDF file to the service layer
      String response = geminiService.getGeminiResponseWithPdf(prompt, pdf);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Error processing the PDF: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      // Catch any other exceptions for better error handling
      return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
