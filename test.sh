#!/bin/bash

echo "=== Testing Hello AI Spring Boot Application ==="
echo ""

# Check if application is running
if ! curl -s http://localhost:8081/api/chat/health > /dev/null 2>&1; then
    echo "❌ Application is not running!"
    echo ""
    echo "Start it with:"
    echo "  ./start.sh"
    echo "  OR"
    echo "  mvn spring-boot:run"
    exit 1
fi

echo "✅ Application is running!"
echo ""

# Test 1: Health check
echo "1. Health Check:"
curl -s http://localhost:8081/api/chat/health
echo ""
echo ""

# Test 2: Ollama chat
echo "2. Testing Ollama with your prompt:"
echo "   Prompt: 'Explain why the sky is blue. Do not show your reasoning. Give only the final answer.'"
echo ""
curl -X POST http://localhost:8081/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Explain why the sky is blue. Do not show your reasoning. Give only the final answer.",
    "model": "ollama"
  }' | jq '.' 2>/dev/null || curl -X POST http://localhost:8081/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Explain why the sky is blue. Do not show your reasoning. Give only the final answer.",
    "model": "ollama"
  }'
echo ""
echo ""

# Test 3: Simple prompt
echo "3. Testing with simple prompt:"
curl -X POST http://localhost:8081/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "What is the capital of France?",
    "model": "ollama"
  }' | jq '.' 2>/dev/null || curl -X POST http://localhost:8081/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "What is the capital of France?",
    "model": "ollama"
  }'
echo ""
echo ""

echo "=== Testing Complete ==="

