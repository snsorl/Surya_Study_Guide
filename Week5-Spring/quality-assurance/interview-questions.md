# Interview Questions: Week 5 - Spring Framework

This interview question bank validates trainee retention of Week 5 concepts, covering Spring Core, Spring ORM, Spring Boot, Spring Data JPA, and related security (OWASP) and workflow best practices. The questions are structured according to the 70-25-5 difficulty distribution.

---

## Monday: Spring Core & IoC Fundamentals

### Beginner (Foundational)

#### Q1: What is Spring Core and what is the main problem it solves?
**Keywords:** Dependency Injection, Loose Coupling, Boilerplate Code
<details>
<summary>Click to Reveal Answer</summary>

Spring Core is the foundation of the Spring Framework, providing the Inversion of Control (IoC) container. Its main purpose is to solve the problem of tight coupling between classes by automating object creation and dependency wiring, which eliminates manual boilerplate code and improves testability.
</details>

---

#### Q2: Define Inversion of Control (IoC) in your own words.
**Keywords:** Control Flow, Container, Hollywood Principle
<details>
<summary>Click to Reveal Answer</summary>

Inversion of Control is a design principle where the control of object creation, configuration, and execution flow is shifted from the application code to a framework or container. Instead of your code calling library methods, the framework calls your code (the "Hollywood Principle": *Don't call us, we'll call you*).
</details>

---

#### Q3: What is the difference between BeanFactory and ApplicationContext?
**Keywords:** Eager Loading, Lazy Loading, Enterprise Services
<details>
<summary>Click to Reveal Answer</summary>

`BeanFactory` is the basic container interface that instantiates beans lazily (on-demand via `getBean()`). `ApplicationContext` is an advanced interface that extends `BeanFactory`, loading singleton beans eagerly by default during container startup, and providing enterprise features like event propagation, internalization, and AOP integration.
</details>

---

#### Q4: What is Constructor Injection and why is it preferred over other injection types?
**Keywords:** Immutability, Mandatory Dependencies, final fields, Testability
<details>
<summary>Click to Reveal Answer</summary>

Constructor Injection is the process of passing dependent beans as parameters to a class constructor. It is preferred because it allows dependencies to be declared as `final` (ensuring immutability), guarantees that objects cannot be created in an incomplete state without their mandatory dependencies, and simplifies testing using plain constructors without reflection.
</details>

---

#### Q5: What is Setter Injection and when is it appropriate to use?
**Keywords:** Optional Dependencies, Reconfiguration, Setter Methods
<details>
<summary>Click to Reveal Answer</summary>

Setter Injection is the process of injecting dependencies through public setter methods after the bean is instantiated. It is appropriate to use for optional or default dependencies that can be reconfigured or changed later without recreating the object.
</details>

---

#### Q6: What is Field Injection and what are its key drawbacks?
**Keywords:** `@Autowired`, Tight Coupling, Reflection, Unit Testing
<details>
<summary>Click to Reveal Answer</summary>

Field Injection injects dependencies directly into private fields using the `@Autowired` annotation. The main drawbacks are that it tightly couples the class to the Spring container, hides mandatory dependencies, and makes unit testing difficult because you cannot instantiate the class with mock dependencies without using reflection or bootstrapping a Spring test context.
</details>

---

#### Q7: What is a Spring Bean?
**Keywords:** Container Managed, Lifecycle, Metadata Configuration
<details>
<summary>Click to Reveal Answer</summary>

A Spring Bean is a plain Java object that is instantiated, assembled, configured, and managed by the Spring IoC container based on configuration metadata (annotations, Java config, or XML).
</details>

---

#### Q8: What are the different Bean Scopes supported by Spring?
**Keywords:** Singleton, Prototype, Request, Session
<details>
<summary>Click to Reveal Answer</summary>

Spring supports two standard scopes: `singleton` (default, one instance per container) and `prototype` (new instance every request). In web environments, it also supports `request` (one instance per HTTP request), `session` (one instance per HTTP session), and `application` (one instance per ServletContext).
</details>

---

#### Q9: What are `@PostConstruct` and `@PreDestroy` annotations used for?
**Keywords:** Lifecycle, Initialization, Cleanup, JSR-250
<details>
<summary>Click to Reveal Answer</summary>

