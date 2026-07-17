# AI: Cross-Cutting Concerns

## Learning Objectives
- Define cross-cutting concerns in web development.
- Utilize AI tools to generate consistent middleware handlers (logging, CORS, input validation filters).
- Critically audit AI-generated middleware code for security, error handling, and performance risks.
- Integrate AI-generated filters into a Javalin application framework.

---

## Why This Matters
When building web APIs, you write core business logic (like updating a database) alongside **cross-cutting concerns**—logic that affects multiple parts of the application (like checking authentication, setting CORS headers, logging requests, or validating inputs). Writing these security filters manually can be repetitive and boring, making them prime candidates for AI generation. However, because these components sit at the gateway of your server, a single error in an AI-generated middleware script can compromise your entire system. Learning to generate, review, and patch these components is a vital skill for modern developers.

---

## The Concept

### What are Cross-Cutting Concerns?
In software engineering, cross-cutting concerns are aspects of a program that affect other parts of the application. They cannot be easily decoupled from the rest of the system without middleware filters.

Common examples in API development include:
-   **Logging**: Tracking the timeline of incoming requests and performance metrics.
-   **CORS Configuration**: Setting response headers to allow frontend access.
-   **Input Validation**: Checking that client parameters match required formats before passing them to database layers.
-   **Security Mapping**: Verifying tokens or authorization credentials.

In Javalin, these are handled using `app.before()`, `app.after()`, and plugins.

### Prompting AI for Middleware Code
When asking an AI to generate middleware configurations or filters, you must write detailed prompts to ensure security best practices are met.
-   *The Risk*: A poorly prompted AI may write CORS configurations that allow all origins in production (`cors.anyHost()`), or create input validation filters that throw generic `NullPointerException` errors instead of returning clean HTTP `400 Bad Request` messages.

To write high-quality prompts:
1.  Explicitly state the environment (e.g., Javalin 6.x, Java 17).
2.  Request specific error handling (e.g., "Catch parsing exceptions and throw a `BadRequestResponse`").
3.  Enforce clean performance practices (e.g., "Do not parse the DB on every logging statement").

### Critically Auditing AI Outputs
Before running AI-generated middleware code, check:
-   **Security**: Does the CORS config allow wildcard origins in a production context? Does the filter log sensitive credentials?
-   **Resource Safety**: Does the validation regex protect against ReDoS (Regular Expression Denial of Service) attacks?
-   **Exception Paths**: If the validation filter fails, does it leak system data or crash the JVM?

---

## Code Example

Here is an example prompt requesting input validation middleware, alongside the generated code and the code review audit.

### Prompt
> Create a Javalin `before()` input validation handler in Java 17. It must intercept POST requests to `/api/users`.
> It must validate that the request body JSON contains:
> 1. A non-empty "email" matching a standard email format.
> 2. A "username" between 3 and 15 alphanumeric characters.
> Return an HTTP 400 Bad Request response with a structured JSON error list if validation fails.

### Generated Middleware Code
```java
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationMiddleware {
    private static final Pattern EMAIL_REGEX = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern USERNAME_REGEX = 
            Pattern.compile("^[a-zA-Z0-9]{3,15}$");

    static class UserPayload {
        public String email;
        public String username;
    }

    public static void register(Javalin app) {
        app.before("/api/users", ctx -> {
            if ("POST".equalsIgnoreCase(ctx.method())) {
                List<String> errors = new ArrayList<>();
                UserPayload payload = ctx.bodyAsClass(UserPayload.class);

                // Validate Email
                if (payload.email == null || !EMAIL_REGEX.matcher(payload.email).matches()) {
                    errors.add("Invalid email format.");
                }

                // Validate Username
                if (payload.username == null || !USERNAME_REGEX.matcher(payload.username).matches()) {
                    errors.add("Username must be alphanumeric and 3-15 characters.");
                }

                // If errors exist, reject the request
                if (!errors.isEmpty()) {
                    ctx.status(400);
                    ctx.json(errors);
                    // Halts route execution
                    throw new BadRequestResponse("Validation failed");
                }
            }
        });
    }
}
```

### Critical Audit of the Code:
-   **Security**: The regex pattern `^[A-Za-z0-9+_.-]+@(.+)$` is simple and safe from ReDoS, but it is not highly strict (e.g., doesn't check domain suffix length). It is sufficient for basic inputs but could be optimized.
-   **Bug Alert**: If the incoming request has a completely empty or malformed body, `ctx.bodyAsClass(UserPayload.class)` will throw a Jackson parsing exception *before* our custom validation blocks execute.
-   *Mitigation*: We should add an exception mapper for `JsonProcessingException` to return 400 Bad Request.

---

## Summary
-   **Cross-cutting concerns** are application-wide concerns (like validation, security, and logging) that apply across multiple API routes.
-   **AI generation** is highly effective for writing repetitive middleware code, but prompts must specify explicit security and error handling constraints.
-   Always **audit AI-generated filters** for wildcards (CORS), regex security (ReDoS), and crash resistance before compiling.

---

## Additional Resources
-   [OWASP Input Validation Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Input_Validation_Cheat_Sheet.html)
-   [Javalin Documentation: Before/After handlers](https://javalin.io/documentation#before-handlers)
-   [Baeldung: Cross-Cutting Concerns in Software Engineering](https://www.baeldung.com/cs/cross-cutting-concerns)
