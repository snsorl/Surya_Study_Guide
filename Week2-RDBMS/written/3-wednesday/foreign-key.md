# Foreign Keys

## Learning Objectives
- Define the purpose and function of a Foreign Key (FK) in relational databases.
- Distinguish between Parent (referenced) and Child (referencing) tables.
- Declare foreign keys in SQL using both column-level and table-level syntax.
- Analyze the behavior of relational database writes when a foreign key constraint is violated.

---

## Why This Matters
In a relational database, data is spread across multiple tables to prevent duplication and preserve structure. However, this separation requires a mechanism to link those tables back together. For example, if you have an `orders` table and a `customers` table, you need a way to track which customer placed which order.

If you simply write a text column in the `orders` table containing the customer's ID (e.g., `customer_id = 99`), but do not enforce a formal relationship, you run a massive risk: someone could delete Customer 99 from the `customers` table, or write an order referencing Customer 1000 (who does not exist). 

This results in **orphaned records**—orders that belong to nobody.

A **Foreign Key** is a database constraint that prevents this. By establishing a formal, secure connection between tables, the database guarantees that child records can never point to non-existent parent records.

---

## The Concept

### 1. What is a Foreign Key?
A **Foreign Key** is a column (or group of columns) in one table that references the primary key (or a unique key) of another table. 

-   **Parent Table (Referenced Table):** The table containing the source unique identifier (usually the Primary Key) that is being referenced.
-   **Child Table (Referencing Table):** The table containing the foreign key column that points to the parent table.

```
  PARENT TABLE: CUSTOMERS
  +-------------+------------+
  | customer_id | name       |  <-- primary key (referenced)
  +-------------+------------+
  | 1           | Alice      |
  +-------------+------------+
        ^
        | (Foreign Key connection)
        |
  CHILD TABLE: ORDERS
  +-------------+-------------+------------+
  | order_id    | customer_id | order_date |  <-- foreign key (referencing)
  +-------------+-------------+------------+
  | 101         | 1           | 2026-07-12 |
  +-------------+-------------+------------+
```

### 2. Standard Constraints Enforced by Foreign Keys
-   **Insert/Update Guardrails:** You cannot insert a row into a child table with a foreign key value that does not exist in the parent table.
-   **Delete Guardrails:** You cannot delete a row from a parent table if there are active rows in the child table referencing it (unless you configure cascade options, which we cover on Thursday).

---

## Code Example

Let's look at how to declare foreign keys in PostgreSQL. We will create a parent table `departments` and a child table `employees`.

### Parent Table Definition
```sql
CREATE TABLE departments (
    dept_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Insert a valid parent record
INSERT INTO departments (dept_id, name) VALUES (10, 'Engineering');
```

---

### Child Table Definition
We can declare the foreign key in two ways:

#### Option A: Column-Level Syntax (Simple)
Useful when the foreign key is a single column.
```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    -- Column-level foreign key declaration
    dept_id INT REFERENCES departments(dept_id)
);
```

#### Option B: Table-Level Syntax (Explicit)
Preferred for composite keys or when you want to name the constraint explicitly for easier debugging.
```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    dept_id INT,
    
    -- Table-level foreign key declaration with custom name
    CONSTRAINT fk_employee_department 
        FOREIGN KEY (dept_id) 
        REFERENCES departments(dept_id)
);
```

---

### Verifying the Constraints

Let's attempt to insert data:

#### 1. Successful Insertion (Valid Reference)
```sql
-- Valid: Department 10 exists in the departments table
INSERT INTO employees (emp_id, name, dept_id) 
VALUES (1, 'Alice Smith', 10);
-- Result: SUCCESS
```

#### 2. Failed Insertion (Invalid Reference)
```sql
-- Invalid: Department 99 does not exist in the departments table
INSERT INTO employees (emp_id, name, dept_id) 
VALUES (2, 'Bob Jones', 99);
-- Result: ERROR: insert or update on table "employees" violates foreign key constraint "fk_employee_department"
-- Detail: Key (dept_id)=(99) is not present in table "departments".
```

#### 3. Failed Deletion of Parent
If we try to delete Department 10 from the parent table while Alice is still referencing it:
```sql
DELETE FROM departments WHERE dept_id = 10;
-- Result: ERROR: update or delete on table "departments" violates foreign key constraint "fk_employee_department" on table "employees"
-- Detail: Key (dept_id)=(10) is still referenced from table "employees".
```
The database blocks the deletion, protecting our referential integrity.

---

## Summary
-   A **Foreign Key (FK)** links a column in a child table to a primary key in a parent table.
-   The **Child Table** references the **Parent Table** using the `REFERENCES` clause.
-   Foreign keys prevent **orphaned records** by rejecting insertions of non-existent parent IDs.
-   By default, the database prevents the deletion of a parent record if active child records reference it.

---

## Additional Resources
-   [PostgreSQL Foreign Key Documentation](https://www.postgresql.org/docs/current/ddl-constraints.html#DDL-CONSTRAINTS-FOREIGN-KEYS)
-   [Relational Databases: Parent-Child Relationships](https://www.studytonight.com/dbms/parent-child-relationship-in-database)
-   [Designing Referential Integrity Schemas](https://www.mysqltutorial.org/mysql-foreign-key/)
