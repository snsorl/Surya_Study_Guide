# Interview Questions: Week 2 â€” RDBMS Foundations

> This bank covers all five days of Week 2. Questions follow the 70-25-5 difficulty rule per day.
> Use the hidden answer blocks for self-quizzing. Aim to answer each question before revealing.

---

## Day 1 â€” Monday: TDD, JUnit 5, and Full-Stack Architecture

---

### Beginner â€” Recall and Definition

---

### Q1: What does TDD stand for, and what is the core principle behind it?

**Keywords:** Test-Driven Development, failing test, implementation

<details>
<summary>Click to Reveal Answer</summary>

TDD stands for Test-Driven Development. The core principle is to write a failing test before writing any production code. The developer only writes the minimum code needed to make that test pass, then refactors. This ensures every piece of production code is covered by a test from the start.

</details>

---

### Q2: What are the three phases of the TDD Red-Green-Refactor cycle?

**Keywords:** Red, Green, Refactor, failing, passing

<details>
<summary>Click to Reveal Answer</summary>

- **Red:** Write a test that fails because the feature does not exist yet.
- **Green:** Write the minimum production code needed to make the test pass.
- **Refactor:** Clean up the code â€” remove duplication, improve readability â€” while keeping all tests passing.

</details>

---

### Q3: What does the "Arrange" phase in the AAA pattern do?

**Keywords:** Arrange, preconditions, inputs, setup

<details>
<summary>Click to Reveal Answer</summary>

The Arrange phase sets up all the preconditions for the test. This includes creating objects, defining input values, configuring mocks or stubs, and establishing the initial state needed before the method under test is called.

</details>

---

### Q4: What does the "Act" phase in the AAA pattern do?

**Keywords:** Act, method under test, invoke, single action

<details>
<summary>Click to Reveal Answer</summary>

The Act phase calls the single method or unit of code being tested. It is a single line of execution that triggers the behavior you want to verify. The result is typically captured in a variable for use in the Assert phase.

</details>

---

### Q5: What does the "Assert" phase in the AAA pattern do?

**Keywords:** Assert, expected, actual, verification, outcome

<details>
<summary>Click to Reveal Answer</summary>

The Assert phase verifies that the outcome of the Act phase matches the expected result. JUnit 5 assertions such as `assertEquals`, `assertTrue`, and `assertThrows` are used here to confirm the code behaved correctly.

</details>

---

### Q6: Name three JUnit 5 annotations and explain what each one does.

**Keywords:** @Test, @BeforeEach, @AfterEach, lifecycle, annotation

<details>
<summary>Click to Reveal Answer</summary>

- **@Test:** Marks a method as a test case that JUnit will execute.
- **@BeforeEach:** Marks a method to run before every individual test method â€” used for resetting shared state.
- **@AfterEach:** Marks a method to run after every individual test method â€” used for cleanup.
- **@BeforeAll:** Marks a static method to run once before all tests in the class.
- **@AfterAll:** Marks a static method to run once after all tests in the class have completed.

</details>

---

### Q7: What is the difference between `@BeforeAll` and `@BeforeEach` in JUnit 5?

**Keywords:** @BeforeAll, @BeforeEach, static, once, each test

<details>
<summary>Click to Reveal Answer</summary>

`@BeforeAll` runs once before any test in the class executes. It must be a `static` method (unless `@TestInstance(Lifecycle.PER_CLASS)` is used). It is suitable for expensive shared setup like starting a server.

`@BeforeEach` runs before every individual test method. It is non-static and is used to reset state that each test should start fresh with.

</details>

---

### Q8: What JUnit 5 assertion would you use to verify that a method throws an `IllegalArgumentException`?

**Keywords:** assertThrows, exception type, lambda, executable

<details>
<summary>Click to Reveal Answer</summary>

You would use `assertThrows(IllegalArgumentException.class, () -> { methodUnderTest(invalidInput); })`. The first argument is the expected exception class; the second is a lambda containing the code that should throw it. The assertion passes only if that specific exception is thrown.

</details>

---

### Q9: In a full-stack Java application, list the layers from the browser to the database.

**Keywords:** Frontend, REST Controller, Service, DAO, JDBC, RDBMS

<details>
<summary>Click to Reveal Answer</summary>

The standard request flow is:

1. **Frontend (Browser / HTML / JavaScript)** â€” presents the UI and sends HTTP requests.
2. **REST Controller (API Layer)** â€” receives HTTP requests and maps them to service calls.
3. **Service / Business Logic Layer** â€” applies business rules and orchestrates data.
4. **Data Access Layer / DAO (JDBC)** â€” executes SQL statements against the database.
5. **RDBMS (PostgreSQL / MySQL)** â€” stores and retrieves the persisted data.

</details>

---

### Q10: What is the primary benefit of structuring tests with the AAA pattern?

**Keywords:** readability, clarity, maintainability, failure isolation

<details>
<summary>Click to Reveal Answer</summary>

The AAA pattern improves test readability and maintainability. When a test fails, the three-phase structure makes it immediately clear which phase caused the failure â€” whether setup was wrong (Arrange), the call was incorrect (Act), or the expectation was wrong (Assert). This speeds up debugging.

</details>

---

### Q11: What is `assertEquals` used for in JUnit 5?

**Keywords:** assertEquals, expected, actual, equality, assertion

<details>
<summary>Click to Reveal Answer</summary>

`assertEquals(expected, actual)` asserts that two values are equal. If they are not, the test fails and JUnit reports both the expected and actual values, making the mismatch easy to diagnose. It uses `.equals()` for object comparison.

</details>

---

### Intermediate â€” Application and Scenario

---

### Q12: A trainee writes a test that passes even when the production code is deleted. What went wrong, and how do you fix it?

**Keywords:** false positive, no assertion, Act, Assert, test validity

**Hint:** Think about what happens if the Assert phase is missing or always evaluates to true.

<details>
<summary>Click to Reveal Answer</summary>

This is a false positive â€” the test never actually verifies anything. Common causes include a missing `assertEquals` or an assertion that always evaluates to true regardless of output (e.g., `assertTrue(true)`). To fix it, ensure the Assert phase checks the actual return value of the method under test against a meaningful expected value. Running the test against code that you know is broken (the Red phase) is the best way to confirm a test is valid.

</details>

---

### Q13: When is TDD NOT the best approach, and why?

**Keywords:** exploratory, prototype, UI, cost-benefit, pragmatic

