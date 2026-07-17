# Introduction to Relational Database Management Systems (RDBMS)

## Learning Objectives
- Define the Relational Model and explain Edgar F. Codd's contribution to database science.
- Identify the core mathematical components of the Relational Model (Relations, Tuples, Attributes) and map them to their physical SQL equivalents (Tables, Rows, Columns).
- Explain the significance of Codd's Twelve Rules for relational database classification.
- Analyze the modern RDBMS software landscape, distinguishing between commercial and open-source database engines.

---

## Why This Matters
Before the invention of the Relational Model, computer systems stored data in flat text files or complex hierarchical tree structures. Finding a specific customer record required writing custom software search loops that navigated through files step-by-step. If you changed how the files were structured on disk, you had to rewrite all your application programs. This was known as logical and physical data dependence.

Relational databases changed everything by separating how data is physically stored on disk from how it is logically viewed by the developer. 

By modeling data as mathematically structured tables, relational databases allowed developers to write simple, standardized queries that run independently of storage details. Today, Relational Database Management Systems (RDBMS) run the data storage layers of virtually all major enterprise systems, handling everything from banking transactions to social media user records.

---

## The Concept

### 1. Edgar F. Codd and the Relational Model
In 1970, an IBM computer scientist named **Edgar F. Codd** published a landmark paper titled *"A Relational Model of Data for Large Shared Data Banks"*. 

Codd proposed that all data should be organized into mathematical structures called **relations** (which we call tables). He asserted that database systems should allow users to retrieve data based on its content, not its physical address on disk. This model is built on set theory and predicate logic.

### 2. Codd's Twelve Rules
In 1985, Codd expanded his model by publishing **Twelve Rules** (numbered 0 to 12, making thirteen rules in total) that define what qualifies as a true Relational Database Management System.

Some of the most important rules include:
-   **Rule 1: The Information Rule:** All information in the database must be represented in one and only one way: as values in tables.
-   **Rule 2: Guaranteed Access Rule:** Every individual piece of data must be logically accessible by combining a table name, a primary key value, and a column name.
-   **Rule 3: Systematic Treatment of Null Values:** The database must support a systematic way to represent missing or inapplicable information (NULL), independent of data types.
-   **Rule 8: Physical Data Independence:** Applications must remain unaffected when the physical storage structures (file formats, indexing methods) are changed.
-   **Rule 9: Logical Data Independence:** Applications must remain unaffected when logical changes are made to tables (such as splitting a table or adding a column).

No database system implements all 12 rules with absolute mathematical perfection, but engines like PostgreSQL and Oracle come very close.

### 3. Core Terminology
While database academic papers use formal mathematical terms, SQL developers use practical physical terms:

| Mathematical Term (Relational Theory) | Physical Term (SQL Database) | Description |
|---|---|---|
| **Relation** | **Table** | A two-dimensional grid of rows and columns. |
| **Tuple** | **Row / Record** | A single horizontal entry in a table, representing an instance of an entity. |
| **Attribute** | **Column / Field** | A vertical division of a table, representing a specific property of the entity. |
| **Domain** | **Data Type** | The set of permissible values for a given attribute (e.g., integers, dates). |

---

## Visual Model of a Relation

Below is a visual representation of a standard `customers` table showing how physical database terms map to database components:

```
+-------------------------------------------------------------+  <-- Table Schema
| CUSTOMERS (Table / Relation)                                |
+------------------+------------------+-----------------------+
| customer_id (PK) | first_name       | email_address         |  <-- Columns / Attributes
| [INT]            | [VARCHAR]        | [VARCHAR]             |  <-- Domains / Data Types
+------------------+------------------+-----------------------+
| 1                | Alice            | alice@email.com       |  <-- Row / Tuple / Record
| 2                | Bob              | bob@email.com         |
| 3                | Charlie          | charlie@email.com     |
+------------------+------------------+-----------------------+
```

---

## The Modern RDBMS Landscape
The enterprise RDBMS market is divided into two primary categories:

### 1. Open Source Databases (Free & Highly Extensible)
-   **PostgreSQL:** Known as the most advanced open-source database. It is highly compliant with SQL standards, supports advanced data structures, and has a robust community. (Our primary database for this program).
-   **MySQL:** The world's most popular open-source database. Known for speed and ease of use; commonly used in web applications.
-   **MariaDB:** A community-developed fork of MySQL created by the original developers to keep it open-source.

### 2. Commercial / Proprietary Databases (Enterprise Support)
-   **Oracle Database:** The industry standard for massive enterprise applications. Known for handling extremely large data volumes, high availability, and advanced security, but carries significant licensing costs.
-   **Microsoft SQL Server:** Widely used in corporate environments, particularly those built on Microsoft .NET frameworks.
-   **IBM DB2:** Common in enterprise mainframe environments and legacy financial systems.

---

## Summary
-   The **Relational Model** was created by Edgar F. Codd in 1970 to isolate application code from physical storage structures.
-   Data is represented logically as **Tables** (Relations), composed of **Rows** (Tuples) and **Columns** (Attributes).
-   **Codd's Twelve Rules** define the standards for classifying database engines as relational.
-   The modern RDBMS landscape contains both dominant open-source engines (**PostgreSQL, MySQL**) and commercial leaders (**Oracle, SQL Server**).

---

## Additional Resources
-   [Codd's 1970 Relational Paper - ACM Archive](https://dl.acm.org/doi/10.1145/362384.362685)
-   [Edgar F. Codd Biography - Turing Award](https://amturing.acm.org/award_winners/codd_1000892.cfm)
-   [DB-Engines Ranking of Relational Databases](https://db-engines.com/en/ranking/relational+dbms)
