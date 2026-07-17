# JavaScript Strict Mode

## Learning Objectives
- Enable Strict Mode globally or within specific function scopes.
- Identify the coding practices flagged as runtime errors in strict mode.
- Explain how strict mode alters the behavior of the `this` keyword in plain functions.
- Recognize that modern modules (ES Modules) enforce strict mode by default.

---

## Why This Matters
JavaScript was originally designed to fail silently. For example, if you misspelled a variable name during assignment, instead of throwing an error, JavaScript would automatically create a new global variable with the misspelled name. These silent failures make debugging extremely difficult and introduce security flaws. **Strict Mode** (introduced in ECMAScript 5) changes JavaScript's runtime behavior to throw explicit errors for these silent failures, enforcing safer, cleaner code patterns.

---

## The Concept

### 1. Activating Strict Mode
To opt into strict mode, add the literal string `"use strict";` at the very beginning of a file or function body:

- **Global File Scope**:
  ```javascript
  "use strict";
  // The entire file runs in strict mode
  let x = 10;
  ```
- **Function Scope**:
  ```javascript
  function processData() {
      "use strict";
      // Only this function runs in strict mode
  }
  ```

*Modern Note:* With the introduction of ES Modules (`<script type="module">` or `import`/`export` files), strict mode is **enabled automatically** by default without declaring `"use strict";`.

### 2. Key Restrictions Enforced in Strict Mode

#### A. Disallow Undeclared Variable Assignment
In non-strict mode, assigning a value to an undeclared variable implicitly creates a global variable:
```javascript
// Non-strict:
mispelledVar = 100; // Automatically registers as window.mispelledVar = 100
```
In strict mode, this immediately throws a `ReferenceError`.
```javascript
// Strict:
"use strict";
mispelledVar = 100; // ReferenceError: mispelledVar is not defined
```

#### B. Eliminates `this` Coercion to Global
In non-strict mode, if a plain function is invoked without a calling context, `this` defaults to the global `window` object:
```javascript
function showThis() {
    console.log(this); // window
}
showThis();
```
In strict mode, `this` is set to `undefined`, preventing accidental modifications to global parameters:
```javascript
"use strict";
function showThisStrict() {
    console.log(this); // undefined
}
showThisStrict();
```

#### C. Prevent Duplicate Parameter Names
Strict mode throws a syntax error if a function declares duplicate parameter names:
```javascript
"use strict";
// Throws SyntaxError: Duplicate parameter name not allowed in this context
function compute(a, b, a) { 
    return a + b;
}
```

---

## Code Examples

### Safe Object Properties Protection
In strict mode, attempting to write values to read-only properties or non-writable descriptor attributes will raise a runtime error, whereas in non-strict mode, they fail silently:

```javascript
"use strict";

const config = {};
Object.defineProperty(config, "databasePort", {
    value: 5432,
    writable: false // Read-only property
});

// config.databasePort = 8080; 
// Throws TypeError: Cannot assign to read only property 'databasePort' of object
```

---

## Summary
- Activate strict mode using the `"use strict";` directive at the top of scripts or functions.
- Modern **ES Modules** execute in strict mode by default.
- Strict mode throws errors for implicit global variable creations, helping catch spelling errors.
- Strict mode prevents `this` from default-binding to the global `window` object in standard functions.

---

## Additional Resources
- [MDN Web Docs: Strict mode reference](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode)
- [JavaScript.info: Strict mode](https://javascript.info/strict-mode)
