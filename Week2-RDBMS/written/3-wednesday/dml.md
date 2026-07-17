# DML: Data Manipulation Language

## Learning Objectives
- Define the role of Data Manipulation Language (DML) in managing table records.
- Explain the syntax and behavior of the core DML commands (`INSERT`, `UPDATE`, `DELETE`).
- Explain the critical importance of the `WHERE` clause in restricting database updates and deletes.
- Describe how DML commands execute within a transaction context.

---

## Why This Matters
If DDL represents the stage, DML represents the actors. While DDL defines the static tables, columns, and relationships of your schema, your application spends 99% of its runtime executing **DML** statements to modify the values inside those structures.

Every time a user registers an account (inserting a row), changes their billing address (updating a row), or clears an item from their shopping cart (deleting a row), your backend Java code sends DML commands to the database. 

Understanding how to write precise, safe DML statements is essential. A single poorly formatted update command without proper filtering can overwrite every single record in your database, causing catastrophic data loss.

---

## The Concept

### 1. What is DML?
**Data Manipulation Language (DML)** is the subset of SQL used to manage data values stored inside relational tables. DML commands do not alter the table structures or schemas; they alter the records stored within those schemas.

### 2. Core DML Commands

-   **`INSERT`**: Creates new rows in a table.
-   **`UPDATE`**: Modifies data values in existing rows.
-   **`DELETE`**: Removes rows from a table.

### 3. The Power and Danger of the WHERE Clause
Both the `UPDATE` and `DELETE` commands rely heavily on the **`WHERE` clause**. The `WHERE` clause defines the conditional criteria that a row must meet to be targeted by the operation.

If you omit the `WHERE` clause:
-   An `UPDATE` statement will apply the new value to **every single row** in the table.
-   A `DELETE` statement will wipe **all records** from the table.

In enterprise settings, developers must exercise extreme caution when drafting and executing updates or deletes.

### 4. Transaction Context
Unlike structure modifications (DDL), DML operations execute within a **transaction context**. When you execute an `INSERT`, `UPDATE`, or `DELETE`, the changes are temporary. The modifications are stored in database buffer logs and are visible only to your current database connection session.

The changes are not saved permanently to the physical storage disk until a Transaction Control Language (TCL) command (like `COMMIT`) is executed. If an error occurs, you can discard all changes using `ROLLBACK`.

---

## Code Example: Data Lifecycle Script

Let's look at a complete data manipulation scenario. We will use a `customers` table.

```sql
CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    membership_tier VARCHAR(20) DEFAULT 'BRONZE'
);
```

---

### 1. Inserting Records (INSERT)
We insert records by specifying columns and values.

```sql
-- Single-row insert
INSERT INTO customers (customer_id, name, membership_tier)
VALUES (1, 'Alice Smith', 'GOLD');

-- Multi-row insert (PostgreSQL standard)
INSERT INTO customers (customer_id, name, membership_tier)
VALUES 
(2, 'Bob Jones', 'SILVER'),
(3, 'Charlie Brown', DEFAULT);
```

---

### 2. Modifying Records (UPDATE)
Suppose Bob Jones upgrades his account to Gold tier. We must filter specifically for Bob.

```sql
-- Safe update: Targets only Bob
UPDATE customers
SET membership_tier = 'GOLD'
WHERE customer_id = 2;

-- DANGER: Omit WHERE. This sets ALL customers to GOLD!
-- UPDATE customers SET membership_tier = 'GOLD';
```

---

### 3. Deleting Records (DELETE)
Suppose Charlie Brown closes his account. We delete only Charlie.

```sql
-- Safe delete: Targets only Charlie
DELETE FROM customers
WHERE customer_id = 3;

-- DANGER: Omit WHERE. This wipes the entire customer database!
-- DELETE FROM customers;
```

---

## Summary
-   **DML (Data Manipulation Language)** inserts, updates, and deletes row-level data.
-   Core commands are **`INSERT`**, **`UPDATE`**, and **`DELETE`**.
-   The **`WHERE` clause** is critical for `UPDATE` and `DELETE` statements. Omitting it applies changes to the entire table.
-   DML statements run in a **transaction context**, meaning changes are temporary until committed.

---

## Additional Resources
-   [PostgreSQL INSERT Tutorial](https://www.postgresql.org/docs/current/dml-insert.html)
-   [PostgreSQL UPDATE Tutorial](https://www.postgresql.org/docs/current/dml-update.html)
-   [PostgreSQL DELETE Tutorial](https://www.postgresql.org/docs/current/dml-delete.html)
