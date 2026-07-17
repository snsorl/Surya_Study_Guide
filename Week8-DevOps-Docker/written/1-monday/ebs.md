# Amazon Elastic Block Store (EBS): Persistent Block Storage in the Cloud

## Learning Objectives
- Describe the purpose and operation of Amazon EBS block storage volumes.
- Compare common EBS volume types: General Purpose (gp3), Provisioned IOPS (io2), Throughput Optimized (st1), and Cold HDD (sc1).
- Explain the processes for attaching, detaching, and resizing EBS volumes.
- Detail the creation, recovery, and security parameters of EBS Snapshots.
- Evaluate storage use cases comparing EBS, Amazon EFS (Elastic File System), and Amazon S3.

---

## Why This Matters
EC2 instances have a default local storage drive known as "Instance Store." However, Instance Store storage is **ephemeral**: if the instance is stopped, restarted on a different physical host, or terminated, all local data is lost permanently. For applications storing user data, server logs, or transactional databases, ephemeral storage is a high risk.

Amazon Elastic Block Store (EBS) provides persistent block-level storage volumes for EC2 instances. EBS volumes behave like physical hard drives connected to your server: they exist independently of the EC2 instance's power cycle, ensuring database engines (like PostgreSQL) keep data safe even if the compute VM is terminated or replaced by an Auto Scaling Group.

---

## The Concept

### 1. What is Amazon EBS?
Amazon EBS is a network-attached block storage system. 
- **Network-Attached**: The physical storage drive is not inside the physical EC2 chassis; it resides on an adjacent storage network and communicates with the EC2 instance over high-speed networks.
- **Availability Zone Bound**: An EBS volume is created within a specific AWS Availability Zone (AZ). It can only be attached to EC2 instances residing in that same AZ.
- **1-to-1 Mapping (Typically)**: A standard EBS volume can only be attached to a single EC2 instance at a time (though specialty `Multi-Attach` is supported on certain SSD volumes).

```
   Availability Zone 1a
  +-----------------------------------------------------+
  |                                                     |
  |  +--------------------+      High-Speed Network     |
  |  |    EC2 INSTANCE    |<========================+   |
  |  |  (Compute Server)  |                         ||  |
  |  +--------------------+                         ||  |
  |                                                 ||  |
  |  +--------------------+                         ||  |
  |  |    EC2 INSTANCE    |                         ||  |
  |  |  (Compute Server)  |                         ||  |
  |  +--------------------+                         ||  |
  |                                                 ||  |
  |  +----------------------------------------------v+  |
  |  |               EBS STORAGE VOLUME              |  |
  |  |       - Persistent database data block        |  |
  |  +-----------------------------------------------+  |
  +-----------------------------------------------------+
```

---

### 2. EBS Volume Types
AWS optimizes EBS volumes for either high Input/Output Operations Per Second (IOPS) or high throughput bandwidth:

#### Solid State Drives (SSD) - Optimized for transactional workloads
- **General Purpose SSD (`gp2`, `gp3`)**: Balanced price and performance. `gp3` allows provisioning IOPS (up to 16,000) and throughput independently of volume size. Best for system boot drives, virtual desktops, and development databases.
- **Provisioned IOPS SSD (`io1`, `io2`)**: Ultra-high performance, lowest latency storage. Designed for mission-critical, high-volume databases (Oracle, PostgreSQL, Microsoft SQL Server).

#### Hard Disk Drives (HDD) - Optimized for large streaming workloads
- **Throughput Optimized HDD (`st1`)**: Low cost, high throughput. Designed for data warehousing, big data analysis, and log aggregation (e.g., streaming large text files to Splunk forwarders). Cannot be used as a boot volume.
- **Cold HDD (`sc1`)**: Lowest cost storage. Designed for cold, infrequently accessed data (archived backups). Cannot be used as a boot volume.

---

