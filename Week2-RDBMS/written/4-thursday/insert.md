# SQL INSERT Syntax and Conflict Handling

## Learning Objectives
- Formulate SQL queries to insert single and multiple rows into tables.
- Implement the `INSERT INTO ... SELECT` pattern to duplicate or migrate data between tables.
- Resolve insertion key conflicts (UPSERT) using the `ON CONFLICT` clause in PostgreSQL.
- Compare the performance of multi-row insertions with sequence-based insertions.

---

## Why This Matters
As your application scales, the volume of data write operations increases. 
-   When a user signs up, you insert a single row.
-   When importing a CSV log sheet, you might need to insert 10,000 records.
-   When syncing data tables daily, you need to write records only if they do not exist, or update them if they have changed (a pattern known as **UPSERT**).

If you write simple, single-row insert queries inside a Java loop to process bulk data imports, you force the application to make thousands of network round-trips to the database server, leading to slow processing times and system timeouts. Furthermore, if your import query encounters a duplicate record, the entire batch will crash unless you implement database-level conflict handling.

Mastering advanced `INSERT` syntax and conflict resolution allows you to write efficient, bulletproof data ingestion services.

---

## The Concept

### 1. Single-Row vs. Multi-Row Insertion
-   **Single-Row:** The standard insert syntax. Inserts one tuple at a time.
-   **Multi-Row (Bulk Insertion):** You specify the column list once, followed by a comma-separated list of value tuples. This is significantly faster than single inserts because the database parses the query only once and executes the writes in a single batch.

### 2. INSERT INTO ... SELECT
This pattern allows you to query data from one or more tables and insert the results directly into a target table in a single operation. It is commonly used for data migration, archiving historical data, or populating reporting tables.

### 3. Conflict Handling (UPSERT)
When inserting records into a table with a `PRIMARY KEY` or `UNIQUE` constraint, if a row already exists with that key value, the database throws an error and aborts the insert. 

In PostgreSQL, you can bypass this error and handle the conflict gracefully using the **`ON CONFLICT`** clause:
-   **`ON CONFLICT (column) DO NOTHING`**: Ignores the insertion of the conflicting row without throwing an error.
-   **`ON CONFLICT (column) DO UPDATE SET ...`**: Converts the insert into an update command for that specific row. The keyword **`EXCLUDED`** is used to refer to the values you originally tried to insert.

---

## Code Example: Data Ingestion and UPSERTs

Let's look at a schema representing active user logins.

```sql
CREATE TABLE user_status (
    username VARCHAR(50) PRIMARY KEY,
    login_count INT DEFAULT 1,
    last_login TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 1. Multi-Row Insertion
Instead of running three queries, we insert multiple users in a single statement:

```sql
INSERT INTO user_status (username, login_count)
VALUES 
('alice_99', 5),
('bob_smith', 12),
('charlie_b', 1);
-- Result: SUCCESS (Three rows inserted in a single round-trip)
```

---

### 2. INSERT INTO ... SELECT
Suppose we have a historical migration table:

```sql
CREATE TABLE archived_users (
    username VARCHAR(50) PRIMARY KEY,
    archived_at TIMESTAMPTZ DEFAULT NOW()
);

-- Copy all users from user_status into archived_users in one step
INSERT INTO archived_users (username)
SELECT username FROM user_status WHERE login_count > 10;
```

---

### 3. Conflict Resolution (UPSERT)
Suppose a user attempts to log in. We want to record this:
-   If the username is new, insert a row with `login_count = 1`.
-   If the username already exists, increment their existing `login_count` by 1 and update their `last_login` timestamp.

```sql
-- Attempt to insert 'alice_99' (who already exists)
INSERT INTO user_status (username, login_count)
VALUES ('alice_99', 1)
ON CONFLICT (username) 
DO UPDATE SET 
    login_count = user_status.login_count + 1,
    last_login = NOW();
```
*Result:* Instead of throwing a primary key violation error, the database updates Alice's existing record, increasing her `login_count` from 5 to 6.

---

## Summary
-   **Multi-row inserts** group multiple value tuples into a single statement, reducing database network round-trips.
-   **`INSERT INTO ... SELECT`** migrates data from queries directly into target tables in one operation.
-   **`ON CONFLICT`** handles unique/primary key violations, supporting **`DO NOTHING`** to ignore duplicates or **`DO UPDATE`** to execute an update instead (UPSERT).
-   Use the **`EXCLUDED`** keyword to access candidate insert values during conflict updates.

---

## Additional Resources
-   [PostgreSQL INSERT Command Reference](https://www.postgresql.org/docs/current/sql-insert.html)
-   [PostgreSQL UPSERT and ON CONFLICT Guide](https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-upsert/)
-   [W3Schools: SQL INSERT INTO SELECT Statement](https://www.w3schools.com/sql/sql_insert_into_select.asp)
