# Attribute Directives in Angular

## Learning Objectives
- Define Attribute Directives and explain how they modify element appearance/behavior.
- Apply dynamic styles using `ngClass` and `ngStyle`.
- Create a Custom Attribute Directive using `@Directive`.
- Intercept host events using `@HostListener` and bind properties using `@HostBinding`.

---

## Why This Matters
While structural directives add or remove elements, **Attribute Directives** modify the appearance or behavior of existing elements. Angular provides built-in attribute directives to dynamically toggle classes and inline styles based on component states. Furthermore, creating custom attribute directives allows you to wrap complex behavior—like changing an element's background color when hovered—into clean, reusable decorators that can be applied to any HTML element.

---

## The Concept

### 1. Built-in Attribute Directives
- **`ngClass`**: Dynamically toggles multiple CSS classes. It accepts an object where keys are class names and values are boolean conditions:
  ```html
  <div [ngClass]="{ 'success-alert': isSuccess, 'error-alert': !isSuccess }">
      Notification Alert
  </div>
  ```
- **`ngStyle`**: Dynamically sets multiple inline styles:
  ```html
  <div [ngStyle]="{ 'font-size.px': isLarge ? 24 : 14, 'color': fontColor }">
      Styled Text
  </div>
  ```

### 2. Custom Attribute Directives
A custom attribute directive is a class decorated with `@Directive`.
- **`@HostListener`**: Intercepts DOM events fired by the element (host) where the directive is applied.
- **`@HostBinding`**: Dynamically binds element properties (like CSS styles or attributes) directly in the directive code.

---

## Code Examples

### Implementing a Hover Highlight Directive
Below is a custom directive that updates the host element's background color when a user hovers over it:

```typescript
import { Directive, HostListener, HostBinding, Input } from '@angular/core';

@Directive({
  selector: '[appHighlight]' // Applied as an attribute (e.g. <p appHighlight="yellow">)
})
export class HighlightDirective {
  // Allows users to pass a custom highlight color (defaults to yellow)
  @Input('appHighlight') highlightColor: string = 'yellow';

  // Bind the host's background-color style property to our local variable
  @HostBinding('style.backgroundColor') backgroundColor: string = 'transparent';

  // Listen for host mouseenter events
  @HostListener('mouseenter') onMouseEnter() {
    this.backgroundColor = this.highlightColor;
  }

  // Listen for host mouseleave events
  @HostListener('mouseleave') onMouseLeave() {
    this.backgroundColor = 'transparent';
  }
}
```

---

## Summary
- **Attribute Directives** modify the appearance, styling, or behavior of existing DOM elements.
- Use **`ngClass`** and **`ngStyle`** to dynamically set CSS classes and inline styles based on component variables.
- Use **`@HostListener`** to listen to events fired by the element where the directive is applied.
- Use **`@HostBinding`** to update the host element's styles or properties programmatically.

---

## Additional Resources
- [Angular Docs: Attribute Directives](https://angular.dev/guide/directives/attribute-directives)
- [Angular Docs: Custom Directives guide](https://angular.dev/guide/directives/custom-directives)
