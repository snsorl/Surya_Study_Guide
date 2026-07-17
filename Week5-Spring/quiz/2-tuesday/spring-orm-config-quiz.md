# Tuesday Knowledge Check: Spring ORM & Configuration

## Part 1: Multiple Choice & Objective Questions

### 1. What is the primary benefit of the exception translation mechanism provided by Spring ORM?
- [ ] A) It converts all SQL exceptions into checked Java Exceptions so compilers can catch database errors.
- [ ] B) It automatically retries failed database transactions until they succeed.
- [ ] C) It translates vendor-specific database/ORM exceptions into a unified, unchecked `DataAccessException` hierarchy.
- [ ] D) It rewrites slow SQL queries on-the-fly to optimize execution speed.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) It translates vendor-specific database/ORM exceptions into a unified, unchecked `DataAccessException` hierarchy.

**Explanation:** Spring ORM intercepts database exceptions from different vendors (PostgreSQL, MySQL, Oracle) or ORM providers (Hibernate, JPA) and translates them into a common runtime exception hierarchy. This decouples business logic from vendor-specific JDBC error codes.
- **Why others are wrong:**
  - A) Spring's data exception hierarchy consists of unchecked (runtime) exceptions, not checked exceptions.
  - B) Exception translation does not handle automated transaction retry policies.
  - D) Query parsing is handled by the database engine or ORM, not exception translators.
</details>

---

### 2. How do you wire a reference bean dependency in XML configuration?
- [ ] A) `<property name="service" value="myServiceBean" />`
- [ ] B) `<property name="service" ref="myServiceBean" />`
- [ ] C) `<property name="service" inject="myServiceBean" />`
- [ ] D) `<bean ref="myServiceBean" />`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `<property name="service" ref="myServiceBean" />`

**Explanation:** In XML configuration, the `ref` attribute is used within `<property>` or `<constructor-arg>` elements to wire another registered bean by its ID. The `value` attribute is reserved for literal primitives or strings.
- **Why others are wrong:**
  - A) Using `value` will attempt to inject the literal string `"myServiceBean"` into the property rather than the bean instance.
  - C) `inject` is not a valid attribute name in Spring XML schemas.
  - D) The `<bean>` tag is used to define new beans, not configure properties on an existing bean definition.
</details>

---

### 3. Which of the following is a major advantage of XML-based configuration over Annotation-based configuration?
- [ ] A) It is less verbose and compiles faster.
- [ ] B) It allows clean separation of configuration from actual Java compilation code (non-intrusive).
- [ ] C) It provides compiler-time type-safety checks.
- [ ] D) It is processed much faster by the JVM class loader.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It allows clean separation of configuration from actual Java compilation code (non-intrusive).

**Explanation:** XML configuration does not require annotating Java source code. This is useful for third-party libraries where you cannot modify the source code, and it provides a single file to view bean mappings without rebuilding the codebase.
- **Why others are wrong:**
  - A) XML is significantly more verbose than annotations.
  - C) XML configs are written in strings and do not have Java compile-time verification.
  - D) The JVM class loader does not parse Spring XML metadata during class loading; it is read by Spring Context parsers at runtime.
</details>

---

### 4. What happens when Spring detects a circular dependency between two beans using Constructor-based Injection?
- [ ] A) The application boots successfully but one of the beans remains null.
- [ ] B) Spring resolves it by automatically converting one dependency into Setter injection at runtime.
- [ ] C) The container fails to bootstrap and throws a `BeanCurrentlyInCreationException`.
- [ ] D) The JVM crashes due to a `StackOverflowError` before Spring can catch it.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) The container fails to bootstrap and throws a `BeanCurrentlyInCreationException`.

**Explanation:** If Bean A requires Bean B in its constructor, and Bean B requires Bean A in its constructor, Spring cannot instantiate either bean first. This results in a circular dependency error, raising a `BeanCurrentlyInCreationException` during startup.
- **Why others are wrong:**
  - A) Spring enforces strict container resolution and will fail to start if dependencies are unresolvable.
  - B) Spring will not dynamically rewrite Java bytecode constructor calls to setter injections.
  - D) Spring's dependency resolution algorithms detect this state during startup, safely raising a container exception rather than allowing an infinite loop JVM crash.
