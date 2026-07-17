# Weekly Knowledge Check: Week 3 — AWS, DynamoDB, & Redshift

> This quiz covers the full Week 3 curriculum: Advanced SQL objects (views, triggers, stored procedures), transactions and isolation levels, database indexing, AWS core services and infrastructure, NoSQL databases, Amazon DynamoDB CRUD operations, Query vs. Scan, relational-to-NoSQL schema conversion, data warehousing concepts (OLAP vs. OLTP), Amazon Redshift architecture and analytical queries, loading data with the COPY command, and Java JDBC integration with Redshift.

---

## Part 1: Multiple Choice (Monday-Wednesday Topics)

---

### Q1. In a database transaction, which property guarantees that either all database modifications within the transaction are completed successfully, or none of them are?

- [ ] A) Consistency
- [ ] B) Isolation
- [ ] C) Durability
- [ ] D) Atomicity

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** D) Atomicity

**Explanation:** Atomicity is the "all-or-nothing" property, ensuring that a transaction is treated as a single, indivisible unit of work. If any operation within it fails, the database rolls back all changes to the starting state.

**Why others are wrong:**
- A) Consistency ensures the database moves from one valid state to another, maintaining all schema constraints.
- B) Isolation ensures concurrent transactions execute independently without exposing intermediate states.
- C) Durability guarantees that once a transaction is committed, its changes survive system crashes and power failures.
</details>

---

### Q2. If a database transaction attempts to insert a record that violates a table's CHECK constraint (e.g., `CHECK (balance >= 0.00)`), which ACID property guarantees that the database rejects the write and rolls back any partial changes?

- [ ] A) Atomicity
- [ ] B) Consistency
- [ ] C) Isolation
- [ ] D) Durability

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Consistency

**Explanation:** Consistency ensures that a transaction cannot transition the database into an invalid state. A transaction must adhere to all schema rules, constraints, and business logic. If a constraint is violated, the transaction is aborted.

**Why others are wrong:**
- A) Atomicity handles the "all-or-nothing" execution mechanism, but the rule enforcement itself is a guarantee of Consistency.
- C) Isolation deals with concurrency control and visibility, not constraint enforcement.
- D) Durability refers to persistent storage survival of committed data.
</details>

---

### Q3. In transaction concurrency, which anomaly occurs when Transaction A reads modifications made by Transaction B before Transaction B has committed those changes?

- [ ] A) Non-Repeatable Read
- [ ] B) Phantom Read
- [ ] C) Dirty Read
- [ ] D) Lost Update

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Dirty Read

**Explanation:** A Dirty Read occurs when a transaction reads uncommitted changes from another concurrent transaction. If the second transaction is later rolled back, the data read by the first transaction becomes invalid.

**Why others are wrong:**
- A) A Non-Repeatable Read occurs when a transaction reads the same row twice and gets different values because another transaction committed an update in between.
- B) A Phantom Read occurs when a transaction executes a query twice and gets a different set of rows because another transaction committed an insert or delete in between.
- D) A Lost Update occurs when two transactions read the same data and write updates simultaneously, overwriting each other's changes.
</details>

---

### Q4. Which transaction isolation level provides the highest level of concurrency protection by preventing Dirty Reads, Non-Repeatable Reads, and Phantom Reads, but at the cost of the lowest execution throughput?

- [ ] A) Read Uncommitted
- [ ] B) Read Committed
- [ ] C) Repeatable Read
- [ ] D) Serializable

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** D) Serializable

**Explanation:** Serializable isolation simulates serial execution of transactions, completely preventing all concurrency anomalies (Dirty, Non-Repeatable, and Phantom Reads). However, it requires extensive locking or concurrency checks, reducing performance.

**Why others are wrong:**
- A) Read Uncommitted is the lowest isolation level and permits Dirty Reads.
- B) Read Committed prevents Dirty Reads but allows Non-Repeatable and Phantom Reads.
- C) Repeatable Read prevents Dirty and Non-Repeatable Reads, but may still allow Phantom Reads depending on the database engine.
</details>

---

### Q5. What is the primary performance tradeoff when creating a database index (such as a B-Tree index) on a table?

- [ ] A) It speeds up read queries but slows down write operations (INSERT, UPDATE, DELETE).
- [ ] B) It speeds up write queries but slows down read queries.
- [ ] C) It increases memory consumption but has no impact on write speed.
- [ ] D) It only speeds up queries involving primary keys.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) It speeds up read queries but slows down write operations (INSERT, UPDATE, DELETE).

**Explanation:** Indexes speed up SELECT queries by providing a fast lookup structure. However, every time data is inserted, updated, or deleted, the database must write to both the table and the index structures, introducing write overhead.

**Why others are wrong:**
- B) It does the opposite: it slows down write operations.
- C) It does impact write speed because the index must be kept synchronized with table updates.
- D) Indexes can be created on any column, not just primary keys.
</details>

---

### Q6. An index is defined on a PostgreSQL table as `CREATE INDEX idx_user_name ON users (last_name, first_name);`. Under the left-prefix rule, which query will NOT benefit from this index?

- [ ] A) `SELECT * FROM users WHERE last_name = 'Smith';`
- [ ] B) `SELECT * FROM users WHERE last_name = 'Smith' AND first_name = 'John';`
- [ ] C) `SELECT * FROM users WHERE first_name = 'John';`
- [ ] D) `SELECT * FROM users WHERE last_name LIKE 'Smi%';`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `SELECT * FROM users WHERE first_name = 'John';`

**Explanation:** A composite index requires the search criteria to include the leftmost column (`last_name` in this case) to be utilized. Searching by `first_name` alone bypasses the index's prefix order, forcing a full-table scan.

**Why others are wrong:**
- A) This query uses the leftmost prefix column (`last_name`), so it utilizes the index.
- B) This query uses both index columns in order, getting the maximum benefit.
- D) This query uses the prefix column with a range match (`LIKE 'Smi%'`), which the B-tree index can optimize.
</details>

---

### Q7. What is the key functional difference between a standard SQL View and a Materialized View?

- [ ] A) A standard View is stored physically on disk; a Materialized View is computed on-the-fly.
- [ ] B) A standard View is computed on-the-fly when queried; a Materialized View stores its result set physically on disk.
- [ ] C) Standard Views can be indexed, whereas Materialized Views cannot.
- [ ] D) Materialized Views automatically update instantly whenever base table data changes.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A standard View is computed on-the-fly when queried; a Materialized View stores its result set physically on disk.

