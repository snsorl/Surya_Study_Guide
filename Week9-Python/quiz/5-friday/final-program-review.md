# Cumulative Quiz: Final Program Review

## Questions

### 1. In Java, what is the default value of an object instance variable if it is not explicitly initialized?
- [ ] A) `null`
- [ ] B) `0`
- [ ] C) `undefined`
- [ ] D) Compiler error

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `null`

**Explanation:** In Java, instance variables of object reference types are automatically initialized to `null` on creation.
- **Why others are wrong:**
  - B) `0` is the default value for numeric primitive types, not object references.
  - C) `undefined` is a JavaScript type, not Java.
  - D) Java allows variables to remain uninitialized in class bodies (though local variables in method scopes will cause compiler errors).
</details>

---

### 2. Which SQL JOIN returns all records from the left table, and the matched records from the right table, filling with NULL values if there is no match?
- [ ] A) INNER JOIN
- [ ] B) FULL OUTER JOIN
- [ ] C) LEFT OUTER JOIN
- [ ] D) RIGHT OUTER JOIN

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) LEFT OUTER JOIN

**Explanation:** A LEFT JOIN returns all rows from the left table, plus matching rows from the right. If no match exists, NULL values are inserted for the right table columns.
- **Why others are wrong:**
  - A) INNER JOIN returns only records that have matches in both tables.
  - B) FULL OUTER JOIN returns all records when there is a match in left OR right.
  - D) RIGHT JOIN returns all records from the right table instead of the left.
</details>

---

### 3. In Amazon DynamoDB, what constitutes a composite primary key?
- [ ] A) A partition key only
- [ ] B) A partition key and a sort key
- [ ] C) A local secondary index and a global secondary index
- [ ] D) A sort key only

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A partition key and a sort key

**Explanation:** A composite primary key in DynamoDB consists of both a Partition Key (hash key) and a Sort Key (range key) working together to identify items uniquely.
- **Why others are wrong:**
  - A) A partition key alone is a simple primary key, not composite.
  - C) Local and Global secondary indices are for query indexing, not the primary key.
  - D) A sort key cannot identify elements without a partition key.
</details>

---

### 4. According to RESTful API design standards, which HTTP method should be used to make idempotent updates that overwrite a resource?
- [ ] A) POST
- [ ] B) GET
- [ ] C) PUT
- [ ] D) PATCH

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) PUT

**Explanation:** `PUT` is designed to completely replace/overwrite a resource. It is idempotent (making identical requests repeatedly yields the same state).
- **Why others are wrong:**
  - A) `POST` creates a new resource and is not idempotent.
  - B) `GET` is for data retrieval only and should not modify resources.
  - D) `PATCH` is for partial updates and is not strictly required to be idempotent.
</details>

---

### 5. In Spring Boot, which annotation is used to inject dependency beans automatically?
- [ ] A) `@Inject`
- [ ] B) `@Autowired`
- [ ] C) `@Bean`
- [ ] D) `@Component`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `@Autowired`

**Explanation:** Spring Boot uses `@Autowired` to declare that a constructor, field, or setter requires dependency injection of a matching bean.
- **Why others are wrong:**
  - A) `@Inject` is a standard Java EE annotation, but `@Autowired` is Spring's native annotation.
  - C) `@Bean` declares that a method returns a bean, but does not perform injection.
  - D) `@Component` registers a class as a Spring bean candidate, but does not inject other beans into it.
</details>

---

### 6. Consider the JavaScript code:
```javascript
setTimeout(() => console.log("A"), 0);
console.log("B");
```
What is the print order in the console?
- [ ] A) A then B
- [ ] B) B then A
- [ ] C) A and B simultaneously
- [ ] D) B only

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) B then A

**Explanation:** `setTimeout` delegates execution to the browser/node Web APIs. Even with a delay of `0`ms, the callback is pushed to the task queue and only executes *after* the main synchronous thread has completed (which prints "B").
- **Why others are wrong:**
  - A) Main thread runs synchronously; callbacks are queued.
  - C) JavaScript is single-threaded and cannot run tasks simultaneously.
  - D) The timeout callback will execute eventually, printing A.
</details>

---

### 7. In Angular, which directive is used to iterate over a list and render elements in the DOM?
- [ ] A) `*ngIf`
- [ ] B) `*ngFor`
- [ ] C) `*ngSwitch`
- [ ] D) `*ngIter`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `*ngFor`

**Explanation:** The structural directive `*ngFor` loops over iterables (like lists) and instantiates a template block for each item in the DOM.
- **Why others are wrong:**
  - A) `*ngIf` conditionally displays or hides DOM elements based on boolean values.
  - C) `*ngSwitch` evaluates switch cases to render sub-blocks.
  - D) `*ngIter` is not a valid Angular directive.
</details>

---

### 8. In a multi-stage Dockerfile, what is the primary benefit of using different build stages?
- [ ] A) It allows the container to run on both Windows and Linux hosts simultaneously.
- [ ] B) It isolates the compilation tools in build stages, producing a smaller, optimized production runtime image.
- [ ] C) It speeds up the runtime by sharing memory across all containers.
- [ ] D) It automatically implements HTTPS encryption for the application routes.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It isolates the compilation tools in build stages, producing a smaller, optimized production runtime image.

