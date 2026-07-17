# Interview Questions: Week 3 — AWS, DynamoDB, & Redshift

> This bank covers all five days of Week 3. Questions follow the 70-25-5 difficulty rule per day.
> Use the hidden answer blocks for self-quizzing. Aim to answer each question before revealing.

---

## Day 1 — Monday: Advanced SQL Objects and Transactions

---

### Beginner — Recall and Definition

---

### Q1: What is a database transaction?
**Keywords:** Transaction, Unit of Work, SQL statements, state change
<details>
<summary>Click to Reveal Answer</summary>

A database transaction is a logical unit of work that contains one or more SQL statements. Either all statements are executed successfully and saved to the database (committed), or none of them are (rolled back), ensuring database consistency.
</details>

---

### Q2: What is the purpose of the COMMIT statement?
**Keywords:** COMMIT, Permanent, Transaction, Write-Ahead Log
<details>
<summary>Click to Reveal Answer</summary>

The `COMMIT` statement saves all modifications made during the current transaction permanently to the database disk and registers them in the transaction logs, making the changes visible to other concurrent transactions.
</details>

---

### Q3: What is the purpose of the ROLLBACK statement?
**Keywords:** ROLLBACK, Revert, Transaction, Undo logs
<details>
<summary>Click to Reveal Answer</summary>

The `ROLLBACK` statement cancels all modifications made during the current active transaction, reverting the database tables to their exact state at the start of the transaction.
</details>

---

### Q4: What is a SAVEPOINT, and when would you use it?
**Keywords:** SAVEPOINT, Partial rollback, Active transaction
<details>
<summary>Click to Reveal Answer</summary>

A `SAVEPOINT` is a marker set within an active transaction. It allows you to perform a partial rollback to that specific point using `ROLLBACK TO savepoint_name` without canceling the entire transaction, which is useful for handling expected errors in complex workflows.
</details>

---

### Q5: What does the ACID acronym stand for?
**Keywords:** Atomicity, Consistency, Isolation, Durability
<details>
<summary>Click to Reveal Answer</summary>

ACID stands for:
- **Atomicity**: All operations in the transaction succeed, or all fail.
- **Consistency**: Transactions move the database from one valid state to another, enforcing all schema constraints.
- **Isolation**: Concurrent transactions execute independently without exposing intermediate states.
- **Durability**: Once committed, transaction changes survive system crashes or power losses.
</details>

---

### Q6: What is a Dirty Read anomaly?
**Keywords:** Uncommitted, Read, Concurrency anomaly, Isolation level
<details>
<summary>Click to Reveal Answer</summary>

A Dirty Read occurs when Transaction A reads modifications made by Transaction B before Transaction B commits. If Transaction B later rolls back, Transaction A has read invalid data that technically never existed permanently.
</details>

---

### Q7: What is a Non-Repeatable Read anomaly?
**Keywords:** Non-Repeatable, Double read, Committed update
<details>
<summary>Click to Reveal Answer</summary>

A Non-Repeatable Read occurs when Transaction A reads a row, and then Transaction B updates or deletes that row and commits. If Transaction A reads the same row again in the same transaction, it sees the updated values or finds the row deleted, resulting in inconsistent reads.
</details>

---

### Q8: What is a Phantom Read anomaly?
**Keywords:** Range query, Inserted rows, Concurrency anomaly
<details>
<summary>Click to Reveal Answer</summary>

A Phantom Read occurs when Transaction A runs a range query (e.g., matching a WHERE condition), and Transaction B inserts new rows that match that condition and commits. When Transaction A re-runs the query, new "phantom" rows appear in the result set.
</details>

---

### Q9: What is a database index, and what is its main structure in relational databases?
**Keywords:** Index, B-Tree, Lookup speed, Binary search tree
<details>
<summary>Click to Reveal Answer</summary>

A database index is a physical structure that improves lookup and data retrieval speed on specific columns. The default and most common index structure is the B-Tree (Balanced Tree), which organizes keys in a sorted hierarchy to allow binary-search-like lookup speeds.
</details>

---

### Q10: What is a Composite Index, and how does the left-prefix rule apply?
**Keywords:** Composite, Multiple columns, Left-prefix rule
<details>
<summary>Click to Reveal Answer</summary>

A composite index is an index built on multiple columns (e.g., `last_name, first_name`). The left-prefix rule dictates that the index can only optimize queries that search using the leftmost column in the index definition. Queries searching only by `first_name` cannot use the index.
</details>

---

### Q11: What is a SQL View?
**Keywords:** View, Saved query, Virtual table, Abstraction
<details>
<summary>Click to Reveal Answer</summary>

A SQL View is a virtual table defined by a saved SELECT query. It does not store physical data on disk (unless materialized); instead, it executes the query dynamically against underlying tables every time the view is referenced in a query.
</details>

---

### Intermediate — Application and Scenario

---

### Q12: Walk through a bank transfer scenario to explain how ACID Atomicity and Consistency prevent data corruption.
**Keywords:** Bank transfer, Rollback, Constraint, Atomicity, Consistency
**Hint:** Imagine a system failure occurs right after deducting funds from Account A but before crediting Account B.
<details>
<summary>Click to Reveal Answer</summary>

In a $100 transfer from Account A to Account B:
- **Atomicity** ensures that if the system crashes after deducting $100 from Account A but before crediting Account B, the entire operation is rolled back. The deduction is undone, preventing money from vanishing.
- **Consistency** ensures that if Account A has a check constraint (`balance >= 0`) and only has $50, the transaction attempts to make the balance -$50, which violates the constraint. The database rejects the transaction, preventing the database from entering an invalid negative balance state.
</details>

---

### Q13: When should you use a Materialized View over a standard View?
**Keywords:** Cache, Performance, Disk space, Manual refresh
**Hint:** Compare execution costs and data freshness requirements.
<details>
<summary>Click to Reveal Answer</summary>

You should use a Materialized View when the underlying query is computationally expensive (e.g., complex multi-table joins or massive aggregate queries) and data does not need to be updated in real-time. Since a Materialized View caches query results on disk, it speeds up query performance at the cost of consuming disk space and requiring scheduled refreshes.
</details>

---