**Explanation:** A standard View is a saved query definition that executes every time it is referenced. A Materialized View computes the query once and caches the physical results on disk, requiring explicit refreshes (`REFRESH MATERIALIZED VIEW`) to update.

**Why others are wrong:**
- A) This is the exact inverse of their true definitions.
- C) Materialized Views can be indexed to speed up queries, while standard Views cannot be indexed directly.
- D) Materialized Views in PostgreSQL do not auto-refresh instantly; they must be refreshed manually or on a scheduled pipeline.
</details>

---

### Q8. You want to automatically update a `last_modified` timestamp column on a row right before the database saves the updated row. Which trigger configuration should you use?

- [ ] A) BEFORE UPDATE row-level trigger
- [ ] B) AFTER UPDATE row-level trigger
- [ ] C) BEFORE UPDATE statement-level trigger
- [ ] D) INSTEAD OF UPDATE statement-level trigger

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) BEFORE UPDATE row-level trigger

**Explanation:** A BEFORE trigger executes before the update is committed, allowing it to modify the `NEW` row variables directly. A row-level trigger is required to act on individual rows as they are updated.

**Why others are wrong:**
- B) An AFTER trigger cannot modify the row values because the write operation has already occurred.
- C) A statement-level trigger executes once per SQL statement, not once per row, and cannot inspect or modify specific row data.
- D) INSTEAD OF triggers are typically used to redirect updates on views, not table rows.
</details>

---

### Q9. In PL/pgSQL (PostgreSQL), what is a key architectural difference between a Stored Procedure and a User-Defined Function (UDF)?

- [ ] A) Stored Procedures return a table; Functions can only return scalar values.
- [ ] B) Stored Procedures are invoked inside a SELECT query; Functions are invoked using CALL.
- [ ] C) Stored Procedures support transaction control (COMMIT and ROLLBACK); Functions execute within an active transaction and cannot control transactions.
- [ ] D) Functions can take parameters, whereas Stored Procedures cannot.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Stored Procedures support transaction control (COMMIT and ROLLBACK); Functions execute within an active transaction and cannot control transactions.

**Explanation:** Stored Procedures are designed to manage procedural logic and can commit or roll back transactions. Functions cannot include transaction commands because they execute as part of an outer query transaction.

**Why others are wrong:**
- A) Both can return tables or multiple values.
- B) Stored Procedures are called using `CALL`, and Functions are called inside SQL statements like `SELECT`.
- D) Both procedures and functions accept input and output parameters.
</details>

---

### Q10. According to the NIST definition of cloud computing, which characteristic refers to the system's ability to automatically scale compute and storage resources up and down dynamically to match demand?

- [ ] A) Resource Pooling
- [ ] B) Rapid Elasticity
- [ ] C) On-Demand Self-Service
- [ ] D) Measured Service

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Rapid Elasticity

**Explanation:** Rapid Elasticity is the ability to provision resources dynamically and release them automatically when they are no longer needed, allowing applications to scale with workload variations.

**Why others are wrong:**
- A) Resource Pooling refers to sharing physical hardware among multiple customers (multi-tenancy).
- C) On-Demand Self-Service allows users to provision resources automatically without human interaction with the provider.
- D) Measured Service refers to the pay-as-you-go billing model based on usage metrics.
</details>

---

### Q11. Which service category describes a cloud database service like Amazon RDS, where the infrastructure and OS patches are managed by AWS, but the database configurations and schemas are managed by the user?

- [ ] A) Infrastructure as a Service (IaaS)
- [ ] B) Software as a Service (SaaS)
- [ ] C) Platform as a Service (PaaS)
- [ ] D) Function as a Service (FaaS)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Platform as a Service (PaaS)

**Explanation:** Amazon RDS is a PaaS. AWS manages the virtual machine, operating system patching, physical storage, and backups. The developer is provided with a database endpoint and handles data modeling and querying.

**Why others are wrong:**
- A) IaaS (like EC2) requires the user to install the database, manage operating system patches, and handle firewall ports manually.
- B) SaaS (like Salesforce or Gmail) provides a fully functional web application where the customer has no access to database administration.
- D) FaaS (like AWS Lambda) is for executing event-driven code blocks, not persistent databases.
</details>

---

### Q12. Under the AWS Shared Responsibility Model, which of the following is a responsibility of the Customer?

- [ ] A) Physical security of AWS data centers.
- [ ] B) Operating system patches for virtual machines running EC2 instances.
- [ ] C) Hypervisor software maintenance on physical hosts.
- [ ] D) Disposal of decommissioned storage drives.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Operating system patches for virtual machines running EC2 instances.

**Explanation:** The customer is responsible for security "in" the cloud. When using EC2 (IaaS), the customer has root access to the operating system and is responsible for installing security patches and configuring the system firewall.

**Why others are wrong:**
- A), C), and D) are responsibilities of AWS (security "of" the cloud), as they involve physical hardware, facilities, and the hypervisor virtualization layers.
</details>

---

### Q13. You need to store system log files in Amazon S3 that are rarely accessed but must be retrieved immediately if a system failure occurs. Which S3 storage class provides the best cost efficiency?

- [ ] A) S3 Standard
- [ ] B) S3 Standard-Infrequent Access (S3 Standard-IA)
- [ ] C) S3 Glacier Flexible Retrieval
- [ ] D) S3 Intelligent-Tiering

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) S3 Standard-Infrequent Access (S3 Standard-IA)

**Explanation:** S3 Standard-IA is designed for data that is accessed less frequently but requires rapid access (millisecond retrieval) when needed. It has a lower storage cost than S3 Standard but charges a small data retrieval fee.

**Why others are wrong:**
- A) S3 Standard is optimized for frequently accessed data and has higher storage costs.
- C) S3 Glacier has retrieval latencies ranging from minutes to hours, which violates the requirement for immediate retrieval.
- D) S3 Intelligent-Tiering is used when access patterns are unknown or changing, which is not the case here (we know it is rarely accessed).
</details>

---

### Q14. In distributed system design, the CAP theorem states that a system can guarantee at most two of its three core properties. What are these three properties?

- [ ] A) Concurrency, Availability, Portability
- [ ] B) Consistency, Availability, Partition Tolerance
- [ ] C) Compatibility, Auditability, Privacy
- [ ] D) Consistency, Authorization, Performance

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Consistency, Availability, Partition Tolerance

**Explanation:** The CAP theorem stands for Consistency (every read receives the most recent write or an error), Availability (every non-failing node returns a response), and Partition Tolerance (the system continues to operate despite network partitions/message losses).

