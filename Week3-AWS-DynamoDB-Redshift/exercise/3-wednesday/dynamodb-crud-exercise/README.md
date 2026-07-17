# Lab: DynamoDB Products Table — CRUD and Query

**Mode:** A — Implementation (Code Lab)
**Estimated Time:** 3–4 hours
**Topics Covered:** DynamoDB PutItem, GetItem, UpdateItem, DeleteItem, Query, FilterExpression, AWS SDK for Java v2

---

## Scenario

You are a junior backend developer at **TechMart**, an online technology retailer. The team has chosen Amazon DynamoDB for the product catalogue because product data is accessed primarily by category and SKU — a perfect fit for key-value retrieval.

Your task is to build the **Products service** in Python. The service must be able to insert the initial product inventory, query products by category, update pricing, and remove discontinued items — all without using a `Scan` operation in any user-facing function.

---

## Prerequisites

- Java 11+ and Apache Maven installed
- AWS credentials configured via `aws configure` (or DynamoDB Local running on port 8000)
- Review the following written content before starting:
  - `written/3-wednesday/key-concepts-tables-items-attributes.md`
  - `written/3-wednesday/create-item.md`
  - `written/3-wednesday/update-item.md`
  - `written/3-wednesday/delete-item.md`
  - `written/3-wednesday/query-vs-scan-filtering.md`
  - `written/3-wednesday/ai-for-data-seeding.md`

---

## Table Design

You will create a DynamoDB table named **`techmart-products`** with the following schema:

| Attribute | Role | Type | Example Value |
|---|---|---|---|
| `category` | Partition Key | String | `"LAPTOPS"` |
| `sku` | Sort Key | String | `"SKU-LAP-001"` |
| `product_name` | Attribute | String | `"ProBook X15 Laptop"` |
| `price` | Attribute | Number | `1299.99` |
| `brand` | Attribute | String | `"TechMart"` |
| `in_stock` | Attribute | Boolean | `true` |
| `units_available` | Attribute | Number | `42` |

**Access Patterns this design must support:**
1. Insert a new product record.
2. Read a single product by `category` + `sku`.
3. Update the price of an existing product.
4. Update the `in_stock` flag and decrement `units_available` atomically.
5. Delete a discontinued product.
6. List all products in a given category (using **Query**, not Scan).
7. List all in-stock products in a category (using **Query** + `FilterExpression`).

---

## Core Tasks

Navigate to `starter_code/` and open `ProductsCrudLab.java`. Implement **each method** marked with a `// TODO` comment. Do **not** modify the method signatures.

### Task 1 — `createProductTable()`
Create the `techmart-products` table with the key schema defined above. Use `PAY_PER_REQUEST` billing mode. Poll `ddb.describeTable()` until the table status is `ACTIVE` before returning.

> **Hint:** Check `describeTable().table().tableStatus() == TableStatus.ACTIVE`.

---

### Task 2 — `putProduct(category, sku, productName, price, brand, inStock, unitsAvailable)`
Insert a new product item using `PutItem`. Add a **ConditionExpression** that prevents overwriting an existing product (check that the sort key does not already exist).

> **Hint:** Use `attribute_not_exists(sku)`. Numbers must be stored as `AttributeValue.builder().n(String.valueOf(price)).build()`.

---

### Task 3 — `getProduct(category, sku)`
Retrieve a single product using `GetItem` with **strongly consistent reads**. Print the product details if found, or print a "Product not found." message if the item does not exist.

---

### Task 4 — `updateProductPrice(category, sku, newPrice)`
Update only the `price` attribute of an existing product using `UpdateItem`. Use an `UpdateExpression` with the `SET` verb. Return the new attribute values using `ReturnValue.UPDATED_NEW`.

---

### Task 5 — `decrementStock(category, sku, quantitySold)`
Update `units_available` by subtracting `quantitySold` atomically. Use a **conditional expression** to prevent `units_available` from going below zero.

> **Hint:** Use `conditionExpression("units_available >= :qty")`.

---

### Task 6 — `deleteProduct(category, sku)`
Delete a product record using `DeleteItem`. Add a **ConditionExpression** to ensure the item exists before deleting (prevent silent no-ops).

