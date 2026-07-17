# Basic Variable Types in TypeScript

## Learning Objectives
- Annotate TypeScript variables with basic primitive types (`string`, `number`, `boolean`, `null`, `undefined`).
- Explain Type Inference and identify when it occurs.
- Write explicit type declarations.
- Contrast the risks of the `any` type with the safety of the `unknown` type.

---

## Why This Matters
Declaring types is the core value proposition of TypeScript. However, typing every single variable explicitly makes your code verbose and redundant. TypeScript uses a smart type inference engine to resolve types automatically. This guide covers how to write basic type annotations, how type inference operates, and how to avoid the dangerous `any` escape hatch by using `unknown` for runtime values.

---

## The Concept

### 1. Basic Type Annotations
To explicitly declare a variable's type, append a colon `:` followed by the type name:

```typescript
let username: string = "Alice";
let count: number = 42;
let isActive: boolean = true;
let emptyValue: null = null;
let missingValue: undefined = undefined;
```

### 2. Type Inference
TypeScript is smart. If you assign a value during declaration, the compiler automatically **infers** the variable's type based on that value. You do not need to write explicit annotations for simple assignments:

```typescript
let score = 95; // TypeScript automatically infers that score is of type 'number'
// score = "completed"; // Type Error: Type 'string' is not assignable to type 'number'.
```
*Best Practice:* Let type inference work for standard local variables; write explicit annotations primarily for function parameters, return values, or when declaring variables before initializing them.

### 3. The `any` Escape Hatch (To Avoid)
The `any` type disables all compiler type checks. It tells TypeScript: *"I don't care about type-safety for this variable; treat it like raw JavaScript."*
```typescript
let value: any = 10;
value = "hello"; // Allowed
value.nonExistentMethod(); // Allowed by compiler, but crashes at runtime!
```
*Warning:* Overusing `any` undermines the benefits of using TypeScript. Avoid `any` whenever possible.

### 4. The Safe Alternative: `unknown`
`unknown` is the type-safe counterpart of `any`. It indicates a value whose type is not yet known (such as data received from a network API).
- You can assign any value to an `unknown` variable.
- However, you **cannot call methods or read properties** on an `unknown` variable until you explicitly check or narrow its type:

```typescript
let data: unknown = "Hello";

// data.toUpperCase(); // Type Error: 'data' is of type 'unknown'.

// Correct: Narrow type using type guards first
if (typeof data === "string") {
    console.log(data.toUpperCase()); // Allowed (type narrowed to string)
}
```

---

## Summary
- Annotate variables explicitly using the **`: type`** syntax.
- **Type inference** automatically assigns types to variables during initialization, reducing type verbosity.
- The **`any`** type disables type checks and should be avoided in production environments.
- Use **`unknown`** for values whose type is not known at compile-time; it forces you to perform type checks before interacting with the value.

---

## Additional Resources
- [TypeScript Docs: Everyday Types](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html)
- [TypeScript Handbook: Type Inference](https://www.typescriptlang.org/docs/handbook/type-inference.html)
