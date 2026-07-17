# Updating Data in DynamoDB: UpdateItem API and Expressions

## Learning Objectives
- Update database records using the Amazon DynamoDB `UpdateItem` API.
- Apply the four primary update operations in `UpdateExpression`: SET, REMOVE, ADD, and DELETE.
- Implement high-concurrency numeric modifications using atomic counters.
- Construct conditional updates using conditional write parameters.

## Why This Matters
In a relational database, updating data is performed using the SQL `UPDATE` statement. To modify a user's address or increment a count, you execute a query that overwrites the column values. However, if multiple transactions try to update the same record simultaneously, they can block each other or overwrite concurrent changes (dirty writes).

Amazon DynamoDB updates items using the `UpdateItem` API and **`UpdateExpression`** parameters. This allows you to update specific attributes of an item without reading the item first or overwriting other attributes. Furthermore, DynamoDB supports atomic counters, which allow you to increment or decrement numeric values concurrently without using transaction locks. Mastering update expressions is essential for building fast, high-concurrency APIs like inventory trackers or click counters.

## The Concept

### The UpdateItem API
The `UpdateItem` API modifies an existing item’s attributes or inserts a new item if the primary key is not found in the table. You must specify the primary key (`PK`/`SK`) of the target item and provide an **`UpdateExpression`** defining the modifications to perform.

#### Read-Modify-Write vs. In-Place Update
- **Read-Modify-Write (Avoid for simple updates)**: The application reads the item, modifies the attributes in code, and writes the entire item back to the database using `PutItem`. This model wastes network capacity and introduces race conditions (where concurrent updates overwrite each other).
- **In-Place Update (Best Practice)**: The application executes a single `UpdateItem` request, sending only the changes. The database applies the changes directly on disk, reducing payload sizes and concurrency issues.

---

### UpdateExpression Syntax
An `UpdateExpression` specifies how DynamoDB should modify an item's attributes. It supports four distinct operations:

#### 1. SET
Used to add new attributes or modify existing attribute values.
- *Syntax Example*: `SET price = :new_price, updated_at = :timestamp`
- *Nesting*: You can set values in nested document maps (e.g., `SET address.city = :new_city`).

#### 2. REMOVE
Used to delete specific attributes from an item.
- *Syntax Example*: `REMOVE temporary_notes, secondary_phone`

#### 3. ADD
Used to increment or decrement numeric attributes, or append elements to a set. (For numbers, `SET` is preferred, but `ADD` is useful for simple numeric updates).
- *Syntax Example*: `ADD login_attempts :val`

#### 4. DELETE
Used to remove one or more elements from a set.
- *Syntax Example*: `DELETE user_roles :role_to_remove`

---

### Atomic Counters
An atomic counter is a design pattern that uses the `UpdateItem` API to increment or decrement a numeric attribute without interfering with other write requests. All update requests are applied in the order they are received.

- **How It Works**: You execute an `UpdateItem` using an expression like `SET page_views = page_views + :inc`.
- **Tradeoff**: Atomic counters are fast and do not lock tables, but they are not idempotent. If a network timeout occurs and your application retries the request, the counter might increment twice. Do not use atomic counters for critical balances (like bank accounts) where exact counts are required.

## Code Examples

### Scenario: Table Setup
Assume we have a table named `inventory` where `item_id` (string) is the partition key.

### Example 1: Standard Update using SET and REMOVE (AWS CLI)
This request updates the `price` and deletes the `promo_code` attribute of a specific item:

```bash
aws dynamodb update-item \
    --table-name inventory \
    --key '{"item_id": {"S": "ITEM-99"}}' \
    --update-expression "SET price = :p, last_updated = :u REMOVE promo_code" \
    --expression-attribute-values '{
        ":p": {"N": "19.99"},
        ":u": {"S": "2026-07-14T10:50:00Z"}
    }'
```

Key options explained:
- `--update-expression`: Defines the update operations (`SET` and `REMOVE`).
- `--expression-attribute-values`: Defines the values for the placeholders (variables starting with `:`) used in the expression.

### Example 2: Implementing an Atomic Counter
To increment a stock level atomically:

```bash
aws dynamodb update-item \
    --table-name inventory \
    --key '{"item_id": {"S": "ITEM-99"}}' \
    --update-expression "SET stock_quantity = stock_quantity + :inc" \
    --expression-attribute-values '{
        ":inc": {"N": "5"}
    }'
```

Even if multiple users trigger this command simultaneously, DynamoDB processes each increment sequentially, ensuring the final stock count is correct without requiring database locks.

### Example 3: Conditional Update using Java SDK v2
We want to decrease the stock quantity by 1, but only if the current stock is greater than zero:

```java
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class DynamoDBUpdateService {
    public static void purchaseItem(DynamoDbClient ddb, String itemId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("item_id", AttributeValue.builder().s(itemId).build());

        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":dec", AttributeValue.builder().n("1").build());
        expressionValues.put(":limit", AttributeValue.builder().n("0").build());

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName("inventory")
                .key(key)
                .updateExpression("SET stock_quantity = stock_quantity - :dec")
                .conditionExpression("stock_quantity > :limit")
                .expressionAttributeValues(expressionValues)
                .build();

        try {
            ddb.updateItem(request);
            System.out.println("Item purchased successfully.");
        } catch (DynamoDbException e) {
            System.err.println("Purchase failed: " + e.getMessage());
            // Catches ConditionalCheckFailedException if stock_quantity <= 0
        }
    }
}
```

## Summary
- The **UpdateItem** API modifies attributes in place without reading the item first.
- **UpdateExpression** supports four operations: **SET** (write/modify), **REMOVE** (delete attribute), **ADD** (increment numbers/sets), and **DELETE** (remove from set).
- **Atomic Counters** allow high-concurrency numeric updates, but they are not idempotent.
- Combined with **ConditionExpressions**, update operations can validate business rules (e.g., checking stock limits) before modifying database state.

## Additional Resources
- [Amazon DynamoDB UpdateItem API Specification](https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_UpdateItem.html)
- [Working with Update Expressions in DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.UpdateExpressions.html)
- [DynamoDB Concurrency Control and Atomic Counters Guide](https://aws.amazon.com/blogs/database/prevent-race-conditions-using-dynamodb-conditional-writes/)
