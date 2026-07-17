# Technical Interview Question Bank: Week 8 - DevOps & Docker

This document contains 75 standard technical interview questions categorized by daily topic areas, mapped to the **70-25-5 Difficulty Rule** (70% Beginner, 25% Intermediate, 5% Advanced). Use this resource for self-quizzing and interview preparation.

---

## Monday: Cloud Infrastructure (EC2, ECS, ASG, API Gateway, EBS)

### Beginner (Recall/Definition)

#### Q1: What is the primary difference between a public subnet and a private subnet in an AWS VPC?
**Keywords:** Routing Table, Internet Gateway, Private IP, NAT Gateway
<details>
<summary>Click to Reveal Answer</summary>

A public subnet has a route to an Internet Gateway in its routing table, allowing resource instances to communicate directly with the public internet. A private subnet does not have a route to the internet gateway and relies on a NAT Gateway in the public subnet for outbound-only internet traffic.
</details>

---

#### Q2: What is the purpose of an Amazon Machine Image (AMI)?
**Keywords:** Template, Operating System, Blueprint, Launching Instances
<details>
<summary>Click to Reveal Answer</summary>

An AMI is a pre-configured read-only template that contains the operating system, server configurations, and installed applications required to launch new EC2 instances quickly.
</details>

---

#### Q3: What is the difference between AWS Security Groups and Network Access Control Lists (NACLs)?
**Keywords:** Stateful, Stateless, Instance Level, Subnet Level
<details>
<summary>Click to Reveal Answer</summary>

Security Groups act as stateful firewalls at the instance level, meaning inbound rules automatically permit matching outbound traffic. NACLs act as stateless firewalls at the subnet level, requiring explicit inbound and outbound rules for traffic connections.
</details>

---

#### Q4: Why is it critical to run `chmod 400` on a private key (`.pem`) file before connecting to an EC2 instance via SSH?
**Keywords:** Read-only, Permissions, Owner, Unsecured Key
<details>
<summary>Click to Reveal Answer</summary>

Running `chmod 400` limits file access to read-only for the file owner. If permissions are too open (allowing group or global read access), the SSH client will reject the key as unsecured.
</details>

---

#### Q5: What is the purpose of an AWS Auto Scaling Group (ASG)?
**Keywords:** High Availability, Scaling Policies, CloudWatch Alarms, Desired Capacity
<details>
<summary>Click to Reveal Answer</summary>

An ASG automatically adjusts the number of EC2 instances running in your VPC. It monitors metrics (like CPU usage) and uses scaling policies to launch or terminate instances, ensuring high availability and cost optimization.
</details>

---

#### Q6: Explain the difference between Fargate and EC2 launch types in Amazon ECS.
**Keywords:** Serverless, Infrastructure Management, Host Patching, Control
<details>
<summary>Click to Reveal Answer</summary>

AWS Fargate is a serverless launch type where AWS manages and sizes the underlying host servers automatically. The EC2 launch type requires you to launch, scale, patch, and manage the underlying EC2 host servers yourself.
</details>

---

#### Q7: What is the role of an Amazon EBS volume snapshot?
**Keywords:** Backup, Incremental, Point-in-time, S3
<details>
<summary>Click to Reveal Answer</summary>

An EBS snapshot is an incremental, point-in-time backup of an EBS volume stored securely in Amazon S3. Only block changes since the last snapshot are saved, optimizing backup storage costs.
</details>

---

#### Q8: What does AWS API Gateway do in a microservices architecture?
**Keywords:** Proxy, Entry Point, Rate Limiting, CORS, Authentication
<details>
<summary>Click to Reveal Answer</summary>

API Gateway acts as a single, secure entry point for clients, routing HTTP/REST API calls to backend services (like EC2 or Lambda) while handling rate limiting, authentication, and CORS checks.
</details>

---

#### Q9: What are the risk implications of sharing unredacted logs with AI tools for query formatting?
**Keywords:** PII leakage, Compliance, Secrets, Security Exposure
<details>
<summary>Click to Reveal Answer</summary>

