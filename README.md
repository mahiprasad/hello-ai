# Hello AI - Spring Boot Application

A Spring Boot application that provides REST API and shell scripts for interacting with both Ollama and OpenAI AI models.

## Features

- REST API for AI chat requests
- GraphQL API for AI chat requests
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

---

### Method 3: Using GraphQL API

#### GraphQL Endpoint

The GraphQL endpoint is available at: `http://localhost:8081/graphql`

#### Test with curl

**1. Health Check Query:**
```bash
curl --location 'http://localhost:8081/graphql' \
--header 'Content-Type: application/json' \
--data '{"query":"{ health }","variables":{}}'
```

**2. Chat Query (Ollama):**
```bash
curl --location 'http://localhost:8081/graphql' \
--header 'Content-Type: application/json' \
--data '{"query":"query {\n  chat(prompt: \"Hello!\", model: \"ollama\") {\n    response\n    model\n  }\n}","variables":{}}'
```

**3. Chat Mutation:**
```bash
curl --location 'http://localhost:8081/graphql' \
--header 'Content-Type: application/json' \
--data '{"query":"mutation {\n    chat(prompt: \"Explain GraphQL\", model: \"ollama\") {\n       response\n       model\n    }\n}","variables":{}}'
```

**4. Chat Query (OpenAI):**
```bash
curl --location 'http://localhost:8081/graphql' \
--header 'Content-Type: application/json' \
--data '{"query":"query {\n  chat(prompt: \"What is Java?\", model: \"openai\") {\n    response\n    model\n  }\n}","variables":{}}'
```

**5. Query with Variables:**
```bash
curl --location 'http://localhost:8081/graphql' \
--header 'Content-Type: application/json' \
--data '{"query":"query ChatQuery($prompt: String!, $model: String) {\n    chat(prompt: $prompt, model: $model) {\n        response\n        model\n    }\n}","variables":{"prompt":"Hello, how are you?","model":"ollama"}}'
```

**6. Mutation with Variables:**
```bash
curl --location 'http://localhost:8081/graphql' \
--header 'Content-Type: application/json' \
--data '{"query":"mutation ChatQuery($prompt: String!, $model: String) {\n    chat(prompt: $prompt, model: $model) {\n        response\n        model\n    }\n}","variables":{"prompt":"Hello, how are you?","model":"ollama"}}'
```

#### Parse Response (Extract Only the Text)

**Using jq (extract response text only):**
```bash
curl -X POST http://localhost:8081/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query { chat(prompt: \"Hello!\", model: \"ollama\") { response model } }"}' \
  | jq -r '.data.chat.response'
```

**Response Structure:**
```json
{
  "data": {
    "chat": {
      "response": "The AI-generated response text here...",
      "model": "Ollama"
    }
  }
}
```

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

# GraphQL Configuration
spring.graphql.path=/graphql
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
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

## GitHub Actions / CI/CD

This project includes GitHub Actions workflows for automated builds and releases.

### Continuous Integration (CI)

The CI workflow runs on every push and pull request:
- Builds the project with Maven
- Runs tests
- Creates build artifacts

### Releases

To create a release:

**Option 1: Using Git Tags (Recommended)**
```bash
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

**Option 2: Using GitHub Actions UI**
1. Go to Actions tab in GitHub
2. Select "Build and Release" workflow
3. Click "Run workflow"
4. Enter version (e.g., `v1.0.0`)
5. Click "Run workflow"

The workflow will:
- Build the project
- Run tests
- Create a JAR file
- Create a GitHub release with artifacts

### Release Artifacts

Each release includes:
- `hello-ai.jar` - Executable JAR file
- `README.md` - Documentation
- `pom.xml` - Maven configuration
- `hello-ai.tar.gz` - Complete package

---

## License

This project is open source and available for personal and commercial use.

