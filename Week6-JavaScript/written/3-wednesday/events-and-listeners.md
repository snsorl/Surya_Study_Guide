# Events and Event Listeners

## Learning Objectives
- Register event listeners using `addEventListener`.
- Identify key event types (`click`, `submit`, `keydown`, `input`, `change`, `DOMContentLoaded`).
- Deregister event handlers cleanly using `removeEventListener`.
- Inspect properties of the `Event` object passed to handlers.

---

## Why This Matters
Web browsers are event-driven environments. The browser waits for user actions—like mouse clicks, form submissions, or key presses—and runs associated JavaScript code in response. Without event listeners, web pages remain static and unresponsive. This guide covers how to capture, inspect, and manage these events cleanly.

---

## The Concept

### 1. The `addEventListener` API
`addEventListener` registers a callback function to run when a specific event occurs on a target element:

```javascript
element.addEventListener(type, listener, options);
```
- **`type`**: A string representing the event name (e.g. `"click"`).
- **`listener`**: The callback function to execute.
- **`options`**: Optional configurations (like registering the event to fire only once).

### 2. Common Event Types
- **`click`**: Fires when a mouse button is clicked on an element.
- **`submit`**: Fires when a `<form>` is submitted. Binding to `submit` is preferred over click handlers on buttons because it captures presses of the `Enter` key automatically.
- **`input`**: Fires in real-time when the value of an `<input>` or `<textarea>` changes.
- **`change`**: Fires when a control loses focus after its value is committed (e.g. selecting an option in a `<select>` dropdown).
- **`keydown`**: Fires when a keyboard key is pressed down.
- **`DOMContentLoaded`**: Fires on the global `document` object when the HTML structure has fully loaded, allowing you to run startup queries safely.

### 3. The `Event` Object
When an event occurs, the browser automatically passes an `Event` object containing metadata to the callback handler:
- **`event.target`**: The specific element that triggered the event.
- **`event.type`**: The name of the event.
- **`event.preventDefault()`**: Stops default browser actions (such as page reloads on form submissions or navigating to links).

### 4. Deregistering Handlers (`removeEventListener`)
To remove an event listener, you must call `removeEventListener` using the **same function reference** that was registered. Anonymous functions cannot be deregistered:

```javascript
// Cannot be removed:
btn.addEventListener("click", () => console.log("Hi"));
btn.removeEventListener("click", () => console.log("Hi")); // Fails (different function references)

// Correct Way:
const handler = () => console.log("Hi");
btn.addEventListener("click", handler);
btn.removeEventListener("click", handler); // Succeeds
```

---

## Code Examples

### Form Handling and Input Validation
```javascript
const loginForm = document.querySelector("#login-form");
const passwordField = document.querySelector("#password");

// 1. Listen for DOM ready before running setup
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOM loaded. Initializing event listeners...");
});

// 2. Real-time validation using the 'input' event
passwordField.addEventListener("input", (event) => {
    const textLength = event.target.value.length;
    if (textLength < 8) {
        passwordField.classList.add("invalid");
    } else {
        passwordField.classList.remove("invalid");
    }
});

// 3. Prevent page reload on submit
loginForm.addEventListener("submit", (event) => {
    event.preventDefault(); // Stop page reload
    console.log("Form data intercepted!");
});
```

---

## Summary
- Use **`addEventListener`** to bind event callbacks to DOM elements.
- **`event.preventDefault()`** is used to block default browser actions, such as page reloads on form submissions.
- The **`event.target`** property points to the element that triggered the event.
- To deregister an event listener using **`removeEventListener`**, you must pass the exact same named function reference.

---

## Additional Resources
- [MDN Web Docs: Introduction to events](https://developer.mozilla.org/en-US/docs/Learn/JavaScript/Building_blocks/Events)
- [JavaScript.info: Introduction to browser events](https://javascript.info/introduction-browser-events)
