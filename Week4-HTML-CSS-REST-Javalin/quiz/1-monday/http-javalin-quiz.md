# Monday Knowledge Check: HTTP and Javalin

## Part 1: HTTP and Javalin Quiz

### 1. What does it mean for HTTP to be a stateless protocol?
- [ ] A) The server shuts down automatically after sending a response.
- [ ] B) The server does not retain memory of previous request-response transactions.
- [ ] C) Browsers are blocked from sending cookies or request headers.
- [ ] D) Clients must establish a persistent database transaction for every query.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) The server does not retain memory of previous request-response transactions.

**Explanation:** Statelessness means each request-response pair is treated as an independent transaction, and the server retains no memory of previous states by default.
- **Why others are wrong:**
  - A) Server execution persists across requests, ignoring connection closures.
  - C) Browsers send cookies and headers automatically to help manage state.
  - D) Database connections are separate pooling operations and are not defined by the statelessness of the network protocol.
</details>

---

### 2. Which of the following HTTP methods is considered both safe and idempotent?
- [ ] A) POST
- [ ] B) PUT
- [ ] C) GET
- [ ] D) DELETE

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) GET

**Explanation:** GET is safe because it only reads data without modifying server state, and idempotent because repeating the query has the exact same effect.
- **Why others are wrong:**
  - A) POST is unsafe and non-idempotent (submitting creates duplicate resources).
  - B) PUT is idempotent but unsafe (it modifies database state).
  - D) DELETE is idempotent but unsafe (it alters database state).
</details>

---

### 3. Which HTTP status code is most appropriate to return after a resource is successfully deleted and no response body is returned?
- [ ] A) 200 OK
- [ ] B) 201 Created
- [ ] C) 204 No Content
- [ ] D) 404 Not Found

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) 204 No Content

**Explanation:** HTTP 204 indicates the request completed successfully, but the response body is intentionally empty.
- **Why others are wrong:**
  - A) 200 OK is used when a response body is returned (e.g. returning the deleted item details).
  - B) 201 Created is used after successful resource insertions.
  - D) 404 Not Found is used when the resource does not exist.
</details>

---

### 4. What underlying embedded container handles servlet connections for Javalin by default?
- [ ] A) Apache Tomcat
- [ ] B) Eclipse Jetty
- [ ] C) GlassFish
- [ ] D) WildFly

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Eclipse Jetty

**Explanation:** Javalin is built directly on top of Jetty, using it as an embedded servlet engine container.
- **Why others are wrong:**
  - A) Apache Tomcat is the default container for Spring Boot, not Javalin.
  - C) GlassFish is an enterprise Java EE application server.
  - D) WildFly (JBoss) is a full application server environment.
</details>

---

### 5. In Javalin, how do you register an endpoint that reads a dynamic URL path parameter (e.g., fetching a task by its ID)?
- [ ] A) `app.get("/tasks/:id", ctx -> ...)`
- [ ] B) `app.get("/tasks/{id}", ctx -> ...)`
- [ ] C) `app.get("/tasks/?id", ctx -> ...)`
- [ ] D) `app.get("/tasks/id", ctx -> ...)`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `app.get("/tasks/{id}", ctx -> ...)`

**Explanation:** Javalin matches path parameters using curly braces `{parameterName}` within route mapping paths.
- **Why others are wrong:**
  - A) Colon syntax `:id` is used in Express.js/Spark, but not Javalin.
  - C) Question marks are used to parse query strings, not path parameters.
  - D) This would match the literal string path "/tasks/id" instead of dynamic values.
</details>

---

### 6. If a client submits a POST request containing a JSON body, which Context method parses it directly into a Java DTO object?
- [ ] A) `ctx.body()`
- [ ] B) `ctx.queryParam()`
- [ ] C) `ctx.bodyAsClass(Class)`
- [ ] D) `ctx.formParam()`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) `ctx.bodyAsClass(Class)`

**Explanation:** `ctx.bodyAsClass()` deserializes the incoming JSON body directly into the specified Java class type.
- **Why others are wrong:**
  - A) `ctx.body()` returns the raw JSON string text without parsing it.
  - B) `ctx.queryParam()` extracts query strings from the URL suffix.
  - D) `ctx.formParam()` is used to parse urlencoded form submissions.
</details>

---

### 7. How are unhandled runtime exceptions mapped globally in Javalin to prevent raw stack traces from reaching clients?
- [ ] A) By enclosing every router in standard try-catch blocks.
- [ ] B) By declaring throws clauses on the main application class.
- [ ] C) By registering exception handlers using `app.exception()`.
- [ ] D) Javalin does this automatically and cannot be customized.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) By registering exception handlers using `app.exception()`.

**Explanation:** `app.exception()` intercepts specified Java exception types globally, allowing developers to set standard HTTP status codes and response bodies.
- **Why others are wrong:**
  - A) Try-catch blocks inside route blocks lead to massive code duplication.
  - B) Throws clauses on main classes pass the stack trace directly to Jetty's fallback page.
  - D) Default servlet containers return HTML error pages; custom mappings are highly recommended for REST architectures.
</details>

---

### 8. Which of the following represents a violation of OWASP A09 (Security Logging and Monitoring Failures)?
- [ ] A) Log statements print the server IP address during startup.
- [ ] B) Password recovery requests record the client email and IP but exclude the credentials.
- [ ] C) Failed authentication endpoints log the user-submitted plain text passwords.
- [ ] D) The application outputs logs to a rolling local file system.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) Failed authentication endpoints log the user-submitted plain text passwords.

**Explanation:** Logging raw passwords represents a log leakage vulnerability, exposing secrets to log readers or operations teams.
- **Why others are wrong:**
  - A) Recording basic server startup configurations is a standard logging procedure.
  - B) Logging recovery event metadata without secrets is a recommended security practice.
  - D) Using rolling files is the industry standard for persistent log backup management.
