# Guided Coding: Spring Boot Project Creation

## Learning Objectives
- Scaffold a new Spring Boot application using Initializr dependencies.
- Establish clean package architecture conventions (Controller, Service, Repository, Entity, DTO).
- Map a database table to a JPA entity using annotations.
- Build a REST controller using `@RestController`, `@GetMapping`, and `@PostMapping`.

---

## Why This Matters
Building a structured Spring Boot application requires following standard package and file naming conventions. If you place repositories in controller folders or mix entities with utility helpers, your codebase will quickly become disorganized and difficult to maintain. In this guided coding guide, we will build a complete "Student Directory API" backend from scratch. We will map out the directory structures, define database entities, and expose REST endpoints, establishing best practices for structuring production-grade backend systems.

---

## Guided Walkthrough

### Step 1: Project Scaffolding
Navigate to [start.spring.io](https://start.spring.io) and select these settings:
-   **Project:** Maven Project
-   **Language:** Java (JDK 17)
-   **Spring Boot:** 3.2.x
-   **Group:** `com.infosys`
-   **Artifact:** `student-directory`
-   **Dependencies:** Spring Web, Spring Data JPA, PostgreSQL Driver, Lombok.

Download the zip file, extract it, and open the folder in your IDE.

---

### Step 2: Establish the Package Directory Hierarchy
Create folders matching this structure under `src/main/java/com/infosys/studentdirectory/`:

```
com.infosys.studentdirectory/
  ├── StudentDirectoryApplication.java (Main Boot Runner)
  ├── controller/ (Exposes HTTP Endpoints)
  ├── service/    (Implements Business Rules)
  ├── repository/ (Handles Database Queries)
  ├── model/      (Contains JPA Database Entities)
  └── dto/        (Contains Data Transfer Objects)
```

---

### Step 3: Create the JPA Database Entity (`model/Student.java`)
This class maps directly to a `students` table in your database.

```java
package com.infosys.studentdirectory.model;

import jakarta.persistence.*;
import lombok.*;

@Entity // Instructs JPA to map this class to a table
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;
}
```

---

### Step 4: Create the Database Access Layer (`repository/StudentRepository.java`)
Create a repository interface that extends `JpaRepository`. This interface provides pre-configured SQL execution methods (like `findAll()`, `save()`, `findById()`).

```java
package com.infosys.studentdirectory.repository;

import com.infosys.studentdirectory.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Basic CRUD operations are inherited automatically!
}
```

---

### Step 5: Create the Business Logic Layer (`service/StudentService.java`)
This service acts as the bridge, enforcing business rules (like email uniqueness checks) before saving records.

```java
package com.infosys.studentdirectory.service;

import com.infosys.studentdirectory.model.Student;
import com.infosys.studentdirectory.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student enrollStudent(Student student) {
        // Enforce business rules before saving
        if (student.getEmail() == null || !student.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid student email address.");
        }
        return studentRepository.save(student);
    }
}
```

---

### Step 6: Create the REST API Controller (`controller/StudentController.java`)
Expose REST HTTP methods to clients.

```java
package com.infosys.studentdirectory.controller;

import com.infosys.studentdirectory.model.Student;
import com.infosys.studentdirectory.service.StudentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Combines @Controller and @ResponseBody
@RequestMapping("/api/students") // Base path mapping
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> listAll() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public Student register(@RequestBody Student student) {
        return studentService.enrollStudent(student);
    }
}
```

---

### Step 7: Configure Database Settings (`application.yml`)
Configure database connections in `src/main/resources/application.yml`.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/school_db
    username: postgres
    password: secretpassword
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update # Automatically creates tables matching entity structure
    show-sql: true # Prints executed SQL to terminal console for debugging
    properties:
      hibernate:
        format_sql: true
```

---

## Summary
-   Spring Boot applications are structured into layers: **Controller $\rightarrow$ Service $\rightarrow$ Repository $\rightarrow$ Database Entity**.
-   **`@Entity`** maps a Java class to a relational database table.
-   **`JpaRepository<Entity, KeyType>`** provides pre-configured database CRUD operations.
-   **`@RestController`** serializes response payloads into JSON automatically.
-   Database connections are configured in **`application.yml`** under resources.

---

## Additional Resources
-   [Spring Boot REST Service Guide](https://spring.io/guides/gs/rest-service/)
-   [Baeldung: Spring Boot Directory Structure](https://www.baeldung.com/spring-boot-directory-structure)
-   [Spring Data JPA Reference Documentation](https://spring.io/projects/spring-data-jpa)
