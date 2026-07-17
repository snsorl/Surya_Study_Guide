# Exercise: Encapsulation with Closures

## Core Tasks
Build a factory function that models private state encapsulation using closures.

1. Create a script file `closures-exercise.js`.
2. Write a function named `createCounter(counterName)` that:
   - Contains a private variable `count = 0`.
   - Returns an object with three methods:
     - **`increment()`**: Increments `count` by 1 and returns the new value.
     - **`decrement()`**: Decrements `count` by 1 and returns the new value.
     - **`status()`**: Prints to the console: `"[Name] current count: X"`.
3. Instantiate 3 separate counters (e.g., `counterA`, `counterB`, `counterC`).
4. Execute increment/decrement commands across them in varying patterns (e.g. increment A twice, B once). Run status calls to demonstrate that they manage their own private in-memory scopes independently without state leakage.

---

## Technical Guidelines
- Do **not** store counter state variables globally. They must exist entirely inside the local scope of `createCounter`.
- Verify execution outputs using Node.

---

## Definition of Done
- A script file executes 3 independent counters.
- Executing updates on `counterA` does not modify counts in `counterB` or `counterC`.
- Attempts to access the `count` variable directly from the outside (e.g. `counterA.count`) return `undefined`, verifying successful encapsulation.
