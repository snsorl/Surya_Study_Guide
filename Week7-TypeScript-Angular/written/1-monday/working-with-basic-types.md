# Hands-on Guide: Basic Types and Compiler Errors

## Learning Objectives
- Write explicit type annotations for variables, parameters, return types, and object literals.
- Identify common TypeScript compiler errors.
- Resolve type compatibility warnings.
- Differentiate between type errors and runtime execution issues.

---

## Why This Matters
Adding types to your code is only half the battle. To write clean TypeScript, you must know how to interpret and resolve compiler errors. If you cannot decode messages like `"Type 'null' is not assignable to type 'string'"` or `"Property 'x' does not exist on type 'Y'"`, you will struggle during build phases. This hands-on guide covers basic annotations and shows how to resolve common compiler errors.

---

## The Concept

### 1. Annotating Variables and Objects
TypeScript maps types using the colon `:` syntax.
- **Variables**: `let name: string = "Value";`
- **Object Literals**:
  ```typescript
  const config: { port: number; host: string } = {
      port: 8080,
      host: "localhost"
  };
  ```

### 2. Common Compiler Errors & Resolutions

#### Error A: Assignability Mismatch
*Message:* `Type 'X' is not assignable to type 'Y'.`
*Why:* You are attempting to assign a value of one type to a variable declared with a different, incompatible type.
*Resolution:* Adjust the assignment value or declare a union type.

#### Error B: Property Omission on Objects
*Message:* `Property 'x' is missing in type 'Y' but required in type 'Z'.`
*Why:* You instantiated an object literal but missed a mandatory property defined in its type contract.
*Resolution:* Add the missing property or mark it as optional (`?`) in the interface.

#### Error C: Implicit Any
*Message:* `Parameter 'x' implicitly has an 'any' type.`
*Why:* You declared a function parameter without a type annotation under strict compiler options.
*Resolution:* Add an explicit type annotation to the parameter.

---

## Code Examples

### Resolving Type Errors
Let's see how compiler warnings are analyzed and resolved in practice:

```typescript
// 1. Buggy Scenario:
interface UserProfile {
    id: number;
    username: string;
    email: string | null;
}

// Compiler Error: Property 'email' is missing in type '{ id: number; username: string; }'
// const profileA: UserProfile = {
//     id: 101,
//     username: "alice_dev"
// };

// Resolved Code:
const profileA: UserProfile = {
    id: 101,
    username: "alice_dev",
    email: null // Correctly satisfies interface contract
};

// 2. Function Parameter Error:
// Compiler Error: Parameter 'price' implicitly has an 'any' type.
// function computeTotal(price) { return price * 1.08; }

// Resolved Code:
function computeTotal(price: number): number {
    return price * 1.08;
}
```

---

## Summary
- Annotate variables, function boundaries, and object structures using the **`: type`** syntax.
- Read compiler errors carefully: they point to the exact line and explain which type contract was violated.
- Resolve implicit `any` warnings by adding explicit type annotations to your function parameters.

---

## Additional Resources
- [TypeScript Docs: Compiler diagnostics list](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html)
- [TypeScript Handbook: Strict null checks guide](https://www.typescriptlang.org/tsconfig#strictNullChecks)
