# JavaScript Fundamentals Demo Read-Along

## Learning Objectives
- Trace variables, datatypes, and coercion behaviors shown during the live demonstration.
- Identify syntax choices for arrays and functions.
- Inspect how scoping differences manifest in execution.

---

## Why This Matters
Live demos move quickly, and it is easy to miss key syntax choices or structural patterns during a screen share. This document acts as your annotated reference companion. It walks through the exact code blocks executed in the Day 1 demo, highlighting why specific declarations, comparisons, and method calls were chosen.

---

## The Guided Demo Script

### 1. Variables and Scoping Mappings
In the demo, the instructor compared `var`, `let`, and `const` inside a block to highlight scoping boundaries.

```javascript
// Variable Declarations
var user = "Alice";
let score = 90;
const databaseUrl = "jdbc:postgresql://localhost:5432/mydb";

if (true) {
    var blockVar = "Visible outside block";
    let blockLet = "Invisible outside block";
    console.log(blockLet); // Prints fine inside
}

console.log(blockVar); // Prints "Visible outside block"
// console.log(blockLet); // Throws ReferenceError: blockLet is not defined
```

*Key Syntax Choice:* We use `const` for configurations like database URLs to prevent accidental reassignment. We prefer `let` over `var` to contain variables inside code blocks (like `if` statements or `for` loops), preventing variables from leaking into the outer scope.

### 2. Primitive Datatypes and `typeof` Operator
The demo output showed the core datatypes recognized by JavaScript.

```javascript
let name = "John";        // string
let count = 42;           // number
let isActive = true;      // boolean
let emptyValue = null;    // object (historic JavaScript bug!)
let undefinedValue;       // undefined

console.log(typeof name);           // "string"
console.log(typeof count);          // "number"
console.log(typeof isActive);       // "boolean"
console.log(typeof emptyValue);     // "object"
console.log(typeof undefinedValue);  // "undefined"
```

*Historical Note:* The fact that `typeof null` returns `"object"` is a historic bug from the first version of JavaScript, preserved for backward compatibility. It should represent `null` indicating a deliberate absence of value.

### 3. Coercion and Comparison Surprises
The instructor demonstrated how loose equality (`==`) forces type conversion, whereas strict equality (`===`) protects type boundaries.

```javascript
let targetPrice = "100";
let currentPrice = 100;

console.log(targetPrice == currentPrice);  // true (string coerced to number)
console.log(targetPrice === currentPrice); // false (different types)

// Falsy value check
let userInput = ""; // Empty string is falsy
if (!userInput) {
    console.log("Input is empty!"); // Executes
}
```

*Key Takeaway:* Always default to using strict equality (`===`) in your code. This forces JavaScript to check both the **value** and the **type**, preventing silent coercion bugs that are extremely hard to debug.

### 4. Array Methods and Callback Functions
We demonstrated array methods that accept callback functions to transform elements.

```javascript
let items = ["Apples", "Bananas", "Cherries"];

// 1. push adds to the end
items.push("Dates");

// 2. map returns a transformed array
let capitalizedItems = items.map(function(item) {
    return item.toUpperCase();
});

console.log(capitalizedItems); // ["APPLES", "BANANAS", "CHERRIES", "DATES"]
```

---

## Summary
- We prefer `let` and `const` to enforce **block scoping**, preventing `var` variables from leaking into global contexts.
- Strict equality (`===`) prevents implicit type conversions during comparison.
- Array methods like `map` are **declarative** and accept callback functions to transform data collections.

---

## Additional Resources
- [MDN Web Docs: Equality comparisons and sameness](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Equality_comparisons_and_sameness)
- [Lydia Hallie: JavaScript Visualized (Scoping & Engines)](https://dev.to/lydiahallie/javascript-visualized-scope-chain-13hc)
