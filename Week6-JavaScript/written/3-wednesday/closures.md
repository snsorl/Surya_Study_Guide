# Closures in JavaScript

## Learning Objectives
- Define a Closure in JavaScript.
- Explain how inner functions retain access to their outer lexical environment.
- Implement data encapsulation and private variables using closures.
- Trace closures inside event handlers and factory functions.
- Identify and resolve closure loops bugs.

---

## Why This Matters
In Java, variable access is protected by keywords like `private`, `protected`, and `public`. JavaScript classes did not traditionally have native private fields (until recently). Instead, JavaScript engineers use **Closures** to achieve encapsulation. A closure allows an inner function to access variables from its outer enclosing function even after the outer function has completed execution. Understanding closures is key to managing private application state, writing factory functions, and designing custom state containers.

---

## The Concept

### 1. What is a Closure?
A closure is the combination of a function bundled together with references to its surrounding state (its **lexical environment**).
Whenever a function is created in JavaScript, a closure is created. The function "remembers" the variables that were in scope when it was defined, regardless of where the function is executed later.

```javascript
function outer() {
    let outerVar = "I am from outer scope";
    
    function inner() {
        console.log(outerVar); // Inner function can access outerVar
    }
    
    return inner;
}

const myFunc = outer(); // outer() executes and returns the inner function
myFunc(); // "I am from outer scope" (even though outer() has finished executing!)
```

### 2. Primary Use Cases

#### A. Data Encapsulation (Private Variables)
You can hide state variables inside a function and expose only specific methods to read or write them, preventing global code pollution:
```javascript
function createCounter() {
    let count = 0; // Private state variable
    
    return {
        increment() { count++; return count; },
        decrement() { count--; return count; },
        getCount() { return count; }
    };
}
```

#### B. Factory Functions
Functions that customize and return other specialized functions based on arguments passed:
```javascript
function makeAdder(x) {
    return function(y) {
        return x + y;
    };
}
const add5 = makeAdder(5);
console.log(add5(3)); // 8
```

---

## Code Examples

### The Classical Loop Gotcha
Before ES6 `let` existed, using `var` inside loops combined with asynchronous callbacks resulted in a famous closure bug:

```javascript
// Buggy Legacy Pattern (var):
for (var i = 1; i <= 3; i++) {
    setTimeout(function() {
        console.log("var val: " + i); 
    }, 100);
}
// Outputs: 4, 4, 4
// Why: var is function-scoped. The callback functions close over the same shared variable 'i'.
// By the time the callbacks run, 'i' has been incremented to 4.

// Correct Modern Pattern (let):
for (let j = 1; j <= 3; j++) {
    setTimeout(function() {
        console.log("let val: " + j);
    }, 100);
}
// Outputs: 1, 2, 3
// Why: 'let' is block-scoped. A new variable binding is created for every loop iteration,
// so each callback closes over its own unique, immutable index value.
```

---

## Summary
- A **closure** is created when an inner function references variables declared in its outer lexical scope.
- Inner functions **retain access** to these variables even after the parent function has finished executing.
- Closures are widely used for **data encapsulation**, simulating private class properties.
- Use **`let`** inside loops to prevent asynchronous closures from sharing the same variable reference.

---

## Additional Resources
- [MDN Web Docs: Closures](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Closures)
- [JavaScript.info: Variable scope, closure](https://javascript.info/closure)
