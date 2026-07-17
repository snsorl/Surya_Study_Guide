# Dependency Injection: Wiring References & Circular Dependencies

## Learning Objectives
- Wire references to other beans using `<ref>` in XML and `@Autowired` in annotations.
- Trace the dependency graph constructed by the Spring container at startup.
- Define circular dependencies and explain why they occur.
- Implement strategies to detect, prevent, and resolve circular dependencies in Spring (such as `@Lazy`).

---

## Why This Matters
Real-world systems are not composed of isolated beans. Instead, components are structured as a graph of cooperating objects. Designing clean associations between these beans is straightforward, but errors in system design can lead to **Circular Dependencies** (e.g., Bean A needs Bean B, and Bean B needs Bean A). When this happens, the Spring container cannot determine which object to construct first and crashes during boot. Knowing how to map references, trace dependency graphs, and resolve circular dependency conflicts is essential for maintaining application stability.

---

## The Concept

### Wiring Bean References
When a bean needs to consume another bean, we map the association using references.

#### 1. In XML: The `<ref>` Element
To reference a bean by its ID in XML, use the `ref` attribute or `<ref>` sub-tag.
```xml
<bean id="orderRepo" class="com.example.OrderRepository" />

<bean id="orderService" class="com.example.OrderService">
    <!-- Wiring reference to orderRepo -->
    <constructor-arg ref="orderRepo" />
</bean>
```

#### 2. In Annotations: `@Autowired`
With annotations, Spring resolves the dependency by searching the container for a bean of the matching class type.
```java
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired // Auto-wires orderRepository by scanning the container
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
```

---

### Circular Dependency: The Deadlock
A **Circular Dependency** occurs when two or more beans require each other directly or indirectly, creating a cycle.

```
┌──────────┐            ┌──────────┐
│  Bean A  │ ──needs──> │  Bean B  │
└──────────┘            └──────────┘
     ▲                       │
     └─────────needs─────────┘
```

#### Why it fails:
To construct `Bean A`, Spring must first inject `Bean B`. But to construct `Bean B`, Spring must first inject `Bean A`. Since neither bean can be fully constructed, Spring throws a **`BeanCurrentlyInCreationException`** during startup and shuts down.

---

### Resolving Circular Dependencies

If you encounter a circular dependency, apply these three strategies (ordered from best to worst practice):

#### 1. Refactor the Code (Recommended)
Circular dependencies are almost always a design flaw (code smell). It indicates that your classes are too tightly coupled or have too many responsibilities.
-   **Solution:** Extract the shared logic into a new, third bean (`Bean C`) that both `Bean A` and `Bean B` can depend on, breaking the loop.

#### 2. Use Setter Injection Instead of Constructor Injection
Constructor injection enforces instantiation order, making cycles fatal. Setter injection allows Spring to instantiate both beans in an unconfigured state first, and wire their properties afterward.
*Warning:* While this allows the application to boot, it can lead to null-reference runtime bugs if not handled carefully.

#### 3. Use `@Lazy` to Break the Cycle
Place the `@Lazy` annotation on one of the injection points. Instead of instantiating the bean, Spring injects a lazy-initialization proxy, breaking the startup cycle. The actual bean is only instantiated when a method is called on it.

```java
@Component
public class BeanA {
    private final BeanB beanB;

    // Use @Lazy on BeanB parameter to prevent startup deadlock
    public BeanA(@Lazy BeanB beanB) {
        this.beanB = beanB;
    }
}
```

---

## Code Example

Let's look at a circular dependency scenario and how to resolve it using `@Lazy`.

### The Circular Deadlock
```java
package com.example.deadlock;
import org.springframework.stereotype.Component;

@Component
class ComponentA {
    private final ComponentB compB;
    public ComponentA(ComponentB compB) { this.compB = compB; }
}

@Component
class ComponentB {
    private final ComponentA compA;
    public ComponentB(ComponentA compA) { this.compA = compA; }
}
```
If you boot this, Spring crashes with:
`Error creating bean with name 'componentA': Requested bean is currently in creation: Is there an unresolvable circular reference?`

### The Resolution (Using `@Lazy`)
```java
package com.example.resolved;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
class ComponentA {
    private final ComponentB compB;
    
    // Lazy proxy injected here breaks the construction loop
    public ComponentA(@Lazy ComponentB compB) { 
        this.compB = compB; 
    }
}

@Component
class ComponentB {
    private final ComponentA compA;
    public ComponentB(ComponentA compA) { 
        this.compA = compA; 
    }
}
```

---

## Summary
-   References are resolved in XML using the `<ref>` attribute and in annotations using `@Autowired`.
-   **Circular Dependencies** happen when beans require each other in a closed loop, triggering a `BeanCurrentlyInCreationException`.
-   The best way to resolve a circular reference is to **refactor the design** by extracting the common dependency.
-   You can break the loop programmatically by switching to setter injection or injecting a proxy using the **`@Lazy`** annotation.

---

## Additional Resources
-   [Spring Documentation: Circular Dependencies](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html#beans-dependency-resolution)
-   [Baeldung: Circular Dependencies in Spring](https://www.baeldung.com/spring-circular-dependencies)
-   [StackOverflow: How to fix circular dependency in Spring](https://stackoverflow.com/questions/43209506/circular-dependency-in-spring-boot-2-0)