### Q14: Explain the differences between a User-Defined Function (UDF) and a Stored Procedure in PL/pgSQL.
**Keywords:** Transaction control, Return type, CALL, SELECT
**Hint:** Think about how they are called and if they support COMMIT.
<details>
<summary>Click to Reveal Answer</summary>

The primary differences are:
- **Transaction Control**: Stored Procedures support transaction commands like `COMMIT` and `ROLLBACK`. Functions execute inside an outer query transaction and cannot contain transaction controls.
- **Invocation**: Procedures are executed using `CALL procedure_name()`. Functions are executed as part of SQL queries using `SELECT function_name()`.
- **Return Values**: Functions must declare a return type and return a value (or table). Procedures do not require a return value, though they can use `INOUT` parameters to return values.
</details>

---

### Q15: Under what conditions will a composite index on `(zip_code, street_address)` NOT be used by the PostgreSQL query optimizer?
**Keywords:** Left-prefix, WHERE clause, Query planner
**Hint:** Recall the prefix column requirement.
<details>
<summary>Click to Reveal Answer</summary>

The index will NOT be used if:
1. The query's `WHERE` filter only references `street_address` without specifying `zip_code` (violating the left-prefix rule).
2. The query optimizer determines that a full-table scan is faster because the table is small or the query returns a high percentage of the rows in the table.
</details>

---

### Advanced — System and Edge Case

---

### Q16: How does PostgreSQL implement rollback operations under the hood using Write-Ahead Logging (WAL) and MVCC?
**Keywords:** Write-Ahead Logging, Undo logs, MVCC, Transaction ID, rollback
<details>
<summary>Click to Reveal Answer</summary>

PostgreSQL utilizes MVCC (Multi-Version Concurrency Control) and WAL. When a row is modified, PostgreSQL writes the change to the WAL on disk and creates a new version of the row marked with the current Transaction ID (xmin/xmax). If a transaction rolls back, PostgreSQL does not physically delete or rewrite the changed data pages. Instead, it marks the transaction status as "aborted" in the commit log (CLOG). Because of MVCC visibility rules, concurrent and subsequent queries ignore row versions created by aborted Transaction IDs, effectively executing the rollback instantly without having to undo row edits on disk.
</details>

---

## Day 2 — Tuesday: AWS Fundamentals and Services

---

### Beginner — Recall and Definition

---

### Q17: What is Cloud Computing according to the NIST definition?
**Keywords:** NIST, On-demand, Shared pool, Resource provisioning
<details>
<summary>Click to Reveal Answer</summary>

Cloud computing is a model for enabling ubiquitous, convenient, on-demand network access to a shared pool of configurable computing resources (e.g., networks, servers, storage, applications, and services) that can be rapidly provisioned and released with minimal management effort or service provider interaction.
</details>

---

### Q18: What is the difference between private, public, and hybrid cloud models?
**Keywords:** Public, Private, Hybrid, Infrastructure ownership
<details>
<summary>Click to Reveal Answer</summary>

- **Public Cloud**: Third-party providers (like AWS) own and operate the computing resources, which are shared among multiple tenants over the internet.
- **Private Cloud**: Infrastructure is dedicated solely to a single organization, either hosted on-premises or by a third party.
- **Hybrid Cloud**: Combines public and private clouds, allowing data and applications to be shared between them to meet compliance, security, or legacy requirements.
</details>

---

### Q19: Explain the difference between vertical scaling and horizontal scaling.
**Keywords:** Vertical scaling, Horizontal scaling, Hardware size, Instance count
<details>
<summary>Click to Reveal Answer</summary>

- **Vertical Scaling (Scale Up)**: Adding more power (CPU, RAM, storage) to an existing server. It has physical limits and requires downtime.
- **Horizontal Scaling (Scale Out)**: Adding more database or compute instances to a resource pool. It allows for infinite scaling and high availability.
</details>

---

### Q20: What is High Availability (HA) in cloud architecture?
**Keywords:** High Availability, Failover, Redundancy, Availability Zones
<details>
<summary>Click to Reveal Answer</summary>

High Availability (HA) refers to designing system infrastructure to ensure operational continuity and minimize downtime. This is achieved by introducing redundancy (running duplicate instances) and implementing automated failover mechanisms across separate physical locations (Availability Zones).
</details>

---

### Q21: Define the three primary cloud service models (IaaS, PaaS, SaaS) and give an AWS example for each.
**Keywords:** IaaS, PaaS, SaaS, Responsibility level, AWS EC2, Elastic Beanstalk
<details>
<summary>Click to Reveal Answer</summary>

- **IaaS (Infrastructure as a Service)**: Provides raw compute, storage, and networking. The user manages the OS and software. *AWS Example*: EC2.
- **PaaS (Platform as a Service)**: Provides managed hardware and software execution environments. The user only manages code and data. *AWS Example*: Elastic Beanstalk (or RDS).
- **SaaS (Software as a Service)**: Provides a fully managed software application accessed over the internet. The user only configures settings. *AWS Example*: Amazon WorkMail (or external apps like Salesforce).
</details>

---

### Q22: What is an AWS Region?
**Keywords:** Region, Geographic area, Latency, Compliance
<details>
<summary>Click to Reveal Answer</summary>

An AWS Region is a physical, geographic location containing multiple, isolated, and physically separated Availability Zones. Regions are chosen based on latency to users, local compliance laws, and service pricing.
</details>

---

### Q23: What is an AWS Availability Zone (AZ)?
**Keywords:** Availability Zone, Data centers, Interconnected, Low-latency
<details>
<summary>Click to Reveal Answer</summary>

An Availability Zone (AZ) consists of one or more discrete, physical data centers, each with redundant power, networking, and connectivity. AZs within a Region are interconnected with ultra-low-latency, high-speed fiber-optic networking, enabling synchronous data replication.
</details>

---

### Q24: What is the core concept of the AWS Shared Responsibility Model?
**Keywords:** Shared Responsibility, Security of the cloud, Security in the cloud
<details>
<summary>Click to Reveal Answer</summary>

