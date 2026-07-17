# Mathematical Operators in Java

## Learning Objectives
- Perform basic arithmetic using addition (`+`), subtraction (`-`), multiplication (`*`), division (`/`), and modulo (`%`).
- Analyze and control the difference between integer division and floating-point division.
- Apply compound assignment operators (`+=`, `-=`, etc.) to streamline arithmetic code.
- Differentiate between prefix and postfix behavior for increment (`++`) and decrement (`--`) operators.
- Determine operator precedence (order of operations) in complex expressions.

---

## Why This Matters
Computers are, at their core, calculation engines. Whether you are computing interest on a bank account, incrementing a loop counter, finding the middle index of an array, or checking if a number is even or odd, you will use **mathematical operators**. 

Understanding how these operators interact with different data types is vital. A simple misunderstanding of how Java handles dividing two integers can lead to bugs where numbers are truncated to zero, causing calculation errors that are hard to trace.

---

## The Concept

### 1. Basic Arithmetic Operators
Java provides five primary operators for basic arithmetic:

| Operator | Operation | Description | Example |
| :---: | :--- | :--- | :--- |
| **`+`** | Addition | Adds two values. (Also used for String concatenation). | `5 + 3` -> `8` |
| **`-`** | Subtraction | Subtracts one value from another. | `5 - 3` -> `2` |
| **`*`** | Multiplication | Multiplies two values. | `5 * 3` -> `15` |
| **`/`** | Division | Divides one value by another. | `6 / 2` -> `3` |
| **`%`** | Modulo (Remainder) | Returns the remainder left over after integer division. | `5 % 2` -> `1` |

---

### Integer Division vs. Floating-Point Division
This is one of the most common pitfalls for beginners in Java.

#### Integer Division
If you divide two integers, Java performs integer division. The result is always another integer. **The decimal portion is truncated (discarded)**; it does *not* round up or down.
```java
int result = 5 / 2; // result is 2 (the .5 is completely thrown away)
```

#### Floating-Point Division
If at least one of the operands is a floating-point number (`double` or `float`), Java performs decimal division, keeping the precision:
```java
double result1 = 5.0 / 2;   // result1 is 2.5 (5.0 is a double literal)
double result2 = (double) 5 / 2; // result2 is 2.5 (casts 5 to double first)
```

---

### 2. Modulo Operator (`%`)
The modulo operator returns the remainder of a division. It is incredibly useful for:
- Checking if a number is even or odd (`number % 2 == 0` is even).
- Checking divisibility or multiples (e.g., checking if a year is a leap year).
- Restricting numbers to a specific range (e.g., array circular buffers).

```java
int remainder1 = 10 % 3; // 1 (10 divided by 3 is 3, with a remainder of 1)
int remainder2 = 15 % 5; // 0 (15 is perfectly divisible by 5)
```

---

### 3. Increment and Decrement Operators (`++`, `--`)
These operators increase or decrease a variable's value by 1. They can be written in two ways:

#### A. Postfix (`x++` or `x--`)
The expression evaluates to the **original** value first, and *then* the variable is incremented/decremented in memory.
```java
int x = 5;
int y = x++; // y gets 5 (original value), then x becomes 6
System.out.println("x: " + x + ", y: " + y); // Prints x: 6, y: 5
```

#### B. Prefix (`++x` or `--x`)
The variable is incremented/decremented in memory first, and *then* the expression evaluates to the **new** value.
```java
int x = 5;
int y = ++x; // x becomes 6, then y gets the new value (6)
System.out.println("x: " + x + ", y: " + y); // Prints x: 6, y: 6
```

---

### 4. Compound Assignment Operators
Compound assignment operators combine an arithmetic operation with variable assignment to write cleaner, more readable code:

- `x += y` is equivalent to `x = x + y`
- `x -= y` is equivalent to `x = x - y`
- `x *= y` is equivalent to `x = x * y`
- `x /= y` is equivalent to `x = x / y`
- `x %= y` is equivalent to `x = x % y`

---

### 5. Operator Precedence
When combining multiple operators, Java follows a strict order of operations, similar to standard algebra (PEMDAS):

1. Parentheses `()` (Highest precedence)
2. Postfix increment/decrement `x++`, `x--`
3. Prefix increment/decrement, unary operators `++x`, `--x`, `+`, `-`
4. Multiplicative `*`, `/`, `%`
5. Additive `+`, `-`

```java
int result = 5 + 3 * 2;   // result is 11 (multiplication happens first)
int result2 = (5 + 3) * 2; // result2 is 16 (parentheses happen first)
```

---

## Code Example
The following class integrates all mathematical concepts covered:

```java
public class MathOperatorsDemo {
    public static void main(String[] args) {
        // 1. Division Pitfall & Modulo
        int a = 7;
        int b = 3;
        
        System.out.println("Integer Division (7 / 3): " + (a / b));   // Prints 2
        System.out.println("Modulo (7 % 3): " + (a % b));               // Prints 1
        System.out.println("Double Division (7.0 / 3): " + (7.0 / b)); // Prints 2.3333333333333335
        
        // Even/Odd check
        int checkNum = 42;
        boolean isEven = (checkNum % 2 == 0);
        System.out.println("Is " + checkNum + " even? " + isEven);

        // 2. Compound Assignment
        int total = 100;
        total += 50; // total is now 150
        total *= 2;  // total is now 300
        System.out.println("Total: " + total);

        // 3. Prefix vs Postfix
        int counter = 10;
        System.out.println("Postfix: " + (counter++)); // Prints 10, but counter is now 11
        System.out.println("Counter now: " + counter); // Prints 11

        System.out.println("Prefix: " + (++counter));  // Counter becomes 12, prints 12
    }
}
```

---

## Summary
- Basic math operators in Java are `+`, `-`, `*`, `/`, and `%`.
- **Integer Division** discards any decimal remainder (truncation). To keep decimal precision, cast at least one operand to `double` or `float`.
- **Modulo (`%`)** calculates the division remainder and is widely used for divisibility checks.
- **Postfix (`x++`)** uses the value first, then increments. **Prefix (`++x`)** increments first, then uses the value.
- **Compound assignments** (e.g. `+=`) are short-hand notations for self-reassignment operations.

---

## Additional Resources
- [Java Operators Reference - W3Schools](https://www.w3schools.com/java/java_operators.asp)
- [Arithmetic in Java - GeeksforGeeks](https://www.geeksforgeeks.org/operators-in-java/)
- [Java Operator Precedence - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html)
