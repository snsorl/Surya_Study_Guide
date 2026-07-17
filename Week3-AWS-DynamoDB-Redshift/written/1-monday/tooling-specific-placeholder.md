# AI Tooling Configuration Notes for Database Development

## Learning Objectives
- Configure database developer environment with AI code assistants.
- Formulate effective prompts for writing, debugging, and optimizing database code.
- Connect local SQL editors and tools (e.g., DBeaver, VS Code extensions) with database endpoints.
- Establish best practices for security and validation when using AI-generated database scripts.

## Why This Matters
Software development has shifted from writing all code by hand to collaborating with AI coding assistants. For database tasks, AI assistants are useful for writing complicated SQL queries, generating realistic test datasets, and converting code between different database dialects. 

However, using AI for databases requires caution. If an AI generates a bad application script, it might throw a compiler error. If an AI generates a bad database script (like a query missing a join condition or a command that runs a table lock), it can lock up a production database, leak data, or delete tables. This guide outlines how to set up your database developer environment with AI assistants and establish safety checks to build database code securely.

## The Concept

### Setting Up AI Coding Assistants for SQL
Most modern developers use AI tools embedded in their IDEs (like VS Code, IntelliJ, or DBeaver AI). 

1. **IDE Integrations**: Extensions like GitHub Copilot, Tabnine, or AWS CodeWhisperer scan your schema comments and open SQL files to suggest queries on the fly.
2. **Dedicated Chat Models**: Using models like Claude, ChatGPT, or Gemini for complex queries, performance optimization, and migration planning.
3. **Database Client Plugins**: Advanced clients like DBeaver Enterprise offer built-in AI assistant integrations. These integrations can look at your database schema metadata and translate natural language (e.g., "Find the top 5 departments by total sales") into correct SQL queries tailored to your database version.

### Prompt Engineering for Database Code
To get high-quality SQL suggestions from AI, you must provide context. Simply asking "write an audit log query" yields generic, unusable results.

Effective prompts should include:
- **Target Database Dialect**: Explicitly specify the database engine (e.g., PostgreSQL 15, Amazon Redshift, Amazon DynamoDB).
- **Schema Metadata**: Provide the exact tables, column names, data types, and primary/foreign key relationships.
- **Constraints and Assumptions**: List constraints like `CHECK (balance >= 0.00)` or assumptions like "include soft-deleted items".
- **Desired Output Structure**: Specify if you need raw SQL, a PL/pgSQL function, or a stored procedure.

### The Validation Workflow
Never run AI-generated database code directly against a database without validation:
1. **Dry Run (Syntax Check)**: Run the script in a local containerized database environment first.
2. **Review Execution Plans**: Use `EXPLAIN` to verify that the query uses database indexes and does not perform slow sequential scans.
3. **Security Review**: Check that the script does not contain dangerous operations like `DROP TABLE` or `DELETE` statements without a `WHERE` clause.

## Code Examples

### Scenario: Writing a Prompt to Generate SQL
Here is an example of an effective prompt and the database code it generates.

#### High-Quality Developer Prompt:
```text
Role: PostgreSQL Database Administrator
Task: Generate a PL/pgSQL trigger function to update inventory balances.
Schema Context:
- Table: product_stock (product_id INT PRIMARY KEY, stock_level INT)
- Table: order_items (item_id SERIAL PRIMARY KEY, order_id INT, product_id INT REFERENCES product_stock(product_id), quantity_ordered INT)
Requirements:
Write an AFTER INSERT row-level trigger on order_items that deducts quantity_ordered from product_stock.stock_level. 
Ensure the function raises an exception if stock_level would drop below zero, preventing the insert.
```

#### AI-Generated Code Output:
```sql
CREATE OR REPLACE FUNCTION fn_deduct_inventory_on_order()
RETURNS TRIGGER AS $$
DECLARE
    current_stock INT;
BEGIN
    -- 1. Fetch current stock level with a lock to prevent concurrency issues
    SELECT stock_level INTO current_stock
    FROM product_stock
    WHERE product_id = NEW.product_id
    FOR UPDATE;

    -- 2. Validate availability
    IF current_stock < NEW.quantity_ordered THEN
        RAISE EXCEPTION 'Insufficient inventory. Product ID: %, Requested: %, Available: %', 
            NEW.product_id, NEW.quantity_ordered, current_stock;
    END IF;

    -- 3. Deduct stock
    UPDATE product_stock
    SET stock_level = stock_level - NEW.quantity_ordered
    WHERE product_id = NEW.product_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Bind the trigger
CREATE TRIGGER trg_after_order_item_insert
AFTER INSERT ON order_items
FOR EACH ROW
EXECUTE FUNCTION fn_deduct_inventory_on_order();
```

### Prompt for Query Performance Tuning
When a query is running slowly, you can ask an AI to suggest optimizations by providing the execution plan.

#### Input Prompt:
```text
Optimize this PostgreSQL query based on the following EXPLAIN ANALYZE output.
Query: 
SELECT first_name, last_name FROM employees WHERE department_id = 5;

EXPLAIN ANALYZE Output:
Seq Scan on employees  (cost=0.00..382.00 rows=45 width=102) (actual time=0.015..3.412 rows=45 loops=1)
  Filter: (department_id = 5)
  Rows Removed by Filter: 15482
Planning Time: 0.14 ms
Execution Time: 3.51 ms
```

#### AI Analysis & Optimization Advice:
```text
The execution plan reveals a Sequential Scan (Seq Scan) on the 'employees' table, scanning all 15,482 rows because there is no index on the 'department_id' column.

Recommended Solution:
Create a single-column B-tree index on 'department_id' to change the Sequential Scan into a fast Index Scan.

SQL Command:
CREATE INDEX idx_employees_dept_id ON employees(department_id);
```

## Summary
- **AI coding assistants** can write, debug, and optimize database scripts when provided with clear schema context.
- Include **database dialect**, **exact column names**, and **constraints** in your prompts to get correct, working SQL.
- Always review AI-generated code using **EXPLAIN** and run tests in a safe, local environment before executing scripts in production.

## Additional Resources
- [GitHub Copilot Documentation for SQL Developers](https://docs.github.com/en/copilot/using-github-copilot/getting-started-with-github-copilot)
- [Prompt Engineering Guide for Software Engineers](https://www.promptingguide.ai/)
- [PostgreSQL EXPLAIN Plan Tuning Best Practices](https://www.pgmustard.com/docs/explain-analyze)
