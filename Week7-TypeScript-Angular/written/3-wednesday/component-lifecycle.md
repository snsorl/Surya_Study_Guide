# Component Lifecycle Hooks

## Learning Objectives
- Map the execution sequence of Angular's component lifecycle hooks.
- Identify the purpose of `ngOnChanges` and explain when it executes.
- Use `ngOnInit` for initial data loading.
- Clean up resources (like event listeners or subscriptions) inside `ngOnDestroy`.

---

## Why This Matters
An Angular component is not static: it is instantiated, mounted to the DOM, receives input properties, checks for updates, and eventually gets destroyed when the user navigates away. If you attempt to fetch data from a database inside a class `constructor` before the component's input properties have been bound, you will encounter runtime bugs. Understanding when each **Lifecycle Hook** fires allows you to run setup and cleanup logic at the correct times.

---

## The Concept: Lifecycle Hook Sequence

When a component is loaded, Angular executes lifecycle hooks in the following sequence:

```
[ constructor ] (Class instantiation, DI resolution - Input properties NOT bound yet)
       │
[ ngOnChanges ] (Fires when @Input values are bound or updated)
       │
[ ngOnInit ] (Fires once after first ngOnChanges - Ideal for data fetches)
       │
[ ngDoCheck ] (Fires during every change detection cycle)
       │
[ ngAfterContentInit ] (Fires after child content projection loads)
       │
[ ngAfterViewInit ] (Fires after component layout templates finish loading)
       │
[ ngOnDestroy ] (Fires immediately before destruction - Ideal for cleanup)
```

### 1. The Class Constructor
Used only to initialize local variables and resolve dependency injection (DI). Do not run heavy application logic, database queries, or API calls inside the constructor.

### 2. OnInit vs. OnChanges
- **`ngOnChanges`**: Fires every time an `@Input` property's value reference changes. It receives a `SimpleChanges` object containing current and previous property values.
- **`ngOnInit`**: Fires once after the first `ngOnChanges` cycle. It is the standard hook for running initial setups, calling APIs, and loading data.

### 3. OnDestroy
Fires immediately before the component is destroyed and removed from the DOM. This is the place to clean up resources, unsubscribe from active Observables, and cancel timers to prevent memory leaks.

---

## Code Examples

### Standard Lifecycle Component
Below is an example showing how to implement these lifecycle hooks in a component:

```typescript
import { Component, OnInit, OnDestroy, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-lifecycle-demo',
  template: `<p>Status for task: {{ taskId }}</p>`
})
export class LifecycleDemoComponent implements OnInit, OnChanges, OnDestroy {
  @Input() taskId!: number;
  private mySubscription!: Subscription;

  constructor() {
    console.log('1. Constructor: TaskId is:', this.taskId); // undefined (not bound yet!)
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log('2. ngOnChanges: Input changes detected:', changes);
  }

  ngOnInit() {
    console.log('3. ngOnInit: Component initialized, loading database records...');
    // Safely load data using input properties:
    // this.mySubscription = this.taskService.getDetails(this.taskId).subscribe(...);
  }

  ngOnDestroy() {
    console.log('4. ngOnDestroy: Component cleanup running...');
    if (this.mySubscription) {
      this.mySubscription.unsubscribe(); // Prevents memory leaks
    }
  }
}
```

---

## Summary
- Implement **`OnInit`** and write setup logic inside **`ngOnInit`** to initialize data.
- **`ngOnChanges`** fires every time parent-bound `@Input` properties update.
- Implement **`OnDestroy`** and run cleanup logic (like unsubscribing from observables) inside **`ngOnDestroy`** to prevent memory leaks.
- Avoid placing API calls or heavy business logic inside class **constructors**.

---

## Additional Resources
- [Angular Docs: Lifecycle Hooks Guide](https://angular.dev/guide/components/lifecycle)
- [Angular Docs: SimpleChanges API](https://angular.dev/api/core/SimpleChanges)
