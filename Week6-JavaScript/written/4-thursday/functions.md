# Functions in TypeScript

## Learning Objectives
- Annotate function parameters and return values explicitly.
- Configure optional parameters and default values.
- Declare and implement Function Overloads.
- Differentiate between `void` and `undefined` return signatures.
- Type callback parameters using function type signatures.

---

## Why This Matters
Functions are the basic building blocks of execution logic. In JavaScript, parameters lack validation: you can pass any number of arguments, missing parameters default to `undefined`, and return values are dynamic. In TypeScript, we declare typed function signatures. This allows the compiler to validate that functions are invoked with the correct arguments and that their outputs are handled safely.

---

## The Concept

### 1. Basic Function Typing
To type a function, add annotations to its parameters and return value:

```typescript
function calculateTax(price: number, taxRate: number): number {
    return price * taxRate;
}
```
*Note:* TypeScript can often infer the return type based on the `return` statement, but writing it explicitly is recommended to prevent accidental changes.

### 2. Optional and Default Parameters
- **Optional Parameters (`?`)**: Declared by appending `?` to the parameter name. Must be placed **after** mandatory parameters:
  ```typescript
  function greet(name: string, title?: string): string {
      return title ? `Hello, ${title} ${name}` : `Hello, ${name}`;
  }
  ```
- **Default Parameters**: Assigned using the `=` operator:
  ```typescript
  function buildUser(username: string, role: string = "USER") {
      // ...
  }
  ```

### 3. Return Signatures: `void` vs. `undefined`
- **`void`**: Tells the compiler that the function **does not return a value**. The actual runtime return value is ignored:
  ```typescript
  function logMessage(msg: string): void {
      console.log(msg);
  }
  ```
- **`undefined`**: If you explicitly annotate a return type as `undefined`, the function **must** contain a `return` statement that returns `undefined` (or nothing, which resolves to `undefined`):
  ```typescript
  function clearCache(): undefined {
      // do cleanup
      return; // Required
  }
  ```

### 4. Typing Callbacks
When passing functions as arguments (callbacks), you declare the callback's parameter and return types:

```typescript
function processNumbers(list: number[], callback: (n: number) => void): void {
    list.forEach(callback);
}
```

### 5. Function Overloads
Function overloads allow a single function to be invoked with different parameter combinations. It requires:
1. Writing multiple **overload signatures** (declaring parameter/return combinations without bodies).
2. Writing a single **implementation signature** that is compatible with all overloads.

---

## Code Examples

### Implementing Function Overloads
```typescript
// 1. Overload Signatures
function getLength(str: string): number;
function getLength(arr: any[]): number;

// 2. Implementation Signature (must be compatible with both overloads)
function getLength(value: string | any[]): number {
    return value.length;
}

console.log(getLength("Hello")); // 5
console.log(getLength([1, 2, 3])); // 3
```

---

## Summary
- Annotate function parameters and return values explicitly using the **`parameter: type`** and **`): returnType`** syntaxes.
- Optional parameters (**`?`**) must be declared after mandatory parameters in the function signature.
- **`void`** indicates a function does not return a value; **`undefined`** requires an explicit return statement.
- Type callback parameters using function signature types: **`(param: type) => returnType`**.
- Use **Function Overloads** to support multiple call signatures on a single implementation.

---

## Additional Resources
- [TypeScript Docs: More on Functions](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#functions)
- [TypeScript Handbook: Function Overloads](https://www.typescriptlang.org/docs/handbook/2/functions.html#function-overloads)
