# Submitting Forms

## Learning Objectives
- Explain the role of the `action` and `method` attributes in form submission.
- Contrast the network behavior of GET and POST form submissions.
- Identify the differences between `application/x-www-form-urlencoded` and `multipart/form-data` encoding types.
- Read form-submitted parameters on the backend using Javalin context queries.

---

## Why This Matters
When a user clicks a form's submit button, the browser performs a substantial amount of work under the hood. It collects all child inputs, encodes them into a single payload, and formats an HTTP request. As a developer, if you select the wrong encoding type or HTTP method, your backend server might receive empty parameters, fail to parse uploaded files, or expose passwords in browser address history logs. Understanding form submission mechanics is key to ensuring secure and reliable user interactions.

---

## The Concept

### The `action` and `method` Attributes
-   **`action`**: The target URL where the form data will be sent (e.g., `action="/api/tasks"`). If omitted, the browser submits the data back to the current page's URL.
-   **`method`**: Specifies which HTTP method to use. For standard HTML form submissions, the only supported values are **GET** and **POST**.

### GET vs. POST Form Submission

#### 1. GET Submission (`method="GET"`)
-   **Behavior**: The browser serializes all input name-value pairs and appends them to the end of the `action` URL as a query string (e.g., `/search?query=java&limit=10`).
-   **Usage**: Use GET submissions for **idempotent read operations** (like searches, sorting, or filtering).
-   **Security Risk**: Never use GET for sensitive forms (like logins or registrations). Since data is appended directly to the URL, it is saved in the user's browser history, server logs, and proxy logs in plain text.

#### 2. POST Submission (`method="POST"`)
-   **Behavior**: The browser packages the serialized input values inside the **HTTP Request Body** rather than in the URL path.
-   **Usage**: Use POST submissions for **write operations** (creating a user, placing an order, uploading files).
-   **Security**: More secure than GET because data is hidden from URLs, though encryption (HTTPS) is still required to secure the payload in transit.

### Form Encodings (`enctype` attribute)
The browser needs to know how to format the request body text. This is configured via the `enctype` attribute on the `<form>` tag:

#### 1. `application/x-www-form-urlencoded` (Default)
Inputs are formatted as key-value query parameters separated by `&`, with spaces replaced by `+` and special characters escaped.
-   *Payload Example*: `username=john+doe&email=john%40example.com`
-   *Use Case*: Standard text-only inputs.

#### 2. `multipart/form-data`
The body is split into multiple parts separated by boundary strings. Each part contains headers and a sub-payload.
-   *Use Case*: **Mandatory when uploading files** (like images, PDFs, or documents) alongside form inputs.
-   *Configuration*: `<form action="/upload" method="POST" enctype="multipart/form-data">`

#### 3. `text/plain`
Sends data as plain text. It is rarely used because it does not follow standard parameter serialization, making it difficult for backend frameworks to parse.

---

## Code Example

Here are examples showing a frontend HTML form and how the backend Javalin server retrieves those parameters.

### Frontend: HTML Form (POST)
```html
<form action="http://localhost:8080/api/users" method="POST">
    <label for="reg-username">Username:</label>
    <input type="text" id="reg-username" name="username">
    
    <label for="reg-email">Email:</label>
    <input type="email" id="reg-email" name="email">

    <button type="submit">Create Account</button>
</form>
```

### Backend: Javalin Context Handlers
When the form is submitted via POST using the default urlencoded formatting, the Javalin controller extracts the parameters using `ctx.formParam()`:

```java
import io.javalin.Javalin;

public class FormSubmitApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        // Intercept Form POST
        app.post("/api/users", ctx -> {
            // Retrieve values by input 'name' attributes
            String username = ctx.formParam("username");
            String email = ctx.formParam("email");

            if (username == null || email == null) {
                ctx.status(400).result("Invalid form parameters submitted.");
                return;
            }

            System.out.println("Processing registration: " + username + " (" + email + ")");
            ctx.status(201).result("User created successfully.");
        });

        // Intercept Form GET Search
        // URL: GET /search?q=query+string
        app.get("/search", ctx -> {
            String query = ctx.queryParam("q");
            ctx.result("Search results matching: " + query);
        });
    }
}
```

---

## Summary
-   The **`action`** attribute defines the target route, while the **`method`** defines the HTTP action (GET or POST).
-   **GET submissions** append form parameters to the URL string, making them ideal for search pages but unsafe for credentials.
-   **POST submissions** enclose parameters inside the request body payload.
-   Use **`multipart/form-data`** encoding when uploading files, and parse incoming form values on the backend using Javalin's **`ctx.formParam()`** utility.

---

## Additional Resources
-   [MDN: Sending Form Data](https://developer.mozilla.org/en-US/docs/Learn/Forms/Sending_and_retrieving_form_data)
-   [W3Schools: HTML Form Attributes](https://www.w3schools.com/html/html_form_attributes.asp)
-   [Javalin Documentation: Context File Uploads](https://javalin.io/documentation#uploaded-files)
