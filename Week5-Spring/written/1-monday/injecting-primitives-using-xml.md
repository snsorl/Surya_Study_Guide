# Injecting Primitives and Values in Spring

## Learning Objectives
- Inject primitive types and strings using XML `<property>` and `<constructor-arg>` value attributes.
- Use the `@Value` annotation to inject literal values directly in Java code.
- Explain the syntax and execution differences between Property Placeholders (`${...}`) and Spring Expression Language (SpEL) (`#{...}`).
- Read and inject configurations from external properties files.

---

## Why This Matters
Applications should never hardcode configuration values (like database URLs, timeout thresholds, API keys, or email hosts) into their Java source code. This makes the code inflexible and poses severe security risks if passwords get committed to source control. Spring allows you to inject primitives and configurations dynamically, either via XML properties or `@Value` annotations. By learning the difference between property resolution and Spring Expression Language (SpEL), you can safely externalize configurations and compute dynamic values at runtime.

---

## The Concept

### 1. XML Primitive Injection
In XML configurations, instead of referencing another bean using the `ref` attribute, you inject literal values (strings, integers, booleans) using the `value` attribute inside `<property>` or `<constructor-arg>` tags. Spring converts the string representation in XML to the target primitive type in Java automatically.

```xml
<bean id="dbConfig" class="com.example.DatabaseConfig">
    <!-- Setter Injection of primitives -->
    <property name="port" value="5432" />
    <property name="driverClassName" value="org.postgresql.Driver" />
</bean>
```

---

### 2. Annotation Injection: The `@Value` Annotation
In annotation-driven applications, `@Value` is used to load configurations. There are three common ways to use it:

#### A. Literal Strings (Rarely used in production)
```java
@Value("AdminUser")
private String adminUsername;
```

#### B. Property Placeholders (`${...}`)
Loads values from an external source, such as an `application.properties` file or system environment variables.
```java
@Value("${db.port:5432}") // Syntax: ${key:fallback_value}
private int dbPort;
```
*Note:* To resolve property placeholders, Spring requires a `PropertySourcesPlaceholderConfigurer` bean registered in the container (Spring Boot registers this automatically).

#### C. Spring Expression Language (SpEL) (`#{...}`)
SpEL is a powerful expression language that evaluates expressions dynamically at runtime. It can call methods, instantiate objects, check system variables, or perform mathematical calculations.
```java
@Value("#{systemProperties['user.home']}")
private String userHome;

@Value("#{2 * 10}") // Injects 20
private int doubleTen;
```

---

### Comparison: Property Placeholders vs. SpEL
It is easy to confuse the two syntaxes:

-   **`${...}` (Property Placeholder)**:
    -   *Action:* Simple string lookup.
    -   *Source:* Retrieves values from property files, OS environment variables, or JVM system properties.
    -   *Cannot:* Run calculations, call methods, or manipulate types.
-   **`#{...}` (SpEL Expression)**:
    -   *Action:* Evaluates expression logic.
    -   *Source:* Executes parser logic against bean graphs, math operations, or complex evaluations.
    -   *Can:* Query property placeholders inside the expression (e.g. `#{'${db.port}' == '5432'}`).

---

## Code Example

Here is a class demonstrating property files, placeholder lookups, and SpEL expression processing.

### The Properties File (`app.properties`)
```properties
app.name=InfosysLearningPortal
app.admin.email=admin@infosys.com
app.max.connections=50
```

### The Component Class
```java
package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:app.properties") // Instructs Spring to load this properties file
public class SystemConfigurator {

    // 1. Injecting a simple property value
    @Value("${app.name}")
    private String appName;

    // 2. Injecting with a default/fallback value if the key is missing
    @Value("${app.mode:production}")
    private String appMode;

    // 3. Spring automatically converts String to primitive int
    @Value("${app.max.connections}")
    private int maxConnections;

    // 4. SpEL: Injects boolean evaluation of logic
    @Value("#{systemProperties['os.name'].toLowerCase().contains('windows')}")
    private boolean isWindowsOS;

    // 5. SpEL combining property lookup and logical comparison
    @Value("#{'${app.mode:production}' == 'development'}")
    private boolean isDevMode;

    public void displayConfig() {
        System.out.println("Application Name: " + appName);
        System.out.println("Execution Mode: " + appMode);
        System.out.println("Max Connection Limit: " + maxConnections);
        System.out.println("Running on Windows OS: " + isWindowsOS);
        System.out.println("Is Developer Environment: " + isDevMode);
    }
}
```

---

## Summary
-   In XML, primitives are wired using `<property value="...">` and `<constructor-arg value="...">` tags.
-   **`@Value`** is the annotation equivalent for loading primitives, configurations, and SpEL outputs in Java code.
-   **`${...}` Property Placeholders** perform simple key-value lookups from external properties files.
-   **`#{...}` SpEL expressions** parse and execute complex, dynamic Java expressions at runtime.

---

## Additional Resources
-   [Spring Documentation: Spring Expression Language (SpEL)](https://docs.spring.io/spring-framework/reference/core/expressions.html)
-   [Baeldung: A Guide to Spring @Value](https://www.baeldung.com/spring-value-annotation)
-   [Baeldung: Spring Expression Language Guide](https://www.baeldung.com/spring-expression-language)
