# User-Defined Functions (UDFs) in PostgreSQL

## Learning Objectives
- Write user-defined functions (UDFs) in PostgreSQL.
- Implement functions using both SQL and PL/pgSQL languages.
- Configure return types, including scalar types, records, and table outputs.
- Apply database functions in SELECT, WHERE, and JOIN clauses.
- Contrast database functions with stored procedures in a detailed comparison.

## Why This Matters
In software development, we follow the DRY (Don't Repeat Yourself) principle. When writing SQL queries, developers often repeat calculations, string cleanups, or lookup logic. For instance, calculating tax on a product price or building a formatted address string from multiple table columns might be written in several different queries. 

User-defined functions allow us to encapsulate reusable calculation logic directly inside the database database catalog. Once created, these functions can be called in any SELECT, WHERE, or JOIN clause as if they were built-in database functions. By understanding how to write functions in both SQL and PL/pgSQL, and knowing how they differ from stored procedures, developers can write clean, modular, and reusable database queries.

## The Concept

### What is a User-Defined Function (UDF)?
A User-Defined Function is a database object that accepts input parameters, performs calculations or database operations, and returns a result value. UDFs are executed as part of standard SQL expressions (e.g., `SELECT my_function(price) FROM products`).

### Return Types in PostgreSQL
PostgreSQL functions support several types of return values:
- **Scalar Types**: Returns a single value (e.g., `INT`, `VARCHAR`, `DECIMAL`, `BOOLEAN`).
- **Composite/Row Types (RETURNS RECORD or table row)**: Returns a complete row structure containing multiple values.
- **Table Structure (RETURNS TABLE)**: Returns a virtual table result set. This is useful for writing parameterized views.

### Language Options: SQL vs. PL/pgSQL
You can write PostgreSQL functions in several procedural languages. The two most common are:

1. **SQL Functions (`LANGUAGE sql`)**:
   Written purely using standard SQL queries. They do not support procedural logic (such as `IF` statements or loops). SQL functions are fast, easy to write, and can be optimized (inlined) by the query planner.
2. **PL/pgSQL Functions (`LANGUAGE plpgsql`)**:
   Written using PostgreSQL's procedural extension language. They support programming constructs like local variables, conditional blocks (`IF-THEN-ELSE`), loops, and exception handling. Use PL/pgSQL when you need procedural control flow.

### Key Differences: Functions vs. Stored Procedures
While functions and procedures look similar, they serve different purposes and have different execution constraints.

| Feature | User-Defined Function | Stored Procedure |
|---|---|---|
| **Execution Command** | Call inside a query (e.g., `SELECT fn_val()`) | Execute via `CALL` command (e.g., `CALL pr_val()`) |
| **Return Value** | Must return a value (scalar, record, or table) | Cannot return a value (uses `OUT` parameters instead) |
| **Transaction Control** | Cannot execute `COMMIT` or `ROLLBACK` | Can execute `COMMIT` and `ROLLBACK` |
| **Usage Context** | Can be used inside `SELECT`, `WHERE`, and `JOIN` clauses | Cannot be used inside queries |
| **Execution Environment**| Must run inside an active transaction block | Can start and manage its own transaction block |

## Code Examples

### Setting up the Schema
```sql
CREATE TABLE products_udf (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    tax_rate DECIMAL(4, 2) NOT NULL
);

INSERT INTO products_udf VALUES 
(1, 'Laptop', 1200.00, 0.08),
(2, 'Mouse Pad', 15.00, 0.05);
```

### Example 1: Standard SQL Function (LANGUAGE SQL)
A simple, high-performance function that calculates the tax amount for a product.

```sql
CREATE OR REPLACE FUNCTION fn_calculate_tax(
    item_price DECIMAL,
    rate DECIMAL
)
RETURNS DECIMAL
LANGUAGE sql
AS $$
    SELECT item_price * rate;
$$;
```

To call this function:

```sql
SELECT 
    product_name,
    price,
    fn_calculate_tax(price, tax_rate) AS tax_amount,
    price + fn_calculate_tax(price, tax_rate) AS total_cost
FROM products_udf;
```

### Example 2: PL/pgSQL Function with Procedural Logic (LANGUAGE plpgsql)
A function that calculates a loyalty discount based on the item price using `IF-THEN` conditional logic.

```sql
CREATE OR REPLACE FUNCTION fn_get_discount(
    item_price DECIMAL
)
RETURNS DECIMAL
LANGUAGE plpgsql
AS $$
DECLARE
    discount DECIMAL := 0.00;
BEGIN
    IF item_price > 1000.00 THEN
        discount := item_price * 0.10; -- 10% discount for expensive items
    ELSIF item_price > 100.00 THEN
        discount := item_price * 0.05; -- 5% discount
    ELSE
        discount := 0.00;
    END IF;
    
    RETURN discount;
END;
$$;
```

To call this function:

```sql
SELECT 
    product_name,
    price,
    fn_get_discount(price) AS applied_discount,
    price - fn_get_discount(price) AS net_price
FROM products_udf;
```

### Example 3: Function Returning a Table (RETURNS TABLE)
A function that returns a table of products matching a specified tax threshold.

```sql
CREATE OR REPLACE FUNCTION fn_get_products_with_high_tax(
    min_rate DECIMAL
)
RETURNS TABLE (
    prod_name VARCHAR,
    prod_price DECIMAL,
    applied_rate DECIMAL
)
LANGUAGE sql
AS $$
    SELECT product_name, price, tax_rate
    FROM products_udf
    WHERE tax_rate >= min_rate;
$$;
```

To call this function:

```sql
-- Query the table function as if it were a physical table
SELECT * FROM fn_get_products_with_high_tax(0.06);
```

## Summary
- **User-defined functions (UDFs)** package reusable queries and logic to be called directly within SQL statements.
- **SQL functions** run simple queries without programming logic, allowing PostgreSQL to optimize execution.
- **PL/pgSQL functions** support procedural programming (variables, conditions, loops).
- **Functions** must return values and cannot control transactions; **Stored procedures** are called using `CALL`, do not return values directly, and can commit or roll back transactions.

## Additional Resources
- [PostgreSQL Documentation on CREATE FUNCTION](https://www.postgresql.org/docs/current/sql-createfunction.html)
- [PostgreSQL PL/pgSQL Procedural Language Guide](https://www.postgresql.org/docs/current/plpgsql.html)
- [UDFs vs Stored Procedures - Mode Analytics Tutorial](https://mode.com/sql-tutorial/sql-user-defined-functions/)
