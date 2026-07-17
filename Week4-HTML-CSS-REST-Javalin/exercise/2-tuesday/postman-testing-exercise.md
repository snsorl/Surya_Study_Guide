# Exercise: Testing APIs with Postman

## Objective
Import a pre-configured Postman collection to test the Javalin CRUD Todo REST API. Add required authorization headers, run the collection queries, and write automated Postman assertion test scripts.

---

## Scenario
Manual curl requests are too slow for validating routing logic under active development. You need to configure a repeatable testing pipeline in Postman that verifies status codes and response bodies automatically, preparing for integrated unit testing.

---

## Core Tasks

### 1. Import Collection
- Launch the Postman client application.
- Import the collection file [TodoAPI.postman_collection.json](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/demos/2-tuesday/code/TodoAPI.postman_collection.json) located in Tuesday's demo code folder.

### 2. Configure Authentication
- Ensure the Javalin server is running with the `AuthHandler` security filter active.
- Verify that sending a GET request without headers returns `401 Unauthorized`.
- Configure the request headers in Postman: add key `X-API-Key` with the value `super-secret-admin-key`.
- Reference: [authorization-vs-authentication.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/2-tuesday/authorization-vs-authentication.md)

### 3. Write Automation Assertions
- In Postman, select the **GET Todo by ID** request tab.
- Navigate to the **Tests** sub-tab under the URL bar.
- Write two JavaScript test assertions:
  1. Verify the HTTP response status code is `200`.
  2. Verify the JSON response body is an object containing properties: `id`, `title`, and `completed`.
- Use the Postman test snippet syntax:
  ```javascript
  pm.test("Status code is 200", function () {
      pm.response.to.have.status(200);
  });
  
  pm.test("Response contains todo fields", function () {
      var jsonData = pm.response.json();
      pm.expect(jsonData).to.have.property("title");
  });
  ```

### 4. Execute the Suite
- Save your requests in Postman.
- Run the entire folder collection sequentially using Postman's collection runner tool.

---

## Definition of Done
- The Postman collection is successfully imported.
- All requests (GET, POST, PUT, DELETE) return correct status codes (2xx/4xx) in Postman logs.
- The **Tests** tab of GET by ID reports **PASS** for both status verification and JSON fields verification.