These are JSR-250 lifecycle annotations. `@PostConstruct` is applied to a method that runs automatically after dependency injection is complete to perform initialization. `@PreDestroy` is applied to a method that runs just before the bean is destroyed by the container to release resources cleanly.
</details>

---

#### Q10: What does the `@ComponentScan` annotation accomplish?
**Keywords:** Stereotypes, Package Scan, Automatic Registration
<details>
<summary>Click to Reveal Answer</summary>

`@ComponentScan` tells the Spring container which base packages to search for classes annotated with stereotype annotations (like `@Component`, `@Service`, `@Repository`, `@Controller`). Classes found are automatically registered as beans in the container.
</details>

---

### Intermediate (Application)

#### Q11: How do you inject a prototype-scoped bean into a singleton-scoped bean without losing its prototype behavior?
**Keywords:** Lookup Method Injection, Scoped Proxy, Provider Interface
<details>
<summary>Click to Reveal Answer</summary>

By default, the prototype bean is injected only once when the singleton is created. To get a new instance on every call, you can configure the prototype bean with a scoped proxy (`@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)`), use Lookup Method Injection (`@Lookup`), or inject an `ObjectFactory<T>` or `Provider<T>` to fetch instances programmatically.
</details>

---

#### Q12: Explain the purpose and behavior of the `@Lazy` annotation.
**Keywords:** Delayed Initialization, Startup performance, Proxy
<details>
<summary>Click to Reveal Answer</summary>

`@Lazy` prevents eager instantiation of a bean. When applied to a singleton bean, the container creates the bean definition during startup but does not instantiate the object until it is first requested in code. When applied to an injection point, Spring injects a lazy proxy instead of the real bean.
</details>

---

#### Q13: What are the differences between `@Component`, `@Service`, `@Repository`, and `@Controller`?
**Keywords:** Stereotypes, Semantic Meaning, Exception Translation
<details>
<summary>Click to Reveal Answer</summary>

All four annotations register classes as Spring beans, but they differ semantically:
- `@Component` is the generic parent annotation.
- `@Service` designates service layer classes containing business logic.
- `@Repository` designates data access components and automatically translates SQL exceptions.
- `@Controller` designates web presentation handlers (MVC/REST).
</details>

---

#### Q14: How does Spring resolve dependencies when multiple beans of the same type are registered?
**Keywords:** `@Qualifier`, `@Primary`, NoUniqueBeanDefinitionException
<details>
<summary>Click to Reveal Answer</summary>

If multiple beans of the same type exist, Spring throws a `NoUniqueBeanDefinitionException`. This can be resolved by using `@Primary` on one bean (marking it as the default choice) or by using `@Qualifier("beanName")` at the injection point to specify the exact bean ID to wire.
</details>

---

### Advanced (Deep Dive)

#### Q15: Walk through the complete lifecycle of a Spring Bean from instantiation to container shutdown.
**Keywords:** Instantiation, Populate Properties, BeanPostProcessor, Initialization, Destruction
<details>
<summary>Click to Reveal Answer</summary>

1. **Instantiation**: The container instantiates the bean using its constructor.
2. **Dependency Injection**: The container populates properties and autowires dependencies.
3. **Aware Interfaces**: If the bean implements Aware interfaces (e.g. `BeanNameAware`, `ApplicationContextAware`), Spring passes the container details.
4. **Before Initialization**: `BeanPostProcessor.postProcessBeforeInitialization()` executes.
5. **Initialization**: The `@PostConstruct` method runs, followed by `InitializingBean.afterPropertiesSet()` and any custom init-method.
6. **After Initialization**: `BeanPostProcessor.postProcessAfterInitialization()` executes. The bean is now ready for use.
7. **Destruction**: When the container closes, `@PreDestroy` runs, followed by `DisposableBean.destroy()` and custom destroy-methods.
</details>

---

## Tuesday: Spring ORM & Configurations

### Beginner (Foundational)

#### Q16: What is Spring ORM and how does it relate to frameworks like Hibernate or JPA?
**Keywords:** Abstraction, Hibernate integration, Transaction Management
<details>
<summary>Click to Reveal Answer</summary>

Spring ORM is a module that integrates Spring with object-relational mapping frameworks like Hibernate, JPA, or JDO. It does not replace these frameworks; rather, it provides a consistent configuration style, Exception Translation, and declarative transaction management wrappers around them.
</details>

---

#### Q17: What is Exception Translation in Spring ORM and why is it useful?
**Keywords:** DataAccessException, Unchecked Exception, Decoupling
<details>
<summary>Click to Reveal Answer</summary>