**Hint:** Consider scenarios where requirements are entirely unknown or the feature is purely visual.

<details>
<summary>Click to Reveal Answer</summary>

TDD is less appropriate when:
- **Requirements are completely unknown** â€” if you do not know what the output should be, you cannot write a meaningful failing test.
- **Pure UI / visual work** â€” testing the exact pixel layout of a component is impractical with unit tests.
- **Throwaway prototypes** â€” when the goal is rapid exploration and the code will be discarded.
- **Time-constrained scripts** â€” one-time data migration scripts may not justify the overhead.

In enterprise settings, TDD is applied selectively based on the criticality and longevity of the code being written.

</details>

---

### Q14: A junior developer asks why you should write the test before the implementation. What do you tell them?

**Keywords:** specification, design, coverage, confidence, verification

<details>
<summary>Click to Reveal Answer</summary>

Writing the test first forces you to think about the public contract and expected behavior of the code before worrying about implementation details. It acts as a living specification. It also guarantees the test is valid â€” if you write the test after the code, you can never be sure the test actually detects a failure. Writing it first and watching it fail (Red) proves it is genuinely testing something.

</details>

---

### Q15: How does the full-stack architecture diagram help a trainee understand where their Java code fits in a real application?

**Keywords:** N-tier, layer responsibility, database, REST, mental model

<details>
<summary>Click to Reveal Answer</summary>

The full-stack diagram provides a mental model of the complete data flow â€” from the user's browser, through the REST API, into the service and DAO layers, down to the database. For a trainee focused on Java, it clarifies that Java code primarily owns the business logic and database access layers. It also sets expectations for the coming weeks: REST APIs (Week 3), Spring (Week 4), etc. Without this picture, trainees write isolated code without understanding where it connects in a production system.

</details>

---

### Advanced â€” System and Edge Case

---

### Q16: When AI generates unit tests for you, what specific risks must you validate before committing that code?

**Keywords:** hallucination, false positive, coverage, context, verification

<details>
<summary>Click to Reveal Answer</summary>

AI-generated tests carry several risks:
- **Hallucinated behavior:** The AI may generate a test that asserts an incorrect expected value, producing a test that passes but tests the wrong thing.
- **Missing edge cases:** AI may cover the happy path but miss null inputs, boundary values, or exception scenarios.
- **Shallow coverage:** Tests may call methods without asserting anything meaningful about the outcome.
- **Context blindness:** AI does not know your business rules â€” it can only infer from method signatures and names.

Mitigation: run generated tests against a deliberately broken implementation (Red phase) to confirm each test actually detects failures. Never trust AI-generated tests without this validation step.

</details>

---

## Day 2 — Tuesday: SQL Fundamentals, RDBMS Concepts, and ERDs

---

### Beginner — Recall and Definition

---

### Q17: What does SQL stand for, and what makes it a declarative language?

**Keywords:** Structured Query Language, declarative, what not how, relational

<details>
<summary>Click to Reveal Answer</summary>

SQL stands for Structured Query Language. It is declarative because you describe *what* data you want, not *how* to retrieve it. The database engine determines the execution plan. For example, SELECT * FROM orders WHERE status = 'PENDING' states the desired result without specifying how to search the table.

</details>

---

### Q18: What is an RDBMS? Give two examples.

**Keywords:** Relational Database Management System, tables, rows, columns, PostgreSQL

<details>
<summary>Click to Reveal Answer</summary>

An RDBMS (Relational Database Management System) is software that stores data in structured tables made up of rows (tuples) and columns (attributes), enforcing relationships between them. It implements Codd's relational model.

Examples: **PostgreSQL**, **MySQL**, Oracle Database, Microsoft SQL Server.

</details>

---

### Q19: What is the difference between a row and a column in a relational table?

**Keywords:** row, tuple, record, column, attribute, schema

<details>
<summary>Click to Reveal Answer</summary>

A **column** (attribute) defines a single field in the table's schema — it has a name and a data type (e.g., email VARCHAR(255)). A **row** (tuple or record) is a single data entry in the table, containing a value for each column. A table with 5 columns and 100 rows has 500 individual values.

</details>

---

### Q20: What is NULL in SQL, and how is it different from an empty string or zero?

**Keywords:** NULL, unknown, missing, three-valued logic, IS NULL

<details>
<summary>Click to Reveal Answer</summary>

NULL in SQL represents an unknown or absent value — it is not the same as an empty string '' or the number  . NULL is not equal to anything, including itself (NULL = NULL evaluates to UNKNOWN, not TRUE). To check for NULL, you must use IS NULL or IS NOT NULL, not the = operator. This is called three-valued logic: TRUE, FALSE, and UNKNOWN.

</details>

---

### Q21: List four PostgreSQL numeric data types and when you would use each.

**Keywords:** INT, DECIMAL, FLOAT, NUMERIC, precision, financial

<details>
<summary>Click to Reveal Answer</summary>

- **INT (INTEGER):** Whole numbers — use for IDs, counts, quantities (e.g., quantity INT).
- **DECIMAL / NUMERIC:** Exact decimal numbers — use for financial values like prices and balances where rounding errors are unacceptable.
- **FLOAT / DOUBLE PRECISION:** Approximate floating-point — use for scientific measurements where small rounding errors are acceptable.
- **SERIAL:** Auto-incrementing integer — use for surrogate primary keys (backed by a sequence).

</details>

---

### Q22: What is an Entity-Relationship Diagram (ERD), and what three things does it model?

**Keywords:** ERD, entity, attribute, relationship, cardinality

<details>
<summary>Click to Reveal Answer</summary>

An ERD (Entity-Relationship Diagram) is a visual blueprint of a database schema. It models three things:

1. **Entities** — objects or concepts that have data stored about them (e.g., Customer, Order, Product).
2. **Attributes** — properties of each entity (e.g., Customer has name, email, address).
3. **Relationships** — how entities connect to each other (e.g., a Customer *places* many Orders), including cardinality (one-to-one, one-to-many, many-to-many).

</details>

---

### Q23: What is a one-to-many relationship? Give a real-world example.

**Keywords:** one-to-many, foreign key, parent, child, multiple

<details>
<summary>Click to Reveal Answer</summary>

A one-to-many relationship means one record in Table A can be associated with many records in Table B, but each record in Table B belongs to only one record in Table A. It is implemented by placing a foreign key on the "many" side.

Example: One **Customer** can place many **Orders**, but each Order belongs to exactly one Customer. The orders table holds a customer_id foreign key referencing the customers table.

