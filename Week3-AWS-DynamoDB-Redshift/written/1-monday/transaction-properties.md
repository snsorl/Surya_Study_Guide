# Transaction Isolation Anomalies and Prevention

## Learning Objectives
- Identify and define the three classic SQL isolation anomalies: Dirty Read, Non-Repeatable Read, and Phantom Read.
- Construct transaction execution timelines illustrating how these anomalies occur.
- Map the four SQL standard isolation levels to the anomalies they prevent.
- Select the appropriate isolation level for common business application requirements.

## Why This Matters
Relational database systems are designed to support multiple client connections running concurrent queries. While absolute transaction isolation (running transactions sequentially) guarantees consistency, it causes severe performance bottlenecks. In a production environment, complete serialization is rarely acceptable. 

Instead, developers configure different transaction isolation levels. To choose the right isolation level, you must understand the specific data anomalies that can occur under lower levels and how they impact application behavior. Choosing an incorrect level can lead to financial errors, double-sold inventory, or invalid reports.

## The Concept

### The Three Transaction Anomalies
When multiple transactions execute concurrently without complete isolation, three primary types of data anomalies can occur:

#### 1. Dirty Read (Active Read of Uncommitted Data)
A dirty read occurs when Transaction A modifies a row, and Transaction B reads that row *before* Transaction A commits the change. If Transaction A subsequently aborts and rolls back, the data read by Transaction B never officially existed in the database.

*Impact*: Transaction B makes decisions or generates outputs based on invalid, temporary data.

#### 2. Non-Repeatable Read (Fuzzy Read of Committed Changes)
A non-repeatable read occurs when Transaction A reads a row, and Transaction B updates or deletes that same row and commits the change. If Transaction A reads the row a second time within the same transaction, it receives the updated value or finds that the row has been deleted.

*Impact*: Within a single logical unit of work, a read operation returns different values for the same record.

#### 3. Phantom Read (Read of Committed Additions)
A phantom read occurs when Transaction A executes a query that retrieves a set of rows satisfying a search condition (e.g., a range query). Transaction B inserts a new row that satisfies Transaction A's search condition and commits. If Transaction A runs the range query again, a new row appears in the result set—this is the "phantom" row.

*Difference from Non-Repeatable Read*: A non-repeatable read involves modifications or deletions of *existing* rows that were already read. A phantom read involves the insertion of *new* rows that match the query's criteria.

---

### Isolation Levels and Anomalies Matrix
The SQL standard defines four isolation levels based on which anomalies they permit or prevent:

| Isolation Level | Dirty Read | Non-Repeatable Read | Phantom Read |
|---|---|---|---|
| **Read Uncommitted** | Allowed | Allowed | Allowed |
| **Read Committed** | Prevented | Allowed | Allowed |
| **Repeatable Read** | Prevented | Prevented | Allowed (Note: PostgreSQL prevents it here too) |
| **Serializable** | Prevented | Prevented | Prevented |

*Note on PostgreSQL*: In PostgreSQL, the `Repeatable Read` level is implemented using MVCC snapshots and does not allow Phantom Reads, exceeding the minimum requirements of the SQL standard.

---

### Transaction Timelines (Visual Textual Flow)

Let us examine step-by-step how these anomalies occur.

#### Timeline 1: Dirty Read (Allowed under Read Uncommitted)
Assume we have an accounts table with Alice's balance = $500.00.

```
Transaction 1 (T1)                      Transaction 2 (T2)
------------------                      ------------------
1. BEGIN;
2. UPDATE accounts 
   SET balance = 400.00 
   WHERE owner = 'Alice';
                                        3. BEGIN;
                                        4. SELECT balance FROM accounts 
                                           WHERE owner = 'Alice';
                                           -- T2 reads $400.00 (Uncommitted!)
5. ROLLBACK;
   -- T1's update is reverted. Alice's balance remains $500.00.
                                        6. COMMIT;
                                           -- T2 operates on $400.00, which is incorrect!
```

#### Timeline 2: Non-Repeatable Read (Allowed under Read Committed)
Assume Alice's balance = $500.00.