Exception Translation is a mechanism where Spring automatically intercepts vendor-specific database/ORM exceptions (such as HibernateException or SQLException) and translates them into Spring's unchecked `DataAccessException` hierarchy. This keeps the service layer decoupled from specific persistence technology errors.
</details>

---

#### Q18: What is the purpose of a DataSource bean in a Spring application?
**Keywords:** Connection Pool, Database URL, Credentials
<details>
<summary>Click to Reveal Answer</summary>

A `DataSource` bean represents the database connection configuration. It manages connection pooling, database URLs, drivers, and credentials, providing a standardized interface for Hibernate, JPA, or JDBC connections to request database connections.
</details>

---

#### Q19: Compare XML-based configuration and Annotation-based configuration.
**Keywords:** Non-intrusive, Compile-time safety, Redundancy
<details>
<summary>Click to Reveal Answer</summary>

- **XML Config** is non-intrusive (no annotations in Java code) and centralizes configurations, making it useful for third-party classes. However, it is verbose and lacks compile-time safety.
- **Annotation Config** is close to the code, highly readable, and type-safe. However, changing configurations requires recompiling Java classes.
</details>

---

#### Q20: What is Reference Injection in Spring configurations?
**Keywords:** `<ref>`, Autowiring, Bean ID
<details>
<summary>Click to Reveal Answer</summary>

Reference Injection is the configuration process of injecting an instance of a registered bean into a property of another bean (e.g. using `ref` in XML or parameter wiring in JavaConfig), rather than passing literal primitive values.
</details>

---

#### Q21: What is a Circular Dependency?
**Keywords:** Infinite Loop, Startup Failure, constructor-injection
<details>
<summary>Click to Reveal Answer</summary>

A Circular Dependency occurs when Bean A depends on Bean B, and Bean B depends on Bean A. If constructor injection is used for both, the Spring container cannot instantiate either bean, resulting in a bootstrapping failure (`BeanCurrentlyInCreationException`).
</details>

---

#### Q22: Explain the OWASP Broken Access Control (A01) vulnerability.
**Keywords:** Authorization, Access rights, Privilege Escalation
<details>
<summary>Click to Reveal Answer</summary>