Sharing raw logs can leak Personally Identifiable Information (PII) or secrets (like API keys, passwords) to third-party AI endpoints, violating data compliance standards (like GDPR or HIPAA).
</details>

---

#### Q10: What is the difference between Fail-Open and Fail-Closed designs in automated hooks?
**Keywords:** Availability, Strict Security, Fallback, Block
<details>
<summary>Click to Reveal Answer</summary>

A Fail-Open design allows execution to proceed if the scanner tool is offline, prioritizing system availability. A Fail-Closed design blocks the process if the scanning tool is offline, prioritizing strict security.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: You launched an EC2 instance hosting a Spring Boot app on port 8080. The application started successfully, but you cannot access it from your browser. How do you troubleshoot this?
**Hint:** Think about firewall rules.
<details>
<summary>Click to Reveal Answer</summary>

First, verify that the instance's Security Group inbound rules permit TCP traffic on port `8080` from your IP address or anywhere (`0.0.0.0/0`). Second, ensure that the Spring Boot process is listening on the public interface (`0.0.0.0`) and not just `127.0.0.1` locally.
</details>

---

#### Q12: Why would you choose Provisioned IOPS SSD (io2) over General Purpose SSD (gp3) for database storage volumes?
**Keywords:** Latency, Consistent, I/O Operations, Throughput
<details>
<summary>Click to Reveal Answer</summary>

Choose Provisioned IOPS SSD (io2) for database engines that require consistent, high-speed input/output operations with low latency. general-purpose SSDs (gp3) are better suited for standard developer workloads with occasional performance bursts.
</details>

---

#### Q13: How does API Gateway handle client request surges using the Token Bucket algorithm?
**Keywords:** Throttling, Burst Capacity, Steady State, 429 Error
<details>
<summary>Click to Reveal Answer</summary>

The Token Bucket algorithm refills a bucket with request tokens at a steady rate. If a client sends a traffic burst, they consume tokens from the bucket; if the bucket runs out, API Gateway throttles subsequent requests, returning a `429 Too Many Requests` error.
</details>

---

#### Q14: When designing custom "Golden AMIs" for auto-scaling pools, what items should be baked into the image versus configured at boot?
**Keywords:** Static Dependencies, Dynamic Configs, Boot Times, Environment
<details>
<summary>Click to Reveal Answer</summary>

Bake static dependencies (such as the OS, Java runtimes, and monitoring agents) into the Golden AMI to reduce instance boot times. Pass dynamic configurations (like database passwords, profiles, or environment variables) at boot using EC2 User Data or parameter vaults.
</details>

---

### Advanced (Deep Dive/System)

#### Q15: Design a highly available, fault-tolerant EC2 architecture for a REST API. How do you configure VPC subnets, scaling, and load balancing boundaries?
**Keywords:** Multi-AZ, Private Subnet, ALB, ASG, Route 53
<details>
<summary>Click to Reveal Answer</summary>

Place the EC2 instances inside an Auto Scaling Group spanning multiple Availability Zones (Multi-AZ) in private subnets. Place an Application Load Balancer (ALB) in public subnets across the same zones to distribute incoming traffic. Use Route 53 to map domain requests to the ALB, and route database calls to a Multi-AZ RDS database in private subnets.
</details>

---

## Tuesday: DevOps & AWS Bedrock (CI/CD, Bedrock API, Sonar)

### Beginner (Recall/Definition)

#### Q1: What is the primary focus of Continuous Integration (CI) versus Continuous Deployment (CD)?
**Keywords:** Automation, Merging, Testing, Production Release
<details>
<summary>Click to Reveal Answer</summary>

Continuous Integration (CI) focuses on developers merging code changes to a shared repository, triggering automated builds and testing steps. Continuous Deployment (CD) automates releasing those tested builds directly to production without human intervention.
</details>

---

#### Q2: What are the key phases of the DevOps "Infinite Loop"?
**Keywords:** Plan, Build, Test, Release, Deploy, Monitor
<details>
<summary>Click to Reveal Answer</summary>

The DevOps lifecycle loop consists of: Plan $\rightarrow$ Code $\rightarrow$ Build $\rightarrow$ Test $\rightarrow$ Release $\rightarrow$ Deploy $\rightarrow$ Operate $\rightarrow$ Monitor, repeating continuously.
</details>

