# Lab: JavaScript Fundamentals

## Core Tasks
1. Navigate to the `starter_code/` directory and open `app.js`.
2. **Task 1: Functional Array Transformations**: Implement the empty functions (`getActiveStudentNames`, `hasFailingStudents`, `getActiveAverageScore`) using functional array methods (`filter`, `map`, `reduce`, `find`). Do **not** use manual `for` loops.
3. **Task 2: Coercion & Comparison**: Add console log statements to `verifyComparisons()` comparing loose (`==`) and strict (`===`) equality, logging the results to the console.
4. **Task 3: Variable Scope Audits**: Run the `runScopeAudit()` function and observe which variables are accessible outside the block scope.

---

## Technical Guidelines
- Use modern `const` and `let` declarations inside your functions where applicable.
- In Task 1, ensure your calculation code handles empty input lists gracefully by returning `0`.
- Use your browser console or run the file with Node to inspect console messages during development.

---

## Definition of Done
- `getActiveStudentNames` returns `["Alice", "Charlie", "David"]`.
- `hasFailingStudents` returns `true` (capturing David with a score of 55).
- `getActiveAverageScore` returns `77.33` (average of 92, 85, and 55).
- Loose vs strict comparison printouts match the theoretical rules defined in `written/1-monday/type-coercion.md`.
- Scope log outputs demonstrate that `var` variables leak outside blocks while `let`/`const` throw a `ReferenceError`.
