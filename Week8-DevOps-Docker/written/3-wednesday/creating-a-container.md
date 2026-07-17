# Creating a Container: Port Mapping, Environment Variables, and Resource Constraints

## Learning Objectives
- Write and execute advanced `docker run` commands with custom parameters.
- Configure Port Mapping (`-p`) to expose container ports to the host system.
- Inject runtime settings using Environment Variables (`-e`).
- Mount directories using Volume Mounts (`-v`) to persist container data.
- Configure container Restart Policies (`--restart`) and allocate CPU/Memory constraints.

---

## Why This Matters
Running a database container (like PostgreSQL) or a backend application (like Spring Boot) with simple commands like `docker run postgres` will not work for production setups:
- The database storage will be ephemeral, and all your records will be lost when the container stops.
- Network ports will remain closed, blocking your Spring Boot API from connecting.
- The database process can consume all host memory, crashing the server.

Learning how to write structured `docker run` commands—including port mapping, environment injection, volume mounts, restart policies, and resource limits—is key to running containers reliably.

---

## The Concept

### 1. The Anatomy of `docker run`
The `docker run` command combines two steps: creating a container instance and starting its processes. You pass flags to configure the container's runtime environment before it boots:

```
                  DOCKER RUN COMMAND ANATOMY
  docker run -d -p 8080:8080 -e PROFILE=prod -v app-data:/data my-app:1.0
  |          |  |            |               |               |
  Command    |  Port Map     Env Variable    Volume Mount    Target Image
             |
             Background Mode (Detached)
```

---

### 2. Core Configurations Explained

#### Port Mapping (`-p host_port:container_port`)
Maps a network port on the host machine to a port inside the container. E.g., `-p 8080:8080` routes incoming traffic on the host's port `8080` to port `8080` inside the container where Spring Boot is listening.

#### Environment Variables (`-e KEY=VALUE`)
Injects runtime configuration variables directly into the container process, making it easy to change settings without rebuilding the image.

#### Volume Mounts (`-v host_path:container_path`)
Mounts a host directory or named Docker volume to a folder inside the container. This bypasses the temporary container filesystem layer, persisting data (like database storage folders) even if the container is deleted.

#### Restart Policies (`--restart`)
Defines the container's recovery behavior when the process exits or the host restarts:
- `no`: Do not restart automatically (default).
- `on-failure`: Restart only if the process exits with a non-zero error code.
- `always`: Restart the container automatically if it stops. If the host daemon restarts, the container starts up again.

#### Resource Limits
Restricts the container's CPU and memory footprint on the host system to prevent a single container from consuming all system resources:
- `-m` or `--memory`: Sets the maximum RAM memory limit (e.g., `--memory="512m"`).
- `--cpus`: Sets the maximum CPU core allocation (e.g., `--cpus="1.5"`).

---

## Code Examples and Walkthroughs

### 1. Running a Secure, Persistent PostgreSQL Instance
This command launches a production-ready PostgreSQL container with configured credentials, host port access, data persistence, automatic restart policies, and resource limits:

```bash
# 1. Create a named persistent volume for database data
docker volume create pg-data-volume

# 2. Run the PostgreSQL container with custom configurations
docker run -d \
  --name project3-postgres \
  -p 5432:5432 \
  -e POSTGRES_USER=project3_admin \
  -e POSTGRES_PASSWORD=SecurePassword2026! \
  -e POSTGRES_DB=project3_db \
  -v pg-data-volume:/var/lib/postgresql/data \
  --restart always \
  --memory "1g" \
  --cpus "1.0" \
  postgres:15-alpine

# Verification:
# Run the ps command to verify the container is active and check the port mapping:
docker ps --filter "name=project3-postgres"
```

---

### 2. Testing Connection Routing
Once the database container is running, verify you can connect to it on host port 5432:

```bash
# Test the database connection port using telnet or nc (netcat) from your host
nc -zv localhost 5432

# Expected Output:
# Connection to localhost (127.0.0.1) 5432 port [tcp/postgresql] succeeded!
```

---

## Summary
- **`docker run`** configures and launches a container from an image template.
- **Port Mapping (`-p`)** routes network traffic from the host to the container.
- **Environment variables (`-e`)** inject configuration settings dynamically at boot.
- **Volume mounts (`-v`)** map persistent storage directories to prevent data loss.
- **Restart policies (`--restart`)** and **resource limits** manage container recovery and prevent CPU/RAM exhaustion.

---

## Additional Resources
- [Docker Reference Guide: docker run CLI Options](https://docs.docker.com/engine/reference/run/)
- [Understanding Docker Networking and Port Binding Patterns](https://docs.docker.com/config/containers/container-networking/)
- [Docker Documentation: Restricting Container Resource Consumption](https://docs.docker.com/config/containers/resource_constraints/)
