# HTML Tags and Syntax

## Learning Objectives
- Write valid HTML elements using correct opening, closing, and self-closing tags.
- Apply attributes (name-value pairs) to configure HTML elements.
- Identify and implement void (self-closing) elements.
- Apply nesting rules correctly to maintain a valid document structure.

---

## Why This Matters
If you write invalid HTML (like forgetting to close a tag or nesting tags incorrectly), browsers will try to guess your intention and automatically repair the structure. This is known as "quirks mode". While this prevents pages from crashing, it leads to layout bugs, broken CSS styles, and unpredictable JavaScript errors. Writing syntactically correct HTML ensures your pages render consistently across all browsers and devices.

---

## The Concept

### HTML Element Anatomy
An HTML **element** typically consists of a start tag, attributes, content, and an end tag:

```
  <tagname attribute="value"> Content goes here </tagname>
  |_________________________| |_______________| |________|
          Start Tag               Content        End Tag
```

-   **Start Tag (`<tagname>`)**: Demarcates the beginning of the element.
-   **End Tag (`</tagname>`)**: Demarcates the end of the element. Note the forward slash.
-   **Content**: The text or nested elements located between the tags.
-   **Attributes**: Key-value pairs declared inside the start tag that modify the behavior or styling of the element (e.g., `href` for links, `src` for images, `id` and `class` for styling/script hooks).

### Void Elements (Self-Closing Tags)
Not all HTML elements have content or require closing tags. Elements that cannot contain nested content are called **void elements**. In HTML5, these do not require a closing slash, though you can write them as self-closing for compatibility:
-   `<img>`: Embeds an image. Requires a `src` attribute.
-   `<br>`: Introduces a line break.
-   `<input>`: Creates a form input field.
-   `<meta>`: Defines page metadata.
-   `<link>`: Links external resources like CSS.

*Valid formats*: `<img src="pic.jpg">` or `<img src="pic.jpg" />`.

### Attributes Configuration
Attributes are always placed in the opening tag. They consist of a name and a value, usually written as `name="value"`.
-   **Boolean Attributes**: Some attributes do not require a value. Their presence implies a value of true (e.g., `disabled`, `required`, `checked`).
    -   *Example*: `<input type="text" required>`

### Nesting Rules
HTML elements can be nested inside other elements. This establishes a tree hierarchy of parents, children, and siblings.
-   **Rule**: Elements must be closed in the reverse order they were opened. First opened, last closed.
-   **Incorrect (Overlapping)**:
    -   `<strong>This is <em>overlapping text</strong> elements.</em>`
-   **Correct (Properly Nested)**:
    -   `<strong>This is <em>properly nested</em> elements.</strong>`

In the correct example:
-   `<strong>` is the **parent**.
-   `<em>` is the **child**.
-   The browser wraps `<em>` completely inside `<strong>`.

---

## Code Example

Here is a block of HTML code illustrating start tags, attributes, void elements, and correct hierarchical nesting.

```html
<!-- Example of a paragraph containing structured links and styling -->
<p>
    Welcome to our website. Please check the 
    <a href="/login" target="_blank"><strong>Login Page</strong></a> 
    to access your account details.
</p>

<!-- Example of void elements (no closing tags needed) -->
<hr> <!-- Horizontal line -->
<p>
    Please submit your profile picture:<br>
    <img src="/assets/placeholder.png" alt="Profile Placeholder" width="100">
</p>
```

---

## Summary
-   HTML elements are built with **opening tags**, **closing tags**, **content**, and **attributes**.
-   **Void elements** (like `<img>`, `<br>`, and `<input>`) have no content and do not require closing tags.
-   **Attributes** modify elements and are written as `name="value"` inside the opening tag.
-   Always close tags in the **reverse order** of opening to ensure proper tree structure.

---

## Additional Resources
-   [MDN: HTML basics - Elements](https://developer.mozilla.org/en-US/docs/Learn/Getting_started_with_the_web/HTML_basics#anatomy_of_an_html_element)
-   [W3Schools: HTML Elements](https://www.w3schools.com/html/html_elements.asp)
-   [HTML Void Elements List](https://html.spec.whatwg.org/multipage/syntax.html#void-elements)
