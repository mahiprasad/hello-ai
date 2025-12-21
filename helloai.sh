#!/bin/bash
echo "Hello, AI!"

# Ask user for prompt
read -p "Enter your prompt: " PROMPT

# Ask user to choose a model
echo "Choose a model:"
echo "1) Ollama (gpt-oss:120b-cloud)"
echo "2) OpenAI (gpt-4o-mini)"
read -p "Enter your choice (1 or 2): " choice

case $choice in
  1)
    sh ollama.sh "$PROMPT"
    ;;
  2)
    sh openai.sh "$PROMPT"
    ;;
  *)
    echo "Invalid choice. Please run the script again and select 1 or 2."
    exit 1
    ;;
esac

