# Angular Templates Overview

## Learning Objectives
- Write Angular template expressions using double curly braces.
- Use the Safe Navigation Operator (`?.`) to prevent null reference errors.
- Pipe data values inside templates.
- Declare and reference Template Reference Variables (`#var`).

---

## Why This Matters
An Angular template is standard HTML extended with Angular-specific syntax to render dynamic content. If you try to render nested object properties (e.g. `user.profile.avatar`) before the user data has loaded from a database, the browser will throw a null reference error and crash the application. Understanding how to write safe template expressions, use pipes, and reference elements directly using template variables is key to building robust user interfaces.

---

## The Concept

### 1. Template Expressions
Template expressions appear inside double curly braces `{{ expression }}` (interpolation). Angular evaluates these expressions and renders the result as text in the HTML page:

```html
<h3>Welcome, {{ user.username }}</h3>
<p>The total is: {{ price * quantity }}</p>
```
*Constraint:* Template expressions must be side-effect free. They cannot assign values (`=`), run increment operations (`++`), or instantiate objects (`new`).

### 2. Safe Navigation Operator (`?.`)
When loading data asynchronously, variables can be `null` or `undefined` on the first render.
To prevent the application from crashing while waiting for data to load, use the safe navigation operator (`?.`). It halts evaluation if a property is missing, resolving to blank instead of throwing an error:

```html
<!-- Safe execution: does not crash if profile is undefined -->
<img src="{{ user.profile?.avatarUrl }}">
```

### 3. Pipes in Templates
Pipes are simple functions used in templates to format values before rendering them (e.g. transforming text to uppercase or formatting currency symbols). Use the pipe operator `|`:

```html
<p>Birthday: {{ user.dob | date:'longDate' }}</p>
```

### 4. Template Reference Variables (`#var`)
A template reference variable (declared using `#varName`) creates a reference to a DOM element or component instance directly in your HTML markup. This allows you to read its properties from other elements in the template:

```html
<!-- Declaring #phone variable on input element -->
<input #phone placeholder="Enter phone">

<!-- Reading the input value directly without TypeScript controller code -->
<button (click)="callPhone(phone.value)">Call</button>
```

---

## Summary
- **Template expressions** inside `{{ }}` evaluate properties dynamically, but must remain side-effect free.
- Use the **safe navigation operator (`?.`)** to prevent null reference crashes during asynchronous data loads.
- **Pipes (`|`)** format data values directly in templates.
- **Template reference variables (`#var`)** create references to DOM elements directly in your HTML code.

---

## Additional Resources
- [Angular Docs: Template syntax guide](https://angular.dev/guide/templates)
- [Angular Docs: Template variables](https://angular.dev/guide/templates/variables)
