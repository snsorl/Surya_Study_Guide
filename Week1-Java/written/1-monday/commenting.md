# Code Commenting and Documentation in Java

## Learning Objectives
- Write single-line, multi-line, and Javadoc comments in Java.
- Understand the structural syntax and purpose of Javadoc documentation.
- Implement key Javadoc tags such as `@param`, `@return`, and `@author`.
- Apply developer best practices for writing clean, meaningful, and maintainable comments.

---

## Why This Matters
Code is read far more often than it is written. While your code should be as self-explanatory as possible, there are times when you need to explain *why* you chose a specific implementation, document complex business algorithms, or describe how an API works so that other developers can use it without reading the source code.

Java has a built-in standard for generating documentation called **Javadoc**. In professional software environments, automated build pipelines run Javadoc compilation scripts to produce HTML API documentation websites directly from your code comments. Writing standardized comments is a critical professional habit that separates junior developers from enterprise-ready software engineers.

---

## The Concept

### The Three Types of Comments in Java

#### 1. Single-Line Comments (`//`)
Used for short explanations of specific lines of code. The compiler ignores everything from the `//` to the end of that line.
```java
int totalAmount = 100; // Represents the user's cart total in USD
```

#### 2. Multi-Line Comments (`/* ... */`)
Used for longer explanations or temporary code exclusion. The compiler ignores everything between the opening `/*` and closing `*/`.
```java
/*
  This block handles the tax calculation.
  Currently, it supports regional tax logic,
  but national VAT is scheduled for next month.
*/
double tax = totalAmount * 0.08;
```

#### 3. Javadoc Comments (`/** ... */`)
Specialized comments used to document classes, interfaces, constructors, methods, and fields. They start with `/**` (two asterisks) and end with `*/`. 

The JDK compiler uses these comments to generate structured HTML documentation. Javadoc comments are placed **immediately before** the declaration of the class, method, or field.

---

### Common Javadoc Tags
Javadoc comments support specific tag annotations (prefixed with the `@` symbol) that help structure the documentation:

| Tag | Purpose | Syntax |
| :--- | :--- | :--- |
| **`@param`** | Describes a method input parameter. | `@param parameterName Description of variable` |
| **`@return`** | Describes the return value of a method. | `@return Description of the returned value` |
| **`@author`** | Identifies the creator of the code. | `@author Developer Name` |
| **`@version`** | Specifies the current version of the class. | `@version 1.0` |
| **`@see`** | References other classes or documents. | `@see PackageName.ClassName` |

---

### Best Practices for Commenting

#### 1. Explain the "Why", Not the "What"
Avoid comments that simply repeat what the code already says.
*   **Bad**: `int age = 18; // Declare age variable and set to 18`
*   **Good**: `int limit = 180; // Default limit in minutes to prevent session timeout`

#### 2. Keep Comments Up-To-Date
Outdated comments are worse than no comments at all because they actively lie to the reader. If you update the code logic, immediately update or delete the corresponding comments.

#### 3. Write Self-Documenting Code First
Choose descriptive variable and method names rather than relying on comments to explain poorly written code.
*   **Bad**: `int d = 30; // d is days to wait`
*   **Good**: `int daysToWait = 30;` (No comment needed!)

---

## Code Example
The following script illustrates all three comment styles, including complete Javadoc annotations for a class and its utility method:

```java
/**
 * Utility class for calculations in the banking application.
 * This class contains helper operations for formatting and calculations.
 * 
 * @author Alex Dev
 * @version 1.1
 */
public class FinanceUtils {

    // Single-line comment: standard interest constant
    private static final double INTEREST_RATE = 0.05;

    /**
     * Calculates the interest accrued over a one-year period.
     * 
     * @param principal The starting balance of the account (must be positive)
     * @return The calculated interest amount as a double
     */
    public double calculateAnnualInterest(double principal) {
        /*
          Multi-line comment block:
          Verify if principal is invalid.
          If negative, we log warning and default interest return to zero.
         */
        if (principal <= 0) {
            System.out.println("Warning: Invalid principal entered.");
            return 0.0;
        }

        return principal * INTEREST_RATE;
    }
}
```

---

## Summary
- **Single-line comments (`//`)** are for quick inline notes.
- **Multi-line comments (`/* ... */`)** are block notes explaining algorithms.
- **Javadoc comments (`/** ... */`)** document public classes, methods, and fields. They reside immediately above declarations.
- Javadoc tags like **`@param`** and **`@return`** help describe input arguments and output returns.
- Good comments explain the **design intent ("Why")** instead of stating the obvious behavior ("What").

---

## Additional Resources
- [How to Write Doc Comments for the Javadoc Tool (Oracle)](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)
- [Javadoc Guide - Baeldung](https://www.baeldung.com/javadoc)
- [Effective Java Commenting Best Practices - GeeksforGeeks](https://www.geeksforgeeks.org/comments-in-java/)
