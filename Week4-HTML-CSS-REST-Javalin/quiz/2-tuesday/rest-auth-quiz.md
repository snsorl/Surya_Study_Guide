# Tuesday Knowledge Check: REST and Security

## Part 1: REST and Auth Quiz

### 1. Which REST constraint mandates that the client does not need to know about the server database configuration, and vice versa?
- [ ] A) Stateless
- [ ] B) Layered System
- [ ] C) Client-Server
- [ ] D) Uniform Interface

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) Client-Server

**Explanation:** The Client-Server constraint separates user interface concerns (client) from data storage concerns (server), letting both evolve independently.
- **Why others are wrong:**
  - A) Stateless means request bodies contain all variables needed for completion.
  - B) Layered System deals with routing requests through proxy servers.
  - D) Uniform Interface deals with standard resource identifiers and hypermedia.
</details>

---

### 2. An API that maps unique resource endpoints to correct HTTP verbs (GET, POST, PUT, DELETE) operates at what level of the Richardson Maturity Model?
- [ ] A) Level 0
- [ ] B) Level 1
- [ ] C) Level 2
- [ ] D) Level 3

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) Level 2

**Explanation:** Level 2 APIs utilize resource URIs coupled with standard HTTP method verbs and response status codes.
- **Why others are wrong:**
  - A) Level 0 represents a single entry point using custom POST commands (Swamp of POX).
  - B) Level 1 introduces multiple resource URLs but lacks semantic HTTP method verb structures.
  - D) Level 3 introduces hypermedia links in responses (HATEOAS).
</details>

---

### 3. Which of the following URLs conforms strictly to REST resource naming conventions?
- [ ] A) `POST /api/v1/create-user`
- [ ] B) `GET /api/v1/users/42`
- [ ] C) `DELETE /api/v1/deleteUser?id=42`
- [ ] D) `GET /api/v1/User_Accounts/42`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `GET /api/v1/users/42`

**Explanation:** This URL uses lowercase paths, plural nouns, and path parameters to identify a specific user, without using verbs.
- **Why others are wrong:**
  - A) `create-user` uses a verb, violating the noun-only rule.
  - C) `deleteUser` uses a verb and query parameters instead of standard path structures.
  - D) Uses uppercase letters and underscores, which violates hyphenation conventions.
</details>

---

### 4. When designing a nested resource path such as `/posts/{postId}/comments`, what relationship is represented?
- [ ] A) A loose association between independent resources.
- [ ] B) A hierarchical parent-child relationship where comments belong to a specific post.
- [ ] C) A remote procedure call invoking comment functions.
- [ ] D) A version control mapping structure.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) A hierarchical parent-child relationship where comments belong to a specific post.

**Explanation:** Nesting represents child sub-resources within the context of their parent resource.
- **Why others are wrong:**
  - A) Hierarchical paths show direct containment, not loose associations.
  - C) The path identifies resources, not execution operations.
  - D) Version control is mapped at the root path (`/v1/`), not at sub-resource levels.
</details>

---

### 5. What is the difference between Authentication (AuthN) and Authorization (AuthZ)?
- [ ] A) AuthN checks what you can do; AuthZ checks who you are.
- [ ] B) AuthN checks who you are; AuthZ checks what permissions/roles you have.
- [ ] C) AuthN validates database queries; AuthZ encrypts passwords.
- [ ] D) Both terms describe identical security steps.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) AuthN checks who you are; AuthZ checks what permissions/roles you have.

**Explanation:** Authentication verifies user identity, while Authorization checks if the authenticated user has permission to access a specific resource.
- **Why others are wrong:**
  - A) This reverses the definitions of AuthN and AuthZ.
  - C) Database operations and hashing are separate implementation processes.
  - D) They represent two separate logical steps inside security architectures.
</details>

---

### 6. If a user tries to access an API endpoint without providing any credentials, what HTTP status code should the server return?
- [ ] A) 400 Bad Request
- [ ] B) 401 Unauthorized
- [ ] C) 403 Forbidden
- [ ] D) 409 Conflict

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) 401 Unauthorized

**Explanation:** HTTP 401 indicates that the request lacks valid authentication credentials for the target resource.
- **Why others are wrong:**
  - A) 400 indicates malformed query syntax, not authentication failure.
  - C) 403 indicates the user is authenticated but lacks the roles required for access.
  - D) 409 indicates database conflicts like duplicate key inserts.
</details>

---

### 7. How does OWASP A04 (Insecure Design) differ from implementation defects?
- [ ] A) Insecure Design can be completely fixed by writing unit tests.
- [ ] B) Implementation defects occur at the coding level; Insecure Design represents logical architectural gaps.
- [ ] C) Insecure Design only applies to front-end page layouts.
- [ ] D) There is no difference; they are synonymous.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Implementation defects occur at the coding level; Insecure Design represents logical architectural gaps.

**Explanation:** Insecure Design refers to flaws in the application design (e.g. missing rate limits), whereas implementation defects are bugs in correctly designed logic (e.g. SQL injections).
- **Why others are wrong:**
  - A) Unit tests confirm the code runs as written, but cannot validate design safety.
  - C) Design flaws apply across all levels of system architecture, especially databases and APIs.
  - D) They represent distinct categories of software vulnerabilities.
</details>

---

### 8. Which of the following illustrates a design-level vulnerability (IDOR)?
- [ ] A) Forgetting to close a database JDBC connection.
- [ ] B) Allowing users to view reports by guessing incremental URL IDs without verifying ownership.
- [ ] C) Throwing a NullPointerException when an email field is missing.
- [ ] D) Enforcing CORS filters on all incoming endpoints.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Allowing users to view reports by guessing incremental URL IDs without verifying ownership.

