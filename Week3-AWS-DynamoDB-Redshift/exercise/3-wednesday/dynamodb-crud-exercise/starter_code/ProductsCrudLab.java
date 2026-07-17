// ============================================================================
// Starter Code: TechMart Products Service
// Lab: DynamoDB CRUD and Query
//
// Instructions:
//   - Implement every method marked with a // TODO comment.
//   - Do NOT modify the method signatures.
//   - Build: mvn compile  |  Run: mvn exec:java -Dexec.mainClass="ProductsCrudLab"
//
// Dependencies (pom.xml):
//   software.amazon.awssdk : dynamodb : 2.25.x
//   software.amazon.awssdk : url-connection-client : 2.25.x
// ============================================================================

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsCrudLab {

    // --- Configuration ---
    // To use DynamoDB Local (offline), keep the endpointOverride.
    // To use AWS Cloud, remove the .endpointOverride() call.
    private static final DynamoDbClient ddb = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI.create("http://localhost:8000")) // Remove for AWS Cloud
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();

    private static final String TABLE_NAME = "techmart-products";

    // =========================================================================
    // Task 1: Create the Table
    // =========================================================================
    /**
     * Creates the 'techmart-products' DynamoDB table.
     * Partition Key : "category" (String)
     * Sort Key      : "sku"      (String)
     * Billing Mode  : PAY_PER_REQUEST
     * Waits until the table status is ACTIVE before returning.
     */
    public static void createProductTable() {
        System.out.println("[CREATE TABLE] Creating " + TABLE_NAME + "...");
        // TODO: Implement table creation.
        // 1. Build a CreateTableRequest with:
        //    - AttributeDefinitions for "category" (S) and "sku" (S)
        //    - KeySchema: category = HASH, sku = RANGE
        //    - BillingMode = PAY_PER_REQUEST
        // 2. Call ddb.createTable(request).
        // 3. Handle ResourceInUseException (table already exists).
        // 4. Use a DescribeTable polling loop OR ddb.waiter() to wait until
        //    the table status is ACTIVE before printing "[CREATE TABLE] Table is ACTIVE."
    }

    // =========================================================================
    // Task 2: Insert a Product (PutItem with Condition)
    // =========================================================================
    /**
     * Inserts a new product item using PutItem.
     * Must NOT overwrite an existing item with the same (category, sku).
     * Prints a confirmation message on success.
     * Prints an error message if the condition check fails.
     */
    public static void putProduct(String category, String sku, String productName,
                                  double price, String brand, boolean inStock, int unitsAvailable) {
        System.out.println("[PUT] Inserting " + sku + " into " + category + "...");
        // TODO: Implement PutItem.
        // 1. Build the item Map<String, AttributeValue> with all attributes.
        //    Remember: Numbers must use AttributeValue.builder().n(String.valueOf(price)).build()
        //    Booleans use: AttributeValue.builder().bool(inStock).build()
        // 2. Build a PutItemRequest with conditionExpression("attribute_not_exists(sku)")
        // 3. Call ddb.putItem(request).
        // 4. Catch DynamoDbException and check for ConditionalCheckFailedException message
        //    to print a specific rejection message.
    }

    // =========================================================================
    // Task 3: Read a Product (GetItem — Strongly Consistent)
    // =========================================================================
    /**
     * Retrieves a single product item using GetItem with strongly consistent reads.
     * Prints product details if found; prints "Product not found." otherwise.
     */
    public static void getProduct(String category, String sku) {
        System.out.println("[GET] Reading " + sku + " from " + category + "...");
        // TODO: Implement GetItem.
        // 1. Build the key Map<String, AttributeValue> with "category" and "sku".
        // 2. Build a GetItemRequest with consistentRead(true).
        // 3. Call ddb.getItem(request).
        // 4. Check response.hasItem():
        //    - If true, extract and print: product_name, price, in_stock
        //      e.g. "[GET] SKU-LAP-001: ProBook X15 Laptop | Price: 1299.99 | In Stock: true"
        //    - If false, print "Product not found."
    }

    // =========================================================================
    // Task 4: Update Product Price (UpdateItem — SET)
    // =========================================================================
    /**
     * Updates only the 'price' attribute of an existing product.
     * Returns the updated attribute values (ReturnValues = UPDATED_NEW).
     * Prints the new price on success.
     */
    public static void updateProductPrice(String category, String sku, double newPrice) {
        System.out.println("[UPDATE PRICE] Setting " + sku + " new price to " + newPrice + "...");
        // TODO: Implement UpdateItem.
        // 1. Build the key Map with "category" and "sku".
        // 2. Build an UpdateItemRequest with:
        //    - updateExpression("SET price = :new_price")
        //    - expressionAttributeValues Map containing ":new_price" as N(String)
        //    - returnValues(ReturnValue.UPDATED_NEW)
        // 3. Call ddb.updateItem(request) and print the new price from
        //    response.attributes().get("price").n()
    }

    // =========================================================================
    // Task 5: Decrement Stock (UpdateItem — Atomic Counter with Condition)
    // =========================================================================
    /**
     * Decrements 'units_available' by quantitySold atomically.
     * Uses a ConditionExpression to prevent units_available from going below 0.
     * Prints the remaining units on success.
     * Prints an error message if there is insufficient stock.
     */
    public static void decrementStock(String category, String sku, int quantitySold) {
        System.out.println("[DECREMENT STOCK] Reducing " + sku + " by " + quantitySold + " units...");
        // TODO: Implement UpdateItem with an atomic decrement.
        // 1. updateExpression("SET units_available = units_available - :qty")
        // 2. conditionExpression("units_available >= :qty")
        //    This prevents the counter from going negative.
        // 3. expressionAttributeValues: {":qty": N(quantitySold)}
        // 4. returnValues(ReturnValue.UPDATED_NEW)
        // 5. Catch DynamoDbException — if ConditionalCheckFailedException,
        //    print "[DECREMENT STOCK ERROR] Insufficient stock for {sku}."
    }

    // =========================================================================
    // Task 6: Delete a Product (DeleteItem with Condition)
    // =========================================================================
    /**
     * Deletes a product record using DeleteItem.
     * Uses a ConditionExpression to confirm the item exists before deleting.
     * Prints a confirmation on success; prints an error if the item is missing.
     */
    public static void deleteProduct(String category, String sku) {
        System.out.println("[DELETE] Deleting " + sku + " from " + category + "...");
        // TODO: Implement DeleteItem.
        // 1. Build the key Map with "category" and "sku".
        // 2. Add conditionExpression("attribute_exists(sku)") to prevent silent no-ops.
        // 3. Call ddb.deleteItem(request).
        // 4. On ConditionalCheckFailedException, print "Item not found - nothing deleted."
        // 5. On success, print "[DELETE] {sku} deleted from {category}."
    }

    // =========================================================================
    // Task 7: Query All Products by Category (Query — No Scan!)
    // =========================================================================
    /**
     * Retrieves ALL products in a given category using Query (NOT Scan).
     * Prints sku, product_name, and price of each result.
     * IMPORTANT: Do NOT call ddb.scan() in this method.
     */
    public static void queryProductsByCategory(String category) {
        System.out.println("[QUERY] Products in " + category + ":");
        // TODO: Implement Query.
        // 1. Build a QueryRequest with:
        //    - tableName(TABLE_NAME)
        //    - keyConditionExpression("category = :cat")
        //    - expressionAttributeValues: {":cat": S(category)}
        // 2. Call ddb.query(request).
        // 3. For each item in response.items(), print:
        //    "  - {sku}: {product_name} | ${price}"
        // 4. Print total count at the end.
    }

    // =========================================================================
    // Task 8: Query In-Stock Products by Category (Query + FilterExpression)
    // =========================================================================
    /**
     * Retrieves only IN-STOCK products in a category using Query + FilterExpression.
     *
     * IMPORTANT: Add a comment below your implementation explaining:
     * "Why are you still billed for reading all items in the partition
     * even though FilterExpression only returns in-stock items?"
     */
    public static void queryInStockByCategory(String category) {
        System.out.println("[QUERY + FILTER] In-stock products in " + category + ":");
        // TODO: Implement Query with FilterExpression.
        // 1. KeyConditionExpression: "category = :cat"
        // 2. FilterExpression:        "in_stock = :true"
        // 3. ExpressionAttributeValues: {":cat": S(category), ":true": BOOL(true)}
        // 4. Add your billing explanation as a comment here.
    }

    // =========================================================================
    // Main — Run All Tasks in Sequence
    // =========================================================================
    public static void main(String[] args) {
        // Task 1: Create the table
        createProductTable();

        // Task 2: Insert 5 products across 2 categories
        putProduct("LAPTOPS",  "SKU-LAP-001", "ProBook X15 Laptop",    1299.99, "TechMart",  true,  42);
        putProduct("LAPTOPS",  "SKU-LAP-002", "UltraSlim 13 Laptop",    849.00, "SwiftBook", true,  15);
        putProduct("LAPTOPS",  "SKU-LAP-003", "Budget Pro 11 Laptop",   399.99, "ValueTech", false,  0);
        putProduct("MONITORS", "SKU-MON-001", "ProDisplay 27\" 4K",     549.99, "TechMart",  true,  30);
        putProduct("MONITORS", "SKU-MON-002", "CurvedView 32\" QHD",    699.00, "VueMaster", true,   8);

        // Attempt a duplicate insert — should be rejected by the ConditionExpression
        putProduct("LAPTOPS",  "SKU-LAP-001", "ProBook X15 Laptop",   1399.99, "TechMart", true, 42);

        // Task 3: Read one product
        getProduct("LAPTOPS", "SKU-LAP-001");
        getProduct("LAPTOPS", "SKU-LAP-999"); // Should print "Product not found."

        // Task 4: Update a price
        updateProductPrice("LAPTOPS", "SKU-LAP-001", 1199.99);

        // Task 5: Decrement stock
        decrementStock("MONITORS", "SKU-MON-002", 3);
        decrementStock("MONITORS", "SKU-MON-002", 99); // Should fail: insufficient stock

        // Task 7: Query all laptops
        queryProductsByCategory("LAPTOPS");

        // Task 8: Query in-stock laptops only
        queryInStockByCategory("LAPTOPS");

        // Task 6: Delete a discontinued product
        deleteProduct("LAPTOPS", "SKU-LAP-003");
        deleteProduct("LAPTOPS", "SKU-LAP-999"); // Should fail gracefully
    }
}
