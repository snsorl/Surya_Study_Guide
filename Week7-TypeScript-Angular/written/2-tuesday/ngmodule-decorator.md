# The @NgModule Decorator

## Learning Objectives
- Describe the role of `@NgModule` in organizing Angular applications.
- Identify the key properties of `@NgModule` (`declarations`, `imports`, `providers`, `bootstrap`, `exports`).
- Differentiate between the root module (`AppModule`) and feature modules.
- Import standard libraries like `FormsModule` and `ReactiveFormsModule` to enable template tools.

---

## Why This Matters
An Angular application can contain dozens of components, services, directives, and pipes. The browser needs to know how these files relate: which components are visible to each other, which external modules are imported, and which services are available for dependency injection. The **`@NgModule`** decorator acts as a manifest file, grouping related components and configurations into clean, reusable modules.

---

## The Concept

### 1. Structure of @NgModule
An `NgModule` is a TypeScript class decorated with `@NgModule`. It accepts a metadata object containing five key properties:

```typescript
@NgModule({
  declarations: [
    // 1. Components, Directives, and Pipes belonging to this module
  ],
  imports: [
    // 2. Other modules containing components/services this module needs
  ],
  exports: [
    // 3. Components/Directives this module shares with other modules
  ],
  providers: [
    // 4. Services available for dependency injection inside this module
  ],
  bootstrap: [
    // 5. The root component to load first (only used in the root AppModule)
  ]
})
export class UserModule { }
```

### 2. Root Module vs. Feature Modules
- **Root Module (`AppModule`)**: Every standard Angular application has at least one root module, typically called `AppModule`. It bootstraps the application and imports global modules like `BrowserModule`.
- **Feature Modules**: Organized sub-modules that package related features together (e.g. a `BillingModule` or an `AdminModule`). These can be lazy-loaded on demand to optimize performance.

### 3. Importing Standard Libraries
To use built-in Angular tools, you must import their corresponding modules into your `AppModule`:
- **`FormsModule`**: Enables template-driven forms and the `[(ngModel)]` two-way data binding directive.
- **`ReactiveFormsModule`**: Enables reactive, validation-heavy forms.

---

## Code Examples

### Custom NgModule Definition
Below is an example of a feature module configuring components and importing external forms libraries:

```typescript
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskListComponent } from './task-list.component';
import { TaskCardComponent } from './task-card.component';

@NgModule({
  declarations: [
    TaskListComponent,
    TaskCardComponent
  ],
  imports: [
    CommonModule, // Required for built-in directives like *ngIf inside feature modules
    FormsModule   // Required to use ngModel in TaskListComponent templates
  ],
  exports: [
    TaskListComponent // Allows other modules to use the tag <app-task-list>
  ]
})
export class TasksModule { }
```

---

## Summary
- **`@NgModule`** metadata groups components, directives, pipes, and services into organized feature modules.
- **`declarations`** register components, **`imports`** load external modules, and **`exports`** expose components to other modules.
- Import **`FormsModule`** in your module imports array to enable `[(ngModel)]` two-way data binding.

---

## Additional Resources
- [Angular Docs: NgModules guide](https://angular.dev/guide/modules)
- [Angular Docs: Feature Modules](https://angular.dev/guide/modules/feature-modules)