</details>

---

### 5. In the context of OWASP Broken Access Control (A01), what is an Insecure Direct Object Reference (IDOR)?
- [ ] A) Sending raw SQL command strings inside an HTTP query parameter to bypass database validation.
- [ ] B) Accessing a database object reference directly via Java code without going through the repository interface.
- [ ] C) A vulnerability where an application uses user-supplied keys or database IDs to access resource records directly without verifying the requester's authorization.
- [ ] D) Intercepting unencrypted HTTPS requests to inspect security session tokens in transit.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) A vulnerability where an application uses user-supplied keys or database IDs to access resource records directly without verifying the requester's authorization.

**Explanation:** IDOR occurs when an application exposes a direct reference to a database record (e.g. `/api/users/123/profile`) and allows users to query or edit that path without verifying whether the user has rights to access account ID `123`.
- **Why others are wrong:**
  - A) This describes SQL Injection (OWASP A03).
  - B) Direct object invocations in Java are standard programming practices, not web access vulnerabilities.
  - D) This describes Man-in-the-Middle traffic sniffing.
</details>

---

### 6. Why is relying on HTTP request headers like `X-User-Role: Admin` unsafe for verifying user privileges?
- [ ] A) HTTP headers are always stripped by modern web browsers before dispatch.
- [ ] B) Clients can easily manipulate, add, or spoof HTTP request headers using command-line tools or proxy extensions.
- [ ] C) Spring MVC cannot parse headers that start with custom prefixes.
- [ ] D) custom headers are rejected by default by SQL database drivers.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Clients can easily manipulate, add, or spoof HTTP request headers using command-line tools or proxy extensions.

**Explanation:** Relying on client-supplied headers for authorization checks is a security risk because attackers can modify HTTP headers using tools like curl or Postman (e.g., sending `X-User-Role: Admin`). Access control decisions must be verified on the server side using authenticated session tokens or cryptographically signed tokens (like JWT).
- **Why others are wrong:**
  - A) Browsers do not strip headers; they frequently append them to manage state.
  - C) Spring MVC can easily retrieve custom headers using `@RequestHeader`.
  - D) Database drivers deal with SQL queries, ignoring HTTP request-level headers entirely.
</details>

---

### 7. In Java-based configuration, how is a dependency relationship declared between bean methods?
- [ ] A) By manually declaring the dependent bean as a method parameter inside the container class.
- [ ] B) By using the `@Dependency` annotation on the bean method.
- [ ] C) JavaConfig does not support reference wiring; beans must be wired manually inside the `main` method.
- [ ] D) By appending `<ref>` inside the `@Bean` annotation.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) By manually declaring the dependent bean as a method parameter inside the container class.

**Explanation:** When one bean method depends on another bean in a `@Configuration` class, you simply declare the target bean type as a parameter in the method signature. Spring resolves the parameter type automatically against the container registry.
- **Why others are wrong:**
  - B) `@Dependency` is not a valid Spring Core annotation.
  - C) Java-based configuration is the default standard for dependency injection.
  - D) `@Bean` has no `ref` parameter in Java annotations.
</details>

---

### 8. What is the role of the `@Qualifier` annotation in Spring dependency injection?
- [ ] A) It verifies that a bean meets database connection quality rules.
- [ ] B) It resolves ambiguity when multiple beans of the same type exist in the container context.
- [ ] C) It changes the bean instantiation scope from Singleton to Prototype.
- [ ] D) It speeds up package scanning during bootstrapping.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It resolves ambiguity when multiple beans of the same type exist in the container context.

**Explanation:** If a bean injection point expects a `MessageSender` interface, and there are two beans (`EmailSender` and `SmsSender`) registered in the container, Spring will fail to wire the bean. `@Qualifier` resolves this by specifying the target bean name.
- **Why others are wrong:**
  - A) `@Qualifier` has nothing to do with database health.
  - C) Scopes are managed by the `@Scope` annotation.
  - D) Scanning performance is not affected by `@Qualifier`.
</details>
