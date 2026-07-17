# The Fetch API

## Learning Objectives
- Initiate HTTP requests using the global `fetch()` API.
- Implement the two-step response parsing pattern (`response.json()`).
- Configure request options, including methods, headers, and body payloads.
- Handle connection errors and HTTP error status codes (e.g. 404, 500) correctly.

---

## Why This Matters
Modern frontend applications rarely operate in isolation. To load active user records, submit contact forms, or save checkout orders, the client-side JavaScript must communicate with backend services (like our Spring Boot APIs). The **Fetch API** is the standard, built-in browser mechanism used to perform these network queries asynchronously. Knowing how to configure headers, serialize body payloads, and inspect status codes is key to building clean client-server integrations.

---

## The Concept

### 1. The Core `fetch()` Pattern
The `fetch()` method starts a network request and returns a **Promise** that resolves to a `Response` object:

```javascript
fetch(url, options)
```
- **`url`**: The API target endpoint address.
- **`options`**: An optional configuration object (defining methods, headers, body).

Because parsing response payloads is also an asynchronous operation, retrieving JSON requires a **two-step Promise resolution**:
1. Resolve the network connection (`fetch()`).
2. Parse the body text as JSON (`response.json()`).

```javascript
fetch("https://api.example.com/data")
    .then(response => response.json()) // Step 1 & 2
    .then(data => console.log(data));
```

### 2. Request Options Configuration
To send data (e.g. POST, PUT, DELETE operations), you must pass a configuration options object as the second argument:
- **`method`**: The HTTP method (e.g., `"POST"`, `"PUT"`, `"DELETE"`).
- **`headers`**: An object containing request headers. When sending JSON payloads, you must explicitly set `"Content-Type": "application/json"`.
- **`body`**: The serialized string data payload (usually parsed via `JSON.stringify(object)`).

### 3. Crucial Fetch Error Handling Behavior
> [!IMPORTANT]
> A Promise returned by `fetch()` **will not reject on HTTP error statuses** (like `404 Not Found` or `500 Internal Server Error`). The promise only rejects on network failures (like dns lookup failure or loss of internet connection).
> 
> To detect HTTP errors, you must check the **`response.ok`** boolean property (which is `true` for status codes in the range `200-299`) and throw an error manually if it is `false`.

---

## Code Examples

### POST Request with Strict Error Handling
Below is a complete pattern showing how to send data to a Spring Boot backend API with proper status checks:

```javascript
const newTodo = {
    title: "Write Spring Data unit tests",
    completed: false
};

fetch("http://localhost:8080/api/todos", {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer token123" // Example Auth Header
    },
    body: JSON.stringify(newTodo) // Serialize payload
})
.then(response => {
    // Check if HTTP status is in the 200-299 range
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return response.json(); // Step 2: Parse JSON
})
.then(savedTodo => {
    console.log("Record saved successfully:", savedTodo);
})
.catch(error => {
    // Captures both network errors and manually thrown HTTP status errors
    console.error("Fetch operation failed:", error.message);
});
```

---

## Summary
- **`fetch()`** initiates asynchronous HTTP requests and returns a Promise resolving to a `Response` object.
- Accessing the response payload requires a two-step process: resolving the connection, then calling **`response.json()`**.
- When sending JSON data in a POST/PUT request, you must configure `"Content-Type": "application/json"` in the headers and serialize the body with **`JSON.stringify()`**.
- Use **`response.ok`** to verify whether the HTTP request was successful (2xx status), as HTTP errors do not reject the fetch Promise.

---

## Additional Resources
- [MDN Web Docs: Fetch API guide](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)
- [JavaScript.info: Fetch](https://javascript.info/fetch)
