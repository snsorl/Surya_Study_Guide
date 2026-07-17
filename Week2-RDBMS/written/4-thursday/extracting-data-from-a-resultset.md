# Extracting Data from a ResultSet

## Learning Objectives
- Describe the cursor mechanics of a JDBC `ResultSet`.
- Implement loops using `next()` to safely traverse query output rows.
- Extract typed database values using appropriate `ResultSet` getter methods (`getInt()`, `getString()`, `getDate()`).
- Compare column index access with column name access, evaluating their maintainability.
- Utilize `wasNull()` to handle database `NULL` values when mapping to Java primitives.

---

## Why This Matters
When you run a `SELECT` query in Java, the database returns a virtual table of rows. However, your Java application cannot use a database row directly: you cannot pass a raw SQL row to a REST controller or display it on a frontend web page. 

You must translate those database rows into standard Java objects (e.g., converting an order row into a `new Order()` instance).

This translation process—known as **object-relational mapping**—is handled in JDBC using the **`ResultSet`**. If you write incorrect mapping logic (such as reading the wrong column data types, using brittle column numbers that change when the SQL schema is modified, or failing to handle `NULL` database values which cause silent defaults in Java primitives), your application will display corrupted data or throw mapping exceptions.

---

## The Concept

### 1. The ResultSet Cursor
A `ResultSet` object maintains a virtual cursor pointing to its current row of data. 

-   **Starting Position:** When first returned, the cursor is positioned **before the first row**.
-   **Traversing Rows:** You call the **`next()`** method to move the cursor forward by one row. `next()` returns a boolean: `true` if there is a valid row to read, and `false` if you have reached the end of the results.
-   **The Read Loop:** Because of this, you traverse a multi-row query using a standard `while(rs.next())` loop.

### 2. Typed Getter Methods
To extract values from the current row, you use data-type-specific getters:
-   `getString(column)`: For character types (`VARCHAR`, `TEXT`).
-   `getInt(column)` / `getLong(column)`: For numeric integers.
-   `getDouble(column)` / `getBigDecimal(column)`: For decimals.
-   `getDate(column)` / `getTimestamp(column)`: For temporal types.

### 3. Column Index vs. Column Name Access
To specify which column you want to read, getters accept either a column number or a column name:

-   **Column Index (1-Indexed):** `rs.getString(1)` retrieves the first column listed in your query's `SELECT` statement.
    -   *Pros:* Slightly faster execution because the driver does not have to lookup the column name map.
    -   *Cons:* Highly brittle. If a developer edits the SQL query to add a column, all column index numbers shift, causing your Java code to read the wrong fields and crash.
-   **Column Name (Or Alias):** `rs.getString("email")` retrieves the column by name.
    -   *Pros:* Extremely readable and maintainable. The select order in SQL does not impact the Java mapping code.
    -   *Cons:* Marginally slower lookup, though negligible in modern applications.

**Enterprise Standard:** Always retrieve values using **Column Names** (or aliases).

### 4. Handling NULLs in Java Primitives
Java primitives (like `int` or `double`) cannot hold `null`. If a database column contains `NULL` and you call `rs.getInt("age")`, JDBC returns a default `0` in Java. This is a severe silent bug: you cannot distinguish between a user whose age is actually `0` and a user whose age is `NULL` (unknown).

To solve this, immediately after reading a primitive, you must call **`rs.wasNull()`**. This method checks if the last column read was database `NULL`. If true, you can assign `null` using Java wrapper classes (like `Integer`).

---

## Code Example: Safe Data Mapping

Below is a Java method illustrating cursor traversal, name-based extraction, and `NULL` primitive handling.

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper {

    public void mapActiveCustomers(Connection conn) {
        String sql = "SELECT customer_id, name, age, registration_date FROM customers WHERE is_deleted = false";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Traverse the cursor row-by-row
            while (rs.next()) {
                // 1. Column Name Access (Clean & Maintainable)
                int id = rs.getInt("customer_id");
                String name = rs.getString("name");
                
                // 2. Handling Primitive NULLs using wasNull()
                // We read the primitive first
                int agePrimitive = rs.getInt("age");
                Integer age = null;
                
                // Check if the database cell was actually NULL
                if (!rs.wasNull()) {
                    age = agePrimitive; // Only assign value if it wasn't NULL
                }

                // 3. Extracting Dates
                java.sql.Date regDate = rs.getDate("registration_date");

                // Print mapped data
                System.out.println(String.format(
                    "Customer: [ID: %d, Name: %s, Age: %s, Registered: %s]",
                    id, name, (age != null ? age : "N/A"), regDate
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Summary
-   A **`ResultSet`** uses a virtual cursor positioned initially *before* the first row; advance it using **`next()`**.
-   Extract data using typed getters (`getString()`, `getInt()`).
-   Use **Column Names** rather than Column Indices (1-indexed numbers) to preserve code maintainability.
-   Use **`wasNull()`** to safely map database `NULL` cells to Java wrapper objects (`Integer`, `Double`), preventing default primitive value bugs.

---

## Additional Resources
-   [Oracle: Retrieving and Modifying Values from Result Sets](https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html)
-   [Baeldung: Handling SQL Nulls in Java JDBC](https://www.baeldung.com/jdbc-handling-sql-nulls)
-   [JDBC ResultSet Java API Specifications](https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html)
