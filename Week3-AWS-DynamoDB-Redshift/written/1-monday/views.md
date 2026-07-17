# Database Views: Standard, Materialized, and Abstraction Layers

## Learning Objectives
- Explain the role of database views as abstraction layers.
- Create database views using the `CREATE VIEW` statement.
- Distinguish between updatable and read-only views.
- Explain the mechanics, benefits, and maintenance requirements of Materialized Views.
- Formulate database design strategies utilizing views for data security and encapsulation.

## Why This Matters
As relational database schemas grow, query complexity increases. Front-end developers and business analysts often have to write long, complicated SQL queries containing multiple joins, calculations, and aggregations just to retrieve basic information. This repeats query logic and increases the risk of calculation errors.

Database views solve this by acting as virtual tables. They allow developers to save complex queries directly inside the database and query them as if they were simple tables. Furthermore, views establish a security boundary, allowing users to query subset data without exposing underlying table columns. By understanding the differences between standard views, updatable views, and materialized views, developers can create clean, secure, and high-performance data abstraction layers.

## The Concept

### What is a Database View?
A view is a logical (virtual) table defined by a SQL query. It does not store data physically on disk (with the exception of materialized views). Instead, a view acts as a saved query definition. When a user runs a query against a view, the database engine merges the view's query definition with the user's query and executes the combined request against the underlying base tables.

### Benefits of Views as Abstraction Layers
1. **Simplification**: Encapsulates complex joins, unions, and aggregations behind a simple table-like interface.
2. **Security**: Limits access to sensitive columns (e.g., exposing an employee view that excludes password hashes or social security numbers).
3. **Consistency**: Centralizes business logic (e.g., how to calculate "active customers") in the database, ensuring all application services use the same logic.
4. **Backward Compatibility**: If database tables are refactored or normalized, a view can match the old table structure, preventing legacy applications from breaking.

### Updatable vs. Read-Only Views
Can you run `INSERT`, `UPDATE`, or `DELETE` statements on a view? It depends on the complexity of the view's query.

- **Updatable Views**: In PostgreSQL, a view is automatically updatable if its query definition meets specific criteria:
  - The query must reference exactly one table or updatable view.
  - The query must not contain `GROUP BY`, `HAVING`, `LIMIT`, `OFFSET`, `DISTINCT`, `UNION`, or aggregate functions (like `SUM` or `COUNT`).
  - The columns in the view must map directly to columns in the base table.
  When an update is run against an updatable view, the changes are written directly to the underlying physical table.
- **Read-Only Views**: If the view contains joins, grouping, or aggregates, it is read-only. Attempts to write to it will throw an error. (Note: These views can be made updatable using triggers, specifically `INSTEAD OF` triggers).

### Materialized Views
A materialized view is a hybrid object: it is defined by a query, but it *physically stores* the result set on disk like a real table. 

- **Benefit**: Queries run against a materialized view execute instantly because the database does not need to run complex calculations or joins on the fly. This is ideal for heavy analytical reporting (OLAP workloads).
- **Tradeoff**: The data in a materialized view is static. If the underlying base tables change, the materialized view does not update automatically. It must be manually refreshed using the `REFRESH MATERIALIZED VIEW` command. This consumes system resources and means data can be stale between refreshes.

## Code Examples

### Setting up the Schema
```sql
CREATE TABLE departments (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL
);

CREATE TABLE staff (
    staff_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    dept_id INT REFERENCES departments(dept_id)
);

INSERT INTO departments VALUES (10, 'Engineering'), (20, 'Sales');
INSERT INTO staff VALUES 
(1, 'Alice', 'Smith', 95000.00, 10),
(2, 'Bob', 'Jones', 60000.00, 20),
(3, 'Charlie', 'Brown', 110000.00, 10);
```

### Creating a Standard View (Abstraction and Security)
Let's create a view that shows staff details but hides their salary, and joins department names automatically.

```sql
CREATE VIEW v_staff_directory AS
SELECT 
    s.staff_id,
    s.first_name,
    s.last_name,
    d.dept_name
FROM staff s
JOIN departments d ON s.dept_id = d.dept_id;

-- Querying the view is as simple as querying a table:
SELECT * FROM v_staff_directory WHERE dept_name = 'Engineering';
```

### Updatable Views in Action
Let's create a view containing only engineering employees. Since it references a single table without aggregates, it is updatable.

```sql
CREATE VIEW v_engineering_staff AS
SELECT staff_id, first_name, last_name, dept_id
FROM staff
WHERE dept_id = 10;

-- Update a record through the view
UPDATE v_engineering_staff 
SET last_name = 'Smith-Jones' 
WHERE staff_id = 1;

-- Verify the change in the underlying staff table
SELECT * FROM staff WHERE staff_id = 1;
```

### Creating and Refreshing Materialized Views
Suppose we need to run a heavy query that calculates total payroll costs per department for a dashboard.

```sql
CREATE MATERIALIZED VIEW mv_department_payroll AS
SELECT 
    d.dept_name,
    COUNT(s.staff_id) AS total_employees,
    SUM(s.salary) AS total_payroll
FROM departments d
LEFT JOIN staff s ON d.dept_id = s.dept_id
GROUP BY d.dept_name;

-- Query the cached result instantly
SELECT * FROM mv_department_payroll;

-- If we insert a new employee, the materialized view is not updated:
INSERT INTO staff VALUES (4, 'David', 'Miller', 80000.00, 10);

-- Querying the materialized view still shows the old payroll data:
SELECT * FROM mv_department_payroll;

-- To sync the data, we must refresh it:
REFRESH MATERIALIZED VIEW mv_department_payroll;

-- Now the view displays the correct updated metrics:
SELECT * FROM mv_department_payroll;
```

## Summary
- A **standard view** is a logical virtual table defined by a SQL query. It does not store physical data.
- Views simplify queries, centralize business logic, and act as security layers by restricting column visibility.
- Views are **updatable** if they target a single base table without groupings or aggregates.
- **Materialized views** store query results physically on disk. They provide excellent read performance for heavy reporting queries but require manual updates using the `REFRESH MATERIALIZED VIEW` command.

## Additional Resources
- [PostgreSQL Documentation on CREATE VIEW](https://www.postgresql.org/docs/current/sql-createview.html)
- [PostgreSQL Documentation on Materialized Views](https://www.postgresql.org/docs/current/rules-materializedviews.html)
- [Understanding Database Views and Best Practices - GeeksforGeeks](https://www.geeksforgeeks.org/sql-views/)
