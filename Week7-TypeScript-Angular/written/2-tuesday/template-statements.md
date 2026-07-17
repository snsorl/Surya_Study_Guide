# Angular Template Statements

## Learning Objectives
- Write Template Statements inside event bindings.
- Differentiate between Template Expressions and Template Statements.
- Access the native event payload using the `$event` parameter.
- Define statement context rules.

---

## Why This Matters
While template *expressions* are used to read and format data for display, template *statements* are used to respond to user actions (like click events or keystrokes). Understanding how template statements execute, how they access event payloads (like input values or key codes), and what expressions are prohibited is key to connecting your HTML templates to your TypeScript controller logic.

---

## The Concept

### 1. Template Statements vs. Expressions
- **Template Expressions**: Read values to render them on screen. They must be side-effect free (cannot modify variables).
- **Template Statements**: Respond to events. They **have side effects** (typically modifying properties in your TypeScript controller or calling service methods).

```html
<!-- Expression (Read-only, no modifications) -->
<p>Current user: {{ user.name }}</p>

<!-- Statement (Triggers side effects on click) -->
<button (click)="user.name = 'Bob'">Rename</button>
```

### 2. Passing the Event Payload (`$event`)
When a DOM event triggers (e.g. click, keyup, input), the browser generates an event object containing details about the action. In Angular, you can access this payload using the reserved variable name **`$event`**:

```html
<!-- Passing event to TypeScript controller method -->
<input (input)="onSearchInput($event)">
```
Inside your TypeScript controller class, you type this parameter as a standard DOM event:
```typescript
onSearchInput(event: Event) {
    const inputVal = (event.target as HTMLInputElement).value;
    console.log("Search query:", inputVal);
}
```

### 3. Syntax Constraints
To keep UI code clean and maintainable, template statements have syntax limits. The following are **prohibited** inside template statements:
- Declaring variables (`let`, `const`, `var`).
- Bitwise operators (`&`, `|`, `^`).
- Creating new object instances via `new`.

---

## Summary
- **Template statements** respond to user events and are designed to perform **side effects** (like calling methods or updating properties).
- Use **`$event`** inside event bindings to pass browser event payloads to your controller methods.
- Statements cannot declare variables, construct objects, or perform bitwise operations.

---

## Additional Resources
- [Angular Docs: Template Statements](https://angular.dev/guide/templates/statements)
- [Angular Docs: Event binding](https://angular.dev/guide/templates/event-binding)
