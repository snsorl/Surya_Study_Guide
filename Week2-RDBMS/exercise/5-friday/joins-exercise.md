# Lab: Constructing SQL JOIN Queries

## Learning Objectives
- Merge data across tables using explicit `INNER JOIN` syntax.
- Preserve unmatched records using `LEFT OUTER JOIN` structures.
- Filter outer joins using `IS NULL` to identify missing relationships.
- Resolve multi-table schemas using chained joins.

---

## Setup Instructions
1. Open DBeaver and connect to your local PostgreSQL instance.
2. Open a new SQL Console editor.
3. Ensure your Library schema tables (`books`, `members`, `loans`) contain seeded records.

---

## Step-by-Step Tasks

Write and execute the following 5 distinct join queries against your Library schema:

### Query 1: All Active Loans (INNER JOIN)
Write a query that returns the member name, book title, and loan date for all active checkout records. 
-   *Constraint:* Ensure you use explicit `INNER JOIN` and table aliases.

---

### Query 2: Identifying Inactive Members (LEFT JOIN)
Write a query listing **all library members**, regardless of whether they have ever borrowed a book. Display the member's name and the loan date (which will be `NULL` if they have no loans).

---

### Query 3: Members with Zero Borrowings
Modify Query 2 to filter the output and return **only** members who have never checked out a book. 
-   *Hint:* Filter for rows where the child loan table's foreign key `IS NULL`.

---

### Query 4: Detailed Book Checkout Catalog (Multi-Table JOIN)
Write a query linking `members` through `loans` to `books` that returns:
-   Member Name
-   Book Title
-   Author
-   Loan Date

---

### Query 5: Overdue Borrowing Log
Suppose a loan is overdue if it was checked out before `2026-07-01` and has not been returned. Write a join query returning the member name, email, book title, and checkout date for these overdue loans.

---

## Definition of Done
-   An SQL script file in your workspace containing the five queries.
-   All statements execute successfully in DBeaver.
-   The results returned match the logical constraints (e.g. Query 3 returns only members who have no loans).
