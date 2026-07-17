# DynamoDB Data Model: Tables, Items, and Attributes

## Learning Objectives
- Describe the structural hierarchy of the Amazon DynamoDB data model.
- Differentiate between tables, items, and attributes in NoSQL.
- Identify the data types supported by DynamoDB: Scalar, Document, and Set types.
- Structure data records as valid DynamoDB JSON documents.

## Why This Matters
When working with relational databases, we organize data into schemas, tables, rows, and columns. Column properties are strict: if a column is configured as a `VARCHAR(50)`, every row must store text matching that limit. 

Amazon DynamoDB organizes data differently. It uses a flexible, document-based data model. To write application code that interacts with DynamoDB, you must understand its hierarchy (tables, items, attributes) and how it formats data as JSON. This understanding allows you to design schema-less structures that store complex, nested objects (like user configurations or shopping items) inside a single database record without breaking validation rules.

## The Concept

### The Structural Hierarchy
The DynamoDB data model consists of three core components:

```
+-------------------------------------------------------------+
|                        TABLE                                |
|  - Collection of items (e.g., "products")                   |
|  - Requires a Primary Key (Partition Key / Sort Key)        |
+-------------------------------------------------------------+
      |
      v
+-------------------------------------------------------------+
|                        ITEM                                 |
|  - A single record in the table                             |
|  - Schema-less (can contain unique attributes)              |
|  - Maximum size limit: 400 KB                               |
+-------------------------------------------------------------+
      |
      v
+-------------------------------------------------------------+
|                      ATTRIBUTE                              |
|  - A name-value pair (e.g., "price" : 49.99)                |
|  - Can be scalar, set, or nested document types             |
+-------------------------------------------------------------+
```

1. **Tables**: Similar to tables in relational databases, a table is a collection of items. You must define a primary key (partition key, or partition key + sort key) when creating a table. The primary key attributes are the only structured schema requirement.
2. **Items**: An item is a collection of attributes that is uniquely identifiable among all other items in the table. Items are analogous to rows, but they are schema-less—individual items can have different attributes. The maximum size of an item in DynamoDB is **400 KB** (including attribute names and values).
3. **Attributes**: An attribute is a fundamental data element that does not need to be broken down further. It is a name-value pair, similar to columns in a relational table, but with a dynamic structure.

---

### Supported Data Types
DynamoDB supports several data types, categorized into three groups:

#### 1. Scalar Types
Represent a single value:
- **String (S)**: Text values (e.g., `"Alice"`).
- **Number (N)**: Numeric values (e.g., `49.99`, `101`). Numbers are sent as strings to prevent precision issues.
- **Binary (B)**: Binary data (e.g., encrypted strings or raw bytes).
- **Boolean (BOOL)**: `true` or `false`.
- **Null (NULL)**: Represents an empty or undefined state.

#### 2. Document Types
Represent nested, hierarchical structures:
- **List (L)**: An ordered collection of values, similar to an array (e.g., `["red", "blue", 42]`).
- **Map (M)**: An unordered collection of name-value pairs, similar to a JSON object (e.g., `{"city": "Boston", "zip": 02108}`).

#### 3. Set Types
Represent collections of unique scalar values:
- **String Set (SS)**: Array of unique strings.
- **Number Set (NS)**: Array of unique numbers.
- **Binary Set (BS)**: Array of unique binary blobs.

---

### DynamoDB JSON Format
When using the AWS CLI or low-level SDKs, DynamoDB requires you to specify the data type of each attribute explicitly using a specialized JSON format:

```json
{
  "attribute_name": {
    "DATA_TYPE_CODE": "value"
  }
}
```

*Example Type Codes*: `S` for String, `N` for Number, `BOOL` for Boolean, `M` for Map, and `L` for List.

## Code Examples

Let us examine how a complex user profile record is structured in DynamoDB JSON.

### Schema-less Item Structure (DynamoDB JSON)
Suppose we want to store a profile item. This item includes scalar types, a nested Map for address details, and a List of strings for system permissions.

```json
{
  "user_id": { "S": "usr_99210" },
  "display_name": { "S": "Alice Smith" },
  "is_admin": { "BOOL": false },
  "login_count": { "N": "14" },
  "address": {
    "M": {
      "street": { "S": "123 Main Street" },
      "city": { "S": "Boston" },
      "zip": { "N": "02108" }
    }
  },
  "roles": {
    "L": [
      { "S": "developer" },
      { "S": "support" }
    ]
  }
}
```

### Writing the Item using AWS CLI
To write this item into a DynamoDB table named `profiles`, save the JSON content as `item.json` and run the `put-item` command:

```bash
aws dynamodb put-item \
    --table-name profiles \
    --item file://item.json
```

Because DynamoDB is schema-less, another item in the same `profiles` table can be inserted with different attributes:

```json
{
  "user_id": { "S": "usr_99211" },
  "display_name": { "S": "Bob Jones" },
  "contract_expiry": { "S": "2026-12-31" }
}
```

This second item is saved in the same table despite having different attributes (`contract_expiry` instead of `address`, `roles`, or `is_admin`).

## Summary
- The DynamoDB data model consists of **Tables** containing **Items** made of **Attributes**.
- Every table must have a **Primary Key** defined during creation, but other attributes are schema-less.
- The maximum size limit for a single DynamoDB item is **400 KB**.
- Attribute data types are split into **Scalar** (S, N, BOOL), **Document** (Map, List), and **Set** (SS, NS) types.
- Low-level APIs use explicit **DynamoDB JSON** key wrappers (e.g., `{"name": {"S": "Alice"}}`) to identify data types.

## Additional Resources
- [Amazon DynamoDB Supported Data Types Reference](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/HowItWorks.NamingRulesDataTypes.html#HowItWorks.DataTypes)
- [Working with JSON in Amazon DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Programming.JSON.html)
- [DynamoDB Item Size Calculation Rules](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/CapacityUnitCalculations.html)
