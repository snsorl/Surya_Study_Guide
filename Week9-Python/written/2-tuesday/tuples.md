# Tuples: Immutable Sequences in Python

## Learning Objectives
- Define tuples and explain their structural and operational properties.
- Apply tuple packing and unpacking techniques, including standard wildcard unpacking.
- Write single-element tuples correctly by using trailing commas.
- Declare and utilize named tuples (`collections.namedtuple`) for self-documenting records.
- Compare and contrast when to use tuples versus lists.
- Analyze the conditions under which a tuple can be used as a dictionary key.

---

## Why This Matters
In Java, grouping a fixed set of heterogeneous values usually requires creating a custom class or using array lists, which carry significant overhead. 

Python provides **Tuples**, an extremely lightweight, immutable sequence type. Tuples allow developers to group data together quickly without the boilerplate of classes. Because they are immutable, tuples are write-protected, thread-safe, and hashable (under certain conditions), making them suitable for use as lookup keys in dictionaries. Understanding when to use a mutable container (like a list) versus an immutable record (like a tuple) is key to writing high-performance, safe Python applications.

---

## The Concept

### 1. What is a Tuple?
A tuple is an ordered sequence of objects. Tuples are written with parentheses `()` containing elements separated by commas:
```python
point = (10, 20)
```
Tuples share indexing and slicing operations with lists, but they are **immutable**. Once created, you cannot append, remove, or modify elements in-place.

### 2. Single-Element Tuples (The Trailing Comma)
Because parentheses are also used for grouping expressions (e.g., `(2 + 3) * 5`), defining a tuple with a single element requires a trailing comma:
*   `not_a_tuple = (42)`  # Evaluates to the integer 42
*   `a_tuple = (42,)`     # Evaluates to a tuple containing one integer element

### 3. Tuple Packing and Unpacking
*   **Packing:** Assigning multiple comma-separated values to a single variable packs them into a tuple automatically.
    - `data = "Alice", 25, "Engineer"` (parentheses are optional during assignment)
*   **Unpacking:** Extracting values from a tuple back into individual variables.
    - `name, age, role = data`
*   **Wildcard Unpacking:** Using the asterisk `*` operator to capture remaining elements into a list:
    - `first, *middle, last = (1, 2, 3, 4, 5)`  # `middle` becomes `[2, 3, 4]`

### 4. Named Tuples (`collections.namedtuple`)
Standard tuples require you to access fields via numerical indices (e.g., `user[0]`), which is error-prone. 
The `collections` module provides `namedtuple`, which creates a subclass of tuple where fields are accessible both by index and by descriptive name:
```python
from collections import namedtuple
Point = namedtuple("Point", ["x", "y"])
p = Point(10, 20)
print(p.x, p.y)  # Self-documenting!
```

### 5. Tuples vs. Lists: When to Use Which?
*   **Use Lists** when you have a homogeneous collection of elements that change dynamically during execution (e.g., adding to-do items to a list).
*   **Use Tuples** when you have a heterogeneous record of values that represents a single entity and should not change (e.g., a database row: `(user_id, username, email)`).
*   *Performance Note:* Tuples are slightly faster and occupy less memory than lists because Python can optimize their storage size statically.

### 6. Tuples as Dictionary Keys
A dictionary key must be **hashable** (its hash value must remain constant throughout its lifetime). 
- If a tuple contains only hashable items (like strings, numbers, or other tuples), the tuple is hashable and **can** be used as a dictionary key.
- If a tuple contains any mutable elements (like a nested list: `(1, 2, [3, 4])`), it is **not** hashable and will raise a `TypeError` if used as a dictionary key.

---

## Code Examples

### 1. Packing, Unpacking, and Single-Element Definition
```python
# Single-element tuple verification
item_a = (10)
item_b = (10,)
print(f"item_a type: {type(item_a)}, item_b type: {type(item_b)}")

# Tuple Packing and Unpacking
employee_record = "Bob", 30, "Sales"  # Packing
name, age, department = employee_record # Unpacking
print(f"{name} is {age} years old.")

# Wildcard unpacking
numbers = (1, 2, 3, 4, 5)
first, *rest = numbers
print(f"First: {first}, Rest: {rest}")  # Rest becomes a list [2, 3, 4, 5]
```

**Output:**
```text
item_a type: <class 'int'>, item_b type: <class 'tuple'>
Bob is 30 years old.
First: 1, Rest: [2, 3, 4, 5]
```

### 2. Using `namedtuple`
```python
from collections import namedtuple

# Define the named tuple structure
Employee = namedtuple("Employee", ["id", "name", "role"])

# Instantiate
emp = Employee(101, "Charlie", "QA Engineer")

# Read using dot notation
print(f"Employee name: {emp.name}, Role: {emp.role}")
# Read using index
print(f"Employee ID from index: {emp[0]}")
```

### 3. Tuples as Dictionary Keys (Hashability Check)
```python
# 1. Valid: Tuple containing hashable integers
locations = {
    (40.7128, -74.0060): "New York",
    (34.0522, -118.2437): "Los Angeles"
}
print(locations[(40.7128, -74.0060)]) # Works!

# 2. Invalid: Tuple containing a mutable list
try:
    bad_key = (1, 2, [3, 4])
    invalid_dict = {bad_key: "failure"}
except TypeError as e:
    print(f"Error caught: {e}")
```

**Output:**
```text
New York
Error caught: unhashable type: 'list'
```

---

## Summary
- **Tuples** are immutable, ordered collections.
- Single-element tuples require a **trailing comma** (e.g., `(item,)`).
- **Tuple unpacking** maps elements to individual variables; wildcard unpacking (`*`) aggregates remaining items.
- **`namedtuple`** improves code readability by enabling dot-notation attribute access.
- Tuples are ideal for heterogeneous records; lists are ideal for homogeneous, mutable collections.
- Tuples are hashable and can be **dictionary keys** as long as all elements inside them are immutable.

---

## Additional Resources
- [Python Documentation: Tuples and Sequences](https://docs.python.org/3/tutorial/datastructures.html#tuples-and-sequences)
- [Real Python: Defining and Using Tuples in Python](https://realpython.com/python-lists-tuples/#python-tuples)
- [Python Documentation: `collections.namedtuple`](https://docs.python.org/3/library/collections.html#collections.namedtuple)
