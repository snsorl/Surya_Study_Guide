# Traversing the DOM

## Learning Objectives
- Navigate from a target element up the DOM tree using `parentElement` and `parentNode`.
- Navigate down to child elements using `children`, `childNodes`, and `firstElementChild`.
- Navigate horizontally between adjacent elements using `nextElementSibling` and `previousElementSibling`.
- Differentiate between Node traversal properties and Element traversal properties.

---

## Why This Matters
Elements in a web page are not isolated; they exist inside parent containers, adjacent to sibling tags, and above child containers. Frequently, when a user clicks a button inside a list item, you need to traverse the DOM to find that list item's ID or update an adjacent text block. Instead of querying the entire document again, traversing from the clicked element directly improves performance and keeps your code localized.

---

## The Concept

### 1. Nodes vs. Elements Traversal
The DOM tree contains comments, whitespaces, and text nodes alongside HTML tags. Because of this, traversing APIs are split into two categories:
- **Node Traversal**: Counts all nodes (including blank space text nodes).
- **Element Traversal**: Skips comments and spaces, targeting only HTML element tags.

```
                  +-----------------------+
                  |    Parent Node /      |
                  |    Parent Element     |
                  +-----------------------+
                              ▲
                              │
  +--------------------+      │      +--------------------+
  |  Previous Sibling  |<-----o----->|    Next Sibling    |
  +--------------------+             +--------------------+
                              │
                              ▼
                  +-----------------------+
                  |  First / Last Child   |
                  |  Children Collection  |
                  +-----------------------+
```

### 2. Traversal Property Toolkit

#### Moving Upwards (Parents)
- **`parentElement`**: Returns the parent element node. Returns `null` if the parent is not an element (e.g. the `document` root).
- **`parentNode`**: Returns any parent node, including the `document` node.

#### Moving Downwards (Children)
- **`children`**: Returns a live HTMLCollection of child **element** nodes (skips blank spaces).
- **`childNodes`**: Returns a live NodeList of **all** child nodes (includes spaces and comments).
- **`firstElementChild`** & **`lastElementChild`**: Returns the first or last child **element**.
- **`firstChild`** & **`lastChild`**: Returns the first or last **node** (often a blank whitespace text node).

#### Moving Horizontally (Siblings)
- **`nextElementSibling`** & **`previousElementSibling`**: Returns the adjacent element immediately after or before the current element.
- **`nextSibling`** & **`previousSibling`**: Returns the adjacent node (can be a whitespace text node).

---

## Code Examples

### Traversal and Element Targeting
Consider a layout where clicking a card's close button deletes the parent card element:

```html
<div class="card" id="card-001">
    <h3 class="card-title">Welcome Guide</h3>
    <p>Some description text here.</p>
    <button class="close-btn">Close Card</button>
</div>

<script>
    const closeButton = document.querySelector(".close-btn");
    
    closeButton.addEventListener("click", function(event) {
        // 1. Traverse up to the parent wrapper card
        const card = event.target.parentElement; 
        console.log(card.id); // "card-001"
        
        // 2. Traverse to the card's first child element (the h3 title)
        const title = card.firstElementChild;
        console.log(title.textContent); // "Welcome Guide"
        
        // Delete the card
        card.remove();
    });
</script>
```

---

## Summary
- Use **Element** traversal properties (e.g. `parentElement`, `children`, `nextElementSibling`) to skip blank spaces and comments.
- **`children`** returns only element tags; **`childNodes`** returns all nodes including spacing.
- **`event.target`** combined with **`parentElement`** is a common pattern to target parent container cards or rows from clicked buttons.

---

## Additional Resources
- [MDN Web Docs: Document Object Model traversal properties](https://developer.mozilla.org/en-US/docs/Web/API/Node/parentElement)
- [JavaScript.info: DOM tree navigation](https://javascript.info/dom-navigation)
