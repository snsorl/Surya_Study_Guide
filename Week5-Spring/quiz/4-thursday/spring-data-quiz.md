# Thursday Knowledge Check: Spring Data JPA & Transactions

## Part 1: Multiple Choice & Objective Questions

### 1. Which interface does `JpaRepository` extend to provide paging and sorting capabilities?
- [ ] A) `CrudRepository`
- [ ] B) `PagingAndSortingRepository`
- [ ] C) `SimpleJpaRepository`
- [ ] D) `Repository`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `PagingAndSortingRepository`

**Explanation:** `JpaRepository` extends `PagingAndSortingRepository` (which extends `CrudRepository`). Because of this inheritance chain, `JpaRepository` bundles CRUD operations, paging, sorting, and JPA-specific methods (like flushing the persistence context or batch deleting).
- **Why others are wrong:**
  - A) `CrudRepository` provides standard CRUD operations but does not declare paging/sorting methods.
  - C) `SimpleJpaRepository` is a concrete class implementing `JpaRepository`, not an interface that it extends.
  - D) `Repository` is the top-level marker interface with no method declarations.
</details>

---

### 2. Following Spring Data JPA's query method naming convention, which method signature will automatically generate a query that searches for books by their title and price matching exact criteria?
- [ ] A) `List<Book> searchTitleAndPrice(String title, double price);`
- [ ] B) `List<Book> findByTitleAndPrice(String title, double price);`
- [ ] C) `List<Book> getBooksWhereTitleAndPrice(String title, double price);`
- [ ] D) `List<Book> queryTitleAndPriceMatches(String title, double price);`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `findByTitleAndPrice(String title, double price);`

**Explanation:** Spring Data JPA parses query method names starting with prefixes like `findBy`, `readBy`, `queryBy`, or `getBy`, followed by the property names joined by logical operators (`And`/`Or`). `findByTitleAndPrice` matches this naming rule.
- **Why others are wrong:**
  - A, C, D) These methods use custom terms (`search`, `getBooksWhere`, `matches`) that Spring Data's query parser does not recognize, resulting in startup errors.
</details>

---

### 3. What is the default rollback behavior of a method annotated with Spring's `@Transactional` when an exception is thrown?
- [ ] A) It rolls back only for Checked Exceptions (subclasses of `Exception`).
- [ ] B) It rolls back only for Unchecked Exceptions (subclasses of `RuntimeException` and `Error`).
- [ ] C) It rolls back for all Exceptions, whether checked or unchecked.
- [ ] D) It does not roll back automatically; the developer must call manual rollback commands.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It rolls back only for Unchecked Exceptions (subclasses of `RuntimeException` and `Error`).

**Explanation:** By default, Spring's transaction infrastructure rolls back transactions only in response to unchecked exceptions (`RuntimeException` or `Error`). If a checked exception (`Exception`) is thrown, the transaction is still committed by default, unless configured otherwise using `@Transactional(rollbackFor = Exception.class)`.
- **Why others are wrong:**
  - A) Checked exceptions do not trigger rollbacks by default.
  - C) Default configuration does not roll back checked exceptions.
  - D) The transaction proxy handles rollback automation automatically.
</details>

---

