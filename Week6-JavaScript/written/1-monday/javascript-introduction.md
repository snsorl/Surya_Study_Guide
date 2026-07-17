# Introduction to JavaScript

## Learning Objectives
- Explain JavaScript's history, including Brendan Eich, Netscape, and its standardization to ECMAScript.
- Characterize JavaScript's behavior as a dynamic scripting language.
- Contrast running JavaScript in a browser environment versus Node.js.
- Explain JavaScript's single-threaded, synchronous execution model and how it handles asynchronous operations.

---

## Why This Matters
For years, Java has been our primary backend tool, offering strong static typing and compiled structure. As we cross over to frontend client interfaces, we transition to JavaScript—the native language of the web browser. Every modern web application relies on JavaScript to build interactive user interfaces, modify documents on the fly, and send asynchronous network requests without page reloads. Understanding JavaScript's dynamic core and single-threaded execution model is vital to writing robust frontends that integrate smoothly with our backend APIs.

---

## The Concept

### 1. History and Standardization (ECMAScript)
In 1995, Brendan Eich developed a language called **Mocha** (later renamed **LiveScript**, and finally **JavaScript**) in just 10 days at Netscape Communications. The goal was to create a lightweight scripting language that web designers could use to add simple interactivity to pages, contrasting with the heavier, compiled Java applets of the era.

To prevent browser makers (like Microsoft and Netscape) from splitting the language into incompatible versions, JavaScript was standardized in 1997 under the Geneva-based standards body Ecma International. The standardized language specification is called **ECMAScript**, while the actual implementation in browsers is referred to as **JavaScript**. Today, major standards updates (like **ES6** / **ECMAScript 2015**) introduce modern language syntax that browser engines implement.

### 2. Characteristics of JavaScript
JavaScript is a **high-level, interpreted, prototype-based, dynamic language**:
- **High-level**: The engine manages memory dynamically, shielding developers from manual memory tracking or garbage collection.
- **Interpreted**: Traditional JavaScript code is executed step-by-step from text files at runtime. Modern engines use **Just-In-Time (JIT) compilation** to compile scripts into native machine instructions on the fly for execution performance.
- **Dynamic**: Variable types are determined dynamically at runtime based on their value, rather than being explicitly declared during development.

### 3. Execution Runtimes: Browser vs. Node.js
Originally, JavaScript could only run inside browser environments. Today, JavaScript is executed across diverse runtime environments, primarily:

| Feature | Web Browser | Node.js |
|---|---|---|
| **Primary Use** | Frontend UI Interactivity | Backend Services & Tooling |
| **Global Object** | `window` | `global` |
| **API Support** | DOM (document), Fetch API, local storage | File system (`fs`), Network sockets (`http`), OS bindings |
| **Security Sandbox** | Strict sandboxing (cannot access local files or OS resources directly) | Full system access (can read/write files and execute OS processes) |

Both runtimes execute using JavaScript engines. The most famous engine is Google's **V8**, which powers Google Chrome and acts as the execution core for Node.js.

### 4. Execution Model: Synchronous vs. Asynchronous
JavaScript is a **single-threaded** language, meaning it contains a single call stack and executes exactly one line of code at a time. If a function is slow (such as a database query or network request), the execution thread blocks, causing the user interface to freeze.

To prevent blocking, JavaScript utilizes a non-blocking asynchronous execution loop:
1. **The Call Stack**: Sync execution runs here sequentially.
2. **Web APIs/Node APIs**: Async operations (e.g., `setTimeout`, `fetch`, database reads) are handed off to the runtime engine environment to execute in the background.
3. **The Callback Queue**: Once the background operation completes, its callback function is placed into this queue.
4. **The Event Loop**: The event loop constantly monitors the Call Stack. If the stack is completely empty, it pulls the next callback from the queue and pushes it onto the Call Stack to execute.

```
+-----------------------------------+
|            Call Stack             | (Synchronous execution)
+-----------------------------------+
                 │
                 ▼ (Async task handed off)
+-----------------------------------+
|      Browser / Node.js APIs       | (Runs task in background)
+-----------------------------------+
                 │
                 ▼ (Task completes)
+-----------------------------------+
|          Callback Queue           | (Holds completed callbacks)
+-----------------------------------+
                 │
                 ▼ (Event Loop detects empty Stack)
            [Event Loop] ---> Pushes to Call Stack
```

---

## Code Example

Below is a simple code snippet demonstrating how JavaScript executes synchronous tasks immediately while deferring asynchronous tasks via the Event Loop, even when the delay is set to 0 milliseconds:

```javascript
console.log("1. Starting script"); // Synchronous

// Asynchronous task (handed off to browser/node timer API)
setTimeout(() => {
    console.log("2. Async callback executing");
}, 0);

console.log("3. Finished script"); // Synchronous
```

**Expected Output in Console:**
```text
1. Starting script
3. Finished script
2. Async callback executing
```

*Explanation:* Even though the `setTimeout` delay is 0ms, its callback is placed in the Callback Queue. The event loop cannot push it onto the Call Stack until the synchronous script has fully completed and the stack is empty.

---

## Summary
- **Brendan Eich** created JavaScript at Netscape in 1995. Its official specification name is **ECMAScript**.
- JavaScript is **single-threaded** and dynamically typed, meaning variable types are resolved at runtime.
- Browser runtimes access web elements via the **DOM**, whereas **Node.js** runs on the server with direct OS and file system access.
- Non-blocking asynchronous code is enabled by handing tasks to runtime APIs, which return callbacks to the Call Stack via the **Event Loop**.

---

## Additional Resources
- [MDN Web Docs: A re-introduction to JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/A_re-introduction_to_javascript)
- [Ecma International: ECMAScript Specification](https://www.ecma-international.org/publications-and-standards/standards/ecma-262/)
- [JS Conf: What the heck is the event loop anyway? (Video)](https://www.youtube.com/watch?v=8aGhZQkoFbQ)
