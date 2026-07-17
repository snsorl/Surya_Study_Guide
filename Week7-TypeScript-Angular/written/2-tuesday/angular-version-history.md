# Angular Version History and Evolution

## Learning Objectives
- Differentiate between AngularJS and modern Angular (v2+).
- Explain Standalone Components introduced in Angular 14.
- Describe the Signals state management system introduced in Angular 17.
- Summarize key Angular version history milestones.

---

## Why This Matters
If you search online for help with Angular, you will find tutorials referencing concepts like `NgModule` and `Zone.js`, alongside modern guides referencing `standalone: true` and `Signals`. This discrepancy occurs because Angular has evolved significantly over the years. To write clean code today, you must understand the difference between legacy configurations (like AngularJS and standard Modules) and modern, optimized features (like Standalone Components and Signals).

---

## The Concept: Key Milestones

```
  AngularJS (v1.x)        Angular 2 (2016)        Angular 14 (2022)      Angular 17 (2023)
┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│ JavaScript       │    │ Complete rewrite │    │ Standalone       │    │ Signals         │
│ Scope bindings   │    │ TypeScript-first │    │ Optional         │    │ Control Flow    │
│ Controller       │    │ Component model  │    │ NgModules        │    │ Defer rendering │
└──────────────────┘    └──────────────────┘    └──────────────────┘    └─────────────────┘
```

### 1. AngularJS vs. Modern Angular (v2+)
- **AngularJS (v1.x)**: Released in 2010. It was a JavaScript framework based on controllers, scopes, and dirty checking. It suffered from performance issues on complex sites.
- **Modern Angular (v2+)**: Released in 2016. It was a complete rewrite of the framework, written in TypeScript and adopting a component-based architecture. To prevent confusion, the original framework is called **AngularJS**, and modern versions are simply called **Angular**.

### 2. Standalone Components (v14+)
Before Angular 14, every component had to be declared in an `NgModule` to be compiled. This introduced boilerplate code.
- **Standalone Components** allow you to declare components, directives, and pipes that import their own dependencies directly, making `NgModule` declarations optional.
- Standalone components are declared by setting `standalone: true` in their `@Component` decorator metadata.

### 3. Signals (v17+)
Historically, Angular used **Zone.js** to detect change events. Zone.js intercepts all asynchronous tasks (like clicks or timers) and triggers change detection globally across the entire DOM tree, which can cause performance overhead.
- **Signals** (introduced in v17) provide a reactive way to track state changes. Instead of dirty-checking the entire page, Signals notify Angular of the exact component that changed, enabling fine-grained, localized rendering updates.

---

## Summary
- **AngularJS (v1.x)** was a legacy JavaScript framework; **modern Angular** is a completely rewritten TypeScript framework.
- **Standalone Components** (v14+) make `NgModule` declarations optional by allowing components to manage their own imports.
- **Signals** (v17+) introduce a reactive state management model, optimizing change detection and rendering performance.

---

## Additional Resources
- [Angular Docs: Standalone Components Guide](https://angular.dev/guide/components/importing)
- [Angular Docs: Signals Guide](https://angular.dev/guide/signals)
