# Lab: SQL Sublanguages Practice

## Learning Objectives
- Differentiate between statement operations across DDL, DML, DQL, DCL, and TCL.
- Construct queries to manipulate schema definitions (DDL) and rows (DML).
- Formulate database user transactions and boundary checkpoints (TCL).
- Apply role and privilege grants to tables (DCL).

---

## Setup Instructions
1. Open DBeaver and connect to your local PostgreSQL database.
2. Open a new SQL Console editor.
3. Ensure your `books`, `members`, and `loans` tables from the previous exercise exist.

---

## Step-by-Step Tasks

Write exactly **one distinct SQL statement** for each sublanguage category, executing them progressively:

### Task 1: Data Definition Language (DDL)
Write an `ALTER TABLE` statement to add a new column named `category` (type `VARCHAR(30)`) to the `books` table:

```sql
-- DDL Statement here
```

---

### Task 2: Data Manipulation Language (DML)
Write an `INSERT` statement adding a new record to the `members` table:

```sql
-- DML Statement here
```

---

### Task 3: Data Query Language (DQL)
Write a `SELECT` statement retrieving books, filtering for books written by a specific author:

```sql
-- DQL Statement here
```

---

### Task 4: Transaction Control Language (TCL)
Write a transaction block containing:
1.  A DML change statement.
2.  A `SAVEPOINT` checkpoint named `insert_check`.
3.  A subsequent change.
4.  A rollback execution returning to `insert_check`.
5.  A final commit.

```sql
-- TCL Script here
```

---

### Task 5: Data Control Language (DCL)
Write a statement granting read-only permissions (`SELECT`) on your `books` table to a user named `guest_reader`:

```sql
-- DCL Statement here
```
*Note: In PostgreSQL, if the role 'guest_reader' does not exist, write the code as comments or create the role first: `CREATE ROLE guest_reader;`.*

---

## Definition of Done
-   A single SQL file in your workspace containing five labeled sections corresponding to the sublanguages.
-   All statements execute successfully in PostgreSQL via DBeaver SQL consoles.
-   The TCL transaction successfully demonstrates rolling back the second change while preserving the first.
