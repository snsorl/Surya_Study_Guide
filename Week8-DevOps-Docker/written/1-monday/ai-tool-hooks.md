# AI Tool Hooks: Integrating AI into the Software Development Lifecycle (SDLC)

## Learning Objectives
- Explain the role and utility of Git Hooks and automated pipeline hooks.
- Configure local pre-commit hooks that trigger AI-driven code reviews.
- Integrate automated AI security and compliance checks into CI/CD pipelines.
- Design resilient workflows with fallback behaviors to handle AI service outages.

---

## Why This Matters
Code reviews, static analysis, and security scanning are critical parts of the development process. However, manual code reviews can slow down development, and standard static analysis tools often miss logical errors, code smell patterns, or security issues.

By integrating AI tools directly into the development lifecycle via hooks, you can automate these checks. Developers receive instant feedback during local git commits or inside automated CI pipelines. This helps catch issues early—before code is merged or deployed.

---

## The Concept

### 1. What are Git Hooks and CI Hooks?
- **Git Hooks**: Scripts that run automatically in response to specific lifecycle events in a Git repository (e.g., `pre-commit`, `prepare-commit-msg`, `post-merge`). They run locally on the developer's machine.
- **CI/CD Pipeline Hooks**: Automated steps executed by continuous integration runners (e.g., GitLab Runner, GitHub Actions) when code is pushed to a remote repository.

---

### 2. Designing AI Integration Hooks
Integrating AI engines (via REST APIs) into these lifecycle steps allows you to automate code reviews:
- **Local Pre-commit AI Hook**: When a developer runs `git commit`, a local script intercepts the command, extracts the code diff, sends it to an AI engine, and reviews it for security flaws or style violations. If the AI flags severe issues, it blocks the commit.
- **CI Pipeline AI Scanner**: Run on code changes during remote builds. An AI agent reviews the code for logic errors, updates documentation, or checks for accidental PII leaks.

```
                  Local git commit command
                             |
                             v
                 +-----------------------+
                 |    Git Pre-Commit     |
                 |      Hook Script      |
                 +-----------t-----------+
                             |
                             | Sends Code Diff
                             v
                 +-----------------------+
                 |    AI API Engine      |
                 |  - Security Review    |
                 |  - Logic Check        |
                 +-----------t-----------+
                             |
                 Pass/Fail   | (JSON Response)
        +--------------------+--------------------+
        |                                         |
        v (Fail)                                  v (Pass)
+-----------------------+                 +-----------------------+
|  Block Commit & Show  |                 |    Commit Allowed     |
|  AI Suggestions       |                 |    to Complete        |
+-----------------------+                 +-----------------------+
```

---

### 3. Implementing Fallback and Resiliency Behaviors
AI engines are external services accessed over networks. They can fail due to rate limits, API key expiration, or service outages. **Your development pipeline must be resilient to these failures.**

When designing AI hooks, apply the **Fail-Open** vs. **Fail-Closed** design principle:
- **Fail-Open (Recommended for Hooks)**: If the AI API returns a server timeout, rate-limiting error, or connection failure, the script logs a warning but allows the commit or build to proceed. This ensures external API issues do not block developers from writing code.
- **Fail-Closed**: If the AI tool fails, the entire pipeline is blocked. This model is only used for critical security validation steps that cannot be bypassed.

---

## Code Examples and Walkthroughs

### 1. Local Pre-commit Hook Script (Bash & Python Integration)
Below is an example of a `.git/hooks/pre-commit` script. It extracts the current Git diff, calls a Python script to scan the code using an AI model, and handles fallback behavior if the API is unavailable:

```bash
#!/bin/bash
# .git/hooks/pre-commit
# Activates during git commit to run AI code analysis

echo "Executing pre-commit AI code analysis hook..."

# 1. Capture the staged code changes (the git diff)
STAGED_DIFF=$(git diff --cached)

# 2. Skip review if no code is staged
if [ -z "$STAGED_DIFF" ]; then
    echo "No staged code changes found. Skipping AI review."
    exit 0
fi

# 3. Call the Python sanitization/scanning script
# Pass the diff data to the script
python3 scripts/ai_commit_scanner.py "$STAGED_DIFF"
RESULT=$?

# 4. Handle exit states and fallback rules
if [ $RESULT -eq 0 ]; then
    echo "AI Review passed successfully. Commit allowed."
    exit 0
elif [ $RESULT -eq 2 ]; then
    echo "WARNING: AI API connection failed or timed out. Failing open to prevent blocking development."
    exit 0
else
    echo "ERROR: AI code scanner flagged security or syntax violations. Commit blocked."
    exit 1
fi
```

Here is the supporting Python script (`scripts/ai_commit_scanner.py`) that handles the API call and implements the fallback logic:

```python
# scripts/ai_commit_scanner.py
import sys
import os
import requests

def main():
    if len(sys.argv) < 2:
        print("No diff content provided.")
        sys.exit(0)

    git_diff = sys.argv[1]
    
    # 1. Check for API configuration
    api_key = os.getenv("AI_API_KEY")
    if not api_key:
        # Fallback open if no API key is set
        print("AI_API_KEY environment variable is missing.")
        sys.exit(2)

    # 2. Build the API request
    url = "https://api.provider.com/v1/analyze"
    headers = {
        "Authorization": f"Bearer {api_key}",
        "Content-Type": "application/json"
    }
    
    prompt = f"Analyze the following code changes for security flaws and logical errors. Return 'PASS' or a list of issues:\n\n{git_diff}"
    payload = {"prompt": prompt, "temperature": 0.0}

    # 3. Execute the API request with a strict connection timeout
    try:
        response = requests.post(url, json=payload, headers=headers, timeout=5.0)
        
        if response.status_code == 200:
            result = response.json().get("result", "PASS")
            if "SECURITY_VIOLATION" in result:
                print(f"Commit rejected. AI Analysis:\n{result}")
                sys.exit(1) # Block commit
            else:
                sys.exit(0) # Pass commit
        else:
            # Fallback open if the API returns an error status code
            print(f"API returned error status: {response.status_code}")
            sys.exit(2)
            
    except requests.exceptions.RequestException as e:
        # Fallback open on connection failures or timeouts
        print(f"Connection error: {e}")
        sys.exit(2)

if __name__ == "__main__":
    main()
```

---

## Summary
- **Git Hooks** run scripts locally in response to Git events, while **CI Hooks** run automated checks on build servers.
- **AI Tool Hooks** integrate code reviews and security scans into the developer workflow.
- **Fallback Logic** is critical when calling third-party APIs; using a **Fail-Open** model prevents API downtime from blocking developers.

---

## Additional Resources
- [Git Book: Customizing Git - Git Hooks Guide](https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks)
- [Python Requests Library: Timeouts and Exception Handling](https://requests.readthedocs.io/en/latest/user/quickstart/#timeouts)
- [OWASP Guide to Automated Security Scanning in CI/CD](https://owasp.org/www-project-devsecops-guideline/)
