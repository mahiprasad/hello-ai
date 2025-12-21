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
public class OpenAIService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String openaiApiUrl;
    
    @Value("${openai.api.key:}")
    private String openaiApiKey;
    
    @Value("${openai.model:gpt-4o-mini}")
    private String openaiModel;
    
    public OpenAIService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        logger.debug("OpenAIService initialized");
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    
    public String getResponse(String prompt) {
        logger.info("OpenAIService.getResponse() called");
        logger.debug("Prompt: {}", prompt);
        logger.debug("OpenAI API URL: {}", openaiApiUrl);
        logger.debug("OpenAI Model: {}", openaiModel);
        logger.debug("API Key present: {}", openaiApiKey != null && !openaiApiKey.isEmpty());
        
        try {
            String requestBody = String.format(
                "{\"model\":\"%s\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}]}",
                openaiModel, escapeJson(prompt)
            );
            logger.debug("Request body: {}", requestBody);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openaiApiKey);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            
            logger.info("Calling OpenAI API at: {}", openaiApiUrl);
            ResponseEntity<String> response = restTemplate.exchange(
                openaiApiUrl,
                HttpMethod.POST,
                entity,
                String.class
            );
            
            logger.debug("OpenAI API response status: {}", response.getStatusCode());
            logger.debug("OpenAI API response body (first 200 chars): {}", 
                response.getBody() != null ? response.getBody().substring(0, Math.min(200, response.getBody().length())) : "null");
            
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String extractedResponse = jsonNode.get("choices").get(0).get("message").get("content").asText();
            logger.debug("Extracted response (first 100 chars): {}", 
                extractedResponse != null ? extractedResponse.substring(0, Math.min(100, extractedResponse.length())) : "null");
            
            return extractedResponse;
            
        } catch (Exception e) {
            logger.error("Error calling OpenAI API", e);
            throw new RuntimeException("Error calling OpenAI API: " + e.getMessage(), e);
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