**Why others are wrong:**
- A), C), and D) contain incorrect words (e.g., Concurrency, Privacy, Performance, Portability) that are not part of the CAP theorem.
</details>

---

### Q15. When creating a DynamoDB table with a composite primary key, what is the role of the Sort Key (SK)?

- [ ] A) It determines the physical partition where the item is stored.
- [ ] B) It acts as a secondary index that automatically sorts the entire table by that attribute.
- [ ] C) It groups items with the same partition key physically together and sorts them by the SK value.
- [ ] D) It is only used to enforce unique values across the entire table.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) It groups items with the same partition key physically together and sorts them by the SK value.

**Explanation:** In a composite primary key, the Partition Key (PK) determines physical partition placement, and the Sort Key (SK) determines the physical sorting order of items within that partition. This enables efficient range queries using the SK.

**Why others are wrong:**
- A) The Partition Key determines physical partition location, not the Sort Key.
- B) The SK sorts items within a specific partition, not globally across the entire table.
- D) The primary key uniqueness is enforced by the combination of PK + SK, not just the SK.
</details>

---

## Part 2: True / False (Monday-Wednesday Topics)

---

### Q16. True or False: Stored procedures in PostgreSQL (PL/pgSQL) are executed using the `SELECT` statement, similar to built-in aggregate functions.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Stored procedures are invoked using the `CALL` command (e.g., `CALL process_order(123);`). User-defined functions (UDFs) are executed within `SELECT` statements (e.g., `SELECT calculate_tax(100.00);`).

</details>

---

### Q17. True or False: Adding an index to a database table will speed up all query operations, including data modifications like INSERT and UPDATE.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** While indexes speed up data retrieval (reads), they slow down data modifications (writes) because the database engine must update both the table data pages and all associated index structures every time a write occurs.

</details>

---

### Q18. True or False: Under the AWS Shared Responsibility Model, the customer is responsible for patch management of the hypervisor layer that runs virtual servers.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** AWS is responsible for physical security, infrastructure, and virtualization hypervisors (security "of" the cloud). The customer is responsible for everything inside the guest operating system (security "in" the cloud).

</details>

---

### Q19. True or False: In Amazon S3, bucket names must be globally unique across all AWS accounts and all AWS regions.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** S3 buckets are accessed via DNS-compliant URLs (e.g., `https://bucket-name.s3.amazonaws.com`). Because bucket names appear in the public URL structure, they must be globally unique across all AWS regions and customer accounts.

</details>

---

### Q20. True or False: Because Amazon DynamoDB is a NoSQL schema-less database, items stored in the same table can contain entirely different sets of attributes, with the sole exception of the primary key attributes.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** DynamoDB only enforces the presence and data types of the primary key attributes (Partition Key and optional Sort Key) when creating items. Other attributes are dynamic and can vary from item to item.

</details>

---

### Q21. True or False: In Amazon DynamoDB, a Scan operation is generally more efficient and cheaper than a Query operation because it searches the entire table at once.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** A Scan is highly inefficient and expensive because it reads every single item in the table and consumes Read Capacity Units (RCUs) for the entire table. A Query is targeted, looking up items in a single partition using the partition key.

</details>

---

### Q22. True or False: In DynamoDB, conditional writes (such as using `ConditionExpression`) prevent concurrent operations from overwriting existing data.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Conditional writes allow developers to specify conditions (e.g., `attribute_not_exists(id)`) that must be met for a write operation to succeed. If the condition is not met, DynamoDB rejects the write, preventing accidental overwrites.

</details>

---

### Q23. True or False: Standard SQL Views physically duplicate table data on disk, which explains why querying a view is faster than querying base tables.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** A standard View is simply a saved SELECT query definition; it does not store data on disk. When queried, it executes the query against the underlying tables. Materialized Views, however, do store data physically on disk.

</details>

---

### Q24. True or False: If a transaction fails mid-way, the Consistency property of ACID is the specific mechanism that performs the rollback to the starting state using the Write-Ahead Log.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** The "all-or-nothing" rollback mechanism is governed by the **Atomicity** property. Atomicity relies on transaction logs to undo partial changes. Consistency ensures that the resulting state conforms to all database constraints.

</details>

---

### Q25. True or False: To mitigate AI hallucinations when designing cloud architectures, anchoring prompts with specific service versions and cross-referencing AI outputs against official, date-stamped cloud documentation is a recommended practice.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Cloud service APIs change frequently, leading to AI training-cutoff hallucinations. Mitigation strategies include version anchoring, using search-grounded prompting, and verifying key settings against official documentation.

</details>

---

## Part 3: Code Prediction (Monday-Wednesday Topics)

---

### Q26. Given the following PostgreSQL table and transactional block:

```sql
CREATE TABLE accounts (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    balance DECIMAL(10,2) CHECK (balance >= 0.00)
);

INSERT INTO accounts VALUES (1, 'Alice', 100.00);

BEGIN;

UPDATE accounts SET balance = balance - 40.00 WHERE id = 1;
SAVEPOINT sp1;

UPDATE accounts SET balance = balance - 80.00 WHERE id = 1;
-- Note: This balance update would violate the check constraint.

ROLLBACK TO sp1;
COMMIT;
```

**What will be the balance of Alice's account after executing this code?**

- [ ] A) 100.00
- [ ] B) 60.00
- [ ] C) -20.00
- [ ] D) The database will throw an error and rollback the entire transaction, leaving the table empty.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) 60.00

**Explanation:** The first update reduces the balance to 60.00. A savepoint `sp1` is set. The second update attempts to deduct 80.00 (resulting in -20.00), which violates the constraint and fails. The script executes `ROLLBACK TO sp1`, which rolls back the second update but keeps the first. The transaction is then committed, leaving the balance at 60.00.

**Why others are wrong:**
- A) The transaction committed the first change ($40.00 deduction).
- C) The balance cannot go negative because of the CHECK constraint.
- D) Because `ROLLBACK TO sp1` was executed, the constraint error inside the transaction block was handled, allowing the transaction to commit successfully.
</details>

---

### Q27. Consider the following PostgreSQL PL/pgSQL function and trigger definition:

```sql
CREATE TABLE items (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    status VARCHAR(20)
);

CREATE OR REPLACE FUNCTION set_default_status()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status IS NULL THEN
        NEW.status := 'pending';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_set_status
BEFORE INSERT ON items
FOR EACH ROW
EXECUTE FUNCTION set_default_status();

INSERT INTO items (id, name, status) VALUES (1, 'Widget', NULL);
INSERT INTO items (id, name, status) VALUES (2, 'Gadget', 'active');
```

