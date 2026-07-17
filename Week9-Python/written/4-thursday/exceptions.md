# Exception Handling and Custom Exceptions in Python

## Learning Objectives
- Design robust error handling structures using `try`, `except`, `else`, and `finally`.
- Differentiate between catching specific exceptions and the risk of broad exception clauses.
- Raise built-in and custom exceptions using the `raise` keyword.
- Create custom exception classes by inheriting from Python's base `Exception` class.
- Apply **exception chaining** using `raise ... from ...` to preserve stack traces.
- Compare Python's exception hierarchy and styling to Java's check/unchecked paradigms.

---

## Why This Matters
Errors are inevitable in software engineering. Network requests fail, databases go offline, and users submit unexpected input. Without structured error handling, an application will crash, resulting in a poor user experience and potential data corruption.

In Java, exceptions are divided into **checked** exceptions (which the compiler forces you to catch or declare) and **unchecked** (runtime) exceptions. Python has **no checked exceptions**; all exception handling occurs dynamically at runtime. This requires you to design defensive code paths carefully. Additionally, Python introduces specific syntax like the **`else`** block in try-except structures and **exception chaining** (`raise ... from ...`) to trace error propagation across application layers.

---

## The Concept

### 1. The `try-except-else-finally` Block
Python expands on the standard exception handling structure:
*   **`try`:** Wraps the code that might raise an exception.
*   **`except`:** Catches and handles specific exceptions.
*   **`else`:** Runs *only* if the code inside the `try` block executed successfully without throwing any exceptions. (Excellent for separating the happy path from safety checks).
*   **`finally`:** Runs under all circumstances (even if the code crashed or returned early), typically used for resource cleanup.

### 2. Best Practice: Specific vs. Broad Catching
Never write a blank `except:` or catch the base `Exception` class unless you plan to re-raise it immediately:
- *Avoid:* `except Exception:` (shadows syntax and keyboard interrupts).
- *Prefer:* `except ValueError:` or `except ConnectionError as e:`.

### 3. Custom Exception Classes
In Python, custom exceptions are created by subclassing the built-in **`Exception`** class. By convention, custom exceptions should end with `Error`:

```python
class DatabaseConnectionError(Exception):
    pass
```

### 4. Exception Chaining (`raise ... from ...`)
When catching one exception and raising a custom domain exception (e.g., catching a low-level SQL error and raising a high-level `InventoryError`), you should chain them. This links the stack traces together so developers can debug the root cause:
```python
try:
    connect_to_db()
except ConnectionRefusedError as e:
    raise DatabaseConnectionError("Failed to initialize database connection") from e
```

---

## Code Examples

### 1. Full `try-except-else-finally` Structure
```python
def divide_numbers(a, b):
    try:
        result = a / b
    except ZeroDivisionError as e:
        print(f"Error caught: {e}")
        result = None
    else:
        print("Division completed successfully.")
    finally:
        print("Cleaning up resources from division procedure.")
    return result

divide_numbers(10, 2)
print("---")
divide_numbers(10, 0)
```

**Output:**
```text
Division completed successfully.
Cleaning up resources from division procedure.
---
Error caught: division by zero
Cleaning up resources from division procedure.
```

### 2. Custom Exception and Chaining Demo
Here is an example illustrating a mock order processing workflow with custom exceptions and chaining:

```python
class InsufficientStockError(Exception):
    """Raised when an order request exceeds available inventory."""
    def __init__(self, item, requested, available):
        self.item = item
        self.requested = requested
        self.available = available
        super().__init__(f"Cannot order {requested} of '{item}'. Only {available} in stock.")

def fulfill_order(item, quantity):
    stock = {"laptop": 2}
    
    # 1. Validation check
    if item not in stock:
        raise KeyError(f"Item '{item}' not found in catalog.")
        
    available = stock[item]
    if quantity > available:
        # Raise custom exception
        raise InsufficientStockError(item, quantity, available)
    print(f"Order fulfilled: {quantity} x {item}")

# 2. Executing code and wrapping exceptions
try:
    fulfill_order("laptop", 5)
except InsufficientStockError as e:
    print(f"Caught high-level error: {e}")
    # Exception chaining logic illustration
    # raise TransactionFailedError("Order fulfillment aborted") from e
```

**Output:**
```text
Caught high-level error: Cannot order 5 of 'laptop'. Only 2 in stock.
```

---

## Summary
- Python uses **`try-except-else-finally`** syntax; the **`else`** block runs if no errors occurred in the try block.
- Always catch **specific exceptions** rather than writing generic, broad `except:` clauses.
- Define custom exception classes by inheriting from the built-in **`Exception`** class.
- Use **`raise ... from ...`** for exception chaining to preserve root cause debugging info.

---

## Additional Resources
- [Python Documentation: Errors and Exceptions](https://docs.python.org/3/tutorial/errors.html)
- [Real Python: Python Exceptions: An Introduction](https://realpython.com/python-exceptions/)
- [Real Python: How to Write Custom Exceptions in Python](https://realpython.com/custom-app-exceptions-python/)
