# LEFT and RIGHT JOINs (Outer Joins)

## Learning Objectives
- Define the behavior and execution logic of `LEFT JOIN` and `RIGHT JOIN` operations.
- Construct SQL queries using outer join syntax to preserve unmatched rows.
- Explain how database engines populate unmatched join columns with `NULL`.
- Utilize outer joins combined with `IS NULL` filters to identify records that lack relationships.

---

## Why This Matters
On Friday, we learned that an `INNER JOIN` is an intersection: it excludes any rows that do not have a match in both tables. While this is useful, it is not appropriate for all business scenarios.

For example, suppose you want to generate a sales report listing all customers along with their total order history. 
-   If you use an `INNER JOIN`, any customer who has never placed an order will be completely hidden from the report.
-   To a business manager, seeing which customers are *not* buying products is just as important as seeing who is.

**Outer Joins (LEFT and RIGHT)** solve this. They allow you to select a "primary" table whose rows are guaranteed to be returned, regardless of whether a matching record exists in the secondary table, ensuring your reports are complete and comprehensive.

---

## The Concept

### 1. LEFT JOIN (LEFT OUTER JOIN)
A `LEFT JOIN` returns **all rows from the left (first) table**, plus any matching rows from the right (second) table.

If a row in the left table has no matching record in the right table, the query still returns the row, but all columns belonging to the right table are filled with **`NULL`**.

```
    Table A (Left Table)                 Table B (Right Table)
    +-------------+                     +-------------+
    | customer_id | name                | order_id    | customer_id
    +-------------+                     +-------------+
    | 1           | Alice               | 101         | 1
    | 2           | Bob (No orders)     |             |
    +-------------+                     +-------------+
                        |
                        v (LEFT JOIN on customer_id)
    +-------------+-------------+-------------+
    | customer_id | name        | order_id    |
    +-------------+-------------+-------------+
    | 1           | Alice       | 101         |  <-- Match
    | 2           | Bob         | NULL        |  <-- Preserved left row, right is NULL
    +-------------+-------------+-------------+
```

### 2. RIGHT JOIN (RIGHT OUTER JOIN)
A `RIGHT JOIN` is the exact inverse of a `LEFT JOIN`. It returns **all rows from the right (second) table**, plus matching rows from the left table. If there is no match, left-table columns are filled with `NULL`.

*Enterprise Practice:* Developers rarely use `RIGHT JOIN`. For readability, it is best practice to keep the primary table on the left and use `LEFT JOIN` exclusively.

### 3. Filtering Outer Joins (Finding Unmatched Rows)
You can use a `LEFT JOIN` to find rows in Table A that **do not exist** in Table B. By running a left join and filtering for rows where the right table's primary key `IS NULL`, you isolate the unmatched records.

---

## Code Example: Querying Accounts and Orders

Let's write a SQL query connecting `customers` and `orders`.

```sql
CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT REFERENCES customers(customer_id),
    amount DECIMAL(10, 2)
);

INSERT INTO customers VALUES (1, 'Alice'), (2, 'Bob'), (3, 'Charlie');
INSERT INTO orders VALUES (101, 1, 99.99), (102, 1, 45.00);
-- Note: Bob and Charlie have no orders
```

---

### Scenario A: List All Customers and Their Orders (LEFT JOIN)
We want to see every customer name. If they have orders, show the amount.

```sql
SELECT c.name, o.order_id, o.amount
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
ORDER BY c.name;
```
*Output:*
```
name    | order_id | amount
--------|----------|--------
Alice   | 101      | 99.99
Alice   | 102      | 45.00
Bob     | NULL     | NULL     <-- Bob is preserved, order values are NULL
Charlie | NULL     | NULL     <-- Charlie is preserved
```

---

### Scenario B: Find Customers Who Have NEVER Placed an Order
We isolate Bob and Charlie by filtering for rows where the child order key `IS NULL`:

```sql
SELECT c.name
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
WHERE o.order_id IS NULL; -- Filters for unmatched right rows
```
*Output:*
```
name
-------
Bob
Charlie
```

---

## Summary
-   A **`LEFT JOIN`** returns all records from the left table and matching records from the right table.
-   Unmatched rows from the secondary table are auto-populated with **`NULL`** values.
-   A **`RIGHT JOIN`** is the inverse, but `LEFT JOIN` is preferred for query readability.
-   Isolate records that **lack relationships** by filtering a `LEFT JOIN` with `WHERE right_table.key IS NULL`.

---

## Additional Resources
-   [PostgreSQL LEFT JOIN Tutorial](https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-left-join/)
-   [W3Schools: SQL LEFT JOIN](https://www.w3schools.com/sql/sql_join_left.asp)
-   [SQL Outer Joins and NULL Handling Principles](https://sqlbolt.com/)
