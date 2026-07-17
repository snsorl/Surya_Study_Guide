# Variables and Scopes in JavaScript

## Learning Objectives
- Compare the differences between `var`, `let`, and `const`.
- Define Global, Function (Local), and Block scopes.
- Explain Variable Hoisting and how it behaves for different declarations.
- Define the Temporal Dead Zone (TDZ) and its impact on code execution.

---

## Why This Matters
For years, JavaScript only had `var` variables. Because `var` does not respect block boundaries (like `if` statements or loops) and can be accessed before it is declared, developers frequently introduced bugs. For instance, variables inside loops leaked out and polluted other routines, and missing declarations failed silently. The introduction of `let` and `const` in ES6 resolved these issues by adding block scoping. Understanding how scopes, hoisting, and the Temporal Dead Zone behave is essential for writing predictable code and avoiding variable pollution.

---

## The Concept

### 1. The Scope Hierarchy
Scope determines where variables are accessible in your code. JavaScript has three scope levels:
- **Global Scope**: Variables declared outside of any function or block. They are accessible everywhere.
- **Function Scope**: Variables declared inside a function are local to that function and cannot be accessed outside.
- **Block Scope**: Variables declared inside a block `{}` (like `if` statements or `for` loops) are accessible only inside that block.

```
+-------------------------------------------------+
| GLOBAL SCOPE                                    |
|   +-------------------------------------------+ |
|   | FUNCTION SCOPE (Local)                    | |
|   |   +-------------------------------------+ | |
|   |   | BLOCK SCOPE {}                      | | |
|   |   +-------------------------------------+ | |
|   +-------------------------------------------+ |
+-------------------------------------------------+
```

### 2. Comparison: `var` vs. `let` vs. `const`

| Feature | `var` | `let` | `const` |
|---|---|---|---|
| **Scope Level** | Function Scope | Block Scope | Block Scope |
| **Hoisting Behavior** | Hoisted (initializes as `undefined`) | Hoisted (enters Temporal Dead Zone) | Hoisted (enters Temporal Dead Zone) |
| **Re-declaration** | Allowed | Throws `SyntaxError` | Throws `SyntaxError` |
| **Re-assignment** | Allowed | Allowed | Throws `TypeError` |

### 3. Hoisting Explained
Hoisting is a behavior where the JavaScript engine moves variable and function declarations to the top of their enclosing scope during the compilation phase, before execution begins.
- When `var` is hoisted, it is initialized with `undefined`. You can access it before its declaration line without throwing an error:
  ```javascript
  console.log(x); // prints undefined
  var x = 5;
  ```
- When `let` and `const` are hoisted, they are **not initialized**. They enter the **Temporal Dead Zone (TDZ)**.

### 4. The Temporal Dead Zone (TDZ)
The TDZ is the period between the start of the block scope and the line where the variable is explicitly declared. Accessing the variable within this zone throws a `ReferenceError`.

```javascript
{
    // === START OF BLOCK SCOPE ===
    // TDZ for score begins here
    
    // console.log(score); // Throws ReferenceError: Cannot access 'score' before initialization
    
    let score = 100; // TDZ ends here
    console.log(score); // Prints 100
}
```

---

## Code Examples

### Block Scope Violation with `var`
```javascript
if (true) {
    var status = "active";
    let count = 10;
}

console.log(status); // "active" (var leaked outside the block!)
// console.log(count); // Throws ReferenceError (let is safely block-scoped)
```

### Constant Object Immutability
A `const` declaration prevents **reassigning** the variable identifier, but it does **not** make the object values immutable. You can modify properties of a constant object or array.

```javascript
const user = { name: "Alice" };

// 1. Changing properties is ALLOWED (object is mutated, reference pointer remains same)
user.name = "Bob"; 
console.log(user.name); // "Bob"

// 2. Reassigning the variable is BLOCKED
// user = { name: "Charlie" }; // Throws TypeError: Assignment to constant variable
```

---

## Summary
- `var` is **function-scoped** and hoisted as `undefined`, which can cause silent declaration errors.
- `let` and `const` are **block-scoped** and respect `{}` boundaries.
- The **Temporal Dead Zone (TDZ)** prevents accessing `let` or `const` variables before their declaration line.
- `const` prevents variable reassignment, but allows modification of properties inside objects and arrays.

---

## Additional Resources
- [MDN Web Docs: Block scope](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/block)
- [MDN Web Docs: Temporal Dead Zone](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/let#temporal_dead_zone_tdz)
- [JavaScript.info: Hoisting & Scopes](https://javascript.info/var)