> **Hint:** Use `attribute_exists(sku)`.

---

### Task 7 — `queryProductsByCategory(category)`
Retrieve **all products** in a given category using `Query` with a `keyConditionExpression`. Do **not** call `ddb.scan()`. Print the `sku`, `product_name`, and `price` of each result.

---

### Task 8 — `queryInStockByCategory(category)`
Retrieve only the **in-stock** products in a category using `Query` + `filterExpression`. Explain in a Java comment why you are still billed for reading all items in the partition even though only in-stock items are returned.

---

### Task 9 — AI-Assisted Seed Data (Reflection Task)
Using the prompt template from `written/3-wednesday/ai-for-data-seeding.md`:

1. Craft an AI prompt to generate **10 product items** for your `techmart-products` table. Include at least 3 categories (e.g., `LAPTOPS`, `MONITORS`, `KEYBOARDS`).
2. Request the output in **DynamoDB JSON BatchWriteItem format**.
3. Validate the output against the 5-point checklist in the written guide.
4. Save the validated JSON to `starter_code/seed-data.json` and execute it via the AWS CLI:
   ```bash
   aws dynamodb batch-write-item --request-items file://seed-data.json
   ```
5. Write a brief reflection (3–5 sentences) in `starter_code/ai-reflection.md`: Did the AI generate valid DynamoDB JSON on the first attempt? What corrections did you need to make?

---

## Running the Lab

```bash
# 1. (Optional) Start DynamoDB Local for offline development
docker run -p 8000:8000 amazon/dynamodb-local

# 2. Build the project
cd starter_code
mvn compile

# 3. Run your solution
mvn exec:java -Dexec.mainClass="ProductsCrudLab"
```

**Expected output for a successful run:**
```
[CREATE TABLE] Creating techmart-products...
[CREATE TABLE] Table is ACTIVE.
[PUT] Inserted SKU-LAP-001 into LAPTOPS.
[GET] SKU-LAP-001: ProBook X15 Laptop | Price: 1299.99 | In Stock: True
[UPDATE PRICE] SKU-LAP-001 new price: 1199.99
[DECREMENT STOCK] SKU-LAP-001 units remaining: 41
[QUERY] Products in LAPTOPS (3 items):
  - SKU-LAP-001: ProBook X15 Laptop | $1199.99
  ...
[QUERY + FILTER] In-stock LAPTOPS: 3 items
[DELETE] SKU-LAP-003 deleted from LAPTOPS.
```

---

## Definition of Done

Your implementation is complete when all of the following are true:

- [ ] `createProductTable()` creates the table with the correct composite key schema.
- [ ] `putProduct()` rejects duplicate writes via a ConditionExpression.
- [ ] `getProduct()` returns the correct item using strongly consistent reads.
- [ ] `updateProductPrice()` modifies only the price; all other attributes remain unchanged.
- [ ] `decrementStock()` prevents units from dropping below zero using a conditional expression.
- [ ] `deleteProduct()` fails gracefully if the item does not exist.
- [ ] `queryProductsByCategory()` uses `Query`, not `Scan`.
- [ ] `queryInStockByCategory()` uses `Query` + `FilterExpression`.
- [ ] `seed-data.json` contains valid DynamoDB JSON (validated against the 5-point checklist).
- [ ] `ai-reflection.md` contains a 3–5 sentence honest reflection.

---

## Stretch Challenges (Optional)

1. **Pagination:** Modify `queryProductsByCategory()` to handle paginated results using `lastEvaluatedKey()`.
2. **BatchWriteItem:** Implement a `bulkInsertProducts(List<Map<String, AttributeValue>> items)` method that inserts up to 25 products in a single API call.
3. **Soft Delete:** Instead of deleting the item, implement a `discontinueProduct()` method that sets a `status` attribute to `"DISCONTINUED"` using `UpdateItem`.

---

## Submission

Push your completed `starter_code/` directory to your GitHub repository in the folder `week3/dynamodb-crud-lab/`. Your repository must contain:
- `ProductsCrudLab.java` — completed implementation
- `pom.xml` — Maven build file (already provided)
- `seed-data.json` — validated BatchWriteItem payload
- `ai-reflection.md` — your AI seeding reflection