**Explanation:** Insecure Direct Object Reference (IDOR) happens when an API trusts client-provided IDs without validating if the logged-in session owner has rights to that ID.
- **Why others are wrong:**
  - A) Connection leaks are implementation defects, not logical resource access errors.
  - C) Null exceptions are coding implementation errors.
  - D) CORS is a standard security configuration, not a vulnerability.
</details>

---

### 9. How does the REST 'Statelessness' constraint benefit application scaling?
- [ ] A) It reduces security concerns by skipping token validation checks.
- [ ] B) It enables any server instance in a cluster to handle any incoming request because no session state is stored on the server.
- [ ] C) It forces clients to cache all database records locally.
- [ ] D) It speeds up database execution times by avoiding index scans.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) It enables any server instance in a cluster to handle any incoming request because no session state is stored on the server.

**Explanation:** Since stateless requests carry all required data, load balancers can route requests to any server node without synchronization.
- **Why others are wrong:**
  - A) Security checks are still required on each request.
  - C) Caching is a separate constraint, and clients do not copy databases.
  - D) Database query execution is unaffected by API statelessness.
</details>

---

### 10. In REST design, what is the maximum depth that nesting resource paths should follow to maintain readability and prevent complexity?
- [ ] A) Up to five levels deep (e.g. `/v1/a/b/c/d/e`)
- [ ] B) No more than two levels deep (e.g. `/parents/{id}/children`)
- [ ] C) There is no limit.
- [ ] D) Nesting is not allowed in REST URL paths.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) No more than two levels deep (e.g. `/parents/{id}/children`)

**Explanation:** Restricting nesting to two levels keeps URLs concise. If you need deeper resources, reference them directly on their own root paths (e.g., `/children/{childId}`).
- **Why others are wrong:**
  - A) Five levels deep makes URLs long, complex, and difficult to manage.
  - C) Complexity increases quickly if no design limits are set.
  - D) Nesting is the standard convention to represent parent-child relationships.
</details>

---

### 11. When using Bearer Token authentication, in which HTTP request header is the token transmitted?
- [ ] A) `Cookie`
- [ ] B) `Authorization`
- [ ] C) `X-API-Key`
- [ ] D) `Content-Type`

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) `Authorization`

**Explanation:** Bearer tokens are sent in the standard HTTP `Authorization` header prefixed with the word `Bearer` and a space (e.g., `Authorization: Bearer <token>`).
- **Why others are wrong:**
  - A) `Cookie` headers are used for stateful cookie-based session management, not bearer tokens.
  - C) `X-API-Key` is a custom non-standard header used for static API Keys, not bearer tokens.
  - D) `Content-Type` describes the media format of the payload, not security credentials.
</details>

---

### 12. In the Basic Authentication scheme, what encoding formatting is applied to the 'username:password' credential string?
- [ ] A) AES Encryption
- [ ] B) SHA-256 Hashing
- [ ] C) Base64 encoding
- [ ] D) UTF-8 plain text string

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** C) Base64 encoding

**Explanation:** Basic Auth formats credentials by joining username and password with a colon and encoding the resulting string to Base64 (e.g., `Authorization: Basic dXNlcjpwYXNz`).
- **Why others are wrong:**
  - A) Basic Auth does not encrypt data; Base64 is easily decoded by anyone who intercepts it. It must run over HTTPS.
  - B) Hashing is one-way and cannot be decoded back by the server to check login tables.
  - D) The plain string must be encoded to Base64 to conform to the HTTP Basic specification.
</details>

---

### 13. What represents the key difference between producing an API and consuming an API?
- [ ] A) Producing is done by JavaScript; consuming is done by Java.
- [ ] B) Producing involves exposing endpoints (server-side); consuming involves calling external endpoints (client-side).
- [ ] C) Producing only handles JSON; consuming only handles HTML text.
- [ ] D) Producing is stateful; consuming is stateless.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Producing involves exposing endpoints (server-side); consuming involves calling external endpoints (client-side).

**Explanation:** Servers produce (expose) APIs for others to use, while clients (browsers, mobile apps, other servers) consume (call) those APIs to retrieve data.
- **Why others are wrong:**
  - A) Both languages are capable of producing and consuming APIs.
  - C) Both operations typically deal with standard formats like JSON.
  - D) API design constraints apply to both sides.
</details>

---

### 14. How does the Principle of Least Privilege apply to API authorization designs?
- [ ] A) Users should have full admin privileges to make development tasks easier.
- [ ] B) Clients should only be granted the minimum access rights necessary to complete their required tasks.
- [ ] C) Databases should allow public access to speed up loading.
- [ ] D) Endpoints should run without authentication checks to lower network latency.

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Clients should only be granted the minimum access rights necessary to complete their required tasks.

**Explanation:** Enforcing minimum access limits potential damage if a user account or API token is compromised.
- **Why others are wrong:**
  - A) Giving all users admin access represents a severe security vulnerability.
  - C) Public database access leads to data theft.
  - D) Disabling auth checks exposes resources to public attackers.
</details>

---

### 15. In threat modeling, which category of the STRIDE model maps to protecting against users modifying resource records unauthorized?
- [ ] A) Spoofing
- [ ] B) Tampering
- [ ] C) Information Disclosure
- [ ] D) Denial of Service

<details>
<summary><b>Click for Solution</b></summary>

**Correct Answer:** B) Tampering

**Explanation:** Tampering is the unauthorized modification of data (such as records or request parameters), which is prevented by input validation and authorization checks.
- **Why others are wrong:**
  - A) Spoofing involves pretending to be another user or system (identity theft).
  - C) Information Disclosure involves leaking data to unauthorized users.
  - D) Denial of Service involves crashing the server to prevent valid access.
</details>
