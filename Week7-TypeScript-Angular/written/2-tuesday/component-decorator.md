# The @Component Decorator

## Learning Objectives
- Explain the role of the `@Component` decorator.
- Configure component selector, template, and style path parameters.
- Contrast inline templates/styles with external file paths.
- Explain Angular's CSS ViewEncapsulation modes.

---

## Why This Matters
In Angular, components are the building blocks of user interfaces. A component consists of a TypeScript class (handling logic), an HTML template (handling markup), and a CSS stylesheet (handling layout). But how do these three separate files combine? The **`@Component`** decorator attaches metadata to the TypeScript class, linking it to its HTML template and CSS stylesheet and instructing Angular how to instantiate and style it.

---

## The Concept

### 1. Component Metadata Parameters
The `@Component` decorator accepts a configuration object with three core properties:

```typescript
@Component({
  selector: 'app-user-card', // 1. The custom HTML tag used to instantiate this component
  templateUrl: './user-card.component.html', // 2. Path to the HTML layout file
  styleUrls: ['./user-card.component.css']  // 3. Array of paths to stylesheet files
})
export class UserCardComponent { }
```

### 2. Selector Rules
The `selector` parameter defines the custom HTML tag name. By default, the Angular CLI prefixes selectors with `app-` (e.g. `<app-user-card></app-user-card>`) to prevent naming collisions with standard HTML5 elements.

### 3. Inline vs. External Files
For small templates or styles (typically under 10 lines of code), you can declare them inline directly inside the decorator using `template` and `styles` instead of pointing to external files:

```typescript
@Component({
  selector: 'app-alert',
  template: `<div class="alert-box"><p>{{ message }}</p></div>`,
  styles: [`.alert-box { border: 1px solid red; padding: 10px; }`]
})
export class AlertComponent {
  message = "Alert!";
}
```

### 4. View Encapsulation
By default, Angular scopes CSS styles to the component where they are declared. If you define a class styling `.btn` in a component, that styling will not leak out to affect buttons in other components.
This is achieved using **`ViewEncapsulation.Emulated`** (the default mode), where Angular dynamically injects unique attributes (like `_ngcontent-c1`) to elements at runtime to scope their CSS.

---

## Summary
- **`@Component`** links a TypeScript controller class to its HTML layout and CSS styles.
- The **`selector`** property defines the custom tag name (e.g. `<app-user-profile>`).
- Use **`templateUrl`** for external files and **`template`** for inline HTML strings.
- **View Encapsulation** scopes CSS styles locally to their components by default, preventing style pollution.

---

## Additional Resources
- [Angular Docs: Components Reference](https://angular.dev/guide/components)
- [Angular Docs: View Encapsulation guide](https://angular.dev/guide/components/styling)
