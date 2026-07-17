// ============================================================================
// SOLUTION: Redshift Analytical Reporting via JDBC
// FOR INSTRUCTOR USE ONLY — Do not distribute to trainees.
// ============================================================================

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RedshiftReportServiceSolution {

    private static final String ENDPOINT = "redshift-cluster-1.abc123xyz.us-east-1.redshift.amazonaws.com"; // Example
    private static final String PORT = "5439";
    private static final String DATABASE = "dev";
    
    private static final String JDBC_URL = String.format(
        "jdbc:redshift://%s:%s/%s?ssl=true&sslfactory=com.amazon.redshift.ssl.NonValidatingFactory",
        ENDPOINT, PORT, DATABASE
    );
    private static final String USER = "dw_admin";
    private static final String PASSWORD = "MySecurePassword123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void runCategoryReport(Connection conn) {
        String query = 
            "SELECT product_name, SUM(quantity) AS total_qty, SUM(quantity * price) AS total_revenue " +
            "FROM s3_sales_load " +
            "GROUP BY product_name " +
            "ORDER BY total_revenue DESC;";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            System.out.println("\n--------------------------------------------------------------");
            System.out.printf("| %-25s | %-10s | %-18s |%n", "Product Name", "Units Sold", "Total Revenue");
            System.out.println("--------------------------------------------------------------");
            
            while (rs.next()) {
                String name = rs.getString("product_name");
                int qty = rs.getInt("total_qty");
                double revenue = rs.getDouble("total_revenue");
                System.out.printf("| %-25s | %-10d | $%,-17.2f |%n", name, qty, revenue);
            }
            System.out.println("--------------------------------------------------------------");
            
        } catch (SQLException e) {
            System.err.println("Failed to run category report: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Initiating connection to Redshift...");
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connection successful. Running report...");
                runCategoryReport(conn);
            }
        } catch (SQLException e) {
            System.err.println("Database error occurred:");
            e.printStackTrace();
        }
    }
}
