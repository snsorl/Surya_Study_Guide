# Component Scanning in Spring

## Learning Objectives
- Explain how `@ComponentScan` discovers beans dynamically at startup.
- Configure `@ComponentScan` base packages using classes and strings.
- Implement scan filters using `FilterType.INCLUDE` and `FilterType.EXCLUDE`.
- Apply lazy loading to component resolution using `@Lazy`.

---

## Why This Matters
By default, Spring scans your project packages to locate and instantiate beans. While this auto-detection is convenient, scanning an entire project containing thousands of classes can slow down your application's startup time significantly. Additionally, you may want to exclude specific classes (like experimental features or database mocks) during production runs. Understanding how to configure component scanning filters and leverage lazy loading with `@Lazy` allows you to optimize application bootstrap performance and control classpath configurations dynamically.

---

## The Concept

### How Component Scanning Works
When you annotate a configuration class with `@ComponentScan`, Spring scans the classpath for classes decorated with `@Component` (or its stereotype derivatives like `@Service`, `@Repository`, and `@Controller`).

By default, if you do not specify a package, Spring scans the package of the `@Configuration` class and all of its sub-packages.

```java
@Configuration
@ComponentScan // Scans the package containing AppConfig and below
public class AppConfig {}
```

### Specifying Base Packages
You can customize the scanned packages using either String identifiers or type-safe classes.

#### 1. String-based Scanning (Not Refactor Safe)
```java
@ComponentScan(basePackages = { "com.example.services", "com.example.repositories" })
```
*Drawback:* If you rename packages in your IDE, String literals are not updated automatically, leading to broken scans.

#### 2. Class-based Scanning (Type-Safe)
Specify classes that reside in the target packages. Spring scans the packages containing those classes.
```java
@ComponentScan(basePackageClasses = { UserService.class, UserRepository.class })
```

---

### Component Scanning Filters
Spring allows you to control which classes are scanned or skipped using inclusion and exclusion filters.

#### Filter Types (`FilterType`)
-   **ANNOTATION**: Filter based on annotations (e.g. exclude all classes annotated with `@Deprecated`).
-   **ASSIGNABLE_TYPE**: Filter based on class inheritance/interface implementations.
-   **ASPECTJ**: Filter using AspectJ expressions.
-   **REGEX**: Filter using regular expressions matching class names.
-   **CUSTOM**: Implement a custom `org.springframework.core.type.filter.TypeFilter`.

```java
@Configuration
@ComponentScan(
    basePackages = "com.example",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION, 
        classes = DummyMock.class // Excludes beans annotated with @DummyMock
    )
)
public class AppConfig {}
```

---

### Lazy Initialization with `@Lazy`
By default, the Spring container eagerly initializes all singleton beans during startup. This ensures that configuration errors are caught immediately. However, if a bean is resource-heavy and rarely used, you might want to delay its creation.

The `@Lazy` annotation shifts instantiation from **startup time** to **first-request time**.

-   **At Class Level:** The bean is only instantiated when requested by the application.
-   **At Injection Point:** If you inject a lazy bean into a non-lazy singleton, Spring injects a lightweight proxy. The actual bean is only instantiated when a method on that proxy is called.

```java
@Component
@Lazy // Instantiated only when first retrieved or called
public class HeavyReportGenerator {
    public HeavyReportGenerator() {
        System.out.println("HeavyReportGenerator initialized!");
    }
}
```

---

## Code Example

Here is a configuration class showing how to set up target scanning, use exclusion filters, and enable lazy loading.

### The Classes
```java
package com.example.annotations;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DevProfileOnly {}
```

```java
package com.example.components;

import com.example.annotations.DevProfileOnly;
import org.springframework.stereotype.Component;

@Component
@DevProfileOnly // This bean will be filtered out by our config
public class MockMailSender {
    public void send() { System.out.println("Mock send."); }
}
```

```java
package com.example.components;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy // Instantiated only when a method is invoked
public class HeavyBillingEngine {
    public HeavyBillingEngine() {
        System.out.println("Billing Engine loaded into memory.");
    }
    public void calculate() {
        System.out.println("Processing billing details.");
    }
}
```

### The Configuration
```java
package com.example;

import com.example.annotations.DevProfileOnly;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = "com.example.components",
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        classes = DevProfileOnly.class // Filters out MockMailSender
    )
)
public class AppConfig {
}
```

---

## Summary
-   **`@ComponentScan`** analyzes the classpath to discover Spring components automatically.
-   Packages can be defined using String names or type-safe classes (`basePackageClasses`).
-   **Inclusion/Exclusion Filters** control which components are loaded based on class types, annotations, or regex patterns.
-   **`@Lazy`** delays the instantiation of singleton beans until their first use, saving memory and startup overhead.

---

## Additional Resources
-   [Spring Documentation: Filters for Component Scanning](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-classpath-scanning.html#beans-scanning-filters)
-   [Baeldung: Spring @ComponentScan Filters](https://www.baeldung.com/spring-component-scanning)
-   [Baeldung: Lazy Initialization in Spring](https://www.baeldung.com/spring-lazy-annotation)
