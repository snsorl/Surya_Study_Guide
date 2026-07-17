# Dictionaries: Key-Value Stores in Python

## Learning Objectives
- Define dictionaries and explain their underlying hash table mechanics.
- Create dictionaries using literals, the `dict()` constructor, and dictionary comprehensions.
- Access and retrieve values safely using square brackets (`[]`) and the `.get()` method.
- Modify dictionary contents using key assignment, `.update()`, `del`, and `.pop()`.
- Iterate through keys, values, and key-value pairs using `.keys()`, `.values()`, and `.items()`.
- Model nested data structures and perform conversions between Python dictionaries and JSON strings.

---

## Why This Matters
In Java development, you frequently use the `Map` interface and `HashMap` implementation to store key-value associations. Accessing entries, checking keys, and parsing JSON in Java can feel syntax-heavy, requiring classes like `ObjectMapper` from Jackson and various type castings.

Python provides **Dictionaries (`dict`)** as a built-in, highly optimized first-class data type. In Python, dictionaries are extremely fast (using O(1) hash table lookups under the hood) and form the structural backbone of Python objects, namespaces, and JSON messaging. Because Python dictionaries map directly to JSON syntax, data parsing, configuration handling, and web API responses are exceptionally clean and simple.

---

## The Concept

### 1. Dictionary Declaration and Mechanics
A dictionary is a mutable, unordered (preserved insertion-ordered since Python 3.7) collection of key-value pairs. 
*   Keys must be **immutable (hashable)** types, such as strings, numbers, or tuples.
*   Values can be of any data type (including lists, other dictionaries, or custom objects).

### 2. Creation Methods
*   **Literals:** `user = {"id": 101, "name": "Alice"}`
*   **Constructor:** `user = dict(id=101, name="Alice")`
*   **Comprehensions:** Dynamic dictionary generation:
    - `{x: x**2 for x in range(5)}`

### 3. Safe Value Access
*   **Bracket Access (`dict[key]`):** Accesses value directly. If the key does not exist, Python raises a **`KeyError`**.
*   **`.get(key, default)` Method:** Retrieves the value safely. If the key does not exist, it returns `None` (or a custom default value), preventing application crashes.

### 4. Modifying and Deleting Elements
*   **Addition / Update:** `dict[key] = value` or `dict.update({"key": value})`
*   **Deletion:**
    - `del dict[key]`: Removes the key-value pair. Throws `KeyError` if key is missing.
    - `dict.pop(key, default)`: Removes the key and returns its value. Safe if default is provided.

### 5. Iteration
Python provides three views for traversing dictionary contents:
*   `.keys()`: Iterates over the keys.
*   `.values()`: Iterates over the values.
*   `.items()`: Iterates over key-value tuples (most common for loops).

### 6. JSON ↔ Dictionary Conversion
Because JSON structure matches Python dictionary syntax, converting between the two is simple using Python's standard **`json`** library:
*   **`json.dumps(dict)`**: Serializes (dumps) a dictionary into a JSON string.
*   **`json.loads(json_string)`**: Deserializes (loads) a JSON string into a Python dictionary.

---

## Code Examples

### 1. Safe Access and Modification
```python
inventory = {"apples": 50, "bananas": 30}

# 1. Direct Access vs. Safe Access
# print(inventory["oranges"])  # Throws KeyError!

oranges_count = inventory.get("oranges", 0)  # Safe! Returns 0
print(f"Oranges: {oranges_count}")

# 2. Add and Update
inventory["oranges"] = 15
inventory.update({"apples": 45, "grapes": 20})

# 3. Deleting values safely
removed_apples = inventory.pop("apples", 0)
print(f"Removed apples: {removed_apples}")
print(f"Updated Inventory: {inventory}")
```

**Output:**
```text
Oranges: 0
Removed apples: 45
Updated Inventory: {'bananas': 30, 'oranges': 15, 'grapes': 20}
```

### 2. Dictionary Comprehension
```python
names = ["Alice", "Bob", "Charlie"]

# Generate a dictionary mapping name to its character length
name_lengths = {name: len(name) for name in names if len(name) > 3}
print(name_lengths)  # Output: {'Alice': 5, 'Charlie': 7}
```

### 3. JSON Serialization and Deserialization
```python
import json

# Python Dictionary representing a nested record
developer = {
    "name": "David",
    "skills": ["Python", "Flask", "PostgreSQL"],
    "active": True
}

# 1. Convert Dictionary to JSON String (Serialization)
json_string = json.dumps(developer, indent=2)
print("JSON String output:")
print(json_string)

# 2. Convert JSON String back to Dictionary (Deserialization)
parsed_dict = json.loads(json_string)
print("\nParsed Dictionary Type:", type(parsed_dict))
print("Skills list:", parsed_dict["skills"])
```

**Output:**
```text
JSON String output:
{
  "name": "David",
  "skills": [
    "Python",
    "Flask",
    "PostgreSQL"
  ],
  "active": true
}

Parsed Dictionary Type: <class 'dict'>
Skills list: ['Python', 'Flask', 'PostgreSQL']
```

---

## Summary
- **Dictionaries (`dict`)** are highly optimized key-value mappings where keys must be immutable.
- Use **`.get()`** to prevent `KeyError` crashes when accessing keys that may not exist.
- Use **`.pop()`** or **`del`** to delete entries.
- Use **`.items()`** to iterate over keys and values simultaneously.
- Use the **`json`** module (`dumps` / `loads`) to seamlessly translate between Python dictionaries and raw JSON text.

---

## Additional Resources
- [Python Documentation: Dictionaries Data Structure](https://docs.python.org/3/tutorial/datastructures.html#dictionaries)
- [Real Python: Dictionaries in Python: Basics and Usage](https://realpython.com/python-dicts/)
- [Real Python: Working With JSON Data in Python](https://realpython.com/python-json/)
