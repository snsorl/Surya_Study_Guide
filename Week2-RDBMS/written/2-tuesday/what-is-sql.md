# What is SQL?

## Learning Objectives
- Define SQL and describe its purpose in relational database management systems.
- Explain the distinction between declarative (SQL) and imperative (Java) programming models.
- Outline the history and evolution of SQL standards.
- Explain the role SQL plays in modern enterprise applications.

---

## Why This Matters
For the first week of this training program, you wrote Java programs that kept their data in local memory (using arrays, lists, or map collections). However, local memory is volatile: as soon as your Java application restarts or crashes, all data is lost. 

In enterprise environments, data is the most valuable asset. Applications must store customer accounts, financial ledgers, and transaction records in a way that is persistent, structured, and easily searchable. To do this, we use relational databases.

However, Java is an Object-Oriented programming language; it does not naturally know how to talk directly to database storage disks. To bridge this gap, we use **SQL (Structured Query Language)**. SQL is the universal language of databases. As a full-stack developer, writing efficient SQL queries is a core skill that directly impacts application performance, user experience, and storage efficiency.

---

## The Concept

### 1. What is SQL?
**Structured Query Language (SQL)** is a standardized programming language used to manage, query, and manipulate data stored in a Relational Database Management System (RDBMS). 

SQL allows you to:
- Create and modify database structures (schemas, tables, columns).
- Insert, update, and delete records.
- Retrieve specific subsets of data using complex filtering, sorting, and aggregation rules.
- Manage user permissions and transaction boundaries.

### 2. Declarative vs. Imperative Programming Models
The most important shift when moving from Java to SQL is moving from an **imperative** model to a **declarative** model.

-   **Imperative Programming (Java):** You write step-by-step instructions telling the computer *how* to achieve a result. You manage loops, pointers, memory allocation, and search algorithms manually.
-   **Declarative Programming (SQL):** You describe *what* data you want to retrieve or *what* changes you want to make, without specifying *how* the computer should physically retrieve it.

In SQL, you do not write loops (`for` or `while`), nor do you manage binary search trees or file pointers. You simply declare the conditions (e.g., *"give me all orders over $100 from users in Texas"*). The RDBMS's internal **Query Optimizer** analyzes the database schema, indexes, and storage locations to determine the most efficient execution plan automatically.

### 3. History and Standardization
SQL was originally developed at IBM in the early 1970s by Donald D. Chamberlin and Raymond F. Boyce. It was designed to implement Edgar F. Codd's mathematical relational database model. Originally called **SEQUEL** (Structured English Query Language), it was later shortened to **SQL** due to trademark issues.

In 1986, the American National Standards Institute (ANSI) and the International Organization for Standardization (ISO) adopted SQL as a formal standard. The standard has evolved over the years:
-   **SQL-86 & SQL-89:** Established basic syntax for schemas and queries.
-   **SQL-92:** The foundational standard that remains widely supported by modern database engines. It introduced explicit JOIN syntax and schema manipulation.
-   **SQL:1999 & SQL:2003:** Added support for object-relational features, XML integration, and window functions.
-   **Modern standards (up to SQL:2023):** Introduce JSON integration, graph query features, and support for dimensional arrays.

*Note:* While the ANSI/ISO SQL standards exist, almost every major RDBMS (PostgreSQL, Oracle, MySQL, SQL Server) implements its own custom dialect, adding proprietary extensions to the standard syntax.

### 4. SQL in Modern Applications
In a standard full-stack request flow, SQL represents the bridge between the backend server and the database engine. When a user requests data, the backend server constructs a SQL query, transmits it over a TCP connection to the RDBMS, reads the resulting rows, converts those rows into Java objects, and sends them back to the browser.

---

## Code Example

To illustrate the declarative nature of SQL, let's look at how we would achieve the same task (finding active users over age 18) in Java (Imperative) vs. SQL (Declarative).

### Java (Imperative)
```java
// We must manually loop through a list, check conditions, and add matches to a new list
List<User> activeAdults = new ArrayList<>();
for (User user : allUsers) {
    if (user.getAge() >= 18 && user.isActive()) {
        activeAdults.add(user);
    }
}
```

### SQL (Declarative)
```sql
-- We simply declare the columns we want, the source table, and the filter conditions
SELECT first_name, last_name 
FROM users 
WHERE age >= 18 
  AND is_active = true;
```

Notice that in the SQL version, we do not care *how* the database searches the table (whether it scans line-by-line or uses a pre-built index); we only specify the criteria of the final result.

---

## Summary
- **SQL** is the standard language used to interact with Relational Database Management Systems.
- Unlike Java (which is **imperative**), SQL is a **declarative** language: you define *what* you want, and the database engine decides *how* to get it.
- Although standards like **SQL-92** establish standard syntax, database engines use custom dialects.
- SQL enables persistence, bridging the gap between temporary application memory and permanent storage disk.

---

## Additional Resources
- [W3Schools SQL Tutorial](https://www.w3schools.com/sql/)
- [ANSI SQL Standards Overview](https://en.wikipedia.org/wiki/ISO/IEC_9075)
- [PostgreSQL SQL Language Syntax Docs](https://www.postgresql.org/docs/current/sql.html)
