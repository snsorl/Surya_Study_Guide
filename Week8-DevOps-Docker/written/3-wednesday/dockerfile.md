# Dockerfiles: Syntax, Layering, and Multi-Stage Builds

## Learning Objectives
- Write Dockerfiles using core keywords: `FROM`, `RUN`, `COPY`, `WORKDIR`, `ENV`, `EXPOSE`, `CMD`, and `ENTRYPOINT`.
- Differentiate between the actions of `CMD` and `ENTRYPOINT`.
- Explain how Docker leverages Layer Caching to optimize build performance.
- Design Multi-Stage Dockerfiles to produce minimal production images.

---

## Why This Matters
If you package your application into a container image by simply copying the entire project directory and running a global start script, the resulting image will be bloated. It will include compile-time source files, compiler tools, local testing databases, and temporary build caches.

This bloat results in large images (often exceeding 1GB) that are slow to transfer to container registries and present security risks by including unnecessary binaries.

Learning how to write optimized Dockerfiles—including ordering instructions to take advantage of **layer caching** and using **multi-stage builds**—is key to producing fast-building, minimal, and secure production images.

---

## The Concept

### 1. Core Dockerfile Keywords
A **Dockerfile** is a text document containing instructions to build a container image:
- **`FROM`**: Sets the base image (e.g., `openjdk:17-slim` or `node:18`). Every Dockerfile must start with `FROM`.
- **`WORKDIR`**: Sets the working directory for subsequent instructions (like `RUN` or `COPY`). If the directory does not exist, Docker creates it.
- **`COPY`**: Copies files from the host machine's build context into the container filesystem.
- **`ADD`**: Similar to `COPY`, but can download remote files from URLs and automatically unpack tar archives. *(`COPY` is preferred for general file transfers).*
- **`RUN`**: Executes commands inside a new filesystem layer during the image build process (e.g., `apt-get install` or `mvn dependency:go-offline`).
- **`ENV`**: Sets environment variables that persist inside the running container.
- **`EXPOSE`**: Documents the network port the container process listens on at runtime. It serves as documentation and does not map or publish ports automatically.

---

### 2. CMD vs. ENTRYPOINT
Both keywords define the process that runs when the container starts, but they behave differently:
- **`ENTRYPOINT`**: Defines the command that will always run when the container starts.
- **`CMD`**: Defines default arguments passed to the `ENTRYPOINT`. If no `ENTRYPOINT` is defined, `CMD` acts as the default shell command.
- **Overriding**: You can override `CMD` parameters easily from the command line (e.g., `docker run image-name arg1`). Overriding the `ENTRYPOINT` requires passing the explicit `--entrypoint` flag.

---

### 3. Layer Caching (Optimizing Builds)
Each instruction in a Dockerfile creates an immutable filesystem layer.
- When you run `docker build`, Docker checks if the files and commands in each step have changed since the last build.
- If no changes are detected, Docker uses the cached layer from the previous build instead of running the command again.
- **Ordering Matters**: As soon as a file changes during a `COPY` step or a command changes in a `RUN` step, the cache is invalidated for that step and all subsequent steps.
- **Best Practice**: Put instructions that change rarely (like installing packages or downloading dependency lists) at the top of the Dockerfile, and instructions that change frequently (like copying source code) at the bottom.

---

### 4. Multi-Stage Builds
Multi-stage builds use multiple `FROM` instructions in a single Dockerfile.
- You run compile steps (like Maven packages or Angular builds) in a heavy "build" stage.
- In the final production stage, you start with a minimal runtime base image and copy *only* the compiled assets (e.g., the `.jar` file) from the build stage.
- The heavy build tools, source code, and intermediate caches are discarded, leaving a minimal production image.

```
       STAGE 1: BUILD ENVIRONMENT (Heavy)
+-------------------------------------------------+
|  FROM maven:3.8-openjdk-17-slim AS builder      |
|  - Copies source code                           |
|  - Downloads build dependencies                 |
|  - Runs 'mvn package' (Produces app.jar)        |
+------------------------t------------------------+
                         | Copies ONLY compiled jar
                         v
       STAGE 2: RUNTIME ENVIRONMENT (Minimal)
+-------------------------------------------------+
|  FROM openjdk:17-ea-17-slim                     |
|  - Copies app.jar from builder                  |
|  - Sets WORKDIR & entrypoint                    |
|  - Discards compiler tools and source files     |
+-------------------------------------------------+
```

---

## Code Examples and Walkthroughs

### 1. Multi-Stage Dockerfile for a Spring Boot Application
This optimized Dockerfile uses two stages to compile and run a Spring Boot application. It copies the dependency list first to take advantage of layer caching:

```dockerfile
# ==========================================
# STAGE 1: Build the application jar
# ==========================================
FROM maven:3.9.2-eclipse-temurin-17-alpine AS builder

# Set the working directory inside the build container
WORKDIR /app

# Copy the pom.xml config first to cache dependency downloads
COPY pom.xml .

# Download dependencies offline (highly cached step)
RUN mvn dependency:go-offline -B

# Copy the actual application source code
COPY src ./src

# Compile and package the executable JAR file
RUN mvn package -DskipTests

# ==========================================
# STAGE 2: Lightweight JRE Runtime environment
# ==========================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a secure, non-privileged system user to run the application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the compiled JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Document container port
EXPOSE 8080

# Execute the application jar using the secure exec form
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Summary
- A **Dockerfile** compiles instructions into layered, immutable container images.
- **Layer caching** uses cached results for unchanged instructions. Order your files to place rarely changing files at the top.
- **`ENTRYPOINT`** sets the default executable, and **`CMD`** sets the default arguments.
- **Multi-stage builds** use multiple stages to compile code in a build environment and copy only the compiled assets into a minimal runtime image.

---

## Additional Resources
- [Docker Documentation: Best Practices for Writing Dockerfiles](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
- [Understanding Docker CMD vs ENTRYPOINT in Detail](https://docs.docker.com/engine/reference/builder/#cmd)
- [Docker Multi-Stage Build Reference Manual](https://docs.docker.com/build/building/multi-stage/)
