# Javalin Handlers and Context

## Learning Objectives
- Differentiate between endpoint handlers (GET, POST, etc.) and before/after interceptors in Javalin.
- Utilize the Context (`ctx`) object to manage HTTP requests and responses.
- Extract path parameters and query parameters from request URLs.
- Read request bodies and return JSON responses.

---

## Why This Matters
When an HTTP request strikes a Javalin server, the framework wraps all request details (headers, URL, query parameters, body) and response configurations inside a single object called the **Context** (`ctx`). Understanding how to interact with `ctx` is crucial. It is the workhorse of your application: you will use it to extract incoming parameters, serialize database records to JSON, parse user inputs, and determine routing logic.

---

## The Concept

### Handlers in Javalin
Javalin defines three primary categories of handlers:

1.  **Endpoint Handlers**: Mapped to a specific HTTP method and path (e.g., `app.get()`, `app.post()`, `app.put()`, `app.delete()`). These perform the main business actions.
2.  **Before Handlers**: Run *before* the matching endpoint handler is executed. Registered via `app.before(path, handler)`. They are ideal for common filters such as checking user authentication, logging metrics, or validating headers.
3.  **After Handlers**: Run *after* the endpoint handler finishes execution. Registered via `app.after(path, handler)`. They are useful for adding custom response headers, auditing, or compression.

### The Context (`ctx`) Object
Every handler is a functional interface that receives a `Context` instance. The context object contains helper methods for querying details about the incoming HTTP request and configuring the outgoing HTTP response.

Key request operations:
-   `ctx.body()`: Returns the raw request body string.
-   `ctx.bodyAsClass(Class)`: De-serializes the JSON request body directly into a Java object.
-   `ctx.header(name)`: Gets the value of a specific request header.
-   `ctx.pathParam(name)`: Retrieves the path parameter token from the URL.
-   `ctx.queryParam(name)`: Retrieves query parameter values.

Key response operations:
-   `ctx.status(code)`: Sets the HTTP response status.
-   `ctx.result(string)`: Sets the response body as text.
-   `ctx.json(object)`: Serializes the Java object to JSON and sets the response `Content-Type` header to `application/json`.
-   `ctx.header(name, value)`: Sets an outgoing response header.

### Path Parameters vs. Query Parameters
-   **Path Parameters**: Variables embedded directly in the URL structure. Used to identify a specific resource. Mapped using curly braces `{name}` in Javalin paths.
    -   *URL Example*: `/users/42`
    -   *Javalin Code*: `app.get("/users/{id}", ctx -> String id = ctx.pathParam("id"));`
-   **Query Parameters**: Key-value pairs appended to the end of the URL path after a `?`. Used for filtering, sorting, or pagination.
    -   *URL Example*: `/users?role=admin&limit=10`
    -   *Javalin Code*: `String role = ctx.queryParam("role");`

---

## Code Example

Here is a controller class representing standard handler patterns in a Javalin application.

```java
import io.javalin.Javalin;
import java.util.ArrayList;
import java.util.List;

public class RouteApp {
    
    static class Item {
        public int id;
        public String name;
        
        public Item() {} // Required for serialization
        public Item(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        // 1. Before Interceptor (runs for all routes starting with /api)
        app.before("/api/*", ctx -> {
            System.out.println("Intercepted request: " + ctx.method() + " " + ctx.path());
            // Add custom header to all API responses
            ctx.header("X-App-Version", "1.0.0");
        });

        // 2. GET Endpoint with Query Parameters (e.g., /api/items?limit=2)
        app.get("/api/items", ctx -> {
            String limitParam = ctx.queryParam("limit");
            int limit = (limitParam != null) ? Integer.parseInt(limitParam) : 10;
            
            List<Item> items = new ArrayList<>();
            items.add(new Item(1, "Guitar"));
            items.add(new Item(2, "Piano"));
            items.add(new Item(3, "Drums"));
            
            // Limit output list if requested
            if (limit < items.size()) {
                ctx.json(items.subList(0, limit));
            } else {
                ctx.json(items);
            }
        });

        // 3. GET Endpoint with a Path Parameter (e.g., /api/items/1)
        app.get("/api/items/{id}", ctx -> {
            String idStr = ctx.pathParam("id");
            int id = Integer.parseInt(idStr);
            
            // Fetch item logically
            Item item = new Item(id, "Selected Item Name");
            ctx.json(item);
        });

        // 4. POST Endpoint receiving JSON body
        app.post("/api/items", ctx -> {
            // Read JSON body and map directly to Item class
            Item newItem = ctx.bodyAsClass(Item.class);
            
            // Process item addition...
            System.out.println("Saved Item: " + newItem.name);
            
            ctx.status(201); // Created status
            ctx.json(newItem);
        });

        // 5. After Interceptor (runs after routes complete)
        app.after("/api/*", ctx -> {
            System.out.println("Response Status: " + ctx.status());
        });
    }
}
```

---

## Summary
-   **Endpoint Handlers** handle target HTTP method requests, while **Before/After filters** handle cross-cutting request pre-processing and post-processing tasks.
-   The **`Context` object (`ctx`)** abstracts the request and response cycles.
-   Use **`ctx.pathParam()`** for path variables identifying specific resources, and **`ctx.queryParam()`** for optional variables like limits, searches, or sorts.
-   Use **`ctx.bodyAsClass()`** to parse incoming JSON payloads, and **`ctx.json()`** to respond with serialized JSON strings.

---

## Additional Resources
-   [Javalin Documentation: Handler basics](https://javalin.io/documentation#handlers)
-   [Javalin Documentation: The Context Object](https://javalin.io/documentation#context)
-   [REST API URL Conventions](https://restfulapi.net/resource-naming/)
