# Exercise: Javalin Hello API

## Objective
Create a lightweight Javalin application from scratch using Maven or Gradle. Implement endpoints to check application health and echo path parameters back to clients.

---

## Scenario
You are building the foundation of a microservices backend. Before implementing database connections or user schemas, you need to establish that the server can boot, handle routing requests, and parse URL structures correctly.

---

## Core Tasks

### 1. Project Initialization
- Initialize a new Maven or Gradle project in your IDE.
- Add the `javalin` dependency (version 6.1.3 recommended) to your build file.
- Add a console logging dependency such as `slf4j-simple`.
- Reference: [introduction-to-javalin.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/1-monday/introduction-to-javalin.md)

### 2. Main Entry Point
- Create a class named `App` containing a `public static void main(String[] args)` method.
- Initialize and start your Javalin server instance on port `8080`.

### 3. Implement `/health` (GET)
- Create a GET handler at path `/health`.
- Return the simple text string `"OK"` with an HTTP status code of `200`.

### 4. Implement `/echo/{message}` (GET)
- Create a GET handler mapping path parameters at `/echo/{message}`.
- Extract the dynamic message path variable.
- Return the value back to the client inside the response body text.
- Reference: [handlers.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/1-monday/handlers.md)

---

## Definition of Done
- The Javalin application boots on port 8080 without compiling errors.
- Running `curl -i http://localhost:8080/health` returns status code `200 OK` and body `"OK"`.
- Running `curl -i http://localhost:8080/echo/testmessage` returns status code `200 OK` and body `"testmessage"`.