---

#### Q3: What is the role of AWS Bedrock in modern application development?
**Keywords:** Unified API, Foundation Models, Serverless, Tokenization
<details>
<summary>Click to Reveal Answer</summary>

AWS Bedrock is a serverless, managed service that provides access to foundation models (such as Anthropic Claude or Meta Llama) from different vendors using a single unified API.
</details>

---

#### Q4: Explain the difference between SonarCloud and SonarLint.
**Keywords:** IDE Plugin, Local Analysis, Cloud Service, Pipeline Gate
<details>
<summary>Click to Reveal Answer</summary>

SonarLint is an IDE plugin that analyzes code quality locally in real-time as you write it. SonarCloud is a cloud service integrated into CI pipelines that scans code on commit and enforces Quality Gates.
</details>

---

#### Q5: What is the purpose of the "Shift-Left" testing paradigm?
**Keywords:** Early Detection, Cost Reduction, Security Scan, Lifecycle
<details>
<summary>Click to Reveal Answer</summary>

Shift-Left means running testing, security scanning, and quality analysis early in the development lifecycle (in the IDE or commit phase) rather than waiting until deployment, reducing the cost of fixing bugs.
</details>

---

#### Q6: What does the "temperature" parameter control in AWS Bedrock model requests?
**Keywords:** Randomness, Deterministic, Variability, Choices
<details>
<summary>Click to Reveal Answer</summary>

Temperature controls the randomness of model responses. A value of `0.0` produces highly deterministic, consistent outputs, while `1.0` allows for creative variability.
</details>

---

#### Q7: In the OWASP Top 10, what is "Session Fixation"?
**Keywords:** Hijack, Session Identifier, Login, Pre-Auth
<details>
<summary>Click to Reveal Answer</summary>

Session Fixation is an authentication exploit where an attacker forces a pre-defined session ID onto a victim. If the application does not regenerate the session ID on login, the attacker can hijack the authenticated session.
</details>

---

#### Q8: What does the DORA metric "Mean Time to Recover" (MTTR) measure?
**Keywords:** Outage, Restore Service, Severity Incident, Duration
<details>
<summary>Click to Reveal Answer</summary>

MTTR measures the average time required for an organization to restore service after a production outage or high-severity incident occurs.
</details>

---

#### Q9: What is "Model Drift" in machine learning?
**Keywords:** Prediction Decay, Real-world data, Accuracy, Pattern Change
<details>
<summary>Click to Reveal Answer</summary>

Model Drift refers to the decay in model prediction accuracy over time. This happens because the real-world data patterns change compared to the dataset the model was originally trained on.
</details>

---

#### Q10: Why should you avoid using hardcoded JWT secret keys in application files?
**Keywords:** Source Control Leak, Signature Forgery, Configuration, Env Variables
<details>
<summary>Click to Reveal Answer</summary>

Hardcoding JWT secrets risks exposing them in source control. If an attacker gains access to the secret key, they can forge user signatures and bypass authentication controls. Secure them using environment variables.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: Explain the difference between Blue-Green and Canary deployment strategies.
**Keywords:** Two Environments, Routing Traffic, Small Subset, Rollback
<details>
<summary>Click to Reveal Answer</summary>

Blue-Green deployment runs two identical production environments (Blue is active, Green is new). Traffic is switched over at once. Canary deployment rolls out the new version to a small subset of users (e.g., 5%) first, monitoring for errors before releasing it to everyone.
</details>

---

#### Q12: How does Retrieval-Augmented Generation (RAG) improve model outputs compared to generic prompting?
**Keywords:** Dynamic Query, External Database, Vector Search, Hallucination
<details>
<summary>Click to Reveal Answer</summary>

RAG queries external databases or vector stores to retrieve context-specific documents. It appends this data to the model prompt at run-time, allowing the model to generate accurate answers and reducing hallucinations.
</details>

---

#### Q13: You run a SonarLint scan on a Java class and it flags a blocker bug: "NullPointerException risk due to unhandled object validation." How do you fix it?
**Hint:** Think about Optional or null checks.
<details>
<summary>Click to Reveal Answer</summary>

