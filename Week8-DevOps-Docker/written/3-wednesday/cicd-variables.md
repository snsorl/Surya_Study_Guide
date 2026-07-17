# GitLab CI/CD Variables: Types, Scopes, and Secrets Management

## Learning Objectives
- Differentiate between Predefined, Project-Level, and Group-Level variables in GitLab CI/CD.
- Leverage predefined system variables (e.g., `CI_COMMIT_SHA`, `CI_JOB_TOKEN`) in scripts.
- Securely store and use sensitive credentials using Masked and Protected variables.
- Pass variables and data outputs between pipeline jobs using dotenv artifacts.

---

## Why This Matters
Software pipelines require configurations, API endpoints, and credentials to build and deploy applications. Hardcoding database passwords, AWS access keys, or API tokens directly into your `.gitlab-ci.yml` file is a major security risk: anyone with access to the repository can read your secrets, leaving your infrastructure vulnerable to exploitation.

GitLab CI/CD variables solve this by separating pipeline configuration from secrets management. You store sensitive credentials securely in GitLab settings and pass them to jobs dynamically at runtime.

---

## The Concept

### 1. Types of Variables

#### Predefined Variables
Built-in variables provided automatically by GitLab for every running job. They describe the git commit, branch, runner environment, and pipeline status. E.g.:
- `CI_COMMIT_SHA`: The commit hash that triggered the pipeline. Excellent for tagging container images uniquely.
- `CI_COMMIT_REF_NAME`: The branch or tag name currently building.
- `CI_JOB_TOKEN`: A short-lived security token used to authenticate with GitLab API registries (like pulling Docker images from the project registry).

#### Project-Level Variables
Configured in your project's settings under **Settings > CI/CD > Variables**. They are accessible by all jobs in the project's pipeline.

#### Group-Level Variables
Configured at the GitLab Group level. They are inherited automatically by all sub-projects within the group, making it easy to share database configurations or API keys across multiple microservices.

---

### 2. Securing Secrets: Masked and Protected Variables
When creating custom variables in GitLab, you can enable security controls:
- **Protected Variables**: Only accessible by jobs running on protected branches (like `main`). This prevents developers from printing secret tokens on unauthorized feature branches.
- **Masked Variables**: GitLab automatically filters and redacts the variable value from the job logs, replacing it with `[MASKED]` if a script accidentally prints it.

```
       Developer pushes code to feature branch
                         |
                         v
       GitLab CI runner requests variables:
  +-------------------------------------------------+
  | DB_PASSWORD (Masked, Protected)                 |
  |                                                 |
  | Is the branch protected (e.g., main)?          |
  |  - YES: Inject value securely.                 |
  |  - NO: Block access to prevent leakage.        |
  +------------------------t------------------------+
                           | Injection (Main branch)
                           v
  +-------------------------------------------------+
  | Run script: echo "Database password is: $DB_PWD"|
  | Console Log Output: "Database password is: [MASKED]" |
  +-------------------------------------------------+
```

---

### 3. Passing Variables Between Jobs
Jobs run in isolated Docker containers, so variables modified by a script in one job are lost when the container terminates.
To pass dynamically generated variables to downstream jobs:
1. Save the variables to a `.env` file (format: `KEY=VALUE`).
2. Export the `.env` file as a **`reports:dotenv`** artifact.
3. GitLab automatically parses the `.env` file and injects the variables into subsequent jobs.

---

## Code Examples and Walkthroughs

### 1. Utilizing Predefined Variables in `.gitlab-ci.yml`
This configuration uses predefined variables to tag a build artifact uniquely with the branch name and commit hash:

```yaml
stages:
  - build

compile-and-tag:
  stage: build
  image: maven:3.8-openjdk-17-slim
  script:
    - echo "Building project for branch $CI_COMMIT_REF_NAME"
    - ./mvnw package -DskipTests
    # Rename the jar using the commit hash for unique version tracking
    - mv target/app.jar target/app-$CI_COMMIT_SHA.jar
  artifacts:
    paths:
      - target/*.jar
```

---

### 2. Passing Dynamic Variables Between Jobs using Dotenv
This pipeline dynamically generates a version number in the first job and passes it to the deploy job:

```yaml
stages:
  - build
  - deploy

# Job 1: Generate version metadata
generate-version-metadata:
  stage: build
  image: alpine:latest
  script:
    # Calculate a unique release version name
    - echo "APP_VERSION=1.0.0-$CI_COMMIT_SHORT_SHA" > build.env
  artifacts:
    reports:
      # Export the dotenv file to pass variables to downstream jobs
      dotenv: build.env

# Job 2: Deploy using the inherited version variable
deploy-app:
  stage: deploy
  image: alpine:latest
  script:
    # $APP_VERSION is resolved automatically from the build.env artifact
    - echo "Deploying version $APP_VERSION to production..."
```

---

## Summary
- **Predefined Variables** (like `CI_COMMIT_SHA`) provide metadata about the running git state automatically.
- **Project-Level** and **Group-Level** variables manage configurations centrally, separating credentials from code.
- **Masked** variables are redacted from job logs, and **Protected** variables are restricted to secure branches.
- Use **`reports:dotenv`** artifacts to pass dynamically generated variables between jobs.

---

## Additional Resources
- [GitLab CI/CD Predefined System Variables Reference](https://docs.gitlab.com/ee/ci/variables/predefined_variables.html)
- [GitLab Guide: Creating and using custom CI/CD variables](https://docs.gitlab.com/ee/ci/variables/)
- [Secrets Management and Masking Restrictions in GitLab](https://docs.gitlab.com/ee/ci/variables/#mask-a-cicd-variable)
