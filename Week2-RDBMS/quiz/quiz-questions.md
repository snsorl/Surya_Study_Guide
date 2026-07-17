# Weekly Knowledge Check: Week 2 â€” RDBMS Foundations

> This quiz covers the full Week 2 curriculum: TDD, JUnit 5, SQL fundamentals, RDBMS concepts, ERDs, key types, SQL sublanguages, JDBC, aggregate functions, JOIN operations, subqueries, and SQL Injection prevention.

---

## Part 1: Multiple Choice

---

### Q1. Which JUnit 5 annotation marks a method that runs once before each individual test method?

- [ ] A) `@BeforeAll`
- [ ] B) `@BeforeEach`
- [ ] C) `@Setup`
- [ ] D) `@Init`

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) `@BeforeEach`

**Explanation:** `@BeforeEach` runs before every single test method in the class, making it ideal for resetting shared state between tests.

**Why others are wrong:**
- A) `@BeforeAll` runs only once before all tests in the class â€” it requires a `static` method.
- C) `@Setup` is not a JUnit 5 annotation; it exists in older testing frameworks like TestNG.
- D) `@Init` is not a JUnit 5 annotation at all.

</details>

---

### Q2. In the TDD Red-Green-Refactor cycle, what does the "Red" phase represent?

- [ ] A) A test that compiles and passes.
- [ ] B) A test that is deliberately skipped.
- [ ] C) A test that fails because the implementation does not yet exist.
- [ ] D) A runtime exception thrown during refactoring.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** C) A test that fails because the implementation does not yet exist.

**Explanation:** In TDD, you write the test first before writing any production code. The test fails (Red) because there is no implementation yet. This confirms the test is actually testing something.

**Why others are wrong:**
- A) A passing test is the "Green" phase.
- B) Skipped tests are not part of the Red-Green-Refactor cycle.
- D) Runtime exceptions during refactoring indicate a broken refactor â€” that is a defect in the process, not the Red phase.

</details>

---

### Q3. Which JUnit 5 assertion is used to verify that a specific exception is thrown by a code block?

- [ ] A) `assertEquals(exception, block)`
- [ ] B) `assertThrows(ExceptionType.class, () -> { ... })`
- [ ] C) `assertException(ExceptionType.class, block)`
- [ ] D) `expectThrows(ExceptionType.class, block)`

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) `assertThrows(ExceptionType.class, () -> { ... })`

**Explanation:** `assertThrows` takes the expected exception class and an executable lambda. It passes only if the lambda throws exactly that exception type.

**Why others are wrong:**
- A) `assertEquals` compares two values â€” it does not handle exception assertions.
- C) `assertException` is not a JUnit 5 method.
- D) `expectThrows` does not exist in JUnit 5's standard API.

</details>

---

### Q4. What does the "Arrange" phase in the AAA (Arrange-Act-Assert) pattern represent?

- [ ] A) Calling the method under test.
- [ ] B) Setting up the test environment, inputs, and dependencies.
- [ ] C) Verifying the result of the method call.
- [ ] D) Cleaning up resources after the test.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) Setting up the test environment, inputs, and dependencies.

**Explanation:** The Arrange phase is where you prepare everything the test needs: create objects, define inputs, mock dependencies, and set initial state before calling the system under test.

**Why others are wrong:**
- A) Calling the method is the "Act" phase.
- C) Verifying the result is the "Assert" phase.
- D) Cleanup after tests is handled by `@AfterEach` or `@AfterAll`, not described as a phase within AAA.

</details>

---

### Q5. In a full-stack Java web application, which layer is responsible for communicating directly with the database?

- [ ] A) The Frontend (Browser/HTML)
- [ ] B) The REST Controller Layer
- [ ] C) The Business Logic / Service Layer
- [ ] D) The Data Access / JDBC Layer

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** D) The Data Access / JDBC Layer

**Explanation:** In N-tier architecture, the Data Access Layer (DAOs using JDBC) is the layer directly responsible for executing SQL statements against the database. Upper layers call into it without knowing the SQL details.

**Why others are wrong:**
- A) The Frontend renders UI and makes HTTP requests â€” it never connects to a database directly.
- B) The REST Controller handles HTTP routing â€” it delegates business logic to service layers.
- C) The Service Layer contains business rules â€” it calls DAOs, but does not directly execute SQL.

</details>

---

### Q6. Which SQL data type should you use in PostgreSQL to store currency values such as $10,500.25 with no floating-point rounding errors?

- [ ] A) `FLOAT`
- [ ] B) `DOUBLE PRECISION`
- [ ] C) `DECIMAL` / `NUMERIC`
- [ ] D) `REAL`

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** C) `DECIMAL` / `NUMERIC`

**Explanation:** `DECIMAL(precision, scale)` and `NUMERIC` store exact decimal values with no floating-point approximation, making them mandatory for financial calculations.

**Why others are wrong:**
- A) `FLOAT` is an approximate type using binary representation â€” it introduces rounding errors unsuitable for currency.
- B) `DOUBLE PRECISION` is also an approximate floating-point type.
- D) `REAL` is a 4-byte approximate floating-point â€” also unsuitable for financial data.

</details>

---

### Q7. What is the correct PostgreSQL column type to store the date AND time of an event, including timezone information?

- [ ] A) `DATE`
- [ ] B) `TIME`
- [ ] C) `TIMESTAMP`
- [ ] D) `TIMESTAMPTZ`

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** D) `TIMESTAMPTZ`

**Explanation:** `TIMESTAMPTZ` (or `TIMESTAMP WITH TIME ZONE`) stores both date and time along with timezone offset data, enabling accurate cross-timezone comparisons.

