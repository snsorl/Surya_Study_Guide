# Amazon Redshift Architecture: Clusters, Columnar Storage, and MPP

## Learning Objectives
- Explain the physical cluster architecture of Amazon Redshift, including leader nodes and compute nodes.
- Describe how columnar storage and data compression optimize query performance.
- Explain the mechanics of Massively Parallel Processing (MPP) inside Redshift clusters.
- Compare and select the three primary data distribution styles: EVEN, KEY, and ALL.

## Why This Matters
Analytical databases must process queries over billions of rows to calculate business trends. Traditional databases (like PostgreSQL) run on a single server, which limits their processing capacity. 

Amazon Redshift addresses this by using a distributed cluster architecture. It leverages **Massively Parallel Processing (MPP)**, **Columnar Storage**, and custom **Data Distribution Styles** to split queries across multiple compute nodes and execute them in parallel. Understanding these architectural concepts allows developers to design database tables that minimize network bottlenecks, optimize storage capacity, and run analytical queries over massive datasets quickly.

## The Concept

### Redshift Cluster Architecture
An Amazon Redshift database runs on a distributed cluster of servers. A cluster consists of two primary node types:

```
                          +------------------------+
                          |      SQL Client        |
                          +------------------------+
                                      |
                                      v
                          +------------------------+
                          |      Leader Node       |
                          +------------------------+
                                      |
                   +------------------+------------------+
                   |                                     |
                   v                                     v
        +---------------------+               +---------------------+
        |    Compute Node 1   |               |    Compute Node 2   |
        |  +---------------+  |               |  +---------------+  |
        |  | Slice 1       |  |               |  | Slice 3       |  |
        |  +---------------+  |               |  +---------------+  |
        |  | Slice 2       |  |               |  | Slice 4       |  |
        |  +---------------+  |               |  +---------------+  |
        +---------------------+               +---------------------+
```

#### 1. Leader Node
The leader node acts as the entry point for SQL clients. It manages communications with client applications, parses SQL statements, generates execution plans, coordinates parallel execution across compute nodes, and aggregates query results before sending them back to the client. The leader node does not store user data.

#### 2. Compute Nodes
Compute nodes execute the compiled execution plans received from the leader node. They perform the physical operations (such as scans, joins, and aggregates) and pass intermediate results back to the leader node.
- **Slices**: Compute nodes are partitioned into logical units called **slices**. Each slice is allocated a portion of the node's memory and disk storage, allowing it to process database rows independently.

---

### Massively Parallel Processing (MPP)
Massively Parallel Processing (MPP) is a design pattern where multiple compute nodes execute queries in parallel. In Redshift, each slice on each compute node executes the query on its portion of the data simultaneously. This shared-nothing architecture ensures that as data volume grows, you can maintain query speeds by adding more compute nodes to the cluster.

---

### Columnar Storage and Compression
Redshift stores data on disk column by column rather than row by row. This optimizes analytical queries in two ways:
- **I/O Reduction**: If a query only aggregates a single column (e.g., `revenue`), Redshift reads that column from disk, ignoring all other attributes.
- **High Compression**: Because values in the same column share the same data type, they compress efficiently. Redshift applies specialized compression algorithms (e.g., LZO, Run-Length, AZ64) to reduce the database storage footprint.

---

### Data Distribution Styles
When you write data to a Redshift cluster, the database must decide which compute node slice should store each row. You configure this partition strategy using one of three **Distribution Styles**:

#### 1. EVEN Distribution (Default)
- **Mechanics**: The leader node distributes rows across slices in a round-robin sequence, regardless of column values.
- **Use Case**: Appropriate for tables that do not participate in joins or when you do not have clear query patterns.

#### 2. KEY Distribution
- **Mechanics**: The database hashes values in a specific column (e.g., `customer_id`) to determine the storage slice. All rows with the same hash value are stored on the same compute node slice.
- **Use Case**: Critical for optimization. If you join two tables (like `sales` and `customers`) on `customer_id`, using KEY distribution on `customer_id` for both tables ensures that matching records are stored on the same compute node. This allows the database to join the records locally without transferring data across the cluster network.

#### 3. ALL Distribution
- **Mechanics**: A complete copy of the table is duplicated onto the first slice of every compute node.
- **Use Case**: Recommended for small dimension lookup tables (e.g., a currency exchange table with fewer than 1,000 rows). Duplicating small tables ensures that joins can be executed locally on every compute node, avoiding network transfer overhead.

## Code Examples

Let us write SQL scripts that define Redshift tables using custom compression and distribution configurations.

### Star Schema Definition with Custom Distribution Styles
In this schema, we use **KEY distribution** on the large fact table and the product dimension table to optimize joins, and **ALL distribution** on the small department lookup table to avoid network transfers.

```sql
-- 1. Small Dimension Table (ALL Distribution)
-- Storing a complete copy on every compute node to eliminate join network transfers
CREATE TABLE dim_departments_rs (
    dept_key INT PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL
)
DISTSTYLE ALL; -- Copies table to all nodes

-- 2. Main Dimension Table (KEY Distribution)
-- Rows are partitioned across slices based on product_key
CREATE TABLE dim_products_rs (
    product_key INT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category_name VARCHAR(50) NOT NULL ENCODE AZ64 -- Custom columnar compression
)
DISTSTYLE KEY
DISTKEY(product_key); -- Partition key

-- 3. Central Fact Table (KEY Distribution)
-- Matches the product_key partition to enable local join execution
CREATE TABLE fact_sales_rs (
    sales_key INT PRIMARY KEY,
    product_key INT REFERENCES dim_products_rs(product_key),
    quantity_sold INT ENCODE LZO, -- Columnar compression
    revenue DECIMAL(12, 2) ENCODE AZ64
)
DISTSTYLE KEY
DISTKEY(product_key); -- Matching partition key
```

If we execute a join query between `fact_sales_rs` and `dim_products_rs` on `product_key`, Redshift executes the join locally on each compute node slice. Since matching keys are stored together, the nodes do not need to transfer data across the network, optimizing query performance.

## Summary
- Amazon Redshift uses a distributed architecture consisting of a **Leader Node** (coordination) and **Compute Nodes** (execution).
- **Compute nodes** are partitioned into logical **slices** that process queries in parallel using **Massively Parallel Processing (MPP)**.
- **Columnar storage** reduces disk I/O and enables efficient data compression.
- **Distribution Styles** determine how rows are partitioned across slices: **EVEN** (round-robin), **KEY** (hash-based partition), and **ALL** (duplicate table on all nodes).

## Additional Resources
- [Amazon Redshift System Architecture Guide](https://docs.aws.amazon.com/redshift/latest/dg/c_high_level_system_architecture.html)
- [Selecting the Best Distribution Style in Amazon Redshift](https://docs.aws.amazon.com/redshift/latest/dg/t_Selecting_a_distribution_style.html)
- [Columnar Database Systems - Research Overview](https://db.cs.cmu.edu/papers/2012/columnstores-tutorial.pdf)