The Shared Responsibility Model divides security tasks between AWS and the customer:
- **AWS is responsible for security "of" the cloud**: Protecting the global infrastructure, physical data centers, hardware, virtualization layers, and managed services.
- **The customer is responsible for security "in" the cloud**: Protecting customer data, configuring access controls (IAM), managing operating systems and patches (on EC2), and setting up network firewalls (security groups).
</details>

---

### Q25: What is Amazon RDS, and which relational database engines does it support?
**Keywords:** RDS, Managed Relational, PostgreSQL, MySQL, Aurora
<details>
<summary>Click to Reveal Answer</summary>

Amazon RDS (Relational Database Service) is a managed database service that simplifies setting up, operating, and scaling relational databases in the cloud. It supports engines including PostgreSQL, MySQL, MariaDB, Oracle, Microsoft SQL Server, and Amazon Aurora.
</details>

---

### Q26: What is Amazon S3, and what are the three components of an S3 object?
**Keywords:** S3, Key-value, Bucket, Key, Value, Metadata
<details>
<summary>Click to Reveal Answer</summary>

Amazon S3 (Simple Storage Service) is an object storage service. Every object in S3 consists of:
- **Key**: The unique string identifier (representing the "folder path" and file name).
- **Value**: The binary content/payload of the file itself.
- **Metadata**: Key-value pairs containing information about the object (e.g., content type, creation date).
</details>

---

### Q27: Compare the storage and retrieval characteristics of S3 Standard vs. S3 Glacier storage classes.
**Keywords:** S3 Standard, S3 Glacier, Storage cost, Retrieval time
<details>
<summary>Click to Reveal Answer</summary>

- **S3 Standard**: High storage cost, zero retrieval cost, and instant (millisecond) retrieval. Optimized for active, frequently accessed data.
- **S3 Glacier**: Extremely low storage cost, retrieval fees apply, and retrieval latency ranges from minutes to hours. Optimized for archiving historical records.
</details>

---

### Intermediate — Application and Scenario

---

### Q28: Under the Shared Responsibility Model, who is responsible for patching a PostgreSQL database running on an EC2 instance versus running on Amazon RDS?
**Keywords:** EC2, RDS, Operating System patch, Managed database
**Hint:** Think about who has root access to the underlying OS.
<details>
<summary>Click to Reveal Answer</summary>

- **On an EC2 instance**: The **Customer** is responsible for patching the guest operating system and the database engine because EC2 is an IaaS service where the customer has root access and full control of the virtual server.
- **On Amazon RDS**: **AWS** is responsible for operating system patching, physical maintenance, and database engine upgrades because RDS is a PaaS service where the OS layer is abstracted away from the customer.
</details>

---

### Q29: Your application stores and reads user profile images. Which AWS service is best suited for this task, and how do you implement security groups or access controls to protect them?
**Keywords:** S3, Public block, IAM policy, Pre-signed URL
**Hint:** Where do flat files go, and how do you authenticate users?
<details>
<summary>Click to Reveal Answer</summary>

The best service is **Amazon S3** because profile images are flat, unstructured files.
To secure them:
1. Enable S3 Block Public Access to keep the bucket private by default.
2. Use **IAM Policies** to authorize your backend application (running on EC2 or Lambda) to read/write images to S3.
3. Serve images to clients securely using **S3 Pre-signed URLs** which grant temporary read access (e.g., valid for 15 minutes) to authorized users, preventing direct public access to your files.
</details>

---

### Q30: How do S3 Lifecycle Policies help optimize cloud storage costs for an enterprise application?
**Keywords:** S3 Lifecycle, Transition, Expiration, Storage classes
**Hint:** Think about automated rule transitions over time.
<details>
<summary>Click to Reveal Answer</summary>

S3 Lifecycle Policies automate data management by transitioning objects between storage classes or deleting them based on rules:
- **Transition Rules**: Move files to lower-cost classes (e.g., transition from S3 Standard to S3 Standard-IA after 30 days, then to S3 Glacier after 90 days) as access frequency drops.
- **Expiration Rules**: Automatically delete temp files or logs after a set period (e.g., delete logs after 1 year), eliminating unnecessary storage fees.
</details>

---

### Advanced — System and Edge Case

---

### Q31: How do you design a database storage solution on AWS that balances cost, compliance, and recovery? Combine RDS, S3, and Glacier.
**Keywords:** RDS, S3, Glacier, CAPEX, Backup, Compliance
<details>
<summary>Click to Reveal Answer</summary>

To design a balanced, compliant, and cost-effective database lifecycle:
1. **Active OLTP Workloads (RDS)**: Keep current, frequently queried transactional data in **Amazon RDS (PostgreSQL)** to support active application users with low latency.
2. **Weekly/Monthly Backups (S3)**: Configure RDS automated snapshot exports to save backups as objects in **Amazon S3 (Standard or Standard-IA)**, providing high durability and rapid point-in-time recovery capabilities.
3. **Long-Term Archive & Compliance (S3 Glacier)**: Define S3 Lifecycle Policies to automatically transition backup files older than 90 days from S3 Standard to **S3 Glacier**. This complies with long-term data retention laws (e.g., keeping financial logs for 7 years) at the lowest possible storage cost, while accepting that recovery times for archived logs will take hours.
</details>

---

### Q32: What are the primary hallucination mitigation strategies when using AI assistants for cloud architecture design?
**Keywords:** Version anchor, Date stamp, Cross-reference, AI Hallucination
<details>
<summary>Click to Reveal Answer</summary>

AI models often hallucinate cloud details because cloud provider console layouts, pricing, and API parameters change rapidly. To mitigate this:
1. **Specific Version Anchoring**: Instruct the AI to write scripts using specific SDK versions (e.g., "boto3 version 1.34").
2. **Date-Stamped Context**: Explicitly state the target year or documentation date in prompts (e.g., "based on AWS services available in 2026").
3. **Cross-Referencing**: Treat all AI architectural advice as a draft, and verify critical constraints (such as service limits, network configurations, or security credentials) against official AWS documentation before implementing.
</details>

---

## Day 3 — Wednesday: NoSQL and Amazon DynamoDB

---

### Beginner — Recall and Definition

---

### Q33: What is NoSQL, and what are the four primary NoSQL database types?
**Keywords:** NoSQL, Key-Value, Document, Column-family, Graph
<details>
<summary>Click to Reveal Answer</summary>