**Why others are wrong:**
- A) `DATE` stores only the calendar date (no time component).
- B) `TIME` stores only the time of day (no date component).
- C) `TIMESTAMP` stores date and time but without timezone context â€” results may be ambiguous across timezone boundaries.

</details>

---

### Q8. In Entity-Relationship Diagram (ERD) notation, a "crow's foot" symbol on a relationship line represents:

- [ ] A) A mandatory, exactly-one cardinality.
- [ ] B) A many-side cardinality.
- [ ] C) A one-to-one relationship.
- [ ] D) An optional participation.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) A many-side cardinality.

**Explanation:** The crow's foot (three lines diverging from a point, resembling bird toes) on a relationship line denotes the "many" side of a one-to-many or many-to-many relationship.

**Why others are wrong:**
- A) A mandatory exactly-one participation is represented by a single vertical line.
- C) A one-to-one relationship has a single line on both ends.
- D) Optional participation is represented by an empty circle (zero) symbol.

</details>

---

### Q9. Which relationship type requires a junction (intersection) table to be modeled in a relational database?

- [ ] A) One-to-One
- [ ] B) One-to-Many
- [ ] C) Many-to-Many
- [ ] D) Unary (Self-Referencing)

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** C) Many-to-Many

**Explanation:** A many-to-many relationship (e.g., Students and Courses) cannot be directly stored in two tables without duplication. A junction table (e.g., `Enrollments`) is created, holding foreign keys from both sides.

**Why others are wrong:**
- A) One-to-One relationships are modeled with a foreign key in one of the tables â€” no junction table needed.
- B) One-to-Many relationships use a foreign key on the "many" side â€” no junction table needed.
- D) Unary relationships may use a self-referencing foreign key in the same table.

</details>

---

### Q10. What is the key difference between a PRIMARY KEY and a UNIQUE constraint in PostgreSQL?

- [ ] A) A PRIMARY KEY can contain NULL values; UNIQUE cannot.
- [ ] B) A table can have multiple PRIMARY KEYS but only one UNIQUE constraint.
- [ ] C) A PRIMARY KEY cannot contain NULL and uniquely identifies each row; UNIQUE allows one NULL per column.
- [ ] D) UNIQUE constraints are automatically indexed, but PRIMARY KEYs are not.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** C) A PRIMARY KEY cannot contain NULL and uniquely identifies each row; UNIQUE allows one NULL per column.

**Explanation:** A PRIMARY KEY combines NOT NULL + UNIQUE. A UNIQUE constraint enforces uniqueness but permits NULL values (since NULL is considered distinct from any value, including other NULLs, in SQL semantics).

**Why others are wrong:**
- A) This is the reverse â€” PRIMARY KEY forbids NULLs, while UNIQUE allows them.
- B) A table can have only one PRIMARY KEY but can have multiple UNIQUE constraints.
- D) Both PRIMARY KEY and UNIQUE constraints automatically create an index in PostgreSQL.

</details>

---

### Q11. Which SQL sublanguage does the `GRANT` statement belong to?

- [ ] A) DDL (Data Definition Language)
- [ ] B) DML (Data Manipulation Language)
- [ ] C) DCL (Data Control Language)
- [ ] D) TCL (Transaction Control Language)

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** C) DCL (Data Control Language)

**Explanation:** DCL controls access permissions. `GRANT` gives a user or role specific privileges (e.g., `GRANT SELECT ON books TO reader`). `REVOKE` removes permissions and is also DCL.

**Why others are wrong:**
- A) DDL manages schema structures: `CREATE`, `ALTER`, `DROP`, `TRUNCATE`.
- B) DML manipulates row data: `INSERT`, `UPDATE`, `DELETE`.
- D) TCL manages transactions: `COMMIT`, `ROLLBACK`, `SAVEPOINT`.

</details>

---

### Q12. What does a `ROLLBACK TO SAVEPOINT` command do?

- [ ] A) Permanently commits all changes up to the savepoint.
- [ ] B) Undoes all changes made after the savepoint was created, without ending the transaction.
- [ ] C) Ends the entire transaction and discards all changes from the beginning.
- [ ] D) Creates a snapshot of the database at a specific point in time.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) Undoes all changes made after the savepoint was created, without ending the transaction.

**Explanation:** `ROLLBACK TO SAVEPOINT savepoint_name` reverts the transaction state to the marked checkpoint, undoing only the work done after the savepoint. The transaction remains open and can continue.

**Why others are wrong:**
- A) Permanently committing is the role of `COMMIT` â€” a savepoint rollback does not commit anything.
- C) A full `ROLLBACK` (without SAVEPOINT) discards all changes from the beginning of the transaction.
- D) Savepoints are not database snapshots â€” they are internal transaction checkpoints within a single session.

</details>

---

### Q13. What is a Composite Primary Key?

- [ ] A) A primary key column that auto-increments using a sequence.
- [ ] B) A primary key that uses a UUID value instead of an integer.
- [ ] C) A primary key formed by combining two or more columns to uniquely identify a row.
- [ ] D) A secondary index on a non-primary column.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** C) A primary key formed by combining two or more columns to uniquely identify a row.

**Explanation:** When no single column is unique on its own, multiple columns can be combined into a composite primary key. For example, a `loans` junction table might have PK(`member_id`, `book_id`, `loan_date`).

**Why others are wrong:**
- A) That describes an auto-increment surrogate key â€” a single column concept.
- B) That describes a natural key or surrogate UUID â€” still a single column.
- D) That describes a secondary index or alternate key, not a primary key.

</details>

---

### Q14. Which SQL statement belongs to DQL (Data Query Language)?

