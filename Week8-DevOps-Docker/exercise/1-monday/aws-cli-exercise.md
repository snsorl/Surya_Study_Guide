# Lab Exercise: Infrastructure Auditing and Storage Management using AWS CLI

## Learning Objectives
- Install and configure credentials for the AWS Command Line Interface (CLI).
- Query active infrastructure resources using CLI filters.
- Create and manage Amazon S3 storage buckets.
- Upload application build files to cloud storage via the CLI.
- Verify object storage using bucket list queries.

---

## The Scenario
Your operations team wants to automate deployment tasks to reduce manual setup in the AWS Console. You must configure the AWS CLI on your machine and use it to check running instances, create a secure cloud storage bucket, and upload application build files (the Project 3 Spring Boot JAR) to S3.

---

## Tasks

### Task 1: Configure AWS CLI Credentials
1.  Verify the AWS CLI is installed on your local machine:
    ```bash
    aws --version
    ```
2.  Configure your access credentials:
    ```bash
    aws configure
    ```
    *   **AWS Access Key ID**: Paste your access key.
    *   **AWS Secret Access Key**: Paste your secret key.
    *   **Default region name**: `us-east-1` (or your target region).
    *   **Default output format**: `json`

---

### Task 2: Audit Running EC2 Instances
To verify your CLI credentials, query your active EC2 instances using search filters:
1.  List all running EC2 instances in your region, filtering the JSON output to display only the Instance ID, Public IP address, and State:
    ```bash
    aws ec2 describe-instances \
      --filters "Name=instance-state-name,Values=running" \
      --query "Reservations[*].Instances[*].{ID:InstanceId,IP:PublicIpAddress,State:State.Name}" \
      --output table
    ```
2.  Verify the details match the `project3-api-sandbox` instance launched in the previous exercise.

---

### Task 3: Create an S3 Storage Bucket
1.  Create a unique Amazon S3 bucket to store application build archives. Replace `<your-name>` with your initials to keep the name globally unique:
    ```bash
    aws s3 mb s3://project3-builds-<your-name> --region us-east-1
    ```
2.  List all S3 buckets in your account to verify:
    ```bash
    aws s3 ls
    ```

---

### Task 4: Upload and Verify Build Archives
1.  Upload your compiled Spring Boot JAR file to the S3 bucket:
    ```bash
    aws s3 cp target/project3-api-1.0.0.jar s3://project3-builds-<your-name>/builds/project3-api-1.0.0.jar
    ```
2.  List the contents of your bucket to verify the file was uploaded:
    ```bash
    aws s3 ls s3://project3-builds-<your-name>/builds/
    ```

---

## Definition of Done
- The command `aws configure` runs successfully and connects to your AWS account.
- The EC2 query returns active instance tables.
- The S3 copy command successfully uploads the JAR file to your bucket.
- The `aws s3 ls` command lists the uploaded build file.

---

## Troubleshooting Tips
- **Invalid Signature or Authentication Errors**: Run `aws configure` again to check for typos in your access keys.
- **S3 BucketNameExists Error**: S3 bucket names are shared globally across all AWS accounts. Choose a unique bucket name by adding custom suffixes (like your name or initials).
