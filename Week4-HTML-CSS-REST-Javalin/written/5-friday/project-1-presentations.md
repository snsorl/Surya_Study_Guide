# Project 1 Presentations Guide

## Learning Objectives
- Prepare and deliver a structured 10-minute technical presentation.
- Demonstrate a running full-stack web application live.
- Explain database design decisions and API routing structures clearly.
- Respond professionally to peer and instructor Q&A queries.
- Evaluate peer projects using a structured feedback rubric.

---

## Why This Matters
Writing great code is only half of your responsibility as a professional software engineer. You must also be able to explain *how* your code works and *why* you made specific design decisions. In your career, you will present new features to product managers, pitch architectures to engineering leads, and demo products to customers. Presenting your Project 1 build to your cohort is the perfect sandbox to practice translating complex technical solutions into clear, engaging, and professional business narratives.

---

## The Concept

### The Presentation Structure
Each team is allotted **10 minutes** for their presentation, followed by **5 minutes** of Q&A. To maximize your score, budget your time according to this standard outline:

1.  **The Hook & User Value (2 Minutes)**: Explain the problem your application solves. Walk through a live demonstration of a user registering, performing CRUD actions (e.g., adding an item), and seeing instant UI changes.
2.  **Database Architecture (2 Minutes)**: Share your Entity Relationship Diagram (ERD). Explain your table relationships, primary/foreign keys, and database constraints.
3.  **Backend & API Routing (3 Minutes)**: Walk through your Javalin controller routes. Explain how you mapped database operations (CRUD) to specific HTTP methods and status codes. Highlight your global exception mapping logic.
4.  **Frontend & JavaScript Integration (2 Minutes)**: Show how your frontend communicates with the backend. Walk through a JS snippet showing your use of `fetch()` to POST JSON data or display database tables.
5.  **Lessons Learned & Teamwork (1 Minute)**: Summarize key blockers your team encountered and how you solved them (e.g., configuring CORS, mapping Jackson objects, or resolving Git merge conflicts).

### Q&A Preparation Tips
During the 5-minute Q&A block, instructors and peers will ask technical questions. Be prepared to answer:
-   "Why did you choose a relative database structure instead of storing data in a single flat table?"
-   "How does your backend handle duplicate primary keys when creating a resource? What HTTP status code is returned?"
-   "If a client inputs invalid email formats in a form, where is that validation handled (frontend, backend, or both)?"

*Tip*: Do not be afraid to say "We did not implement that, but if we had more time we would mitigate it by...". Instructors value honesty and logical planning over rushed, buggy implementations.

### Peer Review Rubric
While other teams present, you will complete a peer review form evaluating their application. The rubric is based on three core areas:

-   **UI Usability**: Is the application layout responsive, styled clean with CSS, and easy to navigate?
-   **API Consistency**: Did the team use standard HTTP methods and return proper status codes?
-   **Technical Depth**: Did they implement robust database constraints and clean Java DAO separations?

---

## Code Example

Here is a visual checklist teams should review to verify their application is presentation-ready before they take the stage.

### Presentation Checklists
- [ ] Database is initialized with test data on the server.
- [ ] All team members know who shares their screen and who speaks on each section.
- [ ] The browser font is zoomed in (+1 or +2) to ensure the audience can read code and layout details.
- [ ] Terminal windows are cleared of noise and logs are active.
- [ ] A backup recording or screenshot of the working application is saved in case of live server issues (the "Demo Effect").

---

## Summary
-   **Presentations** are limited to 10 minutes, covering the user demo, database tables, Javalin routes, fetch integrations, and lessons learned.
-   Be prepared to explain **architectural decisions** (e.g., database relationships, validation boundaries, and exception mappings) during the Q&A block.
-   Active **peer evaluations** are completed during presentations to foster collaboration and feedback.
-   Prepare backup screenshots and double-check layouts to prevent live presentation blockers.

---

## Additional Resources
-   [Technical Presentations: A Guide for Developers](https://handbook.teaching.com/tech-presentations/)
-   [Designing Effective Database ERDs](https://www.lucidchart.com/pages/database-design)
-   [Javalin Error Resolution Guides](https://javalin.io/documentation#exception-mapping)