- [ ] A) `UPDATE orders SET status = 'SHIPPED' WHERE id = 1`
- [ ] B) `ALTER TABLE orders ADD COLUMN tracking_number VARCHAR(50)`
- [ ] C) `SELECT * FROM orders WHERE status = 'PENDING'`
- [ ] D) `COMMIT`

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** C) `SELECT * FROM orders WHERE status = 'PENDING'`

**Explanation:** DQL contains `SELECT` statements used to query and retrieve data from the database. No data is modified â€” only read.

**Why others are wrong:**
- A) `UPDATE` is DML â€” it modifies row data.
- B) `ALTER TABLE` is DDL â€” it modifies the schema structure.
- D) `COMMIT` is TCL â€” it finalizes an open transaction.

</details>

---

### Q15. What is Referential Integrity?

- [ ] A) Ensuring that all column values are stored in the correct data type.
- [ ] B) A guarantee that a foreign key value in a child table always references a valid, existing row in the parent table.
- [ ] C) A rule preventing duplicate rows from existing in a table.
- [ ] D) An index strategy that improves JOIN query performance.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) A guarantee that a foreign key value in a child table always references a valid, existing row in the parent table.

**Explanation:** Referential integrity is enforced by foreign key constraints. It prevents orphaned rows â€” for example, preventing a `loan` record from referencing a `member_id` that does not exist in the `members` table.

**Why others are wrong:**
- A) Correct data types are enforced by column type definitions, not referential integrity.
- C) Preventing duplicate rows is the role of `PRIMARY KEY` or `UNIQUE` constraints.
- D) JOIN performance optimization is the role of indexes â€” a separate concept.

</details>

---

## Part 2: True / False

---

### Q16. True or False: In JUnit 5, the `@AfterAll` method must be declared as `static`.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** `@AfterAll` (and `@BeforeAll`) must be `static` in JUnit 5 because they run at the class level before or after any test instances are created. (Exception: if the test class uses `@TestInstance(Lifecycle.PER_CLASS)`, then static is not required.)

</details>

---

### Q17. True or False: TDD is appropriate for every single software development scenario without exception.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** TDD is highly valuable but is not universally optimal. It can be impractical for pure UI exploration, data migration scripts, or highly exploratory prototyping where requirements are entirely unknown. Enterprise settings also consider cost-benefit tradeoffs.

</details>

---

### Q18. True or False: `VARCHAR(255)` and `TEXT` are functionally identical in PostgreSQL and have no performance difference for most workloads.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** In PostgreSQL specifically, `VARCHAR(n)` and `TEXT` are stored identically internally. The only difference is that `VARCHAR(n)` enforces a maximum length constraint. For most workloads there is no performance difference between them.

</details>

---

### Q19. True or False: A FOREIGN KEY constraint can reference a column that has a UNIQUE constraint (but is not the PRIMARY KEY) in the parent table.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** In PostgreSQL, a foreign key can reference any column (or combination of columns) in the parent table that is covered by a unique constraint â€” not just the primary key. This supports referencing candidate keys.

</details>

---

### Q20. True or False: The `TRUNCATE` statement in PostgreSQL can be rolled back within a transaction.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Unlike some other databases (e.g., MySQL), PostgreSQL implements `TRUNCATE` as a transactional DDL statement. If executed inside a `BEGIN` block, it can be rolled back with `ROLLBACK`.

</details>

---

### Q21. True or False: A table can have more than one PRIMARY KEY constraint defined on it.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** A table can have exactly one PRIMARY KEY (which may be composite across multiple columns). However, a table can have multiple UNIQUE constraints and multiple FOREIGN KEY constraints.

</details>

---

### Q22. True or False: `SELECT` statements in SQL are classified under DML (Data Manipulation Language).

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** `SELECT` belongs to DQL (Data Query Language). DML is specifically the subset of statements that modify data: `INSERT`, `UPDATE`, and `DELETE`. Some older classifications group DQL under DML, but the standard five-sublanguage model treats them separately.

</details>

---

### Q23. True or False: A UNIQUE constraint in PostgreSQL automatically creates an index on that column.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** PostgreSQL automatically creates a unique B-tree index to enforce any UNIQUE constraint. This is also true for PRIMARY KEY constraints. This means uniqueness checks are fast even on large tables.

</details>

---

### Q24. True or False: NULL values are considered equal to each other in SQL comparisons.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** In SQL, `NULL` represents an unknown or missing value. Any comparison involving NULL (including `NULL = NULL`) evaluates to UNKNOWN, not TRUE. To check for NULL, you must use `IS NULL` or `IS NOT NULL`.

</details>

---

### Q25. True or False: Using Chain-of-Thought prompting with an AI assistant causes the AI to reason through steps before producing a final answer, which typically improves the quality of complex SQL queries.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Chain-of-Thought (CoT) prompting instructs the AI to reason step-by-step (e.g., "First identify the tables, then determine the join condition, then write the WHERE clause"). This decomposition reduces errors in complex SQL generation by making the reasoning process explicit.

</details>

---

## Part 3: Code Prediction

---

### Q26. What is the result of the following JUnit 5 test?

```java
@Test
public void testDivideByZero() {
    int numerator = 10;
    int denominator = 0;
    assertThrows(ArithmeticException.class, () -> {
        int result = numerator / denominator;
    });
}
```

- [ ] A) The test fails with an AssertionError.
- [ ] B) The test passes because dividing by zero throws ArithmeticException.
- [ ] C) The test throws a NullPointerException.
- [ ] D) The code does not compile because division cannot be inside a lambda.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) The test passes because dividing by zero throws ArithmeticException.

**Explanation:** Java integer division by zero throws `java.lang.ArithmeticException: / by zero`. `assertThrows` catches this and confirms the exception was thrown â€” causing the test to pass (Green).

