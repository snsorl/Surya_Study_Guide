# Stereotype Annotations in Spring

## Learning Objectives
- Identify the core stereotype annotations: `@Component`, `@Service`, `@Repository`, and `@Controller`.
- Explain the semantic differences and targeted layers for each annotation.
- Describe how stereotype annotations enable specialized exception translation (especially for `@Repository`).
- Select the appropriate stereotype annotation based on the role of the class in the application architecture.

---

## Why This Matters
While annotating every class with `@Component` will make your Spring application run, it is a bad practice. Spring provides specialized "stereotype" annotations that describe the distinct roles of classes in a multi-layered enterprise architecture. Using the correct stereotypes makes your codebase self-documenting and enables framework-specific optimizations, such as turning database exception codes into uniform Spring exception objects.

---

## The Concept

### The Stereotype Hierarchy
Spring's core stereotypes form a hierarchy. The base annotation is `@Component`. The others (`@Service`, `@Repository`, `@Controller`) are specialized annotations that inherit from `@Component` (they are meta-annotated with `@Component` internally).

```
                 [ @Component ]  (Generic Spring Bean)
                       ▲
         ┌─────────────┼─────────────┐
         │             │             │
    [ @Service ]  [ @Repository ]  [ @Controller ]
    (Domain/Biz)  (Data Access)    (Web / REST API)
```

---

### Layer-by-Layer Breakdown

#### 1. `@Component`
-   **Role:** A generic stereotype for any Spring-managed component.
-   **When to use:** Use this for utility classes, helpers, converters, or any class that does not fit neatly into the typical three-layered architecture (Web, Business, Data Access).

#### 2. `@Service`
-   **Role:** Marks a class that houses business logic and service operations.
-   **When to use:** Use this in the business logic layer. Services coordinate transactions, apply business rules, and interface between controllers and repositories.
-   *Note:* It has no special technical behavior beyond `@Component`, but it is semantically important for tools and aspect-oriented programming (AOP) tracing.

#### 3. `@Repository`
-   **Role:** Marks a class that interacts with databases or data storage mechanisms (Data Access Object / DAO).
-   **When to use:** Use this for database repositories.
-   **Special Technical Behavior:** Classes annotated with `@Repository` are automatically targeted by Spring's **`PersistenceExceptionTranslationPostProcessor`**. This translator intercepts native database exceptions (like Hibernate `JDBCConnectionException` or JPA exceptions) and translates them into Spring's uniform `DataAccessException` hierarchy. This keeps your data access layer technology-agnostic.

#### 4. `@Controller`
-   **Role:** Marks a class as a web controller that handles incoming HTTP requests.
-   **When to use:** Use this in the presentation layer (Spring MVC web pages or `@RestController` for API endpoints).
-   **Special Technical Behavior:** The container scans `@Controller` beans to map incoming URL paths to handler methods.

---

## Architecture Context: The 3-Tier Layered Pattern

By aligning stereotype annotations with architectural layers, you create a structured, clean flow of data:

```
[ HTTP Request ] ──> [ Presentation Layer ]  (@Controller / @RestController)
                            │
                            ▼
                     [ Business Layer ]      (@Service)
                            │
                            ▼
                     [ Data Access Layer ]   (@Repository) ──> [ Database ]
```

---

## Code Example

Here is a structured representation of how stereotype annotations align across a complete three-tier user signup flow.

### 1. Data Access Layer (`@Repository`)
```java
package com.example.dao;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public void saveUser(String email) {
        // Any database exceptions thrown here will be translated into Spring's DataAccessException
        System.out.println("Saving user with email: " + email + " to PostgreSQL.");
    }
}
```

### 2. Business Layer (`@Service`)
```java
package com.example.service;

import com.example.dao.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createAccount(String email) {
        // Apply business rules (e.g. validate domain, enforce restrictions)
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        userRepository.saveUser(email);
    }
}
```

### 3. Presentation Layer (`@RestController`)
```java
package com.example.controller;

import com.example.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController // Extends @Controller; automatically serializes responses to JSON
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String email) {
        userService.createAccount(email);
        return "Signup successful for " + email;
    }
}
```

---

## Summary
-   Stereotypes categorize Spring beans according to their architectural roles (Web, Service, Repository, Component).
-   **`@Component`** is the general parent annotation; all other stereotypes inherit its component scanning capabilities.
-   **`@Service`** is used for business logic and contains transactional operations.
-   **`@Repository`** provides automatic **persistence exception translation** for database operations.
-   **`@Controller`** (and `@RestController`) maps web requests to application logic.

---

## Additional Resources
-   [Spring Documentation: Stereotypes and Meta-Annotations](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-classpath-scanning.html#beans-stereotype-annotations)
-   [Baeldung: Spring Stereotype Annotations](https://www.baeldung.com/spring-stereotype-annotations)
-   [StackOverflow: Difference between @Component, @Repository, @Service, and @Controller](https://stackoverflow.com/questions/6827265/difference-between-component-repository-service-and-controller-annotations-in)
