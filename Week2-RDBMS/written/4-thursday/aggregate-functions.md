# Aggregate Functions and Grouping

## Learning Objectives
- Identify and implement the five primary SQL aggregate functions (`COUNT`, `SUM`, `AVG`, `MIN`, `MAX`).
- Group query results based on one or more columns using the `GROUP BY` clause.
- Filter aggregated group calculations using the `HAVING` clause.
- Differentiate between the domains of control of the `WHERE` and `HAVING` clauses.

---

## Why This Matters
As a full-stack developer, your backend service is constantly responsible for displaying summary metrics on user dashboards. 
-   A business owner needs to see their total sales count for the month.
-   A manager needs to see the average employee salary per department.
-   An e-commerce user needs to see their order history grouped by purchase status.

If you retrieve all raw transaction records from the database and calculate these metrics in your Java code (e.g., using streams or loops to sum values), you will create massive performance issues. You will transmit millions of rows across the network just to calculate a single number.

Relational databases are optimized to perform mathematical consolidations directly on the disk storage pages. By using **Aggregate Functions** and **Grouping**, you let the database server calculate summary statistics instantly, returning only the final numbers.

---

## The Concept

### 1. The Five Core Aggregate Functions
Aggregate functions run calculations on a set of values in a column and return a single summary value:
-   **`COUNT()`**: Returns the number of non-null values in a column. Use `COUNT(*)` to count the total rows in a table (including NULLs).
-   **`SUM()`**: Calculates the total addition of numeric column values.
-   **`AVG()`**: Calculates the arithmetic mean of numeric column values.
-   **`MIN()`**: Identifies the smallest value in a column (works on numbers, dates, and strings).
-   **`MAX()`**: Identifies the largest value in a column.

### 2. Grouping Rows (GROUP BY)
The **`GROUP BY`** clause groups rows that share the same values in specified columns into summary rows. 
-   *The rule:* If you use an aggregate function in your `SELECT` list alongside regular columns, you **must** include those regular columns in the `GROUP BY` clause. Otherwise, the database will throw a compilation error.

### 3. Filtering Aggregates (HAVING vs. WHERE)
You cannot filter aggregated statistics using the `WHERE` clause because the database executes the `WHERE` filter *before* it aggregates the data. To filter groups, you must use the **`HAVING`** clause.

| Clause | Domain of Control | Execution Order |
|---|---|---|
| **`WHERE`** | Filters **raw individual rows** before grouping. | Runs before `GROUP BY`. |
| **`HAVING`** | Filters **aggregated group results** after grouping. | Runs after `GROUP BY`. |

---

## Code Example: Sales Metrics Schema

Let's look at an e-commerce `orders` table to demonstrate aggregations.

```sql
CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT NOT NULL,
    department VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL
);

INSERT INTO orders VALUES 
(1, 10, 'Electronics', 150.00),
(2, 10, 'Electronics', 200.00),
(3, 11, 'Books', 20.00),
(4, 12, 'Books', 35.00),
(5, 12, 'Electronics', 500.00),
(6, 13, 'Home', 45.00);
```

---

### Scenario A: Basic Aggregations
We want to see the total number of orders, total sales, and the average order amount:

```sql
SELECT 
    COUNT(order_id) AS "Total Orders", 
    SUM(amount) AS "Total Revenue", 
    AVG(amount) AS "Average Ticket"
FROM orders;
```

---

### Scenario B: Grouping by Category (GROUP BY)
We want to find the total sales amount (`SUM`) and count of orders for each product department:

```sql
SELECT department, COUNT(order_id) AS "Num Orders", SUM(amount) AS "Total Sales"
FROM orders
GROUP BY department;
```
*Output:*
```
department  | Num Orders | Total Sales
------------|------------|------------
Electronics | 3          | 850.00
Books       | 2          | 55.00
Home        | 1          | 45.00
```

---

### Scenario C: Filtering Aggregates (HAVING)
We want to find the departments that have generated more than $100 in total sales:

```sql
SELECT department, SUM(amount) AS "Total Sales"
FROM orders
GROUP BY department
HAVING SUM(amount) > 100.00; -- Filtering the aggregate calculation
```
*Output:* Only `Electronics` ($850.00) is returned. `Books` and `Home` are filtered out.

---

## Summary
-   **Aggregate functions** (`COUNT`, `SUM`, `AVG`, `MIN`, `MAX`) consolidate multi-row column data into single outputs.
-   Use **`GROUP BY`** to segregate calculations across distinct column values (e.g., calculations per department).
-   Use **`WHERE`** to filter raw records; use **`HAVING`** to filter grouped calculations.
-   Performing aggregations database-side reduces network overhead and speeds up applications.

---

## Additional Resources
-   [PostgreSQL Aggregate Functions List](https://www.postgresql.org/docs/current/functions-aggregate.html)
-   [W3Schools: SQL GROUP BY Statement](https://www.w3schools.com/sql/sql_groupby.asp)
-   [LearnSQL: HAVING vs WHERE Clauses](https://learnsql.com/blog/having-vs-where-in-sql/)