**What will be the `status` values for Widget (id=1) and Gadget (id=2) in the database?**

- [ ] A) Widget: `NULL`, Gadget: `active`
- [ ] B) Widget: `pending`, Gadget: `pending`
- [ ] C) Widget: `pending`, Gadget: `active`
- [ ] D) The insert statements will fail because triggers cannot change NULL values.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Widget: `pending`, Gadget: `active`

**Explanation:** The trigger runs `BEFORE INSERT`. For Widget (id=1), `NEW.status` is NULL, so the trigger sets it to `'pending'`. For Gadget (id=2), `NEW.status` is `'active'` (which is not NULL), so the trigger does not change it.

**Why others are wrong:**
- A) The trigger updated Widget's status to 'pending' because it was inserted as NULL.
- B) Gadget's status was not NULL, so the condition was not met and its value remained 'active'.
- D) A BEFORE INSERT trigger can modify `NEW` columns, including resolving NULL values.
</details>

---

### Q28. What will the following PostgreSQL query sequence return?

```sql
CREATE SEQUENCE order_seq START WITH 10 INCREMENT BY 5;

SELECT nextval('order_seq');
SELECT nextval('order_seq');
SELECT currval('order_seq');
```

**What value is returned by the final `currval` query?**

- [ ] A) 10
- [ ] B) 15
- [ ] C) 20
- [ ] D) 25

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) 15

**Explanation:** The sequence starts at 10. The first call to `nextval` returns 10. The second call to `nextval` increments the sequence by 5 and returns 15. The call to `currval` returns the value of the sequence as of the most recent `nextval` call in the current session, which is 15.

**Why others are wrong:**
- A) 10 was the first value returned, but the sequence has been advanced.
- C) The sequence is at 15. Calling `currval` does not increment it.
- D) The sequence has only been advanced once from 10 to 15.
</details>

---

### Q29. In a Python application using `boto3` to communicate with DynamoDB, you execute the following code:

```python
import boto3
from botocore.exceptions import ClientError

dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
table = dynamodb.Table('Products')

try:
    response = table.put_item(
        Item={
            'ProductId': 'P100',
            'Name': 'Laptop',
            'Stock': 5
        },
        ConditionExpression='attribute_not_exists(ProductId)'
    )
except ClientError as e:
    print(e.response['Error']['Code'])
```

**Assume an item with `ProductId` equal to `'P100'` already exists in the table. What will this script print?**

- [ ] A) `Success`
- [ ] B) `ConditionalCheckFailedException`
- [ ] C) `TransactionConflictException`
- [ ] D) Nothing, it executes silently.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `ConditionalCheckFailedException`

**Explanation:** The `ConditionExpression` specifies `attribute_not_exists(ProductId)`. Since an item with ProductId `'P100'` already exists, the condition evaluates to false. DynamoDB aborts the write and throws a `ClientError` with the code `'ConditionalCheckFailedException'`.

**Why others are wrong:**
- A) The write fails, so it does not succeed.
- C) This is not a transaction conflict, but a specific conditional write check failure.
- D) The exception is caught and printed.
</details>

---

### Q30. What will the following PostgreSQL scalar function query return?

```sql
SELECT COALESCE(NULLIF('Active', 'Active'), 'DefaultStatus');
```

- [ ] A) `Active`
- [ ] B) `NULL`
- [ ] C) `DefaultStatus`
- [ ] D) Error: invalid function arguments

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `DefaultStatus`

**Explanation:** `NULLIF('Active', 'Active')` compares the two strings. Since they are equal, it returns `NULL`. The query then evaluates `COALESCE(NULL, 'DefaultStatus')`. Since the first argument is NULL, `COALESCE` returns the first non-NULL argument, which is `'DefaultStatus'`.

**Why others are wrong:**
- A) The result of `NULLIF` is NULL, so `COALESCE` replaces it.
- B) `COALESCE` evaluates the NULL and returns the second argument.
- D) Both functions are standard and the syntax is valid.
</details>

---

## Part 4: Fill in the Blank (Monday-Wednesday Topics)

---

### Q31. The set of database properties that guarantee reliable transaction processing is known by the acronym __________.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** ACID

**Explanation:** ACID stands for Atomicity, Consistency, Isolation, and Durability. These properties ensure that transactions are processed reliably and data integrity is maintained even in the event of failures.

</details>

---

### Q32. In database transactions, the concurrency anomaly where a transaction reads changes made by another concurrent transaction that have not yet been committed is called a __________ Read.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** Dirty

**Explanation:** A Dirty Read occurs when an isolation level allows a transaction to view changes that are not committed. If the modifying transaction is rolled back, the reading transaction has acted on bad data.

</details>

---

### Q33. The AWS Global Infrastructure component that consists of one or more physical data centers with redundant power, networking, and connectivity within a Region is called an __________ Zone.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** Availability

**Explanation:** An Availability Zone (AZ) is a fully isolated partition of the AWS infrastructure. Deploying applications in multiple AZs provides high availability and fault tolerance.

</details>

---

### Q34. In Amazon DynamoDB, the API operation that retrieves a single item using its primary key is called __________, and the API operation that retrieves all items matching a single partition key is called __________.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** GetItem, Query

**Explanation:** `GetItem` is a key lookup returning a single item. `Query` targets a single partition key value and returns a list of items, optionally filtered by a sort key.

</details>

---

### Q35. The CAP Theorem states that a distributed data store can guarantee at most two of three properties: Consistency, Availability, and __________ Tolerance.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** Partition

**Explanation:** In a distributed network, network splits (partitions) are inevitable. Distributed databases must choose between Consistency (all nodes see same data) or Availability (every node responds) when a partition occurs.

</details>

---

## Part 5: Multiple Choice (Thursday-Friday Topics)

---

### Q36. Which database design schema organizes data warehouses into a central fact table surrounded by simplified, denormalized dimension tables?

- [ ] A) Snowflake Schema
- [ ] B) Star Schema
- [ ] C) Relational Schema
- [ ] D) Document Schema

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Star Schema

**Explanation:** A Star Schema is the simplest data warehouse schema. It consists of a large, central fact table referencing several dimension tables. The dimensions are completely denormalized (not split into secondary tables), forming a star shape.

