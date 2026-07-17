# AI Governance: Frameworks, Model Risk Management, and Regulatory Compliance

## Learning Objectives
- Define the core principles of an enterprise AI Governance framework.
- Explain Model Risk Management (MRM) and the lifecycle of AI risk mitigation.
- Design AI Audit Trails and accountability structures for algorithmic transparency.
- Describe the global regulatory landscape, focusing on the EU AI Act classification system.
- Apply Responsible AI principles (Fairness, Accountability, Transparency, Privacy) to development.

---

## Why This Matters
As organizations integrate AI models (such as AWS Bedrock APIs) into enterprise applications, they face new legal, ethical, and operational risks:
- AI models can make incorrect decisions (hallucinations) that harm users.
- Machine learning models can amplify biases present in their training data.
- Storing prompt histories containing customer details can violate privacy laws.

**AI Governance** provides the rules, policies, and audit trails required to manage these risks. It ensures your AI tools are safe, ethical, compliant with global regulations (like the EU AI Act), and auditable.

---

## The Concept

### 1. What is AI Governance?
**AI Governance** is a framework of rules, policies, and accountability structures designed to guide the development, deployment, and monitoring of AI systems. It ensures algorithms are transparent, fair, and secure.

---

### 2. Model Risk Management (MRM)
MRM is a structured lifecycle designed to identify, assess, and mitigate risks associated with using predictive or generative models:

```
                  MODEL RISK MANAGEMENT LIFECYCLE
 +-----------------------------------------------------------+
 | 1. RISK CLASSIFICATION                                    |
 |  - Categorize models by impact (Low, Medium, High).        |
 +-----------------------------t-----------------------------+
                               |
                               v
 +-----------------------------------------------------------+
 | 2. INDEPENDENT VALIDATION                                 |
 |  - Verify model outputs, check for drift and bias.        |
 +-----------------------------t-----------------------------+
                               |
                               v
 +-----------------------------------------------------------+
 | 3. ACTIVE MONITORING                                      |
 |  - Collect logs, review user feedback, audit parameters.  |
 +-----------------------------------------------------------+
```

---

### 3. Regulatory Landscape: The EU AI Act
The European Union AI Act is the world's first comprehensive horizontal legal framework for AI. It classifies AI systems based on risk:

#### Unacceptable Risk (Banned)
Systems that threaten safety or user rights (e.g., social scoring, cognitive behavioral manipulation, or real-time biometric tracking in public spaces).

#### High Risk (Strict Compliance)
Systems used in critical infrastructure, medical devices, educational testing, or employment recruitment. They require formal risk assessments, data quality reviews, logging, and human oversight.

#### Limited/Minimal Risk (Transparency Rules)
General chatbots (like Claude or Llama). They require basic transparency notices informing users they are interacting with AI.

---

### 4. Designing AI Audit Trails and Accountability
To meet regulatory requirements, you must implement auditing:
- **System Logs**: Log all API transactions, noting which user made the request, the model ID used, and the parameter settings (temperature, top-p).
- **Output Validation**: Audit and record human interventions (when a user flags an AI response as incorrect).
- **Version Control**: Track prompt templates, database configurations, and fine-tuning datasets under version control to ensure reproducibility.

---

## Code Examples and Walkthroughs

### 1. Database Schema for AI Prompt Auditing
To build an enterprise audit trail for AI requests, save prompt metadata to a secure database:

```sql
-- Schema for auditing AI API requests
CREATE TABLE ai_audit_log (
    audit_id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id VARCHAR(50) NOT NULL,
    model_id VARCHAR(100) NOT NULL,
    temperature NUMERIC(3,2) NOT NULL,
    prompt_token_count INT,
    response_token_count INT,
    -- Store redacted inputs and outputs for security auditing
    redacted_prompt_text TEXT NOT NULL,
    redacted_response_text TEXT NOT NULL,
    -- Tracks compliance check status (e.g., passed PII guardrails)
    compliance_check_passed BOOLEAN DEFAULT TRUE,
    session_id VARCHAR(100)
);

CREATE INDEX idx_ai_audit_user ON ai_audit_log(user_id);
CREATE INDEX idx_ai_audit_time ON ai_audit_log(timestamp);
```

---

### 2. Implementing Audit Trail Logging in Java
This service class logs API transaction metadata to the audit database before returning the AI response:

```java
package com.example.project3.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AiGovernanceAuditService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void logAiTransaction(String userId, String modelId, double temp, 
                                 int promptTokens, int responseTokens, 
                                 String prompt, String response) {
        
        // 1. Redact PII (e.g., phone numbers or emails) before saving to the audit database
        String cleanPrompt = redactPII(prompt);
        String cleanResponse = redactPII(response);

        String sql = "INSERT INTO ai_audit_log (timestamp, user_id, model_id, temperature, " +
                     "prompt_token_count, response_token_count, redacted_prompt_text, " +
                     "redacted_response_text) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // 2. Persist metadata for regulatory auditing
        jdbcTemplate.update(sql, 
            LocalDateTime.now(), 
            userId, 
            modelId, 
            temp, 
            promptTokens, 
            responseTokens, 
            cleanPrompt, 
            cleanResponse
        );
    }

    private String redactPII(String text) {
        // Simple regex replace for emails to demonstrate redaction
        return text.replaceAll("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", "[REDACTED_EMAIL]");
    }
}
```

---

## Summary
- **AI Governance** defines corporate policies, risk controls, and compliance procedures for AI systems.
- **Model Risk Management (MRM)** categorizes models by risk level and enforces validation checks.
- The **EU AI Act** classifies AI systems into risk categories: Unacceptable (banned), High, and Limited/Minimal.
- Maintain **AI Audit Trails** by saving prompt metadata, token counts, and sanitized inputs to database logs.

---

## Additional Resources
- [The EU AI Act Official Regulatory Framework Portal](https://artificialintelligenceact.eu/)
- [NIST AI Risk Management Framework (AI RMF)](https://www.nist.gov/itl/ai-risk-management-framework)
- [OECD Principles on Artificial Intelligence](https://oecd.ai/en/ai-principles)