</details>

---

### Q24: What is a many-to-many relationship, and how is it physically implemented in a relational database?

**Keywords:** many-to-many, junction table, foreign keys, intersection

<details>
<summary>Click to Reveal Answer</summary>

A many-to-many relationship means records in Table A can relate to many records in Table B, and vice versa. It cannot be directly represented with a foreign key in either table.

It is implemented with a **junction (intersection) table** that holds foreign keys referencing both parent tables. For example, Students and Courses have a many-to-many relationship, implemented with an Enrollments table containing student_id and course_id.

</details>

---

### Q25: What is the crow's foot notation in an ERD?

**Keywords:** crow's foot, many, cardinality, minimum, maximum

<details>
<summary>Click to Reveal Answer</summary>

Crow's foot notation uses symbols on relationship lines to indicate cardinality:
- A **crow's foot** (three diverging lines) represents the "many" side of a relationship.
- A **single vertical line** represents "exactly one."
- A **circle** represents "zero" (optional participation).

Combined: a circle with a crow's foot means "zero or many"; a line with a crow's foot means "one or many."

</details>

---

### Q26: What is the purpose of using DBeaver when working with PostgreSQL?

**Keywords:** GUI, client, query, connection, visual schema

<details>
<summary>Click to Reveal Answer</summary>

DBeaver is a free, open-source database GUI client. It provides a visual interface to connect to PostgreSQL (and other databases), write and execute SQL queries, browse table schemas, view data, and manage database objects — without needing to use the command-line psql tool. It supports multiple database types and is widely used in enterprise settings.

</details>

---

### Q27: What does SELECT version() return in PostgreSQL, and why is it useful?

**Keywords:** version, PostgreSQL, verification, installation

<details>
<summary>Click to Reveal Answer</summary>

SELECT version() returns a string containing the full PostgreSQL version number (e.g., PostgreSQL 15.3), the operating system it was compiled for, and compiler information. It is used to verify that a PostgreSQL installation was successful and to confirm which version is running — important for compatibility with JDBC drivers and features.

</details>

---

### Q28: What is Chain-of-Thought (CoT) prompting?

**Keywords:** Chain-of-Thought, step-by-step, reasoning, prompt, quality

<details>
<summary>Click to Reveal Answer</summary>

Chain-of-Thought (CoT) prompting is a technique where you instruct an AI model to reason through a problem step-by-step before producing a final answer. Instead of asking "Write me a SQL query for X," you prompt: "First, identify the tables involved. Then determine the join condition. Then write the WHERE clause. Finally, write the complete query." CoT reduces errors in complex tasks by making the AI's reasoning process explicit and verifiable.

</details>

---

### Intermediate — Application and Scenario

---

### Q29: A developer designs a users table using FLOAT for the ccount_balance column. What is the risk, and what should they use instead?

**Keywords:** FLOAT, rounding error, DECIMAL, NUMERIC, financial

**Hint:** Consider how binary floating-point represents decimal fractions.

<details>
<summary>Click to Reveal Answer</summary>

FLOAT uses binary floating-point representation, which cannot exactly represent all decimal fractions. For example,  .1 + 0.2 in floating-point arithmetic does not equal exactly  .3. Over many financial transactions, these rounding errors accumulate and produce incorrect account balances.

The correct type is DECIMAL(precision, scale) or NUMERIC, which stores exact decimal values. For example, ccount_balance DECIMAL(15, 2) stores values up to 13 digits before the decimal point with exactly 2 decimal places, with no approximation.

</details>

---

### Q30: You are designing an ERD for a university system. How would you model the relationship between Students, Courses, and Enrollments?

**Keywords:** many-to-many, junction table, Enrollments, foreign key, cardinality

<details>
<summary>Click to Reveal Answer</summary>

Students and Courses have a many-to-many relationship — a student can enroll in many courses, and a course can have many students. To implement this:

1. Create a students table (PK: student_id).
2. Create a courses table (PK: course_id).
3. Create an enrollments junction table with:
   - student_id (FK referencing students)
   - course_id (FK referencing courses)
   - A composite PK on (student_id, course_id)
   - Optional attributes like enrollment_date, grade.

</details>

---

### Q31: How would you constrain AI output to produce valid SQL DDL, and why might you still need to verify it?

**Keywords:** output compliance, format, hallucination, verification, constraint

<details>
<summary>Click to Reveal Answer</summary>

To constrain AI output to SQL DDL, you specify the format explicitly in the prompt: "Respond with only valid PostgreSQL DDL. Do not include explanations or markdown fencing." You can also provide a schema template and ask the AI to fill it in.

Verification is still necessary because AI models can hallucinate valid-looking but semantically incorrect SQL — using a wrong data type, omitting a NOT NULL constraint, or generating a FK referencing a non-existent column. Always test generated DDL against a real database before treating it as correct.

</details>

---

### Advanced — System and Edge Case

---

### Q32: Explain why a NULL value in a column can silently break a NOT IN subquery. How do you defend against it?

**Keywords:** NULL, NOT IN, UNKNOWN, three-valued logic, NOT EXISTS

<details>
<summary>Click to Reveal Answer</summary>

In SQL three-valued logic, any comparison with NULL evaluates to UNKNOWN. When you write WHERE x NOT IN (SELECT col FROM t), if the subquery returns even one NULL value, the entire NOT IN condition evaluates to UNKNOWN for every outer row — returning zero rows regardless of the data. This is a silent data bug with no error message.

Defense: use NOT EXISTS instead of NOT IN when the subquery column may contain NULLs. NOT EXISTS is NULL-safe because it checks for the presence or absence of rows, not value equality. Alternatively, add WHERE col IS NOT NULL inside the subquery.

</details>

---

## Day 3 — Wednesday: Keys, Constraints, and SQL Sublanguages

---

### Beginner — Recall and Definition

---

### Q33: What is a Primary Key, and what two constraints does it enforce?

**Keywords:** PRIMARY KEY, NOT NULL, UNIQUE, identifier, surrogate

<details>
<summary>Click to Reveal Answer</summary>

A Primary Key is a column (or group of columns) that uniquely identifies each row in a table. It enforces two constraints simultaneously:
1. **NOT NULL** — the column cannot contain a NULL value.
2. **UNIQUE** — no two rows can have the same value in this column.

A table can have only one Primary Key. Surrogate keys (auto-incrementing integers) are commonly used when no natural unique identifier exists.

</details>

---

