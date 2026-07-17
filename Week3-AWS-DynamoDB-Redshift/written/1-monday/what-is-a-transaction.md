# Understanding Database Transactions

## Learning Objectives
- Define a database transaction and explain its purpose in data management.
- Contrast implicit and explicit transactions.
- Use PostgreSQL syntax (BEGIN, START TRANSACTION, COMMIT, ROLLBACK) to control transaction scope.
- Explain the behavior of a database session during active transactions.

## Why This Matters
Modern systems process millions of data updates every day. When multiple users or processes interact with a database simultaneously, keeping data consistent is a significant challenge. For example, in an online booking system, two users might try to reserve the last available seat at the same time. In an e-commerce platform, a system crash mid-checkout could result in payment being processed without the inventory being decremented.

A database transaction solves these problems by bundling multiple operations into a single unit. Understanding what a transaction is, and how to explicitly define its boundaries, is the foundation of building reliable, enterprise-grade cloud backends. It ensures that even when errors occur, the database remains in a valid, predictable state.

## The Concept

### What is a Database Transaction?
A database transaction is a logical unit of work that contains one or more SQL statements. A transaction executes as a single, indivisible operation: either all the statements within the transaction succeed and their changes are saved, or they all fail and any changes made up to that point are completely undone. 

Without transactions, if a multi-step database operation is interrupted by a network timeout, a server crash, or a syntax error, the database could be left in a partially updated state. This state of partial updates is known as data inconsistency, which can corrupt application logic and lead to system failures.

### Implicit vs. Explicit Transactions

Relational databases handle transactions in two primary ways:

1. **Implicit Transactions (Autocommit Mode)**:
   By default, database systems like PostgreSQL, MySQL, and Oracle operate in "autocommit" mode. In this mode, every single SQL statement (such as an `INSERT`, `UPDATE`, or `DELETE`) is treated as a separate transaction. The database automatically wraps the statement in a transaction, executes it, and commits it immediately if successful. If it fails, the database automatically rolls back that single statement. While convenient for simple queries, implicit transactions cannot span multiple operations.

2. **Explicit Transactions**:
   An explicit transaction is one where the developer defines the exact boundaries of the transaction. Using control commands, the developer tells the database when to start tracking changes, when to commit them, or when to discard them. This allows multiple, related SQL operations to be treated as a single block.

### Defining Explicit Boundaries in PostgreSQL
To start an explicit transaction, you must execute a command that signals the database to stop committing statements automatically.

- **BEGIN** or **START TRANSACTION**: Initiates a new transaction block. Subsequent SQL commands will be executed within this block, and their changes will only be visible to the current database session. They are not written permanently to disk yet.
- **COMMIT** (or **END**): Permanently saves all changes made since the transaction started and terminates the transaction.
- **ROLLBACK** (or **ABORT**): Discards all modifications made since the transaction started and terminates the transaction.

### Session Isolation and Visibility
When an explicit transaction is active, the database session enters a temporary state. Changes made to rows inside the transaction are immediately visible to the session that made them. However, other sessions querying the database will not see those changes until the transaction executes a `COMMIT`. This ensures that other users do not see incomplete or draft data.

## Code Examples

### Implicit Transaction (Default Behavior)
Each statement is independent and committed immediately.

```sql
-- Each line is its own transaction
INSERT INTO customers (id, name, email) VALUES (101, 'Jane Doe', 'jane@example.com'); 
-- Committed automatically

UPDATE accounts SET balance = balance - 50.00 WHERE customer_id = 101; 
-- Committed automatically
```

### Explicit Transaction with COMMIT
Grouping operations to ensure they both succeed or both fail.

```sql
-- Start the transaction block
BEGIN;

-- Operation 1: Deduct from sender
UPDATE accounts 
SET balance = balance - 150.00 
WHERE customer_id = 101;

-- Operation 2: Add to receiver
UPDATE accounts 
SET balance = balance + 150.00 
WHERE customer_id = 102;

-- Permanently save the changes
COMMIT;
```

### Explicit Transaction with ROLLBACK
If an error occurs or a condition is not met, discard all operations.

```sql
-- Start the transaction block
START TRANSACTION;

-- Operation 1: Create a order record
INSERT INTO orders (order_id, customer_id, total_amount) 
VALUES (5001, 101, 300.00);

-- Operation 2: Reduce product inventory
UPDATE products 
SET stock_quantity = stock_quantity - 1 
WHERE product_id = 999;

-- Suppose we detect that stock_quantity has dropped below zero (handled by application logic or constraints)
-- To prevent negative inventory, we cancel the entire order process:
ROLLBACK;

-- The database state remains unchanged. Order 5001 does not exist, and stock is not reduced.
```

### Checking Transaction Status
In PostgreSQL, you can verify your transaction state using client library functions or by observing the command prompt state in tools like `psql`. For example:
- `psql` displays `*=` in the prompt when inside an active transaction block (e.g., `postgres=*#`).

## Summary
- A **transaction** is a logical unit of database work containing one or more SQL statements.
- **Implicit transactions** commit each statement immediately. This is the default autocommit mode.
- **Explicit transactions** use `BEGIN` or `START TRANSACTION` to start a block, and `COMMIT` or `ROLLBACK` to end it.
- Explicit transactions are necessary to keep data consistent across multiple database updates.

## Additional Resources
- [PostgreSQL BEGIN Statement Documentation](https://www.postgresql.org/docs/current/sql-begin.html)
- [PostgreSQL START TRANSACTION Documentation](https://www.postgresql.org/docs/current/sql-start-transaction.html)
- [Database Transaction Concept Overview - GeeksforGeeks](https://www.geeksforgeeks.org/sql-transactions/)
