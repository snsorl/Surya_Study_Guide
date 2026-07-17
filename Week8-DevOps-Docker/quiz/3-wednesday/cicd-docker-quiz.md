# Knowledge Check: GitLab CI/CD, Docker Architecture, and Container Primitives

This quiz evaluates your understanding of GitLab pipeline configurations, Docker engine architecture, base image layers, container execution flows, and registry operations.

---

## Questions

### 1. In a `.gitlab-ci.yml` pipeline file, which keyword is used to declare that a job depends on the successful completion of a specific prior job?
- [ ] A) `stages`
- [ ] B) `needs`
- [ ] C) `extends`
- [ ] D) `include`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `needs`

**Explanation:** The `needs` keyword defines out-of-order job dependencies in GitLab CI/CD, allowing jobs to start as soon as their dependencies complete, bypassing stage ordering.
- **Why others are wrong:**
  - A) `stages` defines the sequential order of pipeline stages.
  - C) `extends` allows jobs to inherit configuration settings from templates.
  - D) `include` imports external YAML files into the pipeline configuration.
</details>

---

### 2. Which component of the Docker Client-Server architecture acts as the background process (`dockerd`) responsible for compiling, running, and managing container files?
- [ ] A) Docker Client
- [ ] B) Docker Daemon
- [ ] C) Docker Registry
- [ ] D) Docker Desktop GUI

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Docker Daemon

**Explanation:** The Docker Daemon (`dockerd`) runs as a background service on the host, listening for API requests and managing images, containers, networks, and volumes.
- **Why others are wrong:**
  - A) The Docker Client CLI translates user commands into REST API requests.
  - C) The Docker Registry hosts and shares container images.
  - D) Docker Desktop is a graphical interface utility that bundles the client and daemon.
</details>

---

### 3. Which Dockerfile instruction defines the primary runtime executable for the container process, which cannot be overridden easily from the CLI?
- [ ] A) `RUN`
- [ ] B) `CMD`
- [ ] C) `ENTRYPOINT`
- [ ] D) `ENV`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `ENTRYPOINT`

**Explanation:** `ENTRYPOINT` defines the core executable process that runs when the container starts. Unlike `CMD`, overriding it requires passing the explicit `--entrypoint` flag in `docker run`.
- **Why others are wrong:**
  - A) `RUN` executes commands during the image build phase.
  - B) `CMD` defines default arguments passed to the `ENTRYPOINT` command.
  - D) `ENV` defines environment variables.
</details>

---

### 4. True or False: Containers share the host operating system kernel, whereas Virtual Machines require a complete Guest OS to be installed on virtualized hardware.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) True

**Explanation:** Containers virtualize at the OS level, sharing the host kernel to boot quickly with minimal resource overhead. VMs virtualize at the hardware level, requiring a complete Guest OS.
- **Why others are wrong:**
  - B) This is incorrect because kernel sharing is the key architectural difference between containers and VMs.
</details>

---

### 5. What Docker CLI command is used to remove all stopped containers, unused networks, and dangling (untagged) images from the host system?
- [ ] A) `docker rmi`
- [ ] B) `docker rm`
- [ ] C) `docker system prune`
- [ ] D) `docker image prune -a`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `docker system prune`

**Explanation:** `docker system prune` removes stopped containers, unused networks, and dangling images at once.
- **Why others are wrong:**
  - A) `docker rmi` removes specific images manually.
  - B) `docker rm` removes specific containers manually.
  - D) `docker image prune -a` removes unused images, but does not clean up stopped containers or networks.
</details>

---

### 6. In a multi-stage Dockerfile, what keyword allows you to copy compiled files from an earlier build stage into a new runtime stage?
- [ ] A) `COPY --from`
- [ ] B) `ADD --link`
- [ ] C) `FROM AS`
- [ ] D) `WORKDIR`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `COPY --from`

**Explanation:** The `COPY --from=<stage>` instruction copies files from a previous build stage (such as compiled JAR files) into the active stage, discarding build dependencies.
- **Why others are wrong:**
  - B) `ADD` is used to copy files, but does not support stage referencing.
  - C) `FROM AS` names the build stage, but does not copy files.
  - D) `WORKDIR` sets the working directory path.
</details>

---

### 7. What is the default path channel where the local Docker Client CLI talks to the local Docker Daemon on Linux systems?
- [ ] A) `tcp://localhost:2375`
- [ ] B) `/var/run/docker.sock`
- [ ] C) `http://127.0.0.1:8080`
- [ ] D) `/etc/docker/daemon.json`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `/var/run/docker.sock`

**Explanation:** By default, local Docker client-daemon communication occurs over a UNIX domain socket located at `/var/run/docker.sock`.
- **Why others are wrong:**
  - A) Port `2375` is used for unencrypted remote connections, which is disabled by default for security.
  - C) Port `8080` is a standard port for web applications, not the Docker socket.
  - D) `/etc/docker/daemon.json` is a configuration file, not a communication channel.
</details>

---

### 8. What does Docker do when you run `docker run hello-world` and the image is not found in your local cache?
- [ ] A) Returns an error and halts execution.
- [ ] B) Contacts the default registry (Docker Hub), pulls the image automatically, and launches the container.
- [ ] C) Creates an empty container skeleton and starts it.
- [ ] D) Compiles the target image from source code automatically.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Contacts the default registry (Docker Hub), pulls the image automatically, and launches the container.

**Explanation:** If the daemon does not find the requested image locally, it contacts Docker Hub (or configured registries) to download and run the image.
- **Why others are wrong:**
  - A) The run command will only fail if the image does not exist in local cache *and* cannot be found in remote registries.
  - C) and D) describe incorrect behaviors.
</details>

---

### 9. Which GitLab CI/CD job variable contains a temporary authentication token that allows pipeline runners to interact with the project registry automatically?
- [ ] A) `$CI_COMMIT_SHA`
- [ ] B) `$CI_JOB_TOKEN`
- [ ] C) `$CI_REGISTRY`
- [ ] D) `$CI_PROJECT_ID`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `$CI_JOB_TOKEN`

**Explanation:** The `$CI_JOB_TOKEN` is a short-lived token generated automatically for each job, allowing runners to push or pull images from the project registry securely.
- **Why others are wrong:**
  - A) `$CI_COMMIT_SHA` contains the Git commit hash.
  - C) `$CI_REGISTRY` contains the registry endpoint URL.
  - D) `$CI_PROJECT_ID` contains the GitLab project identifier.
</details>

---

### 10. True or False: Docker images are completely immutable, meaning you cannot modify files in their layers once they are built.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) True

**Explanation:** Docker images are immutable. When a container starts, Docker adds a thin read-write layer on top of the read-only image layers, preserving the base image.
- **Why others are wrong:**
  - B) This is incorrect because image layers are read-only and cannot be modified once compiled.
</details>
