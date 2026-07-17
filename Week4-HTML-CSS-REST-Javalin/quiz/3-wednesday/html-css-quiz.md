# Wednesday Knowledge Check: HTML and CSS

## Part 1: HTML and CSS Quiz

### 1. Which of the following is considered a void element in HTML5 and does not require a closing tag?
- [ ] A) `<div>`
- [ ] B) `<span>`
- [ ] C) `<img>`
- [ ] D) `<p>`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) `<img>`

**Explanation:** Void elements (like `<img>`, `<input>`, `<br>`, `<link>`) cannot contain child nodes and are self-closing by definition in HTML5.
- **Why others are wrong:**
  - A) `<div>` wraps blocks of elements and requires closing tags.
  - B) `<span>` wraps inline text content and must be closed.
  - D) `<p>` wraps text paragraph blocks and must be closed.
</details>

---

### 2. How does a web browser represent the hierarchical structure of a parsed HTML document in memory?
- [ ] A) As an array of JSON objects.
- [ ] B) As a relational database table layout.
- [ ] C) As a parent-child tree structure called the Document Object Model (DOM).
- [ ] D) As a flat text file stream.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) As a parent-child tree structure called the Document Object Model (DOM).

**Explanation:** The browser translates HTML elements into a tree of nodes representing the Document Object Model (DOM), enabling JavaScript access.
- **Why others are wrong:**
  - A) JSON is a data exchange format, not a browser memory rendering tree.
  - B) Relational schemas are database structures, not browser DOM trees.
  - D) Flat streams are raw byte arrays read before compilation occurs.
</details>

---

### 3. Which HTML5 tag is the most semantically appropriate container for an independent, self-contained blog post or news article?
- [ ] A) `<section>`
- [ ] B) `<article>`
- [ ] C) `<main>`
- [ ] D) `<aside>`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `<article>`

**Explanation:** The `<article>` element represents a self-contained composition (e.g. blog post, article, forum post) that is independently distributable.
- **Why others are wrong:**
  - A) `<section>` represents generic thematic sections of a page.
  - C) `<main>` isolates the single primary content block of the page.
  - D) `<aside>` contains tangential sidebar links or ads.
</details>

---

### 4. What HTML configuration attribute is mandatory on an input tag to ensure its value is sent to the server upon form submission?
- [ ] A) `id`
- [ ] B) `class`
- [ ] C) `name`
- [ ] D) `placeholder`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) `name`

**Explanation:** The browser uses the `name` attribute as the query parameter key when serializing form inputs. If `name` is omitted, the input data is ignored.
- **Why others are wrong:**
  - A) `id` uniquely identifies the element in the DOM for labels or scripts.
  - B) `class` maps elements to CSS stylesheets.
  - D) `placeholder` displays temporary prompt helper text inside the input box.
</details>

---

### 5. If a form is submitted using the GET method, where is the form data transmitted?
- [ ] A) Encrypted within the TLS/SSL handshake.
- [ ] B) Enclosed inside the HTTP Request Body payload.
- [ ] C) Appended to the URL path as a query string.
- [ ] D) Stored inside a temporary browser session cookie.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) Appended to the URL path as a query string.

**Explanation:** GET submissions serialize and append parameters to the target URL (e.g. `/search?q=text`), making them visible in URLs.
- **Why others are wrong:**
  - A) Encryption (HTTPS) secures GET paths, but the query variables remain in the URL.
  - B) POST submissions place parameters in the Request Body payload.
  - D) Cookies are separately configured state markers, not form methods.
</details>

---

### 6. What layers represent the correct order of the CSS Box Model from inside (the element content) to outside?
- [ ] A) Content -> Border -> Padding -> Margin
- [ ] B) Content -> Padding -> Border -> Margin
- [ ] C) Content -> Margin -> Padding -> Border
- [ ] D) Padding -> Content -> Border -> Margin

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Content -> Padding -> Border -> Margin

**Explanation:** The CSS Box Model encloses Content, wraps it with Padding, surrounds that with the Border, and applies outer Margin spacing.
- **Why others are wrong:**
  - A) Padding is inside the border, not outside.
  - C) Margin is the outermost layer, not adjacent to Content.
  - D) Padding is outside Content, not inside it.
</details>

---

### 7. What does the declaration `box-sizing: border-box;` do?
- [ ] A) It adds a default solid black border to all elements.
- [ ] B) It forces padding and border widths to be included within the element's declared width and height.
- [ ] C) It changes block-level elements into inline text behaviors.
- [ ] D) It centers card layouts horizontally on the page.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) It forces padding and border widths to be included within the element's declared width and height.

**Explanation:** `border-box` simplifies layouts by preventing padding and borders from expanding an element's calculated size beyond its declared width.
- **Why others are wrong:**
  - A) Border styles must be explicitly declared (e.g. `border: 1px solid`).
  - C) Display behaviors are managed by the `display` property, not box-sizing.
  - D) Horizontal centering is managed by margin offsets (e.g. `margin: 0 auto`).
</details>

---

### 8. What is the calculated specificity value of the descendant selector `.sidebar p`?
- [ ] A) 0-0-1
- [ ] B) 0-1-0
- [ ] C) 0-1-1
- [ ] D) 1-0-1

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) 0-1-1

**Explanation:** The selector matches one class (`.sidebar` = 10) and one element (`p` = 1), yielding a total specificity score of 11 (0-1-1).
- **Why others are wrong:**
  - A) 0-0-1 is the score of a single element selector (e.g. `p`).
  - B) 0-1-0 is the score of a single class selector (e.g. `.sidebar`).
  - D) 1-0-1 is the score of an ID selector and an element selector (e.g. `#sidebar p`).
