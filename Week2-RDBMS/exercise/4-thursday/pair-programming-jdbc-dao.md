# Collaborative Lab: Pair Programming a JDBC DAO

## Learning Objectives
- Implement the Data Access Object (DAO) pattern in Java applications.
- Build safe JDBC transaction statements using `PreparedStatement` parameter bindings.
- Configure local databases connections using centralized Connection Factories.
- Practice collaborative Pair Programming roles (Driver/Navigator) in source development.

---

## Setup & Roles (The Thursday Protocol)
This is a **Pair Programming Lab**. Trainees must pair up and assign roles:
-   **Trainee A (Driver):** Manages the IDE, keyboard, and writes the code.
-   **Trainee B (Navigator):** Reviews the code live, checks API syntax against the written specs, suggests edge cases, and guides the architecture.
-   *Note:* You must **swap roles** after implementing each CRUD method (e.g. A drives Create, B drives Read).
-   *AI Policy:* AI code assistants may be used **only** to generate standard boilerplate connections (like setting up ConnectionFactory context) or JUnit test scaffolds. All core database logic must be coded manually.

---

## Step-by-Step Tasks

### Task 1: Initialize the Book Entity and DAO Interface
Create the database entity mapping to the library `books` table, and establish the CRUD method interface:

#### 1. Book.java (POJO)
```java
package exercise;

public class Book {
    private int id;
    private String title;
    private String author;
    
    // Implement constructors, getters, and setters
}
```

#### 2. BookDAO.java (Interface)
```java
package exercise;

import java.util.List;

public interface BookDAO {
    void save(Book book);
    Book getById(int id);
    List<Book> getAll();
    void update(Book book);
    void delete(int id);
}
```

---

### Task 2: Implement the JDBC DAO
Implement the interface inside `BookDAOImpl.java`. 

Ensure your class:
1.  Obtains connections dynamically from a centralized `ConnectionFactory`.
2.  Uses **`PreparedStatement`** for all query executions to prevent SQL Injection.
3.  Implements **Try-With-Resources** to automatically close Connections, Statements, and ResultSets.
4.  Safely maps ResultSet columns by name (e.g. `rs.getString("title")`) rather than index integers.

---

### Task 3: Test with JUnit 5 and H2 In-Memory DB
To test your DAO in isolation without modifying your local PostgreSQL server data:
1.  Configure your Maven project `pom.xml` dependencies to include the **H2 database engine** (scope: `test`).
2.  Write a JUnit 5 test class `BookDAOTest.java` that creates a temporary `books` table inside H2 during `@BeforeEach` setup, and runs tests verifying `save`, `getById`, and `delete`.

---

## Definition of Done
-   A shared Git repository containing compilation-safe Java source code for `Book`, `BookDAO`, `BookDAOImpl`, and `BookDAOTest`.
-   The implementation strictly avoids string concatenation in SQL commands.
-   All JUnit 5 integration tests pass successfully in your IDE runner.
-   Documentation headers in both Java files indicating which trainee was the Driver for each CRUD block.