NoSQL (Not Only SQL) is a non-relational database model designed for high write/read scale, schema flexibility, and distributed hosting. The four primary types are:
- **Key-Value**: Stores data as key-value pairs (e.g., DynamoDB, Redis).
- **Document**: Stores data as JSON-like documents (e.g., MongoDB, DynamoDB).
- **Column-Family**: Stores data in columns rather than rows (e.g., Cassandra).
- **Graph**: Stores data as nodes and edges representing relationships (e.g., Neo4j).
</details>

---

### Q34: Explain three key differences between relational (SQL) and non-relational (NoSQL) databases.
**Keywords:** Schema, Joins, Scaling, Relational, NoSQL
<details>
<summary>Click to Reveal Answer</summary>

- **Schema**: SQL databases have strict, predefined tabular schemas. NoSQL databases are schema-less, allowing items to have dynamic attributes.
- **Relationships & Joins**: SQL databases support server-side table joins. NoSQL databases do not support server-side joins; data is denormalized or queries are handled in application code.
- **Scaling**: SQL databases scale vertically (adding CPU/RAM). NoSQL databases scale horizontally by partitioning data across nodes.
</details>

---

### Q35: What is the CAP Theorem, and how does DynamoDB resolve the trade-offs?
**Keywords:** Consistency, Availability, Partition Tolerance, ConsistentRead
<details>
<summary>Click to Reveal Answer</summary>

The CAP Theorem states that a distributed data store can guarantee at most two of: Consistency, Availability, and Partition Tolerance.
Because network partitions are inevitable in distributed systems, DynamoDB chooses **Partition Tolerance**. By default, it prioritizes **Availability** over Consistency, returning eventually consistent reads. However, developers can request strong **Consistency** by setting `ConsistentRead = True` in their API requests.
</details>

---

### Q36: Describe the DynamoDB data model components: Tables, Items, and Attributes.
**Keywords:** Table, Item, Attribute, DynamoDB model
<details>
<summary>Click to Reveal Answer</summary>

- **Table**: A collection of items. Unlike relational tables, there is no fixed schema enforced on the columns.
- **Item**: A single record within a table, analogous to a row in a SQL database. Items must have a primary key but can otherwise contain any attributes.
- **Attribute**: A key-value pair associated with an item, analogous to a column value. Attributes can be scalar values, sets, or nested documents.
</details>

---

### Q37: What is the difference between a Partition Key (PK) and a Composite Primary Key (PK + SK) in DynamoDB?
**Keywords:** Partition Key, Sort Key, Composite Primary Key, Hash value
<details>
<summary>Click to Reveal Answer</summary>

- **Simple Partition Key**: Consists of a single attribute. DynamoDB passes the PK's value through an internal hash function to determine the physical partition where the item is stored.
- **Composite Primary Key**: Consists of a Partition Key (hash key) and a Sort Key (range key). Multiple items can share the same PK but must have different SK values. DynamoDB physical partition is decided by PK, and items with the same PK are sorted physically by the SK.
</details>

---

### Q38: What does the `PutItem` API operation do in DynamoDB, and what is a conditional write?
**Keywords:** PutItem, Overwrite, ConditionExpression, Conditional Check
<details>
<summary>Click to Reveal Answer</summary>

- `PutItem` creates a new item or completely replaces an existing item with the same primary key.
- A **Conditional Write** executes the write only if a specific condition (provided in `ConditionExpression`) is met (e.g., `attribute_not_exists(ProductId)`). If the condition fails, the write is aborted, preventing accidental overwrites.
</details>

---

### Q39: Explain the difference between `GetItem` and `BatchGetItem`.
**Keywords:** GetItem, BatchGetItem, Primary Key, Multi-table
<details>
<summary>Click to Reveal Answer</summary>

- `GetItem` retrieves a single item using its primary key (PK and SK if composite).
- `BatchGetItem` retrieves up to 100 items from one or more DynamoDB tables in a single network request using their primary keys, which reduces round-trip latencies for bulk reads.
</details>

---

### Q40: What is the difference between `UpdateItem` and `PutItem`?
**Keywords:** UpdateItem, PutItem, Patch, Overwrite
<details>
<summary>Click to Reveal Answer</summary>

- `PutItem` completely replaces the old item. Any attribute not specified in the new item is deleted.
- `UpdateItem` acts like a patch. It modifies, adds, or removes specific attributes on an item without deleting other existing attributes. It also supports atomic operations like incrementing counters.
</details>

---

### Q41: In DynamoDB, what is the difference between a `Query` and a `Scan` operation?
**Keywords:** Query, Scan, Partition Key, Performance, Capacity
<details>
<summary>Click to Reveal Answer</summary>

- `Query` searches for items using a single Partition Key value (and optional Sort Key range filters). It only reads the specific physical partition, making it highly efficient ($O(1)$) and cost-effective.
- `Scan` reads every single item in the entire table. As the table grows, a Scan becomes slow ($O(n)$) and extremely expensive because it consumes RCUs for every item in the table.
</details>

---

### Q42: What is a FilterExpression in DynamoDB, and does it reduce query pricing?
**Keywords:** FilterExpression, Post-retrieval, RCU cost, Query filtering
<details>
<summary>Click to Reveal Answer</summary>

A `FilterExpression` is a query parameter that filters results *after* they are read from physical partitions but *before* returning them to the client. It simplifies application code but does **not** reduce RCU consumption or query pricing, as you are still billed for reading all items before the filter was applied.
</details>

---

### Q43: What is "Denormalization" in NoSQL data modeling, and why is it used?
**Keywords:** Denormalization, Duplicate data, Joins, Query efficiency
<details>
<summary>Click to Reveal Answer</summary>

Denormalization is the practice of duplicating data or structuring items so that related data is stored together in a single table rather than in normalized tables. Since NoSQL databases do not support server-side joins, denormalizing ensures that all data required for a specific query or access pattern is retrieved in a single read operation.
</details>

---

### Intermediate — Application and Scenario

---

### Q44: Compare the performance and RCU cost characteristics of a Query versus a Scan on a 10 GB table.
**Keywords:** RCU calculation, Query vs Scan, 1MB limit, Read capacity
**Hint:** Think about how much data is read from disk in each operation.
<details>
<summary>Click to Reveal Answer</summary>

