# Deleting Data in DynamoDB: DeleteItem API and Soft Deletes

## Learning Objectives
- Delete database records using the Amazon DynamoDB `DeleteItem` API.
- Implement security validation rules using conditional delete parameters.
- Contrast hard delete operations with the soft delete architectural pattern.
- Implement soft delete state filters inside application query parameters.

## Why This Matters
Data deletion is a common administrative operation. In SQL, this is performed using the `DELETE FROM` statement. If a query is written incorrectly (such as missing a `WHERE` clause), it can delete all records in a table. 

Amazon DynamoDB deletes items using the `DeleteItem` API based on their unique primary key. While this key-based requirement protects against mass deletion errors, deleting records permanently (hard deleting) removes audit history and makes it difficult to restore data. In enterprise applications, developers often use the **Soft Delete** pattern instead of hard deletes. By using status attributes to flag items as inactive, developers can retain historical data for audit compliance while filtering deleted items out of active user views.

## The Concept

### The DeleteItem API
The `DeleteItem` API deletes a single item in a table by its primary key. 
- **Required Parameters**: You must specify the partition key (and sort key, if defined) of the target item.
- **WCU Consumption**: Deleting an item consumes Write Capacity Units (WCUs) based on the size of the item being deleted. If the item does not exist, the API call succeeds but consumes 1 write capacity unit to perform the check.

---

### Conditional Deletes
To prevent deleting the wrong items, you can include a **`ConditionExpression`** with the `DeleteItem` request. The item will only be deleted if the condition evaluates to `true`.

#### Common Use Cases:
- **Ownership Verification**: Only delete an order if the `customer_id` matches the user requesting the deletion (e.g., `customer_id = :user_id`).
- **State Validation**: Only delete a record if its status is marked as 'DRAFT' or 'PENDING', preventing the deletion of completed invoices.

---

### Hard Delete vs. Soft Delete

When designing database schemas, developers choose between two deletion strategies:

#### 1. Hard Delete (Physical Deletion)
Removes the record physically from the database storage medium.
- *Pros*: Reclaims storage space immediately, keeping storage costs low.
- *Cons*: Cannot be undone (requires restoring backups); removes historical logs and audit records, violating compliance standards.

#### 2. Soft Delete (Logical Deletion)
Keeps the record in the database but updates an attribute (e.g., `is_deleted = true` or `status = 'DELETED'`) to flag it as inactive.
- *Pros*: Easily reversible (by setting the flag back to false); preserves historical data and referential integrity for reporting.
- *Cons*: Consumes storage space, increasing monthly bills; requires developers to filter out deleted records in all read and scan queries.

```
                  +--------------------------------+
                  |     Request to Delete Item     |
                  +--------------------------------+
                                  |
                                  v
                  +--------------------------------+
                  |      Deletion Strategy?        |
                  +--------------------------------+
                     /                         \
           (Hard)   /                           \ (Soft)
                   v                             v
        +---------------------+       +---------------------------+
        |  Run DeleteItem API |       |  Run UpdateItem API       |
        |  (Physically remove |       |  (Set status = 'DELETED') |
        |  data from disk)    |       |  (Data remains on disk)   |
        +---------------------+       +---------------------------+
```

## Code Examples

### Scenario: Table Configurations
Assume a table named `customer_invoices` where `invoice_id` (string) is the partition key.

### Example 1: Basic DeleteItem (AWS CLI)
This request physically removes the invoice record from the table:

```bash
aws dynamodb delete-item \
    --table-name customer_invoices \
    --key '{"invoice_id": {"S": "INV-1002"}}'
```

### Example 2: Conditional Delete (AWS CLI)
To ensure we only delete an invoice if its status is marked as 'DRAFT':

```bash
aws dynamodb delete-item \
    --table-name customer_invoices \
    --key '{"invoice_id": {"S": "INV-1002"}}' \
    --condition-expression "payment_status = :status" \
    --expression-attribute-values '{
        ":status": {"S": "DRAFT"}
    }'
```

*Expected Result*: If the invoice status is 'PAID', the request is rejected with a `ConditionalCheckFailedException` error, protecting the billing record.

### Example 3: Implementing a Soft Delete
Instead of deleting the item physically, we use the `UpdateItem` API to flag it as deleted by setting a `status` attribute to 'DELETED' and adding a deletion timestamp:

```bash
aws dynamodb update-item \
    --table-name customer_invoices \
    --key '{"invoice_id": {"S": "INV-1002"}}' \
    --update-expression "SET invoice_status = :deleted, deleted_at = :time" \
    --expression-attribute-values '{
        ":deleted": {"S": "DELETED"},
        ":time": {"S": "2026-07-14T10:55:00Z"}
    }'
```

### Example 4: Soft Delete Check in Java SDK v2
Below is a Java code snippet demonstrating how to implement a conditional delete:

```java
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class DynamoDBDeleteService {
    public static void removeDraftInvoice(DynamoDbClient ddb, String invoiceId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("invoice_id", AttributeValue.builder().s(invoiceId).build());

        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":status", AttributeValue.builder().s("DRAFT").build());

        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName("customer_invoices")
                .key(key)
                // Only allow deletion if the invoice is still a draft
                .conditionExpression("payment_status = :status")
                .expressionAttributeValues(expressionValues)
                .build();

        try {
            ddb.deleteItem(request);
            System.out.println("Invoice deleted successfully.");
        } catch (DynamoDbException e) {
            System.err.println("Deletion failed: " + e.getMessage());
            // Catches ConditionalCheckFailedException if invoice is already processed
        }
    }
}
```

## Summary
- The **DeleteItem** API physically removes an item from a table using its unique primary key.
- **Conditional Deletes** protect against data loss by validating attributes (e.g., checking status levels) before execution.
- **Hard Deletes** physically erase data, freeing up storage space.
- **Soft Deletes** use the `UpdateItem` API to flag items as inactive (e.g., setting a status attribute). This preserves historical data for audits and allows you to restore records easily.

## Additional Resources
- [Amazon DynamoDB DeleteItem API Reference Guide](https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_DeleteItem.html)
- [Design Patterns for Soft Deletes in NoSQL Databases](https://aws.amazon.com/blogs/database/effective-data-archival-and-deletion-strategies-for-amazon-dynamodb/)
- [DynamoDB Conditional Deletes Best Practices](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithItems.html#WorkingWithItems.ConditionalUpdate)
