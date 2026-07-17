# GitLab CI/CD Component Breakdown: Configuration Structure and Reusability

## Learning Objectives
- Map the global structure of a `.gitlab-ci.yml` pipeline configuration file.
- Configure global hooks using `before_script` and `after_script`.
- Implement DRY (Don't Repeat Yourself) pipeline code using `extends` and YAML anchors.
- Organize complex configurations across multiple files using `include` statements.

---

## Why This Matters
As applications scale into microservice architectures, their CI/CD pipeline configurations grow rapidly. A single `.gitlab-ci.yml` file can easily grow to thousands of lines of code, making it difficult to read, maintain, and debug.

To manage this complexity, GitLab CI/CD provides structures to split your configuration into modular components. By mastering global configurations, inheritance model patterns, and remote template imports, you can build clean, reusable, and maintainable pipelines.

---

## The Concept

### 1. Global Structure of `.gitlab-ci.yml`
A standard GitLab pipeline configuration consists of several top-level properties:
- **`stages`**: An ordered list of pipeline stages (e.g., `build`, `test`, `deploy`). This defines the execution sequence.
- **`variables`**: Global variables accessible by all jobs in the pipeline.
- **`default`**: Default configurations applied to all jobs (e.g., global Docker images or retry rules).

---

### 2. Global Script Hooks
- **`before_script`**: Runs immediately before a job's primary `script` commands execute. Best for environment initialization, installing dependencies, or authenticating with container registries.
- **`after_script`**: Runs after a job completes, even if the primary script fails. Best for cleanup tasks, collecting test reports, or sending notifications.

---

### 3. Reusability Patterns

#### Extends (Inheritance)
The **`extends`** keyword allows a job to inherit configurations from a template job (often named with a leading dot, like `.common-config`, to prevent GitLab from running it as an active job). This prevents copying and pasting configuration settings across multiple jobs.

#### Include (Modularization)
The **`include`** keyword allows you to split a large configuration into multiple files. You can load configurations from:
- **Local files** in the same repository.
- **Other projects** hosted on the same GitLab instance.
- **Remote HTTPS URLs** (e.g., standardized company pipeline templates).

```
         Root Configuration File (.gitlab-ci.yml)
+-------------------------------------------------+
|  include:                                       |
|    - local: '/ci/templates/java-build.yml'      |
|    - local: '/ci/templates/docker-deploy.yml'   |
+------------------------t------------------------+
                         | Imports configuration blocks
                         v
  +----------------------+----------------------+
  |                                             |
  v                                             v
 /ci/templates/java-build.yml           /ci/templates/docker-deploy.yml
 [compile-job definition]               [deploy-job definition]
```

---

## Code Examples and Walkthroughs

### 1. Complex Reusable Pipeline Configuration
This example demonstrates a modular `.gitlab-ci.yml` file using global variables, script hooks, inheritance templates, and splits:

```yaml
# Define pipeline stages
stages:
  - build
  - test
  - deploy

# Default configurations applied to all jobs
default:
  image: openjdk:17-slim
  before_script:
    - echo "Initializing job runtime environment..."
    - java -version

# Global variables
variables:
  APP_NAME: "project3-backend"
  DEPLOY_REGION: "us-east-1"

# 1. Base Job Template (Abstract, starts with a dot)
.test-template:
  stage: test
  script:
    - echo "Running tests for $APP_NAME..."
    - ./gradlew test
  after_script:
    - echo "Collecting test outputs..."
    - tar -czf reports.tar.gz build/reports/
  artifacts:
    paths:
      - reports.tar.gz
    expire_in: 3 days

# 2. Concrete Job inheriting from the template
unit-test-job:
  extends: .test-template
  variables:
    TEST_SUITE: "unit"
  script:
    - ./gradlew test --tests "*Unit*"

integration-test-job:
  extends: .test-template
  variables:
    TEST_SUITE: "integration"
  script:
    - ./gradlew test --tests "*Integration*"
```

---

### 2. Splitting Configuration Files using `include`
Below is a clean root `.gitlab-ci.yml` file that imports job definitions from local sub-directories:

```yaml
# .gitlab-ci.yml (Root Configuration)
stages:
  - build
  - deploy

# Import modular yml files from a /ci folder
include:
  - local: '/ci/build-pipeline.yml'
  - local: '/ci/deploy-pipeline.yml'
```

And here is one of the imported templates, defining build jobs:

```yaml
# /ci/build-pipeline.yml
compile-java-app:
  stage: build
  image: maven:3.8-openjdk-17-slim
  script:
    - mvn clean package -DskipTests
  artifacts:
    paths:
      - target/*.jar
```

---

## Summary
- The **`.gitlab-ci.yml`** file organizes pipelines using top-level properties like **`stages`**, **`variables`**, and **`default`**.
- **`before_script`** and **`after_script`** run commands before and after jobs to handle environment setup and cleanup.
- **`extends`** allows jobs to inherit configurations from template jobs, reducing duplication.
- The **`include`** keyword splits large configurations into modular, maintainable files.

---

## Additional Resources
- [GitLab CI/CD: Includes Keyword reference](https://docs.gitlab.com/ee/ci/yaml/includes.html)
- [Optimize GitLab Pipelines: Reuse configuration templates](https://docs.gitlab.com/ee/ci/yaml/index.html#extends)
- [YAML Anchors and Aliases in GitLab CI/CD](https://docs.gitlab.com/ee/ci/yaml/yaml_optimization.html)
