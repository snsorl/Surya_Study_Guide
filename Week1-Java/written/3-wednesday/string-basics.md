# String Basics: Immutability and the String Pool

## Learning Objectives
- Explain the concept and design benefits of **String Immutability** in Java.
- Describe how the JVM utilizes the **String Constant Pool** to optimize memory.
- Compare Strings safely using value equality (`.equals()`) instead of reference identity (`==`).
- Utilize core String methods (length, charAt, substring, strip, split) to parse and format text.
- Differentiate between the performance of String concatenation and `StringBuilder` in loops.

---

## Why This Matters
Text processing is at the heart of almost every application. Whether you are validating a user's password, parsing database product codes, or reading JSON request headers, you will constantly interact with the `String` class. 

However, Java manages Strings differently than any other object. Because Strings are so common, Java optimizes memory usage by storing them in a shared cache called the **String Constant Pool** and making them **immutable** (unchangeable). If you do not understand these optimizations, you will write memory-heavy code that runs slowly, or introduce logic bugs where two identical text strings are evaluated as "unequal" because you compared them using `==`.

---

## The Concept

### 1. String Immutability
In Java, **Strings are immutable**. Once a String object is created on the Heap, its character sequence can never be modified. 

When you invoke a method that seems to change a String—like `.toUpperCase()` or `.replace()`—the JVM does *not* modify the original characters. Instead, it allocates a **brand-new String object** on the Heap with the updated characters and returns its address. If you do not capture this new address, the modification is lost.

```java
String greeting = "Hello";
greeting.concat(" World"); // Modifies nothing! Brand-new string was discarded.
System.out.println(greeting); // Prints "Hello"

// Correct way: capture the returned address pointer
greeting = greeting.concat(" World");
System.out.println(greeting); // Prints "Hello World"
```

#### Concatenation in Loops (Performance Risk)
Because Strings are immutable, concatenating strings using `+` inside a loop is extremely inefficient. Each loop iteration creates a new temporary String object on the Heap. For large loops, this creates thousands of short-lived objects, degrading application speed. To build mutable strings efficiently, use **`StringBuilder`**.

---

### 2. The String Constant Pool
To conserve Heap memory, the JVM maintains a special cache region called the **String Constant Pool**.

*   **String Literals**: When you declare a string using double quotes (e.g. `String s1 = "Java"`), the JVM searches the Pool. If `"Java"` already exists, s1 points directly to the cached object. If it doesn't exist, the JVM creates it in the pool.
*   **The `new` Keyword**: If you write `String s2 = new String("Java")`, you instruct the JVM to bypass the pool. It allocates a completely new, duplicate String object in general Heap memory.

```
String Pool (Heap)
[ "Java" (0x505) ] <=== s1 (0x505)
                   <=== s3 (0x505) (literal matched)
General Heap
[ "Java" (0x999) ] <=== s2 (0x999) (explicit 'new')
```

---

### 3. Comparing Strings: `==` vs. `.equals()`
*   **`==` (Reference Identity)**: Checks if both variables point to the **exact same memory address**.
*   **`.equals()` (Value Equality)**: Checks if both variables contain the **exact same character sequence**, regardless of where they reside in memory.

Because literals are cached in the Pool, `s1 == s3` returns `true`. However, because `s2` is in general Heap, `s1 == s2` returns `false`, even though both contain `"Java"`. 
> [!IMPORTANT]
> **Always use `.equals()` (or `.equalsIgnoreCase()`) to compare strings.** Never use `==` for user login validation, database checks, or string inputs.

---

## Code Example: String Mechanics and Performance
The following program demonstrates String immutability updates, pool comparison differences, core string parsing methods, and compares loop concatenation with `StringBuilder`:

```java
public class StringDemo {
    public static void main(String[] args) {
        System.out.println("=== 1. String Immutability Demo ===");
        String base = "Java";
        System.out.println("Original string: " + base);
        
        // Appending text creates a new object; base remains unchanged
        String result = base.concat(" SE 17"); 
        System.out.println("Original post-concat: " + base); // "Java"
        System.out.println("Captured result:      " + result); // "Java SE 17"

        System.out.println("\n=== 2. String Pool: == vs .equals() ===");
        String s1 = "Infosys"; // Allocated in String Pool
        String s2 = "Infosys"; // Reuses reference from Pool
        String s3 = new String("Infosys"); // Explicit new object in General Heap

        System.out.println("s1 == s2 (Literals check):       " + (s1 == s2)); // true
        System.out.println("s1 == s3 (Literal vs New check): " + (s1 == s3)); // false (different locations!)
        System.out.println("s1.equals(s3) (Content check):   " + s1.equals(s3)); // true (same letters)

        System.out.println("\n=== 3. Core String Parsing Methods ===");
        String dataLine = "  USR-101,John Doe,Engineering  ";
        
        // A. strip() removes leading and trailing whitespace
        String cleanData = dataLine.strip();
        System.out.println("Cleaned data: '" + cleanData + "'");

        // B. split() parses strings into arrays based on regex
        String[] fields = cleanData.split(",");
        System.out.println("Parsed ID: " + fields[0]);
        System.out.println("Parsed Name: " + fields[1]);

        // C. substring() extracts character ranges (inclusive start, exclusive end)
        String userCode = fields[0].substring(4); // Extracts index 4 to end
        System.out.println("Extracted User Code: " + userCode); // "101"

        System.out.println("\n=== 4. Loop Performance: String vs StringBuilder ===");
        int iterations = 10000;

        // Inefficient: creates 10,000 intermediate string objects
        long startTime = System.currentTimeMillis();
        String tempStr = "";
        for (int i = 0; i < iterations; i++) {
            tempStr += "a";
        }
        long endTime = System.currentTimeMillis();
        System.out.println("String Loop time: " + (endTime - startTime) + "ms");

        // Efficient: mutates a single buffer in memory
        startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("a");
        }
        String finalStr = sb.toString();
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder Loop time: " + (endTime - startTime) + "ms");
    }
}
```

---

## Summary
- Java Strings are **immutable** (cannot be changed in memory). Operations return new objects.
- The **String Constant Pool** caches string literals to save Heap memory.
- **`==`** compares memory addresses; **`.equals()`** compares character values. Always use `.equals()` for string validations.
- Standard string methods like **`strip()`**, **`split()`**, and **`substring()`** allow safe parsing.
- Use **`StringBuilder`** instead of String concatenation (`+`) inside loops to prevent memory allocation bottlenecks.

---

## Additional Resources
- [String Class Documentation - Oracle Java SE Docs](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html)
- [Guide to the Java String Pool - Baeldung](https://www.baeldung.com/java-string-pool)
- [StringBuilder vs. String Performance - GeeksforGeeks](https://www.geeksforgeeks.org/string-vs-stringbuilder-vs-stringbuffer-in-java/)