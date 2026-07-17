# Introduction to Amazon DynamoDB

## Learning Objectives
- Define Amazon DynamoDB and explain its role as a fully managed, serverless NoSQL database.
- Explain the difference between key-value and document data models in DynamoDB.
- Contrast the two primary key schemes: Simple Primary Key (Partition Key) and Composite Primary Key (Partition Key + Sort Key).
- Create a DynamoDB table with a primary key structure using the AWS CLI.

## Why This Matters
Relational databases (like PostgreSQL) are powerful, but they have scaling limits. When an application receives millions of concurrent requests, standard RDBMS engines can struggle due to connection limits, table locks, and complex join queries. Scaling a relational database vertically is expensive, and scaling horizontally requires complex sharding strategies.

Amazon DynamoDB addresses this challenge. DynamoDB is a fully managed NoSQL database designed to deliver single-digit millisecond performance at any scale. It handles horizontal scaling, replication, and data partitioning automatically. Understanding DynamoDB's data model and primary key schemes is the first step toward building fast, low-latency backends for high-concurrency cloud applications.

## The Concept

### What is Amazon DynamoDB?
Amazon DynamoDB is a fully managed, multi-region, multi-active, durable NoSQL database service. It is **serverless**, meaning you do not need to provision, manage, or patch physical database servers. Instead, you create tables and pay only for the storage you consume and the read/write request throughput you utilize.

DynamoDB scale is virtually unlimited. It can handle more than 10 trillion requests per day and support peaks of more than 20 million requests per second, maintaining single-digit millisecond response times.

### The NoSQL Data Model: Key-Value and Document
DynamoDB stores data using a flexible schema model:
- **Tables**: Collections of data records.
- **Items**: Individual records within a table (analogous to rows in RDBMS). Items are schema-less, meaning each item can have different attributes.
- **Attributes**: Individual data elements within an item (analogous to columns in RDBMS).
- **Supported Data Models**:
  - *Key-Value*: Items are retrieved using their unique primary key.
  - *Document*: Attributes can contain nested JSON structures, lists, and maps, allowing you to model complex, hierarchical data.

---

### DynamoDB Primary Keys
Unlike relational tables where primary keys are optional or can be declared on any column post-creation, **every DynamoDB table must have a primary key defined during table creation**. The primary key is used to distribute data across multiple physical partitions. 

DynamoDB supports two primary key structures:

#### 1. Simple Primary Key (Partition Key Only)
- **Structure**: Consists of a single attribute called the **Partition Key** (also known as the Hash Key).
- **Mechanics**: DynamoDB uses the value of the partition key as input to an internal hash function. The output of this function determines the physical storage partition where the item is stored.
- **Uniqueness**: No two items in the table can have the same partition key value.
- *Example*: A `users` table where `user_id` is the partition key.

#### 2. Composite Primary Key (Partition Key + Sort Key)
- **Structure**: Consists of two attributes: the **Partition Key** (Hash Key) and the **Sort Key** (Range Key).
- **Mechanics**: DynamoDB uses the partition key value to locate the physical partition. Within that partition, it stores and sorts items in ascending order based on the sort key value.
- **Uniqueness**: Multiple items can share the same partition key, but they must have different sort key values. The combination of partition key + sort key must be unique.
- *Example*: An `orders` table where `customer_id` is the partition key and `order_date` (or `order_id`) is the sort key. This allows a customer to have multiple orders, sorted by date.

```
       Table: customer_orders (Composite PK)
+-------------------+-----------------+-----------------------+
| Partition Key     | Sort Key        | Additional Attributes |
| (customer_id)     | (order_date)    | (total_amount)        |
+-------------------+-----------------+-----------------------+
| CUST-100          | 2026-07-01      | 150.00                |
| CUST-100          | 2026-07-05      | 45.00                 |
| CUST-200          | 2026-07-02      | 300.00                |
+-------------------+-----------------+-----------------------+
```

## Code Examples

Let us use the AWS CLI to create tables illustrating both primary key configurations.

### Example 1: Creating a Table with a Simple Primary Key
This table stores user profiles. The partition key is `user_id` (string type).

```bash
aws dynamodb create-table \
    --table-name user_profiles \
    --attribute-definitions AttributeName=user_id,AttributeType=S \
    --key-schema AttributeName=user_id,KeyType=HASH \
    --billing-mode PAY_PER_REQUEST
```

Key options explained:
- `--attribute-definitions`: Declares the name and data type of the key attributes. `S` stands for String, `N` for Number, and `B` for Binary.
- `--key-schema`: Declares the role of the key. `HASH` designates the attribute as the Partition Key.
- `--billing-mode PAY_PER_REQUEST`: Configures serverless billing, charging only for requests executed.

### Example 2: Creating a Table with a Composite Primary Key
This table stores customer orders. The partition key is `customer_id` (string), and the sort key is `order_id` (string).

```bash
aws dynamodb create-table \
    --table-name customer_orders \
    --attribute-definitions \
        AttributeName=customer_id,AttributeType=S \
        AttributeName=order_id,AttributeType=S \
    --key-schema \
        AttributeName=customer_id,KeyType=HASH \
        AttributeName=order_id,KeyType=RANGE \
    --billing-mode PAY_PER_REQUEST
```

Key options explained:
- `KeyType=HASH` designates `customer_id` as the Partition Key.
- `KeyType=RANGE` designates `order_id` as the Sort Key.

## Summary
- **Amazon DynamoDB** is a fully managed, serverless NoSQL database designed for fast performance at scale.
- Data is organized into schema-less **Items** containing **Attributes** grouped inside **Tables**.
- A **Simple Primary Key** uses a **Partition Key** only; hash values determine physical storage partitions.
- A **Composite Primary Key** uses a **Partition Key** and a **Sort Key**; items with the same partition key are sorted by their range values.

## Additional Resources
- [Amazon DynamoDB Developer Guide](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Introduction.html)
- [How Data Partitioning Works in Amazon DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.Partitions.html)
- [Core Components of Amazon DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.CoreComponents.html)
