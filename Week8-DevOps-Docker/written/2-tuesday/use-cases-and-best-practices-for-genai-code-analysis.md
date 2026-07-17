# GenAI Code Analysis: Use Cases and Best Practices in CI/CD Pipelines

## Learning Objectives
- Describe how Generative AI (GenAI) can automate code reviews in CI/CD pipelines.
- Identify core GenAI code review use cases: AI-powered SAST, code refactoring, and dependency risk analysis.
- Apply best practices to run AI code reviews reliably (deterministic checks first, AI as a supplement).
- Mitigate security risks (like code exposure or compliance issues) when running automated reviews.

---

## Why This Matters
Traditional static code analysis tools (like SonarQube or Checkstyle) are **deterministic**: they use pre-defined syntax rules to scan your code. While excellent at catching simple bugs, unused variables, or formatting issues, they cannot understand the *intent* of your code. They struggle to identify complex logical errors, evaluate API design choices, or suggest structural refactoring patterns.

Generative AI (GenAI) can bridge this gap. By integrating LLMs into your CI/CD pipeline, you can automate deeper code reviews. The AI can analyze logic flows, verify documentation accuracy, and suggest optimizations. However, AI can also hallucinate or behave unpredictably. Learning how to combine deterministic checks with AI scanners is key to building a reliable review pipeline.

---

## The Concept

### 1. GenAI Use Cases in the Pipeline
By calling AI model APIs (like AWS Bedrock) during build and test phases, you can automate advanced code analysis:

#### AI-Powered SAST (Static Application Security Testing)
LLMs can identify complex security vulnerabilities (such as authorization flaws or data injection vectors) that require understanding the execution path across multiple classes.

#### Automated Refactoring Suggestions
The AI scans your pull requests and posts inline code suggestions, recommending cleaner algorithms, design patterns, or performance optimizations (e.g., advising you to use a `StringBuilder` instead of simple string concatenation in a loop).

#### Dependency Risk Analysis
When you add new third-party libraries, the AI can check them against security databases, read licensing files, and evaluate the library's health to prevent supply-chain attacks.

---

### 2. Best Practices: Designing a Reliable Analysis Pipeline
Because generative AI models are non-deterministic, they can return different responses for the same input and occasionally hallucinate facts. To prevent these behaviors from breaking your pipelines, follow these design practices:

```
            Automated Pipeline Execution Flow
+-----------------------------------------------------------+
| 1. DETERMINISTIC CHECKS FIRST                             |
|  - Run compiler, unit tests, and SonarQube static scans.   |
|  - If these checks fail, block the build instantly.        |
+-----------------------------t-----------------------------+
                              | Passes checks
                              v
+-----------------------------------------------------------+
| 2. GENAI AS A SUPPLEMENT                                  |
|  - Send clean code diffs to Bedrock API.                  |
|  - Set temperature to 0.0 to prevent hallucinations.      |
|  - Post findings as advisory comments, not build blocks. |
+-----------------------------------------------------------+
```

#### Deterministic Checks First
Always run standard compilers, unit tests, and deterministic static scanners (like SonarQube) before calling AI APIs. If the code has compile errors or broken tests, there is no need to spend API tokens on an AI review.

#### AI as a Supplement (Advisory Role)
Configure your pipeline to treat AI findings as advisory recommendations, not build blockers. Have the AI post findings as comments on the pull request for developers to review manually, preventing false positives from blocking deployment.

#### Low Temperature Settings
When configuring model request parameters (like temperature, top-p, and top-k) for code reviews, always set the `temperature` to `0.0`. This forces the model to be deterministic, minimizing the risk of hallucinations.

---

## Code Examples and Walkthroughs

### 1. Conceptual AI Code Review Hook Implementation
This Python script simulates a pipeline job that scans a Git diff for SQL injection vulnerabilities using an AI model. It follows the fail-open fallback pattern:

```python
# scripts/ai_sast_scanner.py
import sys
import os
import requests

def analyze_diff(diff_content):
    api_key = os.getenv("AWS_BEDROCK_API_KEY")
    if not api_key:
        print("API Key missing. Skipping AI scan (Fail-Open).")
        sys.exit(0) # Fail-open

    # Define a strict system prompt to enforce deterministic responses
    system_prompt = (
        "You are an expert security engineer. Review the following code changes "
        "specifically for SQL injection vulnerabilities. Output 'SAFE' or "
        "'VULNERABLE: [remediation steps]'. Do not include any other text."
    )
    
    payload = {
        "prompt": f"{system_prompt}\n\nCode Diff:\n{diff_content}",
        "temperature": 0.0, # Forces deterministic analysis
        "max_tokens": 300
    }
    
    try:
        # Mock request to AI API gateway
        response = requests.post(
            "https://api.bedrock-gateway.internal/v1/invoke",
            json=payload,
            headers={"Authorization": f"Bearer {api_key}"},
            timeout=10.0 # Strict timeout
        )
        
        if response.status_code == 200:
            analysis = response.json().get("result", "SAFE")
            if "VULNERABLE" in analysis:
                print(f"SECURITY ADVISORY:\n{analysis}")
                # Log warning but do not exit with error (Fail-Open/Advisory mode)
                sys.exit(0)
            else:
                print("AI analysis: No SQL injection indicators found.")
                sys.exit(0)
        else:
            print(f"Gateway returned error {response.status_code}. Skipping scan.")
            sys.exit(0) # Fail-open
            
    except requests.exceptions.RequestException as e:
        print(f"API Connection error: {e}. Skipping scan.")
        sys.exit(0) # Fail-open

if __name__ == "__main__":
    if len(sys.argv) > 1:
        analyze_diff(sys.argv[1])
```

---

## Summary
- **GenAI code review** tools analyze logical intent, code structures, and design choices beyond the capabilities of deterministic static checkers.
- Core use cases include **AI-powered SAST**, **automated refactoring suggestions**, and **dependency security reviews**.
- **Best Practice**: Run deterministic checks first to save token costs, and treat AI findings as advisory comments rather than build blockers.
- Always use a **temperature of 0.0** to ensure AI code evaluations remain consistent and reliable.

---

## Additional Resources
- [OWASP: Guide to Integrating AI Scanners in AppSec Pipelines](https://owasp.org/)
- [Anthropic: Guide to Code Review Prompt Engineering](https://docs.anthropic.com/claude/docs/helper-prompts)
- [Managing AI API Rate Limits and Outage Resiliency](https://docs.aws.amazon.com/bedrock/latest/userguide/quotas.html)
