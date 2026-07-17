# Special Types and Type Narrowing in TypeScript

## Learning Objectives
- Differentiate between special types (`any`, `unknown`, `never`, `void`, `undefined`, `null`).
- Enable and explain Strict Null Checks in compiler options.
- Narrow types using Type Guards (`typeof`, `instanceof`, `in`).
- Apply the non-null assertion operator (`!`) safely, understanding its runtime risks.

---

## Why This Matters
Declaring simple string and number types is straightforward. However, when working with real-world applications, variables can hold multiple types, functions might return nothing, or parameters can be null during loading states. This guide covers how to handle these special situations using TypeScript's special types and type narrowing checks, keeping your code type-safe and robust.

---

## The Concept

### 1. Special Types Glossary
- **`any`**: Disables type checks. Avoid using this in production.
- **`unknown`**: A type-safe version of `any`. It requires type checks before interacting with the value.
- **`void`**: Used as a function return type to indicate the function **does not return a value** (often returning `undefined` implicitly).
- **`never`**: Indicates values that **can never occur**. Commonly used for functions that always throw an exception or enter infinite loops.
- **`null` & `undefined`**: Map to JavaScript's primitives of the same name.

### 2. Strict Null Checks
When `strictNullChecks` is set to `true` in `tsconfig.json`, `null` and `undefined` are treated as distinct types. You cannot assign them to standard types (like `string` or `number`) without declaring a union:

```typescript
let username: string;
// username = null; // Type Error: Type 'null' is not assignable to type 'string'.

let userEmail: string | null = null; // Allowed (Union type)
```

### 3. Type Narrowing (Type Guards)
Type narrowing is the process of resolving a specific type from a wider union type (like `string | number`) within a block of code.
Common **Type Guards** include:
- **`typeof`**: Used for primitive types.
  ```typescript
  if (typeof val === "string") { val.toUpperCase(); }
  ```
- **`instanceof`**: Used for class instances.
  ```typescript
  if (err instanceof Error) { console.log(err.message); }
  ```
- **`in`**: Used to check if a property exists on an object.
  ```typescript
  if ("role" in user) { console.log(user.role); }
  ```

### 4. Non-null Assertion Operator (`!`)
The non-null assertion operator (`!`) tells the compiler: *"I know this value might be null or undefined at compile-time, but I guarantee it is initialized here. Do not throw type errors."*
```typescript
const inputElement = document.querySelector("#todo-input")!; // Asserting it exists
```
*Warning:* Using `!` is risky. If the selector fails at runtime and returns `null`, calling methods on it will crash the application.

---

## Code Examples

### Type Narrowing and Never Exhaustiveness Check
Let's see how type narrowing works with union types, using the `never` type for compile-time safety:

```typescript
type PaymentMethod = "CREDIT_CARD" | "PAYPAL" | "BITCOIN";

function processPayment(method: PaymentMethod) {
    switch (method) {
        case "CREDIT_CARD":
            console.log("Processing credit card...");
            break;
        case "PAYPAL":
            console.log("Redirecting to PayPal...");
            break;
        case "BITCOIN":
            console.log("Checking blockchain status...");
            break;
        default:
            // Exhaustiveness check: if we add a new PaymentMethod later
            // and forget to add a case for it, the compiler flags a type error here.
            const exhaustiveCheck: never = method;
            throw new Error(`Unhandled payment method: ${exhaustiveCheck}`);
    }
}
```

---

## Summary
- **`void`** indicates a function does not return a value; **`never`** indicates a value that can never occur.
- With **`strictNullChecks: true`**, you must declare `null` and `undefined` explicitly using union types.
- Use **Type Guards** (`typeof`, `instanceof`, `in`) to narrow union types down to a specific type before interacting with them.
- The **`!`** operator bypasses compiler null checks but introduces runtime crash risks if the value is null.

---

## Additional Resources
- [TypeScript Docs: Narrowing guide](https://www.typescriptlang.org/docs/handbook/2/narrowing.html)
- [TypeScript Docs: Strict null checks](https://www.typescriptlang.org/tsconfig#strictNullChecks)
