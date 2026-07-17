# Node.js as a JavaScript Runtime

## Learning Objectives
- Describe the architecture of Node.js, including the V8 Engine and Event Loop.
- Compare CommonJS modules (`require`/`module.exports`) and ES Modules (`import`/`export`).
- Execute TypeScript files directly in a development environment using `ts-node`.
- Navigate the npm package ecosystem.

---

## Why This Matters
For years, JavaScript could only run inside browser environments. Node.js changed this, allowing JavaScript to run directly on server operating systems. This opened the door for JavaScript-based backend development, build tools, and package managers. This guide covers how Node.js executes JavaScript on the server, compares standard module systems, and explains how to run TypeScript files directly in development using `ts-node`.

---

## The Concept

### 1. Node.js Architecture
Node.js is an open-source, cross-platform JavaScript runtime built on Google Chrome's **V8 engine**.
- **V8 Engine**: Compiles JavaScript code directly to native machine instructions for fast execution.
- **Asynchronous & Event-Driven**: Node.js utilizes an event loop (built on the `libuv` C library) to handle asynchronous I/O operations (like file reads or database queries) in background threads, keeping the main execution thread free.

### 2. Module Systems: CommonJS vs. ES Modules
Node.js supports two different module standards:

#### CommonJS (CJS)
The legacy, default module system in Node.js. It uses synchronous loading:
- **Exporting**: `module.exports = { myFunction };`
- **Importing**: `const { myFunction } = require("./module");`

#### ES Modules (ESM)
The official ECMAScript standard used in modern browsers and JavaScript code. It supports asynchronous loading:
- **Exporting**: `export function myFunction() {}`
- **Importing**: `import { myFunction } = from "./module.js";`

*Configuration:* To enable ES Modules in Node.js, add `"type": "module"` to your `package.json`.

### 3. Running TypeScript with `ts-node`
Normally, to execute a TypeScript file, you must first compile it to JavaScript using `tsc`, and then run the compiled JS file using Node:
```bash
tsc src/app.ts
node dist/app.js
```
During development, this two-step process can be slow. **`ts-node`** is a package that combines these steps. It compiles TypeScript code in memory and executes it directly on the fly:
```bash
npx ts-node src/app.ts
```

---

## Code Example

Below is a comparison showing the syntax differences between CommonJS and ES Modules:

### CommonJS Module
```javascript
// mathUtils.js
function multiply(a, b) {
    return a * b;
}
module.exports = { multiply };

// app.js
const { multiply } = require("./mathUtils");
console.log(multiply(2, 3));
```

### ES Module
```javascript
// mathUtils.js
export function multiply(a, b) {
    return a * b;
}

// app.js
import { multiply } from "./mathUtils.js";
console.log(multiply(2, 3));
```

---

## Summary
- **Node.js** executes JavaScript code on the server using Google Chrome's **V8 engine**.
- **CommonJS** is the legacy synchronous module standard in Node; **ES Modules** is the modern standard used in browsers.
- Use **`ts-node`** during development to compile and execute TypeScript files in memory in a single step.

---

## Additional Resources
- [Node.js Official Documentation](https://nodejs.org/en/docs/)
- [ts-node Github Repository](https://github.com/TypeStrong/ts-node)
