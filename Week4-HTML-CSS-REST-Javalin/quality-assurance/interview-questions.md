# Interview Questions: Week 4 - HTML, CSS, REST, Javalin

This interview question bank helps validate trainee retention of Week 4 concepts, dividing questions into Beginner, Intermediate, and Advanced tiers.

---

## Part 1: Beginner (Foundational) - 70%

### Q1: What is HTTP statelessness?
**Keywords:** Independent, State, Memory, Session
<details>
<summary>Click to Reveal Answer</summary>

Statelessness means that each request-response transaction is treated as an independent execution unit. The server does not retain memory of previous client interactions or state history by default.
</details>

---

### Q2: Explain safety and idempotency in HTTP.
**Keywords:** Safety, Idempotency, Side-effects, Multiple calls
<details>
<summary>Click to Reveal Answer</summary>

Safety means an HTTP method performs read-only operations without modifying server state (e.g. GET). Idempotency means making multiple identical requests has the same effect as making a single request (e.g. PUT, DELETE).
</details>

---

### Q3: What is the purpose of HTTP 201 Created and 204 No Content?
**Keywords:** Response code, Success, Creation, Empty body
<details>
<summary>Click to Reveal Answer</summary>

HTTP 201 Created indicates that a new resource has been successfully created as a result of the request (typically POST). HTTP 204 No Content indicates the request succeeded but there is no body payload returned in the response (typically DELETE).
</details>

---

### Q4: What are path parameters and how are they defined in Javalin route paths?
**Keywords:** Dynamic parameter, URL path, Curly braces
<details>
<summary>Click to Reveal Answer</summary>

Path parameters identify specific resources dynamically within the URL path. In Javalin, they are defined using curly braces, such as `/tasks/{id}`, and retrieved in handlers using `ctx.pathParam("id")`.
</details>

---

### Q5: What does 'ctx.bodyAsClass(Class)' do in Javalin?
**Keywords:** Jackson, Deserialization, JSON parser, DTO mapping
<details>
<summary>Click to Reveal Answer</summary>

It parses the raw HTTP request body string (usually JSON) and deserializes it directly into a Java object representation of the specified class using the Jackson library mapper.
</details>

---

### Q6: What is the purpose of 'app.exception()' in Javalin?
**Keywords:** Centralized catch, Global mapping, Exception handler
<details>
<summary>Click to Reveal Answer</summary>

It registers a global handler to catch specified Java exceptions thrown during route execution. This allows centralized logic to log the crash details and return clean, custom HTTP responses.
</details>

---

### Q7: Explain the difference between Authentication (AuthN) and Authorization (AuthZ).
**Keywords:** Identity, Permissions, Credentials, Access rights
<details>
<summary>Click to Reveal Answer</summary>

Authentication (AuthN) validates who a user is by checking credentials (e.g. passwords or API keys). Authorization (AuthZ) verifies what resources an authenticated user has permission to access based on their roles.
</details>

---

### Q8: What does HTTP 401 Unauthorized represent?
**Keywords:** Missing credentials, Authentication, AuthN failure
<details>
<summary>Click to Reveal Answer</summary>

HTTP 401 indicates that the request lacks valid authentication credentials for the target resource, meaning the user identity has not been verified.
</details>

---

### Q9: What does HTTP 403 Forbidden represent?
**Keywords:** Insufficient roles, Permissions block, AuthZ failure
<details>
<summary>Click to Reveal Answer</summary>

HTTP 403 indicates that the server understands who the user is (they are authenticated), but the user does not have the permissions or roles required to access the resource.
</details>

---

### Q10: What is the Richardson Maturity Model?
**Keywords:** REST constraints, Maturity levels, Verbs, Hypermedia
<details>
<summary>Click to Reveal Answer</summary>

It is a framework that rates an API's adherence to RESTful design constraints, divided into four levels: Level 0 (single POST endpoint), Level 1 (resources/URIs), Level 2 (HTTP verbs and status codes), and Level 3 (HATEOAS).
</details>

---

