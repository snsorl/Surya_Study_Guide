# Configuring a Connection Factory

## Learning Objectives
- Define the Connection Factory design pattern and explain its architectural benefits.
- Deconstruct the syntax segments of a standard JDBC connection URL.
- Implement a Java Singleton class to manage database connection instances.
- Apply security best practices for credential management, avoiding hardcoded database secrets.

---

## Why This Matters
In a full-stack Java application, many different classes need to talk to the database: user authentication services, inventory trackers, and billing systems. 

If every individual class has to copy-paste the database driver details, database URL, username, and password, you create a major architectural problem:
-   **Hardcoded Credentials:** If you commit your database password to a public Git repository, attackers can compromise your servers instantly.
-   **Lack of Control:** If you change your database password or migrate to a new server IP, you have to find and rewrite connection code in fifty different Java files.
-   **Resource Management Chaos:** You cannot easily track or limit the number of active database connections.

To prevent this, enterprise systems centralize database access using the **Connection Factory** (or DataSource) pattern. Centralizing this logic secures your database credentials and encapsulates connection details in a single file.

---

## The Concept

### 1. The Connection Factory Pattern
A **Connection Factory** is a design pattern that isolates the creation of database connections from the application logic that queries the database. 
-   Your DAO (Data Access Object) classes simply ask the factory: *"Give me a connection"*. 
-   The DAOs do not know or care where the database resides, what driver is used, or what credentials are required.

### 2. JDBC Connection URL Formats
To connect to a database, you must supply a connection string. JDBC URLs use a standardized protocol segment format:

```
  jdbc:postgresql://localhost:5432/ecommerce_db
  |    |            |         |    |
  |    |            |         |    +---> Database Name
  |    |            |         +--------> Port Number (PostgreSQL default: 5432)
  |    |            +------------------> Hostname / Server IP Address
  |    +-------------------------------> Sub-protocol (Database Driver Type)
  +------------------------------------> Core protocol
```

### 3. Credential Management Best Practices
**Rule 1: Never hardcode passwords.** Do not write passwords directly in Java strings.
**Rule 2: Use Environment Variables.** Store database details on the host operating system or container settings. In Java, read them at runtime using `System.getenv("VARIABLE_NAME")`.
**Rule 3: Use gitignore.** If you store connection settings in properties files (like `db.properties` or `application.properties`), add those files to `.gitignore` so they are never committed to your Git repository.

---

## Code Example: Database Connection Factory Singleton

Below is a secure, singleton-based Connection Factory implementation. It reads credentials from environment variables and provides database connections to the rest of the application.

```java
// ConnectionFactory.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Singleton instance placeholder
    private static ConnectionFactory instance;

    // Read connection parameters from environment variables
    private final String url;
    private final String username;
    private final String password;

    // Private constructor prevents instantiation from outside
    private ConnectionFactory() {
        this.url = System.getenv("DB_URL");
        this.username = System.getenv("DB_USER");
        this.password = System.getenv("DB_PASS");

        // Fail-fast validation: Verify environment setup
        if (this.url == null || this.username == null || this.password == null) {
            throw new IllegalStateException(
                "Critical Error: Database environment variables (DB_URL, DB_USER, DB_PASS) are not configured."
            );
        }

        // Force-load the PostgreSQL driver class (recommended in older frameworks)
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC driver not found on classpath.", e);
        }
    }

    // Global access point for the Singleton instance
    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    // Factory method returning a connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
```

### Using the Factory in a Data Access Object (DAO)
Notice how clean your querying code becomes:

```java
public class ProductDAO {
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        
        // DAO does not manage credentials; it simply requests a connection from the factory
        try (
            Connection conn = ConnectionFactory.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Summary
-   The **Connection Factory** pattern centralizes the instantiation of database connections, separating configuration from query logic.
-   JDBC URLs follow a structured format: `jdbc:subprotocol://host:port/database_name`.
-   **Never hardcode secrets**; retrieve credentials at runtime using environment variables (`System.getenv()`).
-   The **Singleton pattern** ensures that only one factory configuration is created and shared across the application threads.

---

## Additional Resources
-   [Oracle: Managing Database Connections](https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html)
-   [OWASP: Securing Database Credentials](https://cheatsheetseries.owasp.org/cheatsheets/Secrets_Management_Cheat_Sheet.html)
-   [Baeldung: Guide to Java Singleton Pattern](https://www.baeldung.com/java-singleton)
