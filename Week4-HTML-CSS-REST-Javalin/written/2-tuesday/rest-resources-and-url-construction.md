# REST Resources and URL Construction

## Learning Objectives
- Identify resources as the core objects of a REST API, represented by nouns.
- Apply industry-standard URL naming conventions, including plural noun rules.
- Design nested URLs to represent relationships between hierarchical resources.
- Implement API versioning patterns in URL paths.

---

## Why This Matters
If you ask five developers to design URLs for an API, you might get five completely different approaches: `/getUser`, `/delete-item`, `/api/v1/orders/view`. Without guidelines, APIs quickly become a disorganized mess of custom routes, making it difficult for frontend developers to integrate with them. REST solves this by introducing a clean, standardized set of URL construction design patterns. Following these practices ensures your APIs are intuitive, predictable, and clean, conforming to standard structures used across top technology companies.

---

## The Concept

### Resources as Nouns
In REST (Representational State Transfer), everything revolves around **Resources**. A resource is any entity or object that your API exposes (e.g., users, orders, tickets, products).
-   **Rule**: URLs must represent *things*, not *actions*. Therefore, URLs should use **nouns**, never verbs.
-   **Bad (Verbs)**:
    -   `/getUser/42`
    -   `/createNewUser`
    -   `/deleteItem?id=12`
-   **Good (Nouns)**:
    -   `/users/42`
    -   `/items/12`

We use HTTP methods (GET, POST, DELETE) to define the action, keeping the URL itself clean of verb details.

### Plural Noun Convention
Although some APIs use singular nouns, the standard industry convention is to always use **plural nouns** for resource collections. This keeps mapping structures consistent:
-   `/users` represents the collection of all users.
-   `/users/42` represents a single user (identified by ID 42) inside that collection.

### URL Structure Best Practices
-   **Use lowercase**: URLs are case-sensitive. Avoid uppercase letters to prevent routing mismatches.
    -   *Bad*: `/api/UserAccounts`
    -   *Good*: `/api/user-accounts`
-   **Use hyphens (`-`), not underscores (`_`)**: Hyphens are easier to read and are the preferred separator in web paths.
    -   *Good*: `/blog-posts`
-   **Do not append file extensions**: Keep URLs clean of system implementation detail.
    -   *Bad*: `/api/users.json`
    -   *Good*: `/api/users`

### Nested Resources (Hierarchical Relationships)
Often, resources are logically related. For example, a "post" has "comments", or a "user" has "orders". We represent these hierarchies by nesting URLs:
-   `GET /posts/12/comments` : Retrieve all comments belonging to post 12.
-   `POST /posts/12/comments` : Add a new comment to post 12.
-   `GET /posts/12/comments/3` : Retrieve comment 3 specifically, within the context of post 12.

*Caution*: Do not nest resources deeper than two levels (e.g., `/users/1/orders/3/items/4/details`). Deep nesting makes URLs excessively long and complicated. If a resource requires deep identification, reference it directly on its root path (e.g., `/items/4`).

### API Versioning
APIs evolve over time. If you make breaking changes to an API layout, you will break existing mobile or web clients currently consuming your service. To prevent this, you should include a version identifier in the root of your URL paths:
-   `/api/v1/users`
-   `/api/v2/users`

This allows you to deploy updates alongside legacy endpoints, giving clients time to migrate to newer data structures.

---

## Code Example

Here is a mapping overview showing how a Javalin server implements a versioned, nested resource structure.

```java
import io.javalin.Javalin;

public class ResourceNamingApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        // Version 1 API Routes
        
        // --- Users Resource Collection ---
        app.get("/api/v1/users", ctx -> {
            ctx.result("GET list of all users");
        });
        
        app.get("/api/v1/users/{id}", ctx -> {
            String userId = ctx.pathParam("id");
            ctx.result("GET details of user " + userId);
        });

        // --- Nested Resource: Orders belonging to a User ---
        // Hierarchy: User (Parent) -> Orders (Child)
        app.get("/api/v1/users/{userId}/orders", ctx -> {
            String userId = ctx.pathParam("userId");
            ctx.result("GET all orders submitted by user " + userId);
        });

        app.post("/api/v1/users/{userId}/orders", ctx -> {
            String userId = ctx.pathParam("userId");
            ctx.result("POST create a new order for user " + userId);
        });
        
        app.get("/api/v1/users/{userId}/orders/{orderId}", ctx -> {
            String userId = ctx.pathParam("userId");
            String orderId = ctx.pathParam("orderId");
            ctx.result("GET order " + orderId + " for user " + userId);
        });
    }
}
```

---

## Summary
-   REST resources represent entities and must be named using **plural nouns**, not verbs.
-   URLs should use **lowercase lettering** and **hyphens** for separation, keeping extensions out of the paths.
-   Represent sub-resources and relationships using **nested URLs** (e.g., `/parent/{id}/child`). Limit nesting depth to two levels.
-   Add **API Versioning** (e.g., `/api/v1/`) to request paths to support incremental improvements without breaking existing integrations.

---

## Additional Resources
-   [RESTful API Design: Resource Modeling](https://restfulapi.net/resource-naming/)
-   [Microsoft API Design Best Practices](https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design)
-   [Google Cloud API Design Guide](https://cloud.google.com/apis/design)
