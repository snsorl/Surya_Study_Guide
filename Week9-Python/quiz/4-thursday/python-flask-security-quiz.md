# Quiz: Flask, Libraries, and GenAI Security

## Questions

### 1. Which dunder method is invoked when the `len()` function is called on an object?
- [ ] A) `__length__`
- [ ] B) `__len__`
- [ ] C) `__size__`
- [ ] D) `__count__`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `__len__`

**Explanation:** Python maps the `len()` function directly to the object's `__len__(self)` magic method.
- **Why others are wrong:**
  - A) `__length__` is incorrect.
  - C) `__size__` is not a standard dunder method.
  - D) `__count__` is not used by `len()`.
</details>

---

### 2. What are the dunder methods required to implement a context manager for use with the `with` statement?
- [ ] A) `__open__` and `__close__`
- [ ] B) `__enter__` and `__exit__`
- [ ] C) `__start__` and `__stop__`
- [ ] D) `__begin__` and `__commit__`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `__enter__` and `__exit__`

**Explanation:** A class must implement `__enter__(self)` and `__exit__(self, exc_type, exc_val, exc_tb)` to act as a context manager.
- **Why others are wrong:**
  - A) These are common file method operations, but not context manager dunders.
  - C) `__start__`/`__stop__` are not used by the `with` statement.
  - D) `__begin__`/`__commit__` are database terms.
</details>

---

### 3. Which block in a Python `try-except` structure executes only if NO exceptions were raised in the try block?
- [ ] A) `finally`
- [ ] B) `else`
- [ ] C) `catch`
- [ ] D) `then`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `else`

**Explanation:** The `else` block runs if the `try` block executes successfully without raising any exceptions.
- **Why others are wrong:**
  - A) The `finally` block runs under all conditions, whether an exception was raised or not.
  - C) Python uses `except`, not `catch`.
  - D) `then` is not a Python keyword.
</details>

---

### 4. By default, Flask routes decorated with `@app.route("/path")` respond to which HTTP methods?
- [ ] A) GET only
- [ ] B) GET and POST
- [ ] C) GET, POST, and OPTIONS
- [ ] D) All standard HTTP methods

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) GET only

**Explanation:** By default, Flask routes only listen for `GET` requests. To support other methods (like `POST`), you must declare them explicitly: `@app.route("/path", methods=["GET", "POST"])`.
- **Why others are wrong:**
  - B) `POST` requests will return a `405 Method Not Allowed` unless explicitly mapped.
  - C) `OPTIONS` is handled automatically, but other active methods require mappings.
  - D) You must define HTTP methods explicitly.
</details>

---

### 5. Which proxy object is used in Flask to read incoming JSON payloads?
- [ ] A) `request.data`
- [ ] B) `request.json`
- [ ] C) `request.args`
- [ ] D) `request.body`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `request.json`

**Explanation:** Flask parses incoming JSON payloads and exposes them as a dictionary through `request.json`.
- **Why others are wrong:**
  - A) `request.data` contains the raw unparsed string data.
  - C) `request.args` contains the query parameters parsed from the URL string.
  - D) `request.body` is not a standard attribute in Flask.
</details>

---

### 6. What is the search path list used by the Python interpreter to resolve module imports?
- [ ] A) `os.path`
- [ ] B) `sys.path`
- [ ] C) `env.path`
- [ ] D) `python.path`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `sys.path`

**Explanation:** `sys.path` is a list of directory paths that Python searches sequentially when executing an `import` statement.
- **Why others are wrong:**
  - A) `os.path` is a module containing utilities for file paths.
  - C) `env` is not a standard Python import resolution module.
  - D) `python.path` is not used by the runtime import engine.
</details>

---

### 7. Which CLI tool is used to scan Python environments or requirements files for known security vulnerabilities?
- [ ] A) `pip list`
- [ ] B) `pip-audit`
- [ ] C) `pip check`
- [ ] D) `sys-audit`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `pip-audit`

**Explanation:** `pip-audit` scans your Python packages and cross-references them against the PyPI vulnerability database to flag known CVEs.
- **Why others are wrong:**
  - A) `pip list` simply lists installed packages without checks.
  - C) `pip check` verifies that installed packages have compatible dependencies, but does not scan for CVEs.
  - D) `sys-audit` is not a package vulnerability scanner.
</details>

---

### 8. What is a 2D labeled tabular data structure in Pandas called?
- [ ] A) `Series`
- [ ] B) `DataFrame`
- [ ] C) `ndarray`
- [ ] D) `Matrix`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `DataFrame`

**Explanation:** A Pandas `DataFrame` represents a 2D table-like structure with labeled rows and columns.
- **Why others are wrong:**
  - A) A `Series` represents a 1D labeled array.
  - C) `ndarray` is NumPy's native array structure.
  - D) `Matrix` is a generic term.
</details>

---

### 9. Which slice selection method in Pandas is label-based?
- [ ] A) `.iloc`
- [ ] B) `.loc`
- [ ] C) `.select`
- [ ] D) `.index`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `.loc`

**Explanation:** `.loc` is label-based selection, whereas `.iloc` is integer-position based selection.
- **Why others are wrong:**
  - A) `.iloc` uses integer index values, not string labels.
  - C) `.select` is not a standard slice method.
  - D) `.index` returns the index list, but does not slice data.
</details>

---

### 10. When using AI coding assistants, which type of data is safe to include in prompt messages?
- [ ] A) Production API keys
- [ ] B) Customer names and emails (PII)
- [ ] C) Mock schema definitions and dummy parameters
- [ ] D) Internal database connection strings

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Mock schema definitions and dummy parameters

**Explanation:** To prevent data leaks and secret exposure, always sanitize prompts by substituting real values with generic mock variables.
- **Why others are wrong:**
  - A), B), and D) contain sensitive information that could be stored or leaked by public AI APIs.
</details>
