# Exercise: Tracing the Spring Bean Lifecycle

## Objective
Implement lifecycle hooks on a Spring bean using JSR-250 annotations (`@PostConstruct` and `@PreDestroy`). You will create a mock `ConnectionPool` bean and trace the container bootstrap, bean initialization, operational execution, and container shutdown stages in chronological order.

---

## Scenario
In enterprise applications, managing resource connections (like databases, sockets, or cache clients) requires strict lifecycle management. If resources are not initialized before clients request them, the application crashes. If resources are not closed upon container shutdown, the host system suffers from connection leaks. You will configure a mock Connection Pool to initialize and destroy resources automatically using Spring's bean lifecycle hooks.

---

## References
- [Bean Lifecycle](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/bean-lifecycle.md)
- [Scopes of a Bean](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/scopes-of-a-bean.md)
- [Spring IoC Container](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/spring-ioc-container.md)

---

## Core Tasks

### 1. Implement the ConnectionPool Bean
- Create a class named `ConnectionPool` containing:
  - Fields for `url` (String), `username` (String), and `poolSize` (int).
  - A constructor that prints: `[Constructor] ConnectionPool instantiated.`
  - A method `public void executeQuery(String sql)` that prints: `[Operation] Executing query: [sql] on [url]`.
  
### 2. Add Lifecycle Hooks
- Add an initialization method named `init()` annotated with `@PostConstruct` (from `jakarta.annotation` or `javax.annotation`). This method should print: `[Lifecycle] Initializing ConnectionPool with URL: [url] and Pool Size: [poolSize]`.
- Add a destruction method named `destroy()` annotated with `@PreDestroy`. This method should print: `[Lifecycle] Closing all connections in pool for URL: [url]`.
- *Note:* In newer Java environments, you may need to add the dependency for JSR-250 annotations if they are not included in the standard runtime:
  ```xml
  <dependency>
      <groupId>jakarta.annotation</groupId>
      <artifactId>jakarta.annotation-api</artifactId>
      <version>2.1.1</version>
  </dependency>
  ```

### 3. Configure the Bean
- Create or update your `AppConfig` class to register the `ConnectionPool` bean.
- Wire properties into the bean using standard setter configuration or constructor values (e.g., URL = `"jdbc:postgresql://localhost:5432/traineedb"`, Username = `"postgres"`, Pool Size = `10`).

### 4. Bootstrap and Trace Output
- Create a runner class named `LifecycleApp` containing a `main` method.
- Bootstrap the container using `AnnotationConfigApplicationContext`.
- Print: `--- Container Bootstrapped ---`
- Retrieve the `ConnectionPool` bean and call `executeQuery("SELECT * FROM users")`.
- Print: `--- Closing Container ---`
- Call the context's `close()` method to trigger the shutdown hook.

---

## Definition of Done
- The project compiles without errors.
- Running `LifecycleApp` outputs the exact log lifecycle sequence:
  ```text
  [Constructor] ConnectionPool instantiated.
  [Lifecycle] Initializing ConnectionPool with URL: jdbc:postgresql://localhost:5432/traineedb and Pool Size: 10
  --- Container Bootstrapped ---
  [Operation] Executing query: SELECT * FROM users on jdbc:postgresql://localhost:5432/traineedb
  --- Closing Container ---
  [Lifecycle] Closing all connections in pool for URL: jdbc:postgresql://localhost:5432/traineedb
  ```
