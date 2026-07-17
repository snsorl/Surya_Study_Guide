# Collaborative Exercise: Multi-Container Orchestration with Docker Compose

## Thursday Protocol: Collaborative Project
- **Format**: Pair Programming (Driver and Navigator roles).
- **Driver Role**: Writes the configuration files (`docker-compose.yml` and `nginx.conf`) in the IDE.
- **Navigator Role**: Guides the driver, checks port routing schemas, verifies health check flags, and reviews volume mount points.
- **Switch Roles**: Switch roles after completing Task 2.
- **Deliverable**: A functional, multi-container `docker-compose.yml` stack repository.

---

## Learning Objectives
- Write a multi-service `docker-compose.yml` config file.
- Configure bridge networks to isolate database containers from public traffic.
- Map host ports to container ports through Nginx proxies.
- Mount named volumes to persist database records.
- Implement dependency health checks to coordinate container startup order.

---

## The Scenario
Your team needs to run the entire Project 3 application stack (Angular frontend, Spring Boot backend API, and PostgreSQL database) locally for integration testing. Instead of running these services individually, you and your partner will build a unified Docker Compose configuration to start and stop the entire stack with a single command.

---

## Tasks

### Task 1: Design the Network and Storage Layout (Navigator Focus)
1.  Discuss with your partner the target network and volume structure:
    *   **Networks**: Create a private `backend-net` bridge network connecting Spring Boot to PostgreSQL. Create a `frontend-net` bridge network connecting Nginx to Spring Boot. *Ensure the database container is not connected to the public frontend network.*
    *   **Volumes**: Define a named volume `pg-database-storage` mapped to PostgreSQL's `/var/lib/postgresql/data` directory to persist records.
2.  Write a simple ASCII topology map illustrating the network divisions and save it in a design file `architecture_map.txt`.

---

### Task 2: Implement the Services (Driver Focus)
1.  Create a `docker-compose.yml` file in the root project folder.
2.  Define the three core services:
    *   **database**: Image `postgres:15-alpine`. Configure database name, user, and passwords. Map to the `pg-database-storage` volume. Connect only to `backend-net`.
    *   **backend-api**: Build from the Spring Boot directory. Configure JDBC connection strings to target the database service on port `5432`. Connect to both `backend-net` and `frontend-net`.
    *   **frontend-web**: Image `nginx:alpine`. Map host port `80:80` and bind-mount your local Nginx reverse proxy configuration. Connect only to `frontend-net`.

***[SWITCH DRIVER & NAVIGATOR ROLES NOW]***

---

### Task 3: Configure Health Checks and Startup Dependency
If the Spring Boot container starts before the database container is ready to accept connections, it will crash. Configure startup coordination:
1.  **Database Health Check**: Add a `healthcheck` block to the database service testing connection readiness:
    ```yaml
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 5s
      timeout: 5s
      retries: 5
    ```
2.  **API Service Dependency**: Configure the `backend-api` service to wait for the database health check using the `service_healthy` condition:
    ```yaml
    depends_on:
      database:
        condition: service_healthy
    ```

---

### Task 4: Launch, Audit, and Test
1.  Start the multi-container stack:
    ```bash
    docker compose up -d --build
    ```
2.  Audit the container startup states. Verify that Spring Boot waits for the database's health check status to change to `healthy` before booting:
    ```bash
    docker compose ps
    ```
3.  Open a browser and navigate to `http://localhost/`. Verify that the Nginx proxy successfully routes traffic to the backend API.
4.  Verify data persistence: Create a test record, stop the stack using `docker compose down`, restart the stack, and confirm the record is preserved.

---

## Definition of Done
- A valid `docker-compose.yml` is saved in the repository root.
- The ASCII network map `architecture_map.txt` is complete.
- Running `docker compose up -d` starts Nginx, Spring Boot, and PostgreSQL in the correct order.
- The PostgreSQL data persists across stack restarts.
- The database container is isolated from public traffic networks.
