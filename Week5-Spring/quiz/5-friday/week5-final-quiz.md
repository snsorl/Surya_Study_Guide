# Friday Cumulative Knowledge Check: Spring Enterprise Integration

## Part 1: Multiple Choice & Objective Questions

### 1. Which of the following best describes the core mechanism of Spring's `@Transactional` annotation under the hood?
- [ ] A) It compiles JDBC statements into native database triggers.
- [ ] B) It wraps the target bean in a dynamic proxy that intercepts method calls to start, commit, or rollback transactions.
- [ ] C) It runs a background thread that polls the database for changes.
- [ ] D) It disables standard Java garbage collection during database transactions.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It wraps the target bean in a dynamic proxy that intercepts method calls to start, commit, or rollback transactions.

**Explanation:** Spring uses AOP proxies to manage transactions. When a method with `@Transactional` is called, the proxy intercepts the call, starts a database connection transaction, executes the target method, and automatically commits (or rolls back if a RuntimeException occurs).
- **Why others are wrong:**
  - A) Spring does not create database triggers at compile-time.
  - C) Transaction boundaries are synchronous thread-bound events, not polling threads.
  - D) Garbage collection is not affected or altered by Spring transaction management.
</details>

---

### 2. If a developer calls a `@Transactional` method from another method *within the same class*, what is the expected transaction behavior?
- [ ] A) A new transaction is started because `@Transactional` is active.
- [ ] B) The call bypasses the transaction proxy (self-invocation), meaning no transaction context is created or joined.
- [ ] C) The application crashes due to a proxy recursion error.
- [ ] D) Spring automatically spins up a separate thread to handle the transaction.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The call bypasses the transaction proxy (self-invocation), meaning no transaction context is created or joined.

**Explanation:** Because Spring transaction management relies on proxy wrapping, calls made from within the same class (self-invocation) bypass the proxy, executing the method directly as a local call. Thus, any `@Transactional` annotation on the inner method is ignored.
- **Why others are wrong:**
  - A) The proxy is bypassed, so the transaction annotation is not parsed.
  - C) Self-invocation compiles and runs but ignores transaction boundaries without throwing context errors.
  - D) Transactions are thread-bound by default; Spring will not run them on asynchronous threads unless `@Async` is configured.
</details>

---

### 3. Which of the following conditions must be met for a Spring Boot auto-configuration class to instantiate a bean?
- [ ] A) The project must compile using XML metadata.
- [ ] B) Conditional rules defined by annotations like `@ConditionalOnClass` or `@ConditionalOnMissingBean` must evaluate to true.
- [ ] C) The bean must be declared in both XML and JavaConfig simultaneously.
- [ ] D) The developer must manually scan the starter jar files.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Conditional rules defined by annotations like `@ConditionalOnClass` or `@ConditionalOnMissingBean` must evaluate to true.

**Explanation:** Spring Boot uses `@Conditional*` annotations to check whether a class is on the classpath, whether a property is set, or whether another bean is already registered. If the conditions are met, the auto-configuration class registers the bean.
- **Why others are wrong:**
  - A) XML is optional in Spring Boot.
  - C) Dual-declaration is a configuration conflict and will throw exceptions.
  - D) Class scanning is handled automatically by the boot framework, requiring no manual actions by the developer.
</details>

---

### 4. What is the key principle of Human-in-the-Loop (HITL) validation in AI-assisted software development?
- [ ] A) Running AI-generated code directly in production to collect real-world feedback.
- [ ] B) Decomposing tasks so that AI does the entire coding task while humans only write documentation.
- [ ] C) Integrating structured checkpoints where a human review process validates AI-generated code for security, architecture, and correctness before integration.
- [ ] D) Disabling automated CI/CD checks to let humans test code manually in staging.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Integrating structured checkpoints where a human review process validates AI-generated code for security, architecture, and correctness before integration.

**Explanation:** HITL establishes checks where developers audit AI outputs against security rules (like OWASP guidelines) and architectural designs before accepting changes, ensuring AI is used safely.
- **Why others are wrong:**
  - A) Running unverified AI code directly in production violates safety and quality practices.
  - B) Coding tasks must be audited by humans; humans write, review, and test code alongside AI.
  - D) Automated checks are essential; HITL supplements automated testing with human judgment.
</details>

---

### 5. In a Spring Data JPA application, what occurs if you attempt to save a child entity that has a `@ManyToOne` relationship to a parent entity without saving the parent first, assuming no cascades are configured?
- [ ] A) Spring automatically creates and saves a blank parent record.
- [ ] B) A database foreign key violation exception is thrown at runtime because the parent ID reference does not exist in the parent table.
- [ ] C) The child entity is saved and the parent reference is silently ignored.
- [ ] D) The compiler throws a syntax error preventing build completion.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A database foreign key violation exception is thrown at runtime because the parent ID reference does not exist in the parent table.

**Explanation:** Relational databases enforce foreign key constraints. If a child record points to a parent ID that does not exist in the parent table, the database rejects the insert, throwing a data constraint violation exception. To prevent this, you must save the parent entity first or configure cascade options (`CascadeType.PERSIST`/`CascadeType.ALL`).
- **Why others are wrong:**
  - A) Hibernate does not auto-create parent entities without cascade configurations.
  - C) Foreign key constraints prevent invalid parent references.
  - D) This is a runtime relational integrity check, not a compiler syntax check.