</details>

---

### 9. Which CSS positioning property removes an element from the normal document flow and places it relative to its closest non-static parent container?
- [ ] A) `position: relative;`
- [ ] B) `position: absolute;`
- [ ] C) `position: fixed;`
- [ ] D) `position: static;`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `position: absolute;`

**Explanation:** `position: absolute` removes elements from flow coordinates and anchors them relative to their nearest positioned (usually relative) ancestor container.
- **Why others are wrong:**
  - A) `relative` offsets elements relative to their *own* normal flow position.
  - C) `fixed` positions elements relative to the browser viewport screen.
  - D) `static` is default flow layout and ignores offsets.
</details>

---

### 10. In Flexbox layout, what property is used to align child items vertically when the row direction is active?
- [ ] A) `flex-direction`
- [ ] B) `justify-content`
- [ ] C) `align-items`
- [ ] D) `flex-wrap`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) `align-items`

**Explanation:** For row layouts, `align-items` manages vertical cross-axis alignment, while `justify-content` manages horizontal main-axis distribution.
- **Why others are wrong:**
  - A) `flex-direction` sets the layout axes (row or column).
  - B) `justify-content` aligns items horizontally along the main-axis.
  - D) `flex-wrap` controls whether flex items wrap onto multiple lines.
</details>

---

### 11. Which method of linking CSS styles is most performant because it allows separate browser file caching?
- [ ] A) Inline styles directly on individual HTML elements.
- [ ] B) Internal style blocks in the head section.
- [ ] C) External stylesheets linked using the `<link>` tag.
- [ ] D) Importing CSS files dynamically via JavaScript callbacks.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) External stylesheets linked using the `<link>` tag.

**Explanation:** Browsers load external CSS files as separate assets, allowing them to cache the design rules locally so they do not need to be re-downloaded on subsequent page visits.
- **Why others are wrong:**
  - A) Inline styles must be downloaded with the HTML layout on every request.
  - B) Internal style blocks must be parsed inside the main HTML page payload.
  - D) Dynamic script injection bypasses standard browser pre-loading parser checks.
</details>

---

### 12. What is the correct CSS syntax to apply identical styling rules to multiple selectors simultaneously (grouped selectors)?
- [ ] A) `h1 h2 h3 { ... }`
- [ ] B) `h1, h2, h3 { ... }`
- [ ] C) `h1.h2.h3 { ... }`
- [ ] D) `h1 & h2 & h3 { ... }`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `h1, h2, h3 { ... }`

**Explanation:** Group selectors by separating them with commas to apply the same CSS rules without repeating code blocks.
- **Why others are wrong:**
  - A) Space separation defines descendant relationships (e.g., h3 nested inside h2 inside h1).
  - C) Dot separation represents class chaining on a single element (e.g. h1 carrying classes h2 and h3).
  - D) Ampersand `&` is a nesting operator used in preprocessors like Sass, but is not standard grouped vanilla CSS.
</details>

---

### 13. What is the primary purpose of the accessibility attribute 'aria-label'?
- [ ] A) It adds visual text labels next to inputs.
- [ ] B) It provides a hidden text description for elements that do not contain visible text content, which is read by screen readers.
- [ ] C) It links form variables to server database tables.
- [ ] D) It formats font weights inside card containers.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) It provides a hidden text description for elements that do not contain visible text content, which is read by screen readers.

**Explanation:** Elements like icon-only buttons (e.g. an "X" icon for closing a dialog) use `aria-label` to provide screen readers with a clear description of the button's action.
- **Why others are wrong:**
  - A) ARIA labels do not draw visible pixels on the browser viewport page.
  - C) Access coordinates are handled by input name values, not screen reader tags.
  - D) Fonts are styled using CSS font properties.
</details>

---

### 14. Which of the following nests elements in a way that violates browser parsing rules?
- [ ] A) `<div><span>Text</span></div>`
- [ ] B) `<p><a href="/">Link</a></p>`
- [ ] C) `<span><div>Text</div></span>`
- [ ] D) `<section><article>Text</article></section>`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) `<span><div>Text</div></span>`

**Explanation:** Inline elements (like `<span>` or `<a>`) must never contain block-level elements (like `<div>` or `<p>`), as it breaks browser DOM parsing trees.
- **Why others are wrong:**
  - A) Divs (block) can contain spans (inline) safely.
  - B) Paragraphs (block) can contain anchors (inline) safely.
  - D) Sections (block) can contain articles (block) safely.
</details>

---

### 15. When building a form that allows trainees to upload their profile picture files, what 'enctype' attribute configuration must be declared on the `<form>` element?
- [ ] A) `enctype="application/x-www-form-urlencoded"`
- [ ] B) `enctype="text/plain"`
- [ ] C) `enctype="multipart/form-data"`
- [ ] D) No configuration is needed; standard submissions upload files.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) `enctype="multipart/form-data"`

**Explanation:** The default urlencoded format cannot handle binary data blocks; you must configure `multipart/form-data` to split files and fields into separate multi-part boundaries.
- **Why others are wrong:**
  - A) This is the default text-only format and will fail to submit files.
  - B) Plain text formats do not serialize inputs, making them unusable for files.
  - D) Browsers will ignore binary file streams during submit cycles unless configured.
</details>
