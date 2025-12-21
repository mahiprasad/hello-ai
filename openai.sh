#!/bin/bash
echo "Hello, I'm OpenAI!"

PROMPT="${1:-What is the capital of France?}"

# Load environment variables from .env file
if [ -f .env ]; then
  export $(cat .env | grep -v '^#' | xargs)
fi

RESPONSE=$(curl https://api.openai.com/v1/chat/completions \
  -H "Authorization: Bearer $OPENAI_API_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "model": "gpt-4o-mini",
    "messages": [{"role": "user", "content": "'"$PROMPT"'"}]
  }')

# Extract and print only the response content
if command -v jq &> /dev/null; then
  echo "$RESPONSE" | jq -r '.choices[0].message.content'
else
  echo "$RESPONSE" | grep -o '"content":"[^"]*"' | sed 's/"content":"\(.*\)"/\1/' | head -1
fi

