# Thursday Knowledge Check: Cumulative Week 4 Review

## Part 1: Week 4 Review Quiz

### 1. In the context of full-stack API integration, what happens during the client-side Fetch execution?
- [ ] A) The browser reloads the entire page and compiles the Java server files.
- [ ] B) JavaScript triggers an asynchronous HTTP request, receives response data, and updates targeted DOM nodes without a full page refresh.
- [ ] C) The database automatically synchronizes all local repository records.
- [ ] D) The server session is forced to invalidate.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) JavaScript triggers an asynchronous HTTP request, receives response data, and updates targeted DOM nodes without a full page refresh.

**Explanation:** The JS Fetch API operates asynchronously in the background, allowing web pages to update content dynamically without reloading.
- **Why others are wrong:**
  - A) Fetch avoids page refreshes and Java compilation is a backend process.
  - C) Database operations are backend concerns mapped to APIs; frontend cannot access database resources directly.
  - D) Session state remains active unless explicitly modified by headers or timeouts.
</details>

---

### 2. If you try to fetch resources from a backend API running on port 8080 from a frontend page served on port 5500, what security boundary block will occur by default?
- [ ] A) SQL Injection Block
- [ ] B) Cross-Origin Resource Sharing (CORS) restriction
- [ ] C) Same-Site Cookie decryption failure
- [ ] D) Network DNS routing timeout

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Cross-Origin Resource Sharing (CORS) restriction

**Explanation:** Browsers block cross-origin requests unless the target server explicitly sends headers allowing the calling origin. Different ports represent different origins.
- **Why others are wrong:**
  - A) SQL injection blocks are database query validation checks.
  - C) Decryption handles credentials, not origin requests.
  - D) DNS routes domains, but ports are managed by browser security APIs.
</details>

---

### 3. Which of the following code blocks illustrates the correct syntax to serialize a JavaScript object into a JSON string payload for a POST request body?
- [ ] A) `JSON.parse(data)`
- [ ] B) `data.toJSON()`
- [ ] C) `JSON.stringify(data)`
- [ ] D) `new JSON(data)`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) `JSON.stringify(data)`

**Explanation:** `JSON.stringify()` converts JavaScript values/objects into JSON text strings.
- **Why others are wrong:**
  - A) `JSON.parse()` does the reverse: it converts JSON text back into JavaScript objects.
  - B) `toJSON` is a utility format hook, not the standard serialization method.
  - D) JSON is a global namespace object and cannot be instantiated with `new`.
</details>

---

### 4. What is the specificity score of the selector `#user-form input[type="text"]`?
- [ ] A) 0-1-2
- [ ] B) 1-1-1
- [ ] C) 1-1-2
- [ ] D) 1-2-1

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) 1-1-1

**Explanation:** The selector matches one ID (`#user-form` = 100), one attribute selector (`[type="text"]` counts as a class/attribute = 10), and one element (`input` = 1), yielding a score of 1-1-1 (111).
- **Why others are wrong:**
  - A) Lacks the ID score weight.
  - C) Attributes do not count as elements, only tags do.
  - D) The attribute selector counts as a single class/attribute selector weight of 10, not 20.
</details>

---

### 5. Why should sensitive parameters like user passwords never be submitted via GET forms?
- [ ] A) GET requests do not support text parameters.
- [ ] B) The browser body payload is limited to 1KB.
- [ ] C) GET parameters are appended directly to the URL string, leaking credentials into history logs and search indexes.
- [ ] D) Server session tokens are automatically cleared.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) GET parameters are appended directly to the URL string, leaking credentials into history logs and search indexes.

**Explanation:** URL queries are not secure and get saved in plain text in browser histories, proxies, and server logs. Sensitive data must use POST.
- **Why others are wrong:**
  - A) GET is built specifically to transfer text query strings.
  - B) URLs carry length limits, but request body limits apply to POST payloads.
  - D) Session configurations are separate and do not change based on route methods.
</details>

---

### 6. What REST Maturity Level represents the introduction of resources (different URIs) without HTTP Verbs?
- [ ] A) Level 0
- [ ] B) Level 1
- [ ] C) Level 2
- [ ] D) Level 3

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Level 1

**Explanation:** Level 1 of the Richardson Maturity Model organizes the API into distinct resources (URIs) but continues to process actions through a single HTTP method.
- **Why others are wrong:**
  - A) Level 0 represents a single entry point endpoint (Swamp of POX).
  - C) Level 2 uses multiple resources matched with correct HTTP verbs and status codes.
  - D) Level 3 introduces hypermedia navigation links (HATEOAS).
</details>

---

### 7. In a Javalin application, what is the role of the `before()` filter?
- [ ] A) It compiles Javascript files before sending them to Jetty.
- [ ] B) It executes cross-cutting concern logic (like authentication checks or request logging) prior to the matching route handler.
- [ ] C) It renders CSS files before HTML DOM assembly.
- [ ] D) It initializes connection pools on database startups.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) It executes cross-cutting concern logic (like authentication checks or request logging) prior to the matching route handler.

**Explanation:** `before()` filters are Javalin middleware interceptors running before matching endpoints to perform filtering, audit logging, and security validations.
- **Why others are wrong:**
  - A) Javalin does not compile JavaScript.
  - C) CSS rendering is a client-side browser process, not backend middleware.
  - D) Database connection factories manage database pools, not Javalin routing filters.
</details>

---

### 8. Which CSS display property turns an element into a flex container, aligning its children along horizontal or vertical layout tracks?
- [ ] A) `display: block;`
- [ ] B) `display: inline-block;`
- [ ] C) `display: flex;`
- [ ] D) `display: grid;`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) `display: flex;`

