# Database Stored Procedures in PL/pgSQL

## Learning Objectives
- Explain the purpose and business advantages of database stored procedures.
- Write stored procedures using PostgreSQL PL/pgSQL syntax.
- Configure input (IN), output (OUT), and bidirectional (INOUT) parameters in procedures.
- Execute stored procedures using the `CALL` statement.
- Describe how stored procedures support transactional control (COMMIT/ROLLBACK) within their execution blocks.

## Why This Matters
When designing database-driven applications, we often need to execute complex business workflows that involve multiple SQL updates, loops, and conditional checks. Implementing these workflows inside an application layer (such as Java or Node.js) requires multiple network round trips between the application server and the database, which increases latency and system overhead.

Stored procedures allow developers to group complex business logic and run it directly on the database server. Since the logic executes close to the data, network overhead is reduced and performance is improved. Crucially, unlike functions, stored procedures can manage transaction boundaries (COMMIT and ROLLBACK) internally, allowing developers to execute complex, multi-step transaction workflows inside a single procedure call.

## The Concept

### What is a Stored Procedure?
A stored procedure is a collection of SQL and control flow statements (such as loops and condition checks) that is stored in the database catalog and executed on demand. In PostgreSQL, procedures are typically written in PL/pgSQL (Procedural Language/Postgres SQL), which adds programming constructs to standard SQL.

### Key Characteristics of Stored Procedures:
- **Execution via CALL**: Procedures are executed using the `CALL` command (e.g., `CALL my_procedure(args)`), whereas functions are executed as part of a query (e.g., `SELECT my_function(args)`).
- **Transaction Control**: Stored procedures support explicit transaction control. You can execute `COMMIT` or `ROLLBACK` directly within the body of a procedure. This is not allowed inside database functions.
- **Parameters**: Stored procedures support various parameter modes:
  - **IN** (default): Input parameters passed into the procedure.
  - **OUT**: Output parameters used to return data back to the caller.
  - **INOUT**: A parameter that acts as both an input and an output.

### When to Use Stored Procedures
- **Multi-Step Transactions**: When you need to commit parts of a business workflow before proceeding (e.g., reserving inventory before attempting a payment authorization).
- **Network Reduction**: When an operation requires querying database data, analyzing it, and running conditional updates. Doing this inside the database avoids transferring large datasets back and forth to an application server.
- **Batch Processing**: Scheduling data archive tasks or overnight report aggregations.

## Code Examples

### Setting up the Schema
```sql
CREATE TABLE client_accounts (
    account_id INT PRIMARY KEY,
    owner_name VARCHAR(100) NOT NULL,
    balance DECIMAL(12, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

INSERT INTO client_accounts VALUES 
(1, 'Alice', 1000.00, 'ACTIVE'),
(2, 'Bob', 500.00, 'ACTIVE');
```

### Example 1: Basic Stored Procedure with Input Parameters
This procedure transfers funds between two client accounts.

```sql
CREATE OR REPLACE PROCEDURE pr_transfer_funds(
    sender_id INT,
    receiver_id INT,
    amount DECIMAL
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Deduct money from sender
    UPDATE client_accounts 
    SET balance = balance - amount 
    WHERE account_id = sender_id;

    -- Add money to receiver
    UPDATE client_accounts 
    SET balance = balance + amount 
    WHERE account_id = receiver_id;
    
    -- Changes are committed automatically upon procedure completion, 
    -- but we can also execute COMMIT explicitly if needed:
    COMMIT;
END;
$$;
```

To execute this procedure:

```sql
CALL pr_transfer_funds(1, 2, 150.00);

-- Verify the new balances
SELECT * FROM client_accounts;
```

### Example 2: Stored Procedure with OUT Parameters
Procedures can return values to the caller using OUT parameters.

```sql
CREATE OR REPLACE PROCEDURE pr_get_account_status(
    acct_id INT,
    OUT acct_owner VARCHAR,
    OUT acct_balance DECIMAL,
    OUT acct_status VARCHAR
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT owner_name, balance, status 
    INTO acct_owner, acct_balance, acct_status
    FROM client_accounts
    WHERE account_id = acct_id;
END;
$$;
```

To call this procedure and capture the output in a SQL client:

```sql
CALL pr_get_account_status(1, NULL, NULL, NULL);
```

### Example 3: Stored Procedure with Transaction Control
This procedure logs a transaction attempt. If the transfer fails due to insufficient funds, the procedure rolls back the transfer attempt but logs the failure inside a separate transaction state (or logs and rolls back to a safe state).

```sql
CREATE OR REPLACE PROCEDURE pr_safe_withdraw(
    acct_id INT,
    amount DECIMAL,
    OUT is_success BOOLEAN
)
LANGUAGE plpgsql
AS $$
DECLARE
    current_bal DECIMAL;
BEGIN
    -- Check balance
    SELECT balance INTO current_bal 
    FROM client_accounts 
    WHERE account_id = acct_id;

    IF current_bal < amount THEN
        is_success := FALSE;
        -- Rollback any active changes in the transaction block
        ROLLBACK;
    ELSE
        -- Perform withdrawal
        UPDATE client_accounts 
        SET balance = balance - amount 
        WHERE account_id = acct_id;
        
        is_success := TRUE;
        -- Commit changes permanently
        COMMIT;
    END IF;
END;
$$;
```

To execute the safe withdrawal:

```sql
CALL pr_safe_withdraw(2, 600.00, NULL); -- Should return is_success = false and rollback
CALL pr_safe_withdraw(2, 100.00, NULL); -- Should return is_success = true and commit
```

## Summary
- **Stored procedures** are compiled SQL blocks stored in the database catalog and executed using `CALL`.
- Unlike functions, procedures can control transactions explicitly using **COMMIT** and **ROLLBACK**.
- Stored procedures use **IN**, **OUT**, and **INOUT** parameters to accept inputs and return results.
- Use procedures to group complex multi-step data modifications, reduce network traffic, and centralize transaction logic.

## Additional Resources
- [PostgreSQL Documentation on CREATE PROCEDURE](https://www.postgresql.org/docs/current/sql-createprocedure.html)
- [PostgreSQL PL/pgSQL Declarations and Logic Control](https://www.postgresql.org/docs/current/plpgsql-declarations.html)
- [Stored Procedures in PostgreSQL - Tutorialspoint Guide](https://www.tutorialspoint.com/postgresql/postgresql_stored_procedures.htm)
