# Testing APIs with Postman

## Learning Objectives
- Navigate the Postman interface to construct and send HTTP requests.
- Configure request components: HTTP methods, URLs, headers, and request bodies.
- Group API requests into Collections for better organization and testing.
- Utilize Postman Environments and Variables to write modular requests.
- Export and import Postman collections to share testing suites with a team.

---

## Why This Matters
Building a REST API is only useful if it functions correctly under all scenarios. While you can use tool commands like `curl` or browser address bars to test simple GET requests, manually testing complex endpoints—especially those requiring POST/PUT JSON payloads, headers, or authentication tokens—becomes tedious and error-prone. Postman provides a visual, powerful interface to construct, execute, automate, and share API requests. As you transition to front-end integration, Postman acts as a critical sandbox to isolate backend behavior and verify API responses in isolation.

---

## The Concept

### The Postman Interface Overview
Postman is a popular API client that makes it easy for developers to create, share, test, and document APIs. The core window is divided into:
-   **Sidebar**: Displays your history, collections, environment settings, and mock servers.
-   **Request Builder (Tabs)**: The workspace where you define the details of an HTTP request.
-   **Response Pane**: Displays the response status code, execution time, size, headers, and formatted response body (HTML, JSON, XML, etc.) after sending a request.

### Constructing Requests
To build a request in Postman:
1.  **Select the Method**: Choose GET, POST, PUT, DELETE, etc., from the dropdown next to the URL input.
2.  **Enter the URL**: Input the server target endpoint (e.g., `http://localhost:8080/api/users`).
3.  **Configure Headers**: In the **Headers** tab, add metadata key-value pairs (e.g., `Content-Type: application/json` or `Authorization: Bearer <token>`).
4.  **Add a Request Body**: If using a write method (like POST or PUT), click the **Body** tab, select the **raw** radio button, and choose **JSON** from the format dropdown. Type your JSON payload in the editor.

### Organising with Collections
A **Collection** is a group of saved requests. Instead of rebuilding requests every time you open Postman, you can save requests to a collection representing a specific project or feature area. 
Collections enable you to:
-   Document request workflows.
-   Run the entire suite of requests sequentially to verify API behaviors.
-   Configure authorization and headers at the collection level, which automatically inherit down to individual requests.

### Environments and Variables
Hardcoding URLs (like `http://localhost:8080`) across dozens of requests in a collection is an anti-pattern. If you deploy your API to staging or production, you would have to manually edit every single request.

Postman resolves this using **Environments** and **Variables**:
-   **Environment**: A set of key-value pairs (variables) representing a specific deployment target (e.g., "Local Dev", "Staging", "Production").
-   **Variables**: Placeholders written in double curly braces, such as `{{baseUrl}}` or `{{adminToken}}`.

When you run a request, Postman replaces `{{baseUrl}}/api/users` with the value configured in the currently selected active environment.

### Collaboration and Exporting
To share your testing suite with teammates or commit it to source control alongside your backend code:
1.  Click the three dots `...` next to the Collection name in the sidebar.
2.  Select **Export** and choose the recommended format (usually Collection v2.1).
3.  Save the generated `.json` file to your workspace.
4.  Teammates can click the **Import** button in Postman to load your collection file.
5.  You can follow the same flow to export Environments.

---

## Code Example

Here is an example structure of a JSON payload that you would enter in the raw body tab of Postman, alongside a representation of the variables pattern.

### Request Body (raw -> JSON)
```json
{
  "title": "Buy groceries",
  "dueDate": "2026-07-20",
  "priority": "High"
}
```

### Modular URL Setup in Postman
Instead of entering:
`http://localhost:8080/api/v1/tasks/45`

You configure variables:
`{{host}}:{{port}}/api/v1/tasks/{{selectedTaskId}}`

In your "Local Dev" Environment configuration:
-   `host`: `http://localhost`
-   `port`: `8080`
-   `selectedTaskId`: `45`

---

## Summary
-   **Postman** is a graphical client used for building, executing, and testing HTTP requests.
-   Requests are configured with HTTP methods, target URLs, custom headers, and payload bodies (often formatted as raw JSON).
-   **Collections** group related API requests, allowing them to share configurations and run in sequences.
-   **Environments and Variables** parameterize values like host names or port numbers, making it easy to swap between local dev and live server environments.
-   Collections and Environments can be **exported as JSON** files to share test suites across teams.

---

## Additional Resources
-   [Postman Learning Center: Getting Started](https://learning.postman.com/docs/getting-started/introduction/)
-   [Postman: Building Requests](https://learning.postman.com/docs/sending-requests/requests/)
-   [Postman: Using Environments](https://learning.postman.com/docs/sending-requests/managing-environments/)
