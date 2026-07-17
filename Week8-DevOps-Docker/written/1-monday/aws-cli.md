# AWS CLI: Configuration, Credential Management, and Core Operations

## Learning Objectives
- Install and configure the AWS Command Line Interface (CLI) on local development environments.
- Explain the role of credentials files and configure Named Profiles.
- Execute basic resource management commands for AWS S3, EC2, ECS, and DynamoDB.
- Secure local configuration credentials by applying the least privilege access model.

---

## Why This Matters
While the AWS Management Console is excellent for visual exploration and learning, it is inefficient for repetitive tasks, deployment pipelines, or automation scripts. A DevOps engineer cannot click through a web console to launch dozens of servers, update code packages, or manage backup cycles daily.

The AWS CLI provides a direct interface to manage AWS services from your terminal. It allows you to script infrastructure operations, integrate AWS queries directly into continuous delivery scripts, and execute operational tasks programmatically. Mastering the CLI is key to automating deployment tasks.

---

## The Concept

### 1. What is the AWS CLI?
The **AWS Command Line Interface (CLI)** is an open-source tool built on top of the Python SDK (`boto3`). It translates shell commands directly into AWS REST API requests, parses JSON responses, and outputs formatted data back to the terminal.

---

### 2. Configuration & Identity Management
To make API requests, the CLI must authenticate with AWS using access keys generated from the Identity and Access Management (IAM) service:
- **AWS Access Key ID**: A unique identifier (analogous to a username).
- **AWS Secret Access Key**: A cryptographic key used to sign requests (analogous to a password).
- **Default Region Name**: The physical geographic region where commands are routed by default (e.g., `us-east-1`).
- **Default Output Format**: Defines how the CLI formats responses (e.g., `json`, `text`, `table`).

These credentials are saved locally in the user's home folder under the `.aws` directory:
- `~/.aws/credentials`: Contains sensitive access keys.
- `~/.aws/config`: Contains regional and formatting configurations.

---

### 3. Named Profiles (Handling Multiple Environments)
By default, configuration details are stored in a profile named `default`. If you manage multiple AWS accounts (e.g., one for personal learning, one for a project development environment, and one for a staging system), you can configure **Named Profiles**.
You trigger commands against a specific profile by appending the `--profile` flag or setting the `AWS_PROFILE` environment variable in your terminal session.

```
       Local Workstation (.aws/credentials)
+-------------------------------------------------+
|  [default]                                      |
|  aws_access_key_id = AKIA123...                 |
|                                                 |
|  [project3-dev]                                 |
|  aws_access_key_id = AKIA789...                 |
+------------------------t------------------------+
                         |
      AWS CLI Request    | --profile project3-dev
                         v
+-------------------------------------------------+
|              AWS CLOUD ENVIRONMENT             |
|                                                 |
|  Routes request to Dev Account using            |
|  project3-dev credentials                       |
+-------------------------------------------------+
```

---

## Code Examples and Walkthroughs

### 1. Configuration Walkthrough
Setting up your default credential workspace is interactive:

```bash
# 1. Start the interactive configuration wizard
aws configure

# 2. Complete the prompts (example credentials shown):
# AWS Access Key ID [None]: AKIAIOSFODNN7EXAMPLE
# AWS Secret Access Key [None]: wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY
# Default region name [None]: us-east-1
# Default output format [None]: json

# 3. Create a named profile for Project 3 Development
aws configure --profile project3-dev

# Prompts will repeat, allowing you to configure a distinct account target
```

Let's look at how the CLI writes these files under the hood:

```ini
# ~/.aws/config
[default]
region = us-east-1
output = json

[profile project3-dev]
region = us-west-2
output = table
```

```ini
# ~/.aws/credentials
[default]
aws_access_key_id = AKIAIOSFODNN7EXAMPLE
aws_secret_access_key = wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY

[project3-dev]
aws_access_key_id = AKIAPROJECT3DEVKEY
aws_secret_access_key = Project3SecretAccessKeyExample
```

---

### 2. Core Service Commands Reference

#### Amazon S3 (Simple Storage Service)
Used to copy artifacts (like compiled JAR files) to secure cloud storage:

```bash
# Create an S3 storage bucket
aws s3 mb s3://project3-deploy-bucket-unique

# Copy a local Spring Boot application JAR file to S3
aws s3 cp target/app.jar s3://project3-deploy-bucket-unique/app.jar

# List the contents of the S3 bucket
aws s3 ls s3://project3-deploy-bucket-unique/
```

#### Amazon EC2 (Elastic Compute Cloud)
Used to monitor and manage virtual servers:

```bash
# List all running instances in the current region, returning only IDs and Public IPs
aws ec2 describe-instances \
    --filters "Name=instance-state-name,Values=running" \
    --query "Reservations[*].Instances[*].{ID:InstanceId,IP:PublicIpAddress}" \
    --output table
```

#### Amazon ECS (Elastic Container Service)
Used to run and inspect tasks:

```bash
# Trigger an ECS task run inside a specific cluster using a task definition
aws ecs run-task \
    --cluster project3-cluster \
    --task-definition springboot-api-task \
    --launch-type FARGATE \
    --network-configuration "awsvpcConfiguration={subnets=[subnet-12345],securityGroups=[sg-67890],assignPublicIp=ENABLED}"
```

#### Amazon DynamoDB
Used for quick database operations:

```bash
# Insert a record into a DynamoDB metadata tracking table
aws dynamodb put-item \
    --table-name UserSessions \
    --item '{"SessionId": {"S": "session-xyz-101"}, "Username": {"S": "john_doe"}, "TTL": {"N": "1719840000"}}'
```

---

## Summary
- The **AWS CLI** converts terminal commands into AWS REST API calls, returning structured JSON or table formats.
- Configuration credentials are saved in the user's **`~/.aws`** home folder inside configuration and credentials files.
- **Named Profiles** allow developers to switch between multiple AWS environments securely.
- Common commands permit scripted automation across core AWS compute, container, database, and storage platforms.

---

## Additional Resources
- [AWS CLI Installation Guide (Windows/macOS/Linux)](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
- [AWS CLI Command Reference Index](https://awscli.amazonaws.com/v2/documentation/api/latest/index.html)
- [AWS Guide: Named Profiles Configuration](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-profiles.html)
