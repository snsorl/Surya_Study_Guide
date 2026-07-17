# Lab Exercise: Containerizing a Spring Boot API with Multi-Stage Dockerfiles

## Learning Objectives
- Write a multi-stage Dockerfile for a Spring Boot Java application.
- Optimize image sizes by separating compilation dependencies from runtime JREs.
- Configure secure, non-root user execution inside container runtimes.
- Build, run, and test local container port routing mappings.
- Authenticate and push images to public registries (Docker Hub).

---

## The Scenario
Your operations team wants to containerize the Project 3 Spring Boot application to make deployments standard and portable. You must write a multi-stage `Dockerfile`, build it, run the container locally, verify port routing, and publish the image to your personal Docker Hub registry.

---

## Tasks

### Task 1: Write the Multi-Stage Dockerfile
1.  Navigate to your Project 3 backend repository root.
2.  Create a file named `Dockerfile` (no extension).
3.  Implement a two-stage build:
    *   **Stage 1 (`builder`)**: Use a Maven base image (e.g., `maven:3.9.2-eclipse-temurin-17-alpine`) to compile and package the application into a JAR file.
    *   **Stage 2 (Runtime)**: Use a minimal JRE base image (e.g., `eclipse-temurin:17-jre-alpine`). Copy the compiled JAR from Stage 1.
    *   **Security**: Create a secure, non-privileged user (e.g., `appuser`) and configure the container to run under that context.

---

### Task 2: Build the Container Image
1.  Open a terminal in the folder containing your Dockerfile.
2.  Build the image, tagging it with your Docker Hub username namespace:
    ```bash
    # Replace 'mydockerusername' with your actual Docker Hub username
    docker build -t mydockerusername/project3-api:1.0.0 .
    ```
3.  Verify the image is cached locally:
    ```bash
    docker images
    ```
    *Check the size of your image; a multi-stage JRE image should typically be under 250MB.*

---

### Task 3: Launch and Verify Local Port Routing
1.  Run the container in detached background mode, mapping host port `8080` to container port `8080`:
    ```bash
    docker run -d -p 8080:8080 --name api-runtime-test mydockerusername/project3-api:1.0.0
    ```
2.  Verify the container process is running:
    ```bash
    docker ps
    ```
3.  Open a browser or Postman and test the endpoint access:
    `http://localhost:8080/actuator/health`
4.  Stop and remove the container:
    ```bash
    docker stop api-runtime-test
    docker rm api-runtime-test
    ```

---

### Task 4: Push the Image to Docker Hub
1.  Log in to the Docker Hub registry from your terminal:
    ```bash
    docker login
    ```
2.  Upload your image to the registry:
    ```bash
    docker push mydockerusername/project3-api:1.0.0
    ```
3.  Open the Docker Hub website, log in, and verify that the `project3-api` repository and version `1.0.0` tag are visible.

---

## Definition of Done
- A multi-stage `Dockerfile` is saved in the project root.
- The command `docker build` runs successfully, creating a local image.
- `docker run` launches the container, routing port `8080` traffic to the host.
- The health actuator endpoint returns `{"status":"UP"}`.
- The image is pushed and visible in your Docker Hub account.

---

## Troubleshooting Tips
- **Spring Boot Port Conflict**: If the container fails to start because port `8080` is already in use, stop any local Spring Boot processes running on your host machine before running the container.
- **Access Denied on Push**: Ensure your image tag matches your Docker Hub username namespace exactly (e.g., `username/repository:tag`). If you make a mistake, re-tag the image using `docker tag` before pushing.
