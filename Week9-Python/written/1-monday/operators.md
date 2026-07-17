# Python Operators: Arithmetic, Logical, Comparison, and Expressions

## Learning Objectives
- Master Python's arithmetic operators, focusing on the differences in division operations and exponentiation.
- Contrast value equality (`==`) with reference identity (`is`).
- Implement membership operators (`in`, `not in`) in conditions.
- Apply logical operators (`and`, `or`, `not`) and leverage short-circuit evaluation.
- Utilize bitwise operators and basic compound assignment operators.
- Write assignment expressions using the **Walrus operator** (`:=`).

---

## Why This Matters
Operators are the fundamental building blocks of expression evaluation in any programming language. While Python shares many operators with Java, it introduces specific syntax and operational behaviors that can surprise Java developers.

For instance, in Java, integer division like `5 / 2` yields `2` because fractional parts are truncated. In Python, `5 / 2` yields `2.5`. Python introduces a separate operator `//` for floor division. Python also lacks the increment/decrement operators (`i++`, `--i`) found in Java. Additionally, Python's identity checks (`is`) and assignment expressions (`:=`) have specific memory management and performance implications that are crucial for writing clean, optimized algorithms.

---

## The Concept

### 1. Arithmetic Operators
Python supports standard arithmetic, with distinct features:
*   **Division (`/`):** Always returns a floating-point number, even if the division is clean (e.g., `4 / 2` is `2.0`).
*   **Floor Division (`//`):** Divides and rounds down to the nearest integer (e.g., `5 // 2` is `2`, and `-5 // 2` is `-3`).
*   **Exponentiation (`**`):** Raises a base to a power (e.g., `2 ** 3` is `8`). This replaces Java's requirement to use `Math.pow()`.
*   **Modulo (`%`):** Returns the remainder of division.

### 2. Comparison and Reference Operators
Python splits comparison into value checking and reference checking:
*   **Value Comparison (`==`, `!=`, `<`, `>`, `<=`, `>=`):** Compares the *values* of the operands.
*   **Identity Comparison (`is`, `is not`):** Compares the *memory addresses* of the operands. It checks if both names point to the exact same object in memory. Equivalent to checking `id(a) == id(b)`.
    - *Java equivalence:* `a == b` for object references in Java is equivalent to `a is b` in Python. `a.equals(b)` in Java is equivalent to `a == b` in Python.
*   **Membership Operators (`in`, `not in`):** Returns `True` if a value exists within a sequence (string, list, tuple, set, or dictionary keys).

### 3. Logical Operators
Instead of symbols like `&&`, `||`, and `!`, Python uses readable english keywords:
*   **`and`**: Returns `True` if both expressions are true. Supports **short-circuit evaluation** (if the first expression is `False`, the second is not evaluated).
*   **`or`**: Returns `True` if at least one expression is true. Supports short-circuiting.
*   **`not`**: Reverses the boolean state of the expression.

### 4. Assignment and the Walrus Operator (`:=`)
Python supports standard compound assignments (e.g., `+=`, `-=`, `*=`, `/=`). However, Python **does not support increment/decrement operators (`++` or `--`)**. Instead, use `x += 1`.

#### The Walrus Operator (`:=`)
Introduced in PEP 572, the assignment expression operator (`:=`) allows you to assign a value to a variable *inside* an expression. This is useful for writing concise code in loop headers or conditional checks where you need to check a value and store it simultaneously.

---

## Code Examples

### 1. Division, Exponentiation, and Incrementing
```python
# Division differences
print(5 / 2)   # Output: 2.5 (Float Division)
print(5 // 2)  # Output: 2   (Floor Division)
print(-5 // 2) # Output: -3  (Floor Division rounds down)

# Exponentiation
print(3 ** 4)  # Output: 81  (3 raised to the power of 4)

# Incrementing (No ++ exists!)
count = 0
count += 1     # Proper Python syntax
# count++      # SyntaxError!
```

### 2. Equality vs. Identity (`==` vs. `is`)
```python
# Create two lists with the same contents
list_a = [1, 2, 3]
list_b = [1, 2, 3]
list_c = list_a

# Value comparison
print(list_a == list_b)  # Output: True (Their values are identical)

# Identity comparison
print(list_a is list_b)   # Output: False (They point to different objects in memory)
print(list_a is list_c)   # Output: True (They point to the exact same memory location)
```

### 3. Membership Checking
```python
message = "Welcome to Python training"
print("Python" in message)      # Output: True
print("Java" not in message)    # Output: True

user_roles = ["admin", "editor", "analyst"]
print("guest" in user_roles)    # Output: False
```

### 4. The Walrus Operator in Action
Without the walrus operator, you assign a value, then run a conditional check:
```python
# Standard approach
input_string = "Hello World"
length = len(input_string)
if length > 5:
    print(f"String is too long: {length} characters")
```

With the walrus operator, you perform both actions in a single line:
```python
# Walrus operator approach
input_string = "Hello World"
if (length := len(input_string)) > 5:
    print(f"String is too long: {length} characters")
```

---

## Summary
- **`/`** always outputs a float, while **`//`** truncates decimal fractions to the next lowest integer.
- **`**`** represents mathematical exponentiation (power).
- **`==`** compares object values, whereas **`is`** checks if two variables point to the exact same object reference in memory.
- **`in` / `not in`** evaluate membership within collections and sequences.
- **`and` / `or` / `not`** are the logical operators, replacing Java's symbols `&&`, `||`, and `!`.
- **`:=` (Walrus)** assigns values to variables as part of an expression evaluation.

---

## Additional Resources
- [PEP 572: Assignment Expressions (The Walrus Operator)](https://peps.python.org/pep-0572/)
- [Real Python: Operators and Expressions in Python](https://realpython.com/python-operators-expressions/)
- [Real Python: Python 'is' vs '==' - Difference Explained](https://realpython.com/python-is-identity-vs-equality/)
