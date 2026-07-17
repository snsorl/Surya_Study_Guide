# Variable Scopes and the LEGB Rule

## Learning Objectives
- Explain the concept of namespaces and scopes in Python.
- Apply the **LEGB Rule** (Local, Enclosing, Global, Built-in) to trace variable lookup resolution.
- Differentiate between and correctly apply the `global` and `nonlocal` keywords.
- Recognize and prevent variable shadowing issues in nested code structures.
- Detail variable scope behavior inside loop constructs versus list comprehensions.

---

## Why This Matters
As applications grow in complexity, variables are defined in multiple files, classes, and nested functions. Without strict rules governing which variables can be read or modified at any given line of code, programs would experience unpredictable bugs and side effects.

In Java, scopes are bounded by block braces `{}` (such as `if` blocks or `for` loops). In Python, blocks like `if` statements and `for` loops do **not** create a new scope. Instead, scopes are created by functions, classes, and modules. Mastering Python's **LEGB rule** and scope-manipulation keywords is crucial for writing clean APIs, working with closures, and keeping namespaces predictable.

---

## The Concept

### 1. The LEGB Scope Resolution Rule
When you reference a variable name in Python, the interpreter searches for that name in a specific, hierarchical order. It stops searching at the first scope layer where the name is found. If the name is not found in any scope, a `NameError` is raised.

The search hierarchy is:
1.  **L - Local:** Names defined inside the currently executing function (or lambda).
2.  **E - Enclosing:** Names defined in the local scope of any enclosing (outer) functions (relevant when nesting functions).
3.  **G - Global:** Names defined at the top-level of the module/file, or declared as global in a function.
4.  **B - Built-in:** Names pre-loaded by Python (like `len`, `range`, `print`, `ValueError`).

```
+------------------------------------------+
|  Built-in (B) - print, len, range, etc.  |
|   +----------------------------------+   |
|   |  Global (G) - Module-level names |   |
|   |   +--------------------------+   |   |
|   |   | Enclosing (E) - Outer fn |   |   |
|   |   |   +------------------+   |   |   |
|   |   |   | Local (L) - Inner|   |   |   |
|   |   |   +------------------+   |   |   |
|   |   +--------------------------+   |   |
|   +----------------------------------+   |
+------------------------------------------+
Search order: Local -> Enclosing -> Global -> Built-in
```

### 2. Reading vs. Writing variables in Outer Scopes
By default, functions can *read* variables from enclosing or global scopes without extra configuration. However, if a function attempts to *write* (reassign) a variable in an outer scope, Python assumes you want to create a new **local variable** with the same name. This causes **variable shadowing**.

To override this default write behavior, Python provides two keywords:
*   **`global`:** Instructs the local scope that a variable belongs to the module-level (Global) scope.
*   **`nonlocal`:** Instructs a nested function that a variable belongs to the immediate outer (Enclosing) function's scope.

### 3. Loop Scopes vs. List Comprehensions
In Python, standard control flow blocks (like `if`, `try`, `for`, `while`) **do not** create their own scopes. Any variable assigned inside a `for` loop is visible outside the loop within the same function block.

However, **list comprehensions** (which we will cover in depth on Wednesday) *do* create their own temporary scope. The loop counter variable in a list comprehension is isolated and does not leak or overwrite existing variables in the enclosing scope.

---

## Code Examples

### 1. Tracing LEGB Scope Lookup

```python
x = "global"  # Global Scope

def outer_function():
    x = "enclosing"  # Enclosing Scope
    
    def inner_function():
        x = "local"  # Local Scope
        print(f"Inside inner_function: {x}")
        
    inner_function()
    print(f"Inside outer_function: {x}")

outer_function()
print(f"Inside global scope: {x}")
```

**Output:**
```text
Inside inner_function: local
Inside outer_function: enclosing
Inside global scope: global
```

### 2. Modifying Scopes: `global` vs. `nonlocal`

#### Using the `global` Keyword
```python
counter = 0  # Global variable

def increment_global():
    global counter  # Tells Python to use the module-level variable
    counter += 1

increment_global()
print(counter)  # Output: 1
```

#### Using the `nonlocal` Keyword
```python
def parent_job():
    task_id = 100  # Enclosing variable
    
    def child_subtask():
        nonlocal task_id  # Modifies task_id in parent_job, not a new local one
        task_id = 200
        
    child_subtask()
    print(f"Parent task_id is now: {task_id}")

parent_job()  # Output: Parent task_id is now: 200
```

### 3. Variable Shadowing Pitfall
If you modify a variable *before* declaring it as global, or assign to a name that shadows a global variable inside the same function block, Python will throw an `UnboundLocalError`:

```python
value = 10

def invalid_modification():
    print(value)  # Intends to print global 'value'
    value = 20    # Assignment causes Python to treat 'value' as local for the entire block!
    # Throws: UnboundLocalError: local variable 'value' referenced before assignment
```

### 4. Loop Leakage vs. List Comprehension Isolation
```python
# 1. Standard For Loop: Variable leaks
for i in range(3):
    pass
print(f"Value of 'i' after standard loop: {i}")  # Output: 2

# 2. List Comprehension: Variable is isolated
[j * 2 for j in range(5)]
# print(j)  # Throws NameError: name 'j' is not defined (Isolated scope!)
```

---

## Summary
- **LEGB rule** dictates lookup order: Local -> Enclosing -> Global -> Built-in.
- **Functions** create scopes, while standard control structures (`if`, `for`) do not.
- Use **`global`** to modify variables at the module-level.
- Use **`nonlocal`** to modify variables in nested (enclosing) function scopes.
- **List comprehensions** isolate their loop variables, preventing scope leakage that occurs in standard `for` loops.

---

## Additional Resources
- [Real Python: Python Scope & LEGB Rule: Resolving Names in Your Code](https://realpython.com/python-scope-legb-rule/)
- [Python Documentation: Execution Model - Naming and Binding](https://docs.python.org/3/reference/executionmodel.html#naming-and-binding)
- [W3Schools: Python Scope Guide](https://www.w3schools.com/python/python_scope.asp)
