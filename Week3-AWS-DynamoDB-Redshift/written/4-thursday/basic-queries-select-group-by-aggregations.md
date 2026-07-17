# Analytical Queries on Amazon Redshift

## Learning Objectives
- Write analytical SQL queries utilizing aggregations and groupings.
- Explain the evaluation differences between WHERE and HAVING clauses.
- Implement date and string manipulation extensions specific to Amazon Redshift.
- Optimize query performance by evaluating execution plans inside Redshift.

## Why This Matters
Operational databases (like PostgreSQL) are optimized to retrieve single records or small datasets quickly. However, business decisions require analyzing large datasets: calculating total sales by region, identifying the top 10 products by profit margin, or tracking user engagement trends over time.

Amazon Redshift is optimized for these analytical workloads. It uses standard SQL syntax but leverages columnar storage and distributed compute nodes to process queries over millions of rows quickly. Understanding how to write efficient analytical queries—including using aggregate functions, grouping results, and leveraging Redshift's date and string extensions—is essential for generating business intelligence reports and optimizing data warehouse performance.

## The Concept

### Aggregate Functions and Grouping
Analytical queries summarize data across multiple rows. This is done using **Aggregate Functions**:
- **SUM(column)**: Calculates the total value of a numeric column.
- **AVG(column)**: Calculates the average value of a numeric column.
- **COUNT(column)**: Calculates the number of rows or non-null values.
- **MIN(column) / MAX(column)**: Finds the smallest or largest value in a column.

#### The GROUP BY Clause
To apply aggregate calculations to specific categories, use the **`GROUP BY`** clause. When executing a query with aggregates, **every non-aggregated column in the `SELECT` list must be included in the `GROUP BY` clause**.

### Filtering Aggregates: WHERE vs. HAVING
A common SQL mistake is trying to filter aggregate results using the `WHERE` clause:
- **WHERE Clause**: Filters individual data rows **before** any grouping or aggregate calculations occur. It cannot reference aggregate functions (e.g., `WHERE SUM(sales) > 1000` is invalid).
- **HAVING Clause**: Filters group results **after** the `GROUP BY` clause and aggregate calculations are complete. Use `HAVING` to filter aggregated metrics (e.g., `HAVING SUM(sales) > 1000`).

---

### Redshift-Specific SQL Extensions
While Redshift is PostgreSQL-compatible, it includes custom SQL extensions designed to simplify time-series analysis and string manipulations in data warehouses:

#### 1. DATE_PART and DATE_TRUNC
Used to extract specific date units (year, month, day, quarter) or truncate timestamps to a specific precision:
- `DATE_PART('quarter', order_date)`: Returns the quarter number (1-4).
- `DATE_TRUNC('month', order_date)`: Truncates a date to the first day of that month (e.g., `2026-07-14` becomes `2026-07-01`).

#### 2. LISTAGG
An aggregate function that combines string values from multiple rows into a single, delimited list:
- `LISTAGG(product_name, ', ') WITHIN GROUP (ORDER BY product_name)`: Concatenates product names into a comma-separated string for each group.

## Code Examples

### Setting up the Star Schema
We will run queries against a sales data warehouse schema containing a central fact table and dimension tables.

```sql
-- Fact Table
CREATE TABLE fact_transactions (
    transaction_id INT,
    product_key INT,
    customer_key INT,
    store_key INT,
    sale_date TIMESTAMP,
    quantity_sold INT,
    revenue DECIMAL(12, 2)
);

-- Dimension Table
CREATE TABLE dim_products_analysis (
    product_key INT PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50),
    price DECIMAL(10, 2)
);
```

### Example 1: Basic Aggregation and Grouping
This query calculates the total quantity sold, average revenue, and total revenue for each product category:

```sql
SELECT 
    p.category,
    SUM(t.quantity_sold) AS total_units_sold,
    ROUND(AVG(t.revenue), 2) AS average_order_value,
    SUM(t.revenue) AS total_category_revenue
FROM fact_transactions t
JOIN dim_products_analysis p ON t.product_key = p.product_key
GROUP BY p.category
ORDER BY total_category_revenue DESC;
```

### Example 2: Filtering with HAVING
Suppose we only want to display product categories that generated more than $10,000.00 in total revenue:

```sql
SELECT 
    p.category,
    SUM(t.revenue) AS total_revenue
FROM fact_transactions t
JOIN dim_products_analysis p ON t.product_key = p.product_key
GROUP BY p.category
HAVING SUM(t.revenue) > 10000.00 -- Filter group results
ORDER BY total_revenue DESC;
```

### Example 3: Redshift Date Extensions (Quarterly Reporting)
This query calculates total sales revenue by product category for each quarter of the year:

```sql
SELECT 
    p.category,
    DATE_PART('year', t.sale_date) AS sales_year,
    DATE_PART('quarter', t.sale_date) AS sales_quarter,
    SUM(t.revenue) AS quarterly_revenue
FROM fact_transactions t
JOIN dim_products_analysis p ON t.product_key = p.product_key
GROUP BY p.category, sales_year, sales_quarter
ORDER BY sales_year, sales_quarter, quarterly_revenue DESC;
```

### Example 4: Using LISTAGG for String Aggregation
Suppose we want to generate a report showing each product category and a list of all products belonging to that category:

```sql
SELECT 
    category,
    LISTAGG(product_name, ' | ') WITHIN GROUP (ORDER BY product_name) AS product_catalog
FROM dim_products_analysis
GROUP BY category;
```

Output:
```text
---------------------------------------------------------------------
| category    | product_catalog                                     |
+-------------+-----------------------------------------------------+
| Electronics | Bluetooth Keyboard | Ergonomic Chair | USB-C Hub    |
| Furniture   | Office Desk | Standing Desk                         |
+-------------+-----------------------------------------------------+
```

## Summary
- **Aggregate functions** (`SUM`, `AVG`, `COUNT`) summarize data rows, and the **GROUP BY** clause partitions calculations across category columns.
- The **WHERE** clause filters rows before grouping occurs; the **HAVING** clause filters group results after aggregations are calculated.
- Use Redshift-specific functions like **DATE_PART** and **DATE_TRUNC** to simplify time-series reporting.
- Use **LISTAGG** to concatenate string attributes from multiple rows into a single delimited text block.

## Additional Resources
- [Amazon Redshift SQL Reference - Aggregate Functions](https://docs.aws.amazon.com/redshift/latest/dg/r_Aggregate_Functions.html)
- [PostgreSQL GROUP BY and HAVING Clause Syntax](https://www.postgresql.org/docs/current/queries-table-expressions.html#QUERIES-GROUP)
- [Redshift Date and Time Functions Reference Guide](https://docs.aws.amazon.com/redshift/latest/dg/Date_functions_header.html)
