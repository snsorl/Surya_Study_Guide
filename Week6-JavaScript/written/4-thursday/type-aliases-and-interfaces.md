# Type Aliases vs. Interfaces

## Learning Objectives
- Declare object shapes using both Type Aliases and Interfaces.
- Compare the similarities and differences between Type Aliases and Interfaces.
- Extend interfaces using `extends` and combine types using intersections (`&`).
- Explain Declaration Merging in interfaces.
- Identify when to choose a Type Alias versus an Interface.

---

## Why This Matters
TypeScript provides two features to declare custom object schemas: **Type Aliases** (`type`) and **Interfaces** (`interface`). For many use cases, they appear interchangeable: both define object shapes, support property checks, and handle optional fields. However, they have key differences in inheritance syntax, declaration merging, and union support. Knowing when to choose a type alias versus an interface is key to writing clean, maintainable type systems.

---

## The Concept

### 1. Similarities
Both syntaxes can describe object shapes and document API contracts:

```typescript
// Interface
interface UserInterface {
    id: number;
    username: string;
}

// Type Alias
type UserType = {
    id: number;
    username: string;
};
```

### 2. Key Differences

#### A. Inheritance Syntax
- **Interfaces** use the **`extends`** keyword to inherit properties.
- **Type Aliases** combine shapes using **Intersection (`&`)** operations.

```typescript
// Extending an Interface
interface EmployeeInterface extends UserInterface {
    role: string;
}

// Intersecting a Type Alias
type EmployeeType = UserType & {
    role: string;
};
```

#### B. Declaration Merging (Interfaces Only)
If you declare multiple interfaces with the same name in the same scope, TypeScript automatically merges their properties. Type aliases cannot be redeclared.

```typescript
interface Customer {
    name: string;
}
interface Customer {
    email: string;
}
// Merged into: Customer { name: string; email: string; }
```
This is useful when writing third-party plugins or typing global configurations, as it allows other modules to extend existing shapes.

#### C. Unions and Primitives (Type Aliases Only)
Type aliases can describe primitive values, union types, tuple shapes, or mapped types. Interfaces are restricted to describing object structures.

```typescript
type ID = string | number; // Allowed
type Status = "ACTIVE" | "PENDING"; // Allowed
// interface Status = ... // Syntax Error
```

### 3. When to Choose Which
- **Use Interfaces**:
  - For defining standard object shapes, services, classes, and API contracts.
  - When writing libraries or public APIs where consumers might need to extend types using declaration merging.
- **Use Type Aliases**:
  - When defining union types, intersections, function signatures, tuple configurations, or mapping primitive values.

---

## Summary
- **Interfaces** describe object shapes and support **declaration merging** and class implementation checks.
- **Type Aliases** are more flexible, supporting primitive names, union mappings, and intersections.
- Extend interfaces using **`extends`**; combine type aliases using the **intersection (`&`)** operator.
- Prefer **interfaces** by default for standard objects; choose **type aliases** when using unions or specialized mappings.

---

## Additional Resources
- [TypeScript Docs: Differences between type aliases and interfaces](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#differences-between-type-aliases-and-interfaces)
- [TypeScript Handbook: Interface reference](https://www.typescriptlang.org/docs/handbook/2/objects.html)
