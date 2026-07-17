# Advanced Functions and Parameters in TypeScript

## Learning Objectives
- Annotate function parameters and return values explicitly.
- Declare optional and default parameters.
- Build generic functions that handle dynamic input types safely.
- Implement function overloads for multiple call signatures.
- Type callback parameters using function type signatures.

---

## Why This Matters
Functions process data throughout our applications. In JavaScript, functions lack parameter verification: you can pass any number of arguments, missing parameters default to `undefined`, and return values are dynamic. In TypeScript, we write statically typed function signatures. This allows the compiler to validate that functions are invoked with the correct arguments and that their outputs are handled safely.

---

## The Concept

### 1. Optional and Default Parameters
- **Optional Parameters (`?`)**: Declared by appending `?` to the parameter name. Must be placed **after** mandatory parameters.
- **Default Parameters**: Assigned using the `=` operator:
  ```typescript
  function greet(name: string, title: string = "User"): string {
      return `Hello ${title} ${name}`;
  }
  ```

### 2. Typing Callbacks
When passing functions as arguments (callbacks), you declare the callback's parameter and return types:

```typescript
function processItems(items: string[], callback: (item: string) => void): void {
    items.forEach(callback);
}
```

### 3. Generic Functions
Generic functions use a type variable `<T>` to capture the type of the argument provided, allowing you to enforce type relations between inputs and outputs:

```typescript
function firstElement<T>(arr: T[]): T | undefined {
    return arr[0];
}

const firstNum = firstElement([1, 2, 3]); // Type is number
const firstStr = firstElement(["A", "B"]); // Type is string
```

### 4. Function Overloads
Function overloads allow a single function to support different parameter combinations. It requires:
1. Declaring multiple **overload signatures** (without function bodies).
2. Writing a single **implementation signature** that is compatible with all overloads.

---

## Code Examples

### Implementing Function Overloads and Generics
Let's see how overloads and generics verify parameters:

```typescript
// 1. Function Overloads
function parseInput(value: string): string[];
function parseInput(value: number): number[];

// Implementation signature compatible with both overloads
function parseInput(value: string | number): any[] {
    if (typeof value === "string") {
        return value.split("");
    }
    return [value];
}

console.log(parseInput("Hi")); // ["H", "i"]
console.log(parseInput(42));   // [42]

// 2. Generic Function with Constraint
interface Lengthwise {
    length: number;
}

function logLength<T extends Lengthwise>(arg: T): void {
    console.log("Length is:", arg.length);
}

logLength("String text"); // Allowed
logLength([1, 2, 3]);    // Allowed
// logLength(101);       // Compile Error: type number lacks a length property
```

---

## Summary
- Annotate function parameters and return values explicitly using the **`parameter: type`** and **`): returnType`** syntaxes.
- Optional parameters (**`?`**) must be declared after mandatory parameters in the function signature.
- Use **Generic Functions (`<T>`)** to enforce type relationships between inputs and outputs.
- Use **Function Overloads** to support multiple call signatures on a single implementation.

---

## Additional Resources
- [TypeScript Docs: More on Functions](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#functions)
- [TypeScript Handbook: Generic Functions](https://www.typescriptlang.org/docs/handbook/2/functions.html#generic-functions)
