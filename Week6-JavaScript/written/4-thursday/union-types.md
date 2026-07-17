# Union and Intersection Types in TypeScript

## Learning Objectives
- Declare Union types using the pipe (`|`) operator.
- Declare Intersection types using the ampersand (`&`) operator.
- Handle nullable values using union types with `null` or `undefined`.
- Design and narrow Discriminated Unions using literal properties and switch statements.

---

## Why This Matters
Real-world application variables are rarely restricted to a single type. For instance, an API call status can be a string key or an error object, and a user's address can be a structured object or a simple text description. TypeScript provides **Union** and **Intersection** types to model these combinations safely.

---

## The Concept

### 1. Union Types (`|`)
A union type indicates that a value can belong to one of several types. Declared using the pipe (`|`) operator:

```typescript
let id: string | number;
id = 101;      // Valid
id = "ID-101"; // Valid
```
*Restriction:* When interacting with a union type variable, you can only access properties that are common to **all** types in the union. To access type-specific properties, you must narrow the type first using a type guard.

### 2. Nullable Unions
To allow a variable to hold null or undefined values when `strictNullChecks` is active, append them to the union:
```typescript
let email: string | null = null;
```

### 3. Intersection Types (`&`)
An intersection type combines multiple types into a single new type containing all properties of the combined types. Declared using the ampersand (`&`) operator:

```typescript
interface Loggable {
    log: () => void;
}
interface Serializable {
    serialize: () => string;
}

// Combining both interfaces
type Entity = Loggable & Serializable;
```

### 4. Discriminated Unions (Tagged Unions)
A discriminated union is a pattern for creating safe, mutually exclusive objects. It requires:
1. Object types containing a common property with a **literal type** (a "tag").
2. A union type combining these objects.
3. A switch statement or condition block that narrows the type based on the tag.

---

## Code Examples

### Discriminated Unions in Async States
Below is a pattern showing how to model API response states using discriminated unions:

```typescript
// 1. Define states with a common literal tag: 'type'
interface LoadingState {
    type: "LOADING"; // Literal type
}

interface SuccessState {
    type: "SUCCESS";
    data: string[];
}

interface ErrorState {
    type: "ERROR";
    message: string;
}

// 2. Combine into a union
type ApiState = LoadingState | SuccessState | ErrorState;

// 3. Narrow the union using a switch statement
function renderDashboard(state: ApiState) {
    switch (state.type) {
        case "LOADING":
            console.log("Loading dashboard stats...");
            break;
        case "SUCCESS":
            // Compiler knows data exists here
            console.log("Stats loaded:", state.data.join(", "));
            break;
        case "ERROR":
            // Compiler knows message exists here
            console.error("Dashboard error:", state.message);
            break;
    }
}
```

---

## Summary
- Use **Union types (`|`)** to declare that a variable can hold one of several types.
- Use **Intersection types (`&`)** to combine multiple object shapes into a single type.
- Declare **nullable types** explicitly using union combinations (e.g. `string | null`).
- Design **Discriminated Unions** using literal property tags (like `type: "SUCCESS"`) to enable safe, compiler-checked type narrowing.

---

## Additional Resources
- [TypeScript Docs: Union and Intersection Types](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#union-types)
- [TypeScript Handbook: Discriminated Unions](https://www.typescriptlang.org/docs/handbook/2/narrowing.html#discriminated-unions)
