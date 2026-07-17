# Wednesday Prompt Log: HTML and CSS Generation

## Learning Objectives
- Maintain a structured log of AI prompts used to generate front-end code.
- Critique AI-generated HTML markup for semantic validity and compliance.
- Audit AI-generated CSS layouts for responsiveness and box-model safety.
- Document iterations and modifications made to AI code before final deployment.

---

## Why This Matters
AI assistants excel at generating HTML skeletons and CSS boilerplate. However, if you blindly copy and paste AI-generated markup, you risk importing invalid tags, absolute layout coordinates (which break responsiveness), and inline style attributes that override clean stylesheets. Keeping a prompt log forces you to slow down, review AI output critically, and document structural adjustments. This habit ensures you retain control of your design system and build secure, compliant, and accessible web pages.

---

## The Concept

### The Prompt Log Practice
During UI development sessions, keep this log updated with three components:
1.  **The Prompt**: The exact prompt sent to the AI, detailing context and constraints.
2.  **The Critique**: Your evaluation of the AI's first draft (e.g., checking for semantic structure, CSS unit safety, alt text missing).
3.  **The Patch**: The updated code after correcting AI mistakes.

### Common AI HTML/CSS Mistakes to Check For
-   **Div Soup**: Using generic `<div>` wrappers instead of semantic HTML5 tags (like `<nav>`, `<main>`).
-   **Absolute Units**: Hardcoding layout properties in absolute pixels (`width: 800px;`) rather than relative percentages or viewport coordinates (`width: 100%; max-width: 800px;`).
-   **Inline Styles**: Applying styling directly onto elements (`style="color: red;"`), which destroys CSS cascade specificity rules.
-   **Missing Accessibility**: Forgetting `alt` descriptions on images or failing to map `<label>` attributes to `<input>` boxes.

---

## Code Example

Here is a logged entry illustrating a common frontend AI generation cycle.

### Log Entry: Navigation Bar Generation

#### 1. Initial Prompt
> "Generate HTML and CSS for a navigation bar with a site logo and three links: Home, Tasks, Profile."

#### 2. AI Output Critique
-   *HTML Review*: The AI used `<div class="navbar">` instead of the semantic `<nav>` layout tag. The logo was missing `alt` text.
-   *CSS Review*: The layout was absolute (`width: 1024px`), meaning it would break and overflow on mobile viewports. It also used absolute pixel sizes for text instead of `rem` parameters.

#### 3. Logged Remediation (The Patch)
We replaced the container tag with a semantic `<nav>`, added `alt="Task Management Logo"` to the image, and refactored the CSS to use Flexbox (`display: flex; justify-content: space-between;`) with `width: 100%` relative scaling.

---

## Summary
-   **Documenting prompts** enables teams to archive successful prompting templates and repeat reliable generations.
-   **Auditing HTML outputs** focuses on enforcing semantic tags and accessibility controls.
-   **Auditing CSS layouts** focuses on replacing absolute pixels with responsive viewport parameters and relative units.
-   A prompt log turns AI generation from a copy-paste risk into a structured engineering process.

---

## Additional Resources
-   [Google Cloud: Prompt Engineering Best Practices](https://cloud.google.com/vertex-ai/docs/generative-ai/text/text-prompts)
-   [W3C Web Standards Checklist](https://www.w3.org/standards/webdesign/)
-   [Learn CSS: Responsive Layouts (MDN)](https://developer.mozilla.org/en-US/docs/Learn/CSS/CSS_layout/Responsive_Design)
