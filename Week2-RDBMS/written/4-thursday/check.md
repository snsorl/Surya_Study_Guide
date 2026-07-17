# CHECK Constraints

## Learning Objectives
- Define the purpose and mechanics of a `CHECK` constraint in SQL databases.
- Construct boolean expression-based validation rules.
- Identify common validation patterns (numeric boundaries, date ranges, text formats).
- Implement string pattern matching checks using regular expressions (`~`) in PostgreSQL.

---

## Why This Matters
When building applications, validating user inputs is a core security concern. In Java, you might use validation annotations like `@Min` or write conditional blocks to reject bad data. However, what happens if a database administrator manually runs a script, or if a backend bug bypasses your Java code validations?

If the database accepts bad data, you introduce silent corruption: a user with age `-5`, a discount rate of `200%`, or a customer with an invalid email address format.

The **`CHECK` constraint** represents the database engine's validation guard. By defining expression-based logical rules directly in your table schemas, you guarantee that no database write operation can ever store values that violate your business rules, maintaining data consistency.

---

## The Concept

### 1. What is a CHECK Constraint?
A **`CHECK` constraint** is a validation rule defined on a table that checks row values against a boolean expression. 

When a DML operation (`INSERT` or `UPDATE`) attempts to write data:
1.  The database engine evaluates the column values against the check expression.
2.  If the expression evaluates to `TRUE` (or `NULL`), the write succeeds.
3.  If the expression evaluates to `FALSE`, the transaction aborts, the write is rejected, and the database throws an error.

### 2. Common CHECK Patterns

-   **Numeric Ranges:** Ensuring values fall within valid boundaries (e.g., `balance >= 0.00`, `percent_discount BETWEEN 0 AND 100`).
-   **String Lengths:** Preventing empty string bypasses (e.g., `LENGTH(TRIM(username)) >= 3`).
-   **Enumerated Lists:** Restricting values to a pre-defined set of options (e.g., `status IN ('PENDING', 'APPROVED', 'SHIPPED')`).
-   **Format Validation (Regex):** Enforcing text patterns (such as emails or zip codes) using regular expressions. In PostgreSQL, the `~` operator is used to perform case-sensitive regex pattern matching.

---

## Code Example: Validating a User Schema

Let's write a SQL schema that secures user registration data using multiple check constraints.

### Schema Definition
```sql
CREATE TABLE accounts_registry (
    account_id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    age INT,
    account_status VARCHAR(20) DEFAULT 'PENDING',
    email VARCHAR(100) NOT NULL,
    
    -- Constraint 1: Username must not be blank or too short
    CONSTRAINT chk_username_length CHECK (LENGTH(TRIM(username)) >= 4),
    
    -- Constraint 2: Age must be a reasonable positive number
    CONSTRAINT chk_valid_age CHECK (age >= 18 AND age <= 120),
    
    -- Constraint 3: Status must match specific categories (Enum simulation)
    CONSTRAINT chk_status_categories CHECK (account_status IN ('PENDING', 'ACTIVE', 'SUSPENDED')),
    
    -- Constraint 4: Email must match standard format using regex (PostgreSQL ~ operator)
    -- This checks for: non-empty characters + '@' + non-empty characters + '.' + 2-4 alphabet characters
    CONSTRAINT chk_email_format CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')
);
```

---

### Verifying the Constraints

Let's test our validations:

#### 1. Successful Insertion (All Constraints Pass)
```sql
INSERT INTO accounts_registry (account_id, username, age, email)
VALUES (1, 'john_doe', 25, 'john.doe@email.com');
-- Result: SUCCESS
```

---

#### 2. Failed Insertion (Age Boundary Violation)
```sql
INSERT INTO accounts_registry (account_id, username, age, email)
VALUES (2, 'sara_smith', 17, 'sara@email.com'); -- 17 is under the minimum age limit of 18
-- Result: ERROR: new row for relation "accounts_registry" violates check constraint "chk_valid_age"
```

---

#### 3. Failed Insertion (Invalid Email Format Violation)
```sql
INSERT INTO accounts_registry (account_id, username, age, email)
VALUES (3, 'alex_jones', 32, 'alex_jones_email_dot_com'); -- Missing '@' and '.'
-- Result: ERROR: new row for relation "accounts_registry" violates check constraint "chk_email_format"
```

---

## Summary
-   A **`CHECK` constraint** evaluates table row data against a logical boolean expression.
-   If the expression evaluates to `FALSE`, the database rejects the write and rolls back the operation.
-   Common validations include **numeric ranges, string limits, enum lists, and text regex formats**.
-   In PostgreSQL, use the **`~`** operator within a CHECK constraint to run regular expression format verifications.

---

## Additional Resources
-   [PostgreSQL CHECK Constraints Documentation](https://www.postgresql.org/docs/current/ddl-constraints.html#DDL-CONSTRAINTS-CHECK-CONSTRAINTS)
-   [PostgreSQL Regular Expression Syntax Guide](https://www.postgresql.org/docs/current/functions-matching.html)
-   [Baeldung: Guide to SQL CHECK Constraint](https://www.baeldung.com/sql-check-constraint)
