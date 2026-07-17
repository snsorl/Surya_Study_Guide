# Lab Exercise: Creating and Verifying a GitLab CI/CD Pipeline

## Learning Objectives
- Write a `.gitlab-ci.yml` pipeline configuration script.
- Define sequential stages for compilation, testing, and manual deployment.
- Configure dependency caching to optimize execution times.
- Validate and debug pipeline syntax.

---

## The Scenario
Your development team is transitioning to automated deployments. You must configure a GitLab CI/CD pipeline (`.gitlab-ci.yml`) in your Project 3 backend repository that automatically compiles the Java code, runs tests on every commit, and exposes a manual gate button to trigger a production deploy.

---

## Tasks

### Task 1: Initialize the Pipeline File
1.  Navigate to your Project 3 Spring Boot root directory.
2.  Create a file named `.gitlab-ci.yml`.
3.  Add the core layout defining three sequential stages:
    ```yaml
    stages:
      - build
      - test
      - deploy
    ```

---

### Task 2: Configure Build and Test Jobs
Add jobs to compile the code and execute testing checks:
1.  **Build Job**: Configure a job using the `maven:3.9.2-eclipse-temurin-17-alpine` base image to compile the application and save the compiled `/target` directory as a build artifact.
2.  **Test Job**: Configure a job in the `test` stage that executes `mvn test`.
3.  **Caching**: Add global cache configurations to preserve Maven dependency folders (`.m2/repository`), reducing compile times on subsequent runs.

---

### Task 3: Configure a Manual Deploy Gate
Add a deployment job that requires manual approval to execute:
1.  Create a job in the `deploy` stage.
2.  Configure the script block to echo "Deploying build artifact to staging/production server...".
3.  Use the **`when: manual`** parameter to create a manual approval button in the GitLab UI, preventing automatic deployment without validation.

---

### Task 4: Verify the Pipeline
Commit and push the file to GitLab, then verify the execution:
1.  Commit your changes and push to your GitLab repository:
    ```bash
    git add .gitlab-ci.yml
    git commit -m "docs: add gitlab ci pipeline configuration"
    git push origin main
    ```
2.  Open your browser, navigate to your GitLab project, and select **Build > Pipelines**.
3.  Verify that:
    - The `build` job runs first, compiling the JAR.
    - The `test` job runs automatically after compilation succeeds.
    - The `deploy` job stays in a **Paused** state, displaying a manual play icon.
4.  Click the play icon to trigger the manual deployment and verify the run completes successfully.

---

## Definition of Done
- A valid `.gitlab-ci.yml` file is saved in the repository root.
- The pipeline executes automatically in GitLab on push.
- The `build` and `test` stages run and pass.
- The `deploy` stage pauses for manual trigger and completes when clicked.

---

## Troubleshooting Tips
- **Pipeline Syntax Errors**: Use the built-in **GitLab CI Lint Tool** (located in the GitLab interface under *Build > Pipeline editor > Lint*) to validate your YAML formatting.
- **Yum or Package Download Timeouts**: Ensure your runners have outbound internet access to download Maven dependencies and base images.
