# Project-Level AI Rulesets & Team Conventions

## Learning Objectives
- Differentiate between personal AI configuration settings and project-level AI rulesets.
- Structure and document a team ruleset for AI coding assistants.
- Track AI-assisted architectural decisions using standard documentation templates.
- Enforce code safety, style conventions, and dependency controls through rules files.

---

## Why This Matters
As AI coding assistants like Copilot, Gemini, and Cursor become integrated into development workflows, teams face new challenges. If every developer prompts the AI differently, the codebase quickly devolves into a mix of conflicting styles, redundant dependencies, and security vulnerabilities. Elevating AI configurations from individual preferences to **Project-Level Rulesets** ensures consistency. It enables teams to write rules that guide the AI to generate code matching the exact patterns, frameworks, and security boundaries defined for the project.

---

## The Concept

### Personal vs. Project-Level AI Configuration

-   **Personal Settings**: Custom instructions written in a developer's local IDE settings (e.g., "Use tab spacing of 4, prefer concise explanations"). These settings only affect that individual developer's environment.
-   **Project-Level Rulesets**: Shared rules files checked directly into the root of the project repository (such as `.cursorrules`, `.github/copilot-instructions.md`, or custom `.agent` configurations). Because they live in the repository, they apply to **every team member** and ensure the AI produces uniform code across the entire team.

---

### Key Components of a Project-Level Ruleset

A robust project ruleset should define constraints across four areas:

1.  **Tech Stack Constraints**: Restrict the AI to specific library versions and frameworks.
    *Example:* "Only use Spring Boot 3.x and Java 17 features. Do not use raw JDBC; use Spring Data JPA."
2.  **Coding Styles & Conventions**: Set naming formats and code structures.
    *Example:* "Always use constructor injection for required fields; declare dependency fields as `private final`."
3.  **Security Guidelines**: Prevent the AI from generating common vulnerabilities.
    *Example:* "Never generate code that prints passwords or keys in logs. Intercept database exceptions in `@Repository` classes."
4.  **Testing Requirements**: Enforce how code must be verified.
    *Example:* "For every new controller endpoint, generate a corresponding MockMvc integration test."

---

### Documenting AI-Assisted Design Decisions (ADRs)
When using AI to design code, it is easy to accept generated code without understanding *why* the AI chose a specific approach. Teams should document significant decisions using **Architectural Decision Records (ADRs)**.

An ADR should contain:
-   **Context**: What problem were we trying to solve?
-   **AI Suggestion**: What pattern did the AI suggest?
-   **Human Evaluation**: Why did the team accept or modify the suggestion?
-   **Consequences**: What are the trade-offs of this design choice?

---

## Example: Project-Level AI Ruleset File (`.cursorrules` or `.agents/AGENTS.md`)

Below is a template for a shared project configuration file that guides AI coding assistants when modifying a Spring Boot repository.

```markdown
# Project AI Coding Ruleset - Spring Boot API

## Context
You are an expert Java Backend Engineer working on our enterprise Banking API. 
All generated code must adhere to these strict constraints.

## Tech Stack Constraints
- Java Version: JDK 17 (Use records, text blocks, and switch patterns where appropriate).
- Framework: Spring Boot 3.2.x.
- Persistence: Spring Data JPA (PostgreSQL).

## Design & Coding Conventions
1. **Dependency Injection**: 
   - ALWAYS use constructor-based injection.
   - Do NOT use field-level `@Autowired` injection.
   - Declare dependency fields as `private final`.
2. **Stereotypes**:
   - Align classes to layers using: `@RestController`, `@Service`, and `@Repository`.
   - Utility classes must be annotated with `@Component`.
3. **Data Mapping**:
   - Use Lombok annotations (`@Getter`, `@Setter`, `@NoArgsConstructor`) to reduce boilerplate on data carriers.
   - Do NOT use `@Data` on JPA Entities (due to performance problems with `hashCode` and `toString` on lazy associations).

## Security Boundaries (OWASP)
- Never hardcode keys or secrets. Use `@Value("${config.key}")` lookups.
- Enforce validation rules on all incoming controller request payloads using `@Valid` and JSR-380 annotations.
- Log message fields must be scrubbed; do not print raw PII or session tokens.
```

---

## Summary
-   **Project-level rulesets** are configuration files checked into source control that define rules for AI code generation across a team.
-   Rulesets ensure **consistent styling**, enforce **security conventions**, and prevent AI assistants from importing unauthorized libraries.
-   Teams should document AI-assisted architecture design choices using **Architectural Decision Records (ADRs)** to preserve technical context.

---

## Additional Resources
-   [Martin Fowler: Architectural Decision Records](https://martinfowler.com/articles/adr.html)
-   [Cursor Guide: Custom Rules (.cursorrules)](https://docs.cursor.com/context/rules-for-ai)
-   [GitHub Copilot: Configuring Instructions for your Team](https://docs.github.com/en/copilot/configuring-github-copilot/configuring-custom-instructions-for-github-copilot)
