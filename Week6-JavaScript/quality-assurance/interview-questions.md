# Interview Questions: Week 6 - JavaScript & TypeScript

This bank contains 75 technical interview questions mapped to the Week 6 curriculum, categorized by day and difficulty (70% Beginner, 25% Intermediate, 5% Advanced).

---

## Monday – JavaScript Fundamentals

### Q1: What are JavaScript's 7 primitive data types?
**Keywords:** String, Number, BigInt, Boolean, undefined, null, Symbol
<details>
<summary>Click to Reveal Answer</summary>

The 7 primitive types in JavaScript are: String, Number, BigInt, Boolean, undefined, null, and Symbol. Primitives are immutable and copied by value.
</details>

---

### Q2: What is the difference between `undefined` and `null`?
**Keywords:** Uninitialized, Intentional Empty, Object Type
<details>
<summary>Click to Reveal Answer</summary>

`undefined` means a variable has been declared but not yet assigned a value. `null` is an assigned value representing the intentional absence of any object value.
</details>

---

### Q3: Why does `typeof null` return `"object"`?
**Keywords:** Legacy engine bug, Backward compatibility
<details>
<summary>Click to Reveal Answer</summary>

This is a legacy bug in the original JavaScript engine implementation where values were represented as type tags in memory, and object/null shared the same tag bits. It has been preserved to avoid breaking existing websites.
</details>

---

### Q4: Explain the difference between loose equality (`==`) and strict equality (`===`).
**Keywords:** Coercion, Type Comparison, Loose, Strict
<details>
<summary>Click to Reveal Answer</summary>

Loose equality (`==`) performs implicit type coercion before comparing values, whereas strict equality (`===`) compares both the value and the type, resolving to `false` if types differ.
</details>

---

### Q5: What is implicit type coercion? Give an example.
**Keywords:** Automatic Conversion, Addition concatenation, Operators
<details>
<summary>Click to Reveal Answer</summary>

Implicit coercion is the automatic conversion of values from one data type to another by the JavaScript engine during runtime operations. For example, `10 + "5"` yields the string `"105"`.
</details>

---

### Q6: What list of values are evaluated as "falsy" in JavaScript?
**Keywords:** Falsy values, Boolean coercion
<details>
<summary>Click to Reveal Answer</summary>

The falsy values in JavaScript are: `false`, `0` (and `-0`, `0n`), `""` (empty string), `null`, `undefined`, and `NaN`. All other values are truthy.
</details>

---

### Q7: Explain the difference between `slice()` and `splice()` array methods.
**Keywords:** Mutate original, Return copy, Swatch
<details>
<summary>Click to Reveal Answer</summary>

`slice(start, end)` returns a shallow copy of a portion of an array without mutating the original. `splice(start, count, items)` mutates the original array in place by adding or removing elements.
</details>

---

### Q8: What does the `map()` array helper do?
**Keywords:** Array mapping, Return transformed, Immutable
<details>
<summary>Click to Reveal Answer</summary>

`map()` iterates over an array and returns a new array of the same length containing elements transformed by the callback function, leaving the original array unmodified.
</details>

---

### Q9: How does `reduce()` operate?
**Keywords:** Accumulator, Aggregate value, Reduce array
<details>
<summary>Click to Reveal Answer</summary>

`reduce()` executes a reducer callback function on each element of the array, passing the accumulated result forward, reducing the array to a single aggregated value (like a sum or average).
</details>

---

### Q10: What is variable hoisting?
**Keywords:** Lift declarations, Compile phase, Initialization omission
<details>
<summary>Click to Reveal Answer</summary>

Hoisting is the behavior where variable and function declarations are moved to the top of their enclosing scope during the compilation phase, before execution begins.
</details>

---

### Q11: Why does accessing a `var` variable before its declaration log `undefined` instead of throwing an error?
**Keywords:** Hoisting initialization, Var behavior
<details>
<summary>Click to Reveal Answer</summary>

Because declarations with `var` are hoisted and automatically initialized with `undefined`. The value assignment itself remains on the original line.
</details>

---

### Q12: What is the Temporal Dead Zone (TDZ)?
**Keywords:** Let/Const hoisting, Uninitialized state, ReferenceError
<details>
<summary>Click to Reveal Answer</summary>

The TDZ is the period between the start of a block scope and the line where a `let` or `const` variable is declared. Accessing the variable within this zone throws a `ReferenceError`.
</details>

---

