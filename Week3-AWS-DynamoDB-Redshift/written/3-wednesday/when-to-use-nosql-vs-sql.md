# When to Use NoSQL vs. SQL: A Decision Framework

## Learning Objectives
- Formulate a decision framework to choose between SQL and NoSQL databases.
- Compare structured and unstructured data requirements.
- Distinguish between strong consistency and eventual consistency models.
- Explain the CAP Theorem and its three dimensions: Consistency, Availability, and Partition Tolerance.

## Why This Matters
As a database developer, you must decide which database engine to use for new applications. Relational databases (SQL) and non-relational databases (NoSQL) are not competing solutions. Instead, they are optimized for different workloads. 

Choosing the wrong database type has significant consequences. Using SQL for high-concurrency IoT logging can crash your servers during traffic spikes. Conversely, using NoSQL for complex accounting ledgers can lead to double-spending bugs due to eventual consistency delays. Understanding the tradeoffs of SQL and NoSQL—guided by data structure, scaling needs, consistency rules, and the CAP theorem—allows you to build fast, reliable systems.

## The Concept

### The SQL vs. NoSQL Decision Parameters
When selecting a database architecture, engineers evaluate five primary dimensions:

#### 1. Data Structure and Relationships
- **SQL (Relational)**: Optimized for structured tables with strict schemas. Relationships between entities are defined by foreign keys, and queries combine data across tables using joins.
- **NoSQL**: Optimized for unstructured, semi-structured, or polymorphic data (e.g., records with different attributes). Relationships are modeled by nesting data (document nesting) or duplicating data (denormalization) rather than joining tables.

#### 2. Schema Flexibility
- **SQL**: Rigid schema. Table columns, data types, and constraints must be declared before inserting data. Changes to the schema require running `ALTER TABLE` migrations, which can lock tables in production.
- **NoSQL**: Dynamic schema (schema-less). Items within the same table can contain different attributes. Fields can be added to individual items without running database migrations.

#### 3. Scaling Model
- **SQL**: Vertical scaling (scaling up). To handle more traffic, you upgrade the database server’s hardware (more CPU, RAM, or storage). Horizontal scaling (sharding) is complex because it is difficult to maintain relational constraints and transactions across network nodes.
- **NoSQL**: Horizontal scaling (scaling out). Databases are designed to split (partition) data across a cluster of servers automatically, allowing you to add small servers to handle traffic growth.

#### 4. Consistency Model
- **SQL**: Strong consistency (ACID guarantees). Once a transaction commits, all subsequent reads see the updated data.
- **NoSQL**: Eventual consistency (BASE model - Basically Available, Soft state, Eventual consistency). Writes are replicated across server clusters asynchronously. A query run immediately after a write might return the old value for a brief period before the update propagates across all nodes.

---

### The CAP Theorem
Formulated by Eric Brewer, the CAP Theorem states that a distributed data system can guarantee at most **two out of three** properties simultaneously:

```
                  Consistency (C)
                        /\
                       /  \
                      /    \
                     /  CA  \
                    /        \
                   /__________\
   Availability (A)            Partition Tolerance (P)
                    (CP or AP)
```

1. **Consistency (C)**: Every read query returns the most recent write or an error.
2. **Availability (A)**: Every non-failing node returns a response for every request (no errors, but no guarantee it contains the most recent write).
3. **Partition Tolerance (P)**: The system continues to operate despite network partitions (communication failures between nodes).

#### The CAP Tradeoff:
In a physical network, network partitions (P) are unavoidable because connection drops and hardware failures will happen. Therefore, when a partition occurs, a distributed database must choose between:
- **CP (Consistency + Partition Tolerance)**: The database blocks writes and reads on isolated nodes to prevent inconsistent data states, sacrificing Availability (A).
- **AP (Availability + Partition Tolerance)**: The database allows nodes to process writes and reads independently, sacrificing Consistency (C). Nodes will synchronize once the network partition is resolved.

---

### Decision Framework Table
Use this matrix to guide your database selections:

| Use Case Indicator | Choose SQL (e.g., PostgreSQL) | Choose NoSQL (e.g., DynamoDB) |
|---|---|---|
| **Data Relationships** | Complex, multi-table relationships. | Hierarchical, isolated, or key-value access. |
| **Transaction Pattern** | Strict, multi-table transactions (ACID). | Single-item updates or simple operations. |
| **Traffic Characteristics** | Predictable read/write volume. | High-frequency, unpredictable spikes. |
| **Schema Stability** | Stable, structured, predefined. | Dynamic, evolving, polymorphic. |
| **Analysis Requirements**| Heavy reporting, joins, and aggregates. | Operational lookups (analytical data to Redshift). |

## Code Examples

### Query Contrast: Schema Enforcement

#### SQL PostgreSQL (Enforced Schema)
Attempting to insert a row with an undeclared column will fail:

```sql
-- Table users has columns: user_id, username
INSERT INTO users (user_id, username, location) 
VALUES ('usr-1', 'alice', 'Boston');
-- ERROR: column "location" does not exist
```

#### NoSQL DynamoDB (Flexible Schema)
You can insert items with custom attributes directly without modifying the table schema:

```bash
# Insert Item 1 with 2 attributes
aws dynamodb put-item \
    --table-name developers \
    --item '{"developer_id": {"S": "dev-01"}, "name": {"S": "Alice"}}'

# Insert Item 2 with an additional "location" attribute
aws dynamodb put-item \
    --table-name developers \
    --item '{"developer_id": {"S": "dev-02"}, "name": {"S": "Bob"}, "location": {"S": "Boston"}}'
# Both inserts succeed. The table accepts dynamic structures automatically.
```

## Summary
- **SQL** is optimized for structured data, complex relations, and ACID consistency; it scales vertically.
- **NoSQL** is optimized for semi-structured data, dynamic schemas, and horizontal scaling; it often uses eventual consistency.
- The **CAP Theorem** states that distributed systems must choose between **Consistency** (CP) and **Availability** (AP) during network partitions.
- Choose SQL when your data requires strict integrity constraints and table joins; choose NoSQL when you need high-frequency writes and horizontal scaling.

## Additional Resources
- [The CAP Theorem: Myths and Realities - Eric Brewer paper](https://www.infoq.com/articles/cap-twelve-years-later-how-the-rules-have-changed/)
- [SQL vs NoSQL Database Comparison - AWS Guide](https://aws.amazon.com/compare/the-difference-between-nosql-and-sql/)
- [Designing Data-Intensive Applications - Chapter 2: Data Models by Martin Kleppmann](https://www.oreilly.com/library/view/designing-data-intensive-applications/9781491903063/)
