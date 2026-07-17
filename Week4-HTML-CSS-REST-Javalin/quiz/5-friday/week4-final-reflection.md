# Friday Knowledge Check: Project 1 Reflection

This reflection is not graded. It is designed to help you analyze your technical decisions, pair programming workflows, and identify areas for growth as you transition into next week's frameworks.

---

## Retrospective Questions

### 1. Database Schema Design
Reflect on your final PostgreSQL database layout:
- If you were starting the project from scratch today, how would you design your tables differently?
- Did you run into obstacles with foreign key constraints or cascading deletes? How did you resolve them?

---

### 2. Backend Routing and DAO Separation
Evaluate your Java codebase structure:
- Did you maintain a clean separation between your database query logic (DAO) and your request handling routing code (Javalin Controllers)?
- Where did you handle input data validation (e.g. checking empty fields), and do you believe that was the most secure boundary?

---

### 3. Frontend-Backend Integration (The Fetch Loop)
Analyze your asynchronous Javascript integrations:
- What was the most challenging aspect of connecting your HTML forms and button click actions to your Javalin REST API endpoints?
- How did you resolve CORS block restrictions or parsing errors when dealing with JSON objects?

---

### 4. Collaborative Development (Pair Programming)
Review your team's coordination and Git workflow:
- How did the Driver/Navigator rotation schedule affect your understanding of the code you did not write directly?
- Did your team run into Git merge conflicts? What strategies did you use to merge codebases without losing work?

---

### 5. Architectural Security (Threat Retrospective)
Evaluate the security posture of your final application:
- Does your API suffer from Insecure Direct Object References (IDOR) on database updates or deletions? How could you mitigate this?
- Did you implement logging? What events do your logs capture, and did you successfully prevent credential log leakage?

---

### 6. Statelessness vs Stateful Sessions
Reflect on your state management strategy:
- Did you utilize `ctx.sessionAttribute()` to store stateful parameters? What scaling limitations would this introduce if your app were deployed on a server cluster?
- What are the benefits of transitioning to stateless JWTs for future authentication checks?

---

### 7. Structured Logging Configurations
Analyze your logging infrastructure:
- Did you configure console and file appenders using a `logback.xml` file?
- How did setting logger severity thresholds (like INFO vs DEBUG) help you inspect SQL statements without bloating console logs?

---

### 8. API URL Conventions Compliance
Audit your API endpoint paths:
- Did you strictly adhere to noun-based, lowercase paths for your resources, avoiding action verbs in URLs?
- How did nesting paths (e.g. `/users/{id}/tasks`) help you structure related database tables?

---

### 9. Semantic HTML Layouts
Reflect on your frontend markup layout:
- Did you structure your pages using semantic landmarks (like `<header>`, `<main>`, `<section>`) or did you rely on generic `<div>` grids?
- How did this choice affect page accessibility checks and screen reader flows?

---

### 10. CSS Box Model Calculations
Analyze your styling math:
- Did you use `box-sizing: border-box;` on all elements? How did this prevent padding adjustments from breaking your column grid coordinates?
- Did you encounter margins leaking outside parent blocks? How did you resolve it?

---

### 11. CSS Specificity Debugging
Evaluate selector conflicts:
- Did you have rules that were ignored by the browser due to lower specificity? How did you trace and debug this conflict using Developer Tools?
- Why is it recommended to avoid inline styles and root element overrides?

---

### 12. In-Context Learning Prompting
Analyze your AI tool collaboration:
- How did providing high-density context (class schemas, table structures, and frameworks) change the quality of AI responses compared to basic prompting?
- Did you encounter AI hallucinations, and how did you resolve them?

---

### 13. Exposing vs Consuming APIs
Reflect on server-to-server and server-to-client roles:
- How does the process of exposing JSON on the server differ from fetching and parsing JSON on the client?
- What role does serialization (Jackson `ObjectMapper` vs JS `JSON.stringify`) play in this cycle?

---

### 14. STRIDE Threat Model Mitigations
Analyze your design-level defenses:
- What potential threats (like Spoofing or Tampering) were mitigated by your API key handler or input validation checks?
- What gaps remain unprotected in your current system?

---

### 15. Automated Testing Value
Retrospect on Postman test scripts:
- How did writing automated assertions in Postman (like testing for status code 200 or payload fields) speed up your integration loops?
- How does this prepare you for writing JUnit tests in future sprints?

