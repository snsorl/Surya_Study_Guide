# AI Prompt Logs: Asynchronous & DOM Operations

## Learning Objectives
- Formulate effective prompts for generating async API calls and DOM manipulation.
- Evaluate AI-generated DOM and asynchronous code for quality, performance, and security issues.
- Apply structural refinements to AI outputs to meet clean-code practices.

---

## Why This Matters
AI assistants can quickly write HTML boilerplate, event listeners, and Fetch API templates. However, AI often introduces hidden flaws: it might use `innerHTML` unsafely, neglect to handle HTTP error status codes (like 404 or 500) during fetch operations, or suggest sequential await requests that block performance. This log documents prompt designs and code reviews to ensure AI integrations remain secure and optimized.

---

## Prompt Log Analysis

### 1. Generating a Fetch Handler
- **Initial Prompt**: *"Write a JS function to fetch todos from /api/todos and render them as list items."*
- **AI Output Result**:
  ```javascript
  // AI Output
  function getTodos() {
      fetch("/api/todos")
          .then(res => res.json())
          .then(data => {
              const list = document.getElementById("list");
              data.forEach(item => {
                  list.innerHTML += `<li>${item.title}</li>`; // XSS Vulnerability!
              });
          });
  }
  ```
- **Code Review Audit**:
  - **Security Flaw**: Using `innerHTML +=` with raw API data leaves the app vulnerable to **Cross-Site Scripting (XSS)** if a todo contains malicious script tags (e.g. `<img src=x onerror=alert(1)>`).
  - **Error Handling**: The code assumes the network call will always succeed. It lacks a `.catch()` block and does not check `response.ok`.
  - **Performance**: Modifying `innerHTML` in a loop forces the browser to re-parse and redraw the DOM tree on every iteration, causing slow rendering.

- **Refined Secure Prompt**:
  *"Write a JS function using async/await to fetch data from /api/todos. Enforce safe text assignment using textContent, check response.ok, handle exceptions with try/catch, and optimize DOM inserts by using a DocumentFragment."*

- **Refined Code Output**:
  ```javascript
  async function loadTodosSecurely() {
      const list = document.getElementById("list");
      try {
          const response = await fetch("/api/todos");
          if (!response.ok) throw new Error("HTTP error: " + response.status);
          
          const todos = await response.json();
          
          // Use DocumentFragment to optimize rendering (minimizes reflows)
          const fragment = document.createDocumentFragment();
          list.innerHTML = ""; // Clear list
          
          todos.forEach(todo => {
              const li = document.createElement("li");
              li.textContent = todo.title; // Safe text escaping
              fragment.appendChild(li);
          });
          
          list.appendChild(fragment); // Single DOM insert
      } catch (error) {
          console.error("Failed to load todos:", error.message);
          list.innerHTML = `<li class="error">Failed to load content.</li>`; // Safe static HTML
      }
  }
  ```

---

## Summary
- Audit AI-generated DOM code to ensure variables are not written directly to `innerHTML` placeholders.
- Always verify that AI-generated `fetch` code checks `response.ok`.
- Refine prompts using concrete instructions (e.g. *"use DocumentFragment"*, *"use textContent"*) to force clean, secure code patterns.

---

## Additional Resources
- [OWASP: Cheat Sheet Series](https://cheatsheetseries.owasp.org/)
- [MDN Web Docs: DocumentFragment](https://developer.mozilla.org/en-US/docs/Web/API/DocumentFragment)
