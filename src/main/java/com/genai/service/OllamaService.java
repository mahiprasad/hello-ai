package com.genai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OllamaService {
    
    private static final Logger logger = LoggerFactory.getLogger(OllamaService.class);
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${ollama.api.url:http://localhost:11434/api/generate}")
    private String ollamaApiUrl;
    
    @Value("${ollama.model:gpt-oss:120b-cloud}")
    private String ollamaModel;
    
    public OllamaService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    public String getResponse(String prompt) {
        try {
            String requestBody = String.format(
                "{\"model\":\"%s\",\"prompt\":\"%s\",\"stream\":false}",
                ollamaModel, escapeJson(prompt)
            );
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            
            logger.info("Calling Ollama API with model: {}", ollamaModel);
            ResponseEntity<String> response = restTemplate.exchange(
                ollamaApiUrl,
                HttpMethod.POST,
                entity,
                String.class
            );
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("response").asText();
            
        } catch (Exception e) {
            logger.error("Error calling Ollama API", e);
            throw new RuntimeException("Error calling Ollama API: " + e.getMessage(), e);
        }
    }
    
    private String escapeJson(String str) {
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}

