# Using Terraform with GitLab CI: Infrastructure as Code (IaC) Pipelines

## Learning Objectives
- Automate Terraform execution stages (Init, Plan, Apply, Destroy) inside a GitLab CI pipeline.
- Configure GitLab-Managed Terraform State storage.
- Pass Terraform plan outputs as Merge Request artifacts for review.
- Set up secure, manual approval gates before running infrastructure changes.

---

## Why This Matters
**Infrastructure as Code (IaC)** tools like Terraform allow you to define cloud resources (like EC2 instances, RDS databases, or VPC subnets) in declarative configuration files. However, running Terraform commands manually from a developer workstation is risky:
- The state file (which tracks the active cloud setup) can become corrupted if multiple developers run updates simultaneously.
- Access keys must be distributed locally, increasing the risk of credential leaks.
- Outages can occur if changes are applied without team review.

By running Terraform within a GitLab CI pipeline, you solve these problems. GitLab manages state locking, runs compliance scans, and posts execution plan summaries directly onto Merge Requests for review before updates are applied.

---

## The Concept

### 1. The Terraform Pipeline Workflow
A standard IaC deployment pipeline consists of three core phases:
1.  **Initialize (`terraform init`)**: Downloads provider plugins (like the AWS provider) and configures backend state storage.
2.  **Plan (`terraform plan`)**: Analyzes the configuration files, compares them to the active state, and outputs a list of changes (resources to create, modify, or destroy). This plan is exported as an artifact.
3.  **Apply (`terraform apply`)**: Executes the changes in the cloud. In production pipelines, this step is placed behind a manual approval gate.

---

### 2. GitLab-Managed Terraform State Backend
Terraform uses a **state file** (`terraform.tfstate`) to track the mappings between your configuration files and actual cloud resources.
- GitLab provides a built-in, secure **Terraform State HTTP Backend**.
- This backend is integrated with GitLab projects, providing automated encryption at rest, secure state locking (preventing concurrent runs from corrupting data), and version tracking out-of-the-box.

---

### 3. Integrating Plans into Merge Requests
A best practice for IaC is to display the `terraform plan` output directly in the Merge Request.
- Team members can review the exact infrastructure changes (e.g., verifying that a database volume is not being accidentally deleted) before merging code.
- Once the Merge Request is approved and merged to the main branch, the pipeline executes the `apply` stage to provision the resources.

```
       Developer creates Merge Request with TF changes
                             |
                             v
           +-----------------------------------+
           |        CI RUNS 'TF PLAN'          |
           |  - Compares config with live state|
           |  - Saves plan file as artifact    |
           +-----------------t-----------------+
                             |
                             v
       Merge Request updated with plan summary review
                             |
                             v Merged to main
           +-----------------v-----------------+
           |       CI WAITS FOR APPROVAL       |
           |  - Manual gate button triggers    |
           |    deploy on main branch          |
           +-----------------t-----------------+
                             | Triggered
                             v
           +-----------------v-----------------+
           |        CI RUNS 'TF APPLY'         |
           |  - Provisions actual AWS resources|
           +-----------------------------------+
```

---

## Code Examples and Walkthroughs

### 1. GitLab-Managed State Backend Configuration
To tell Terraform to use GitLab's secure state backend, configure an empty HTTP backend block in your Terraform configuration file:

```hcl
# backend.tf
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  # An empty HTTP backend configuration block.
  # GitLab CI/CD will inject the connection credentials at runtime automatically.
  backend "http" {}
}
```

---

### 2. Complete GitLab CI/CD Terraform Pipeline
Below is a complete `.gitlab-ci.yml` pipeline that initializes, plans, and applies infrastructure changes securely using GitLab's managed backend:

```yaml
# A production-ready GitLab CI configuration for Terraform
stages:
  - init-plan
  - apply

# Use HashiCorp's official lightweight Terraform CLI image
image: hashicorp/terraform:1.5.0

# Configure variables to map Terraform to GitLab's HTTP state API endpoint
variables:
  TF_STATE_NAME: "production"
  TF_ADDRESS: "${CI_API_V4_URL}/projects/${CI_PROJECT_ID}/terraform/state/${TF_STATE_NAME}"

# Global before_script to configure authentication credentials
before_script:
  - terraform --version
  # Configure the HTTP backend credentials dynamically
  - terraform init 
      -backend-config="address=${TF_ADDRESS}" 
      -backend-config="lock_address=${TF_ADDRESS}/lock" 
      -backend-config="unlock_address=${TF_ADDRESS}/lock" 
      -backend-config="username=gitlab-ci-token" 
      -backend-config="password=${CI_JOB_TOKEN}" 
      -backend-config="lock_method=POST" 
      -backend-config="unlock_method=DELETE" 
      -backend-config="retry_wait_min=5"

# Stage 1: Run Terraform Plan
tf-plan:
  stage: init-plan
  script:
    # Run plan and export the plan binary artifact
    - terraform plan -out=tfplan
  artifacts:
    name: "terraform-plan"
    paths:
      - tfplan
    expire_in: 2 days

# Stage 2: Run Terraform Apply (Manual Gate)
tf-apply:
  stage: apply
  script:
    # Apply the plan artifact generated in the previous stage
    - terraform apply -auto-approve tfplan
  dependencies:
    - tf-plan
  # Restrict apply to the main branch
  only:
    - main
  # Force human confirmation before provisioning
  when: manual
```

---

## Summary
- **Infrastructure as Code (IaC)** pipelines automate cloud provisioning safely within the CI/CD pipeline.
- The standard workflow runs **`init`** $\rightarrow$ **`plan`** $\rightarrow$ **`apply`**, passing the plan artifact between jobs.
- **GitLab-Managed State Backend** secures the Terraform state file, providing S3-like locking and version tracking out-of-the-box.
- **Manual approval gates** prevent untracked infrastructure changes from deploying to production without team review.

---

## Additional Resources
- [GitLab Infrastructure as Code: Terraform Integration Guide](https://docs.gitlab.com/ee/user/infrastructure/terraform_state.html)
- [HashiCorp Terraform HTTP Backend Documentation](https://developer.hashicorp.com/terraform/language/settings/backends/http)
- [Best Practices for Managing AWS Credentials in Terraform Pipelines](https://docs.aws.amazon.com/general/latest/gr/aws-access-keys-best-practices.html)
