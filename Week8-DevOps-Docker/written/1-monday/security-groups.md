# AWS Security Groups: Virtual Firewalls for Instance Security

## Learning Objectives
- Describe the purpose of Security Groups as instance-level virtual firewalls.
- Compare inbound and outbound rule evaluation.
- Explain the stateful nature of Security Group evaluations.
- Read and interpret Classless Inter-Domain Routing (CIDR) notation.
- Design security group rule sets for common architectures (SSH, Nginx, Spring Boot).

---

## Why This Matters
If you deploy your Spring Boot backend or PostgreSQL database onto an EC2 instance in the cloud, it is immediately exposed to public networks. Without security controls, malicious bots will scan your instance, search for vulnerabilities, and attempt brute-force access.

Security Groups are your first line of defense in AWS. They act as virtual firewalls directly surrounding your compute resources. Designing security rules with the "least privilege" principle is key: only expose the minimum necessary ports to the specific IP ranges that require access, keeping databases and backends safe from unauthorized internet traffic.

---

## The Concept

### 1. What is an AWS Security Group?
A **Security Group** acts as a virtual firewall that controls inbound and outbound network traffic for your EC2 instances (and other AWS resources like RDS databases and load balancers).
- **Instance-Level Control**: Security groups are attached to the Elastic Network Interface (ENI) of an instance, not the subnet. This means two instances in the same subnet can have completely different security rules.
- **Permissive Rules Only**: You can only add rules that *allow* traffic. You cannot write "deny" rules. (To deny specific IP ranges, you must use Network Access Control Lists - NACLs - at the subnet boundary).

```
                      INBOUND TRAFFIC
           +----------------------------------+
           |                                  |
           v                                  v
     Port 22 Allowed                   Port 3306 Blocked
      from My IP Only                   from Everyone
           |                                  |
     +-----v----------------------------------x-----+
     |               SECURITY GROUP                 |
     |  +----------------------------------------+  |
     |  |              EC2 INSTANCE              |  |
     |  |           - Spring Boot app            |  |
     |  |           - Database instance          |  |
     |  +----------------------------------------+  |
     +----------------------------------------------+
```

---

### 2. Inbound vs. Outbound Rules
- **Inbound Rules**: Control incoming traffic to the instance. By default, when you create a new security group, all inbound traffic is blocked. You must explicitly allow the traffic you want.
- **Outbound Rules**: Control traffic exiting the instance. By default, security groups allow all outbound traffic (`0.0.0.0/0`), allowing the instance to download updates or query public APIs freely.

---

### 3. The Stateful Nature of Security Groups
Security groups are **stateful**:
- If an inbound request is allowed, the response traffic is automatically allowed to exit the instance, regardless of outbound rules.
- If an outbound request is allowed, the response traffic is automatically allowed to enter the instance, regardless of inbound rules.

*(Contrast this with Network Access Control Lists (NACLs), which are stateless and require explicit rules in both directions).*

---

### 4. Understanding CIDR Notation
Security Group rules use **CIDR (Classless Inter-Domain Routing)** notation to define IP address ranges:
- **`0.0.0.0/0`**: Represents the entire internet. Use this for public web traffic (ports 80/443), but *never* for admin traffic (SSH, database ports).
- **`192.168.1.50/32`**: The `/32` indicates a single specific IP address. Best for limiting SSH access exclusively to your workspace IP.
- **`10.0.0.0/16`**: Represents a private subnet range. Best for letting instances within the same AWS Virtual Private Cloud (VPC) talk to each other without public exposure.

---

### 5. Common Port Configurations
Below are the standard ports and configurations used for full-stack deployments:

| Port | Protocol | Common Use Case | Target Security Practice |
| :--- | :--- | :--- | :--- |
| **`22`** | TCP | SSH (Secure Shell) | Restrict to `/32` (your developer IP) or dynamic Bastion hosts. |
| **`80`** | TCP | HTTP (Web Traffic) | Expose to `0.0.0.0/0` (public access). |
| **`443`** | TCP | HTTPS (Secure Web Traffic) | Expose to `0.0.0.0/0` (public access). |
| **`8080`** | TCP | Spring Boot Application | Restrict access to the API Gateway or Application Load Balancer IP/Security Group. |
| **`5432`** | TCP | PostgreSQL Database | Restrict access exclusively to the Security Group of the Spring Boot instances. |

---

## Code Examples and Walkthroughs

### 1. Creating and Configuring a Security Group via AWS CLI
The CLI commands below demonstrate how to provision a security group for a Spring Boot backend, allowing SSH from your IP and web requests from the public internet:

```bash
# 1. Create a security group in your default VPC
aws ec2 create-security-group \
    --group-name project3-backend-sg \
    --description "Security Group for Project 3 Spring Boot Backend" \
    --vpc-id vpc-0123456789abcdef0

# Expected Output: GroupId (e.g., sg-0987654321fedcba0)
```

```bash
# 2. Add inbound rule: Allow SSH (Port 22) exclusively from your specific local IP
aws ec2 authorize-security-group-ingress \
    --group-id sg-0987654321fedcba0 \
    --protocol tcp \
    --port 22 \
    --cidr 203.0.113.50/32

# 3. Add inbound rule: Allow Spring Boot traffic (Port 8080) from anywhere
aws ec2 authorize-security-group-ingress \
    --group-id sg-0987654321fedcba0 \
    --protocol tcp \
    --port 8080 \
    --cidr 0.0.0.0/0
```

### 2. Security Group Chaining (Best Practice)
Instead of permitting port access to IP address ranges, you can allow access to other security groups. This is called **Security Group Chaining**:

```bash
# Allow the Database Security Group (sg-database) to accept connections on port 5432 (Postgres)
# ONLY from instances that carry the Backend Security Group (sg-backend)
aws ec2 authorize-security-group-ingress \
    --group-id sg-database \
    --protocol tcp \
    --port 5432 \
    --source-group sg-backend

# Verification:
# If you launch an EC2 instance associated with sg-backend, it can query the database.
# Any other computer (even on the same subnet) is blocked automatically.
```

---

## Summary
- **Security Groups** operate at the instance level as virtual stateful firewalls, defaulting to blocking all inbound traffic.
- **Stateful evaluation** means allowed traffic in one direction automatically permits the return response.
- **CIDR notation** specifies IP ranges: `/32` limits access to a single IP, while `/0` exposes the port to the entire internet.
- **Security Group Chaining** permits communication between infrastructure components (e.g., Application to Database) by referencing security group IDs rather than static IPs.

---

## Additional Resources
- [AWS Guide: Amazon EC2 Security Groups for Linux Instances](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-security-groups.html)
- [VPC CIDR Notation and IP Addressing Basics](https://docs.aws.amazon.com/vpc/latest/userguide/vpc-ip-addressing.html)
- [AWS Security Best Practices for Security Groups](https://docs.aws.amazon.com/codec/latest/userguide/security-best-practices.html)
