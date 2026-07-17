# Dynamic Typing and Duck Typing in Python

## Learning Objectives
- Differentiate between static typing (Java) and dynamic typing (Python).
- Explain the pointer-like reference behavior of Python variables.
- Apply the philosophy of **Duck Typing** in API design.
- Utilize the `type()` and `isinstance()` functions for runtime type introspection.
- Write and inspect **PEP 484 Type Annotations** for documentation and static linting.

---

## Why This Matters
In Java, variables must be explicitly declared with a type (e.g., `int x = 5;`), and this type cannot change. The compiler enforces this safety check at build time.

Python is a **dynamically-typed** language. This means you do not declare variable types; the interpreter resolves types at runtime based on the object bound to the variable name. While dynamic typing allows for faster writing and flexible architectures, it places the responsibility for type safety on the developer. Understanding how Python handles types, how it evaluates object behavior (Duck Typing), and how to use modern Type Annotations ensures you write robust, readable, and self-documenting code.

---

## The Concept

### 1. Dynamic Typing: Names vs. Objects
In Python, variables are merely **labels (or names)** that point to objects in memory. The *objects* have types, but the *names* do not.

When you write:
```python
x = 42      # 'x' points to an integer object
x = "Hello" # 'x' now points to a string object. The integer object is garbage-collected.
```
In Java, this is invalid. In Python, it is normal because `x` is simply a label.

```
       +----+
x ---> | 42 | (int)
       +----+

       +----+
x -/-> | 42 | (int) - eligible for garbage collection
 \     +----+
  \    +---------+
   --> | "Hello" | (str)
       +---------+
```

### 2. Duck Typing Philosophy
Python relies heavily on **Duck Typing**, which is summarized by the adage:
> *"If it walks like a duck and quacks like a duck, then it's a duck."*

In statically-typed Java, if a method expects a `List` interface, you *must* pass an object that implements the `List` interface.
In Python, if a function needs to iterate over an object, it doesn't care if the object is a `list`, `tuple`, `set`, or a custom class. It only cares that the object *behaves* like a collection (i.e., it implements the iteration protocol `__iter__`).

### 3. Type Introspection: `type()` vs. `isinstance()`
When you need to inspect object types at runtime, Python provides two main tools:
- **`type(obj)`**: Returns the exact class type of the object.
- **`isinstance(obj, classinfo)`**: Returns `True` if the object is an instance of the class or a subclass of it. 
  - *Best Practice:* Always prefer `isinstance()` because it supports polymorphism (inheritance structures) and allows checking against multiple types.

### 4. PEP 484 Type Annotations
Introduced in Python 3.5, **Type Annotations** allow you to document the expected types of variables, arguments, and return values:

```python
def greet(name: str) -> str:
    return "Hello, " + name
```

#### Crucial Note on Runtime Behavior:
**Python does not enforce type annotations at runtime.**
If you execute `greet(123)`, Python will execute it anyway, resulting in a runtime `TypeError` when it tries to concatenate the string and integer. Type annotations are purely for:
1.  **Documentation:** Making code easier for humans to read.
2.  **Linting / Static Analysis:** Tools like **MyPy** or IDEs (like VS Code / PyCharm) read these annotations to flag type mismatches *before* you run the program.

---

## Code Examples

### 1. Introspection Demo
Let's see how `type()` and `isinstance()` behave when working with custom classes:

```python
class Animal:
    pass

class Dog(Animal):
    pass

my_dog = Dog()

# 1. Exact type checking using type()
print(type(my_dog) == Dog)     # Output: True
print(type(my_dog) == Animal)  # Output: False (it does not check inheritance!)

# 2. Inheritance-aware checking using isinstance()
print(isinstance(my_dog, Dog))    # Output: True
print(isinstance(my_dog, Animal)) # Output: True (Dog is a subclass of Animal)
print(isinstance(my_dog, (list, Dog))) # Output: True (Checks if it is a list OR Dog)
```

### 2. Duck Typing Demo
This example demonstrates a function that calls a `quack` method. Notice that the custom classes do not inherit from any common base class; they simply implement the expected method:

```python
class RealDuck:
    def quack(self):
        print("Quack! Quack!")

class PersonInDuckCostume:
    def quack(self):
        print("I am quacking like a duck!")

# This function does not perform class checks. It relies on duck typing.
def make_it_quack(duck_like_object):
    duck_like_object.quack()

# Executing behavior
real = RealDuck()
imposter = PersonInDuckCostume()

make_it_quack(real)      # Output: Quack! Quack!
make_it_quack(imposter)  # Output: I am quacking like a duck!
```

---

## Summary
- **Dynamic Typing** means variables are names pointing to objects; type is associated with the value (object), not the variable name.
- **Duck Typing** focuses on what an object *can do* rather than what class it inherits from.
- **Introspection**: `isinstance()` is preferred over `type()` because it accounts for class inheritance hierarchies.
- **Type Annotations (PEP 484)** provide static documentation and linting safety checks (via tools like MyPy), but are not validated or enforced at runtime by Python.

---

## Additional Resources
- [PEP 484: Type Hints Official Specification](https://peps.python.org/pep-0484/)
- [Real Python: Python Type Checking - Guide to Type Hints](https://realpython.com/python-type-checking/)
- [Real Python: Duck Typing - Dynamic and Static Protocols](https://realpython.com/python-duck-typing/)
