# DDL: Data Definition Language

## Learning Objectives
- Define the role of Data Definition Language (DDL) in managing database schemas.
- Explain the syntax and behavior of the core DDL commands (`CREATE`, `ALTER`, `DROP`, `TRUNCATE`).
- Describe the typical lifecycle of a database schema in production.
- Analyze the consequences and risks of executing schema modifications on live systems.

---

## Why This Matters
When building applications, the code you write will inevitably change. You will add features, merge teams, or reorganize how data is processed. These software updates often require changes to how data is structured on disk: you might need to add a `middle_name` column to your users table, remove an obsolete status column, or create a brand-new table to track customer loyalty rewards.

In the industry, managing these changes is known as **Schema Lifecycle Management**. 

Because **DDL** commands physically alter the database structure, running them incorrectly on a production server can be catastrophic. Dropping a column by mistake deletes all data in that column instantly. Understanding how to write and run DDL statements safely is a critical skill for full-stack developers participating in release deployments.

---

## The Concept

### 1. What is DDL?
**Data Definition Language (DDL)** is the subset of SQL used to define, modify, and destroy the structural catalog of the database. DDL does not interact with row-level data values; it defines the containers (tables, schemas, views) that hold those values.

### 2. Core DDL Commands

-   **`CREATE`**: Declares a new object in the database (e.g., `CREATE TABLE`, `CREATE DATABASE`, `CREATE INDEX`).
-   **`ALTER`**: Modifies an existing database structure (e.g., adding a column to a table, changing a column's data type, or renaming a constraint).
-   **`DROP`**: Permanently deletes a structural container *and all data stored inside it* from the database catalog (e.g., `DROP TABLE`).
-   **`TRUNCATE`**: Instantly empties a table of all its records while keeping the table structure intact. (We cover this in detail on Thursday).

### 3. Execution Mechanics & Transaction Behavior
In many database engines (such as Oracle or MySQL), DDL commands are **implicitly committed**. This means that when you run a DDL command, the database engine immediately saves the change to disk, bypassing any transaction safety boundaries. You cannot easily undo a `DROP TABLE` command.

*Note:* PostgreSQL is a powerful exception. It supports transactional DDL, allowing you to run `CREATE` or `ALTER` statements within a transaction and rollback if an error occurs.

---

## Code Example: Schema Lifecycle Script

Let's walk through a script illustrating the complete lifecycle of a `accounts` table.

### 1. Creating a Table (CREATE)
```sql
CREATE TABLE accounts (
    account_id INT PRIMARY KEY,
    owner_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 2. Modifying the Schema (ALTER)
As business requirements evolve, we need to add a `status` column and rename the owner column.

```sql
-- Add a new column
ALTER TABLE accounts ADD COLUMN status VARCHAR(20) DEFAULT 'ACTIVE';

-- Rename an existing column
ALTER TABLE accounts RENAME COLUMN owner_name TO full_name;
```

---

### 3. Clearing Data vs. Destroying Containers (TRUNCATE & DROP)
Suppose we are running a cleanup script on a test database.

```sql
-- Empties the table rows immediately, but the table schema remains
TRUNCATE TABLE accounts;

-- Destroys the table schema, indexes, and columns completely
DROP TABLE accounts;
```
If you run `SELECT * FROM accounts` after a `TRUNCATE`, you will receive an empty table. If you run it after a `DROP`, the database will throw an error: `relation "accounts" does not exist`.

---

## Summary
-   **DDL (Data Definition Language)** defines, modifies, and deletes database structural containers (tables, indexes).
-   Core commands are **`CREATE`** (new structures), **`ALTER`** (modifications), **`DROP`** (total destruction), and **`TRUNCATE`** (data clearing).
-   DDL commands interact with the **schema metadata**, not row-level values.
-   Executing DDL changes in production requires careful planning, as structural deletes (`DROP`) are immediate and destructive.

---

## Additional Resources
-   [PostgreSQL DDL Tutorial](https://www.postgresql.org/docs/current/ddl.html)
-   [Relational Schema Refactoring Patterns](https://refactoringdatabases.com/)
-   [Baeldung: Guide to DDL vs DML](https://www.baeldung.com/cs/ddl-vs-dml-sql)
