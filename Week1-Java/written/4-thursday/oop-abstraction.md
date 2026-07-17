# OOP Abstraction

## Learning Objectives
- Explain Abstraction as a core design principle for reducing cognitive load and application coupling.
- Differentiate between the *specification* of a system behavior and its *implementation*.
- Distinguish between abstract classes and interfaces as distinct abstraction tools.
- Implement a decoupled system where client code interacts exclusively with abstract definitions.

---

## Why This Matters
As software scales, it becomes impossible for a single developer to understand every line of code in the project. If you are building a feature that sends text notifications to users, you should not need to understand network sockets, cellular protocols, or carrier APIs. 

If you had to write that low-level networking code directly inside your user registration class, your program would become complex and fragile. **Abstraction** solves this by separating **what** a system does from **how** it does it. You define a clean interface containing a single method—`sendNotification(String phone, String msg)`—and interact exclusively with that. The complex networking logic is hidden inside a separate implementation class. This allows you to update or replace the notification logic without editing a single line of your user registration class.

---

## The Concept

### Interface vs. Implementation
Abstraction is the process of hiding complex internal details and exposing only the essential features through a simple, user-friendly interface. In OOP, this separates your code into two layers:

1.  **The Specification (The Interface)**: Declares the contract or behavior (e.g., the TV remote power button). It defines *what* is possible.
2.  **The Implementation**: Contains the actual executing statements (e.g., the TV circuit board receiving the signal, activating the screen, and adjusting power grids). It defines *how* the action is performed.

```
                  [ CLIENT / CONSUMER CODE ]
                              │
                    Interacts exclusively with
                              ▼
                [ SPECIFICATION: Interface / Abstract Class ]
                              │
                    Dynamically routed to
                              ▼
            [ IMPLEMENTATION: Concrete Subclass ]
             - Hides SQL queries, sockets, hashing
```

---

### Abstraction Tools in Java
Java provides two primary structures for achieving abstraction:

*   **Abstract Classes**: Used when subclasses share a common identity and require a base template. For example, a base `AbstractDatabase` handles the shared state (connection pools, timeouts) but leaves the specific query execution abstract.
*   **Interfaces**: Used when unrelated classes need to share a common capability contract. For example, both a `User` class and a `Server` class can implement `Loggable` to generate activity logs, even though they share no common family identity.

---

## Code Example: Authentication Abstraction
The following program defines a `SecureAuthenticator` interface (specification), a concrete database template (abstract class), and shows how client code uses these abstractions without accessing database configurations directly:

```java
// ==================== THE SPECIFICATION (INTERFACE) ====================
interface SecureAuthenticator {
    /**
     * Authenticates a user against secure credentials.
     * The caller does not know if this uses databases, local files, or LDAP.
     */
    boolean login(String username, String password);
}

// ==================== ABSTRACT DATA TEMPLATE (ABSTRACT CLASS) ====================
abstract class DatabaseConnection {
    protected String dbUrl;

    public DatabaseConnection(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    // Concrete utility method
    public void openConnection() {
        System.out.println("[DATABASE] Connected to " + dbUrl);
    }

    // Abstract operational method - customized by database types
    public abstract String queryUserCredentials(String user);
}

// ==================== CONCRETE IMPLEMENTATION LAYER ====================
class PostgresConnection extends DatabaseConnection {
    public PostgresConnection(String dbUrl) {
        super(dbUrl);
    }

    @Override
    public String queryUserCredentials(String user) {
        System.out.println("[POSTGRES] Executing query: SELECT hash FROM users WHERE username = '" + user + "'");
        // Mock returning MD5 hashed password "adminPass123"
        return "adminPass123"; 
    }
}

class UserAuthenticatorImpl implements SecureAuthenticator {
    private DatabaseConnection db;

    public UserAuthenticatorImpl(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public boolean login(String username, String password) {
        // 1. Establish database connection
        db.openConnection();
        
        // 2. Query credentials
        String storedHash = db.queryUserCredentials(username);

        // 3. Simulating hash check (abstraction hides password processing)
        System.out.println("[AUTH ENGINE] Comparing password input with database hash...");
        return storedHash.equals(password);
    }
}

// ==================== CLIENT CONSUMER TIER ====================
public class AbstractionDemo {
    public static void main(String[] args) {
        // A. Setup configuration (Done by system administrator/factory)
        DatabaseConnection pgDb = new PostgresConnection("jdbc:postgresql://10.0.0.5:5432/user_db");
        SecureAuthenticator authSystem = new UserAuthenticatorImpl(pgDb);

        System.out.println("--- 1. Client Executing Login ---");
        // B. Client interacts EXCLUSIVELY with the SecureAuthenticator interface contract
        // The client has no access to PostgresConnection, SQL statements, or database connection strings.
        boolean success = authSystem.login("admin", "adminPass123");
        System.out.println("[CLIENT] Authentication Success Status: " + success);
    }
}
```

---

## Summary
- **Abstraction** simplifies systems by separating the **specification** (what a class does) from the **implementation** (how it does it).
- Abstraction reduces **cognitive load** by hiding low-level details (like SQL statements or network sockets) behind clean APIs.
- In Java, abstraction is achieved using **abstract classes** (identity templates) and **interfaces** (capability contracts).
- Programming to abstract interfaces makes your code **decoupled**, allowing implementations to be replaced without editing client code.

---

## Additional Resources
- [Abstraction in Java - GeeksforGeeks](https://www.geeksforgeeks.org/abstraction-in-java-2/)
- [Java Abstract Classes & Methods - W3Schools](https://www.w3schools.com/java/java_abstract.asp)
- [Separation of Concerns Design Principle - Wikipedia](https://en.wikipedia.org/wiki/Separation_of_concerns)