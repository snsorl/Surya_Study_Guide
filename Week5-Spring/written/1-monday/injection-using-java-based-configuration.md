# Dependency Injection Using Java-Based Configuration

## Learning Objectives
- Describe the purpose and usage of `@Configuration` and `@Bean` annotations.
- Wire dependencies using Java-based configuration (JavaConfig).
- Explain how `@Import` is used to organize modular configuration files.
- Compare Java-based configuration to traditional XML configuration.

---

## Why This Matters
For years, Spring developers struggled with XML file bloat. Typos in bean class names were only discovered at runtime when the application crashed. Spring JavaConfig solves this by replacing XML configurations with pure Java classes. Java-based configuration provides compile-time safety, auto-completion in modern IDEs, and refactoring support, which are crucial for maintaining speed and quality in fast-paced software projects.

---

## The Concept

### The Rise of JavaConfig
Introduced as an alternative to XML config, **JavaConfig** allows developers to configure Spring using standard Java classes annotated with `@Configuration`. It provides type-safety, which means the compiler checks your configuration code, catching typos and missing classes before the application starts up.

### Core Annotations

#### 1. `@Configuration`
This annotation is placed at the class level. It indicates that the class contains methods that define and configure beans. Spring will process the class to generate bean definitions at runtime.

#### 2. `@Bean`
This annotation is placed at the method level inside a `@Configuration` class. The method returns an object that the Spring IoC container will manage.
-   **Method Name:** By default, the name of the method becomes the identifier (ID) of the bean.
-   **Method Body:** Contains the actual instantiation logic.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserRepository userRepository() {
        return new SQLUserRepository(); // Instantiation
    }
}
```

### Dependency Wiring in JavaConfig
To wire dependencies, we can call the `@Bean` method directly, or pass the dependency as a parameter to the method. Spring's container intercept calls to `@Bean` methods to ensure singleton scope rules are followed (it won't instantiate the object multiple times; it will return the existing managed bean).

#### Option A: Direct Method Calling
```java
@Bean
public UserService userService() {
    // Calling userRepository() returns the managed bean instance
    return new UserService(userRepository()); 
}
```

#### Option B: Parameter Injection (Recommended)
You can declare the dependency as a parameter on the `@Bean` method. Spring will look up the bean in the container and pass it as an argument automatically.
```java
@Bean
public UserService userService(UserRepository userRepository) {
    return new UserService(userRepository); 
}
```

### Organizing Configurations with `@Import`
For large applications, putting all bean definitions in a single file is hard to manage. `@Import` allows you to split configurations into modular classes and import them into a main configuration class.

```java
@Configuration
@Import({ DatabaseConfig.class, SecurityConfig.class })
public class MainAppConfig {
    // Aggregates config modules
}
```

---

## Code Example

Let's look at a complete example of wiring a service and repository using JavaConfig.

### The Component Classes
```java
package com.example;

public class EmailService {
    public void sendEmail(String recipient, String message) {
        System.out.println("Sending email to " + recipient + ": " + message);
    }
}

public class NotificationManager {
    private final EmailService emailService;

    // Dependency constructor
    public NotificationManager(EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyUser(String userEmail) {
        emailService.sendEmail(userEmail, "Welcome to Spring JavaConfig!");
    }
}
```

### The Configuration Class (`AppConfig.java`)
```java
package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Define the EmailService Bean
    @Bean
    public EmailService emailService() {
        return new EmailService();
    }

    // Define the NotificationManager Bean and inject the EmailService bean
    @Bean
    public NotificationManager notificationManager(EmailService emailService) {
        return new NotificationManager(emailService);
    }
}
```

### Bootstrapping the Application
```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Bootstrap using JavaConfig class
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        NotificationManager manager = context.getBean(NotificationManager.class);
        manager.notifyUser("trainee@infosys.com");
    }
}
```

---

## Summary
-   **JavaConfig** uses `@Configuration` classes to define bean metadata using standard Java.
-   The `@Bean` annotation marks a method whose returned object is registered in the Spring IoC container.
-   Dependencies are wired in JavaConfig by passing parameters to the bean methods.
-   `@Import` allows configuration files to be broken down into modular, logical configuration chunks.
-   JavaConfig provides **compile-time check safety** and **IDE refactoring support**, making it the modern standard over XML.

---

## Additional Resources
-   [Spring Framework Reference: Java-based Container Configuration](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-java-config.html)
-   [Baeldung: Spring Java-based Configuration](https://www.baeldung.com/spring-java-config-vs-xml-config)
-   [Spring Blog: JavaConfig Commons Patterns](https://spring.io/blog/2009/06/09/javaconfig-features-and-patterns/)
