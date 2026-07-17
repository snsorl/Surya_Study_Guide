# AI: Prompt Maintenance

## Learning Objectives
- Review and evaluate the efficiency of prompts collected throughout the week.
- Archive successful prompts into a reusable prompt library for future projects.
- Flag and analyze low-quality or hallucinated AI responses.
- Implement incremental prompting techniques to maintain context.

---

## Why This Matters
As you build more software, prompt engineering becomes a critical developer utility. If you write a complex prompt that successfully generates a database configuration, a CORS filter, or an validation pattern, you should not delete it when your chat window closes. If you discard these scripts, you will have to rewrite them from scratch on your next project. Actively organizing, maintaining, and archiving your successful prompt configurations ensures you build a powerful toolset that increases your coding speed and accuracy over time.

---

## The Concept

### Maintaining a Prompt Library
A **Prompt Library** is a catalog of documented, tested prompts. For full-stack Java/Web developers, your library should be categorized by feature areas:
-   **Database**: Prompts including base table layouts and JDBC connection boilerplates.
-   **Javalin Routes**: Prompts detailing routing stubs and Jackson mapping.
-   **CSS Layouts**: Flexbox alignment prompts.
-   **Security**: Filter setups and exception mappings.

By archiving templates, you only need to swap variables (like table names or route paths) to reuse them.

### Analyzing Low-Quality AI Responses
When an AI assistant returns buggy, obsolete, or hallucinated code, do not simply prompt "try again". Stop and analyze why the failure occurred:
-   **Did you violate context limits?** If the chat thread is long, the AI might have forgotten early constraints. *Solution*: Start a fresh session and paste the updated, clean context.
-   **Was the prompt ambiguous?** *Solution*: Rewrite the instructions, separating goals, environment specifications, and constraints with clear headings.
-   **Did the AI reference obsolete APIs?** *Solution*: Provide the exact API signatures of the version you are using (e.g., Javalin 6.x) directly in the prompt.

### Prompt Log Auditing Process
At the end of a project week, run this quick check:
1.  **Extract**: Identify the 3 most useful prompts you wrote.
2.  **Generalize**: Remove project-specific names (like table columns) and replace them with placeholder variables (e.g., `{{tableName}}`).
3.  **Save**: Add them to a workspace file (like `prompt-templates.md`) in your documentation folder.

---

## Code Example

Here is an example of a generalized, reusable prompt template stored in a library archive.

```markdown
# Reusable Javalin controller Prompt Template

Use this template when generating routes for standard CRUD entities.

---

**Task**: Create a Javalin controller class for the entity: `{{EntityName}}`.

**Environment**:
- Java 17, Javalin 6.1.3, Jackson mapper, SLF4J with Logback

**Entity Properties**:
`{{PropertiesList}}`

**Repository Signature**:
```java
public class `{{EntityName}}`Repository {
    public List<`{{EntityName}}`> findAll();
    public void save(`{{EntityName}}` item);
}
```

**Constraints**:
1. Implement two routes:
   - GET `/api/{{URLPath}}` -> Returns list as JSON with 200 OK.
   - POST `/api/{{URLPath}}` -> Reads JSON body using `ctx.bodyAsClass`, saves to repository, returns 201 Created.
2. Use parameterized logging (`logger.info`) to track request processing.
3. Catch any parsing exceptions and throw a Javalin `BadRequestResponse`.
```

---

## Summary
-   **Prompt maintenance** involves reviewing, generalizing, and archiving effective prompts.
-   Organize your archived prompts into a **Prompt Library** grouped by functional areas (database, routing, layout).
-   Analyze failed AI generations to see if they were caused by **context drift**, **version differences**, or **unclear instructions**.
-   Use **generalized templates** with placeholder variables to quickly spin up components in future development cycles.

---

## Additional Resources
-   [GitHub Guide: Managing Prompt Templates](https://github.com/features/preview/copilot-templates)
-   [Developer Guides: Curating Prompt Libraries](https://www.promptingguide.ai/applications/coding)
-   [Javalin Version Release Changelog](https://javalin.io/news)