**Why others are wrong:**
- A) A Snowflake Schema normalizes dimension tables, splitting them into sub-tables.
- C) Relational Schema is a generic database structure, not specific to OLAP dimensional modeling.
- D) Document Schema is a NoSQL paradigm.
</details>

---

### Q37. What is the fundamental difference between an OLTP workload and an OLAP workload?

- [ ] A) OLTP databases are column-oriented; OLAP databases are row-oriented.
- [ ] B) OLTP is optimized for small, fast transaction writes; OLAP is optimized for complex, read-heavy analytical aggregations over historical data.
- [ ] C) OLTP is for non-relational data; OLAP is for relational data.
- [ ] D) OLTP does not support transactions, whereas OLAP does.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) OLTP is optimized for small, fast transaction writes; OLAP is optimized for complex, read-heavy analytical aggregations over historical data.

**Explanation:** OLTP (Online Transaction Processing) handles operational data changes (e.g., e-commerce orders, bank deposits). OLAP (Online Analytical Processing) handles historical database query analysis (e.g., total sales revenue by region over three years).

**Why others are wrong:**
- A) OLTP is typically row-oriented (fast write updates), and OLAP is columnar (fast aggregates).
- C) Both relational and NoSQL can participate in OLTP; OLAP is historically relational but can ingest NoSQL.
- D) OLTP is specifically designed for high-concurrency transactions.
</details>

---

### Q38. How does Columnar Storage in Amazon Redshift speed up analytical queries such as `SELECT AVG(sales_amount) FROM sales;`?

- [ ] A) It caches all query results in memory.
- [ ] B) It reads only the data blocks associated with the `sales_amount` column from disk, skipping all other columns.
- [ ] C) It automatically converts the table into a NoSQL document.
- [ ] D) It executes the query on the client side.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It reads only the data blocks associated with the `sales_amount` column from disk, skipping all other columns.

**Explanation:** In a row-oriented database, the system must read every row's full record from disk to extract the `sales_amount` column. In columnar storage, each column is stored in its own set of disk blocks. The query engine only reads the blocks for the target column, drastically reducing disk I/O.

**Why others are wrong:**
- A) While Redshift caches query results, the primary speed benefit of columnar storage comes from physical disk I/O savings.
- C) Redshift remains a relational SQL database.
- D) The query is executed in parallel across compute nodes, not on the client.
</details>

---

### Q39. In the Amazon Redshift architecture, which component manages incoming SQL connections, parses queries, and coordinates parallel execution across compute nodes?

- [ ] A) Compute Node
- [ ] B) Leader Node
- [ ] C) Query Slice
- [ ] D) Redshift Spectrum

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Leader Node

**Explanation:** The Leader Node is the front-end coordinator of the Redshift cluster. It handles connections, compiles queries, generates the execution plan, and aggregates results returned by compute nodes.

**Why others are wrong:**
- A) Compute Nodes store the actual database tables and execute execution plan steps in parallel.
- C) A Slice is a partition of a compute node's memory and disk.
- D) Redshift Spectrum is a feature for querying data directly in Amazon S3.
</details>

---

### Q40. Which Amazon Redshift table distribution style copies the entire table to every compute node, maximizing join performance for small lookup tables?

- [ ] A) EVEN Distribution
- [ ] B) KEY Distribution
- [ ] C) ALL Distribution
- [ ] D) AUTO Distribution

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) ALL Distribution

**Explanation:** The `ALL` distribution style copies the entire table to the first slice of every compute node. This eliminates network traffic when joining this table with other distributed tables, but increases storage overhead. It is ideal for small dimension tables.

**Why others are wrong:**
- A) `EVEN` distributes rows round-robin across nodes, causing network shuffling during joins.
- B) `KEY` distributes rows based on hash values of a specific column.
- D) `AUTO` lets Redshift choose the distribution style, dynamically changing it over time.
</details>

---

### Q41. Why is the Redshift `COPY` command preferred over standard SQL `INSERT` statements when loading millions of records into a table from S3?

- [ ] A) `COPY` skips all validation checks.
- [ ] B) `COPY` leverages the cluster's compute nodes to download and write data files in parallel.
- [ ] C) `COPY` automatically normalizes the target tables.
- [ ] D) `INSERT` statements cannot connect to S3.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `COPY` leverages the cluster's compute nodes to download and write data files in parallel.

**Explanation:** The `COPY` command is designed for bulk loading. Compute nodes read partition files in parallel directly from S3, bypassing the leader node's execution bottlenecks. Using row-by-row `INSERT` statements routes all writes through the leader node, which is highly inefficient.

**Why others are wrong:**
- A) `COPY` validates data formats and constraints during the load.
- C) `COPY` writes directly to the existing table schema; it does not alter schema normalization.
- D) `INSERT` statements can insert query results from S3 using Spectrum, but it is not optimized for parallel bulk loading.
</details>

---

### Q42. When configuring a Redshift `COPY` command, what is the most secure and recommended way to authorize the cluster to read data from your Amazon S3 bucket?

- [ ] A) Embed the AWS Account Root password directly in the SQL script.
- [ ] B) Associate an IAM Role with the Redshift cluster and provide the role's Amazon Resource Name (ARN) in the command.
- [ ] C) Configure the S3 bucket to be completely public to the internet.
- [ ] D) Hardcode your personal AWS Access Key and Secret Key in the query string.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Associate an IAM Role with the Redshift cluster and provide the role's Amazon Resource Name (ARN) in the command.

**Explanation:** Providing an IAM Role ARN is the industry best practice. The role grants temporary, secure access permissions to Redshift, eliminating the need to hardcode permanent credentials in scripts or expose S3 buckets publicly.

**Why others are wrong:**
- A) Root passwords should never be used or shared in code.
- C) Exposing buckets publicly is a major security risk.
- D) Hardcoding API keys is insecure and violates AWS security guidelines.
</details>

---

### Q43. What is the correct Java JDBC connection string format for connecting to an Amazon Redshift cluster?

- [ ] A) `jdbc:postgresql://[endpoint]:[port]/[database]`
- [ ] B) `jdbc:redshift://[endpoint]:[port]/[database]`
- [ ] C) `jdbc:aws:redshift://[endpoint]:[port]/[database]`
- [ ] D) `redshift:jdbc://[endpoint]:[port]/[database]`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `jdbc:redshift://[endpoint]:[port]/[database]`

**Explanation:** Redshift uses the connection protocol `jdbc:redshift://` followed by the cluster endpoint (usually on port 5439) and the database name.

