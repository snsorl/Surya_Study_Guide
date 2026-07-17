# SQL Sublanguages Overview

## Learning Objectives
- Define the five primary SQL sublanguages (DDL, DML, DQL, DCL, TCL).
- Identify the core commands associated with each sublanguage.
- Explain the specific domain of control for each sublanguage.
- Contrast schema structure modification (DDL) with data value manipulation (DML).

---

## Why This Matters
When developers speak of "writing SQL," they are actually referring to using several different sub-dialects of the database language. If you are configuring database structures (like creating a new table), you are doing something fundamentally different than if you are query-filtering data or setting user permissions.

By dividing SQL into five specialized sublanguages, the database environment separates structural management from daily operations and security:
-   **DDL** defines the "shape" of your data.
-   **DML** and **DQL** manipulate and query the data.
-   **DCL** secures the database.
-   **TCL** manages transaction stability.

Understanding these sublanguages allows you to organize your database scripts, configure security policies, and manage database connection properties in your backend Java applications.

---

## The Concept

SQL is unified, but it is organized into five functional sublanguages:

### 1. DDL (Data Definition Language)
DDL commands manage the **structure** or **schema** of the database. When you run DDL commands, you are modifying the database blueprint—creating, changing, or deleting tables, columns, indexes, and schemas.
-   *Primary Commands:* `CREATE`, `ALTER`, `DROP`, `TRUNCATE`.
-   *Execution Detail:* In most databases (like Oracle and PostgreSQL), DDL commands commit changes to disk immediately.

### 2. DML (Data Manipulation Language)
DML commands manage the **data values** stored inside the tables. These commands allow you to insert new records, modify existing values, or delete rows.
-   *Primary Commands:* `INSERT`, `UPDATE`, `DELETE`.
-   *Execution Detail:* DML changes are temporary until they are formally finalized by a transaction control command.

### 3. DQL (Data Query Language)
DQL is used to **retrieve** data from the database. It is the most frequently used sublanguage for full-stack developers.
-   *Primary Commands:* `SELECT`.
-   *Execution Detail:* DQL operations are read-only; they do not alter the database schema or values.

### 4. DCL (Data Control Language)
DCL commands manage **permissions and security**. They control which users, applications, or backend services are allowed to execute operations on specific tables or schemas.
-   *Primary Commands:* `GRANT`, `REVOKE`.
-   *Execution Detail:* Critical for securing database connections in production environments.

### 5. TCL (Transaction Control Language)
TCL commands manage **transactions**. A transaction is a group of DML operations executed as a single unit. TCL allows you to save changes permanently, discard changes, or set recovery savepoints.
-   *Primary Commands:* `COMMIT`, `ROLLBACK`, `SAVEPOINT`.
-   *Execution Detail:* Critical for maintaining database consistency during application errors.

---

## Sublanguage Comparison Table

| Sublanguage | Acronym | Core Commands | Purpose |
|---|---|---|---|
| **Data Definition** | **DDL** | `CREATE`, `ALTER`, `DROP`, `TRUNCATE` | Defines and modifies database schemas and structures. |
| **Data Manipulation**| **DML** | `INSERT`, `UPDATE`, `DELETE` | Enters, modifies, and deletes row values. |
| **Data Query** | **DQL** | `SELECT` | Retrieves specific subsets of data. |
| **Data Control** | **DCL** | `GRANT`, `REVOKE` | Controls access permissions for database objects. |
| **Transaction Control**| **TCL** | `COMMIT`, `ROLLBACK`, `SAVEPOINT` | Manages transaction execution boundaries. |

---

## Summary
-   SQL is divided into **five sublanguages** to manage different database responsibilities.
-   **DDL** defines the database schema structure (tables, columns).
-   **DML** and **DQL** write and query row-level data.
-   **DCL** secures data by granting/revoking permissions.
-   **TCL** manages transactions, ensuring database reliability.

---

## Additional Resources
-   [GeeksforGeeks: SQL Sublanguages](https://www.geeksforgeeks.org/sql-ddl-dcl-dml-dql-tcl-commands/)
-   [W3Schools: SQL Commands Reference](https://www.w3schools.com/sql/sql_quickref.asp)
-   [Standard SQL Syntax and Command Classification](https://www.sqlite.org/lang.html)
