# Integrating Java Applications with Redshift via JDBC

## Learning Objectives
- Connect Java applications to Amazon Redshift clusters using Java Database Connectivity (JDBC).
- Configure the Redshift JDBC Driver and dependencies in Maven build files.
- Formulate database connection URLs with specific SSL parameters.
- Write Java code to execute analytical queries and process query results.

## Why This Matters
Data warehouses are useful for generating reports and dashboard visualizations. To display these insights in a custom dashboard or a client application, your backend web application must query the data warehouse directly. 

In Java development, you connect to databases using the Java Database Connectivity (JDBC) API. Connecting to a remote cloud warehouse like Amazon Redshift requires specific configurations: importing the official Redshift JDBC driver, configuring the connection URL with SSL, and writing queries that process large result sets efficiently. Mastering JDBC integration allows developers to build fast, secure analytical reporting applications.

## The Concept

### The JDBC Connection Pipeline
The JDBC API provides standard interfaces that Java applications use to interact with relational databases. To connect to Amazon Redshift:

```
+-------------------------------------------------------------+
|                     JAVA APPLICATION RUNTIME                 |
|  - Redshift Connection Factory                              |
|  - java.sql.Connection / Statement / ResultSet APIs         |
+-------------------------------------------------------------+
                              |
                              | (Uses Redshift JDBC Driver)
                              v
+-------------------------------------------------------------+
|                 AMAZON REDSHIFT LEADER NODE                 |
|  - Port 5439 Connection handler                            |
|  - Executes SQL query and streams results back              |
+-------------------------------------------------------------+
```

### Key Integration Requirements

#### 1. Maven Dependency (The Driver)
You must include the official Amazon Redshift JDBC driver in your project configuration. The driver class is:
- **`com.amazon.redshift.jdbc.Driver`** (for driver versions 2.x)

#### 2. Connection String URL Format
The Redshift connection URL follows a specific pattern:
- **`jdbc:redshift://[ENDPOINT]:[PORT]/[DATABASE_NAME]`**
- *Example*: `jdbc:redshift://enterprise-dw.c1234567890.us-east-1.redshift.amazonaws.com:5439/dev`

#### 3. SSL Configuration (Security in Transit)
To protect sensitive analytical data transferred over the network, you must require SSL encryption by appending connection properties to the URL:
- Append **`?ssl=true&sslfactory=com.amazon.redshift.ssl.NonValidatingFactory`** to the connection string to enable SSL encryption.

---

### Executing Queries and Processing ResultSets
When executing query statements in Java, the database returns a **`ResultSet`**.
- **Cursors**: For large analytical datasets containing millions of rows, loading the entire result set into the Java application's memory can cause OutOfMemory errors.
- **Batch Streaming**: Configure the JDBC connection to fetch data in batches (e.g., using `statement.setFetchSize(1000)`) to stream records sequentially.

## Code Examples

### 1. Maven POM Configuration
Add the official Amazon Redshift JDBC driver dependency to your `pom.xml` file:

```xml
<dependencies>
    <!-- Amazon Redshift JDBC Driver -->
    <dependency>
        <groupId>com.amazon.redshift</groupId>
        <artifactId>redshift-jdbc42</artifactId>
        <version>2.1.0.9</version>
    </dependency>
</dependencies>
```

### 2. Complete Java Connection and Query Execution
Below is a complete Java program demonstrating how to connect to Redshift, execute an aggregation query, and print the results:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class RedshiftJDBCService {

    // Redshift connection properties
    private static final String REDSHIFT_URL = 
        "jdbc:redshift://enterprise-dw.c1234567890.us-east-1.redshift.amazonaws.com:5439/dev" +
        "?ssl=true&sslfactory=com.amazon.redshift.ssl.NonValidatingFactory";
    
    private static final String USER = "dw_admin";
    private static final String PASSWORD = "SecurityPassword987";

    public static void main(String[] args) {
        String query = 
            "SELECT category, SUM(revenue) AS total_revenue " +
            "FROM fact_transactions t " +
            "JOIN dim_products_analysis p ON t.product_key = p.product_key " +
            "GROUP BY category " +
            "ORDER BY total_revenue DESC;";

        System.out.println("Connecting to Amazon Redshift...");
        
        try (Connection conn = DriverManager.getConnection(REDSHIFT_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            System.out.println("Connection established. Executing query...");
            
            // Set fetch size to limit memory usage when processing large datasets
            stmt.setFetchSize(500);
            
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("\n--- Category Revenue Report ---");
                while (rs.next()) {
                    String category = rs.getString("category");
                    double revenue = rs.getDouble("total_revenue");
                    System.out.printf("Category: %-15s | Revenue: $%,.2f%n", category, revenue);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("JDBC Execution failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

### 3. Connection Verification Best Practices
When deploying Java applications to production:
- Use a **Connection Pool** (such as HikariCP) to reuse database connections, reducing connection overhead.
- Store database credentials securely using a service like **AWS Secrets Manager**, rather than hardcoding credentials in configuration files.

## Summary
- Connect Java applications to Redshift using the **Redshift JDBC Driver** and the **`jdbc:redshift://`** URL format.
- Secure data in transit by appending **`ssl=true`** parameters to the connection string.
- Configure **`setFetchSize`** on your Statement objects to stream large query results sequentially, preventing memory exhaustion.
- Store database credentials securely and use connection pooling for production deployments.

## Additional Resources
- [Configuring Connections in Java using the Amazon Redshift JDBC Driver](https://docs.aws.amazon.com/redshift/latest/mgmt/connecting-using-jdbc.html)
- [AWS Secrets Manager Developer Guide for Database Credentials](https://docs.aws.amazon.com/secretsmanager/latest/userguide/intro.html)
- [HikariCP Connection Pool Configuration Guide](https://github.com/brettwooldridge/HikariCP)
