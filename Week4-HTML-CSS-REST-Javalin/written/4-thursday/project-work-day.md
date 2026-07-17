# Project 1 Guidelines

## Learning Objectives
- Design and build a complete end-to-end full-stack web application.
- Integrate a Javalin REST API backend with a PostgreSQL database layer.
- Build a responsive front-end user interface using semantic HTML and vanilla CSS.
- Consume the backend API asynchronously from the frontend using the JS Fetch API.
- Apply security controls, logging, and error handling mappings across all layers.

---

## Why This Matters
Building individual code files during exercise sessions is helpful, but it does not mirror the real-world experience of a software developer. In the industry, you must build complete, coherent applications from scratch. Project 1 is your opportunity to connect everything you have learned: database normalization, JDBC queries, Javalin route controllers, HTML forms, and CSS layouts. Completing this project will prove your ability to design database schemas, map server routing tables, and deliver a working, styled application to users.

---

## The Concept

### Project 1 Scope & Architecture
Trainees are tasked with building a web application of their choice (e.g., a Task Manager, library catalog, inventory tracker, or contact manager). The application must strictly follow a three-tier architecture model:

```
  +------------------------------------------------------+
  | 1. Frontend: HTML / CSS / Vanilla JavaScript (Fetch) |
  +------------------------------------------------------+
                             |  HTTP JSON Requests
                             v
  +------------------------------------------------------+
  | 2. Backend: Javalin REST API (Java Controllers)      |
  +------------------------------------------------------+
                             |  JDBC Queries
                             v
  +------------------------------------------------------+
  | 3. Database: PostgreSQL                              |
  +------------------------------------------------------+
```

### Core Requirements

#### 1. Database (PostgreSQL)
-   At least two related tables (e.g., `users` and `tasks`).
-   Proper primary keys, foreign keys, and constraint checks.
-   A setup SQL script (`schema.sql`) to initialize tables.

#### 2. Backend (Java & Javalin)
-   JDBC connection factory to manage Postgres database access.
-   DAO (Data Access Object) classes to cleanly encapsulate SQL logic.
-   REST routing structure (GET, POST, PUT, DELETE) handling requests.
-   Centralized exception mapping mapping Java exceptions to HTTP status codes (2xx, 4xx, 5xx).
-   Logback logging tracking database operations and security alerts.

#### 3. Frontend (HTML, CSS, JS)
-   A single-page style layout (or clean navigation pages) served by Javalin or static asset hosting.
-   Fully semantic HTML structure and CSS Box Model layouts (no plain raw pages).
-   Use of the browser's Fetch API to handle user actions (like form submissions) asynchronously, updating the DOM dynamically without page refreshes.

### Deliverables & Timeline
-   **Thursday**: Active pair programming work day. Teams must demonstrate at least one endpoint fully integrated from database to Javalin and fetched in the HTML view before the end of the day.
-   **Friday**: Presentation Day. Each team has 10 minutes to present their running application, review their code architecture, and conduct a brief Q&A session.

### Grading Criteria
-   **Functional Correctness (40%)**: Does the application run without crashing? Do all CRUD operations work from the frontend interface?
-   **Code Quality & Architecture (30%)**: Is the code clean, separated into tiers (DAO/Controller/Frontend), and free of raw database logins hardcoded in methods?
-   **API Design & Security (20%)**: Are HTTP methods and status codes mapped correctly? Is security logging active?
-   **Presentation & Demo (10%)**: Clear explanation of technical decisions, database designs, and teamwork coordination.

---

## Code Example

Here is an example setup demonstrating how the Javalin backend configures CORS and serves both the static frontend files and the JSON REST endpoints.

```java
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class ProjectServer {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            // Enable CORS so local static test files can query this API
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> rule.anyHost());
            });

            // Serve the frontend assets (index.html, styles.css, app.js)
            // located in the folder: src/main/resources/public
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
                staticFiles.location = Location.CLASSPATH;
            });
        });

        // Initialize routes
        app.get("/api/todos", ctx -> {
            // Fetch from database via DAO...
            ctx.json("[]");
        });

        // Start server
        app.start(8080);
    }
}
```

---

## Summary
-   **Project 1** is a full-stack CRUD application connecting a PostgreSQL database, JDBC backend, Javalin REST API, and HTML/CSS/JS frontend.
-   Applications must adhere to **clean tier divisions** separating SQL logic from route handling and front-end displays.
-   Submit database initialization scripts alongside source code repositories.
-   Review the **grading criteria** to prioritize core functional correctness and secure API configurations.

---

## Additional Resources
-   [Vanilla JS Fetch Guide](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API)
-   [Javalin Static File Serving Guide](https://javalin.io/documentation#static-files)
-   [PostgreSQL JDBC Driver Documentation](https://jdbc.postgresql.org/)
