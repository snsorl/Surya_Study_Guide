# Installing and Verifying Docker: Workstations, Backends, and Command Liners

## Learning Objectives
- Install Docker Desktop on Windows, macOS, and Linux workstations.
- Configure the Windows Subsystem for Linux 2 (WSL2) backend for Docker on Windows.
- Verify installation using `docker version` and `docker info` commands.
- Compare Docker Desktop (managed GUI application) with Docker Engine (raw daemon engine).

---

## Why This Matters
Before running containerized applications, you must install and configure Docker on your development workstation. 

For Windows developers, this installation requires configuring the Windows Subsystem for Linux 2 (WSL2) backend. If WSL2 integration is configured incorrectly, Docker will experience performance issues or fail to launch. Understanding installation steps, backend integrations, and status check commands is key to setting up your workspace.

---

## The Concept

### 1. Docker Desktop vs. Docker Engine
Understanding the differences between these two distributions is important:

#### Docker Desktop (Developer Workstations)
- **Concept**: A managed, graphical interface application designed for development on Windows, macOS, and Linux.
- **Includes**: Docker Daemon, Docker Client CLI, Docker Compose, Kubernetes, dashboard GUI, and automatic virtual machine configuration.
- **Licensing**: Free for personal use, education, and small businesses; paid subscription required for large enterprises.

#### Docker Engine (Production Servers)
- **Concept**: The raw, CLI-only background service (`dockerd`) and client toolset running natively on Linux.
- **Includes**: No graphical interface. It runs directly on the host operating system kernel.
- **Licensing**: Free and open-source under the Apache 2.0 license. Used for cloud servers (like AWS EC2 hosts) and CI build nodes.

---

### 2. WSL2: The Engine of Docker on Windows
Because Docker containers are Linux processes, they require a Linux kernel to run. Windows cannot run Linux containers natively.
- **WSL2 (Windows Subsystem for Linux 2)**: A lightweight virtual machine running a real Linux kernel alongside Windows.
- **Docker Integration**: Docker Desktop on Windows uses WSL2 as its compute backend. Instead of running a heavy Hyper-V virtual machine, Docker routes container processes through the lightweight WSL2 kernel, improving performance, file access speeds, and startup times.

```
+-------------------------------------------------------------+
|                      WINDOWS WORKSTATION                    |
|                                                             |
|  +--------------------+        +-------------------------+  |
|  |   Docker Desktop   |=======>|      WSL2 BACKEND       |  |
|  |     (GUI/CLI)      | Access | (Lightweight VM running |  |
|  +--------------------+        |  actual Linux Kernel)   |  |
|                                +-------------------------+  |
+-------------------------------------------------------------+
```

---

## Code Examples and Walkthroughs

### 1. Configuring WSL2 on Windows (PowerShell)
Before installing Docker Desktop on Windows, you must enable the WSL2 feature:

```powershell
# 1. Run PowerShell as Administrator and enable the WSL feature
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart

# 2. Enable Virtual Machine Platform feature
dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart

# 3. Restart your computer to complete the installation.

# 4. Once restarted, set WSL2 as the default architecture version
wsl --set-default-version 2
```

---

### 2. Verifying a Successful Installation
Once Docker Desktop or Docker Engine is installed and running, execute these commands to verify your setup:

```bash
# 1. Check basic version info (confirms the CLI client can talk to the daemon)
docker version

# 2. Check detailed system information (shows CPU count, memory, container count, and OS details)
docker info

# Expected Output includes system resource summaries:
# Client:
#  Context:    default
#  Debug Mode: false
#
# Server:
#  Containers: 0
#   Running: 0
#   Paused: 0
#   Stopped: 0
#  Images: 0
#  Server Version: 24.0.7
#  Storage Driver: overlay2  <-- Default Linux storage driver
#  Kernel Version: 5.15.133.1-microsoft-standard-WSL2  <-- WSL2 backend kernel
```

---

## Summary
- **Docker Desktop** is a graphical developer tool for local workstations, while **Docker Engine** is the raw, CLI-only runtime for production Linux servers.
- On Windows, Docker uses the **WSL2 backend** to run containerized Linux processes efficiently.
- Use **`docker version`** to verify API communication, and **`docker info`** to check the daemon's host system resources.

---

## Additional Resources
- [Docker Desktop Installation Guide for Windows](https://docs.docker.com/desktop/install/windows-install/)
- [Microsoft Guide: How to Install WSL2 on Windows](https://learn.microsoft.com/en-us/windows/wsl/install)
- [Docker System Reference Manual: docker info command](https://docs.docker.com/engine/reference/commandline/info/)
