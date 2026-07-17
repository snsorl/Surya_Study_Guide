# Lab: Subqueries and Joins Comparison

## Learning Objectives
- Convert SQL join statements into equivalent nested subqueries.
- Identify the logical performance differences between joins and subqueries.
- Formulate scalar, row, and table-based subqueries.
- Evaluate execution plan structures inside database clients.

---

## Setup Instructions
1. Open DBeaver and connect to your local PostgreSQL instance.
2. Ensure you have the solutions to Query 1 and Query 3 from the previous Joins lab.

---

## Step-by-Step Tasks

### Task 1: Convert Inner Join to Subquery
Open your SQL script. Take the query that retrieves members who have checked out books using an `INNER JOIN`. Rewrite this query to return the same list of member names using a nested **subquery** with the `IN` operator.

```sql
-- Original INNER JOIN:
-- SELECT c.name FROM members c INNER JOIN loans o ...

-- Rewrite using nested subquery (WHERE ... IN):
```

---

### Task 2: Convert Left Outer Join to Subquery
Take the query that identifies members who have **never** checked out a book (previously solved using a `LEFT JOIN` and `IS NULL` check). Rewrite it to use the **`NOT EXISTS`** correlated subquery pattern.

```sql
-- Original LEFT JOIN with IS NULL:
-- ...

-- Rewrite using CORRELATED subquery (WHERE NOT EXISTS):
```

---

### Task 3: Compare Readability and Performance
Analyze your rewritten queries and answer the following questions:

1.  **Readability:** Which syntax (Joins or nested Subqueries) is easier for a human developer to read and maintain for both tasks?
2.  **Performance Analysis:** In DBeaver, run both versions of the queries (Joins vs. Subqueries) with the prefix **`EXPLAIN ANALYZE`** (e.g. `EXPLAIN ANALYZE SELECT name FROM ...`). Observe the returned output plan. Did the database engine translate the subquery into a join automatically, or does the plan differ?

---

## Definition of Done
-   A saved SQL script containing both the Join and Subquery versions of both tasks.
-   Verification logs showing that both the Join and Subquery versions of each task return the exact same rows.
-   A brief written note or comment in your script explaining the differences seen in the `EXPLAIN ANALYZE` execution plans.
