package com.example.ai_code_generator_backend.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.model.Media;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class GeminiService {

  private final VertexAiGeminiChatModel chatModel;

  @Autowired
  public GeminiService(VertexAiGeminiChatModel chatModel) {
    this.chatModel = chatModel;
  }

  public String getGeminiResponse(String promptText) {
    Prompt prompt = new Prompt(new UserMessage(promptText));

    return chatModel.call(prompt).getResult().getOutput().getText();
  }


  public String getGeminiResponseWithImage(String promptText, MultipartFile image) throws IOException {
    // Convert the MultipartFile to byte[]
    byte[] imageBytes = image.getBytes();

    // Determine the MIME type of the file
    MimeType mimeType = MimeType.valueOf(image.getContentType());

    // Create a Media object using the byte array and MIME type
    Media media = new Media(mimeType, new ByteArrayResource(imageBytes));

    // Create the Prompt object with the media included
    Prompt prompt = new Prompt(new UserMessage(promptText, List.of(media)));

    // Get the response from the model
    return chatModel.call(prompt).getResult().getOutput().getText();
  }

  // Example method to handle PDF
  public String getGeminiResponseWithPdf(String prompt, MultipartFile pdfFile) throws IOException {
    // Extract text from the PDF
    String extractedText = extractTextFromPdf(pdfFile);
    byte[] imageBytes = pdfFile.getBytes();

    // Determine the MIME type of the file
    MimeType mimeType = MimeType.valueOf(pdfFile.getContentType());

    // Create a Media object using the byte array and MIME type
    Media media = new Media(mimeType, new ByteArrayResource(imageBytes));
    // Combine the extracted text with the prompt (or process in another way)
    String fullText = prompt + " " + extractedText;
    Prompt promptWithPdf = new Prompt(new UserMessage(prompt, List.of(media)));
    // Your logic to handle the text and generate a response
    return chatModel.call(promptWithPdf).getResult().getOutput().getText();
  }

  private String extractTextFromPdf(MultipartFile pdfFile) throws IOException {
    PDDocument document = PDDocument.load(pdfFile.getInputStream());
    PDFTextStripper stripper = new PDFTextStripper();
    String text = stripper.getText(document);
    document.close();
    return text;
  }
}
