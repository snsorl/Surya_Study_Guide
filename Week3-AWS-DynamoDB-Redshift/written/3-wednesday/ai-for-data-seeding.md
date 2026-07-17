# AI-Assisted Data Seeding for DynamoDB

## Learning Objectives
- Generate realistic, structured seed datasets for database testing using AI assistants.
- Format seed data as valid DynamoDB JSON payloads.
- Formulate validation rules to check generated data structure against primary key and constraint rules.
- Execute batch write operations using the AWS CLI and language-specific SDKs.

## Why This Matters
When developing database applications, testing with empty tables is insufficient. To verify query performance, index utilization, and pagination logic, you need realistic test datasets that match production data volume and complexity. Generating thousands of test records manually is slow, and using simple mock scripts often produces repetitive data that does not test real-world edge cases (such as long text strings, null fields, or invalid emails).

AI assistants can generate large, realistic datasets based on your schema. However, because DynamoDB requires a specific data type formatting (DynamoDB JSON), generic AI data generators often produce standard JSON that fails API validation checks. This guide explains how to prompt AI assistants to generate correct DynamoDB JSON seed data, validate the output, and write the data using batch APIs.

## The Concept

### The Data Seeding Process
The workflow for seeding DynamoDB tables using AI assistance consists of four steps:

```
+---------------------------------------------------------+
|                  Generate Schema Context                |
|  (Describe table, primary keys, and data types)        |
+---------------------------------------------------------+
                             |
                             v
+---------------------------------------------------------+
|                  AI Seed Data Prompt                    |
|  (Request DynamoDB JSON format, specify row count)     |
+---------------------------------------------------------+
                             |
                             v
+---------------------------------------------------------+
|                 Verification Check                      |
|  - Check primary key presence and uniqueness            |
|  - Confirm DynamoDB JSON type wrapper syntax            |
+---------------------------------------------------------+
                             |
                             v
+---------------------------------------------------------+
|                Batch Write Execution                    |
|  - Execute BatchWriteItem API to insert records         |
+---------------------------------------------------------+
```

---

### Writing Effective Seeding Prompts
To get working seed data from an AI assistant, your prompt must include:
1. **Target Table Schema**: Define the table name and the primary key attributes.
2. **Explicit Format Instruction**: Request the output in **DynamoDB JSON** format (with S, N, BOOL, and Map tags) rather than standard JSON.
3. **Data Quality Criteria**: List business validation rules (e.g., "generate realistic names, emails matching `@example.com`, and numeric product prices between $10 and $500").
4. **Batch Compatibility**: Request that the items be grouped inside a `BatchWriteItem` structure to simplify execution.

---

### Data Validation Checklist
Before executing bulk database writes, verify that the AI's output meets the following criteria:
- **Primary Key Integrity**: Confirm that every record contains the exact partition and sort key attributes defined for the table.
- **Uniqueness**: Ensure there are no duplicate primary key values within the seed file. Duplicate keys in a batch write request will cause the database to reject the write or overwrite records.
- **Syntactic Correctness**: Verify type declarations (e.g., numeric values must be sent as string representations inside the number tag: `{"price": {"N": "49.99"}}`).
- **Batch Limits**: The `BatchWriteItem` API can process a maximum of **25 write requests** per call. If you have more than 25 seed records, they must be split into separate batch requests.

## Code Examples

### Scenario: Table Configuration
Assume we want to seed a table named `store-products` where `sku` (string) is the partition key.

### Example 1: The AI Seeding Prompt
```text
Role: Database Test Engineer
Task: Generate DynamoDB JSON seed data for testing.
Table Name: store-products
Primary Key: sku (String)

Generate 3 test product items. Ensure the output is formatted as a valid BatchWriteItem request.
Include the following attributes:
- sku: Unique identifier (e.g., PROD-01)
- product_name: String
- price: Number (between 10.00 and 150.00)
- in_stock: Boolean

Format output as raw JSON ready for execution via the AWS CLI.
```

### Example 2: Validating and Executing the Seed Data
The AI assistant generates the following output. We save this payload as `seed-data.json`:

```json
{
  "RequestItems": {
    "store-products": [
      {
        "PutRequest": {
          "Item": {
            "sku": { "S": "PROD-101" },
            "product_name": { "S": "Ergonomic Office Chair" },
            "price": { "N": "129.99" },
            "in_stock": { "BOOL": true }
          }
        }
      },
      {
        "PutRequest": {
          "Item": {
            "sku": { "S": "PROD-102" },
            "product_name": { "S": "USB-C Hub Adapter" },
            "price": { "N": "34.50" },
            "in_stock": { "BOOL": true }
          }
        }
      },
      {
        "PutRequest": {
          "Item": {
            "sku": { "S": "PROD-103" },
            "product_name": { "S": "Bluetooth Keyboard" },
            "price": { "N": "59.99" },
            "in_stock": { "BOOL": false }
          }
        }
      }
    ]
  }
}
```

#### Validation Review:
- *Check 1*: Table name matches `store-products`. (Pass)
- *Check 2*: Each item contains the partition key `sku`. (Pass)
- *Check 3*: Every `sku` value is unique (`PROD-101`, `PROD-102`, `PROD-103`). (Pass)
- *Check 4*: Numbers are sent as string literals inside the `"N"` wrapper (e.g., `"129.99"`). (Pass)
- *Check 5*: Total items do not exceed the 25-record batch write limit. (Pass)

#### Execution via CLI:
Once validated, write the seed data to the table using the `batch-write-item` command:

```bash
aws dynamodb batch-write-item --request-items file://seed-data.json
```

Output:
```json
{
    "UnprocessedItems": {}
}
```

An empty `UnprocessedItems` block confirms that all items were successfully written to the database.

## Summary
- AI assistants can generate realistic test datasets when provided with clear **schema context** and **validation rules**.
- Request seed data in **DynamoDB JSON** format to ensure compatibility with AWS APIs.
- Validate that all seed records contain the correct **primary key** attributes and that these values are unique.
- The **BatchWriteItem** API can write up to 25 items in a single request. Split larger seed datasets into multiple batch files.

## Additional Resources
- [Amazon DynamoDB BatchWriteItem API Reference Guide](https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_BatchWriteItem.html)
- [Mock Data Generation Techniques for NoSQL Databases](https://aws.amazon.com/blogs/database/automating-test-data-generation-for-amazon-dynamodb/)
- [AWS CLI Command Reference - BatchWriteItem](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/dynamodb/batch-write-item.html)
