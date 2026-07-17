# Cloud Service Models: IaaS, PaaS, and SaaS

## Learning Objectives
- Define the three primary cloud service models: IaaS, PaaS, and SaaS.
- Identify AWS service examples corresponding to each service model.
- Analyze the distribution of administration responsibilities between the cloud provider and the customer across different service models.
- Recommend the appropriate service model based on business and technical constraints.

## Why This Matters
When migrating applications to the cloud, you do not have to manage every layer of the technology stack. Depending on your organization's resources, expertise, and time constraints, you can choose how much infrastructure control you want to delegate to the cloud provider.

Understanding the differences between Infrastructure as a Service (IaaS), Platform as a Service (PaaS), and Software as a Service (SaaS) is a core architectural skill. It allows developers to choose service levels that balance control and operational overhead. For example, deciding whether to run a database manually on a virtual server (IaaS) or to use a managed database service (PaaS) directly impacts long-term maintenance costs and deployment speed.

## The Concept

### The Three Service Models
Cloud services are categorized into three primary service models, representing different levels of abstraction and control:

```
+-------------------------------------------------------------+
|              Software as a Service (SaaS)                   |
|  (e.g., Salesforce, Microsoft 365, Google Workspace)       |
+-------------------------------------------------------------+
|              Platform as a Service (PaaS)                   |
|  (e.g., AWS Elastic Beanstalk, Heroku, AWS RDS)             |
+-------------------------------------------------------------+
|            Infrastructure as a Service (IaaS)               |
|  (e.g., AWS EC2, AWS VPC, Amazon S3)                        |
+-------------------------------------------------------------+
```

#### 1. Infrastructure as a Service (IaaS)
IaaS provides access to fundamental computing, storage, and networking resources. The provider owns and maintains the physical hardware, virtualization layer, and data center facilities. The customer is responsible for configuring and managing the operating system, middleware, runtime environment, data, and applications.
- *AWS Example*: **Amazon EC2 (Elastic Compute Cloud)**. When you launch an EC2 instance, AWS provides a virtual machine. You must select the operating system, configure firewall rules, install updates, set up security configurations, and deploy your software manually.
- *Use Case*: When an organization needs complete control over the operating system environment or is migrating legacy applications that require specific OS configurations.

#### 2. Platform as a Service (PaaS)
PaaS removes the need for you to manage the underlying operating system and hardware infrastructure. The cloud provider manages the OS, patching, scaling, hardware provisioning, and database engines. The customer only needs to deploy their application code and configure application settings.
- *AWS Example*: **AWS Elastic Beanstalk** or **Amazon RDS**. With Elastic Beanstalk, you upload your Java or Python application code, and AWS automatically handles deployment, capacity provisioning, load balancing, auto-scaling, and health monitoring.
- *Use Case*: When developers want to focus on writing application logic and deploying code without spending time on system administration tasks like OS patching or backups.

#### 3. Software as a Service (SaaS)
SaaS provides a complete, fully functional software application that is managed and run by the service provider. The end-user accesses the application over the internet, typically through a web browser or mobile app. The customer has no visibility into the underlying infrastructure, operating system, database, or application code configurations.
- *Example*: **Salesforce**, **Microsoft 365**, or **AWS Console / Amazon WorkMail**.
- *Use Case*: For standard business applications (like email, CRM, or file sharing) where customization of the underlying codebase or database is not required.

---

### Shared Responsibility Comparison
The following matrix shows which party is responsible for managing each layer of the technology stack under the three service models compared to traditional on-premises setups:

| Infrastructure Layer | On-Premises | IaaS | PaaS | SaaS |
|---|---|---|---|---|
| **Physical Facilities & Power** | Customer | Provider | Provider | Provider |
| **Physical Servers & Cables** | Customer | Provider | Provider | Provider |
| **Virtualization Layer** | Customer | Provider | Provider | Provider |
| **Operating System & OS Patching**| Customer | **Customer** | Provider | Provider |
| **Middleware & Runtimes** | Customer | **Customer** | Provider | Provider |
| **Database Engines** | Customer | **Customer** | Provider | Provider |
| **Application Code** | Customer | **Customer** | **Customer** | Provider |
| **Data & Access Configurations** | Customer | **Customer** | **Customer** | **Customer** |

*Key Takeaway*: The customer is **always** responsible for securing their data and configuring access control, regardless of the service model selected.

## Code Examples

To illustrate the difference in developer effort, let us compare how you run a PostgreSQL database using an IaaS model versus a PaaS model.

### IaaS Approach: Manual Setup on EC2
To run a database on IaaS, you must provision an EC2 instance and run bash commands to configure the operating system, install packages, and manage the database service yourself:

```bash
# 1. Connect to the virtual machine
ssh -i key.pem ec2-user@your-ec2-ip

# 2. Update the OS package manager
sudo yum update -y

# 3. Install the PostgreSQL database engine
sudo amazon-linux-extras install postgresql14 -y
sudo yum install postgresql-server -y

# 4. Initialize the database instance
sudo postgresql-setup --initdb

# 5. Start and enable the service
sudo systemctl start postgresql
sudo systemctl enable postgresql

# The developer must also manually configure backups (cron jobs), 
# scale disk partitions, and apply security patches.
```

### PaaS Approach: Managed Database via Amazon RDS
With PaaS (Amazon RDS), you do not log into an operating system. You declare the database requirement using an API call or CLI command, and AWS provisions, configures, and manages the database automatically:

```bash
aws rds create-db-instance \
    --db-instance-identifier prod-postgres-db \
    --db-instance-class db.t3.micro \
    --engine postgres \
    --allocated-storage 20 \
    --master-username adminuser \
    --master-user-password SuperSecurePassword123 \
    --backup-retention-period 7
```

AWS automatically provisions the system, configures PostgreSQL, schedules automated daily backups, and handles operating system patching without developer intervention.

## Summary
- **IaaS** provides virtualized hardware (e.g., EC2). The customer manages the operating system, runtimes, and databases.
- **PaaS** manages the underlying OS and middleware (e.g., Elastic Beanstalk, RDS). The customer only manages application code and data.
- **SaaS** provides a complete end-user software solution (e.g., Salesforce). The customer only configures settings and manages user data.
- Moving from IaaS to SaaS reduces operational overhead but decreases customization control.

## Additional Resources
- [Microsoft Learn - Describe IaaS, PaaS, and SaaS](https://learn.microsoft.com/en-us/training/modules/describe-features-infrastructure-as-service-platform-as-service-software-as-service/)
- [AWS Managed Services vs Self-Managed Comparison](https://aws.amazon.com/managed-services/)
- [Cloud Service Models Guide - GeeksforGeeks](https://www.geeksforgeeks.org/service-models-in-cloud-computing/)
