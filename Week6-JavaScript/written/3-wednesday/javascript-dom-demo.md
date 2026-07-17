# JavaScript DOM Demo Read-Along

## Learning Objectives
- Trace how JavaScript interacts with and manipulates the document structure shown during the live demonstration.
- Identify methods used to bind events and invoke backend API queries.
- Analyze state encapsulation using closures.

---

## Why This Matters
As web systems evolve, standard text interfaces are replaced by interactive graphical interfaces. During Wednesday's demo, the instructor constructed a live "Todo Application" frontend from scratch, showcasing how JavaScript links to the DOM tree and communicates asynchronously with the Spring Boot backend. This document acts as your annotated reference companion, outlining why specific selectors, event mappings, and async handlers were used.

---

## The Guided Demo Script

### 1. Document Structure & State Encapsulation
The demo started with a simple container class to hold state securely using a closure:

```javascript
function createTodoStore() {
    let todos = []; // Encapsulated private state
    
    return {
        getTodos: () => [...todos], // Return shallow copy to protect state
        addTodo: (todo) => { todos.push(todo); },
        setTodos: (newTodos) => { todos = newTodos; }
    };
}
const store = createTodoStore();
```

### 2. Selecting Elements and Binding Events
The instructor bound form submission events and input fields to control execution flows:

```javascript
// Selecting elements
const form = document.querySelector("#todo-form");
const input = document.querySelector("#todo-input");
const list = document.querySelector("#todo-list");

// Binding listener
form.addEventListener("submit", async (event) => {
    event.preventDefault(); // Stop standard page reload
    const taskText = input.value.trim();
    if (taskText) {
        await saveTodoToBackend(taskText);
        input.value = ""; // Clear input field
    }
});
```

*Key Syntax Choice:* `event.preventDefault()` is mandatory. Otherwise, browsers execute standard form submission, reloading the page and clearing the in-memory application context.

### 3. Fetching and Rendering Data
The demo used `fetch` wrapped in `async/await` to send HTTP operations:

```javascript
async function loadTodos() {
    try {
        const response = await fetch("http://localhost:8080/api/todos");
        if (!response.ok) throw new Error("Failed to load records");
        
        const data = await response.json();
        store.setTodos(data);
        render(); // Trigger DOM redrawing
    } catch (err) {
        console.error("API error:", err);
    }
}

function render() {
    list.innerHTML = ""; // Clear existing DOM nodes
    store.getTodos().forEach(todo => {
        const li = document.createElement("li");
        li.textContent = todo.title;
        list.appendChild(li); // Insert node into DOM tree
    });
}
```

---

## Summary
- State is encapsulated inside **closures** to protect data arrays from global modification.
- **`event.preventDefault()`** overrides default browser reloads during form submissions.
- **`fetch()`** runs asynchronously in the background, updating the view via DOM manipulation once the promise resolves.

---

## Additional Resources
- [MDN Web Docs: Document Object Model](https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model)
- [JavaScript.info: DOM tree](https://javascript.info/dom-nodes)