To fix this, check that the object is not null (e.g., using `if (obj != null)`) before calling its methods, or wrap the lookup in Java's `Optional` wrapper class to handle empty results safely.
</details>

---

#### Q14: When calling AWS Bedrock, why is it standard practice to use `DefaultCredentialsProvider` instead of hardcoding AWS access keys?
**Keywords:** Credentials Search, Env Variables, IAM Role, Security Leak
<details>
<summary>Click to Reveal Answer</summary>

`DefaultCredentialsProvider` checks standard locations (environment variables, system properties, and ~/.aws credentials) automatically. This prevents hardcoding access keys in source files, allowing the code to run securely on local workstations and EC2 instances using IAM roles.
</details>

---

### Advanced (Deep Dive/System)

#### Q15: Detail the architecture of a secure GenAI gateway. How do you prevent Prompt Injection, protect customer data privacy, and handle API rate limits?
**Keywords:** Input Sanitization, PII Redaction, Token Bucket, Guardrails, Model Auditing
<details>
<summary>Click to Reveal Answer</summary>

A secure GenAI gateway should sanitize user inputs using regex and guardrail classifiers (like Bedrock Guardrails) to block prompt injections. It must redact customer PII before routing queries to the model. Finally, it should use a Token Bucket algorithm to manage request rate limits, and log sanitized transactions to an audit database.
</details>

---

## Wednesday: GitLab CI/CD & Docker Basics (Pipelines, Runners, Docker Client-Daemon)

### Beginner (Recall/Definition)

#### Q1: In GitLab CI/CD, what is the role of a Runner?
**Keywords:** Execution Agent, Build Environment, Container Executor, Script Run
<details>
<summary>Click to Reveal Answer</summary>

A runner is an agent process that compiles and runs the pipeline jobs defined in your `.gitlab-ci.yml` file, using executors like shell, Docker, or Kubernetes.
</details>

---

#### Q2: What is the difference between Shared Runners and Project Runners in GitLab?
**Keywords:** Global Resource, Dedicated Host, Security Isolation, Project Bound
<details>
<summary>Click to Reveal Answer</summary>

Shared Runners are available to all projects across a GitLab instance, making them cost-effective for general builds. Project Runners are dedicated agents registered to a specific repository, providing security isolation and access to local network resources.
</details>

---

#### Q3: What is the purpose of the Docker Daemon (`dockerd`)?
**Keywords:** Background Service, API Endpoint, Lifecycle Manager, UNIX Socket
<details>
<summary>Click to Reveal Answer</summary>

The Docker Daemon runs as a background service on the host. It listens for REST API calls from the client CLI and manages container lifecycles, images, volumes, and networks.
</details>

---

#### Q4: How does a Docker container differ from a traditional Virtual Machine?
**Keywords:** Shared Kernel, Guest OS, Boot Time, Hardware Hypervisor
<details>
<summary>Click to Reveal Answer</summary>

Containers share the host operating system kernel and run as isolated processes, making them lightweight and fast to boot. Virtual Machines require a complete Guest OS running on top of a hardware hypervisor, which consumes more resources.
</details>

---

#### Q5: What is the difference between `docker create`, `docker start`, and `docker run`?
**Keywords:** Instantiation, Process Execution, Combination, Lifecycle
<details>
<summary>Click to Reveal Answer</summary>

`docker create` instantiates a container filesystem from an image without starting its processes. `docker start` starts a stopped container. `docker run` combines both steps, creating and starting a new container.
</details>

---

#### Q6: In a Dockerfile, what does the `WORKDIR` instruction do?
**Keywords:** Set Directory, Absolute Path, Next Commands, Creation
<details>
<summary>Click to Reveal Answer</summary>

`WORKDIR` sets the working directory for subsequent instructions (like `RUN`, `CMD`, `COPY`) in the Dockerfile. If the directory does not exist, Docker creates it.
</details>

---

#### Q7: What is the difference between public and private repositories on Docker Hub?
**Keywords:** Access Control, Global Visibility, Secrets Safety, Namespace
<details>
<summary>Click to Reveal Answer</summary>

