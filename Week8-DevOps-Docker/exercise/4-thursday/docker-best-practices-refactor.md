# Lab Exercise: Dockerfile Code Auditing and Security Hardening

## Learning Objectives
- Identify security and performance violations in existing Dockerfiles.
- Configure secure, non-root user execution environments.
- Optimize build times and cache hits using `.dockerignore` files.
- Lock down base image tags to specific, minor versions.
- Implement active health monitoring using the `HEALTHCHECK` keyword.

---

## The Scenario
A developer has pushed a draft Dockerfile for the Spring Boot application. While the image builds and runs successfully, it violates several production security and performance guidelines: it runs as root, has no build optimizations, uses the unstable `latest` tag, and does not have active health monitoring.

In this exercise, you will audit the flawed Dockerfile, document its issues, and refactor it to meet production standards.

---

## The Insecure Dockerfile
Analyze this configuration file, which will be refactored:

```dockerfile
# INSECURE DOCKERFILE - DO NOT USE IN PRODUCTION
FROM openjdk:latest

# Copy everything from local workspace including test folders and secrets
COPY . /app
WORKDIR /app

# Run compile commands inside the container as root
RUN ./mvnw package -DskipTests

# Expose port
EXPOSE 8080

# Start application
ENTRYPOINT ["java", "-jar", "target/project3-api-1.0.0.jar"]
```

---

## Tasks

### Task 1: Document the Audit Findings
Create a report named `docker_audit_report.md` in the exercise folder. Document the security and performance issues in the insecure Dockerfile, including:
1.  **Tag locking risk**: Explain why `FROM openjdk:latest` is insecure.
2.  **Privilege escalation risk**: Explain the risk of running the application without the `USER` keyword.
3.  **Build context bloat**: Explain what files are copied by `COPY . /app` when `.dockerignore` is missing, and how this impacts build performance.
4.  **Process monitoring limitations**: Explain the risk of running a container without a `HEALTHCHECK` parameter.

---

### Task 2: Create a `.dockerignore` File
1.  Create a `.dockerignore` file in the folder containing your Dockerfile.
2.  Add rules to exclude heavy, unnecessary local folders from the build context:
    ```text
    .git
    .gitignore
    node_modules
    target
    *.log
    .idea
    .vscode
    ```

---

### Task 3: Refactor the Dockerfile
Refactor the configuration to meet production standards:
1.  **Lock the Base Tag**: Use a specific, minimal base image (e.g., `eclipse-temurin:17-jre-alpine`).
2.  **Separate Stages**: Use a multi-stage build to compile code in a build stage and copy only the JAR file to the runtime stage.
3.  **Run as a Non-Root User**: Create a dedicated group and user (e.g., `appuser`) and use the `USER` keyword.
4.  **Add active health checks**: Configure a `HEALTHCHECK` block that runs every 30 seconds to query the Spring Boot actuator health endpoint.

---

### Task 4: Build and Verify the Refactored Image
1.  Build the refactored image:
    ```bash
    docker build -t myusername/project3-hardened:1.0.0 .
    ```
2.  Run the container locally:
    ```bash
    docker run -d -p 8080:8080 --name audit-test myusername/project3-hardened:1.0.0
    ```
3.  Verify the container's health status in your terminal:
    ```bash
    docker ps --filter "name=audit-test"
    # Wait 30 seconds for the health check loop to run, then confirm status displays as '(healthy)'
    ```

---

## Definition of Done
- The file `docker_audit_report.md` is saved.
- A `.dockerignore` file is configured in the project root.
- The refactored `Dockerfile` implements multi-stage builds, non-root execution, minor version tags, and active health checks.
- Running `docker ps` displays the container status as `healthy`.
