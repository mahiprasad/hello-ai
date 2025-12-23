package com.genai.resolver;

import com.genai.dto.ChatResponse;
import com.genai.service.OllamaService;
import com.genai.service.OpenAIService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatResolver {
    
    private final OllamaService ollamaService;
    private final OpenAIService openAIService;
    
    public ChatResolver(OllamaService ollamaService, OpenAIService openAIService) {
        this.ollamaService = ollamaService;
        this.openAIService = openAIService;
    }
    
    @QueryMapping
    public String health() {
        return "Hello, AI! Service is running.";
    }
    
    @QueryMapping(name = "chat")
    public ChatResponse chatQuery(@Argument String prompt, @Argument String model) {
        return processChatRequest(prompt, model);
    }
    
    @MutationMapping(name = "chat")
    public ChatResponse chatMutation(@Argument String prompt, @Argument String model) {
        return processChatRequest(prompt, model);
    }
    
    private ChatResponse processChatRequest(String prompt, String model) {
        if (prompt == null || prompt.trim().isEmpty()) {
            throw new IllegalArgumentException("Prompt is required");
        }
        
        String response;
        String modelName;
        
        String modelChoice = model != null ? model.toLowerCase() : "ollama";
        
        if ("openai".equals(modelChoice) || "2".equals(modelChoice)) {
            response = openAIService.getResponse(prompt);
            modelName = "OpenAI";
        } else {
            response = ollamaService.getResponse(prompt);
            modelName = "Ollama";
        }
        
        return new ChatResponse(response, modelName);
    }
}

