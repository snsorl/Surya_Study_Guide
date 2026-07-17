# Exception Handling in Javalin

## Learning Objectives
- Map Java exceptions to HTTP responses using `app.exception()`.
- Use Javalin's built-in `HttpResponseException` and its subclasses.
- Return structured JSON error responses to the client instead of generic HTML error pages.

---

## Why This Matters
If a backend application encounters an unhandled exception (like a database query error or a NullPointerException), Jetty will catch it and return a generic `500 Internal Server Error` page containing a raw stack trace. This exposes the inner implementation details of your code to external users, representing a security risk. A well-designed REST API must catch runtime exceptions, log them securely, and respond to the client with clean, structured JSON detailing the error alongside a relevant HTTP status code.

---

## The Concept

### Mapping Exceptions
In Javalin, you handle exceptions globally by registering exception handlers on the app instance. This keeps your request controllers thin and clean of repetitive try-catch blocks.

```java
app.exception(NullPointerException.class, (exception, ctx) -> {
    ctx.status(400);
    ctx.result("A required resource reference was null.");
});
```
Whenever any handler throws a `NullPointerException` (or one of its subclasses), Javalin intercepts it, executes the registered handler, and returns the mapped status code and response body.

### Built-in HTTP Response Exceptions
Javalin provides a set of pre-built runtime exceptions mapping directly to standard HTTP status codes. These are subclasses of `HttpResponseException`.

Commonly used subclasses:
-   `BadRequestResponse` -> status code 400
-   `UnauthorizedResponse` -> status code 401
-   `ForbiddenResponse` -> status code 403
-   `NotFoundResponse` -> status code 404
-   `ConflictResponse` -> status code 409
-   `InternalServerErrorResponse` -> status code 500

You can throw these exceptions directly from your controller handlers or business logic:

```java
app.get("/users/{id}", ctx -> {
    int id = Integer.parseInt(ctx.pathParam("id"));
    User user = database.findUser(id);
    if (user == null) {
        throw new NotFoundResponse("User with ID " + id + " does not exist.");
    }
    ctx.json(user);
});
```

### Returning Structured JSON Errors
Instead of returning raw text strings, it is best practice to return structured JSON payloads to clients. This allows the frontend client to parse the error message and display helpful warnings to users.

To achieve this:
1.  Define a simple error response object or map.
2.  Serialize the object to JSON using Jackson.
3.  Register a global exception handler for `Exception.class` to capture any otherwise unhandled runtime exceptions.

---

## Code Example

Here is a complete setup for mapping exceptions to structured JSON responses.

### 1. The Custom Error Response DTO
```java
// ErrorResponse.java
public class ErrorResponse {
    private String message;
    private long timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}
```

### 2. Registering Exception Mappings in Javalin
```java
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;

public class ExceptionApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        // Map Specific Built-in HTTP exceptions to JSON
        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ctx.status(404);
            ctx.json(new ErrorResponse(e.getMessage()));
        });

        app.exception(BadRequestResponse.class, (e, ctx) -> {
            ctx.status(400);
            ctx.json(new ErrorResponse("Invalid Request: " + e.getMessage()));
        });

        // Map General Runtime Exceptions to prevent raw 500 stacks
        app.exception(Exception.class, (e, ctx) -> {
            // Log the actual stack trace locally for developers (do not return it to client!)
            System.err.println("Internal Server Error: " + e.getMessage());
            e.printStackTrace();
            
            ctx.status(500);
            ctx.json(new ErrorResponse("An unexpected server error occurred."));
        });

        // Example trigger routes
        app.get("/trigger-404", ctx -> {
            throw new NotFoundResponse("Requested book search returned no results.");
        });

        app.get("/trigger-500", ctx -> {
            throw new RuntimeException("Database connection died mid-query.");
        });
    }
}
```

---

## Summary
-   **`app.exception()`** lets you intercept Java exceptions thrown by route handlers and translate them into clean HTTP responses.
-   Javalin's **`HttpResponseException` subclasses** (like `NotFoundResponse`) make it easy to halt execution and return a specific HTTP status code with a descriptive string.
-   **Structured JSON errors** ensure client-side code can easily parse errors, while hiding internal stack traces protects the application's underlying code implementation.

---

## Additional Resources
-   [Javalin Documentation: Exception Mapping](https://javalin.io/documentation#exception-mapping)
-   [REST API Design: Error Handling Guidelines](https://restfulapi.net/http-status-codes/)
-   [Jackson Databind documentation](https://github.com/FasterXML/jackson-databind)
