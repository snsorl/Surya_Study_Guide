# Database Indexes: B-Trees, Syntax, Performance, and Analysis

## Learning Objectives
- Explain the physical structure and operation of a B-tree database index.
- Write SQL statements to create single-column, composite, and covering indexes in PostgreSQL.
- Analyze the cost-to-benefit tradeoffs of creating database indexes.
- Use `EXPLAIN` and `EXPLAIN ANALYZE` to inspect and optimize query execution plans.

## Why This Matters
As datasets grow from thousands to millions of rows, database query performance can degrade significantly. A simple search that runs instantly on a developer's local machine can take several seconds in a production cloud environment, causing application timeouts and poor user experience. 

Database indexes are the primary tool used by software engineers to optimize database search performance. By creating structured, sorted search trees, indexes allow databases to locate specific records without scanning every row in a table. However, indexes are not free; they consume storage space and slow down write operations (insert, update, delete). Mastering indexing strategies is essential for building high-performance, cost-effective database backends.

## The Concept

### The B-Tree Index Structure
Most relational databases, including PostgreSQL, use B-trees (Balanced Trees) as their default index structure. A B-tree is a self-balancing search tree designed to store sorted data and allow search, sequential access, insertion, and deletion operations in logarithmic time ($O(\log n)$).

Structure layers:
1. **Root Node**: The entry point of the search. Contains pointers to intermediate child nodes.
2. **Internal Nodes (Branch Nodes)**: Store ranges of key values and act as routing keys, directing the search to lower layers.
3. **Leaf Nodes**: The bottom-most layer. Leaf nodes contain the actual indexed key values and the physical pointers (row identifiers, or TIDs in PostgreSQL) to the matching data rows in the heap table.

*How a Search Works*: To find a specific key, the database engine starts at the root node, compares the search key to the range values, follows the appropriate pointer to an internal node, repeats this search until it reaches a leaf node, and then retrieves the physical row pointer to fetch the record from the main table. Instead of scanning millions of rows, the database only reads a few index pages (typically 3 to 4 page reads).

### Types of Indexes

1. **Single-Column Index**: An index created on a single table column.
2. **Composite Index (Multi-Column Index)**: An index created on multiple columns. The order of columns in a composite index is critical. The database can search a composite index starting from the leftmost column first. An index on `(last_name, first_name)` is useful for searching `last_name` alone or `last_name` AND `first_name`, but it is not useful for searching `first_name` alone.
3. **Covering Index**: An index that contains all the data columns required by a query, allowing the database to satisfy the query entirely from the index itself without reading the actual table pages. This is implemented in PostgreSQL using the `INCLUDE` clause.

### Index Cost vs. Benefit Analysis

While indexes dramatically speed up read queries, they introduce several tradeoffs:

- **Write Overhead**: Every time a row is inserted, updated, or deleted, the database must also update all indexes associated with that table. This consumes CPU cycles and increases write latency.
- **Storage Overhead**: Indexes require disk space. In write-heavy applications, the combined size of a table's indexes can exceed the size of the table itself.
- **Maintenance Cost**: Over time, indexes can become fragmented, requiring reorganization (`REINDEX`).

### Query Plan Analysis: EXPLAIN and EXPLAIN ANALYZE
Relational databases use a query planner to determine the most efficient execution plan for a SQL query. Engineers use two tools to inspect these plans:

- **EXPLAIN**: Displays the query planner's estimated cost and execution path (e.g., Sequential Scan vs. Index Scan) without actually running the query.
- **EXPLAIN ANALYZE**: Actually runs the query, measuring exact execution times, rows retrieved, and buffer reads, and displays the execution plan along with the actual runtime statistics.

## Code Examples

### Setting up a Sample Schema
```sql
CREATE TABLE employees (
    employee_id INT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    department_id INT NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    hire_date DATE NOT NULL
);

-- Note: A primary key automatically creates a unique index on employee_id.
```

### Creating Single-Column and Composite Indexes
```sql
-- Single-column index on department_id
CREATE INDEX idx_employees_dept ON employees(department_id);

-- Composite index on last_name and first_name
CREATE INDEX idx_employees_name ON employees(last_name, first_name);
```

### Creating a Covering Index
A covering index speeds up queries that only retrieve a small set of columns by adding those columns as payload data in the leaf nodes, bypassing the table heap entirely.

```sql
-- Create index on department_id, including the salary column
CREATE INDEX idx_employees_dept_salary ON employees(department_id) INCLUDE (salary);

-- A query like this will execute an "Index Only Scan" (fastest scan type):
SELECT department_id, salary 
FROM employees 
WHERE department_id = 5;
```

### Analyzing Query Performance with EXPLAIN ANALYZE
Before indexing, the database must scan the entire table (Sequential Scan) to locate a record.

```sql
-- Check plan for searching by last name before creating the index
EXPLAIN ANALYZE 
SELECT * FROM employees WHERE last_name = 'Smith';
```

Output highlights:
- `Seq Scan on employees`
- `Filter: ((last_name)::text = 'Smith'::text)`
- `Planning Time: 0.12 ms`
- `Execution Time: 4.85 ms` (simulated value for small table, much higher for large tables)

After creating the composite index, run the analysis again:

```sql
EXPLAIN ANALYZE 
SELECT * FROM employees WHERE last_name = 'Smith';
```

Output highlights:
- `Index Scan using idx_employees_name on employees`
- `Index Cond: ((last_name)::text = 'Smith'::text)`
- `Planning Time: 0.15 ms`
- `Execution Time: 0.08 ms`

## Summary
- **B-tree indexes** organize data hierarchically to locate records in logarithmic time.
- **Composite indexes** require ordered column matching; they must be queried starting from the leftmost column.
- **Covering indexes** use the `INCLUDE` clause to pack additional columns into the index leaf node, enabling fast Index Only Scans.
- Use `EXPLAIN` to view planner estimates, and `EXPLAIN ANALYZE` to run queries and obtain real performance statistics.

## Additional Resources
- [PostgreSQL Documentation on Indexes](https://www.postgresql.org/docs/current/indexes.html)
- [PostgreSQL EXPLAIN Statement Reference](https://www.postgresql.org/docs/current/sql-explain.html)
- [Use The Index, Luke! - A Guide to Database Indexing](https://use-the-index-luke.com/)
