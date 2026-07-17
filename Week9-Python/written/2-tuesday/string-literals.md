# String Literals and Manipulation in Python

## Learning Objectives
- Differentiate between single, double, and triple-quoted string literals in Python.
- Apply raw strings (`r""`) for regular expressions and file paths.
- Master string formatting using **f-strings** (PEP 498) and the `.format()` method.
- Perform common string manipulations using built-in string methods.
- Explain the concept of string **immutability** and its performance implications.

---

## Why This Matters
Text processing is one of the most common tasks in software engineering. Whether parsing config files, handling JSON API payloads, or analyzing log files, you need a robust understanding of string manipulation.

In Java, strings are represented by the `String` class and formatting is often done using standard concatenation (`+`) or helper classes like `StringBuilder` or `String.format()`. Python treats strings as first-class sequences, offering elegant syntax like **f-strings** (formatted string literals) for interpolation, triple quotes for multi-line blocks, and a vast collection of built-in methods. Understanding how Python handles strings under the hood—including their immutability—will help you write clean, memory-efficient data processing pipelines.

---

## The Concept

### 1. Types of String Literals
Python allows you to define string literals in several ways:
*   **Single (`'`) and Double (`"`) Quotes:** These are functionally identical in Python. They allow you to define single-line strings. Having both makes it easy to embed quotes inside a string without escaping:
    - `message = 'He said, "Hello"'`
*   **Triple Quotes (`'''` or `"""`):** Used for defining multi-line strings. They preserve newlines and whitespace exactly as typed. They are also used for writing docstrings (documentation headers for functions/classes).
*   **Raw Strings (`r"..."` or `r'...'`):** Prefixing a string with `r` disables escape sequence processing (like `\n` or `\t`). Extremely useful for windows file paths and regex patterns where backslashes are common.

### 2. String Interpolation (f-strings vs. `.format()`)
For years, Python used C-style `%` formatting and the `.format()` method. 
Introduced in PEP 498, **f-strings** (formatted string literals) are the modern industry standard:
*   They are defined by prefixing the string with `f` (e.g., `f"Hello {name}"`).
*   Any valid Python expression can be placed inside curly braces `{}` and is evaluated at runtime.
*   f-strings are faster because they are evaluated as part of the bytecode compilation rather than a function call.

### 3. String Immutability
Just like in Java, Python strings are **immutable**. Once a string object is created in memory, it cannot be modified:
```python
name = "python"
# name[0] = "P"  # Throws TypeError: 'str' object does not support item assignment
```
Every time you manipulate a string (e.g., calling `.upper()` or concatenation), Python allocates a new string object in memory.
*   *Optimization Tip:* To build a large string from multiple components, avoid looping with `+` (which creates multiple intermediate objects). Instead, collect elements in a list and use the `"".join(list)` method, which allocates memory once.

### 4. Built-in String Methods
Python provides a rich array of utility methods on string objects. Some of the most frequently used include:
*   `.strip()`, `.lstrip()`, `.rstrip()`: Removes leading and trailing whitespace.
*   `.split(separator)`: Splits a string into a list of substrings.
*   `.join(iterable)`: Joins elements of an iterable (like a list) into a single string.
*   `.upper()`, `.lower()`, `.title()`: Case modification.
*   `.replace(old, new)`: Replaces occurrences of a substring.
*   `.startswith(prefix)`, `.endswith(suffix)`: Boolean check for boundary matching.

---

## Code Examples

### 1. Literal Declaration and Raw Strings
```python
# Triple-quoted multi-line string
query = """
SELECT id, name, salary
FROM employees
WHERE department = 'Engineering';
"""

# Windows path comparison (Standard vs. Raw)
standard_path = "C:\\Users\\workspace\\project"  # Double backslash required
raw_path = r"C:\Users\workspace\project"         # Clean raw string
```

### 2. String Interpolation Methods
```python
user = "Alice"
score = 95.823

# f-strings (Modern & Preferred)
print(f"User {user} scored {score:.1f}%")  # Output: User Alice scored 95.8%

# .format() (Older style, useful in dictionaries or templates)
print("User {} scored {:.1f}%".format(user, score))
```

### 3. Basic String Parsing and Manipulation
```python
csv_data = "  johndoe,35,Developer,New York  "

# 1. Clean whitespace
cleaned_data = csv_data.strip()

# 2. Split into elements
fields = cleaned_data.split(",")
print(fields)  # Output: ['johndoe', '35', 'Developer', 'New York']

# 3. Modify text and join back
fields[0] = fields[0].upper()
final_csv = "|".join(fields)
print(final_csv)  # Output: JOHNDOE|35|Developer|New York
```

---

## Summary
- Python strings can be defined using `'`, `"`, `"""` (multi-line), or `r""` (raw strings).
- **f-strings** are the preferred tool for string interpolation due to their readability, efficiency, and flexibility.
- Strings are **immutable**. Operations that modify strings allocate new objects. Use `"".join()` for efficient concatenations.
- Built-in methods like `.strip()`, `.split()`, and `.join()` provide standard string utility functions.

---

## Additional Resources
- [PEP 498: Formatted String Literals Specification](https://peps.python.org/pep-0498/)
- [Real Python: Python String Formatting Best Practices](https://realpython.com/python-string-formatting/)
- [W3Schools: Python String Methods Reference](https://www.w3schools.com/python/python_ref_string.asp)