Public repositories allow anyone globally to pull your images. Private repositories restrict access, requiring authentication to prevent proprietary code and configurations from leaking.
</details>

---

#### Q8: What does the `-d` flag do in the command `docker run -d nginx`?
**Keywords:** Detached Mode, Background Run, Terminal Control, Container ID
<details>
<summary>Click to Reveal Answer</summary>

The `-d` flag runs the container in detached (background) mode. It returns the container ID instantly, freeing your terminal while the container runs in the background.
</details>

---

#### Q9: What is the purpose of `.dockerignore` files?
**Keywords:** Build Context, Exclude Files, Upload Size, Secrets Security
<details>
<summary>Click to Reveal Answer</summary>

A `.dockerignore` file prevents heavy or sensitive files (like `.git`, `node_modules`, or local configuration secrets) from being copied into the Docker build context, optimizing build times and security.
</details>

---

#### Q10: What is a GitLab CI/CD Component?
**Keywords:** Reusable Template, Pipeline Fragment, Include, DRY Config
<details>
<summary>Click to Reveal Answer</summary>

A GitLab CI/CD component is a reusable pipeline template configuration. It allows you to import standard build, test, or deploy jobs into multiple pipelines, keeping configurations DRY.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: Explain how Docker utilizes Layer Caching to speed up image builds, and why instruction order matters.
**Keywords:** Cache Invalidation, Reusable Layers, Step Changes, Bottom Placement
<details>
<summary>Click to Reveal Answer</summary>

Docker caches the layer generated by each Dockerfile instruction. If an instruction and its files have not changed, Docker reuses the cached layer. When a change is detected, that instruction's cache and all subsequent caches are invalidated. To optimize build speeds, place rarely changing steps (like installing packages) at the top, and frequently changing steps (like copying source code) at the bottom.
</details>

---

#### Q12: Why would you use a GitLab Parent-Child Pipeline instead of a standard linear pipeline configuration?
**Keywords:** Monorepos, Independent Triggers, Decoupled YAML, Pipeline Sizing
<details>
<summary>Click to Reveal Answer</summary>

Use Parent-Child pipelines in large codebases or monorepos to trigger independent sub-project pipelines. This prevents running the entire test suite when only one module has changed.
</details>

---

#### Q13: When a Docker container crashes, what happens to the files written to its local filesystem?
**Keywords:** Ephemeral Layer, Container Reset, Data Loss, Volume Mount
<details>
<summary>Click to Reveal Answer</summary>

Any changes written to the container's default filesystem layer are lost when the container is deleted. To persist data across container lifecycles, you must mount a persistent volume.
</details>

---

#### Q14: How does a GitLab runner authenticate to push images to Docker Hub during a pipeline run?
**Keywords:** Masked Variables, API Token, CLI Login, Environment Variables
<details>
<summary>Click to Reveal Answer</summary>

The runner uses masked environment variables configured in your GitLab project settings (like `$DOCKER_USERNAME` and `$DOCKER_PASSWORD`) to run the `docker login` command before pushing images, preventing credentials from appearing in build logs.
</details>

---

### Advanced (Deep Dive/System)

#### Q15: Explain how a Docker runner configured with the "Docker-in-Docker" (DinD) executor executes build steps. What are the security trade-offs?
**Keywords:** Nested Daemon, Privileged Mode, Host Compromise, Shared Socket
<details>
<summary>Click to Reveal Answer</summary>

DinD runs a nested Docker daemon inside the runner container, allowing it to build and push images. However, DinD requires the container to run in `--privileged` mode, which exposes the host machine's hardware. If an attacker compromises the runner container, they can access the host system.
</details>

---

## Thursday: Advanced Docker & Compose (Keywords, Compose, Volumes, Best Practices)

### Beginner (Recall/Definition)

#### Q1: What does the `HEALTHCHECK` keyword do in a Dockerfile?
**Keywords:** Process Status, actutator endpoint, orchestrator check, exit code
<details>
<summary>Click to Reveal Answer</summary>

