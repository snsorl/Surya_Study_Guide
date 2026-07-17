# The Spring Bean Lifecycle

## Learning Objectives
- Trace the lifecycle phases of a Spring bean from instantiation to destruction.
- Identify and implement initialization lifecycle hooks using `@PostConstruct` and the `InitializingBean` interface.
- Identify and implement destruction lifecycle hooks using `@PreDestroy` and the `DisposableBean` interface.
- Understand the execution order of lifecycle hooks.

---

## Why This Matters
Beans often require database connections, file handles, socket bindings, or thread execution pools. Creating these resources when the object is instantiated and releasing them when the object is destroyed is critical. Failing to handle resource lifecycles correctly leads to memory leaks, connection exhaustion, and locking issues. By mastering the Spring Bean Lifecycle hooks, you can ensure that your enterprise applications boot cleanly, operate efficiently, and shut down gracefully.

---

## The Concept

### The Lifecycle Pipeline
The Spring Bean Lifecycle is a pipeline of phases that every bean goes through. Below is the sequential order of events:

```
[ Instantiation ]
       │
[ Populate Properties (DI) ]
       │
[ BeanNameAware / BeanFactoryAware / ApplicationContextAware Hooks ]
       │
[ Custom Initialization Hooks ] (e.g. @PostConstruct, afterPropertiesSet)
       │
[ Bean is Ready / Active ]
       │ (Application Runs)
[ Custom Destruction Hooks ] (e.g. @PreDestroy, destroy)
       │
[ Bean Destroyed ]
```

---

### Step-by-Step Breakdown

1.  **Instantiation**: The container instantiates the bean (calling its constructor, either default or parameter-injected).
2.  **Populate Properties (Dependency Injection)**: The container injects the bean's dependencies via setters or fields.
3.  **Aware Interfaces**: If the bean implements certain `Aware` interfaces, Spring injects metadata (e.g. `BeanNameAware` injects the bean name; `ApplicationContextAware` injects the container reference).
4.  **Initialization Hooks**:
    -   **`@PostConstruct`**: Annotation placed on a method that should run immediately after dependency injection is complete.
    -   **`InitializingBean`**: Interface requiring you to implement `afterPropertiesSet()`.
    -   **Custom `init-method`**: Configured in XML or `@Bean(initMethod = "...")`.
5.  **Bean Ready**: The bean is active and available for use by the application.
6.  **Destruction Hooks** (fired during container shutdown):
    -   **`@PreDestroy`**: Annotation placed on a cleanup method.
    -   **`DisposableBean`**: Interface requiring you to implement `destroy()`.
    -   **Custom `destroy-method`**: Configured in XML or `@Bean(destroyMethod = "...")`.

---

### Execution Order of Hooks
If you implement multiple initialization or destruction hooks on the same bean, Spring executes them in a specific order:

#### Initialization Order:
1.  Methods annotated with **`@PostConstruct`** (Recommended - standard JSR-250 annotation).
2.  The **`afterPropertiesSet()`** method defined by the `InitializingBean` interface.
3.  Custom **`initMethod`** declared in `@Bean` or XML configuration.

#### Destruction Order:
1.  Methods annotated with **`@PreDestroy`** (Recommended - standard JSR-250 annotation).
2.  The **`destroy()`** method defined by the `DisposableBean` interface.
3.  Custom **`destroyMethod`** declared in `@Bean` or XML configuration.

---

## Code Example

Here is a class demonstrating the implementation of multiple lifecycle hooks and how to trace their execution order.

### The Managed Bean
```java
package com.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class DatabaseConnectionPool implements InitializingBean, DisposableBean {

    private String databaseUrl;

    public DatabaseConnectionPool() {
        System.out.println("1. Constructor: Instantiating Connection Pool.");
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        System.out.println("2. Dependency Injection: Setting Database URL to: " + databaseUrl);
    }

    // Annotation hook (JSR-250) - Preferred
    @PostConstruct
    public void postConstruct() {
        System.out.println("3. @PostConstruct: Simulating resource allocation (opening connection).");
    }

    // Interface hook
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("4. InitializingBean.afterPropertiesSet: Validating configuration.");
        if (databaseUrl == null) {
            throw new IllegalStateException("Database URL must be set!");
        }
    }

    // Custom init-method configuration (AppConfig defined)
    public void customInit() {
        System.out.println("5. Custom Init-Method: Connection Pool is fully warmed up.");
    }

    // Annotation hook (JSR-250) - Preferred
    @PreDestroy
    public void preDestroy() {
        System.out.println("6. @PreDestroy: Starting shutdown cleanup.");
    }

    // Interface hook
    @Override
    public void destroy() throws Exception {
        System.out.println("7. DisposableBean.destroy: Closing database connections.");
    }

    // Custom destroy-method configuration
    public void customDestroy() {
        System.out.println("8. Custom Destroy-Method: Connection Pool released.");
    }
}
```

### Configuration Class
```java
package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
    public DatabaseConnectionPool connectionPool() {
        DatabaseConnectionPool pool = new DatabaseConnectionPool();
        pool.setDatabaseUrl("jdbc:postgresql://localhost:5432/mydb");
        return pool;
    }
}
```

---

## Summary
-   The Spring bean lifecycle steps are: Instantiation $\rightarrow$ Property Injection $\rightarrow$ Initialization Hooks $\rightarrow$ Active State $\rightarrow$ Destruction Hooks.
-   **`@PostConstruct`** and **`@PreDestroy`** are modern JSR-250 annotations and the recommended way to register lifecycle hooks.
-   The **`InitializingBean`** and **`DisposableBean`** interfaces offer programmatic callback hooks, but couple your code to Spring interfaces.
-   Custom **`initMethod`** and **`destroyMethod`** are defined during bean registration and are useful when you do not own the source code of the class.

---

## Additional Resources
-   [Spring Documentation: Lifecycle Callbacks](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-nature.html)
-   [Baeldung: Spring Bean Lifecycle](https://www.baeldung.com/spring-bean-lifecycle)
-   [StackOverflow: Difference between @PostConstruct and InitializingBean](https://stackoverflow.com/questions/1395893/difference-between-postconstruct-and-initializingbean)
