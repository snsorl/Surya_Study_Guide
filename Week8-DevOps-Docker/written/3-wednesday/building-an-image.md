# Building an Image: Build Context, Tagging, and Multi-Stage Walkthroughs

## Learning Objectives
- Execute image builds using `docker build` command parameters.
- Define and configure the Docker Build Context.
- Configure custom Dockerfile paths (`-f`) and image tags (`-t`).
- Troubleshoot failed image builds by reading build output logs.
- Design a multi-stage Dockerfile that packages a Spring Boot API and an Angular frontend into a unified production container.

---

## Why This Matters
For simple applications, packaging your backend and frontend into separate container images is standard. However, this multi-container setup requires running and managing multiple servers, reverse proxies, and CORS configurations.

For low-resource environments (like a single AWS EC2 t3.micro instance) or staging environments, you can package both the Spring Boot API and the compiled Angular static files into a single, unified container image.

This guide walks you through this setup, demonstrating how to use **build contexts**, **multi-stage compilation**, and reverse proxies within a single container.

---

## The Concept

### 1. The Build Context
When you run `docker build -t app:1.0 .`, the dot (`.`) at the end specifies the **Build Context**:
- The build context is the directory path containing the files you want to copy into your image.
- When the build starts, the Docker Client packs all files in this directory and sends them to the Docker Daemon.
- **Performance Warning**: If you run the build command in a directory containing heavy, unrelated files (like a local database directory or test logs), sending the large build context to the daemon will be slow.
- **Solution**: Use a **`.dockerignore`** file to exclude heavy, unrelated directories (like `.git`, `node_modules`, or `target`) from the build context.

---

### 2. Custom Dockerfile Paths (`-f`) and Tagging (`-t`)
- **`docker build -t my-repo/my-app:v1.0 .`**: Builds an image from the default `Dockerfile` in the current folder, tagging it with the name `my-repo/my-app` and version `v1.0`.
- **`-f /path/to/Dockerfile`**: Tells Docker to use a custom Dockerfile path, which is useful when maintaining multiple Dockerfiles (e.g., `Dockerfile.dev` vs. `Dockerfile.prod`) in the same project directory.

---

### 3. Unified Deployment Architecture
In this guide's walkthrough, we build a multi-stage Dockerfile that:
1.  Compiles the Angular frontend to generate static HTML and JavaScript assets.
2.  Compiles the Spring Boot backend source files.
3.  Copies the compiled frontend files into the Spring Boot project's `/static` resource directory.
4.  Packages the Spring Boot app into a single executable JAR that serves both the API endpoints and the frontend files from port 8080.

```
       STAGE 1: ANGULAR BUILD                   STAGE 2: SPRING BUILD
+-----------------------------------+   +-----------------------------------+
|  FROM node:18 AS frontend-builder |   |  FROM maven:3.8-openjdk-17-slim   |
|  - Downloads node modules         |   |  - Copies Spring source files     |
|  - Runs 'npm run build'           |   |  - Copies compiled static files   |
|    (Produces static files)        |   |    from STAGE 1 to /static        |
+-----------------t-----------------+   |  - Runs 'mvn package' (app.jar)   |
                  |                     +-----------------t-----------------+
                  +===> Copies static                     |
                        files                             v
                                                STAGE 3: RUNTIME (JRE)
                                        +-----------------------------------+
                                        |  FROM openjdk:17-ea-17-slim       |
                                        |  - Copies app.jar from STAGE 2    |
                                        |  - Serves static files and API    |
                                        +-----------------------------------+
```

---

## Code Examples and Walkthroughs

### 1. Unified Multi-Stage Dockerfile (Spring Boot + Angular)
Here is the complete Dockerfile configuration for this unified build architecture:

```dockerfile
# =========================================================
# STAGE 1: Build the Angular frontend
# =========================================================
FROM node:18-alpine AS frontend-builder
WORKDIR /workspace/frontend

# Copy package configurations first to cache npm installs
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci

# Copy the frontend source code and compile
COPY frontend/ ./
RUN npm run build -- --configuration=production

# =========================================================
# STAGE 2: Build the Spring Boot backend
# =========================================================
FROM maven:3.9.2-eclipse-temurin-17-alpine AS backend-builder
WORKDIR /workspace/backend

# Copy the pom.xml config first to cache dependency downloads
COPY backend/pom.xml .
RUN mvn dependency:go-offline -B

# Copy the backend source files
COPY backend/src ./src

# Copy the compiled Angular static files from Stage 1
# into Spring Boot's resources directory to serve them automatically
COPY --from=frontend-builder /workspace/frontend/dist/my-angular-app/browser/ ./src/main/resources/static/

# Package the application into a single executable jar
RUN mvn package -DskipTests

# =========================================================
# STAGE 3: Runtime Execution environment
# =========================================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create a secure, non-privileged system user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the compiled JAR file containing both API and Frontend
COPY --from=backend-builder /workspace/backend/target/*.jar app.jar

# Document container port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### 2. Building the Unified Image via CLI
Run the build command from the root project directory (which contains both the `/frontend` and `/backend` folders):

```bash
# 1. Build the image, bypassing local layer caches to force fresh downloads
docker build \
  -f Dockerfile \
  -t my-registry-username/unified-app:1.0.0 \
  --no-cache \
  .

# 2. Verify the image built successfully and check its size
docker images my-registry-username/unified-app:1.0.0

# Expected Output shows the image size is minimal (typically under 250MB) 
# because node modules and Maven caches were discarded:
# REPOSITORY                         TAG       IMAGE ID       CREATED        SIZE
# my-registry-username/unified-app   1.0.0     b78fbc312a89   1 minute ago   210MB
```

---

## Summary
- The **Build Context** specifies the directory path containing files sent to the Docker Daemon during image creation.
- A **`.dockerignore`** file excludes heavy, unrelated files from the build context to optimize build performance.
- The **`-f`** flag specifies a custom Dockerfile path, and **`-t`** defines the repository name and version tag.
- **Multi-stage builds** can compile separate frontend and backend assets and package them into a single, unified container image.

---

## Additional Resources
- [Docker Documentation: Deep Dive into the Build Command](https://docs.docker.com/engine/reference/commandline/build/)
- [Configuring and using the .dockerignore file](https://docs.docker.com/engine/reference/builder/#dockerignore)
- [Deploying Spring Boot Applications as Static File Hosts](https://docs.spring.io/spring-boot/docs/current/reference/html/vivid-deployment.html)
