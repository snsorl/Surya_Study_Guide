# Exercise: Spring Core Wiring & Scope Verification

## Objective
Create a standard Java Maven project using the Spring Core container. You will define and wire a dependency graph consisting of a `NotificationService` that depends on an `EmailSender` bean. You must implement the wiring using both traditional XML configuration and modern Java-based configuration, and then verify the default Singleton bean scope.

---

## Scenario
You are developing the messaging system for a corporate application. Rather than manually instantiating services and senders, you will migrate the wiring logic to the Spring IoC container. This allows the application components to remain loosely coupled, making it significantly easier to test and swap email providers or dispatch rules.

---

## References
- [Introduction to Spring](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/intro-to-spring.md)
- [Overview of Dependency Injection](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/overview-of-dependency-injection.md)
- [Types of Dependency Injection](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/types-of-dependency-injection.md)
- [Injection Using XML-Based Configuration](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/injection-using-xml-based-configuration.md)
- [Injection Using Java-Based Configuration](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/1-monday/injection-using-java-based-configuration.md)

---

## Core Tasks

### 1. Project Dependencies
- Initialize a Maven project.
- Add the `spring-context` dependency (version 6.1.10 recommended) to your `pom.xml`.
- Ensure your Java compiler version is set to 17 or higher.

### 2. Implement the Java Classes
- **`EmailSender`**: Create a class representing the email sender. Implement a method `public void send(String recipient, String message)` that prints: `[Email] Sending message to: [recipient] - Contents: [message]`.
- **`NotificationService`**: Create a class that depends on `EmailSender`. Implement constructor-based injection for the dependency:
  ```java
  public class NotificationService {
      private final EmailSender emailSender;

      public NotificationService(EmailSender emailSender) {
          this.emailSender = emailSender;
      }

      public void notifyUser(String recipient, String alert) {
          emailSender.send(recipient, alert);
      }
  }
  ```

### 3. XML-Based Configuration
- Create a file named `applicationContext.xml` in your `src/main/resources/` directory.
- Define a `<bean>` for `EmailSender` (id: `emailSender`).
- Define a `<bean>` for `NotificationService` (id: `notificationService`) and wire it using `<constructor-arg ref="emailSender" />`.

### 4. Java-Based Configuration
- Create a configuration class named `AppConfig` annotated with `@Configuration`.
- Define `@Bean` methods to instantiate and wire the components:
  - `public EmailSender emailSender()`
  - `public NotificationService notificationService(EmailSender emailSender)`

### 5. Bootstrapping and Singleton Scope Verification
- Create a class named `App` with a `public static void main(String[] args)` method.
- **Part A (XML Context)**:
  - Bootstrap the container using `ClassPathXmlApplicationContext`.
  - Retrieve the `notificationService` bean and call `notifyUser("trainee@jfsd.com", "XML Config Successful!")`.
- **Part B (Java Context)**:
  - Bootstrap the container using `AnnotationConfigApplicationContext`.
  - Retrieve the `NotificationService` bean and call `notifyUser("trainee@jfsd.com", "Java Config Successful!")`.
  - Retrieve the `NotificationService` bean a second time.
  - Print whether the two retrieved `NotificationService` instances are identical using the `==` reference comparison operator.

---

## Definition of Done
- The project compiles without errors.
- Running `App` bootstraps both contexts and displays the following lines in the console:
  ```text
  [Email] Sending message to: trainee@jfsd.com - Contents: XML Config Successful!
  [Email] Sending message to: trainee@jfsd.com - Contents: Java Config Successful!
  Are NotificationService instances identical? true
  ```
