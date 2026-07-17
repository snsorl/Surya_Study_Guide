# Candidate Keys, Alternate Keys, and Secondary Indexes

## Learning Objectives
- Define Candidate Keys and explain how the database design team selects the Primary Key from them.
- Define Alternate Keys and identify their role as secondary identifiers.
- Create Secondary Indexes on alternate keys to optimize database query performance.
- Analyze the performance trade-offs (read speed vs. write overhead) of creating secondary indexes.

---

## Why This Matters
When designing database schemas, we spend a lot of time defining primary keys to identify rows uniquely. However, once our application is in production, users rarely search for data using physical primary key integers (like `customer_id = 4921`). Instead, they search using real-world fields: they log in using their `email`, look up a transaction by a `confirmation_code`, or search for a product using its `upc_barcode`.

If these secondary search fields are not indexed, the database engine must perform a **Full Table Scan**—reading every single page of data on the disk line-by-line to find a match. For a table with millions of rows, this can take seconds, causing your web page to freeze.

Understanding how to classify **Candidate and Alternate Keys** and knowing when to build **Secondary Indexes** on them is a critical full-stack engineering skill that directly controls application performance.

---

## The Concept

### 1. Key Classifications
To design a database, you must understand the hierarchy of keys:

-   **Superkey:** Any column (or group of columns) that uniquely identifies a row in a table.
-   **Candidate Key:** A minimal Superkey. This means you cannot remove any columns from it without losing the property of uniqueness. A table can have multiple Candidate Keys.
    -   *Example:* In a user table, both `user_id` (surrogate) and `email` (natural) are Candidate Keys.
-   **Primary Key:** The single Candidate Key selected by the database designer to act as the primary, physical identifier for the table.
-   **Alternate (Secondary) Key:** Any Candidate Key that was *not* chosen as the primary key. Alternate keys are secured using `UNIQUE` constraints.

```
       +--------------------------------------------+
       | SUPERKEYS (Any unique column combination)  |
       |   +------------------------------------+   |
       |   | CANDIDATE KEYS (Minimal uniqueness)|   |
       |   |   +-------------+  +-----------+   |   |
       |   |   | PRIMARY KEY |  | ALTERNATE |   |   |
       |   |   | (Chosen PK) |  |   KEYS    |   |   |
       |   |   +-------------+  +-----------+   |   |
       |   +------------------------------------+   |
       +--------------------------------------------+
```

### 2. Secondary Indexes
When you declare a Primary Key or a Unique Key constraint, the database engine automatically creates an index on those columns.

However, if you frequently search or filter a table by a column that is *not* a primary or unique key (for instance, searching employees by `last_name`), you must create a **Secondary Index** manually.

#### How Indexes Speed Up Queries
Think of a database index like the index at the back of a textbook:
-   Without the index, if you want to find every page that mentions "transactions", you must read the entire book from page 1 to the end (Full Table Scan).
-   With the index, you flip to the back, look up the word "transactions", find the list of page numbers, and jump directly to those pages (Index Scan).

In PostgreSQL, indexes are typically structured as **B-Trees (Balanced Trees)**, which reduce search time from linear complexity $O(N)$ to logarithmic complexity $O(\log N)$.

#### The Indexing Trade-off: Read vs. Write Speed
Creating indexes is not free. You must evaluate the trade-offs:
-   **Read Performance:** Queries with `WHERE` filters matching indexed columns execute almost instantly.
-   **Write Overhead:** Every time you execute an `INSERT`, `UPDATE`, or `DELETE`, the database must write the data to the table *and* update the indexes on disk. If a table has too many indexes, write performance will drop.
-   **Storage Space:** Indexes consume physical disk space. In large enterprise systems, index storage can occasionally exceed table data storage.

---

## Code Example: Optimizing Schema Lookups

Let's write a SQL schema showing key classifications and index creation.

### Schema Definition
```sql
CREATE TABLE member_accounts (
    member_id INT PRIMARY KEY,              -- Primary Key (chosen from Candidate Keys)
    email VARCHAR(100) UNIQUE NOT NULL,     -- Alternate Key (Unique, not chosen as PK)
    tax_identifier CHAR(9) UNIQUE,          -- Alternate Key
    last_name VARCHAR(50) NOT NULL,         -- Regular search field (non-unique)
    first_name VARCHAR(50) NOT NULL
);
```

### Creating a Secondary Index
Suppose our web app has a search directory where users frequently look up members by their last name. Because `last_name` is not unique, we did not declare a constraint, so no index was created automatically. 

We create a secondary index manually:

```sql
CREATE INDEX idx_members_last_name ON member_accounts(last_name);
```

If we execute this query:
```sql
SELECT first_name, email 
FROM member_accounts 
WHERE last_name = 'Smith';
```
The database optimizer will use our B-Tree index `idx_members_last_name` to find the matches instantly, avoiding a full table scan.

---

## Summary
-   **Candidate Keys** are all columns that qualify to be unique identifiers.
-   The **Primary Key** is the chosen identifier; **Alternate Keys** are the remaining candidate keys.
-   **Secondary Indexes** are data structures built manually to speed up lookups on frequently queried columns.
-   Indexes improve **read performance** but introduce **write overhead** and consume disk storage.

---

## Additional Resources
-   [PostgreSQL Indexes Official Documentation](https://www.postgresql.org/docs/current/indexes.html)
-   [SQL Candidate Key vs Alternate Key Explained](https://www.javatpoint.com/dbms-candidate-key)
-   [Use The Index, Luke! - Guide to Database Indexing](https://use-the-index-luke.com/)