### Q11: What is the DOM (Document Object Model)?
**Keywords:** Node tree, Hierarchical structure, Browser memory, HTML parse
<details>
<summary>Click to Reveal Answer</summary>

The DOM is a programming interface that represents a parsed HTML document as a hierarchical tree of nodes in browser memory. This tree can be modified dynamically by JavaScript.
</details>

---

### Q12: What is the difference between inline and block-level HTML tags?
**Keywords:** Line break, Width footprint, Element nesting
<details>
<summary>Click to Reveal Answer</summary>

Block-level elements (e.g. `<div>`, `<p>`) start on a new line and stretch horizontally to fill their parent container. Inline elements (e.g. `<span>`, `<a>`) do not start on new lines and only take up as much width as their text content.
</details>

---

### Q13: What are HTML5 semantic tags and why are they used?
**Keywords:** Meaning, Accessibility, SEO, Layout landmarks
<details>
<summary>Click to Reveal Answer</summary>

Semantic tags (e.g. `<header>`, `<main>`, `<article>`, `<nav>`) describe their meaning to both the browser and developer. They improve accessibility for screen readers and help search engines index page content.
</details>

---

### Q14: Explain the role of the input 'name' attribute in HTML forms.
**Keywords:** Parameter key, Form serialization, Submit payload
<details>
<summary>Click to Reveal Answer</summary>

The `name` attribute is used as the query parameter or body payload key when the browser serializes form inputs for submission. Inputs without a `name` attribute are not sent to the server.
</details>

---

### Q15: What is the difference between GET and POST form submissions?
**Keywords:** URL query string, Request body, Sensitivity limits
<details>
<summary>Click to Reveal Answer</summary>

GET form submissions append parameters to the URL query string, making them visible in history logs and limiting payload size. POST submissions place parameters inside the HTTP request body payload, which is secure and supports large payloads.
</details>

---

### Q16: What is the CSS Box Model?
**Keywords:** Margin, Border, Padding, Content dimensions
<details>
<summary>Click to Reveal Answer</summary>

The Box Model represents the structural layout of HTML elements, consisting of four layers: the Content itself, surrounded by Padding (inner space), the Border, and the Margin (outer spacing).
</details>

---

### Q17: What does 'box-sizing: border-box' configure in CSS?
**Keywords:** Dimension math, Width calculation, Border inclusion
<details>
<summary>Click to Reveal Answer</summary>

It forces the browser to include padding and border widths within an element's declared width and height, preventing layout breakage when adding spacing borders.
</details>

---

### Q18: What is CSS selector specificity and how is it calculated?
**Keywords:** Rules weight, Inline, ID, Class, Element tag
<details>
<summary>Click to Reveal Answer</summary>

Specificity is the algorithm browsers use to decide which CSS rule applies to an element when multiple rules conflict. It is calculated by counting weights in categories: Inline styles (1000), IDs (100), Classes/attributes (10), and Elements (1).
</details>

---

### Q19: What is the purpose of the Logback console appender?
**Keywords:** System output, Console stream, Terminal logger
<details>
<summary>Click to Reveal Answer</summary>

The console appender writes formatted log statements directly to standard output (`System.out`) or standard error (`System.err`) streams for developer visibility in the IDE console.
</details>

---

### Q20: List the standard Logback logging levels in increasing order of severity.
**Keywords:** TRACE, DEBUG, INFO, WARN, ERROR
<details>
<summary>Click to Reveal Answer</summary>

The standard levels in increasing order of severity are: TRACE -> DEBUG -> INFO -> WARN -> ERROR.
</details>

---

### Q21: What is the difference between a logging facade (SLF4J) and a logging engine (Logback)?
**Keywords:** Interface layer, Abstract API, Concrete engine
<details>
<summary>Click to Reveal Answer</summary>

SLF4J is an abstract API facade that defines standard logging method interfaces. Logback is the concrete logging engine implementation that performs the actual format parsing and file writing.
</details>

---

## Part 2: Intermediate (Application) - 25%