```
Transaction 1 (T1)                      Transaction 2 (T2)
------------------                      ------------------
1. BEGIN;
2. SELECT balance FROM accounts 
   WHERE owner = 'Alice';
   -- T1 reads $500.00
                                        3. BEGIN;
                                        4. UPDATE accounts 
                                           SET balance = 600.00 
                                           WHERE owner = 'Alice';
                                        5. COMMIT;
                                           -- T2 changes Alice's balance to $600.00.
6. SELECT balance FROM accounts 
   WHERE owner = 'Alice';
   -- T1 reads $600.00 (Value changed within T1!)
7. COMMIT;
```

#### Timeline 3: Phantom Read (Allowed under Read Committed / Standard Repeatable Read)
Assume we are counting the number of accounts with balance > $1000.00. Currently, there are 2 such accounts.

```
Transaction 1 (T1)                      Transaction 2 (T2)
------------------                      ------------------
1. BEGIN;
2. SELECT COUNT(*) FROM accounts 
   WHERE balance > 1000.00;
   -- T1 gets result: 2
                                        3. BEGIN;
                                        4. INSERT INTO accounts (owner, balance) 
                                           VALUES ('Charlie', 1500.00);
                                        5. COMMIT;
                                           -- Charlie's account is saved and committed.
6. SELECT COUNT(*) FROM accounts 
   WHERE balance > 1000.00;
   -- T1 gets result: 3 (Charlie is the phantom!)
7. COMMIT;
```

## Code Examples

The following PostgreSQL scripts demonstrate how changing isolation levels prevents these anomalies.

### Preventing Dirty Reads using Read Committed (PostgreSQL Default)
PostgreSQL does not allow Read Uncommitted; it defaults to Read Committed, which prevents dirty reads automatically.

```sql
-- Session 1
BEGIN;
UPDATE accounts SET balance = balance - 100 WHERE id = 1;
-- Change is active in Session 1, but NOT committed.

-- Session 2
BEGIN;
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
SELECT balance FROM accounts WHERE id = 1;
-- Session 2 blocks or returns the OLD balance. It will NOT see the uncommitted update.
COMMIT;

-- Session 1
COMMIT;
```

### Preventing Non-Repeatable Reads using Repeatable Read
If an application needs to read a row multiple times and guarantee the value does not change, it must use the Repeatable Read isolation level.

```sql
-- Session 1
BEGIN;
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SELECT balance FROM accounts WHERE id = 1; -- Returns 500.00

-- Session 2 (Concurrent execution)
BEGIN;
UPDATE accounts SET balance = 600.00 WHERE id = 1;
COMMIT; -- Session 2 commits change to 600.00

-- Session 1
SELECT balance FROM accounts WHERE id = 1; 
-- Returns 500.00 (The update from Session 2 is isolated from Session 1)
COMMIT;

-- After Session 1 commits, a new transaction will see the updated value:
SELECT balance FROM accounts WHERE id = 1; -- Returns 600.00
```

### Handling Serialization Failures (Serializable Level)
When using the `SERIALIZABLE` level, the database detects concurrency conflicts that would result in inconsistencies and aborts one of the conflicting transactions. The application must be designed to catch this error and retry the transaction.

```sql
-- Session 1
BEGIN;
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
SELECT SUM(balance) FROM accounts; -- Reads data to make a decision

-- Session 2
BEGIN;
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
INSERT INTO accounts (id, balance) VALUES (4, 300.00);
COMMIT;

-- Session 1
INSERT INTO audit_logs (log_msg) VALUES ('Calculated sum of accounts');
COMMIT; 
-- ERROR: could not serialize access due to read/write dependencies among transactions
-- Application code must intercept this error and run the entire Session 1 block again.
```

## Summary
- **Dirty Reads** are prevented by Read Committed, Repeatable Read, and Serializable levels.
- **Non-Repeatable Reads** are prevented by Repeatable Read and Serializable levels.
- **Phantom Reads** are prevented by Serializable (and PostgreSQL's implementation of Repeatable Read).
- Selecting the correct isolation level requires balancing consistency requirements against concurrency performance. Higher levels introduce more blocking, serialization failures, and application-side retry requirements.

## Additional Resources
- [PostgreSQL Documentation on Transaction Isolation](https://www.postgresql.org/docs/current/transaction-iso.html)
- [Microsoft Learn - Transaction Isolation Levels and Concurrency](https://learn.microsoft.com/en-us/sql/t-sql/statements/set-transaction-isolation-level-transact-sql)
- [Database Transaction Isolation Anomalies Explained - Vlad Mihalcea](https://vladmihalcea.com/database-transaction-isolation-levels-anomalies/)
