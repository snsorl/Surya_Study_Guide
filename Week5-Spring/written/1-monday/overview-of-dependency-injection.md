# Overview of Dependency Injection (DI)

## Learning Objectives
- Define Dependency Injection (DI) and its relationship to Inversion of Control (IoC).
- Explain how DI promotes loose coupling, maintainability, and testability.
- Differentiate between Dependency Injection and the Service Locator pattern.
- Recognize how DI transfers the responsibility of object instantiation to a third party (the container).

---

## Why This Matters
In a large application, objects depend on other objects to perform their tasks. If an object is responsible for creating and configuring its own dependencies, code becomes tightly coupled and rigid. Changes to one class ripple through the entire codebase, and writing isolated unit tests becomes extremely difficult. Dependency Injection solves this by delegating dependency management to the framework. It allows you to write modular, focus-driven classes that can easily be reconfigured and mock-tested in isolation, which is an industry best practice for enterprise systems.

---

## The Concept

### What is Dependency Injection?
**Dependency Injection (DI)** is a design pattern used to achieve Inversion of Control (IoC) between classes and their dependencies. 
-   A **Dependency** is an object that another object requires to function (for example, a `UserService` depends on a `UserRepository`).
-   **Injection** is the process of passing the required dependency into the dependent object, rather than having the object construct it itself.

By using DI, the dependent object is completely passive. It merely exposes a way to receive the dependency (like a constructor or setter), and an external entity (in our case, the Spring IoC container) is responsible for instantiating the dependency and "injecting" it.

### Benefits of Dependency Injection
Applying DI to your system architecture yields several significant benefits:

1.  **Loose Coupling**: Classes do not need to know the specific concrete implementation of their dependencies; they only need to know the interface. This decouples the class using the service from the implementation of that service.
2.  **Testability**: Since dependencies can be injected, they can also be mocked. During unit testing, you can inject mock implementations of database services or network clients to test the class logic in complete isolation without hitting real databases or APIs.
3.  **Maintainability**: Changes to the creation logic of a dependency (such as adding new configuration arguments) only need to be updated in one place (the configuration/container layer) rather than in every class that instantiates that dependency.
4.  **Reusability**: Components are modular and focus on a single responsibility, making them easier to reuse across different parts of the application.

### Dependency Injection vs. Service Locator Pattern
Before DI became popular, the **Service Locator Pattern** was a common way to decouple classes from concrete implementations.

-   **Service Locator**: A central registry that knows how to resolve dependencies. A class asks the locator explicitly for the dependency it needs.
    ```java
    // Service Locator style: The class still actively pulls its dependency
    public class UserService {
        private UserRepository userRepo;

        public UserService() {
            this.userRepo = (UserRepository) ServiceLocator.getService("userRepository");
        }
    }
    ```
    *Drawbacks:* The class is still dependent on the Service Locator class itself. Unit testing requires mocking the global service locator state, which is notoriously error-prone. It also hides dependencies from the class signature.

-   **Dependency Injection**: The class does not pull anything. The dependencies are pushed into it at creation time.
    ```java
    // DI style: The class is completely passive
    public class UserService {
        private UserRepository userRepo;

        public UserService(UserRepository userRepo) {
            this.userRepo = userRepo;
        }
    }
    ```
    *Advantages:* The dependency requirements are completely transparent from the constructor signature. No dependency on a framework-specific locator class exists inside the business code. Mocking dependencies for tests is as simple as passing a mock object to the constructor.

---

## Code Example

Let's look at how DI transforms tight coupling to loose coupling.

### Tight Coupling (Without DI)
```java
// UserService is tightly coupled to SQLUserRepository.
// Changing to an InMemory or Mongo Repository requires changing this code.
// Unit testing UserService requires a live database connection.
public class UserService {
    private SQLUserRepository userRepository;

    public UserService() {
        this.userRepository = new SQLUserRepository(); // UserService is responsible for creation
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
```

### Loose Coupling (With DI)
```java
// UserRepository interface allows for multiple implementations
public interface UserRepository {
    void save(User user);
}

// UserService relies on the interface and does not instantiate anything.
public class UserService {
    private final UserRepository userRepository;

    // Dependency is injected via constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
```

---

## Summary
-   **Dependency Injection (DI)** is a pattern where an external container manages and supplies dependencies to objects.
-   DI shifts the responsibility of object instantiation away from business logic classes.
-   DI enhances **testability** by enabling easy mock injection, improves **loose coupling** via interface-based programming, and aids **maintainability**.
-   Compared to the **Service Locator** pattern, DI is less invasive and keeps class dependency contracts explicit.

---

## Additional Resources
-   [Martin Fowler: Inversion of Control Containers and the Dependency Injection pattern](https://martinfowler.com/articles/injection.html)
-   [Baeldung: Introduction to Spring Dependency Injection](https://www.baeldung.com/spring-dependency-injection)
-   [Oracle Java Magazine: Understanding Dependency Injection](https://blogs.oracle.com/javamagazine/post/understanding-dependency-injection)