### 3. Lifecycle Operations: Attaching, Detaching, and Snapshots
- **Dynamic Attachment**: You can attach or detach EBS volumes to running EC2 instances on the fly without rebooting the OS.
- **EBS Snapshots**: Point-in-time backups of your EBS volume stored in Amazon S3. Snapshots are **incremental**: only blocks that have changed since the last snapshot are recorded, keeping storage costs minimal.
- **Encryption**: EBS supports full volume encryption at rest using AWS Key Management Service (KMS) customer keys. It has no performance impact, and snapshots created from encrypted volumes are automatically encrypted.

---

### 4. AWS Storage Comparison: EBS vs. EFS vs. S3
Deciding where to store data depends on file sharing requirements and access patterns:

| Storage Type | Amazon EBS (Block) | Amazon EFS (File) | Amazon S3 (Object) |
| :--- | :--- | :--- | :--- |
| **Model** | Hard Drive (SAN) | Shared Network Drive (NAS) | Web Storage (API/HTTP) |
| **Simultaneous Connections** | Single EC2 instance (usually) | Thousands of EC2 instances | Unlimited connections via web |
| **Protocol** | Block Interface (SATA/NVMe) | NFSv4 (POSIX file system) | HTTPS REST API |
| **Latency** | Single-digit millisecond (Very low) | Low (tens of milliseconds) | High (first byte in ~100ms) |
| **Use Case** | System boot disks, database engines. | Shared configuration folders, shared media directory. | User file uploads, static site hosting, data lake assets. |

---

## Code Examples and Walkthroughs

### 1. Attaching and Formatting a New EBS Volume (Linux Console)
When you attach a new EBS volume to an EC2 instance, it appears as a raw, unformatted device block. You must partition and mount it inside the operating system before using it:

```bash
# 1. List the storage devices attached to the server
lsblk

# Expected output shows physical devices (e.g., nvme1n1 or xvdf)
# NAME        MAJ:MIN RM  SIZE RO TYPE MOUNTPOINTS
# xvda        202:0    0    8G  0 disk 
# └─xvda1     202:1    0    8G  0 part /
# xvdf        202:80   0   20G  0 disk  <-- Our new 20GB volume

# 2. Check if the volume contains an existing filesystem (returns 'data' if empty)
sudo file -s /dev/xvdf

# 3. Create an ext4 filesystem on the raw device
sudo mkfs -t ext4 /dev/xvdf

# 4. Create a mount directory
sudo mkdir /data

# 5. Mount the drive to the mount directory
sudo mount /dev/xvdf /data

# Verification:
# Run disk space checks to verify the new volume is mounted and writable:
df -h /data
```

### 2. Backing up an EBS Volume via CLI Snapshots
You can trigger snapshots using the AWS CLI:

```bash
# Create an incremental backup snapshot of an EBS volume
aws ec2 create-snapshot \
    --volume-id vol-0123456789abcdef0 \
    --description "Project 3 Postgres database backup"

# The CLI output returns JSON metadata including the SnapshotId:
# {
#     "SnapshotId": "snap-0987654321fedcba0",
#     "VolumeId": "vol-0123456789abcdef0",
#     "State": "pending",
#     "Description": "Project 3 Postgres database backup"
# }
```

---

## Summary
- **Amazon EBS** provides AZ-bound block storage that persists independent of the associated EC2 compute lifecycle.
- **Volume types** accommodate different application priorities: SSD drives (`gp3`/`io2`) optimize transaction speeds, while HDD drives (`st1`/`sc1`) optimize throughput cost.
- **EBS Snapshots** are incremental, secure backups saved directly to Amazon S3.
- **Storage Selection**: Choose **EBS** for databases, **EFS** for network directories shared across multiple servers, and **S3** for general web-accessible file storage.

---

## Additional Resources
- [Amazon EBS Volume Types Reference Guide](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ebs-volume-types.html)
- [AWS Guide: Incremental EBS Snapshots](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/EBSSnapshots.html)
- [AWS Storage Services Overview Comparison](https://aws.amazon.com/products/storage/)
