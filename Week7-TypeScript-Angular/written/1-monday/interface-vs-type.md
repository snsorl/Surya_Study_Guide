# Interface vs. Type Alias: Final Comparison

## Learning Objectives
- Differentiate between Interface declarations and Type Aliases.
- Describe Declaration Merging and explain when it occurs.
- Write Computed Type Properties.
- Extend schemas using `extends` and intersection (`&`) operators.
- Apply community conventions when choosing between interfaces and types.

---

## Why This Matters
On Day 4 of Week 6, we compared interfaces and type aliases. As we build Angular applications, we define complex data entities, component property bindings, and service boundaries. Understanding the nuances of TypeScript's type system—such as declaration merging and computed properties—allows you to structure clean, maintainable type systems.

---

## The Concept

### 1. Interface vs. Type Alias Comparison Matrix

| Feature | Interface (`interface`) | Type Alias (`type`) |
|---|---|---|
| **Intended Use** | Describes public object contracts, classes, and services. | Declares union types, intersections, tuples, and aliases for primitives. |
| **Declaration Merging** | **Supported**. Multiple declarations merge their properties. | **Not supported**. Redeclaring a type alias throws an error. |
| **Computed Properties** | Not supported. | **Supported** via mapped and template literal types. |
| **Inheritance Syntax** | Extends other interfaces using **`extends`**. | Combines structures using **Intersection (`&`)**. |
| **Class Binding** | Classes can implement them via **`implements`**. | Classes can implement them (unless they are unions). |

### 2. Declaration Merging (Interface Only)
If you declare an interface multiple times with the same name, TypeScript automatically merges their properties. This is useful when writing third-party plugins or extending global scopes:

```typescript
// Initial declaration
interface Window {
    appConfig: { apiUrl: string };
}

// Extended declaration elsewhere
interface Window {
    analyticsToken: string;
}

// Result: window.appConfig and window.analyticsToken are both typed correctly!
```

### 3. Computed Type Properties (Type Only)
Type aliases can dynamically calculate keys using mapped types and template literals. Interfaces cannot perform these calculations:

```typescript
type Status = "active" | "inactive";

// Computed keys: creates { activeChanged: boolean; inactiveChanged: boolean; }
type StatusListener = {
    [K in Status as `${K}Changed`]: () => void;
};
```

### 4. Community Conventions
- **Default to Interface**: Use `interface` for defining standard objects, services, component inputs/outputs, and data records. This matches the OOP style of Angular.
- **Use Type Aliases**: When you need to define union types (e.g. `Role = "ADMIN" | "USER"`), tuples, function signatures, or complex computed types.

---

## Code Examples

### Inheritance Comparison
Let's see how both approaches handle inheritance:

```typescript
// Interface Inheritance
interface User {
    id: number;
}
interface Admin extends User {
    roles: string[];
}

// Type Alias Intersection
type UserType = {
    id: number;
};
type AdminType = UserType & {
    roles: string[];
};
```

---

## Summary
- **Interfaces** describe object shapes and support **declaration merging**, making them ideal for extending APIs and packages.
- **Type aliases** are more flexible, supporting **computed properties**, unions, tuples, and primitives.
- Default to using **interfaces** for standard objects and component properties in Angular, using **type aliases** for unions and computed types.

---

## Additional Resources
- [TypeScript Docs: Interface vs. Type Alias](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html#differences-between-type-aliases-and-interfaces)
- [TypeScript Handbook: Mapped Types](https://www.typescriptlang.org/docs/handbook/2/mapped-types.html)
