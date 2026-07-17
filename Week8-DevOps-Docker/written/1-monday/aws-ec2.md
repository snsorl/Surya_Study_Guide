# Amazon Elastic Compute Cloud (EC2): Virtual Servers in the Cloud

## Learning Objectives
- Describe the core purpose and utility of Amazon EC2 virtual servers.
- Compare common EC2 instance families (General Purpose, Compute Optimized, Memory Optimized).
- Explain the role of Amazon Machine Images (AMIs) and Key Pairs in instance provisioning.
- Analyze the states of the EC2 instance lifecycle (pending, running, stopping, stopped, shutting-down, terminated).
- Distinguish between different EC2 billing models (On-Demand, Reserved, Savings Plans, Spot).

---

## Why This Matters
Before the cloud era, deploying a production web application required buying physical rack hardware, configuring network cabling, provisioning hypervisors, and managing physical datacenter cooling systems. This took weeks or months.

Amazon Elastic Compute Cloud (EC2) changed this by introducing resizable virtual compute capacity on demand. With EC2, you can boot a virtual machine (an "instance") in seconds, scale it dynamically, and control it exactly like a physical server. For a standard Java Spring Boot backend or a static Angular frontend server, EC2 represents the baseline virtual compute infrastructure of modern cloud architectures.

---

## The Concept

### 1. What is an EC2 Instance?
An EC2 instance is a virtual machine running on AWS's physical host infrastructure. AWS uses hypervisors (historically Xen, and modern Nitro system architectures) to carve out physical host resources (CPU cores, RAM blocks, network cards) and allocate them to virtual instances.

### 2. EC2 Instance Families and Types
AWS categorizes EC2 instances into families optimized for different workloads. The names follow a pattern like `t3.micro` or `c5.large`:
- **First Character (Family)**: E.g., `t` (burstable general purpose), `c` (compute optimized), `m` (general purpose).
- **Number (Generation)**: E.g., `3` or `5` (higher generation indicates newer hardware).
- **Size**: E.g., `micro`, `small`, `medium`, `large` (indicates the scale of CPU and RAM).

#### Core Families
- **General Purpose (`t2`, `t3`, `m5`)**: Balanced CPU, memory, and networking. Burstable instances (`t2`, `t3`) use a credit model allowing temporary bursts of performance. Best for development servers, low-traffic APIs, and testing environments.
- **Compute Optimized (`c5`, `c6g`)**: High ratio of virtual CPUs (vCPUs) to memory. Best for high-performance web servers, scientific modeling, batch processing, and media transcoding.
- **Memory Optimized (`r5`, `r6g`)**: High ratio of memory to vCPUs. Best for large relational databases (PostgreSQL, MySQL) or in-memory caches (Redis).

---

### 3. AMIs and Key Pairs: The Provisioning Blocks
To launch an EC2 instance, you must configure two key resources:
- **Amazon Machine Image (AMI)**: The template that contains the software configuration (operating system, application server, and pre-installed tools) for the instance. For example, you might select an Amazon Linux 2023 AMI, an Ubuntu Server 22.04 AMI, or a custom AMI pre-loaded with Java 17 and Docker.
- **Key Pair**: A security credential. AWS stores the public key on the instance (in the `/home/username/.ssh/authorized_keys` file), and you download the private key (a `.pem` file) to authenticate your connection via SSH.

---

### 4. The EC2 Instance Lifecycle
An EC2 instance transitions through several distinct states:

```
                  +---------+
                  | Pending |
                  +----t----+
                       | (Automatic)
                  +----v----+
    +------------>| Running |<-------------+
    |             +----t----+              |
    | (Stop            |             (Start|
    | command)         | (Stop/Start  Cmd) |
+---+----+             | commands)    +----+---+
|Stopping|             |              |Starting|
+---t----+             |              +----^---+
    |                  |                   |
    +------------>+----v----+--------------+
                  | Stopped |
                  +----t----+
                       | (Terminate command)
                  +----v----+
                  | Shutting|
                  | -Down   |
                  +----t----+
                       | (Automatic)
                  +----v----+
                  |Terminated|
                  +---------+
```

- **Pending**: The instance is booting up or acquiring physical host resources.
- **Running**: The instance is active and accessible. Billing accumulates.
- **Stopping / Stopped**: The instance is powered down. Compute billing stops, but you still pay for attached storage volumes (EBS).
- **Terminated**: The instance is permanently deleted and its resources are released. You cannot restart a terminated instance.

---

### 5. EC2 Billing Models
AWS offers several purchasing options to optimize cost:
- **On-Demand**: Pay per second of usage. No long-term commitments. Ideal for testing, development, and unpredictable workloads.
- **Reserved Instances (RIs)**: Commit to a 1 or 3-year term for specific instance parameters. Saves up to 72% compared to On-Demand.
- **Savings Plans**: Commit to a specific compute spend per hour (e.g., $10/hour) for 1 or 3 years. Offers the same savings as RIs but with more flexibility.
- **Spot Instances**: Bid on spare AWS compute capacity at discounts up to 90%. However, AWS can reclaim the instance with a 2-minute warning. Ideal for fault-tolerant workloads, batch processing, and test pipelines.

---

## Code Examples and Walkthroughs

### 1. Launching and Listing Instances via AWS CLI
You can control the EC2 lifecycle programmatically. The following scripts show how to launch an Ubuntu instance and monitor its state:

```bash
# 1. Launch a single t3.micro EC2 instance using an Ubuntu 22.04 AMI
# (Replace the subnet-id and security-group-ids with your target AWS values)
aws ec2 run-instances \
    --image-id ami-0fc5d935ebf8bc3bc \
    --count 1 \
    --instance-type t3.micro \
    --key-name project3-ssh-key \
    --security-group-ids sg-0123456789abcdef0 \
    --subnet-id subnet-0123456789abcdef0

# Expected JSON output includes the InstanceId (e.g., i-0abcdef1234567890)
```

```bash
# 2. Check the lifecycle status of your newly created instance
aws ec2 describe-instances \
    --instance-ids i-0abcdef1234567890 \
    --query "Reservations[*].Instances[*].State.Name" \
    --output text

# Expected Output: pending or running
```

```bash
# 3. Stop the instance to halt compute billing
aws ec2 stop-instances --instance-ids i-0abcdef1234567890

# 4. Terminate the instance permanently when it is no longer needed
aws ec2 terminate-instances --instance-ids i-0abcdef1234567890
```

---

## Summary
- **Amazon EC2** provides virtual server compute instances running in highly secure AWS data centers.
- **Instance Families** optimize compute capability: `t3`/`m5` are General Purpose, `c5` are Compute Optimized, and `r5` are Memory Optimized.
- **AMIs** define the baseline software configuration (OS/apps), and **Key Pairs** secure remote access via SSH.
- **Lifecycle States** dictate billing: you pay for compute when instances are *Running*, and only for storage when instances are *Stopped*.
- **Billing Models** allow you to match cost to stability: use *On-Demand* for development, *Reserved/Savings Plans* for persistent production systems, and *Spot* for batch pipelines.

---

## Additional Resources
- [Amazon EC2 Instance Types Directory](https://aws.amazon.com/ec2/instance-types/)
- [AWS Guide: EC2 Instance Lifecycle](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-instance-lifecycle.html)
- [Amazon Machine Images (AMI) Product Guide](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AMIs.html)
