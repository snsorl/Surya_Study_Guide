# Docker CLI Cheatsheet: Commands, Categories, and Quick References

## Learning Objectives
- Quickly reference common Docker CLI commands categorized by resource type.
- Manage Docker containers, images, persistent volumes, and virtual networks.
- Run multi-container setups using core Docker Compose commands.
- Perform system-wide cleanups to free up disk space.

---

## Why This Matters
As you work with containerized architectures, you will interact with the Docker CLI daily. Having a clean, categorized reference sheet for common commands—images, containers, volumes, networks, and compose—helps you work more efficiently and troubleshoot issues faster.

---

## The Cheat Sheet

### 1. Managing Images
Commands to build, download, and clean up Docker images:

```bash
# Build an image from a Dockerfile in the current directory
docker build -t <image-name>:<tag> .

# Build an image ignoring cached layers (force fresh install)
docker build --no-cache -t <image-name>:<tag> .

# Pull an image from Docker Hub
docker pull <image-name>:<tag>

# Push an image to a registry (requires docker login first)
docker push <username>/<image-name>:<tag>

# List all local images
docker images

# Delete a local image
docker rmi <image-id-or-name>

# Delete untagged (dangling) images
docker image prune
```

---

### 2. Running and Managing Containers
Commands to run, monitor, and troubleshoot containers:

```bash
# Run a container in the background (detached mode) with port mapping
docker run -d -p <host-port>:<container-port> --name <container-name> <image-name>

# Run a container with an interactive shell (e.g. for testing commands)
docker run -it --name <container-name> <image-name> sh

# List running containers
docker ps

# List all containers (running and stopped)
docker ps -a

# Stop a running container gracefully (SIGTERM)
docker stop <container-id-or-name>

# Start a stopped container
docker start <container-id-or-name>

# Delete a stopped container
docker rm <container-id-or-name>

# Force-delete a running container
docker rm -f <container-id-or-name>
```

---

### 3. Debugging and Diagnostics
Commands to inspect configurations and stream logs:

```bash
# Stream container logs in real-time
docker logs -f <container-name>

# View the last 50 log lines
docker logs --tail 50 <container-name>

# Execute an interactive shell inside a running container
docker exec -it <container-name> sh

# Inspect a container's detailed configuration settings (IP address, volumes, etc.)
docker inspect <container-name>

# Check real-time resource utilization (CPU, memory, network)
docker stats
```

---

### 4. Volumes and Networking
Commands to configure persistent storage and virtual networks:

```bash
# Create a persistent named volume
docker volume create <volume-name>

# List all volumes
docker volume ls

# Delete a volume
docker volume rm <volume-name>

# Create a virtual bridge network (enabling container communication)
docker network create <network-name>

# List all networks
docker network ls

# Connect a container to an existing network
docker network connect <network-name> <container-name>
```

---

### 5. Multi-Container Orchestration (Docker Compose)
Commands to manage multi-container applications:

```bash
# Launch all services in background (detached mode) and build if missing
docker compose up -d --build

# View logs for all running compose services
docker compose logs -f

# Check status of compose services
docker compose ps

# Stop and delete all containers, networks, and volumes defined in compose
docker compose down -v
```

---

### 6. System Cleanup
Commands to free up disk space by removing unused Docker assets:

```bash
# Check current Docker disk space usage
docker system df

# Remove all stopped containers, unused networks, and dangling images
docker system prune

# Comprehensive cleanup: delete all unused images, containers, networks, and volumes
docker system prune -a --volumes
```

---

## Summary
- This cheatsheet provides a quick reference for common Docker commands.
- Use **`docker system prune -a --volumes`** to clear unused container assets and free up disk space.

---

## Additional Resources
- [Official Docker CLI Reference Guide Index](https://docs.docker.com/engine/reference/commandline/cli/)
- [Docker Compose Command Line Cheatsheet](https://docs.docker.com/compose/reference/)
- [Best Practices for Pruning and Cleaning Host Environments](https://docs.docker.com/config/pruning/)
