# Hello AI - Spring Boot Application

A Spring Boot application that provides REST API and shell scripts for interacting with both Ollama and OpenAI AI models.

## Features

- REST API for AI chat requests
- Shell scripts for command-line usage
- Support for both Ollama and OpenAI models
- Environment variable configuration for API keys
- Clean JSON response extraction

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Ollama (for local AI model)
- OpenAI API key (optional, for OpenAI model)

---

## Setup

### 1. Install and Setup Ollama

Follow the official Ollama documentation for installation and setup:
- **Official Documentation:** [https://ollama.com/docs](https://ollama.com/docs)

After installation:
- Start Ollama service: `ollama serve`
- Pull the required model: `ollama pull gpt-oss:120b-cloud`
- Ollama will run on `http://localhost:11434` by default

Update the model name in `application.properties` if using a different model.

### 4. Set OpenAI API Key (Optional)

If you want to use OpenAI, create a `.env` file:
```bash
echo 'OPENAI_API_KEY="your-api-key-here"' > .env
```

---

## Running the Project

### Method 1: Using Shell Scripts (Terminal)

#### Interactive Script (Recommended)
```bash
./helloai.sh
```

This will:
1. Ask you to enter a prompt
2. Let you choose between Ollama (1) or OpenAI (2)
3. Display the AI response

#### Direct Scripts

**Using Ollama:**
```bash
./ollama.sh "Your prompt here"
```

**Using OpenAI:**
```bash
./openai.sh "Your prompt here"
```

---

### Method 2: Using Spring Boot REST API

#### Start the Application

**Normal Mode:**
```bash
mvn spring-boot:run
```

**Debug Mode:**
```bash
./debug.sh
```

The application will start on **http://localhost:8081**

#### Test with curl

**Chat with Ollama:**
```bash
curl -X POST http://localhost:8081/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "What is the capital of France?",
    "model": "ollama"
  }'
```

**Chat with OpenAI:**
```bash
curl -X POST http://localhost:8081/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "What is the capital of France?",
    "model": "openai"
  }'
```

#### Test with Postman

1. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```
   Or for debugging:
   ```bash
   ./debug.sh
   ```

2. **Health Check:**
   - Method: `GET`
   - URL: `http://localhost:8081/api/chat/health`
   - Expected Response: `"Hello, AI! Service is running."`

## Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Server port
server.port=8081

# OpenAI Configuration
openai.api.key=${OPENAI_API_KEY:}
openai.api.url=https://api.openai.com/v1/chat/completions
openai.model=gpt-4o-mini

# Ollama Configuration
ollama.api.url=http://localhost:11434/api/generate
ollama.model=gpt-oss:120b-cloud
```

---

## Debugging

### Using VS Code

1. Press `F5` or go to Run and Debug panel
2. Select "Debug Spring Boot App"
3. Set breakpoints in your code
4. Make requests to test

### Using debug.sh

1. Run: `./debug.sh`
2. Attach debugger to port 5005
3. Set breakpoints and debug

---

## Troubleshooting

### Ollama Connection Error
- Make sure Ollama is running: `ollama serve`
- Check if Ollama is on port 11434: `curl http://localhost:11434/api/tags`
- Verify model is pulled: `ollama list`

### Port 8081 Already in Use
- Change port in `application.properties`: `server.port=8082`
- Or stop the process using port 8081

### OpenAI API Error
- Check your API key is set: `echo $OPENAI_API_KEY`
- Verify the key is valid and has credits

### JSON Parsing Error in Shell Scripts
- Install `jq`: `brew install jq` (macOS) or `apt-get install jq` (Linux)
- Or use Python3 (already included in the script)

---

## Quick Start Summary

1. **Install Ollama:** `brew install ollama` (macOS)
2. **Start Ollama:** `ollama serve`
3. **Pull model:** `ollama pull gpt-oss:120b-cloud`
4. **Set OpenAI key (optional):** `export OPENAI_API_KEY="your-key"`
5. **Run:**
   - Shell script: `./helloai.sh`
   - Or Spring Boot: `mvn spring-boot:run`
   - Then test: `curl http://localhost:8081/api/chat/health`

---

## License

This project is open source and available for personal and commercial use.

