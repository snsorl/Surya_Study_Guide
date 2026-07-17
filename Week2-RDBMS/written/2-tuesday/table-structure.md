# Relational Table Structure and NULL Semantics

## Learning Objectives
- Describe the structural elements of a relational table (schemas, columns, rows).
- Define the semantic meaning of `NULL` in relational databases.
- Analyze the behavior of three-valued logic (True, False, Unknown) in SQL conditional statements.
- Construct SQL queries using `IS NULL` and `IS NOT NULL` operators.
- Explain the risks of using standard equality operators (`=`) with `NULL` values.

---

## Why This Matters
In Java, we are all too familiar with the dreaded `NullPointerException` (NPE). If a variable holds a reference that points to nothing, trying to invoke a method on it crashes the application. 

In relational databases, `NULL` behaves differently but is equally dangerous if misunderstood. In SQL, `NULL` does not mean zero, it does not mean an empty string, and it does not represent a pointer. It represents a state: **unknown or missing information**.

If you treat `NULL` like a regular value (for instance, trying to run a comparison like `WHERE phone_number = NULL`), the database will not throw a runtime crash, but it will return zero rows—silently failing to find the data. Understanding the mathematical mechanics of `NULL` and three-valued logic is essential to writing accurate SQL queries and avoiding data-retrieval bugs.

---

## The Concept

### 1. Structural Components of a Table
A relational database table is structured into distinct layers:
-   **Schema:** The metadata definition that declares the table name, the names of all columns, and their respective data types and constraints.
-   **Columns (Attributes):** The vertical columns that define the shape of the data. Every cell in a column must conform to the column's data type.
-   **Rows (Tuples / Records):** The horizontal entries containing the actual values. Each row represents a single entity instance (e.g., one specific customer).

### 2. NULL Semantics
In relational databases, `NULL` is a special marker used to indicate that a data value does not exist in the database. 

It is used in three scenarios:
1.  **Unknown value:** We know the attribute exists, but we do not know its value yet (e.g., a customer's middle name).
2.  **Inapplicable value:** The attribute does not apply to this record (e.g., a "business_tax_id" for a personal customer account).
3.  **Missing value:** The information was skipped or is unavailable.

### 3. Three-Valued Logic (3VL)
In Java, boolean logic is binary: a statement is either `true` or `false`. 
Because SQL has to handle `NULL` (unknown) states, it uses **Three-Valued Logic**. Every condition evaluates to one of three states:
-   `TRUE`
-   `FALSE`
-   `UNKNOWN`

Any comparison between a regular value and a `NULL` always evaluates to `UNKNOWN`. Even comparing `NULL` to `NULL` results in `UNKNOWN`. 
-   `5 = 5` is `TRUE`.
-   `5 = NULL` is `UNKNOWN`.
-   `NULL = NULL` is `UNKNOWN`.

Because SQL query filter clauses (`WHERE`) only return rows where the condition evaluates strictly to `TRUE`, any check using `= NULL` will filter out all records.

---

## Code Example

Let's illustrate these mechanics with a SQL query exercise.

### Defining the Schema and Inserting Data
We will create a table representing student contacts. Note that some students do not have middle names or phone numbers.

```sql
CREATE TABLE students (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50),           -- Can be NULL
    phone_number VARCHAR(20)          -- Can be NULL
);

INSERT INTO students (student_id, first_name, middle_name, phone_number)
VALUES 
(1, 'John', 'Edward', '555-0199'),
(2, 'Sara', NULL, '555-0211'),
(3, 'Alex', 'James', NULL),
(4, 'Emma', NULL, NULL);
```

---

### Query Pitfalls: The Mistake
Suppose we want to find all students who do not have a phone number. A beginner might write:

```sql
SELECT first_name FROM students WHERE phone_number = NULL;
-- Result: ZERO ROWS RETURNED
```
Why? Because `phone_number = NULL` evaluates to `UNKNOWN`. The database rejects the row because it is not `TRUE`.

---

### The Correct Approach: Using `IS NULL`
To check for unknown values, you must use the specialized SQL operators **`IS NULL`** and **`IS NOT NULL`**.

```sql
-- Find students without phone numbers
SELECT first_name FROM students WHERE phone_number IS NULL;
-- Result: Alex, Emma

-- Find students with middle names
SELECT first_name FROM students WHERE middle_name IS NOT NULL;
-- Result: John, Alex
```

---

### Arithmetic and Aggregations with NULL
If you perform arithmetic operations on a column containing `NULL`, the result is always `NULL`:
-   `100 + NULL` = `NULL`.
-   `'Hello ' || NULL` = `NULL`.

*Aggregate functions* (like `SUM` or `AVG`) behave differently: they ignore `NULL` values. If you calculate the average of three scores (`100`, `80`, and `NULL`), the database calculates `(100 + 80) / 2 = 90`, ignoring the missing row.

---

## Summary
-   A **Table** is defined by its metadata **Schema** and contains vertical **Columns** and horizontal **Rows**.
-   **`NULL`** represents missing, unknown, or inapplicable data. It is not equivalent to an empty string or zero.
-   SQL uses **Three-Valued Logic (3VL)**: conditions evaluate to True, False, or Unknown.
-   Any comparison using regular operators (`=`, `<>`) with `NULL` returns `UNKNOWN`.
-   You must use **`IS NULL`** and **`IS NOT NULL`** to search for empty cells.

---

## Additional Resources
-   [PostgreSQL Null Value Documentation](https://www.postgresql.org/docs/current/functions-comparison.html)
-   [Three-Valued Logic in SQL - Wikipedia](https://en.wikipedia.org/wiki/Three-valued_logic#SQL)
-   [SQL Null Semantics Explained](https://use-the-index-luke.com/)
