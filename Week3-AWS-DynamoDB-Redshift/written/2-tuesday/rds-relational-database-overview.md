# Amazon Relational Database Service (RDS) Overview

## Learning Objectives
- Define Amazon Relational Database Service (RDS) and explain its role as a managed PaaS database platform.
- Identify the database engines supported by Amazon RDS.
- Explain the mechanics and differences between automated backups and manual database snapshots.
- Compare Multi-AZ deployments (for high availability) with Read Replicas (for performance scaling).

## Why This Matters
Installing and managing relational databases manually on host servers is complex. Database administrators (DBAs) must manage operating system installation, allocate storage arrays, configure firewall access, implement automated backup scripts, plan failover strategies, and perform database software upgrades. 

Amazon RDS simplifies this by providing a fully managed relational database service. By offloading infrastructure management to AWS, organizations can launch databases in minutes, automate backups and software patching, and scale storage and compute resources with a single click. Understanding RDS capabilities allows developers to design robust, scalable databases while focusing their efforts on schema design and query optimization.

## The Concept

### What is Amazon RDS?
Amazon Relational Database Service (RDS) is a fully managed web service that simplifies the setup, operation, and scaling of relational databases in the AWS cloud. As a Platform as a Service (PaaS) offering, RDS manages the underlying hardware, operating system, and database software installations, leaving database schema design, indexing, and querying to the customer.

### Supported Database Engines
Amazon RDS supports six relational database engines:
1. **PostgreSQL**
2. **MySQL**
3. **MariaDB**
4. **Oracle**
5. **Microsoft SQL Server**
6. **Amazon Aurora**: A cloud-native relational database engine built by AWS that is compatible with MySQL and PostgreSQL, offering up to 5x the throughput of standard MySQL.

---

### Managed Database Maintenance: Backups and Snapshots
RDS provides two mechanisms for securing database states:

#### 1. Automated Backups
- **How It Works**: When enabled, RDS automatically backs up your database volume and captures transaction logs in real time. 
- **Point-in-Time Recovery (PITR)**: Allows you to restore your database to any specific second within your backup retention window (typically 1 to 35 days).
- **Cleanup**: Automated backups are deleted automatically when you delete the parent RDS database instance.

#### 2. Manual Snapshots
- **How It Works**: User-initiated, full database backups.
- **Retention**: Manual snapshots are stored indefinitely in Amazon S3, even after the source RDS database instance is deleted. They are useful for archiving database states before running major migrations.

---

### Multi-AZ Deployments vs. Read Replicas
To optimize performance and reliability, RDS offers two distinct replication strategies:

| Feature Metric | Multi-AZ Deployments | Read Replicas |
|---|---|---|
| **Primary Purpose** | **High Availability & Disaster Recovery** | **Read Performance Scaling** |
| **Replication Method**| Synchronous replication (writes are committed to primary and standby). | Asynchronous replication (standby updates shortly after primary commits). |
| **Database Count** | One active primary DB, one passive standby DB. | One active primary DB, one or more active read-only DBs. |
| **Failover Action** | Automatic failover to the standby DB when the primary goes offline. | Manual promotion of replica to primary if the source DB goes offline. |
| **Write/Read Access** | Applications write/read from the primary DB only. Standby is passive. | Applications write to the primary DB, and route read queries to replicas. |
| **Deployment Zone** | Spans across different Availability Zones (AZs) in a single Region. | Can span across different AZs or different AWS Regions. |

```
                 +--------------------------+
                 |       Application        |
                 +--------------------------+
                    | (Writes)          | (Reads)
                    v                   v
        +---------------------+   +---------------------+
        |  US-EAST-1A (Primary|   |  US-EAST-1B (Replica|
        |  PostgreSQL Database|==>|  PostgreSQL Database|
        |      (Read/Write)   |   |     (Read-Only)     |
        +---------------------+   +---------------------+
                            (Asynchronous)
```

## Code Examples

Let us use the AWS CLI to deploy, modify, and query Amazon RDS instances.

### Launching an RDS PostgreSQL Instance
This command launches a managed PostgreSQL database inside the default region:

```bash
aws rds create-db-instance \
    --db-instance-identifier developer-postgres-db \
    --db-instance-class db.t3.micro \
    --engine postgres \
    --engine-version 14.7 \
    --allocated-storage 20 \
    --master-username rds_admin \
    --master-user-password PasswordSecure987 \
    --backup-retention-period 7 # Enables automated daily backups
```

### Adding a Read Replica for Scaling Read Traffic
To scale read capacity, you can create a read replica linked to your primary database instance:

```bash
aws rds create-db-instance-read-replica \
    --db-instance-identifier developer-postgres-replica \
    --source-db-instance-identifier developer-postgres-db \
    --db-instance-class db.t3.micro
```

### Creating a Manual Database Snapshot
Before performing database schema migrations, you can create a manual snapshot:

```bash
aws rds create-db-snapshot \
    --db-snapshot-identifier pre-migration-snapshot-01 \
    --db-instance-identifier developer-postgres-db
```

## Summary
- **Amazon RDS** is a managed PaaS database platform supporting PostgreSQL, MySQL, MariaDB, Oracle, SQL Server, and Aurora.
- **Automated Backups** support point-in-time recovery to any second, while **Manual Snapshots** are user-initiated and saved indefinitely.
- **Multi-AZ deployments** replicate data synchronously to a passive standby database for **High Availability**.
- **Read Replicas** replicate data asynchronously to active read-only database instances to scale **Read Performance**.

## Additional Resources
- [Amazon RDS User Guide](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/Welcome.html)
- [Working with Database Replication in Amazon RDS](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/USER_ReadRepl.html)
- [RDS Backup and Restore Best Practices](https://docs.aws.amazon.com/AmazonRDS/latest/UserGuide/USER_WorkingWithAutomatedBackups.html)
