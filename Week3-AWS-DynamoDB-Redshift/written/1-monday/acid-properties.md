# The ACID Properties of Transactions

## Learning Objectives
- Explain the meaning and importance of the ACID acronym: Atomicity, Consistency, Isolation, and Durability.
- Illustrate how each ACID property is applied in a real-world financial transaction.
- Describe the database mechanisms that support and enforce ACID compliance.
- Identify the consequences of violating any of the ACID principles in business applications.

## Why This Matters
When building enterprise-grade applications, developers make assumptions about how the database behaves. We assume that data is saved permanently once a confirm action occurs, that concurrent updates do not overwrite each other, and that systems recover correctly from hardware failures. 

These assumptions are guaranteed by a set of database standards known as ACID properties. As we transition from localized SQL databases to cloud-scale data storage (like Amazon DynamoDB and Redshift), understanding the strict guarantees of ACID helps us evaluate the design tradeoffs of NoSQL, data lakes, and distributed transactions. If a database is not ACID-compliant, application developers must write additional complex code to handle data corruption, incomplete operations, and synchronization issues.

## The Concept

ACID is an acronym representing the four primary properties that guarantee database transactions are processed reliably:

### 1. Atomicity ("All or Nothing")
Atomicity guarantees that a transaction is treated as a single, indivisible unit of work. Either all modifications within the transaction are completed successfully, or none of them are. If any statement fails, the database aborts the transaction and rolls back all changes made by that transaction to its starting state.

*Mechanism*: The database uses a Write-Ahead Log (WAL) or undo/redo logs to track changes. If a transaction fails mid-way, the database uses these logs to reverse the operations that were already completed.

### 2. Consistency ("Valid State to Valid State")
Consistency ensures that a transaction can only transition the database from one valid state to another, maintaining all schema rules, constraints, and business logic. Any data written to the database must conform to all defined rules, including data types, unique constraints, primary keys, foreign keys, and check constraints. If a transaction attempts to write invalid data, the entire transaction is rejected.

*Mechanism*: The database engine validates integrity constraints (e.g., `NOT NULL`, `FOREIGN KEY`, `CHECK`) during transaction execution. If any constraint is violated, the transaction fails and is rolled back.

### 3. Isolation ("Independent Execution")
Isolation ensures that concurrent execution of transactions leaves the database in the same state that would be obtained if the transactions were executed sequentially. Changes made by a transaction are not visible to other concurrent transactions until the transaction has been committed. This prevents transactions from reading partial, uncommitted data.

*Mechanism*: Databases enforce isolation through locking mechanisms (read/write locks on rows or tables) and Multi-Version Concurrency Control (MVCC), which presents a snapshot of the data to each transaction.

### 4. Durability ("Permanent and Surviving")
Durability guarantees that once a transaction is committed, its changes are permanently recorded in non-volatile storage (disk) and will survive any subsequent system crash, power outage, or database failure. 

*Mechanism*: To ensure durability without sacrificing performance, databases write transaction logs to disk before updating the actual database tables. This is known as write-ahead logging. If the system crashes, the recovery manager reads the WAL on startup to re-apply (redo) committed transactions that had not yet been written to the main data files.

---

### Real-World Bank Transfer Scenario
To understand ACID properties, let us analyze a standard bank transfer of $100.00 from Alice's account (Account A) to Bob's account (Account B).

#### Step-by-Step Flow
1. Verify Alice has sufficient funds (balance >= $100.00).
2. Deduct $100.00 from Account A.
3. Add $100.00 to Account B.
4. Record an audit log entry for the transfer.

#### How ACID Guarantees Safety:

- **Atomicity**: If the system crashes after deducting $100.00 from Account A but before adding it to Account B, the $100.00 disappears. Atomicity guarantees that if the transfer fails at step 3, the deduction in step 2 is rolled back. Alice's money is restored.
- **Consistency**: Suppose the database has a check constraint: `balance >= 0.00`. If Alice has $50.00 and tries to transfer $100.00, the update in step 2 violates the constraint. Consistency ensures the database rejects the change and rolls back the transaction. The database never enters a state where an account has a negative balance.
- **Isolation**: Suppose Alice transfers $100.00 to Bob. Simultaneously, Alice's landlord checks Alice's balance to process a rent payment. Isolation ensures that the landlord's query either sees Alice's balance *before* the transfer ($100.00 higher) or *after* the transfer ($100.00 lower), but never a temporary state where the money has been deducted from Alice but not yet added to Bob.
- **Durability**: Once the bank transfer transaction completes and the system displays "Transfer Successful", the database writes the transaction to the WAL on disk. If the database server loses power a millisecond later, the transfer remains saved. When the server restarts, Alice's balance remains deducted, and Bob's balance remains credited.

## Code Examples

The following SQL script demonstrates how these rules are enforced in PostgreSQL.

### Creating the Environment with Constraints (Consistency)
```sql
-- Creating tables with constraints to enforce consistency
CREATE TABLE bank_accounts (
    account_id INT PRIMARY KEY,
    owner_name VARCHAR(100) NOT NULL,
    balance DECIMAL(12, 2) NOT NULL CHECK (balance >= 0.00)
);

-- Seed Initial Data
INSERT INTO bank_accounts (account_id, owner_name, balance) VALUES 
(1, 'Alice', 500.00),
(2, 'Bob', 150.00);
```

### Executing a Transaction with Integrity Checks
```sql
BEGIN;

-- Deducting from Alice's account
UPDATE bank_accounts 
SET balance = balance - 100.00 
WHERE account_id = 1;

-- Crediting Bob's account
UPDATE bank_accounts 
SET balance = balance + 100.00 
WHERE account_id = 2;

-- If both operations succeed, save permanently (Durability & Atomicity)
COMMIT;
```

### Demonstrating a Consistency Constraint Failure
If we attempt to transfer more money than Alice has, the check constraint will trigger a failure.

```sql
BEGIN;

-- Alice attempts to transfer $600.00 (but only has $400.00 remaining)
UPDATE bank_accounts 
SET balance = balance - 600.00 
WHERE account_id = 1; 
-- ERROR: new row for relation "bank_accounts" violates check constraint "bank_accounts_balance_check"

-- Because of the consistency violation, the transaction is poisoned. 
-- Any subsequent statements in this block will be ignored.
UPDATE bank_accounts 
SET balance = balance + 600.00 
WHERE account_id = 2;

-- We must rollback to restore the system to a clean state
ROLLBACK;
```

## Summary
- **Atomicity** ensures all statements in a transaction succeed, or all are rolled back.
- **Consistency** ensures that data adheres to all schema constraints and database rules before and after a transaction.
- **Isolation** prevents concurrent transactions from seeing each other's partial or uncommitted modifications.
- **Durability** guarantees that committed changes are saved permanently, surviving system crashes.

## Additional Resources
- [MDN Web Docs - ACID (Atomicity, Consistency, Isolation, Durability)](https://developer.mozilla.org/en-US/docs/Glossary/ACID)
- [IBM Documentation - Database Transaction Properties (ACID)](https://www.ibm.com/docs/en/cics-ts/6.1?topic=processing-acid-properties-transaction)
- [Designing Data-Intensive Applications - Transaction Chapter by Martin Kleppmann](https://www.oreilly.com/library/view/designing-data-intensive-applications/9781491903063/)