### Q13: Contrast function declarations and function expressions.
**Keywords:** Hoisting expression, Named function, Assignment
<details>
<summary>Click to Reveal Answer</summary>

Function declarations are hoisted and can be called before they appear in code. Function expressions assign an anonymous function to a variable, are not hoisted, and cannot be accessed before assignment.
</details>

---

### Q14: When should you start a new AI chat session to avoid context drift?
**Keywords:** Context boundaries, Topic shift, Debugging loop
<details>
<summary>Click to Reveal Answer</summary>

You should start a new session when shifting topics (e.g. from Java to CSS), when caught in a repetitive debugging loop of more than 4-5 prompts, or when the AI begins to hallucinate code.
</details>

---

### Q15: [Advanced] How does the single-threaded Event Loop handle asynchronous timers (like `setTimeout`)?
**Keywords:** Web APIs, Callback Queue, Call Stack, Event Loop
<details>
<summary>Click to Reveal Answer</summary>

JavaScript is single-threaded. Asynchronous timers are handed off to browser runtime Web APIs. When the timer expires, its callback is placed in the Callback Queue. The Event Loop pushes callbacks from the queue onto the Call Stack only when the Call Stack is empty.
</details>

---

## Tuesday – ES6+ Features & Object-Oriented Programming

### Q16: Compare `let` and `const` scope rules.
**Keywords:** Block scope, Reassignment bounds
<details>
<summary>Click to Reveal Answer</summary>

Both are block-scoped and exist only inside curly braces `{}`. `let` variables can be reassigned new values, whereas `const` variables prevent variable pointer reassignment.
</details>

---

### Q17: Can you modify the properties of an object declared with `const`?
**Keywords:** Reference immutability, Property modification
<details>
<summary>Click to Reveal Answer</summary>

Yes. `const` prevents reassigning the variable identifier to a new object, but it does not make the object's properties immutable.
</details>

---

### Q18: What is strict mode and how do you enable it?
**Keywords:** `"use strict"`, Silent errors, Runtime exception
<details>
<summary>Click to Reveal Answer</summary>

Strict mode is a feature that enforces cleaner code by converting silent syntax warnings into runtime errors. Enable it by adding the string `"use strict";` at the top of a file or function.
</details>

---

### Q19: Name two errors strict mode throws that non-strict mode ignores.
**Keywords:** Undeclared variables, Duplicate parameters, Read-only write
<details>
<summary>Click to Reveal Answer</summary>

Strict mode throws errors for assigning values to undeclared variables (implicit globals) and declaring duplicate function parameter names.
</details>

---

### Q20: What is the lexical `this` binding of arrow functions?
**Keywords:** Enclosing scope, Lexical inheritance, No own this
<details>
<summary>Click to Reveal Answer</summary>

Arrow functions do not bind their own `this`. Instead, they inherit the `this` value of their enclosing scope when declared, making them ideal for timers and event callbacks.
</details>

---

### Q21: What are the syntax rules for concise arrow function bodies?
**Keywords:** Single expression, Implicit return, Parentheses omission
<details>
<summary>Click to Reveal Answer</summary>

For single expressions, omit curly braces `{}` and the `return` keyword for an implicit return. Parentheses around parameters can be omitted only for single parameters.
</details>

---

### Q22: What are the main limitations of arrow functions?
**Keywords:** Constructor blocks, No arguments, Prototype absence
<details>
<summary>Click to Reveal Answer</summary>

Arrow functions cannot be invoked as constructors with `new`, do not possess a `prototype` property, and do not contain their own `arguments` array object.
</details>

---

### Q23: How do template literals preserve formatting?
**Keywords:** Backticks, Preserved whitespaces, Multi-line
<details>
<summary>Click to Reveal Answer</summary>