**Why others are wrong:**
- A) The test passes, not fails.
- C) NullPointerException is unrelated â€” all variables are initialized primitive ints.
- D) Lambdas can contain any valid Java code block including arithmetic expressions.

</details>

---

### Q27. What will this SQL query return?

```sql
-- orders: (1, customer_id=1, amount=100), (2, customer_id=1, amount=200), (3, customer_id=2, amount=50)
SELECT customer_id, COUNT(*) AS total_orders, SUM(amount) AS total_spent
FROM orders
GROUP BY customer_id
HAVING SUM(amount) > 150;
```

- [ ] A) All 3 raw rows from the orders table.
- [ ] B) One row: customer 1 with 2 orders and $300 total.
- [ ] C) Two rows: both customers grouped.
- [ ] D) No rows â€” HAVING requires WHERE first.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) One row: customer 1 with 2 orders and $300 total.

**Explanation:** GROUP BY creates two groups: customer 1 (sum=$300) and customer 2 (sum=$50). HAVING filters these â€” only customer 1 ($300) exceeds the $150 threshold.

**Why others are wrong:**
- A) HAVING filters groups, not raw rows.
- C) Customer 2's $50 total does not meet `> 150`.
- D) HAVING can be used without WHERE â€” it filters aggregated groups.

</details>

---

### Q28. What error does PostgreSQL produce?

```sql
-- members table has UNIQUE constraint on email; john@email.com already exists
INSERT INTO members (member_id, name, email) VALUES (5003, 'Alex Jones', 'john@email.com');
```

- [ ] A) `null value in column "email" violates not-null constraint`
- [ ] B) `duplicate key value violates unique constraint "members_email_key"`
- [ ] C) `insert or update violates foreign key constraint`
- [ ] D) No error â€” PostgreSQL silently ignores duplicate emails.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) `duplicate key value violates unique constraint "members_email_key"`

**Explanation:** PostgreSQL enforces UNIQUE constraints at insert/update time. The existing email causes a violation and the statement is rejected.

**Why others are wrong:**
- A) A not-null violation requires a NULL value â€” not a duplicate.
- C) A foreign key violation requires a missing parent row â€” unrelated here.
- D) PostgreSQL never silently ignores constraint violations.

</details>

---

### Q29. What does this SQL statement return?

```sql
SELECT name FROM members
WHERE member_id NOT IN (SELECT member_id FROM loans);
```

- [ ] A) All members who have at least one loan.
- [ ] B) All members who have never taken out a loan.
- [ ] C) A Cartesian product of members and loans.
- [ ] D) A syntax error â€” NOT IN cannot be used with subqueries.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** B) All members who have never taken out a loan.

**Explanation:** The subquery returns all member_ids that appear in loans. `NOT IN` excludes those IDs from the outer query â€” returning only members with zero loans.

**Why others are wrong:**
- A) That requires `IN` (not `NOT IN`).
- C) A Cartesian product comes from CROSS JOIN or a missing join condition.
- D) `NOT IN` with a subquery is fully standard SQL.

</details>

---

### Q30. What does this Java snippet print?

```java
String userInput = "admin' OR '1'='1";
String sql = "SELECT * FROM members WHERE name = '" + userInput + "'";
System.out.println(sql);
```

- [ ] A) `SELECT * FROM members WHERE name = 'admin' OR '1'='1'`
- [ ] B) `SELECT * FROM members WHERE name = 'admin OR 1=1'`
- [ ] C) A compilation error.
- [ ] D) The injection is filtered automatically by Java.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** A) `SELECT * FROM members WHERE name = 'admin' OR '1'='1'`

**Explanation:** The attacker's quote breaks out of the string literal. The resulting SQL adds `OR '1'='1'` â€” always true â€” bypassing any authentication check. This is the classic SQL Injection attack.

**Why others are wrong:**
- B) The quote characters act as SQL delimiters, not part of a string value.
- C) String concatenation compiles fine â€” the problem is the runtime security vulnerability.
- D) Java performs no automatic SQL filtering.

</details>

---

## Part 4: Fill in the Blank

---

### Q31. In the JUnit 5 AAA pattern, "AAA" stands for: __________, __________, and __________.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** Arrange, Act, Assert

**Explanation:** Arrange sets up the test preconditions; Act calls the method under test; Assert verifies the outcome. This structured pattern improves test readability and identifies which phase failed.

</details>

---

### Q32. The SQL sublanguage responsible for CREATE, ALTER, and DROP statements is called __________.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** DDL (Data Definition Language)

**Explanation:** DDL statements define and manage the structure (schema) of database objects â€” tables, indexes, views, and sequences. They do not manipulate the data inside tables.

</details>

---

### Q33. In PostgreSQL, a foreign key constraint references a column in the __________ table, and the table containing the FK column is called the __________ table.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** parent (referenced), child (referencing)

**Explanation:** The parent table owns the referenced column (typically the primary key). The child table holds the foreign key column that points back to the parent. Referential integrity ensures the FK value always matches a row in the parent table.

</details>

---

### Q34. The JUnit 5 annotation that marks a method to execute once after all tests in the class have finished is __________.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** `@AfterAll`

**Explanation:** `@AfterAll` runs once after the last test method in the class completes. It is used for releasing shared resources like database connections or file handles. It must be `static` unless `@TestInstance(Lifecycle.PER_CLASS)` is applied.

</details>

---

### Q35. The SQL command used to undo all database changes since the beginning of the current transaction is __________.

<details>
<summary><b>ðŸ”Ž Click for Solution</b></summary>

**Correct Answer:** `ROLLBACK`

