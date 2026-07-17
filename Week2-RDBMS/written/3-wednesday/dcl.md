# DCL: Data Control Language

## Learning Objectives
- Define the role of Data Control Language (DCL) in securing database access.
- Explain the syntax and behavior of the core DCL commands (`GRANT`, `REVOKE`).
- Establish Role-Based Access Control (RBAC) schemas in PostgreSQL database systems.
- Apply the Principle of Least Privilege when connecting backend applications to databases.

---

## Why This Matters
When building database-driven web applications, you must establish network connections from your backend server (e.g., your Java app) to the database server. A common security mistake is to configure the Java connection factory to log in using the database administrative superuser credentials (like `postgres` with full admin powers).

If an attacker exploits a code vulnerability (like SQL Injection) in your application, they gain the full system permissions of the logged-in database user. If your app is logged in as the admin, the attacker can delete all database tables, edit transaction records, or steal customer profiles.

**Data Control Language (DCL)** provides security control. By creating a restricted database user role specifically for your backend application and granting only the minimum required privileges (e.g., allowing reads and writes to inventory tables but blocking schema changes), you secure your data against exploitation.

---

## The Concept

### 1. What is DCL?
**Data Control Language (DCL)** is the subset of SQL used to manage user permissions, access privileges, and security controls on database objects (tables, schemas, views).

### 2. Core DCL Commands

-   **`GRANT`**: Gives specific permissions (like `SELECT`, `INSERT`, `UPDATE`, `DELETE`) on a database object to a database user or role.
-   **`REVOKE`**: Removes previously granted permissions from a database user or role.

### 3. Role-Based Access Control (RBAC) in PostgreSQL
In PostgreSQL, you do not manage permissions for individual users directly. Instead, you create a **Role** (a group representation of permissions), assign permissions to that Role, and then assign users to that Role.

Common role patterns for full-stack environments:
-   **App Reader Role:** Read-only access (`SELECT`) for basic analytical or reporting microservices.
-   **App Writer Role:** Read and write access (`SELECT`, `INSERT`, `UPDATE`, `DELETE`) for the primary transactional backend service.
-   **DBA Role:** Administrative access (`ALL PRIVILEGES`) for migration scripts and database administrators.

---

## Code Example: Database Access Security Script

Let's write a script that sets up a secure, restricted database role for a Java application connection.

### Setup Table (Executed as Admin)
```sql
CREATE TABLE transaction_records (
    transaction_id INT PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    account_number VARCHAR(30) NOT NULL
);
```

---

### 1. Creating a Limited Role
We create a role specifically for our Java backend service and configure a password.

```sql
CREATE ROLE java_backend_app WITH LOGIN PASSWORD 'SecurePass99!';
```

---

### 2. Granting Permissions (GRANT)
We grant our new role permission to read and insert transactions, but we **do not** allow updates or deletions.

```sql
-- Allow connection to the public schema
GRANT USAGE ON SCHEMA public TO java_backend_app;

-- Grant select and insert permissions
GRANT SELECT, INSERT ON TABLE transaction_records TO java_backend_app;
```

If the Java application logs in using `java_backend_app` credentials, it can successfully execute queries:

```sql
-- Executed as java_backend_app: SUCCESS
SELECT * FROM transaction_records;

-- Executed as java_backend_app: SUCCESS
INSERT INTO transaction_records VALUES (101, 150.00, 'ACC-1122');
```

---

### 3. Verification of Rejection (Security Enforcement)
If the Java application (or an attacker exploiting the application) attempts to delete transactions:

```sql
-- Executed as java_backend_app: REJECTED
DELETE FROM transaction_records WHERE transaction_id = 101;
-- Result: ERROR: permission denied for table transaction_records
```
The database blocks the deletion, enforcing security at the database engine level.

---

### 4. Revoking Permissions (REVOKE)
If business requirements change and we want to lock down the table, revoking insert access is simple:

```sql
REVOKE INSERT ON TABLE transaction_records FROM java_backend_app;
```
Now, any insert commands attempted by `java_backend_app` will be rejected.

---

## Summary
-   **DCL (Data Control Language)** manages database security, users, and permissions.
-   Core commands are **`GRANT`** (add permissions) and **`REVOKE`** (remove permissions).
-   **The Principle of Least Privilege** requires that applications connect to the database using roles with the absolute minimum access required for their features.
-   PostgreSQL manages security configurations using **Roles** that can be assigned to login users.

---

## Additional Resources
-   [PostgreSQL GRANT Clause Documentation](https://www.postgresql.org/docs/current/sql-grant.html)
-   [PostgreSQL Role-Based Access Security Model](https://www.postgresql.org/docs/current/user-manag.html)
-   [OWASP: Database Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Database_Security_Cheat_Sheet.html)
