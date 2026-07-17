# Creating Data in DynamoDB: PutItem API and Conditional Writes

## Learning Objectives
- Execute database insert operations using the Amazon DynamoDB `PutItem` API.
- Describe the primary key validation requirements for writing new items.
- Write write validation rules utilizing DynamoDB `ConditionExpression` constraints.
- Implement create-item logic using the AWS CLI and language-specific SDKs.

## Why This Matters
In a relational database, inserting a row is done using the SQL `INSERT` statement. If you try to insert a row with an existing primary key, the database throws a duplicate key error and aborts the insert. 

In Amazon DynamoDB, the default write operation is `PutItem`. Unlike SQL, `PutItem` has an overwrite-by-default behavior: if you write an item with an existing primary key, DynamoDB overwrites the entire existing item without warning. To prevent accidental data loss and build secure APIs (such as user registration or order intake), developers must use `ConditionExpression` constraints. This ensures writes only succeed if specific validation criteria are met.

## The Concept

### The PutItem API
The `PutItem` API writes a single item to a DynamoDB table. If the primary key does not exist, the API creates a new item. If the primary key already exists, `PutItem` replaces the entire item with the new payload.

#### Key Characteristics:
- **Partition Key Requirement**: The write payload must contain the exact partition key attribute (and sort key attribute, if defined for the table) configured for the table.
- **Payload Structure**: The write payload must be formatted in DynamoDB JSON, defining the data type of each attribute explicitly.
- **Consumption Cost**: A `PutItem` operation consumes **Write Capacity Units (WCUs)** based on the size of the item. One WCU allows you to write up to 1 KB of data per second. Items are rounded up to the nearest 1 KB (e.g., writing a 1.2 KB item consumes 2 WCUs).

---

### Conditional Writes with ConditionExpression
To override the default overwrite behavior, you can include a **`ConditionExpression`** parameter with your write request. A conditional write will only succeed if the specified expression evaluates to `true`. If the expression evaluates to `false`, DynamoDB rejects the write and throws a `ConditionalCheckFailedException` error.

#### Common Condition Expressions:
- **`attribute_not_exists(attribute_name)`**: Confirms that a specific attribute does not exist in the table. Applying this to the partition key (e.g., `attribute_not_exists(user_id)`) prevents overwriting existing records, functioning like an RDBMS unique constraint.
- **`attribute_exists(attribute_name)`**: Confirms that an attribute already exists before writing.
- **Logical Comparisons**: Using operators like `=`, `<>`, `<`, `>`, or `BETWEEN` to validate attribute values (e.g., only updating an invoice status if the outstanding balance is greater than zero).

```
                  +-------------------------------+
                  |      PutItem Request          |
                  +-------------------------------+
                                  |
                                  v
                  +-------------------------------+
                  |  Does Primary Key Exist?      |
                  +-------------------------------+
                     /                         \
             (No)   /                           \ (Yes)
                   v                             v
        +---------------------+       +---------------------------+
        | Write Item to Table |       | Is Condition Expression   |
        |      (Success)      |       |      Provided?            |
        +---------------------+       +---------------------------+
                                         /                     \
                                 (No)   /                       \ (Yes)
                                       v                         v
                            +--------------------+    +-----------------------+
                            | Overwrite Existing |    |  Does Condition Pass? |
                            |       Item         |    +-----------------------+
                            +--------------------+       /                 \
                                                 (Yes)  /                   \ (No)
                                                       v                     v
                                            +--------------------+ +------------------+
                                            | Overwrite Existing | | Reject Write     |
                                            |       Item         | | (Throw Exception) |
                                            +--------------------+ +------------------+
```

## Code Examples

### Scenario: Setting up a Users Table
Assume we have a table named `users` where `username` is the partition key.

### Example 1: Overwrite-by-Default PutItem (AWS CLI)
This command writes a user item to the table. If a user with the username `alice_smith` already exists, this command overwrites their profile:

```bash
aws dynamodb put-item \
    --table-name users \
    --item '{
        "username": {"S": "alice_smith"},
        "email": {"S": "alice@example.com"},
        "status": {"S": "ACTIVE"}
    }'
```

### Example 2: Preventing Overwrites using ConditionExpression
To ensure we do not overwrite an existing user profile during registration, we check that the partition key `username` does not exist:

```bash
aws dynamodb put-item \
    --table-name users \
    --item '{
        "username": {"S": "alice_smith"},
        "email": {"S": "new_alice@example.com"},
        "status": {"S": "PENDING"}
    }' \
    --condition-expression "attribute_not_exists(username)"
```

*Expected Result*: If `alice_smith` already exists, the command returns:
`An error occurred (ConditionalCheckFailedException) when calling the PutItem operation: The conditional request failed.`

### Example 3: PutItem Logic in Java SDK v2
Below is a Java code snippet demonstrating how to implement a conditional write using the AWS SDK for Java v2:

```java
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class DynamoDBWriteService {
    public static void registerUser(DynamoDbClient ddb, String username, String email) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("username", AttributeValue.builder().s(username).build());
        item.put("email", AttributeValue.builder().s(email).build());
        item.put("status", AttributeValue.builder().s("ACTIVE").build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("users")
                .item(item)
                // Enforce unique usernames
                .conditionExpression("attribute_not_exists(username)")
                .build();

        try {
            ddb.putItem(request);
            System.out.println("User registered successfully.");
        } catch (DynamoDbException e) {
            System.err.println("Registration failed: " + e.getMessage());
            // Catch ConditionalCheckFailedException to handle duplicate users
        }
    }
}
```

## Summary
- The **PutItem** API writes a single item to a table, overwriting any existing item with the same primary key by default.
- Every `PutItem` payload must contain the **Partition Key** (and sort key, if defined) matching the table schema.
- **ConditionExpressions** enable conditional writes, allowing you to validate item states before saving changes.
- Use the expression **`attribute_not_exists(partition_key)`** to enforce unique constraints and prevent accidental data overwrites.

## Additional Resources
- [Amazon DynamoDB PutItem API Reference Documentation](https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_PutItem.html)
- [Working with Condition Expressions in DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.ConditionExpressions.html)
- [AWS SDK for Java v2 Developer Guide](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html)
