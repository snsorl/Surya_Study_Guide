# Async/Await Keywords in JavaScript

## Learning Objectives
- Declare asynchronous execution contexts using the `async` keyword.
- Pause execution flow using the `await` keyword to resolve Promise values synchronously in appearance.
- Refactor standard Promise `.then()` chains to clean `async`/`await` structures.
- Implement structured exception handling with `try`/`catch`/`finally`.
- Optimize parallel operations using `Promise.all` combined with `await`.

---

## Why This Matters
While Promises resolve callback nesting, long `.then()` and `.catch()` chains can still become verbose and difficult to trace. ES2017 introduced **Async/Await** as a syntactic wrapper over Promises. It allows developers to write asynchronous code that reads like synchronous code, using standard language constructs like `try/catch` blocks for error handling and standard variables instead of callback parameter scopes.

---

## The Concept

### 1. The `async` and `await` Rules
- **`async`**: Applied to a function declaration. It tells JavaScript that the function contains asynchronous logic. An async function **always returns a Promise** (if you return a raw value, the engine wraps it in a resolved Promise automatically).
- **`await`**: Can only be used inside an `async` function. It pauses the execution of the function, waiting for the Promise to settle. Once settled, it returns the resolved value, or throws the rejection reason as a standard exception.

```javascript
// Example:
async function getData() {
    const data = await fetch("https://api.example.com"); // Pauses here until resolved
    return data;
}
```

### 2. Error Handling with `try/catch`
Instead of appending `.catch()` to handle failures, `async`/`await` utilizes standard JavaScript `try`/`catch`/`finally` blocks:

```javascript
async function load() {
    try {
        const data = await fetchData();
    } catch (error) {
        console.error("Caught error:", error.message);
    }
}
```

### 3. Converting Promise Chains to Async/Await
Compare how refactoring improves readability:

```javascript
// Promise Chain:
function loadUser() {
    getUser(1)
        .then(user => getProfile(user.id))
        .then(profile => console.log(profile))
        .catch(err => console.error(err));
}

// Async/Await Refactored:
async function loadUserAsync() {
    try {
        const user = await getUser(1);
        const profile = await getProfile(user.id);
        console.log(profile);
    } catch (err) {
        console.error(err);
    }
}
```

---

## Code Examples

### Managing Concurrent Operations Efficiently
A common mistake when using `async`/`await` is pausing execution sequentially when tasks could run in parallel:

```javascript
// 1. Slow Sequential Execution:
async function loadDashboardSlow() {
    const user = await fetchUser(); // Takes 1s
    const stats = await fetchStats(); // Takes 1s (total 2s)
    return { user, stats };
}

// 2. Optimized Parallel Execution (Promise.all):
async function loadDashboardFast() {
    try {
        // Trigger both requests in parallel
        const userPromise = fetchUser(); 
        const statsPromise = fetchStats();
        
        // Wait for both to complete
        const [user, stats] = await Promise.all([userPromise, statsPromise]);
        return { user, stats }; // Total 1s
    } catch (error) {
        console.error("Dashboard load failed:", error);
    }
}
```

---

## Summary
- Annotate functions with **`async`** to enable asynchronous execution; they always return a Promise.
- Use **`await`** to pause function execution until a Promise resolves, returning the resolved value directly.
- Wrap `await` lines in standard **`try/catch/finally`** blocks to handle failures cleanly.
- Use **`Promise.all()`** with `await` to run independent async operations in parallel, preventing sequential bottlenecks.

---

## Additional Resources
- [MDN Web Docs: async function](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/async_function)
- [MDN Web Docs: await operator](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/await)
- [JavaScript.info: Async/await](https://javascript.info/async-await)