**Why others are wrong:**
- A) While Redshift was historically compatible with PostgreSQL drivers, the official Redshift driver uses the `jdbc:redshift://` prefix.
- C) and D) are invalid connection URI formats.
</details>

---

### Q44. Which of the following file formats is NOT supported natively by the Redshift `COPY` command for bulk data ingestion?

- [ ] A) CSV
- [ ] B) JSON
- [ ] C) Apache Parquet
- [ ] D) Microsoft Excel (.xlsx)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** D) Microsoft Excel (.xlsx)

**Explanation:** Redshift does not support loading raw Excel spreadsheets. Data must be exported to text/binary formats like CSV, JSON, Parquet, ORC, or Avro before executing `COPY`.

**Why others are wrong:**
- A), B), and C) are fully supported native formats.
</details>

---

### Q45. Your application must process 10,000 requests per second of simple, key-value user session updates with sub-10 millisecond latency. Which database service should you choose?

- [ ] A) Amazon RDS PostgreSQL
- [ ] B) Amazon Redshift
- [ ] C) Amazon DynamoDB
- [ ] D) Amazon S3

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Amazon DynamoDB

**Explanation:** DynamoDB is a serverless, key-value/document NoSQL database engineered to provide single-digit millisecond latency at massive scale. It is ideal for transactional, high-throughput key-value operations like session states.

**Why others are wrong:**
- A) RDS PostgreSQL is a relational database and cannot scale to 10k writes/sec as cost-effectively or quickly as DynamoDB.
- B) Redshift is an OLAP warehouse meant for analytics; it cannot handle high-concurrency, low-latency key-value writes.
- D) S3 is object storage, which has high latencies (50-100+ ms) and is not suitable for high-throughput transactional states.
</details>

---

### Q46. If your Redshift `COPY` command fails due to a data type mismatch in one of the input files, which system table should you query to inspect the detailed error logs?

- [ ] A) `STL_LOAD_ERRORS`
- [ ] B) `SVV_TABLE_INFO`
- [ ] C) `PG_STAT_ACTIVITY`
- [ ] D) `STL_QUERY_METRICS`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `STL_LOAD_ERRORS`

**Explanation:** `STL_LOAD_ERRORS` is the Redshift system table that records detailed error messages, filenames, line numbers, and reason codes for all failed data load operations.

**Why others are wrong:**
- B) `SVV_TABLE_INFO` contains storage and key info for tables.
- C) `PG_STAT_ACTIVITY` displays current active connections and queries.
- D) `STL_QUERY_METRICS` records runtime metrics of executed queries.
</details>

---

### Q47. When using an AI assistant to translate SQL queries from PostgreSQL to Amazon Redshift, what is the primary risk?

- [ ] A) The AI will use standard SELECT statements which Redshift does not support.
- [ ] B) The AI may generate syntax containing unsupported PostgreSQL functions or design features (such as local indexes or recursive CTE constraints).
- [ ] C) The AI will try to translate the relational SQL into a DynamoDB API call.
- [ ] D) The AI will fail to output valid SQL text formats.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The AI may generate syntax containing unsupported PostgreSQL functions or design features (such as local indexes or recursive CTE constraints).

**Explanation:** Although Redshift is based on PostgreSQL, it has diverged significantly. It does not support many PostgreSQL scalar functions, triggers, or indices. AI engines often hallucinate compatibility, requiring developers to manually review the generated SQL.

**Why others are wrong:**
- A) Redshift is a SQL database and fully supports standard SELECT statements.
- C) The AI output depends on the prompt; it will not translate to DynamoDB unless explicitly instructed.
- D) AI assistants excel at generating syntactically structured text blocks.
</details>

---

### Q48. In a Java JDBC application, why is it critical to close connection objects (e.g., using Try-With-Resources) after executing statements against Redshift?

- [ ] A) Redshift will delete the tables if the connection is left open.
- [ ] B) It prevents connection leaks that exhaust the database server's connection pool, which would block new incoming client connections.
- [ ] C) Closed connections are required to trigger the Redshift query compiler.
- [ ] D) The driver will throw a compilation error in Java if close is omitted.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It prevents connection leaks that exhaust the database server's connection pool, which would block new incoming client connections.

**Explanation:** Database connections are expensive resources. Failing to close connections causes "leaks," keeping database ports open and exhausting the connection capacity of the cluster. Try-with-resources in Java guarantees that resources are closed automatically.

**Why others are wrong:**
- A) Redshift persists data independently of connections.
- C) Compilation and execution happen inside active sessions.
- D) Omiting `close()` is a runtime logic error, not a Java compiler syntax error.
</details>

---

## Part 6: True / False (Thursday-Friday Topics)

---

### Q49. True or False: In a columnar database like Amazon Redshift, retrieving all column values for a single row is more efficient than in a row-oriented database like PostgreSQL.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Row-oriented databases store entire rows contiguously on disk, making single-row lookups highly efficient. Columnar databases store column values contiguously. Retrieving all columns for a single row requires reading from multiple separate disk blocks, causing more I/O.

</details>

---

### Q50. True or False: The Redshift `COPY` command can automatically divide the load workload and load multiple source files from S3 in parallel if the files share a prefix or are defined in a manifest file.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Redshift computes copy slices in parallel. If your S3 data is split into multiple files (e.g., `data.csv.1`, `data.csv.2`), Redshift compute node slices will download and ingest these files concurrently, speeding up the load.

</details>

---

### Q51. True or False: In a multi-node Amazon Redshift cluster, the Leader Node is responsible for storing user data slices and executing the physical query segments.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** The **Compute Nodes** store user data and execute query blocks in parallel. The Leader Node only coordinates, parses SQL, and aggregates final results.

</details>

---

### Q52. True or False: When building Java DAO applications that connect to Amazon Redshift, using the standard PostgreSQL JDBC driver is recommended for optimal performance and compatibility.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** While the PostgreSQL driver works for basic queries, you should use the official Amazon Redshift JDBC driver for production. The Redshift-specific driver provides significant performance improvements and supports Redshift-specific security features (like IAM credentials).

</details>

---

### Q53. True or False: An OLAP database is optimized for handling high-concurrency, low-latency row inserts from millions of active users.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** OLAP databases (like Redshift) are optimized for complex read queries over large datasets. High-frequency individual row inserts are slow on OLAP and should be buffered in S3 and loaded in batches using `COPY`. OLTP databases are optimized for low-latency row inserts.

</details>

---

