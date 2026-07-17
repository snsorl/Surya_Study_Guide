# GenAI Security Best Practices for Developers

## Learning Objectives
- Define security best practices when querying generative AI coding models.
- Implement strict PII and credential exclusions in prompt parameters.
- Establish validation workflows for AI-generated security and authentication code.
- Analyze the importance of human accountability in automated software engineering.
- Implement AI-assisted security decision documenting protocols.

---

## Why This Matters
As you transition to enterprise development environments, you will likely have access to corporate AI coding assistants. While these tools increase productivity, developers are legally, operationally, and ethically responsible for the security posture of the software they deliver.

Past security breaches have occurred because developers copy-pasted real customer data or active API keys into public AI prompts, or accepted complex cryptographic algorithms written by an AI without checking them. Adhering to structured best practices ensures you use AI engines safely without compromising company secrets or introducing system bugs.

---

## Core Security Guidelines for AI Development

```
                       AI DEV GUIDELINES
       
       +-----------------------------------------------+
       | 1. Never Include Real Secrets/PII in Prompts   |
       +-----------------------------------------------+
       | 2. Manually Validate Authentication Logic     |
       +-----------------------------------------------+
       | 3. Run Static Code Scanners on AI Output      |
       +-----------------------------------------------+
       | 4. Maintain Full Human Accountability         |
       +-----------------------------------------------+
```

### 1. Data Hygiene: Zero Secret Exposure
*   **The Rule:** Never include production passwords, database connection strings, client API tokens, or actual user Personal Identifiable Information (PII) in prompts sent to AI systems.
*   **The Practice:** Use generic placeholder values (e.g., `YOUR_DATABASE_PASSWORD_PLACEHOLDER`) and dummy data formats. If you need to debug a JSON error, sanitize the email, names, and address fields before pasting.

### 2. Manual Verification of Security Logic
AI models can generate elegant-looking cryptographic implementations that are actually fundamentally broken (e.g., using predictable random number generators or insecure padding).
*   **The Rule:** Any code block managing authorization, authentication, cookie handling, token validation, or encryption **must** be verified line-by-line by a human engineer.
*   **The Practice:** Validate cryptographic key sizes, check salt configurations, and ensure tokens (like JWTs) check signatures and expiration times correctly.

### 3. Supplement, Do Not Replace, Traditional Security Scanning
AI assistants are not substitute scanners. They do not have access to the complete running build context, system boundaries, and active database connection paths.
*   **The Rule:** AI security reviews must be treated as a supplement, not a replacement, for static application security testing (SAST), software composition analysis (SCA), and dynamic application security testing (DAST) pipelines.
*   **The Practice:** Continue running your standard security checkers (e.g., Maven security plugin, SonarQube, `pip-audit`) alongside AI helpers.

### 4. Human Accountability & Decisional Tracking
"The AI wrote this" is not an acceptable defense when an application experiences a data breach. 
*   **The Rule:** Developers must take full ownership of every line of code they merge, regardless of its origin.
*   **The Practice:** Document all AI-assisted security decisions. If an AI proposes a specific mitigation or encryption method, record the rationale in pull request descriptions or developer diaries so future reviewers understand why a particular pattern was selected.

---

## Code Examples

### 1. Insecure vs. Secure Prompting Techniques

#### Insecure Prompt (Leaking Context & Data):
> *"We are building an API connection for client Infosys. Here is our database config: `postgresql://db_user:InfosysSecure2026@192.168.1.55:5432/production`. How do we optimize the following query? [Pasted query]"*

*Why this is bad:* You have leaked an internal network IP address, database name, and the active database user credential to a public engine.

---

#### Secure Prompt (Sanitized & Delimited):
> *"Write an optimized SQL query for a PostgreSQL database. Below are the table schemas using generic fields, and the original slow query.
>
> Table Schema:
> - users (id UUID, name VARCHAR, created_at TIMESTAMP)
> - orders (id UUID, user_id UUID, total NUMERIC)
>
> Query to Optimize:
> [Pasted query with mock table and column names]*"

---

## Summary
- **Data Hygiene:** Never paste credentials, connection strings, or user PII in AI prompts. Use mock variables.
- **Verification:** Security, authentication, and encryption code must be reviewed line-by-line by an engineer.
- **Tooling:** Treat AI helpers as code generators; continue using standard scanners (SCA, SAST, DAST) to guarantee code security.
- **Accountability:** Maintain ownership of the code you ship, and document major AI-assisted security decisions.

---

## Additional Resources
- [NIST: Cybersecurity Framework Profile for AI](https://www.nist.gov/artificial-intelligence)
- [OWASP: Large Language Model Security Best Practices](https://owasp.org/www-project-top-10-for-large-language-model-applications/)
- [Snyk: Secure Development Best Practices with Generative AI](https://snyk.io/blog/secure-development-best-practices-gen-ai/)
