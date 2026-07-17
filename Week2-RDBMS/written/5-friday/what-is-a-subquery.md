# What is a Subquery?

## Learning Objectives
- Define a SQL subquery and identify its position inside a parent query.
- Differentiate between Non-Correlated and Correlated subqueries.
- Classify subqueries based on their return data shapes (Scalar, Row, and Table).
- Implement subqueries in the `WHERE` and `FROM` clauses of SELECT statements.

---

## Why This Matters
When querying databases, you will frequently encounter scenarios where you need to filter data based on a dynamic calculation rather than a static value. 

For example:
-   Find all products whose price is higher than the **average** product price. (You cannot write `WHERE price > AVG(price)` directly due to SQL aggregation constraints).
-   Find all customers who made a purchase yesterday but have never made a purchase before.
-   Find employees earning more than the average salary of **their specific department**.

To solve these problems in a single database round-trip, you must write a query inside another query. This is a **Subquery** (or nested query). Subqueries allow you to calculate dynamic, multi-step filter criteria on the fly, making your data retrieval operations incredibly powerful and flexible.

---

## The Concept

### 1. What is a Subquery?
A **Subquery** is a `SELECT` statement nested inside the clause of another SQL query (known as the parent or outer query). A subquery must always be enclosed in parentheses `()`.

### 2. Classification by Output Shape
-   **Scalar Subquery:** Returns exactly one value (one row, one column). Can be used anywhere a literal constant is expected (e.g., inside `WHERE` comparisons).
-   **Row Subquery:** Returns a single row with multiple columns. Useful for comparing multiple values simultaneously.
-   **Table Subquery:** Returns one or more columns and multiple rows. Typically used with operators like `IN`, `ANY`, `ALL`, or `EXISTS`.

### 3. Classification by Execution Mechanics
-   **Non-Correlated Subquery:** An independent query that executes **once** before the parent query runs. The parent query uses the subquery's static output directly.
-   **Correlated Subquery:** A query that references one or more columns from the parent query. Because it depends on the outer query, the database engine logically executes the subquery **once for every row** processed by the parent query, which can reduce performance on large tables.

### 4. Locations: WHERE vs. FROM
-   **WHERE Subquery:** Filters rows of the parent query based on the subquery's calculation.
-   **FROM Subquery (Inline View / Derived Table):** The subquery returns a temporary table that the parent query selects from. You must always give a FROM subquery a table alias (e.g., `FROM (SELECT...) AS temp`).

---

## Code Examples

Let's work with an `employees` table to demonstrate subqueries.

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL
);

INSERT INTO employees VALUES 
(1, 'Alice', 'Engineering', 100000.00),
(2, 'Bob', 'Engineering', 90000.00),
(3, 'Charlie', 'HR', 60000.00),
(4, 'Diana', 'HR', 70000.00);
```

---

### Example 1: Non-Correlated Scalar Subquery (WHERE)
Find employees earning more than the overall average company salary:

```sql
SELECT name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees); -- Subquery runs once, returns 80000.00
```
*Result:* Alice ($100k) and Bob ($90k) are returned.

---

### Example 2: Non-Correlated Table Subquery (FROM)
Select from a temporary aggregated derived table to find department salary spans:

```sql
SELECT temp.department, temp.max_sal
FROM (
    SELECT department, MAX(salary) AS max_sal 
    FROM employees 
    GROUP BY department
) AS temp -- FROM subquery must have an alias
WHERE temp.max_sal > 80000.00;
```

---

### Example 3: Correlated Subquery
Find employees who earn more than the average salary of **their specific department**:

```sql
SELECT e1.name, e1.department, e1.salary
FROM employees e1
WHERE e1.salary > (
    SELECT AVG(e2.salary) 
    FROM employees e2 
    WHERE e2.department = e1.department -- Correlation link back to outer row e1
);
```
*Result:* Returns Alice (Engineering, $100k vs. dept avg $95k) and Diana (HR, $70k vs. dept avg $65k).

---

## Summary
-   A **Subquery** is a nested `SELECT` statement enclosed in parentheses `()`.
-   **Scalar subqueries** return a single value; **Table subqueries** return rows/columns.
-   **Non-correlated subqueries** run independently once; **correlated subqueries** reference parent columns and run repeatedly per row.
-   Subqueries in the **`FROM`** clause represent temporary tables and require an alias.

---

## Additional Resources
-   [PostgreSQL Subquery Documentation](https://www.postgresql.org/docs/current/functions-subquery.html)
-   [W3Schools: SQL Subqueries](https://www.w3schools.com/sql/sql_subquery.asp)
-   [GeeksforGeeks: Correlated Subqueries](https://www.geeksforgeeks.org/sql-correlated-subqueries/)
