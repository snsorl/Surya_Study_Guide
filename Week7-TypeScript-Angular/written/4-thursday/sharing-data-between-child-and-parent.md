# Sharing Data Between Parent and Child Components

## Learning Objectives
- Pass data down to child components using the `@Input()` decorator.
- Intercept and validate changes to input properties using setters and `ngOnChanges`.
- Emit events up to parent components using the `@Output()` decorator and `EventEmitter`.
- Describe the principle of Unidirectional Data Flow.

---

## Why This Matters
Real-world user interfaces are constructed from nested component trees (e.g. a `Dashboard` parent rendering multiple `TaskCard` children). To make these components dynamic, they must be able to share data and communicate with each other: passing task details down to cards, and letting the dashboard know when a task's status has been modified. In Angular, we manage this parent-child communication using **`@Input()`** and **`@Output()`**, enforcing a clean, structured data flow.

---

## The Concept

### 1. @Input: Pushing Data Down
The `@Input()` decorator marks a class property as writeable, allowing parent components to pass data values down to it inside their templates:

```typescript
// Child Component:
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-task-card',
  template: `<div><h3>{{ title }}</h3></div>`
})
export class TaskCardComponent {
  @Input() title: string = ''; // Property can be bound by parents
}
```
*Parent template binding:*
```html
<!-- Binds parent's 'currentTaskTitle' value to child's 'title' property -->
<app-task-card [title]="currentTaskTitle"></app-task-card>
```

### 2. Intercepting Input Changes
If you need to execute code whenever a parent updates an `@Input` property (like formatting a name or performing audits), you can use a property **setter** (`set`):

```typescript
private _username: string = '';

@Input()
set username(val: string) {
    this._username = val.trim().toUpperCase();
}
get username(): string {
    return this._username;
}
```

### 3. @Output: Emitting Actions Up
To pass data back up to its parent, a child component uses the `@Output()` decorator combined with an **`EventEmitter`**. The child triggers an event, and the parent listens for it:

```typescript
// Child Component:
import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-task-card',
  template: `<button (click)="notifyParent()">Complete</button>`
})
export class TaskCardComponent {
  @Output() taskCompleted = new EventEmitter<number>(); // Output event emitter

  notifyParent() {
    this.taskCompleted.emit(42); // Emits the task ID up
  }
}
```
*Parent template binding:*
```html
<!-- Intercepts event and maps payload ($event) to controller method -->
<app-task-card (taskCompleted)="handleCompletion($event)"></app-task-card>
```

### 4. Unidirectional Data Flow
Angular enforces **Unidirectional Data Flow**: data always travels down the tree (via `@Input`), and events always travel up (via `@Output`). Components cannot modify their inputs directly. This prevents hard-to-trace bugs where child elements modify parent states unexpectedly.

---

## Summary
- Pass data down the component tree using the **`@Input()`** decorator.
- Intercept input updates programmatically using property **setters**.
- Emit events up the component tree using **`@Output()`** and **`EventEmitter`**.
- Angular enforces **Unidirectional Data Flow** to keep component state changes predictable and easy to debug.

---

## Additional Resources
- [Angular Docs: Component Communication](https://angular.dev/guide/components/inputs)
- [Angular Docs: Component Outputs](https://angular.dev/guide/components/outputs)
