# Two-Way Data Binding in Angular

## Learning Objectives
- Write two-way data bindings using the `[(ngModel)]` directive syntax.
- Explain how `[(ngModel)]` combines property and event binding under the hood.
- Import `FormsModule` to enable form bindings.
- Contrast template-driven form bindings with reactive forms.

---

## Why This Matters
One-way data binding works well for pushing data from your code to the UI. However, for interactive form elements (like text inputs, select dropdowns, or checkboxes), we need **Two-Way Data Binding**: when the user types in the input, the TypeScript variable updates; and if the variable is updated in code, the input's displayed value updates automatically. Angular implements this using the **`[(ngModel)]`** directive, simplifying user input handling.

---

## The Concept

### 1. The Banana-in-a-Box Syntax `[()]`
Two-way data binding uses the "banana-in-a-box" syntax: `[(ngModel)]`.
- **Square Brackets `[]`**: Set property binding (input).
- **Parentheses `()`**: Set event binding (output).

```
    [(ngModel)] = Property Binding [value] + Event Binding (input)
```

```html
<!-- Automatically synchronizes the input value with 'user.username' -->
<input [(ngModel)]="user.username">
```

### 2. Under the Hood
The `[(ngModel)]` directive is syntactic sugar that combines property binding with a change event listener. The code above compiles to:
```html
<input [ngModel]="user.username" (ngModelChange)="user.username = $event">
```
When the user types, the input fires an event, which updates the `username` property. If the property is modified in TypeScript, the new value is pushed back to the input.

### 3. Setup Requirements
To use `ngModel`, you must import **`FormsModule`** from `@angular/forms` into your `@NgModule` imports array. If you forget this import, the compiler will throw a template parsing error:
`"Can't bind to 'ngModel' since it isn't a known property of 'input'."`

---

## Code Examples

### Synchronized Input Dashboard
Below is an example showing how two-way binding synchronizes form inputs with your component state:

```typescript
import { Component } from '@angular/core';

@Component({
  selector: 'app-user-form',
  template: `
    <div class="form-container">
      <h3>Edit Profile</h3>
      <!-- Binds text input to 'username' -->
      <input type="text" [(ngModel)]="username" placeholder="Enter username">
      
      <!-- Binds checkbox to 'receiveNotifications' -->
      <label>
        <input type="checkbox" [(ngModel)]="receiveNotifications">
        Subscribe to updates
      </label>

      <hr>
      <h4>Live Preview:</h4>
      <p>Username: {{ username }}</p>
      <p>Subscribed: {{ receiveNotifications ? 'Yes' : 'No' }}</p>
    </div>
  `
})
export class UserFormComponent {
  username: string = "Alice";
  receiveNotifications: boolean = true;
}
```

---

## Summary
- **Two-way data binding** synchronizes inputs and component variables automatically.
- It uses the "banana-in-a-box" syntax: **`[(ngModel)]`**.
- Using `ngModel` requires importing **`FormsModule`** from `@angular/forms` in your module.
- Under the hood, `[(ngModel)]` combines property binding (`[ngModel]`) and event binding (`(ngModelChange)`).

---

## Additional Resources
- [Angular Docs: Two-way Binding](https://angular.dev/guide/templates/two-way-binding)
- [Angular Docs: Template-driven Forms](https://angular.dev/guide/forms/template-driven-forms)