### Q34: What is the difference between a Surrogate Key and a Natural Key?

**Keywords:** surrogate key, natural key, business meaning, SERIAL, stability

<details>
<summary>Click to Reveal Answer</summary>

A **natural key** is a column whose value has inherent business meaning and already uniquely identifies a record (e.g., a Social Security Number, ISBN, or email address).

A **surrogate key** is a system-generated value with no business meaning (e.g., an auto-incrementing integer id SERIAL). Surrogate keys are preferred because natural keys can change (a person changes their email) whereas a surrogate key is stable and under the system's control.

</details>

---

### Q35: What is a Foreign Key, and what does it enforce?

**Keywords:** FOREIGN KEY, reference, parent table, child table, referential integrity

<details>
<summary>Click to Reveal Answer</summary>

A Foreign Key is a column in a child table that references the Primary Key (or a UNIQUE column) in a parent table. It enforces **referential integrity** — guaranteeing that the value in the FK column always matches an existing value in the referenced parent table. Inserting a child row with an FK value that does not exist in the parent is rejected by the database.

</details>

---

### Q36: What is Referential Integrity, and why does it matter?

**Keywords:** referential integrity, foreign key, orphan row, consistency, enforcement

<details>
<summary>Click to Reveal Answer</summary>

Referential integrity is the guarantee that every foreign key value in a child table references a valid, existing row in the parent table. It prevents orphaned records — for example, a loan record referencing a member_id that no longer exists in the members table. Without it, data becomes inconsistent and queries return meaningless results. It is enforced by the database engine through FOREIGN KEY constraints.

</details>

---

### Q37: What is a Composite Primary Key, and when would you use one?

**Keywords:** composite key, multiple columns, junction table, natural combination

<details>
<summary>Click to Reveal Answer</summary>

A Composite Primary Key is a Primary Key formed by combining two or more columns. No single column is unique on its own, but the combination is unique.

Use case: junction (intersection) tables in many-to-many relationships. For example, a loans table with member_id and ook_id as a composite PK ensures that the same member cannot borrow the same book twice simultaneously, while allowing both to appear in multiple other rows.

</details>

---

### Q38: What is a UNIQUE constraint, and how does it differ from a PRIMARY KEY?

**Keywords:** UNIQUE, NULL allowed, multiple, PRIMARY KEY, candidate key

<details>
<summary>Click to Reveal Answer</summary>

A UNIQUE constraint ensures no two rows in a column (or group of columns) have the same value. The key differences from PRIMARY KEY:
- A table can have only one PRIMARY KEY but multiple UNIQUE constraints.
- PRIMARY KEY implies NOT NULL; UNIQUE allows NULL values (since NULL is distinct from any value, including other NULLs).
- Both automatically create an index in PostgreSQL.

UNIQUE constraints are often used for candidate keys like email addresses.

</details>

---

### Q39: List the five SQL sublanguages and one statement that belongs to each.

**Keywords:** DDL, DML, DQL, DCL, TCL, sublanguage

<details>
<summary>Click to Reveal Answer</summary>

| Sublanguage | Stands For | Example Statement |
|---|---|---|
| DDL | Data Definition Language | CREATE TABLE |
| DML | Data Manipulation Language | INSERT INTO |
| DQL | Data Query Language | SELECT |
| DCL | Data Control Language | GRANT |
| TCL | Transaction Control Language | COMMIT |

</details>

---

### Q40: What does DDL stand for, and which statements does it include?

**Keywords:** Data Definition Language, CREATE, ALTER, DROP, TRUNCATE, schema

<details>
<summary>Click to Reveal Answer</summary>

DDL stands for **Data Definition Language**. It manages the structure of database objects (schema), not the data inside them. DDL statements include:
- CREATE — creates a new table, index, view, or other object.
- ALTER — modifies an existing object (e.g., adds a column).
- DROP — permanently deletes an object and all its data.
- TRUNCATE — removes all rows from a table quickly (DDL in most systems).

</details>

---

### Q41: What is the difference between DELETE (DML) and TRUNCATE (DDL)?

**Keywords:** DELETE, TRUNCATE, WHERE, triggers, row-level, transactional

<details>
<summary>Click to Reveal Answer</summary>

| Feature | DELETE | TRUNCATE |
|---|---|---|
| Sublanguage | DML | DDL |
| Selective | Yes (WHERE clause) | No (removes all rows) |
| Row-level triggers | Fires triggers | Does NOT fire row-level triggers |
| Speed on large tables | Slower (scans rows) | Much faster (page deallocation) |
| Transactional in PostgreSQL | Yes | Yes |
| Resets SERIAL sequence | No | Yes (optionally) |

</details>

---

### Q42: What does GRANT do, and which sublanguage does it belong to?

**Keywords:** GRANT, DCL, privilege, role, permission

<details>
<summary>Click to Reveal Answer</summary>

GRANT belongs to **DCL (Data Control Language)**. It gives a user or role specific privileges on a database object. For example:

`sql
GRANT SELECT, INSERT ON books TO app_user;
`

This allows pp_user to read and insert rows in the ooks table. The counterpart is REVOKE, which removes previously granted privileges.

</details>

---

### Q43: What does COMMIT do, and why is it important?

**Keywords:** COMMIT, TCL, transaction, persist, durable

<details>
<summary>Click to Reveal Answer</summary>

COMMIT belongs to **TCL (Transaction Control Language)**. It permanently saves all changes made during the current transaction to the database, making them durable and visible to other sessions. Without a COMMIT, changes are only visible within the current session and will be lost if the session ends abnormally.

</details>

---

### Q44: What is a SAVEPOINT, and how does it differ from a full ROLLBACK?

**Keywords:** SAVEPOINT, ROLLBACK TO, partial undo, transaction, checkpoint

<details>
<summary>Click to Reveal Answer</summary>

A SAVEPOINT creates a named checkpoint within an open transaction. ROLLBACK TO SAVEPOINT name undoes only the work done after that checkpoint, leaving earlier changes intact. The transaction remains open.

A full ROLLBACK (without SAVEPOINT) undoes all changes made since the beginning of the transaction and ends the transaction.

SAVEPOINT is useful when you want to attempt a risky operation and roll back only if it fails, without losing all other work in the transaction.

</details>

---

### Intermediate — Application and Scenario

---

### Q45: A developer deletes a parent row from a customers table, but the orders table still has rows referencing that customer. What happens, and how do you prevent orphaned data?

**Keywords:** foreign key violation, referential integrity, ON DELETE CASCADE, ON DELETE RESTRICT