`HEALTHCHECK` configures a command to test the container's health status periodically (e.g., querying a `/health` endpoint). If the test fails, the container is marked as unhealthy, prompting orchestrators to restart it.
</details>

---

#### Q2: What is the purpose of the `USER` instruction in a Dockerfile?
**Keywords:** Strip Privileges, Non-Root execution, Security Hardening, UID/GID
<details>
<summary>Click to Reveal Answer</summary>

`USER` sets the non-privileged system username or UID/GID to run subsequent commands and start the container process, preventing the application from running as `root`.
</details>

---

#### Q3: What is the difference between a Named Volume and a Bind Mount?
**Keywords:** Docker Managed, Host Path, Portability, File System Mount
<details>
<summary>Click to Reveal Answer</summary>

A Named Volume is managed by Docker and stored in an isolated directory on the host. A Bind Mount maps a specific directory path on your host machine to a folder inside the container.
</details>

---

#### Q4: What does the command `docker compose down -v` do?
**Keywords:** Stop Services, Delete Containers, Network Removal, Volume Purge
<details>
<summary>Click to Reveal Answer</summary>

The command stops and deletes all containers, networks, and images defined in the compose file. The `-v` flag deletes all associated named volumes.
</details>

---

#### Q5: In a Dockerfile, why should you avoid using the `latest` tag in the `FROM` instruction?
**Keywords:** Production Mismatch, Unpredictable builds, Breaking updates, Versioning
<details>
<summary>Click to Reveal Answer</summary>

Using the `latest` tag is unstable because it pulls the newest base image automatically. This can introduce unexpected breaking changes or security bugs to your application. Always lock down specific version tags.
</details>

---

#### Q6: What does the `cap_drop` parameter do in a Docker Compose configuration?
**Keywords:** Security privileges, Host Kernel access, Drop Capabilities, Hardening
<details>
<summary>Click to Reveal Answer</summary>

`cap_drop` drops specific Linux kernel capabilities from the container, preventing the container from accessing host resources even if the process is compromised.
</details>

---

#### Q7: True or False: YAML anchors are used to define reusable configuration blocks, keeping Docker Compose files clean and DRY.
- [ ] A) True
- [ ] B) False

<details>
<summary>Click to Reveal Answer</summary>

**Correct Answer:** A) True

**Explanation:** YAML anchors (`&`) and aliases (`*`) allow you to define configuration blocks once and reuse them across multiple services in your Compose file.
- **Why others are wrong:**
  - B) This is incorrect because YAML anchors are a standard feature used to reduce configuration duplication.
</details>

---

#### Q8: What does the `STOPSIGNAL` instruction do in a Dockerfile?
**Keywords:** Graceful Shutdown, Unix Signal, SIGTERM override, Systemd
<details>
<summary>Click to Reveal Answer</summary>

`STOPSIGNAL` defines the system signal sent to the container's primary process to stop it gracefully (defaults to `SIGTERM`).
</details>

---

#### Q9: What is a `tmpfs` mount?
**Keywords:** RAM storage, Volatile, Memory mount, Secrets security
<details>
<summary>Click to Reveal Answer</summary>

A `tmpfs` mount writes storage data directly to the host system's RAM, keeping it volatile. The data is wiped when the container stops, making it ideal for temporary secrets.
</details>

---

#### Q10: What does the `ports` parameter do in a `docker-compose.yml` file?
**Keywords:** Port Mapping, Host routing, Ingress rules, Container expose
<details>
<summary>Click to Reveal Answer</summary>

The `ports` parameter maps a port on the host machine to a port inside the container (e.g., `"80:80"`), allowing external traffic to reach the container.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: Explain how the "Shell Form" of `CMD` differs from the "Exec Form" in terms of OS signal propagation.
**Keywords:** Subshell, PID 1, SIGTERM block, Graceful stop
<details>
<summary>Click to Reveal Answer</summary>

The Shell Form wraps the command in a subshell (`/bin/sh -c`), running the shell as PID 1. This blocks OS signals (like `SIGTERM`) from reaching the application, causing it to hang. The Exec Form runs the executable directly as PID 1, allowing it to handle shutdown signals instantly.
</details>

