# Guided Coding: Java-Based Spring Configuration

## Learning Objectives
- Write a Spring `@Configuration` class to manage bean lifecycles.
- Use `@Bean` methods to declare and configure dependency injections.
- Implement constructor injection in JavaConfig by passing dependencies as parameters.
- Bootstrap a JavaConfig context using `AnnotationConfigApplicationContext`.

---

## Why This Matters
JavaConfig is the modern standard for structuring configuration in Spring. While annotations like `@Component` are easy to write, they scatter configuration directives across the codebase. JavaConfig brings the configuration logic back into a centralized location while retaining the advantages of compile-time type checking and IDE refactoring safety. In this guide, we will implement the User Messaging application using pure JavaConfig to see how bean definitions and injections are resolved programmatically.

---

## Guided Walkthrough

### Step 1: Add Maven Dependencies
Ensure the standard Spring context is added in your `pom.xml`.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.1.10</version>
    </dependency>
</dependencies>
```

### Step 2: Implement Component Classes
We will use the same application logic classes, but configure them via constructor injection.

#### The Service Class: `EmailService.java`
```java
package com.example.service;

public class EmailService {
    public void sendEmail(String recipient, String message) {
        System.out.println("Email sent: " + message + " to " + recipient);
    }
}
```

#### The Consumer Class: `UserNotifier.java` (Constructor Injection)
```java
package com.example.client;

import com.example.service.EmailService;

public class UserNotifier {
    private final EmailService emailService;

    // Constructor injection (no @Autowired needed for JavaConfig setup)
    public UserNotifier(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendWelcomeAlert(String email) {
        emailService.sendEmail(email, "Welcome! Your JavaConfig-wired account is active.");
    }
}
```

### Step 3: Implement the Configuration Class (`AppConfig.java`)
Create `AppConfig.java` to act as the blueprint for the Spring container. We will use parameter injection to link the notifier to the email service.

```java
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.service.EmailService;
import com.example.client.UserNotifier;

@Configuration // Tells Spring this is a configuration source
public class AppConfig {

    // Define the EmailService Bean
    @Bean
    public EmailService emailService() {
        return new EmailService(); // Instantiation logic
    }

    // Define the UserNotifier Bean
    // The emailService parameter is automatically injected by Spring
    @Bean
    public UserNotifier userNotifier(EmailService emailService) {
        return new UserNotifier(emailService); // Wiring logic
    }
}
```

### Step 4: Bootstrap the Application
Create `Main.java` to load the configuration class using `AnnotationConfigApplicationContext`.

```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.config.AppConfig;
import com.example.client.UserNotifier;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bootstrapping Spring JavaConfig Container...");

        // Load the context using the configuration class
        ApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Retrieve the fully wired UserNotifier bean
        UserNotifier notifier = context.getBean(UserNotifier.class);

        // Run the alert
        notifier.sendWelcomeAlert("student@infosys.com");
    }
}
```

---

## Summary
-   A class annotated with **`@Configuration`** serves as the central hub for bean registration.
-   Methods annotated with **`@Bean`** tell Spring that the returned object should be registered as a bean in the container.
-   Pass dependencies as parameters to `@Bean` methods to allow Spring to resolve and inject them automatically.
-   **`AnnotationConfigApplicationContext`** is used to bootstrap JavaConfig contexts programmatically.

---

## Additional Resources
-   [Spring Documentation: Java-based container configuration](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-java-config.html)
-   [Baeldung: Guide to Spring Configuration Beans](https://www.baeldung.com/spring-bean-annotations)
-   [GeeksforGeeks: Spring Java-based Configuration](https://www.geeksforgeeks.org/spring-dependency-injection-with-java-configuration/)
