# The AWS Shared Responsibility Model

## Learning Objectives
- Describe the core concept of the AWS Shared Responsibility Model.
- Distinguish between security "of" the cloud and security "in" the cloud.
- Categorize specific security tasks as either AWS responsibilities or customer responsibilities.
- Apply the model to practical database administration and deployment workflows.

## Why This Matters
One of the most common misconceptions about migrating to the cloud is that the cloud provider automatically makes your applications and data 100% secure. Developers sometimes assume that because a database is hosted on AWS, they do not need to worry about SQL injection vulnerabilities, weak passwords, or data encryption.

This assumption is incorrect and dangerous. If a hacker breaches your cloud database because the database port was left open to the public internet with a default password, AWS is not responsible. That breach is the customer's responsibility. The AWS Shared Responsibility Model defines exactly where AWS's security duties end and your organization's security duties begin, allowing you to design secure, compliant cloud architectures.

## The Concept

### Security "OF" the Cloud vs. Security "IN" the Cloud
The Shared Responsibility Model splits security tasks into two main areas:

```
+-------------------------------------------------------------+
|               Security "IN" the Cloud                       |
|               (Customer Responsibility)                    |
|  - Customer Data & Classification                           |
|  - Identity & Access Management (IAM Users & Passwords)    |
|  - Operating System Configurations & Patches (IaaS only)    |
|  - Network Firewall Rules (Security Groups)                 |
|  - Data Encryption (In-Transit & At-Rest)                  |
+-------------------------------------------------------------+
|               Security "OF" the Cloud                       |
|                 (AWS Responsibility)                        |
|  - Physical Security of Data Centers (Guards, Cameras)      |
|  - Hardware Infrastructure (Servers, Storage Arrays)        |
|  - Virtualization Hypervisors                               |
|  - Global Networking Infrastructure (Bridges, Fibers)       |
+-------------------------------------------------------------+
```

#### 1. AWS Responsibility: Security "OF" the Cloud
AWS is responsible for protecting the global infrastructure that runs all of the services offered in the AWS Cloud. This includes:
- **Physical Security**: Preventing unauthorized physical access to data center facilities using biometric scanners, security guards, and 24/7 camera monitoring.
- **Infrastructure Security**: Maintaining the physical server hardware, storage devices, and networking components.
- **Virtualization Software**: Managing the hypervisor layer that isolates virtual machines running on the same physical host server.

#### 2. Customer Responsibility: Security "IN" the Cloud
The customer is responsible for configuring, managing, and securing the services and resources they deploy on AWS. This includes:
- **Data Protection**: Encrypting sensitive data at rest (using tools like AWS KMS) and in transit (using SSL/TLS).
- **Access Control**: Managing IAM users, roles, password policies, and multi-factor authentication (MFA).
- **Network Configuration**: Defining firewall rules (AWS Security Groups and Network ACLs) to control network traffic.
- **Operating System and Application Security**: Applying software updates and OS security patches (if using IaaS services like EC2), and securing application code against vulnerabilities.

---

### Practical Database Implications
Your security responsibilities change depending on whether you run a database on EC2 (IaaS) or use Amazon RDS (PaaS).

#### Scenario A: Database on EC2 (IaaS)
If you install PostgreSQL on an EC2 instance, you are responsible for:
- Operating system updates and database engine patches.
- Configuring the database configuration files (e.g., `pg_hba.conf` and `postgresql.conf`).
- Implementing database backups and recovery procedures.
- Configuring OS-level firewalls.

#### Scenario B: Database on Amazon RDS (PaaS)
If you run your database on Amazon RDS, AWS manages the operating system, database engine installation, patching, and backups. However, you are still responsible for:
- Database-level user permissions (e.g., SQL grant statements).
- Configuring database network access permissions (RDS Security Groups).
- Ensuring data encryption is enabled during RDS cluster initialization.

## Code Examples

To illustrate the model, let us look at how network access control is configured. The database network security group is a firewall rule managed entirely by the customer (Security "IN" the cloud).

### Creating and Configuring a Security Group via AWS CLI
The customer must explicitly configure security group rules to restrict database port access. Leaving the database port open to the public internet violates the customer's responsibility.

```bash
# 1. Create a security group for a PostgreSQL database
aws ec2 create-security-group \
    --group-name database-security-group \
    --description "Security group for PostgreSQL database" \
    --vpc-id vpc-0123456789abcdef0

# 2. Authorize access to port 5432 (PostgreSQL) ONLY from the web application's IP range
aws ec2 authorize-security-group-ingress \
    --group-id sg-0987654321fedcba0 \
    --protocol tcp \
    --port 5432 \
    --cidr 10.0.1.0/24 # Web application subnet CIDR block
```

By executing these commands, the customer configures the firewall rules. If a developer accidentally opens access to `0.0.0.0/0` (the entire public internet), the resulting security vulnerability is a customer configuration error, not an AWS infrastructure failure.

## Summary
- The **AWS Shared Responsibility Model** divides security duties between AWS and the customer.
- **AWS** is responsible for the security **of** the cloud (physical centers, host servers, network, hypervisors).
- The **customer** is responsible for security **in** the cloud (customer data, operating systems on EC2, network configurations, encryption, and IAM access).
- Managed services (PaaS) shift operating system and engine maintenance tasks to AWS, but data classification and network access control remain customer responsibilities.

## Additional Resources
- [AWS Security and Compliance - Shared Responsibility Model](https://aws.amazon.com/compliance/shared-responsibility-model/)
- [AWS Identity and Access Management (IAM) Best Practices](https://docs.aws.amazon.com/IAM/latest/UserGuide/best-practices.html)
- [Securing Databases in Amazon Web Services Guide](https://aws.amazon.com/blogs/database/database-security-best-practices-on-aws/)
