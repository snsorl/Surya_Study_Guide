# Spring Data JPA & Relationship Annotations

## Learning Objectives
- Map Java classes to database tables using `@Entity` and `@Table`.
- Configure primary key generation strategies using `@Id` and `@GeneratedValue`.
- Map table columns and validation rules using `@Column`.
- Configure table relationships using `@ManyToOne`, `@OneToMany`, and `@JoinColumn`.
- Differentiate between JPA standard annotations and Hibernate-specific annotations.

---

## Why This Matters
Object-Relational Mapping (ORM) relies on metadata annotations to translate database tables and rows into Java classes and instances. If you do not configure relationship mapping annotations (like `@ManyToOne` or `@JoinColumn`) correctly, Hibernate will create duplicate join tables, fetch relationships slowly, or throw database constraint errors at runtime. Having a clear, detailed reference guide for these database-mapping annotations is essential for building structured, clean entity graphs.

---

## Core JPA Mapping Annotations

### 1. Basic Entity Configuration

-   **`@Entity`**: Marks a class as a JPA database entity. The class must have a public or protected no-argument constructor.
-   **`@Table(name = "table_name")`**: Maps the class to a specific database table. If omitted, JPA defaults to the class name.
-   **`@Id`**: Marks a field as the primary key of the table.
-   **`@GeneratedValue`**: Configures the primary key generation strategy.
    -   *Strategies:*
        -   `GenerationType.IDENTITY`: Relies on database auto-increment columns (e.g. PostgreSQL `SERIAL`).
        -   `GenerationType.SEQUENCE`: Uses a database sequence object.
        -   `GenerationType.UUID`: Automatically generates UUID primary keys (introduced in Spring Boot 3 / Hibernate 6).

```java
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
```

-   **`@Column`**: Maps a field to a specific database column.
    -   *Key Attributes:*
        -   `name`: The database column name.
        -   `nullable`: Set to `false` to enforce database-level `NOT NULL` constraints.
        -   `unique`: Enforces unique index constraints.
        -   `length`: Sets character limits on strings (default is 255).

---

### 2. Relationship Configurations

To represent database foreign key relationships:

#### A. `@ManyToOne`
Represents a many-to-one relationship (e.g. many Order records belong to one Customer).
-   **`@JoinColumn`**: Configures the foreign key column name inside the child table.

```java
@ManyToOne
@JoinColumn(name = "customer_id", nullable = false) // Foreign key column in 'orders' table
private Customer customer;
```

#### B. `@OneToMany`
Represents a one-to-many relationship (e.g. one Customer has many Order records).
-   **`mappedBy`**: Declares that the relationship is mapped by the `customer` field in the child class. This prevents Hibernate from generating an unnecessary third join table.

```java
@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
private List<Order> orders = new ArrayList<>();
```

---

### Standard JPA vs. Hibernate-Specific Annotations

Always prioritize **JPA Standard Annotations** (from `jakarta.persistence.*`) over **Hibernate-specific annotations** (from `org.hibernate.annotations.*`). Standard annotations keep your codebase database-agnostic, allowing you to switch ORM providers (like EclipseLink) in the future without changing your code.

-   *JPA Standard:* `@Entity`, `@Table`, `@Id`, `@Column`.
-   *Hibernate Specific (use only when necessary):* `@CreationTimestamp`, `@UpdateTimestamp`, `@Formula`.

---

## Code Example: Relationship Mapping

Here is a complete, two-class relationship mapping between a `Department` and its `Employee` records.

### The Parent Entity (`Department.java`)
```java
package com.example.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // One Department has many Employees.
    // 'mappedBy' references the 'department' field inside Employee.java.
    // 'CascadeType.ALL' ensures that saving/deleting a department cascades to its employees.
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();
    
    // Constructors, Getters, Setters
}
```

### The Child Entity (`Employee.java`)
```java
package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String name;

    // Many Employees belong to one Department.
    // Declares the foreign key column 'department_id' in the 'employees' table.
    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading optimizes database queries
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Constructors, Getters, Setters
}
```

---

## Summary
-   **`@Entity`** and **`@Table`** map classes to database tables.
-   **`@Id`** and **`@GeneratedValue(strategy = GenerationType.IDENTITY)`** configure auto-incrementing primary keys.
-   **`@ManyToOne`** maps a child field, and **`@JoinColumn`** specifies the foreign key column name.
-   **`@OneToMany(mappedBy = "...")`** maps a parent collection, using the `mappedBy` attribute to prevent duplicate join tables.
-   Prioritize **`jakarta.persistence`** imports over vendor-specific Hibernate annotations.

---

## Additional Resources
-   [Jakarta Persistence API Documentation](https://jakarta.ee/specifications/persistence/)
-   [Baeldung: Hibernate Relationship Mappings Guide](https://www.baeldung.com/jpa-one-to-many)
-   [Baeldung: JPA Cascade Types Explainer](https://www.baeldung.com/jpa-cascade-types)
