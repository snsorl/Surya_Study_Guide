# HTTP Status Codes

## Learning Objectives
- Identify the five standard classes of HTTP status codes.
- Explain the meanings of commonly used status codes in REST APIs (200, 201, 204, 400, 401, 403, 404, 409, 500, 503).
- Choose the correct HTTP status code for successful and failed API scenarios.

---

## Why This Matters
When a client sends a request to your API, it needs to know whether the request succeeded or failed, and *why*. Instead of returning raw error messages or custom text formats, HTTP provides a standardized vocabulary of status codes. By using the correct status code, you build APIs that work seamlessly with client-side applications, load balancers, proxies, and error monitoring tools.

---

## The Concept

### HTTP Status Code Classes
HTTP status codes are three-digit numbers divided into five classes based on the first digit:

-   **1xx (Informational)**: The request was received, and the process is continuing. (Rarely used in REST APIs).
-   **2xx (Success)**: The action was successfully received, understood, and accepted.
-   **3xx (Redirection)**: Further action must be taken by the client to complete the request.
-   **4xx (Client Error)**: The request contains bad syntax or cannot be fulfilled due to a client-side issue.
-   **5xx (Server Error)**: The server failed to fulfill an apparently valid request due to a server-side issue.

### Commonly Used REST Status Codes

#### 2xx Success Codes
-   **200 OK**: The standard response for successful HTTP requests. For GET requests, the response body contains the requested resource.
-   **201 Created**: The request has been fulfilled and has resulted in one or more new resources being created. Commonly returned after a successful POST request.
-   **204 No Content**: The server successfully processed the request, but is not returning any content. Commonly used for successful PUT or DELETE requests when no response body is needed.

#### 3xx Redirection Codes
-   **301 Moved Permanently**: The resource has been assigned a new permanent URI.
-   **304 Not Modified**: Indicates that the resource has not been modified since the last request, allowing the client to use its cached version.

#### 4xx Client Error Codes
-   **400 Bad Request**: The server cannot process the request due to client error (e.g., malformed request syntax, missing payload, invalid data types).
-   **401 Unauthorized**: The request requires user authentication. The client must authenticate itself to get the requested response.
-   **403 Forbidden**: The client does not have access rights to the content (i.e., they are authenticated but do not have permission/roles to access the resource).
-   **404 Not Found**: The server cannot find the requested resource. This is returned when a URL points to a non-existent path or ID.
-   **409 Conflict**: The request conflicts with the current state of the server. Commonly returned when trying to create a resource that already exists (e.g., duplicate email registration).

#### 5xx Server Error Codes
-   **500 Internal Server Error**: A generic error message returned when an unexpected condition was encountered on the server (e.g., database connection failure, unhandled NullPointerException).
-   **503 Service Unavailable**: The server is temporarily unable to handle the request due to maintenance or overload.

---

## Code Example

Here is a conceptual mapping of Javalin routes to their appropriate status codes.

```java
// Returning 200 OK with data
ctx.status(200);
ctx.json(userList);

// Returning 201 Created after resource creation
ctx.status(201);
ctx.json(newlyCreatedUser);

// Returning 204 No Content after successful deletion
ctx.status(204);

// Returning 400 Bad Request due to validation error
if (requestBody.getUsername() == null) {
    ctx.status(400);
    ctx.json(new ErrorResponse("Username is required"));
}

// Returning 404 Not Found if resource doesn't exist
if (user == null) {
    ctx.status(404);
}
```

---

## Summary
-   HTTP status codes are grouped into classes from **1xx** to **5xx**.
-   **2xx** codes denote success; use **200** for standard success, **201** for creation, and **204** for empty responses.
-   **4xx** codes denote client errors; use **400** for bad data, **401** for unauthenticated requests, **403** for unauthorized permission issues, **404** for missing items, and **409** for state conflicts.
-   **5xx** codes represent server-side problems; a clean API should minimize **500** errors by mapping exceptions to relevant client errors.

---

## Additional Resources
-   [MDN Web Docs: HTTP Response Status Codes](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
-   [HTTP Status Codes - Web3 Ref Guide](https://httpstatuses.com/)
-   [REST API Tutorial: HTTP Status Codes](https://restfulapi.net/http-status-codes/)
