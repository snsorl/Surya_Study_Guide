# Continuous Delivery (CD): Pipelines, Environments, and Approval Gates

## Learning Objectives
- Define Continuous Delivery and distinguish it from Continuous Deployment.
- Map the promotion of code through deployment environments: Development, Staging, User Acceptance Testing (UAT), and Production.
- Explain the role of human approval gates in release pipelines.
- Evaluate when to choose Continuous Delivery over Continuous Deployment.

---

## Why This Matters
For many industries (such as healthcare, banking, or government services), deploying code automatically to production is not allowed. Regulations often require formal compliance audits, sign-offs, or scheduled release windows before new features can go live.

This is where **Continuous Delivery** is used. Continuous Delivery ensures that your codebase is *always ready* to be deployed to production, automating the build, test, and staging phases. However, the final push to production is kept behind a manual approval gate. This gives you the speed of automation while keeping release control in human hands.

---

## The Concept

### 1. Continuous Delivery vs. Continuous Deployment
While both practices share the abbreviation "CD," they differ in their final release step:

```
                          CONTINUOUS DELIVERY
 +------------+     +------------+     +------------+     +------------+
 | CI Pipeline|====>|  Staging   |====>|   MANUAL   |====>| Release to |
 | Build/Test |     | Deployment |     |  APPROVAL  |     | Production |
 +------------+     +------------+     +-----+------+     +------------+
                                             |
                                             v Manual Gate
                                             
                         CONTINUOUS DEPLOYMENT
 +------------+     +------------+     +------------+     +------------+
 | CI Pipeline|====>|  Staging   |====>| AUTOMATED  |====>| Release to |
 | Build/Test |     | Deployment |     | VALIDATION |     | Production |
 +------------+     +------------+     +------------+     +------------+
                                        No Manual Gate
```

- **Continuous Delivery**: Every change that passes your automated tests is automatically deployed to a testing or staging environment. However, deploying the update to production requires manual authorization (e.g., clicking a button in GitLab CI).
- **Continuous Deployment**: The entire process is automated. Every code change that passes the testing pipeline is automatically deployed directly to production users.

---

### 2. Environment Promotion Topology
To ensure stability, applications are promoted through a sequence of isolated cloud environments:

#### 1. Development (Dev)
Where developers merge feature branches. The environment is unstable, uses simulated datasets, and is used for active integration testing.

#### 2. Staging / Pre-Production (Stg)
An exact replica of the production environment's configuration. Used to test deployment scripts, verify configurations, and run automated integration tests.

#### 3. User Acceptance Testing (UAT)
Where business stakeholders and QA teams verify features manually, checking that the software meets business requirements.

#### 4. Production (Prod)
The live application environment accessed by real users. Access is highly restricted, and changes are carefully audited.

---

### 3. Manual Approval Gates in CI/CD Tools
CI/CD tools (like GitLab CI/CD or GitHub Actions) support configuration keywords to implement manual gates:
- **GitLab `when: manual`**: Pauses the pipeline at a specific job stage. The job will not execute until an authorized user clicks the Play button.
- **GitHub Environments & Reviewers**: Allows you to restrict deployments to specific environments (like `production`) until designated reviewers approve the run.

---

## Code Examples and Walkthroughs

### 1. Implementing a Manual Release Gate in GitLab CI/CD (YAML)
This configuration demonstrates how to set up automated staging deployments, followed by a manual approval gate for production releases:

```yaml
# A pipeline config illustrating Continuous Delivery
stages:
  - build
  - deploy_staging
  - deploy_production

# Automated Staging Job: Runs automatically when code merges to main
deploy_to_staging:
  stage: deploy_staging
  script:
    - echo "Deploying build artifact to Staging ECS Cluster..."
    - ./deploy-scripts/ecs-update.sh staging
  environment:
    name: staging

# Continuous Delivery Production Job: Staged and waiting for manual click
deploy_to_production:
  stage: deploy_production
  script:
    - echo "Deploying build artifact to Production ECS Cluster..."
    - ./deploy-scripts/ecs-update.sh production
  environment:
    name: production
  # The critical manual gate configuration:
  when: manual
  # Restrict execution to the main branch
  only:
    - main
```

---

## Summary
- **Continuous Delivery** automates the build, test, and staging process, ensuring the code is always release-ready, but keeps the production deployment behind a manual gate.
- **Continuous Deployment** automates the entire release cycle, shipping every passing commit directly to production users.
- **Environment promotion** routes applications through Dev, Staging, and UAT environments before releasing to Production.
- CI/CD tools use manual triggers (like `when: manual` in GitLab) to create release approval gates.

---

## Additional Resources
- [Continuous Delivery: Reliable Software Releases through Build, Test, and Deployment Automation](https://jumble.org/continuous-delivery/)
- [GitLab CI/CD: Manual Jobs for Release Gates](https://docs.gitlab.com/ee/ci/jobs/job_control.html#run-a-job-manually)
- [Managing GitHub Environments and Deployment Reviewers](https://docs.github.com/en/actions/deployment/targeting-different-environments/using-environments-for-deployment)
