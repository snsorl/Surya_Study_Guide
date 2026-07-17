# Converting a Relational Schema to DynamoDB

## Learning Objectives
- Map a normalized relational schema to a NoSQL DynamoDB design.
- Define data access patterns based on application requirements before designing keys.
- Choose appropriate partition and sort key structures to support query requirements.
- Apply denormalization and single-table design strategies to represent relationships.

## Why This Matters
When designing database schemas, relational and NoSQL databases require completely different approaches. In a relational database, you normalize tables to reduce redundancy, and construct SQL query joins as needed. 

If you use this relational approach in a NoSQL database (like DynamoDB), it results in poor performance because NoSQL does not support join queries. To build fast NoSQL databases, you must change your design process. Instead of starting with data entities, you start by defining your application's query requirements, and then design a single-table layout to support those queries. This guide provides a step-by-step framework for converting relational structures into efficient DynamoDB models.

## The Concept

### The Relational vs. NoSQL Design Workflow
The design workflows of relational and NoSQL databases are compared below:

```
    Relational Database Workflow
+-----------------------------------+
|      Define Data Entities         |
+-----------------------------------+
                  |
                  v
+-----------------------------------+
|      Normalize tables (3NF)       |
+-----------------------------------+
                  |
                  v
+-----------------------------------+
|   Write arbitrary SQL joins       |
+-----------------------------------+

      NoSQL Database Workflow
+-----------------------------------+
|     Identify Access Patterns      |
+-----------------------------------+
                  |
                  v
+-----------------------------------+
|   Design Partition & Sort Keys    |
+-----------------------------------+
                  |
                  v
+-----------------------------------+
|   Denormalize and combine data    |
+-----------------------------------+
```

---

### Step-by-Step Conversion Framework

#### Step 1: Identify and List all Access Patterns
Before writing any database configurations, list every query your application needs to run.
- *Example 1*: Fetch customer details by CustomerID.
- *Example 2*: List all orders placed by a specific customer, sorted by date.
- *Example 3*: Retrieve a specific order by OrderID, including the customer's name and contact info.

#### Step 2: Choose Partition Keys and Sort Keys
Identify the attributes used to query each record.
- If you only query an entity by a single identifier (e.g., `CustomerID`), use a **Simple Primary Key** with `customer_id` as the partition key.
- If you need to retrieve a list of related items (e.g., all orders for a customer) or filter records by a range (e.g., orders by date), use a **Composite Primary Key**. Set the parent ID (e.g., `customer_id`) as the partition key, and the sorting attribute (e.g., `order_date`) as the sort key.

#### Step 3: Denormalize Relationships
To avoid running multiple database queries, duplicate or nest related data:
- **One-to-Many Relationships**: Nest child items (like order items) inside a single parent item using a DynamoDB JSON List or Map attribute.
- **Many-to-Many Relationships**: Duplicate attributes. For example, store the product name inside the order record to avoid querying a separate products table.

#### Step 4: Apply Single-Table Design
Combine your entities into a single table. Define generic key names: **`PK`** (Partition Key) and **`SK`** (Sort Key). Use prefixes to isolate different types of records (e.g., `USER#` or `ORDER#`).

---

### Schema Conversion Example
Let us convert a relational e-commerce schema into a DynamoDB single-table design.

#### The Relational Setup:
- Table `customers` (id, name, email)
- Table `orders` (id, customer_id, order_date, total)

#### The DynamoDB Single-Table Design:
We create a single table named `ecommerce-table` with generic keys `PK` and `SK`.

| PK Value | SK Value | Entity Type | Attributes |
|---|---|---|---|
| `CUST#101` | `METADATA` | Customer | `name`: Alice, `email`: alice@example.com |
| `CUST#101` | `ORD#5001` | Order | `order_date`: 2026-07-14, `total`: 150.00 |
| `CUST#102` | `METADATA` | Customer | `name`: Bob, `email`: bob@example.com |

#### Access Patterns Supported:
1. **Fetch Customer by ID**: Query `PK = CUST#101` and `SK = METADATA`.
2. **List all Orders for a Customer**: Query `PK = CUST#101` and `SK BEGINS_WITH ORD#`. This retrieves all of Alice's orders in a single partition read.

## Code Examples

### Querying the Single-Table Layout (AWS CLI)
This query retrieves a customer's profile details and all of their orders in a single database request. By searching `PK = CUST#101`, DynamoDB returns both the customer metadata record and their historical order records:

```bash
aws dynamodb query \
    --table-name "ecommerce-table" \
    --key-condition-expression "PK = :pk" \
    --expression-attribute-values '{
        ":pk": {"S": "CUST#101"}
    }'
```

Output:
```json
{
  "Items": [
    {
      "PK": { "S": "CUST#101" },
      "SK": { "S": "METADATA" },
      "name": { "S": "Alice" },
      "email": { "S": "alice@example.com" }
    },
    {
      "PK": { "S": "CUST#101" },
      "SK": { "S": "ORD#5001" },
      "order_date": { "S": "2026-07-14" },
      "total": { "N": "150.00" }
    }
  ],
  "Count": 2,
  "ScannedCount": 2
}
```

This single query retrieves the customer profile and all their orders, bypassing the SQL table joins required in relational setups.

## Summary
- Relational database designs focus on normalizing tables, while NoSQL models are **designed around application query patterns**.
- The conversion workflow starts by **defining access patterns first**, choosing primary keys, and denormalizing relationships.
- Represent relationships by **nesting documents** or using **single-table design**.
- Single-table designs use generic keys (**PK** and **SK**) with entity-specific value prefixes (e.g., `CUST#101` and `ORD#5001`) to store multiple entities in one table.

## Additional Resources
- [Amazon DynamoDB Relational-to-NoSQL Conversion Guide](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-modeling-nosql-compared-to-relational.html)
- [Single-Table Design on AWS DynamoDB - Best Practices](https://aws.amazon.com/blogs/database/single-table-vs-multi-table-design-in-amazon-dynamodb/)
- [NoSQL Database Modeling Tutorial - Baeldung](https://www.baeldung.com/cassandra-nosql-data-modeling)
