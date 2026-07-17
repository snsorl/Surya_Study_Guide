# Checked vs. Unchecked Exceptions

## Learning Objectives
- Contrast Checked Exceptions with Unchecked Exceptions (Runtime Exceptions).
- Identify the role of the `java.lang.RuntimeException` parent class.
- Apply the **Catch-or-Declare** compiler rule for Checked Exceptions.
- Declare exception propagation using the **`throws`** keyword in method signatures.

---

## Why This Matters
Java is unique among modern programming languages because it enforces two separate categories of exceptions at compile-time: **Checked** and **Unchecked**. 

If you write a method that reads a file on a disk, the Java compiler will refuse to compile your program unless you explicitly catch the potential `IOException` or declare that your method passes that error up the call stack. This forces you to plan for external failures. However, if your method can throw an `ArrayIndexOutOfBoundsException` (an unchecked exception), the compiler lets it compile without warnings. Understanding this distinction is vital to designing APIs that communicate their failure modes clearly to other developers.

---

## The Concept

### Checked vs. Unchecked Exceptions

```
                           [ Exception ]
                            /         \
                 [ Checked ]           [ RuntimeException ]
                 - Inherits: Exception  - Inherits: RuntimeException
                 - External anomalies  - Logic bugs / Developer errors
                 - Compile-Enforced     - Not Compile-Enforced
```

#### 1. Checked Exceptions (Compile-Time Enforced)
Checked exceptions represent operational failures that are outside the programmer's direct control. For example, your code is correct, but the network connection is blocked, or the user deleted a required database file.
*   **Root Class**: Any subclass of `java.lang.Exception` that does **not** inherit from `java.lang.RuntimeException` (e.g. `IOException`, `FileNotFoundException`, `SQLException`).
*   **The Catch-or-Declare Rule**: The compiler strictly checks these. If a method can throw a checked exception, the caller **must** either:
    1.  Handle it inside a `try-catch` block.
    2.  Declare it in the method signature using the **`throws`** keyword, passing the responsibility to the next method in the call stack.

---

#### 2. Unchecked Exceptions (Runtime Exceptions)
Unchecked exceptions represent programming mistakes, logic bugs, or API misuses. 
*   **Root Class**: Any subclass of `java.lang.RuntimeException` (e.g. `NullPointerException`, `ArithmeticException`, `IllegalArgumentException`).
*   **Compiler Rule**: The compiler does not enforce any checks. You are not forced to write `try-catch` blocks or declare them in your method signature. The correct solution for unchecked exceptions is writing clean logic (like checking for null pointers or validating ranges) rather than catching the error.

---

### Comparison Matrix

| Feature | Checked Exceptions | Unchecked (Runtime) Exceptions |
| :--- | :--- | :--- |
| **Root Parent Class** | `java.lang.Exception` | `java.lang.RuntimeException` |
| **Root Cause Category**| External failures (network, file system). | Logic bugs, developer mistakes. |
| **Enforced by Compiler**| Yes (Catch-or-Declare rule active). | No (Can compile without handling). |
| **Propagated using** | `throws` keyword in signature. | Optional declaration only. |
| **Handling Strategy** | Recover gracefully (retry, prompt user).| Fix the underlying code logic bug. |

---

## Code Example: Compile-time Validation
The following program defines a file scanner (throwing a Checked exception) and a calculator (throwing an Unchecked exception), showcasing the compiler requirements:

```java
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ExceptionTypesDemo {

    /**
     * Simulates reading a configuration file.
     * Since FileReader can throw FileNotFoundException (Checked), 
     * we MUST declare it in the method signature using the 'throws' keyword.
     */
    public static void loadConfigFile(String filePath) throws FileNotFoundException {
        System.out.println("[CONFIG] Attempting to open file: " + filePath);
        // Compiles only because of the 'throws FileNotFoundException' in signature
        FileReader reader = new FileReader(filePath); 
    }

    /**
     * Sets a student grade value.
     * Throws IllegalArgumentException (Unchecked) if the grade is invalid.
     * Note: No 'throws' declaration is required in the signature!
     */
    public static void updateGrade(double score) {
        if (score < 0.0 || score > 100.0) {
            // Throwing unchecked exception manually
            throw new IllegalArgumentException("Invalid grade score: " + score); 
        }
        System.out.println("[GRADE] Grade updated to: " + score);
    }

    public static void main(String[] args) {
        System.out.println("--- 1. Processing Unchecked Exceptions ---");
        // We can call updateGrade without wrapping it in try-catch.
        // It compiles fine. If an error occurs, it crashes the thread at runtime.
        updateGrade(85.5); 
        
        try {
            updateGrade(-5.0); // Triggers IllegalArgumentException
        } catch (IllegalArgumentException e) {
            System.out.println("[RECOVERED] Caught logical error: " + e.getMessage());
        }

        System.out.println("\n--- 2. Processing Checked Exceptions ---");
        // loadConfigFile("config.txt"); 
        // COMPILER ERROR: Unhandled exception type java.io.FileNotFoundException
        
        // Enforcing the Catch-or-Declare rule
        try {
            loadConfigFile("non_existent_profile.txt");
        } catch (FileNotFoundException e) {
            System.out.println("[RECOVERED] Captured File System Error: " + e.getMessage());
        }
    }
}
```

---

## Summary
- **Checked Exceptions** represent external failures (e.g. `FileNotFoundException`) and are checked by the compiler.
- **Unchecked Exceptions** (RuntimeException subclasses, e.g. `NullPointerException`) represent developer logic bugs.
- **Catch-or-Declare**: Checked exceptions must be caught using `try-catch` or declared in the signature using the **`throws`** keyword.
- Fix unchecked exceptions by writing boundary guards; handle checked exceptions to prevent system crashes.

---

## Additional Resources
- [Checked vs Unchecked Exceptions - Baeldung](https://www.baeldung.com/java-checked-unchecked-exceptions)
- [How to Declare Exceptions using throws - Oracle Docs](https://docs.oracle.com/javase/tutorial/essential/exceptions/declaring.html)
- [Exception Handling Conventions - GeeksforGeeks](https://www.geeksforgeeks.org/exceptions-in-java/)