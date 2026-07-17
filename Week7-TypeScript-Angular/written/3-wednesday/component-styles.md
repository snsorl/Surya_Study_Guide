# Component Styling and Encapsulation

## Learning Objectives
- Explain Angular's CSS ViewEncapsulation modes.
- Apply styling rules using `:host` and `:host-context` selectors.
- Identify the risks and deprecation status of deep piercing selectors (`::ng-deep`).

---

## Why This Matters
One of the biggest frustrations in large-scale web development is "CSS leak": writing a style rule for a button in one part of the application that accidentally overrides buttons globally. Angular solves this by scoping component styles locally. However, sometimes you need to target a component's wrapper container itself, or customize a third-party child component's styling. Understanding how Angular implements style encapsulation and how to target host elements prevents styling bugs.

---

## The Concept

### 1. View Encapsulation Modes
You can configure encapsulation behavior in your `@Component` decorator settings:

- **`ViewEncapsulation.Emulated` (Default)**: Angular modifies your template's classes and elements at runtime, attaching unique hashes (like `_nghost-c0`) to scope CSS styles strictly to the component.
- **`ViewEncapsulation.None`**: Component styles are compiled as global CSS rules, which can leak and affect other parts of the application.
- **`ViewEncapsulation.ShadowDom`**: Angular uses the browser's native Shadow DOM API, completely isolating elements and styles from the outer document.

### 2. Specialized Selectors

#### The `:host` Selector
Used to style the custom component tag itself (the host element) from within its own CSS stylesheet:
```css
/* Styles the <app-task-card> container element */
:host {
  display: block;
  border: 1px solid #ccc;
  margin: 10px;
}
```

#### The `:host-context` Selector
Applies styles to the component only if a specific class exists on one of its ancestor elements in the DOM tree (useful for implementing global themes):
```css
/* Applies a dark background to the host container only if '.dark-theme' is present on a parent element */
:host-context(.dark-theme) {
  background-color: #333;
}
```

### 3. The `::ng-deep` Pseudo-Class (Deprecated)
Sometimes you need to force styles to apply to a child component (for example, to override style definitions inside a third-party material design card component). The `::ng-deep` pseudo-class disables encapsulation for that rule, allowing styles to pierce down to child elements:

```css
/* Pierces down to style all paragraph tags inside child components */
:host ::ng-deep p {
  color: blue;
}
```
> [!WARNING]
> `::ng-deep` is deprecated and should be used with caution, as it can cause styles to leak globally if not scoped under a `:host` selector.

---

## Summary
- **`ViewEncapsulation.Emulated`** (the default mode) scopes component styles locally using runtime hashing.
- Use the **`:host`** selector to style the component's custom tag wrapper container.
- Use **`:host-context`** to apply style changes based on parent context styles (like themes).
- **`::ng-deep`** is a deprecated style-piercing selector that should be wrapped under `:host` to prevent global CSS leakage.

---

## Additional Resources
- [Angular Docs: Component Styling](https://angular.dev/guide/components/styling)
- [W3C: Shadow DOM encapsulation standard](https://www.w3.org/TR/shadow-dom/)
