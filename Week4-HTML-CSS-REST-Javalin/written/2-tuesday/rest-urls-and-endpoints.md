# REST URLs and Endpoints

## Learning Objectives
- Map CRUD (Create, Read, Update, Delete) database operations to standard HTTP methods and URL structures.
- Contrast the use cases for Path Parameters versus Query Parameters.
- Critique bad URL designs that mix verbs into paths.
- Design clean, intuitive API endpoint tables for project planning.

---

## Why This Matters
When designing an API backend, your primary task is to connect HTTP endpoints to backend database operations. If you map these endpoints incorrectly, you make it incredibly difficult for frontend clients to communicate with your backend. By mastering the mapping between CRUD actions and HTTP method/URL combinations, you can create clean, self-documenting routing files that developers can immediately navigate without guessing.

---

## The Concept

### Mapping CRUD to HTTP
In database engineering, we speak of CRUD. In RESTful web design, we map these database actions to standard HTTP methods:

| CRUD Operation | SQL equivalent | HTTP Method | Target URL Pattern | Expected Status |
|---|---|---|---|---|
| **Create** | `INSERT` | **POST** | `/resources` | 201 Created |
| **Read (List)** | `SELECT *` | **GET** | `/resources` | 200 OK |
| **Read (Single)**| `SELECT WHERE id`| **GET** | `/resources/{id}` | 200 OK / 404 |
| **Update (Full)**| `UPDATE SET` | **PUT** | `/resources/{id}` | 200 OK / 204 |
| **Update (Part)**| `UPDATE SET` | **PATCH** | `/resources/{id}` | 200 OK |
| **Delete** | `DELETE` | **DELETE** | `/resources/{id}` | 200 OK / 204 |

### Parameter Conventions: Path vs. Query

#### Path Parameters
Path parameters are part of the resource identifier URL. They are **mandatory** components.
-   **Rule**: Use path parameters to identify a *specific resource instance* or parent-child context.
-   *Example*: `GET /api/books/9780132350884` (identifies a book by ISBN).
-   *Example*: `GET /api/authors/5` (identifies author #5).

#### Query Parameters
Query parameters appear after the path, separated by a `?` and joined by `&`. They are **optional** modifiers.
-   **Rule**: Use query parameters to change *how* the collection is displayed or filtered, without altering the resource path itself.
-   *Use cases*:
    -   **Filtering**: `GET /api/books?author=martin`
    -   **Sorting**: `GET /api/books?sortBy=publishDate&order=desc`
    -   **Pagination**: `GET /api/books?page=2&limit=50`
    -   **Search**: `GET /api/books?q=clean+code`

### Avoiding Verbs in URLs
A common mistake when moving from basic script-based coding to REST is writing RPC-style (Remote Procedure Call) endpoints that put the action in the URL path.

Compare these structures:
-   *RPC Style*:
    -   `POST /api/createBook`
    -   `POST /api/updateBook?id=4`
    -   `GET /api/getAllBooks`
    -   `POST /api/deleteBook/4`
-   *REST Style*:
    -   `POST /api/books` (Create)
    -   `PUT /api/books/4` (Update)
    -   `GET /api/books` (Get all)
    -   `DELETE /api/books/4` (Delete)

By relying on the HTTP method to convey the verb, the REST URLs remain identical, stable, and clean.

---

## Code Example

Here is a clear representation of a clean endpoint mapping implementation inside a Javalin server routing module.

```java
import io.javalin.Javalin;

public class EndpointMappingApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        // API Endpoint mapping structure for a "products" resource

        // 1. GET ALL (Read List - with query filter)
        // URL Pattern: GET /api/products?category=electronics
        app.get("/api/products", ctx -> {
            String category = ctx.queryParam("category");
            if (category != null) {
                ctx.result("Fetching all products in category: " + category);
            } else {
                ctx.result("Fetching all products in the catalog");
            }
        });

        // 2. GET ONE (Read Single - with path parameter)
        // URL Pattern: GET /api/products/42
        app.get("/api/products/{id}", ctx -> {
            String productId = ctx.pathParam("id");
            ctx.result("Fetching details for product with ID: " + productId);
        });

        // 3. POST (Create)
        // URL Pattern: POST /api/products
        app.post("/api/products", ctx -> {
            ctx.status(201); // 201 Created
            ctx.result("Creating a new product from JSON payload...");
        });

        // 4. PUT (Full Update)
        // URL Pattern: PUT /api/products/42
        app.put("/api/products/{id}", ctx -> {
            String productId = ctx.pathParam("id");
            ctx.result("Replacing product " + productId + " entirely with request payload...");
        });

        // 5. DELETE (Delete)
        // URL Pattern: DELETE /api/products/42
        app.delete("/api/products/{id}", ctx -> {
            String productId = ctx.pathParam("id");
            ctx.status(204); // 204 No Content
            System.out.println("Product " + productId + " deleted from DB.");
        });
    }
}
```

---

## Summary
-   REST maps **CRUD database actions** directly to standard HTTP methods (POST, GET, PUT/PATCH, DELETE) acting on noun resource paths.
-   **Path Parameters** are part of the resource path and identify individual items; **Query Parameters** are optional string values appended to filter, sort, or paginate results.
-   Keep URL paths clean of **action verbs**; the action is defined by the HTTP method itself.
-   Designing a clear, logical URL structure is essential before writing code for any backend web application.

---

## Additional Resources
-   [REST API Tutorial: Restful URL Designing](https://restfulapi.net/rest-api-design-tutorial-with-example/)
-   [HTTP Method Semantics (RFC 7231)](https://datatracker.ietf.org/doc/html/rfc7231#section-4)
-   [Javalin Routing Guide](https://javalin.io/documentation#handlers)
