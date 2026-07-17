# Unique Keys

## Learning Objectives
- Define the purpose of the `UNIQUE` key constraint in relational databases.
- Contrast the differences between a `UNIQUE` constraint and a `PRIMARY KEY`.
- Explain how the database engine handles `NULL` values within unique columns.
- Implement composite `UNIQUE` constraints across multiple columns.

---

## Why This Matters
On Tuesday, we established that a primary key uniquely identifies each row in a table. However, in enterprise applications, you often have other columns that also need to be unique, even if they aren't the primary key.

For example, in a user registry:
-   The primary key is a surrogate integer (`user_id`).
-   However, you also want to guarantee that no two users register with the same `email` address.
-   Additionally, you might want to ensure that no two users register with the same `phone_number`, but you want to allow users to skip entering a phone number (leaving it `NULL`).

If you rely on a single primary key, it cannot protect these additional fields from duplication. 

The **`UNIQUE` constraint** solves this. It allows you to protect specific columns (and combinations of columns) from duplicate values while maintaining structural flexibility.

---

## The Concept

### 1. Unique Constraints vs. Primary Keys
While both constraints prevent duplicate values, they have distinct differences:

| Property | Primary Key | Unique Key |
|---|---|---|
| **Count Per Table** | Limit of **exactly one** primary key per table. | You can define **multiple** unique constraints on a single table. |
| **Nullability** | Implicitly **`NOT NULL`**. You cannot have a blank identifier. | By default, unique columns **allow `NULL` values**. |
| **Purpose** | Defines the physical identity of the row. | Enforces business uniqueness rules on optional or secondary fields. |

### 2. UNIQUE Constraints and NULL Values
In standard SQL (including PostgreSQL), **`NULL` represents an unknown value**. 

Because one unknown value is not considered equal to another unknown value (as discussed in Tuesday's NULL Semantics lesson), PostgreSQL allows you to insert **multiple `NULL` values** into a column defined with a `UNIQUE` constraint.
-   If you have a unique `phone_number` column, multiple users can have `NULL` phone numbers.
-   However, as soon as a user inserts a real phone number (like `'555-0199'`), the database will reject any other rows trying to use that same number.

### 3. Composite UNIQUE Constraints
Just like composite primary keys, you can combine multiple columns to form a single unique constraint. The database will guarantee that the **combination** of values across those columns is unique.
-   *Example:* In a seating chart, multiple rows can have Seat 'A', and multiple rows can have Row '10', but only one record can have the combination `(row_number = 10, seat_letter = 'A')`.

---

## Code Example: User Registration Schema

Let's write a SQL schema implementing multiple unique constraints.

### Schema Definition
```sql
CREATE TABLE user_profiles (
    user_id INT PRIMARY KEY,               -- Single primary key
    email VARCHAR(100) UNIQUE NOT NULL,    -- Secondary unique column, must not be null
    phone_number VARCHAR(20) UNIQUE,       -- Optional unique column (allows NULLs)
    office_room INT,
    desk_number INT,
    
    -- Composite unique constraint
    CONSTRAINT uq_office_desk UNIQUE (office_room, desk_number)
);
```

---

### Verifying the Constraints

Let's test our inserts:

#### 1. Valid Setup
```sql
-- Insert User 1
INSERT INTO user_profiles VALUES (1, 'alice@email.com', '555-0101', 101, 1);

-- Valid: User 2 has NULL phone, and shares Alice's room 101 but uses desk 2
INSERT INTO user_profiles VALUES (2, 'bob@email.com', NULL, 101, 2);

-- Valid: User 3 also has NULL phone (PostgreSQL allows multiple NULLs in unique columns)
INSERT INTO user_profiles VALUES (3, 'charlie@email.com', NULL, 102, 1);
```
All three inserts succeed.

---

#### 2. Duplicate Column Violation
User 4 tries to register with Alice's email:

```sql
INSERT INTO user_profiles VALUES (4, 'alice@email.com', '555-0104', 103, 1);
-- Result: ERROR: duplicate key value violates unique constraint "user_profiles_email_key"
```

---

#### 3. Duplicate Composite Violation
User 5 tries to take Alice's desk in Room 101:

```sql
INSERT INTO user_profiles VALUES (5, 'emma@email.com', '555-0105', 101, 1); -- Room 101, Desk 1 is taken by Alice!
-- Result: ERROR: duplicate key value violates unique constraint "uq_office_desk"
```
The database blocks the insert, protecting our desk booking logic.

---

## Summary
-   The **`UNIQUE` constraint** prevents duplicate values in a column or combination of columns.
-   Unlike primary keys, you can have **multiple unique constraints** on a table, and unique columns **allow `NULL` values** by default.
-   PostgreSQL allows **multiple `NULL` values** in a unique column because `NULL` comparisons do not evaluate to equality.
-   **Composite unique constraints** ensure that combinations of column values remain distinct.

---

## Additional Resources
-   [PostgreSQL Unique Constraints Documentation](https://www.postgresql.org/docs/current/ddl-constraints.html#DDL-CONSTRAINTS-UNIQUE-CONSTRAINTS)
-   [W3Schools: SQL UNIQUE Constraint](https://www.w3schools.com/sql/sql_unique.asp)
-   [Database Constraints and Indexes - Detailed Explainer](https://use-the-index-luke.com/)