</details>

---

### 9. What is the correct order of Logback severity levels, from most verbose (least severe) to least verbose?
- [ ] A) ERROR > WARN > INFO > DEBUG > TRACE
- [ ] B) TRACE > DEBUG > INFO > WARN > ERROR
- [ ] C) DEBUG > TRACE > INFO > ERROR > WARN
- [ ] D) INFO > DEBUG > WARN > ERROR > TRACE

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) TRACE > DEBUG > INFO > WARN > ERROR

**Explanation:** Logback levels escalate in severity from TRACE (most verbose) to ERROR (least verbose/critical defects).
- **Why others are wrong:**
  - A) This lists the order from most severe (least verbose) to least severe.
  - C) TRACE is more verbose than DEBUG.
  - D) WARN is more severe than INFO.
</details>

---

### 10. Why is SLF4J referred to as a logging facade rather than an engine?
- [ ] A) It compiles code files into runtime binary formats.
- [ ] B) It provides interface abstractions, leaving the actual output writing to an implementation engine like Logback.
- [ ] C) It only outputs logs to standard console print streams.
- [ ] D) It is built specifically to filter database JDBC queries only.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) It provides interface abstractions, leaving the actual output writing to an implementation engine like Logback.

**Explanation:** SLF4J decouples application code from specific logging frameworks, allowing developers to swap underlying logging libraries without changing logger code calls.
- **Why others are wrong:**
  - A) Logging facade layers are runtime libraries, not compilers.
  - C) Engine engines (like Logback) choose where to write outputs, not SLF4J.
  - D) SLF4J logs general application events, not just SQL queries.
</details>

---

### 11. What is the purpose of the HTTP request header 'Content-Type'?
- [ ] A) It informs the client which server software is hosting the resources.
- [ ] B) It informs the server about the media format of the request body payload.
- [ ] C) It enables encryption over the network connection stream.
- [ ] D) It specifies which language/locale the user browser prefers.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) It informs the server about the media format of the request body payload.

**Explanation:** The `Content-Type` header (e.g. `application/json` or `text/html`) tells the receiver how to parse the incoming byte payload body.
- **Why others are wrong:**
  - A) The `Server` response header indicates host software, not the request `Content-Type`.
  - C) Encryption is handled by the TLS handshake at the TCP connection level.
  - D) The `Accept-Language` header specifies user locale preferences.
</details>

---

### 12. What are the standard default network ports used for HTTP and HTTPS communication respectively?
- [ ] A) HTTP: 8080, HTTPS: 8443
- [ ] B) HTTP: 80, HTTPS: 443
- [ ] C) HTTP: 22, HTTPS: 443
- [ ] D) HTTP: 80, HTTPS: 8080

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) HTTP: 80, HTTPS: 443

**Explanation:** By default, web traffic flows over port 80 for unencrypted HTTP connections and port 443 for secure HTTPS TLS connections.
- **Why others are wrong:**
  - A) Port 8080 and 8443 are common proxy/development ports, but not the standard internet defaults.
  - C) Port 22 is used for SSH (Secure Shell) terminal connections.
  - D) Port 8080 is a generic dev alternative, not HTTPS.
</details>

---

### 13. Which Javalin configuration property sets the folder location for serving frontend static assets (like index.html or styles.css) from resources?
- [ ] A) `config.staticFiles.add("/folder")`
- [ ] B) `config.bundledPlugins.enableCors()`
- [ ] C) `config.router.mount()`
- [ ] D) `config.server.setPort()`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** A) `config.staticFiles.add("/folder")`

**Explanation:** The `config.staticFiles` API manages setting folders in the classpath or external local directories to host frontend static files.
- **Why others are wrong:**
  - B) `enableCors` configures Cross-Origin Resource Sharing rules, not file hosting folders.
  - C) `mount` is not the standard API configuration for file mapping.
  - D) `setPort` configures the TCP listening port.
</details>

---

### 14. What is the difference between ctx.result(string) and ctx.json(object) in a route handler response?
- [ ] A) `ctx.result` only works for errors; `ctx.json` only works for successful codes.
- [ ] B) `ctx.result` outputs raw text strings, while `ctx.json` serializes Java objects to JSON and sets the Content-Type header to application/json automatically.
- [ ] C) `ctx.json` converts strings to XML arrays.
- [ ] D) Both perform identical text outputs and have identical content-types.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `ctx.result` outputs raw text strings, while `ctx.json` serializes Java objects to JSON and sets the Content-Type header to application/json automatically.

**Explanation:** `ctx.json()` leverages Jackson under the hood to map Java object instances to serialized JSON arrays/objects and sets content-type headers to `application/json`.
- **Why others are wrong:**
  - A) Both methods are valid across all success and error HTTP statuses.
  - C) `ctx.json` outputs JSON strings, not XML structures.
  - D) They set different Content-Type headers (`text/plain` vs `application/json`).
</details>

---

### 15. In a logback.xml file, what is the role of the `<root>` configuration tag?
- [ ] A) It defines the root folder path for rolling log files.
- [ ] B) It configures the default global logging level and attaches appender outputs to the root logger level.
- [ ] C) It enables connection hooks to the PostgreSQL database.
- [ ] D) It compiles the logback classes during build cycles.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) It configures the default global logging level and attaches appender outputs to the root logger level.

**Explanation:** The `<root>` tag sets the fallback logging severity threshold and maps appenders (Console, File) globally across all classes.
- **Why others are wrong:**
  - A) Root paths are defined in `<file>` elements inside the appenders.
  - C) Logging configuration handles data printing, not SQL connections.
  - D) Logback is compiled by Maven or Gradle build runs.
</details>
