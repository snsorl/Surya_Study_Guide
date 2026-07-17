# Knowledge Check: Advanced Dockerfile Keywords, Volumes, and Compose Orchestration

This quiz evaluates your understanding of Docker Compose services, network isolation, persistent named volumes, signal handling in execution forms, and container security best practices.

---

## Questions

### 1. In a `docker-compose.yml` file, how do you prevent a container from starting until another container passes its health check?
- [ ] A) Using `links: - database`
- [ ] B) Using `depends_on` with the `condition: service_healthy` rule.
- [ ] C) Using the `restart: always` parameter.
- [ ] D) By writing a custom startup script.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Using `depends_on` with the `condition: service_healthy` rule.

**Explanation:** In modern Docker Compose, you can define startup dependencies using `depends_on` and set `condition: service_healthy` to wait for health checks to pass.
- **Why others are wrong:**
  - A) `links` is a legacy parameter that only links networks, without checking container health status.
  - C) `restart: always` restarts the container if it exits, but does not coordinate startup order.
  - D) Custom scripts can manage startup order, but Compose handles this natively.
</details>

---

### 2. Which Docker storage mount option is managed entirely by the Docker Daemon on the host system, making it the standard choice for database data persistence?
- [ ] A) Bind Mount
- [ ] B) Tmpfs Mount
- [ ] C) Named Volume
- [ ] D) Overlay Mount

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Named Volume

**Explanation:** Named Volumes are managed by Docker and stored in a managed directory (e.g., `/var/lib/docker/volumes/` on Linux), isolating storage directories from direct host modifications.
- **Why others are wrong:**
  - A) Bind Mounts link to specific, user-defined file paths on the host system.
  - B) Tmpfs Mounts write data directly to the host's temporary memory (RAM), not to persistent storage.
  - D) Overlay refers to the filesystem driver, not a storage mount type.
</details>

---

### 3. What is the impact of using the "Shell Form" (e.g., `CMD java -jar app.jar`) instead of the "Exec Form" (e.g., `CMD ["java", "-jar", "app.jar"]`) for the primary container process?
- [ ] A) Shell Form reduces build size.
- [ ] B) Shell Form prevents the container from accepting environment variables.
- [ ] C) Shell Form wraps the process in a subshell, preventing OS termination signals (like `SIGTERM`) from reaching the application, causing it to hang on stops.
- [ ] D) There is no difference in behavior.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Shell Form wraps the process in a subshell, preventing OS termination signals (like `SIGTERM`) from reaching the application, causing it to hang on stops.

**Explanation:** Shell Form runs processes under `/bin/sh -c`, which does not forward OS signals. This prevents the application from shutting down gracefully, causing it to hang during stops.
- **Why others are wrong:**
  - A), B), and D) describe incorrect behaviors for these execution forms.
</details>

---

### 4. Which Dockerfile instruction registers trigger instructions that run automatically when this image is used as a base image for another Dockerfile?
- [ ] A) `HEALTHCHECK`
- [ ] B) `ONBUILD`
- [ ] C) `STOPSIGNAL`
- [ ] D) `LABEL`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `ONBUILD`

**Explanation:** `ONBUILD` registers trigger instructions that run when this image is used as the base image (in a `FROM` instruction) for another build.
- **Why others are wrong:**
  - A) `HEALTHCHECK` configures a command to test container health periodically.
  - C) `STOPSIGNAL` sets the signal sent to the container process to stop it.
  - D) `LABEL` adds metadata key-value pairs to the image.
</details>

---

### 5. What security configuration parameter should be set on production containers to prevent attackers from downloading and executing malware inside the container?
- [ ] A) `cap_add: - ALL`
- [ ] B) Running the container with a read-only root filesystem (`--read-only` or `read_only: true`).
- [ ] C) Exposing database port `5432` to the public network.
- [ ] D) Increasing the CPU resource allocation limits.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Running the container with a read-only root filesystem (`--read-only` or `read_only: true`).

**Explanation:** Enforcing a read-only root filesystem prevents attackers from writing files, saving scripts, or modifying binaries if the application is compromised.
- **Why others are wrong:**
  - A) `cap_add: ALL` grants all system capabilities, creating major security risks.
  - C) Exposing database ports increases the attack surface.
  - D) CPU resource limits manage performance, but do not prevent malicious writes.
</details>

---

### 6. What is the difference between build-time variables (`ARG`) and runtime variables (`ENV`) in Dockerfiles?
- [ ] A) `ARG` values are available in the running container; `ENV` values are only used during the build phase.
- [ ] B) `ARG` variables are used only during the build phase and are stripped from the final image; `ENV` variables persist inside the running container.
- [ ] C) They are identical variables used for credentials.
- [ ] D) `ARG` can only handle integer numbers; `ENV` handles string text.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `ARG` variables are used only during the build phase and are stripped from the final image; `ENV` variables persist inside the running container.

**Explanation:** Use `ARG` for variables needed only during image build (e.g., compiler versions). Use `ENV` for variables that must persist inside the container at runtime.
- **Why others are wrong:**
  - A), C), and D) describe incorrect behaviors for these variables.
</details>

---

### 7. What Docker Compose parameter allows you to define a list of optional containers (like administrative tools) that do not start by default?
- [ ] A) `profiles`
- [ ] B) `networks`
- [ ] C) `extends`
- [ ] D) `env_file`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `profiles`

**Explanation:** The `profiles` parameter allows you to assign services to optional profiles. These services are only started if you pass the `--profile` flag to the `up` command.
- **Why others are wrong:**
  - B) `networks` configures virtual networks.
  - C) `extends` allows services to share configurations.
  - D) `env_file` loads environment variables from a file.
</details>

---

### 8. True or False: Docker Compose automatically creates a shared default network for all services defined in your `docker-compose.yml` file, allowing them to resolve each other by service name.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) True

**Explanation:** By default, Docker Compose sets up a single default network for all services in the configuration file. Each container can communicate with other services using their service names (e.g., `http://database:5432`) as hostnames.
- **Why others are wrong:**
  - B) This is incorrect because default network creation and service name resolution are standard features of Compose.
</details>

---

### 9. What syntax format allows developers to define a configuration template once in a Compose file and reuse it across multiple services (DRY configuration)?
- [ ] A) JSON array brackets
- [ ] B) YAML Anchors (`&`) and Aliases (`*`)
- [ ] C) Maven POM dependencies
- [ ] D) systemd unit files

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) YAML Anchors (`&`) and Aliases (`*`)

**Explanation:** YAML Anchors allow you to define common parameters once (using `&name`) and copy them into other service blocks (using `*name` or `<<: *name`), keeping configurations DRY.
- **Why others are wrong:**
  - A), C), and D) are formatting syntax for other runtimes.
</details>

---

### 10. When executing file copies in your Dockerfile, why is `COPY` preferred over `ADD` for general files?
- [ ] A) `COPY` can download files from remote URLs.
- [ ] B) `COPY` automatically extracts compressed tar files.
- [ ] C) `COPY` is more transparent and only handles copying files from the local build context, preventing unexpected behaviors.
- [ ] D) `COPY` is a deprecated keyword.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `COPY` is more transparent and only handles copying files from the local build context, preventing unexpected behaviors.

**Explanation:** `COPY` only copies files from the local build context. `ADD` can download files from remote URLs and automatically extract compressed archives, which can introduce unexpected vulnerabilities or files into the build.
- **Why others are wrong:**
  - A) and B) describe features of `ADD`, which are typically avoided unless explicitly needed.
  - D) `COPY` is a standard, active keyword.
</details>
