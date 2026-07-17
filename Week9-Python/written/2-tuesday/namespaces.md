# Namespaces, Introspection, and Entrypoints in Python

## Learning Objectives
- Define the concept of a namespace in Python.
- Describe the lifecycle and hierarchy of built-in, global, local, and enclosing namespaces.
- Use `dir()` and `vars()` for structural runtime introspection.
- Explain the role and mechanics of the `if __name__ == '__main__':` design pattern.

---

## Why This Matters
In Java, the scope of variables and methods is strictly bound to class structures and packages. In Python, variables are unbound names that map to object references. How does Python keep track of these mappings without collision?

The answer is **Namespaces**. Python manages identifiers using dictionary-based namespaces under the hood. Understanding how Python constructs, resolves, and cleans up these namespaces is crucial for managing application state. Furthermore, knowing how to inspect namespaces at runtime via introspection utilities like `dir()` and writing correct entrypoints with `__name__ == '__main__'` prevents bugs when importing modules into larger applications.

---

## The Concept

### 1. What is a Namespace?
A **namespace** is a mapping from names (identifiers) to objects. Python implements namespaces internally as standard **dictionaries** (`dict`). When you assign `x = 10`, Python inserts the key `"x"` mapping to the integer object `10` in the active namespace dictionary.

### 2. Namespace Hierarchy and Lifecycles
Python manages namespaces dynamically based on scope levels:

#### Built-in Namespace
*   Contains all built-in functions, constants, and exceptions (e.g., `print`, `len`, `ValueError`).
*   **Lifecycle:** Created when the Python interpreter starts and destroyed when the interpreter shuts down.

#### Global Namespace
*   Contains names defined at the module (file) level, including imported modules and functions.
*   **Lifecycle:** Created when the module is loaded/imported and exists until the interpreter terminates.

#### Enclosing and Local Namespaces
*   **Enclosing:** Created when an outer function executes; contains outer function variables.
*   **Local:** Created when a function or method is called. Holds local variables and arguments.
*   **Lifecycle:** Created on function invocation and **destroyed** when the function returns (unless preserved in a closure).

```
+------------------------------------------------+
|  Built-in Namespace (e.g., print, len)         |
|   +----------------------------------------+   |
|   |  Global Namespace (Module-level names)  |   |
|   |   +--------------------------------+   |   |
|   |   | Enclosing Namespace (Outer fn) |   |   |
|   |   |   +------------------------+   |   |   |
|   |   |   | Local Namespace (Inner)|   |   |   |
|   |   |   +------------------------+   |   |   |
|   |   +--------------------------------+   |   |
|   +----------------------------------------+   |
+------------------------------------------------+
```

### 3. Namespace Introspection: `dir()` vs. `vars()`
Introspection allows a running program to inspect its own structure:
*   **`dir()`:** Without arguments, returns a list of names in the current local namespace. With an object argument, returns an alphabetized list of valid attributes and methods of that object.
*   **`vars()`:** Without arguments, returns a dictionary representing the current local namespace (equivalent to `locals()`). With an object argument, returns the `__dict__` attribute of the object.

### 4. The `__name__ == '__main__'` Entrypoint Pattern
Unlike Java, which requires a static `main` method within a class as an entrypoint, Python executes scripts from top to bottom when run. 

Every module in Python has a special built-in string variable called `__name__`.
*   If you run a script **directly** from the command line (e.g., `python app.py`), Python assigns the value `__main__` to `__name__`.
*   If you **import** the script as a module into another file (e.g., `import app`), Python assigns the module's actual filename to `__name__`.

```python
if __name__ == '__main__':
    # This code ONLY runs when executing this file directly.
    # It does not run when this file is imported as a library elsewhere.
    start_application()
```
This pattern allows you to write files that can act both as reusable library modules (without running code on import) and as executable scripts.

---

## Code Examples

### 1. Introspecting Namespaces with `dir()` and `vars()`
```python
# Declare a global variable
app_version = "1.0.0"

def calculate():
    local_val = 42
    # Inspect the local namespace dictionary
    print("Local vars:", vars()) 
    
calculate()

# Inspecting class structure via dir
class User:
    def __init__(self, name):
        self.name = name

user = User("Bob")
print("\nUser attributes via dir():")
print([attr for attr in dir(user) if not attr.startswith("__")]) # Filter standard dunders
```

**Output:**
```text
Local vars: {'local_val': 42}

User attributes via dir():
['name']
```

### 2. Module Execution Pattern (`__name__`)
Consider a helper module named `calculator.py`:

```python
# calculator.py
def add(a, b):
    return a + b

# This code is run for testing/validation ONLY when executed directly
if __name__ == '__main__':
    print("Running self-test for calculator module...")
    assert add(2, 3) == 5
    print("All tests passed!")
```

If we import `calculator.py` into a main application file:
```python
# main.py
import calculator

print(calculator.add(10, 20))
```
**Execution:**
- Running `python calculator.py` prints: `Running self-test... All tests passed!`
- Running `python main.py` prints: `30` (the self-test code inside `calculator.py` is safely ignored).

---

## Summary
- A **Namespace** is an internal dictionary mapping names to objects.
- Namespaces are structured hierarchically (Built-in -> Global -> Enclosing -> Local).
- Use **`dir()`** to list namespace contents/object attributes and **`vars()`** to retrieve the mapping dictionary directly.
- The **`if __name__ == '__main__':`** check isolates code execution, ensuring that script entrypoints do not trigger when imported as library modules.

---

## Additional Resources
- [Real Python: Namespaces and Scope in Python](https://realpython.com/python-namespaces-scope/)
- [Python Documentation: `__main__` — Top-level code environment](https://docs.python.org/3/library/__main__.html)
- [GeeksforGeeks: `dir()` Function in Python](https://www.geeksforgeeks.org/python-dir-function/)
