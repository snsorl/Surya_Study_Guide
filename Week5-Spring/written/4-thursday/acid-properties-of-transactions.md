# ACID Properties & Transaction Boundaries in Spring

## Learning Objectives
- Define the ACID properties of database transactions.
- Explain how Spring's `@Transactional` annotation enforces transaction boundaries.
- Describe how `@Transactional` guarantees atomicity across multiple repository operations.
- Tracing transaction lifecycle interceptors and rollbacks under the hood.

---

## Why This Matters
Suppose you are coding a bank transfer system. The transfer method subtracts $100 from Account A, and adds $100 to Account B. If the network drops or a database constraint fails *after* subtracting from Account A but *before* adding to Account B, the money vanishes. In enterprise systems, database operations cannot execute as isolated updates. They must execute as a single atomic unit. Understanding the ACID properties of database transactions and how Spring manages transaction boundaries using `@Transactional` is critical to ensuring database consistency and preventing data corruption.

---

## The Concept

### What is a Transaction?
A **database transaction** is a sequence of one or more database operations executed as a single, logical unit of work.

To guarantee data safety, transactions must adhere to the **ACID** properties:

```
                      ┌───────────────────────┐
                      │    ACID Properties    │
                      └───────────┬───────────┘
         ┌──────────────────┬─────┴──────┬──────────────────┐
         ▼                  ▼            ▼                  ▼
   [ Atomicity ]     [ Consistency ] [ Isolation ]    [ Durability ]
 (All-or-Nothing)   (Rule Integrity) (Concurrence)   (Perm. Storage)
```

1.  **Atomicity**: The "All-or-Nothing" rule. Either all database updates in the transaction succeed, or the entire transaction is aborted and rolled back, leaving the database unchanged.
2.  **Consistency**: A transaction can only transition the database from one valid state to another, maintaining database rules (such as foreign keys and constraints).
3.  **Isolation**: Prevents concurrent transactions from seeing each other's uncommitted data changes, preventing dirty reads or updates.
4.  **Durability**: Once a transaction commits, the changes are written to persistent storage (disk) and will not be lost even if the system crashes immediately afterward.

---

### Spring's Transaction Manager: How `@Transactional` Works

Spring provides a declarative transaction management framework. By placing `@Transactional` on a class or method, Spring intercepts method execution to manage the transaction lifecycle.

#### Under the Hood: The Proxy Pattern
When Spring loads a bean annotated with `@Transactional`, it wraps the bean in a dynamic **AOP Proxy** wrapper.

```
[ Client Call ] ──> [ Spring Proxy Interceptor ] ──> [ Starts Transaction ]
                                                             │
     [ Commits or Rolls Back ] ◄── [ Runs Method Logic ] ◄───┘
```

1.  **Starts Transaction**: When the method is invoked, the proxy intercepts the call and asks the `PlatformTransactionManager` to start a new transaction.
2.  **Runs Method**: The proxy runs the actual business method logic.
3.  **Evaluates Exit**:
    -   *Success:* If the method returns normally, the proxy commits the transaction, writing changes to the database.
    -   *Failure:* If the method throws a **Runtime Exception** (`RuntimeException` or `Error`), the proxy catches it, rolls back the transaction (reverting all changes), and rethrows the exception.

---

## Code Example: Ensuring Atomicity

Let's look at a bank transfer example to see how `@Transactional` ensures atomicity.

### The Service Class
```java
package com.example.service;

import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankTransferService {

    private final AccountRepository accountRepository;

    public BankTransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // @Transactional guarantees that if either update fails, the entire transaction rolls back!
    @Transactional
    public void transferFunds(Long sourceAccountId, Long targetAccountId, double amount) {
        // 1. Subtract funds from source account
        accountRepository.decreaseBalance(sourceAccountId, amount);

        // Simulate a system crash or logic error (e.g. check limit boundary)
        if (amount > 10000.0) {
            // Throwing this runtime exception forces the transaction manager to rollback
            // the decreaseBalance call above!
            throw new IllegalArgumentException("Transfer limit exceeded. Rolling back transaction.");
        }

        // 2. Add funds to target account
        accountRepository.increaseBalance(targetAccountId, amount);
    }
}
```

---

## Summary
-   **ACID** properties ensure database integrity through **Atomicity**, **Consistency**, **Isolation**, and **Durability**.
-   **`@Transactional`** delegates transaction management to Spring, removing the need for manual JDBC `commit()` or `rollback()` code.
-   Spring uses **AOP Proxies** to intercept method calls, opening a transaction at method start and committing it at method end.
-   By default, transactions are **rolled back** automatically if a **Runtime Exception** occurs inside the method.

---

## Additional Resources
-   [Spring Documentation: Declarative Transaction Management](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative.html)
-   [Baeldung: Guide to Transactions in Spring](https://www.baeldung.com/transaction-configuration-with-jpa-and-spring)
-   [Wikipedia: ACID Database Transactions](https://en.wikipedia.org/wiki/ACID)
