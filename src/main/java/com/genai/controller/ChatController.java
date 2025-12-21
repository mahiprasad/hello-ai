package com.genai.controller;

import com.genai.dto.ChatRequest;
import com.genai.dto.ChatResponse;
import com.genai.service.OllamaService;
import com.genai.service.OpenAIService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    
    private final OllamaService ollamaService;
    private final OpenAIService openAIService;
    
    public ChatController(OllamaService ollamaService, OpenAIService openAIService) {
        logger.debug("ChatController initialized with OllamaService and OpenAIService");
        this.ollamaService = ollamaService;
        this.openAIService = openAIService;
    }
    
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        logger.info("=== Received chat request ===");
        logger.debug("Request prompt: {}", request.getPrompt());
        logger.debug("Request model: {}", request.getModel());
        
        String response;
        String model;
        
        String modelChoice = request.getModel() != null ? request.getModel().toLowerCase() : "ollama";
        logger.debug("Model choice (normalized): {}", modelChoice);
        
        if ("openai".equals(modelChoice) || "2".equals(modelChoice)) {
            logger.info("Routing to OpenAI service");
            response = openAIService.getResponse(request.getPrompt());
            model = "OpenAI";
        } else {
            logger.info("Routing to Ollama service");
            response = ollamaService.getResponse(request.getPrompt());
            model = "Ollama";
        }
        
        logger.debug("Response length: {} characters", response != null ? response.length() : 0);
        logger.info("=== Returning chat response ===");
        
        return ResponseEntity.ok(new ChatResponse(response, model));
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Hello, AI! Service is running.");
    }
}