**Explanation:** `ROLLBACK` discards all pending changes within the current transaction, returning the database to its last committed state. It is part of TCL (Transaction Control Language).

</details>

---

## Part 5: Multiple Choice — Thursday and Friday Topics

---

### Q36. In JDBC, which interface executes a pre-compiled SQL query with parameter placeholders?

- [ ] A) `Statement`
- [ ] B) `PreparedStatement`
- [ ] C) `ResultSet`
- [ ] D) `DriverManager`

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) `PreparedStatement`

**Explanation:** `PreparedStatement` pre-compiles the SQL template with `?` placeholders. Parameters are bound via typed setters (`setString`, `setInt`, etc.) before execution, preventing SQL Injection and improving performance on repeated executions.

**Why others are wrong:**
- A) `Statement` executes raw SQL strings without parameterization — vulnerable to SQL Injection.
- C) `ResultSet` holds the data returned by a query — it does not execute queries.
- D) `DriverManager` manages driver registration and provides connections — it does not execute SQL.

</details>

---

### Q37. What is the DAO (Data Access Object) design pattern?

- [ ] A) A pattern connecting the View layer directly to the database without a service layer.
- [ ] B) A structural pattern separating database access logic from business logic through an interface and implementation.
- [ ] C) A singleton class that manages connection pooling.
- [ ] D) A JDBC utility that automatically generates SQL from Java objects.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) A structural pattern separating database access logic from business logic through an interface and implementation.

**Explanation:** The DAO pattern defines a clean contract (interface) for CRUD operations. The implementation handles JDBC details. Business layers call the interface without being coupled to database technology, making the code testable and replaceable.

**Why others are wrong:**
- A) Connecting the View directly to the database violates separation of concerns.
- C) Connection management is the ConnectionFactory / DataSource pattern — distinct from DAO.
- D) Automatic SQL generation from objects is ORM territory (e.g., Hibernate), not DAO.

</details>

---

### Q38. Which Java JDBC method obtains a database connection?

- [ ] A) `Connection.open(url)`
- [ ] B) `DriverManager.getConnection(url, user, password)`
- [ ] C) `PreparedStatement.connect(url)`
- [ ] D) `ResultSet.getConnection(url)`

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) `DriverManager.getConnection(url, user, password)`

**Explanation:** `DriverManager.getConnection()` is the standard JDBC entry point for obtaining a `Connection` object. The URL specifies driver type, host, port, and database name.

**Why others are wrong:**
- A) `Connection.open()` does not exist in the JDBC API.
- C) `PreparedStatement` is obtained from a `Connection` — it cannot establish connections.
- D) `ResultSet` is obtained from query execution — it has no connection method.

</details>

---

### Q39. What is the purpose of Try-With-Resources in JDBC?

- [ ] A) To automatically retry failed SQL queries on network errors.
- [ ] B) To automatically close JDBC resources (Connection, Statement, ResultSet) after the block ends, preventing connection leaks.
- [ ] C) To catch SQL exceptions and display them in the console.
- [ ] D) To pool JDBC connections for reuse across threads.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) To automatically close JDBC resources (Connection, Statement, ResultSet) after the block ends, preventing connection leaks.

**Explanation:** JDBC objects hold physical OS resources (network sockets). Try-With-Resources guarantees `close()` is called even if an exception occurs, preventing resource leaks that could exhaust the database server's connection pool.

**Why others are wrong:**
- A) Retry logic must be implemented manually — Try-With-Resources handles lifecycle only.
- C) Exception handling requires explicit catch blocks — Try-With-Resources does not print exceptions.
- D) Connection pooling is handled by a DataSource (e.g., HikariCP) — not Try-With-Resources.

</details>

---

### Q40. Which aggregate function returns the number of rows matching a query condition?

- [ ] A) `SUM()`
- [ ] B) `COUNT()`
- [ ] C) `AVG()`
- [ ] D) `MAX()`

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) `COUNT()`

**Explanation:** `COUNT(*)` returns the total number of rows. `COUNT(column_name)` returns the count of non-NULL values in that column.

**Why others are wrong:**
- A) `SUM()` adds up numeric values across rows.
- C) `AVG()` calculates the arithmetic mean.
- D) `MAX()` returns the largest value in a column.

</details>

---

### Q41. What does the HAVING clause do that WHERE cannot?

- [ ] A) HAVING filters rows before grouping; WHERE filters groups after grouping.
- [ ] B) HAVING filters grouped aggregate results after GROUP BY; WHERE filters individual rows before grouping.
- [ ] C) HAVING only works with COUNT; WHERE works with all aggregates.
- [ ] D) There is no difference — HAVING is an alias for WHERE.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) HAVING filters grouped aggregate results after GROUP BY; WHERE filters individual rows before grouping.

**Explanation:** WHERE runs before aggregation and cannot reference aggregate functions. HAVING runs after GROUP BY and can filter groups based on aggregate values (e.g., HAVING SUM(amount) > 100).

**Why others are wrong:**
- A) This reverses the correct behavior.
- C) HAVING works with any aggregate — COUNT, SUM, AVG, MIN, MAX.
- D) They are syntactically and functionally distinct clauses.

</details>

---

### Q42. What does ON DELETE CASCADE do on a foreign key constraint?

- [ ] A) Prevents deletion of the parent row if child rows exist.
- [ ] B) Automatically deletes all child rows when the referenced parent row is deleted.
- [ ] C) Sets the foreign key column to NULL in the child table when the parent is deleted.
- [ ] D) Generates a default replacement value in child rows when the parent is deleted.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) Automatically deletes all child rows when the referenced parent row is deleted.

