# Knowledge Check: AWS Infrastructure and Container Platforms

This quiz evaluates your understanding of virtual servers (EC2), security groups, container hosting (ECS), API proxies (API Gateway), block storage (EBS), and automatic scaling groups.

---

## Questions

### 1. Which AWS EC2 scaling mechanism dynamically launches or terminates instances in response to active CloudWatch metric thresholds?
- [ ] A) Elastic Load Balancer (ELB)
- [ ] B) Elastic Block Store (EBS)
- [ ] C) Auto Scaling Group (ASG)
- [ ] D) Amazon Machine Image (AMI)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Auto Scaling Group (ASG)

**Explanation:** An Auto Scaling Group (ASG) automatically adjusts your running EC2 instance count based on capacity policies or CloudWatch metric alarms (like CPU utilization).
- **Why others are wrong:**
  - A) ELB distributes incoming network traffic across instances, but does not launch or terminate them.
  - B) EBS is a persistent raw block storage volume, not a compute management service.
  - D) An AMI is a read-only template used to boot instances, not a dynamic scaling mechanism.
</details>

---

### 2. True or False: Security groups are stateful, meaning that if you define a rule allowing inbound traffic on port 8080, corresponding outbound responses are allowed automatically.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) True

**Explanation:** AWS Security Groups are stateful. Any inbound traffic allowed on a port is permitted to exit back to the source automatically, regardless of outbound rule restrictions.
- **Why others are wrong:**
  - B) This is incorrect because security groups do not require manual outbound rules for corresponding inbound connections.
</details>

---

### 3. Which AWS CLI command is used to upload a local file to an Amazon S3 storage bucket?
- [ ] A) `aws s3 mb`
- [ ] B) `aws s3 cp`
- [ ] C) `aws s3 ls`
- [ ] D) `aws s3 rb`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `aws s3 cp`

**Explanation:** The `aws s3 cp` (copy) command uploads local files to S3 buckets, downloads files locally, or copies files between buckets.
- **Why others are wrong:**
  - A) `aws s3 mb` is used to make (create) a new bucket.
  - C) `aws s3 ls` is used to list bucket contents.
  - D) `aws s3 rb` is used to remove (delete) a bucket.
</details>

---

### 4. What is the main purpose of an Amazon Machine Image (AMI)?
- [ ] A) To monitor real-time CPU performance graphs.
- [ ] B) To act as a read-only boot template containing the OS, libraries, and application configs.
- [ ] C) To load-balance traffic across multiple availability zones.
- [ ] D) To act as a stateful database firewall.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) To act as a read-only boot template containing the OS, libraries, and application configs.

**Explanation:** AMIs are pre-packaged OS and configuration blueprints used to boot new EC2 virtual server instances quickly.
- **Why others are wrong:**
  - A) This is handled by CloudWatch.
  - C) This is handled by Elastic Load Balancing (ELB).
  - D) This is handled by Security Groups.
</details>

---

### 5. Fill in the Blank: To prevent unauthorized users from modifying key files, you must configure your private key (`.pem`) file permissions to read-only for the owner using the command `chmod _____ key.pem`.
- [ ] A) `777`
- [ ] B) `644`
- [ ] C) `400`
- [ ] D) `000`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `400`

**Explanation:** Running `chmod 400` sets the file to read-only for the owner, preventing other users from reading it. AWS SSH connections will be rejected if the key permissions are too open.
- **Why others are wrong:**
  - A) `777` grants read, write, and execute permissions to everyone.
  - B) `644` allows other users on the host system to read your private key.
  - D) `000` strips all permissions, making the key unreadable even by the owner.
</details>

---

### 6. What ECS launch type allows you to run containerized workloads without managing the underlying EC2 virtual servers?
- [ ] A) ECS EC2 Launch Type
- [ ] B) AWS Fargate
- [ ] C) Amazon EKS
- [ ] D) AWS Elastic Beanstalk

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) AWS Fargate

**Explanation:** AWS Fargate is a serverless compute engine for containers that manages the underlying virtual machine sizing and provisioning automatically.
- **Why others are wrong:**
  - A) The EC2 launch type requires you to launch, patch, and manage the EC2 hosts manually.
  - C) EKS is a managed Kubernetes service, not a direct serverless ECS launch type.
  - D) Elastic Beanstalk is a PaaS deployment framework, not a serverless container containerization runtime.
</details>

---

### 7. Which EBS volume category is best suited for high-performance databases requiring low latency and consistent Input/Output operations?
- [ ] A) Throughput Optimized HDD (st1)
- [ ] B) Cold HDD (sc1)
- [ ] C) Provisioned IOPS SSD (io2)
- [ ] D) Standard Magnetic (standard)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Provisioned IOPS SSD (io2)

**Explanation:** Provisioned IOPS volumes are designed for critical database applications that require high I/O performance and low latency.
- **Why others are wrong:**
  - A) and B) are HDD-based volumes best suited for large, sequential workloads (like data warehouses) rather than random database transactions.
  - D) Magnetic volumes are legacy drives with low performance capabilities.
</details>

---

### 8. What throttling algorithm does AWS API Gateway use to manage client request volumes using token buckets?
- [ ] A) Round Robin
- [ ] B) Leaky Bucket
- [ ] C) Token Bucket
- [ ] D) Least Connections

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Token Bucket

**Explanation:** API Gateway uses the Token Bucket algorithm. Requests consume tokens from a bucket; if the bucket runs out of tokens, requests are throttled and receive a `429 Too Many Requests` error.
- **Why others are wrong:**
  - A) and D) are load balancing algorithms, not rate-limiting algorithms.
  - B) Leaky Bucket processes requests at a constant, steady rate, whereas Token Bucket allows for temporary traffic bursts.
</details>

---

### 9. True or False: Security hooks designed as Fail-Open models will block all commit changes if the security scanning server goes offline.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** Fail-Open models prioritize developer availability: if the security scanner goes offline, the check is skipped, allowing commits to proceed. A Fail-Closed model blocks commits if scanners are offline.
- **Why others are wrong:**
  - A) This describes a Fail-Closed model.
</details>

---

### 10. When designing secure AI prompting hooks, what is the best practice for handling Personal Identifiable Information (PII)?
- [ ] A) Push the raw PII data and let the model handle it.
- [ ] B) Redact or anonymize all PII before sending it to the model API.
- [ ] C) Save the PII in the local Docker image layers.
- [ ] D) Encrypt the PII in the prompt text.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Redact or anonymize all PII before sending it to the model API.

**Explanation:** To comply with data privacy regulations, redact or anonymize sensitive customer details before sending data to third-party AI endpoints.
- **Why others are wrong:**
  - A) This violates data privacy standards (like GDPR).
  - C) Storing PII in local image layers makes it persistent and vulnerable.
  - D) Generative AI models cannot process encrypted prompt text directly.
</details>
