# Statement vs. PreparedStatement

## Learning Objectives
- Compare the execution processes of `Statement` and `PreparedStatement` in JDBC.
- Construct secure, parameterized SQL queries using `PreparedStatement`.
- Explain the compilation lifecycle differences that allow `PreparedStatement` to run faster.
- Analyze the technical mechanics of how parameter binding prevents SQL Injection attacks.

---

## Why This Matters
When writing backend code to query a database based on user input (such as a search box or login form), you must construct SQL strings dynamically. 

A common, dangerous mistake is to use string concatenation to build the query:
`String query = "SELECT * FROM users WHERE username = '" + userInput + "'";`

If the user types a normal username like `alice`, the query is safe. But if they type:
`' OR '1'='1`

The database compiles the query as:
`SELECT * FROM users WHERE username = '' OR '1'='1'`

This is a **SQL Injection** attack. The query bypasses validation and returns every user record, logging the attacker in. 

Understanding the differences between `Statement` and `PreparedStatement` is your first line of defense. In enterprise environments, **`PreparedStatement` is the mandatory standard** for all queries containing user parameters.

---

## The Concept

### 1. Statement: Direct Compilation
When you run a standard `Statement`, the Java application sends the raw SQL string over the network to the database engine.

The database must perform three compilation steps *every time* the statement is run:
1.  **Parse:** Check syntax.
2.  **Compile:** Translate SQL to machine execution plans.
3.  **Execute:** Scan disk pages and return rows.

If you execute a query in a loop 1,000 times, the database must compile it 1,000 times, creating a major performance bottleneck. Furthermore, because the user input is concatenated directly, the database cannot distinguish between the query structure (keywords) and the query data (values).

### 2. PreparedStatement: Pre-Compiled Templates
A `PreparedStatement` uses a template structure. You write the SQL query beforehand, using question marks (**`?`**) as placeholders for variables.

When you instantiate a `PreparedStatement`, the Java application sends the template SQL string to the database immediately. **The database compiles the execution plan first, before any values are provided.**

When you execute the query:
1.  Java binds the parameter values to the placeholders.
2.  The database executes the pre-compiled plan directly, bypassing the parsing and compilation stages.

This provides two massive advantages:
-   **Performance Optimization:** If you run the query in a loop, it compiles only once. Subsequent executions run at maximum speed.
-   **SQL Injection Prevention:** Because the query plan is compiled *before* the user input is bound, the database treats user input strictly as **data values (literals)**, never as executable code. If the attacker inputs `' OR '1'='1`, the database simply searches for a user whose literal username is `"' OR '1'='1"`.

---

## Code Example: Vulnerable vs. Secure Queries

### Vulnerable Implementation (Statement with Concatenation)
This method is highly insecure. If `userInput` contains malicious SQL syntax, the database executes it.

```java
// INSECURE: Never write SQL this way!
public boolean loginVulnerable(String userInput, String password, Connection conn) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = '" + userInput + "' AND password = '" + password + "'";
    
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        return rs.next(); // If rows exist, login succeeds
    }
}
```

---

### Secure Implementation (PreparedStatement with Parameter Binding)
This method compiles the query structure first. User input is safely parameterized.

```java
// SECURE: Industry Standard
public boolean loginSecure(String userInput, String password, Connection conn) throws SQLException {
    // 1. Define template with placeholders '?'
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    
    // 2. Database compiles the SQL query plan here
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        // 3. Bind the data parameter values (1-indexed matching '?')
        pstmt.setString(1, userInput);
        pstmt.setString(2, password);
        
        // 4. Execute the pre-compiled plan
        try (ResultSet rs = pstmt.executeQuery()) {
            return rs.next();
        }
    }
}
```

---

## Summary
-   **`Statement`** compiles SQL strings dynamically on every run; it is slow and vulnerable to SQL injection.
-   **`PreparedStatement`** pre-compiles a query template using **`?`** placeholders, providing execution speed optimization.
-   `PreparedStatement` prevents **SQL Injection** because the query logic is locked before user parameters are bound, forcing the engine to evaluate parameters strictly as data.
-   Always bind parameters using data-type setters (like `setString()`, `setInt()`).

---

## Additional Resources
-   [Oracle: Using Prepared Statements](https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html)
-   [OWASP: SQL Injection Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)
-   [Baeldung: Statement vs PreparedStatement](https://www.baeldung.com/cs/jdbc-statement-vs-preparedstatement)