### Q54. True or False: In a Star schema design, dimension tables are highly normalized into multiple related tables to eliminate data redundancy.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Star schemas feature **denormalized** dimension tables (keeping details in a single flat table) to simplify queries and avoid expensive JOIN operations. Normalizing dimension tables results in a **Snowflake schema**.

</details>

---

### Q55. True or False: When migrating SQL scripts from PostgreSQL to Amazon Redshift, all queries will run without modification because Redshift is fully compliant with PostgreSQL's SQL dialect.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Redshift lacks support for many PostgreSQL features, including serial data types, array columns, certain date/time functions, and stored procedures written in languages other than PL/pgSQL. Schema tables must also be modified to include distribution keys.

</details>

---

### Q56. True or False: By default, if the Redshift `COPY` command encounters a single invalid data row in your S3 file (e.g., text in an integer column), it rolls back the entire load operation.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** The default behavior of `COPY` is transactionally atomic. If an error is encountered, the load aborts and rolls back. You can modify this behavior using parameters like `MAXERROR` to allow a certain number of bad rows before failing.

</details>

---

## Part 7: Code Prediction (Thursday-Friday Topics)

---

### Q57. Consider the following Redshift `COPY` statement:

```sql
COPY retail_sales
FROM 's3://my-company-bucket/raw-data/sales_2026.csv'
IAM_ROLE 'arn:aws:iam::123456789012:role/RedshiftS3Read'
FORMAT AS CSV;
```

**Assume the S3 file `sales_2026.csv` contains a header row (`transaction_id,item_id,amount`). The `retail_sales` table has columns `(transaction_id INT, item_id VARCHAR(50), amount DECIMAL(10,2))`.**

**What will happen when you execute this query?**

- [ ] A) The query succeeds, automatically skipping the header row because column names match.
- [ ] B) The query fails with a data type mismatch error in `STL_LOAD_ERRORS` because it tries to parse `'transaction_id'` as an integer.
- [ ] C) The header row is loaded into the table, converting all column types to text.
- [ ] D) The database engine suspends the execution and prompts the user to verify column alignments.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The query fails with a data type mismatch error in `STL_LOAD_ERRORS` because it tries to parse `'transaction_id'` as an integer.

**Explanation:** By default, the `COPY` command tries to load every line of the CSV as a data row. The header row contains text strings. Since `transaction_id` is defined as an integer, parsing the text `'transaction_id'` causes a type mismatch and aborts the load. You must add `IGNOREHEADER 1` to skip the header.

**Why others are wrong:**
- A) Redshift does not auto-detect headers based on column names.
- C) It cannot load the header text because column types are strictly checked (decimal/integer).
- D) Redshift queries run non-interactively; it will fail immediately.
</details>

---

### Q58. Examine the following Java JDBC code snippet executing against a Redshift cluster:

```java
import java.sql.*;

public class AnalyticsReport {
    public static void main(String[] args) {
        String url = "jdbc:redshift://redshift-cluster.abc123xyz.us-east-1.redshift.amazonaws.com:5439/dev";
        String user = "awsuser";
        String password = "Password123";

        String query = "SELECT category, SUM(price) FROM sales GROUP BY category;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println(rs.getString("category") + ": " + rs.getDouble(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

**Assuming the database connection credentials are correct and the table contains valid data, what will be the result?**

- [ ] A) It fails to compile because `rs.getDouble(2)` uses a 1-indexed column index, which is out of bounds for a query with two select terms.
- [ ] B) It compiles and runs successfully, printing each category name and its sum value.
- [ ] C) It throws a SQLException because Redshift JDBC statement objects do not support `executeQuery()`.
- [ ] D) It throws a NullPointerException because the try-with-resources block automatically closes the ResultSet before the while loop starts.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It compiles and runs successfully, printing each category name and its sum value.

**Explanation:** In JDBC, column indices are 1-based. The select statement returns `category` (index 1) and `SUM(price)` (index 2). Accessing columns using `rs.getString("category")` and `rs.getDouble(2)` is valid. The try-with-resources handles closing the resources at the end of the block.

**Why others are wrong:**
- A) The index is 1-based, so index 2 refers to the second column, which is valid.
- C) `Statement.executeQuery()` is standard JDBC and fully supported.
- D) Try-with-resources only closes resources when exiting the block, not during execution of the block.
</details>

---

### Q59. Review the following Redshift SQL query:

```sql
SELECT category, region, SUM(amount) AS total_sales
FROM sales_data
GROUP BY category, region
HAVING SUM(amount) > 5000
ORDER BY total_sales DESC;
```

**Which of the following statements correctly describes what this query does?**

- [ ] A) It filters rows in the `sales_data` table where `amount > 5000`, groups them, and sorts the results.
- [ ] B) It calculates the sum of sales for each category/region group, filters out groups where the sum is 5000 or less, and sorts the remaining groups.
- [ ] C) It returns an error because `HAVING` cannot reference aggregate functions.
- [ ] D) It returns an error because you cannot order by an alias `total_sales` in Redshift.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It calculates the sum of sales for each category/region group, filters out groups where the sum is 5000 or less, and sorts the remaining groups.

**Explanation:** The `GROUP BY` clause groups rows by category and region. `SUM(amount)` aggregates sales within each group. The `HAVING` clause acts on the aggregated results, filtering out any group with a sum less than or equal to 5000. `ORDER BY` sorts the remaining records.

**Why others are wrong:**
- A) Filtering rows before grouping is done with the `WHERE` clause, not `HAVING`.
- C) `HAVING` is specifically designed to filter on aggregates.
- D) Redshift supports ordering by select aliases.
</details>

---

## Part 8: Fill in the Blank (Thursday-Friday Topics)

---

### Q60. In Amazon Redshift, the table distribution style where a copy of the entire table is duplicated on every compute node is called the __________ distribution style.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** ALL

**Explanation:** The `ALL` distribution style copies the entire table to every compute node, which avoids network transfer costs during JOIN operations with other tables, but consumes more storage.

</details>

---

### Q61. The Redshift system log table that records error details for failed S3 data loads is called __________.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** STL_LOAD_ERRORS

**Explanation:** Querying `STL_LOAD_ERRORS` allows database administrators to identify the specific file, line number, column, and parsing error that caused a `COPY` command to abort.

</details>

---

### Q62. When connecting a Java application to a database, the JDBC URL for Redshift begins with the protocol prefix `jdbc:__________://`.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** redshift

**Explanation:** The database protocol identifier is `jdbc:redshift://`. Standard PostgreSQL JDBC applications can also connect using `jdbc:postgresql://`, but the official Redshift driver is preferred.

</details>

---

