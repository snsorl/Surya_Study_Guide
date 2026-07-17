# Composite Keys

## Learning Objectives
- Define a Composite Key and explain how it differs from a single-column Primary Key.
- Identify scenarios where a composite key is appropriate, specifically in junction tables.
- Declare a composite primary key in SQL using table-level syntax.
- Analyze the performance and development trade-offs of using composite keys in enterprise applications.

---

## Why This Matters
On Tuesday, we discussed how Many-to-Many (M:N) relationships require a third table (a **junction table**) to link records together. For example, in an enrollment system, a student can enroll in multiple courses, and a course can have multiple students. We build an `enrollments` table to track this connection.

But how do we uniquely identify a row in this junction table? A student should not be able to enroll in the same course twice—that would create duplicate garbage data.

To prevent this, we combine the `student_id` and the `course_id` to form a single, multi-column identifier known as a **Composite Key**. Understanding how and when to use composite keys is crucial for building clean relationships, enforcing integrity on intermediate tables, and designing data models.

---

## The Concept

### 1. What is a Composite Key?
A **Composite Key** is a primary key that consists of two or more columns. 

While a standard primary key relies on a single column (like `customer_id`) to guarantee uniqueness, a composite key guarantees that the **combination** of values across all defined columns is unique. Individual columns within a composite key can repeat across different rows, but the overall group of values must never repeat.

Like standard primary keys, no column in a composite key can contain `NULL` values.

### 2. When to Use Composite Keys
-   **Junction Tables (M:N Relationships):** This is the most common use case. Combining the foreign keys of the two linked tables guarantees that a connection between two specific records is made only once.
-   **Weak Entities:** Objects that cannot exist without a parent entity. For example, a `dependent` of an employee might use a composite key of `(employee_id, dependent_number)`.
-   **Data Partitioning & Time-Series Data:** Tables storing massive log data might use `(device_id, log_timestamp)` to track data points uniquely over time.

### 3. Architectural Trade-offs
Using composite keys carries significant trade-offs that full-stack developers must evaluate:

**Advantages:**
-   **No Extra Indexes Needed:** Because the key itself enforces relationship uniqueness, you do not need to create additional tables or unique indexes.
-   **No "Boilerplate" Columns:** You do not need to generate a meaningless auto-incrementing integer (`enrollment_id`) when the combination of IDs is already unique.

**Disadvantages (The Enterprise Cost):**
-   **Complex Foreign Keys:** If Table B has a composite primary key of `(first_name, last_name)` and Table C wants to reference it, Table C must store *both* columns as foreign keys. This leads to schema bloat.
-   **JDBC Mapping Complexity:** Modern Java frameworks (like Hibernate/JPA, which we cover in Week 5) require writing custom key classes to handle composite keys, adding structural boilerplate code to your Java application.
-   **Bulkier Indexes:** Indexing multiple columns consumes more memory than indexing a single, small integer.

---

## Code Example

Let's look at how to declare a composite primary key in PostgreSQL. We will build a junction table `course_enrollments` to link a `students` table and a `courses` table.

```sql
CREATE TABLE students (
    student_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE courses (
    course_id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);
```

### Declaring the Junction Table with a Composite Key
To declare a composite key, you must use **table-level constraint syntax** at the bottom of the DDL definition:

```sql
CREATE TABLE course_enrollments (
    student_id INT REFERENCES students(student_id),
    course_id INT REFERENCES courses(course_id),
    enrollment_date DATE NOT NULL,
    
    -- Composite primary key declaration
    PRIMARY KEY (student_id, course_id)
);
```

---

### Verifying the Constraints

Let's insert data:

```sql
-- Valid: Student 1 enrolls in Course 101
INSERT INTO course_enrollments (student_id, course_id, enrollment_date)
VALUES (1, 101, '2026-07-12');

-- Valid: Student 1 enrolls in Course 102 (student_id repeats, but the pair is unique)
INSERT INTO course_enrollments (student_id, course_id, enrollment_date)
VALUES (1, 102, '2026-07-12');

-- Valid: Student 2 enrolls in Course 101 (course_id repeats, but the pair is unique)
INSERT INTO course_enrollments (student_id, course_id, enrollment_date)
VALUES (2, 101, '2026-07-12');
```

The database accepts all three entries. However, if we try to enroll Student 1 in Course 101 again:

```sql
INSERT INTO course_enrollments (student_id, course_id, enrollment_date)
VALUES (1, 101, '2026-07-13');
-- Result: ERROR: duplicate key value violates unique constraint "course_enrollments_pkey"
```
The database engine rejects the insert because the combination `(1, 101)` already exists.

---

## Summary
-   A **Composite Key** is a primary key composed of multiple columns.
-   It guarantees that the **combination** of columns is unique and none of the columns are `NULL`.
-   Composite keys are heavily used in **junction tables** to resolve Many-to-Many relationships and prevent duplicate links.
-   While composite keys preserve relational integrity, they add complexity to Java framework mappings and foreign key configurations in child tables.

---

## Additional Resources
-   [PostgreSQL Composite Key Constraints Documentation](https://www.postgresql.org/docs/current/ddl-constraints.html)
-   [Composite Keys vs. Surrogate Keys in Relational Modeling](https://www.oracletutorial.com/oracle-database-design/oracle-composite-primary-key/)
-   [Baeldung: Hibernate Composite Primary Keys in Java](https://www.baeldung.com/jpa-composite-primary-keys)
