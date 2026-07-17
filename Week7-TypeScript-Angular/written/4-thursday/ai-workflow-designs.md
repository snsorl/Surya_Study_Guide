# AI Workflow Designs in Angular Development

## Learning Objectives
- Design multi-step AI workflows for generating complex Angular features.
- Define a 4-step prompt chain: Spec → Scaffold → Service → Tests.
- Establish validation gates and handoff points to ensure code quality.

---

## Why This Matters
For simple tasks, a single AI prompt works well. However, when building complete, end-to-end features—like a User Dashboard that fetches data from a backend, handles state validation, and includes unit tests—requesting everything in one prompt can lead to incomplete code, formatting errors, or compilation failures. To prevent this, we design **multi-step AI workflows**: breaking down feature development into a chain of specific prompts, with validation checks at each step to ensure code quality.

---

## The Concept: The Prompt Chain

Rather than generating all feature files at once, follow this 4-step prompt chain:

```
[ Step 1: Design Spec ] ──> Verify types and requirements match backend schemas.
         │
         ▼
[ Step 2: Component Scaffold ] ──> Generate and verify UI layouts and data bindings.
         │
         ▼
[ Step 3: Service Logic ] ──> Generate and verify HttpClient methods and error handling.
         │
         ▼
[ Step 4: Unit Tests ] ──> Generate and execute tests to verify feature behavior.
```

### 1. Step 1: Design Spec (Inputs & Models)
Establish your data models and interface contracts first:
- *Action:* Prompt the AI to generate the TypeScript interfaces and types for your feature (e.g. `User`, `ApiResponse`).
- *Validation Gate:* Confirm these interfaces match the entity schemas exposed by your Spring Boot backend endpoints.

### 2. Step 2: Component Scaffold (Views & Bindings)
Scaffold the UI components and layout templates:
- *Action:* Prompt the AI to generate the component class and HTML template using mock data arrays.
- *Validation Gate:* Run `ng serve` and check the browser console to verify data bindings, layouts, and style classes render correctly.

### 3. Step 3: Service Logic (HTTP Client)
Implement API integrations and state updates:
- *Action:* Generate the Angular Service using `HttpClient` to call backend endpoints, replacing the component's mock data arrays with real API streams.
- *Validation Gate:* Compile the project with `npx tsc` to verify imports and dependency injections resolve without errors.

### 4. Step 4: Unit Tests (Specs)
Write automated tests to verify code paths and boundary cases:
- *Action:* Generate unit test specifications (`.spec.ts` files) for both components and services.
- *Validation Gate:* Run `ng test` to ensure all tests pass cleanly.

---

## Summary
- Break down end-to-end feature development into a **multi-step prompt chain** instead of generating all files at once.
- Chain tasks logically: start with **TypeScript interfaces**, then **UI layouts**, then **API services**, and finally **unit tests**.
- Implement **validation gates** (compiling, visual checks, test runs) at each step to verify correctness before proceeding.

---

## Additional Resources
- [Angular Docs: Testing Overview](https://angular.dev/guide/testing)
- [W3C: Automated Testing Principles](https://www.w3.org/wiki/Testing)
