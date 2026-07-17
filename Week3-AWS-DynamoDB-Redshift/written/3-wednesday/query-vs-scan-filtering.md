# Query vs. Scan: Search Performance and Cost Optimization

## Learning Objectives
- Compare the operations and performance characteristics of DynamoDB `Query` and `Scan` APIs.
- Configure `KeyConditionExpression` parameters to query items using partition keys.
- Apply post-retrieval filters using the `FilterExpression` parameter.
- Calculate Read Capacity Unit (RCU) consumption for both Query and Scan operations.
- Formulate database lookup strategies that minimize slow, expensive full-table scans.

## Why This Matters
In a relational database, running search queries is simple. You write a `SELECT` statement with a `WHERE` clause, and the database engine decides whether to use an index or perform a full-table scan. Both query types are executed using the same interface.

In Amazon DynamoDB, searching data requires making an explicit choice between two APIs: **Query** and **Scan**. Choosing the incorrect API directly impacts your application's performance and monthly cloud bill. Using `Scan` instead of `Query` on a large table can consume all your read capacity, slow down search responses from milliseconds to seconds, and cause service outages. To build fast, cost-effective NoSQL backends, you must understand how these two operations work and design your search logic to avoid full-table scans.

## The Concept

### The Scan API (Expensive and Slow)
The `Scan` API examines **every single item** in a DynamoDB table. It reads the entire dataset from disk, page by page, and evaluates each record against your search criteria.

#### Characteristics of Scan:
- **Performance**: High latency for large tables. As the dataset grows, the search time increases linearly ($O(n)$).
- **Billing Cost**: Highly expensive. You are billed for the Read Capacity Units (RCUs) required to read **every item in the table**, regardless of how many records match your search criteria. 
- **Limits**: A single `Scan` request can only return a maximum of **1 MB** of data. If your table is larger than 1 MB, the database returns a partial result set and a `LastEvaluatedKey`, requiring your application to run multiple paginated requests to scan the entire table.

---

### The Query API (Fast and Targeted)
The `Query` API retrieves a subset of items that share a **single partition key value**. It targets the specific physical storage partition where the data is stored, ignoring the rest of the table.

#### Characteristics of Query:
- **Performance**: Low latency (single-digit milliseconds) regardless of table size. The search time remains constant ($O(1)$ lookup to find the partition).
- **KeyConditionExpression**: You must specify the exact partition key value in a `KeyConditionExpression` (e.g., `PK = :userId`). You can also include sort key comparisons (e.g., `SK BEGINS_WITH :prefix` or `SK > :date`) to filter the results within that partition.
- **Billing Cost**: Highly cost-effective. You are only billed for the RCU capacity required to read the items that match your partition key filter.

---

### Filtering Results: KeyConditionExpression vs. FilterExpression
When running queries, you can use two different expressions to filter your results:

1. **KeyConditionExpression (Fast and Cheap)**:
   - Evaluates the partition key and sort key values directly.
   - Evaluates *before* data is read from disk.
   - Reduces both latency and RCU billing costs.
2. **FilterExpression (Evaluated Post-Retrieval)**:
   - Evaluates non-key attributes (e.g., searching for users where `status = 'ACTIVE'`).
   - Evaluates *after* data has been read from disk.
   - **Important**: Using a `FilterExpression` reduces the size of the payload sent over the network, but **it does not reduce the RCU billing cost**. You are still charged for reading the entire partition (for Query) or the entire table (for Scan) before the filter is applied.

```
                  +--------------------------------+
                  |         Query Request          |
                  +--------------------------------+
                                  |
                                  v
                  +--------------------------------+
                  |    KeyConditionExpression      |
                  | (Locates partition and reads   |
                  | matching keys from disk - RCU  |
                  | billed here)                   |
                  +--------------------------------+
                                  |
                                  v
                  +--------------------------------+
                  |       FilterExpression         |
                  | (Filters non-key attributes    |
                  | from the retrieved dataset in  |
                  | memory - no effect on RCU bill)|
                  +--------------------------------+
                                  |
                                  v
                  +--------------------------------+
                  |  Return payload to application |
                  +--------------------------------+
```

