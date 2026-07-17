# Security Lab: Mitigating SQL Injection

## Learning Objectives
- Identify SQL Injection vulnerability entry points in Java code.
- Execute SQL Injection payloads to demonstrate authentication bypasses.
- Refactor vulnerable query builders using `PreparedStatement` parameterized binding templates.
- Enforce secure database coding standards.

---

## Setup Instructions
1. Open IntelliJ IDEA on your workstation.
2. In your local package directory, view the following vulnerable Java snippet representing a database logging query:

```java
package exercise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VulnerableAuthGateway {

    public boolean authenticateUser(String emailInput, String passwordInput, Connection conn) throws SQLException {
        // VULNERABLE: Direct string concatenation of variables into a Statement
        String query = "SELECT * FROM members WHERE email = '" + emailInput + "' AND password = '" + passwordInput + "'";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next();
        }
    }
}
```

---

## Step-by-Step Tasks

### Task 1: Document the Vulnerability
In a text block, analyze the code and write a short explanation detailing:
1.  **Why** the concatenation makes the query unsafe.
2.  An example **exploit payload** string that an attacker could pass to the `emailInput` variable to bypass the password check completely and return all rows.

---

### Task 2: Refactor using Parameterized Queries
Modify the `VulnerableAuthGateway.java` class to secure the authentication method:

1.  Replace the dynamic SQL concatenation statement with a static parameterized template using `?` placeholders.
2.  Instantiate a `PreparedStatement` instead of a raw `Statement`.
3.  Bind the parameters (`emailInput` and `passwordInput`) using the driver's typed setter methods.

```java
// Write your secured authenticateUser implementation here
```

---

### Task 3: Test and Verify the Fix
Write a JUnit test class `AuthGatewayTest.java` that:
1.  Creates a mock database connection (or links to your local H2/PostgreSQL instance).
2.  Attempts to authenticate using a normal, valid email and password. Verify it works.
3.  Attempts to login using the exploit string: `' OR '1'='1`. Verify that the secured gateway successfully **rejects** the authentication attempt.

---

## Definition of Done
-   A completed document explaining the SQL Injection exploit vector.
-   Secured Java code in `SecureAuthGateway.java` using parameterized queries.
-   JUnit test logs proving that the exploit string is safely neutralized, blocking access.
