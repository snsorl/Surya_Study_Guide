# Container Registries: Registry Platforms, Authentication, and Tagging Strategies

## Learning Objectives
- Compare container registry platforms: Docker Hub, Amazon ECR, GitLab Registry, and GitHub Container Registry (GHCR).
- Configure secure token authentication for private registries in CI pipelines.
- Apply industry-standard image tagging strategies (semver, commit SHA, environment tags).
- Manage registry vulnerability scanning and container security policies.

---

## Why This Matters
Building container images locally is only the first step. To deploy your Spring Boot backend or Angular frontend to production cloud platforms (like AWS ECS), you must publish your images to a centralized registry that your servers can access.

If you use poor tagging strategies (like relying on the `latest` tag), your servers could pull different versions of the code when they restart, causing staging mismatches. Similarly, if your registry authentication is insecure, your private images could be exposed.

Learning how to manage registry platforms, configure token authentication, and implement structured tagging strategies is key to building secure pipelines.

---

## The Concept

### 1. Container Registry Platforms

#### Docker Hub (Default Public Registry)
- **Concept**: The default public registry configured in the Docker CLI.
- **Best For**: Open-source runtimes, community base images, and public tools.

#### Amazon Elastic Container Registry / ECR (AWS Native)
- **Concept**: AWS's fully managed container registry.
- **Best For**: Applications deployed on AWS EC2, ECS, or Fargate, leveraging native IAM roles for authentication.

#### GitLab Container Registry (Pipeline Integrated)
- **Concept**: Built directly into GitLab repositories.
- **Best For**: GitLab CI/CD pipelines, allowing runners to push and pull images using temporary tokens (`CI_JOB_TOKEN`) automatically.

#### GitHub Container Registry / GHCR (GitHub Native)
- **Concept**: Part of GitHub Packages, hosting images under the `ghcr.io` namespace.

---

### 2. Image Tagging Strategies
Never rely on the `latest` tag for production deployments. Instead, use structured version tags:

```
                  IMAGE TAGGING PATTERNS
 +-----------------------------------------------------------+
 | SEMANTIC VERSIONING (SemVer): E.g., app:1.2.4             |
 | Best for public releases and stable software versions.    |
 +-----------------------------------------------------------+
 | GIT COMMIT SHA: E.g., app:sha-a8721c9                     |
 | Best for tracking exactly which code commit is running.   |
 +-----------------------------------------------------------+
 | BRANCH / ENVIRONMENT: E.g., app:dev, app:prod             |
 | Floating tags that point to the latest staging version.   |
 +-----------------------------------------------------------+
```

---

### 3. Private Registry Authentication
- **Local Workstations**: Authenticated using CLI login commands (e.g., `docker login` or `aws ecr get-login-password`).
- **CI/CD Runners**: Run in temporary containers, so they cannot use interactive CLI prompts. Instead, you inject registry access keys or API tokens using secure pipeline variables.
- **AWS ECS Nodes**: Authenticated using IAM Instance Profile roles. The ECS task agent uses these roles to pull images from ECR automatically, avoiding the need to manage password files on servers.

---

## Code Examples and Walkthroughs

### 1. Authenticating and Pushing to Amazon ECR
This script demonstrates how to authenticate your local Docker client to a private Amazon ECR registry and upload an image:

```bash
# 1. Authenticate the local Docker client to the AWS ECR registry
# (This command retrieves a temporary authorization token and pipes it to the docker login command)
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 123456789012.dkr.ecr.us-east-1.amazonaws.com

# 2. Tag your local image to match the remote ECR repository URL
# (We append both the SemVer tag and the Git Commit SHA tag for tracking)
docker tag project3-api:1.2.0 123456789012.dkr.ecr.us-east-1.amazonaws.com/project3-api:1.2.0
docker tag project3-api:1.2.0 123456789012.dkr.ecr.us-east-1.amazonaws.com/project3-api:sha-a8721c9

# 3. Push both tags to Amazon ECR
docker push 123456789012.dkr.ecr.us-east-1.amazonaws.com/project3-api:1.2.0
docker push 123456789012.dkr.ecr.us-east-1.amazonaws.com/project3-api:sha-a8721c9
```

---

### 2. Authenticating GitLab CI/CD pipelines to Project Registries
In GitLab pipelines, you do not need to configure custom passwords. The runner resolves credentials automatically using built-in environment variables:

```yaml
# GitLab CI push to project container registry
stages:
  - build

publish-image:
  stage: build
  image: docker:20.10.16
  services:
    - docker:20.10.16-dind # Docker-in-Docker service
  variables:
    # Use the predefined project registry endpoint
    IMAGE_TAG: $CI_REGISTRY_IMAGE:$CI_COMMIT_SHORT_SHA
  script:
    # Authenticate using the temporary build job token
    - echo "$CI_JOB_TOKEN" | docker login -u gitlab-ci-token --password-stdin $CI_REGISTRY
    - docker build -t $IMAGE_TAG .
    - docker push $IMAGE_TAG
```

---

## Summary
- **Amazon ECR** and **GitLab Registry** are the standard private registries for AWS and GitLab CI environments.
- Use **Git Commit SHAs** or **SemVer** tags for production deployments to ensure you can track exactly which code version is running.
- **AWS ECS** leverages native IAM roles to authenticate and pull images from ECR, removing the need to manage password files.
- **GitLab pipelines** use the predefined `$CI_JOB_TOKEN` to authenticate with project registries automatically.

---

## Additional Resources
- [Amazon ECR User Guide: Authenticating and pushing images](https://docs.aws.amazon.com/AmazonECR/latest/userguide/Registries.html)
- [GitLab Container Registry: User Guide and Pipeline Integration](https://docs.gitlab.com/ee/user/packages/container_registry/)
- [Best Practices for Designing Container Image Tagging Workflows](https://docs.docker.com/build/building/tag/)
