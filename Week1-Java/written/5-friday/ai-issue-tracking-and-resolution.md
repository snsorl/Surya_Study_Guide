# AI Issue Tracking and Resolution

## Learning Objectives
- Apply AI coding assistants as automated static analysis tools to review code quality.
- Categorize identified code issues by severity: Critical, Major, and Minor.
- Construct prompts to locate hidden security holes and memory leaks.
- Resolve flagged issues systematically, verifying the safety of each modification.

---

## Why This Matters
When you write code, it is easy to focus exclusively on "happy path" logic—making sure the program runs correctly when the inputs are perfect. However, in production environments, code is bombarded with invalid requests, network interruptions, and malicious attacks. 

Finding these edge-case vulnerabilities through manual testing is extremely difficult. You can use your AI assistant as a dedicated static analysis reviewer. By feeding your completed code to the AI and requesting a structured issue log, you can locate logical loops, memory leaks, and security vulnerabilities (like SQL injections) before your code is submitted to your team's pull request review.

---

## The Concept

### The Automated Review Workflow
To track and resolve code issues using an LLM, enforce this structured loop:

```
[ Code Submission ] ---> [ Request Severity Log ] ---> [ Sequential Fixes ] ---> [ Re-Verify Code ]
                             (Critical/Major/Minor)       (Fix Critical first)      (Compile & test check)
```

#### Step 1: Submit Code for Review
Provide the AI with your complete, compiled class code.
*   *Prompt*: *"Act as a security auditor and static analysis engine. Review this Java class and generate a list of issues. Group them into three categories: Critical (security vulnerabilities, resource leaks, immediate crashes), Major (performance bottlenecks, logic errors, API misuses), and Minor (code style, naming conventions, redundancy)."*

#### Step 2: Analyze the Issue Log
Analyze the AI's output table. Do not try to fix everything at once. Focus on **Critical** issues first.

#### Step 3: Resolve and Re-Verify
Ask the AI to generate a solution for the Critical issues. Review the code, compile it, and verify that it does not introduce new compiler warnings. Repeat the process for Major and Minor issues.

---

## Code Example: Tracking and Resolving Roster Issues
Let's see this workflow applied to a user storage coordinator class.

### The Original Un-Reviewed Code:
```java
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmailRoster {
    private List<String> emails = new ArrayList<>();
    
    // Insecure query builder: vulnerable to SQL Injection
    public void saveToDatabase(Connection conn, String email) throws Exception {
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO users (email) VALUES ('" + email + "')"; 
        stmt.executeUpdate(sql); // LINE 12: SQL Injection point!
    }

    public void addEmail(String email) {
        emails.add(email); // Allows duplicate emails
    }
}
```

---

### Step 1: The AI-Generated Audit Log

| Severity | Issue Description | Location | Fix Strategy |
| :--- | :--- | :--- | :--- |
| **CRITICAL** | **SQL Injection Vulnerability**: Concatenating `email` directly into the SQL string allows malicious users to execute arbitrary database commands. | Line 12 | Use `PreparedStatement` with parameterized placeholders. |
| **CRITICAL** | **Resource Leak**: The `Statement` object is opened but never closed, consuming database connection handles. | Line 10 | Use Try-with-Resources. |
| **MAJOR** | **Duplicate Data**: `addEmail` uses a `List` without checking if the email already exists, violating roster uniqueness rules. | Line 15 | Switch the backing structure from `ArrayList` to `HashSet`. |
| **MINOR** | **Unused Import**: `import java.util.List;` is redundant if we switch to Set. | Line 4 | Remove unused imports. |

---

### Step 2: Implementing the Resolved Code
We apply the fix strategies sequentially, producing the secure, optimized version:

```java
import java.sql.Connection;
import java.sql.PreparedStatement; // 1. Switch to PreparedStatement
import java.sql.SQLException;
import java.util.HashSet; // 2. Switch to HashSet to prevent duplicates
import java.util.Set;

public class EmailRosterClean {
    // HashSet guarantees email uniqueness at O(1) time complexity
    private Set<String> emails = new HashSet<>();

    /**
     * Safely saves user email to database.
     * Uses Try-with-Resources to prevent connection leaks, and PreparedStatement to block SQL injections.
     */
    public void saveToDatabase(Connection conn, String email) throws SQLException {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        String sql = "INSERT INTO users (email) VALUES (?)"; // Parameterized placeholder

        // 3. Try-with-resources automatically closes PreparedStatement on block exit
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim()); // 4. Safe binding prevents SQL injections
            stmt.executeUpdate();
            System.out.println("[DB-SAVE] Successfully saved email: " + email);
        }
    }

    public void addEmail(String email) {
        if (email != null) {
            // Set will automatically ignore duplicates
            boolean wasAdded = emails.add(email.trim().toLowerCase()); 
            if (wasAdded) {
                System.out.println("[ROSTER] Added email: " + email);
            } else {
                System.out.println("[ROSTER] Skipped duplicate email: " + email);
            }
        }
    }

    public Set<String> getEmails() {
        return this.emails;
    }
}
```

---

## Summary
- Use AI as an automated **static analysis reviewer** to check code security and performance.
- Group issues by severity: **Critical** (security, resources), **Major** (logic, speed), and **Minor** (style).
- Fix **Critical** security issues (like SQL injection or connection leaks) before optimizing logic.
- Replace raw SQL concatenation with **`PreparedStatement`** variables.
- Close connection objects using **Try-with-Resources** blocks.

---

## Additional Resources
- [OWASP SQL Injection Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)
- [Static Code Analysis with LLMs - InfoQ](https://www.infoq.com/)
- [Java PreparedStatement Guide - GeeksforGeeks](https://www.geeksforgeeks.org/java-database-connectivity-jdbc-preparedstatement/)