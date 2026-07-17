# SQL UPDATE Syntax and Practices

## Learning Objectives
- Construct SQL statements to modify existing table records using `UPDATE`.
- Formulate precise `WHERE` clauses to restrict updates to target rows.
- Analyze the logical and business risks of executing `UPDATE` statements without filters.
- Implement the `UPDATE ... FROM` pattern to modify records based on data from other tables.

---

## Why This Matters
On Wednesday, we introduced DML as the tool used to manage data values in tables. In a full-stack system, updating existing data is a constant task: updating a user's password hash, modifying stock levels after a purchase, or marking a package as delivered.

However, `UPDATE` commands are inherently dangerous. Unlike `INSERT` (which adds new containers), `UPDATE` modifies existing records on disk. 

If you make a logical mistake in your Java code that builds the update statement, or if you run a query directly against a database client like DBeaver and forget to highlight the filter clause, you will overwrite the data of every single record in that table. Recovering from an unfiltered update is a complex task that requires restoring database backups, causing system downtime.

---

## The Concept

### 1. Basic UPDATE Syntax
The `UPDATE` statement specifies the target table, defines the new column values using the `SET` keyword, and applies a `WHERE` clause to filter which rows are modified.

```sql
UPDATE table_name
SET column_1 = value_1, column_2 = value_2
WHERE condition;
```

### 2. The Danger of the Unfiltered UPDATE
If you omit the `WHERE` clause, the database does not ask for confirmation. It immediately applies the new values to **every row** in the table.
-   *The query:* `UPDATE users SET email = 'temp@email.com';`
-   *The outcome:* Every single user in your system now has the exact same email address. The original emails are overwritten and lost.

**Enterprise Best Practice:** In many corporate environments, database clients (like DBeaver or Toad) are configured with "Safe Update Mode" enabled, which rejects any `UPDATE` or `DELETE` statement that does not contain a `WHERE` clause.

### 3. Advanced Pattern: UPDATE ... FROM
Occasionally, you need to update a table based on data stored in a completely different table. For example, if a supplier changes their discount structure, you might need to update the price of all inventory items supplied by them.

In PostgreSQL, you use the `UPDATE ... FROM` clause to join tables during an update operation:

```sql
UPDATE target_table t
SET t.column = source_table.value
FROM source_table
WHERE t.join_key = source_table.join_key
  AND source_table.filter_condition;
```

---

## Code Example: Safe and Advanced Updates

Let's work with an e-commerce `products` table and a `suppliers` table.

```sql
CREATE TABLE suppliers (
    supplier_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    discount_percent INT DEFAULT 0
);

CREATE TABLE products (
    product_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    supplier_id INT REFERENCES suppliers(supplier_id)
);
```

---

### Scenario A: Standard Safe Update
We want to increase the price of product 101 by 5%:

```sql
-- Safe: Targets ONLY product 101
UPDATE products
SET price = price * 1.05
WHERE product_id = 101;
```

---

### Scenario B: The Unfiltered Update (Danger Example)
Suppose a developer attempts to update product 101 but forgets the filter:

```sql
-- DANGER: Wipes all price differentiation!
UPDATE products
SET price = 29.99;
-- Result: Every product in the database is now priced at $29.99.
```

---

### Scenario C: Advanced Update from Another Table (UPDATE ... FROM)
A supplier named "A1 Tech" (ID 1) announces a 10% discount on all their products. We want to update our prices based on the discount percentage stored in the `suppliers` table.

```sql
-- Join tables during the update using FROM
UPDATE products p
SET price = price * (1 - (s.discount_percent / 100.0))
FROM suppliers s
WHERE p.supplier_id = s.supplier_id
  AND s.supplier_id = 1;
```

---

## Summary
-   The **`UPDATE`** statement modifies column values in existing table records.
-   The **`WHERE` clause** is critical; omitting it causes the update to apply to **every row** in the table, destroying unique records.
-   PostgreSQL supports **`UPDATE ... FROM`** to join tables during update operations, allowing values to be calculated based on related tables.

---

## Additional Resources
-   [PostgreSQL UPDATE Clause Reference](https://www.postgresql.org/docs/current/sql-update.html)
-   [W3Schools: SQL UPDATE Statement](https://www.w3schools.com/sql/sql_update.asp)
-   [DBeaver: Enabling Safe Updates Mode](https://dbeaver.io/docs/wiki/SQL-Editor/)