### 4. Which transaction propagation strategy suspends any existing outer transaction and runs the target method in a brand-new, independent database transaction?
- [ ] A) `Propagation.REQUIRED`
- [ ] B) `Propagation.REQUIRES_NEW`
- [ ] C) `Propagation.NESTED`
- [ ] D) `Propagation.SUPPORTS`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Propagation.REQUIRES_NEW`

**Explanation:** `REQUIRES_NEW` creates a new transaction, suspending any active outer transaction. The new transaction is committed or rolled back independently of the outer transaction's outcome.
- **Why others are wrong:**
  - A) `REQUIRED` (default) joins the active outer transaction if one exists, rather than creating a separate transaction.
  - C) `NESTED` runs within a nested transaction using database savepoints, which rollback if the outer transaction rolls back.
  - D) `SUPPORTS` executes the method in a transaction if one exists; otherwise, it executes non-transactionally.
</details>

---

### 5. What is the difference between JSR-380 validation annotations `@NotNull`, `@NotEmpty`, and `@NotBlank` when applied to a String field?
- [ ] A) They are identical and can be used interchangeably.
- [ ] B) `@NotNull` checks for non-null; `@NotEmpty` checks for non-null and length > 0; `@NotBlank` checks for non-null, length > 0, and requires at least one non-whitespace character.
- [ ] C) `@NotBlank` is used only for integer fields; `@NotEmpty` is used for lists.
- [ ] D) `@NotNull` is database-level; the others are JVM-level checks.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `@NotNull` checks for non-null; `@NotEmpty` checks for non-null and length > 0; `@NotBlank` checks for non-null, length > 0, and requires at least one non-whitespace character.

**Explanation:** `@NotNull` allows empty strings or strings containing only whitespace. `@NotEmpty` rejects nulls and empty strings (`""`), but allows white spaces (`"   "`). `@NotBlank` is the most restrictive; it rejects nulls, empty strings, and white space strings.
- **Why others are wrong:**
  - A) They check different levels of string content completeness.
  - C) `@NotBlank` is used specifically for character sequences, not integers.
  - D) All of these annotations are JVM-level bean validation checks.
</details>

---

### 6. Where must the `@Valid` annotation be placed to trigger validation checks on an incoming JSON request payload in a Spring REST Controller?
- [ ] A) On the REST Controller class definition itself.
- [ ] B) Directly on the property fields of the entity class.
- [ ] C) Immediately before the `@RequestBody` parameter in the controller method signature.
- [ ] D) Inside the application's configuration class.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Immediately before the `@RequestBody` parameter in the controller method signature.

**Explanation:** Placing `@Valid` or `@Validated` before `@RequestBody` instructs Spring's argument resolver to run validation checks on the deserialized object before executing the controller method.
- **Why others are wrong:**
  - A) Class-level annotations do not trigger parameter validations.
  - B) Field-level validation constraints declare *what* to validate, but `@Valid` in the controller method triggers *when* to execute that validation.
  - D) Configuration classes do not intercept incoming web requests.
</details>

---

### 7. Which exception is thrown by Spring when validation on a `@RequestBody` argument fails?
- [ ] A) `ConstraintViolationException`
- [ ] B) `MethodArgumentNotValidException`
- [ ] C) `TypeMismatchException`
- [ ] D) `NullPointerException`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `MethodArgumentNotValidException`

**Explanation:** When validation fails on a controller parameter annotated with `@Valid` and `@RequestBody`, Spring throws a `MethodArgumentNotValidException`.
- **Why others are wrong:**
  - A) `ConstraintViolationException` is thrown when validating parameters of path variables or query parameters (or raw JPA entities outside the web layer).
  - C) `TypeMismatchException` is thrown when a request parameter cannot be converted to the target method parameter type.
  - D) A validation failure is handled cleanly by throwing a validation exception rather than raising a null reference error.
</details>

---

### 8. In OWASP Cryptographic Failures (A02), why is using BCrypt preferred over MD5 or SHA-256 for password hashing?
- [ ] A) BCrypt hashes are shorter and require less database storage space.
- [ ] B) BCrypt includes a built-in salt mechanism and is computationally slow (adaptive work factor), making it highly resistant to brute-force and rainbow table attacks.
- [ ] C) BCrypt is a reversible encryption algorithm, allowing admins to retrieve lost passwords.
- [ ] D) MD5 has been deprecated by Java compiler specifications and will not compile.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) BCrypt includes a built-in salt mechanism and is computationally slow (adaptive work factor), making it highly resistant to brute-force and rainbow table attacks.

**Explanation:** Algorithms like MD5 and SHA-256 are fast. Attackers can check billions of hashes per second using GPUs or lookup tables (rainbow tables). BCrypt enforces an adaptive work factor that slows down checks, and automatically salts each password to prevent identical passwords from having identical hashes.
- **Why others are wrong:**
  - A) BCrypt hashes are longer than MD5 hashes.
  - C) Password hashing must be one-way (irreversible).
  - D) MD5 is still compilable in Java, but its use is a security risk.
</details>

---

### 9. Which JPA annotation is used to specify that the database should auto-assign the primary key using database identity columns (e.g. PostgreSQL `SERIAL` or `IDENTITY`)?
- [ ] A) `@GeneratedValue(strategy = GenerationType.AUTO)`
- [ ] B) `@GeneratedValue(strategy = GenerationType.SEQUENCE)`
- [ ] C) `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- [ ] D) `@GeneratedValue(strategy = GenerationType.TABLE)`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `@GeneratedValue(strategy = GenerationType.IDENTITY)`

**Explanation:** `GenerationType.IDENTITY` maps key generation to the database's identity column support, delegating ID assignment entirely to the database engine upon insertion.
- **Why others are wrong:**
  - A) `AUTO` lets the JPA provider choose the strategy based on the database dialect.
  - B) `SEQUENCE` uses an external database sequence generator.
  - D) `TABLE` uses a dedicated key generation table in the database, which is slow and rarely used.
</details>

---

### 10. In a `@OneToMany` relationship mapping, where should the `mappedBy` attribute be placed to define the inverse side of the relationship and avoid duplicate join tables?
- [ ] A) On the `@ManyToOne` side of the relationship.
- [ ] B) On the `@OneToMany` side of the relationship.
- [ ] C) On both sides of the relationship.
- [ ] D) Inside the database schema XML definition.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) On the `@OneToMany` side of the relationship.

**Explanation:** The `mappedBy` attribute is placed on the parent `@OneToMany` collection side. It tells Hibernate that the relationship is mapped by the foreign key property on the child entity (which has the `@ManyToOne` annotation), preventing Hibernate from creating a separate join table.
- **Why others are wrong:**
  - A) The `@ManyToOne` side is the owner of the relationship and contains the `@JoinColumn` declaration; it does not declare `mappedBy`.
  - C) Placing `mappedBy` on both sides is syntactically invalid.
  - D) Relationship ownership is defined in Java entity metadata annotations.
</details>
