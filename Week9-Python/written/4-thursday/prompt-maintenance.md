# Prompt Log Maintenance and Archiving

## Learning Objectives
- Formulate a strategy for long-term prompt library maintenance.
- Archive developmental and architectural AI prompts by technology stack.
- Create a structured personal prompt library for post-program engineering use.
- Document variable placeholders and system constraints to make prompt templates reusable.

---

## Why This Matters
Throughout this 9-week program, you have used Generative AI agents and assistants to help build full-stack web applications, run database queries, create Docker structures, and write Python scripts. However, prompts are like source code: if they are not maintained, documented, and organized, they lose value.

As you prepare to transition to professional roles, having a categorized, searchable library of **proven prompt templates** is a massive career accelerator. Rather than struggling to remember how to prompt an AI for a Spring Boot config or a secure Flask route, you can copy, adjust, and execute a template from your library. This guide explains how to document, organize, and catalog your prompt history.

---

## The Concept

### 1. The Structure of a Reusable Prompt Template
A high-quality prompt template should be decoupled from specific variables (such as file names or database tables). It should contain:
*   **Role Definition:** E.g., *"Act as a Senior Database Administrator..."*
*   **Context Details:** Context instructions (e.g., table definitions).
*   **Clear Directives:** Explicit lists of what the model must and must not do.
*   **Placeholders:** Variables enclosed in brackets `[like_this]` that can be replaced before execution.

### 2. Archiving Schema
Organize your prompts in a central directory or GitHub repository using a taxonomy based on the program's weeks:

```text
my-prompt-library/
│
├── 01-Java-Fundamentals/
│   └── exception_mapping_prompt.md
├── 02-RDBMS-Postgres/
│   └── normalized_schema_prompt.md
├── 05-Spring-Boot/
│   └── spring_sec_config_prompt.md
└── 09-Python-Flask/
    └── flask_rest_crud_prompt.md
```

---

## Code Examples

Here are two production-ready prompt templates ready to copy into your personal library.

### Template 1: Generating a Secure Flask API Endpoint

```markdown
# Role
Act as an expert Python Backend Engineer and Security Specialist.

# Context
I am building a lightweight Python service using Flask to act as a data processing gateway.

# Objective
Write a complete, PEP 8 compliant Flask route for the following endpoint:
`[HTTP_METHOD] [ROUTE_PATH]`

# Technical Constraints
- The handler function name must use `snake_case`.
- Use the `@app.route` decorator explicitly binding the method.
- Check that the request payload is JSON (`request.is_json`). If not, return a 400 Bad Request error.
- Use explicit try/except blocks to catch expected data parser exceptions.
- Return responses using `jsonify()` with the appropriate HTTP status code (e.g., 201 for success creation, 400 for input errors).
- Protect against common code quality defects: do not use mutable default arguments or global states.

# Input Payload Example
[INSERT_JSON_MOCK_HERE]
```

---

### Template 2: Database Table Creation and Normalization (PostgreSQL)

```markdown
# Role
Act as a Senior Database Architect.

# Context
I am designing a normalized PostgreSQL schema for a transactional business system.

# Objective
Generate the `CREATE TABLE` DDL queries for the following entity: `[ENTITY_NAME]`.

# Requirements
- Implement proper primary keys (using UUID or BIGSERIAL) and foreign keys.
- Ensure 3rd Normal Form (3NF) structural design.
- Enforce relational constraints: non-null check bounds, field length restrictions, and cascade delete rules where appropriate.
- Include indices (`CREATE INDEX`) on foreign keys or columns frequently used in WHERE conditions.
- Write output in raw, clean SQL with uppercase commands (e.g., SELECT, CREATE TABLE) and snake_case naming conventions for fields.
```

---

## Summary
- Organize your prompt library using a technology-based folder hierarchy.
- Design prompts as templates using variables like `[PLACEHOLDER]` to ensure reusability.
- Include Role, Context, Constraints, and Target Output requirements in every prompt template.
- Maintain your personal prompt library alongside your portfolio codebases as a core developer utility.

---

## Additional Resources
- [Learn Prompting: A Free, Open-Source Guide to Prompting](https://learnprompting.com/)
- [GitHub: A Collection of Awesome ChatGPT Prompts](https://github.com/f/awesome-chatgpt-prompts)
- [Microsoft Guide: Introduction to Prompt Engineering](https://learn.microsoft.com/en-us/azure/ai-services/openai/concepts/prompt-engineering)
