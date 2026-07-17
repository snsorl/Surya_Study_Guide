# Docker Compose Configuration: Structure, Services, and DRY Configurations

## Learning Objectives
- Write and structure multi-container `docker-compose.yml` configuration files.
- Configure core Compose blocks: `version`, `services`, `volumes`, `networks`.
- Define container dependencies using `depends_on` health check conditions.
- Implement DRY (Don't Repeat Yourself) configurations using YAML Anchors.

---

## Why This Matters
If you run a full-stack application (frontend, backend, database) using raw Docker commands, you must configure multiple things manually: creating networks, mounting volumes, running containers in the correct order, and matching ports. Writing these commands repeatedly is slow and error-prone.

**Docker Compose** solves this by letting you define your entire multi-container application stack in a single YAML file (`docker-compose.yml`). You can start, stop, scale, and configure all services with a single command (`docker compose up`), simplifying development and deployment.

---

## The Concept

### 1. Structure of `docker-compose.yml`
A Docker Compose file consists of several top-level blocks:
- **`version`**: The configuration syntax version (e.g., `version: '3.8'`). *Note: Modern Compose files (Compose V2) can omit this version block.*
- **`services`**: Defines the containers to launch (e.g., database, backend, frontend).
- **`volumes`**: Defines named volumes for persistent data storage, shared across services.
- **`networks`**: Defines virtual networks to isolate and connect services.

---

### 2. Service Parameters Explained
Inside a service definition, you configure the container properties:
- **`image` / `build`**: Specifies the pre-built image to pull or the directory path containing a Dockerfile to build.
- **`ports`**: Maps host ports to container ports (similar to `-p` in `docker run`).
- **`environment`**: Injects environment variables (similar to `-e` in `docker run`).
- **`volumes`**: Mounts named volumes or bind-mounts directories.
- **`depends_on`**: Specifies execution order. E.g., forcing the backend service to wait until the database container is running and healthy before starting.
- **`networks`**: Attaches the service container to virtual networks.

---

### 3. DRY Configurations using YAML Anchors
If you have multiple containers sharing similar configurations (like the same database variables or log parameters), you can use **YAML Anchors** (`&`) and **Aliases** (`*`) to define a configuration template once and reuse it across multiple services. This keeps your Compose file clean and DRY.

---

## Code Examples and Walkthroughs

### 1. Robust Three-Tier `docker-compose.yml` Configuration
This configuration orchestrates a Spring Boot backend, a PostgreSQL database, and an Nginx reverse proxy. It uses YAML anchors to share common database environment variables and forces the backend to wait for the database's health check:

```yaml
# docker-compose.yml
version: '3.8'

# 1. Define shared environment configurations using YAML anchors
x-db-config: &db-shared-variables
  POSTGRES_USER: project3_user
  POSTGRES_PASSWORD: SecurePassword2026!
  POSTGRES_DB: project3_database

services:
  # Database Service
  database:
    image: postgres:15-alpine
    container_name: project3-db
    environment:
      # Inject the shared configurations anchor
      <<: *db-shared-variables
    volumes:
      - db-data:/var/lib/postgresql/data
    networks:
      - backend-network
    # Configure a health check to verify PostgreSQL is ready to accept connections
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U project3_user -d project3_database"]
      interval: 10s
      timeout: 5s
      retries: 5

  # Spring Boot Backend API Service
  backend-api:
    build:
      context: ./backend
      dockerfile: Dockerfile
    container_name: project3-api
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://database:5432/project3-database
      SPRING_DATASOURCE_USERNAME: project3_user
      SPRING_DATASOURCE_PASSWORD: SecurePassword2026!
    networks:
      - backend-network
      - frontend-network
    depends_on:
      database:
        # Wait for the database health check to pass before starting
        condition: service_healthy

  # Frontend Nginx Proxy Service
  frontend-web:
    image: nginx:alpine
    container_name: project3-frontend
    ports:
      - "80:80"
    volumes:
      - ./frontend/nginx.conf:/etc/nginx/nginx.conf:ro
      - ./frontend/dist:/usr/share/nginx/html:ro
    networks:
      - frontend-network
    depends_on:
      - backend-api

# 2. Define persistent volumes
volumes:
  db-data:
    name: pg-production-data

# 3. Define isolated virtual networks
networks:
  backend-network:
    driver: bridge
  frontend-network:
    driver: bridge
```

---

## Summary
- **Docker Compose** orchestrates multi-container applications using a single configuration file (`docker-compose.yml`).
- Define container templates inside **`services`**, persistent disks in **`volumes`**, and routing rules in **`networks`**.
- Use **`depends_on`** with the **`service_healthy`** condition to manage container startup order.
- **YAML Anchors** (`&`) and **Aliases** (`*`) keep your Compose configurations clean and DRY.

---

## Additional Resources
- [Docker Compose File Reference Manual](https://docs.docker.com/compose/compose-file/)
- [Understanding Startup Order and Service Health Checks in Compose](https://docs.docker.com/compose/startup-order/)
- [YAML Specifications: Anchors, Aliases, and Merge Keys](https://yaml.org/spec/1.2.2/)
