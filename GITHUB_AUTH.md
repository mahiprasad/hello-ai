# GitHub Authentication for Different Account

## Option 1: HTTPS (Recommended - Easiest)

**No SSH setup needed!** Just use HTTPS URL:

```bash
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
git push -u origin main
```

When you push, GitHub will prompt for:
- **Username**: Your GitHub username
- **Password**: Use a **Personal Access Token** (not your password)

### How to Create Personal Access Token:

1. Go to GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Give it a name (e.g., "GenAI Project")
4. Select scopes: Check `repo` (full control of private repositories)
5. Click "Generate token"
6. **Copy the token immediately** (you won't see it again!)
7. Use this token as your password when pushing

**Advantages:**
- ✅ No SSH key setup needed
- ✅ Works immediately
- ✅ Easy to use

---

## Option 2: SSH (If you prefer SSH)

### If you want to use the same SSH key:

You can use your existing SSH key for multiple GitHub accounts if you have access to both accounts.

```bash
# Add the key to the new GitHub account:
# 1. Copy your public key:
cat ~/.ssh/id_rsa.pub
# or
cat ~/.ssh/id_ed25519.pub

# 2. Go to GitHub → Settings → SSH and GPG keys → New SSH key
# 3. Paste the key and save

# Then use SSH URL:
git remote add origin git@github.com:YOUR_USERNAME/YOUR_REPO_NAME.git
```

### If you want separate SSH keys for different accounts:

1. **Generate a new SSH key:**
   ```bash
   ssh-keygen -t ed25519 -C "your-email@example.com" -f ~/.ssh/id_ed25519_github2
   ```

2. **Add to SSH config** (`~/.ssh/config`):
   ```
   # First GitHub account
   Host github.com
     HostName github.com
     User git
     IdentityFile ~/.ssh/id_ed25519

   # Second GitHub account
   Host github-second
     HostName github.com
     User git
     IdentityFile ~/.ssh/id_ed25519_github2
   ```

3. **Add key to new GitHub account:**
   ```bash
   cat ~/.ssh/id_ed25519_github2.pub
   # Copy and add to GitHub → Settings → SSH and GPG keys
   ```

4. **Use the custom host:**
   ```bash
   git remote add origin git@github-second:YOUR_USERNAME/YOUR_REPO_NAME.git
   ```

---

## Recommendation

**Use HTTPS (Option 1)** - It's the simplest and works immediately without any setup. You just need to create a Personal Access Token once.

---

## Quick Start (HTTPS)

```bash
# 1. Create repo on GitHub (don't initialize it)

# 2. Add and commit files
git add .
git commit -m "Initial commit"

# 3. Add remote (HTTPS - no SSH needed)
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git

# 4. Push (will prompt for username and token)
git push -u origin main
```

When prompted:
- **Username**: Your GitHub username
- **Password**: Your Personal Access Token (not your GitHub password)

