# DEFAULT Constraints

## Learning Objectives
- Define the purpose of the `DEFAULT` constraint in relational databases.
- Differentiate between Literal defaults and Dynamic Expression defaults.
- Utilize database functions (like `NOW()` and `gen_random_uuid()`) to auto-populate record metadata.
- Predict column values during insertions based on omission, explicit `DEFAULT` flags, and explicit `NULL` assignments.

---

## Why This Matters
When designing backend database operations, you want to write insertion queries that are as clean and concise as possible. If a table has twenty columns, forcing your Java application to supply values for all twenty fields in every single insert statement creates bloated code and wastes network resources.

Many columns store system metadata that should be managed automatically:
-   A record's creation timestamp (`created_at`).
-   A secure identifier like a UUID (`record_id`).
-   A starting status code (like setting a new order's status to `'PENDING'`).

The **`DEFAULT` constraint** handles this automatically. By declaring standard default fallback values directly in the schema, you simplify insertion logic, automate metadata logging, and ensure database consistency when columns are omitted.

---

## The Concept

### 1. What is a DEFAULT Constraint?
A **`DEFAULT` constraint** specifies a value to be automatically assigned to a column when an insertion operation does not provide a value for that column.

### 2. Literal Defaults vs. Expression Defaults
PostgreSQL supports two categories of default values:

#### A. Literal Defaults
Static values that never change.
-   *Examples:* `status VARCHAR(20) DEFAULT 'ACTIVE'`, `retry_attempts INT DEFAULT 0`, `is_verified BOOLEAN DEFAULT FALSE`.

#### B. Dynamic Expression Defaults
Values calculated dynamically by the database engine at the exact millisecond the write operation occurs.
-   *Examples:*
    -   `created_at TIMESTAMPTZ DEFAULT NOW()`: Sets the timestamp to the current server date and time.
    -   `record_uuid UUID DEFAULT gen_random_uuid()`: Auto-generates a secure, 36-character random identifier.

### 3. Insert Behaviors
How does the database populate columns with default constraints during an `INSERT` statement?

1.  **Column Omitted:** If you exclude the column from the column list, the database automatically applies the default. (Standard choice).
2.  **Explicit DEFAULT Keyword:** If you list the column but use the SQL keyword `DEFAULT` as the value, the database applies the default.
3.  **Explicit NULL Value:** If you list the column but pass `NULL`, the database **overwrites** the default and stores `NULL` (assuming the column does not have a `NOT NULL` constraint).

---

## Code Example: Metadata Automation

Let's write a SQL schema showing literal and expression defaults, and then test the three insert behaviors.

### Schema Definition
```sql
CREATE TABLE client_registrations (
    registration_id UUID DEFAULT gen_random_uuid() PRIMARY KEY, -- Dynamic UUID
    client_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) DEFAULT 'INACTIVE',                      -- Literal string default
    registered_at TIMESTAMPTZ DEFAULT NOW()                     -- Dynamic timestamp
);
```

---

### Verifying the Insert Behaviors

#### Behavior 1: Column Omission (Standard default application)
We insert only the `client_name`. The database auto-generates the UUID, status, and timestamp:

```sql
INSERT INTO client_registrations (client_name)
VALUES ('Alpha Corp');

SELECT * FROM client_registrations;
```
*Output:*
```
registration_id                      | client_name | status   | registered_at
-------------------------------------+-------------+----------+-------------------------------
d3b07384-d113-4956-a548-a0e28f321487 | Alpha Corp  | INACTIVE | 2026-07-12 23:47:00-04
```

---

#### Behavior 2: Explicit DEFAULT Keyword
We explicitly write `DEFAULT` in our insert query:

```sql
INSERT INTO client_registrations (client_name, status)
VALUES ('Beta LLC', DEFAULT);
```
*Output:* The status column is set to `'INACTIVE'` automatically.

---

#### Behavior 3: Explicit NULL Value
We pass `NULL` to the status column:

```sql
INSERT INTO client_registrations (client_name, status)
VALUES ('Gamma Inc', NULL);
```
*Output:*
```
registration_id                      | client_name | status   | registered_at
-------------------------------------+-------------+----------+-------------------------------
c7e11244-b011-4777-a999-c0c19f220011 | Gamma Inc   | NULL     | 2026-07-12 23:47:05-04
```
Notice that the default was **not** applied. The database stored `NULL` because we explicitly instructed it to do so.

---

## Summary
-   The **`DEFAULT` constraint** populates empty columns automatically on insertion.
-   **Literal defaults** store static values (like `'INACTIVE'`); **expression defaults** resolve database functions dynamically (like `NOW()` or `gen_random_uuid()`).
-   If you omit a column during an insert, the default is applied.
-   Passing **`NULL`** explicitly overrides the default and stores `NULL` on disk.

---

## Additional Resources
-   [PostgreSQL DEFAULT Constraints Documentation](https://www.postgresql.org/docs/current/ddl-constraints.html#id-1.5.4.12.9)
-   [UUID Generation in PostgreSQL](https://www.postgresql.org/docs/current/uuid-ossp.html)
-   [W3Schools: SQL DEFAULT Constraint](https://www.w3schools.com/sql/sql_default.asp)
