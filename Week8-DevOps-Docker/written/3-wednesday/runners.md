# GitLab Runners: Architectures, Executors, and Configuration

## Learning Objectives
- Explain the role of a GitLab Runner and how it communicates with GitLab.
- Compare Runner scopes: Shared Runners, Group Runners, and Project (Specific) Runners.
- Evaluate Runner Executors: Shell, Docker, and Kubernetes.
- Register and configure a GitLab Runner using the Docker Executor.
- Assign Runner Tags to route specific pipeline jobs.

---

## Why This Matters
Writing a `.gitlab-ci.yml` pipeline configuration is only the first step. To execute those commands, you need compute resources. In GitLab CI/CD, this execution is handled by **GitLab Runners**.

If you do not understand runner architectures or executor configurations, your pipeline jobs will get stuck in a "pending" state or fail to run. Setting up and configuring runners—including Docker executors, cache volumes, and routing tags—is key to managing your own build infrastructure.

---

## The Concept

### 1. What is a GitLab Runner?
A **GitLab Runner** is an open-source application that runs on a server, virtual machine, or cloud instance. It polls GitLab over HTTPS for pending pipeline jobs, executes the job scripts, and sends the logs and status results back to GitLab.

```
+--------------------+                 +--------------------+
|  GITLAB INSTANCE   |                 |   GITLAB RUNNER    |
| (Web UI, API, Git) |<====(HTTPS)====>| (Executes scripts) |
+--------------------+   Polls Jobs    +---------t----------+
                                                 | Spawns
                                                 v
                                       +--------------------+
                                       |      EXECUTOR      |
                                       | - Runs commands    |
                                       | - E.g., Docker container
                                       +--------------------+
```

---

### 2. Runner Scope levels
Runners are organized by who can use them:
- **Shared Runners**: Available to all projects on the GitLab instance. Usually managed by GitLab system administrators.
- **Group Runners**: Available to all projects within a specific GitLab Group.
- **Project (Specific) Runners**: Dedicated to a single, specific project repository.

---

### 3. Runner Executors
The **Executor** defines the runtime environment where the job script commands run:
- **Shell Executor**: Runs commands directly on the host machine's shell environment (e.g., executing commands directly on the host's terminal). Simple, but lacks isolation—jobs can conflict with each other or modify the host system.
- **Docker Executor**: Launches a fresh Docker container for each job. The job scripts run inside this isolated container, and the container is destroyed when the job completes. This is the recommended executor for most pipelines.
- **Kubernetes Executor**: Spawns a new Pod on a Kubernetes cluster for each job, offering automatic scaling for high-volume enterprise workloads.

---

### 4. Routing Jobs with Runner Tags
If you have multiple runners (e.g., a fast Linux runner with GPUs and a Windows runner for desktop testing), you must ensure jobs run on the correct machine.
- **Tags** are metadata labels assigned to runners during registration.
- In your `.gitlab-ci.yml` file, you add a `tags` array to your jobs. GitLab will only assign the job to a runner that has the matching tags.

---

## Code Examples and Walkthroughs

### 1. Registering a GitLab Runner (Docker Executor)
This command walks through registering a local GitLab Runner on a host machine using the CLI. It configures the runner to use the Docker executor with an Ubuntu default image:

```bash
# Register the runner with your GitLab instance
# (Replace the --url and --registration-token with values from your GitLab project settings)
gitlab-runner register \
  --non-interactive \
  --url "https://gitlab.com/" \
  --registration-token "GLRT-1234567890abcdef" \
  --executor "docker" \
  --docker-image "ubuntu:22.04" \
  --description "Project 3 Dev Build Runner" \
  --tag-list "docker,java,project3" \
  --run-untagged="true" \
  --locked="false"
```

---

### 2. Configuring the Runner Host Config File (`config.toml`)
When registered, the runner writes its configurations to `config.toml`. To speed up Docker builds, you can configure local directory mounts for Maven caches in this file:

```toml
# /etc/gitlab-runner/config.toml
concurrent = 4
check_interval = 0

[session_server]
  session_timeout = 1800

[[runners]]
  name = "Project 3 Dev Build Runner"
  url = "https://gitlab.com/"
  id = 1
  token = "GLRT-1234567890abcdef"
  token_obtained_at = 2026-07-16T15:02:00Z
  token_expires_at = 0001-01-01T00:00:00Z
  executor = "docker"
  
  [runners.custom_build_dir]
  [runners.cache]
    MaxUploadedArchiveSize = 26214400
  [runners.docker]
    tls_verify = false
    image = "ubuntu:22.04"
    privileged = false
    disable_entrypoint_overwrite = false
    oom_kill_disable = false
    disable_cache = false
    # Mount the local host's /tmp folder and cache folders into the job container
    volumes = ["/cache", "/tmp:/tmp:rw"]
    shm_size = 2147483648
```

---

### 3. Routing Jobs using Tags in `.gitlab-ci.yml`
Configure a pipeline job to target a specific runner tagged with `java`:

```yaml
run-unit-tests:
  stage: test
  image: maven:3.8-openjdk-17-slim
  script:
    - mvn test
  # Only assign this job to runners carrying the 'java' tag
  tags:
    - java
```

---

## Summary
- **GitLab Runners** poll GitLab for pending jobs, execute the script commands, and return results.
- **Runner scopes** dictate availability: **Shared** (global), **Group** (group-wide), and **Project** (specific repo).
- The **Docker Executor** runs each job in an isolated container, making it the industry standard over the **Shell** executor.
- **Runner Tags** route pipeline jobs to specific machines that have matching capabilities.

---

## Additional Resources
- [GitLab Runner Architecture and Command Guide](https://docs.gitlab.com/runner/)
- [GitLab Runner Executors: Differences and Trade-offs](https://docs.gitlab.com/runner/executors/)
- [Configuring config.toml: GitLab Runner Advanced configuration](https://docs.gitlab.com/runner/configuration/advanced-configuration.html)
