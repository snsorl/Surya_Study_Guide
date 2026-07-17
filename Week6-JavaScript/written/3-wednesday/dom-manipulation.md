# DOM Manipulation: Modifying the Page

## Learning Objectives
- Create new DOM nodes dynamically using `createElement`.
- Modify element properties using `textContent`, `innerHTML`, `setAttribute`, and `classList`.
- Insert nodes into the DOM tree using `appendChild`, `prepend`, `insertBefore`, and `insertAdjacentHTML`.
- Delete elements safely using `remove` and `removeChild`.

---

## Why This Matters
Querying elements is only the first step. To build interactive web apps, we need to alter the page dynamically—for instance, appending new tasks to a list, changing the color of a status badge, or disabling a button during form submission. This guide covers how to create, modify, insert, and delete DOM elements safely.

---

## The Concept

### 1. Creating Nodes
- **`document.createElement(tagName)`**: Instantiates a new element node of the specified HTML tag (e.g. `div`, `li`). The element is created in memory but is not yet visible on the page.

### 2. Modifying Content and Attributes
- **`textContent`**: Gets or sets the text content of a node. It escapes HTML tags, protecting against Cross-Site Scripting (XSS) attacks.
- **`innerHTML`**: Gets or sets raw HTML markup. It parses HTML string tags.
  *Security warning:* Assigning untrusted user input directly to `innerHTML` can introduce severe XSS security vulnerabilities.
- **`classList`**: An API to modify element CSS class designations without string operations.
  - `classList.add(name)`
  - `classList.remove(name)`
  - `classList.toggle(name)` (adds class if missing, removes if present)
- **`setAttribute(name, value)`** and **`removeAttribute(name)`**: Modify HTML attributes directly (e.g., `disabled`, `src`).

### 3. Inserting Nodes into the DOM Tree
To render in-memory elements on the page, they must be appended as child nodes of a parent element:
- **`parent.appendChild(child)`**: Adds the child element to the **end** of the parent's child list.
- **`parent.prepend(child)`**: Adds the child element to the **beginning** of the parent's child list.
- **`parent.insertBefore(newChild, referenceChild)`**: Inserts the new child immediately before the reference child.
- **`parent.insertAdjacentHTML(position, text)`**: Parses an HTML string and inserts the parsed nodes at a specific position relative to the element (e.g. `"beforeend"`).

### 4. Deleting Nodes
- **`element.remove()`**: Deletes the element directly from the DOM tree.
- **`parent.removeChild(child)`**: Removes the child node from the parent. Returns the removed node reference.

---

## Code Examples

### Building a Dynamic Task Item Safely
Let's see how to build and insert a styled task element with a delete button safely:

```javascript
const listContainer = document.querySelector("#todo-list");

function addNewTodo(todoText) {
    // 1. Create wrapper element
    const item = document.createElement("li");
    item.classList.add("todo-item", "pending"); // Apply CSS classes
    
    // 2. Set text content safely (escapes HTML tags automatically)
    item.textContent = todoText; 
    
    // 3. Create delete button
    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete";
    deleteBtn.classList.add("btn-danger");
    
    // 4. Bind delete action directly
    deleteBtn.addEventListener("click", () => {
        item.remove(); // Deletes the entire item row from the DOM
    });
    
    // 5. Assemble and insert into the DOM
    item.appendChild(deleteBtn);
    listContainer.appendChild(item);
}
```

---

## Summary
- Use **`document.createElement`** to instantiate HTML nodes in memory.
- Prefer **`textContent`** over `innerHTML` when writing plain text to prevent **XSS security flaws**.
- Use the **`classList`** API to manage CSS states instead of parsing raw `class` attributes.
- Render elements on the page by appending them to parent elements using **`appendChild`** or **`prepend`**.
- Delete elements directly using the **`remove()`** method.

---

## Additional Resources
- [MDN Web Docs: Document.createElement](https://developer.mozilla.org/en-US/docs/Web/API/Document/createElement)
- [OWASP: Cross-Site Scripting (XSS) Prevention](https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html)
