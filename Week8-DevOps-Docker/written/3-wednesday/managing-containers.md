# Managing Containers: Lifecycle Commands, Log Aggregation, and Resource Auditing

## Learning Objectives
- Execute container lifecycle commands: `ps`, `stop`, `start`, `restart`, and `rm`.
- Monitor container console outputs using `docker logs`.
- Inspect detailed container configuration metadata using `docker inspect`.
- Auditing real-time CPU, memory, and network resource usage using `docker stats`.

---

## Why This Matters
Launching containers is only the first step. In production, you must monitor, debug, and manage running container instances continuously.

For example, if your Spring Boot API crashes or slows down under load, you need tools to diagnose the issue. You must know how to inspect resource usage, parse logs, query internal container details, and restart services safely. Learning how to use Docker's diagnostic commands is key to troubleshooting containerized applications.

---

## The Concept

### 1. The Core Lifecycle Commands
To manage running containers, you use the following core command set:
- **`docker ps`**: Lists running containers. Adding the `-a` flag lists all containers on the host, including stopped or exited containers.
- **`docker stop`**: Stops a running container gracefully, sending a `SIGTERM` signal to let the process clean up connections before shutting down.
- **`docker start`**: Starts a stopped container, preserving its configuration and filesystem.
- **`docker restart`**: Restarts a container, equivalent to running `stop` followed by `start`.
- **`docker rm`**: Deletes a stopped container from the host system, freeing its associated resources.

---

### 2. Log Aggregation (`docker logs`)
Because containers run in isolated environments, their logs are captured by the Docker Daemon:
- The Daemon captures all output written to standard output (`stdout`) and standard error (`stderr`) by the container's primary process.
- **`docker logs`** retrieves these logs, allowing you to troubleshoot application errors. Adding the `-f` flag (follow mode) streams logs in real-time.

---

### 3. Metadata Inspection (`docker inspect`)
- **`docker inspect`** returns a detailed JSON object containing all configuration settings for a container (IP address, volume mappings, environment variables, health status).
- You can filter the output using **Go templates** (`--format` flag) to extract specific properties (like the container's IP address) quickly.

---

### 4. Real-time Resource Auditing (`docker stats`)
- **`docker stats`** acts like a system task manager (similar to `top` in Linux), displaying real-time resource consumption (CPU usage, memory limits, network I/O, disk write speeds) for all active containers.

---

## Code Examples and Walkthroughs

### 1. Lifecycle and Diagnostic CLI Script
This script walks through common container management workflows, including inspecting configurations, streaming logs, and auditing resource usage:

```bash
# 1. Run a detached Nginx container
docker run -d --name test-nginx nginx:alpine

# 2. Check the container's real-time resource footprint (CPU, RAM, Net)
docker stats --no-stream test-nginx

# Expected Output:
# CONTAINER ID   NAME         CPU %     MEM USAGE / LIMIT     MEM %     NET I/O           BLOCK I/O
# 9a78bc312a89   test-nginx   0.00%     2.51MiB / 7.66GiB     0.03%     1.02kB / 0B       0B / 0B

# 3. Stream the last 20 log lines and follow output in real-time
docker logs --tail 20 -f test-nginx

# (Press Ctrl+C to exit log follow mode)

# 4. Inspect the container's private IP address allocated by the daemon
docker inspect --format='{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' test-nginx

# Expected Output: 172.17.0.2

# 5. Stop and delete the container, cleaning up the host
docker stop test-nginx
docker rm test-nginx
```

---

## Summary
- Manage container states using **`docker ps`**, **`docker stop`**, **`docker start`**, and **`docker rm`**.
- Retrieve container logs using **`docker logs -f`** to troubleshoot errors in real-time.
- Query container configuration metadata using **`docker inspect`** and Go template filters.
- Monitor container resource usage (CPU, RAM, and network I/O) using **`docker stats`**.

---

## Additional Resources
- [Docker CLI Command Reference: docker ps](https://docs.docker.com/engine/reference/commandline/ps/)
- [Docker logs reference and logging driver guide](https://docs.docker.com/config/containers/logging/)
- [Using Go template format filters with docker inspect](https://docs.docker.com/engine/reference/commandline/inspect/)
