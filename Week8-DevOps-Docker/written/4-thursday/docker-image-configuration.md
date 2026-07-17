# Docker Image Configuration: Shell vs. Exec Forms, Signal Handling, and Variable Scopes

## Learning Objectives
- Differentiate between Shell Form and Exec Form in Dockerfile instructions.
- Analyze the interaction between `ENTRYPOINT` and `CMD`.
- Explain how container execution forms impact OS signal handling (`SIGTERM`, `SIGKILL`).
- Configure compile-time variables (`ARG`) and runtime variables (`ENV`) correctly.

---

## Why This Matters
When defining startup commands in your Dockerfile, you can write them in two ways: **Shell Form** (e.g., `CMD java -jar app.jar`) or **Exec Form** (e.g., `CMD ["java", "-jar", "app.jar"]`). While they look similar, they behave differently under the hood.

If you use Shell Form, Docker wraps your process in a `/bin/sh -c` subshell. This blocks OS signals (like `SIGTERM`) from reaching your application. When you run `docker stop`, the container will not shut down gracefully; it will hang for 10 seconds before being forcefully terminated (`SIGKILL`). This can corrupt files or drop active database transactions.

Learning how to configure execution forms, manage signal propagation, and scope variables is key to building stable, responsive container images.

---

## The Concept

### 1. Shell Form vs. Exec Form

#### Shell Form (Avoid for primary processes)
- **Syntax**: `CMD java -jar app.jar`
- **Under the Hood**: Runs as a child process of the shell (`/bin/sh -c "java -jar app.jar"`).
- **Process ID (PID)**: The shell runs as PID 1, and the Java application runs as a sub-process.
- **Signal Impact**: The shell does not forward OS signals. If you run `docker stop`, the Java process never receives the shutdown signal, preventing graceful connection cleanups.

#### Exec Form (Recommended)
- **Syntax**: `CMD ["java", "-jar", "app.jar"]` (JSON array format).
- **Under the Hood**: Runs the executable directly, without invoking a command shell.
- **Process ID (PID)**: The Java application runs directly as PID 1.
- **Signal Impact**: OS signals are received directly by the application process, enabling instant, graceful shutdowns.

```
       SHELL FORM PROCESS TREE (PID 1 = /bin/sh)
+-------------------------------------------------+
|  PID 1: /bin/sh -c "java -jar app.jar"          |
|    └─ PID 7: java -jar app.jar                  |
|  * Blocked: SIGTERM stays at PID 1, PID 7 hangs *|
+-------------------------------------------------+

       EXEC FORM PROCESS TREE (PID 1 = Application)
+-------------------------------------------------+
|  PID 1: java -jar app.jar                       |
|  * Handled: SIGTERM hits application instantly  *|
+-------------------------------------------------+
```

---

### 2. ENTRYPOINT and CMD Interaction
When combined, `ENTRYPOINT` and `CMD` behave as follows:
- **`ENTRYPOINT`** defines the core binary or start script to run.
- **`CMD`** sets the default arguments passed to that binary.

| Dockerfile Configurations | Resulting Run Command |
| :--- | :--- |
| `ENTRYPOINT ["java", "-jar"]`<br>`CMD ["app.jar"]` | `java -jar app.jar` |
| `ENTRYPOINT ["ping"]`<br>`CMD ["localhost"]` | `ping localhost` (running `docker run image google.com` overrides `CMD`, running `ping google.com` instead). |

---

### 3. Variable Scopes: Build-Time `ARG` vs. Runtime `ENV`
- **`ARG`**: Used to pass parameters during the build phase (e.g., `docker build --build-arg VERSION=1.2 .`). They are completely stripped from the final image.
- **`ENV`**: Injected into the container's environment, persisting at runtime.

---

## Code Examples and Walkthroughs

### 1. Verification: Testing Signal Handling (Shell vs. Exec)
Let's build a simple script to verify how execution forms impact OS signal handling:

#### Dockerfile using Shell Form (Incorrect)
```dockerfile
FROM alpine:3.18
# Shell form: wraps the command in a subshell
CMD sleep 1000
```
Build and run this container:
```bash
docker build -t shell-test -f Dockerfile.shell .
docker run -d --name shell-instance shell-test

# Try to stop the container gracefully
time docker stop shell-instance

# Output shows the command hangs for exactly 10 seconds:
# real    0m10.250s
# (This indicates the process hung and had to be forcefully killed via SIGKILL).
```

#### Dockerfile using Exec Form (Correct)
```dockerfile
FROM alpine:3.18
# Exec form: runs the process directly as PID 1
CMD ["sleep", "1000"]
```
Build and run this container:
```bash
docker build -t exec-test -f Dockerfile.exec .
docker run -d --name exec-instance exec-test

# Stop the container gracefully
time docker stop exec-instance

# Output shows the container stops instantly (under 1 second):
# real    0m0.320s
```

---

## Summary
- **Exec Form** (`["executable", "param"]`) runs processes directly as PID 1, enabling proper OS signal handling.
- **Shell Form** wraps processes in a subshell (`/bin/sh -c`), blocking shutdown signals and causing containers to hang during stops.
- Combine **`ENTRYPOINT`** (core binary) and **`CMD`** (default arguments) to create flexible, overridable container entrypoints.
- Use **`ARG`** for build-time configurations and **`ENV`** for runtime settings.

---

## Additional Resources
- [Docker Reference Manual: ENTRYPOINT Instruction Details](https://docs.docker.com/engine/reference/builder/#entrypoint)
- [OS Signals and Container PID 1 Lifecycles](https://docs.docker.com/engine/reference/run/#foreground)
- [How to pass ARG and ENV variables dynamically in Dockerfiles](https://docs.docker.com/engine/reference/builder/#arg)