**Hint:** Think about the FK constraint options available when defining the relationship.

<details>
<summary>Click to Reveal Answer</summary>

Without any FK constraint, the delete succeeds and the orders rows are left with an invalid customer_id — these are orphaned rows that break data integrity.

With a FK constraint, the behavior depends on the option chosen:
- **ON DELETE RESTRICT / NO ACTION (default):** The delete is rejected if child rows exist.
- **ON DELETE CASCADE:** The delete propagates and automatically deletes all matching child rows.
- **ON DELETE SET NULL:** The FK column in child rows is set to NULL.

The correct option depends on business requirements. RESTRICT is the safest default; CASCADE is appropriate when child data has no independent meaning without the parent.

</details>

---

### Q46: A junior developer wants to use email address as the primary key for a users table. What argument would you make for using a surrogate key instead?

**Keywords:** surrogate key, natural key, mutability, stability, performance

<details>
<summary>Click to Reveal Answer</summary>

Email addresses are a poor primary key because they are **mutable** — a user can change their email. When a natural key changes, every foreign key in every child table must also be updated, creating cascading changes across the database.

Surrogate keys (auto-incrementing integers like SERIAL) are stable — they never change once assigned. They are also more compact (4 bytes for INT vs. up to 255 bytes for VARCHAR), making joins and index lookups faster. Email can still be enforced as UNIQUE to prevent duplicates while remaining a non-PK attribute.

</details>

---

### Q47: Explain the difference between WHERE and HAVING in a SQL query, with an example of each.

**Keywords:** WHERE, HAVING, GROUP BY, aggregate, filter, pre-aggregation, post-aggregation

<details>
<summary>Click to Reveal Answer</summary>

WHERE filters individual rows **before** aggregation occurs. It cannot reference aggregate functions.

HAVING filters **groups** after GROUP BY and aggregation. It can reference aggregate functions.

Example:

`sql
-- WHERE: filter rows before grouping (only orders with amount > 10)
SELECT customer_id, SUM(amount)
FROM orders
WHERE amount > 10
GROUP BY customer_id;

-- HAVING: filter groups after aggregation (only customers with total > 500)
SELECT customer_id, SUM(amount) AS total
FROM orders
GROUP BY customer_id
HAVING SUM(amount) > 500;
`

</details>

---

### Advanced — System and Edge Case

---

### Q48: Explain what happens under the hood when PostgreSQL enforces a FOREIGN KEY constraint on an INSERT. What internal mechanisms are involved?

**Keywords:** index lookup, constraint check, transaction, rollback, MVCC

<details>
<summary>Click to Reveal Answer</summary>

When an INSERT with a FK value is executed, PostgreSQL performs a **constraint check** before writing the row:

1. It looks up the FK value against the index on the referenced column in the parent table (the PK or UNIQUE index).
2. If no matching row is found in the parent, the INSERT is immediately rejected with a foreign key violation error and the statement is rolled back.
3. If a match is found, the row is written within the current transaction (using MVCC — Multi-Version Concurrency Control), remaining invisible to other transactions until a COMMIT occurs.
4. If the parent row is being concurrently modified or deleted in another transaction, locking mechanisms prevent a race condition.

This check is efficient because the referenced column always has an index (PK or UNIQUE constraint), making the lookup an O(log n) index scan rather than a full table scan.

</details>

---

## Day 4 — Thursday: JDBC, Constraints, Aggregates, and the DAO Pattern

---

### Beginner — Recall and Definition

---

### Q49: What is JDBC, and what problem does it solve?

**Keywords:** JDBC, Java Database Connectivity, bridge, driver, java.sql

<details>
<summary>Click to Reveal Answer</summary>

JDBC (Java Database Connectivity) is a standard Java API in the java.sql package that provides a uniform interface for connecting Java applications to relational databases. Without JDBC, each database vendor would require a completely different API. With JDBC, the same Java code (using standard interfaces like Connection, Statement, and ResultSet) can work with PostgreSQL, MySQL, Oracle, and others simply by swapping the JDBC driver.

</details>

---

### Q50: What is the role of DriverManager.getConnection() in JDBC?

**Keywords:** DriverManager, getConnection, Connection, URL, credentials

<details>
<summary>Click to Reveal Answer</summary>

