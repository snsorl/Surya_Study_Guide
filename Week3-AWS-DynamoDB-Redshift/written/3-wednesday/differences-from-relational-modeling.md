# Differences from Relational Modeling: Denormalization and Single-Table Design

## Learning Objectives
- Explain the key differences between relational schema design and NoSQL data modeling.
- Explain the architectural impact of having no foreign keys or join operations in NoSQL databases.
- Design denormalized document models to optimize data access.
- Describe the core concepts and benefits of the single-table design pattern in DynamoDB.

## Why This Matters
When database developers transition from SQL to NoSQL, their default instinct is to model NoSQL tables exactly like relational tables. They create separate tables for each entity (e.g., a users table, an orders table, a products table) and use IDs to link them. 

This relational approach in a NoSQL database results in poor performance. Because NoSQL databases (like DynamoDB) do not support join operations, fetching a single order with user and product details requires running multiple, sequential queries across the network. To build high-performance NoSQL systems, developers must unlearn relational modeling habits and master NoSQL design patterns, including denormalization and single-table design.

## The Concept

### The Core Modeling Constraints
Relational databases separate storage considerations from querying patterns. You normalize tables to reduce data duplication and run joins at runtime to compile the data.

NoSQL databases reverse this approach. In NoSQL, you **design your data model around your application's query patterns**. This design is guided by three constraints:

1. **No Joins**: There is no SQL `JOIN` command. To retrieve data from multiple entities in a single query, you must store the entities together.
2. **No Foreign Key Enforcement**: The database does not validate that a `customer_id` in an order record exists in a customer table. Relationships are logical, not physical.
3. **No Schema Enforcement**: The database only enforces the primary key. This allows you to store different types of records in the same table.

---

### Denormalization as a Design Strategy
Denormalization is the practice of duplicating or nesting data within a single record to optimize read performance. 

- **Relational (Normalized)**: To record an order, you save the `customer_id`. To display the order history, you join the `customers` table to get the customer's name. If the customer updates their name, it changes once in the database.
- **NoSQL (Denormalized)**: You save the customer's name directly inside the order record. When displaying the order history, you read a single item with no joins.
- **Tradeoff**: Writes become more complex because updating a customer's name requires updating multiple order records. However, read performance is optimized. This is a beneficial tradeoff for read-heavy systems.

---

### The Single-Table Design Pattern
In relational databases, every entity type has its own table. In DynamoDB, a common design pattern is **Single-Table Design**, where all application entities (e.g., Users, Orders, Products, Logs) are stored in a **single DynamoDB table**.

#### How Single-Table Design Works:
- **Generic Key Names**: The table does not use specific primary key names like `user_id` or `order_id`. Instead, it uses generic names: **`PK`** (Partition Key) and **`SK`** (Sort Key).
- **Prefixing Keys**: Entity records are distinguished by adding prefixes (e.g., `USER#100` or `ORDER#5001`) to the key values.
- **Overloading**: The partition key (`PK`) and sort key (`SK`) hold different types of identifiers depending on the entity type.

```
       Generic Single Table Design Schema
+----------------+----------------+--------------------------+
| PK (Partition) | SK (Sort Key)  | Entity Attributes        |
+----------------+----------------+--------------------------+
| USER#100       | METADATA       | name: Alice, email: ...  |
| USER#100       | ORDER#5001     | total: 150.00, items: 3  |
| USER#100       | ORDER#5002     | total: 45.00, items: 1   |
| PRODUCT#A90    | DETAILS        | price: 49.99, stock: 50  |
+----------------+----------------+--------------------------+
```

*Querying Benefits*: To fetch a user profile AND all of their orders in a single database request, run a query with `PK = USER#100`. DynamoDB retrieves the user metadata row and all associated order rows in a single physical partition read.

## Code Examples

Let us compare the structure of a normalized relational schema with a denormalized DynamoDB document design.

### Relational normalized Schema (SQL)
```sql
CREATE TABLE customers (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT REFERENCES customers(id),
    order_date DATE
);

-- Retrieve order with customer details (Requires a Join)
SELECT o.id, c.name 
FROM orders o 
JOIN customers c ON o.customer_id = c.id 
WHERE o.id = 5001;
```

### NoSQL Denormalized Document Schema (DynamoDB JSON)
In DynamoDB, we store the customer metadata directly inside the order record to bypass the join:

```json
{
  "order_id": { "S": "ORDER#5001" },
  "order_date": { "S": "2026-07-14" },
  "total_amount": { "N": "150.00" },
  "customer_details": {
    "M": {
      "customer_id": { "S": "CUST#100" },
      "customer_name": { "S": "Alice Smith" }
    }
  }
}
```

To fetch this order with customer details, you run a single `GetItem` request using the key `ORDER#5001`. The database returns the nested customer details immediately without performing any table joins.

## Summary
- **NoSQL databases** do not support foreign key checks or table joins; schemas are enforced only on primary keys.
- **Denormalization** duplicates or nests data within records to optimize read queries, shifting complexity from reads to writes.
- **Single-Table Design** stores multiple entity types in a single DynamoDB table, using generic primary keys (`PK` and `SK`) with entity prefixes (e.g., `USER#` or `ORDER#`).
- Single-table design allows you to retrieve a parent entity and its associated child records in a single database query.

## Additional Resources
- [Amazon DynamoDB Best Practices for Designing Databases](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/best-practices.html)
- [The DynamoDB Book by Alex DeBrie - Guide to Single-Table Design](https://www.dynamodbbook.com/)
- [AWS reinvent Video: Advanced Design Patterns for DynamoDB](https://www.youtube.com/watch?v=HaEUXN4gJYk)
