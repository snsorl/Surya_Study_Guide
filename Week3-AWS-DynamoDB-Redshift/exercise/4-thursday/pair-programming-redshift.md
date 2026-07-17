# Collaborative Lab: Redshift Analytical Modeling & Queries

**Exercise Mode:** Thursday Protocol: Collaborative Project (Pair Programming)
**Format:** Driver / Navigator Roles
**Target Engine:** Amazon Redshift
**Estimated Time:** 3–4 hours

---

## Scenario
You and your partner are database engineers at **TechMart**. You have been tasked with building the analytical reporting layer in the company's Amazon Redshift data warehouse. 

You need to:
1. Connect to the Redshift cluster using DBeaver.
2. Design a **Star Schema** with a fact table (`sales_fact`) and a dimension table (`products_dim`).
3. Load test data.
4. Execute analytical reports to identify top-performing product categories.

---

## The Roles

- **Partner A (Driver):** Writes the SQL code, configures DBeaver, and executes the queries on the database.
- **Partner B (Navigator):** Reviews code syntax, checks constraints, guides table distribution strategy, and validates query logic and output data.

*Recommendation:* Swap roles after completing Task 2 so both partners experience both driver and navigator workflows.

---

## Part A: Redshift Schema Setup

### Task 1: Star Schema Tables
Define and create your analytical tables. Since Redshift is a Massively Parallel Processing (MPP) columnar database, you must choose appropriate table distribution styles:
- **`products_dim`:** A small dimension table containing product category metadata. Use `DISTSTYLE ALL` so it is replicated on every compute node.
- **`sales_fact`:** A large transactional sales log table. Use `DISTSTYLE KEY` distributed on the product identifier `sku`.

Create the tables:

```sql
-- Product Dimension Table
CREATE TABLE products_dim (
    sku VARCHAR(50) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    brand VARCHAR(50) NOT NULL
)
DISTSTYLE ALL;

-- Sales Fact Table
CREATE TABLE sales_fact (
    sale_id INT PRIMARY KEY,
    sku VARCHAR(50) REFERENCES products_dim(sku),
    quantity INT NOT NULL,
    price_per_unit DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    sale_date DATE NOT NULL
)
DISTSTYLE KEY
DISTKEY(sku)
SORTKEY(sale_date);
```

### Task 2: Insert Test Data
Insert these sample datasets (since COPY from S3 is covered on Friday, we will use standard inserts for this task):

```sql
INSERT INTO products_dim (sku, product_name, category, brand) VALUES
('SKU-LAP-001', 'ProBook X15 Laptop', 'LAPTOPS', 'TechMart'),
('SKU-LAP-002', 'UltraSlim 13 Laptop', 'LAPTOPS', 'SwiftBook'),
('SKU-MON-001', 'ProDisplay 27" 4K', 'MONITORS', 'TechMart'),
('SKU-MON-002', 'CurvedView 32" QHD', 'MONITORS', 'VueMaster');

INSERT INTO sales_fact (sale_id, sku, quantity, price_per_unit, total_amount, sale_date) VALUES
(1, 'SKU-LAP-001', 2, 1200.00, 2400.00, '2026-07-01'),
(2, 'SKU-MON-001', 5, 550.00, 2750.00, '2026-07-02'),
(3, 'SKU-LAP-002', 1, 850.00, 850.00, '2026-07-02'),
(4, 'SKU-LAP-001', 1, 1200.00, 1200.00, '2026-07-03'),
(5, 'SKU-MON-002', 3, 700.00, 2100.00, '2026-07-04');
```

---

## Part B: Analytical Reports (Collaborative)

**Swap Roles Here:** The Driver becomes the Navigator, and the Navigator becomes the Driver.

Write and execute SQL analytical reports. Review the result sets together and explain what business insights they provide.

### Query 1: Total Sales and Quantity Sold by Product Category
Write a query joining `sales_fact` and `products_dim` to calculate total revenue (`SUM(total_amount)`) and unit sales (`SUM(quantity)`) grouped by `category`.
- *Expected Output:* One row for LAPTOPS and one row for MONITORS.

### Query 2: Product Performance Ranking
Write a query to calculate total sales amount per product (`sku` and `product_name`) and order them from highest revenue to lowest.

### Query 3: Daily Sales Run Rate
Write a query to aggregate total sales amount and quantity sold by `sale_date` (chronological order) to show daily store performance.

---

## Part C: Performance Review Checklist (Navigator Lead)

The Navigator must lead the discussion on these questions, and the partners must document their combined responses:
1. **Columnar Benefit:** Why is columnar storage in Redshift more efficient for running `Query 1` (aggregating prices) than traditional row-oriented databases?
2. **Distribution Strategy:** Why did we distribute `sales_fact` on the `sku` key and replicate `products_dim` on all nodes? How does this choice prevent network shuffles during join operations?

---

## Deliverables
A collaborative repository or design document in your folder containing:
1. `redshift_analytics.sql` containing all table schemas and query reports.
2. `discussion_answers.md` containing the performance review answers from Part C.
3. A statement indicating who performed the Driver and Navigator roles for each part.
