# SQL Constraints: Column-Level vs. Table-Level

## Learning Objectives
- Review the six primary database constraints (NOT NULL, UNIQUE, CHECK, DEFAULT, PRIMARY KEY, FOREIGN KEY).
- Explain the syntax differences between column-level and table-level constraint declarations.
- Identify which constraints *must* be declared at the table level.
- Utilize explicit constraint naming conventions to simplify database diagnostics.

---

## Why This Matters
On Tuesday, we introduced constraints as the gatekeepers of database consistency. By declaring rules like `NOT NULL` or `CHECK`, you force PostgreSQL to reject invalid data. 

However, as you build complex tables, you will need to apply rules that span multiple columns. For example, a table tracking flights might need a rule stating: *"the return_date must be after the departure_date"*. If you try to declare this rule on a single column, the database engine will reject your code.

To solve this, database engines support two distinct syntax structures: **Column-Level** and **Table-Level** declarations. Knowing how and when to use both allows you to build clean, descriptive constraints, customize constraint names for easier error logs, and implement multi-column validations.

---

## The Concept

### 1. Constraint Summary Review
-   **`NOT NULL`**: Prevents empty cell values in a column.
-   **`UNIQUE`**: Guarantees distinct values in a column.
-   **`CHECK`**: Evaluates row values against a boolean logic expression.
-   **`DEFAULT`**: Automatically supplies a placeholder value if the insert leaves the column blank.
-   **`PRIMARY KEY`**: Defines the unique row identity.
-   **`FOREIGN KEY`**: Preserves relational links to parent tables.

### 2. Column-Level vs. Table-Level Syntax
When writing DDL, you can declare constraints in two ways:

#### A. Column-Level Syntax
Constraints are declared inline, immediately following the column's data type definition.
-   *Best Use:* Simple, single-column rules (like `NOT NULL` or basic primary keys).
-   *Restriction:* You cannot write composite (multi-column) rules inline.
-   *Default Names:* The database engine generates generic names (e.g., `sys_c00714`), making error logs cryptic.

#### B. Table-Level Syntax
Constraints are declared at the bottom of the table definition, separated from the columns.
-   *Best Use:* Multi-column validations (composite primary keys, foreign keys, or complex CHECK expressions).
-   *Explicit Naming:* Allows you to name constraints (e.g., `CONSTRAINT uq_user_email UNIQUE (email)`). If a user inserts a duplicate email, the error log explicitly reports: *"violates unique constraint 'uq_user_email'"*, making it easy to debug.
-   *Note:* **`NOT NULL` is the only constraint that cannot be declared at the table level.** It must always be written inline on the target column.

---

## Code Example: Column-Level vs. Table-Level

### Example 1: Column-Level Declaration (Simple)
Here, the constraints are defined directly on the column definitions.

```sql
CREATE TABLE column_tickets (
    ticket_id INT PRIMARY KEY,                         -- Primary Key
    passenger_name VARCHAR(100) NOT NULL,              -- Not Null
    flight_number CHAR(6) UNIQUE,                      -- Unique
    price DECIMAL(10, 2) DEFAULT 0.00 CHECK (price >= 0.00) -- Default & Check
);
```

---

### Example 2: Table-Level Declaration (Enterprise Practice)
Here, we separate the constraints and apply custom names and multi-column rules.

```sql
CREATE TABLE table_tickets (
    -- Column Types only
    ticket_id INT,
    passenger_name VARCHAR(100) NOT NULL,              -- NOT NULL must remain inline
    departure_date DATE NOT NULL,
    return_date DATE NOT NULL,
    flight_number CHAR(6),
    price DECIMAL(10, 2) DEFAULT 0.00,
    
    -- Table-Level Constraint Definitions
    CONSTRAINT pk_tickets PRIMARY KEY (ticket_id),
    
    CONSTRAINT uq_tickets_flight UNIQUE (flight_number),
    
    -- Multi-column validation CHECK (Cannot be done inline)
    CONSTRAINT chk_ticket_travel_dates CHECK (return_date >= departure_date),
    
    CONSTRAINT chk_ticket_price CHECK (price >= 0.00)
);
```

### Analyzing the Table-Level Advantages:
1.  **Named Constraints:** If a user registers a return date that happens before their departure date, PostgreSQL throws:
    `ERROR: new row violates check constraint "chk_ticket_travel_dates"`
    The developer immediately knows which validation logic failed.
2.  **Multi-Column Logic:** The `chk_ticket_travel_dates` constraint evaluates both `departure_date` and `return_date` simultaneously, which is impossible using column-level DDL.

---

## Summary
-   **Column-Level constraints** are declared inline with the column. They are simple but generate cryptic system-defined names.
-   **Table-Level constraints** are declared at the bottom of the table. They support **explicit naming** and **multi-column logic**.
-   **`NOT NULL`** is the only constraint that must always be declared inline (column-level).
-   Use table-level naming to make database error diagnostics readable in your backend application logs.

---

## Additional Resources
-   [PostgreSQL Column-Level vs Table-Level Constraints](https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-constraints/)
-   [W3Schools: SQL Constraints Overview](https://www.w3schools.com/sql/sql_constraints.asp)
-   [Database Naming Conventions and Best Practices](https://launchschool.com/books/sql/read/defining_constraints)
