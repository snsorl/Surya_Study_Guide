# PostgreSQL Data Types

## Learning Objectives
- Identify the primary categories of PostgreSQL data types (Numeric, Character, Temporal, and Boolean).
- Compare the storage and precision characteristics of numeric types (INT, DECIMAL/NUMERIC, FLOAT/REAL).
- Distinguish between character types (VARCHAR, CHAR, TEXT) and identify their appropriate use cases.
- Select correct temporal data types for managing dates, times, and time zones.
- Map standard Java types to their equivalent PostgreSQL database types.

---

## Why This Matters
When designing database schemas, choosing the correct data type for each column is a critical architectural decision. In Java, you might use a generic `double` for all fractional numbers and `String` for all text fields. However, in a relational database, data type selection directly affects:
-   **Storage Performance:** Storing a small number in a 64-bit integer instead of an 8-bit integer wastes server storage when scaled to millions of rows.
-   **Mathematical Precision:** Using floating-point values (`FLOAT`) for financial transactions leads to rounding errors. You must use exact decimal structures (`DECIMAL`) instead.
-   **Data Integrity:** If a column is defined as `DATE`, the database engine will reject invalid date values like "2026-02-30" or arbitrary text, preserving consistency.

Understanding SQL data types and how they map to Java variables allows you to build performant, bug-free, and clean database structures.

---

## The Concept

PostgreSQL supports a rich set of data types. Let's explore the core types you will use in full-stack applications.

### 1. Numeric Data Types
Numeric values are split into integer and fractional types:

-   **Integers (Whole Numbers):**
    -   `SMALLINT` (2 bytes): Range from -32,768 to 32,767. Useful for small status codes or age fields.
    -   `INTEGER` / `INT` (4 bytes): Range from -2.1 billion to +2.1 billion. The standard choice for IDs and counts.
    -   `BIGINT` (8 bytes): Range for extremely large integers (e.g., global transaction IDs).
-   **Fractional (Floating-Point vs. Exact Decimals):**
    -   `REAL` (4 bytes) / `DOUBLE PRECISION` (8 bytes): Inexact, variable-precision floating-point types. Matches Java's `float` and `double`. Excellent for scientific calculations or GPS coordinates where speed is preferred over absolute precision.
    -   `DECIMAL(p, s)` / `NUMERIC(p, s)`: Exact, user-specified precision numbers. You declare the total digits (**p**recision) and how many digits fall after the decimal point (**s**cale). For example, `DECIMAL(10, 2)` represents values up to `99,999,999.99`. **Always use DECIMAL/NUMERIC for currency and financial calculations.**

### 2. Character (Text) Data Types
Character types are used to store string data:

-   **`CHAR(n)` / `CHARACTER(n)`**: Fixed-length text. If the inserted string is shorter than `n`, PostgreSQL pads it with spaces. Useful for codes of static lengths (e.g., 2-character country codes or ISO currencies).
-   **`VARCHAR(n)` / `CHARACTER VARYING(n)`**: Variable-length text with a maximum limit `n`. If the string is shorter, it stores only the string characters without padding. Perfect for names, emails, and address fields.
-   **`TEXT`**: Variable-length text with unlimited storage length (up to 1 GB). Ideal for blog posts, description blocks, or raw JSON data.

### 3. Temporal (Date / Time) Data Types
Temporal types represent calendar values:

-   **`DATE`**: Stores a calendar date (Year, Month, Day) without a time components (e.g., `2026-07-12`).
-   **`TIME`**: Stores the time of day (Hour, Minute, Second) without a date.
-   **`TIMESTAMP`**: Stores both date and time (e.g., `2026-07-12 14:30:00`).
-   **`TIMESTAMP WITH TIME ZONE`** / `TIMESTAMPTZ`: Stores date, time, and timezone information. **This is the industry best practice** for enterprise applications because it prevents conversion issues when users or servers operate in different time zones.

### 4. Boolean Data Type
-   **`BOOLEAN`**: Stores logical states. Valid values are `TRUE`, `FALSE`, or `NULL`.

---

## Mapping Java to PostgreSQL

When writing data access code (JDBC), you must map Java variable types to the database schema:

| Java Data Type | PostgreSQL Data Type | Example Mapping |
|---|---|---|
| `int` / `Integer` | `INTEGER` | User age, count of views |
| `long` / `Long` | `BIGINT` | Primary keys, large order numbers |
| `double` / `Double` | `DOUBLE PRECISION` | Latitude, sensor readings |
| `BigDecimal` | `DECIMAL` / `NUMERIC` | Prices, interest rates, balances |
| `String` | `VARCHAR` / `TEXT` / `CHAR` | Names, long description text, codes |
| `boolean` / `Boolean` | `BOOLEAN` | `is_active`, `has_shipped` |
| `LocalDate` | `DATE` | Date of birth, hire date |
| `Instant` / `ZonedDateTime` | `TIMESTAMPTZ` | Created timestamp, audit log times |

---

## Code Example

Here is a SQL table definition illustrating these data types in a practical system:

```sql
CREATE TABLE product_catalog (
    product_id BIGINT PRIMARY KEY,
    product_code CHAR(5) NOT NULL,            -- e.g., 'PROD1'
    name VARCHAR(100) NOT NULL,               -- e.g., 'Wireless Mouse'
    description TEXT,                         -- Long descriptions
    price DECIMAL(10, 2) NOT NULL,            -- e.g., 29.99
    weight_kg DOUBLE PRECISION,               -- e.g., 0.1543
    in_stock BOOLEAN DEFAULT TRUE,            -- true/false
    added_at TIMESTAMPTZ DEFAULT NOW()        -- Date & time with zone
);
```

---

## Summary
-   **Numeric types** are split into integers, inexact floats (`DOUBLE PRECISION`), and exact decimals (`DECIMAL`). Always use `DECIMAL` for currency.
-   **Character types** include fixed `CHAR(n)`, variable `VARCHAR(n)`, and unlimited `TEXT`.
-   **Temporal types** manage dates and times; `TIMESTAMPTZ` is preferred for enterprise configurations.
-   **Java types** must match the database types (e.g., `BigDecimal` maps to `DECIMAL`).

---

## Additional Resources
-   [PostgreSQL Official Data Types Documentation](https://www.postgresql.org/docs/current/datatype.html)
-   [Baeldung: Guide to SQL Data Types](https://www.baeldung.com/sql-data-types)
-   [Java JDBC Type Mapping Standards](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/thrift.html)
