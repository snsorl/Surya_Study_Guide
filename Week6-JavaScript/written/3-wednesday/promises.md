# Promises in JavaScript

## Learning Objectives
- Describe the three states of a Promise (Pending, Fulfilled, Rejected).
- Consume Promise states using `then()`, `catch()`, and `finally()`.
- Chain multiple Promises sequentially to resolve nested flows.
- Coordinate concurrent operations using `Promise.all` and `Promise.race`.
- Explain how Promises avoid legacy callback hell.

---

## Why This Matters
As asynchronous programs (like network calls, database queries, and timer tasks) scale, nesting callbacks inside other callbacks leads to unreadable, hard-to-maintain code blocks, commonly referred to as **Callback Hell**. ES6 introduced **Promises** to resolve this. A Promise acts as a placeholder for a value that is not yet available but will resolve in the future, providing a clean, chainable structure for managing asynchronous control flows.

---

## The Concept

### 1. Promise States
A Promise is a proxy object for a value not necessarily known when the promise is created. It exists in one of three mutually exclusive states:
- **Pending**: Initial state; the asynchronous operation is still executing.
- **Fulfilled**: The operation completed successfully. The promise holds a resolved value.
- **Rejected**: The operation failed. The promise holds a rejection reason (an error object).

```
                      +-------------------+
                      |      Pending      |
                      +-------------------+
                                │
            ┌───────────────────┴───────────────────┐
            ▼ (Successful)                          ▼ (Error)
+-----------------------+               +-----------------------+
|       Fulfilled       |               |       Rejected        |
+-----------------------+               +-----------------------+
|  Resolves with value  |               |  Rejects with reason  |
|  Triggers then()      |               |  Triggers catch()     |
+-----------------------+               +-----------------------+
```

Once a Promise is either fulfilled or rejected, its state is **settled** and cannot change.

### 2. Consuming Promises
To capture the result of a Promise, use these handler methods:
- **`then(resolvedCallback)`**: Executes when the Promise is fulfilled.
- **`catch(rejectedCallback)`**: Executes if the Promise is rejected. It intercepts errors thrown anywhere in the chain.
- **`finally(callback)`**: Executes when the Promise is settled (either fulfilled or rejected) to perform clean-up tasks (like turning off a loading spinner).

### 3. Chaining Promises
If a `then()` block returns a new Promise, subsequent `then()` blocks wait for it to resolve. This allows you to write sequential asynchronous steps without nesting:

```javascript
fetchUser()
    .then(user => fetchUserOrders(user.id))
    .then(orders => renderOrders(orders))
    .catch(error => console.error("Error in chain:", error));
```

### 4. Concurrent Promise Methods
If you need to coordinate multiple asynchronous operations in parallel, JavaScript provides concurrency methods:
- **`Promise.all([p1, p2, p3])`**: Executes all promises in parallel. It resolves only when **all** promises resolve. If **any** promise rejects, the entire operation rejects immediately.
- **`Promise.allSettled([p1, p2, p3])`**: Waits for all promises to settle and returns an array of objects describing the outcome of each promise.
- **`Promise.race([p1, p2])`**: Resolves or rejects as soon as the **first** promise in the array settles.

---

## Code Examples

### Creating and Consuming a Promise
```javascript
function checkStock(itemId) {
    return new Promise((resolve, reject) => {
        console.log("Checking warehouse database...");
        
        setTimeout(() => {
            const hasStock = Math.random() > 0.3; // 70% success rate
            
            if (hasStock) {
                resolve({ itemId: itemId, status: "IN_STOCK" });
            } else {
                reject(new Error(`Item ${itemId} is out of stock`));
            }
        }, 1000);
    });
}

// Consuming the promise
checkStock("ITEM-101")
    .then(result => {
        console.log("Success:", result);
    })
    .catch(error => {
        console.error("Failed:", error.message);
    })
    .finally(() => {
        console.log("Stock check complete.");
    });
```

---

## Summary
- A Promise is an object representing the eventual completion of an asynchronous task.
- Promises exist in one of three states: **Pending**, **Fulfilled**, or **Rejected**.
- Handle success with **`then()`**, errors with **`catch()`**, and cleanup with **`finally()`**.
- **`Promise.all`** coordinates parallel tasks but fails if *any* single task fails.
- Chaining `then()` statements flattens nested asynchronous flows, avoiding callback hell.

---

## Additional Resources
- [MDN Web Docs: Using Promises](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Using_promises)
- [JavaScript.info: Promise](https://javascript.info/promise-basics)
