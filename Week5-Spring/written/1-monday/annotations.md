# Spring Core Annotations Reference Guide

## Learning Objectives
- Memorize the purpose, usage rules, and attributes of core Spring annotations.
- Correctly configure dependency injection using `@Autowired` and `@Qualifier`.
- Inject application properties and SpEL expressions using `@Value`.
- Configure initialization order and bean metadata using `@DependsOn`, `@Lazy`, `@Scope`, and `@Primary`.

---

## Why This Matters
When writing, reviewing, or debugging Spring Boot code, you will read and write dozens of annotations daily. Having a clear, unified reference sheet that details the specific actions, attributes, and default behaviors of these core annotations is a massive time-saver. This guide functions as a quick-lookup cheatsheet to help you select the exact tool you need to control the behavior of the IoC container.

---

## Core Annotations Reference

### 1. `@Autowired`
Instructs Spring to inject a matching dependency automatically.
-   **Placement:** Constructors, methods, setter methods, fields.
-   **Key Attribute:** `required` (boolean, default: `true`). If set to `false`, Spring will not crash if the dependency is missing, instead injecting `null`.
-   *Usage:*
    ```java
    @Autowired(required = false)
    private OptionalService optionalService;
    ```

### 2. `@Value`
Injects literal values, property keys, or Spring Expression Language (SpEL) expressions into fields or parameters.
-   **Property Key Syntax:** `${property.name}`
-   **SpEL Syntax:** `#{systemProperties['user.home']}`
-   *Usage:*
    ```java
    @Value("${app.timeout.seconds:30}") // 30 is the fallback default value
    private int timeout;
    ```

### 3. `@Qualifier`
Differentiates between multiple beans of the same type. Used alongside `@Autowired` to specify the exact name of the bean to inject.
-   **Placement:** Fields, constructor parameters, method parameters.
-   *Usage:*
    ```java
    public PaymentService(@Qualifier("stripePayment") PaymentGateway gateway) { ... }
    ```

### 4. `@Primary`
Designates a default bean candidate when multiple beans of the same type exist in the container.
-   **Placement:** Classes annotated with `@Component` or methods annotated with `@Bean`.
-   *Usage:*
    ```java
    @Component
    @Primary
    public class DefaultLogger implements Logger { ... }
    ```

### 5. `@Scope`
Defines the lifecycle scope of the bean (e.g. singleton, prototype).
-   **Attributes:**
    -   `value` (e.g., `ConfigurableBeanFactory.SCOPE_PROTOTYPE` or `"prototype"`).
    -   `proxyMode` (`ScopedProxyMode`, default: `DEFAULT`). Used to resolve injection of short-lived beans into long-lived ones.
-   *Usage:*
    ```java
    @Component
    @Scope("prototype")
    public class RequestTracker { ... }
    ```

### 6. `@Lazy`
Delays the initialization of a singleton bean until it is first requested (lazily initialized).
-   **Placement:** `@Component` classes, `@Configuration` classes, `@Bean` methods, or `@Autowired` injection points.
-   *Usage:*
    ```java
    @Component
    @Lazy
    public class ExpensiveReportCreator { ... }
    ```

### 7. `@DependsOn`
Explicitly forces Spring to initialize one or more specific beans *before* initializing the annotated bean. Useful for ordering database migrations, driver initialization, or caching lookups.
-   **Placement:** `@Component` classes or `@Bean` methods.
-   *Usage:*
    ```java
    @Component
    @DependsOn("databaseMigrationRunner")
    public class CacheWarmingService { ... }
    ```

---

## Quick Comparison Table

| Annotation | Primary Purpose | Scope | Key Attribute |
| :--- | :--- | :--- | :--- |
| **`@Autowired`** | Automates dependency injection by type. | Container | `required` (default: `true`) |
| **`@Value`** | Injects property values or SpEL evaluations. | Field/Parameter | String expression value |
| **`@Qualifier`** | Disambiguates duplicate beans by name. | Injection Point | Bean name String |
| **`@Primary`** | Sets default bean candidate in a group. | Declaration | None |
| **`@Scope`** | Adjusts object instantiation boundaries. | Declaration | `value`, `proxyMode` |
| **`@Lazy`** | Defers bean construction to first use. | Declaration/Injection| `value` (default: `true`) |
| **`@DependsOn`** | Commands explicit startup initialization order. | Declaration | String array of bean names |

---

## Summary
-   **`@Autowired`** and **`@Qualifier`** work together to find and wire dependencies by type and name.
-   **`@Value`** pulls externalized application configurations directly into Java code.
-   **`@Primary`** and **`@Scope`** govern configuration candidates and bean lifespans.
-   **`@Lazy`** and **`@DependsOn`** give you control over application startup performance and initialization order.

---

## Additional Resources
-   [Spring Framework Javadocs: Core Annotations](https://docs.spring.io/spring-framework/docs/current/javadoc-api/)
-   [Baeldung: Spring Core Annotations Cheatsheet](https://www.baeldung.com/spring-core-annotations)
-   [Spring Documentation: Annotation Metadata](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html)
