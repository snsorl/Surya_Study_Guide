# Pair Programming Activity: Project 1 Build Session

## Objective
Collaborate in assigned developer pairs (Driver and Navigator) to integrate your backend Javalin REST API, JDBC data objects, PostgreSQL database, and HTML/CSS web frontend. Demonstrate a working end-to-end CRUD cycle for at least one database resource.

---

## Scenario
You are part of a software engineering squad. Individual code chunks are insufficient; you must combine your team's code branches into a cohesive, running application. This session replicates enterprise team integration workflows, requiring coordination, source control management, and collective debugging.

---

## The Collaborative Protocol

### 1. Developer Roles
-   **The Driver**: Sits at the keyboard. They write the actual code and compile/run the application locally. They focus on structural correctness, syntax, and immediate details.
-   **The Navigator**: Guides the strategy. They read requirements, audit code for logic defects or design-level security vulnerabilities, keep track of time, and look up documentation references.
-   **The Shift Rule**: Partners must exchange roles (swap Driver and Navigator keys) every **45 minutes** to ensure equal hands-on contribution.

---

## Core Integration Tasks

### 1. Database Connections (PostgreSQL)
- Set up a shared or local PostgreSQL database instance.
- Run your initialization SQL script (`schema.sql`) to establish primary/foreign key tables.

### 2. Backend Routing and DAO
- Build your DAO classes using JDBC statements to execute SQL queries.
- Build your Javalin controller endpoints mapping routes to your DAO logic.
- Verify backend operations separately in Postman before trying to connect the frontend.

### 3. Frontend Fetch Integration
- Serve your HTML and CSS resources from Javalin's static file handler.
- Write browser JavaScript using `fetch()` to query your API endpoints.
- Map the response JSON data to update the live DOM tree dynamically.
- Reference: [project-work-day.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/4-thursday/project-work-day.md)

### 4. CORS Configuration
- Configure CORS rules inside your Javalin startup config block to resolve connection issues.
- Reference: [configuration.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/1-monday/configuration.md)

---

## Definition of Done (Deliverable)
By the end of the day, your pair must demonstrate to the instructor:
- A running Javalin server communicating with a PostgreSQL database.
- An HTML page that fetches records from the database and renders them in the DOM.
- A form on the HTML page that successfully sends a POST request to add a record to the database.
- Centralized logging in the console showing request patterns and status codes.
