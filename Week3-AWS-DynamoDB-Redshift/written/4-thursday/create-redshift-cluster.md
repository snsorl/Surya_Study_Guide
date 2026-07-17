# Provisioning an Amazon Redshift Cluster

## Learning Objectives
- Provision an Amazon Redshift cluster using configuration parameters.
- Select node types and compute capacities based on analytical workloads.
- Configure Virtual Private Cloud (VPC) subnets and security group settings for secure network access.
- Authorize Redshift clusters to access other AWS services using IAM roles.

## Why This Matters
Before you can run analytical SQL queries on Amazon Redshift, you must provision the database cluster infrastructure. Unlike serverless databases (like DynamoDB) that scale automatically without configuration, Redshift requires you to define a cluster of compute nodes. 

Setting up a Redshift cluster involves configuring several layers: choosing compute hardware, setting up network subnets (VPC), defining firewall access rules (Security Groups), and authorizing access to data sources (like S3 buckets) using IAM roles. Understanding this setup process is a core skill for cloud architects and data engineers, ensuring that your data warehouse is secure, scalable, and connected to your data sources.

## The Concept

### Redshift Node Types
When configuring a cluster, you must select the hardware specification for your compute nodes. Redshift offers two primary node families:

1. **RA3 (Recommended)**:
   - *Mechanics*: Separates compute capacity from storage capacity. You pay for the compute nodes you run, and data is saved on low-cost managed storage (backed by S3). This allows you to scale compute and storage independently.
   - *Use Case*: Standard for production warehouses where data size is large but query volume fluctuates.
2. **Dense Compute (DC2)**:
   - *Mechanics*: Uses local SSD storage. Storage and compute are linked; if you need more storage, you must buy more compute nodes.
   - *Use Case*: Suitable for small, high-performance datasets under 500 GB.

---

### Step-by-Step Provisioning Guide
Setting up a secure Redshift cluster involves configuring three resource categories:

```
+---------------------------------------------------------+
|                  1. Compute Cluster                     |
|  - Define Cluster Identifier, Node Type, and Node Count |
|  - Set master username and admin credentials            |
+---------------------------------------------------------+
                             |
                             v
+---------------------------------------------------------+
|                  2. Network Isolation                   |
|  - Assign cluster to Virtual Private Cloud (VPC)        |
|  - Restrict access via Database Security Group rules    |
+---------------------------------------------------------+
                             |
                             v
+---------------------------------------------------------+
|               3. Access Authorization                   |
|  - Attach IAM Role to allow access to S3 buckets        |
+---------------------------------------------------------+
```

#### Step 1: Cluster Configuration
Define the physical properties of the cluster:
- **Cluster Identifier**: A unique name (e.g., `enterprise-dw-cluster`).
- **Database Name**: The default database name (e.g., `dev`).
- **Database Port**: The network port used to connect (default is `5439`).
- **Node Type and Count**: Choose the node class (e.g., `ra3.xlplus`) and the number of compute nodes.

#### Step 2: VPC and Network Settings
Isolate the data warehouse inside a Virtual Private Cloud (VPC):
- **VPC Subnet Group**: Assign the cluster to specific private subnets within your VPC, ensuring it is not directly exposed to the public internet.
- **Security Group**: Configure firewall rules to restrict ingress traffic. By default, block all access, and explicitly authorize port `5439` access only from trusted application servers or corporate IP ranges.

#### Step 3: IAM Role Setup
To load data from S3 buckets into Redshift, the cluster must be authorized to read S3 resources:
- Create an **IAM Role** containing the `AmazonS3ReadOnlyAccess` policy.
- Associate this IAM Role with your Redshift cluster during provisioning. This allows the cluster to assume the role and execute S3 read requests safely.

## Code Examples

Let us use the AWS CLI to automate the provisioning of a Redshift cluster.

### 1. Creating the IAM Role for S3 Access
First, create an IAM trust policy that allows the Redshift service to assume the role.

Save the following trust policy as `redshift-trust-policy.json`:
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "redshift.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
```

Create the role and attach the S3 read policy:
```bash
# Create the IAM Role
aws iam create-role \
    --role-name RedshiftS3ReadRole \
    --assume-role-policy-document file://redshift-trust-policy.json

# Attach the read-only S3 access policy
aws iam attach-role-policy \
    --role-name RedshiftS3ReadRole \
    --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess
```

### 2. Provisioning the Redshift Cluster via CLI
Launch the cluster using the node specifications, network groups, and the IAM role created above:

```bash
aws redshift create-cluster \
    --cluster-identifier enterprise-dw \
    --node-type ra3.xlplus \
    --number-of-nodes 2 \
    --db-name dev \
    --master-username dw_admin \
    --master-user-password SecurityPassword987 \
    --vpc-security-group-ids sg-0987654321fedcba0 \
    --cluster-subnet-group-name default-vpc-subnet-group \
    --iam-roles arn:aws:iam::123456789012:role/RedshiftS3ReadRole
```

*Note*: Replace the account ID in the `--iam-roles` parameter with your actual AWS account number.

### 3. Verifying Cluster Status
Provisioning a cluster takes several minutes. You can check the status of the creation process using this command:

```bash
aws redshift describe-clusters \
    --cluster-identifier enterprise-dw \
    --query "Clusters[*].[ClusterIdentifier,ClusterStatus,Endpoint.Address]" \
    --output table
```

Output:
```text
----------------------------------------------------------------------------------------
|                                    DescribeClusters                                  |
+---------------+----------------+-----------------------------------------------------+
|  enterprise-dw|  available     |  enterprise-dw.c1234567890.us-east-1.redshift...    |
+---------------+----------------+-----------------------------------------------------+
```

Once the status transitions to `available`, the cluster is ready, and you can use the endpoint address to connect your database clients.

## Summary
- Amazon Redshift clusters are provisioned by configuring **Leader Nodes** and **Compute Nodes**.
- **RA3 node types** are recommended for production because they separate compute scaling from low-cost storage scaling.
- Secure the cluster by isolating it within private **VPC subnets** and restricting network access via **Security Groups**.
- Attach an **IAM Role** containing S3 read permissions to allow the cluster to access data files stored in S3.

## Additional Resources
- [Amazon Redshift Cluster Management Guide](https://docs.aws.amazon.com/redshift/latest/mgmt/working-with-clusters.html)
- [AWS CLI Reference - Redshift Commands](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/redshift/index.html)
- [How to Set Up Network Access for a Redshift Cluster](https://docs.aws.amazon.com/redshift/latest/mgmt/managing-cluster-security-groups.html)
