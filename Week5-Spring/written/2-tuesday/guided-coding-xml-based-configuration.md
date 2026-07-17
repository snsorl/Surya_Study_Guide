# Guided Coding: XML-Based Spring Configuration

## Learning Objectives
- Set up a standard Maven-based Spring project from scratch.
- Create a complete XML configuration file (`applicationContext.xml`) mapping bean dependencies.
- Implement setter and constructor injection using `<property>` and `<constructor-arg>`.
- Bootstrap the application container using `ClassPathXmlApplicationContext`.

---

## Why This Matters
Theory is useful, but the best way to understand how Spring operates is by building a project yourself. Writing XML-based configurations manually helps you visualize how the Spring container constructs, manages, and connects beans. In this guide, we will build a simple console-based User Messaging application, configuring it entirely through an XML configuration file to see exactly how bean references are resolved.

---

## The Concept

### Application Topology
We will build three simple classes:
1.  `EmailService`: A service class that prints email messages to the console.
2.  `UserNotifier`: A class that coordinates notifications and depends on `EmailService`.
3.  `Main`: The entry point that bootstraps the XML container.

```
[ ClassPathXmlApplicationContext ]
                │
                ▼ (instantiates & wires)
        [ UserNotifier ]
                │
                ▼ (injects)
         [ EmailService ]
```

---

## Guided Walkthrough

### Step 1: Add Maven Dependencies
To use Spring XML configuration, ensure you have the core Spring Context dependency in your `pom.xml` file.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>6.1.10</version>
    </dependency>
</dependencies>
```

### Step 2: Implement the Service Class
Create `EmailService.java`. This class has a simple method to print text and does not import any Spring classes (it is a pure POJO).

```java
package com.example.service;

public class EmailService {
    public void sendEmail(String recipient, String message) {
        System.out.println("Email sent to: " + recipient);
        System.out.println("Message: " + message);
    }
}
```

### Step 3: Implement the Consumer Class (Setter Injection)
Create `UserNotifier.java`. It depends on `EmailService` and provides a setter method for Spring to inject the dependency.

```java
package com.example.client;

import com.example.service.EmailService;

public class UserNotifier {
    private EmailService emailService;

    // Default constructor (required for Setter Injection)
    public UserNotifier() {}

    // Setter method for dependency injection
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendWelcomeAlert(String email) {
        emailService.sendEmail(email, "Welcome! Your XML-wired account is active.");
    }
}
```

### Step 4: Create `applicationContext.xml`
Create the XML file at `src/main/resources/applicationContext.xml`. This file tells Spring how to instantiate these classes and wire them together.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 1. Define the EmailService Bean -->
    <bean id="emailServiceBean" class="com.example.service.EmailService" />

    <!-- 2. Define the UserNotifier Bean -->
    <bean id="userNotifierBean" class="com.example.client.UserNotifier">
        <!-- 
           Inject the emailServiceBean into the UserNotifier's 'emailService' field.
           Spring calls the 'setEmailService(EmailService emailService)' method.
        -->
        <property name="emailService" ref="emailServiceBean" />
    </bean>

</beans>
```

### Step 5: Bootstrap the Application
Create `Main.java` to load the XML configuration file from the classpath, retrieve the wired bean, and run the welcome alert method.

```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.example.client.UserNotifier;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bootstrapping Spring XML Container...");

        // Load the XML configuration from resources
        ApplicationContext context = 
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the fully-configured UserNotifier bean
        UserNotifier notifier = context.getBean("userNotifierBean", UserNotifier.class);

        // Run business logic
        notifier.sendWelcomeAlert("student@infosys.com");
    }
}
```

---

## Summary
-   To use XML-based DI, you must declare beans using the `<bean>` tag, specifying an `id` and a fully qualified `class` path.
-   **`<property name="..." ref="..." />`** tells Spring to use Setter Injection, calling the appropriate setter method on the host bean.
-   The **`ClassPathXmlApplicationContext`** container loads the XML bean configurations from the project's resource folders.
-   Wired classes remain decoupled from Spring libraries, preserving their design as pure Java POJOs.

---

## Additional Resources
-   [Spring Framework Guide: XML-based configuration metadata](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-metadata.html)
-   [Baeldung: Guide to ClassPathXmlApplicationContext](https://www.baeldung.com/spring-classpathxmlapplicationcontext-vs-filesystemxmlapplicationcontext)
-   [W3Schools: XML Schema Definition (XSD) Tutorial](https://www.w3schools.com/xml/schema_intro.asp)
