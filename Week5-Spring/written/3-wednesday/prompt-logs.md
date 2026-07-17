# Prompt Log: Spring Boot Scaffolding & Configuration

## Learning Objectives
- Document prompts used to generate Spring Boot project structures and configuration files.
- Evaluate AI-generated explanations of auto-configuration behavior.
- Structure prompts to ensure AI coding assistants generate clean, modular Spring Boot configurations.
- Reflect on the accuracy and structural quality of AI-assisted outputs.

---

## Why This Matters
AI tools are highly capable assistants, but their output is heavily dependent on the quality of your prompts. If you ask a vague question, the AI will provide a generic solution that might violate your project's design standards (like using field injection or deprecated configuration methods). By documenting and evaluating your prompts in a **Prompt Log**, you can refine your prompting techniques, learn how to steer AI coding assistants effectively, and ensure that AI-generated code remains clean, safe, and aligned with your team's architectural decisions.

---

## The Concept

### Key Strategies for Prompting Spring Boot Tasks
To get the best results from AI coding assistants when working with Spring Boot:
1.  **Define the Technology Stack Context**: Specify the exact framework versions (e.g. Spring Boot 3.2.x, Java 17, Maven).
2.  **Declare Coding Conventions Upfront**: Enforce constraints (e.g. "Do not use field-level `@Autowired`, use constructor injection instead").
3.  **Request Explanations for Magic behaviors**: Ask the AI to explain *why* it selected specific configurations (like auto-configured classes) to ensure it is not hiding configuration issues.
4.  **Use Multi-Step Prompting**: Break complex setups (like setting up a database and security layers) into separate, sequential prompts to prevent the AI from generating incomplete code.

---

## Prompts & Evaluation Log

Below are examples of prompts used to generate Spring Boot scaffolding and configuration, along with evaluations of the AI's responses.

---

### Prompt 1: Scaffolding a REST API Project

#### The Prompt:
> "Generate a Maven-based Spring Boot 3.2 project structure for a Student Directory REST API. The project must target Java 17. The directory hierarchy should separate model, controller, service, and repository layers under the package `com.infosys.student`. The API should support JPA (PostgreSQL) and Lombok. Write the `pom.xml` file, the main application class, and a sample entity using Lombok annotations."

#### Evaluation of the AI Response:
-   **Structural Quality:** The AI correctly mapped out the standard Maven directory layout.
-   **Correctness of Dependencies:** The AI generated the `pom.xml` file correctly, using the `spring-boot-starter-parent` configuration to manage transitive library versions.
-   **Coding Style:** The AI correctly used Lombok's `@Getter` and `@Setter` annotations instead of `@Data` on the database entity class, following JPA performance guidelines.
-   **Self-Correction:** The AI initially missed adding the PostgreSQL driver dependency. On review, it added the `postgresql` runtime dependency to the `pom.xml`.

---

### Prompt 2: Diagnosing Auto-Configuration Behavior

#### The Prompt:
> "I added `spring-boot-starter-data-jpa` to my dependencies, but I haven't configured a database URL yet. The application crashes at startup. Explain why this happens, how Spring Boot's auto-configuration engine made this decision, and how I can temporarily disable database auto-configuration for testing."

#### Evaluation of the AI Response:
-   **Explanatory Quality:** High. The AI explained that the `DataSourceAutoConfiguration` class was loaded because it detected JPA classes on the classpath. It then explained that the configuration class failed its conditional check because no database URL property was defined.
-   **Practical Solution:** The AI provided the correct properties string to temporarily disable the database configuration check during tests:
    `@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })`.
-   **Accuracy:** The suggested solution was correct and allowed the application context to boot without needing a database connection.

---

## Summary
-   **Prompt logs** help developers track and improve their prompts for AI coding assistants.
-   Provide clear **technology stack contexts** and **coding constraints** to prevent the AI from generating deprecated or poor quality configurations.
-   Ask the AI to explain **auto-configuration matches** to confirm that it is loading the correct classes.
-   Evaluate AI-generated responses to identify and correct missing dependencies or configuration errors early.

---

## Additional Resources
-   [Google Cloud: Guide to Prompt Engineering](https://cloud.google.com/blog/products/application-development/prompt-engineering-for-developers)
-   [Spring Boot Documentation: Disabling Specific Auto-Configurations](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-disabling-specific-auto-configuration)
-   [Baeldung: Guide to Spring Boot Application Testing](https://www.baeldung.com/spring-boot-testing)
