# Lists: Mutable Sequences and Comprehensions in Python

## Learning Objectives
- Define Python lists and explain their operational characteristics.
- Master index-based access, including negative indexing.
- Apply **list slicing** using the `[start:stop:step]` syntax.
- Utilize primary list methods for structural modification (`append`, `extend`, `insert`, `remove`, `pop`).
- Write idiomatic **list comprehensions** with inline filtering.
- Traverse and manipulate multi-dimensional nested lists.

---

## Why This Matters
In Java development, you rely on arrays and the `ArrayList` class for mutable ordered lists. Modifying, slicing, or filtering these collections in Java often requires verbose code, streams, collectors, or loop constructs.

Python provides **Lists** as a built-in, highly flexible type. Python lists can grow dynamically, store mixed data types, and support powerful built-in operations like negative indexing, slicing, and list comprehensions. List comprehensions allow you to map and filter lists in a single, highly readable line of code. Mastering lists is essential for handling collections, processing datasets, and writing idiomatic Python.

---

## The Concept

### 1. Structure and Indexing
A list is a mutable, ordered sequence of items enclosed in square brackets `[]`. 
*   **Zero-Based Indexing:** Accessing items from the beginning (`0` to `n-1`).
*   **Negative Indexing:** Accessing items from the end, starting at `-1` (e.g., `list[-1]` returns the last element, `list[-2]` returns the second to last).

### 2. List Slicing (`[start:stop:step]`)
Slicing extracts a sublist from a list. The syntax is: `list[start:stop:step]`
*   **`start`:** The index to begin the slice (inclusive). Defaults to 0.
*   **`stop`:** The index to end the slice (exclusive). Defaults to the end of the list.
*   **`step`:** The step increment (stride). Defaults to 1. Negative steps traverse the list backward (e.g., `list[::-1]` reverses the list).

### 3. Modifying Lists (Key Methods)
*   `.append(item)`: Adds a single item to the end.
*   `.extend(iterable)`: Appends all items from an iterable (e.g., another list) to the end.
*   `.insert(index, item)`: Inserts an item at a specific index.
*   `.remove(value)`: Removes the first occurrence of a value. Throws `ValueError` if the value is missing.
*   `.pop(index)`: Removes and returns the item at the specified index (defaults to the last item).

### 4. List Comprehensions
List comprehensions provide a concise way to create lists. They replace standard `for` loops that build lists line-by-line.
- *Syntax:* `[expression for item in iterable if condition]`
- *Java equivalence:* Equivalent to using `.stream().filter(...).map(...).collect(Collectors.toList())` but with less syntax overhead.

---

## Code Examples

### 1. Indexing and Slicing Demo
```python
fruits = ["apple", "banana", "cherry", "date", "elderberry"]

# 1. Negative indexing
print(f"Last item: {fruits[-1]}")        # Output: elderberry
print(f"Second to last: {fruits[-2]}")   # Output: date

# 2. Slicing variations
print(f"Index 1 to 3: {fruits[1:4]}")    # Output: ['banana', 'cherry', 'date']
print(f"First three: {fruits[:3]}")      # Output: ['apple', 'banana', 'cherry']
print(f"Every second item: {fruits[::2]}") # Output: ['apple', 'cherry', 'elderberry']
print(f"Reversed list: {fruits[::-1]}")   # Output: ['elderberry', 'date', 'cherry', 'banana', 'apple']
```

### 2. Modifying Lists (`append` vs. `extend`)
```python
numbers = [1, 2, 3]

# Append: Adds the object as a single element
numbers.append([4, 5])
print("After append:", numbers) # Output: [1, 2, 3, [4, 5]] (nested list!)

# Reset list and use extend
numbers = [1, 2, 3]
numbers.extend([4, 5])
print("After extend:", numbers) # Output: [1, 2, 3, 4, 5] (merged lists)

# Pop element
popped_val = numbers.pop(1)
print(f"Popped: {popped_val}, List now: {numbers}") # Output: Popped: 2, List now: [1, 3, 4, 5]
```

### 3. List Comprehensions vs. Traditional Loops
Consider generating a list of squared even numbers from 0 to 9:

#### The Traditional Loop Approach:
```python
squares = []
for x in range(10):
    if x % 2 == 0:
        squares.append(x ** 2)
print("Loop output:", squares) # Output: [0, 4, 16, 36, 64]
```

#### The Idiomatic List Comprehension:
```python
squares_comp = [x ** 2 for x in range(10) if x % 2 == 0]
print("Comp output:", squares_comp) # Output: [0, 4, 16, 36, 64]
```

---

## Summary
- **Lists** are mutable, ordered sequences that support negative indexing (from the end).
- **Slicing (`[start:stop:step]`)** is a powerful mechanism for extracting sublists or reversing sequences.
- Use **`.append()`** to add an item, and **`.extend()`** to unpack and append an entire collection.
- **List comprehensions** allow mapping and filtering in a single line, following standard Pythonic style rules.

---

## Additional Resources
- [Python Documentation: Data Structures - More on Lists](https://docs.python.org/3/tutorial/datastructures.html#more-on-lists)
- [Real Python: Lists and Tuples in Python](https://realpython.com/python-lists-tuples/)
- [Real Python: Using List Comprehensions in Python](https://realpython.com/list-comprehension-python/)
