# Cascading Referential Actions

## Learning Objectives
- Explain the role of cascading referential actions in maintaining referential integrity.
- Implement `ON DELETE CASCADE` and `ON UPDATE CASCADE` clauses in SQL schemas.
- Identify alternative referential actions, including `RESTRICT`, `NO ACTION`, `SET NULL`, and `SET DEFAULT`.
- Analyze the performance and data loss risks of cascading deletes in production enterprise systems.

---

## Why This Matters
On Wednesday, we learned that referential integrity protects databases from orphaned records by blocking deletions. For example, if you try to delete a customer who still has orders in the `orders` table, the database rejects the deletion.

However, in real-world applications, you need flexible delete options. 
-   If a user deletes their profile, you might want to automatically delete all their private message logs (`ON DELETE CASCADE`).
-   If you delete a product category (like "Summer Deals"), you do not want to delete the products inside it; you just want to set their category category column to `NULL` (`ON DELETE SET NULL`) so they can be re-categorized.

Understanding cascading referential actions allows you to automate these cleanup tasks at the database level. However, using them blindly is highly dangerous: a single accidental delete command can trigger a chain reaction that deletes millions of rows across multiple tables in seconds.

---

## The Concept

### 1. What are Referential Actions?
When you define a Foreign Key, you can specify what action the database engine should take when a referenced parent row is updated or deleted. These actions are declared using the **`ON DELETE`** and **`ON UPDATE`** clauses.

### 2. Available Referential Actions

-   **`CASCADE`**: Propagates the action down to the child rows.
    -   *ON DELETE CASCADE:* If the parent row is deleted, all referencing child rows are deleted automatically.
    -   *ON UPDATE CASCADE:* If the parent row's primary key value changes, the foreign key values in all child rows are updated automatically.
-   **`SET NULL`**: If the parent row is deleted or updated, the foreign key columns in all referencing child rows are set to `NULL`. (The child row is preserved, but the link is broken).
-   **`SET DEFAULT`**: If the parent row is deleted or updated, the foreign key columns in the child rows are set to their declared `DEFAULT` value.
-   **`RESTRICT` / `NO ACTION`**: The default behavior. Any deletion or update on a parent row is blocked if child rows reference it.

### 3. Cascading Risks in Production
-   **Accidental Bulk Data Loss:** If Table A cascades to Table B, which cascades to Table C, running a simple `DELETE FROM table_a WHERE id = 1` might delete one row in Table A, but silently delete 50,000 rows in Table C.
-   **Locking & Performance Bottlenecks:** When a cascading delete triggers, the database must write delete logs and lock all affected tables. This can block other application transactions, causing performance timeouts.
-   **Loss of Audit History:** In financial systems, you must never delete records. Using `ON DELETE CASCADE` on transaction tables violates accounting compliance laws.

---

## Code Example: Cascading vs. Setting Null

Let's write a SQL schema comparing `CASCADE` and `SET NULL` behaviors.

### Setup Tables
```sql
CREATE TABLE parent_teams (
    team_id INT PRIMARY KEY,
    team_name VARCHAR(50) NOT NULL
);

-- Seed parent teams
INSERT INTO parent_teams VALUES (1, 'Development'), (2, 'Marketing');
```

---

### Scenario A: ON DELETE CASCADE
We create a `team_projects` table. If a team is deleted, their projects are useless, so we delete them.

```sql
CREATE TABLE team_projects (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    team_id INT REFERENCES parent_teams(team_id) ON DELETE CASCADE -- Cascade Action
);

INSERT INTO team_projects VALUES (101, 'Mobile App', 1), (102, 'Web Portal', 1);
```

If we delete Team 1:
```sql
DELETE FROM parent_teams WHERE team_id = 1;

SELECT * FROM team_projects;
-- Result: ZERO ROWS. The mobile app and web portal projects were deleted automatically.
```

---

### Scenario B: ON DELETE SET NULL
We create an `employees` table. If a team is deleted, we do not want to fire the employees. We just want to clear their team assignment.

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    team_id INT REFERENCES parent_teams(team_id) ON DELETE SET NULL -- Set Null Action
);

INSERT INTO employees VALUES (1, 'Alice Smith', 2);
```

If we delete Team 2:
```sql
DELETE FROM parent_teams WHERE team_id = 2;

SELECT * FROM employees;
```
*Output:*
```
emp_id | name        | team_id
-------|-------------|---------
1      | Alice Smith | NULL
```
Alice's row was preserved, but her `team_id` was cleared to `NULL`.

---

## Summary
-   **Referential actions** define what happens to child rows when a referenced parent row is updated or deleted.
-   **`ON DELETE CASCADE`** deletes child rows automatically; **`ON UPDATE CASCADE`** propagates primary key updates.
-   Alternatives include **`RESTRICT`** (default block), **`SET NULL`** (clears parent reference), and **`SET DEFAULT`** (applies default fallback).
-   Cascading deletes present a high risk of **accidental data loss** and **performance locking** in production.

---

## Additional Resources
-   [PostgreSQL Foreign Keys Referential Actions](https://www.postgresql.org/docs/current/ddl-constraints.html#DDL-CONSTRAINTS-FOREIGN-KEYS)
-   [SQL Referential Integrity Actions - GeeksforGeeks](https://www.geeksforgeeks.org/sql-on-delete-cascade-constraint/)
-   [Database Design Pitfalls: Dangerous Cascades](https://www.db-book.com/)
