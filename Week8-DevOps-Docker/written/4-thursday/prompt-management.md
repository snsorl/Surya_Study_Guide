# Prompt Management: Reusable Templates for Docker and CI/CD Generation

## Learning Objectives
- Design structured, reusable AI prompts to generate production-grade Dockerfiles.
- Establish parameter patterns to configure multi-container configurations.
- Audit AI-generated code for security risks (like root execution or hardcoded secrets).
- Build a shared team library of archived deployment prompts.

---

## Why This Matters
Using AI assistants to write deployment code (like Dockerfiles or docker-compose files) can speed up development. However, generic prompts often yield poor results: the AI might use outdated base images, run processes as root, or fail to configure security groups correctly.

To generate reliable, secure deployment configurations, you must write structured prompts. Learning how to create reusable prompt templates and verify the AI's output is key to integrating AI assistants into your DevOps workflows safely.

---

## The Concept

### 1. Key Components of a DevOps Prompt Template
To generate secure, functional Docker configurations, your prompts should include:
- **Environment Context**: Specify the application runtime (e.g., Java Corretto 17, Node 18, PostgreSQL 15).
- **Security Constraints**: Instruct the AI to run processes as a non-root user and to avoid hardcoded credentials.
- **Performance Optimization**: Direct the AI to use minimal base images (like Alpine Linux) and configure multi-stage builds.
- **Port and Volume Documentation**: Specify the ports to expose and directories to mount for persistent data.

---

### 2. Auditing and Verification Checklist
Always review AI-generated configurations against this checklist before applying them:
- **Base Image Audit**: Ensure the image points to a specific, trusted version tag, not `latest`.
- **Security Audit**: Verify the `USER` keyword is used to strip root privileges.
- **Credentials Check**: Ensure secret keys are resolved from environment variables, not hardcoded in files.
- **Data Persistence**: Confirm that database directories are mapped to persistent volumes.

---

## Code Examples and Walkthroughs

### 1. Reusable Prompt Template for Spring Boot Dockerfiles
Use this template to generate secure, production-ready Dockerfiles for Spring Boot applications:

```text
[CONTEXT]
Generate a secure, production-grade Dockerfile for a Spring Boot REST API application.

[ENVIRONMENT]
- Runtime: Java 17
- Base Image: eclipse-temurin:17-jre-alpine (or equivalent minimal secure JRE image)

[SECURITY REQUIREMENTS]
1. Do NOT run the application process as root. Create a dedicated system user/group named 'appuser'/'appgroup' and execute using that context.
2. Ensure the file ownership of the copied JAR is assigned to the non-root user.
3. Do not hardcode database URLs or passwords. Let the application resolve them from environment variables.

[PORT AND STORAGE]
- Expose Port: 8080
- Mount Volume: Map /tmp to a persistent volume.

[FORMAT]
Provide the complete Dockerfile with inline comments explaining each step.
```

---

### 2. Reusable Prompt Template for Multi-Container Compose Files
Use this template to generate a multi-container Docker Compose configuration for a web application stack:

```text
[CONTEXT]
Generate a docker-compose.yml file for a three-tier web application stack.

[SERVICES]
1. Frontend: Nginx proxy container serving static Angular files on port 80.
2. Backend: Java Spring Boot API container listening on port 8080.
3. Database: PostgreSQL container listening on port 5432.

[INTEGRATION AND HEALCHECKS]
- The Spring Boot container must wait for the PostgreSQL container to pass its health check before starting (use depends_on with service_healthy condition).
- Configure a persistent named volume for PostgreSQL data storage.

[SECURITY]
- Route all database passwords using environment variables.
- Keep the database container on a private internal network, exposing only the Nginx port to the host system.
```

---

## Summary
- **DevOps Prompt Templates** must specify runtime environments, security constraints, and performance optimization rules.
- Review AI-generated files to ensure they use **trusted base images**, run as **non-root users**, and avoid **hardcoded secrets**.
- Build and archive a **reusable prompt library** to help team members generate consistent, secure deployment configurations.

---

## Additional Resources
- [Docker Security Team Best Practices for Container Isolation](https://docs.docker.com/engine/security/)
- [Anthropic: Prompt Library and Enterprise Engineering Guide](https://docs.anthropic.com/claude/prompt-library)
- [OWASP: Container Security and Scanning Best Practices](https://owasp.org/www-project-docker-top-10/)
