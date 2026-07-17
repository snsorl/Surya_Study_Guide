# Annotation-Based Configuration

## Learning Objectives
- Describe how annotations replace traditional XML metadata configuration.
- Explain the role of `@ComponentScan` in discovering components automatically.
- Wire dependencies using the `@Autowired` annotation.
- Resolve bean autowiring conflicts using `@Primary` and `@Qualifier` annotations.

---

## Why This Matters
As Spring applications grew, managing massive XML files containing thousands of beans became a maintenance nightmare. Annotation-based configuration shifted the paradigm by co-locating bean definitions with the class files themselves. This makes code easier to read and faster to write. However, when multiple classes implement the same interface, Spring's auto-wiring engine needs clear instructions on which implementation to select. Mastering annotations like `@Autowired`, `@Qualifier`, and `@Primary` is critical to avoiding dependency mismatch errors at runtime.

---

## The Concept

### Moving from XML to Annotations
Instead of declaring every bean and dependency manually, annotation-based configuration allows Spring to scan classes for metadata. The configuration is declared directly in the Java source files using annotations.

To enable scanning, a `@Configuration` class is decorated with `@ComponentScan`, instructing Spring to search packages for classes annotated with `@Component` (and its sub-types).

### Core Spring Annotations

#### 1. `@Autowired`
Tells Spring to perform automatic dependency injection. It can be placed on constructors (preferred), setters, or fields. Spring looks up a bean matching the required type in the container and injects it.

#### 2. Resolving Ambiguity
If you have an interface with multiple implementations and try to `@Autowire` it:
```java
public interface MessageSender { void send(String msg); }

@Component public class EmailSender implements MessageSender { ... }
@Component public class SmsSender implements MessageSender { ... }
```
If you request `MessageSender` injection, Spring will throw a `NoUniqueBeanDefinitionException` because it does not know whether you want `EmailSender` or `SmsSender`.

Spring provides two annotations to resolve this conflict:

#### A. `@Primary`
Marks one implementation as the default choice. If a conflict occurs, Spring will select the bean marked with `@Primary`.
```java
@Component
@Primary
public class EmailSender implements MessageSender { ... }
```

#### B. `@Qualifier`
Provides a specific identifier to match. This allows you to select a specific bean by name at the injection point, overriding any `@Primary` defaults.
```java
@Component
public class NotificationService {
    private final MessageSender messageSender;

    // Direct Spring to inject the "smsSender" bean specifically
    public NotificationService(@Qualifier("smsSender") MessageSender messageSender) {
        this.messageSender = messageSender;
    }
}
```

---

## Code Example

Here is a complete, working example illustrating how to configure automatic scanning and resolve interface conflicts.

### The Beans and Interface
```java
package com.example.service;

public interface PaymentProcessor {
    void process(double amount);
}
```

```java
package com.example.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary // Marks CreditCardProcessor as the default choice
public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}
```

```java
package com.example.service;

import org.springframework.stereotype.Component;

@Component
public class PaypalProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}
```

### The Consumer Class
```java
package com.example.client;

import com.example.service.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CheckoutService {

    private final PaymentProcessor defaultProcessor;
    private final PaymentProcessor paypalProcessor;

    // Inject the primary bean (CreditCardProcessor) into defaultProcessor,
    // and explicitly target the PayPal processor using @Qualifier
    @Autowired
    public CheckoutService(
            PaymentProcessor defaultProcessor,
            @Qualifier("paypalProcessor") PaymentProcessor paypalProcessor) {
        this.defaultProcessor = defaultProcessor;
        this.paypalProcessor = paypalProcessor;
    }

    public void checkout(double total) {
        System.out.println("Using default processor:");
        defaultProcessor.process(total);

        System.out.println("Using PayPal processor:");
        paypalProcessor.process(total);
    }
}
```

### Configuration and Bootstrap
```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.example.client.CheckoutService;

@Configuration
@ComponentScan(basePackages = "com.example") // Scans for components
public class AppConfig {
}
```

---

## Summary
-   **Annotation-based configuration** replaces XML declarations with inline annotations placed directly in class files.
-   **`@ComponentScan`** tells the Spring container where to look for components.
-   **`@Autowired`** executes automatic dependency injection by type.
-   **`@Primary`** designates a default bean when multiple beans of the same type exist.
-   **`@Qualifier`** specifies the exact name of the bean to inject, overriding `@Primary`.

---

## Additional Resources
-   [Spring Documentation: Annotation-based Container Configuration](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html)
-   [Baeldung: Spring Primary vs Qualifier](https://www.baeldung.com/spring-primary-vs-qualifier)
-   [Refactoring Guru: Dependency Injection Patterns](https://refactoring.guru/)
