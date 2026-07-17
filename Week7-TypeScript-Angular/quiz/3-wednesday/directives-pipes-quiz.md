# Practice Quiz: Directives & Pipes

## Part 1: Multiple Choice (MCQ)

### 1. Which directive completely adds or removes an element from the DOM based on a boolean conditional check?
- [ ] A) `[hidden]`
- [ ] B) `*ngIf`
- [ ] C) `ngClass`
- [ ] D) `*ngSwitchCase`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `*ngIf`

**Explanation:** `*ngIf` is a structural directive that inserts or destroys elements in the physical DOM.
- **Why others are wrong:**
  - A) `[hidden]` is an attribute property binding that hides elements using CSS `display: none` but leaves them inside DOM memory.
  - C) `ngClass` toggles class names, it doesn't add/remove elements.
  - D) `*ngSwitchCase` is a sub-directive used with `[ngSwitch]`, not an independent boolean checker.
</details>

---

### 2. How can you optimize rendering performance in a long list rendered with `*ngFor` when items are modified?
- [ ] A) Use the `async` pipe.
- [ ] B) Bind custom CSS styles using `ngStyle`.
- [ ] C) Implement the `trackBy` function parameter to track items by unique identifiers (e.g. ID).
- [ ] D) Enable change detection manually.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Implement the `trackBy` function parameter to track items by unique identifiers (e.g. ID).

**Explanation:** Implementing `trackBy` prevents Angular from re-rendering the entire DOM list whenever items are added, updated, or removed, re-rendering only the changed node items.
- **Why others are wrong:**
  - A) The `async` pipe subscribes to Observables.
  - B) `ngStyle` binds inline styles and does not affect iteration performance.
  - D) Manual change detection is an advanced API (ChangeDetectorRef) and is not the standard optimization helper for list rendering.
</details>

---

### 3. What decorator metadata parameter defines a pipe as "Pure" in Angular?
- [ ] A) `name: 'pipeName'`
- [ ] B) `pure: true`
- [ ] C) `transform: true`
- [ ] D) `type: 'pure'`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `pure: true`

**Explanation:** Setting `pure: true` (which is the default value) specifies that the pipe transform is only re-evaluated when the input arguments change.
- **Why others are wrong:**
  - A) `name` registers the pipe template selector string.
  - C & D) `transform` and `type` are not valid pipe decorator properties.
</details>

---

### 4. Which lifecycle hook is executed immediately after Angular sets the component's input data-bound properties?
- [ ] A) `constructor`
- [ ] B) `ngOnInit`
- [ ] C) `ngOnChanges`
- [ ] D) `ngAfterViewInit`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `ngOnChanges`

**Explanation:** `ngOnChanges` runs first and receives a SimpleChanges map containing updated properties whenever a parent passes new values down via `@Input`.
- **Why others are wrong:**
  - A) `constructor` is a native Javascript engine method that executes before inputs are bound.
  - B) `ngOnInit` runs once after the initial property bindings are completed.
  - D) `ngAfterViewInit` executes after component template view elements are initialized.
</details>

---

## Part 2: True / False

### 5. Custom attribute directives are used to modify the behavior or styling of an existing DOM element, rather than changing the structure of the DOM tree.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Attribute directives (like `ngClass` or custom hover directives) change style/behaviors. Structural directives (prefixed with an asterisk like `*ngIf` and `*ngFor`) add or remove elements in the DOM tree.
</details>

---

### 6. Pure pipes re-evaluate their output values on every application mouse move or keystroke change detection cycle.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Impure pipes run on every change detection cycle. Pure pipes only re-evaluate when their input argument references change, making them highly performant.
</details>

---

## Part 3: Code Prediction

### 7. What is the output of the following template compilation when `productTitle = "Enterprise Angular Components"`?
```html
<p>{{ productTitle | truncate:10 }}</p>
```
*Assume `truncate` returns the first N characters followed by '...'.*
- [ ] A) `Enterprise...`
- [ ] B) `Enterprise Angular Components`
- [ ] C) `Enterprise`
- [ ] D) `Enterprise Angular...`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `Enterprise...`

**Explanation:** The pipe splits the string at index `10` ("Enterprise" contains 10 letters) and appends the default ellipsis suffix.
</details>

---

## Part 4: Fill-in-the-Blank

### 8. Custom pipes must implement the `PipeTransform` interface and define a method named `___________`.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `transform`

**Explanation:** The `transform` method accepts the input value and optional configuration parameters, returning the formatted string or value.
</details>
