# Comparison Operators in Java

## Learning Objectives
- Apply comparison operators: `==`, `!=`, `<`, `>`, `<=`, and `>=` to primitives.
- Explain the key difference between **Value Equality** and **Reference Equality**.
- Identify and avoid the common pitfall of using `==` on reference variables like arrays or objects.
- Compare array contents using helper tools like `java.util.Arrays.equals()`.

---

## Why This Matters
Conditional logic is useless without comparison checks. If you want to build a security system, you need to check if the entered password equals (`==`) the stored password. If you are building a logistics tracker, you must know if the current package temperature is greater than (`>`) a safe threshold. 

However, in Java, comparing values is not always as simple as using the `==` operator. If you use `==` to compare two lists containing the exact same items, Java may tell you they are not equal! Understanding how comparison operators interact with primitives versus reference types in memory is vital to avoiding silent logic bugs.

---

## The Concept

### The Comparison Operators
Comparison operators are binary operators that compare two values and always return a **`boolean`** value (`true` or `false`). They are commonly used in `if` statements and loops.

| Operator | Meaning | Example | Result |
| :---: | :--- | :--- | :--- |
| **`==`** | Equal to | `5 == 5` | `true` |
| **`!=`** | Not equal to | `5 != 3` | `true` |
| **`>`** | Greater than | `5 > 8` | `false` |
| **`<`** | Less than | `5 < 8` | `true` |
| **`>=`** | Greater than or equal to | `5 >= 5` | `true` |
| **`<=`** | Less than or equal to | `4 <= 3` | `false` |

---

### The Reference vs. Value Equality Pitfall
This is one of the most critical conceptual differences in Java:

#### 1. Value Equality (For Primitives)
When you use `==` on primitive variables, Java looks directly at the values stored in **Stack memory** and compares them. This works exactly as you would expect.
```java
int a = 10;
int b = 10;
boolean match = (a == b); // true
```

#### 2. Reference Equality (For Reference Variables)
When you use `==` on reference variables (like arrays or objects), Java compares the **memory address (references)** stored on the Stack. It does **not** check the actual data stored on the Heap.
- If two reference variables point to different objects in memory, `==` will return `false`, even if the contents of those objects are identical!

```
STACK                                   HEAP
+----------------+                      +---------------------+
| array1 (0x101) +====================> | [ 1, 2, 3 ] (0x101) |
+----------------+                      +---------------------+
| array2 (0x202) +====================> | [ 1, 2, 3 ] (0x202) |
+----------------+                      +---------------------+

Checking array1 == array2 compares 0x101 and 0x202, returning false!
```

```java
int[] array1 = {1, 2, 3};
int[] array2 = {1, 2, 3};

System.out.println(array1 == array2); // Prints false! (different memory locations)
```

#### How to Check Content Equality
To check if the contents of two reference types are identical:
*   **For Arrays**: Use the utility method **`java.util.Arrays.equals(array1, array2)`**. This compares the sizes and elements of the arrays rather than their addresses.
*   **For Objects (like Strings)**: Use the `.equals()` method (e.g., `str1.equals(str2)`), which we will explore in detail on Wednesday.

---

## Code Example
Let's see primitive comparisons and the reference equality pitfall in a complete program:

```java
import java.util.Arrays; // Required for Arrays.equals

public class ComparisonDemo {
    public static void main(String[] args) {
        // 1. Primitive Comparisons
        int temperature = 72;
        int threshold = 70;
        
        System.out.println("Is hot? " + (temperature > threshold));     // true
        System.out.println("Is not hot? " + (temperature != threshold)); // true

        // 2. Reference Equality Pitfall (using Arrays)
        int[] originalPrices = {10, 20, 30};
        int[] matchPrices = {10, 20, 30};
        int[] linkedPrices = originalPrices; // Points to same memory address

        // Address comparison (==)
        System.out.println("Identical arrays == : " + (originalPrices == matchPrices)); // Prints false
        System.out.println("Same referenced arrays == : " + (originalPrices == linkedPrices)); // Prints true

        // Content comparison (Arrays.equals)
        boolean contentsMatch = Arrays.equals(originalPrices, matchPrices);
        System.out.println("Identical arrays content check: " + contentsMatch); // Prints true
    }
}
```

---

## Summary
- Comparison operators (`==`, `!=`, `<`, `>`, `<=`, `>=`) evaluate expressions to `true` or `false`.
- For **primitives**, `==` checks value equality.
- For **reference types**, `==` checks reference equality (are they located at the same memory address in the Stack?).
- To compare the contents of two arrays, use **`Arrays.equals()`**. To compare objects/strings, use the **`.equals()`** method (do not use `==`).

---

## Additional Resources
- [Java Comparison Operators - W3Schools](https://www.w3schools.com/java/java_operators.asp)
- [Difference between == and .equals() in Java - Baeldung](https://www.baeldung.com/java-comparing-objects)
- [Java Array Equality Guide - GeeksforGeeks](https://www.geeksforgeeks.org/arrays-equals-in-java-with-examples/)
