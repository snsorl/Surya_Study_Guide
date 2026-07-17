# Docker CLI Commands: Complete Quick Reference and Usage Guide

## Learning Objectives
- Execute core Docker CLI commands to manage images and containers.
- Apply command-line flags to configure port forwarding, run modes, and resource limits.
- Authenticate with container registries and manage remote image pushes.
- Troubleshoot container issues using CLI diagnostic tools.

---

## Why This Matters
As you work with containerized architectures, you will interact with the Docker CLI daily. From building images locally to pulling registries, managing containers, and troubleshooting logs on remote cloud servers, the CLI is your primary interface.

Using the GUI dashboard is fine for learning, but it cannot be used on remote Linux servers or inside CI pipeline automation scripts. Having a comprehensive, categorized CLI cheatsheet with examples is key to executing DevOps workflows efficiently.

---

## The Concept

### 1. Categorized Command Summary

#### 1. Image Creation and Builds
- **`docker build`**: Compiles a Dockerfile into an image.
- **`docker tag`**: Creates a human-readable alias (tag) for an Image ID.
- **`docker rmi`**: Deletes local images from the host cache.

#### 2. Container Execution and Lifecycle
- **`docker run`**: Creates and starts a container from an image template.
- **`docker ps`**: Lists running containers (use `-a` to show all).
- **`docker stop`**: Stops a running container gracefully.
- **`docker start`**: Restarts a stopped container.
- **`docker rm`**: Deletes stopped containers from the host.

#### 3. Registry Operations
- **`docker login`**: Authenticates with a container registry.
- **`docker pull`**: Downloads an image from a registry.
- **`docker push`**: Uploads an image to a registry.
- **`docker logout`**: Clears local authentication credentials.

#### 4. Diagnosis and Troubleshooting
- **`docker logs`**: Retrieves container console outputs.
- **`docker exec`**: Executes shell commands inside a running container.
- **`docker inspect`**: Returns detailed configuration JSON metadata.
- **`docker stats`**: Displays real-time CPU, memory, and network utilization.

---

## Code Examples and Walkthroughs

### 1. Hands-On CLI Execution Lifecycle
This workflow demonstrates typical CLI commands used to pull, run, debug, and clean up a container:

```bash
# 1. Authenticate with your container registry
docker login --username "myusername"

# 2. Pull a base image from Docker Hub
docker pull alpine:latest

# 3. Tag the image for a private repository namespace
docker tag alpine:latest myusername/custom-alpine:v1.0

# 4. Push the tagged image to your repository
docker push myusername/custom-alpine:v1.0

# 5. Run the container in detached mode with an active sleep process
docker run -d --name alpine-diag myusername/custom-alpine:v1.0 sleep 1000

# 6. Stream and follow container logs
docker logs -f alpine-diag
# (Press Ctrl+C to exit log follow)

# 7. Check container resource consumption
docker stats --no-stream alpine-diag

# 8. Execute an interactive diagnostics shell inside the container
docker exec -it alpine-diag sh
# $ hostname
# $ exit

# 9. Clean up: stop and remove the container, then delete the local image
docker stop alpine-diag
docker rm alpine-diag
docker rmi myusername/custom-alpine:v1.0
```

---

## Command Reference Summary Table

| Command | Common Flags | Example Usage | Description |
| :--- | :--- | :--- | :--- |
| **`build`** | `-t` (tag), `-f` (file), `--no-cache` | `docker build -t app:1.0 .` | Builds an image from a Dockerfile. |
| **`run`** | `-d` (detach), `-p` (port), `-v` (volume), `-e` (env) | `docker run -d -p 80:80 nginx` | Creates and starts a container. |
| **`ps`** | `-a` (all), `-q` (numeric IDs only) | `docker ps -a` | Lists containers. |
| **`exec`** | `-i` (interactive), `-t` (tty) | `docker exec -it my-app sh` | Runs commands inside a container. |
| **`logs`** | `-f` (follow), `--tail` (line limit) | `docker logs -f --tail 50 my-app` | Retrieves container logs. |
| **`inspect`** | `--format` (Go template filter) | `docker inspect my-app` | Returns detailed metadata. |

---

## Summary
- The **Docker CLI** is divided into commands for managing images, running containers, registry operations, and diagnostics.
- Commands like **`docker run`** use configuration flags to manage ports (`-p`), environments (`-e`), and volumes (`-v`).
- Use **`docker exec`** and **`docker logs`** to troubleshoot and debug running containers.
- Always prune and clean up stopped containers and unused images to free up storage space.

---

## Additional Resources
- [Docker CLI Command Line Reference Index](https://docs.docker.com/engine/reference/commandline/cli/)
- [Docker Run Reference and Flag Configurations](https://docs.docker.com/engine/reference/run/)
- [Best Practices for Keeping Your Local Docker Workspace Clean](https://docs.docker.com/config/pruning/)
