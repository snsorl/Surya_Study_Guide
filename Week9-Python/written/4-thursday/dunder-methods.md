# Dunder Methods and Context Managers in Python

## Learning Objectives
- Define double-underscore (dunder) methods and explain their execution.
- Implement representation methods (`__str__` and `__repr__`) to control object formatting.
- Design custom collections by implementing sequence-like dunders (`__len__`, `__contains__`).
- Implement rich comparison dunder methods (`__eq__`, `__lt__`) for object sorting.
- Apply arithmetic operator overloading using dunder methods like `__add__`.
- Create custom **Context Managers** using `__enter__` and `__exit__` for resource cleanup.

---

## Why This Matters
In Java, operator overloading is not supported (except for string concatenation with `+`). If you want to compare two objects, you must implement the `Comparable` interface and override `compareTo()`. If you want to handle resource cleanups (like files or database connections), you use Try-With-Resources, which requires the class to implement the `AutoCloseable` interface.

Python uses **Dunder (Double Underscore) Methods**, often called **Magic Methods**, to implement operator overloading and integrate custom classes directly into the Python grammar. By implementing specific dunder methods, your custom classes can respond to operators like `+`, `<`, `==`, `len()`, and support context managers (`with` statements) for automatic resource cleanup.

---

## The Concept

### 1. What are Dunder Methods?
Dunder methods are special methods with leading and trailing double underscores (e.g., `__init__`). They are called implicitly by Python when certain operations are performed on an object. For example, writing `a + b` invokes `a.__add__(b)` under the hood.

### 2. Common Categories of Dunder Methods

#### String Representations
*   **`__str__(self)`:** Returns an informal, user-friendly string representation of the object (invoked by `print(obj)` or `str(obj)`). Equivalent to Java's `toString()`.
*   **`__repr__(self)`:** Returns an official, developer-facing representation of the object (invoked by typing the object in the REPL or calling `repr(obj)`). By convention, it should look like the Python code used to construct the object.

#### Sequence and Collection Emulation
*   **`__len__(self)`:** Called by the `len()` function. Must return an integer.
*   **`__contains__(self, item)`:** Called by the `in` operator. Must return a boolean.
*   **`__iter__(self)` and `__next__(self)`:** Implements the iteration protocol to make an object iterable.

#### Rich Comparisons
*   **`__eq__(self, other)`:** Compares for equality (`==`).
*   **`__lt__(self, other)`:** Compares for less-than (`<`). Enables standard sorting using `sorted()` or `.sort()`.

#### Operator Overloading
*   **`__add__(self, other)`:** Overloads the addition (`+`) operator.

### 3. Context Managers (`__enter__` and `__exit__`)
Context managers clean up resources (like file streams, database connections, or socket locks) automatically using the `with` statement.
*   **`__enter__(self)`:** Runs before the code block inside the `with` statement executes. It sets up the resource and returns the target variable.
*   **`__exit__(self, exc_type, exc_val, exc_tb)`:** Runs immediately after the code block exits—even if an exception was thrown inside the block. It handles cleanup (closing connections, releasing locks) and can suppress exceptions by returning `True`.

---

## Code Examples

### 1. Implementing Basic Dunders and Comparisons
```python
class Product:
    def __init__(self, name, price):
        self.name = name
        self.price = price

    # Developer representation
    def __repr__(self):
        return f"Product(name='{self.name}', price={self.price})"

    # User representation
    def __str__(self):
        return f"{self.name} (${self.price:.2f})"

    # Equality check (==)
    def __eq__(self, other):
        if not isinstance(other, Product):
            return False
        return self.name == other.name and self.price == other.price

    # Less-than comparison (<) for sorting
    def __lt__(self, other):
        if not isinstance(other, Product):
            return NotImplemented
        return self.price < other.price

# Instantiation & Testing
p1 = Product("Laptop", 1200)
p2 = Product("Mouse", 25)
p3 = Product("Mouse", 25)

print(p1)          # Calls __str__: Laptop ($1200.00)
print(repr(p1))    # Calls __repr__: Product(name='Laptop', price=1200)
print(p2 == p3)    # Calls __eq__: True

# Sorting relies on __lt__
products = [p1, p2]
print(sorted(products)) # Output: [Mouse ($25.00), Laptop ($1200.00)]
```

### 2. Creating a Custom Context Manager
Let's build a mock database connection manager to demonstrate `__enter__` and `__exit__`:

```python
class DatabaseConnection:
    def __init__(self, db_name):
        self.db_name = db_name

    def __enter__(self):
        print(f"Connecting to database: {self.db_name}")
        # Return the object (or connection client) to bind in the 'as' clause
        return self

    def query(self, sql):
        print(f"Executing query on {self.db_name}: {sql}")

    def __exit__(self, exc_type, exc_val, exc_tb):
        print("Closing database connection.")
        # If an exception occurred, its details are in exc_type, exc_val
        if exc_type is not None:
            print(f"An error occurred inside the block: {exc_val}")
        # Return False to let the exception propagate, or True to suppress it
        return False

# Usage
with DatabaseConnection("production_db") as db:
    db.query("SELECT * FROM users;")
    # Simulate an error inside the block
    # raise RuntimeError("Connection lost!")

print("\nCode execution continues outside the block.")
```

**Output:**
```text
Connecting to database: production_db
Executing query on production_db: SELECT * FROM users;
Closing database connection.

Code execution continues outside the block.
```

---

## Summary
- **Dunder methods** allow custom Python classes to interface natively with Python language operators and built-ins.
- Use **`__str__`** for user-friendly text output, and **`__repr__`** for developer-oriented debugging output.
- Overload comparisons using **`__eq__`** and **`__lt__`** to support sorting lists of custom objects.
- Implement **`__enter__`** and **`__exit__`** to support the **`with`** keyword, ensuring reliable setup and cleanup of system resources.

---

## Additional Resources
- [Python Documentation: Data Model - Special Method Names](https://docs.python.org/3/reference/datamodel.html#special-method-names)
- [Real Python: Operator Overloading in Python](https://realpython.com/operator-overloading-python/)
- [Real Python: Context Managers and the `with` Statement](https://realpython.com/python-with-statement/)