**Explanation:** ON DELETE CASCADE propagates the delete operation. If a book is deleted, all loan records referencing that book are automatically deleted, maintaining referential integrity.

**Why others are wrong:**
- A) That is ON DELETE RESTRICT / NO ACTION behavior.
- C) That is ON DELETE SET NULL behavior.
- D) That is ON DELETE SET DEFAULT behavior.

</details>

---

### Q43. In PostgreSQL, what does the SERIAL data type do?

- [ ] A) Stores large binary serial data files.
- [ ] B) Creates an auto-incrementing integer column backed by a sequence.
- [ ] C) Generates a random UUID for each inserted row.
- [ ] D) Resets the column to its default value on each update.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) Creates an auto-incrementing integer column backed by a sequence.

**Explanation:** SERIAL is shorthand for creating an integer column with DEFAULT nextval('sequence_name'). Each INSERT automatically gets the next sequence value. Modern PostgreSQL prefers GENERATED ALWAYS AS IDENTITY.

**Why others are wrong:**
- A) SERIAL has nothing to do with binary data storage.
- C) UUID generation requires gen_random_uuid() or uuid_generate_v4().
- D) SERIAL affects INSERT assignment only — it does not reset on UPDATE.

</details>

---

### Q44. Which SQL JOIN type returns ALL rows from the left table and matching rows from the right table, filling unmatched right columns with NULL?

- [ ] A) INNER JOIN
- [ ] B) RIGHT JOIN
- [ ] C) LEFT JOIN
- [ ] D) CROSS JOIN

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** C) LEFT JOIN

**Explanation:** A LEFT JOIN preserves all rows from the left table. Where no matching row exists in the right table, right-side columns are populated with NULL. Useful for finding records with optional relationships.

**Why others are wrong:**
- A) INNER JOIN returns only rows where both tables have a match.
- B) RIGHT JOIN preserves all rows from the right table — the inverse of LEFT JOIN.
- D) CROSS JOIN returns a Cartesian product with no ON condition.

</details>

---

### Q45. What is the result of a CROSS JOIN between a table with 4 rows and a table with 3 rows?

- [ ] A) 4 rows
- [ ] B) 3 rows
- [ ] C) 7 rows
- [ ] D) 12 rows

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** D) 12 rows

**Explanation:** A CROSS JOIN produces a Cartesian product — every row from the first table is combined with every row from the second. 4 x 3 = 12 result rows.

**Why others are wrong:**
- A) 4 rows would be the left table alone.
- B) 3 rows would be the right table alone.
- C) 7 rows would come from adding (UNION), not multiplying (CROSS JOIN).

</details>

---

### Q46. Which OWASP Top 10 category specifically covers SQL Injection attacks?

- [ ] A) A02: Cryptographic Failures
- [ ] B) A03: Injection
- [ ] C) A07: Identification and Authentication Failures
- [ ] D) A09: Security Logging and Monitoring Failures

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) A03: Injection

**Explanation:** The OWASP Top 10 2021 classifies SQL Injection under A03: Injection, covering all injection flaws where hostile data is sent to an interpreter as part of a command or query.

**Why others are wrong:**
- A) A02 relates to weak encryption and exposed data at rest or in transit.
- C) A07 covers broken login systems and session management.
- D) A09 covers the inability to detect and respond to attacks after they occur.

</details>

---

### Q47. Why is it safer to access ResultSet columns by name rather than by index?

- [ ] A) Column names are faster to look up at runtime.
- [ ] B) Column names remain valid if columns are reordered in the SELECT list; index positions shift.
- [ ] C) JDBC only supports column name access — index access is not in the API.
- [ ] D) Index access is reserved for stored procedures only.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) Column names remain valid if columns are reordered in the SELECT list; index positions shift.

**Explanation:** If you access rs.getString(1) and the SELECT query is later modified, the index silently shifts and you read the wrong column. Using rs.getString("email") is robust to query restructuring.

**Why others are wrong:**
- A) Column-name lookups require a map lookup — index access is marginally faster.
- C) JDBC supports both index and name access — both are in the API.
- D) Index access is available in all JDBC query results.

</details>

---

### Q48. What is the key difference between DELETE and TRUNCATE?

- [ ] A) DELETE can only remove one row; TRUNCATE removes all rows.
- [ ] B) DELETE uses WHERE to selectively remove rows and fires row-level triggers; TRUNCATE removes all rows faster and does NOT fire row-level triggers.
- [ ] C) DELETE is DDL; TRUNCATE is DML.
- [ ] D) TRUNCATE fires row-level triggers; DELETE does not.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) DELETE uses WHERE to selectively remove rows and fires row-level triggers; TRUNCATE removes all rows faster and does NOT fire row-level triggers.

**Explanation:** DELETE scans rows, fires triggers, and is fully logged. TRUNCATE removes all rows via page deallocation — much faster for large tables — but does not fire row-level triggers. Both are transactional in PostgreSQL.

**Why others are wrong:**
- A) DELETE can remove any number of rows matching a WHERE clause.
- C) DELETE is DML; TRUNCATE is DDL in most classification systems.
- D) This is the reverse — TRUNCATE does not fire row-level triggers.

</details>

---

## Part 6: True / False — Thursday and Friday

---

### Q49. True or False: PreparedStatement prevents SQL Injection because the database compiles the query template before any user input is bound as a parameter.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** The database parses and compiles the SQL structure (with ? placeholders) before parameters are bound. Parameters are treated as literal data values — no SQL syntax parsing occurs on them, making injection structurally impossible.

</details>

---

### Q50. True or False: An INNER JOIN will include rows from the left table that have no matching row in the right table.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** An INNER JOIN returns only rows where a match exists in BOTH tables. Rows with no matching partner are excluded. A LEFT JOIN is needed to preserve unmatched left-side rows.

