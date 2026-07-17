# The `@Transactional` Annotation in Detail

## Learning Objectives
- Detail the configuration options and attributes of the `@Transactional` annotation.
- Compare class-level placement vs. method-level placement of `@Transactional`.
- Use the `readOnly` flag to optimize database select query performance.
- Configure custom rollback rules using `rollbackFor` and `noRollbackFor`.
- Avoid common transaction proxy self-invocation traps.

---

## Why This Matters
Adding `@Transactional` to your code is simple, but its default settings might not be optimal for your application. For example, by default, Spring *only* rolls back transactions for unchecked exceptions (like `NullPointerException` or `IllegalArgumentException`). If your code throws a checked exception (like `IOException` or a custom `SQLException`), Spring will commit the transaction anyway, which can lead to database inconsistency. Understanding the attributes of `@Transactional`—specifically custom rollback rules, read-only optimizations, and proxy behaviors—is critical to writing reliable database logic.

---

## The Concept

### Key Attributes of `@Transactional`

---

### 1. Custom Rollback Rules
By default, Spring rolls back transactions only for **unchecked exceptions** (`RuntimeException` and its subclasses, plus `Error` objects). It does *not* roll back for **checked exceptions** (classes extending `Exception` directly).

You can customize this behavior using:
-   **`rollbackFor`**: Defines exception classes that should trigger a rollback (even checked ones).
-   **`noRollbackFor`**: Defines exception classes that should *not* trigger a rollback.

```java
// Roll back for checked exceptions (like CustomCheckedException)
@Transactional(rollbackFor = CustomCheckedException.class)
public void updateData() throws CustomCheckedException { ... }
```

---

### 2. Read-Only Optimization (`readOnly`)
For methods that only query the database (like search or view operations), set `readOnly = true`.
-   **Performance Benefit**: Hibernate disables dirty checking (checking if the entity was modified) on read-only queries. This saves memory and CPU cycles during database retrieval.
-   **Safety**: Prevents developers from modifying records inside the read-only query method.

```java
@Transactional(readOnly = true)
public Customer getCustomer(Long id) {
    return customerRepository.findById(id).orElse(null);
}
```

---

### 3. Annotation Placement: Class vs. Method
-   **Class Level**: Applies to all public methods in the class. It is useful for repositories or read-only service classes.
-   **Method Level**: Overrides the class-level configuration. Use this to apply specific configurations (like transaction write boundaries) to individual methods.

```java
@Service
@Transactional(readOnly = true) // All public methods are read-only by default
public class BookingService {

    // Overrides class default to allow database updates and set rollback rules
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void createBooking(Booking booking) { ... }
}
```

---

### The Self-Invocation Trap (Proxy Mechanics)
Because Spring implements transactions using **AOP Proxies**, `@Transactional` only works when calls are intercepted by the proxy.

#### The Pitfall:
If a non-transactional method in a class calls a `@Transactional` method in the **same class**, the call is executed internally using the Java `this` reference. Since it does *not* go through the Spring proxy, **the transaction will not start**.

```java
public class BookingService {

    public void processAll() {
        // SELF-INVOCATION: Calls saveBooking() internally.
        // The @Transactional annotation on saveBooking() is completely IGNORED!
        saveBooking(new Booking()); 
    }

    @Transactional
    public void saveBooking(Booking booking) {
        // DB updates here run without a transaction!
    }
}
```
*Solution:* Ensure transactional methods are invoked from separate, external beans, or inject the bean into itself (using setter injection) to force calls to go through the proxy.

---

## Code Example: Configured Transaction

Here is a configured service method setting custom rollback rules and transaction limits.

```java
package com.example.service;

import com.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Configures transaction:
    // - Enforces database write permissions (readOnly = false)
    // - Sets timeout to 5 seconds. If exceeded, the transaction rolls back.
    // - Rolls back even if a checked IOException is thrown.
    @Transactional(
        readOnly = false,
        timeout = 5,
        rollbackFor = { IOException.class, CustomException.class }
    )
    public void importProducts(String filepath) throws IOException {
        // Read file and parse records...
        // Database operations using productRepository...
    }
}
```

---

## Summary
-   **`@Transactional`** configurations default to rolling back only on unchecked exceptions.
-   Use **`rollbackFor`** to force rollbacks on checked exceptions, and **`noRollbackFor`** to prevent rollbacks.
-   Set **`readOnly = true`** on query methods to optimize memory usage and CPU cycles by disabling Hibernate's dirty-checking.
-   **Self-Invocation** (calling a transactional method within the same class) bypasses the Spring proxy, meaning the transaction will not start.

---

## Additional Resources
-   [Spring Documentation: Transaction Management Customization](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-decl-explained.html)
-   [Baeldung: Spring @Transactional Annotation Guide](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring)
-   [Baeldung: Spring Transaction Timeout Explainer](https://www.baeldung.com/spring-transaction-timeouts)
