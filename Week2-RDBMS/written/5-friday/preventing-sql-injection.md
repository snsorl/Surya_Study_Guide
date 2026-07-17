# Preventing SQL Injection

## Learning Objectives
- Detail the anatomy and mechanics of a SQL Injection (SQLi) attack.
- Analyze the compile-time and runtime differences between vulnerable concatenated SQL and secure parameterized SQL.
- Implement input validation and sanitization filters as secondary layers of defense.
- Describe how ORM (Object-Relational Mapping) frameworks provide built-in SQL injection protections.
- Apply the absolute coding standard: never use string concatenation to construct SQL commands.

---

## Why This Matters
SQL Injection is one of the oldest, most prevalent, and most destructive vulnerabilities in web software history. Rated high on the OWASP Top 10 list (under Injection), it allows attackers to bypass security layers and talk directly to your database.

If an application is vulnerable to SQL Injection, an attacker can:
-   Log into any administrative account without knowing the password.
-   Steal customer lists, passwords, and credit card profiles.
-   Delete entire databases (`DROP TABLE`).
-   Execute operating system commands on the database server.

As a full-stack developer, preventing SQL injection is a mandatory, non-negotiable coding standard. A single vulnerable query in a minor dashboard search bar can compromise an entire corporate network.

---

## The Concept

### 1. Anatomy of a SQL Injection (SQLi) Attack
SQL Injection occurs when untrusted user input is concatenated directly into a SQL query string. This allows the input to break out of the data context and alter the logical structure of the SQL command itself.

#### The Vulnerable Code:
`String query = "SELECT * FROM users WHERE email = '" + userInput + "'";`

#### The Normal Input:
User enters `alice@email.com`. The query executes as:
`SELECT * FROM users WHERE email = 'alice@email.com'`
*(Result: Matches Alice's profile).*

#### The Injection Exploit:
User enters `' OR '1'='1`. The query executes as:
`SELECT * FROM users WHERE email = '' OR '1'='1'`
*(Result: Because '1'='1' is always TRUE, the WHERE filter evaluates to TRUE for every single row. The database returns all users, logging the attacker in).*

### 2. How PreparedStatement Prevents SQLi
As discussed on Thursday, `PreparedStatement` solves this by separating **Compilation** from **Binding**:

1.  **Compilation Phase:** The SQL template is compiled by the database first:
    `SELECT * FROM users WHERE email = ?`
    The database optimizer creates the execution tree, specifying that the filter expects a **literal text value** at `?`. The query structure is locked.
2.  **Binding Phase:** The parameter value `' OR '1'='1` is bound.
    The database searches for a user whose actual, literal email is exactly `"' OR '1'='1"`. The quote symbols and operators are automatically escaped by the driver, preventing them from being executed as code.

### 3. Additional Defense Tiers
While query parameterization is the primary defense, secure systems apply **Defense-in-Depth**:
-   **Input Validation:** Filter inputs before they reach the query (e.g., ensuring an age input is strictly numeric, or using regex to verify email formats).
-   **ORM Protections:** Modern Object-Relational Mapping frameworks (like JPA or Hibernate, covered in Week 5) generate parameterized queries automatically under the hood, protecting developers from writing raw SQL.
-   **Least Privilege Roles:** As discussed in Wednesday's DCL lesson, connect your application using a database role that has read/write permissions but cannot drop tables, limiting the impact if an injection occurs.

---

## Code Example: The Exploit and the Shield

### 1. The Exploit (Vulnerable Statement)
```java
public boolean deleteUserVulnerable(String emailInput, Connection conn) throws SQLException {
    // String concatenation allows the input to execute commands
    String sql = "DELETE FROM users WHERE email = '" + emailInput + "'";
    
    try (Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(sql);
        return true;
    }
}
```

If an attacker inputs:
`alice@email.com' OR '1'='1`

The executed SQL becomes:
`DELETE FROM users WHERE email = 'alice@email.com' OR '1'='1'`
The database deletes **every user in the system**.

---

### 2. The Shield (Secure PreparedStatement)
```java
public boolean deleteUserSecure(String emailInput, Connection conn) throws SQLException {
    // Parameterized template
    String sql = "DELETE FROM users WHERE email = ?";
    
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        // Bind parameter. The driver escapes any quotes or boolean operators automatically.
        pstmt.setString(1, emailInput);
        
        pstmt.executeUpdate();
        return true;
    }
}
```

If the attacker inputs the same exploit:
`alice@email.com' OR '1'='1`

The database runs:
`DELETE FROM users WHERE email = 'alice@email.com'' OR ''1''=''1'`
The query tries to delete a user whose email string is exactly `'alice@email.com\' OR \'1\'=\'1'`. Nobody matches, and **zero rows are deleted**. Your data is completely safe.

---

## Summary
-   **SQL Injection (SQLi)** occurs when user input is parsed as SQL commands, altering query logic.
-   **Never use string concatenation** to construct SQL statements.
-   **`PreparedStatement`** prevents injection by pre-compiling the query structure before parameters are bound, forcing the engine to treat inputs strictly as data values.
-   Apply **Defense-in-Depth**: combine query parameterization with input validation and least-privilege database roles.

---

## Additional Resources
-   [OWASP: SQL Injection Explanation](https://owasp.org/www-community/attacks/SQL_Injection)
-   [PortSwigger: SQL Injection Academy and Labs](https://portswigger.net/web-security/sql-injection)
-   [Baeldung: Preventing SQL Injection in Java](https://www.baeldung.com/sql-injection-prevention-java)