- **Scan**: A Scan will read all 10 GB of data. Since DynamoDB reads are billed in blocks of 4 KB (for eventually consistent) or 4 KB (strongly consistent, 1 RCU per 4KB), a Scan on a 10 GB table will consume approximately 2,560,000 RCUs and will require running multiple paginated requests (due to the 1 MB response limit). This is slow and highly expensive.
- **Query**: A Query retrieves only items matching a specific partition key. If the query targets a partition key containing 10 items (totaling 20 KB), it will read only 20 KB of data, consuming just 5 RCUs (eventually consistent) and returning the results in milliseconds.
</details>

---

### Q45: Scenario: You are building an inventory system. When a purchase occurs, you must decrement the `Stock` attribute of a product item in DynamoDB, but only if the current `Stock` is greater than 0. How do you implement this safely?
**Keywords:** UpdateItem, Conditional Update, ConditionExpression, Stock
**Hint:** Use atomic counters combined with write conditions.
<details>
<summary>Click to Reveal Answer</summary>

You should use `UpdateItem` with an update expression and a condition expression:
1. **UpdateExpression**: Use `SET Stock = Stock - :val` to atomically decrement the stock.
2. **ConditionExpression**: Set `Stock > :zero` (where `:zero` is 0).
If two transactions execute this simultaneously when stock is 1, the first succeeds and decrements stock to 0. The second transaction's check evaluates to false because stock is now 0, throwing a `ConditionalCheckFailedException`, which prevents negative inventory.
</details>

---

### Q46: Walk through the steps to convert a relational library database (Books, Members, Loans tables) into a DynamoDB single-table design.
**Keywords:** Single-Table, Access patterns, PK, SK, Denormalize
**Hint:** NoSQL designs start with query requirements first.
<details>
<summary>Click to Reveal Answer</summary>

1. **Identify Access Patterns**: (e.g., Get book details, Get loans by member, Get books due today).
2. **Define Generic Keys**: Create a single table with generic keys `PK` and `SK`.
3. **Denormalize & Map Entities**:
   - Books: `PK = BOOK#[ISBN]`, `SK = METADATA`. Store details as attributes.
   - Members: `PK = MEMBER#[Id]`, `SK = METADATA`. Store details.
   - Loans: Store as a separate item: `PK = MEMBER#[Id]`, `SK = LOAN#[ISBN]`. This groups a member's loans physically together under their member partition, allowing you to fetch the member's profile and all their active loans in a single query.
</details>

---

### Advanced — System and Edge Case

---

### Q47: Design a single-table schema for an e-commerce order system. Explain how the Partition Keys (PK) and Sort Keys (SK) are structured to support both fetching user orders and querying order items.
**Keywords:** Single-table design, E-commerce, PK, SK, GSI, access patterns
<details>
<summary>Click to Reveal Answer</summary>

To design an e-commerce order system supporting multiple access patterns:
1. **Base Table Structure**:
   - **Customer Profile**: `PK = CUSTOMER#[Id]`, `SK = PROFILE`. Attributes: Name, Email.
   - **Customer Order**: `PK = CUSTOMER#[Id]`, `SK = ORDER#[OrderId]`. Attributes: TotalPrice, OrderStatus, Timestamp.
   - **Order Item**: `PK = ORDER#[OrderId]`, `SK = ITEM#[ItemId]`. Attributes: ProductName, Quantity, Price.

2. **Access Patterns Supported**:
   - *Get customer profile and all their orders*: Query `PK = CUSTOMER#[Id]`. This returns the profile item and all order metadata items because they share the same partition and are sorted by SK.
   - *Get all items in a specific order*: Query `PK = ORDER#[OrderId]` on the base table. Since order items use the order ID as the partition key, this returns all products in that order.

3. **Querying orders by status (using GSI)**:
   Create a Global Secondary Index (GSI) where:
   - GSI Partition Key: `OrderStatus` (e.g., `PENDING`).
   - GSI Sort Key: `Timestamp`.
   This allows the system to query the GSI to retrieve all pending orders across all customers sorted by date.
</details>

---

### Q48: How does DynamoDB distribute data across physical partitions under the hood, and how do "Hot Partitions" occur?
**Keywords:** Partitioning, Hashing, Consistent Hashing, Hot partition, Skew
<details>
<summary>Click to Reveal Answer</summary>

DynamoDB uses the Partition Key value to calculate a hash. This hash maps the item to a specific physical storage partition managed by AWS. Each partition has limits (10 GB of storage, 1,000 WCU, and 3,000 RCU).
A **Hot Partition** occurs when data is skewed so that a large percentage of database read/write requests target the same partition key (e.g., a highly popular product ID or a sequential date key like `2026-07-14`). When requests on that partition exceed the 3,000 RCU or 1,000 WCU limit, DynamoDB throttles requests, causing application timeouts, even if the overall table has plenty of unused provisioned capacity.
</details>

---

## Day 4 — Thursday: Data Warehousing and Redshift Fundamentals

---

### Beginner — Recall and Definition

---

### Q49: What is a Data Warehouse, and how does it differ from a transactional database?
**Keywords:** Data Warehouse, OLAP, Columnar, Historical analysis
<details>
<summary>Click to Reveal Answer</summary>

A Data Warehouse is a centralized repository that consolidates data from multiple sources for reporting and analysis. Unlike transactional databases (OLTP) optimized for quick, single-row writes, data warehouses (OLAP) are optimized for complex, read-heavy analytical queries spanning massive, historical datasets.
</details>

---

### Q50: Explain the differences between OLTP and OLAP workloads.
**Keywords:** OLTP, OLAP, Write-heavy, Read-heavy, Row-oriented, Columnar
<details>
<summary>Click to Reveal Answer</summary>

- **OLTP (Online Transaction Processing)**: Handles active application operations. Characterized by high-concurrency, fast, low-latency writes and updates of single rows (row-oriented).
- **OLAP (Online Analytical Processing)**: Handles business intelligence and reporting. Characterized by low-concurrency, complex read-heavy queries that aggregate columns across millions of rows (column-oriented).
</details>

---

