# JavaScript Data Types

## Learning Objectives
- Identify and describe JavaScript's 7 primitive types.
- Contrast primitive types with reference types (objects, arrays, functions).
- Predict the output of the `typeof` operator for different types.
- Distinguish between `null` and `undefined` in code.

---

## Why This Matters
Java enforces strict static typing. If a variable is declared as an `int`, it cannot hold a `String`. JavaScript variables are dynamically typed containers that can hold any data type over their lifetime. Without a clear understanding of primitive boundaries and reference memory behaviors, you will introduce bugs when copying objects, modifying list data, or checking variable initialization states.

---

## The Concept

### 1. Primitive vs. Reference Types
In JavaScript, values are divided into two main categories: **Primitives** and **Reference Types (Objects)**.

```
                    +--------------------+
                    |     JavaScript     |
                    +--------------------+
                              │
            ┌─────────────────┴─────────────────┐
            ▼                                   ▼
+-----------------------+           +-----------------------+
|      Primitives       |           |    Reference Types    |
+-----------------------+           +-----------------------+
| - Stored by VALUE     |           | - Stored by REFERENCE |
| - Immutable           |           | - Mutable             |
| - string, number, etc |           | - object, array, func |
+-----------------------+           +-----------------------+
```

#### Primitives (Stored by Value)
Primitives are stored directly in the stack memory allocated to a variable. They are **immutable**, meaning the value itself cannot be changed. If you reassign a primitive variable, a new value is stored in memory; the original value is not mutated.

There are **7 Primitive Types** in JavaScript:
1. **String**: Represents textual data (e.g., `"Hello"`, `'World'`).
2. **Number**: Represents double-precision 64-bit binary format IEEE 754 values. Unlike Java, there is no separate `int` or `float` type; all numbers are floating-point.
3. **BigInt**: Represents integers with arbitrary precision, declared by appending `n` to the end of an integer literal (e.g. `9007199254740991n`).
4. **Boolean**: Represents logical values: `true` or `false`.
5. **Undefined**: Represents a variable that has been declared but has not yet been assigned a value.
6. **Null**: Represents a deliberate, intentional absence of any object value.
7. **Symbol**: Represents a unique, immutable identifier, often used as private object keys.

#### Reference Types (Stored by Reference)
Reference types are objects. The variable does not store the object data directly. Instead, it stores a **reference pointer** to the object's memory location in the heap.
- **Objects**: Key-value collections (e.g. `{ name: "John", age: 30 }`).
- **Arrays**: Ordered list collections (e.g. `[1, 2, 3]`).
- **Functions**: Executable code blocks.

### 2. The `typeof` Operator
The `typeof` operator evaluates the type of a value or expression, returning a string representation of its type.

| Expression | `typeof` Output | Note / Gotcha |
|---|---|---|
| `typeof "hello"` | `"string"` | |
| `typeof 42` | `"number"` | |
| `typeof 10n` | `"bigint"` | |
| `typeof true` | `"boolean"` | |
| `typeof undefined` | `"undefined"` | |
| `typeof null` | `"object"` | **Historic Bug!** Preserved for backward compatibility. |
| `typeof Symbol("id")`| `"symbol"` | |
| `typeof { a: 1 }` | `"object"` | |
| `typeof [1, 2]` | `"object"` | Arrays are specialized objects in JS. |
| `typeof function(){}`| `"function"` | Special subcategory returned for callable objects. |

---

## Code Examples

### Primitive Copy (By Value)
When you copy a primitive, a completely new copy of the value is created in memory. Changing the copy does not affect the original.

```javascript
let countA = 10;
let countB = countA; // Copied by value

countB = 20;

console.log(countA); // 10 (unaffected)
console.log(countB); // 20
```

### Reference Copy (By Reference)
When you copy an object, only the reference pointer is copied. Both variables now point to the same memory location in the heap. Changing properties via one variable affects both.

```javascript
let userA = { name: "Alice" };
let userB = userA; // Copied by reference pointer

userB.name = "Bob";

console.log(userA.name); // "Bob" (changed!)
console.log(userB.name); // "Bob"
```

---

## Summary
- JavaScript has **7 primitive types** which are immutable and stored by value.
- **Objects, Arrays, and Functions** are reference types stored as pointers in heap memory.
- `typeof null` yields `"object"`, which is a legacy language bug.
- To copy objects without copying references, you must clone them (which we will cover on later days).

---

## Additional Resources
- [MDN Web Docs: JavaScript data types and data structures](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Data_structures)
- [JavaScript.info: Primitives vs Objects](https://javascript.info/primitives-methods)