---

#### Q12: How do you configure a database named volume mount as read-only for a web-server container?
**Keywords:** Mount Suffix, :ro flag, Configuration block, Write prevent
<details>
<summary>Click to Reveal Answer</summary>

To configure a volume as read-only, append the `:ro` suffix to the volume mapping in your Docker Compose file (e.g., `- ./nginx.conf:/etc/nginx/nginx.conf:ro`).
</details>

---

#### Q13: What happens when you run `docker run --read-only` without configuring a `tmpfs` mount for directories that require write access?
**Keywords:** Filesystem block, Process crash, Write failure, Temp directory
<details>
<summary>Click to Reveal Answer</summary>

The container will crash if the application attempts to write temporary files (like logs or temp directory files) to the read-only root filesystem. To resolve this, mount a temporary RAM volume (`tmpfs`) to those directories.
</details>

---

#### Q14: How does the `extends` keyword in Docker Compose support DRY configuration practices?
**Keywords:** Inherit properties, Base file, Shared variables, Service reuse
<details>
<summary>Click to Reveal Answer</summary>

The `extends` keyword allows a service to inherit configuration settings from a base template or another service, reducing duplication across different environments (like dev and prod).
</details>

---

### Advanced (Deep Dive/System)

#### Q15: Explain how Linux kernel cgroups and namespaces isolate processes in containers under the hood.
**Keywords:** Resource Limiting, Virtual View, Network Stack, Process Table, cgroups
<details>
<summary>Click to Reveal Answer</summary>

Namespaces provide isolation boundaries by giving each container a private view of system resources (such as process trees, network stacks, and mount points). Control Groups (cgroups) enforce resource limits on these isolated processes, restricting their CPU, memory, and disk usage.
</details>

---

## Friday: Splunk & Observability (Splunk Ingestion, SPL, OWASP A05/A08, AI Governance)

### Beginner (Recall/Definition)

#### Q1: What is the primary role of the Splunk Indexer?
**Keywords:** Parse Logs, Timestamp extraction, Compressed Storage, Search Engine
<details>
<summary>Click to Reveal Answer</summary>

The Splunk Indexer processes raw, incoming log streams, extracts timestamps, attaches metadata, and stores the structured events in compressed directory indexes to make them searchable.
</details>

---

#### Q2: What is the Splunk HTTP Event Collector (HEC)?
**Keywords:** Ingestion Endpoint, Port 8088, HTTPS POST, Log Appender
<details>
<summary>Click to Reveal Answer</summary>

The HEC is an API endpoint that allows applications to send logs directly to Splunk using HTTP POST requests, avoiding the need to write logs to local files.
</details>

---

#### Q3: What does the pipe (`|`) operator do in Splunk search queries?
**Keywords:** Chain commands, Data stream, Filter output, Transform
<details>
<summary>Click to Reveal Answer</summary>

The pipe operator (`|`) chains commands in SPL, passing the filtered results from the left to the command on the right for transformation, formatting, or aggregation.
</details>

---

#### Q4: In the OWASP Top 10, what is a "Software and Data Integrity Failure" (A08)?
**Keywords:** Unverified updates, Tampered dependencies, Checksum missing, CI pipeline hijack
<details>
<summary>Click to Reveal Answer</summary>

This vulnerability occurs when code, data, or libraries are imported without verifying their integrity (such as downloading unverified packages, or running automated updates without checking signatures).
</details>

---

#### Q5: What is the Common Information Model (CIM) in Splunk?
**Keywords:** Standard schema, Normalized fields, Multi-source analysis, Unified views
<details>
<summary>Click to Reveal Answer</summary>

The CIM is a standardized schema of field names and event definitions. It normalizes data from different sources (like mapping `c_ip` and `clientip` to `src_ip`), allowing you to use unified dashboards.
</details>

---

#### Q6: What does the Splunk SPL command `dedup` do?
**Keywords:** Remove duplicates, Unique events, Filter results, Keep latest
<details>
<summary>Click to Reveal Answer</summary>

The `dedup` command removes duplicate events from your search results based on specified fields, keeping only the most recent matching entry.
</details>

