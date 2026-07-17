# Functions in JavaScript

## Learning Objectives
- Contrast Function Declarations and Function Expressions.
- Implement functions using default parameters and rest parameters.
- Define what it means for functions to be first-class citizens.
- Pass functions as arguments and return them from other functions.

---

## Why This Matters
In Java, execution logic is bound inside class structures (methods). You cannot pass a raw method as an argument without wrapping it in an interface class or lambda representation. JavaScript treats functions as **first-class citizens**. This means functions can be stored in variables, passed as arguments, and returned from other functions. First-class functions enable powerful design patterns like callback handlers, closures, and higher-order functions that drive modern asynchronous event-driven web programming.

---

## The Concept

### 1. Function Declaration vs. Function Expression
There are two primary ways to define a function in JavaScript:

#### Function Declaration
Declared using the `function` keyword followed by the function name.
```javascript
function greet(name) {
    return "Hello, " + name;
}
```
*Behavior:* Function declarations are **hoisted** to the top of their enclosing scope, meaning you can call them *before* they appear in the source code.

#### Function Expression
Defined by creating a function and assigning it to a variable.
```javascript
const greet = function(name) {
    return "Hello, " + name;
};
```
*Behavior:* Function expressions are **not hoisted** in the same way; you cannot call them before the line of code that assigns them, resolving the Temporal Dead Zone (TDZ).

### 2. Parameters vs. Arguments
- **Parameters**: The variable names listed in the function definition (e.g. `name`).
- **Arguments**: The actual values passed to the function when it is invoked (e.g. `"Alice"`).
- *Flexibility:* JavaScript does not throw compile errors if you pass fewer or more arguments than parameters declared. Missing arguments are assigned `undefined`, and extra arguments are ignored (but remain accessible inside the legacy `arguments` object).

### 3. Modern Parameter Syntaxes
- **Default Parameters**: Assign default values to parameters if they are missing or passed as `undefined`.
  ```javascript
  function welcome(user = "Guest") {
      return "Welcome, " + user;
  }
  ```
- **Rest Parameters (`...`)**: Groups a variable number of arguments into a single array parameter. Must be the last parameter in the function signature.
  ```javascript
  function sumAll(...numbers) {
      return numbers.reduce((total, n) => total + n, 0);
  }
  ```

### 4. First-Class Functions
JavaScript functions are objects. This allows them to be used in the following ways:
1. **Stored in Variables**:
   ```javascript
   const logMsg = console.log;
   logMsg("Direct log mapping");
   ```
2. **Passed as Arguments (Callbacks)**:
   ```javascript
   function performAction(callback) {
       callback();
   }
   ```
3. **Returned from Other Functions (Higher-Order Functions)**:
   ```javascript
   function createMultiplier(factor) {
       return function(number) {
           return number * factor;
       };
   }
   ```

---

## Code Examples

### Default and Rest Parameters
```javascript
function sendAlert(message, level = "INFO", ...recipients) {
    console.log(`[${level}] ${message}`);
    console.log(`Sending to: ${recipients.join(", ")}`);
}

sendAlert("System Reboot", undefined, "admin@test.com", "devops@test.com");
// Output:
// [INFO] System Reboot
// Sending to: admin@test.com, devops@test.com
```

### Higher-Order Function (Functions Returning Functions)
```javascript
function makeGreeting(greetingWord) {
    return function(name) {
        return `${greetingWord}, ${name}!`;
    };
}

const sayHello = makeGreeting("Hello");
const sayGoodbye = makeGreeting("Goodbye");

console.log(sayHello("Alice"));   // "Hello, Alice!"
console.log(sayGoodbye("Bob"));    // "Goodbye, Bob!"
```

---

## Summary
- **Function Declarations** are hoisted; **Function Expressions** are not and are treated as variable assignments.
- **Default parameters** prevent missing inputs from resolving to `undefined`.
- **Rest parameters (`...`)** gather list arguments into a single array.
- **First-class functions** allow functions to be passed, stored, and returned dynamically, forming the foundation of callbacks and closures.

---

## Additional Resources
- [MDN Web Docs: Functions reference](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Functions)
- [JavaScript.info: Function expressions](https://javascript.info/function-expressions)
