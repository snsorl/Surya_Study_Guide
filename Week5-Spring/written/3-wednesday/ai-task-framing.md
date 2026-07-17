# AI Task Framing & Decomposition in Spring Boot

## Learning Objectives
- Frame complex backend programming tasks clearly for AI coding assistants.
- Apply task decomposition to break multi-step Spring Boot configurations into manageable stages.
- Construct high-fidelity prompts containing system contexts, output layouts, and constraints.
- Evaluate and refine AI outputs at intermediate check gates.

---

## Why This Matters
Asking an AI to "Build a complete Spring Boot e-commerce backend" will yield a generic, incomplete solution that likely has database, security, and integration issues. Large language models operate best when tasks are broken down into logical steps. By mastering **AI Task Framing** and **Task Decomposition**, you can guide the AI to generate high-quality, production-ready code. This approach ensures the AI acts as a reliable programming assistant rather than a source of code churn and architecture debt.

---

## The Concept

### The Principles of Task Framing

To ensure an AI coding assistant generates code that aligns with your project standards, structure your prompts using the **4-Part Framing Framework**:

```
┌──────────────────────────────────────────────────────────────┐
│                  High-Fidelity Prompt                        │
├──────────────────────────────────────────────────────────────┤
│ 1. Context: "Spring Boot 3.2, Java 17, PostgreSQL"           │
│ 2. Task: "Create a UserSignup service method"               │
│ 3. Output Format: "Write only the Java code and unit tests"  │
│ 4. Constraints: "Must use BCrypt, constructor injection"    │
└──────────────────────────────────────────────────────────────┘
```

1.  **System Context**: Describe your technical stack, versions, build tools, database systems, and testing libraries.
2.  **Specific Task**: Define the single action or function the AI must perform.
3.  **Desired Output Format**: Specify exactly what files, snippets, or explanations you expect the AI to return (e.g., "Write only the Java service class and its JUnit 5 integration test, do not write explanations").
4.  **Architectural Constraints**: State the rules the code must follow (e.g., "Must be transactional, must use constructor injection, do not use field-level annotations").

---

### Task Decomposition
**Task Decomposition** is the process of breaking down a large, complex task into smaller, sequential steps.

For example, instead of asking the AI to build a complete database-backed REST API in a single prompt, decompose the task into four distinct stages:

```
[ Stage 1: Build Database Entity ]
              │
              ▼
[ Stage 2: Build JpaRepository ]
              │
              ▼
[ Stage 3: Build Service Layer & Validation ]
              │
              ▼
[ Stage 4: Build REST Controller Endpoints ]
```

#### Why Decompose?
-   **Token Management:** Prevents the AI from running out of output tokens, which causes it to truncate code.
-   **Step-by-Step Validation:** You can verify that the database mapping is correct before writing service logic on top of it.
-   **Easier Debugging:** If a bug occurs, you know exactly which stage triggered the error.

---

## Code Example: A Decomposed Prompt Sequence

Here is how you would decompose a request to build a "User Registration Flow" into separate, sequential prompts.

---

### Step 1: Generate the JPA Database Entity

#### The Prompt:
> "We are building a Spring Boot 3.2 application using JDK 17 and PostgreSQL.
> Write a JPA Entity class called `User` that maps to the table `users`.
> Columns: `id` (auto-generated Long), `username` (unique, non-null String), `password_hash` (non-null String), and `created_at` (LocalDateTime automatically set).
> **Constraints:**
> - Use Lombok annotations for getters and setters.
> - Do not use `@Data` (to avoid performance problems with JPA).
> - Write only the Java class, no explanations."

---

### Step 2: Generate the Repository Layer

#### The Prompt:
> "Using the `User` entity created in the previous step:
> Write a Spring Data JPA Repository interface called `UserRepository` that extends `JpaRepository`.
> **Task:**
> - Add a query method to check if a user exists by their username: `existsByUsername`.
> - Do not write explanations."

---

### Step 3: Generate the Service Layer with Transaction Boundaries

#### The Prompt:
> "Using the `User` entity and `UserRepository` created in the previous steps:
> Write a service class called `UserService` that registers new users.
> **Constraints:**
> - Must use constructor injection to wire `UserRepository`.
> - Do not use field-level `@Autowired` injection.
> - The class must be annotated with Spring's `@Service`.
> - The `registerUser` method must be annotated with `@Transactional`.
> - Check if the username already exists using the repository. If it does, throw a custom `DuplicateUserException`.
> - Assume a bean helper named `PasswordEncoder` is available in the container. Use it to hash the incoming password before saving."

---

## Summary
-   **Task Framing** provides the system context, task details, output format, and design constraints to ensure the AI generates high-quality code.
-   **Task Decomposition** breaks large tasks down into smaller, sequential steps to prevent code truncation and simplify verification.
-   By executing tasks in **stages**, you can inspect, test, and approve code before proceeding to the next layer of the application.

---

## Additional Resources
-   [Google Cloud: Prompt Engineering Best Practices](https://cloud.google.com/blog/products/application-development/prompt-engineering-for-developers)
-   [Martin Fowler: Agile Software Task Decomposition Patterns](https://martinfowler.com/articles/agile-decomposition.html)
-   [Baeldung: Guide to Spring Boot REST API Design](https://www.baeldung.com/spring-boot-rest-api)
