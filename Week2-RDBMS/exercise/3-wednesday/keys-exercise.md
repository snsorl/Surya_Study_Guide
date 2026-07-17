# Lab: Enforcing Key Constraints and Referential Integrity

## Learning Objectives
- Enforce database integrity using `PRIMARY KEY` and `FOREIGN KEY` constraints.
- Implement explicit unique constraints using the `UNIQUE` clause.
- Design and execute composite primary keys for intersection tables.
- Construct SQL test scripts to deliberately violate constraint limits, verifying validation errors.

---

## Setup Instructions
1. Open DBeaver and connect to your local PostgreSQL instance.
2. Open a new SQL Console editor.
3. Review your library ERD designed on Tuesday: you will create three tables: `books`, `members`, and `loans`.

---

## Step-by-Step Tasks

### Task 1: Create Tables with Key Constraints
Write the DDL SQL script to create the Library schema. You must apply:
1.  **`books` Table:** `book_id` is the primary key.
2.  **`members` Table:** `member_id` is the primary key, and `email` must be unique (`UNIQUE`).
3.  **`loans` Table:** An intersection table linking members and books. It must contain:
    -   `member_id` referencing `members.member_id` as a Foreign Key.
    -   `book_id` referencing `books.book_id` as a Foreign Key.
    -   A **composite primary key** consisting of the pair: `(member_id, book_id, loan_date)`.

```sql
-- DDL Script: Write your tables setup statements here
```

---

### Task 2: Seed Valid Parental Records
Insert at least 2 books and 2 members to establish parent records:

```sql
INSERT INTO books VALUES (101, 'The Hobbit', 'J.R.R. Tolkien'), (102, '1984', 'George Orwell');
INSERT INTO members VALUES (5001, 'John Doe', 'john@email.com'), (5002, 'Jane Smith', 'jane@email.com');
```

---

### Task 3: Test Constraint Violations
Write and execute queries to deliberately trigger the following error conditions. Observe the database error log outputs.

#### 1. Test Foreign Key Constraint Violation (Orphan Check)
Try to check out a book for a member who does not exist:

```sql
INSERT INTO loans (member_id, book_id, loan_date)
VALUES (9999, 101, '2026-07-12'); -- Member 9999 does not exist!
```
*Expected Error:* `violates foreign key constraint...`

---

#### 2. Test Unique Constraint Violation (Duplicate email check)
Try to insert a new member using John Doe's email:

```sql
INSERT INTO members VALUES (5003, 'Alex Jones', 'john@email.com');
```
*Expected Error:* `duplicate key value violates unique constraint...`

---

## Definition of Done
-   A complete DDL script that successfully creates the library tables with all constraints.
-   SQL scripts showing the successful execution of the valid seeds.
-   A written log or markdown note summarizing the database error messages returned during the violation tests.
