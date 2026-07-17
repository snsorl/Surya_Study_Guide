# Quiz: OOP and Core Data Structures

## Questions

### 1. In Python, class constructors are declared using which method name?
- [ ] A) `__init__`
- [ ] B) `constructor`
- [ ] C) `__new__`
- [ ] D) The name of the class

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `__init__`

**Explanation:** Python uses the double underscore method `__init__` to initialize instances of a class.
- **Why others are wrong:**
  - B) `constructor` is used in JavaScript, not Python.
  - C) `__new__` is for object creation, while `__init__` is for object initialization.
  - D) Java uses the class name for constructors, but Python does not.
</details>

---

### 2. What is the role of the explicit `self` parameter in Python instance methods?
- [ ] A) It represents a static class context.
- [ ] B) It refers to the calling object instance (equivalent to 'this' in Java).
- [ ] C) It is a built-in function to create new local scopes.
- [ ] D) It is an optional parameter used only for documentation.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It refers to the calling object instance (equivalent to 'this' in Java).

**Explanation:** In Python, the first parameter of any instance method must explicitly represent the current object instance, and by convention is named `self`.
- **Why others are wrong:**
  - A) `self` represents an instance reference, not a static class level.
  - C) It is a parameter, not a function.
  - D) It is mandatory at runtime for instance methods.
</details>

---

### 3. Consider the code:
```python
class Team:
    members = []  # Class attribute
    
    def __init__(self, name):
        self.name = name
```
If we run `t1 = Team("A")` and `t2 = Team("B")`, what is the risk of executing `t1.members.append("Alice")`?
- [ ] A) It will raise a AttributeError.
- [ ] B) "Alice" will be added only to t1.
- [ ] C) "Alice" will be added to the members list of both t1 and t2.
- [ ] D) It will raise a TypeError because lists are mutable.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) "Alice" will be added to the members list of both t1 and t2.

**Explanation:** `members` is a class attribute (defined outside methods), making it shared by all instances. Since lists are mutable, modifying `members` on `t1` affects the shared list accessed by `t2`.
- **Why others are wrong:**
  - A) The attribute exists on the class, so no AttributeError will be thrown.
  - B) Because it is a class attribute, the reference is shared.
  - D) Python allows list mutations.
</details>

---

### 4. What is the command to extract dependencies and export them to a file in a virtual environment?
- [ ] A) `pip list > requirements.txt`
- [ ] B) `pip freeze > requirements.txt`
- [ ] C) `venv export requirements.txt`
- [ ] D) `pip export requirements.txt`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `pip freeze > requirements.txt`

**Explanation:** `pip freeze` outputs installed packages and their exact versions in a standard format, which is redirected to `requirements.txt`.
- **Why others are wrong:**
  - A) `pip list` outputs a human-readable table containing column titles, which is not suitable for pip installations.
  - C) `venv` does not have an export command.
  - D) `export` is not a valid `pip` command.
</details>

---

### 5. Consider the list: `numbers = [10, 20, 30, 40, 50]`. What is the result of the slice `numbers[1:4:2]`?
- [ ] A) `[20, 30, 40]`
- [ ] B) `[20, 40]`
- [ ] C) `[20, 30]`
- [ ] D) `[30, 50]`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `[20, 40]`

**Explanation:** The slice starts at index 1 (`20`), ends before index 4 (`50` is exclusive), and increments by a step of 2. This grabs elements at index 1 (`20`) and index 3 (`40`).
- **Why others are wrong:**
  - A) This is the output of `numbers[1:4]` with the default step of 1.
  - C) This does not jump by step 2.
  - D) Index 3 is `40`, not `50`.
</details>

---

### 6. What does `numbers[::-1]` do to a list?
- [ ] A) Clears the list.
- [ ] B) Returns a list containing only the last element.
- [ ] C) Reverses the list.
- [ ] D) Raises an IndexError.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Reverses the list.

**Explanation:** A negative step of `-1` instructs Python to slice backward from the end to the beginning, reversing the sequence.
- **Why others are wrong:**
  - A) It does not modify the list in-place or clear it.
  - B) To get only the last element, use index `[-1]`, not a slice.
  - D) Slicing syntax handles negative steps correctly without raising errors.
</details>

---

### 7. What is the time complexity of checking membership (`item in collection`) in a Set compared to a List?
- [ ] A) O(N) in Sets, O(1) in Lists
- [ ] B) O(1) in Sets, O(N) in Lists
- [ ] C) O(1) in both
- [ ] D) O(N) in both

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) O(1) in Sets, O(N) in Lists

**Explanation:** Sets are backed by hash tables, providing constant time lookup O(1). Lists require a linear search scanning elements one-by-line, resulting in linear time O(N).
- **Why others are wrong:**
  - A) The complexity values are reversed.
  - C) Lists cannot perform O(1) lookups.
  - D) Sets are significantly faster than O(N) lists.
</details>

---

### 8. How do you initialize a blank, empty set in Python?
- [ ] A) `my_set = {}`
- [ ] B) `my_set = set()`
- [ ] C) `my_set = []`
- [ ] D) `my_set = ()`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `my_set = set()`

**Explanation:** Writing `{}` initializes an empty **dictionary**. To initialize an empty set, you must use the `set()` constructor.
- **Why others are wrong:**
  - A) This creates a dictionary, not a set.
  - C) This creates an empty list.
  - D) This creates an empty tuple.
</details>

---

### 9. Which method retrieves a dictionary value safely, returning a default value if the key does not exist?
- [ ] A) `dict.retrieve()`
- [ ] B) `dict.get()`
- [ ] C) `dict.pop()`
- [ ] D) `dict.find()`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `dict.get()`

**Explanation:** `dict.get(key, default)` returns the value if the key is present, otherwise it returns the default value (or None), avoiding a KeyError crash.
- **Why others are wrong:**
  - A) `retrieve()` is not a dictionary method.
  - C) `pop()` removes the item and returns it, but raises a KeyError if the key is missing (unless a default is specified).
  - D) `find()` is a string method, not a dictionary method.
</details>

---

### 10. During a code freeze, developers are allowed to merge new features into the release branch. True or False?
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** Code freeze locks the codebase against new feature development. Only critical bug fixes and documentation patches are allowed to stabilize the release.
- **Why others are wrong:**
  - A) Merging new features during a freeze violates the freeze rules and risks stability.
</details>