DriverManager.getConnection(url, user, password) is the entry point for establishing a physical connection to a database. The url string specifies the JDBC driver protocol, host, port, and database name (e.g., jdbc:postgresql://localhost:5432/mydb). It returns a Connection object, which is the foundation for all subsequent SQL operations.

</details>

---

### Q51: What is the difference between Statement and PreparedStatement in JDBC?

**Keywords:** Statement, PreparedStatement, parameterization, SQL Injection, pre-compiled

<details>
<summary>Click to Reveal Answer</summary>

Statement executes a raw SQL string that is built by concatenating user input. This is vulnerable to SQL Injection — an attacker can manipulate the SQL by injecting special characters.

PreparedStatement uses a pre-compiled SQL template with ? placeholders. User input is bound as typed parameters after compilation. The database engine treats the parameters as pure data, never as SQL syntax — making injection structurally impossible. PreparedStatement is always preferred for queries involving user input.

</details>

---

### Q52: How do you iterate through the rows of a JDBC ResultSet?

**Keywords:** ResultSet, next(), while loop, cursor, column

<details>
<summary>Click to Reveal Answer</summary>

You call s.next() in a while loop. Each call to s.next() advances the internal cursor to the next row and returns 	rue if a row is available, or alse when the result set is exhausted.

`java
while (rs.next()) {
    int id = rs.getInt("member_id");
    String name = rs.getString("name");
}
`

</details>

---

### Q53: What is the DAO (Data Access Object) design pattern?

**Keywords:** DAO, interface, implementation, separation of concerns, CRUD

<details>
<summary>Click to Reveal Answer</summary>

The DAO pattern separates database access logic from business logic. It defines a **interface** that specifies the CRUD (Create, Read, Update, Delete) contract, and a concrete **implementation class** that contains the actual JDBC code. Business and service layers depend on the interface — not the implementation — making the data access layer easily swappable (e.g., switching from PostgreSQL to an in-memory H2 database for testing) without modifying business code.

</details>

---

### Q54: What is the NOT NULL constraint in SQL?

**Keywords:** NOT NULL, mandatory, column, INSERT, reject

<details>
<summary>Click to Reveal Answer</summary>

The NOT NULL constraint on a column prevents a NULL value from being stored there. Any INSERT or UPDATE that attempts to assign NULL to a NOT NULL column is rejected with an error. It enforces that the field is mandatory — for example, a username column should always have a value.

</details>

---

### Q55: What is a CHECK constraint? Give an example.

**Keywords:** CHECK, boolean expression, validation, INSERT, reject

<details>
<summary>Click to Reveal Answer</summary>

A CHECK constraint allows you to define a custom validation rule using any SQL boolean expression. The database enforces the rule on every INSERT and UPDATE. If the expression evaluates to FALSE, the statement is rejected.

Example:
`sql
ALTER TABLE products ADD CONSTRAINT chk_price CHECK (price > 0);
`
This ensures no product can be stored with a zero or negative price.

</details>

---

### Q56: What is the DEFAULT constraint in PostgreSQL?

**Keywords:** DEFAULT, literal, expression, INSERT, NOW()

<details>
<summary>Click to Reveal Answer</summary>

The DEFAULT constraint specifies the value a column receives if no value is provided during an INSERT. The default can be a literal value or a function call. For example:

- created_at TIMESTAMPTZ DEFAULT NOW() — automatically records the insertion timestamp.
- status VARCHAR(20) DEFAULT 'ACTIVE' — sets new records to 'ACTIVE' by default.
- id SERIAL is shorthand for an integer column with a sequence-based DEFAULT.

</details>

---

### Q57: What is the COUNT aggregate function, and how does COUNT(*) differ from COUNT(column_name)?

**Keywords:** COUNT, aggregate, NULL, all rows, non-null

<details>
<summary>Click to Reveal Answer</summary>

COUNT is an aggregate function that returns the number of rows matching a query.

- **COUNT(*)** counts every row, including rows with NULL values in any column.
- **COUNT(column_name)** counts only the rows where the specified column is NOT NULL — rows with NULL in that column are excluded.

Example: if a loans table has 10 rows but 2 have a NULL eturn_date, then COUNT(return_date) returns 8 while COUNT(*) returns 10.

</details>

---

### Q58: What is the purpose of GROUP BY in a SQL query?

**Keywords:** GROUP BY, aggregate, partition, collapse, categories

<details>
<summary>Click to Reveal Answer</summary>

GROUP BY collapses rows that share the same value in the specified column(s) into a single group, then applies aggregate functions to each group. For example, GROUP BY customer_id with SUM(amount) calculates the total order value per customer, rather than summing all orders together.

Without GROUP BY, aggregate functions collapse all rows into a single result. With GROUP BY, you get one result row per unique value of the grouping column.

</details>

---

### Q59: What is the HAVING clause used for?

**Keywords:** HAVING, GROUP BY, aggregate filter, post-grouping, condition

<details>
<summary>Click to Reveal Answer</summary>

HAVING filters the results of a GROUP BY query based on aggregate conditions. Unlike WHERE (which filters individual rows before aggregation), HAVING filters grouped results after aggregation.

Example: HAVING COUNT(*) > 5 keeps only groups that contain more than 5 rows. You cannot put COUNT(*) > 5 in a WHERE clause because aggregation has not happened yet at that point.

</details>

---

### Q60: What is a ConnectionFactory in JDBC, and why is it useful?

**Keywords:** ConnectionFactory, singleton, connection string, credentials, reuse

<details>
<summary>Click to Reveal Answer</summary>

A ConnectionFactory (or DataSource) is a class that centralizes the logic for creating database connections. Instead of repeating the JDBC URL, username, and password in every DAO class, the factory encapsulates this in one place. Typically implemented as a singleton, it ensures consistent connection configuration and is the first refactoring step toward connection pooling. Credential management (avoiding hardcoded passwords) is also handled here.

</details>

---

### Intermediate — Application and Scenario

---

### Q61: A developer writes a DAO method that queries the database but never closes the Connection. What happens over time, and how do you fix it?

**Keywords:** resource leak, connection pool, exhaustion, try-with-resources, AutoCloseable

**Hint:** Think about what a Connection represents at the operating system level.

<details>
<summary>Click to Reveal Answer</summary>

Each JDBC Connection holds a physical network socket to the database server. If connections are never closed, the database server runs out of available connections. New connection requests fail with a "too many connections" error, causing the application to crash for all users.

The fix is to use **try-with-resources** (	ry (Connection conn = DriverManager.getConnection(...)) { ... }). Since Connection implements AutoCloseable, Java guarantees the connection is closed when the block exits, even if an exception is thrown. This applies to PreparedStatement and ResultSet as well.

</details>

---

### Q62: You need to write a DAO indById(int id) method that returns a Member object from the database. Walk through the JDBC steps.

**Keywords:** PreparedStatement, setInt, executeQuery, ResultSet, map, return

<details>
<summary>Click to Reveal Answer</summary>

`
1. Obtain a Connection from the ConnectionFactory.
2. Write the SQL: "SELECT * FROM members WHERE member_id = ?"
3. Create a PreparedStatement from the Connection.
4. Call pstmt.setInt(1, id) to bind the id parameter.
5. Execute: ResultSet rs = pstmt.executeQuery()
6. Check if rs.next() returns true.
7. If yes, extract columns: rs.getInt("member_id"), rs.getString("name"), etc.
8. Construct and return a Member object populated with those values.
9. If no row found, return null or throw a NotFoundException.
10. Close all resources (Connection, PreparedStatement, ResultSet) in finally or try-with-resources.
`

</details>

---

### Q63: Explain the difference between MIN, MAX, SUM, and AVG with a practical use case for each.

**Keywords:** MIN, MAX, SUM, AVG, aggregate, GROUP BY

<details>
<summary>Click to Reveal Answer</summary>

- **MIN(column):** Returns the smallest value. Use case: find the cheapest product in inventory.
- **MAX(column):** Returns the largest value. Use case: find the most recent order date.
- **SUM(column):** Adds up all values. Use case: calculate total revenue from all orders.
- **AVG(column):** Returns the arithmetic mean. Use case: calculate the average customer order value.

All four are commonly combined with GROUP BY to compute these statistics per category (e.g., per customer, per product category).

</details>

---

### Q64: When would you use ON DELETE CASCADE versus ON DELETE RESTRICT on a foreign key?

**Keywords:** CASCADE, RESTRICT, business rule, orphan, parent, child

<details>
<summary>Click to Reveal Answer</summary>

- **ON DELETE CASCADE** is appropriate when child rows have no independent meaning without the parent. Example: deleting a user account should automatically delete all their sessions, 
otifications, and preferences — those records are useless without the user.

- **ON DELETE RESTRICT** (the default) is appropriate when child rows may have independent value or when you want to prevent accidental parent deletion. Example: you should not be allowed to delete a product if there are existing order_items referencing it — those order items are part of financial records that must be preserved.

The choice is a **business rule decision**, not a technical one.

</details>

---

### Advanced — System and Edge Case

---

### Q65: Explain what GENERATED ALWAYS AS IDENTITY does in PostgreSQL and why it is preferred over SERIAL.

**Keywords:** GENERATED ALWAYS AS IDENTITY, SERIAL, sequence, SQL standard, override

<details>
<summary>Click to Reveal Answer</summary>

GENERATED ALWAYS AS IDENTITY is the SQL:2003 standard equivalent of SERIAL. Like SERIAL, it creates an auto-incrementing column backed by a sequence. The key differences:

- **Standard compliance:** GENERATED ALWAYS AS IDENTITY is part of the SQL standard; SERIAL is a PostgreSQL-specific shorthand that will not be removed but is considered legacy.
- **Stricter enforcement:** ALWAYS prevents manual insertion of values — any attempt to supply a value for the column is rejected with an error (use OVERRIDING SYSTEM VALUE to override explicitly). This prevents accidental sequence corruption.
- **GENERATED BY DEFAULT AS IDENTITY:** A looser variant that allows manual value insertion — useful when importing data with existing IDs.

PostgreSQL documentation recommends GENERATED ALWAYS AS IDENTITY for new schemas.

</details>

---

## Day 5 — Friday: JOINs, Subqueries, and SQL Injection Prevention

---

### Beginner — Recall and Definition

---

### Q66: What is an INNER JOIN? What rows does it return?

**Keywords:** INNER JOIN, match, both tables, exclude, join condition

<details>
<summary>Click to Reveal Answer</summary>

An INNER JOIN combines rows from two tables based on a matching condition in the ON clause. It returns **only the rows where a match exists in BOTH tables**. Rows that have no corresponding row in the other table are excluded from the result set entirely.

Example: joining members and loans on member_id returns only members who have at least one loan — members with no loans are excluded.

</details>

---

### Q67: What is a LEFT JOIN? How is it different from an INNER JOIN?

**Keywords:** LEFT JOIN, all left rows, NULL, unmatched, outer

<details>
<summary>Click to Reveal Answer</summary>

A LEFT JOIN (LEFT OUTER JOIN) returns **all rows from the left table**, plus the matching rows from the right table. Where no match exists in the right table, the right-side columns are filled with NULL.

Difference from INNER JOIN: an INNER JOIN excludes unmatched left-side rows; a LEFT JOIN preserves them with NULL right-side values. LEFT JOIN is used when you want to include records that may not have a related entry (e.g., all members, even those who have never taken a loan).

</details>

---

### Q68: What is a CROSS JOIN, and when might you use one?

**Keywords:** CROSS JOIN, Cartesian product, all combinations, test data, performance

<details>
<summary>Click to Reveal Answer</summary>

A CROSS JOIN returns the Cartesian product of two tables — every row from the left table is combined with every row from the right table. If the left has M rows and the right has N rows, the result has M x N rows.

Use cases:
- Generating all combinations of attributes for test data.
- Creating a calendar grid (e.g., all days combined with all employees).
- Combination pricing tables.

Warning: on large tables, CROSS JOIN produces massive result sets that can overwhelm memory. Use with care.

</details>

---

### Q69: What is the difference between an Equi-join and a Theta-join?

**Keywords:** equi-join, theta-join, equality, comparison operator, ON clause

<details>
<summary>Click to Reveal Answer</summary>

An **equi-join** uses the equality operator (=) in the join condition — matching rows where a column in one table equals a column in the other. This is the most common form of join.

A **theta-join** generalizes this to allow any comparison operator (<, >, !=, BETWEEN, etc.) in the join condition. For example, joining a products table to a discounts table where product.price BETWEEN discount.min_price AND discount.max_price is a theta-join.

</details>

---

### Q70: What is a subquery in SQL? Name the two main types.

**Keywords:** subquery, nested, correlated, non-correlated, WHERE, FROM

<details>
<summary>Click to Reveal Answer</summary>

A subquery is a SQL query nested inside another query (the outer query). It is enclosed in parentheses and can appear in the WHERE, FROM, or SELECT clauses.

Two main types:
1. **Non-correlated subquery:** Executes once independently; its result is passed to the outer query. Example: WHERE member_id IN (SELECT member_id FROM loans).
2. **Correlated subquery:** References a column from the outer query, causing it to re-execute for every row the outer query processes. More powerful but potentially slower.

</details>

---

### Q71: What is SQL Injection?

**Keywords:** SQL Injection, concatenation, malicious input, vulnerability, attacker

<details>
<summary>Click to Reveal Answer</summary>

SQL Injection is an attack where a malicious user supplies specially crafted input that, when concatenated into a SQL query string, changes the structure of the query. For example, entering ' OR '1'='1 into a login field can transform WHERE username = 'input' into WHERE username = '' OR '1'='1' — which is always true, bypassing authentication.

It is classified as A03: Injection in the OWASP Top 10 and is one of the oldest and most damaging web application vulnerabilities.

</details>

---

### Q72: How does PreparedStatement prevent SQL Injection?

**Keywords:** PreparedStatement, parameterization, pre-compiled, placeholder, literal data

<details>
<summary>Click to Reveal Answer</summary>

PreparedStatement pre-compiles the SQL template with ? placeholders before any user data is provided. When parameters are bound via setString(), setInt(), etc., the database engine treats the parameter values as **literal data**, not as SQL syntax. No parsing of the parameter values occurs — the SQL structure is already locked. An attacker injecting ' OR '1'='1 through a setString() call will find that the database treats the entire string (including quotes) as a plain text value to compare, not as SQL code.

</details>

---

### Q73: What is the OWASP Top 10?

**Keywords:** OWASP, Top 10, web application, security risks, awareness

<details>
<summary>Click to Reveal Answer</summary>

The OWASP Top 10 is a standard awareness document published by the **Open Worldwide Application Security Project (OWASP)**, a non-profit security foundation. It lists the 10 most critical web application security risks (most recent edition: 2021). The list is compiled from real-world vulnerability data and is widely used by developers, security teams, and auditors to prioritize security efforts. Examples: A01 Broken Access Control, A03 Injection (SQL Injection), A02 Cryptographic Failures.

</details>

---

### Q74: What is the difference between a scalar subquery and a table subquery?

**Keywords:** scalar subquery, table subquery, single value, multiple rows, FROM clause

<details>
<summary>Click to Reveal Answer</summary>

A **scalar subquery** returns exactly one row and one column — a single value. It can be used anywhere a single value is expected (SELECT list, WHERE comparison, etc.). Example: SELECT (SELECT MAX(amount) FROM orders) AS max_order.

A **table subquery** (or derived table) returns multiple rows and columns. It is used in the FROM clause and given an alias, acting like a temporary table. Example: FROM (SELECT customer_id, SUM(amount) AS total FROM orders GROUP BY customer_id) AS customer_totals.

</details>

---

### Q75: What does ON DELETE SET NULL do, and when is it appropriate?

**Keywords:** SET NULL, foreign key, parent deleted, nullable, optional relationship

<details>
<summary>Click to Reveal Answer</summary>

ON DELETE SET NULL on a foreign key constraint means that when a parent row is deleted, the FK column in all matching child rows is set to NULL (rather than cascading the delete or rejecting it).

This is appropriate when the relationship is optional — the child record can exist independently of the parent. Example: if an ssigned_manager_id FK is deleted because the manager left the company, setting it to NULL means the employee record is preserved but without an assigned manager. The FK column must be nullable for this to work.

</details>

---

### Intermediate — Application and Scenario

---

### Q76: Write the SQL to find all members who have never taken out a loan, using both a subquery approach and a LEFT JOIN approach.

**Keywords:** NOT IN, NOT EXISTS, LEFT JOIN, NULL, unmatched rows

<details>
<summary>Click to Reveal Answer</summary>

**Subquery approach (NOT IN):**
`sql
SELECT name FROM members
WHERE member_id NOT IN (SELECT member_id FROM loans);
`
Caution: if any member_id in loans is NULL, this returns zero rows due to three-valued logic.

**Safer subquery (NOT EXISTS):**
`sql
SELECT name FROM members m
WHERE NOT EXISTS (
    SELECT 1 FROM loans l WHERE l.member_id = m.member_id
);
`

**LEFT JOIN approach:**
`sql
SELECT m.name
FROM members m
LEFT JOIN loans l ON m.member_id = l.member_id
WHERE l.member_id IS NULL;
`
The LEFT JOIN includes all members; the WHERE l.member_id IS NULL keeps only those with no matching loan row.

</details>

---

### Q77: A developer is using string concatenation to build a SQL login query. Explain the specific attack vector and rewrite the query using PreparedStatement.

**Keywords:** concatenation, injection vector, PreparedStatement, setString, bypass

**Hint:** Show the vulnerable and secure versions side by side.

<details>
<summary>Click to Reveal Answer</summary>

**Vulnerable code:**
`java
String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);
`
Attack: username = "admin' --" turns the query into WHERE username = 'admin' --' AND password = '...'. The -- comments out the password check.

**Secure code:**
`java
String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
PreparedStatement pstmt = conn.prepareStatement(sql);
pstmt.setString(1, username);
pstmt.setString(2, password);
ResultSet rs = pstmt.executeQuery();
`
The database compiles the query structure first. The username and password values are bound as data — the ' and -- characters are treated as literal characters in a string comparison, not SQL syntax.

</details>

---

### Q78: When would you choose a subquery over a JOIN, and vice versa?

**Keywords:** subquery, JOIN, readability, performance, correlated, reuse

<details>
<summary>Click to Reveal Answer</summary>

**Prefer a JOIN when:**
- You need columns from both tables in the result set.
- Performance is critical on large datasets — JOINs are typically optimized well by query planners.
- You are finding records that have a matching related record.

**Prefer a subquery when:**
- You only need data from the outer table, and the inner query is purely for filtering.
- The logic is a clear "find X where a condition in Y is met" — a subquery expresses this intent more naturally.
- You need an aggregated value for comparison (e.g., WHERE price > (SELECT AVG(price) FROM products)).
- A correlated subquery is needed for row-by-row comparison.

In practice, the query planner often produces identical execution plans for logically equivalent JOINs and subqueries.

</details>

---

### Q79: A web application allows users to search for books by title using a text field. Describe two security measures you would implement in the data access layer.

**Keywords:** PreparedStatement, input validation, SQL Injection, OWASP, A03

<details>
<summary>Click to Reveal Answer</summary>

1. **Use PreparedStatement with a parameterized query:**
`java
String sql = "SELECT * FROM books WHERE title ILIKE ?";
pstmt.setString(1, "%" + searchTerm + "%");
`
This ensures the search term — including any injected SQL — is treated as a literal string.

2. **Input validation and sanitization:**
   - Define a maximum length for the search input (e.g., 200 characters) and reject inputs exceeding it.
   - Optionally, strip or reject characters that are unusual in a book title but common in injection payloads (e.g., ;, --, /*).
   - Note: input validation is a defense-in-depth measure, not a substitute for parameterized queries. PreparedStatement is the primary defense.

</details>

---

### Advanced — System and Edge Case

---

### Q80: Explain MVCC (Multi-Version Concurrency Control) in PostgreSQL and how it relates to the isolation level of transactions.

**Keywords:** MVCC, snapshot, concurrent, dirty read, isolation, version

<details>
<summary>Click to Reveal Answer</summary>

MVCC (Multi-Version Concurrency Control) is the mechanism PostgreSQL uses to allow multiple transactions to run concurrently without blocking each other with locks. Instead of locking a row when it is read, PostgreSQL keeps **multiple versions** of each row. Each transaction sees a consistent snapshot of the data as it existed at the start of the transaction (or statement, depending on the isolation level).

Implications:
- **Readers do not block writers** and vice versa — a SELECT never waits for an UPDATE to complete.
- **Dirty reads** (reading uncommitted changes from another transaction) are impossible in PostgreSQL's default isolation level (Read Committed).
- Higher isolation levels (Repeatable Read, Serializable) provide stronger guarantees by restricting which snapshot a transaction sees, at the cost of potential serialization failures.
- Old row versions are periodically cleaned up by PostgreSQL's **VACUUM** process.

This is directly relevant to JDBC: each Connection operates within a transaction, and the isolation level can be set via conn.setTransactionIsolation().

</details>

---

*End of Week 2 — RDBMS Foundations Interview Question Bank (80 Questions)*