### Q51: Compare a Star schema and a Snowflake schema.
**Keywords:** Star schema, Snowflake schema, Dimension tables, Normalization
<details>
<summary>Click to Reveal Answer</summary>

- **Star Schema**: Consists of a central fact table surrounded by completely denormalized dimension tables. It simplifies queries and requires fewer joins, resulting in faster performance.
- **Snowflake Schema**: Normalizes the dimension tables, splitting them into secondary related tables. It reduces data redundancy and saves storage but requires more complex joins, which can slow down query speeds.
</details>

---

### Q52: What is a Fact table, and what is a Dimension table?
**Keywords:** Fact table, Dimension table, Metrics, Foreign Keys, Attributes
<details>
<summary>Click to Reveal Answer</summary>

- **Fact Table**: The central table containing quantitative, measurable metrics (e.g., `amount_sold`, `quantity_ordered`) and foreign keys that link to the surrounding dimension tables.
- **Dimension Table**: Tables containing descriptive, qualitative attributes (e.g., `customer_name`, `product_category`, `store_location`) that provide context to the facts.
</details>

---

### Q53: What does MPP (Massively Parallel Processing) mean in Amazon Redshift?
**Keywords:** MPP, Massively Parallel, Compute Nodes, Query execution
<details>
<summary>Click to Reveal Answer</summary>

MPP means that Amazon Redshift automatically distributes database tables and query workloads across all compute nodes in a cluster. When a query is run, the compute nodes execute segments of the query in parallel on their respective portions of the data, dramatically speeding up processing times.
</details>

---

### Q54: What is Columnar Storage, and why is it optimal for data warehouses?
**Keywords:** Columnar, Disk I/O, Compression, Aggregation
<details>
<summary>Click to Reveal Answer</summary>

Columnar storage records data block-by-block by column rather than by row. It is optimal for data warehouses because:
1. Analytical queries typically aggregate a few columns (e.g., `SUM(price)`) across millions of rows. Redshift only reads the blocks for the target columns, reducing disk I/O.
2. Storing identical data types contiguously allows for high compression ratios (up to 3x or 4x), saving disk storage.
</details>

---

### Q55: What is the role of the Leader Node in an Amazon Redshift cluster?
**Keywords:** Leader Node, SQL connection, Parse, Execution plan
<details>
<summary>Click to Reveal Answer</summary>

The Leader Node is the front-end entry point of the cluster. It manages client connections, parses SQL statements, compiles the query into execution code, distributes the tasks to the compute nodes, and aggregates the final results returned by the compute nodes to send back to the client.
</details>

---

### Q56: What is the role of Compute Nodes in an Amazon Redshift cluster?
**Keywords:** Compute Nodes, Slices, Execute, Storage
<details>
<summary>Click to Reveal Answer</summary>

Compute Nodes execute the compiled query steps in parallel on their assigned partition of data. They store the physical database tables and perform the calculations (filtering, sorting, joins), returning their partial results to the leader node.
</details>

---

### Q57: Explain the three main table distribution styles in Amazon Redshift: EVEN, KEY, and ALL.
**Keywords:** EVEN, KEY, ALL, Distribution key, Compute nodes
<details>
<summary>Click to Reveal Answer</summary>

- **EVEN**: Distributes rows across compute nodes in a round-robin fashion. Ensures uniform storage distribution but may require network data shuffling during joins.
- **KEY**: Distributes rows based on the hash values of a specific column. Rows with the same key are sent to the same node, eliminating network transfer if joined on that column.
- **ALL**: Copies the entire table to every compute node. Eliminates network transfer for joins, but consumes more storage. Best for small lookup tables.
</details>

---

### Q58: How do you connect to Amazon Redshift using DBeaver?
**Keywords:** DBeaver, JDBC, Endpoint, Username, Port 5439
<details>
<summary>Click to Reveal Answer</summary>

1. In DBeaver, create a new connection and select the **Amazon Redshift** driver.
2. Input the Redshift cluster **Endpoint** (host address).
3. Set the database name, port (default is `5439`), username, and password.
4. Download the Redshift JDBC driver when prompted, and test the connection.
</details>

---

### Q59: What is Query Compilation in Amazon Redshift?
**Keywords:** Compilation, Caching, Leader node, Compute nodes
<details>
<summary>Click to Reveal Answer</summary>

When a query is run for the first time, the leader node parses the SQL and compiles it into C++ code optimized for the cluster's CPU architecture. This compiled code is sent to compute nodes. While compilation introduces initial delay (a few seconds), the code is cached. Subsequent executions of similar queries run instantly.
</details>

---

### Intermediate — Application and Scenario

---

### Q60: When should you choose Amazon RDS PostgreSQL over Amazon Redshift, and vice versa?
**Keywords:** RDS PostgreSQL, Redshift, OLTP vs OLAP, Joins vs Aggregates
**Hint:** Focus on concurrency, row-by-row updates, and analytical query scale.
<details>
<summary>Click to Reveal Answer</summary>

- **Choose RDS PostgreSQL**: When your application requires a transactional database (OLTP) that handles high-concurrency, row-by-row CRUD operations, foreign key constraints, instant updates, and serves active web users.
- **Choose Amazon Redshift**: When you need to store terabytes or petabytes of historical data and perform complex, read-heavy analytical queries (aggregates, reports, trends) across millions of rows where query speeds in PostgreSQL would choke.
</details>

---

### Q61: Scenario: You have a small `date_dimension` table (365 rows) and a massive `website_clicks` fact table (100 million rows). Which distribution styles would you assign to each table to optimize join queries?
**Keywords:** ALL distribution, KEY distribution, Joins, Web logs
**Hint:** Small tables go everywhere; large tables hash on join columns.
<details>
<summary>Click to Reveal Answer</summary>

- **`date_dimension` table**: Use **ALL** distribution. Since the table is tiny (365 rows), duplicating it to all compute nodes consumes negligible storage but ensures that any join on date can be resolved locally on each compute node slice.
- **`website_clicks` table**: Use **KEY** distribution on the `date` column (or EVEN if joins are not frequently performed). If we key-distribute on `date`, rows matching specific dates reside on the same compute nodes as the date lookup, eliminating network data transfers.
</details>

