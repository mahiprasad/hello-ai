package com.genai.controller;

import com.genai.dto.ChatRequest;
import com.genai.dto.ChatResponse;
import com.genai.service.OllamaService;
import com.genai.service.OpenAIService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final OllamaService ollamaService;
    private final OpenAIService openAIService;
    
    public ChatController(OllamaService ollamaService, OpenAIService openAIService) {
        this.ollamaService = ollamaService;
        this.openAIService = openAIService;
    }
    
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        String response;
        String model;

        String modelChoice = request.getModel() != null ? request.getModel().toLowerCase() : "ollama";
        
        if ("openai".equals(modelChoice) || "2".equals(modelChoice)) {
            response = openAIService.getResponse(request.getPrompt());
            model = "OpenAI";
        } else {
            response = ollamaService.getResponse(request.getPrompt());
            model = "Ollama";
        }
        
        return ResponseEntity.ok(new ChatResponse(response, model));
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Hello, AI! Service is running.");
    }
}

