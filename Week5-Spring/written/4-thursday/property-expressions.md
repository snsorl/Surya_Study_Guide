# Spring Data Query Methods & `@Query`

## Learning Objectives
- Write query methods using Spring Data query derivation syntax (`findBy`, `countBy`, `deleteBy`).
- Chain multiple search parameters together using logical operators (`And`, `Or`, `Between`).
- Write custom database queries using `@Query` with Java Persistence Query Language (JPQL).
- Write custom database queries using native SQL.

---

## Why This Matters
Writing custom SQL queries for every database operation (like searching for users by email, counting active tasks, or deleting old logs) requires substantial boilerplate database execution code. Spring Data JPA solves this by deriving SQL queries directly from your interface method names. If query derivation is not flexible enough (e.g. for complex multi-table joins), Spring Data allows you to write custom SQL or JPQL statements directly above your repository methods using the `@Query` annotation.

---

## The Concept

### Query Derivation (Method Naming Magic)
Spring Data JPA parses method signatures in your repository interface and builds the corresponding SQL queries automatically.

#### Naming Syntax Pattern:
`[Subject][Predicate][Property]...`
-   **Subjects:** `find...By`, `read...By`, `query...By`, `count...By`, `delete...By`.
-   **Properties:** Refers to fields in the entity class.

```
Subject          Property   Operator   Property
 └─── findBy ───────┼───────── And ───────┼───
                    ▼                     ▼
                FirstName              LastName
```

#### Naming Examples:
-   `findByEmail(String email)` $\rightarrow$ `SELECT * FROM users WHERE email = ?`
-   `findByAgeGreaterThan(int age)` $\rightarrow$ `SELECT * FROM users WHERE age > ?`
-   `findByLastNameAndStatus(String name, String status)` $\rightarrow$ `SELECT * FROM users WHERE last_name = ? AND status = ?`
-   `countByStatus(String status)` $\rightarrow$ `SELECT COUNT(*) FROM users WHERE status = ?`

---

### Custom Queries with `@Query`

If a query requires complex logic (like inner joins, grouping, or complex sub-queries), query derivation method names become too long and unreadable. The `@Query` annotation allows you to define custom queries directly.

#### 1. JPQL (Java Persistence Query Language) - Default
JPQL is database-agnostic. Instead of targeting database tables and columns, it targets your Java **Entity classes** and **fields**.
-   *Syntax:*
    ```java
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User searchByEmail(@Param("email") String email);
    ```

#### 2. Native SQL
Native SQL queries target the database tables directly. Use this if you need to use vendor-specific database functions (like PostgreSQL JSON operators).
-   *Syntax:* Set `nativeQuery = true`.
    ```java
    @Query(value = "SELECT * FROM users WHERE registration_date > NOW() - INTERVAL '30 days'", nativeQuery = true)
    List<User> findRecentSignups();
    ```

---

## Code Example: Derived vs. Custom Queries

Here is a complete repository interface showing derived methods, JPQL, and native SQL queries.

### The Repository Interface
```java
package com.example.repository;

import com.example.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 1. Derived: Find by email domain (e.g., 'gmail.com')
    List<Customer> findByEmailContaining(String domain);

    // 2. Derived: Find active customers registered between two dates
    List<Customer> findByActiveTrueAndRegistrationDateBetween(java.time.LocalDate start, java.time.LocalDate end);

    // 3. JPQL: Find by name using positional parameters (?1)
    @Query("SELECT c FROM Customer c WHERE c.lastName = ?1 AND c.active = true")
    List<Customer> findActiveByLastName(String lastName);

    // 4. JPQL: Named Parameters (:status)
    @Query("SELECT c FROM Customer c WHERE c.status = :status")
    List<Customer> findByStatus(@Param("status") String status);

    // 5. Native SQL: PostgreSQL-specific query
    @Query(value = "SELECT * FROM customers WHERE email ILIKE %:suffix", nativeQuery = true)
    List<Customer> findByEmailDomainNative(@Param("suffix") String suffix);
}
```

---

## Summary
-   **Query Derivation** automatically translates interface method names (e.g. `findByStatus`) into SQL queries.
-   Use **`And`**, **`Or`**, and property operators (e.g., `LessThan`, `Like`) to build complex search queries.
-   **`@Query`** allows you to declare custom queries.
-   **JPQL** queries target Java entity classes and attributes; **Native SQL** (with `nativeQuery = true`) targets database tables directly.

---

## Additional Resources
-   [Spring Data JPA Reference: Query Creation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
-   [Baeldung: Spring Data JPA Query Methods](https://www.baeldung.com/spring-data-derived-queries)
-   [Baeldung: The Spring @Query Annotation Guide](https://www.baeldung.com/spring-data-jpa-query)
