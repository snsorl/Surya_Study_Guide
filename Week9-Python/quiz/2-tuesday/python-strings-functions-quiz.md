# Quiz: Strings, Tuples, and Functions

## Questions

### 1. In Python, single quotes ('...') and double quotes ("...") string definitions behave differently at runtime. True or False?
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** Single and double quotes are functionally identical in Python. They only differ in convenience (e.g., embedding double quotes inside single quotes without escape slashes).
- **Why others are wrong:**
  - A) There is no structural difference; both compile to the same `str` class.
</details>

---

### 2. How do you declare a raw string in Python to disable escape sequences like `\n`?
- [ ] A) `w"path\to\file"`
- [ ] B) `r"path\to\file"`
- [ ] C) `f"path\to\file"`
- [ ] D) `b"path\to\file"`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `r"path\to\file"`

**Explanation:** Prefixing a string with `r` or `R` creates a raw string, where backslashes are treated as literal characters.
- **Why others are wrong:**
  - A) `w` is not a valid Python string prefix.
  - C) `f` is used for formatted f-strings.
  - D) `b` is used to define bytes objects.
</details>

---

### 3. What does Python's `__name__` built-in variable evaluate to when a script is executed directly from the terminal?
- [ ] A) `__init__`
- [ ] B) `__module__`
- [ ] C) `__main__`
- [ ] D) The name of the file

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `__main__`

**Explanation:** When a file is run directly, Python sets `__name__` to `"__main__"`. If the file is imported as a module, it is set to the module's name.
- **Why others are wrong:**
  - A) `__init__` is the constructor name for classes.
  - B) `__module__` is an attribute of classes and functions, not a global script status.
  - D) The filename is only set when the file is imported.
</details>

---

### 4. Tuples are mutable sequence objects in Python. True or False?
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** Tuples are immutable sequences. Once created, their size and element references cannot be modified or appended to in-place.
- **Why others are wrong:**
  - A) Lists are mutable, but tuples are immutable.
</details>

---

### 5. What is the correct syntax to define a single-element tuple?
- [ ] A) `x = (42)`
- [ ] B) `x = (42,)`
- [ ] C) `x = [42]`
- [ ] D) `x = tuple(42)`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `x = (42,)`

**Explanation:** A trailing comma is required to tell Python to create a tuple instead of evaluating parentheses as an arithmetic grouping.
- **Why others are wrong:**
  - A) `(42)` evaluates to the integer `42`.
  - C) `[42]` creates a list, not a tuple.
  - D) `tuple(42)` throws a TypeError because integers are not iterable.
</details>

---

### 6. Consider the function signature:
```python
def process_data(action, *args, **kwargs):
    pass
```
Into what data types are `*args` and `**kwargs` collected?
- [ ] A) list and dict
- [ ] B) tuple and dict
- [ ] C) tuple and list
- [ ] D) dict and tuple

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) tuple and dict

**Explanation:** `*args` collects extra positional parameters into a **tuple**, and `**kwargs` collects extra keyword parameters into a **dictionary**.
- **Why others are wrong:**
  - A) `args` is packed as a tuple, not a list.
  - C) `kwargs` is packed as a dict, not a list.
  - D) The order is reversed.
</details>

---

### 7. What is the danger of using a mutable default argument like `def add_item(item, cart=[])`?
- [ ] A) It throws a compile-time SyntaxError.
- [ ] B) The default list is instantiated once at function definition, meaning modifications persist across calls.
- [ ] C) The default list is deleted immediately after the first call, causing NameError on subsequent calls.
- [ ] D) The default list is read-only and will raise a TypeError if appended to.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The default list is instantiated once at function definition, meaning modifications persist across calls.

**Explanation:** Python evaluates default parameters once when the function is parsed, not on execution. A mutable default list `cart` is shared across all function calls that do not provide an explicit list.
- **Why others are wrong:**
  - A) It is valid syntax and will not throw a compile error.
  - C) The list is persistent in memory, not deleted.
  - D) The list is mutable and can be modified, which is why it causes bugs.
</details>

---

### 8. Which code snippet correctly uses a lambda expression to sort a list of dictionary employees by salary ascending?
- [ ] A) `sorted(employees, key=lambda e: e["salary"])`
- [ ] B) `sorted(employees, key=lambda e: return e["salary"])`
- [ ] C) `sorted(employees, key=e["salary"])`
- [ ] D) `sorted(employees, sort=lambda e: e["salary"])`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `sorted(employees, key=lambda e: e["salary"])`

**Explanation:** The `key` parameter expects a function that takes an item and returns the sort score. Lambda expressions implicitly return their evaluated expression (no `return` keyword allowed inside lambdas).
- **Why others are wrong:**
  - B) Using the `return` keyword inside a lambda is a syntax error.
  - C) `key` expects a callable function, not a direct value lookup.
  - D) The argument name is `key`, not `sort`.
</details>
