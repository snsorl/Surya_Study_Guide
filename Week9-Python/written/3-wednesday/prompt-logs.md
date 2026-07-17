# Prompt Engineering Log: Generating Idiomatic Python

## Learning Objectives
- Design AI prompts that produce highly idiomatic, "Pythonic" code.
- Analyze the characteristics of Java-influenced Python code versus true Pythonic code.
- Structure prompt instructions to avoid common AI traps (like generating unnecessary getters and setters).
- Evaluate prompt responses for compliance with PEP 8 and Python design patterns.

---

## Why This Matters
Generative AI models are trained on massive volumes of public code. When you ask an AI to write Python, it can easily write code that is syntactically valid but structurally un-idiomatic. This is particularly common if the context mentions Java, as the model may default to Java paradigms written in Python syntax (e.g., writing explicit getters and setters, implementing manual loops instead of list comprehensions, or wrapping scripts in unnecessary classes).

As a software engineer, you must know how to direct AI tools to produce clean, maintainable, and Pythonic code. This log document outlines prompting strategies and showcases how to steer models toward idiomatic Python patterns.

---

## The Concept

### 1. Java-Influenced vs. Pythonic Patterns
When reviewing AI-generated Python code, you will often spot "Java-isms." Here are the key anti-patterns to watch out for:

| Pattern | Java-Influenced Python (Anti-Pattern) | Idiomatic Python (Pythonic) |
| :--- | :--- | :--- |
| **Encapsulation** | Private variables with manual `get_x()` and `set_x()` methods. | Public attributes. Use `@property` getters/setters only if validation is required. |
| **Data Classes** | Boilerplate class with init, string representation, and equality methods. | Python's built-in `@dataclass` decorator or `namedtuple`. |
| **List Manipulation** | Initializing an empty list and running a `for` loop to append elements. | **List Comprehension** or higher-order functions. |
| **Type Checking** | Checking exact types using `type(x) == Y`. | Relying on **Duck Typing** or using `isinstance()`. |
| **Resource Cleanup** | Manual `try` / `finally` blocks with `.close()` calls. | **`with`** statements (Context Managers). |

### 2. Prompting Strategy: The "Pythonic Directive"
To force the AI model to write idiomatic code, use explicit guidelines in your system prompt or user query:
*   *Avoid:* "Write a Python class for a user."
*   *Better:* "Create a Python class for a User using the `@dataclass` decorator. Ensure it follows PEP 8. Avoid writing custom getters, setters, or standard dunder methods (like `__repr__` or `__eq__`) manually."

---

## Prompt Log Comparisons

Here are actual prompt logs comparing weak prompts (resulting in Java-style code) against optimized prompts (producing Pythonic code).

### Case 1: Class Definition and Encapsulation

#### Prompt A (Weak):
> *"Write a Python class representing a bank account with owner and balance. Include methods to get and set the balance, and to withdraw money."*

**AI Output (Non-Idiomatic):**
```python
# Weak Output: Manual getters and setters in Python is a design anti-pattern.
class BankAccount:
    def __init__(self, owner, balance):
        self._owner = owner
        self._balance = balance

    def get_balance(self):
        return self._balance

    def set_balance(self, balance):
        if balance < 0:
            raise ValueError("Balance cannot be negative")
        self._balance = balance

    def withdraw(self, amount):
        if amount > self._balance:
            raise ValueError("Insufficient funds")
        self._balance -= amount
```

---

#### Prompt B (Optimized):
> *"Write a Python class representing a bank account with owner and balance. Use the standard Python property decorator (@property) to wrap the balance attribute so that it cannot be set to a negative value. Avoid writing get_x and set_x methods; expose attributes directly unless validation is required."*

**AI Output (Pythonic):**
```python
# Pythonic Output: Exposes owner directly; uses @property decorator for balance validation
class BankAccount:
    def __init__(self, owner, balance):
        self.owner = owner          # Exposed directly (no getter/setter needed)
        self.balance = balance      # Triggers the setter method below

    @property
    def balance(self):
        return self._balance

    @balance.setter
    def balance(self, value):
        if value < 0:
            raise ValueError("Balance cannot be negative")
        self._balance = value

    def withdraw(self, amount):
        if amount > self._balance:
            raise ValueError("Insufficient funds")
        self.balance -= amount      # Uses the setter logic
```

---

### Case 2: List Processing

#### Prompt A (Weak):
> *"Write a function that takes a list of numbers and returns a list of their squares, but only for numbers that are odd."*

**AI Output (Non-Idiomatic):**
```python
def get_odd_squares(numbers):
    # Java-style list compilation: verbose and slower
    squares = []
    for num in numbers:
        if num % 2 != 0:
            squares.append(num * num)
    return squares
```

#### Prompt B (Optimized):
> *"Write a Python function that takes a list of numbers and returns a list of their squares for odd numbers. Implement it using an idiomatic, single-line list comprehension."*

**AI Output (Pythonic):**
```python
def get_odd_squares(numbers):
    # Pythonic list comprehension: fast, readable, and standard
    return [num ** 2 for num in numbers if num % 2 != 0]
```

---

## Summary
- When prompting AI tools, explicitly demand **PEP 8 compliance** and **Pythonic idioms**.
- Steer models away from "Java-isms" like manual getter/setter methods. Use **`@property`** or **`@dataclass`** instead.
- Direct models to use modern Python features such as **list comprehensions**, **context managers**, and **built-in library operations**.

---

## Additional Resources
- [The Hitchhiker’s Guide to Python: Writing Code Style](https://docs.python-guide.org/writing/style/)
- [Real Python: How to Write Pythonic Code](https://realpython.com/learning-paths/write-pythonic-code/)
- [OpenAI Guide: Prompt Engineering Best Practices](https://platform.openai.com/docs/guides/prompt-engineering)