### When to Avoid Scan
As a general rule, **avoid using the `Scan` API in operational application paths**. A scan is acceptable during occasional administration tasks (like running an export or data migration) or on small lookup tables (under 100 KB) where the cost is negligible. For user-facing search flows, you must use the `Query` API. If you need to query attributes other than your primary key, you should create a **Global Secondary Index (GSI)** instead of running a scan.

## Code Examples

### Scenario: Table Setup
Assume we have a table named `customer_orders` where `customer_id` (string) is the Partition Key, and `order_date` (string) is the Sort Key. The table also contains a non-key attribute `payment_status`.

### Example 1: Efficient Query using KeyConditionExpression (AWS CLI)
This query retrieves all orders for a specific customer (`customer_id = 'CUST-100'`) placed after a specific date. This query is fast, targets a single partition, and is cost-effective.

```bash
aws dynamodb query \
    --table-name customer_orders \
    --key-condition-expression "customer_id = :cid AND order_date >= :date" \
    --expression-attribute-values '{
        ":cid": {"S": "CUST-100"},
        ":date": {"S": "2026-07-01"}
    }'
```

### Example 2: Query with KeyCondition and FilterExpression (AWS CLI)
This request retrieves the customer's orders, but uses a `FilterExpression` to return only the orders that are marked as 'FAILED'. 
*Note*: We are still billed for reading all of `CUST-100`'s orders, but only the failed ones are sent over the network.

```bash
aws dynamodb query \
    --table-name customer_orders \
    --key-condition-expression "customer_id = :cid" \
    --filter-expression "payment_status = :status" \
    --expression-attribute-values '{
        ":cid": {"S": "CUST-100"},
        ":status": {"S": "FAILED"}
    }'
```

### Example 3: Running a Scan (Avoid in Production)
This request scans the entire table to locate failed payments across all customers. This command is slow and expensive for large tables.

```bash
aws dynamodb scan \
    --table-name customer_orders \
    --filter-expression "payment_status = :status" \
    --expression-attribute-values '{
        ":status": {"S": "FAILED"}
    }'
```

### Example 4: Query Execution in Java SDK v2
```java
import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

public class DynamoDBSearchService {
    public static void queryCustomerOrders(DynamoDbClient ddb, String customerId, String minDate) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":cid", AttributeValue.builder().s(customerId).build());
        expressionValues.put(":date", AttributeValue.builder().s(minDate).build());

        QueryRequest request = QueryRequest.builder()
                .tableName("customer_orders")
                .keyConditionExpression("customer_id = :cid AND order_date >= :date")
                .expressionAttributeValues(expressionValues)
                .build();

        QueryResponse response = ddb.query(request);
        if (response.hasItems()) {
            response.items().forEach(item -> {
                System.out.println("Order ID: " + item.get("order_id").s() + 
                                   ", Date: " + item.get("order_date").s());
            });
            System.out.println("Consumed Capacity: " + response.consumedCapacity());
        }
    }
}
```

## Summary
- The **Query** API searches data in a single partition using the partition key; the **Scan** API reads every item in the entire table.
- **Query** executes in constant time ($O(1)$ partition lookup), while **Scan** latency grows linearly with the table size ($O(n)$).
- **KeyConditionExpression** filters data *before* it is read from disk, reducing RCU billing costs.
- **FilterExpression** filters data *after* it is read from disk. It reduces network payload sizes but does not reduce RCU billing costs.
- Avoid using the `Scan` API in operational application paths; use the `Query` API and index attributes using GSIs instead.

## Additional Resources
- [Amazon DynamoDB Query API Specification Reference](https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_Query.html)
- [Amazon DynamoDB Scan API Specification Reference](https://docs.aws.amazon.com/amazondynamodb/latest/APIReference/API_Scan.html)
- [Best Practices for Querying and Scanning Data in DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/bp-query-scan.html)