**Explanation:** Setting `display: flex` establishes a flex context on the parent container, enabling alignment properties on child items.
- **Why others are wrong:**
  - A) `block` displays elements on standard new lines filling width.
  - B) `inline-block` lets elements flow horizontally while respecting box dimensions.
  - D) `grid` is a two-dimensional grid layout model, not Flexbox.
</details>

---

### 9. What is the fundamental difference between the absolute unit 'px' and the relative unit 'rem' in CSS?
- [ ] A) `px` is relative to parent divs; `rem` is absolute on all viewports.
- [ ] B) `px` defines a fixed physical size on the screen, while `rem` scales dynamically relative to the font-size of the root HTML element.
- [ ] C) `rem` only works inside flexbox layouts.
- [ ] D) There is no difference; both calculate identically.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `px` defines a fixed physical size on the screen, while `rem` scales dynamically relative to the font-size of the root HTML element.

**Explanation:** Relative units like `rem` scale with root settings (usually 16px), enabling layout responsiveness and user browser accessibility zooms.
- **Why others are wrong:**
  - A) This reverses the definitions of absolute vs relative dimensions.
  - C) `rem` is a general sizing unit and is valid across all layout containers.
  - D) Absolute and relative sizes behave differently when resizing screen resolutions.
</details>

---

### 10. Which of the following is a syntax requirement of valid JSON documents?
- [ ] A) Keys can be written without quotes.
- [ ] B) Strings must be enclosed in double quotes (`"key"`: `"value"`).
- [ ] C) Every file must contain a schema definition block.
- [ ] D) Functions can be embedded within JSON variables.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Strings must be enclosed in double quotes (`"key"`: `"value"`).

**Explanation:** JSON syntax is strict: double quotes must wrap both keys and string values, and functions or comments are not supported.
- **Why others are wrong:**
  - A) Single quotes or unquoted keys represent invalid JSON.
  - C) Schema attributes are optional descriptors and are not mandatory.
  - D) JSON is a pure data format; programming functions are ignored.
</details>

---

### 11. Which HTTP status code is most appropriate when a client attempts to create an account with an email that is already registered in the database?
- [ ] A) 400 Bad Request
- [ ] B) 401 Unauthorized
- [ ] C) 409 Conflict
- [ ] D) 503 Service Unavailable

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) 409 Conflict

**Explanation:** HTTP 409 indicates the request conflicts with the current database state, such as duplicate unique constraint violations.
- **Why others are wrong:**
  - A) 400 represents syntax errors, not business logic duplicate constraints.
  - B) 401 indicates missing credentials.
  - D) 503 indicates server maintenance or overload failures.
</details>

---

### 12. Which group contains only block-level HTML elements that start on new lines and fill the container width?
- [ ] A) `<span>`, `<a>`, `<img>`
- [ ] B) `<div>`, `<p>`, `<h1>`
- [ ] C) `<strong>`, `<em>`, `<div>`
- [ ] D) `<input>`, `<button>`, `<label>`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `<div>`, `<p>`, `<h1>`

**Explanation:** Divs, paragraphs, and headings are block-level structural containers by default.
- **Why others are wrong:**
  - A) Spans, anchors, and images are inline elements.
  - C) Strong and em are inline text styling tags.
  - D) Form controls are inline-block elements.
</details>

---

### 13. When debugging backend integration issues, what does the browser DevTools 'Network' tab allow you to inspect?
- [ ] A) The Java source code classes compiled on the server.
- [ ] B) The SQL queries executed inside PostgreSQL.
- [ ] C) The exact request headers, response codes, payloads, and execution timelines of all HTTP transactions.
- [ ] D) The root password hashes stored in database tables.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) The exact request headers, response codes, payloads, and execution timelines of all HTTP transactions.

**Explanation:** The Network tab traces all network traffic exiting and entering the browser sandbox, providing details on request-response cycles.
- **Why others are wrong:**
  - A) Server Java code resides on the host and is hidden from browser screens.
  - B) Database queries are private server operations and are not sent to clients.
  - D) Plain passwords are never leaked to client tools.
</details>

---

### 14. How do PUT and PATCH HTTP methods differ in REST resource update operations?
- [ ] A) PUT is safe; PATCH is unsafe.
- [ ] B) PUT replaces the entire target resource with the request payload, while PATCH applies partial modifications to specific fields.
- [ ] C) PATCH is only used for deletes; PUT is used for creations.
- [ ] D) There is no difference; they are interchangeable.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) PUT replaces the entire target resource with the request payload, while PATCH applies partial modifications to specific fields.

**Explanation:** PUT replaces the resource completely, which is why missing fields are cleared out. PATCH modifies only the specified keys.
- **Why others are wrong:**
  - A) Both methods modify server state and are unsafe.
  - C) DELETE handles removals, and POST handles creation.
  - D) They have distinct update scopes and behaviors.
</details>

---

### 15. In a Logback configuration file, what destination appender is configured to print log statements to the standard system console?
- [ ] A) `ch.qos.logback.core.FileAppender`
- [ ] B) `ch.qos.logback.core.ConsoleAppender`
- [ ] C) `ch.qos.logback.core.rolling.RollingFileAppender`
- [ ] D) `ch.qos.logback.classic.net.SMTPAppender`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `ch.qos.logback.core.ConsoleAppender`

**Explanation:** The ConsoleAppender outputs log strings to `System.out` or `System.err` streams for display in terminals.
- **Why others are wrong:**
  - A) FileAppender writes logs to a static file.
  - C) RollingFileAppender archives log logs dynamically across daily intervals.
  - D) SMTPAppender sends emails on error triggers.
</details>
