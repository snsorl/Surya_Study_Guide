# Lab: Dynamic Color Palette Generator

## Core Tasks
1. Open `starter_code/index.html` in your editor.
2. **Implement Dynamic Card Creation**: Inside the form's submit listener callback, implement code to create color card elements dynamically:
   - Create a div container (`class="color-card"`).
   - Create an inner color swatch element (`class="color-block"`) and set its background color to the hex code value.
   - Create a text container (`class="color-hex"`) showing the hex code value.
   - Create a delete button (`class="delete-btn"`).
   - Append children and render the card inside the `#palette-grid` container.
3. **Implement Event Delegation**: Instead of binding click actions to each delete button during creation, register a single click event listener on the `#palette-grid` element. Check if the clicked target matches `.delete-btn` and delete its parent `.color-card` node.

---

## Technical Guidelines
- Make sure to clear the text input field once a color card is successfully created.
- Escape any text values assigned to elements using `textContent` to prevent script injections.
- Run this file in your browser to verify rendering and interactions.

---

## Definition of Done
- Submitting a valid hex code (like `#ffaa00` or `#333`) inserts a matching color block card into the grid view.
- Submitting invalid inputs is blocked by form validation rules.
- Clicking any card's delete button removes that specific card from the page.
- Card deletion is handled via event delegation on the grid container.