Template literals use backticks (\`\`) and preserve any newlines, indentation, and spacing directly inside the string without needing manual escape characters.
</details>

---

### Q24: What is a prototype chain in JavaScript?
**Keywords:** Prototype link, Property lookup, Inherited methods
<details>
<summary>Click to Reveal Answer</summary>

The prototype chain is a delegation link. If a property is missing on an object, the runtime searches its internal prototype link (`__proto__`) recursively up the chain until it finds it or reaches `null`.
</details>

---

### Q25: How does `Object.create(proto)` work?
**Keywords:** Prototype config, Inherit parent
<details>
<summary>Click to Reveal Answer</summary>

`Object.create(proto)` creates a new empty object and sets its internal prototype link (`__proto__`) to the object passed as the argument.
</details>

---

### Q26: What does `hasOwnProperty()` do?
**Keywords:** Instance property, Ignore inherited, Object validation
<details>
<summary>Click to Reveal Answer</summary>

`hasOwnProperty(prop)` checks if a property is an instance property defined directly on the object itself, ignoring properties inherited from its prototype chain.
</details>

---

### Q27: How does `this` behave inside a standalone function call?
**Keywords:** Standalone invoke, Undefined strict, Global window
<details>
<summary>Click to Reveal Answer</summary>

Inside a standalone function call, `this` refers to the global object (`window` in browsers) in non-strict mode, and resolves to `undefined` in strict mode.
</details>

---

### Q28: Explain the difference between `call()`, `apply()`, and `bind()`.
**Keywords:** Immediate invoke, Argument array, Bind return function
<details>
<summary>Click to Reveal Answer</summary>

`call()` and `apply()` invoke a function immediately with an explicit `this` context (passing arguments individually or as an array, respectively). `bind()` returns a new function with its `this` context permanently locked to the target object.
</details>

---

### Q29: Why are ES6 classes described as "syntactic sugar"?
**Keywords:** Underlying prototypes, Sugar abstraction, Prototype compilation
<details>
<summary>Click to Reveal Answer</summary>

ES6 classes do not change JavaScript's prototype-based inheritance model. Under the hood, class declarations compile to constructor functions, and methods are added to the constructor's `prototype` object.
</details>

---

### Q30: [Advanced] Why does passing an object method as a callback (e.g. to `setTimeout`) cause it to lose its `this` context?
**Keywords:** Reference copy, Standalone invocation, Lost context
<details>
<summary>Click to Reveal Answer</summary>

When a method is passed as a callback (e.g. `setTimeout(obj.method, 100)`), you are passing a reference to the function itself, stripping it of its calling object. When invoked later, it is executed as a standalone function call, resetting `this` to `undefined` (in strict mode).
</details>

---

## Wednesday – DOM & Asynchronous Operations

### Q31: What is the DOM?
**Keywords:** Document Object Model, HTML tree, In-memory API
<details>
<summary>Click to Reveal Answer</summary>

The DOM is an in-memory tree representation of an HTML document created by the browser, allowing JavaScript to dynamically read, write, and manipulate the page structure and styles.
</details>

---

### Q32: What is the difference between `window` and `document`?
**Keywords:** Global tab context, Active page content
<details>
<summary>Click to Reveal Answer</summary>

`window` represents the global browser execution context (the browser tab). `document` is a property of the `window` object representing the active HTML page structure loaded in the tab.
</details>

---

### Q33: Compare a `NodeList` and an `HTMLCollection`.
**Keywords:** Legacy collection, Static query, Array helpers
<details>
<summary>Click to Reveal Answer</summary>

`HTMLCollection` contains only element nodes, is always live, and does not support `.forEach`. `NodeList` can contain any node type, is usually static, and supports `.forEach` natively.
</details>

---

### Q34: What is the risk of using `innerHTML` to inject user-provided text?
**Keywords:** Cross-Site Scripting, XSS vulnerabilities, HTML parsing
<details>
<summary>Click to Reveal Answer</summary>

Using `innerHTML` parses input strings as HTML. If user-provided text contains malicious script tags, they will execute in the user's browser, leading to XSS vulnerabilities. Use `textContent` instead to escape inputs automatically.
</details>

---

### Q35: Why should you use `document.createDocumentFragment()` when appending multiple nodes?
**Keywords:** Minimizing reflows, Memory batching, Single insertion
<details>
<summary>Click to Reveal Answer</summary>

A DocumentFragment is a lightweight container in memory. Appending nodes to it does not trigger layout reflows. Once all nodes are added, appending the fragment to the DOM triggers only a single layout paint.
</details>

---

### Q36: Explain the difference between event bubbling and capturing.
**Keywords:** Propagation phases, Flow direction, Bubbles up
<details>
<summary>Click to Reveal Answer</summary>

Event capturing travels down the DOM tree from the root to the target element. Event bubbling travels back up the tree from the target element to the root. By default, event listeners trigger during the bubbling phase.
</details>

---

### Q37: How does `event.stopPropagation()` differ from `event.preventDefault()`?
**Keywords:** Halting bubbles, Override default browser action
<details>
<summary>Click to Reveal Answer</summary>

`stopPropagation()` stops the event from propagating further up or down the DOM tree. `preventDefault()` blocks the browser's default action for the event (e.g. form submission reloads) but does not stop it from bubbling up the tree.
</details>

---

### Q38: What is Event Delegation and what is its primary benefit?
**Keywords:** Parent listener, Bubbling capture, Memory efficiency
<details>
<summary>Click to Reveal Answer</summary>

Event delegation is a pattern where you bind a single event listener to a parent container to manage events triggered on its children. It saves memory and handles dynamically added children automatically.
</details>

---

### Q39: What are the JSON syntax rules?
**Keywords:** Double quotes keys, Data primitives, Commas validation
<details>
<summary>Click to Reveal Answer</summary>

JSON keys and strings must use double quotes. It does not support comments, trailing commas, or function definitions, and is restricted to strings, numbers, booleans, arrays, nested objects, and null.
</details>

---

### Q40: What happens when you attempt to serialize an object containing a function or `undefined` property to JSON?
**Keywords:** Stripped properties, JSON.stringify exclusion
<details>
<summary>Click to Reveal Answer</summary>

During `JSON.stringify()`, properties holding functions or `undefined` values are stripped from objects. If they are elements inside an array, they are coerced to `null`.
</details>

---

### Q41: Explain the three states of a Promise.
**Keywords:** Pending, Fulfilled, Rejected, Settle
<details>
<summary>Click to Reveal Answer</summary>

A Promise starts as **Pending** (operation running). It settles as either **Fulfilled** (successful completion with a value) or **Rejected** (failed completion with an error reason). Once settled, its state cannot change.
</details>

---

### Q42: What does `Promise.all()` do if one of the promises fails?
**Keywords:** Parallel execution, Reject immediately, All or nothing
<details>
<summary>Click to Reveal Answer</summary>

`Promise.all` executes promises in parallel and resolves only when all promises succeed. If any single promise rejects, the entire operation rejects immediately, ignoring all other resolutions.
</details>

---

### Q43: How do `async` and `await` keywords simplify async code?
**Keywords:** Syntactic sugar, Pause execution, Synchronous reading
<details>
<summary>Click to Reveal Answer</summary>

`async` declares a function that returns a Promise. `await` pauses execution until the promise resolves, allowing asynchronous code to be written and read like synchronous code.
</details>

---

### Q44: How do you handle errors in `async`/`await` code?
**Keywords:** Try/catch blocks, Exception handling
<details>
<summary>Click to Reveal Answer</summary>

Instead of appending `.catch()`, wrap the `await` expressions in standard `try`/`catch`/`finally` blocks to capture exceptions.
</details>

---

### Q45: [Advanced] What is a closure loop gotcha and how does `let` resolve it?
**Keywords:** Var function scope, Let block binding, Async loops
<details>
<summary>Click to Reveal Answer</summary>

When using `var` in a loop with asynchronous callbacks, all callbacks close over a single, shared, function-scoped variable, logging only the final incremented value. `let` creates a new block-scoped variable binding for every loop iteration, capturing the index value for each callback.
</details>

---

## Thursday – TypeScript Tooling & Types

### Q46: What is TypeScript?
**Keywords:** Superset of JavaScript, Static type check, Transpiled JS
<details>
<summary>Click to Reveal Answer</summary>

TypeScript is a statically typed superset of JavaScript developed by Microsoft. It checks types at compile-time and transpiles to clean, standard JavaScript for browser execution.
</details>

---

### Q47: What does the term "transpilation" mean in the context of TypeScript?
**Keywords:** Source-to-source, Strip annotations, JS target
<details>
<summary>Click to Reveal Answer</summary>

Transpilation is source-to-source compilation. The TypeScript compiler (`tsc`) reads `.ts` files, validates types, and compiles them to standard `.js` files by stripping out type annotations.
</details>

---

### Q48: What is the difference between `dependencies` and `devDependencies` in `package.json`?
**Keywords:** Production run, Compile dependencies, Dev build separation
<details>
<summary>Click to Reveal Answer</summary>

`dependencies` are packages required for the application to run in production. `devDependencies` are only required during local development and build steps (like compilers or test runners).
</details>

---

### Q49: What is the purpose of `package-lock.json`?
**Keywords:** Lock versions, Lock tree, Consistent dependencies
<details>
<summary>Click to Reveal Answer</summary>

`package-lock.json` locks the exact version of every dependency and sub-dependency installed, ensuring consistent builds across all developer machines.
</details>

---

### Q50: How do you configure a project output folder in `tsconfig.json`?
**Keywords:** outDir parameter, dist compilation
<details>
<summary>Click to Reveal Answer</summary>

By setting the `"outDir"` option under `"compilerOptions"` to your target directory (e.g. `"outDir": "./dist"`).
</details>

---

### Q51: Explain the difference between `any` and `unknown` types.
**Keywords:** Disable type check, Safe alternative, Narrowing check
<details>
<summary>Click to Reveal Answer</summary>

`any` disables all type-checking, letting you call any method or property. `unknown` is type-safe: you can assign anything to it, but you cannot call methods or read properties on it without narrowing its type first.
</details>

---

### Q52: How do you declare optional properties in a TypeScript object or interface?
**Keywords:** Question mark, Optional validation, Undefined possibility
<details>
<summary>Click to Reveal Answer</summary>

Append a question mark `?` after the property name (e.g. `email?: string`). This allows the property to be omitted or set to `undefined`.
</details>

---

### Q53: What does the `readonly` modifier do?
**Keywords:** Immutable property, Compile-time assignment lock
<details>
<summary>Click to Reveal Answer</summary>

`readonly` prevents a property's value from being changed after the object has been initialized, throwing a compile error on attempts to reassign it.
</details>

---

### Q54: What is an index signature?
**Keywords:** Dynamic key maps, Index bracket, Dictionary type
<details>
<summary>Click to Reveal Answer</summary>

An index signature allows you to type objects with dynamic key names (e.g., `{ [key: string]: number }`), defining the allowed types for keys and values.
</details>

---

### Q55: Explain Discriminated Unions.
**Keywords:** Union shapes, Literal tag, Switch type guard
<details>
<summary>Click to Reveal Answer</summary>

Discriminated Unions combine multiple object types that share a common literal tag property (e.g., `type: "SUCCESS"`). This tag allows the compiler to narrow types inside switch/conditional blocks.
</details>

---

### Q56: How do type assertions (`as`) differ from runtime type conversions?
**Keywords:** Compile assertion, Runtime cast, Stripped execution
<details>
<summary>Click to Reveal Answer</summary>

Type assertions (`as`) are compile-time instructions that guide the compiler's type resolution, but do not execute any conversion code at runtime. Runtime conversions (like `Number()`) alter values in memory at runtime.
</details>

---

### Q57: What does the `satisfies` operator do?
**Keywords:** Contract validation, Inferred specificity preservation
<details>
<summary>Click to Reveal Answer</summary>

The `satisfies` operator validates that an object matches a specific type or interface contract, but **preserves** the most specific inferred type of the object properties.
</details>

---

### Q58: How does `void` differ from `undefined` as a function return type annotation?
**Keywords:** Void return bypass, Undefined return check
<details>
<summary>Click to Reveal Answer</summary>

`void` tells the compiler that the function does not return a value. `undefined` requires an explicit return statement that returns `undefined` (or nothing).
</details>

---

### Q59: What are function overloads?
**Keywords:** Multiple signatures, Single implementation, Arg combinations
<details>
<summary>Click to Reveal Answer</summary>

Function overloads allow you to declare multiple call signatures (parameters and return types) for a single function name, followed by a single implementation compatible with all signatures.
</details>

---

### Q60: [Advanced] Why is it unsafe to use the non-null assertion operator (`!`) regularly?
**Keywords:** Bypassing validation, Runtime crashes, Null dereference
<details>
<summary>Click to Reveal Answer</summary>

The `!` operator tells the compiler to assume a value is not null, bypassing strict null checks. If the value is actually `null` at runtime, calling methods on it will cause the application to crash, defeating the purpose of TypeScript.
</details>

---

## Friday – Presentations & Peer Review

### Q61: What are the core presentation requirements for Project 2?
**Keywords:** Running frontend, Fetch async queries, Spring Boot database integration
<details>
<summary>Click to Reveal Answer</summary>

The presentation must demonstrate a working frontend interface executing asynchronous Fetch API calls to interact with a Spring Boot database backend.
</details>

---

### Q62: Why is error feedback important in frontend applications?
**Keywords:** UX responsiveness, API downtime, Graceful error recovery
<details>
<summary>Click to Reveal Answer</summary>

If an API request fails, the application should display a user-friendly error message rather than freezing, crashing, or failing silently, keeping the interface responsive.
</details>

---

### Q63: How do you structure a 10-minute sprint review presentation?
**Keywords:** App demo, Code walkthrough, Q&A session
<details>
<summary>Click to Reveal Answer</summary>

Spend 4 minutes demonstrating user flows, 4 minutes walking through key codebase elements (selectors, fetch handlers, endpoints), and 2 minutes answering questions.
</details>

---

### Q64: Name 3 criteria used to grade peer projects.
**Keywords:** Stack integration, Async error handling, UX responsiveness, DOM security
<details>
<summary>Click to Reveal Answer</summary>

Key criteria include: the quality of stack integration, async error handling (using `try/catch` and checking `response.ok`), DOM security (XSS prevention), and overall UX responsiveness.
</details>

---

### Q65: What is the role of a system registry or configuration manager in client apps?
**Keywords:** Configuration settings, Environment endpoints
<details>
<summary>Click to Reveal Answer</summary>

A configuration manager centralizes API base URLs and environment variables, keeping variables clean and making it easy to point the application to different environments (e.g. local vs staging).
</details>

---

### Q66: What is a DTO and how does it relate to TypeScript interfaces?
**Keywords:** Data Transfer Object, Interface boundary, API validation
<details>
<summary>Click to Reveal Answer</summary>

A Data Transfer Object (DTO) defines the shape of data sent over the network. Declaring matching TypeScript interfaces on the frontend ensures payload compatibility with the backend endpoints.
</details>

---

### Q67: Explain how the `Authorization` header is typically passed in fetch options.
**Keywords:** Headers config, Bearer authorization, API credentials
<details>
<summary>Click to Reveal Answer</summary>

It is passed as a string property inside the `headers` object, typically using the Bearer token scheme: `'Authorization': 'Bearer <token_value>'`.
</details>

---

### Q68: What is a package lock file collision and how do you resolve it?
**Keywords:** Merge conflicts, npm install re-lock
<details>
<summary>Click to Reveal Answer</summary>

Lock file collisions occur during git merges when developers install different packages. Resolve them by running `npm install` to regenerate the lock file cleanly.
</details>

---

### Q69: Explain why we use `npx` instead of `npm run` for temporary scripts.
**Keywords:** Execute package, Temp download, CLI utilities
<details>
<summary>Click to Reveal Answer</summary>

`npx` is a CLI tool that downloads and executes packages from the npm registry in a single step without needing to install them globally or save them to your `package.json` dependencies.
</details>

---

### Q70: What does the `"type": "module"` setting do in a Node project?
**Keywords:** ESM standard, Import/export keywords, CommonJS bypass
<details>
<summary>Click to Reveal Answer</summary>

It configures the Node runtime to use ES Modules (`import`/`export` keywords) instead of the legacy CommonJS (`require`/`module.exports`) module standard.
</details>

---

### Q71: How do you configure typescript target version compilations?
**Keywords:** tsconfig compilerOptions, Target standard
<details>
<summary>Click to Reveal Answer</summary>

By specifying the `"target"` property in the `tsconfig.json` compiler options to a target standard version (e.g., `"target": "ES2022"`).
</details>

---

### Q72: Explain the difference between DOM Node and Element traversal.
**Keywords:** childNodes spaces, children Element nodes
<details>
<summary>Click to Reveal Answer</summary>

Node traversal includes all nodes, including whitespace text and comments. Element traversal (using properties like `children` or `nextElementSibling`) targets only HTML element nodes.
</details>

---

### Q73: Why is `event.preventDefault()` crucial in submit event handlers?
**Keywords:** Form submission refresh, Prevent context clear
<details>
<summary>Click to Reveal Answer</summary>

Because browsers default to submitting the form and reloading the page, which clears the in-memory JavaScript state. `preventDefault()` blocks this reload.
</details>

---

### Q74: Explain the difference between `instanceof` and `typeof`.
**Keywords:** Class constructor validation, Primitive string output
<details>
<summary>Click to Reveal Answer</summary>

`typeof` returns a string representing the primitive type of a variable. `instanceof` checks if an object is an instance of a specific class constructor.
</details>

---

### Q75: [Advanced] Why is using a DocumentFragment inside a DOM loop more performant than direct DOM insertions?
**Keywords:** Layout reflows, Memory batching, Browser rendering pipeline
<details>
<summary>Click to Reveal Answer</summary>

Inserting elements directly in a loop forces the browser to recalculate the page layout (reflow) on every iteration. A `DocumentFragment` batches these updates in memory, triggering only a single layout calculation when appended to the page.
</details>
