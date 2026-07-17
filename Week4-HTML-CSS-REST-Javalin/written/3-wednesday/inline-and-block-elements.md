# Inline and Block Elements

## Learning Objectives
- Differentiate between the layout behaviors of block-level and inline-level elements.
- Identify common block elements (`<div>`, `<p>`, `<h1>` to `<h6>`, `<ul>`, `<ol>`, `<table›`).
- Identify common inline elements (`<span>`, `<a>`, `<img>`, `<strong>`, `<em>`).
- Predict how nested structures behave based on block and inline rules.

---

## Why This Matters
Have you ever tried to position two elements side-by-side (like a label and an input field, or two links), only to have one of them jump to the next line? Or tried to apply top margins to an element, and nothing happened? These visual layout bugs are almost always caused by a misunderstanding of display behaviors. Knowing the differences between block and inline elements—and how they take up space on a page—is crucial to designing predictable layouts and writing efficient CSS.

---

## The Concept

### Two Primary Display Behaviors
Every HTML element has a default display behavior depending on what type of element it is. These behaviors are divided into two main categories:

#### 1. Block-Level Elements
Block-level elements are structural pillars.
-   **Line Behavior**: Always start on a new line. They push subsequent elements down to the next line.
-   **Width Behavior**: Stretch to take up the full horizontal width of their parent container (100% width by default).
-   **Box Properties**: Respect all box-model settings: width, height, margins, and padding.
-   *Common Elements*:
    -   `<div>`: A generic container used to group elements for styling or layout.
    -   `<p>`: Paragraph block.
    -   `<h1>` to `<h6>`: Heading blocks.
    -   `<ul>`, `<ol>`, `<li>`: Lists and list items.
    -   `<table>`, `<form>`: Structural components.

#### 2. Inline-Level Elements
Inline-level elements are inline text formatters or connectors.
-   **Line Behavior**: Do not start on a new line. They sit side-by-side with other inline elements, flowing horizontally like words in a sentence.
-   **Width Behavior**: Only take up as much width as their immediate content requires.
-   **Box Properties**: Do **not** respect height or top/bottom margins. They only accept padding and left/right margins.
-   *Common Elements*:
    -   `<span>`: A generic inline container used to style portions of text.
    -   `<a>`: Anchor links.
    -   `<img>`: Image element (inline-block by default, behaves similarly to inline but respects width/height).
    -   `<strong>`, `<em>`: Bold and italic semantic markup text tags.

```
+-------------------------------------------------------------+
| Block Element (Takes up full width, pushes down)            |
+-------------------------------------------------------------+
| Inline Element | | Another Inline | Text flows inline...    |
+-------------------------------------------------------------+
```

### Nesting Restrictions
-   **Rule**: Block-level elements can contain other block-level elements or inline-level elements.
-   **Rule**: Inline-level elements can contain other inline-level elements, but they must **never** contain block-level elements.
    -   *Incorrect*: `<span><div>Bad nesting</div></span>` (Invalid document structure).

---

## Code Example

Here is a block of HTML code illustrating block containers holding text and inline elements.

```html
<!-- Div acts as a block container, taking up 100% width and starting on a new line -->
<div class="alert-box">
    
    <!-- H2 is block-level, starting on its own line -->
    <h2>Notification Alert</h2>
    
    <!-- Paragraph is block-level, starting on its own line -->
    <p>
        The following information is <strong>highly confidential</strong>. 
        Please do not distribute. You can view our 
        <a href="/privacy">Privacy Policy</a> online.
        <!-- Note: <strong> and <a> are inline elements; they flow inside the paragraph text -->
    </p>

</div>
```

---

## Summary
-   **Block-level elements** (like `<div>` and `<p>`) start on new lines, take up full container width, and respect all CSS box-model settings.
-   **Inline-level elements** (like `<span>` and `<a>`) flow horizontally within text, take up minimal content width, and ignore height and top/bottom margin declarations.
-   Never nest a **block-level element** inside an **inline-level element**, as it violates browser parsing rules.

---

## Additional Resources
-   [MDN: Block-level elements](https://developer.mozilla.org/en-US/docs/Web/HTML/Block-level_elements)
-   [MDN: Inline elements](https://developer.mozilla.org/en-US/docs/Web/HTML/Inline_elements)
-   [W3Schools: HTML Block and Inline Elements](https://www.w3schools.com/html/html_blocks.asp)
