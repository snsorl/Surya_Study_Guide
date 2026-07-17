# Elements and Attributes

## Learning Objectives
- Define and implement global attributes (`id`, `class`, `style`, `title`).
- Create and read custom data attributes (`data-*`).
- Identify element-specific attributes (e.g., `href` for `<a>`, `src` for `<img>`).
- Utilize accessibility attributes (`alt`, `aria-label`, `role`) to build inclusive user interfaces.

---

## Why This Matters
HTML attributes are the primary way we configure how elements behave and how they integrate with styles and scripts. For example, without the `class` attribute, you cannot apply reusable CSS styles across elements. Without accessibility attributes like `alt` or `aria-label`, screen readers used by visually impaired users will fail to read your content, representing an exclusionary barrier and violating compliance standards (like WCAG). Mastering attributes allows you to build pages that are functional, styleable, and accessible.

---

## Concept

### Global Attributes
Global attributes can be applied to **any** HTML element. The most commonly used global attributes are:

-   **`id`**: Defines a unique identifier for an element across the entire document. No two elements in a single file should share the same ID. Used for unique CSS styling or target selectors in JS (`document.getElementById()`).
-   **`class`**: Assigns one or more class names to an element. Multiple elements can share classes. Used for applying reusable CSS styles.
    -   *Example*: `<div class="card active highlighted">` (Assigns three classes).
-   **`style`**: Contains inline CSS declarations. Inline styles should be used sparingly, as they bypass external stylesheets and make code harder to maintain.
-   **`title`**: Adds advisory information, usually displayed as a tooltip when a user hovers their mouse over the element.

### Element-Specific Attributes
Some attributes are only valid on specific elements:
-   `<a>` (Anchor): Requires `href` (hyperlink reference) and optionally `target` (e.g., `_blank` to open in a new tab).
-   `<img>` (Image): Requires `src` (source path) and `alt` (alternative text description).
-   `<input>`: Requires `type` (e.g., `text`, `password`, `submit`) and uses configuration attributes like `value`, `placeholder`, `disabled`, or `required`.

### Custom Data Attributes (`data-*`)
HTML allows you to store custom private data values directly on standard HTML elements without breaking validation rules. These must begin with the prefix `data-`:
-   *Example*: `<div id="user-card" data-user-id="42" data-role="moderator">`

In JavaScript, you can easily read these attributes using the element's `dataset` API:
```javascript
const card = document.getElementById('user-card');
const userId = card.dataset.userId; // returns "42" (automatically camel-cased)
```

### Accessibility Attributes (ARIA)
Web accessibility ensures that websites remain fully functional for users accessing them via screen readers, keyboard-only inputs, or assistive utilities.
-   **`alt`**: Every image tag must have an `alt` attribute describing the content. If the image is purely decorative, set it to empty (`alt=""`).
-   **`aria-label`**: Provides a string label for elements that do not contain visible text context (like an icon-only button).
    -   *Example*: `<button aria-label="Close modal">X</button>`
-   **`role`**: Defines the purpose of an element to assistive technology when standard semantic tags cannot be used.

---

## Code Example

Here is a block of HTML demonstrating global attributes, custom data attributes, accessibility definitions, and element-specific configurations.

```html
<!-- Reusable card component using classes, unique ID, and data attributes -->
<div id="item-row-101" class="product-card featured" data-item-id="101" data-stock="12">
    
    <!-- Image with mandatory alt text for accessibility -->
    <img src="/images/shoes.jpg" alt="Red running shoes with white soles" class="product-image">
    
    <h3>Athletic Shoes</h3>
    
    <!-- Link using element-specific attributes -->
    <a href="/products/shoes" class="details-link">View Product details</a>
    
    <!-- Icon-only button configured with aria-label for screen readers -->
    <button class="btn-delete" aria-label="Delete Athletic Shoes product">
        <i class="icon-trash"></i>
    </button>
    
</div>
```

---

## Summary
-   **Global attributes** (like `id` and `class`) apply to all elements to enable target selection and reusable styling.
-   **Element-specific attributes** (like `href` and `src`) configure unique element actions and asset links.
-   **`data-*` attributes** embed custom, private parameters on HTML tags that can be read dynamically in JavaScript.
-   **Accessibility parameters** (like `alt` and `aria-label`) ensure applications remain readable and navigateable by screen readers and assistive devices.

---

## Additional Resources
-   [MDN: HTML Attribute Reference](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes)
-   [W3Schools: HTML Attributes](https://www.w3schools.com/html/html_attributes.asp)
-   [W3C Web Accessibility Initiative (WAI) - Introduction to ARIA](https://www.w3.org/WAI/standards-guidelines/aria/)
