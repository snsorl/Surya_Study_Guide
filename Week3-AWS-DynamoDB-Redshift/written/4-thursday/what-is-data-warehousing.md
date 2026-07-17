# Introduction to Data Warehousing

## Learning Objectives
- Define a data warehouse and explain its purpose in enterprise decision-making.
- Analyze the characteristics of historical data storage.
- Contrast Extract-Transform-Load (ETL) and Extract-Load-Transform (ELT) data integration pipelines.
- Differentiate between Star Schema and Snowflake Schema designs for analytical databases.

## Why This Matters
Operational databases (like PostgreSQL or DynamoDB) are optimized to handle real-time transactions: creating customer accounts, checking out shopping carts, and updating inventory levels. These databases must respond instantly. However, if a business analyst runs a query that aggregates five years of sales data to identify buying trends, the query will slow down the operational database, blocking active users.

To solve this, organizations use **Data Warehouses**. A data warehouse is a separate database optimized specifically for aggregate, historical analysis. By copying data from operational databases into a warehouse using ETL or ELT pipelines, businesses can run complex analytics queries over billions of rows without slowing down production applications. Understanding data warehousing fundamentals is essential for designing modern data architectures.

## The Concept

### What is a Data Warehouse?
A data warehouse is a centralized repository that stores integrated data from multiple, disparate sources (e.g., transactional databases, CRM systems, web logs) for analysis and reporting. 

#### Core Characteristics:
- **Subject-Oriented**: Organized around key business subjects (such as Sales, Inventory, or Customers) rather than business processes (like checkout transactions).
- **Integrated**: Combines data from different sources into a standardized format. (e.g., converting different gender codes like 'M'/'F' and 'Male'/'Female' to a single standard).
- **Time-Variant**: Stores historical data over a long duration (typically 5 to 10 years), allowing you to identify trends over time. Transactional databases, by contrast, typically keep only the current state of data.
- **Non-Volatile**: Data in a warehouse is read-only. Once written, records are rarely updated or deleted. This ensures historical consistency for reporting.

---

### Data Pipelines: ETL vs. ELT

To move data from operational systems into a warehouse, engineers build data pipelines:

```
            ETL Pipeline (Traditional)
+---------+     +-----------+     +---------+
| Source  |====>| Transform |====>| Target  |
| Systems |     |  Server   |     | Warehouse|
+---------+     +-----------+     +---------+

             ELT Pipeline (Modern Cloud)
+---------+     +-----------+     +---------+
| Source  |====>| Target    |====>| Transform|
| Systems |     | Warehouse |     | Cluster |
+---------+     +-----------+     +---------+
```

#### Extract-Transform-Load (ETL)
Data is extracted from source databases, transformed (cleaned, formatted, joined) on a separate staging server, and then loaded into the target data warehouse.
- *Use Case*: Traditional systems with limited database storage or when data must be anonymized before leaving secure zones.

#### Extract-Load-Transform (ELT)
Data is extracted from source systems, loaded directly into the target data warehouse in its raw form, and then transformed using the warehouse's compute power.
- *Use Case*: Modern cloud systems (like AWS) where storage is cheap (using S3 as a data lake) and the warehouse cluster (like Redshift) is optimized to perform transformations at scale.

---

### Analytical Schemas: Star vs. Snowflake
Data warehouses organize tables into dimensional models rather than normalized transactional schemas. The two primary designs are:

#### 1. Star Schema
The simplest and most common design. It consists of a central **Fact Table** surrounded by **Dimension Tables**. The diagram resembles a star shape.
- **Fact Table**: Contains quantitative metrics (e.g., `sale_amount`, `quantity_ordered`) and foreign keys linking to dimension tables.
- **Dimension Tables**: Contain descriptive context attributes (e.g., `customer_name`, `product_category`, `store_location`). Dimension tables are **denormalized** (they do not link to other tables).
- *Pros*: Simple query logic, fast joins, optimized read performance.

#### 2. Snowflake Schema
A variation of the star schema where **dimension tables are normalized**, splitting out sub-dimensions into separate tables. For example, a `product` dimension table links to a normalized `category` table.
- *Pros*: Reduces data redundancy, saving storage space.
- *Cons*: Requires more complex query joins, which can slow down analytical queries.

## Code Examples

Let us look at SQL schemas that illustrate the difference between a Star Schema and a Snowflake Schema.

### Example 1: Star Schema Design (Denormalized Dimensions)
In a star schema, all product details are stored in a single table, even if it causes data duplication:

```sql
-- Dimension Table (Denormalized)
CREATE TABLE dim_products (
    product_key INT PRIMARY KEY,
    product_name VARCHAR(100),
    category_name VARCHAR(50),      -- Stored directly here
    manufacturer_name VARCHAR(50)  -- Stored directly here
);

-- Central Fact Table
CREATE TABLE fact_sales (
    sales_key INT PRIMARY KEY,
    date_key INT,
    product_key INT REFERENCES dim_products(product_key),
    quantity_sold INT,
    revenue DECIMAL(10, 2)
);

-- Analytical Query (Requires only 1 Join to retrieve category sales)
SELECT p.category_name, SUM(s.revenue) AS total_revenue
FROM fact_sales s
JOIN dim_products p ON s.product_key = p.product_key
GROUP BY p.category_name;
```

### Example 2: Snowflake Schema Design (Normalized Dimensions)
In a snowflake schema, the product attributes are normalized into separate tables:

```sql
-- Sub-Dimension Table
CREATE TABLE dim_categories (
    category_key INT PRIMARY KEY,
    category_name VARCHAR(50)
);

-- Main Dimension Table (Normalized)
CREATE TABLE dim_products_snowflake (
    product_key INT PRIMARY KEY,
    product_name VARCHAR(100),
    category_key INT REFERENCES dim_categories(category_key) -- Linked reference
);

-- Central Fact Table
CREATE TABLE fact_sales_snowflake (
    sales_key INT PRIMARY KEY,
    product_key INT REFERENCES dim_products_snowflake(product_key),
    revenue DECIMAL(10, 2)
);

-- Analytical Query (Requires 2 Joins to retrieve category sales)
SELECT c.category_name, SUM(s.revenue) AS total_revenue
FROM fact_sales_snowflake s
JOIN dim_products_snowflake p ON s.product_key = p.product_key
JOIN dim_categories c ON p.category_key = c.category_key
GROUP BY c.category_name;
```

## Summary
- A **data warehouse** is a read-only repository of historical, integrated data optimized for Online Analytical Processing (OLAP).
- **ETL** transforms data on a staging server before loading; **ELT** loads raw data directly and transforms it inside the warehouse.
- **Star Schema** uses denormalized dimension tables to enable fast, simple query joins.
- **Snowflake Schema** normalizes dimension tables to reduce redundancy, increasing query join complexity.

## Additional Resources
- [The Data Warehouse Toolkit by Ralph Kimball - Dimensional Modeling Guide](https://www.kimballgroup.com/data-warehouse-business-intelligence-resources/books/data-warehouse-toolkit/)
- [AWS Data Warehousing Overview and Best Practices](https://aws.amazon.com/data-warehouse/)
- [ETL vs ELT: Comparing Data Integration Strategies - Fivetran](https://fivetran.com/blog/etl-vs-elt)
