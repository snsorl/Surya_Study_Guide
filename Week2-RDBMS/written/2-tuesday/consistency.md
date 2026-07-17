# Data Consistency in Relational Databases

## Learning Objectives
- Define database consistency and explain its importance in enterprise applications.
- Identify the risks associated with database inconsistency.
- Explain how SQL constraints act as the primary mechanisms for enforcing database consistency.
- Differentiate between structural constraints (NOT NULL, UNIQUE) and validation constraints (CHECK, FOREIGN KEY).

---

## Why This Matters
Imagine running an online store where a customer completes a purchase. The application successfully processes the payment, but due to a software crash, it fails to record the order details. Or imagine a banking application that allows a user to withdraw money even when their account balance goes below zero, violating a strict business rule. 

These are examples of database inconsistency. If database data becomes corrupt, duplicate, or mathematically impossible, the entire business operation can grind to a halt.

While you can write validation checks in your Java application code (e.g., `if (balance < 0)`), this is not sufficient. In an enterprise system, multiple applications, analytical scripts, and administrators may edit the database directly. By defining consistency rules at the database engine level, you ensure that no invalid data can ever be written to the disk, regardless of where the edit command originates.

---

## The Concept

### 1. What is Consistency?
In relational databases, **consistency** guarantees that the database transitions from one valid state to another valid state, maintaining all structural and business rules at all times. 

A database is considered consistent if:
- All columns contain values matching their declared data types.
- Relational integrity is preserved (e.g., an order cannot refer to a customer ID that does not exist).
- Custom business logic constraints are upheld (e.g., stock levels must never be negative).

If an operation attempts to write data that violates any consistency rules, the RDBMS engine will **abort the transaction**, reject the write, and throw an error.

### 2. The Role of SQL Constraints
Constraints are rules defined on tables or columns that restrict the types of data that can be inserted, updated, or deleted. They act as automated "gatekeepers" at the database level.

The primary database constraints include:
-   **`NOT NULL`**: Ensures that a column must have a value and cannot be left blank.
-   **`UNIQUE`**: Guarantees that all values in a column are distinct (e.g., no two customers can have the same email address).
-   **`CHECK`**: Enforces custom conditions on data values (e.g., age must be greater than or equal to 18).
-   **`FOREIGN KEY`**: Maintains referential integrity, ensuring that a column's value corresponds to an existing record in another table.
-   **`PRIMARY KEY`**: A combination of `NOT NULL` and `UNIQUE`, uniquely identifying each row in a table.

---

## Code Example

Let's look at how database constraints enforce consistency. We will create a `bank_accounts` table with several constraints, then attempt to execute inserts that succeed and inserts that fail due to constraint violations.

### Defining the Schema with Constraints
```sql
CREATE TABLE bank_accounts (
    account_id INT PRIMARY KEY,
    owner_name VARCHAR(100) NOT NULL,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    balance DECIMAL(15, 2) CHECK (balance >= 0.00) -- Balance must never be negative
);
```

### Successful Insertion (Valid State)
This insert conforms to all rules:
- `account_id` is unique.
- `owner_name` and `account_number` are provided (not null).
- `account_number` is unique.
- `balance` is positive ($500.00), passing the CHECK constraint.

```sql
INSERT INTO bank_accounts (account_id, owner_name, account_number, balance)
VALUES (1, 'Alice Smith', 'ACC-9981-88', 500.00);
-- Result: SUCCESS
```

---

### Failed Insertion (Consistency Violations)

#### 1. Violating NOT NULL
This statement fails because `owner_name` is omitted:
```sql
INSERT INTO bank_accounts (account_id, account_number, balance)
VALUES (2, 'ACC-0021-99', 150.00);
-- Result: ERROR: null value in column "owner_name" violates not-null constraint
```

#### 2. Violating UNIQUE
This statement fails because the account number already belongs to Alice:
```sql
INSERT INTO bank_accounts (account_id, owner_name, account_number, balance)
VALUES (3, 'Bob Jones', 'ACC-9981-88', 250.00);
-- Result: ERROR: duplicate key value violates unique constraint "bank_accounts_account_number_key"
```

#### 3. Violating CHECK
This statement fails because the balance goes below zero:
```sql
INSERT INTO bank_accounts (account_id, owner_name, account_number, balance)
VALUES (4, 'Charlie Brown', 'ACC-1122-33', -50.00);
-- Result: ERROR: new row for relation "bank_accounts" violates check constraint "bank_accounts_balance_check"
```

---

## Summary
-   **Consistency** ensures the database stays in a mathematically and logically valid state.
-   **Database-level validation** is safer than application-level validation because it applies universally, regardless of the client tool used.
-   **Constraints** (NOT NULL, UNIQUE, CHECK, PRIMARY KEY, FOREIGN KEY) are SQL rules that reject any operations attempting to write invalid data.
-   If any constraint is violated, the database engine rolls back the entire write operation.

---

## Additional Resources
-   [PostgreSQL Constraints Documentation](https://www.postgresql.org/docs/current/ddl-constraints.html)
-   [What is ACID? - Consistency Explained](https://en.wikipedia.org/wiki/ACID)
-   [Designing Consistent Database Schemas](https://www.db-book.com/)
