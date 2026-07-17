# Exercise: Styling the Recipe Page

## Objective
Style your previously built `recipe.html` page using a separate, external CSS stylesheet. Apply a layout grid using Flexbox, define card containers using the CSS Box Model, and practice selector rules.

---

## Scenario
The raw HTML recipe page is structured but visually plain. You need to apply custom stylesheet rules to transform it into a responsive, clean, and highly readable card interface.

---

## Core Tasks

### 1. Linking External Stylesheet
- Create a CSS file named `styles.css` in the same directory as your HTML file.
- Decouple your design: link the stylesheet within the `<head>` block of `recipe.html` using the `<link>` tag. Do not use inline styles or internal style blocks.
- Reference: [inline-internal-and-external-stylesheets.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/3-wednesday/inline-internal-and-external-stylesheets.md)

### 2. Box Model Configuration
- Apply a global border-box reset using `* { box-sizing: border-box; }`.
- Style your primary sections (such as the main article and recipe sections) as card modules with:
  - Custom border styles.
  - Border radius margins.
  - Padding separations.
- Reference: [css-properties.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/3-wednesday/css-properties.md)

### 3. Flexbox Layout
- Style the header navigation block: make the link list flow horizontally using `display: flex` and separate links using spacing gaps or alignment properties.
- Distribute the main page layout: position the `<article>` content area and the `<aside>` sidebar side-by-side using Flexbox rules on a parent container block.
- Ensure the layout degrades gracefully (stacks vertically) on narrow viewports.

### 4. Selector Configuration
- Use class selectors (`.classname`) to style the recipe cards and button designs.
- Use ID selectors (`#idname`) to style unique layout landmarks (like a site logo container or the submission form).
- Use descendant selectors (e.g. `.nav-list li a`) to target specific links.
- Reference: [class-and-id-selectors.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/3-wednesday/class-and-id-selectors.md)

---

## Definition of Done
- A `styles.css` file exists and is correctly loaded by `recipe.html`.
- Global layouts align side-by-side using Flexbox without hardcoded float coordinates or absolute offsets.
- Card paddings and margins render correctly.
- Layout remains responsive and readable when resizing the browser view width.
