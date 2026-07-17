# Control Flow: Conditional Statements

## Learning Objectives
- Direct the flow of program execution using `if`, `else if`, and `else` statements.
- Simplify simple binary branching using the Ternary Operator.
- Use traditional `switch` statements and avoid fall-through compilation bugs.
- Write modern, safe, and concise Java `switch` expressions using arrow (`->`) syntax.

---

## Why This Matters
By default, the JVM executes your code sequentially, from the first line of the main method to the last. However, real-world applications are not linear. 

A banking app needs to verify if a user has sufficient funds before processing a withdrawal. A routing system needs to display different UI pages depending on whether a user is a trainee, instructor, or administrator. To make these choices, programs use **conditional statements**. Masterfully controlling execution paths is the key to creating interactive, dynamic, and intelligent business logic.

---

## The Concept

### 1. The `if` - `else if` - `else` Hierarchy
The most common way to branch execution is using the `if` statement. It evaluates a boolean condition (which must result in `true` or `false`).

*   **`if`**: Evaluates the initial condition. If true, its block runs.
*   **`else if`**: Evaluates a secondary condition if the previous conditions were false. You can chain as many `else if` statements as needed.
*   **`else`**: The fallback block. Runs only if all preceding conditions evaluate to false.

```java
int score = 85;

if (score >= 90) {
    System.out.println("Grade: A");
} else if (score >= 80) {
    System.out.println("Grade: B"); // This block executes, outputting Grade: B
} else if (score >= 70) {
    System.out.println("Grade: C");
} else {
    System.out.println("Grade: F");
}
```

---

### 2. The Ternary Operator
The ternary operator (`? :`) is a compact, single-line shorthand for a basic `if-else` statement. It is called "ternary" because it takes three operands:
1. A boolean condition
2. An expression to return if the condition is `true`
3. An expression to return if the condition is `false`

#### Syntax:
```java
variable = (condition) ? valueIfTrue : valueIfFalse;
```

#### Example:
```java
int age = 19;
// Using regular if-else:
String status;
if (age >= 18) {
    status = "Adult";
} else {
    status = "Minor";
}

// Using Ternary (much cleaner for simple assignments):
String statusTernary = (age >= 18) ? "Adult" : "Minor";
```

---

### 3. Switch Statements
When you need to compare a single variable against multiple potential values, chaining many `if-else` statements can become hard to read. A `switch` block is a cleaner alternative.

Java allows switching on: `byte`, `short`, `char`, `int`, `String`, and Enums.

#### A. Traditional Switch (Statement)
In traditional switch blocks, execution starts at the matching `case` and "falls through" to subsequent cases unless stopped by a `break` statement.

```java
int dayNumber = 2;
String dayName;

switch (dayNumber) {
    case 1:
        dayName = "Monday";
        break;
    case 2:
        dayName = "Tuesday"; // Matches here
        break;               // Exits the switch
    case 3:
        dayName = "Wednesday";
        break;
    default:
        dayName = "Unknown Day";
        break;
}
```
> [!CAUTION]
> If you omit the `break` keyword, the program will continue executing the code in the subsequent cases even if they don't match the variable value. This is a common logic bug known as **switch fall-through**.

#### B. Modern Switch (Expression)
Introduced in Java 14, **Switch Expressions** modernize the switch syntax, making it safer and cleaner:
- Uses arrow labels (`->`) instead of colons (`:`).
- **No fall-through**: Only the block code next to the arrow is executed. No `break` statement is needed.
- **Returns a Value**: The switch block itself behaves like an expression that can be assigned directly to a variable.
- Enforces completeness: The compiler will check if all possible paths are covered (requiring a `default` case in most scenarios).

```java
int dayNumber = 2;

// The entire switch evaluates to a value and assigns it to 'dayName'
String dayName = switch (dayNumber) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    default -> "Unknown Day"; // Required to cover all other integers
};
```

If you need a multi-line block within a modern switch expression, you can use curly braces and return the final value using the **`yield`** keyword:
```java
String status = switch (statusCode) {
    case 200 -> "Success";
    case 404 -> {
        System.out.println("Resource not found log alert");
        yield "Not Found"; // returns this value
    }
    default -> "Unknown Error";
};
```

---

## Code Example
Let's see conditionals, ternary assignments, and switch expressions integrated in a single Java class:

```java
public class ConditionalsDemo {
    public static void main(String[] args) {
        int accountBalance = 150;
        int withdrawalAmount = 200;

        // 1. If-Else logic
        if (withdrawalAmount <= 0) {
            System.out.println("Invalid amount.");
        } else if (withdrawalAmount <= accountBalance) {
            System.out.println("Transaction approved!");
        } else {
            System.out.println("Transaction declined: Insufficient funds.");
        }

        // 2. Ternary Operator
        boolean isOverdraft = (withdrawalAmount > accountBalance) ? true : false;
        System.out.println("Is overdraft? " + isOverdraft);

        // 3. Modern Switch Expression
        char cardTier = 'G'; // S = Silver, G = Gold, P = Platinum
        double discountRate = switch (cardTier) {
            case 'S' -> 0.05; // 5% discount
            case 'G' -> 0.10; // 10% discount
            case 'P' -> 0.15; // 15% discount
            default -> 0.0;   // No discount
        };
        System.out.println("Discount Rate: " + (discountRate * 100) + "%");
    }
}
```

---

## Summary
- **`if`-`else if`-`else`** statements evaluate boolean conditions to route control flow.
- The **Ternary Operator (`? :`)** is a clean shorthand for simple binary `if-else` assignments.
- Traditional switch statements suffer from potential **fall-through** errors if `break` is omitted.
- Modern **Switch Expressions (`->`)** are safer, support expression values, automatically prevent fall-through, and can return values using `yield` for multi-line cases.

---

## Additional Resources
- [Java Control Flow Statements - Oracle Tutorials](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/flow.html)
- [Java Switch Expressions - Baeldung](https://www.baeldung.com/java-switch-expression)
- [Control Flow Guide - W3Schools](https://www.w3schools.com/java/java_conditions.asp)