### Q22: How would you prevent raw Exception stack traces from being returned to API clients?
**Keywords:** Central handler, Exception mapper, 500 status code
<details>
<summary>Click to Reveal Answer</summary>

You register a global exception mapper block (e.g. `app.exception(Exception.class, ...)` in Javalin). This mapper logs the stack trace on the server at the ERROR level and returns a generic JSON response body with an HTTP 500 status code.
</details>

---

### Q23: How does CORS restrict browser applications from making requests to different origins, and how do you resolve it in Javalin?
**Keywords:** Security boundary, Port mismatch, Access headers, CorsPlugin
<details>
<summary>Click to Reveal Answer</summary>

Browsers prevent scripts from reading responses from a different domain or port unless the server sends authorization headers. In Javalin, you resolve this by configuring the CORS plugin inside the app instantiation config, specifying permitted origin domains.
</details>

---

### Q24: Explain how to configure a Javalin app to serve HTML/CSS files as static files.
**Keywords:** Static handler, Resource folder, Classpath mapping
<details>
<summary>Click to Reveal Answer</summary>

Inside your Javalin configuration block, call `config.staticFiles.add("/public")`. Place your HTML and CSS files inside the `src/main/resources/public` folder so Javalin can resolve and serve them directly.
</details>

---

### Q25: How would you write a Postman assertion to check if a response has status 200 and a title field?
**Keywords:** pm.test, pm.response, pm.expect, json property
<details>
<summary>Click to Reveal Answer</summary>

Under the request's Tests tab, write:
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});
pm.test("Payload has title", function () {
    var data = pm.response.json();
    pm.expect(data).to.have.property("title");
});
```
</details>

---

### Q26: How do you structure a REST API endpoint for comments belonging to a specific post?
**Keywords:** Nested path, Sub-resource, Plural nouns, Path parameters
<details>
<summary>Click to Reveal Answer</summary>

Structure it as a nested resource path using plural nouns and dynamic parent IDs: `GET /api/posts/{postId}/comments` (to retrieve comments) or `POST /api/posts/{postId}/comments` (to add a comment).
</details>

---

### Q27: How does Insecure Direct Object Reference (IDOR) occur, and how do you prevent it in design?
**Keywords:** Account identifier, Direct resource lookup, AuthZ verification
<details>
<summary>Click to Reveal Answer</summary>

IDOR occurs when an application retrieves database records using user-supplied IDs (e.g. `/accounts/42`) without checking if the logged-in session user owns that record. Prevent it by validating ownership on the server side using session variables before executing query lookups.
</details>

---

### Q28: How do you format a JavaScript 'fetch()' POST request sending JSON payloads?
**Keywords:** method, headers, Content-Type, JSON.stringify, body
<details>
<summary>Click to Reveal Answer</summary>

Configure the options object with:
1. `method: 'POST'`
2. `headers: { 'Content-Type': 'application/json' }`
3. `body: JSON.stringify(object)`
Example:
```javascript
fetch('/api/todos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title: 'New task' })
});
```
</details>

---

## Part 3: Advanced (Deep Dive) - 5%

### Q29: What happens under the hood when a browser processes a cross-origin preflight request (OPTIONS method)?
**Keywords:** Preflight request, OPTIONS method, Access-Control headers, Browser validation
<details>
<summary>Click to Reveal Answer</summary>

For unsafe cross-origin requests (e.g. POST with JSON), the browser sends a preflight `OPTIONS` request. The server must respond with `Access-Control-Allow-Origin`, `Access-Control-Allow-Methods`, and `Access-Control-Allow-Headers`. The browser validates these headers before sending the actual request.
</details>

---

### Q30: How would you design a centralized logging policy that logs request details without exposing credentials, complying with OWASP A09?
**Keywords:** Middleware filter, Request log, Parameter sanitization, Security-by-Design
<details>
<summary>Click to Reveal Answer</summary>

Implement a Javalin `before()` filter to log request methods, paths, and source IPs using parameterized logging. To protect credentials, sanitize headers (e.g., mask `Authorization` tokens) and query parameters before writing to Logback rolling files.
</details>
