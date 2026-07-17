# Project 3: Capstone Presentations Guide

## Learning Objectives
- Understand the technical and operational requirements for the Project 3 Capstone presentation.
- Structure an effective architecture walkthrough and demo for a full-stack containerized application.
- Align with the grading rubric across DevOps pipelines, Docker orchestration, back-end/front-end design, and database integration.
- Plan the timing and roles within the team to deliver a cohesive, professional presentation.

---

## Why This Matters
The Project 3 Capstone represents the culmination of your journey toward becoming a production-ready, polyglot software engineer. In a professional setting, writing working code is only half the battle; you must also be able to package, deploy, and explain your system's architecture to cross-functional stakeholders.

This presentation simulates a production release review. By demonstrating a fully Dockerized application that builds and deploys via a continuous integration and continuous deployment (CI/CD) pipeline, you prove that your application is not just functional on a local machine, but ready for the cloud. It ties together the Java Spring Boot backend, Angular frontend, database persistence, and the newly integrated Python data analysis/utility layer, demonstrating your capabilities across multiple technology stacks.

---

## Core Presentation Requirements

Teams will have a total of **15 minutes** for their presentation, strictly enforced to mimic real-world business time constraints. 

### 1. Timing Breakdown
*   **0:00 - 0:08 (8 Minutes) - Functional Product Demonstration:** A live walk-through of the application's user flows, highlighting edge-case handling and the seamless integration of all system components.
*   **0:08 - 0:12 (4 Minutes) - Architecture & DevOps Walkthrough:** A high-level explanation of the system architecture, Docker Compose orchestration, and the CI/CD pipeline.
*   **0:12 - 0:15 (3 Minutes) - Question & Answer Session:** Technical questions from instructors and peers regarding design decisions, security choices, and testing strategies.

---

## Technical Specifications & Rubric

Your project will be evaluated across five key technical and presentation categories. Each category is worth up to 20 points, for a maximum score of 100 points.

### 1. Functional Implementation (20 Points)
*   **Front-End Integration:** An interactive, responsive Angular user interface that communicates with the backend via RESTful APIs.
*   **Back-End Core:** A robust Spring Boot REST API implementing business logic, validation, and proper HTTP response codes.
*   **Python Component:** A dedicated Python utility (e.g., executing background data analysis, serving custom Flask endpoints, or compiling system statistics) integrated cleanly into the system.
*   **Database Persistence:** A PostgreSQL database storing application state with appropriate constraints, foreign keys, and normalized schemas.

### 2. Dockerization & Orchestration (20 Points)
*   **Multi-Stage Dockerfiles:** Optimizing Docker images using multi-stage builds to minimize image sizes (e.g., compiling Angular/Java in build containers, and deploying only the compiled artifacts in lightweight runtimes).
*   **Docker Compose Orchestration:** A single, well-structured `docker-compose.yml` file that spins up all services:
    - Angular client
    - Spring Boot server
    - PostgreSQL database (using volume mounts for data persistence)
    - Python background processor/API
*   **Network Isolation:** Services must communicate over custom Docker bridge networks, ensuring database ports are not exposed to the host machine unnecessarily.

### 3. CI/CD Pipeline (20 Points)
*   **Automated Builds and Tests:** A GitHub Actions workflow that triggers on every push/merge to the main branch, compiling code and executing all unit/integration tests for both Java and Angular.
*   **Image Deployment:** Upon successful testing, the pipeline must automatically build production Docker images and push them to a container registry (e.g., Docker Hub or AWS ECR).
*   **Configuration Management:** Using environment variables to inject sensitive data (like database credentials and API keys) at runtime rather than hardcoding them in source control.

### 4. Architectural Design & Code Quality (20 Points)
*   **Clean Code Principles:** Adhering to language-specific style guides (Java CamelCase, Python snake_case, TypeScript camelCase).
*   **System Decoupling:** Services should interact via well-defined REST API boundaries. No database sharing between microservices.
*   **Error Handling & Logging:** Graceful error handling in the UI, proper exception mappings in the REST API, and meaningful log outputs across all microservices.

### 5. Presentation Delivery & Collaboration (20 Points)
*   **Active Team Participation:** Every team member must present a portion of either the demo or the architecture walkthrough.
*   **Slide Quality and Structure:** A slide deck showing:
    - Team introduction and roles.
    - System architecture diagram (Mermaid or visual mockup).
    - Database Schema ER diagram.
    - CI/CD workflow path.
*   **Q&A Readiness:** Concise, technically accurate answers demonstrating deep understanding of your system's design and trade-offs.

---

## Architectural Walkthrough Expectations

During the 4-minute technical walkthrough, your team must explain how data flows through your system. Be prepared to present a diagram similar to the following:

```mermaid
graph TD
    Client[Angular Frontend] -->|HTTP Requests| Gateway[Spring Boot Backend]
    Gateway -->|Read/Write| DB[(PostgreSQL Database)]
    Gateway -->|Invoke Analysis| PyUtil[Python Service]
    PyUtil -->|Read/Analyze| DB
    
    subgraph CI/CD Pipeline (GitHub Actions)
        Git[Git Push] --> Test[Run Tests]
        Test --> Build[Build Docker Images]
        Build --> Push[Push to Docker Hub]
    end
```

### Key Areas to Highlight:
1.  **Environment Separation:** How does the frontend resolve the URL of the backend service dynamically in Docker? (e.g., using environment-specific endpoints).
2.  **Data Flow:** How does the Python service obtain its data? Does it read directly from the PostgreSQL instance, or does it receive payloads via REST endpoints from Spring Boot?
3.  **DevOps Lifecycle:** Walk through the GitHub Actions file, briefly explaining the jobs, steps, and secrets injection.

---

## Presentation Order & Timing Tips
- **Rehearse the transitions:** The transition of screen sharing and speakers is where most teams lose valuable time. Practice passing control seamlessly.
- **Prepare seed data:** Do not start your demo with a blank system. Pre-populate your database with realistic, rich sample data so the dashboard, graphs, and lists look complete and professional immediately.
- **Fail gracefully:** If a live feature fails during the presentation, do not spend 3 minutes trying to debug it. Simply explain what the feature does, state the expected behavior, and move to the next item.

---

## Additional Resources
- [Docker Documentation: Best Practices for Writing Dockerfiles](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
- [GitHub Actions Documentation: Building and Testing Java & Node.js Applications](https://docs.github.com/en/actions/automating-builds-and-tests)
- [Twelve-Factor App: Configuration Best Practices](https://12factor.net/config)
