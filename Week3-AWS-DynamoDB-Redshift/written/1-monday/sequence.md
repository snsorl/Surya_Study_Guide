# Sequence Management in PostgreSQL

## Learning Objectives
- Explain the purpose and behavior of database sequences.
- Create and configure database sequences using the `CREATE SEQUENCE` command.
- Generate and retrieve sequence values using `NEXTVAL` and `CURRVAL`.
- Contrast explicit sequence management with the implicit `SERIAL` column shorthand.
- Resolve sequence out-of-sync errors during manual database data migrations.

## Why This Matters
Relational tables require a primary key to uniquely identify each row. In many schemas, this key is a surrogate integer key that auto-increments with each insertion. Under the hood, databases do not simply "add one" to the last row's ID. Instead, they use a dedicated database object called a sequence. 

When building backend services, understanding how sequences work is critical. If your application logic inserts records with manual IDs during migrations, the sequence will fall out of sync, causing subsequent automated inserts to crash with duplicate key exceptions. Mastering sequence management allows engineers to manage primary key generation safely, maintain reference continuity during data imports, and write high-throughput bulk insertions.

## The Concept

### What is a Database Sequence?
A sequence is a special, schema-bound database object designed to generate a sequence of unique integers. Sequences are optimized for high-concurrency environments. Unlike standard table queries, a sequence does not lock its state during a transaction. If Transaction A requests a sequence number, that number is immediately consumed, and Transaction B will receive the next number, even if Transaction A subsequently rolls back. This prevents transactions from blocking each other while generating primary keys.

*Note on Gaps*: Because sequence generation is isolated from transaction rollback, any sequence number generated during a transaction that is later rolled back is lost. This is normal behavior and means sequences are guaranteed to be unique, but not necessarily contiguous (gaps are acceptable).

### Core Sequence Operations
- **NEXTVAL('sequence_name')**: Increments the sequence state and returns the new value. This is atomic and safe across concurrent sessions.
- **CURRVAL('sequence_name')**: Returns the value most recently obtained by `NEXTVAL` in the *current database session*. If no `NEXTVAL` has been called in the session, calling `CURRVAL` will throw an error. This is session-isolated to prevent conflicts with concurrent users.

### Sequences vs. SERIAL Shorthand
In PostgreSQL, developers often define auto-incrementing columns using the `SERIAL` pseudo-type:

```sql
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50)
);
```

When you define a column as `SERIAL`, PostgreSQL performs the following tasks automatically behind the scenes:
1. Creates a sequence object named `users_user_id_seq`.
2. Sets the default value of the `user_id` column to `nextval('users_user_id_seq')`.
3. Marks the sequence as owned by the `users.user_id` column, meaning the sequence is automatically dropped if the table or column is deleted.

Using `SERIAL` is convenient, but understanding the underlying sequence allows you to manage it manually when performing bulk operations, database seeding, or primary key adjustments.

## Code Examples

### Defining and Configuring a Custom Sequence
You can create a sequence with custom boundaries, step values, and caching settings.

```sql
CREATE SEQUENCE order_id_seq
    START WITH 1000
    INCREMENT BY 10
    MINVALUE 1000
    MAXVALUE 999999
    NO CYCLE;
```

### Accessing Sequence Values
Let's see how sequence values are generated and queried.

```sql
-- Retrieve the next sequence value (will return 1000)
SELECT NEXTVAL('order_id_seq');

-- Retrieve the current value within this session (returns 1000)
SELECT CURRVAL('order_id_seq');

-- Retrieve the next value again (returns 1010)
SELECT NEXTVAL('order_id_seq');
```

Using the sequence to populate a table manually:

```sql
CREATE TABLE customer_orders (
    order_id INT PRIMARY KEY,
    order_date DATE DEFAULT CURRENT_DATE
);

-- Insert rows using NEXTVAL
INSERT INTO customer_orders (order_id) VALUES (NEXTVAL('order_id_seq'));
INSERT INTO customer_orders (order_id) VALUES (NEXTVAL('order_id_seq'));

SELECT * FROM customer_orders;
-- Orders will have IDs 1020 and 1030
```

### Inspecting Sequence Metadata
You can inspect the properties and current state of a sequence by querying the sequence catalog.

```sql
-- In PostgreSQL 10+, you can query sequences like tables:
SELECT last_value, log_cnt, is_called 
FROM order_id_seq;
```

### Manual Sequence Management (Fixing Out-of-Sync Primary Keys)
A common database error occurs when a developer imports data with pre-existing primary keys into a table with a `SERIAL` column:

```sql
CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,
    item_name VARCHAR(100)
);

-- Seeding data with explicit IDs
INSERT INTO items (item_id, item_name) VALUES (1, 'Widget A'), (2, 'Widget B'), (3, 'Widget C');

-- If we now try to insert a row using the default sequence generator:
INSERT INTO items (item_name) VALUES ('Widget D');
-- ERROR: duplicate key value violates unique constraint "items_pkey"
-- DETAIL: Key (item_id)=(1) already exists.
```

Why did this fail? The sequence `items_item_id_seq` was never incremented during the manual inserts. Its state is still at `1`. When we ran the default insert, the sequence generated `1`, which collided with the pre-existing row.

To resolve this, we must manually update (set) the sequence to match the maximum ID in the table using `setval()`:

```sql
-- Find the maximum ID and set the sequence to that value
SELECT SETVAL('items_item_id_seq', COALESCE(MAX(item_id), 1)) 
FROM items;

-- Now, inserting a new row works perfectly:
INSERT INTO items (item_name) VALUES ('Widget D');
-- Success! Item D gets item_id = 4.
```

## Summary
- A **sequence** is a database object optimized to generate unique integers concurrently without transaction locks.
- **NEXTVAL** advances the sequence and returns the next value; **CURRVAL** retrieves the current session value.
- The **SERIAL** type is a convenient shorthand that automatically creates and links a sequence to a column.
- Manual data imports bypass sequence incrementing. Use `SETVAL` to sync a sequence back to the maximum ID in the table to prevent duplicate key errors.

## Additional Resources
- [PostgreSQL Documentation on CREATE SEQUENCE](https://www.postgresql.org/docs/current/sql-createsequence.html)
- [PostgreSQL Sequence Manipulation Functions](https://www.postgresql.org/docs/current/functions-sequence.html)
- [Fixing PostgreSQL Out-of-Sync Sequences - StackOverflow Guide](https://stackoverflow.com/questions/4448340/how-to-reset-postgres-primary-key-sequence-when-it-falls-out-of-sync)
