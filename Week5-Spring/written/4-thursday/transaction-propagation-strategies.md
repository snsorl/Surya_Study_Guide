# Spring Transaction Propagation Strategies

## Learning Objectives
- Define what transaction propagation means in the Spring framework.
- Explain the behavior of the seven propagation strategies (`REQUIRED`, `REQUIRES_NEW`, `SUPPORTS`, `NOT_SUPPORTED`, `MANDATORY`, `NEVER`, `NESTED`).
- Identify the appropriate propagation level based on application scenarios.
- Avoid transactional proxy boundary pitfalls.

---

## Why This Matters
When a transaction-enabled method calls *another* transaction-enabled method, Spring needs instructions on how to handle the transaction boundary. Should the second method run inside the existing transaction? Or should it suspend the active transaction and open a brand-new one? The wrong configuration can lead to data loss or unwanted rollbacks. For example, if your application throws a non-critical error during audit logging, you do not want it to roll back your primary purchase transaction. Configuring transaction **propagation levels** allows you to orchestrate nested transaction boundaries precisely.

---

## The Concept

### What is Transaction Propagation?
**Transaction Propagation** defines how transaction boundaries are created and shared when a transaction-scoped method invokes another transaction-scoped method.

```
[ Service Method A ] ─── calls ───► [ Service Method B ]
  (@Transactional)                    (@Transactional)
                                             │
      (How does Method B run?) ◄─────────────┘
```

---

### The Seven Propagation Levels

Spring supports seven propagation levels, configured via `Propagation` properties inside the `@Transactional` annotation.

#### 1. `REQUIRED` (Default)
Spring joins the active transaction if one exists. If no transaction is active, it creates a new one.
-   *Use Case:* The default choice for 99% of business service methods.

#### 2. `REQUIRES_NEW`
Spring suspends the active transaction (if present) and opens a **new, independent transaction**. When this new transaction finishes, the original transaction resumes.
-   *Use Case:* Logging, auditing, or notification tasks. If the nested service fails, it rolls back its own transaction, but the parent transaction can still commit successfully.

#### 3. `MANDATORY`
Requires an active transaction. If no transaction is active, Spring throws an exception immediately.
-   *Use Case:* High-security calculation methods that must only be called within an active transaction block.

#### 4. `SUPPORTS`
If a transaction is active, the method joins it. If no transaction is active, the method executes non-transactionally.
-   *Use Case:* Read-only operations.

#### 5. `NOT_SUPPORTED`
Suspends any active transaction and executes the method non-transactionally.
-   *Use Case:* Long-running operations (like third-party API calls) that do not need database locks and should run outside the transaction.

#### 6. `NEVER`
Executes the method non-transactionally. If a transaction is active, it throws an exception.
-   *Use Case:* Legacy database utilities.

#### 7. `NESTED`
If a transaction is active, it creates a database **savepoint**. If the nested method fails, it rolls back to this savepoint, leaving the parent transaction active.
-   *Use Case:* Sub-processes that can fail without rolling back the primary transaction (only supported by databases that support JDBC savepoints).

---

### Comparison Cheat-Sheet

| Strategy | If Transaction Exists | If No Transaction Exists |
| :--- | :--- | :--- |
| **`REQUIRED`** | Joins active transaction | Opens new transaction |
| **`REQUIRES_NEW`**| Suspends active; opens new | Opens new transaction |
| **`MANDATORY`** | Joins active transaction | Throws Exception |
| **`SUPPORTS`** | Joins active transaction | Runs non-transactionally |
| **`NOT_SUPPORTED`**| Suspends active; runs non-tx | Runs non-transactionally |
| **`NEVER`** | Throws Exception | Runs non-transactionally |
| **`NESTED`** | Creates database Savepoint | Opens new transaction |

---

## Code Example: Primary Flow vs. Audit Logging

Let's look at how to use `REQUIRES_NEW` to ensure audit logs are written even if the main purchase transaction fails.

### The Audit Service (Independent Transaction)
```java
package com.example.service;

import com.example.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {
    private final AuditLogRepository logRepository;

    public AuditService(AuditLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    // REQUIRES_NEW guarantees this log record is committed to the database
    // even if the main purchase method fails and rolls back!
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeLog(String message) {
        logRepository.save(new AuditLog(message));
    }
}
```

### The Purchase Service (Parent Transaction)
```java
package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillingService {
    private final AuditService auditService;
    private final AccountRepository accountRepository;

    public BillingService(AuditService auditService, AccountRepository accountRepository) {
        this.auditService = auditService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void processPurchase(Long userId, double cost) {
        // 1. Audit log executes in a separate transaction (commits immediately)
        auditService.writeLog("Attempting purchase for user ID: " + userId);

        // 2. Main debit operation
        accountRepository.debit(userId, cost);

        // If a check fails here, processPurchase rolls back,
        // but the audit log entry remains committed in the database!
        if (cost > 5000.0) {
            throw new IllegalArgumentException("Purchase exceeds transaction limit.");
        }
    }
}
```

---

## Summary
-   **Propagation** controls how transactions are shared between nested method calls.
-   **`REQUIRED`** (default) joins existing transactions; **`REQUIRES_NEW`** suspends parent transactions to open a new one.
-   Use **`REQUIRES_NEW`** for logging, auditing, and notifications to ensure logs persist even if the main transaction fails.
-   Use **`MANDATORY`** to enforce that a method must only be called within an active transaction block.

---

## Additional Resources
-   [Spring Documentation: Transaction Propagation](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/tx-propagation.html)
-   [Baeldung: Transaction Propagation in Spring](https://www.baeldung.com/spring-transactional-propagation-isolation)
-   [Baeldung: Understanding @Transactional Pitfalls](https://www.baeldung.com/spring-transactional-problems)
