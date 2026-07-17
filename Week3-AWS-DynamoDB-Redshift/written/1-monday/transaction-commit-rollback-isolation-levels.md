# Transaction Control: COMMIT, ROLLBACK, SAVEPOINT, and Isolation Levels

## Learning Objectives
- Execute transaction control statements: COMMIT, ROLLBACK, and SAVEPOINT.
- Explain the role of transaction control in maintaining database consistency.
- Identify the four SQL standard transaction isolation levels and their tradeoffs.
- Apply appropriate transaction commands to design robust database interaction workflows.

## Why This Matters
In enterprise software development, data integrity is paramount. When transitioning from single-user applications to distributed cloud architectures, multiple processes read and write to the same database simultaneously. Without transactional control, concurrent access leads to corrupted records, incomplete operations, and system failures. 

Consider a financial ledger or an e-commerce checkout system: updating inventory without capturing payment, or debiting an account without a corresponding credit, is catastrophic. Transaction control statements (COMMIT, ROLLBACK, and SAVEPOINT) provide developers with the tools to group database modifications into atomic units. Understanding how these commands operate and how they interact with different isolation levels allows engineers to construct reliable, data-consistent applications.

## The Concept

### What is Transaction Control?
Transaction control is the mechanism by which database systems manage groups of SQL statements as a single logical unit of work. By default, relational database management systems (RDBMS) run in an autocommit mode, where each SQL statement is treated as a separate transaction that is committed immediately. Transaction control statements allow developers to override this behavior, explicitly marking where a transaction begins, when changes should be permanently saved, and when changes should be discarded.

### Transaction Control Commands
1. **COMMIT**: This command is used to save all changes made during the current transaction to the database permanently. Once a COMMIT is executed, the changes are written to the database logs and disk, making them visible to all other database sessions. The transaction is terminated, and a new one must be explicitly started for subsequent operations.
2. **ROLLBACK**: This command is used to discard all modifications made during the current transaction. If an error occurs, or if a business validation rule is violated, executing a ROLLBACK restores the database to the exact state it was in before the transaction began. It clears all lock structures and terminates the transaction.
3. **SAVEPOINT**: A SAVEPOINT is a temporary marker within a transaction. It allows you to roll back a portion of the transaction rather than the entire set of changes. This is particularly useful in complex, multi-step batch operations where some errors can be handled gracefully without discarding the entire process.

### SQL Standard Isolation Levels
Isolation is the "I" in ACID. It dictates how changes made by one transaction are visible to other concurrent transactions. The SQL standard defines four isolation levels, ordered from the lowest isolation (highest concurrency) to the highest isolation (lowest concurrency):

1. **Read Uncommitted**: The lowest isolation level. A transaction can read data that has been modified by another transaction but not yet committed. This leads to the maximum possible concurrency but introduces the risk of dirty reads.
2. **Read Committed**: The default level in many database systems (like PostgreSQL). A transaction can only read data that has been committed before the query began. It prevents dirty reads but allows non-repeatable reads and phantom reads.
3. **Repeatable Read**: Ensures that any data read by a transaction remains consistent throughout the transaction's lifetime. If a transaction reads a row, subsequent reads of that same row will return the same values, even if another transaction commits changes to that row in the meantime. This prevents dirty and non-repeatable reads but may still allow phantom reads in some systems (though PostgreSQL's implementation prevents phantoms here as well).
4. **Serializable**: The highest isolation level. It enforces strict ordering, making concurrent transactions appear as if they were executed sequentially (one after another). While it guarantees absolute data consistency, it severely limits concurrency and can lead to serialization failures (where transactions are aborted and must be retried by the application).

## Code Examples

### Standard COMMIT and ROLLBACK Flow
The following PostgreSQL script illustrates how a transaction is initiated, modified, and either committed or rolled back.

```sql
-- Assume we have an accounts table:
-- CREATE TABLE accounts (id INT PRIMARY KEY, balance DECIMAL(10,2));
-- INSERT INTO accounts VALUES (1, 1000.00), (2, 500.00);

-- Example 1: A successful transaction sequence
BEGIN;

UPDATE accounts 
SET balance = balance - 100.00 
WHERE id = 1;

UPDATE accounts 
SET balance = balance + 100.00 
WHERE id = 2;

COMMIT;
-- Both accounts have been successfully updated. Changes are permanent.
```

If a step fails, a ROLLBACK is executed to revert all changes:

```sql
-- Example 2: Rolling back on failure
BEGIN;

UPDATE accounts 
SET balance = balance - 200.00 
WHERE id = 1;

-- Suppose the receiver account does not exist or an error occurs here.
-- Instead of saving a partial transfer, we abort:
ROLLBACK;
-- The balance of account 1 is restored to its original value.
```

### Utilizing SAVEPOINT for Partial Rollbacks
SAVEPOINT allows a developer to establish checkpoints during a complex execution sequence.

```sql
BEGIN;

-- Insert a new log record
INSERT INTO accounts (id, balance) VALUES (3, 250.00);
SAVEPOINT log_checkpoint;

-- Attempt an experimental operation
UPDATE accounts 
SET balance = balance + 50.00 
WHERE id = 3;

-- If we decide to revert the experimental update but keep the insert:
ROLLBACK TO log_checkpoint;

-- Confirming and saving the initial insert
COMMIT;
-- Account 3 exists with a balance of 250.00. The 50.00 update was discarded.
```

### Setting Transaction Isolation Levels
You can set the isolation level for the current transaction using the `SET TRANSACTION` statement immediately after initiating the transaction.

```sql
BEGIN;
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;

SELECT balance FROM accounts WHERE id = 1;

-- If another session updates account 1 now, this session will still see 
-- the old balance if it executes the select again within this transaction.

COMMIT;
```

## Summary
- **COMMIT** saves transaction changes permanently.
- **ROLLBACK** discards all transaction changes since the last BEGIN or rollback target.
- **SAVEPOINT** creates local checkpoints, enabling partial rollbacks within a single transaction.
- **Isolation levels** control transaction visibility tradeoffs. Moving from Read Uncommitted to Serializable increases data integrity but reduces concurrency and increases locking/retries.

## Additional Resources
- [PostgreSQL Documentation on Transactions](https://www.postgresql.org/docs/current/tutorial-transactions.html)
- [PostgreSQL SET TRANSACTION Documentation](https://www.postgresql.org/docs/current/sql-set-transaction.html)
- [SQL Isolation Levels - Standard Tradeoffs Guide](https://database.guide/what-are-the-four-transaction-isolation-levels-in-sql/)
