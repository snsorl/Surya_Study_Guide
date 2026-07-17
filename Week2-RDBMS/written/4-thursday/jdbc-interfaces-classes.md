# Core JDBC Interfaces and Classes

## Learning Objectives
- Describe the purpose and responsibilities of the five core JDBC components (`DriverManager`, `Connection`, `Statement`, `PreparedStatement`, `ResultSet`).
- Analyze the lifecycle of a JDBC database interaction session.
- Implement resource cleanup patterns using Java's Try-With-Resources block.
- Explain the performance advantages of `PreparedStatement` lifecycle caching.

---

## Why This Matters
When writing Java code that interacts with a database, you are managing **expensive resources**. Instantiating network connections to PostgreSQL and keeping transaction statements open consumes server memory, thread counts, and network sockets.

If you fail to manage the lifecycle of these objects—such as leaving a connection open after retrieving data—you will create a **Connection Leak**. As users interact with your web application, connections will pile up until the PostgreSQL server runs out of sockets and refuses to connect, causing your app to crash.

Understanding the purpose and lifecycle of the core JDBC classes and interfaces is essential to writing performant, leak-free, and secure Java data access layers.

---

## The Concept

Let's explore the roles and lifecycles of the five core JDBC components.

### 1. The Core Components

#### A. DriverManager (Class)
The starting point. It acts as the driver manager registry.
-   *Purpose:* Parses your database connection string and loads the matching driver to return a `Connection` instance.
-   *Lifecycle:* Used once at session start; has no persistent lifecycle.

#### B. Connection (Interface)
Represents the active network session/pipeline to the database.
-   *Purpose:* Used to create statement objects, set transaction boundaries (`setAutoCommit()`), and execute commits or rollbacks.
-   *Lifecycle:* Opened at task start, shared across active statements, and **must be closed immediately** when the task is complete.

#### C. Statement (Interface)
The container used to execute static SQL.
-   *Purpose:* Sends static SQL query strings to the database.
-   *Lifecycle:* Created from a connection, used once, and closed immediately after query execution.

#### D. PreparedStatement (Interface)
An advanced, pre-compiled statement (Extends `Statement`).
-   *Purpose:* The database parses, compiles, and optimizes the SQL template *before* you run it. You then bind parameters (values) to the placeholders (`?`). **Always use PreparedStatement in enterprise environments** to prevent SQL injection and speed up repeated inserts/queries.
-   *Lifecycle:* Created from a connection, executed multiple times with different parameters, and closed.

#### E. ResultSet (Interface)
A cursor pointing to the query output rows.
-   *Purpose:* Iterates through response rows returned by a query. You extract values using data-type methods (e.g., `getString()`, `getInt()`).
-   *Lifecycle:* Active only while the parent `Connection` and `Statement` remain open. It is closed automatically when the statement is closed.

---

## The JDBC Lifecycle Flow

```
[DriverManager]
       | (getConnection)
       v
  [Connection]
       | (prepareStatement)
       v
[PreparedStatement]
       | (executeQuery)
       v
   [ResultSet] ---------> (Iterate and map rows)
       |
       v
[Try-With-Resources] ---> Automatically closes: ResultSet -> PreparedStatement -> Connection
```

---

## Code Example: Safe Lifecycle Execution

Here is a practical Java method showing how to query a database safely. We use Java's **Try-With-Resources** block, which guarantees that all open resources are closed automatically, preventing connection leaks even if the query throws a runtime error.

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeQueryService {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "SecurePass99!";

    public void printEmployeeSalaries(double minSalary) {
        String sql = "SELECT first_name, salary FROM employees WHERE salary > ? ORDER BY salary DESC";

        // Try-With-Resources: Automatically closes resources in reverse order of declaration
        try (
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Bind parameters (1-indexed matching '?')
            stmt.setDouble(1, minSalary);

            // Execute query and retrieve ResultSet cursor
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Extract data by column name
                    String name = rs.getString("first_name");
                    double salary = rs.getDouble("salary");
                    
                    System.out.println("Employee: " + name + " | Salary: $" + salary);
                }
            } // rs is closed here automatically
            
        } catch (SQLException e) {
            System.err.println("Database operation failed: " + e.getMessage());
            e.printStackTrace();
        } // stmt and conn are closed here automatically
    }
}
```

---

## Summary
-   **`DriverManager`** opens database connections; **`Connection`** manages transaction sessions.
-   **`PreparedStatement`** compiles SQL templates and binds parameters, preventing SQL injection.
-   **`ResultSet`** acts as a data cursor to iterate over response rows.
-   Use Java's **Try-With-Resources** block to handle JDBC resource closures automatically, preventing memory leaks and connection socket exhaustions.

---

## Additional Resources
-   [Oracle: Processing SQL Statements with JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html)
-   [Java Try-With-Resources Documentation](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
-   [Baeldung: Guide to JDBC ResultSet](https://www.baeldung.com/java-jdbc-resultset)
