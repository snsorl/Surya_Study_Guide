# Lab: ES6+ Syntax Refactoring

## Core Tasks
1. Open `starter_code/legacy-employees.js`.
2. **Refactor Scopes**: Replace all `var` declarations with `const` or `let` depending on whether they need to be reassigned.
3. **Refactor OOP to Classes**: Convert the `Employee` constructor function and its prototype method `getDetails` into a clean ES6 `class` definition. Add a getter for `fullName` and validation checks in the constructor.
4. **Refactor String Formatting**: Replace string concatenations (`+`) inside your methods with template literals using backticks and expression interpolation (`${}`).
5. **Refactor Function Signatures**: Convert the `calculateTotalPayroll` function expression into an arrow function. Replace the manual `for` loop with the functional `.reduce()` array method.
6. Enable **Strict Mode** by adding `"use strict";` at the top of your file to catch declaration leaks.

---

## Technical Guidelines
- Ensure your class declaration contains a constructor that validates that `salary` is a positive number.
- Ensure arrow functions utilize concise implicit returns where applicable.

---

## Definition of Done
- The refactored code has zero compile or syntax warnings.
- The output logged to the console remains unchanged, but uses clean ES6 template literal rendering.
- No `var` keywords remain in the source file.
- The manual index `for` loop inside `calculateTotalPayroll` is replaced by `.reduce()`.
