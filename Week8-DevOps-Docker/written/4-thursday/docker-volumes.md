# Docker Volumes: Named Volumes, Bind Mounts, and Data Persistence

## Learning Objectives
- Compare Docker storage mounting options: Named Volumes, Bind Mounts, and `tmpfs` mounts.
- Manage Docker volumes using CLI commands (`create`, `ls`, `inspect`, `rm`, `prune`).
- Explain the lifecycle of container data and how volumes survive container resets.
- Configure secure, read-only volume mounts to protect configurations.

---

## Why This Matters
Docker containers run as isolated processes with a temporary (ephemeral) filesystem layer. Any file changes, database additions, or upload files created inside a container are saved in this temporary layer.

If the container crashes, is stopped and recreated, or is updated to a new code version, **this temporary layer is deleted, and all your data is lost permanently.**

To prevent this data loss, you must use **Docker Volumes**. Volumes map directories inside the container to persistent storage on the host system, keeping databases (like PostgreSQL) safe during container updates.

---

## The Concept

### 1. Storage Mount Types Compared

```
                DOCKER STORAGE MOUNT OPTIONS
  +-----------------------------------------------------------+
  | NAMED VOLUMES: Managed by Docker. Stored in /var/lib/docker|
  | Best for databases and persistent application data.       |
  +-----------------------------------------------------------+
  | BIND MOUNTS: Mapped to a specific folder on the host disk.|
  | Best for local development (live-reloading source code).  |
  +-----------------------------------------------------------+
  | TMPFS MOUNTS: Stored in the host's RAM memory (volatile). |
  | Best for transient, high-security secrets and temp caches.|
  +-----------------------------------------------------------+
```

#### Named Volumes (Docker Managed)
- **Concept**: Docker creates a managed directory on the host's filesystem (typically `/var/lib/docker/volumes/` on Linux). You do not manage the host directory path; Docker handles it for you.
- **Best For**: Persistent database storage, application upload directories, and production state storage.

#### Bind Mounts (Host Managed)
- **Concept**: You map a specific directory on your host machine (e.g., `/Users/dev/project/frontend`) to a folder inside the container.
- **Best For**: Local development. E.g., mounting your source code directory into the container so file edits sync instantly (live-reloading) without rebuilding the image.

#### Tmpfs Mounts (Memory Bound)
- **Concept**: Maps storage directly to the host's temporary memory (RAM). The data is never written to disk and is wiped when the container stops.
- **Best For**: Storing sensitive temporary tokens, private keys, or high-speed caching files.

---

## Code Examples and Walkthroughs

### 1. Volume Lifecycle Management via CLI
Manage volumes using the Docker CLI:

```bash
# 1. Create a named volume for PostgreSQL database storage
docker volume create db-prod-volume

# 2. List all volumes on the host system
docker volume ls

# Expected Output:
# DRIVER    VOLUME NAME
# local     db-prod-volume

# 3. Inspect volume details to find the physical mount path on the host
docker volume inspect db-prod-volume

# Expected Output:
# [
#     {
#         "Name": "db-prod-volume",
#         "Mountpoint": "/var/lib/docker/volumes/db-prod-volume/_data",
#         "Driver": "local"
#     }
# ]

# 4. Remove a volume (The volume must not be attached to any containers)
docker volume rm db-prod-volume

# 5. Remove all unused local volumes to free disk space
docker volume prune
```

---

### 2. Mounting Named Volumes and Bind Mounts (CLI Syntax)
This script demonstrates how to mount storage options using the `-v` flag:

```bash
# 1. Mount a Named Volume to a database container
docker run -d \
  --name pg-server \
  -v db-prod-volume:/var/lib/postgresql/data \
  -e POSTGRES_PASSWORD=my_password \
  postgres:alpine

# 2. Mount a Bind Mount to an Nginx web server for local configuration testing
# (This maps the local config file as a secure, read-only volume using the ':ro' suffix)
docker run -d \
  --name web-server \
  -p 80:80 \
  -v $(pwd)/nginx.conf:/etc/nginx/nginx.conf:ro \
  nginx:alpine
```

---

## Summary
- **Named Volumes** are managed by Docker on the host filesystem and are the standard choice for databases.
- **Bind Mounts** link to specific folders on the host disk, making them ideal for live-reloading code during local development.
- **`tmpfs` Mounts** write data to the host system's RAM, keeping sensitive keys and caches secure.
- Configure volumes with the **`:ro`** suffix to make mounts read-only, preventing containers from modifying configuration files.

---

## Additional Resources
- [Docker Documentation: Deep Dive into Volumes](https://docs.docker.com/storage/volumes/)
- [Using Bind Mounts in Local Development Environments](https://docs.docker.com/storage/bind-mounts/)
- [Docker CLI Command Reference: docker volume](https://docs.docker.com/engine/reference/commandline/volume/)
