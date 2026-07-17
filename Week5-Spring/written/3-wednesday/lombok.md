# Project Lombok in Spring Boot

## Learning Objectives
- Explain how Project Lombok reduces boilerplate code in Java applications.
- Identify and apply key Lombok annotations: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`, and `@Data`.
- Configure IDE plugins to support Lombok's compilation hooks.
- Identify scenarios where Lombok annotations can cause issues, particularly `@Data` in JPA entities.

---

## Why This Matters
Java is a powerful and type-safe language, but it is notoriously verbose. Writing standard data-carrier classes (DTOs or domain models) requires writing getter methods, setter methods, constructors, `toString()`, `equals()`, and `hashCode()` methods manually. This "boilerplate" code litters your classes and takes time to write and maintain. **Project Lombok** resolves this. By using simple annotations, Lombok plugs into the compilation lifecycle to generate these methods on the fly, keeping your source code clean, readable, and focused.

---

## The Concept

### What is Project Lombok?
**Project Lombok** is a Java library that automatically plugs into your editor and build tools to reduce Java boilerplate. During compilation, Lombok parses annotations in your source files and injects the corresponding bytecode (such as compiled getter and setter methods) directly into the target `.class` file.

```
[ Developer Source Code (with @Getter/@Setter) ]
                       │
                       ▼ (Lombok compilation processor)
 [ Compiled Bytecode (.class file with full get/set methods) ]
```

---

### Core Lombok Annotations

-   **`@Getter` / `@Setter`**: Generates getter/setter methods for all fields in the class (if placed at the class level) or for a specific field.
-   **`@NoArgsConstructor`**: Generates a default constructor with no arguments.
-   **`@AllArgsConstructor`**: Generates a constructor containing parameters for all fields in the class.
-   **`@Builder`**: Generates a type-safe, fluent builder API for constructing instances of the class.
-   **`@Data`**: A bundle annotation that combines `@Getter`, `@Setter`, `@RequiredArgsConstructor`, `@ToString`, and `@EqualsAndHashCode`.

---

### IDE Setup (IntelliJ IDEA)
Because Lombok generates methods during compilation, your IDE might highlight getter/setter calls as errors because they don't exist in the raw source code. To fix this:
1.  **Install the Plugin:** Open IntelliJ settings, navigate to **Plugins**, search for **Lombok**, and click install (pre-installed in modern IntelliJ versions).
2.  **Enable Annotation Processing:** Navigate to **Settings $\rightarrow$ Build, Execution, Deployment $\rightarrow$ Compiler $\rightarrow$ Annotation Processors** and check **Enable annotation processing**.

---

### Critical Caution: `@Data` and JPA Entities
While `@Data` is convenient for simple Data Transfer Objects (DTOs), **do not use `@Data` on JPA Entity classes**. 

JPA entities manage database relationships lazily. The `@Data` annotation generates an `@EqualsAndHashCode` and `@ToString` implementation that reads all fields. When logging a lazy-loaded relationship field, it forces Hibernate to query the database, which can trigger severe database query loops (the N+1 select problem) or throw `LazyInitializationException` errors.

*Best Practice for JPA Entities:* Write `@Getter` and `@Setter` annotations explicitly, and write your own custom, safe `equals` and `hashCode` implementations.

---

## Code Example: Reducing Boilerplate

Let's contrast a standard Java DTO class with a Lombok-configured class.

### The Plain Java Approach (Verbose)
```java
public class UserDto {
    private String name;
    private String email;

    public UserDto() {}

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "UserDto(name=" + name + ", email=" + email + ")";
    }
}
```

### The Lombok Approach
```java
package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private String name;
    private String email;
}
```
Lombok generates the exact same methods under the hood during compilation, reducing the code from 24 lines to just 12 lines of readable, clean code.

---

## Summary
-   **Project Lombok** reduces Java boilerplate by injecting bytecode during compilation.
-   **`@Getter` / `@Setter`** generate property accessors automatically.
-   **`@Builder`** provides a fluent builder pattern to configure instances easily.
-   To compile correctly, you must **enable Annotation Processing** in your IDE settings.
-   **Avoid `@Data` on JPA entities** to prevent lazy loading bugs and performance issues.

---

## Additional Resources
-   [Project Lombok Official Documentation](https://projectlombok.org/)
-   [Baeldung: Guide to Project Lombok](https://www.baeldung.com/intro-to-project-lombok)
-   [Lombok: Why @Data should be avoided on JPA Entities](https://jpa-buddy.com/blog/lombok-and-jpa-what-could-go-wrong/)
