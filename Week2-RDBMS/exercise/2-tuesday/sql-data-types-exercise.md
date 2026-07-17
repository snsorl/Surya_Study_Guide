# Lab: SQL Data Types Mapping

## Learning Objectives
- Map application properties to PostgreSQL system data types.
- Construct schemas featuring correct numeric scale and precision parameters.
- Apply data integrity filters using explicit text formats.
- Differentiate between fixed, variable, and temporal data type parameters.

---

## Setup Instructions
1. Open DBeaver and connect to your local PostgreSQL instance.
2. Open a new SQL Console editor.
3. Keep the PostgreSQL Data Types reference guide open as a sidebar.

---

## Step-by-Step Tasks

### Task 1: Analyze the Column Specifications
You are asked to create a table named `customers`. Evaluate the properties below and match them with the most appropriate PostgreSQL data type:

1.  **`customer_id`**: A unique whole number identifier (Primary Key).
2.  **`full_name`**: A text field. Maximum length should be 100 characters. Must not be empty.
3.  **`email_address`**: A text field. Maximum length 100 characters. Must be unique.
4.  **`age`**: A positive whole number. Choose a space-efficient type.
5.  **`credit_balance`**: Stores currency values (e.g. `$10,500.25`). Must protect against floating-point rounding errors.
6.  **`registered_at`**: Stores the date and the exact time the customer registered, including timezone information.

---

### Task 2: Write and Run the DDL script
Write the SQL DDL statement to create the table based on your analysis.

```sql
-- Write your CREATE TABLE query here
-- Ensure you use exact types (e.g. avoid FLOAT or DOUBLE for credit_balance)
```

Run the statement inside your DBeaver console. Review the output log to verify execution success.

---

### Task 3: Test and Verify Columns Types
Run a diagnostic schema lookup query to inspect the table structure on PostgreSQL:

```sql
SELECT column_name, data_type, character_maximum_length
FROM information_schema.columns
WHERE table_name = 'customers';
```
Verify that the output data types match your original design specifications.

---

## Definition of Done
-   A saved DDL script containing the complete, executable `CREATE TABLE customers` statement.
-   The table is successfully created on your local database.
-   `credit_balance` uses exact numeric scale types (`DECIMAL` or `NUMERIC`), and `registered_at` uses time zone temporal types (`TIMESTAMPTZ` or `TIMESTAMP WITH TIME ZONE`).
