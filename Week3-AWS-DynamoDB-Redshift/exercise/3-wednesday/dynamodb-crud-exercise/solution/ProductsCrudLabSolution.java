// ============================================================================
// SOLUTION: TechMart Products Service
// Lab: DynamoDB CRUD and Query
// FOR INSTRUCTOR USE ONLY — Do not distribute to trainees.
// ============================================================================

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsCrudLabSolution {

    private static final DynamoDbClient ddb = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI.create("http://localhost:8000")) // Remove for AWS Cloud
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();

    private static final String TABLE_NAME = "techmart-products";

    // =========================================================================
    // Task 1: Create the Table
    // =========================================================================
    public static void createProductTable() {
        System.out.println("[CREATE TABLE] Creating " + TABLE_NAME + "...");
        try {
            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName(TABLE_NAME)
                    .attributeDefinitions(
                            AttributeDefinition.builder().attributeName("category").attributeType(ScalarAttributeType.S).build(),
                            AttributeDefinition.builder().attributeName("sku").attributeType(ScalarAttributeType.S).build()
                    )
                    .keySchema(
                            KeySchemaElement.builder().attributeName("category").keyType(KeyType.HASH).build(),
                            KeySchemaElement.builder().attributeName("sku").keyType(KeyType.RANGE).build()
                    )
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build();

            ddb.createTable(request);

            // Poll until ACTIVE
            boolean active = false;
            while (!active) {
                DescribeTableResponse desc = ddb.describeTable(DescribeTableRequest.builder().tableName(TABLE_NAME).build());
                active = desc.table().tableStatus() == TableStatus.ACTIVE;
                if (!active) Thread.sleep(500);
            }
            System.out.println("[CREATE TABLE] Table is ACTIVE.");

        } catch (ResourceInUseException e) {
            System.out.println("[CREATE TABLE] Table already exists. Proceeding.");
        } catch (DynamoDbException | InterruptedException e) {
            System.err.println("[CREATE TABLE] Error: " + e.getMessage());
        }
    }

    // =========================================================================
    // Task 2: Insert a Product (PutItem with Condition)
    // =========================================================================
    public static void putProduct(String category, String sku, String productName,
                                  double price, String brand, boolean inStock, int unitsAvailable) {
        System.out.println("[PUT] Inserting " + sku + " into " + category + "...");
        try {
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("category",         AttributeValue.builder().s(category).build());
            item.put("sku",              AttributeValue.builder().s(sku).build());
            item.put("product_name",     AttributeValue.builder().s(productName).build());
            item.put("price",            AttributeValue.builder().n(String.valueOf(price)).build());
            item.put("brand",            AttributeValue.builder().s(brand).build());
            item.put("in_stock",         AttributeValue.builder().bool(inStock).build());
            item.put("units_available",  AttributeValue.builder().n(String.valueOf(unitsAvailable)).build());

            PutItemRequest request = PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(item)
                    .conditionExpression("attribute_not_exists(sku)")
                    .build();

            ddb.putItem(request);
            System.out.println("[PUT] " + sku + " inserted successfully.");

        } catch (DynamoDbException e) {
            if (e.getMessage().contains("ConditionalCheckFailed")) {
                System.out.println("[PUT ERROR] " + sku + " already exists in " + category + ". Write rejected.");
            } else {
                System.err.println("[PUT ERROR] " + e.getMessage());
            }
        }
    }

    // =========================================================================
    // Task 3: Read a Product (GetItem — Strongly Consistent)
    // =========================================================================
    public static void getProduct(String category, String sku) {
        System.out.println("[GET] Reading " + sku + " from " + category + "...");
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("category", AttributeValue.builder().s(category).build());
        key.put("sku",      AttributeValue.builder().s(sku).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .consistentRead(true)
                .build();

        GetItemResponse response = ddb.getItem(request);
        if (response.hasItem()) {
            Map<String, AttributeValue> item = response.item();
            String name  = item.get("product_name").s();
            String price = item.get("price").n();
            boolean stock = item.get("in_stock").bool();
            System.out.println("[GET] " + sku + ": " + name + " | Price: " + price + " | In Stock: " + stock);
        } else {
            System.out.println("[GET] Product not found.");
        }
    }

    // =========================================================================
    // Task 4: Update Product Price (UpdateItem — SET)
    // =========================================================================
    public static void updateProductPrice(String category, String sku, double newPrice) {
        System.out.println("[UPDATE PRICE] Setting " + sku + " new price to " + newPrice + "...");
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("category", AttributeValue.builder().s(category).build());
        key.put("sku",      AttributeValue.builder().s(sku).build());

        Map<String, AttributeValue> exprValues = new HashMap<>();
        exprValues.put(":new_price", AttributeValue.builder().n(String.valueOf(newPrice)).build());

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .updateExpression("SET price = :new_price")
                .expressionAttributeValues(exprValues)
                .returnValues(ReturnValue.UPDATED_NEW)
                .build();

        UpdateItemResponse response = ddb.updateItem(request);
        String updated = response.attributes().get("price").n();
        System.out.println("[UPDATE PRICE] " + sku + " new price: " + updated);
    }

    // =========================================================================
    // Task 5: Decrement Stock (UpdateItem — Atomic Counter with Condition)
    // =========================================================================
    public static void decrementStock(String category, String sku, int quantitySold) {
        System.out.println("[DECREMENT STOCK] Reducing " + sku + " by " + quantitySold + " units...");
        try {
            Map<String, AttributeValue> key = new HashMap<>();
            key.put("category", AttributeValue.builder().s(category).build());
            key.put("sku",      AttributeValue.builder().s(sku).build());

            Map<String, AttributeValue> exprValues = new HashMap<>();
            exprValues.put(":qty", AttributeValue.builder().n(String.valueOf(quantitySold)).build());

            UpdateItemRequest request = UpdateItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(key)
                    .updateExpression("SET units_available = units_available - :qty")
                    .conditionExpression("units_available >= :qty")
                    .expressionAttributeValues(exprValues)
                    .returnValues(ReturnValue.UPDATED_NEW)
                    .build();

            UpdateItemResponse response = ddb.updateItem(request);
            String remaining = response.attributes().get("units_available").n();
            System.out.println("[DECREMENT STOCK] " + sku + " units remaining: " + remaining);

        } catch (DynamoDbException e) {
            if (e.getMessage().contains("ConditionalCheckFailed")) {
                System.out.println("[DECREMENT STOCK ERROR] Insufficient stock for " + sku + ". Write rejected.");
            } else {
                System.err.println("[DECREMENT STOCK ERROR] " + e.getMessage());
            }
        }
    }

    // =========================================================================
    // Task 6: Delete a Product (DeleteItem with Condition)
    // =========================================================================
    public static void deleteProduct(String category, String sku) {
        System.out.println("[DELETE] Deleting " + sku + " from " + category + "...");
        try {
            Map<String, AttributeValue> key = new HashMap<>();
            key.put("category", AttributeValue.builder().s(category).build());
            key.put("sku",      AttributeValue.builder().s(sku).build());

            DeleteItemRequest request = DeleteItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(key)
                    .conditionExpression("attribute_exists(sku)")
                    .build();

            ddb.deleteItem(request);
            System.out.println("[DELETE] " + sku + " deleted from " + category + ".");

        } catch (DynamoDbException e) {
            if (e.getMessage().contains("ConditionalCheckFailed")) {
                System.out.println("[DELETE ERROR] Item " + sku + " not found — nothing deleted.");
            } else {
                System.err.println("[DELETE ERROR] " + e.getMessage());
            }
        }
    }

    // =========================================================================
    // Task 7: Query All Products by Category (Query — No Scan!)
    // =========================================================================
    public static void queryProductsByCategory(String category) {
        System.out.println("[QUERY] Products in " + category + ":");
        Map<String, AttributeValue> exprValues = new HashMap<>();
        exprValues.put(":cat", AttributeValue.builder().s(category).build());

        QueryRequest request = QueryRequest.builder()
                .tableName(TABLE_NAME)
                .keyConditionExpression("category = :cat")
                .expressionAttributeValues(exprValues)
                .build();

        QueryResponse response = ddb.query(request);
        List<Map<String, AttributeValue>> items = response.items();
        for (Map<String, AttributeValue> item : items) {
            String sku   = item.get("sku").s();
            String name  = item.get("product_name").s();
            String price = item.get("price").n();
            System.out.println("  - " + sku + ": " + name + " | $" + price);
        }
        System.out.println("[QUERY] Total: " + items.size() + " items in " + category + ".");
    }

    // =========================================================================
    // Task 8: Query In-Stock Products by Category (Query + FilterExpression)
    // =========================================================================
    public static void queryInStockByCategory(String category) {
        System.out.println("[QUERY + FILTER] In-stock products in " + category + ":");
        Map<String, AttributeValue> exprValues = new HashMap<>();
        exprValues.put(":cat",  AttributeValue.builder().s(category).build());
        exprValues.put(":true", AttributeValue.builder().bool(true).build());

        QueryRequest request = QueryRequest.builder()
                .tableName(TABLE_NAME)
                .keyConditionExpression("category = :cat")
                .filterExpression("in_stock = :true")
                .expressionAttributeValues(exprValues)
                .build();

        // BILLING EXPLANATION:
        // DynamoDB reads ALL items in the partition (where category = :cat) from disk
        // and charges RCUs for every item scanned. FilterExpression is evaluated IN MEMORY
        // after the read is complete. We only receive in-stock items over the network,
        // but the RCU cost reflects the full partition read, not the filtered subset.

        QueryResponse response = ddb.query(request);
        List<Map<String, AttributeValue>> items = response.items();
        for (Map<String, AttributeValue> item : items) {
            String sku  = item.get("sku").s();
            String name = item.get("product_name").s();
            System.out.println("  - " + sku + ": " + name);
        }
        System.out.println("[QUERY + FILTER] " + items.size() + " in-stock items found in " + category + ".");
    }

    // =========================================================================
    // Main
    // =========================================================================
    public static void main(String[] args) {
        createProductTable();

        putProduct("LAPTOPS",  "SKU-LAP-001", "ProBook X15 Laptop",    1299.99, "TechMart",  true,  42);
        putProduct("LAPTOPS",  "SKU-LAP-002", "UltraSlim 13 Laptop",    849.00, "SwiftBook", true,  15);
        putProduct("LAPTOPS",  "SKU-LAP-003", "Budget Pro 11 Laptop",   399.99, "ValueTech", false,  0);
        putProduct("MONITORS", "SKU-MON-001", "ProDisplay 27\" 4K",     549.99, "TechMart",  true,  30);
        putProduct("MONITORS", "SKU-MON-002", "CurvedView 32\" QHD",    699.00, "VueMaster", true,   8);

        putProduct("LAPTOPS",  "SKU-LAP-001", "ProBook X15 Laptop", 1399.99, "TechMart", true, 42); // rejected

        getProduct("LAPTOPS", "SKU-LAP-001");
        getProduct("LAPTOPS", "SKU-LAP-999");

        updateProductPrice("LAPTOPS", "SKU-LAP-001", 1199.99);

        decrementStock("MONITORS", "SKU-MON-002", 3);
        decrementStock("MONITORS", "SKU-MON-002", 99); // rejected

        queryProductsByCategory("LAPTOPS");
        queryInStockByCategory("LAPTOPS");

        deleteProduct("LAPTOPS", "SKU-LAP-003");
        deleteProduct("LAPTOPS", "SKU-LAP-999"); // rejected
    }
}
