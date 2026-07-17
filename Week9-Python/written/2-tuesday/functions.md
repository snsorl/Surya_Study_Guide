# Functions, Arguments, and Functional Programming in Python

## Learning Objectives
- Declare functions using the `def` syntax and define return values.
- Apply default argument values and analyze the "mutable default argument" pitfall.
- Implement variable-length argument collectors using `*args` and `**kwargs`.
- Restrict function arguments using positional-only (`/`) and keyword-only (`*`) parameters.
- Write inline anonymous expressions using `lambda` functions.
- Utilize Python functions as first-class citizens in higher-order functions like `map()`, `filter()`, and `sorted()`.
- Add docstrings and type annotations to verify function signatures.

---

## Why This Matters
Functions are the fundamental unit of modularity in any codebase. In Java, functions cannot exist in isolation; they must be declared as methods inside a Class structure, and passing functions around requires interfaces (like standard Java 8 functional interfaces).

Python treats functions as **first-class citizens**. This means functions can be assigned to variables, passed as arguments to other functions, and returned from functions just like integers, strings, or lists. Additionally, Python's flexible parameter model—supporting optional arguments, arbitrary arguments (`*args` / `**kwargs`), and keyword-only constraints—makes it incredibly powerful for writing clear, highly flexible microservices and pipelines.

---

## The Concept

### 1. Basic Declaration and Return Values
Functions are declared using the `def` keyword. Unlike Java, which requires declaring return types, Python functions return `None` by default unless an explicit `return` statement is reached.

### 2. Default Arguments and the Mutable Default Trap
Parameters can have default values, making them optional when calling the function:
```python
def log_message(message, level="INFO"):
    print(f"[{level}] {message}")
```

> [!CAUTION]
> **Never use mutable objects (like lists or dictionaries) as default arguments.**
> Python evaluates default arguments *once* when the function is defined, not when it is executed. If you modify a default list, it remains modified across subsequent function executions!

*Correct Approach:* Use `None` as the default value and instantiate inside the function:
```python
def add_item(item, item_list=None):
    if item_list is None:
        item_list = []
    item_list.append(item)
    return item_list
```

### 3. Arbitrary Arguments: `*args` and `**kwargs`
*   **`*args` (Positional Arguments):** Collects extra positional arguments passed to the function into a **tuple**.
*   **`**kwargs` (Keyword Arguments):** Collects extra keyword arguments passed to the function into a **dictionary**.

### 4. Positional-Only and Keyword-Only Arguments
You can enforce how arguments are passed using special markers in the function signature:
*   **Positional-Only (`/`):** All parameters *before* `/` must be passed positionally.
*   **Keyword-Only (`*`):** All parameters *after* `*` must be passed as keyword arguments.

```python
def configure(ip, port, /, timeout=30, *, secure=True):
    # 'ip' and 'port' are positional-only.
    # 'secure' is keyword-only.
```

### 5. Lambda Functions
Lambdas are small, anonymous, inline functions. They are restricted to a single expression.
- *Syntax:* `lambda arguments: expression`
- *Example:* `add = lambda x, y: x + y`

### 6. First-Class Functions and Functional Tools
Because functions are first-class, you can pass them into higher-order functions:
*   **`map(func, iterable)`**: Applies `func` to every item in the iterable.
*   **`filter(func, iterable)`**: Filters items in the iterable where `func(item)` returns `True`.
*   **`sorted(iterable, key=func)`**: Sorts the iterable using the return value of `func` as the sorting criterion.

---

## Code Examples

### 1. The Mutable Default Argument Bug

```python
# --- VULNERABLE CODE (The Bug) ---
def add_to_cart(item, cart=[]):
    cart.append(item)
    return cart

print(add_to_cart("Apple"))  # Output: ['Apple']
print(add_to_cart("Banana")) # Output: ['Apple', 'Banana']  <-- cart was not reset!

# --- SECURE/CORRECT CODE ---
def add_to_cart_fixed(item, cart=None):
    if cart is None:
        cart = []
    cart.append(item)
    return cart
```

### 2. Utilizing `*args` and `**kwargs`
```python
def build_profile(first, last, *hobbies, **metadata):
    print(f"Name: {first} {last}")
    print(f"Hobbies (tuple): {hobbies}")
    print(f"Metadata (dict): {metadata}")

build_profile(
    "John", "Doe", 
    "Hiking", "Reading",  # Captured by *hobbies
    role="Developer", location="Boston" # Captured by **metadata
)
```

**Output:**
```text
Name: John Doe
Hobbies (tuple): ('Hiking', 'Reading')
Metadata (dict): {'role': 'Developer', 'location': 'Boston'}
```

### 3. Argument Enforcement (`/` and `*`)
```python
def record_transaction(amount, currency, /, *, category="General"):
    print(f"Amount: {amount} {currency}, Category: {category}")

# Correct Invocation
record_transaction(100, "USD", category="Software")

# Incorrect: amount and currency passed as keywords
# record_transaction(amount=100, currency="USD") # Throws TypeError

# Incorrect: category passed positionally
# record_transaction(100, "USD", "Software")     # Throws TypeError
```

### 4. Functional Operations with Lambdas
```python
employees = [
    {"name": "Alice", "salary": 70000},
    {"name": "Bob", "salary": 85000},
    {"name": "Charlie", "salary": 60000}
]

# 1. Filter employees earning more than 65000
high_earners = list(filter(lambda emp: emp["salary"] > 65000, employees))
print("High Earners:", high_earners)

# 2. Extract employee names using map
names = list(map(lambda emp: emp["name"], employees))
print("Names:", names)

# 3. Sort employees by salary ascending
sorted_employees = sorted(employees, key=lambda emp: emp["salary"])
print("Sorted by Salary:", sorted_employees)
```

---

## Summary
- Declare functions using `def`; default values are set in the parameter signature.
- **Always avoid mutable defaults** like lists/dicts; default them to `None` instead.
- Use **`*args`** to collect extra positional parameters, and **`**kwargs`** to collect keyword parameters.
- Enforce strict parameter bounds using **`/`** (positional-only) and **`*`** (keyword-only).
- **Lambdas** are anonymous, single-expression functions.
- Functions are **first-class citizens** and can be passed to higher-order functions like `map`, `filter`, and `sorted`.

---

## Additional Resources
- [Python Documentation: More on Defining Functions](https://docs.python.org/3/tutorial/controlflow.html#defining-functions)
- [Real Python: Defining Your Own Python Function](https://realpython.com/defining-your-own-python-function/)
- [Real Python: Functional Programming in Python](https://realpython.com/python-functional-programming/)