Broken Access Control occurs when web endpoints fail to enforce server-side validation of user permissions. This allows users to access resources or perform actions outside of their intended privileges (e.g., viewing other users' accounts or calling admin functions).
</details>

---

#### Q23: What is Insecure Direct Object Reference (IDOR)?
**Keywords:** Key tampering, Owner check, Access validation
<details>
<summary>Click to Reveal Answer</summary>

IDOR is a subcategory of Broken Access Control where an application exposes references to database records (such as primary keys or files) in URLs or parameters, allowing attackers to access unauthorized records by simply modifying the reference value.
</details>

---

#### Q24: What does the `@Primary` annotation do?
**Keywords:** Default Bean, Ambiguity, Autowiring preference
<details>
<summary>Click to Reveal Answer</summary>

`@Primary` designates a bean as the default choice when multiple beans of the same type are registered. It tells the Spring container to select this bean for autowiring if no qualifier is explicitly requested.
</details>

---

#### Q25: Why is using raw HTTP headers for authorization checks unsafe?
**Keywords:** Client Tampering, Spoofing, Curl/Postman
<details>
<summary>Click to Reveal Answer</summary>

HTTP request headers can be modified or spoofed by clients using tools like Postman or browser extensions. Recommending access controls based on unverified client-side headers is a security risk; authorization must be verified on the server side using cryptographically secure tokens.
</details>

---

### Intermediate (Application)

#### Q26: How does Spring resolve a dependency using `@Autowired` if there are multiple beans of the same type?
**Keywords:** By Type, By Name, Field Name match
<details>
<summary>Click to Reveal Answer</summary>

Spring first attempts to resolve the dependency **by type**. If multiple beans of the same type are found, Spring falls back to resolving **by name**, matching the variable name of the injection point to the bean ID of the registered beans. If no name matches and no `@Qualifier` or `@Primary` is present, it throws an exception.
</details>

---

#### Q27: How can you resolve a circular dependency between two Spring beans?
**Keywords:** Setter Injection, `@Lazy` annotation
<details>
<summary>Click to Reveal Answer</summary>

You can resolve circular dependencies by refactoring from constructor injection to setter injection (which allows Spring to instantiate the beans before injecting properties), or by annotating one of the constructors' parameters with `@Lazy` to inject a lazy proxy instead of the real bean.
</details>

---

#### Q28: How do you mitigate IDOR vulnerabilities in a Spring REST API?
**Keywords:** Security Context, Principal, Database Validation
<details>
<summary>Click to Reveal Answer</summary>

To prevent IDOR, do not fetch records solely by user-supplied ID parameters. Instead, retrieve the logged-in user's identity from the secure backend security context (e.g. JWT Principal) and verify that the user owns the requested resource before returning it from the database.
</details>

---

#### Q29: What is the difference between `@Autowired` and `@Resource` annotations?
**Keywords:** JSR-250, Spring Framework, Resolution order
<details>
<summary>Click to Reveal Answer</summary>

- `@Autowired` is a Spring-specific annotation that resolves dependencies **by type** first, then falls back to name.
- `@Resource` is a standard Java annotation (JSR-250) that resolves dependencies **by name** first, then falls back to type.
</details>

---

### Advanced (Deep Dive)

#### Q30: Explain how Spring processes class annotations under the hood during the bootstrapping scan phase.
**Keywords:** BeanDefinitionRegistry, ASM Bytecode, BeanFactoryPostProcessor
<details>
<summary>Click to Reveal Answer</summary>

During bootstrapping, Spring scans target packages using an ASM bytecode reader to detect classes with stereotype annotations without loading them into the JVM classloader first. For each class found, a `BeanDefinition` metadata template is created and registered in the `BeanDefinitionRegistry`. Later, `BeanFactoryPostProcessor` classes parse and modify these definitions before the container actually instantiates the beans.
</details>

---

## Wednesday: Spring Boot & Observability

### Beginner (Foundational)

#### Q31: What is Spring Boot and what is its primary value proposition?
**Keywords:** Auto-Configuration, Starters, Embedded Server, Opinionated
<details>
<summary>Click to Reveal Answer</summary>

Spring Boot is an extension of the Spring Framework designed to simplify application configuration. Its primary value is providing opinionated defaults, starters for dependency management, auto-configuration of beans, and embedded web containers, allowing developers to create standalone, production-ready apps quickly.
</details>

---

#### Q32: What is the purpose of Spring Initializr?
**Keywords:** Scaffolding, Project structure, pom.xml generation
<details>
<summary>Click to Reveal Answer</summary>

Spring Initializr (start.spring.io) is a web-based project scaffolding tool. It generates a pre-configured Maven or Gradle project structure containing selected dependencies, Java versions, and build settings to start development immediately.
</details>

---

#### Q33: Explain Auto-Configuration in Spring Boot.
**Keywords:** Classpath check, Conditional bean configuration
<details>
<summary>Click to Reveal Answer</summary>

Auto-Configuration is Spring Boot's mechanism for configuring beans in the application context based on the jar dependencies present on the project classpath. For example, if a JDBC driver is on the classpath, Spring Boot automatically configures a database DataSource bean with default settings.
</details>

---

#### Q34: What is a Spring Boot Starter?
**Keywords:** Dependency grouping, Version management, Transitive dependencies
<details>
<summary>Click to Reveal Answer</summary>

A Spring Boot Starter is a curated set of dependency descriptors that groups related libraries (like Spring Web, Hibernate, and Jackson) under a single dependency declaration. This simplifies build configuration and eliminates version compatibility issues.
</details>

---

#### Q35: What is Project Lombok and how does it benefit Java developers?
**Keywords:** Boilerplate removal, Compile-time annotations
<details>
<summary>Click to Reveal Answer</summary>

Project Lombok is a Java library that plugs into the IDE and compiler to generate getters, setters, constructors, builders, and other boilerplate code at compile-time based on annotations, reducing code verbosity.
</details>

---

#### Q36: What does Lombok's `@Data` annotation do?
**Keywords:** `@Getter`, `@Setter`, `@RequiredArgsConstructor`, `@ToString`, `@EqualsAndHashCode`
<details>
<summary>Click to Reveal Answer</summary>

`@Data` is a convenience annotation that bundles `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, and `@RequiredArgsConstructor` (for final fields) into a single declaration on a class.
</details>

---

#### Q37: What is Spring Boot DevTools used for?
**Keywords:** Live Reload, Fast Restart, Cache disabling
<details>
<summary>Click to Reveal Answer</summary>

`spring-boot-devtools` is a module that improves developer productivity by automatically restarting the application context when class files change, enabling LiveReload for static resources, and disabling browser-side caching for templates during development.
</details>

---

#### Q38: What is Spring Boot Actuator?
**Keywords:** Observability, Production-ready monitoring, Diagnostics
<details>
<summary>Click to Reveal Answer</summary>

Spring Boot Actuator is a sub-project that adds monitoring, metrics, health indicators, and environment diagnostics to an application through built-in HTTP endpoints.
</details>

---

#### Q39: Which Actuator endpoint is exposed by default over HTTP?
**Keywords:** `/health`, Security fallback, UP/DOWN status
<details>
<summary>Click to Reveal Answer</summary>

By default, only the `/actuator/health` endpoint is exposed over HTTP for security reasons. It returns a simple JSON object indicating whether the application is active (`{"status":"UP"}`).
</details>

---

#### Q40: What does the `@Profile` annotation accomplish?
**Keywords:** Conditional registration, Environment selection
<details>
<summary>Click to Reveal Answer</summary>

`@Profile` conditionally restricts bean registration based on the active application environment. A bean annotated with `@Profile("prod")` will only be registered in the application context if the "prod" profile is active.
</details>

---

### Intermediate (Application)

#### Q41: Explain how Spring Boot's `@EnableAutoConfiguration` uses conditional annotations.
**Keywords:** `@ConditionalOnClass`, `@ConditionalOnMissingBean`, spring.factories
<details>
<summary>Click to Reveal Answer</summary>

`@EnableAutoConfiguration` reads candidate configuration classes listed in meta-files (like `imports` files inside starters). Each configuration class uses conditional annotations (such as `@ConditionalOnClass` or `@ConditionalOnMissingBean`) to register beans only if specific conditions are met on the classpath.
</details>

---

#### Q42: What are the security implications of exposing all Actuator endpoints via web configurations?
**Keywords:** Information Disclosure, Credentials leak, heapdump risk
<details>
<summary>Click to Reveal Answer</summary>

Exposing all endpoints (using `*`) is a security risk because paths like `/env` can leak database credentials or API keys, while `/heapdump` or `/threaddump` can leak sensitive data in memory. These endpoints should be secured behind authentication or kept on a private port.
</details>

---

#### Q43: How do you configure profile-specific properties files in Spring Boot?
**Keywords:** application-{profile}.yml, Profile activation
<details>
<summary>Click to Reveal Answer</summary>

Create files named `application-{profile}.yml` (e.g. `application-dev.yml`, `application-prod.yml`). Properties defined in these files override default configurations in `application.yml` when the corresponding profile is activated via `spring.profiles.active`.
</details>

---

#### Q44: How do you implement a custom Actuator InfoContributor?
**Keywords:** InfoContributor interface, Info.Builder, `@Component`
<details>
<summary>Click to Reveal Answer</summary>

Implement the `InfoContributor` interface and define the `contribute(Info.Builder builder)` method to add custom data. Annotating the implementation class with `@Component` registers it to display data under the `/actuator/info` endpoint.
</details>

---

### Advanced (Deep Dive)

#### Q45: Trace the execution flow from `SpringApplication.run(App.class, args)` to the bootstrap of an embedded servlet container.
**Keywords:** ApplicationContext bootstrap, tomcat/jetty startup, run method
<details>
<summary>Click to Reveal Answer</summary>

1. Calling `run()` initializes a `SpringApplication` instance, scanning class paths to identify the application type.
2. It notifies listeners and starts the `ApplicationContext`.
3. During context initialization, Spring Boot's auto-configuration registers an embedded servlet container factory bean (Tomcat, Jetty, or Undertow).
4. The context refresh phase calls the factory to bootstrap and start the web server container, binding it to the servlet dispatcher.
</details>

---

## Thursday: Spring Data JPA & Transactions

### Beginner (Foundational)

#### Q46: What is Spring Data JPA and how does it simplify database operations?
**Keywords:** Repository Abstraction, boilerplate reduction, JpaRepository
<details>
<summary>Click to Reveal Answer</summary>

Spring Data JPA is a framework that provides repository support for the JPA persistence standard. It eliminates the boilerplate code needed to write DAOs and entity queries by automatically implementing database CRUD repositories at runtime based on developer interfaces.
</details>

---

#### Q47: What is the difference between `JpaRepository` and `CrudRepository`?
**Keywords:** Pagination, Sorting, JPA flushing
<details>
<summary>Click to Reveal Answer</summary>

`CrudRepository` provides basic CRUD operations. `JpaRepository` extends `PagingAndSortingRepository` (which extends `CrudRepository`), adding JPA-specific features like flushing changes immediately, deleting records in batches, and returning results as Lists instead of Iterables.
</details>

---

#### Q48: What are Property Expressions in Spring Data query methods?
**Keywords:** Method naming convention, SQL parsing, CamelCase fields
<details>
<summary>Click to Reveal Answer</summary>

Property Expressions are naming patterns in repository interface methods (e.g. `findByEmailAndLastName`) that Spring Data JPA parses to generate database queries dynamically based on entity field names.
</details>

---

#### Q49: What is the `@Query` annotation used for in Spring Data JPA?
**Keywords:** JPQL, Native SQL, Custom query override
<details>
<summary>Click to Reveal Answer</summary>

`@Query` defines custom JPQL (Java Persistence Query Language) or native SQL statements inside repository methods. It is used when Spring's automatic method naming parser is insufficient for complex database queries.
</details>

---

#### Q50: Explain the `@Transactional` annotation.
**Keywords:** Transaction boundary, commit/rollback, AOP proxy
<details>
<summary>Click to Reveal Answer</summary>

`@Transactional` wraps a class method in a database transaction boundary. The framework automatically starts a database transaction when the method is called, commits it when the method completes successfully, and rolls it back if a RuntimeException is thrown.
</details>

---

#### Q51: What are the ACID properties of database transactions?
**Keywords:** Atomicity, Consistency, Isolation, Durability
<details>
<summary>Click to Reveal Answer</summary>

- **Atomicity**: The transaction succeeds or fails as a single unit.
- **Consistency**: Changes bring the database from one valid state to another.
- **Isolation**: Concurrent transactions do not interfere with each other.
- **Durability**: Committed data is permanently saved to database storage.
</details>

---

#### Q52: List three common bean validation annotations defined in JSR-380.
**Keywords:** `@NotNull`, `@NotBlank`, `@Size`, `@Min`, `@Pattern`
<details>
<summary>Click to Reveal Answer</summary>

Common validation annotations include `@NotBlank` (verifies character sequences are not null or whitespace), `@Min` (verifies numbers are above a threshold), and `@Size` (checks string or array boundaries).
</details>

---

#### Q53: What does the `@Valid` annotation do when placed before a controller request body parameter?
**Keywords:** Argument Resolver validation, 400 Bad Request
<details>
<summary>Click to Reveal Answer</summary>

`@Valid` tells the Spring MVC request resolver to validate the incoming JSON object constraints (like `@NotNull` or `@Size` on fields) before executing the target controller handler method. If validation fails, it throws a validation exception.
</details>

---

#### Q54: What is a Global Exception Handler in Spring and how is it defined?
**Keywords:** `@RestControllerAdvice`, `@ExceptionHandler`, JSON response mapping
<details>
<summary>Click to Reveal Answer</summary>

A Global Exception Handler intercepts exceptions thrown across all web controllers. It is defined using the `@RestControllerAdvice` annotation on a configuration class, containing custom methods annotated with `@ExceptionHandler` to return unified JSON error responses.
</details>

---

#### Q55: What is OWASP Cryptographic Failure (A02)?
**Keywords:** Plaintext passwords, Weak hashing, Key management
<details>
<summary>Click to Reveal Answer</summary>

Cryptographic Failure refers to vulnerabilities arising from insufficient protection of sensitive data in transit or at rest. Examples include saving passwords in plaintext, using weak hashing algorithms (like MD5), or implementing poor encryption key management.
</details>

---

### Intermediate (Application)

#### Q56: Compare the `Propagation.REQUIRED` and `Propagation.REQUIRES_NEW` transaction propagation strategies.
**Keywords:** Active transaction, Suspend transaction, Rollback boundary
<details>
<summary>Click to Reveal Answer</summary>

- `REQUIRED` (default) joins the active outer transaction if one exists; otherwise, it creates a new transaction. A rollback in the inner method rolls back the entire transaction.
- `REQUIRES_NEW` suspends any active outer transaction and creates a new, independent transaction. Rollbacks inside the inner transaction do not affect the outer transaction.
</details>

---

#### Q57: What exception is thrown when validation fails on a `@RequestBody` parameter, and how do you customize the response body?
**Keywords:** MethodArgumentNotValidException, BindingResult, RestControllerAdvice
<details>
<summary>Click to Reveal Answer</summary>

Spring throws a `MethodArgumentNotValidException`. To customize the response, write an `@ExceptionHandler` method for this exception in a `@RestControllerAdvice` class, extract field errors from the exception's `BindingResult`, and return a custom formatted JSON payload with a `400 Bad Request` status.
</details>

---

#### Q58: Why is using BCrypt preferred over SHA-256 for password hashing?
**Keywords:** Salt, Computational cost, Brute-force resistance
<details>
<summary>Click to Reveal Answer</summary>

While SHA-256 is fast, it is vulnerable to brute-force attacks using GPUs. BCrypt includes a built-in salt mechanism (preventing rainbow table attacks) and enforces an adaptive work factor (computational cost delay), making brute-force attacks slow and expensive.
</details>

---

#### Q59: How do you create custom validation constraints in Spring?
**Keywords:** ConstraintValidator interface, Custom Annotation, `@Constraint`
<details>
<summary>Click to Reveal Answer</summary>

Create a custom annotation interface and define a validator class that implements `ConstraintValidator<AnnotationName, TargetType>`. Annotate the custom annotation with `@Constraint(validatedBy = ValidatorClass.class)` to bind them together.
</details>

---

### Advanced (Deep Dive)

#### Q60: Explain the proxy mechanism of `@Transactional` and why self-invocation does not trigger transactions.
**Keywords:** AOP Proxy, Internal call, transaction bypass
<details>
<summary>Click to Reveal Answer</summary>

Spring uses aspect-oriented programming (AOP) proxies to manage transactions. When a client invokes a method on a bean, the call goes through a proxy that wraps transaction boundaries around the execution. However, if a method within the bean calls another transactional method *in the same class*, the call bypasses the proxy (self-invocation), meaning no transaction context is created or rolled back.
</details>

---

## Friday: Production Scaffolding & HITL Workflows

### Beginner (Foundational)

#### Q61: What is database schema migration?
**Keywords:** Schema history, Versioning, SQL scripts
<details>
<summary>Click to Reveal Answer</summary>

Database schema migration is the process of tracking, versioning, and applying updates to a database schema (DDL changes) using version-controlled scripts to keep database environments synchronized.
</details>

---

#### Q62: What is the risk of using `hibernate.ddl-auto = update` in a production environment?
**Keywords:** Data loss, Performance degradation, schema corruption
<details>
<summary>Click to Reveal Answer</summary>

Using `update` in production is risky because Hibernate might run unintended DDL changes, corrupting tables or modifying column types without review, which can lead to data loss or performance degradation.
</details>

---

#### Q63: What does the `@Table` annotation do in JPA?
**Keywords:** Table name mapping, Database schema naming
<details>
<summary>Click to Reveal Answer</summary>

The `@Table` annotation maps a Java entity class to a specific database table name. It is used to override default class-to-table naming conventions or define unique constraints.
</details>

---

#### Q64: What is the purpose of `@GeneratedValue` in JPA entity modeling?
**Keywords:** Primary Key generation, Identity, Sequences
<details>
<summary>Click to Reveal Answer</summary>

`@GeneratedValue` defines the strategy for assigning entity primary keys. Common strategies include `IDENTITY` (database-managed auto-increment) and `SEQUENCE` (database sequence generator).
</details>

---

#### Q65: Explain the difference between `@OneToMany` and `@ManyToOne` relationships.
**Keywords:** Parent-child relation, Foreign Key owner
<details>
<summary>Click to Reveal Answer</summary>

- `@ManyToOne` maps a relationship from a child entity to a single parent entity (owning the foreign key).
- `@OneToMany` maps a relationship from a parent entity to a collection of child entities.
</details>

---

#### Q66: What is the purpose of the `mappedBy` attribute in a `@OneToMany` relationship?
**Keywords:** Inverse side, Foreign key owner, Avoid join table
<details>
<summary>Click to Reveal Answer</summary>

The `mappedBy` attribute defines the inverse side of a bidirectional relationship, pointing to the property in the child class that owns the foreign key. This prevents Hibernate from creating a separate join table.
</details>

---

#### Q67: What are Cascades in JPA and why are they used?
**Keywords:** CascadeType, Propagation, Persistence context
<details>
<summary>Click to Reveal Answer</summary>

Cascading propagates entity state transitions (persist, merge, remove) from a parent entity to its associated child entities. This allows saving or deleting a parent entity to automatically save or delete its children.
</details>

---

#### Q68: What does the `orphanRemoval` attribute do in a `@OneToMany` collection mapping?
**Keywords:** Orphan records, Garbage collection, Collection removal
<details>
<summary>Click to Reveal Answer</summary>

If a child entity is removed from a parent's collection mapping, `orphanRemoval = true` automatically deletes that orphan record from the database.
</details>

---

#### Q69: What is Human-in-the-Loop (HITL) validation in AI workflows?
**Keywords:** AI verification, Approval gate, Security check
<details>
<summary>Click to Reveal Answer</summary>

HITL is a workflow where a human developer audits and approves AI outputs at key checkpoints (such as database schemas or security configurations) before committing changes to the project.
</details>

---

#### Q70: Why is deploying AI-generated security configurations without human review high-risk?
**Keywords:** Security vulnerabilities, Over-permissions, CSRF/CORS bugs
<details>
<summary>Click to Reveal Answer</summary>

AI models may suggest insecure configurations to bypass connection errors (like disabling CSRF or using wildcard CORS rules), leading to security vulnerabilities if deployed without human review.
</details>

---

### Intermediate (Application)

#### Q71: Explain the JPA N+1 select query problem and how to resolve it.
**Keywords:** Lazy Loading, JOIN FETCH, EntityGraph
<details>
<summary>Click to Reveal Answer</summary>

The N+1 problem occurs when an application loads a list of $N$ parent entities, and then makes $N$ additional database queries to fetch child relationships lazily. This can be resolved by using `JOIN FETCH` inside custom `@Query` definitions or using an `@EntityGraph` to eagerly load relations in a single database query.
</details>

---

#### Q72: What are the key steps to migrate a REST API from Javalin to Spring Boot?
**Keywords:** Dependency refactoring, Stereotypes, Controller endpoints
<details>
<summary>Click to Reveal Answer</summary>

1. Add Spring Boot starters and remove Javalin dependencies.
2. Annotate the main class with `@SpringBootApplication`.
3. Refactor Javalin handlers to Spring `@RestController` methods, mapping paths using `@GetMapping` or `@PostMapping`.
4. Refactor direct DTO bindings to Spring controller parameters with `@RequestBody` or `@PathVariable`.
5. Migrate manual database connections to Spring Data JPA repositories.
</details>

---

#### Q73: How do you map a composite primary key in a JPA Entity?
**Keywords:** `@Embeddable`, `@EmbeddedId`, IdClass
<details>
<summary>Click to Reveal Answer</summary>

Create a class representing the primary key fields and annotate it with `@Embeddable`. In the main entity class, declare this key class as a field and annotate it with `@EmbeddedId`. Alternatively, use `@IdClass` on the entity class and annotate key fields with `@Id`.
</details>

---

#### Q74: What checklist items should be included in a HITL verification protocol for auditing AI-generated Spring Security code?
**Keywords:** BCrypt Hashing check, CSRF validation, CORS restrict, Wildcard path checks
<details>
<summary>Click to Reveal Answer</summary>

- Verify `PasswordEncoder` uses `BCrypt` or `Argon2` instead of legacy hashing.
- Confirm CSRF is only disabled if stateless JWT authentication is used.
- Restrict CORS origins to trusted domains, avoiding wildcards (`*`).
- Review URL authorization matchers (`**` vs `*`) to ensure nested paths are secured.
</details>

---

### Advanced (Deep Dive)

#### Q75: Explain the four JPA Entity States (Transient, Managed, Detached, Removed) and how transitions affect database synchronization.
**Keywords:** Persistence Context, Session flush, Entity transition
<details>
<summary>Click to Reveal Answer</summary>

- **Transient**: The object is created (`new`) but is not associated with the persistence context (no database record).
- **Managed/Persistent**: The object is tracked by the persistence context. Changes are automatically flushed to the database when the transaction commits.
- **Detached**: The context is closed or cleared, and changes are no longer tracked unless the entity is merged back.
- **Removed**: The entity is marked for deletion and will be removed from the database when the session flushes.
</details>
