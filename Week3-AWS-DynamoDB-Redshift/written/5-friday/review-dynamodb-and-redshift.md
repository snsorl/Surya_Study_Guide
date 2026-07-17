# Service Selection: Amazon DynamoDB vs. Amazon Redshift

## Learning Objectives
- Compare Amazon DynamoDB and Amazon Redshift across operational paradigms.
- Analyze the differences between NoSQL key-value lookups and SQL analytical aggregation queries.
- Evaluate business requirements using a database selection decision tree.
- Select the appropriate database engine based on performance, capacity, and cost constraints.

## Why This Matters
As you build applications in the cloud, you must select the appropriate database services for your workloads. Both Amazon DynamoDB and Amazon Redshift are managed database engines, but they are built for completely different tasks. 

If you use DynamoDB to run analytical reports, the query scans will consume all your capacity and lead to high cloud bills. Conversely, using Redshift to process real-time user checkout transactions will result in slow performance due to transactional latency. Understanding the operational tradeoffs between DynamoDB and Redshift is essential for building fast, cost-effective cloud architectures.

## The Concept

### Operational Paradigms: OLTP vs. OLAP
The primary difference between DynamoDB and Redshift lies in their operational workloads:

- **Amazon DynamoDB**: An **Online Transaction Processing (OLTP)** database. It is designed to handle high volumes of concurrent read and write operations on individual records with single-digit millisecond latency. It uses a serverless, NoSQL document/key-value data model.
- **Amazon Redshift**: An **Online Analytical Processing (OLAP)** database. It is designed to run complex, long-running queries that aggregate and analyze large historical datasets. It uses a columnar storage model and is organized using a clustered relational SQL schema.

---

### Comparative Review Matrix
This matrix summarizes the differences between DynamoDB and Redshift:

| Operational Metric | Amazon DynamoDB | Amazon Redshift |
|---|---|---|
| **Database Class** | NoSQL Key-Value / Document | Relational Data Warehouse |
| **Operational Goal** | High-throughput, low-latency writes/reads | Complex historical aggregations and reporting |
| **Scaling Model** | Serverless (Autopartitioning scaling) | Clustered compute nodes (RA3 instances) |
| **Query Latency** | 2 to 9 milliseconds (constant) | Seconds to hours (batch aggregates) |
| **Data Storage Layout**| Row-level partition clustering | Column-oriented database slices |
| **Joins / Relations** | None (Nesting or denormalization required) | Supported (Optimized using distribution keys) |
| **Primary Cost Metric**| Capacity Units (RCUs/WCUs) consumed | Provisioned compute node run hours |

---

### The Selection Decision Tree
Use this decision framework to select the appropriate database service for your application requirements:

```
                            [Start Data Requirement]
                                       |
                     Is the primary workload Transactional (OLTP) 
                       or Analytical Aggregation (OLAP)?
                                      / \
                       (Transactional)   (Analytical)
                             /                   \
                            v                     v
                Is schema structure rigid           [Amazon Redshift]
                or dynamic/polymorphic?
                          / \
               (Dynamic) /   \ (Rigid)
                        v     v
           [Amazon DynamoDB]  [Amazon RDS (PostgreSQL)]
```

#### Selection Scenarios:
1. **IoT Sensor Log Collection**:
   - *Requirement*: Thousands of write requests per second from device sensors. Data is structured as simple time-series entries.
   - *Selection*: **Amazon DynamoDB**. Its horizontal write capacity handles high-frequency writes without transaction bottlenecks.
2. **Monthly Financial Audits**:
   - *Requirement*: Querying a year of sales transaction records (100 million rows) to calculate revenue totals and average order values by region.
   - *Selection*: **Amazon Redshift**. Its columnar storage and parallel compute nodes process aggregate queries quickly, preventing performance degradation on transactional databases.
3. **E-Commerce Shopping Cart**:
   - *Requirement*: Storing active cart contents for users. Items are updated frequently by the user and must be retrieved instantly during checkout.
   - *Selection*: **Amazon DynamoDB**. Key-value retrieval using the `user_id` partition key ensures single-digit millisecond access.

## Code Examples

Let us look at how the same sales data is stored and queried differently across the two services.

### DynamoDB Schema and Query (Transactional)
In DynamoDB, we store individual sales transactions as separate items. We query a single customer's orders using the partition key:

```bash
# Querying a specific customer's orders (returns in 5ms)
aws dynamodb query \
    --table-name "store-sales" \
    --key-condition-expression "customer_id = :cid" \
    --expression-attribute-values '{
        ":cid": {"S": "CUST-1002"}
    }'
```

### Redshift Schema and Query (Analytical)
In Redshift, we store transactions in a fact table and query total sales across all customers, grouping the results by product category:

```sql
-- Aggregating sales revenue across millions of rows (returns in seconds)
SELECT 
    p.product_category,
    SUM(s.quantity) AS total_units_sold,
    SUM(s.sale_amount) AS total_revenue
FROM fact_sales s
JOIN dim_products p ON s.product_key = p.product_key
GROUP BY p.product_category
ORDER BY total_revenue DESC;
```

If run on Redshift, the query engine scans only the `product_key`, `quantity`, and `sale_amount` columns, executing the aggregation in parallel across all compute node slices.

## Summary
- **Amazon DynamoDB** is a serverless, NoSQL OLTP database optimized for low-latency, high-concurrency transactional writes and reads.
- **Amazon Redshift** is a clustered, relational OLAP database optimized for running complex aggregate queries over historical datasets.
- Use **DynamoDB** to manage active user states, cart systems, and high-frequency time-series inputs.
- Use **Redshift** to build data warehouses, run business intelligence reports, and aggregate data from multiple systems.

## Additional Resources
- [Amazon Web Services - Choosing the Right Database](https://aws.amazon.com/products/databases/determine-database-type/)
- [Amazon DynamoDB Core Design Principles](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.html)
- [Amazon Redshift Clusters and Nodes Performance Reference](https://docs.aws.amazon.com/redshift/latest/dg/c_high_level_system_architecture.html)
