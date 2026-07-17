# Event Propagation and Event Delegation

## Learning Objectives
- Describe the three phases of Event Propagation (Capture, Target, Bubbling).
- Halt event propagation using `event.stopPropagation()`.
- Explain how `event.preventDefault()` differs from `event.stopPropagation()`.
- Implement the Event Delegation pattern to manage dynamic child listeners efficiently.

---

## Why This Matters
If you bind click listeners to 1,000 list items individually, your application consumes unnecessary memory. Furthermore, if you dynamically append new items to the list later, they will not inherit those listeners automatically. **Event Delegation** solves both problems. By leveraging how events bubble up the DOM tree, you can bind a single listener to a parent container to manage all present and future child actions.

---

## The Concept

### 1. The Event Propagation Pipeline
When an event occurs on an element (e.g. clicking a `<td>` tag inside a `<table>`), the event travels through three distinct phases:

```
[ document ]  --(1. Capture Phase: goes down)-->  [ <table> ]
                                                      │
[ <td> ] (Target) <== (2. Target Phase) <============┘
   │
   └=========(3. Bubbling Phase: goes up)=========>  [ document ]
```

1.  **Capture Phase**: The event starts at the `window` / `document` root and travels down the DOM tree through ancestor nodes to the target element.
2.  **Target Phase**: The event reaches the target element that was clicked (`event.target`).
3.  **Bubbling Phase**: The event travels back up the DOM tree from the target element through ancestor nodes to the root. By default, almost all event listeners trigger during this bubbling phase.

### 2. Stopping Propagation vs. Preventing Default
- **`event.stopPropagation()`**: Stops the event from traveling further up (bubbling) or down (capturing) the DOM tree. Ancestor elements will not receive the event.
- **`event.preventDefault()`**: Instructs the browser to skip its default action (like reloads or link navigation) but **does not** stop the event from bubbling up the tree.

### 3. The Event Delegation Pattern
Because events bubble up to their parent elements, you can listen for child clicks at the parent container level:
1. Bind a single event listener to the parent container.
2. Inside the handler, check `event.target` to identify which child element was clicked.
3. Perform actions only if the target matches your selection criteria (e.g., using `element.matches()`).

---

## Code Examples

### Implementing Event Delegation
Instead of binding delete listeners to every task element, we bind a single listener to the parent `#todo-list` container. This handles dynamically added elements automatically:

```html
<ul id="todo-list">
    <li>Task 1 <button class="delete-btn">Delete</button></li>
    <li>Task 2 <button class="delete-btn">Delete</button></li>
</ul>

<script>
    const list = document.querySelector("#todo-list");
    
    // Bind a single listener to the parent container
    list.addEventListener("click", (event) => {
        // Check if the clicked element has the 'delete-btn' class
        if (event.target.matches(".delete-btn")) {
            // Find the list item parent and delete it
            const li = event.target.parentElement;
            li.remove();
            console.log("Deleted task successfully!");
        }
    });
</script>
```

---

## Summary
- Event propagation has 3 phases: **Capturing**, **Targeting**, and **Bubbling** (default handler phase).
- **`event.stopPropagation()`** prevents the event from bubbling up to parent nodes.
- **`event.preventDefault()`** disables default browser actions without halting propagation.
- **Event Delegation** optimizes performance by binding a single listener to a parent container to manage actions on dynamic child elements.

---

## Additional Resources
- [MDN Web Docs: Event bubbling and capture](https://developer.mozilla.org/en-US/docs/Learn/JavaScript/Building_blocks/Events#event_bubbling_and_capture)
- [JavaScript.info: Bubbling and capturing](https://javascript.info/bubbling-and-capturing)
