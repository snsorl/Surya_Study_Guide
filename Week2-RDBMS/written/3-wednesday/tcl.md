# TCL: Transaction Control Language

## Learning Objectives
- Define a Database Transaction and explain its role in maintaining data consistency.
- Explain the syntax and utility of core TCL commands (`COMMIT`, `ROLLBACK`, `SAVEPOINT`).
- Define transaction boundaries and manage execution flows using transactional control blocks.
- Construct SQL scripts that simulate transactional recovery during simulated system failures.

---

## Why This Matters
Suppose you log into your mobile banking app to transfer $100 to a friend. The system performs two operations:
1.  It updates your account, deducting $100 (debit).
2.  It updates your friend's account, adding $100 (credit).

Now imagine if the database server crashes or loses power immediately after step 1, but before step 2. Your money has vanished from your account, but your friend never received it. This is a fatal data consistency error.

To prevent this, relational databases use **Transactions**. A transaction groups multiple database operations into a single unit of work. **Transaction Control Language (TCL)** allows you to manage these boundaries, guaranteeing that either *all* changes are saved permanently, or *none* of them are, returning the database to a clean starting state.

---

## The Concept

### 1. What is a Transaction?
A **Transaction** is a logical unit of database processing that includes one or more DML statements (inserts, updates, or deletes). Transactions are defined by the ACID principles (which we cover in depth in Week 3). 

For now, the key property to understand is **Atomicity**: a transaction is "all-or-nothing".

### 2. Transaction Boundaries
By default, most database connections run in **Auto-Commit Mode**. In this mode, every individual DML statement is treated as a separate transaction and is automatically saved to the disk immediately.

To group statements together, you must declare transaction boundaries:
-   **`BEGIN`** or **`BEGIN TRANSACTION`**: Disables auto-commit mode for the current session and opens a new transaction block.
-   **`COMMIT`**: Saves all DML modifications made within the current transaction permanently to the physical disk, making them visible to all other users.
-   **`ROLLBACK`**: Discards all modifications made since the transaction started, restoring the tables to their pre-transaction states.
-   **`SAVEPOINT name`**: Creates a marked placeholder within a transaction. This allows you to roll back *part* of a transaction to a specific point without discarding the entire block.

---

## Code Example: Transactional Bank Transfer

Let's write a SQL transaction simulating the $100 transfer. We will use a simple `checking_accounts` table.

```sql
CREATE TABLE checking_accounts (
    account_id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL
);

INSERT INTO checking_accounts VALUES 
(1, 'Alice', 500.00),
(2, 'Bob', 50.00);
```

---

### Scenario A: Successful Transaction (COMMIT)
Both accounts update correctly. We commit the transaction.

```sql
BEGIN TRANSACTION;

-- Step 1: Debit Alice's account
UPDATE checking_accounts 
SET balance = balance - 100.00 
WHERE account_id = 1;

-- Step 2: Credit Bob's account
UPDATE checking_accounts 
SET balance = balance + 100.00 
WHERE account_id = 2;

-- Step 3: Finalize the transaction
COMMIT;
```
If we run `SELECT *` now, Alice's balance is $400 and Bob's balance is $150. The changes are saved permanently.

---

### Scenario B: Failed Transaction Recovery (ROLLBACK)
Alice tries to transfer another $100, but a network error occurs mid-execution. We rollback to protect the balances.

```sql
BEGIN TRANSACTION;

-- Step 1: Debit Alice's account
UPDATE checking_accounts 
SET balance = balance - 100.00 
WHERE account_id = 1;

-- ERROR OCCURS HERE (e.g., connection lost or credit query fails)
-- We must discard the pending debit to prevent money loss

ROLLBACK;
```
If we inspect the database after the `ROLLBACK`, Alice's balance is restored to $400. The pending $100 deduction was completely discarded.

---

### Scenario C: Partial Rollback (SAVEPOINT)
Suppose we want to execute a multi-step operation but want to revert only the final step if it fails.

```sql
BEGIN TRANSACTION;

-- Step 1: Update Alice's balance
UPDATE checking_accounts SET balance = balance - 50.00 WHERE account_id = 1;

-- Create a savepoint
SAVEPOINT step_one_complete;

-- Step 2: Attempt an unstable update
UPDATE checking_accounts SET balance = balance + 50.00 WHERE account_id = 999; -- Account 999 does not exist!

-- Recovery: Rollback only the failed step 2
ROLLBACK TO SAVEPOINT step_one_complete;

-- The transaction is still open. Alice's debit is preserved. We finalize.
COMMIT;
```

---

## Summary
-   A **Transaction** is an "all-or-nothing" unit of database work.
-   **TCL (Transaction Control Language)** manages transaction execution boundaries.
-   **`COMMIT`** saves pending changes to disk; **`ROLLBACK`** discards all changes made since the transaction began.
-   **`SAVEPOINT`** allows you to roll back a specific portion of an active transaction block.

---

## Additional Resources
-   [PostgreSQL Transaction Documentation](https://www.postgresql.org/docs/current/tutorial-transactions.html)
-   [ACID Transactions Overview - GeeksforGeeks](https://www.geeksforgeeks.org/acid-properties-in-dbms/)
-   [PostgreSQL ROLLBACK Command Syntax](https://www.postgresql.org/docs/current/sql-rollback.html)
