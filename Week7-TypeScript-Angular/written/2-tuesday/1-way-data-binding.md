# One-Way Data Binding in Angular

## Learning Objectives
- Write Interpolation expressions using double curly braces.
- Use Property Binding to set element properties programmatically.
- Bind HTML attributes using Attribute Binding.
- Dynamically toggle styles and classes using Class and Style Bindings.

---

## Why This Matters
In raw DOM manipulation, updating the UI requires finding elements, reading input values, and updating properties manually (e.g. `document.getElementById('img').src = newUrl`). If you forget to write an update statement, your UI becomes out of sync with your application state. Angular resolves this with **One-Way Data Binding**, automatically pushing values from your TypeScript controller class directly into the HTML DOM.

---

## The Concept: Binding Types

```
    [ TypeScript Controller Class ] ===( Pushes Value )===> [ HTML DOM Element ]
```

### 1. Interpolation (`{{ }}`)
Interpolation renders class properties as text within your HTML tags:
```html
<p>User Role: {{ role }}</p>
```

### 2. Property Binding (`[property]`)
Property binding sets DOM element properties programmatically. Enclose the target property in square brackets `[]` and set it to a variable or expression from your controller class:
```html
<!-- Binds controller property 'avatarUrl' to the image's 'src' property -->
<img [src]="avatarUrl">

<!-- Binds boolean property 'isDisabled' to the button's 'disabled' property -->
<button [disabled]="isDisabled">Submit</button>
```

### 3. Attribute Binding (`[attr.xxx]`)
Sometimes you need to bind values to standard HTML attributes that do not have corresponding DOM properties (such as ARIA attributes or table spans). Use the `attr.` prefix:
```html
<td [attr.colspan]="spanCount">Cell Data</td>
```

### 4. Class and Style Bindings (`[class]` / `[style]`)
- **Class Binding**: Dynamically toggles CSS classes:
  ```html
  <!-- Applies the 'active' class only when 'isActive' is true -->
  <div [class.active]="isActive">Navigation Link</div>
  ```
- **Style Binding**: Dynamically applies inline styles:
  ```html
  <!-- Sets the element's color dynamically -->
  <p [style.color]="userThemeColor">Dynamic Text</p>
  ```

---

## Summary
- **Interpolation (`{{ }}`)** renders component properties as text inside your HTML templates.
- **Property Binding (`[property]`)** binds controller values to DOM properties (like `src` or `disabled`).
- Use **Attribute Binding (`[attr.name]`)** for elements that do not have matching DOM properties (like `colspan`).
- Use **Class and Style Bindings** to dynamically update element styles and classes based on component state.

---

## Additional Resources
- [Angular Docs: Property Binding](https://angular.dev/guide/templates/property-binding)
- [Angular Docs: Class and Style Binding](https://angular.dev/guide/templates/class-and-style-binding)
