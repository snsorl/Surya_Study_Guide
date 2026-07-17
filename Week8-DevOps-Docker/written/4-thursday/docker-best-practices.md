# Docker Best Practices: Security, Image Optimization, and Production Guidelines

## Learning Objectives
- Design optimized, minimal container images by reducing layer count and using `.dockerignore`.
- Implement security best practices: running as a non-root user and configuring read-only filesystems.
- Compare file transfer commands: `COPY` vs. `ADD`.
- Manage secrets securely inside containerized runtimes.
- Configure container health monitoring for production orchestrators.

---

## Why This Matters
Building a Docker image that runs locally is easy, but running containers securely in production requires extra care. 

For example, if your application runs as the default `root` user, database credentials are hardcoded in the image layers, and the filesystem is writable, you create security risks: if an attacker compromises the application, they can access the host system.

Learning production best practices—including image optimization, non-root execution, read-only filesystems, and secure secrets management—is key to building secure, enterprise-grade container pipelines.

---

## The Concept

### 1. Image Optimization Guidelines

#### Minimize Image Layers
Each `RUN`, `COPY`, and `ADD` instruction in your Dockerfile creates a new filesystem layer. To reduce image sizes and build times:
- Combine related shell commands into a single `RUN` block using logical operators (`&&` and line continuations `\`).
- Clean up package manager caches (like running `rm -rf /var/lib/apt/lists/*` in the same step) to prevent temporary download files from bloating the image.

#### Use `.dockerignore`
Exclude build directories (like `target` or `node_modules`), logs, and local configurations from the build context. This speeds up builds and prevents sensitive local files from leaking into the image.

---

### 2. Container Security Best Practices

#### Run as a Non-Root User
By default, Docker container processes run as the privileged `root` user. To follow the **Principle of Least Privilege**, create a non-privileged system user in your Dockerfile and switch to it using `USER` before starting the application.

#### Use Specific Base Image Tags
Never use the `latest` tag in your Dockerfiles (e.g., `FROM openjdk:latest`). If a new version of the base image is published, it could introduce breaking changes or security bugs to your application. Always lock down specific, minor version tags (e.g., `openjdk:17-slim`).

#### Read-Only Filesystems (`--read-only`)
Run containers with a read-only root filesystem. This prevents attackers from modifying application code, writing malicious scripts, or downloading malware inside the container. If the application needs to write temporary files, mount temporary memory volumes (`tmpfs`) to specific folders (like `/tmp` or `/var/log`).

---

### 3. Secure Secrets Management
- **Never** bake API keys, passwords, or database credentials directly into your Dockerfile instructions or image layers (even if you delete them in a later step, they remain readable in the image history).
- Pass secrets dynamically at runtime using environment variables (`-e` or Docker Compose secrets) or resolve them from managed configuration services (like AWS Secrets Manager) at runtime.

---

## Code Examples and Walkthroughs

### 1. Insecure vs. Secure Dockerfile Comparison

#### Insecure Dockerfile
This configuration contains several production security risks:

```dockerfile
# INSECURE DOCKERFILE
FROM openjdk:latest # Risk: Unlocked base tag pulls breaking changes automatically

COPY . /app # Risk: Lacks .dockerignore, copying local secrets and node_modules
WORKDIR /app

RUN apt-get update # Risk: Creates unnecessary intermediate layers without cleaning cache
RUN apt-get install -y wget

# Risk: Runs process as root
ENTRYPOINT ["java", "-jar", "target/app.jar"]
```

#### Remediation (Production Secure Dockerfile)
This refactored Dockerfile applies optimization and security best practices:

```dockerfile
# SECURE PRODUCTION DOCKERFILE
FROM eclipse-temurin:17-jre-alpine

# Set configuration variables
ENV APP_HOME=/opt/application
WORKDIR ${APP_HOME}

# 1. Create a secure, non-privileged system user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 2. Copy the compiled JAR file, assigning file ownership to the secure user
# (We assume Stage 1 or the CI server compiled the JAR before build)
COPY --chown=appuser:appgroup target/app.jar app.jar

# 3. Switch execution context to the secure non-root user
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 2. Launching a Secure Container via CLI
This command runs the application container with restricted resources, automatic restart rules, and a secure **read-only filesystem**:

```bash
# Run the application with a read-only root filesystem
# (We mount a temporary RAM volume to /tmp so the Java runtime can write temporary files)
docker run -d \
  --name secure-api-container \
  -p 8080:8080 \
  --read-only \
  --tmpfs /tmp \
  --restart on-failure:5 \
  --memory "512m" \
  mydockerusername/project3-api:1.2.0
```

---

## Summary
- **Optimize Image Size** by combining commands inside `RUN` blocks and using `.dockerignore` files.
- **Secure Runtime Environments** by locking base image tags, running as a **non-root user**, and mounting **read-only root filesystems**.
- **Never bake credentials** into image layers; resolve secrets dynamically at runtime using environment variables or secret vaults.

---

## Additional Resources
- [Docker Documentation: Production Deployment Best Practices](https://docs.docker.com/develop/security-best-practices/)
- [OWASP: Container Security Standards Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Docker_Security_Cheat_Sheet.html)
- [Managing Application Secrets securely in Container Environments](https://docs.docker.com/engine/swarm/secrets/)
