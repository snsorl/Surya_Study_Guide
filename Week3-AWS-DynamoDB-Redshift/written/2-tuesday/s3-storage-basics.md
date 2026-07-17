# Amazon Simple Storage Service (S3) Basics

## Learning Objectives
- Explain the key-value structure of Amazon S3, including buckets, objects, and keys.
- Compare the use cases and cost tradeoffs of S3 Storage Classes: Standard, Infrequent Access (IA), and Glacier.
- Configure S3 object versioning and define lifecycle policies.
- Explain the significance of S3 Block Public Access settings for data security.

## Why This Matters
Relational databases are designed to store structured, queryable tabular data. They are not suited for storing large, unstructured files (such as images, PDF reports, application installers, or backup archives). Storing massive files directly in database tables slows down queries, bloats table sizes, and increases storage costs.

Amazon Simple Storage Service (S3) provides an alternative. S3 is a highly durable, scalable, and low-cost object storage service designed to store unstructured files. In cloud architectures, developers store files in S3 and save the corresponding S3 URLs in their databases (like PostgreSQL or DynamoDB). By understanding S3 fundamentals, developers can build scalable architectures that handle large files efficiently.

## The Concept

### What is Amazon S3?
Amazon S3 (Simple Storage Service) is an object storage service offering industry-leading scalability, data availability, security, and performance. S3 is designed for 99.999999999% (11 9s) of durability, achieved by replicating data across multiple physical facilities within an AWS region automatically.

### Key Concepts: Buckets, Objects, and Keys
Unlike standard operating systems that organize files in hierarchical folder structures, S3 uses a flat, key-value storage structure:

- **Buckets**: Logical containers for objects stored in S3. S3 bucket names are globally unique across all AWS accounts.
- **Objects**: The actual files stored in a bucket. An object contains the file data, a unique identifier, and metadata (system metadata and user-defined tags).
- **Keys**: The unique identifier (path name) for an object inside a bucket. In S3, directories are logical, not physical. An object key like `uploads/2026/report.pdf` is a single string containing slash characters, which client tools display as folders for readability.

---

### S3 Storage Classes
To optimize storage costs, S3 offers several storage classes based on access frequency:

1. **S3 Standard**: Default class. Designed for active, frequently accessed data. Offers low latency and high throughput.
2. **S3 Standard-Infrequent Access (S3 Standard-IA)**: Designed for data that is accessed less frequently (e.g., once a month) but requires rapid access when needed. Offers lower storage costs than Standard, but charges a retrieval fee per gigabyte read.
3. **S3 Glacier Flexible Retrieval**: Low-cost storage class designed for archival data. Retrieval times range from minutes to hours.
4. **S3 Glacier Deep Archive**: The lowest-cost storage class in AWS. Designed for long-term data archiving (e.g., compliance records). Retrieval times are typically 12 to 48 hours.

---

### Versioning and Lifecycle Policies
- **Object Versioning**: A bucket configuration that saves multiple versions of an object. If versioning is enabled, modifying or deleting an object creates a new version instead of overwriting the file. This protects against accidental deletions or application errors.
- **Lifecycle Policies**: Automated rules that manage objects over time. For example, a policy can automatically move raw logs from S3 Standard to Standard-IA after 30 days, transition them to S3 Glacier after 90 days, and delete them permanently after one year.

---

### S3 Block Public Access Settings
By default, all newly created S3 buckets have public access disabled. S3 Block Public Access is a centralized security control that prevents users from accidentally exposing bucket contents to the public internet. Keeping Block Public Access enabled is a security best practice for buckets containing proprietary data, database backups, or user uploads.

## Code Examples

Let us use the AWS CLI to create buckets, upload objects, and configure lifecycle rules.

### Creating an S3 Bucket and Uploading a File
Since bucket names must be globally unique, you should include a distinct prefix:

```bash
# 1. Create a new S3 bucket in the N. Virginia region
aws s3 mb s3://enterprise-sales-data-2026

# 2. Upload a CSV file to the bucket
aws s3 cp sales-report.csv s3://enterprise-sales-data-2026/uploads/sales-report.csv

# 3. List the contents of the bucket
aws s3 ls s3://enterprise-sales-data-2026/ --recursive
```

Output:
```text
2026-07-14 10:45:12       1285 uploads/sales-report.csv
```

### Enabling Object Versioning
To protect files against accidental deletion, enable versioning on your bucket:

```bash
aws s3api put-bucket-versioning \
    --bucket enterprise-sales-data-2026 \
    --versioning-configuration Status=Enabled
```

### Configuring an S3 Lifecycle Policy
To automate data lifecycle management, create a policy configuration (JSON) and apply it to your bucket.

#### `lifecycle-policy.json`:
```json
{
  "Rules": [
    {
      "ID": "MoveOlderFilesToIAAndGlacier",
      "Status": "Enabled",
      "Prefix": "uploads/",
      "Transitions": [
        {
          "Days": 30,
          "StorageClass": "STANDARD_IA"
        },
        {
          "Days": 90,
          "StorageClass": "GLACIER"
        }
      ]
    }
  ]
}
```

Apply this policy to the bucket:
```bash
aws s3api put-bucket-lifecycle-configuration \
    --bucket enterprise-sales-data-2026 \
    --lifecycle-configuration file://lifecycle-policy.json
```

## Summary
- **Amazon S3** is a durable, key-value object storage service designed to store unstructured files.
- S3 resources are organized into globally unique **Buckets** containing **Objects** identified by unique **Keys**.
- S3 offers various **Storage Classes** (Standard, IA, Glacier) to balance access frequency against storage costs.
- **Versioning** retains historical copies of files, and **Lifecycle Policies** automate file transitions to archive tiers.
- **Block Public Access** is a critical security control that blocks unauthorized public access to buckets.

## Additional Resources
- [Amazon S3 User Guide](https://docs.aws.amazon.com/AmazonS3/latest/userguide/Welcome.html)
- [Amazon S3 Storage Classes Catalog](https://aws.amazon.com/s3/storage-classes/)
- [Managing Object Lifecycles in S3](https://docs.aws.amazon.com/AmazonS3/latest/userguide/object-lifecycle-mgmt.html)
