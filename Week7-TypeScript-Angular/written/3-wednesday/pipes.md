# Built-in Pipes in Angular

## Learning Objectives
- Define Pipes and describe how they transform data in templates.
- Apply standard built-in pipes (`DatePipe`, `CurrencyPipe`, `DecimalPipe`).
- Use the `AsyncPipe` to subscribe to observables directly in HTML.
- Chain multiple pipes together.
- Pass parameters to configure pipe formatting.

---

## Why This Matters
Raw data from a database is rarely user-friendly: prices are stored as raw floats (e.g. `29.99`), dates as ISO strings (e.g. `2026-07-16T15:24:54Z`), and JSON objects are unreadable on screen. In Angular, we handle these transformations using **Pipes**. Pipes allow you to format data directly in your HTML templates without modifying the underlying variable values in your TypeScript class, keeping your presentation logic clean.

---

## The Concept

### 1. What is a Pipe?
A pipe is a simple function used in template expressions to format values before rendering them. Pipes use the pipe character (`|`):

```html
<!-- Formats 'john' to 'JOHN' -->
<p>User: {{ name | uppercase }}</p>
```

### 2. Built-in Pipes Reference
Angular provides several standard pipes for common data transformations:
- **`DatePipe`**: Formats dates. Accepts parameters like `'shortDate'` or `'yyyy-MM-dd'`.
  ```html
  <p>{{ user.created | date:'mediumDate' }}</p>
  ```
- **`CurrencyPipe`**: Formats numbers as currency. Accepts currency symbols and display rules:
  ```html
  <!-- Formats '29.99' to '$29.99' -->
  <p>{{ product.price | currency:'USD':'symbol':'1.2-2' }}</p>
  ```
- **`DecimalPipe`**: Formats decimals, specifying minimum/maximum fractional digits.
- **`JsonPipe`**: Stringifies objects, which is useful for debugging template variables.

### 3. The `AsyncPipe`
The `AsyncPipe` automatically subscribes to an **Observable** or **Promise** directly in your template, returning the latest emitted value and automatically unsubscribing when the component is destroyed to prevent memory leaks:

```html
<!-- Automatically unpacks and renders the title when the stream emits -->
<h3>{{ postObservable$ | async }}</h3>
```

### 4. Pipe Chaining
You can chain multiple pipes together to apply multiple formatting steps in sequence:

```html
<!-- Formats the date, then converts the resulting string to uppercase -->
<p>{{ user.dob | date:'medium' | uppercase }}</p>
```

---

## Summary
- **Pipes (`|`)** format raw data for display directly in HTML templates.
- Angular provides built-in pipes for **dates, currencies, numbers, and case formatting**.
- Pass parameters to pipes using the colon syntax (e.g. **`date:'short'`**).
- Use **`AsyncPipe`** to subscribe to Observables directly in templates, preventing memory leaks automatically.

---

## Additional Resources
- [Angular Docs: Pipes Reference](https://angular.dev/guide/pipes)
- [Angular Docs: AsyncPipe API](https://angular.dev/api/common/AsyncPipe)
