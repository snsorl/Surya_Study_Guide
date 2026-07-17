# Quiz: DOM & Asynchronous JavaScript

## Part 1: Multiple Choice & True/False

### 1. Which DOM selector returns a static `NodeList` instead of a live `HTMLCollection`?
- [ ] A) `document.getElementsByClassName`
- [ ] B) `document.getElementsByTagName`
- [ ] C) `document.querySelectorAll`
- [ ] D) `document.getElementById`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `document.querySelectorAll`

**Explanation:** `querySelectorAll` returns a static `NodeList` containing a snapshot of matching elements at the time of query execution. Legacy helpers like `getElementsByClassName` return live collections.
- **Why others are wrong:**
  - A) `getElementsByClassName` returns a live `HTMLCollection`.
  - B) `getElementsByTagName` returns a live `HTMLCollection`.
  - D) `getElementById` returns a single Element node, not a list container.
</details>

---

### 2. How does the browser's event delegation pattern work?
- [ ] A) By registering event handlers on every single child element in a list.
- [ ] B) By registering a single listener on a parent element, intercepting bubbled events.
- [ ] C) By executing events in capturing order to disable element actions.
- [ ] D) By forcing event handlers to execute synchronously on the call stack.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) By registering a single listener on a parent element, intercepting bubbled events.

**Explanation:** Because events bubble up the DOM tree, you can listen for child clicks at a parent element level. This optimizes performance and handles dynamically added children automatically.
- **Why others are wrong:**
  - A) Binding to every child is the slow, non-delegated approach.
  - C) Delegation leverages event bubbling, not capturing modification.
  - D) It does not change call stack synchronicity.
</details>

---

### 3. What happens if a `fetch()` request targets a URL that returns a `500 Internal Server Error`?
- [ ] A) The fetch Promise rejects, triggering the `.catch()` block.
- [ ] B) The fetch Promise resolves successfully with `response.ok` set to `false`.
- [ ] C) The browser reloads the page automatically.
- [ ] D) Execution pauses indefinitely on the call stack.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The fetch Promise resolves successfully with `response.ok` set to `false`.

**Explanation:** A `fetch()` Promise only rejects on network failures (DNS issues, connection loss). HTTP error status codes (like 404 or 500) resolve successfully, requiring developers to inspect `response.ok` manually.
- **Why others are wrong:**
  - A) Rejections do not fire for standard HTTP error status codes.
  - C) The browser does not force reloads.
  - D) Execution does not freeze.
</details>

---

### 4. What is a Closure in JavaScript?
- [ ] A) A method that closes browser tabs.
- [ ] B) A function combined with references to its surrounding lexical environment.
- [ ] C) A type of compiler validation tool.
- [ ] D) A data structure used for encryption.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A function combined with references to its surrounding lexical environment.

**Explanation:** A closure is created when an inner function is declared inside an outer function, retaining access to the outer variables even after the outer function finishes executing.
- **Why others are wrong:**
  - A) Closures have no relation to window closing commands.
  - C) Closures are runtime features, not compile-time validation check suites.
  - D) They are scoping behaviors, not cryptographic libraries.
</details>

---

## Part 2: Code Predictions

### 5. What does the following code print to the console?
```javascript
const p = new Promise((resolve, reject) => {
    reject("Error!");
});
p.then(() => console.log("Success"))
 .catch(err => console.log("Caught: " + err));
```
- [ ] A) `"Success"`
- [ ] B) `"Caught: Error!"`
- [ ] C) Logs both statements
- [ ] D) Throws a runtime crash exception

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `"Caught: Error!"`

**Explanation:** Because the Promise was rejected with `"Error!"`, the `.then()` success handler is bypassed, and the `.catch()` block catches the rejection value.
- **Why others are wrong:**
  - A) The Promise was rejected, not resolved, so the success callback does not execute.
  - C) Only one block executes based on the settled state.
  - D) The rejection is handled by the `.catch()` statement, preventing a crash.
</details>

---

### 6. What is the output of the following asynchronous script execution?
```javascript
console.log("A");
setTimeout(() => console.log("B"), 0);
console.log("C");
```
- [ ] A) `"A"`, `"B"`, `"C"`
- [ ] B) `"A"`, `"C"`, `"B"`
- [ ] C) `"B"`, `"A"`, `"C"`
- [ ] D) `"C"`, `"B"`, `"A"`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `"A"`, `"C"`, `"B"`

**Explanation:** Even though `setTimeout` has a delay of `0`ms, its callback is placed in the Callback Queue. The event loop waits for the Call Stack to be clear (meaning all synchronous code like `"C"` has finished executing) before running the callback.
- **Why others are wrong:**
  - A) `"B"` is asynchronous and must wait for the call stack to be empty.
  - C) Synchronous code always executes first.
</details>

---

### 7. What does the following closure-based function log?
```javascript
function makeCounter() {
    let count = 0;
    return () => ++count;
}
const c1 = makeCounter();
c1();
console.log(c1());
```
- [ ] A) `0`
- [ ] B) `1`
- [ ] C) `2`
- [ ] D) `undefined`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `2`

**Explanation:** The first invocation `c1()` increments `count` to `1`. The second invocation inside the console log increments it to `2` and returns the value.
- **Why others are wrong:**
  - A) The counter increments before returning due to prefix `++`.
  - B) `1` is the result after the first call, not the second call.
  - D) The closure retains the variable in scope, returning a number, not `undefined`.
</details>

---

### 8. What is the return type of any function annotated with the `async` keyword?
- [ ] A) The type of the value returned inside the function body.
- [ ] B) `Promise`
- [ ] C) `void`
- [ ] D) `unknown`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Promise`

**Explanation:** Functions declared with `async` always return a Promise. If the function returns a raw value, the JavaScript engine automatically wraps it in a resolved Promise.
- **Why others are wrong:**
  - A) The return type is always wrapped in a Promise; you cannot return a synchronous value directly.
  - C) Even if the function has no return statement, it returns a Promise resolving to `undefined`.
</details>

---

### 9. Which method prevents a link from navigating to a new URL when clicked?
- [ ] A) `event.stopPropagation()`
- [ ] B) `event.preventDefault()`
- [ ] C) `element.remove()`
- [ ] D) `document.clear()`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `event.preventDefault()`

**Explanation:** `preventDefault()` instructs the browser to skip the default action associated with the event (like form submission reloads or link navigation).
- **Why others are wrong:**
  - A) `stopPropagation()` halts event bubbling up the DOM tree, but does not stop the default action on the element.
  - C) `remove()` deletes the node from the page.
  - D) `clear()` is not a valid event handler method.
</details>

---

### 10. In the DOM tree, what is the difference between `childNodes` and `children`?
- [ ] A) `childNodes` returns only text nodes; `children` returns comments.
- [ ] B) `children` contains only Element nodes; `childNodes` contains all Node types.
- [ ] C) `children` is static; `childNodes` is live.
- [ ] D) There is no difference.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `children` contains only Element nodes; `childNodes` contains all Node types.

**Explanation:** The `children` property returns a list containing only HTML element tags (like `<div>` or `<p>`). `childNodes` includes all nodes, including comments and whitespace text nodes.
- **Why others are wrong:**
  - A) `childNodes` returns all node types, not just text nodes.
  - C) Both collections are live, but they filter node types differently.
</details>
