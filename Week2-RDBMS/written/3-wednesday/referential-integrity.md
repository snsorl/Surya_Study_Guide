# Referential Integrity

## Learning Objectives
- Define Referential Integrity and explain its role in maintaining data quality.
- Explain the technical guarantees provided by referential integrity in relational tables.
- Identify primary violation scenarios (orphan records, parent updates, parent deletions).
- Analyze how RDBMS engines enforce referential integrity using foreign key constraints.

---

## Why This Matters
In enterprise software development, data is constantly changing. Customers update their account details, products are discontinued, and orders are processed, modified, or canceled. Because this data is distributed across multiple related tables, keeping the relationships in sync is a major challenge.

If a developer deletes a product from the database, what happens to the thousands of historical orders that refer to that product? If the database does not enforce **Referential Integrity**, those orders will point to a blank ID. If your Java reporting code tries to look up the product name to generate a tax receipt, the application will crash or display corrupted lines.

Referential integrity guarantees that relationships between tables remain consistent at all times. By delegating this responsibility to the database engine, you write less validation boilerplate in Java and ensure that database operations are always safe.

---

## The Concept

### 1. What is Referential Integrity?
**Referential Integrity** is a state of relational database consistency which dictates that any foreign key value in a table must point to a valid, existing primary key in the referenced table. 

It guarantees that:
- A child table record can never reference a non-existent parent table record.
- A parent record cannot be modified or deleted if doing so leaves referencing child records "orphaned".

Referential integrity is enforced at the schema level using **Foreign Key Constraints**.

### 2. Violations and Protection Scenarios
If a database engine did not enforce referential integrity, three main violation scenarios could occur. RDBMS engines protect against these automatically:

#### Scenario A: Inserting an Orphan (Child Write)
-   *The Action:* A user tries to create a record in a child table referencing a parent ID that does not exist.
-   *The Protection:* The database engine rejects the write command immediately.

#### Scenario B: Deleting a Referenced Parent (Parent Delete)
-   *The Action:* An administrator tries to delete a parent record that still has child records pointing to it.
-   *The Protection:* By default, the database rejects the deletion with an integrity error. (Alternatively, the database can cascade the delete or set references to NULL, which we cover on Thursday).

#### Scenario C: Modifying a Referenced Parent Key (Parent Update)
-   *The Action:* An administrator tries to change the primary key value of a parent record (e.g., changing a department's ID from `10` to `20`), which would leave employee records pointing to the old ID `10`.
-   *The Protection:* The database blocks the modification, preventing the link from breaking.

---

## Code Example

Let's illustrate these protection mechanics with a PostgreSQL demonstration.

### Setup Schema
```sql
CREATE TABLE parent_suppliers (
    supplier_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE child_inventory (
    item_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    supplier_id INT REFERENCES parent_suppliers(supplier_id) -- Enforces Referential Integrity
);

-- Seed parent data
INSERT INTO parent_suppliers (supplier_id, name) VALUES (1, 'Apex Industrial');
```

---

### Violation Scenario Demonstrations

#### 1. Orphan Insertion Violation (Scenario A)
We attempt to insert an inventory item supplied by Supplier 99 (which does not exist):

```sql
INSERT INTO child_inventory (item_id, name, supplier_id)
VALUES (101, 'Steel Bolts', 99);
-- Result: ERROR: insert or update on table "child_inventory" violates foreign key constraint
```
The database blocks the insertion, preventing an orphan record.

---

#### 2. Referenced Parent Deletion Violation (Scenario B)
We insert a valid inventory item, and then attempt to delete the supplier:

```sql
-- Insert valid item
INSERT INTO child_inventory (item_id, name, supplier_id)
VALUES (102, 'Copper Washers', 1);

-- Attempt to delete Supplier 1
DELETE FROM parent_suppliers WHERE supplier_id = 1;
-- Result: ERROR: update or delete on table "parent_suppliers" violates foreign key constraint
```
The database blocks the deletion, protecting the child record from losing its supplier association.

---

## Summary
-   **Referential Integrity** guarantees that all relationship links between tables remain valid and consistent.
-   It is enforced using **Foreign Key Constraints**.
-   It prevents **orphan records** by blocking inserts of non-existent parent IDs.
-   By default, it protects database states by blocking the deletion or key modification of referenced parent records.

---

## Additional Resources
-   [GeeksforGeeks: Referential Integrity in DBMS](https://www.geeksforgeeks.org/referential-integrity-in-dbms/)
-   [PostgreSQL Foreign Keys and Constraints](https://www.postgresql.org/docs/current/ddl-constraints.html)
-   [Relational Database Design Rules - Referential Integrity](https://www.dummies.com/article/technology/software/databases/relational-database-design-rules-referential-integrity-281987/)
