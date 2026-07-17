# Reading Data in DynamoDB: GetItem, Consistency Models, and Projections

## Learning Objectives
- Retrieve database items using the Amazon DynamoDB `GetItem` API.
- Differentiate between eventually consistent reads and strongly consistent reads.
- Optimize network bandwidth using the `ProjectionExpression` parameter.
- Execute batch retrieval operations using the `BatchGetItem` API.

## Why This Matters
Retrieving records from a database is a core task in software applications. In SQL, this is done using the `SELECT` statement, where you declare the specific columns to retrieve and filter rows using a `WHERE` clause. 

Amazon DynamoDB retrieves single items using the `GetItem` API. Unlike SQL databases, DynamoDB replicates data across multiple physical servers to guarantee high availability. This replication introduces a choice between consistency models: eventually consistent reads (cheaper and faster) and strongly consistent reads (more expensive but always up-to-date). Mastering these models, along with projection filters and batch retrieval operations, is essential for designing fast, reliable, and cost-effective cloud backend applications.

## The Concept

### The GetItem API
The `GetItem` API retrieves a single item from a DynamoDB table based on its unique primary key. You must specify the partition key (and sort key, if defined) in your request. If the primary key matches an item, DynamoDB returns the item's attributes; otherwise, it returns an empty response.

#### Read Capacity Units (RCUs)
Read operations are metered in **Read Capacity Units (RCUs)**. One RCU allows you to execute:
- One **strongly consistent read** per second for an item up to 4 KB.
- Two **eventually consistent reads** per second for items up to 4 KB.
If an item size exceeds 4 KB, the RCU consumption is calculated by rounding the item size up to the next 4 KB boundary and dividing by the consistency factor.

---

### Consistency Models: Eventually vs. Strongly Consistent
To protect data against server failures, DynamoDB replicates items across three physical storage nodes within an AWS region automatically. When a write occurs, the database commits the change to a leader node and replicates the write asynchronously to the other nodes. This architecture supports two read consistency options:

#### 1. Eventually Consistent Reads (Default)
- **Mechanics**: DynamoDB queries one of the three replication nodes at random. Because replication takes time, a read request executed immediately after a write might retrieve stale data from a node that has not yet processed the update.
- **Benefit**: Lowest latency and half the RCU cost (1 RCU per 8 KB read throughput).
- **Use Case**: Social media feeds, user profiles, product reviews, or any application where a slight delay in data propagation is acceptable.

#### 2. Strongly Consistent Reads
- **Mechanics**: DynamoDB queries a majority of the replication nodes (or the primary leader node) to ensure it returns the most recent write state.
- **Tradeoff**: Higher read latency and double the cost (1 RCU per 4 KB read throughput).
- **Use Case**: Financial balance lookups, product stock availability during checkout, or security token validation.

---

### ProjectionExpression: Filtering Payload Size
By default, the `GetItem` API returns every attribute stored inside the matching item. If an item contains a large JSON object but the application only needs two fields, transferring the entire payload wastes network bandwidth.

To optimize network performance, use a **`ProjectionExpression`**. This parameter specifies the subset of attributes you want to retrieve. The database filters the payload before sending it over the network. 
*Note on Cost*: Using `ProjectionExpression` reduces network usage, but it does not change the RCU billing cost. DynamoDB bills you based on the size of the *entire* item read from disk, not the filtered subset sent to the client.

---

### BatchGetItem API
To retrieve multiple items across one or more tables in a single request, use the **`BatchGetItem`** API.
- **Capacity**: A single `BatchGetItem` request can retrieve up to **100 items** or a maximum of **16 MB** of data.
- **Optimization**: It executes queries in parallel across partitions, reducing request overhead.

## Code Examples

Let us execute these read APIs using the AWS CLI and the Java SDK.

### Scenario: Table Configurations
Assume a table named `products` where `product_id` (string) is the partition key.

### Example 1: Basic GetItem (Eventually Consistent)
```bash
aws dynamodb get-item \
    --table-name products \
    --key '{"product_id": {"S": "PRD-102"}}'
```

### Example 2: Strongly Consistent GetItem with ProjectionExpression
This request retrieves only the `price` and `stock_count` attributes, using a strongly consistent read model:

```bash
aws dynamodb get-item \
    --table-name products \
    --key '{"product_id": {"S": "PRD-102"}}' \
    --consistent-read \
    --projection-expression "price, stock_count"
```

### Example 3: BatchGetItem CLI Request
This request retrieves details for multiple products in a single network request.

Create a request file named `batch-request.json`:
```json
{
  "RequestItems": {
    "products": {
      "Keys": [
        { "product_id": { "S": "PRD-102" } },
        { "product_id": { "S": "PRD-204" } }
      ],
      "ProjectionExpression": "product_name, price"
    }
  }
}
```

Execute the batch request:
```bash
aws dynamodb batch-get-item --request-items file://batch-request.json
```

### Example 4: Strongly Consistent Read in Java SDK v2
```java
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

public class DynamoDBReadService {
    public static Map<String, AttributeValue> getProductDetails(DynamoDbClient ddb, String productId) {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("product_id", AttributeValue.builder().s(productId).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName("products")
                .key(key)
                // Force strongly consistent read
                .consistentRead(true)
                .projectionExpression("product_name, price")
                .build();

        GetItemResponse response = ddb.getItem(request);
        if (response.hasItem()) {
            return response.item();
        }
        return null;
    }
}
```

## Summary
- The **GetItem** API retrieves a single item based on its unique primary key.
- **Eventually Consistent Reads** (default) are fast and cost-efficient but may return stale data; **Strongly Consistent Reads** guarantee accuracy at double the RCU cost.
- **ProjectionExpression** limits the attributes returned to the application, reducing network payload sizes.
- **BatchGetItem** retrieves up to 100 items or 16 MB of data in a single parallel operation.

## Additional Resources
- [Amazon DynamoDB GetItem API Reference Documentation](https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_GetItem.html)
- [DynamoDB Read Consistency Models Explained](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.ReadConsistency.html)
- [BatchGetItem API Specifications](https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_BatchGetItem.html)
