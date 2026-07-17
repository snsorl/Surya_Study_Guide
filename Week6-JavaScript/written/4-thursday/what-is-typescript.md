# What is TypeScript?

## Learning Objectives
- Define TypeScript and characterize its relationship to JavaScript.
- Explain compile-time type-safety.
- Describe how TypeScript is transpiled to raw JavaScript before execution.
- Summarize Microsoft's design objectives for TypeScript.

---

## Why This Matters
JavaScript is a dynamic language. While this dynamic behavior makes it fast to write small scripts, it introduces significant risks in large enterprise applications. For example, a misspelled property name or an unexpected null parameter passes through compiler checks unnoticed, raising runtime exceptions in production. **TypeScript** solves this. Developed by Microsoft, TypeScript adds static type declarations to JavaScript, catching entire categories of bugs during compilation before the code is ever executed.

---

## The Concept

### 1. A Typed Superset of JavaScript
TypeScript is a **strongly typed superset of JavaScript**. This means:
- Any valid JavaScript code is also valid TypeScript code.
- TypeScript adds an optional typing syntax layer to JavaScript.

```
+-----------------------------------+
|  TypeScript                       |
|   +-----------------------------+ |
|   |  JavaScript                 | |
|   |   - dynamic, objects, scope | |
|   +-----------------------------+ |
|   - static typing, interfaces   |
+-----------------------------------+
```

### 2. Static Typing and Compile-Time Check
- **JavaScript is Dynamically Typed**: Types are resolved at runtime during execution.
- **TypeScript is Statically Typed**: Types are declared and validated at **compile-time** by the TypeScript compiler (`tsc`). If a variable is declared as a `number` and you assign a `string`, the compiler immediately flags it as an error.

### 3. Transpilation (Compilation)
Web browsers do **not** understand TypeScript. They only execute JavaScript.
To run TypeScript in a browser:
1. You write `.ts` source files.
2. The TypeScript compiler (`tsc`) compiles (transpiles) the code, checking for type correctness.
3. The compiler strips out all type annotations and outputs clean, standard `.js` files that browsers can execute.

```
[ Code.ts ] ===( tsc compilation: checks types )===> [ Code.js ] ===( Browser Execution )
```

If type errors are found during compilation, the compiler flags them. By default, it will still generate JS files, but developers can configure it to fail the build (using strict settings which we will cover next).

---

## Code Example

Below is a simple code snippet demonstrating how static type annotations are written in TypeScript, and how the compiled output is stripped of types:

### TypeScript Source Code (`user.ts`)
```typescript
function greetUser(username: string, age: number): string {
    return `Hello ${username}, age ${age}`;
}

console.log(greetUser("Alice", 28));
// greetUser("Alice", "twenty-eight"); 
// Type error caught by IDE/compiler: Argument of type 'string' is not assignable to parameter of type 'number'.
```

### Compiled JavaScript Output (`user.js`)
```javascript
function greetUser(username, age) {
    return "Hello ".concat(username, ", age ").concat(age);
}
console.log(greetUser("Alice", 28));
```

---

## Summary
- TypeScript is a **statically typed superset** of JavaScript developed by Microsoft.
- It catches type compatibility bugs at **compile-time**, before code runs.
- The TypeScript compiler (**`tsc`**) transpiles `.ts` files into standard, clean `.js` files by stripping type annotations.
- JavaScript's dynamic runtime behavior remains the execution core in browsers.

---

## Additional Resources
- [TypeScript Official Website](https://www.typescriptlang.org/)
- [TypeScript Handbook: The Basics](https://www.typescriptlang.org/docs/handbook/2/basic-types.html)
