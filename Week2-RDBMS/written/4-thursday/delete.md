# SQL DELETE Syntax and Soft Deletes

## Learning Objectives
- Construct SQL statements to remove table records using `DELETE`.
- Formulate precise filter criteria using `WHERE` to isolate target deletions.
- Explain the Soft Delete design pattern and implement it in relational tables.
- Evaluate the trade-offs between Hard Deletes and Soft Deletes in enterprise environments.

---

## Why This Matters
On Tuesday, we discussed how data is a company's most valuable asset. When a user clicks "Delete Account" on a website, a junior developer might write a script that executes a physical delete command (`DELETE FROM users WHERE id = 5`) on disk. This is known as a **Hard Delete**.

However, in professional enterprise settings, physical deletions are rarely used. 
-   If you physically delete a user, what happens to their historical purchase history? The accounting logs will lose their linkages, corrupting tax records.
-   If a user accidentally deletes a project, how do you recover it? If it was hard deleted, it is gone from the database completely.
-   How do you audit system access if your logs are deleted?

To solve these problems, software architects use the **Soft Delete** pattern. By learning how to implement soft deletes, you preserve audit history, protect historical reports from corruption, and enable instant data recovery.

---

## The Concept

### 1. Hard Delete (Physical Deletion)
A physical deletion uses the SQL `DELETE` statement. It instructs the database engine to search for the record, remove it from the data pages on disk, and update the table indexes.
-   *Syntax:* `DELETE FROM table_name WHERE condition;`
-   *The Risk:* If you omit the `WHERE` clause, you delete all records from the table.

### 2. The Soft Delete Pattern (Logical Deletion)
Instead of deleting a row, a soft delete marks the row as "deleted" while keeping the record intact on the disk. 

#### How to Implement Soft Deletes:
1.  **Add a Flag Column:** Add a boolean column (e.g., `is_deleted BOOLEAN DEFAULT FALSE`) or a timestamp column (e.g., `deleted_at TIMESTAMPTZ`) to the table schema.
2.  **Delete by Updating:** When a delete command is requested, execute an `UPDATE` statement that sets the flag to `TRUE` (or sets the timestamp to `NOW()`).
3.  **Filter Read Queries:** Update all read queries (`SELECT`) to filter out marked rows (e.g., `WHERE is_deleted = FALSE`).

### 3. Hard Delete vs. Soft Delete Comparison

| Trade-off | Hard Delete (`DELETE`) | Soft Delete (`is_deleted = TRUE`) |
|---|---|---|
| **Data Recovery** | Impossible without database backup restoration. | Instant (just set the flag back to `FALSE`). |
| **Audit Trails** | Violated. Historical records are destroyed. | Maintained. All historical linkages remain intact. |
| **Storage Impact** | Reclaims disk space. | Table size keeps growing over time. |
| **Query Complexity** | Simple. | Every query must remember to filter for active rows. |

---

## Code Example: Hard Deletes vs. Soft Deletes

Let's look at a schema representing customer profiles.

```sql
CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    
    -- Soft Delete columns
    is_deleted BOOLEAN DEFAULT FALSE,
    deleted_at TIMESTAMPTZ
);

INSERT INTO customers VALUES (1, 'Alice Smith', 'alice@email.com');
```

---

### Scenario A: Hard Delete (Physical Removal)
```sql
-- Physically deletes Alice from disk
DELETE FROM customers WHERE customer_id = 1;
```

---

### Scenario B: Soft Delete (Logical Removal)
Let's reset Alice's row and perform a soft delete instead:

```sql
-- Step 1: Execute the Soft Delete via UPDATE
UPDATE customers
SET is_deleted = TRUE,
    deleted_at = NOW()
WHERE customer_id = 1;

-- Step 2: Querying Active Customers (Standard Read)
-- All standard application reads must include this filter
SELECT name, email 
FROM customers 
WHERE is_deleted = FALSE;
-- Result: ZERO ROWS (Alice is hidden from the user interface)

-- Step 3: Querying for Admin Audits
-- Administrators can still see historical accounts
SELECT name, email, deleted_at 
FROM customers 
WHERE is_deleted = TRUE;
-- Result: Alice Smith (deleted_at: 2026-07-12 23:48:00)
```

---

## Summary
-   **`DELETE`** physically removes records from database storage disks.
-   The **`WHERE` clause** is critical; omitting it wipes all rows in the table.
-   **Soft Deletes** use a flag column (like `is_deleted`) to hide records from application read queries while preserving them on disk.
-   Soft Deletes are standard practice in enterprise environments to preserve audit history and enable easy data recovery.

---

## Additional Resources
-   [PostgreSQL DELETE Command Reference](https://www.postgresql.org/docs/current/sql-delete.html)
-   [Soft Delete Design Patterns - Baeldung](https://www.baeldung.com/spring-jpa-soft-delete)
-   [Database Audit Logging Best Practices](https://cheatsheetseries.owasp.org/cheatsheets/Logging_Vocabulary_Cheat_Sheet.html)