**Explanation:** Multi-stage builds compile code in temporary images (containing SDKs, build tools) and copy only the compiled binaries to the final stage, keeping image sizes small.
- **Why others are wrong:**
  - A) Multi-stage builds do not alter OS platform compatibility.
  - C) Containers have isolated namespaces and do not share process memory.
  - D) Encryption requires TLS setups, not Dockerfile structures.
</details>

---

### 9. Which Docker Compose property ensures that a service is not started until its dependent database container is healthy?
- [ ] A) `depends_on`
- [ ] B) `links`
- [ ] C) `ports`
- [ ] D) `networks`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `depends_on`

**Explanation:** The `depends_on` directive expresses startup order dependencies. Combined with a `condition: service_healthy` check, it ensures the database is online before starting dependent APIs.
- **Why others are wrong:**
  - B) `links` is a legacy Docker Compose property and is deprecated.
  - C) `ports` configures host-container port exposures.
  - D) `networks` defines bridge domain connections.
</details>

---

### 10. How does CPython execute Python programs?
- [ ] A) It compiles code directly to assembly machine instructions before running.
- [ ] B) It parses code, compiles it to bytecode in-memory, and runs it line-by-line via the interpreter.
- [ ] C) It uses a JIT compiler to compile all code to machine code before execution starts.
- [ ] D) It sends the script to the JVM to run inside Java packages.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It parses code, compiles it to bytecode in-memory, and runs it line-by-line via the interpreter.

**Explanation:** CPython is an interpreted execution model: it generates intermediate bytecode in memory and feeds it into the virtual machine interpreter loop.
- **Why others are wrong:**
  - A) C compiling produces raw assembly, but standard Python uses bytecode.
  - C) PyPy uses JIT compilers, but standard CPython does not compile code to machine code before running.
  - D) Jython routes to JVM, but CPython runs on native C runtimes.
</details>

---

### 11. Which Python data structure represents a collection that is unordered and holds only unique values?
- [ ] A) List
- [ ] B) Tuple
- [ ] C) Set
- [ ] D) Dictionary

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Set

**Explanation:** A Python `set` is an unordered collection that automatically enforces uniqueness, preventing duplicate entries.
- **Why others are wrong:**
  - A) Lists are ordered and allow duplicates.
  - B) Tuples are ordered, immutable, and allow duplicates.
  - D) Dictionaries hold key-value maps, not single values.
</details>

---

### 12. What is the security risk of taking a user-provided URL and fetching its contents from a backend server without validation?
- [ ] A) SQL Injection
- [ ] B) Server-Side Request Forgery (SSRF)
- [ ] C) Cross-Site Scripting (XSS)
- [ ] D) Remote Code Execution (RCE)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Server-Side Request Forgery (SSRF)

**Explanation:** If the server fetches an unvalidated user URL, an attacker can use it to query internal local interfaces (`127.0.0.1`) or cloud metadata endpoints (`169.254.169.254`).
- **Why others are wrong:**
  - A) SQL injection targets database query strings.
  - C) XSS targets execution of scripts inside client browsers.
  - D) RCE runs commands on the OS, whereas SSRF targets unauthorized request routing.
</details>

---

### 13. How does AWS IMDSv2 protect against Server-Side Request Forgery (SSRF) compared to IMDSv1?
- [ ] A) It encrypts all metadata payloads using SSL certificates.
- [ ] B) It requires a session-oriented token exchange (POST request followed by GET header token transmission).
- [ ] C) It disables the link-local IP `169.254.169.254` completely.
- [ ] D) It filters calls by allowlisting user agent headers.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It requires a session-oriented token exchange (POST request followed by GET header token transmission).

**Explanation:** AWS IMDSv2 enforces a token exchange. Since most basic SSRF exploits only perform simple GET requests without the ability to pass token headers dynamically, the exploit is blocked.
- **Why others are wrong:**
  - A) Metadata interfaces do not enforce SSL routing keys.
  - C) The link-local IP address remains active in IMDSv2.
  - D) User-Agent filtering is easily bypassed by attackers.
</details>

---

### 14. Which Python built-in is preferred to verify if a variable matches a specific type while supporting polymorphism?
- [ ] A) `type()`
- [ ] B) `isinstance()`
- [ ] C) `is_type()`
- [ ] D) `is_subclass()`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `isinstance()`

**Explanation:** `isinstance()` checks if an object is an instance of a class or a subclass thereof, supporting polymorph class hierarchies.
- **Why others are wrong:**
  - A) `type()` checks the exact class type but ignores inheritance pathways.
  - C) `is_type` is not a valid Python built-in function.
  - D) `is_subclass()` checks relationships between class types, not object instances.
</details>

---

### 15. In Python, standard loops (`for`, `while`) create their own scope blocks. True or False?
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** Unlike Java, loops in Python do not create new scope boundaries. Variables assigned inside a loop remain visible outside the loop within the same enclosing function or module scope.
- **Why others are wrong:**
  - A) Python only isolates scopes using functions, classes, modules, and list comprehensions.
</details>
