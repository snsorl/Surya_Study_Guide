# Docker Images: Layers, Identifiers, Tags, and CLI Management

## Learning Objectives
- Explain the anatomy of a Docker Image, focusing on read-only image layers and union filesystems.
- Manage images using tags and image IDs.
- Execute Docker CLI commands to download, list, inspect, and delete images.
- Prune unused images from host systems to free disk space.

---

## Why This Matters
Docker images are the building blocks of containerized deployments. An image is an immutable template used to launch active container processes.

If you do not know how Docker manages images under the hood, you will waste storage space on your development machines and staging servers. Unused image layers accumulate quickly during development, which can consume hundreds of gigabytes of disk space if not pruned regularly. Learning how to manage, tag, inspect, and clean your local image cache is key to maintaining healthy development environments.

---

## The Concept

### 1. Image Layers and the Union Filesystem
A Docker image is structured as a stack of read-only filesystem layers.
- **Base Layer**: The initial operating system layer (e.g., Alpine Linux).
- **Intermediate Layers**: Created by running commands in your Dockerfile (like installing packages, configuring variables, or copying code files).
- **Union Filesystem**: Combines these separate layers into a single, unified filesystem view.
- **Immutability**: Once built, these layers are completely read-only. When you launch a container, Docker adds a thin, read-write layer (the "Container Layer") on top of this read-only stack. Any file modifications made by the running container are saved in this temporary read-write layer, keeping the underlying image clean and reusable.

```
       Container Layer (Read-Write)  <-- Created on launch
+-----------------------------------+
|  File changes, log files, temp    |
+-----------------------------------+
       IMAGE LAYERS (Read-Only)      <-- Built from Dockerfile
+-----------------------------------+
|  Layer 3: Executable JAR file     |
+-----------------------------------+
|  Layer 2: Corretto Java 17        |
+-----------------------------------+
|  Layer 1: Minimal OS (Alpine)     |
+-----------------------------------+
```

---

### 2. Tags and Image IDs
- **Image ID**: A unique SHA-256 hash identifying the image configuration and layer stack.
- **Tags**: A user-friendly alias pointing to an Image ID (format: `repository-name:tag-name`, e.g., `postgres:15-alpine`).
- **`latest` Tag**: If you pull or build an image without specifying a tag, Docker defaults to the tag `latest`. *Warning: Do not use `latest` in production environments, as it can pull unexpected breaking changes when servers restart. Always specify version numbers.*

---

## Code Examples and Walkthroughs

### 1. Managing Images with the Docker CLI
These commands cover downloading, listing, inspecting, and deleting Docker images on your host system:

```bash
# 1. Pull a specific PostgreSQL image version from Docker Hub
docker pull postgres:15-alpine

# 2. List all images currently cached on your local host
docker images

# Expected Output:
# REPOSITORY   TAG          IMAGE ID       CREATED        SIZE
# postgres     15-alpine    d22384a56903   3 days ago     379MB
# openjdk      17-slim      a8721c9a101b   2 weeks ago    292MB

# 3. Inspect the metadata of an image (returns a detailed JSON description of environment variables, ports, and layers)
docker image inspect postgres:15-alpine

# 4. Remove a specific local image using its repository tag
docker rmi postgres:15-alpine

# 5. Force-remove an image using its unique Image ID hash
docker rmi -f d22384a56903
```

---

### 2. Cleaning Up Disk Space
During build and test pipelines, old, untagged images (called "dangling images") accumulate in your local cache. Clean them up using prune commands:

```bash
# 1. Remove all dangling (untagged) images from the host system
docker image prune

# 2. Remove all unused images (both dangling and cached images not currently running in a container)
docker image prune -a

# Verification:
# Run the disk space usage check to verify freed storage:
docker system df
```

---

## Summary
- **Docker Images** are built from read-only filesystem layers stacked using a union filesystem.
- **Image IDs** are unique cryptographic hashes, while **Tags** act as human-readable aliases.
- Use **`docker pull`** to download images, **`docker images`** to list them, and **`docker rmi`** to delete them.
- Regularly run **`docker image prune -a`** to clear unused images and free storage space on host machines.

---

## Additional Resources
- [Docker Documentation: Understanding Images, Containers, and Storage Drivers](https://docs.docker.com/storage/storagedriver/)
- [Docker CLI Command Reference: docker image](https://docs.docker.com/engine/reference/commandline/image/)
- [Best Practices for Tagging and Versioning Production Images](https://docs.docker.com/build/building/tag/)
