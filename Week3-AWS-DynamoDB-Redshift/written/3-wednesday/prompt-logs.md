# AI Prompt Log: DynamoDB Schema Design and Evaluation

## Learning Objectives
- Document AI assistant prompts used to design NoSQL DynamoDB schemas.
- Evaluate AI-generated designs against performance, cost, and operational constraints.
- Iterate on AI prompts to refine partition key strategies and single-table designs.
- Establish best practices for documenting prompt histories in team settings.

## Why This Matters
Using AI assistants to design databases is an iterative process. If you provide a generic prompt, the AI might suggest an incorrect relational structure or a design that leads to hot partition bottlenecks. To get optimal designs, you must evaluate the AI's suggestions and refine your prompts based on specific access patterns.

This prompt log documents the design process for a DynamoDB single-table schema. It records the initial prompts, the AI's suggestions, the evaluation of those suggestions, and the final optimized prompts. This log provides a framework that team members can use to write effective prompts and document design decisions.

## The Concept

### The Iterative Prompting Workflow
Designing a DynamoDB database with an AI assistant follows a three-step cycle:

1. **Initial Prompt (The Draft)**: Provide basic requirements and query goals to generate a draft schema.
2. **Design Evaluation**: Analyze the AI's output for issues like table index configurations, partition bottlenecks, and missing access patterns.
3. **Refined Prompt (The Optimization)**: Adjust the prompt with specific constraints (e.g., "enforce single-table design" or "avoid hot partition keys") to get a production-ready model.

---

### Key Evaluation Criteria
When reviewing AI-generated DynamoDB designs, check for the following issues:
- **Hot Partitions**: Ensure the partition key is not set to a low-cardinality attribute (like `status` or `gender`). This concentrates all writes onto a single physical server partition, causing performance issues.
- **Relational Bleed**: Verify that the AI has not split every entity into a separate table, causing the application to execute slow, expensive query joins in code.
- **NoSQL Constraints**: Check that item sizes do not exceed DynamoDB's 400 KB limit.

## Code Examples

Here is the log documenting the prompt iterations and schema designs for a customer order tracking database.

### Iteration 1: The Initial Request and Draft Design

#### Prompt:
```text
Design a DynamoDB database for an order tracking system. 
We have customers and orders. Each customer has a name and email. 
Each order has an order ID, customer ID, order date, and total amount.
```

#### AI Suggestion:
The AI recommended creating two separate tables:
1. Table `customers` with Partition Key `customer_id`.
2. Table `orders` with Partition Key `order_id` and an attribute `customer_id`.

#### Evaluation:
- *Problem*: This design uses a relational multi-table approach. To display a list of all orders for a customer with the customer's name, the application must run a query on `customers` and then run a `Scan` or a query on `orders`, joining the data in code. This is inefficient.

---

### Iteration 2: Refinement for Single-Table Design

#### Prompt (Optimized):
```text
Redesign the order tracking system database using a Single-Table Design pattern in DynamoDB.
The table must store both Customer and Order entities.
Generic Primary Keys must be used:
- Partition Key: PK
- Sort Key: SK

Access Patterns to Support:
1. Retrieve customer metadata by CustomerID.
2. List all orders for a specific customer, sorted by order date (most recent first).
3. Retrieve a specific order by OrderID.

Explain the key structures and show sample items in DynamoDB JSON.
```

#### AI Suggestion:
The AI recommended a single table named `order-tracking-table` with PK and SK.

Sample items generated:

```json
[
  {
    "PK": { "S": "CUST#101" },
    "SK": { "S": "METADATA" },
    "name": { "S": "Alice" },
    "email": { "S": "alice@example.com" }
  },
  {
    "PK": { "S": "CUST#101" },
    "SK": { "S": "ORD#2026-07-14#5001" },
    "total": { "N": "150.00" }
  }
]
```

#### Evaluation:
- **Access Pattern 1 (Get Customer)**: Supported. Query `PK = CUST#101` and `SK = METADATA`.
- **Access Pattern 2 (List Orders by Date)**: Supported. Query `PK = CUST#101` and `SK BEGINS_WITH ORD#`. Because the SK value is prefixed with the date (e.g., `ORD#2026-07-14#`), DynamoDB sorts the records chronologically automatically.
- **Access Pattern 3 (Get Order by ID)**: Problematic. We cannot locate an order directly using its `order_id` (e.g., `5001`) because the partition key is `CUST#101`. If the application does not know which customer placed the order, it cannot retrieve it without scanning the entire table.

---

### Iteration 3: Adding Indexes for Secondary Queries

#### Prompt (Final):
```text
The Single-Table Design works for customer-centric queries, but we also need to search orders by OrderID directly (Access Pattern 3) without knowing the CustomerID.
Suggest how to modify the table using a Global Secondary Index (GSI) to support this query.
Provide the AWS CLI command to create the GSI.
```

#### AI Suggestion:
Create a Global Secondary Index (GSI) named `GSI1` where the partition key is the order identifier.

AWS CLI Command to add the GSI:
```bash
aws dynamodb update-table \
    --table-name "order-tracking-table" \
    --attribute-definitions AttributeName=SK,AttributeType=S \
    --global-secondary-index-updates '[
        {
            "Create": {
                "IndexName": "GSI1",
                "KeySchema": [
                    {"AttributeName": "SK", "KeyType": "HASH"}
                ],
                "Projection": {
                    "ProjectionType": "ALL"
                }
            }
        }
    ]'
```

#### Evaluation:
- *Result*: Supported. Since every order item uses its `order_id` prefixed as the `SK` (e.g., `ORD#5001`), the GSI index allows us to search by `SK` directly. This enables fast, index-based lookups by OrderID without needing to know the CustomerID, satisfying all access patterns.

## Summary
- Designing NoSQL databases with AI is an **iterative process** of refining prompts based on query patterns.
- Initial AI suggestions often fallback to **relational multi-table models**; you must explicitly request single-table configurations.
- Evaluate AI outputs against **hot partitions**, **size limits**, and **specific access patterns**.
- Use **Global Secondary Indexes (GSIs)** to support secondary search keys (e.g., looking up orders by OrderID directly).

## Additional Resources
- [AWS Guide to Best Practices for Global Secondary Indexes](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GSI.html)
- [How to Design DynamoDB Tables with AI Assistants - blog post](https://aws.amazon.com/blogs/developer/using-generative-ai-to-design-amazon-dynamodb-schemas/)
- [DynamoDB Single-Table Design Deep Dive - Alex DeBrie](https://www.dynamodbbook.com/)
