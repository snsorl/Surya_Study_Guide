# AI-Assisted Query Translation: Dialects and Validation

## Learning Objectives
- Translate SQL queries between database dialects (PostgreSQL to Redshift to DynamoDB expressions) using AI assistants.
- Identify syntax and function differences between relational SQL and NoSQL API models.
- Apply validation techniques to verify translated queries for correctness and performance.
- Evaluate the risk of AI hallucinations when translating query structures.

## Why This Matters
When migrating applications between databases (such as converting a relational PostgreSQL database to Amazon DynamoDB or Amazon Redshift), developers must rewrite their existing database queries. This process can be tedious because each database uses a different dialect, query API, or data access pattern.

AI assistants can help automate query translation, saving time during migrations. However, query translation is not a simple search-and-replace task. Each database engine has different performance limits, data models, and query execution rules. If an AI generates a translated query that is syntactically valid but runs a full-table scan, it can crash your production database. Understanding how to prompt AI assistants for query translation and how to validate their output is essential for database migrations.

## The Concept

### Syntax Differences Across Dialects
Querying data differs significantly across PostgreSQL, Redshift, and DynamoDB:

1. **PostgreSQL**: Standard SQL. Supports complex joins, recursive CTEs (Common Table Expressions), PL/pgSQL procedural functions, and fine-grained transaction control.
2. **Amazon Redshift**: Analytical SQL. Derived from PostgreSQL but lacks support for procedural triggers or row-level constraints. It introduces custom analytical functions (e.g., `LISTAGG`) and requires distribution keys to optimize joins.
3. **Amazon DynamoDB**: NoSQL API. Does not support SQL queries or table joins. You retrieve items using explicit API expressions (like `GetItem` or `Query`) and filter results using JSON-formatted conditions.

---

### The Translation and Validation Workflow
To translate database queries securely using AI assistance, follow this validation pipeline:

```
+---------------------------------------------------------+
|                  1. Source Query Input                  |
|  - Provide the original working SQL query               |
|  - Document the source database engine and version      |
+---------------------------------------------------------+
                             |
                             v
+---------------------------------------------------------+
|                 2. AI Translation Prompt                |
|  - Specify target dialect and schema structure          |
|  - Anchor target version numbers                        |
+---------------------------------------------------------+
                             |
                             v
+---------------------------------------------------------+
|                 3. Validation Check                     |
|  - Dry run the query in a local container environment    |
|  - Check for scan operations and index utilization      |
+---------------------------------------------------------+
```

---

### Risks of AI-Generated Dialect Translation
When validating AI-translated queries, watch for these common issues:
- **Join Hallucinations on NoSQL**: AI assistants often try to translate SQL queries containing joins directly into DynamoDB query expressions, suggesting APIs that do not exist. To resolve this, you must denormalize the tables or rewrite the logic to execute sequential queries in your application code.
- **Outdated Function Mapping**: AI might translate PostgreSQL date functions into Redshift using outdated syntax, causing syntax errors.
- **Query Performance Blindspots**: An AI model might translate a SQL filter condition into a DynamoDB `FilterExpression`. While syntactically correct, this runs a slow, expensive scan operation instead of an index-based query, consuming all your read capacity.

## Code Examples

Let us walk through an example where we translate a PostgreSQL query into both Amazon Redshift and Amazon DynamoDB.

### Source Query: PostgreSQL SQL
This query joins two tables to find active orders for a customer:

```sql
SELECT o.order_id, o.order_date, c.customer_name
FROM orders o
JOIN customers c ON o.customer_id = c.id
WHERE o.customer_id = 'CUST-9910' AND o.status = 'COMPLETED';
```

---

### Translation 1: Target Amazon Redshift (OLAP SQL)
Redshift uses standard SQL, but we must optimize the tables using distribution keys.

#### AI-Generated Redshift DDL and SQL:
```sql
-- DDL configured for Redshift MPP
CREATE TABLE customers_rs (
    id VARCHAR(50) PRIMARY KEY,
    customer_name VARCHAR(100)
) DISTSTYLE ALL; -- Duplicate lookup table to all nodes

CREATE TABLE orders_rs (
    order_id INT PRIMARY KEY,
    customer_id VARCHAR(50) REFERENCES customers_rs(id),
    order_date DATE,
    status VARCHAR(20)
) DISTSTYLE KEY DISTKEY(customer_id); -- Partition by join key

-- Optimized Query
SELECT o.order_id, o.order_date, c.customer_name
FROM orders_rs o
JOIN customers_rs c ON o.customer_id = c.id
WHERE o.customer_id = 'CUST-9910' AND o.status = 'COMPLETED';
```

*Validation Check*: **Pass**. The join runs locally because `orders_rs` is partitioned on the join key (`customer_id`) and the small `customers_rs` table is duplicated on all compute nodes, avoiding network bottlenecks.

---

### Translation 2: Target Amazon DynamoDB (NoSQL API)
DynamoDB does not support joins. The AI must denormalize the data structure.

#### AI-Generated Prompt and DynamoDB JSON Query:
To support the query pattern, the `customer_name` must be denormalized directly inside the order record.

The target table `orders-table` has Partition Key `customer_id` and Sort Key `order_id`.

```bash
# Querying DynamoDB using the partition key (returns in 2ms)
aws dynamodb query \
    --table-name "orders-table" \
    --key-condition-expression "customer_id = :cid" \
    --filter-expression "order_status = :status" \
    --expression-attribute-values '{
        ":cid": {"S": "CUST-9910"},
        ":status": {"S": "COMPLETED"}
    }'
```

#### Validation Check:
- **Keys**: The query targets the partition key (`customer_id`) in the key condition. (Pass)
- **Filters**: The query filters on `order_status` post-retrieval. Since this is a non-key attribute, using a `FilterExpression` is correct. (Pass)
- **Data Model**: The query retrieves the denormalized `customer_name` directly from the order item, bypassing the need for a join. (Pass)

## Summary
- Query translation requires matching the **data structure and query execution models** of the target database, not just translating syntax.
- **PostgreSQL** supports standard relational SQL; **Redshift** requires distribution key configurations to optimize joins; **DynamoDB** requires denormalizing data to bypass joins.
- Prevent translation errors by **anchoring prompts with target versions** and **explicitly listing performance constraints**.
- Always validate AI-translated queries using **dry runs** in test environments and check execution plans to confirm they use indexes.

## Additional Resources
- [AWS database Migration Service: Schema Conversion Tool](https://aws.amazon.com/dms/schema-conversion-tool/)
- [SQL Translation Guide for Amazon Redshift](https://docs.aws.amazon.com/redshift/latest/dg/c_SQL_commands.html)
- [How to Model and Query Data in DynamoDB - Alex DeBrie Tutorial](https://www.dynamodbbook.com/)
