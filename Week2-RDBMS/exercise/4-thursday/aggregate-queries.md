# Lab: Constructing SQL Aggregate Queries

## Learning Objectives
- Consolidate records using mathematical functions (`COUNT`, `SUM`, `AVG`, `MIN`, `MAX`).
- Segment query outputs across groups using the `GROUP BY` clause.
- Filter consolidated calculations using the `HAVING` clause.
- Sort aggregated results to rank items.

---

## Setup Instructions
1. Open DBeaver and connect to your local PostgreSQL instance.
2. Open a new SQL Console editor.
3. Ensure your e-commerce tables (`customers`, `orders`, `products`, `order_items`) from Tuesday and Wednesday are created and populated.

---

## Step-by-Step Tasks

Write and execute the following 5 distinct aggregate queries:

### Query 1: Overall Order Metrics
Find the total number of orders placed in the system, and calculate the overall average order amount.

---

### Query 2: Order Summaries Per Customer
List each customer's ID and name along with the total count of orders they have placed. Sort the results in descending order by the number of orders.

---

### Query 3: High-Value Customers (HAVING)
Find the customer IDs and names of clients who have spent more than `$150.00` in total across all their orders.

---

### Query 4: Inventory Analysis (MIN / MAX / AVG)
Write a query against your products table that returns:
-   The lowest product price.
-   The highest product price.
-   The average product price.
-   The total number of unique products.

---

### Query 5: Top-Selling Products
Find the top 3 product names by total quantity sold. Combine `GROUP BY` on products and sort by the sum of quantity.

---

## Definition of Done
-   An SQL script file containing the five aggregate query statements.
-   Each query runs successfully in DBeaver against your seeded tables.
-   The results returned match the logical conditions (e.g. Query 5 returns exactly 3 products ranked in descending order by quantity).
