# DQL: Data Query Language

## Learning Objectives
- Define the role of Data Query Language (DQL) in retrieving database records.
- Construct SQL queries using `SELECT`, `WHERE`, `ORDER BY`, `LIMIT`, and `DISTINCT` clauses.
- Implement column and table aliases using the `AS` keyword to improve query readability.
- Explain the physical execution order of SQL query clauses.

---

## Why This Matters
If you think about the websites and apps you use daily (such as searching for a product, browsing a social media feed, or filtering emails), you are triggering database read operations. In full-stack development, **DQL** is the most frequently written sublanguage.

Every time your Java backend handles a read request, it constructs a DQL query to fetch the requested records. 

However, if your queries are poorly structured—for instance, retrieving thousands of columns you do not need, or failing to sort results correctly—your application's performance will degrade, leading to slow page loads and memory issues. Mastering the filters and sorting clauses of DQL allows you to construct precise database requests that return only what is needed.

---

## The Concept

### 1. What is DQL?
**Data Query Language (DQL)** consists of the commands used to retrieve data from database tables. In SQL, this is handled entirely by the **`SELECT`** statement. DQL operations are read-only: they read records from the disk and display them, leaving the database state completely unchanged.

### 2. Core DQL Clauses

-   **`SELECT`**: Specifies which columns you want to retrieve. You can fetch all columns using the asterisk wildcard (`SELECT *`) or list specific columns (e.g., `SELECT name, email`). **Best Practice:** Always select specific columns to minimize network bandwidth.
-   **`WHERE`**: Filters rows based on specific logical conditions (e.g., `WHERE age > 18`).
-   **`ORDER BY`**: Sorts the resulting rows based on one or more columns in ascending (`ASC`, default) or descending (`DESC`) order.
-   **`LIMIT`**: Restricts the maximum number of rows returned by the query. Essential for implementing search pagination.
-   **`DISTINCT`**: Eliminates duplicate rows from the query output, returning only unique values.

### 3. Aliasing (AS Keyword)
Aliases are temporary names given to columns or tables within a query to make the output cleaner or the SQL code shorter.
-   **Column Aliasing:** Renames output headers (e.g., `SELECT first_name AS "First Name"`).
-   **Table Aliasing:** Creates short handles for tables, which is critical when joining multiple tables (e.g., `FROM customer_profiles AS cp`).

### 4. Logical vs. Physical Execution Order
When writing SQL, you write clauses in a specific syntax order. However, the database engine executes them in a completely different physical order:

```
Syntax Order (How you write it):         Execution Order (How database runs it):
1. SELECT                                 1. FROM (Identify source tables)
2. FROM                                   2. WHERE (Filter rows first)
3. WHERE                                  3. SELECT (Extract target columns)
4. ORDER BY                               4. DISTINCT (Eliminate duplicates)
5. LIMIT                                  5. ORDER BY (Sort final rows)
                                          6. LIMIT (Cap output count)
```

Understanding this execution order is key to understanding why you cannot use a column alias defined in the `SELECT` clause within your `WHERE` filter.

---

## Code Example: Data Retrieval Script

Let's work with an `employees` table to demonstrate these clauses:

```sql
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL
);

INSERT INTO employees (emp_id, first_name, last_name, department, salary)
VALUES 
(1, 'Alice', 'Smith', 'Engineering', 95000.00),
(2, 'Bob', 'Jones', 'Engineering', 105000.00),
(3, 'Charlie', 'Brown', 'HR', 65000.00),
(4, 'Diana', 'Prince', 'Engineering', 120000.00),
(5, 'Evan', 'Wright', 'HR', 60000.00);
```

---

### Scenario A: Basic Column Filtering & Aliasing
We want to extract employee names and salaries, renaming the headers:

```sql
SELECT first_name AS "First Name", salary AS "Base Salary"
FROM employees;
```

---

### Scenario B: Row Filtering, Sorting, and Pagination
We want to find all engineers earning more than $90,000, sorted from highest salary to lowest, showing only the top 2:

```sql
SELECT first_name, last_name, salary
FROM employees
WHERE department = 'Engineering' 
  AND salary > 90000.00
ORDER BY salary DESC
LIMIT 2;
```
*Result:* Diana Prince ($120k) and Bob Jones ($105k) are returned. Alice Smith is filtered out by the limit.

---

### Scenario C: Unique Values (DISTINCT)
We want to see a list of all active departments:

```sql
SELECT DISTINCT department
FROM employees;
-- Result: Engineering, HR
```

---

## Summary
-   **DQL (Data Query Language)** retrieves data using the **`SELECT`** statement.
-   **`WHERE`** filters rows; **`ORDER BY`** sorts rows; **`LIMIT`** restricts output count.
-   **`DISTINCT`** filters out duplicate rows.
-   **Aliases (`AS`)** temporarily rename columns or tables to improve output styling and simplify queries.
-   SQL query clauses execute physically in a different order than they are written.

---

## Additional Resources
-   [PostgreSQL SELECT Clause Documentation](https://www.postgresql.org/docs/current/sql-select.html)
-   [W3Schools: SQL Select and Filters Guide](https://www.w3schools.com/sql/sql_select.asp)
-   [Standard SQL Execution Order Breakdown](https://sqlbolt.com/)
