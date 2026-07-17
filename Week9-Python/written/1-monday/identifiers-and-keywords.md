# Identifiers, Keywords, and Coding Standards (PEP 8)

## Learning Objectives
- Define the rules for creating valid identifiers in Python.
- Apply PEP 8 naming conventions across variables, functions, classes, and constants.
- Identify the set of Python reserved keywords that cannot be used as identifiers.
- Explain the risks of shadowing built-in Python functions and names.
- Interpret the meaning of single and double leading underscores in identifiers.

---

## Why This Matters
In software engineering, readability is paramount. Code is read far more often than it is written. Every language has syntactic rules for names (identifiers) and conventions that developers follow to collaborate effectively.

In Python, the official style guide is **PEP 8**. Adhering to PEP 8 standards makes your code immediately understandable to any Python developer. Additionally, since Python dynamically binds names, knowing the language's reserved keywords and standard built-ins prevents hard-to-debug shadowing errors—where you accidentally redefine standard library functions like `print` or `list` inside your local scopes.

---

## The Concept

### 1. Rules for Valid Identifiers
An **identifier** is a name given to entities like variables, functions, classes, and modules. Python identifiers must adhere to these syntax rules:
*   Can contain letters (`A-Z`, `a-z`), digits (`0-9`), and underscores (`_`).
*   **Cannot start with a digit** (e.g., `1variable` is invalid).
*   Are **case-sensitive** (`myVar`, `myvar`, and `MYVAR` are three distinct identifiers).
*   Cannot contain spaces or special symbols like `@`, `$`, `%`, etc.
*   Cannot be a **reserved keyword** (like `def` or `if`).

### 2. PEP 8 Naming Conventions
PEP 8 is the standard coding style guide for Python. It recommends the following naming patterns:

*   **Variables, Attributes, and Functions:** Use **`snake_case`** (lowercase words separated by underscores).
    *   *Example:* `student_name`, `calculate_total_price()`, `db_connection`
*   **Classes:** Use **`PascalCase`** (each word capitalized, no separators).
    *   *Example:* `DatabaseConnector`, `TraineeService`
*   **Constants:** Use **`UPPERCASE_SNAKE`** (all caps with underscores).
    *   *Example:* `MAX_RETRY_COUNT`, `DEFAULT_TIMEOUT`
*   **Modules and Packages:** Use short, all-lowercase names. Underscores can be used if they improve readability.
    *   *Example:* `json_parser.py`

### 3. Underscore Prefixes (Special Meaning)
Python uses underscores to indicate access control and scope behavior:
*   **`_single_leading_underscore` (e.g., `_internal_method`):** A warning to developers that this is a private implementation detail. It is not enforced by the language, but it tells others "use at your own risk."
*   **`__double_leading_underscore` (e.g., `__hidden_attribute`):** Triggers **name mangling**, where the compiler automatically changes the name to prevent subclass namespace collisions (e.g., `_ClassName__hidden_attribute`).

### 4. Reserved Keywords
Python has 35 reserved keywords (as of Python 3.12). You cannot use these as variable, function, or class names:

```text
False      class      finally    is         return
None       continue   for        lambda     try
True       def        from       nonlocal   while
and        del        global     not        with
as         elif       if         or         yield
assert     else       import     pass
async      except     in         raise
await
```

### 5. Shadowing Built-ins (Common Pitfall)
Python allows you to reassign names that belong to built-in functions. Doing so overrides the function for that scope.
For example, if you declare `list = [1, 2, 3]`, you can no longer use the standard constructor function `list()` (e.g., `list("abc")` will throw a `TypeError: 'list' object is not callable`). 

---

## Code Examples

### 1. Correct vs. Incorrect PEP 8 Style

```python
# --- AVOID (Java-influenced camelCase and poor style) ---
class dbConnection:
    def connectToDatabase(self, connectionUrl):
        PORT_NUMBER = 5432
        print(f"Connecting to {connectionUrl} on {PORT_NUMBER}")

# --- RECOMMENDED (PEP 8 Compliant) ---
class DbConnection:
    PORT_NUMBER = 5432  # Class-level constant
    
    def connect_to_database(self, connection_url):
        print(f"Connecting to {connection_url} on {self.PORT_NUMBER}")
```

### 2. Built-in Shadowing Bug

```python
# Bug Scenario: Shadowing the built-in 'sum' function
def process_data(items):
    # Shadowing the built-in sum() with a variable
    sum = 0
    for item in items:
        sum += item
        
    # Later in the code, trying to use the built-in sum()
    other_items = [10, 20, 30]
    total = sum(other_items)  # Throws TypeError: 'int' object is not callable
    return total
```
*Correction:* Change the variable name `sum` to `total_sum` or `sum_of_items` to avoid overriding the built-in function name.

---

## Summary
- **Identifiers** must start with a letter or underscore, and are case-sensitive.
- **PEP 8 Conventions**: `snake_case` for variables and functions; `PascalCase` for classes; `UPPERCASE_SNAKE` for constants.
- **Underscores**: `_` represents internal/private conventions, and `__` triggers class name mangling.
- **Keywords** represent the syntax grammar of Python and cannot be redefined.
- **Built-in shadowing** occurs when variable names overwrite built-in function names like `sum` or `type`, causing runtime errors when those functions are invoked later.

---

## Additional Resources
- [PEP 8: Style Guide for Python Code](https://peps.python.org/pep-0008/)
- [Python Documentation: Lexical Analysis - Identifiers and Keywords](https://docs.python.org/3/reference/lexical_analysis.html#identifiers)
- [Real Python: Defining Variables and Data Types in Python](https://realpython.com/python-variables/)