</details>

---

### Q51. True or False: The GROUP BY clause must always be accompanied by a HAVING clause.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** GROUP BY can be used without HAVING. HAVING is optional — it is only needed when filtering grouped results based on aggregate conditions.

</details>

---

### Q52. True or False: A JDBC Connection object must always be closed after use to prevent resource exhaustion on the database server.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Each JDBC Connection holds a physical network socket. Unclosed connections exhaust the database server's connection pool, causing new connection attempts to fail.

</details>

---

### Q53. True or False: A CHECK constraint in PostgreSQL can validate that a numeric column's value is greater than zero.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** CHECK constraints accept any valid SQL boolean expression. For example: CHECK (amount > 0). Any INSERT or UPDATE violating this expression is rejected.

</details>

---

### Q54. True or False: The NOT EXISTS pattern and the NOT IN pattern always produce identical results.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** They differ on NULL handling. If the subquery returns any NULL values, NOT IN returns no rows (because x NOT IN (..., NULL) evaluates to UNKNOWN). NOT EXISTS handles NULLs correctly. For null-safe queries, NOT EXISTS is preferred.

</details>

---

### Q55. True or False: A RIGHT JOIN can always be rewritten as a LEFT JOIN by swapping the table order.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** A RIGHT JOIN B is semantically equivalent to B LEFT JOIN A. Standardizing on LEFT JOINs keeps query logic consistent and readable.

</details>

---

### Q56. True or False: SQL Injection can only occur in login forms.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** SQL Injection can occur anywhere user-supplied data is concatenated into a SQL query — including search boxes, URL parameters, API bodies, cookie values, and HTTP headers. Any unsanitized input reaching a SQL statement is a potential injection point.

</details>

---

## Part 7: Code Prediction — Thursday and Friday

---

### Q57. What does this JDBC snippet print if the books table is empty?

`java
PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books");
ResultSet rs = pstmt.executeQuery();
if (rs.next()) {
    System.out.println("Found: " + rs.getString("title"));
} else {
    System.out.println("No records found.");
}
`

- [ ] A) Found: null
- [ ] B) No records found.
- [ ] C) A NullPointerException is thrown.
- [ ] D) A SQLException is thrown because the table is empty.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) No records found.

**Explanation:** When a query returns zero rows, rs.next() returns false on the first call. The if block is skipped and the else branch prints "No records found."

**Why others are wrong:**
- A) rs.getString("title") is never called because rs.next() returned false.
- C) No NullPointerException — the ResultSet is a valid but empty object.
- D) An empty result set is not a SQL error — it is a normal query outcome.

</details>

---

### Q58. What does this SQL aggregate query return?

`sql
-- products: (501, Keyboard, 120.00), (502, Mouse, 45.00), (503, Cable, 15.00)
SELECT MIN(price) AS cheapest, MAX(price) AS most_expensive, ROUND(AVG(price), 2) AS average
FROM products;
`

- [ ] A) Three rows, one per product.
- [ ] B) One row: cheapest=15.00, most_expensive=120.00, average=60.00
- [ ] C) One row: cheapest=120.00, most_expensive=15.00, average=60.00
- [ ] D) A syntax error — MIN, MAX, and AVG cannot appear in the same SELECT.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) One row: cheapest=15.00, most_expensive=120.00, average=60.00

**Explanation:** Aggregates without GROUP BY collapse all rows to one result. MIN=15.00 (Cable), MAX=120.00 (Keyboard), AVG=(120+45+15)/3=60.00.

**Why others are wrong:**
- A) Without GROUP BY, aggregates produce one result row.
- C) MIN and MAX values are reversed.
- D) Multiple aggregates in one SELECT list is standard SQL.

</details>

---

### Q59. What does this LEFT JOIN query return if member_id=3 has no loans?

`sql
SELECT m.name, l.loan_date
FROM members m
LEFT JOIN loans l ON m.member_id = l.member_id
WHERE m.member_id = 3;
`

- [ ] A) No rows are returned.
- [ ] B) One row: member name with loan_date = NULL.
- [ ] C) One row: member name with loan_date = 0000-00-00.
- [ ] D) A SQL error because NULL cannot appear in a LEFT JOIN result.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) One row: member name with loan_date = NULL.

**Explanation:** LEFT JOIN preserves all rows from the left table. Member 3 has no matching loans, so loan_date is populated with NULL. The row is included, not excluded.

**Why others are wrong:**
- A) INNER JOIN would return no rows — LEFT JOIN returns the member row regardless.
- C) PostgreSQL uses NULL (not a zero date) for missing values in outer joins.
- D) NULL in LEFT JOIN results is expected and valid.

</details>

---

## Part 8: Fill in the Blank — Thursday and Friday

---

### Q60. In JDBC, the method pstmt.__________(1, "Alice") binds a String parameter to the first placeholder in a PreparedStatement.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** setString

**Explanation:** pstmt.setString(1, "Alice") binds the String value "Alice" to the first ? placeholder. JDBC provides typed setters for each Java type: setInt, setDouble, setDate, setBoolean, etc.

</details>

---

### Q61. A SQL JOIN that returns only rows where a matching record exists in BOTH tables is called an __________ JOIN.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** INNER

**Explanation:** An INNER JOIN applies the join condition and returns only the matching rows from both sides. Rows without a match are excluded from the result set.

</details>

---

### Q62. The OWASP Top 10 is published by the __________ organization, listing the most critical web application security risks.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** OWASP (Open Worldwide Application Security Project)

