# Project 2 Presentation Guide

## Learning Objectives
- Prepare for the Project 2 presentation session.
- Identify the core demonstration requirements for your frontend-backend integration.
- Structure your architecture walkthrough presentation.
- Use the peer review rubric to evaluate peer presentations.

---

## Why This Matters
Building a local project is a great learning experience, but communicating your technical design, demonstrating a working integration, and fielding questions is what prepares you for professional software engineering roles. The Project 2 presentation replicates industry sprint reviews. You will demonstrate a working frontend communicating asynchronously with your Spring Boot backend, walk through your codebase architecture, and receive feedback from peers and instructors.

---

## The Concept

### 1. Core Demonstration Requirements
A successful presentation must demonstrate the end-to-end integration of your stack:
- **Running Frontend**: A user interface rendered dynamically in the browser, showing styled inputs and lists.
- **Asynchronous Fetch Calls**: Actionable buttons or forms that trigger asynchronous network calls (using the Fetch API with `async`/`await`) to retrieve, add, or delete data.
- **Spring Boot Backend**: A backend service that processes incoming requests, communicates with the PostgreSQL database, and returns standard JSON payloads.
- **Error Handling**: The frontend must handle connection failures gracefully (e.g. showing alert messages if the backend server is down, rather than freezing or crashing).

### 2. Architecture Walkthrough Structure
You have **10 minutes** to present. Structure your time as follows:
- **Sprint Demo (4 minutes)**: Demonstrate user flows in the browser (adding records, listing items, triggering validation errors, and verifying changes in the database).
- **Code Walkthrough (4 minutes)**:
  - Explain how you structured DOM element selectors and event listeners.
  - Walk through your asynchronous fetch wrapper functions.
  - Show how you structured the Spring Boot controller, transactional service, and database repository.
- **Q&A Session (2 minutes)**: Answer technical questions from peers and instructors.

### 3. Peer Review Rubric Focus Areas
During peer presentations, you will fill out a review form focusing on:
- **Stack Integration**: Did the frontend communicate with the backend API, or was data mocked locally?
- **Data Integrity and Validation**: Does the frontend validate input fields (like empty strings or negative numbers), and does the backend enforce validation?
- **Async Code Quality**: Were async/await promises resolved cleanly, or did you detect nested callbacks?
- **User Experience (UX)**: Did the interface remain responsive during network calls, or did it freeze?

---

## Summary
- Project 2 presentations demonstrate a working frontend communicating with a Spring Boot database backend via the **Fetch API**.
- Limit your presentation to **10 minutes**, balancing live demo flows and code structure walkthroughs.
- Evaluate peer projects on **stack integration, validation, async code quality, and error handling**.

---

## Additional Resources
- [Google Search: How to present code to non-technical teams](https://www.google.com/search?q=how+to+present+code+to+non-technical+teams)
- [HBR: How to give a great presentation](https://hbr.org/2013/06/how-to-give-a-killer-presentation)
