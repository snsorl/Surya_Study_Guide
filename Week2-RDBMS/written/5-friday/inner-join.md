# INNER JOINs

## Learning Objectives
- Define the behavior and execution logic of an `INNER JOIN`.
- Construct SQL queries using `INNER JOIN` syntax and the `ON` clause.
- Explain NULL exclusion behavior in inner join results.
- Implement table aliases to resolve column naming conflicts and improve readability.

---

## Why This Matters
When querying database relationships, your primary concern is matching related data: finding orders that have active customers, products that belong to a category, or employees assigned to a department. 

If you use a generic join type, you might end up returning incomplete records (like customers who have never bought anything, displaying blank tables in your user interface).

An **INNER JOIN** is the default, most common type of join. It acts as a logical intersection, filtering out all unmatched rows automatically. This ensures your application API receives clean, complete records where every relation is fully resolved, preventing null-mapping crashes in your backend Java code.

---

## The Concept

### 1. What is an INNER JOIN?
An **INNER JOIN** compares rows from Table A and Table B using a match condition defined in the `ON` clause. The output contains *only* the rows that satisfy the condition. If a row in Table A has no matching key in Table B, it is completely excluded from the final result.

```
       Table A (Left)              Table B (Right)
     +---------------+           +---------------+
     | key | value_a |           | key | value_b |
     +---------------+           +---------------+
     | 1   | Apple   | -- Match -| 1   | Fruit   |
     | 2   | Carrot  |           | 3   | Meat    |
     | 3   | Beef    | -- Match -|               |
     +---------------+           +---------------+
                            |
                            v (INNER JOIN)
                     +-----------------------------+
                     | key | value_a  | value_b    |
                     +-----------------------------+
                     | 1   | Apple    | Fruit      |
                     | 3   | Beef     | Meat       |
                     +-----------------------------+
                     * Carrot is excluded (no key 2 in Table B)
```

### 2. NULL Exclusion Behavior
Because a comparison using `NULL` always evaluates to `UNKNOWN` (as discussed in Tuesday's Three-Valued Logic lesson), any rows where the matching key is `NULL` are automatically excluded from an `INNER JOIN`. 
-   If an employee's `dept_id` is `NULL` (they are not assigned to a department), they will never appear in an `INNER JOIN` query linking employees and departments.

### 3. Table Aliasing
When joining tables, columns in both tables might share the same name (e.g., `customers.name` and `companies.name`). To prevent the database from throwing an "ambiguous column name" compilation error, you must:
1.  Assign temporary short names (aliases) to the tables in the `FROM` and `JOIN` clauses.
2.  Prefix all columns with their table aliases (e.g., `c.name`, `o.order_date`).

---

## Code Example: Querying Mapped Profiles

Let's write a SQL query connecting `users` and `member_profiles`.

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

CREATE TABLE member_profiles (
    profile_id INT PRIMARY KEY,
    user_id INT, -- Can be NULL (unassigned profile)
    bio TEXT
);

INSERT INTO users VALUES (1, 'alice_jones'), (2, 'bob_smith'), (3, 'charlie_b');
-- Note: Bob (ID 2) does not have a profile, and User 3 has a NULL profile link
INSERT INTO member_profiles VALUES (101, 1, 'Software Developer bio...'), (102, NULL, 'Unassigned bio...');
```

---

### Executing the INNER JOIN with Aliasing
We want to extract the username and bio for mapped profiles. We alias `users` as `u` and `member_profiles` as `mp`.

```sql
SELECT u.username, mp.bio
FROM users u
INNER JOIN member_profiles mp ON u.user_id = mp.user_id;
```
*Output (1 Row):*
```
username    | bio
------------|---------------------------
alice_jones | Software Developer bio...
```

### Analysis of Exclusions (NULL and Unmatched):
-   **Bob (User 2)** is excluded because his `user_id` does not exist in the `member_profiles` table.
-   **Profile 102** is excluded because its `user_id` is `NULL`. `NULL = u.user_id` evaluates to `UNKNOWN`, which is rejected.
-   **Charlie (User 3)** is excluded because he has no matching record in `member_profiles`.

---

## Summary
-   An **`INNER JOIN`** returns rows only where the join condition evaluates to `TRUE`.
-   **NULL values** are automatically excluded because comparisons with `NULL` evaluate to `UNKNOWN`.
-   Use **Table Aliases** (e.g., `FROM users u`) to prevent column name ambiguity and keep queries clean.

---

## Additional Resources
-   [PostgreSQL INNER JOIN Syntax Guide](https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-inner-join/)
-   [W3Schools: SQL INNER JOIN](https://www.w3schools.com/sql/sql_join_inner.asp)
-   [Relational Joins Optimization and Indexing](https://use-the-index-luke.com/)