**Explanation:** OWASP is a non-profit foundation dedicated to improving software security. The Top 10 list (most recent: 2021) is widely used by developers, auditors, and security teams to prioritize security efforts.

</details>

---

### Q63. When using JDBC, you call rs.__________() in a while loop to iterate through each row returned by a query.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** next()

**Explanation:** rs.next() advances the cursor to the next row and returns true if a row is available, false when the result set is exhausted. This is the standard JDBC ResultSet iteration pattern.

</details>

---

### Q64. A CHECK constraint restricting a status column to only ACTIVE, SUSPENDED, or EXPIRED uses a __________ expression in its definition.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** IN (or enumeration / list expression)

**Explanation:** The constraint is written as: CHECK (status IN ('ACTIVE', 'SUSPENDED', 'EXPIRED')). The IN operator concisely enumerates valid values without multiple OR conditions.

</details>

---

### Q65. The SQL clause used to sort the final output of a SELECT query in descending order is ORDER BY column_name __________.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** DESC

**Explanation:** ORDER BY column_name DESC sorts results from largest to smallest (or Z to A for text). The default sort order when no keyword is specified is ASC (ascending).

</details>

---

## Part 9: Additional Multiple Choice — Advanced Topics

---

### Q66. What does a correlated subquery do differently from a non-correlated subquery?

- [ ] A) A correlated subquery runs once and its result is used by the outer query.
- [ ] B) A correlated subquery references a column from the outer query, causing it to re-execute for each row processed by the outer query.
- [ ] C) A correlated subquery can only be used in the FROM clause.
- [ ] D) A correlated subquery is always faster than an equivalent JOIN.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) A correlated subquery references a column from the outer query, causing it to re-execute for each row processed by the outer query.

**Explanation:** A correlated subquery ties itself to the current outer row via a column reference. It re-runs for every outer row, enabling row-by-row comparisons but potentially at significant performance cost on large datasets.

**Why others are wrong:**
- A) That describes a non-correlated (independent) subquery — it runs once.
- C) Correlated subqueries are most commonly used in WHERE or SELECT clauses.
- D) Correlated subqueries can be significantly slower than equivalent JOINs on large datasets.

</details>

---

### Q67. When extracting a date from a JDBC ResultSet to java.time.LocalDate, what conversion is needed?

- [ ] A) rs.getString("date_column")
- [ ] B) rs.getDate("date_column").toLocalDate()
- [ ] C) LocalDate.parse(rs.getDate("date_column"))
- [ ] D) rs.getLocalDate("date_column")

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) rs.getDate("date_column").toLocalDate()

**Explanation:** rs.getDate() returns java.sql.Date (legacy). Calling .toLocalDate() converts it to the modern java.time.LocalDate type, avoiding legacy timezone complications.

**Why others are wrong:**
- A) getString returns a text string — parsing it manually is fragile.
- C) LocalDate.parse() accepts a String, not a java.sql.Date object.
- D) getLocalDate() does not exist in the standard JDBC ResultSet API.

</details>

---

### Q68. What is the primary consideration when using AI-generated JDBC boilerplate code?

- [ ] A) AI always generates secure, vulnerability-free JDBC code automatically.
- [ ] B) AI can accelerate boilerplate generation, but developers must always review for correct PreparedStatement usage and proper resource closing.
- [ ] C) AI-generated JDBC code should be used in production without review.
- [ ] D) AI cannot generate JDBC code and should not be used for database tasks.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) AI can accelerate boilerplate generation, but developers must always review for correct PreparedStatement usage and proper resource closing.

**Explanation:** AI assistants can generate JDBC scaffolding quickly, but can also produce code using raw Statement, forgetting to close resources, or including hardcoded credentials. Human review for security properties is always required.

**Why others are wrong:**
- A) AI does not guarantee security — hallucinations and outdated patterns are documented risks.
- C) Production use without review violates secure development lifecycle (SDLC) principles.
- D) AI can generate JDBC code — the concern is verification, not capability.

</details>

---

### Q69. Which SQL keyword eliminates duplicate rows from a SELECT result?

- [ ] A) UNIQUE
- [ ] B) DISTINCT
- [ ] C) EXCLUDE
- [ ] D) FILTER

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** B) DISTINCT

**Explanation:** SELECT DISTINCT column deduplicates the result set, returning only one row per unique value combination. It applies to the full row returned.

**Why others are wrong:**
- A) UNIQUE is a constraint keyword used in DDL — not a SELECT modifier.
- C) EXCLUDE is not a standard SQL deduplication keyword.
- D) FILTER is used with window functions or aggregate expressions — not for row deduplication.

</details>

---

### Q70. Which correctly describes the difference between an Equi-join and a Theta-join?

- [ ] A) An equi-join uses the = operator in the join condition; a theta-join can use any comparison operator.
- [ ] B) An equi-join is performed using INNER JOIN; a theta-join requires CROSS JOIN.
- [ ] C) A theta-join always returns more rows than an equi-join on the same tables.
- [ ] D) Equi-join is PostgreSQL-specific; theta-join is MySQL-specific.

<details>
<summary><b>?? Click for Solution</b></summary>

**Correct Answer:** A) An equi-join uses the = operator in the join condition; a theta-join can use any comparison operator.

**Explanation:** An equi-join matches rows based on equality of a shared column (the most common form). A theta-join generalizes this to allow any comparison operator — ranges, inequalities, or distance conditions.

**Why others are wrong:**
- B) Both are expressed using INNER JOIN syntax — the difference is in the ON condition operator.
- C) The row count depends on data and condition — no fixed rule applies.
- D) These are relational algebra terms applicable to all SQL-compliant databases.

</details>

---

*End of Week 2 — RDBMS Foundations Practice Quiz (70 Questions)*
