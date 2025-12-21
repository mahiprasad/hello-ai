package com.genai.dto;

public class ChatResponse {
    
    private String response;
    private String model;
    
    public ChatResponse() {
    }
    
    public ChatResponse(String response, String model) {
        this.response = response;
        this.model = model;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
}

