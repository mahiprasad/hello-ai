#!/bin/bash
echo "Hello, I'm Ollama!"

PROMPT="${1:-What is the capital of France?}"

RESPONSE=$(curl -s --location 'http://localhost:11434/api/generate' \
  --header 'Content-Type: application/json' \
  --data '{
    "model": "gpt-oss:120b-cloud",
    "prompt": "'"$PROMPT"'",
    "stream": false
  }')

# Extract and print only the response field
# Try Python first as it handles control characters better
if command -v python3 &> /dev/null; then
  echo "$RESPONSE" | python3 -c "import sys, json; data = json.load(sys.stdin); print(data.get('response', ''))" 2>/dev/null
elif command -v jq &> /dev/null; then
  # jq with raw output, suppress errors
  echo "$RESPONSE" | jq -r '.response' 2>/dev/null || echo "$RESPONSE" | grep -oP '"response"\s*:\s*"([^"\\]|\\.)*"' | sed 's/"response"\s*:\s*"\(.*\)"/\1/' | sed 's/\\n/\n/g' | sed 's/\\"/"/g'
else
  # Basic grep/sed fallback
  echo "$RESPONSE" | grep -oP '"response"\s*:\s*"([^"\\]|\\.)*"' | sed 's/"response"\s*:\s*"\(.*\)"/\1/' | sed 's/\\n/\n/g' | sed 's/\\"/"/g'
fi

