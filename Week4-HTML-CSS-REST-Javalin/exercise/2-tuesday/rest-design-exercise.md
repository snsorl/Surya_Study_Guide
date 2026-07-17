# Design Challenge: REST API Schema

## Objective
Design the REST API endpoint routing structure for a Social Media application. Document the HTTP methods, URL paths, input parameters, and expected response codes for Users, Posts, and Comments.

---

## The Scenario
Your company is building "ConnectHub", a new social network. You are the Lead Architect. Before writing any backend Javalin controllers, you must design a clean, intuitive, noun-based REST API routing schema that the frontend team can read as their integration contract.

---

## Core Tasks

### 1. Requirements Specifications
The API must support these operations:
- **Users**: Creating a profile, viewing all profiles, fetching a single profile, deleting a profile.
- **Posts**: Creating a post (associated with a user), viewing all posts in the system, viewing all posts *by a specific user*, deleting a post.
- **Comments**: Adding a comment to a specific post, viewing all comments under a post, editing a comment text.

### 2. URL Design Rules
- Follow REST conventions: use plural nouns, lowercase paths, and hyphens. Avoid putting verbs in paths (e.g., no `/deletePost`).
- Nest sub-resources correctly to represent relationships (e.g., comments belonging to a post).
- Differentiate where to use path parameters vs. query parameters (e.g., sorting/filtering posts vs. fetching a single post by ID).
- Reference: [rest-resources-and-url-construction.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/2-tuesday/rest-resources-and-url-construction.md)

### 3. Deliverable Layout
Create a markdown document containing a structured layout table of your routing design. For each endpoint, list:
- HTTP Method
- URL Path
- Request Body required (Yes/No - if yes, brief details)
- Response Status (Success code and failure code)
- Description of the operation

---

## Definition of Done
Your submitted design document must contain:
- A table listing at least 10 clean REST endpoints mapping the required social media operations.
- Zero RPC-style routes containing action verbs.
- Nested paths for Comments (e.g. `/posts/{postId}/comments`).
- Clear mapping of HTTP status codes (200, 201, 204, 400, 404).
