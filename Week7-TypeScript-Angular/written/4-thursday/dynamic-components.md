# Dynamic Components in Angular

## Learning Objectives
- Reference DOM elements inside component classes using the `@ViewChild` decorator.
- Use `ViewContainerRef` and `ComponentRef` to load components dynamically.
- Implement an `<ng-template>` container placeholder for dynamic injection.
- Describe common use cases for dynamic components (like modal overlays and tabs).

---

## Why This Matters
Normally, we declare and load child components statically inside templates using their selector tags (e.g. `<app-task-card>`). However, some UI elements—such as modal overlays, toast notifications, and dynamic dashboards—cannot be hard-coded into templates. We need to instantiate and render these components programmatically at runtime. Angular provides **`ViewContainerRef`** and **`@ViewChild`** to handle this dynamic component loading.

---

## The Concept

### 1. ViewContainerRef and ComponentRef
- **`ViewContainerRef`**: Represents a container in the DOM where you can dynamically attach components.
- **`ComponentRef`**: A reference handle to the dynamically created component instance, allowing you to read properties, set inputs, and call methods programmatically.

### 2. Setting Up an Injection Placeholder
To load a component dynamically, define an `<ng-template>` placeholder in your template and mark it with a template reference variable (e.g. `#modalHost`):

```html
<div class="modal-wrapper">
  <!-- The dynamic component will be loaded inside this template block -->
  <ng-template #modalHost></ng-template>
</div>
```

### 3. Loading Components Programmatically
Inside your TypeScript component controller, locate the placeholder tag using **`@ViewChild`** and request the container reference:

```typescript
import { Component, ViewChild, ViewContainerRef } from '@angular/core';
import { AlertComponent } from './alert.component';

@Component({
  selector: 'app-parent',
  templateUrl: './parent.component.html'
})
export class ParentComponent {
  // Locates the #modalHost tag and reads its ViewContainerRef properties
  @ViewChild('modalHost', { read: ViewContainerRef, static: true })
  container!: ViewContainerRef;

  loadAlert(message: string) {
    this.container.clear(); // Clear existing components in container
    
    // Instantiates and appends AlertComponent dynamically
    const componentRef = this.container.createComponent(AlertComponent);
    
    // Access and modify the component instance properties directly in code
    componentRef.instance.message = message;
  }
}
```

---

## Summary
- Declare an **`<ng-template>`** placeholder to act as the target container for dynamic components.
- Locate the placeholder container inside your TypeScript class using the **`@ViewChild`** decorator.
- Dynamically instantiate and render components by calling **`ViewContainerRef.createComponent()`**.
- Access and modify the loaded component's state programmatically using **`ComponentRef.instance`**.

---

## Additional Resources
- [Angular Docs: Dynamic Components Guide](https://angular.dev/guide/components/programmatic-rendering)
- [Angular Docs: ViewContainerRef API](https://angular.dev/api/core/ViewContainerRef)
