# Creating Custom Exceptions

## Learning Objectives
- Explain the business and technical value of designing domain-specific custom exceptions.
- Select between inheriting from `Exception` (checked) and `RuntimeException` (unchecked) when building custom exceptions.
- Implement standard constructor overrides inside custom exception classes, including root-cause chaining.
- Trigger custom exceptions manually using the **`throw`** keyword.

---

## Why This Matters
Java's standard library provides a rich set of built-in exceptions like `IllegalArgumentException`, `NullPointerException`, and `IOException`. While these are useful, they are generic. 

If you are building a banking application and a transaction fails because the client has insufficient balance, throwing a generic `IllegalArgumentException` is unclear. It does not communicate what business rule was violated. By creating a custom exception named **`InsufficientFundsException`**, you make your codebase highly readable, ensure logs pinpoint the exact business error, and allow your exception handling blocks to route payment failures separately from generic validation errors.

---

## The Concept

### When to Create Custom Exceptions
Build custom exceptions when:
1.  You want to capture domain-specific errors (e.g. `UserBlockedException`, `InvalidPromoCodeException`).
2.  You want to separate specific business failures from generic system errors.
3.  You need to bundle additional metadata variables (like account ID or remaining limits) along with the error message.

---

### Implementation Workflow

#### Step 1: Choose the Base Class
*   **Unchecked Custom Exception**: Extend **`java.lang.RuntimeException`**. This is the standard practice in modern Java systems (such as Spring Boot frameworks) because it prevents code clutter from mandatory `throws` declarations.
*   **Checked Custom Exception**: Extend **`java.lang.Exception`**. Use this when the caller must be explicitly forced to write a recovery plan immediately.

#### Step 2: Implement Constructors
At a minimum, your class should declare a constructor that accepts a String detail message and passes it to the parent constructor using **`super(message)`**:

```java
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message); // Send message to parent RuntimeException
    }
}
```

---

### Exception Chaining
If your custom exception is triggered because a lower-level library threw a different error, you should preserve the original exception history. This is called **Exception Chaining**. You implement this by adding a constructor that accepts a `Throwable cause`:

```java
public InsufficientFundsException(String message, Throwable cause) {
    super(message, cause); // Preserves original stack trace history!
}
```

---

### Triggering Custom Exceptions
To manually trigger your exception, construct a new instance and use the **`throw`** keyword. This immediately halts normal method execution and initiates stack propagation.

```java
if (balance < amount) {
    throw new InsufficientFundsException("Withdrawal of $" + amount + " failed due to low balance.");
}
```

---

## Code Example: Bank Account Custom Routines
The following class defines a custom unchecked `InsufficientFundsException`, a custom checked `AccountLockedException`, and implements a bank account model that manual-throws both errors:

```java
// 1. Custom Unchecked Exception (RuntimeException)
class InsufficientFundsException extends RuntimeException {
    private double currentBalance;
    private double attemptAmount;

    public InsufficientFundsException(String message, double balance, double attempt) {
        super(message);
        this.currentBalance = balance;
        this.attemptAmount = attempt;
    }

    public double getDeficit() {
        return attemptAmount - currentBalance;
    }
}

// 2. Custom Checked Exception (Exception)
class AccountLockedException extends Exception {
    public AccountLockedException(String message) {
        super(message);
    }
    
    // Constructor supporting Exception Chaining
    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }
}

// 3. Banking Logic Class
class BankAccount {
    private double balance;
    private boolean locked = false;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    // Throws Unchecked exception (throws declaration is optional)
    public void withdraw(double amount) {
        if (locked) {
            System.out.println("Transaction blocked: Account is locked.");
            return;
        }

        if (amount > balance) {
            // Triggering the custom unchecked exception manually
            throw new InsufficientFundsException("Insufficient funds.", this.balance, amount);
        }
        this.balance -= amount;
        System.out.println("Successfully withdrew $" + amount + ". New Balance: $" + this.balance);
    }

    // Throws Checked exception (throws signature is MANDATORY)
    public void processSecurityAudit(int failedAttempts) throws AccountLockedException {
        if (failedAttempts > 3) {
            this.locked = true;
            // Triggering the custom checked exception manually
            throw new AccountLockedException("Account locked: Exceeded maximum password attempts.");
        }
    }
}

// Execution Class
public class CustomExceptionDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(500.00);

        System.out.println("--- 1. Testing Custom Unchecked Exception ---");
        try {
            account.withdraw(600.00); // Throws InsufficientFundsException
        } catch (InsufficientFundsException e) {
            System.out.println("[CATCH] Error: " + e.getMessage());
            System.out.println("Deficit details: Missing $" + e.getDeficit());
        }

        System.out.println("\n--- 2. Testing Custom Checked Exception ---");
        try {
            // Processing security check (Requires try-catch to compile!)
            account.processSecurityAudit(5); 
        } catch (AccountLockedException e) {
            System.out.println("[CATCH] Security Alert: " + e.getMessage());
        }
    }
}
```

---

## Summary
- Create **custom exceptions** to represent specific domain rules (e.g. `InsufficientFundsException`) and make logs descriptive.
- Inherit from **`RuntimeException`** to write custom unchecked exceptions (recommended).
- Inherit from **`Exception`** to write custom checked exceptions (forces immediate compilation checks).
- Trigger custom exceptions manually using the **`throw new CustomException("message")`** statement.
- Implement **Exception Chaining** (`super(message, cause)`) to preserve the historical stack trace when rethrowing exceptions.

---

## Additional Resources
- [How to Create a Custom Exception in Java - Baeldung](https://www.baeldung.com/java-new-custom-exception)
- [Exception Chaining in Java - GeeksforGeeks](https://www.geeksforgeeks.org/chained-exceptions-java/)
- [Java throw keyword - W3Schools](https://www.w3schools.com/java/ref_keyword_throw.asp)