---

### Q62: Why are single-row inserts and updates slow and inefficient in Amazon Redshift, and how should data ingestion be handled instead?
**Keywords:** Columnar, Block write, S3, COPY, Batching
**Hint:** Row writes force rewriting multiple separate column blocks.
<details>
<summary>Click to Reveal Answer</summary>

Single-row inserts/updates are slow because Redshift is a columnar database. Writing a single row requires opening, writing, and re-compressing disk blocks for every column in the table, creating massive I/O bottlenecks.
To ingest data efficiently:
Buffer incoming transactional data in **Amazon S3** as files (e.g., CSV or Parquet), and then execute the **`COPY`** command in batches (e.g., every 15 minutes or hourly) to load millions of rows in parallel directly to the compute nodes.
</details>

---

### Advanced — System and Edge Case

---

### Q63: Explain what happens under the hood during query execution in Redshift when data needs to be redistributed (shuffled) over the network during a JOIN.
**Keywords:** Shuffling, Redistribution, Broadcast, Colocated join
<details>
<summary>Click to Reveal Answer</summary>

During a `JOIN` between two tables, Redshift compute nodes must match join keys.
- If the tables are joined on columns that are colocated (e.g., both tables are KEY-distributed on the join column, or the join table is ALL-distributed), the nodes perform a **colocated join** locally without network transfer.
- If the keys are not aligned, Redshift must redistribute data. The query planner either:
  1. **Broadcasts** the smaller table over the network to all compute nodes (copying the data on the fly).
  2. **Shuffles/Redistributes** rows of both tables dynamically by hashing the join column and sending rows to their matching node slices.
This network redistribution (shuffling) is highly expensive and is often the primary bottleneck in query execution, which is why choosing matching `DISTKEY`s is critical.
</details>

---

### Q64: What is Query Queue management and WLM (Workload Management) in Amazon Redshift, and why is it important for production clusters?
**Keywords:** WLM, Concurrency, Queues, Resource allocation
<details>
<summary>Click to Reveal Answer</summary>

Workload Management (WLM) allows administrators to define query queues and allocate memory and CPU resources to them. In a production data warehouse, quick dashboard reporting queries can get stuck behind slow, resource-heavy ETL processing scripts. WLM resolves this by segregating queries into different queues (e.g., an ETL queue, a BI reporting queue, and a short query queue). Redshift also supports Concurrency Scaling, which automatically spins up temporary cluster capacity to handle spikes in read queries, ensuring consistent user performance.
</details>

---

## Day 5 — Friday: Data Ingestion, Java Integration & Query Translation

---

### Beginner — Recall and Definition

---

### Q65: What is the Redshift `COPY` command?
**Keywords:** COPY, Ingestion, Parallel download, S3
<details>
<summary>Click to Reveal Answer</summary>

The Redshift `COPY` command is a SQL command used to load bulk data from external sources (such as files in an Amazon S3 bucket, DynamoDB tables, or EMR clusters) in parallel directly into a Redshift table.
</details>

---

### Q66: Why is the `COPY` command faster than executing standard SQL `INSERT` statements?
**Keywords:** Parallel, Ingestion, Compute Nodes, Leader Node bypass
<details>
<summary>Click to Reveal Answer</summary>

`INSERT` statements route data row-by-row through the single leader node, which compiles and executes write statements sequentially. The `COPY` command bypasses this bottleneck by instructing all compute nodes to download partition files concurrently from S3, writing the data directly to disk in parallel.
</details>

---

### Q67: How do you authorize a Redshift cluster to access and load data from S3 using IAM?
**Keywords:** IAM Role, Trust relationship, ARN, COPY command
<details>
<summary>Click to Reveal Answer</summary>

1. Create an IAM Role in the AWS Console with read access to the target S3 bucket (e.g., `AmazonS3ReadOnlyAccess`).
2. Configure the role's trust relationship to allow the Redshift service (`redshift.amazonaws.com`) to assume the role.
3. Associate the IAM Role with your Redshift cluster.
4. Pass the role's Amazon Resource Name (ARN) inside the `COPY` command's `credentials` block.
</details>

---

### Q68: What file formats are natively supported by the Redshift `COPY` command?
**Keywords:** CSV, JSON, Parquet, Avro, ORC
<details>
<summary>Click to Reveal Answer</summary>

The `COPY` command natively supports structured text and binary formats including CSV, JSON, Apache Parquet, Apache Avro, ORC, and delimiter-separated text files.
</details>

---

### Q69: What is JDBC, and what is its role in Java applications?
**Keywords:** JDBC, Java Database Connectivity, Driver, SQL execution
<details>
<summary>Click to Reveal Answer</summary>

JDBC (Java Database Connectivity) is a standard Java API that defines how a Java application connects to database systems, manages active sessions, executes SQL statements, and processes returned query result sets.
</details>

---

### Q70: What is the standard official JDBC connection string format for Amazon Redshift?
**Keywords:** Connection string, jdbc:redshift, Port 5439
<details>
<summary>Click to Reveal Answer</summary>

The standard format is:
`jdbc:redshift://[cluster-endpoint]:5439/[database_name]`
Where `5439` is the default port for Redshift clusters.
</details>

---

### Q71: What is the purpose of the `STL_LOAD_ERRORS` system log table in Redshift?
**Keywords:** STL_LOAD_ERRORS, COPY failure, Data mismatch, Debugging
<details>
<summary>Click to Reveal Answer</summary>

`STL_LOAD_ERRORS` is a system table that logs detailed error information for failed `COPY` commands. It records the file path, error line number, offending column data, and the specific parsing error reason, allowing database developers to debug data loading failures.
</details>

---

### Q72: What is the difference between ETL and ELT data processing models?
**Keywords:** ETL, ELT, Staging server, Compute nodes, Warehouse
<details>
<summary>Click to Reveal Answer</summary>

- **ETL (Extract, Transform, Load)**: Data is extracted, transformed on an external staging server (e.g., using Spark or python), and then loaded into the data warehouse.
- **ELT (Extract, Load, Transform)**: Raw data is loaded directly into the data warehouse (via S3 and `COPY`), and then the warehouse's own parallel compute nodes are used to execute the SQL queries that transform the data.
</details>

