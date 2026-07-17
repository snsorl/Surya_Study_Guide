# Docker Compose Overview: Command Operations, Profiles, and Service Integrations

## Learning Objectives
- Describe the purpose and utility of Docker Compose in multi-container architectures.
- Execute core Compose lifecycle commands: `up`, `down`, `ps`, `logs`, `exec`, and `build`.
- Configure Service Profiles to manage optional container environments.
- Integrate container health checks to manage service startup dependencies.

---

## Why This Matters
As you transition from running individual Docker containers to managing multi-container application stacks (e.g., PostgreSQL databases, Spring Boot APIs, and Angular frontends), executing manual Docker commands becomes inefficient.

Docker Compose provides a unified command line interface to manage your entire application stack as a single unit. It allows you to build, start, monitor, and tear down all associated services with simple commands.

---

## The Concept

### 1. What is Docker Compose?
**Docker Compose** is a tool for defining and running multi-container Docker applications. It translates a declarative configuration file (`docker-compose.yml`) into API requests to configure networks, volumes, and containers.

---

### 2. Core CLI Commands

- **`docker compose up`**: Builds, creates, starts, and attaches to containers for a service. Adding the `-d` flag runs containers in the background (detached mode).
- **`docker compose down`**: Stops and removes containers, networks, and images created by `up`. Adding the `-v` flag deletes associated named volumes.
- **`docker compose ps`**: Lists running containers and status summaries for the active Compose stack.
- **`docker compose logs`**: Aggregates and displays logs for all services in the stack. Adding the `-f` flag streams logs in real-time.
- **`docker compose exec`**: Executes shell commands inside a running service container.
- **`docker compose build`**: Rebuilds the images for services that have custom `build` configurations in the Compose file.

---

### 3. Service Profiles (Handling Optional Containers)
In complex stacks, you may not want to run all containers every time. E.g., you might want to run the database and API during standard testing, but only spin up administrative dashboards or performance monitoring tools when troubleshooting.
- **Profiles** allow you to group services in your Compose file.
- Services assigned to a profile are ignored by default. They are only launched if you pass the `--profile` flag to the `docker compose up` command.

---

### 4. Health Check Integration
When starting a multi-container stack, containers start in parallel. This can cause issues: if your Spring Boot container starts faster than your PostgreSQL container, the backend will fail to connect and crash.
- You configure **`healthcheck`** checks on the database container to verify it is active and responding.
- You configure **`depends_on`** constraints on the backend container to prevent it from starting until the database's health check passes.

---

## Code Examples and Walkthroughs

### 1. Complete Multi-Service CLI Workflow
This workflow demonstrates how to build, start, monitor, and clean up a multi-container Compose stack:

```bash
# 1. Build images and start all services in the background (detached mode)
docker compose up -d --build

# 2. Check the running status of your Compose services
docker compose ps

# Expected Output shows active containers and ports:
# NAME                IMAGE          COMMAND                  SERVICE    CREATED          STATUS                    PORTS
# project3-api        project3-api   "java -jar app.jar"      api        30 seconds ago   Up 28 seconds (healthy)   0.0.0.0:8080->8080/tcp
# project3-db         postgres:15    "docker-entrypoint.s…"   database   30 seconds ago   Up 29 seconds (healthy)   5432/tcp

# 3. Stream logs for the backend API service only
docker compose logs -f api
# (Press Ctrl+C to exit log follow)

# 4. Execute a quick database integrity check inside the database container
docker compose exec database pg_isready -U postgres

# 5. Stop and delete the entire stack, including networks and volumes
docker compose down -v
```

---

### 2. Implementing Service Profiles (YAML Snippet)
This snippet shows how to configure an optional database UI administration service (pgAdmin) using Compose profiles:

```yaml
services:
  # Database Service (Runs automatically)
  database:
    image: postgres:15-alpine
    networks:
      - backend-network

  # pgAdmin Service (Optional - Runs only if profile is requested)
  db-admin-dashboard:
    image: dpage/pgadmin4
    ports:
      - "5050:80"
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@company.com
      PGADMIN_DEFAULT_PASSWORD: AdminPassword2026!
    networks:
      - backend-network
    # Assign the service to the 'debug' profile
    profiles:
      - debug
```

To launch the stack with the optional administration tool, pass the profile flag:

```bash
# Launch both default services and the debug pgAdmin dashboard
docker compose --profile debug up -d
```

---

## Summary
- **Docker Compose** simplifies managing multi-container stacks by replacing individual commands with a single configuration file.
- Use **`docker compose up -d`** to launch stacks, and **`docker compose down -v`** to stop services and delete network allocations.
- **Service Profiles** allow you to exclude optional utility containers from default runs.
- Integrate **health checks** and **`depends_on`** rules to manage service startup order and prevent database connection failures.

---

## Additional Resources
- [Docker Compose Command Line CLI Reference](https://docs.docker.com/compose/reference/)
- [Using Profiles to Manage Optional Services in Compose](https://docs.docker.com/compose/profiles/)
- [Docker Documentation: Managing Service Health and Dependencies](https://docs.docker.com/compose/startup-order/)
