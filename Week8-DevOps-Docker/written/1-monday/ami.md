# Amazon Machine Images (AMI): Concepts and Custom Configuration

## Learning Objectives
- Describe the structure and contents of an Amazon Machine Image (AMI).
- Create a custom AMI from an active EC2 instance.
- Distinguish between an AMI and an EBS Snapshot.
- Analyze the "Golden AMI" design pattern.
- Manage sharing settings and permissions for AMIs.

---

## Why This Matters
When an EC2 instance launches, it boots from a template image. If you launch a standard base Ubuntu or Amazon Linux image, the virtual server starts empty. You must then wait for automation scripts (like User Data) to update the OS packages, install Java, pull the Spring Boot executable, set up logs, and configure security parameters. This boot configuration phase can take 5 to 10 minutes.

If an Auto Scaling Group launches a new instance during a traffic spike, waiting 10 minutes for configuration leaves your application vulnerable to crashes.

The **Golden AMI** pattern resolves this. You build a custom image pre-loaded with the exact runtime, configuration files, and software dependencies required. Your instances boot ready to serve traffic instantly, improving scaling response times.

---

## The Concept

### 1. What is an Amazon Machine Image (AMI)?
An **AMI** is a template containing the software configuration required to launch a virtual instance. It contains:
- **Root Volume Template**: A snapshot of the root filesystem containing the Operating System (e.g., Ubuntu, Linux) and any pre-installed software dependencies.
- **Launch Permissions**: Controls which AWS accounts can use the AMI to launch instances (Public, Private, or shared with specific AWS IDs).
- **Block Device Mapping**: Specifies which volumes (e.g., EBS disks) to attach to the instance upon launch.

---

### 2. AMI vs. EBS Snapshot
It is common to confuse AMIs with EBS Snapshots, but they occupy different layers in the AWS storage ecosystem:

| Attribute | EBS Snapshot | Amazon Machine Image (AMI) |
| :--- | :--- | :--- |
| **Primary Purpose** | Raw backup of data blocks. | Bootable template configuration. |
| **Target** | Individual EBS Storage Volumes. | Virtual Machine instances (includes CPU architectures and multiple volume mappings). |
| **Bootable?** | No. You cannot launch an instance directly from a raw snapshot file. | Yes. An AMI contains the partition metadata and bootloader required to start a server. |

---

### 3. The "Golden AMI" Architecture Pattern
A **Golden AMI** is a pre-configured virtual machine template:
1.  **Baseline Launch**: You boot a clean OS (e.g., Amazon Linux 2023).
2.  **Configuration**: You patch the system, install dependencies (like Corretto Java 17, Docker Engine, Splunk Forwarders), and configure security parameters.
3.  **Baking**: You capture a snapshot of the configured instance, generating a custom "Golden AMI."
4.  **Scaling**: The Auto Scaling Group uses this custom AMI to launch new instances. Boot times drop to under 60 seconds because all software dependencies are already present.

```
+---------------+      Install Java,      +---------------+     Bake Custom      +--------------+
|   Standard    | ====> Docker, logs, ===>|  Configured   | ===================> |  GOLDEN AMI  |
|   Base AMI    |      security tools     |  EC2 Instance |  (Create Image)      |  (Blueprint) |
+---------------+                         +---------------+                      +-------t------+
                                                                                         |
                                                                                         | Launch
                                                                                         v
                                                                                 +--------------+
                                                                                 | Fast-Booting |
                                                                                 | ASG Fleet    |
                                                                                 +--------------+
```

---

### 4. Sharing and Security Permissions
Custom AMIs are private to the AWS account that created them. However, they can be shared:
- **Private Sharing**: Grant launch permissions directly to specific AWS Account IDs (best for secure corporate environments).
- **Public Sharing**: Publish the image globally (best for open-source tools or marketplace software).
- **Deregistration**: When an AMI is deprecated, you *deregister* it to prevent new instances from launching from it. The underlying EBS snapshots must be deleted separately to stop storage billing.

---

## Code Examples and Walkthroughs

### 1. Creating a Custom AMI via AWS CLI
You can capture a custom image from a running instance. By default, AWS stops the instance temporarily during image creation to ensure data consistency on disk:

```bash
# 1. Create a custom Golden AMI from a configured instance
aws ec2 create-image \
    --instance-id i-0abcdef1234567890 \
    --name "Golden-Java17-Docker-Image-v1" \
    --description "Ubuntu 22.04 base with Corretto Java 17 and Docker engine" \
    --no-reboot

# The --no-reboot flag overrides the default behavior, capturing the image 
# while the instance is running. (Use with caution to avoid filesystem corruption).
# Record the ImageId (e.g., ami-0987654321fedcba0)
```

```bash
# 2. Check the creation status of the AMI
aws ec2 describe-images \
    --image-ids ami-0987654321fedcba0 \
    --query "Images[*].State" \
    --output text

# Expected output: pending, then available when complete
```

### 2. Modifying AMI Launch Permissions
To share the custom image securely with a secondary development account:

```bash
# Grant launch permissions to another AWS Account ID (e.g., 987654321012)
aws ec2 modify-image-attribute \
    --image-id ami-0987654321fedcba0 \
    --launch-permission "Add=[{UserId=987654321012}]"

# Verification:
# Run describe attributes to verify the target ID has permission to launch:
aws ec2 describe-image-attributes \
    --image-id ami-0987654321fedcba0 \
    --attribute launchPermission
```

---

## Summary
- An **AMI** is a bootable template containing an Operating System snapshot, launch permissions, and volume mappings.
- **AMIs vs. Snapshots**: Snapshots are individual virtual drive backups, while AMIs are bootable templates for entire servers.
- The **Golden AMI Pattern** reduces scaling startup times by baking dependency packages directly into the template image ahead of time.
- **Launch Permissions** control image access, restricting launch capabilities to specific AWS account IDs or publishing them publicly.

---

## Additional Resources
- [AWS Guide: How to Create an Amazon Machine Image](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/creating-an-ami-ebs.html)
- [AWS Architecture Blog: The Golden AMI Pipeline Pattern](https://aws.amazon.com/blogs/aws-marketplace/building-a-golden-ami-pipeline-with-aws-systems-manager-and-packer/)
- [AWS Command Reference: modify-image-attribute](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/ec2/modify-image-attribute.html)
