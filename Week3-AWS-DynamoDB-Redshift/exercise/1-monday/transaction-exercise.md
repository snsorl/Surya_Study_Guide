# Exercise: SQL Transactions and Atomicity

**Exercise Mode:** Mode A: Implementation (Code Lab)
**Target Engine:** PostgreSQL

---

## Scenario
You are building the transaction processing module for the City Bank core banking engine. When a customer initiates a fund transfer, the operation involves two steps:
1. Deducting the amount from the sender's account.
2. Adding the amount to the receiver's account.

If the sender has insufficient funds, the transaction must abort completely, and the database state must roll back. No money should be created or lost.

---

## Part A: Setup and Seed Data
Create a database table named `bank_accounts` and insert the initial test records:

```sql
DROP TABLE IF EXISTS bank_accounts CASCADE;

CREATE TABLE bank_accounts (
    account_id INT PRIMARY KEY,
    owner_name VARCHAR(100) NOT NULL,
    balance DECIMAL(12, 2) NOT NULL,
    CONSTRAINT chk_positive_balance CHECK (balance >= 0.00)
);

INSERT INTO bank_accounts (account_id, owner_name, balance) VALUES 
(101, 'David Miller', 500.00),
(102, 'Emily Davis', 150.00);
```

---

## Part B: Tasks

### Task 1: Basic Successful Transfer
Write an explicit SQL transaction block (`BEGIN` ... `COMMIT`) that transfers **$100.00** from David Miller (101) to Emily Davis (102). Verify the balances before and after using a `SELECT` query.

### Task 2: Failed Transfer (Insufficient Funds)
Write an explicit SQL transaction block that attempts to transfer **$600.00** from David Miller (101) to Emily Davis (102). 
- Observe how the database's check constraint `chk_positive_balance` behaves.
- Run a `ROLLBACK` to discard the changes when the update fails.
- Verify that David's and Emily's balances are unaffected and remain at their post-Task 1 values.

### Task 3: Savepoint Checkpoints
David Miller wants to open a savings vault and transfer money to it. Write a transaction block that:
1. Creates a new account record for David's Vault:
   ```sql
   INSERT INTO bank_accounts (account_id, owner_name, balance) VALUES (103, 'David Vault', 0.00);
   ```
2. Sets a `SAVEPOINT` named `vault_created`.
3. Attempts to transfer **$300.00** from David's main account (101) to David's Vault (103).
4. Realizes that David wants to hold off on the transfer, and issues a `ROLLBACK TO SAVEPOINT vault_created`.
5. Commits the transaction.
6. Verify that the vault account (103) exists with a balance of $0.00, and David's main account (101) is unchanged.

---

## Deliverables
Save your SQL query statements for Tasks 1, 2, and 3 in a file named `transfer_exercise.sql`. Ensure you write explanations/comments for each step.