### Q63. The modern data architecture process that loads raw data directly into the target data warehouse and then performs transformations using the warehouse's compute nodes is known as __________.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** ELT (Extract, Load, Transform)

**Explanation:** Unlike traditional ETL (Extract, Transform, Load), ELT takes advantage of the massive parallel computing power of modern data warehouses (like Redshift) to run transformations within the database itself.

</details>

---

### Q64. The Redshift table distribution style that distributes rows across compute node slices in a round-robin fashion, ensuring an equal number of rows on each node, is called the __________ distribution style.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** EVEN

**Explanation:** The `EVEN` distribution style is the default. It distributes rows evenly to ensure uniform resource utilization, but may require data shuffling over the network during complex table joins.

</details>

---

### Q65. The Redshift `COPY` parameter used to specify the number of row formatting errors that are permitted before the entire load fails is __________.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** MAXERROR

**Explanation:** The `MAXERROR` option prevents a bulk load from rolling back if a few rows are corrupted. For example, `MAXERROR 10` ignores the first 9 row errors and only fails if a 10th error occurs.

</details>

---

## Part 9: Additional Multiple Choice (Advanced Topics)

---

### Q66. When selecting a column to act as the distribution key (`DISTKEY` / KEY distribution style) for a large Redshift fact table, which column selection is best?

- [ ] A) A column with low cardinality, such as `gender` or `status_active`.
- [ ] B) A column with high cardinality that is frequently used as a join condition with dimension tables.
- [ ] C) A column containing sequential dates, such as `created_at`.
- [ ] D) The primary key of the table, regardless of how it is queried.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A column with high cardinality that is frequently used as a join condition with dimension tables.

**Explanation:** An optimal distribution key distributes data evenly across nodes to prevent "skew" (hotspots) while aligning rows with tables they are frequently joined against, which eliminates network transfers (shuffling) during query execution.

**Why others are wrong:**
- A) Low cardinality columns cause skew, sending most data rows to a small subset of nodes.
- C) Sequential date keys cause date-based queries to hit only one node (the one storing today's date), leaving other compute nodes idle.
- D) The primary key is only optimal if it matches join criteria; otherwise, it may trigger network shuffling.
</details>

---

### Q67. In Amazon DynamoDB Single-Table Design, why are key attributes typically given generic names like `PK` (Partition Key) and `SK` (Sort Key) instead of entity-specific names like `CustomerId` or `OrderId`?

- [ ] A) DynamoDB only allows two column names in the entire database.
- [ ] B) Generic names allow the table to store different entity types (e.g., Users, Orders, Products) in the same table, using prefixes (like `USER#123` or `ORDER#456`) to separate them.
- [ ] C) Generic names automatically enable Global Secondary Indexes.
- [ ] D) Boto3 scripts throw syntax errors if entity-specific names are used.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Generic names allow the table to store different entity types (e.g., Users, Orders, Products) in the same table, using prefixes (like `USER#123` or `ORDER#456`) to separate them.

**Explanation:** Single-Table Design collapses multiple relational tables into a single NoSQL table. To support different entity types, the table must use generic key names. PK/SK values are formatted with prefixes (e.g., `PK=USER#123`, `SK=METADATA`) to represent different entities and access patterns.

**Why others are wrong:**
- A) DynamoDB allows thousands of attribute names.
- C) GSIs must still be explicitly defined; generic key names do not configure them automatically.
- D) Boto3 supports any attribute names.
</details>

---

### Q68. By default, Amazon DynamoDB reads are eventually consistent. If your application requires the absolute latest write data immediately, how should you request a read?

- [ ] A) Run a Scan operation instead of a GetItem.
- [ ] B) Set the `ConsistentRead` parameter to `True` in your API request.
- [ ] C) Execute a SQL commit statement before querying.
- [ ] D) Wait 5 seconds after writing before executing the query.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Set the `ConsistentRead` parameter to `True` in your API request.

**Explanation:** By default, DynamoDB reads may return stale data (eventual consistency). To retrieve the most recent write, setting `ConsistentRead = True` forces DynamoDB to read from a majority of replica nodes to return the latest state, consuming double the Read Capacity Units (RCUs).

**Why others are wrong:**
- A) A Scan does not change the consistency settings of the database lookup.
- C) DynamoDB is a NoSQL API and does not support SQL COMMIT statements.
- D) While waiting works, it is an application-level workaround, not a database consistency guarantee.
</details>

---

### Q69. Which of the following operations is highly inefficient and should be minimized in analytical data warehouses like Amazon Redshift?

- [ ] A) Running massive SUM or AVG aggregates over billions of rows.
- [ ] B) Executing low-latency, single-row UPDATE or INSERT statements.
- [ ] C) Executing COPY commands to load millions of rows from S3.
- [ ] D) Running queries with multiple GROUP BY dimensions.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Executing low-latency, single-row UPDATE or INSERT statements.

**Explanation:** Columnar databases write data column-by-column, which makes single-row updates/inserts slow and computationally expensive. They are designed for batch loads (using `COPY`) and analytical queries, not transactional OLTP workloads.

**Why others are wrong:**
- A) Aggregates are what columnar databases are optimized to run at high speed.
- C) The `COPY` command is the standard, highly optimized way to load data into Redshift.
- D) Grouping by multiple dimensions is a standard OLAP operation that Redshift executes in parallel.
</details>

---

### Q70. When translating a PostgreSQL query that contains complex recursive Common Table Expressions (CTEs) and multi-table joins to an Amazon DynamoDB access pattern, what is the primary architectural challenge?

- [ ] A) DynamoDB cannot store JSON data.
- [ ] B) DynamoDB does not support server-side joins or recursive queries; the data must be pre-joined (denormalized) using a Single-Table design or resolved using multiple sequential queries.
- [ ] C) PostgreSQL queries cannot run on cloud environments.
- [ ] D) Python does not support connecting to both PostgreSQL and DynamoDB.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) DynamoDB does not support server-side joins or recursive queries; the data must be pre-joined (denormalized) using a Single-Table design or resolved using multiple sequential queries.

**Explanation:** Relational queries rely on the database query engine to execute joins on-the-fly. DynamoDB is designed for scale and lacks join logic. To query related data in NoSQL, you must denormalize data so related items are pre-merged, or write code to fetch records via sequential API lookups.

**Why others are wrong:**
- A) DynamoDB natively supports JSON-like document attributes.
- C) Both databases run on AWS cloud infrastructure.
- D) Python can query any number of databases within a single script.
</details>
