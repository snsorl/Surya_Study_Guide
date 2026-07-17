# Project Integration & Migration: Javalin to Spring Boot

## Learning Objectives
- Plan and execute a migration from a Javalin-based REST API to a Spring Boot REST API.
- Map existing database tables (RDBMS schema) to standard JPA Entities.
- Map Javalin handler endpoints to Spring MVC controller methods (`@RestController`).
- Establish a task checklist to migrate multi-layered applications systematically.

---

## Why This Matters
Many companies start by building lightweight prototypes using micro-frameworks like Javalin or Express.js. As these systems grow, they require enterprise capabilities like robust security, database scaling, automated metrics, and transaction boundary coordination. When this occurs, developers are tasked with migrating the prototype to a robust enterprise framework like Spring Boot. Knowing how to map database schemas to JPA models and translate routing handlers to Spring Controller methods systematically is a vital skill for engineering migrations.

---

## The Concept

### The Migration Strategy: Layer by Layer
When migrating a Javalin API to Spring Boot, do not attempt to rewrite the entire application in a single pass. Instead, perform a bottom-up refactoring:

```
[ Step 1: Map Database Schemas to JPA Entities ]
                        │
                        ▼
[ Step 2: Write Spring Data Repositories (replace JDBC) ]
                        │
                        ▼
[ Step 3: Implement Services & Transactions (@Service) ]
                        │
                        ▼
[ Step 4: Map Javalin Routes to Spring MVC (@RestController) ]
```

---

### Step-by-Step Translation Guide

#### 1. Database Layer: JDBC to JPA
-   **Javalin/Raw JDBC:** Connection mappings, manual SQL query strings, and result set parser classes.
-   **Spring Boot JPA:** Decorate the class with `@Entity`, map fields using `@Column`, and write a simple `JpaRepository` interface.

#### 2. Service Layer: Handlers to Services
-   Extract business validations and model transformations from Javalin endpoint handlers and group them under `@Service` classes using `@Transactional` boundaries.

#### 3. Controller Layer: Routing to Controllers
-   **Javalin Route:**
    ```java
    app.get("/api/todos", ctx -> {
        List<Todo> todos = todoDao.findAll();
        ctx.json(todos);
    });
    ```
-   **Spring MVC Controller:**
    ```java
    @RestController
    @RequestMapping("/api/todos")
    public class TodoController {
        private final TodoService todoService;
        public TodoController(TodoService todoService) { this.todoService = todoService; }

        @GetMapping
        public List<Todo> getTodos() {
            return todoService.getAll();
        }
    }
    ```

---

## Migration Task Checklist

Use this standard checklist to guide your migration projects:

- [ ] **Infrastructure Setup**
  - [ ] Initialize new Spring Boot project via start.spring.io.
  - [ ] Add dependencies: `web`, `data-jpa`, `postgresql`, `lombok`.
  - [ ] Configure PostgreSQL connections in `application.yml`.
- [ ] **Data Access Migration**
  - [ ] Convert database tables into JPA `@Entity` classes.
  - [ ] Generate `JpaRepository` interfaces to replace JDBC DAOs.
- [ ] **Business Logic Migration**
  - [ ] Refactor business operations into `@Service` classes.
  - [ ] Apply `@Transactional` annotations on write operations.
- [ ] **API Endpoint Migration**
  - [ ] Build `@RestController` classes to replace Javalin routes.
  - [ ] Apply JSR-380 validation (`@Valid`) on payload request bodies.
- [ ] **Verification & Testing**
  - [ ] Start application and verify Actuator health checks.
  - [ ] Verify endpoints using Postman or integration test requests.

---

## Summary
-   Migrate APIs using a **bottom-up strategy** (Entity $\rightarrow$ Repository $\rightarrow$ Service $\rightarrow$ Controller).
-   Translate **Javalin route handlers** into Spring **`@RestController`** classes mapping paths with `@GetMapping`/`@PostMapping`.
-   Replace **JDBC DAOs** with **`JpaRepository`** interfaces.
-   Follow a structured **migration checklist** to track configuration stages and ensure testing verification.

---

## Additional Resources
-   [Spring Boot Documentation: Migrating to Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.traditional-deployment)
-   [Baeldung: Guide to Spring Boot REST Controllers](https://www.baeldung.com/spring-new-requestmapping-shortcuts)
-   [Javalin Official Documentation](https://javalin.io/)
