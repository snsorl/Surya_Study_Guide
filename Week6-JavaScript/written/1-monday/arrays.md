# Arrays in JavaScript

## Learning Objectives
- Declare arrays and read/write elements using dynamic indexing.
- Manipulate arrays using standard queue/stack methods (`push`, `pop`, `shift`, `unshift`).
- Extract or modify array segments using `slice` and `splice`.
- Utilize functional array iterators (`map`, `filter`, `reduce`, `find`, `includes`).

---

## Why This Matters
Data lists are the lifeblood of web APIs. Whether fetching a list of active users, mapping product collections, or filtering logs, developers interact with arrays constantly. While Java relies on distinct types like `ArrayList`, `LinkedList`, or arrays, JavaScript uses a single, highly flexible `Array` object. JavaScript arrays can grow dynamically, hold multiple data types simultaneously, and provide powerful functional iteration methods that allow you to write clean transformations without verbose index loops.

---

## The Concept

### 1. Characteristics of JavaScript Arrays
- **Dynamic Size**: JavaScript arrays resize automatically. There is no need to declare a fixed size during initialization.
- **Heterogeneous**: A single array can hold elements of different data types (e.g. `[42, "hello", true, { key: "value" }]`).
- **Indexed**: Indices are zero-based, and elements are accessed using square brackets `[]`.

```javascript
let fruits = ["Apple", "Banana", "Cherry"];
console.log(fruits[0]); // "Apple"
console.log(fruits.length); // 3
```

### 2. Modifying Elements: Queue & Stack Operations
JavaScript arrays contain built-in methods allowing them to function as stacks or queues:

- **`push(element)`**: Adds an element to the **end** of the array. Returns the new length.
- **`pop()`**: Removes and returns the **last** element of the array.
- **`unshift(element)`**: Adds an element to the **beginning** of the array. Returns the new length.
- **`shift()`**: Removes and returns the **first** element of the array.

```
       +--- shift() <=== [ Array ] ===> push() ---+
       |                                          |
       +=== unshift() ===> [ Array ] <=== pop() --+
```

### 3. Extracting and Slicing Sections
- **`slice(start, end)`**: Returns a **shallow copy** of a portion of the array from `start` index to `end` index (exclusive). The original array is **not modified**.
- **`splice(start, deleteCount, item1, item2...)`**: Modifies the original array by deleting or replacing existing elements and/or adding new elements. This is a **destructive** operation.

### 4. Functional Iterators (Callbacks)
Modern JavaScript leverages functional programming patterns for list transformations:
- **`forEach(callback)`**: Executes a function on each element. Returns `undefined` (used for side-effects).
- **`map(callback)`**: Creates a **new array** containing the results of calling the function on every element.
- **`filter(callback)`**: Creates a **new array** containing elements that pass the truth test inside the callback.
- **`find(callback)`**: Returns the **first element** that passes the truth test, or `undefined` if none match.
- **`reduce(callback, initialValue)`**: Accumulates array values into a single output value (e.g. summing elements).
- **`includes(value)`**: Returns `true` if the array contains the specified value.

---

## Code Examples

### Stack/Queue Manipulation
```javascript
let todoList = ["Buy Milk"];

todoList.push("Call Support"); // ["Buy Milk", "Call Support"]
todoList.unshift("Emergency meeting"); // ["Emergency meeting", "Buy Milk", "Call Support"]

let urgentTask = todoList.shift(); // "Emergency meeting"
console.log(todoList); // ["Buy Milk", "Call Support"]
```

### Slice (Non-destructive) vs Splice (Destructive)
```javascript
let colors = ["Red", "Green", "Blue", "Yellow"];

// slice returns a copy of indices [1, 2]
let extracted = colors.slice(1, 3);
console.log(extracted); // ["Green", "Blue"]
console.log(colors);    // ["Red", "Green", "Blue", "Yellow"] (unchanged)

// splice removes 2 elements starting at index 1 and inserts "Orange"
colors.splice(1, 2, "Orange");
console.log(colors); // ["Red", "Orange", "Yellow"] (mutated!)
```

### Functional Iterators in Action
```javascript
let students = [
    { name: "John", score: 45 },
    { name: "Jane", score: 85 },
    { name: "Jake", score: 92 }
];

// 1. filter passing students (score >= 50)
let passing = students.filter(student => student.score >= 50);
console.log(passing); // Jane and Jake objects

// 2. map to extract names
let names = passing.map(student => student.name);
console.log(names); // ["Jane", "Jake"]

// 3. reduce to calculate total score of passing students
let totalScore = passing.reduce((sum, student) => sum + student.score, 0);
console.log(totalScore); // 177 (85 + 92)
```

---

## Summary
- Arrays are dynamic collections that can store mixed data types.
- Stack/Queue methods modify array boundaries: `push`/`pop` at the end, `shift`/`unshift` at the beginning.
- `slice` extracts sections safely, whereas `splice` alters the target array directly.
- Modern array methods like `map`, `filter`, and `reduce` take callback functions and avoid writing manual `for` loops.

---

## Additional Resources
- [MDN Web Docs: Array reference](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array)
- [JavaScript.info: Array methods](https://javascript.info/array-methods)
