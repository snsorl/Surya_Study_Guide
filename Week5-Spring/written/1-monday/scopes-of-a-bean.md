# Spring Bean Scopes

## Learning Objectives
- Identify and describe the six standard bean scopes provided by Spring.
- Explain the behavioral difference between **singleton** and **prototype** scopes.
- Understand web-aware scopes: **request**, **session**, **application**, and **websocket**.
- Address the "scoped proxy" problem (injecting a prototype-scoped bean into a singleton-scoped bean).

---

## Why This Matters
By default, every bean you define in Spring is a singleton (only one instance is created per container). This works well for stateless services and repositories, but causes severe bugs if you store user-specific state in them. In contrast, prototype-scoped beans create new instances every time they are requested, which is useful for stateful components. Knowing when to apply different bean scopes—and how to handle dependencies between different scopes—is essential for writing safe, concurrent multi-user web applications.

---

## The Concept

### The Six Standard Scopes
Spring support six bean scopes out of the box. Four of these are only available if you use a web-aware `ApplicationContext`.

| Scope | Description | Environment |
| :--- | :--- | :--- |
| **singleton** | (Default) Scopes a single bean definition to a single object instance per Spring IoC container. | Any |
| **prototype** | Scopes a single bean definition to any number of object instances. A new instance is created every time the bean is requested. | Any |
| **request** | Scopes a single bean definition to the lifecycle of a single HTTP request. | Web-aware |
| **session** | Scopes a single bean definition to the lifecycle of an HTTP Session. | Web-aware |
| **application** | Scopes a single bean definition to the lifecycle of a `ServletContext`. | Web-aware |
| **websocket** | Scopes a single bean definition to the lifecycle of a `WebSocket`. | Web-aware |

---

### Singleton vs. Prototype Behavioral Differences

#### 1. Singleton (Default)
When a bean is configured as a singleton, the Spring container creates **exactly one instance** of that bean. This instance is stored in a cache, and all requests for that bean will return that exact shared object instance.
-   *Behavior:* Eagerly instantiated on startup (by default).
-   *Use Case:* Stateless beans, such as Controllers, Services, and Repositories.

#### 2. Prototype
When a bean is configured as a prototype, the Spring container creates a **new instance** of the bean every single time it is requested (either via dependency injection or a direct `getBean()` call).
-   *Behavior:* Lazily instantiated (created on demand).
-   *Use Case:* Stateful beans carrying transaction-specific parameters or non-thread-safe helpers.
-   *Crucial Detail:* **Spring does not manage the complete lifecycle of a prototype bean.** The container instantiates and injects it, but does *not* call its `@PreDestroy` methods. The client must clean up prototype beans to prevent memory leaks.

---

### The Scoped Proxy Problem
A common architectural challenge arises when you attempt to inject a short-lived bean (like a `prototype` or `request` scope) into a long-lived bean (like a `singleton`).

#### The Problem:
Because a singleton bean is initialized only once during container startup, its dependencies are also injected **only once**. If you inject a prototype bean into a singleton bean, the singleton bean gets a single prototype instance at startup, and keeps using that same instance forever. The prototype bean effectively behaves like a singleton, defeating the purpose of the prototype scope.

#### The Solution: Scoped Proxies
To solve this, Spring uses **Scoped Proxies**. Instead of injecting the real prototype bean directly, Spring injects an AOP **proxy** object that implements the same interface as the prototype. Every time the singleton calls a method on the proxy, the proxy intercepts the call and fetches a brand-new instance of the prototype bean from the container.

---

## Code Example

Let's look at how to declare scopes and resolve the scoped proxy problem using annotations.

### Declaring a Prototype Bean with Scoped Proxy
```java
package com.example;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
// Set scope to prototype and request a proxy so it can be injected into singletons
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TokenGenerator {
    private final String id;

    public TokenGenerator() {
        this.id = UUID.randomUUID().toString();
    }

    public String generate() {
        return "TOKEN-" + id;
    }
}
```

### Injecting into a Singleton Bean
```java
package com.example;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    // This is a singleton.
    // Because TokenGenerator uses ScopedProxyMode, each call to getNewToken()
    // will invoke a brand-new TokenGenerator instance behind the proxy!
    private final TokenGenerator tokenGenerator;

    public AuthenticationService(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    public String getNewToken() {
        return tokenGenerator.generate();
    }
}
```

---

## Summary
-   Spring beans have **scopes** that define their lifecycle boundaries and instantiation strategies.
-   **Singleton** (default) is a single instance per container. **Prototype** produces a new instance on every request.
-   Web-aware scopes link bean lifecycles to HTTP requests, sessions, and applications.
-   Injecting a prototype bean into a singleton directly breaks prototype behavior because wiring only occurs once. Use **`ScopedProxyMode`** to dynamically resolve a new prototype instance on every method execution.

---

## Additional Resources
-   [Spring Documentation: Bean Scopes](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-scopes.html)
-   [Baeldung: Spring Bean Scopes](https://www.baeldung.com/spring-bean-scopes)
-   [StackOverflow: Spring Injecting Prototype Bean into Singleton](https://stackoverflow.com/questions/4621433/how-to-inject-a-prototype-bean-into-a-singleton-bean-in-spring)
