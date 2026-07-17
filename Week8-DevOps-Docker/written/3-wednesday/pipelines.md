# GitLab Pipeline Types: Branch, Merge Request, Scheduled, and Multi-Project Pipelines

## Learning Objectives
- Differentiate between the core GitLab pipeline types: Branch Pipelines and Merge Request Pipelines.
- Configure Scheduled Pipelines for automated periodic tasks.
- Design Multi-Project and Parent-Child pipelines to manage complex dependencies.
- Read and interpret GitLab pipeline visualizations.

---

## Why This Matters
For a simple application, running a single linear pipeline on every commit is sufficient. However, as applications grow into multi-module projects or split into independent frontend and backend repositories, a single linear pipeline creates bottlenecks.

For example, why run a full 30-minute frontend test suite when a developer only updated a backend configuration file? Why trigger a production deployment script on a temporary feature branch?

GitLab provides multiple pipeline architectures to solve these issues. By mastering branch filters, scheduled triggers, and parent-child pipelines, you can build efficient pipelines that scale with your application.

---

## The Concept

### 1. GitLab Pipeline Types

#### Branch Pipelines
The default pipeline type. Runs automatically whenever a commit is pushed to a branch in the repository.

#### Merge Request Pipelines
Runs specifically on the code changes inside a Merge Request (MR). These pipelines are critical because they run in the context of the target branch merge, helping prevent integration issues before code is merged.

#### Scheduled Pipelines
Runs automatically based on time schedules (configured using cron syntax). Excellent for running nightly security scans, generating weekly reports, or purging temporary environments.

---

### 2. Multi-Project and Parent-Child Pipelines
To manage complex build dependencies, you can configure downstream pipelines:

```
               PARENT-CHILD PIPELINE (Single Repo)
+-------------------------------------------------------------+
|                     PARENT PIPELINE                         |
|  [build-job] ===> [trigger-child]                           |
|                         ||                                  |
|                         v Trigger                           |
|                   +----------------------------------+      |
|                   |          CHILD PIPELINE          |      |
|                   |  - Run sub-module unit tests     |      |
|                   +----------------------------------+      |
+-------------------------------------------------------------+

             MULTI-PROJECT PIPELINE (Multiple Repos)
+------------------------+             +----------------------+
|   API BACKEND REPO     |             |  FRONTEND APP REPO   |
|  [release-api-job]     |==(Trigger)=>|  [run-e2e-tests]     |
+------------------------+             +----------------------+
```

- **Parent-Child Pipelines (Mono-repo)**: A parent pipeline in the root directory triggers child pipelines in sub-directories. This allows you to split configurations for different modules (e.g., separating the `/backend` pipeline from the `/frontend` pipeline).
- **Multi-Project Pipelines (Multi-repo)**: A pipeline in one code repository triggers a pipeline in a completely separate repository (e.g., updating the backend API triggers integration tests in the frontend repository).

---

### 3. Pipeline Visualization
GitLab provides a graphical visualization graph of your running pipeline:
- **Stages**: Displayed as columns.
- **Jobs**: Displayed as node cards within stages.
- **Dependencies**: Indicated by connector lines, showing the execution path mapped by `needs` configurations.

---

## Code Examples and Walkthroughs

### 1. Creating a Parent-Child Pipeline
This configuration shows a root parent pipeline (`.gitlab-ci.yml`) that triggers child pipelines dynamically based on which directories have code changes:

```yaml
# .gitlab-ci.yml (Parent Configuration)
stages:
  - triggers

# Trigger the backend pipeline only if files in /backend changed
trigger-backend:
  stage: triggers
  trigger:
    include: backend/.gitlab-ci-backend.yml
    strategy: depend
  rules:
    - changes:
        - backend/**/*

# Trigger the frontend pipeline only if files in /frontend changed
trigger-frontend:
  stage: triggers
  trigger:
    include: frontend/.gitlab-ci-frontend.yml
    strategy: depend
  rules:
    - changes:
        - frontend/**/*
```

> [!NOTE]
> The `strategy: depend` configuration forces the parent pipeline to wait until the child pipeline completes successfully before marking itself as passed.

And here is the child configuration file for the backend:

```yaml
# backend/.gitlab-ci-backend.yml
stages:
  - test

run-backend-tests:
  stage: test
  image: maven:3.8-openjdk-17-slim
  script:
    - cd backend && mvn test
```

---

### 2. Multi-Project Pipeline Trigger (Cross-Repository)
To trigger a pipeline in a completely separate frontend repository when the backend API updates:

```yaml
# backend repository .gitlab-ci.yml
stages:
  - deploy
  - trigger-downstream

deploy-api:
  stage: deploy
  script:
    - echo "Deploying backend API..."

trigger-frontend-tests:
  stage: trigger-downstream
  trigger:
    project: project3-group/angular-frontend-app
    branch: main
```

---

## Summary
- **Branch Pipelines** run on standard branch pushes; **Merge Request Pipelines** run specifically on active code reviews.
- **Scheduled Pipelines** automate periodic tasks (like nightly builds) using standard cron scheduling.
- **Parent-Child Pipelines** isolate configurations within a single repository, while **Multi-Project Pipelines** trigger builds across distinct repositories.
- Downstream pipelines are configured using the **`trigger`** keyword.

---

## Additional Resources
- [GitLab CI/CD: Pipeline Types and Behaviors](https://docs.gitlab.com/ee/ci/pipelines/)
- [GitLab Guide: Multi-Project and Parent-Child Pipelines](https://docs.gitlab.com/ee/ci/pipelines/downstream_pipelines.html)
- [CRON Syntax Reference for Scheduled Pipelines](https://docs.gitlab.com/ee/ci/pipelines/schedules.html)
