# Dockerfile Keywords: Reference and Usage Guide

## Learning Objectives
- Define and apply advanced Dockerfile keywords.
- Compare similar commands: `CMD` vs. `ENTRYPOINT` and `COPY` vs. `ADD`.
- Contrast build-time variables (`ARG`) with runtime variables (`ENV`).
- Implement container health monitoring using the `HEALTHCHECK` keyword.
- Configure security permissions using the `USER` keyword.

---

## Why This Matters
Writing a basic Dockerfile is straightforward, but running containers in production requires security, performance, and monitoring configurations.

For example, running a container process as the default `root` user creates security risks: if an attacker compromises the application, they gain root access to the host. Similarly, launching containers without health monitoring makes it difficult for orchestrators (like ECS) to detect locked or unresponsive processes.

Learning advanced Dockerfile keywords—including user permissions, build arguments, health monitoring, and layer execution triggers—is key to building secure, production-grade container images.

---

## The Concept

### 1. Complete Keyword Directory

- **`FROM`**: Sets the base image (must be first).
- **`WORKDIR`**: Sets the working directory for subsequent instructions.
- **`RUN`**: Executes shell commands during the build phase (creates a new layer).
- **`COPY`**: Copies local files from the host to the container.
- **`ADD`**: Copies files, but can pull remote URLs and unpack tar archives. *(`COPY` is preferred unless archive extraction is required).*
- **`ENV`**: Sets runtime environment variables.
- **`ARG`**: Defines build-time variables passed during `docker build` (not available in the running container).
- **`EXPOSE`**: Documents the container's network ports.
- **`VOLUME`**: Creates a mount point to link to host storage for persistent data.
- **`USER`**: Sets the OS username or UID to run subsequent commands and start the container process (essential for stripping root privileges).
- **`LABEL`**: Adds metadata key-value pairs to the image (e.g., version, author).
- **`HEALTHCHECK`**: Tells Docker how to test the container process to verify it is active and responding (not just running in memory).
- **`ONBUILD`**: Registers trigger instructions that run automatically when this image is used as a base image for *another* Dockerfile.
- **`STOPSIGNAL`**: Sets the system signal sent to the container to stop it gracefully (defaults to `SIGTERM`).

---

### 2. Deep Dives and Comparisons

#### Build-Time `ARG` vs. Runtime `ENV`
- **`ARG`**: Used to pass variables during the build process (e.g., specifying Java compiler versions). They are completely stripped from the final image and cannot be read by the running container.
- **`ENV`**: Injected into the container's environment, persisting at runtime.

#### Secure Execution: The `USER` Keyword
By default, Docker container processes run as the privileged `root` user. To follow the **Principle of Least Privilege**, create a non-privileged system user in your Dockerfile and switch to it using `USER` before starting the application.

#### Health Checks (`HEALTHCHECK`)
A health check command runs periodically inside the container. If the command returns exit code `0`, the container is marked as `healthy`. If it returns `1`, the container is marked as `unhealthy`, prompting orchestrators to restart it.

---

## Code Examples and Walkthroughs

### 1. Robust Production-Grade Dockerfile Reference
This example combines advanced keywords, non-root user execution, build arguments, and active health monitoring for a Java application:

```dockerfile
# 1. Base Image definition with build argument versioning
ARG BASE_VERSION=17-alpine
FROM eclipse-temurin:${BASE_VERSION}

# Add metadata labels
LABEL org.opencontainers.image.authors="devops-team@company.com"
LABEL org.opencontainers.image.version="1.0"

# Define build arguments and default values
ARG APP_DIR=/opt/application
ARG JAR_FILE=target/app.jar

# Configure runtime environment variables
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75.0"
ENV PORT=8080

WORKDIR ${APP_DIR}

# Create a secure, non-root user and group
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the build artifact, assigning file ownership to the secure user
COPY --chown=appuser:appgroup ${JAR_FILE} app.jar

# Mount a persistent volume point for application file uploads
VOLUME ["/var/log/app", "/tmp"]

# Switch execution context to the secure non-root user
USER appuser

# Document service port
EXPOSE ${PORT}

# Active container health check: query the actuator health endpoint every 30 seconds
HEALTHCHECK --interval=30s --timeout=5s --start-period=15s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:${PORT}/actuator/health || exit 1

# Start execution command
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
```

---

## Summary
- Core keywords like **`FROM`**, **`RUN`**, and **`COPY`** form the base structure of images.
- Use the **`USER`** keyword to strip root privileges and run processes under a secure, non-privileged user account.
- **`ARG`** defines variables used only during the build phase; **`ENV`** variables persist inside the running container.
- Configure **`HEALTHCHECK`** parameters to let Docker and orchestrators monitor application health dynamically.

---

## Additional Resources
- [Docker Reference Manual: Complete Dockerfile Instruction Directory](https://docs.docker.com/engine/reference/builder/)
- [Docker Security: Running Containers as a Non-Root User](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/#user)
- [How to configure container Health Checks in production](https://docs.docker.com/engine/reference/builder/#healthcheck)