---

#### Q7: What is the difference between a Splunk Report and a Splunk Alert?
**Keywords:** Scheduled search, Email summary, Threshold trigger, Action notification
<details>
<summary>Click to Reveal Answer</summary>

A Report runs a saved search on a schedule to generate and email data summaries. An Alert runs a search periodically and triggers notifications (like emails or Slack calls) if results exceed a defined threshold (e.g., error rate > 10%).
</details>

---

#### Q8: In the EU AI Act, what is an "Unacceptable Risk" category?
**Keywords:** Social scoring, Real-time biometrics, Banned systems, Cognitive manipulation
<details>
<summary>Click to Reveal Answer</summary>

Unacceptable Risk covers AI systems that threaten safety or user rights (like social scoring or real-time biometric tracking in public spaces). These systems are banned.
</details>

---

#### Q9: What does the `stats dc(user_id)` function do in an SPL query?
**Keywords:** Distinct count, Unique entries, Ignore duplicates, Aggregator
<details>
<summary>Click to Reveal Answer</summary>

The `dc` (Distinct Count) function calculates the number of *unique* values in a field, ignoring duplicate actions by the same user.
</details>

---

#### Q10: What is Software Composition Analysis (SCA)?
**Keywords:** Dependency audit, Package vulnerability, CVE mapping, Third-party security
<details>
<summary>Click to Reveal Answer</summary>

SCA is a security check that audits third-party libraries and dependencies used in your application, flagging packages with known vulnerabilities in CVE databases.
</details>

---

### Intermediate (Application/Scenario)

#### Q11: You need to extract a custom transaction ID from raw logs that Splunk does not extract automatically. What SPL command do you use, and how do you configure it?
**Keywords:** regex, rex command, group identifier, raw field
<details>
<summary>Click to Reveal Answer</summary>

Use the `rex` command with a regular expression to extract the field on the fly: `| rex field=_raw "tx_id=(?<transaction_id>[a-zA-Z0-9\-]+)"`. This saves the matching text as a new field you can query.
</details>

---

#### Q12: How do you configure Spring Boot's Logback settings to ensure that log ingestion to a remote Splunk HEC cluster does not block application execution?
**Keywords:** Async Appender, Non-blocking, Queue capacity, Thread pool
<details>
<summary>Click to Reveal Answer</summary>

Wrap the HEC appender inside Logback's `AsyncAppender`. This routes logs to an in-memory queue, allowing the application thread to return instantly without waiting for the HTTP request to Splunk to finish.
</details>

---

#### Q13: How does a software supply chain attack (like the SolarWinds incident) bypass typical firewall and code quality controls?
**Keywords:** Upstream compromise, Signed binaries, Trusted vendor, Automatic update
<details>
<summary>Click to Reveal Answer</summary>

Attackers compromise upstream vendor dependencies or build servers to inject malware into trusted software updates. Because the update is signed and distributed by a trusted vendor, it bypasses firewalls and is deployed automatically.
</details>

---

#### Q14: How do you configure a GitLab CI pipeline to run automated dependency vulnerability scanning using Git templates?
**Keywords:** include statement, Dependency-Scanning.gitlab-ci.yml, SCA stage, pipeline run
<details>
<summary>Click to Reveal Answer</summary>

Include GitLab's built-in dependency scanning template in your `.gitlab-ci.yml` file: `include: - template: Jobs/Dependency-Scanning.gitlab-ci.yml`. This adds automated SCA scans to your pipeline runs.
</details>

---

### Advanced (Deep Dive/System)

#### Q15: Design a robust AI Audit and Governance logging framework. What metadata fields must you record to comply with high-risk regulations like the EU AI Act?
**Keywords:** Model Identifier, sanitized prompts, PII flag, Token count, temperature settings, compliance log
<details>
<summary>Click to Reveal Answer</summary>

To comply with high-risk regulations, you must record: transaction timestamps, user IDs, model IDs, temperature settings, prompt and response token counts, and redacted prompt/response text. You must also log validation checks (like confirming the prompt passed PII guardrails) and audit outputs for accuracy.
</details>
