# Docker Containers: Lifecycle, Detached Mode, and Runtime Command Execution

## Learning Objectives
- Compare the relationship between a Docker Image (blueprint) and a Docker Container (instance).
- Trace the container lifecycle states: Created, Running, Paused, Stopped, and Exited.
- Execute Docker CLI commands to create, start, run, and stop containers.
- Differentiate between Foreground (attached) and Background (detached) runtime modes.
- Log in to and execute shell commands inside running containers using `docker exec`.

---

## Why This Matters
When working with Docker, developers often use `docker run` as a catch-all command. However, running a container without managing its lifecycle or console streams can cause issues.

For example, running a database container in foreground mode locks your terminal, and terminating the terminal session stops the database process. Similarly, running web applications without knowing how to execute debugging shells inside the running container makes troubleshooting runtime errors or viewing configuration files difficult.

Learning container lifecycle management—including background execution and executing terminal sessions in running containers—is key to managing applications in Docker.

---

## The Concept

### 1. Image vs. Container
- **Docker Image**: A read-only template containing the application code, libraries, and runtime environment. It takes up disk storage space but does not consume CPU or memory.
- **Docker Container**: A running instance of an image. It executes as an isolated process on the host machine, consuming CPU, memory, and networking resources.

```
       DOCKER IMAGE (Blueprint on Disk)
+---------------------------------------------+
|  Reads OS files, Java binary, App.jar       |
+----------------------t----------------------+
                       | Instantiates (docker run)
                       v
     DOCKER CONTAINER (Active Process in RAM)
+---------------------------------------------+
|  - Spawns Java process                      |
|  - Allocates RAM and virtual IP             |
|  - Adds thin read-write filesystem layer    |
+---------------------------------------------+
```

---

### 2. Container Lifecycle States
A container transitions through several states:
- **Created**: The container's read-write filesystem layer and network rules are defined (`docker create`), but its processes are not running yet.
- **Running**: The container's primary process is executing.
- **Stopped**: The primary process has been terminated (using `docker stop`, which sends a `SIGTERM` signal followed by `SIGKILL`). The container's state and filesystem are saved, and you can restart it.
- **Exited**: The container process finished its task (e.g., a test script completed) or crashed.

---

### 3. Foreground vs. Detached Mode
- **Foreground (Attached) Mode**: The default mode. The container's standard output (stdout) and standard error (stderr) streams are piped directly to your terminal. Pressing `Ctrl+C` sends an interrupt signal, stopping the container process.
- **Detached Mode (`-d` flag)**: Runs the container in the background. The terminal returns the container ID instantly, freeing your command line while the container process executes. This is the standard mode for hosting databases, web APIs, and background queue workers.

---

## Code Examples and Walkthroughs

### 1. Basic Lifecycle Commands
These commands demonstrate how to start, monitor, and stop containers:

```bash
# 1. Run an Nginx web server in the background (detached mode)
docker run -d --name project3-web nginx:alpine

# 2. List all active (running) containers
docker ps

# 3. List all containers on the host, including stopped or exited containers
docker ps -a

# Expected Output:
# CONTAINER ID   IMAGE          COMMAND                  STATUS          NAMES
# a78fbc312a89   nginx:alpine   "/docker-entrypoint.…"   Up 45 seconds   project3-web

# 4. Stop the running container gracefully
docker stop project3-web

# 5. Start the stopped container again
docker start project3-web

# 6. Delete the container definition from the host
docker rm project3-web
```

---

### 2. Logging in and Debugging (docker exec)
To troubleshoot database connection pools or inspect configurations inside a running container, execute an interactive shell session inside it using `docker exec`:

```bash
# Start an interactive bash shell session inside the running container
# (The -i flag makes the session interactive, and -t allocates a pseudo-TTY terminal)
docker exec -it project3-web sh

# Verification:
# Inside this container terminal, you can run commands:
# $ hostname
# Expected Output: a78fbc312a89 (matches the Container ID)

# Exit the container session back to your host terminal
# $ exit
```

---

## Summary
- **Docker Images** are static templates on disk; **Containers** are active, running processes in memory.
- Use **`docker run -d`** to launch containers in the background (**detached mode**), freeing your terminal.
- Use **`docker ps -a`** to list all containers and trace their lifecycle states.
- Execute interactive shell debugging sessions inside running containers using **`docker exec -it [container] sh`**.

---

## Additional Resources
- [Docker Documentation: Lifecycle and Container Operations Guide](https://docs.docker.com/engine/reference/commandline/container/)
- [Understanding the difference between docker run, start, and create](https://docs.docker.com/engine/reference/commandline/run/)
- [Docker CLI Reference: docker exec command](https://docs.docker.com/engine/reference/commandline/exec/)
