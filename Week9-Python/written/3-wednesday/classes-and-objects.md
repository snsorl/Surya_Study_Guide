# Classes and Objects: OOP in Python

## Learning Objectives
- Declare classes and instantiate objects using Python syntax.
- Understand the role of the `__init__` constructor and the explicit `self` parameter.
- Differentiate between instance attributes and class attributes.
- Compare Python's dynamic object model to Java's static, structured class hierarchy.
- Articulate the concept that "everything is an object" in Python.
- Investigate object reference addresses using the `id()` function.

---

## Why This Matters
For Java developers, Object-Oriented Programming (OOP) is second nature. Java enforces OOP strictly—nearly every line of code lives inside a class, and variables must belong to class structures.

Python is a multi-paradigm language. While it supports procedural and functional styles, its OOP implementation is exceptionally powerful and flexible. In Python, class structures are dynamic, and **everything** (including functions, modules, and integers) is an object. Understanding Python's unique object system, the explicit `self` reference, and runtime attribute binding will help you transition your design patterns from Java to Python cleanly, avoiding common traps like mutable class attributes.

---

## The Concept

### 1. Declaring Classes and the `__init__` Constructor
Python classes are declared using the `class` keyword. Unlike Java, where constructor functions match the class name, Python uses a special double-underscore (dunder) method called **`__init__`** to initialize new instances.

```python
class Student:
    def __init__(self, name, age):
        self.name = name
        self.age = age
```

### 2. The `self` Parameter Explained
In Java, the current object instance is referenced implicitly using the `this` keyword. In Python, the reference is explicit.
*   The first parameter of any instance method in a Python class **must** be the object reference. By convention, this is named **`self`**.
*   When calling a method (e.g., `student.study()`), you do not pass `self` manually. Python injects the calling object reference into the first parameter automatically behind the scenes.

### 3. Instance vs. Class Attributes
*   **Instance Attributes:** Specific to a single object instance. They are bound inside methods (usually the constructor) using `self.attribute_name = value`.
*   **Class Attributes:** Shared across all instances of a class. They are defined directly inside the class body, outside of any methods.

> [!WARNING]
> **Beware of mutable class attributes.**
> If a class attribute is a list or dictionary, modifying it on one instance modifies it for *all* instances of that class, mimicking the behavior of a static reference in Java.

### 4. "Everything is an Object"
In Java, primitive types (like `int`, `char`, `boolean`) are not objects.
In Python, **everything is an object**.
*   Integers, strings, functions, classes, and imported modules are all instances of a class.
*   For example, the number `42` is an instance of the class `int`. You can call methods directly on it: `(42).bit_length()`.

### 5. Object Identity and `id()`
Because everything is an object in memory, each object is allocated a unique reference identity.
*   **`id(obj)`**: A built-in function that returns the unique integer memory address of the object.
*   The comparison `a is b` is equivalent to checking `id(a) == id(b)`.

---

## Code Examples

### 1. Class Declaration and Attribute Scope
Let's see how class attributes differ from instance attributes:

```python
class SoftwareTeam:
    # Class Attribute (Shared by all instances - like a static variable in Java)
    company = "Infosys"
    
    def __init__(self, project_name):
        # Instance Attribute (Unique to each object)
        self.project_name = project_name
        self.developers = []  # Instance list

# Instantiation (No 'new' keyword!)
team_a = SoftwareTeam("Inventory System")
team_b = SoftwareTeam("Payment Gateway")

# 1. Modify class attribute on the class level
SoftwareTeam.company = "Infosys ADM"
print(team_a.company)  # Output: Infosys ADM
print(team_b.company)  # Output: Infosys ADM

# 2. Add developers to unique instance lists
team_a.developers.append("Alice")
team_b.developers.append("Bob")

print(f"Team A: {team_a.developers}") # Output: Team A: ['Alice']
print(f"Team B: {team_b.developers}") # Output: Team B: ['Bob']
```

### 2. The Class Attribute Mutable Trap (Avoid This)
```python
# --- VULNERABLE CODE ---
class WrongTeam:
    # Defining a list as a class attribute makes it shared!
    developers = [] 
    
    def __init__(self, name):
        self.name = name

t1 = WrongTeam("Alpha")
t2 = WrongTeam("Beta")

t1.developers.append("Charlie")
print(t2.developers)  # Output: ['Charlie']  <-- Shared across all instances!
```

### 3. Object Identity Check
```python
x = [1, 2, 3]
y = [1, 2, 3]
z = x

print(f"id(x): {id(x)}")
print(f"id(y): {id(y)}")
print(f"id(z): {id(z)}")

# Check value equality
print(x == y)  # Output: True
# Check object identity (is)
print(x is y)  # Output: False (different memory addresses)
print(x is z)  # Output: True  (same memory address)
```

---

## Summary
- Declare classes using `class`; instantiate objects by calling the class directly (no `new` keyword).
- Use **`__init__`** to initialize attributes; use the explicit **`self`** parameter to reference the instance.
- **Instance attributes** belong to the specific object, while **class attributes** are shared across all instances of the class.
- **Everything in Python is an object**, including primitives, functions, and modules.
- Use **`id()`** to inspect the unique memory reference address of an object.

---

## Additional Resources
- [Python Documentation: Classes and OOP](https://docs.python.org/3/tutorial/classes.html)
- [Real Python: Object-Oriented Programming (OOP) in Python 3](https://realpython.com/python3-object-oriented-programming/)
- [Real Python: Java vs Python OOP Comparison](https://realpython.com/java-vs-python/#oop-design-and-implementation)
