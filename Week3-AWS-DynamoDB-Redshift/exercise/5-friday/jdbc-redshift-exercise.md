# Lab: Redshift Analytical Reporting via JDBC

**Exercise Mode:** Mode A: Implementation (Code Lab)
**Target Engine:** Amazon Redshift (JDBC)

---

## Scenario
You are developing a Java-based reporting dashboard for TechMart's executives. Instead of having executives write raw SQL query statements, they want to execute pre-packaged reports via a Java CLI tool.

Your task is to build a Java application that:
1. Establishes a secure connection to the Redshift cluster using JDBC.
2. Queries the `s3_sales_load` table created in the S3 COPY exercise.
3. Groups the records by product name, calculates total units sold and total revenue.
4. Formats and prints the results as a text-based report.

---

## Prerequisites

- Java 11+ and Maven installed.
- Access to your Redshift cluster endpoint details.

---

## Core Tasks

Navigate to `starter_code/` and open `RedshiftReportService.java`. Complete the methods marked with a `// TODO` comment.

### Task 1 — Add the Redshift JDBC Dependency
Open `starter_code/pom.xml`. Add the official Redshift JDBC driver to the `<dependencies>` block.
> **Note:** The driver is:
> - **Group ID:** `com.amazon.redshift`
> - **Artifact ID:** `redshift-jdbc42-no-awssdk`
> - **Version:** `2.1.0.26`

### Task 2 — Implement the Connection Logic
In `RedshiftReportService.java`, construct the JDBC connection URL string:
```
jdbc:redshift://[YOUR_ENDPOINT]:5439/[DATABASE]?ssl=true&sslfactory=com.amazon.redshift.ssl.NonValidatingFactory
```
Establish the connection in `getConnection()` using `DriverManager.getConnection()`.

### Task 3 — Execute the Aggregate Query
In `runCategoryReport(Connection conn)`:
1. Write a SQL `SELECT` statement that joins/aggregates the sales records from the `s3_sales_load` table:
   ```sql
   SELECT product_name, SUM(quantity) AS total_qty, SUM(quantity * price) AS total_revenue
   FROM s3_sales_load
   GROUP BY product_name
   ORDER BY total_revenue DESC;
   ```
2. Prepare and execute the statement.
3. Read the `ResultSet` and print the results in a formatted text table.

---

## Running the Lab

```bash
# Navigate to starter code
cd starter_code

# Build the project
mvn compile

# Run the program (ensure you pass your Redshift credentials in your code/environment)
mvn exec:java -Dexec.mainClass="RedshiftReportService"
```

**Expected output layout:**
```
Initiating connection to Redshift...
Connection successful. Running report...

--------------------------------------------------------------
| Product Name         | Units Sold | Total Revenue          |
--------------------------------------------------------------
| Mechanical Keyboard  | 2          | $150.00                |
| Ergonomic Chair      | 1          | $150.00                |
...
--------------------------------------------------------------
```

---

## Submission
Push your completed `starter_code/` directory containing:
- `RedshiftReportService.java`
- `pom.xml`
