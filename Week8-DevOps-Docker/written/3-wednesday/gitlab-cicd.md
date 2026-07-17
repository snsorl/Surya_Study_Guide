# GitLab CI/CD Walkthrough: End-to-End Execution and Spring Boot Configuration

## Learning Objectives
- Trace the lifecycle of a commit from local Git push to remote production deployment.
- Read and edit a complete `.gitlab-ci.yml` pipeline configuration for a Spring Boot application.
- Explain the role of runners, cache volumes, artifacts, and variables in a real-world pipeline.
- Troubleshoot common pipeline failures.

---

## Why This Matters
Learning about individual CI/CD concepts (like jobs, runners, and stages) is useful, but you must understand how they work together in a real-world pipeline.

This guide walks you through the entire lifecycle of a code change: from pushing a commit, to triggering the runner, executing builds and tests, publishing artifacts, and deploying to production. We will use a complete, real-world `.gitlab-ci.yml` file for a Spring Boot project to show how these concepts fit together.

---

## The Concept

### 1. End-to-End Pipeline Execution Lifecycle
When a developer pushes code to GitLab, the following steps occur automatically:

```
[Local Machine]         [GitLab Server]         [GitLab Runner]         [AWS Cloud]
  1. git push ====>
                     2. Receives push,
                        parses .gitlab-ci.yml
                        creates pipeline
                     3. Queues jobs ========>
                                              4. Pulls job,
                                                 pulls Docker image
                                              5. Runs scripts,
                                                 saves target/*.jar ===>
                     6. Stores jar artifact <==
                     7. Runs deploy job ====>
                                              8. Pulls deploy script,
                                                 updates ECS task ====>
                                                                         9. ECS pulls ECR image,
                                                                            replaces containers
```

1.  **Commit & Push**: A developer pushes code changes to the remote repository.
2.  **Pipeline Creation**: GitLab detects the push, reads the `.gitlab-ci.yml` file, and builds a pipeline graph of stages and jobs.
3.  **Job Queuing**: Jobs in the first stage are marked as `pending` and queued.
4.  **Runner Execution**: An active GitLab Runner polls the queue, claims the job, pulls the configured Docker image, and executes the script commands.
5.  **Artifact Publishing**: If the job specifies `artifacts`, the runner uploads those files back to the GitLab server.
6.  **Pipeline Progress**: Once all jobs in a stage pass, GitLab queues the jobs in the next stage.
7.  **Deployment**: The final deploy stage uses the saved build artifacts or container images to update your hosting infrastructure (like AWS ECS).

---

## Code Examples and Walkthroughs

### 1. Complete Spring Boot `.gitlab-ci.yml` Configuration
Below is a complete, production-ready `.gitlab-ci.yml` pipeline configuration for a Spring Boot REST API. It handles compiling, unit testing, static code analysis, and staging deployment:

```yaml
# Define pipeline stages in execution order
stages:
  - build
  - test
  - deploy

# Global variables
variables:
  MAVEN_OPTS: "-Dmaven.repo.local=.m2/repository"
  AWS_DEFAULT_REGION: "us-east-1"
  APP_NAME: "project3-api"

# Default configuration applied to all jobs
default:
  image: openjdk:17-slim
  cache:
    key: "${CI_COMMIT_REF_SLUG}"
    paths:
      - .m2/repository

# Stage 1: Build & Compile
build-job:
  stage: build
  script:
    - echo "Compiling application source code..."
    - ./mvnw compile -Dmaven.repo.local=.m2/repository

# Stage 2: Run Unit Tests
test-job:
  stage: test
  script:
    - echo "Running JUnit test assertions..."
    - ./mvnw test -Dmaven.repo.local=.m2/repository
  artifacts:
    name: "test-reports"
    paths:
      - target/surefire-reports/
    expire_in: 3 days

# Stage 2: Package Executable JAR
package-job:
  stage: test
  script:
    - echo "Packaging executable production JAR file..."
    - ./mvnw package -DskipTests -Dmaven.repo.local=.m2/repository
  artifacts:
    name: "production-artifact"
    paths:
      - target/*.jar
    expire_in: 1 week
  only:
    - main

# Stage 3: Deploy to Staging (Manual Approval Gate)
deploy-staging:
  stage: deploy
  image: amazon/aws-cli:latest
  script:
    - echo "Deploying Spring Boot JAR artifact to AWS ECS Staging Cluster..."
    # (In a real pipeline, we copy the JAR to ECR or run the ECS container update commands)
    - aws ecs update-service --cluster project3-staging-cluster --service api-service --force-new-deployment
  environment:
    name: staging
  when: manual
  only:
    - main
```

---

## Summary
- Pushing code to GitLab triggers an automated lifecycle: **Git Push** $\rightarrow$ **Pipeline Creation** $\rightarrow$ **Runner Execution** $\rightarrow$ **Artifact Upload** $\rightarrow$ **Cloud Deployment**.
- A real-world `.gitlab-ci.yml` file uses stages, image environments, local dependency caching, and build artifacts to automate the release cycle safely.
- Manual triggers (`when: manual`) are used to create staging and production deployment gates.

---

## Additional Resources
- [GitLab CI/CD: Pipeline Architecture Guide](https://docs.gitlab.com/ee/ci/pipelines/pipeline_architectures.html)
- [Spring Boot: Guide to Packaging Applications for Deployment](https://docs.spring.io/spring-boot/docs/current/reference/html/vivid-deployment.html)
- [GitLab CI Predefined Environment Variables Reference](https://docs.gitlab.com/ee/ci/variables/predefined_variables.html)
