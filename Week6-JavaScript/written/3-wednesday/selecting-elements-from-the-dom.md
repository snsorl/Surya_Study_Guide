# Selecting Elements from the DOM

## Learning Objectives
- Query elements from the DOM using legacy selectors (`getElementById`, `getElementsByClassName`).
- Utilize modern CSS selectors (`querySelector`, `querySelectorAll`) for complex queries.
- Compare the characteristics of a `NodeList` versus an `HTMLCollection`.
- Distinguish between **live** and **static** DOM collections.

---

## Why This Matters
Before modifying an element's text, binding click actions, or altering CSS classes, you must query that element from the DOM tree. JavaScript provides several selection APIs. Standard methods return different object types (like static NodeLists or live HTMLCollections). Choosing the incorrect selector can lead to performance degradation or bugs where list changes fail to register at runtime.

---

## The Concept

### 1. The Selector API Toolkit

#### Legacy Selectors
- **`document.getElementById(id)`**: Selects a single element matching the unique ID. It is highly optimized and fast.
- **`document.getElementsByClassName(className)`**: Selects all elements matching the class name. Returns a **live HTMLCollection**.

#### Modern Selectors (Recommended)
- **`document.querySelector(cssSelector)`**: Returns the **first element** that matches the specified CSS selector pattern (e.g. `"#container .active"`). If no element matches, it returns `null`.
- **`document.querySelectorAll(cssSelector)`**: Returns all elements matching the CSS selector pattern as a **static NodeList**.

```javascript
// Examples:
const header = document.querySelector("#main-header");
const activeItems = document.querySelectorAll(".list-item.active");
```

### 2. NodeList vs. HTMLCollection

| Feature | `HTMLCollection` | `NodeList` |
|---|---|---|
| **Selector Source** | Returned by legacy class/tag name queries | Returned by modern `querySelectorAll` |
| **Node Types** | Contains only **Element Nodes** | Can contain any **Node types** (text, comment) |
| **Built-in Array Methods** | Lacks array methods (no `.forEach`) | Declares `.forEach` natively |
| **Collection Update State** | Always **Live** | Usually **Static** (snapshot) |

### 3. Live vs. Static Collections
- **Live Collection**: The object contains a reference to the active DOM. If elements are added or deleted in the document, the collection updates its length and content automatically in real-time.
- **Static Collection (Snapshot)**: The object contains a copy of the state at the time of query execution. If elements are added or deleted later, the collection length and references remain unchanged.

---

## Code Examples

### Live vs. Static Collection Behavior Comparison
Let's see how adding elements changes the collections:

```html
<ul id="list">
    <li class="item">Item 1</li>
    <li class="item">Item 2</li>
</ul>

<script>
    // 1. Live HTMLCollection
    const liveItems = document.getElementsByClassName("item");
    
    // 2. Static NodeList
    const staticItems = document.querySelectorAll(".item");
    
    console.log(liveItems.length);   // 2
    console.log(staticItems.length); // 2
    
    // Create and append a 3rd item dynamically
    const ul = document.getElementById("list");
    const newLi = document.createElement("li");
    newLi.className = "item";
    newLi.textContent = "Item 3";
    ul.appendChild(newLi);
    
    // Compare lengths after addition
    console.log(liveItems.length);   // 3 (Updated automatically!)
    console.log(staticItems.length); // 2 (Remains static)
</script>
```

---

## Summary
- Use **`querySelector`** to fetch a single element and **`querySelectorAll`** to fetch multiple elements using CSS syntax.
- **`HTMLCollection`** contains only element nodes and is always **live**; it does not support `.forEach`.
- **`NodeList`** supports `.forEach` natively and is **static** when returned by `querySelectorAll`.
- Converting collections to standard arrays (using `Array.from(collection)` or the spread operator `[...collection]`) simplifies data manipulation using standard array methods.

---

## Additional Resources
- [MDN Web Docs: Locating DOM elements using selectors](https://developer.mozilla.org/en-US/docs/Web/API/Document_object_model/Locating_DOM_elements_using_selectors)
- [JavaScript.info: Searching elements](https://javascript.info/searching-elements-dom)
