# Prompt Logs: Documenting AI Assistance in SQL Design

## Learning Objectives
- Explain the purpose and benefits of maintaining a prompt log in developer workflows.
- Implement a standardized structure for documenting AI prompts, responses, and parameters.
- Evaluate AI outputs systematically based on accuracy, formatting compliance, and hallucination metrics.
- Utilize prompt logging to track architectural decisions during relational database modeling.

---

## Why This Matters
As you integrate generative AI into your professional workflow (e.g., using LLMs to suggest SQL schemas or optimize indexes), your interactions with the model represent architectural design decisions. In team settings, if you write a query or set up constraints based on an AI's suggestion, other developers need to know:
-   What context was provided to the AI?
-   What constraints did you establish?
-   How did you verify that the AI's output was secure and correct?

Without documentation, AI-assisted development becomes a black box, making it difficult to debug errors or reproduce solutions. A **Prompt Log** acts as a developer's audit trail, tracking how AI was used, how prompts evolved, and how completions were validated.

---

## The Concept

### 1. What is a Prompt Log?
A **Prompt Log** is a developer artifact that records specific interactions with an AI assistant. Rather than pasting raw chat histories, a prompt log records the specific metadata, prompt inputs, code outputs, and validation ratings for a target task.

### 2. Core Components of a Prompt Log Entry
To be useful for engineering teams, a prompt log should capture:
-   **Task Description:** What problem were you trying to solve?
-   **System Context & Role:** What persona or guidelines were assigned to the AI?
-   **The Prompt:** The exact text sent to the model.
-   **AI Output:** The code block or response returned.
-   **Evaluation Metrics:**
    -   *Accuracy:* Does the code execute correctly without syntax errors?
    -   *Security:* Does it introduce vulnerabilities (like SQL injection)?
    -   *Hallucinations:* Did the model make up non-existent libraries or SQL features?
-   **Human Modifications:** What changes did you have to make to the AI code before committing it?

---

## Code Example: Prompt Log Entry Template

Below is a completed example of a Prompt Log entry documenting a Wednesday database design task (creating a junction table with foreign keys).

---

### Prompt Log Entry: Junction Table Generation
-   **Date:** 2026-07-12
-   **Task:** Create a junction table `project_assignments` linking `employees` and `projects` tables.
-   **Model Used:** Gemini 3.5 Flash
-   **Persona Configured:** Senior Database Engineer (No Emojis)

#### The Prompt
```
You are a Senior Database Engineer. Create the DDL for a junction table named 'project_assignments' that resolves a many-to-many relationship between 'employees' (primary key: emp_id INT) and 'projects' (primary key: project_id INT).
Follow these constraints:
1. Enforce referential integrity using foreign keys.
2. Establish a composite primary key.
3. Ensure no conversational text is included. Return ONLY the raw SQL code.
4. Do not use any emojis in your response.
```

---

#### The AI Output (Completions)
```sql
CREATE TABLE project_assignments (
    emp_id INT,
    project_id INT,
    assigned_date DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (emp_id, project_id),
    FOREIGN KEY (emp_id) REFERENCES employees(emp_id),
    FOREIGN KEY (project_id) REFERENCES projects(project_id)
);
```

---

#### Evaluation & Compliance Verification
-   **Syntax Check (Pass/Fail):** **Pass**. The DDL runs without compile errors in PostgreSQL.
-   **Constraint Verification:** **Pass**. The composite primary key is declared correctly at the table level. Foreign keys link correctly to parent tables.
-   **Formatting Compliance:** **Pass**. The model adhered to the constraint of returning only raw SQL without conversational wrappers.
-   **Hallucination Rating:** **0/5** (No hallucinations detected. Used standard ANSI/PostgreSQL DDL syntax).
-   **Human Adjustments Made:** None. The output was 100% compliant and ready for schema migration.

---

## Summary
-   A **Prompt Log** documents specific AI interactions to maintain transparency and reproducibility in engineering workflows.
-   Logs record **metadata, prompts, completions, evaluations, and human modifications**.
-   **Evaluation metrics** assess syntax accuracy, safety compliance, and model hallucinations.
-   Maintaining logs helps teams audit AI-assisted design choices and track prompt evolutions.

---

## Additional Resources
-   [OWASP Prompt Engineering Guidelines](https://owasp.org/www-project-top-10-for-large-language-model-applications/)
-   [Software Engineering Audit Trails - Best Practices](https://en.wikipedia.org/wiki/Audit_trail)
-   [Markdown Standards for Developer Documentation](https://www.markdownguide.org/)
