package com.genai.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {
    
    @NotBlank(message = "Prompt is required")
    private String prompt;
    
    private String model; // "ollama" or "openai"
    
    public ChatRequest() {
    }
    
    public ChatRequest(String prompt, String model) {
        this.prompt = prompt;
        this.model = model;
    }
    
    public String getPrompt() {
        return prompt;
    }
    
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
}

