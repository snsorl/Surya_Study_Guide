# Handling Exceptions: Try-Catch-Finally

## Learning Objectives
- Implement robust exception handling using the `try`, `catch`, and `finally` structural blocks.
- Catch multiple distinct exceptions using the multi-catch parameter syntax (`|`).
- Explain why the order of catch blocks must move from subclass (specific) to superclass (generic) exceptions.
- Manage system resources safely using the **Try-with-Resources** (`AutoCloseable`) pattern.

---

## Why This Matters
When your Java program crashes during a network connection or a file read, it throws an exception. If you do not handle this exception, the JVM shuts down. 

To build production-grade backends (like an API server), you must intercept these failures using **`try-catch`** blocks, log the errors, and present a helpful message to the user rather than letting the server crash. Furthermore, accessing files, database connections, or network sockets opens system handles (file descriptors). If your code fails mid-process and bypasses the code that closes these handles, they remain open in memory, causing a resource leak that will eventually crash the server. Mastering try-catch blocks and automated resource management (**Try-with-Resources**) is vital to building stable, high-availability software.

---

## The Concept

### The Try-Catch-Finally Architecture
Java handles exception routing using three block components:

*   **`try`**: Encloses the block of statements that might throw an exception.
*   **`catch`**: Intercepts and processes a specific type of exception thrown inside the try block. You can write multiple catch blocks to handle different errors differently.
*   **`finally`**: A block that **always executes**, regardless of whether an exception was thrown, caught, or skipped. It is used exclusively to close resources and clean up memory handles.

```
                  [ ENTER TRY BLOCK ]
                           │
             Any statement throws exception?
                      /         \
                 [ YES ]       [ NO ]
                   /             \
       [ Match Catch Block ]      [ Skip Catch ]
                   \             /
                [ RUN FINALLY BLOCK ]
```

---

### Catch Ordering Rule
When declaring multiple catch blocks, **always write specific subclass exceptions first, and generic parent exceptions (like `Exception` or `Throwable`) last.**

If you put `catch (Exception e)` at the top, it will intercept all exceptions (due to polymorphism upcasting), making the subsequent catch blocks (like `catch (IOException e)`) unreachable, which triggers a compilation failure.

```java
// COMPILER ERROR PATHWAY:
try { ... }
catch (Exception e) { ... } // Intercepts everything!
// catch (IOException ioe) { ... } // ERROR: IOException has already been caught!
```

---

### Multi-Catch Syntax
If different exceptions require the exact same handling logic, you can combine them into a single catch block using the pipe operator (`|`) to reduce code duplication:

```java
try { ... }
catch (NullPointerException | ArithmeticException e) {
    System.out.println("Common math/logic error captured: " + e.getMessage());
}
```

---

### Try-With-Resources (AutoCloseable)
Introduced in Java 7, this syntax automatically closes resources (like files or database connections) declared inside the `try (...)` parentheses when the block exits, eliminating the need for manual cleanup inside a `finally` block.
*   **The Constraint**: The resource class must implement the **`java.lang.AutoCloseable`** interface.
*   **Advantage**: The compiler implicitly inserts a hidden `finally` block that invokes the `.close()` method on all declared resources, preventing resource leaks even if the block crashes.

---

## Code Example: Resource Management Simulation
The following program defines a custom `MockDatabaseConnection` class implementing `AutoCloseable` and contrasts the old `try-finally` cleanup style with the modern `Try-with-Resources` style:

```java
import java.io.IOException;

// A mock resource implementing AutoCloseable for automated cleanup
class MockDatabaseConnection implements AutoCloseable {
    private String dbName;

    public MockDatabaseConnection(String dbName) throws IOException {
        this.dbName = dbName;
        System.out.println("[DB] Opened connection to: " + dbName);
    }

    public void runQuery(String sql) throws IOException {
        if (sql.contains("INVALID")) {
            throw new IOException("Database syntax error in query: " + sql);
        }
        System.out.println("[DB] Executed query: " + sql);
    }

    @Override
    public void close() {
        System.out.println("[DB-AUTO-CLOSE] Closing connection to: " + dbName);
    }
}

public class HandlingDemo {

    public static void runLegacyStyle() {
        System.out.println("\n--- 1. Legacy Try-Catch-Finally Resource Cleanup ---");
        MockDatabaseConnection conn = null;
        try {
            conn = new MockDatabaseConnection("legacy_db");
            conn.runQuery("SELECT * FROM users;");
            conn.runQuery("INVALID QUERY"); // Triggers IOException
        } catch (IOException e) {
            System.out.println("[CATCH] Captured Database Exception: " + e.getMessage());
        } finally {
            // Manual cleanup requires null checks and nested try-catch blocks!
            System.out.println("[FINALLY] Executing manual cleanup...");
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void runModernStyle() {
        System.out.println("\n--- 2. Modern Try-with-Resources Cleanup ---");
        // Resource declared inside try parentheses is closed automatically on exit
        try (MockDatabaseConnection conn = new MockDatabaseConnection("modern_db")) {
            conn.runQuery("SELECT * FROM products;");
            conn.runQuery("INVALID PRODUCTS LIMIT"); // Triggers IOException
        } catch (IOException e) {
            // Close occurs BEFORE the catch block executes!
            System.out.println("[CATCH] Captured Database Exception: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        runLegacyStyle();
        runModernStyle();
    }
}
```

---

## Summary
- Use **`try-catch`** blocks to intercept exceptions and prevent application crashes.
- The **`finally`** block runs unconditionally to clean up resources, regardless of success or failure.
- Declare **specific catch blocks first**, and generic parent exception blocks last to avoid unreachable code errors.
- Use **multi-catch (`|`)** to route different exceptions to the same handler code.
- Enforce **Try-with-Resources** on classes implementing `AutoCloseable` to automate connection and stream closures safely.

---

## Additional Resources
- [The try-catch-finally blocks - Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/essential/exceptions/try.html)
- [Try-With-Resources Guide - Baeldung](https://www.baeldung.com/java-try-with-resources)
- [Handling Multiple Exceptions in Java - GeeksforGeeks](https://www.geeksforgeeks.org/multicatch-in-java/)