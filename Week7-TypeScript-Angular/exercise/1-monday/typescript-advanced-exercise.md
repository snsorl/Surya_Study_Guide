# Lab: Modeling a Product Catalog in TypeScript

## Objectives
- Implement TypeScript class syntax hierarchies and access modifiers.
- Use built-in utility types (`Partial`, `Pick`, `Omit`) to create clean data transfer objects.
- Write generic classes to handle collection structures safely.
- Create method decorators to audit class execution paths.

---

## The Scenario
Our e-commerce team is building a frontend dashboard, and we need to model our backend Product catalog in TypeScript. You need to write a type-safe Product schema, implement DTO variations, write a generic container for paginated query results, and implement a decorator that logs warning messages whenever a deprecated catalog method is called.

---

## Core Tasks

### Task 1: Complete the Product Interface and DTOs
Navigate to `starter_code/product-catalog.ts` and inspect the structures:
- Define the `Product` interface shape contract.
- Using utility types, declare:
  - `CreateProductDto`: Omit the system-generated `id` and `createdDate` fields.
  - `UpdateProductDto`: Set all fields of `CreateProductDto` as optional to support partial edits.

### Task 2: Implement the Generic PaginatedResponse Class
Complete the generic class `PaginatedResponse<T>`:
- The class should declare a private property `items` holding an array of type `T[]`, a public constructor, and a getter method to retrieve the items safely.

### Task 3: Create the Deprecated Decorator
Write a method decorator factory `@Deprecated(message: string)`:
- When a method annotated with `@Deprecated` runs, it should log a warning message to the console containing the warning string, before executing the underlying method.

---

## Verification and Execution
To compile and execute your typescript solution, run:
```bash
npx tsc starter_code/product-catalog.ts
node starter_code/product-catalog.js
```
Confirm the console output displays:
1. The paginated search items list.
2. A warning trace log from the `@Deprecated` decorator when the old fetch API is called.
