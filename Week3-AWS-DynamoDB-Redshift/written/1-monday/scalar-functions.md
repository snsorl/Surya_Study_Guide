# PostgreSQL Scalar Functions

## Learning Objectives
- Define the role of scalar functions in SQL query construction.
- Write queries utilizing standard text manipulation functions: UPPER, LOWER, LENGTH, and SUBSTRING.
- Execute numerical calculations and data conversions using ROUND and CAST.
- Apply conditional null-handling functions: COALESCE and NULLIF.
- Format dates and system timestamps using NOW() and TO_CHAR.

## Why This Matters
Raw data in database tables is rarely formatted exactly as needed for application interfaces or analytics reporting. Text may contain irregular capitalization, dates might require localized formatting, and missing values (NULLs) can break application calculation logic. 

Scalar functions process individual values, returning a single, transformed value for each row in the query result set. Utilizing scalar functions directly in SQL allows developers to offload data-formatting and null-handling logic to the database engine. This reduces network payload sizes and simplifies the business logic that needs to be written in downstream application code (such as Java or Node.js).

## The Concept

### What is a Scalar Function?
A scalar function is a database function that operates on a single input value (or a set of arguments for a single row) and returns a single value. This is in contrast to aggregate functions (like `SUM` or `AVG`), which operate on groups of rows to return a single summarized result. Scalar functions can be applied to columns, literals, or expressions within `SELECT`, `WHERE`, `ORDER BY`, and `HAVING` clauses.

### Core PostgreSQL Scalar Functions

#### 1. Text Functions
- **UPPER(text)**: Converts all characters in a string to uppercase.
- **LOWER(text)**: Converts all characters in a string to lowercase. Useful for standardizing text during case-insensitive searches.
- **LENGTH(text)**: Returns the number of characters in a string.
- **SUBSTRING(text FROM start_index FOR length)**: Extracts a portion of a string starting at a specified character index (1-based index in SQL) for a defined length.

#### 2. Math and Data Conversion Functions
- **ROUND(numeric_value, decimal_places)**: Rounds a numeric value to a specified number of decimal places.
- **CAST(value AS target_type)** or **value::target_type**: Converts an expression from one data type to another (e.g., text to integer, or integer to decimal).

#### 3. Null Handling Functions
- **COALESCE(val1, val2, ...)**: Evaluates arguments in order and returns the first non-NULL value. Often used to provide default values for columns that contain NULL.
- **NULLIF(val1, val2)**: Compares two values. If they are equal, it returns NULL; otherwise, it returns the first value. This is useful for preventing divide-by-zero errors.

#### 4. Date and Time Functions
- **NOW()**: Returns the current database server transaction timestamp (with timezone).
- **TO_CHAR(timestamp, format_pattern)**: Converts a timestamp or numeric value to formatted text based on a template (e.g., 'YYYY-MM-DD').

## Code Examples

Let us execute these functions using a sample SQL script in PostgreSQL.

### Table Setup
```sql
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_code VARCHAR(20) NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 4) NOT NULL,
    discount DECIMAL(10, 4) NULL, -- Can be NULL
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO products VALUES 
(1, 'PRD-102-X', 'Wireless Mouse', 49.9982, 5.0000, '2026-07-01 10:15:30'),
(2, 'prd-204-y', 'Mechanical Keyboard', 119.5000, NULL, '2026-07-02 14:20:00');
```

### Applying Scalar Functions in Select Queries

#### Text Transformation and Substrings
```sql
SELECT 
    product_name,
    UPPER(product_name) AS upper_name,
    LOWER(product_code) AS lower_code,
    LENGTH(product_name) AS name_length,
    -- Extract the middle part of the product code (e.g., '102')
    SUBSTRING(product_code FROM 5 FOR 3) AS numeric_code_part
FROM products;
```

#### Numeric Rounding and Explicit Casting
```sql
SELECT 
    product_name,
    price,
    ROUND(price, 2) AS rounded_price,
    -- Convert decimal price to integer (truncates decimal portion)
    CAST(price AS INT) AS price_as_int,
    -- PostgreSQL shorthand for casting
    price::INT AS price_as_int_shorthand
FROM products;
```

#### Handling NULL Values
If a product has no discount, the `discount` column contains NULL. We can replace this with `0.00` using `COALESCE`.

```sql
SELECT 
    product_name,
    price,
    discount,
    -- Provide default value of 0.00 if discount is null
    COALESCE(discount, 0.00) AS clean_discount,
    -- Calculate net price safely
    price - COALESCE(discount, 0.00) AS net_price
FROM products;
```

We can use `NULLIF` to prevent division errors. For example, if we want to calculate price-to-discount ratio:

```sql
SELECT 
    product_name,
    -- If discount is 0, NULLIF returns NULL. Division by NULL yields NULL instead of throwing an error.
    price / NULLIF(COALESCE(discount, 0.00), 0.00) AS price_discount_ratio
FROM products;
```

#### Working with Timestamps and Formatting
```sql
SELECT 
    product_name,
    created_at,
    -- Current server timestamp
    NOW() AS current_system_time,
    -- Format timestamp as readable string
    TO_CHAR(created_at, 'Day, DD Month YYYY') AS formatted_creation,
    TO_CHAR(created_at, 'YYYY-MM-DD HH24:MI:SS') AS standard_creation_format
FROM products;
```

## Summary
- **Scalar functions** operate on individual row values and return a single, transformed value.
- Use **UPPER**, **LOWER**, **LENGTH**, and **SUBSTRING** to manage string data and enforce case-insensitive search logic.
- Use **ROUND** to format decimals, and **CAST** (or `::`) to convert data types explicitly.
- Use **COALESCE** to provide fallback values for NULL, and **NULLIF** to intercept problematic inputs (like zero-values).
- Use **NOW()** to capture transaction times, and **TO_CHAR** to format dates, times, and numbers into human-readable strings.

## Additional Resources
- [PostgreSQL Documentation on String Functions](https://www.postgresql.org/docs/current/functions-string.html)
- [PostgreSQL Documentation on Date/Time Formatting Functions](https://www.postgresql.org/docs/current/functions-formatting.html)
- [PostgreSQL Conditional Expressions (COALESCE/NULLIF)](https://www.postgresql.org/docs/current/functions-conditional.html)
