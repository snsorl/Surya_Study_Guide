# HTTP Methods

## Learning Objectives
- Identify the primary HTTP methods (GET, POST, PUT, PATCH, DELETE) and their semantic purposes.
- Explain the concepts of safe and unsafe HTTP methods.
- Define what idempotency is and why it matters in API design.
- Differentiate between PUT and PATCH operations.

---

## Why This Matters
When building RESTful APIs using backend frameworks like Javalin, you do not just use URLs to define actions. Instead, HTTP methods act as verbs that tell the server *what action* to perform on a given resource. Correctly utilizing HTTP methods according to their specifications ensures your API behaves predictably, scales well, can be cached efficiently, and integrates correctly with web clients, browsers, and proxies.

---

## The Concept

### HTTP Methods as Actions
In RESTful design, resources (like users, files, or products) are represented by URLs. We use HTTP methods to perform operations on these resources. The five most common methods are:

1.  **GET**: Requests a representation of the specified resource. GET requests should only retrieve data and must have no side effects (i.e., they should not change the state of the server).
2.  **POST**: Submits data to the server to create a new resource. The type of the body is defined by the `Content-Type` header.
3.  **PUT**: Replaces all current representations of the target resource with the request payload. If the resource does not exist, PUT can sometimes create it, though it is primarily used for full updates.
4.  **PATCH**: Applies partial modifications to a resource. Unlike PUT, which replaces the entire resource, PATCH updates only the fields specified in the request.
5.  **DELETE**: Deletes the specified resource.

### Safe HTTP Methods
An HTTP method is considered **safe** if it does not alter the state of the server. In other words, a safe method only reads data and does not create, update, or delete resources.
-   **Safe Methods**: GET, HEAD, OPTIONS
-   **Unsafe Methods**: POST, PUT, PATCH, DELETE

Browsers and web crawlers feel free to call safe methods automatically (e.g., pre-fetching pages), which is why you must never design a GET endpoint that modifies database state (like `/deleteUser?id=5`).

### Idempotent HTTP Methods
An HTTP method is **idempotent** if making multiple identical requests has the exact same effect on the server as making a single request. 
-   **GET**: Idempotent. Retrieving a resource multiple times does not change the resource.
-   **PUT**: Idempotent. If you update a user's email to `test@example.com` five times, the email is still `test@example.com` after the first, second, and fifth requests.
-   **DELETE**: Idempotent. The first request deletes the resource. Subsequent requests will return a 404 Not Found or 200/204 but the resource remains deleted; the state of the server has not changed any further.
-   **PATCH**: Not strictly idempotent by definition, though it can be. If a PATCH request performs a relative operation (e.g., `{ "incrementAgeBy": 1 }`), calling it multiple times will change the state repeatedly.
-   **POST**: **Not idempotent**. If you call POST five times to submit an order, you will create five different orders in the database.

| HTTP Method | Safe | Idempotent | Request Body | Response Body |
|---|---|---|---|---|
| **GET** | Yes | Yes | No | Yes |
| **POST** | No | No | Yes | Yes |
| **PUT** | No | Yes | Yes | Yes / No |
| **PATCH** | No | No / Yes | Yes | Yes |
| **DELETE** | No | Yes | No / Yes | Yes / No |

---

## Code Example

Here are examples of how these methods look in practice when communicating with a `/users` API.

### 1. Creating a User (POST)
```http
POST /users HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com"
}
```

### 2. Updating the User completely (PUT)
```http
PUT /users/42 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "username": "johndoe",
  "email": "newjohn@example.com"
}
```

### 3. Modifying only the email (PATCH)
```http
PATCH /users/42 HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "email": "john.doe@example.com"
}
```

### 4. Deleting the User (DELETE)
```http
DELETE /users/42 HTTP/1.1
Host: api.example.com
```

---

## Summary
-   **GET** is safe and idempotent; it should only retrieve data.
-   **POST** is neither safe nor idempotent; it is typically used to create new resources.
-   **PUT** is used to replace an entire resource and is idempotent.
-   **PATCH** is used for partial updates.
-   **DELETE** is used to remove a resource and is idempotent.
-   Understanding **safety** and **idempotency** is crucial to prevent unexpected bugs and design clean REST APIs.

---

## Additional Resources
-   [MDN Web Docs: HTTP Request Methods](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods)
-   [RFC 9110: HTTP Semantics - Methods](https://datatracker.ietf.org/doc/html/rfc9110#section-9)
-   [Rest API Tutorial: HTTP Methods](https://restfulapi.net/http-methods/)