</details>

---

### 6. Which Spring Boot profile configuration file has the highest priority and will override values declared in other files, assuming all profiles are active?
- [ ] A) `application.properties`
- [ ] B) `application-dev.yml` (when the `dev` profile is active)
- [ ] C) `application-prod.properties` (when the `prod` profile is active)
- [ ] D) Profile-specific files (`application-{profile}`) always override the default `application` configuration file.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** D) Profile-specific files (`application-{profile}`) always override the default `application` configuration file.

**Explanation:** Spring Boot evaluates default settings inside `application.properties`/`application.yml` first. Profile-specific configuration files (`application-dev`, `application-prod`) are loaded later and override properties with the same keys.
- **Why others are wrong:**
  - A) Default files have the lowest priority and are overridden by profile-specific files.
  - B, C) The priority is based on profile activation, but profile-specific properties always override non-specific defaults.
</details>

---

### 7. How does a developer configure Spring Validation to return a HTTP `400 Bad Request` instead of a HTTP `500 Internal Server Error` when input validation fails?
- [ ] A) By disabling default exception logging inside `application.properties`.
- [ ] B) By using a `@RestControllerAdvice` class with an `@ExceptionHandler` method targeting `MethodArgumentNotValidException`.
- [ ] C) By annotating the REST Controller class with `@ResponseStatus(HttpStatus.BAD_REQUEST)`.
- [ ] D) Spring automatically handles validation exceptions by returning a 400 status code without requiring custom code.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) By using a `@RestControllerAdvice` class with an `@ExceptionHandler` method targeting `MethodArgumentNotValidException`.

**Explanation:** By default, Spring MVC returns a 400 Bad Request status code for `MethodArgumentNotValidException`, but it returns a raw default error body. To customize the response body, return a map of validation messages, and ensure a clean error structure, developers use a `@RestControllerAdvice` class with a target `@ExceptionHandler` method.
- **Why others are wrong:**
  - A) Disabling logging does not change the HTTP response status code.
  - C) Placing `@ResponseStatus` on the class forces that status code for all routes, even successful ones.
  - D) While Spring returns a 400 status, it does not format the validation fields and messages into a clean JSON map by default; that requires an exception handler.
</details>

---

### 8. What is the risk of using Field Injection (`@Autowired` on private class fields) regarding unit testing?
- [ ] A) It prevents tests from running in parallel.
- [ ] B) It forces developers to instantiate the entire Spring container or use reflection utilities (like Mockito's `@InjectMocks`) to inject dependencies into the class under test.
- [ ] C) It compiles slower than constructor injection.
- [ ] D) It does not support mock dependencies.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It forces developers to instantiate the entire Spring container or use reflection utilities (like Mockito's `@InjectMocks`) to inject dependencies into the class under test.

**Explanation:** Field injection hides dependencies from class constructors. To write a unit test with mock dependencies, you cannot simply call `new MyService(mockRepository)`. Instead, you must use reflection libraries or start a Spring context, which slows down test suites.
- **Why others are wrong:**
  - A) Field injection does not affect test parallelism.
  - C) Java compilation speed is unaffected by annotation placement.
  - D) Mocking is supported, but the setup code is complex.
</details>

---

### 9. What is the behavior of the `CascadeType.REMOVE` option in a JPA relationship mapping?
- [ ] A) It automatically deletes foreign key columns from database tables during startup.
- [ ] B) It deletes the child entities in the database when the parent entity is deleted.
- [ ] C) It throws an exception if you attempt to delete an entity that has active relationships.
- [ ] D) It wipes the entire database table when the entity mapping changes.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It deletes the child entities in the database when the parent entity is deleted.

**Explanation:** `CascadeType.REMOVE` ensures that if a parent entity (e.g. `Order`) is deleted, Hibernate automatically deletes all associated child records (e.g. `OrderItem`) that are referenced in the collection.
- **Why others are wrong:**
  - A) Database columns are managed by schema configurations, not entity cascades.
  - C) Cascade options automate deletes; they do not block them.
  - D) It only affects related records of the deleted entity instance, not entire database tables.
</details>

---

### 10. Which bean lifecycle phase executes immediately after the Spring container instantiates a bean and injects all of its dependencies?
- [ ] A) `@PreDestroy`
- [ ] B) `@PostConstruct`
- [ ] C) `BeanFactoryPostProcessor.postProcessBeanFactory()`
- [ ] D) The bean's class constructor.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `@PostConstruct`

**Explanation:** After instantiation and dependency injection are complete, the container invokes any initialization callbacks. In modern Spring, this is defined by annotating a method with `@PostConstruct`.
- **Why others are wrong:**
  - A) `@PreDestroy` executes when the container is shutting down, which is the final phase of the lifecycle.
  - C) `BeanFactoryPostProcessor` runs before any bean instances are created.
  - D) The class constructor runs first to instantiate the bean, before dependencies are injected.
</details>
