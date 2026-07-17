# Wrapper Classes and Autoboxing

## Learning Objectives
- Identify Java's eight primitive types and their corresponding **Wrapper Classes**.
- Explain the compilation mechanics of **Autoboxing** and **Unboxing**.
- Avoid the `NullPointerException` runtime crash during implicit unboxing.
- Explain the **Integer Cache** memory optimization and its impact on comparison operations.
- Utilize wrapper utility parsing methods to convert strings into numbers.

---

## Why This Matters
In Java, primitive data types (like `int`, `double`, `boolean`) are fast, lightweight, and stored directly on the Stack. However, they are not objects. They do not have methods, they cannot represent a missing value using `null`, and they cannot be stored inside Java's Collections Framework (which we will study on Friday, such as `ArrayList` or `HashMap` that require objects).

To solve this, Java provides **Wrapper Classes**. A wrapper class wraps a primitive value inside a heap-allocated object container. Understanding how the compiler moves values between primitives and wrappers (via **Autoboxing**) and the common pitfalls (like comparing wrappers with `==` or unboxing null objects) is essential to writing crash-free Java code.

---

## The Concept

### The 8 Wrapper Classes
Every primitive type in the `java.lang` package has a matching object wrapper class:

| Primitive Type | Wrapper Class | Size in Memory (Typical) |
| :--- | :--- | :--- |
| `byte` | `Byte` | 1 byte primitive |
| `short` | `Short` | 2 bytes primitive |
| `int` | `Integer` | 4 bytes primitive |
| `long` | `Long` | 8 bytes primitive |
| `float` | `Float` | 4 bytes primitive |
| `double` | `Double` | 8 bytes primitive |
| `char` | `Character` | 2 bytes primitive |
| `boolean`| `Boolean` | 1 bit primitive |

---

### Autoboxing and Unboxing
To make code clean, the Java compiler performs conversions between primitives and objects automatically:

*   **Autoboxing**: The automatic conversion of a primitive type into its corresponding wrapper object.
    ```java
    Integer age = 25; // Autoboxing
    // What the compiler actually writes under the hood:
    Integer age = Integer.valueOf(25);
    ```
*   **Unboxing**: The automatic conversion of a wrapper object back to a primitive type.
    ```java
    int agePrim = age; // Unboxing
    // What the compiler actually writes under the hood:
    int agePrim = age.intValue();
    ```

---

### Critical Pitfalls with Wrapper Classes

#### Pitfall 1: Unboxing a Null Pointer
Because wrapper classes are objects, their reference variables can be set to `null`. If you attempt to assign a `null` wrapper variable to a primitive variable, the compiler will not warn you, but the JVM will throw a **`NullPointerException`** at runtime.

```java
Integer scoreObj = null; // Valid object reference
int scorePrim = scoreObj; // CRASH! JVM attempts to run scoreObj.intValue() on a null pointer.
```

#### Pitfall 2: The Integer Cache Identity Check
For memory efficiency, Java maintains a cache of `Integer` objects for values ranging from **`-128` to `127`**. 
- If you box an integer in this range, Java returns a shared reference from the cache. Comparing them using `==` returns `true`.
- If you box an integer outside this range, Java creates a new object in Heap memory. Comparing them using `==` returns `false`, even if they represent the same numeric value!

```java
Integer a = 100;
Integer b = 100;
System.out.println(a == b); // true (referenced from cache)

Integer x = 200;
Integer y = 200;
System.out.println(x == y); // false (different Heap addresses!)
System.out.println(x.equals(y)); // true (compares values correctly)
```
> [!IMPORTANT]
> **Always use `.equals()` to compare wrapper objects for equality.** Never use `==` unless you are explicitly checking if they point to the exact same memory address.

---

## Code Example: Wrapper Operations and Pitfalls
The following Java program demonstrates Autoboxing, the Integer Cache comparison pitfall, safe parsing operations, and the Null pointer crash:

```java
public class WrapperDemo {
    public static void main(String[] args) {
        System.out.println("=== 1. Autoboxing & Unboxing ===");
        Integer boxedVal = 42; // Autoboxing: primitive to Integer object
        int unboxedVal = boxedVal; // Unboxing: object to primitive int
        System.out.println("Boxed: " + boxedVal + ", Unboxed: " + unboxedVal);

        System.out.println("\n=== 2. The Integer Cache Pitfall ===");
        Integer val1 = 120;
        Integer val2 = 120;
        System.out.println("Comparing 120 using ==: " + (val1 == val2)); // true (cached)

        Integer val3 = 150;
        Integer val4 = 150;
        System.out.println("Comparing 150 using ==: " + (val3 == val4)); // false (not cached!)
        System.out.println("Comparing 150 using .equals(): " + val3.equals(val4)); // true

        System.out.println("\n=== 3. Utility String Parsing ===");
        String numberString = "250";
        // Parse converts string to primitive int
        int parsedInt = Integer.parseInt(numberString);
        // ValueOf converts string to boxed Integer object
        Integer parsedObj = Integer.valueOf(numberString);
        System.out.println("Parsed primitive: " + parsedInt + ", Parsed Object: " + parsedObj);

        System.out.println("\n=== 4. Null Unboxing Danger ===");
        try {
            Integer score = null;
            System.out.println("Attempting to unbox null score...");
            int finalScore = score; // Implicit unboxing: score.intValue()
            System.out.println("Final Score: " + finalScore);
        } catch (NullPointerException e) {
            System.out.println("[CRASH PREVENTED] Caught NullPointerException during unboxing!");
        }
    }
}
```

---

## Summary
- **Wrapper classes** (like `Integer`, `Double`) wrap Stack primitives into Heap-based objects.
- **Autoboxing** is the automatic boxing of primitives into wrappers; **Unboxing** is the retrieval of primitives from wrappers.
- Unboxing a `null` wrapper reference throws a **`NullPointerException`**.
- Java caches `Integer` objects from **`-128` to `127`**. Always compare wrappers using **`.equals()`** to avoid cache comparison bugs.
- Use static helper methods like **`Integer.parseInt()`** to parse string values.

---

## Additional Resources
- [Autoboxing and Unboxing - Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html)
- [Java Wrapper Classes Reference - W3Schools](https://www.w3schools.com/java/java_wrapper_classes.asp)
- [Understanding the Java Integer Cache - Baeldung](https://www.baeldung.com/java-integer-cache-default-value)