---

### Q73: What is the role of the `MAXERROR` option in the `COPY` command?
**Keywords:** MAXERROR, Tolerant, Load failures, Rollback
<details>
<summary>Click to Reveal Answer</summary>

By default, the `COPY` command rolls back the entire load if a single row error is encountered. The `MAXERROR` option specifies the maximum number of row errors that are allowed to occur before the load fails. If errors are below this number, the valid rows are loaded and the invalid rows are skipped.
</details>

---

### Q74: Why is using `PreparedStatement` preferred over standard `Statement` in Java JDBC databases?
**Keywords:** PreparedStatement, SQL Injection, Pre-compilation, Performance
<details>
<summary>Click to Reveal Answer</summary>

- **Security**: It compiles the SQL query template before binding user parameters, preventing SQL injection attacks.
- **Performance**: The database compiles the query plan once and re-uses it for subsequent runs, improving execution speeds when running queries in loops.
</details>

---

### Q75: What is the Data Access Object (DAO) pattern?
**Keywords:** DAO, Separation of Concerns, Entity, SQL abstraction
<details>
<summary>Click to Reveal Answer</summary>

The DAO (Data Access Object) is a structural design pattern that abstracts and encapsulates all interactions with the database. It separates the business logic layer of the application from the persistence layer, providing a clean API (e.g., `findById`, `save`) to the rest of the application.
</details>

---

### Intermediate — Application and Scenario

---

### Q76: Walk through the Java JDBC steps to connect to a Redshift cluster, execute an aggregation query, and print the output.
**Keywords:** DriverManager, Connection, Statement, ResultSet, close
**Hint:** Use try-with-resources to manage connections.
<details>
<summary>Click to Reveal Answer</summary>

```java
String url = "jdbc:redshift://cluster-endpoint:5439/dev";
String user = "user";
String password = "pwd";
String sql = "SELECT category, SUM(amount) FROM sales GROUP BY category;";

try (Connection conn = DriverManager.getConnection(url, user, password);
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery(sql)) {
     
    while (rs.next()) {
        System.out.println(rs.getString(1) + ": " + rs.getDouble(2));
    }
} catch (SQLException e) {
    e.printStackTrace();
}
```
*Steps*: Load driver (automatic in modern Java), establish connection via `DriverManager`, create statement, execute query to get `ResultSet`, iterate using `rs.next()`, and read columns by name or index. Try-with-resources handles closing.
</details>

---

### Q77: Scenario: A Redshift `COPY` command loading a CSV file fails. Walk through the SQL troubleshooting query steps to find the file and row that caused the crash.
**Keywords:** STL_LOAD_ERRORS, query, query_id, colname, err_reason
**Hint:** Query system logs sorting by the most recent start time.
<details>
<summary>Click to Reveal Answer</summary>

You should query the `STL_LOAD_ERRORS` system table:
```sql
SELECT query, starttime, filename, line_number, colname, err_reason
FROM stl_load_errors
ORDER BY starttime DESC
LIMIT 1;
```
This query returns the most recent load error. You inspect `filename` to see which S3 partition failed, `line_number` to find the exact line in the file, `colname` to see which database column failed, and `err_reason` to check the parsing issue (e.g., "Invalid digit").
</details>

---

### Q78: What are the risks of using AI assistants to translate SQL queries from PostgreSQL to Amazon Redshift, and how do you mitigate them?
**Keywords:** SQL dialect, Unsupported functions, Mitigation, Verification
**Hint:** Think about procedural code (triggers/SP) and index translations.
<details>
<summary>Click to Reveal Answer</summary>

- **Risks**: Redshift does not support many PostgreSQL features (e.g., `SERIAL` keys, spatial indexes, recursive CTEs, triggers, and certain string functions). The AI may translate these line-for-line, producing SQL that fails to execute in Redshift.
- **Mitigation**: Ask the AI specifically to output Redshift-compatible SQL. Review all translations manually, replace unsupported functions with Redshift equivalents (e.g., use identity columns instead of serial), and test all translated queries on a development Redshift cluster.
</details>

---

### Q79: How do you configure a Java database application that reads millions of records from Redshift to prevent OutOfMemory (OOM) errors?
**Keywords:** Fetch size, Cursor, Stream, ResultSet
**Hint:** Avoid loading all records into JVM memory at once.
<details>
<summary>Click to Reveal Answer</summary>

By default, JDBC drivers load the entire `ResultSet` into JVM memory. To prevent OOM errors:
1. Disable autocommit on the Connection: `conn.setAutoCommit(false);` (required by Redshift/PostgreSQL drivers to enable streaming).
2. Set a fetch size on the statement: `stmt.setFetchSize(1000);`.
This configures the driver to use a database cursor, fetching data in batches of 1,000 rows over the network as you iterate, keeping JVM memory footprint constant regardless of the total record count.
</details>

---

### Advanced — System and Edge Case

---

### Q80: Design an end-to-end data pipeline where transaction logs are written to DynamoDB, streamed/written to S3, loaded into Redshift, and analyzed via a Java application. Explain the purpose of each database service in this architecture.
**Keywords:** Polyglot persistence, DynamoDB Streams, S3 Data Lake, Redshift COPY, Java JDBC
<details>
<summary>Click to Reveal Answer</summary>

An optimal polyglot persistence pipeline includes:
1. **Operational Layer (DynamoDB)**: Low-latency user transactions are written to **Amazon DynamoDB (OLTP)**, giving users sub-10ms response times.
2. **Streaming & Buffering (DynamoDB Streams to S3)**: Enable DynamoDB Streams to capture every insert. A Kinesis Firehose stream reads this stream and buffers logs, writing them in batches as compressed Parquet files to **Amazon S3** (which acts as the raw **Data Lake**).
3. **Analytical Layer (Redshift)**: An automated orchestrator runs a Redshift **`COPY`** command periodically to load the Parquet files from S3 into **Amazon Redshift (OLAP)** in parallel, leveraging its MPP architecture.
4. **Application Report (Java JDBC)**: A Java administration console connects to Redshift via the **Redshift JDBC Driver** to execute aggregate query reporting, providing business dashboards without slowing down the active DynamoDB database.
</details>
