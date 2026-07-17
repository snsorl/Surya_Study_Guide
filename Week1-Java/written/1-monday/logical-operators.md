# Logical Operators in Java

## Learning Objectives
- Define and apply the logical operators: AND (`&&`), OR (`||`), and NOT (`!`).
- Construct truth tables to understand logic outcomes.
- Explain **Short-Circuit Evaluation** and its practical performance and safety benefits.
- Combine multiple conditions to build complex logical structures.

---

## Why This Matters
Real-world systems rarely make decisions based on a single condition. For example, an e-commerce checkout should apply free shipping *only if* the customer is a premium member **AND** their purchase is over $25, **OR** if they have a special promotional code. 

To build these complex rules, developers use **logical operators**. They allow you to glue multiple boolean comparison checks together. Furthermore, understanding how Java evaluates these expressions (via short-circuiting) is a critical optimization tool that also prevents common runtime crashes.

---

## The Concept

### The Three Logical Operators
Java provides three main logical operators that operate strictly on boolean values:

| Operator | Name | Behavior | Truth Condition |
| :--- | :--- | :--- | :--- |
| **`&&`** | **Logical AND** | Returns `true` if **both** operands are true. | `true && true` -> `true` (any other combination is `false`) |
| **`||`** | **Logical OR** | Returns `true` if **at least one** operand is true. | `false || false` -> `false` (any other combination is `true`) |
| **`!`** | **Logical NOT** | Inverts the value (unary operator). | `!true` -> `false`, `!false` -> `true` |

---

### Truth Tables
A truth table shows the resulting boolean values for all possible operand inputs. Let `A` and `B` represent boolean values:

| A | B | A && B | A \|\| B | !A |
| :---: | :---: | :---: | :---: | :---: |
| `true` | `true` | `true` | `true` | `false` |
| `true` | `false` | `false` | `true` | `false` |
| `false` | `true` | `false` | `true` | `true` |
| `false` | `false` | `false` | `false` | `true` |

---

### Short-Circuit Evaluation (Lazy Evaluation)
Java's logical AND (`&&`) and logical OR (`||`) perform what is known as **short-circuit evaluation**. This means the JVM evaluates the expression from left to right and stops checking as soon as the final outcome is guaranteed.

#### 1. Short-Circuit AND (`&&`)
For the expression `A && B`, if `A` evaluates to `false`, it is mathematically impossible for the entire expression to be true. Therefore, the JVM **skips** evaluating operand `B` entirely.
```java
boolean result = (5 > 10) && (someExpensiveDatabaseCall());
// (5 > 10) is false.
// someExpensiveDatabaseCall() is NEVER executed, saving resources!
```

#### 2. Short-Circuit OR (`||`)
For the expression `A || B`, if `A` evaluates to `true`, the overall expression is guaranteed to be true regardless of what `B` is. Therefore, the JVM **skips** evaluating operand `B`.
```java
boolean result = (10 > 5) || (someLongCalculation());
// (10 > 5) is true.
// someLongCalculation() is NEVER executed!
```

---

### Practical Safety Example: Avoiding Crashes
Short-circuit evaluation is frequently used to write clean defensive code. For example, if you want to check if an array has elements, you must ensure the array reference is not null first. 

Using `&&` prevents a `NullPointerException` because if the reference *is* null, the left-hand check fails and the right-hand member access check is skipped:

```java
int[] data = null;

// Safe because of short-circuit AND:
if (data != null && data.length > 0) {
    System.out.println("Processing data...");
} else {
    System.out.println("No data to process."); // This prints safely.
}
```
If Java did not support short-circuiting (or if you used the bitwise `&` operator instead, which evaluates both sides), checking `data.length` on a null reference would crash the program.

---

## Code Example
Let's see how logical operators combine flags and prevent crashes in a runnable Java program:

```java
public class LogicalOperatorsDemo {
    public static void main(String[] args) {
        boolean hasGoodCredit = true;
        boolean hasHighIncome = false;
        boolean hasCoSigner = true;

        // 1. Combining AND (&&) and OR (||)
        // A loan is approved if applicant has: (Good Credit AND High Income) OR has a Co-signer
        boolean isLoanApproved = (hasGoodCredit && hasHighIncome) || hasCoSigner;
        System.out.println("Loan Approved? " + isLoanApproved); // Prints true (because of the co-signer)

        // 2. Logical NOT (!)
        boolean isRestrictedUser = false;
        if (!isRestrictedUser) {
            System.out.println("Access granted to content.");
        }

        // 3. Short-circuit safety check
        String username = null;
        
        // This is safe. The left condition (username != null) is false.
        // The right condition (username.startsWith("admin")) is NOT evaluated.
        if (username != null && username.startsWith("admin")) {
            System.out.println("Welcome, Administrator!");
        } else {
            System.out.println("Regular guest checkout.");
        }
    }
}
```

---

## Summary
- **Logical AND (`&&`)** requires both sides to be true.
- **Logical OR (`||`)** requires at least one side to be true.
- **Logical NOT (`!`)** flips a boolean value.
- **Short-circuiting** evaluates expressions from left to right and stops early once the result is determined.
- Short-circuiting is a core tool for writing safe checks (like checking for `null` before reading values).

---

## Additional Resources
- [Java Logical Operators - GeeksforGeeks](https://www.geeksforgeeks.org/java-logical-operators-with-examples/)
- [Understanding Short-Circuiting in Java - Baeldung](https://www.baeldung.com/java-operators#logical-operators)
- [Operator Precedence and Logic - Oracle Java SE Docs](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html)
