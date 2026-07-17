// ============================================================================
// Starter Code: Redshift Analytical Reporting via JDBC
//
// Instructions:
//   - Implement every method marked with a // TODO comment.
//   - Do NOT modify the method signatures.
//   - Build: mvn compile | Run: mvn exec:java -Dexec.mainClass="RedshiftReportService"
// ============================================================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RedshiftReportService {

    // --- Configuration details ---
    private static final String ENDPOINT = "[YOUR_REDSHIFT_CLUSTER_ENDPOINT]";
    private static final String PORT = "5439";
    private static final String DATABASE = "dev";
    
    // TODO: Task 2 - Define your JDBC URL and Credentials
    private static final String JDBC_URL = String.format(
        "jdbc:redshift://%s:%s/%s?ssl=true&sslfactory=com.amazon.redshift.ssl.NonValidatingFactory",
        ENDPOINT, PORT, DATABASE
    );
    private static final String USER = "dw_admin";
    private static final String PASSWORD = "[YOUR_MASTER_PASSWORD]";

    public static Connection getConnection() throws SQLException {
        // TODO: Task 2 - Establish and return the JDBC Connection using DriverManager
        return null;
    }

    public static void runCategoryReport(Connection conn) {
        // TODO: Task 3 - Query the 's3_sales_load' table and aggregate units sold and revenue per product
        // 1. Write the aggregate SELECT query:
        //    SELECT product_name, SUM(quantity) AS total_qty, SUM(quantity * price) AS total_revenue
        //    FROM s3_sales_load GROUP BY product_name ORDER BY total_revenue DESC;
        // 2. Prepare the statement and execute.
        // 3. Loop through results and print as a formatted table.
    }

    public static void main(String[] args) {
        System.out.println("Initiating connection to Redshift...");
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connection successful. Running report...");
                runCategoryReport(conn);
            } else {
                System.err.println("Connection failed - returned null.");
            }
        } catch (SQLException e) {
            System.err.println("Database error occurred:");
            e.printStackTrace();
        }
    }
}
