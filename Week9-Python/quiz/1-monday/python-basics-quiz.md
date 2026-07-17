# Quiz: Python Basics Knowledge Check

## Questions

### 1. In Python, variables are statically typed. True or False?
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** Python is dynamically typed. Variable names are labels bound to objects at runtime; the objects have types, but the variable names do not.
- **Why others are wrong:**
  - A) Java is statically typed, but Python is dynamically typed.
</details>

---

### 2. Which function is preferred for checking an object's type while supporting class inheritance structures?
- [ ] A) `type()`
- [ ] B) `isinstance()`
- [ ] C) `typeof()`
- [ ] D) `classinfo()`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `isinstance()`

**Explanation:** `isinstance(obj, class)` returns True if the object is an instance of the class or any of its subclasses.
- **Why others are wrong:**
  - A) `type()` returns the exact type of the object but does not check subclass/inheritance hierarchies.
  - C) `typeof()` is a JavaScript keyword, not Python.
  - D) `classinfo()` is not a standard type check function in Python.
</details>

---

### 3. What is the value of the expression `11 // 3` in Python?
- [ ] A) 3.6666666666666665
- [ ] B) 3.0
- [ ] C) 3
- [ ] D) 2

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) 3

**Explanation:** The `//` operator represents floor division, which divides two numbers and rounds down to the nearest integer.
- **Why others are wrong:**
  - A) This is the result of standard float division `11 / 3`.
  - B) Floor division with integers returns an integer, not a float.
  - D) 2 is the remainder returned by the modulo operator `11 % 3`.
</details>

---

### 4. What is the execution search order for variable lookup in Python as defined by the LEGB rule?
- [ ] A) Local, Enclosing, Global, Built-in
- [ ] B) Local, External, Global, Boundary
- [ ] C) Loop, Enclosing, Global, Built-in
- [ ] D) Local, Enclosing, General, Built-in

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) Local, Enclosing, Global, Built-in

**Explanation:** When resolving a variable name, Python checks scopes in this order: Local (active function), Enclosing (enclosing nested functions), Global (module level), and Built-in (standard library names).
- **Why others are wrong:**
  - B) "External" and "Boundary" are not part of the LEGB scopes.
  - C) Loops do not create their own scope in Python.
  - D) "General" is incorrect; the G stands for "Global".
</details>

---

### 5. What does the expression `2 ** 4` evaluate to in Python?
- [ ] A) 8
- [ ] B) 16
- [ ] C) 6
- [ ] D) Error

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) 16

**Explanation:** The `**` operator represents mathematical exponentiation (2 raised to the power of 4, which is 16).
- **Why others are wrong:**
  - A) 8 is the result of multiplication `2 * 4` or addition `2 + 6`.
  - C) 6 is the result of addition `2 + 4`.
  - D) `**` is valid Python syntax.
</details>

---

### 6. Consider the code:
```python
x = [1, 2]
y = [1, 2]
print(x is y)
```
What is the output?
- [ ] A) True
- [ ] B) False
- [ ] C) None
- [ ] D) Error

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** The `is` operator checks reference identity (whether both names point to the exact same object in memory). Since `x` and `y` are two separate list objects in memory, `x is y` is False, even though their values are identical (`x == y` is True).
- **Why others are wrong:**
  - A) If we assigned `y = x`, the result would be True. But here they are separate objects.
  - C) The expression evaluates to a boolean value, not None.
  - D) The syntax is correct.
</details>

---

### 7. What is the CPython compiler/interpreter bytecode cache file format?
- [ ] A) `.class`
- [ ] B) `.py`
- [ ] C) `.pyc`
- [ ] D) `.pyw`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `.pyc`

**Explanation:** CPython compiles Python source code into bytecode files ending in `.pyc` and stores them in the `__pycache__` directory to speed up imports on subsequent runs.
- **Why others are wrong:**
  - A) `.class` is Java's compiled bytecode format.
  - B) `.py` is the raw Python source code format.
  - D) `.pyw` is for windowed Python scripts running without terminal consoles.
</details>

---

### 8. Which keyword is used to modify a variable declared at the module-level from inside a local function scope?
- [ ] A) `nonlocal`
- [ ] B) `global`
- [ ] C) `static`
- [ ] D) `extern`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `global`

**Explanation:** The `global` keyword tells Python to bind the local name to the module-level scope, allowing writes/reassignments to affect the global variable.
- **Why others are wrong:**
  - A) `nonlocal` is used to modify variables in the enclosing nested function scope (not the global scope).
  - C) `static` is a Java keyword and is not used for scope binding in Python.
  - D) `extern` is not a Python keyword.
</details>
