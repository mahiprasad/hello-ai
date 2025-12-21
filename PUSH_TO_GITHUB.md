# Push to GitHub Guide

## Step 1: Create a New Repository on GitHub

1. Go to https://github.com/new
2. Create a new repository (e.g., `hello-ai` or `genai-spring-boot`)
3. **DO NOT** initialize with README, .gitignore, or license (we already have these)
4. Copy the repository URL (e.g., `https://github.com/yourusername/hello-ai.git`)

## Step 2: Add All Files and Commit

```bash
# Add all files
git add .

# Create initial commit
git commit -m "Initial commit: Spring Boot AI chat application with Ollama and OpenAI support"
```

## Step 3: Add Remote and Push

```bash
# Add your new GitHub repository as remote (replace with your URL)
git remote add origin https://github.com/yourusername/your-repo-name.git

# Push to GitHub
git branch -M main
git push -u origin main
```

## Quick One-Liner (after creating repo on GitHub)

Replace `YOUR_USERNAME` and `YOUR_REPO_NAME`:

```bash
git add . && \
git commit -m "Initial commit: Spring Boot AI chat application" && \
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git && \
git branch -M main && \
git push -u origin main
```

## Important Notes

- ✅ `.env` file is already in `.gitignore` (your API keys won't be pushed)
- ✅ `target/` directory is ignored (build artifacts)
- ✅ `.vscode/` is ignored (but `.vscode/launch.json` might be useful - you can add it if needed)

## If You Want to Include .vscode/launch.json

If you want to share the debug configuration:

```bash
# Remove .vscode from .gitignore temporarily
# Or add this to .gitignore:
# .vscode/*
# !.vscode/launch.json
```

## Troubleshooting

### If remote already exists:
```bash
git remote remove origin
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
```

### If you need to force push (be careful!):
```bash
git push -u origin main --force
```

