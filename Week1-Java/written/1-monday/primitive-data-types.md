# Primitive Data Types in Java

## Learning Objectives
- Define what a primitive data type is and how it differs from other data structures.
- Identify the 8 primitive data types in Java: their size, range, and default values.
- Declare, initialize, and use variables for each primitive type.
- Explain the difference between implicit (widening) and explicit (narrowing) type casting.
- Predict and avoid runtime issues like integer overflow and precision loss.

---

## Why This Matters
Java is a **strongly-typed** programming language. This means every variable you declare must have a predefined type, and the compiler strictly enforces that you only store values matching that type. 

At the foundation of Java's type system are the **8 Primitive Data Types**. Unlike complex objects, primitives are stored directly in the fastest part of system memory (the Stack) rather than on the Heap. They represent raw values (numbers, letters, truths) without any object-oriented overhead. Knowing when to use a lightweight `int` versus a high-precision `double` or a small `byte` is essential for writing memory-efficient and high-performance applications.

---

## The Concept

### The 8 Primitive Types
Java defines exactly eight primitive data types. They are pre-defined by the language and named by reserved keywords:

| Type | Category | Size (Bytes) | Size (Bits) | Range of Values | Default Value | Example Literal |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| `byte` | Integer | 1 | 8 | -128 to 127 | `0` | `(byte) 42` |
| `short` | Integer | 2 | 16 | -32,768 to 32,767 | `0` | `(short) 1000` |
| `int` | Integer | 4 | 32 | -2,147,483,648 to 2,147,483,647 | `0` | `100000` |
| `long` | Integer | 8 | 64 | -9.22 x 10¹⁸ to 9.22 x 10¹⁸ | `0L` | `5000000000L` |
| `float` | Floating-Pt | 4 | 32 | ~1.4 x 10⁻⁴⁵ to 3.4 x 10³⁸ (6-7 decimal digits) | `0.0f` | `3.14f` |
| `double` | Floating-Pt | 8 | 64 | ~4.9 x 10⁻³²⁴ to 1.7 x 10³⁰⁸ (15-16 decimal digits) | `0.0d` | `3.1415926535` |
| `char` | Character | 2 | 16 | Single Unicode character (`\u0000` to `\uffff`) | `\u0000` (null) | `'A'` or `'\u0041'` |
| `boolean`| Logical | *Varies | 1 | `true` or `false` | `false` | `true` |

*\*Note: The size of a boolean is not explicitly defined in the Java Virtual Machine specification, but it is typically represented by 1 byte at runtime, or an int in arrays.*

---

### Detailed Review of Primitives

#### 1. Integer Types (`byte`, `short`, `int`, `long`)
- **`int`**: The default integer type in Java. Unless you have a specific reason, use `int` for counting, looping, and general numeric math.
- **`long`**: Used when values exceed the limits of `int` (e.g., millisecond timestamps, national IDs, database primary keys). You **must** append an `L` (or lowercase `l`, though uppercase is preferred to avoid confusion with the number `1`) to the literal value:
  ```java
  long worldPopulation = 8000000000L;
  ```
- **`byte` & `short`**: Used primarily to save memory in large arrays or when interfacing with low-level network packets or files.

#### 2. Floating-Point Types (`float`, `double`)
Used for decimals.
- **`double`**: The default type for decimal numbers. It has double-precision (64-bit), making it much more accurate for mathematical calculations.
- **`float`**: Has single-precision (32-bit). You **must** append an `f` or `F` to the literal value:
  ```java
  float temperature = 98.6f;
  ```
> [!WARNING]
> Floating-point numbers are represented in binary scientific notation, which cannot represent all decimal values exactly (e.g., `0.1` has a repeating binary representation). Therefore, **never use float or double for financial or precise monetary calculations**. For currency, use integers representing cents (e.g., `100` cents instead of `$1.00`), or class tools designed for high precision (which we will introduce later).

#### 3. Character (`char`)
Represents a single 16-bit Unicode character enclosed in single quotes. It can store letters, digits, punctuation, or escape sequences:
```java
char grade = 'A';
char newline = '\n';
```

#### 4. Boolean (`boolean`)
Can only hold one of two values: `true` or `false`. Used for conditional logic and flags.

---

### Type Casting (Conversion)
Casting is the process of converting a variable of one data type into another.

#### 1. Widening Cast (Implicit Casting)
Occurs automatically when you convert a smaller data type to a larger data type. Because the target type has a larger memory size, there is no risk of losing data.
*   **Direction**: `byte` -> `short` -> `char` -> `int` -> `long` -> `float` -> `double`
```java
int myInt = 9;
double myDouble = myInt; // Automatic casting: int to double (value becomes 9.0)
```

#### 2. Narrowing Cast (Explicit Casting)
Must be done manually by placing the target type in parentheses in front of the value. Since you are squeezing a larger value into a smaller memory container, data loss or overflow can occur.
```java
double pi = 3.14159;
int truncatedPi = (int) pi; // Explicit casting: double to int (value becomes 3, decimal part is lost)
```

#### Integer Overflow/Underflow Pitfall
If you cast a number that is too large for the destination type, the value wraps around according to binary limits:
```java
int largeNumber = 130;
byte squeezedByte = (byte) largeNumber; 
System.out.println(squeezedByte); // Outputs -126 (overflowed the max 127 byte limit!)
```

---

## Code Example
Here is a comprehensive script demonstrating primitive declarations, literals, and casting:

```java
public class PrimitivesDemo {
    public static void main(String[] args) {
        // 1. Declarations & Literals
        byte age = 25;
        short score = 32000;
        int population = 150000000;
        long distanceToStar = 9460730472580L; // Note the 'L'
        
        float price = 19.99f;                 // Note the 'f'
        double gravity = 9.80665;
        
        char initial = 'J';
        boolean isJavaFun = true;

        // Printing values
        System.out.println("Integer value: " + population);
        System.out.println("Long value: " + distanceToStar);
        System.out.println("Float value: " + price);

        // 2. Widening (Implicit) Casting
        int smallVal = 100;
        double bigVal = smallVal; // Automatic
        System.out.println("Implicit double: " + bigVal); // Prints 100.0

        // 3. Narrowing (Explicit) Casting
        double exactRate = 85.99;
        int wholeRate = (int) exactRate; // Truncates decimal (.99 is lost)
        System.out.println("Explicit int: " + wholeRate); // Prints 85
    }
}
```

---

## Summary
- Java's type system has 8 primitives: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, and `boolean`.
- Primitives store raw values directly on the Stack, making them memory-efficient.
- Floating-point types (`float` and `double`) are subject to precision limits and should not be used for currency.
- **Implicit casting** (widening) occurs automatically when moving to a larger container.
- **Explicit casting** (narrowing) requires syntax like `(type) value` and can result in truncation or numeric overflow.

---

## Additional Resources
- [Java Primitives - Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html)
- [Primitive Data Types - W3Schools](https://www.w3schools.com/java/java_data_types.asp)
- [Java Castings and Conversions - Baeldung](https://www.baeldung.com/java-type-casting)
