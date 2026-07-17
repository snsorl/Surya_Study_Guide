# SSH and EC2 Instance Connect: Securely Accessing Virtual Servers

## Learning Objectives
- Generate SSH key pairs using the AWS Console and command line utilities.
- Configure local file permissions (`chmod 400`) on private key files (`.pem`).
- Establish a remote command terminal using the `ssh -i` command.
- Diagnose and troubleshoot common SSH connection errors (timeouts, permission denied, key mismatches).
- Contrast standard SSH with AWS EC2 Instance Connect.

---

## Why This Matters
Launching a server in the cloud is only the first step. To configure, manage, and debug your application, you must be able to log in to the virtual machine. Because EC2 instances sit in secure remote data centers, you need a secure, encrypted channel to execute terminal commands.

Secure Shell (SSH) is the industry standard for logging in to and managing Unix-based servers. Without a clear understanding of SSH key mechanisms, file permissions, and routing, you will face authentication blocks when attempting to configure your Spring Boot runtime or troubleshoot a running Docker container on your cloud instance.

---

## The Concept

### 1. Cryptographic Authentication: Key Pairs
SSH does not use standard username/password combinations. Instead, it relies on asymmetric cryptography (RSA or ED25519 key pairs):
- **Private Key (`.pem` file)**: Stored locally on your workstation. It should remain private. If anyone gains access to this key, they can access your server.
- **Public Key**: Uploaded to AWS and injected into the target EC2 instance's `/home/[username]/.ssh/authorized_keys` file when it boots.

During connection, the SSH client uses the private key to sign a challenge from the server. The server verifies the signature using the matching public key. If the signature matches, connection is granted.

---

### 2. Guarding the Key: File Permissions (`chmod`)
Linux/Unix SSH clients enforce strict security checks. If your private key file (`.pem`) is accessible by other users on your local computer, the SSH client will refuse to connect and throw an error.
You must update the file permissions using the `chmod` command to ensure the file is readable only by your owner account:

- **Permission Value: `400`**
  - `4`: Read permissions for the owner.
  - `0`: No permissions for the owner's user group.
  - `0`: No permissions for other users.

```
          Local Private Key File (.pem)
           Permissions must be restricted:
                 -r-------- (400)
                        |
                        | (ssh -i key.pem user@ip)
                        v
+-----------------------------------------------+
|             AWS CLOUD ENVIRONMENT             |
|                                               |
|  EC2 Instance IP: 54.210.12.3                 |
|  Public key matching private key registered   |
|  in authorized_keys                           |
+-----------------------------------------------+
```

---

### 3. Common Connection Failures
Troubleshooting connectivity is a key DevOps skill. Typical failures include:

#### Connection Timeout (`ssh: connect to host ... port 22: Connection timed out`)
- **Root Cause**: The network request did not reach the server.
- **Solution**: Check the EC2 Instance's **Security Group**. The group must have an inbound rule allowing traffic on Port 22 (SSH) from your local IP address. Also verify the instance has a public IP address and sits in a routing path connected to an AWS Internet Gateway.

#### Permission Denied (`Permission denied (publickey)`)
- **Root Cause**: Authentication failed. The private key used does not match the public key registered in the target instance's `authorized_keys`.
- **Solution**: Verify you are targeting the correct default username for the base OS image:
  - Amazon Linux 2 / Amazon Linux 2023: `ec2-user`
  - Ubuntu Linux: `ubuntu`
  - CentOS: `centos`
  - Debian: `admin`

#### Unprotected Private Key File (`WARNING: UNPROTECTED PRIVATE KEY FILE!`)
- **Root Cause**: The local `.pem` file permissions are too open.
- **Solution**: Execute `chmod 400 keyname.pem` on Linux/Mac, or restrict permissions via File Properties on Windows.

---

### 4. Alternative: EC2 Instance Connect
AWS EC2 Instance Connect offers a browser-based SSH terminal in the AWS Management Console:
- **No Private Keys to Manage**: AWS manages public key authorization dynamically. When you click "Connect," AWS automatically pushes a temporary SSH key to the instance for 60 seconds.
- **Console Access**: Integrates with AWS IAM permissions, eliminating the need to distribute `.pem` files to developers.

---

## Code Examples and Walkthroughs

### 1. Step-by-Step Connection Lifecycle (Linux/Mac Client)
Here is the sequence of commands to configure security and connect to an Ubuntu instance:

```bash
# 1. Move the downloaded PEM key to your secure SSH credentials directory
mkdir -p ~/.ssh
mv ~/Downloads/project3-key.pem ~/.ssh/

# 2. Restrict permissions to read-only for the current owner
chmod 400 ~/.ssh/project3-key.pem

# 3. Verify permissions look correct (should show -r--------)
ls -l ~/.ssh/project3-key.pem

# 4. Connect to the remote EC2 instance using its Public IP address (e.g. 54.210.12.3)
ssh -i ~/.ssh/project3-key.pem ubuntu@54.210.12.3

# The client will ask: "Are you sure you want to continue connecting (yes/no)?"
# Type 'yes' to append the server to your ~/.ssh/known_hosts registry.
```

### 2. Restricting PEM Key Permissions on Windows (PowerShell)
Windows does not support the Unix `chmod` command natively. Use these PowerShell commands to strip inherited permissions from a `.pem` file and assign exclusive access to your active user account:

```powershell
# 1. Store the file path variable
$Path = "C:\Users\JasdhirSingh\.ssh\project3-key.pem"

# 2. Reset and disable inheritance
icacls.exe $Path /reset
icacls.exe $Path /inheritance:r

# 3. Grant explicit Read access to the current active user account
$CurrentUser = [System.Security.Principal.WindowsIdentity]::GetCurrent().Name
icacls.exe $Path /grant "${CurrentUser}:R"

# Verification:
# Run the command to list current Access Control Entries for the file:
icacls.exe $Path
# Output should display permissions exclusively for your active username.
```

---

## Summary
- **Key Pairs** utilize asymmetric cryptography, pairing a local private `.pem` file with a public key injected into the remote server.
- **Chmod 400** is required to lock down private key permissions on Unix/Linux systems; Windows requires restricting file Access Control lists.
- **Connection timeouts** are almost always caused by security groups blocking inbound port 22 or incorrect VPC subnet routing.
- **"Permission denied"** failures result from using the incorrect SSH private key or typing the wrong default username for the operating system.
- **EC2 Instance Connect** is a secure browser-based alternative that pushes keys dynamically, removing the need to manage key files.

---

## Additional Resources
- [AWS Guide: Connect to Linux Instance using SSH](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AccessingInstancesLinux.html)
- [How EC2 Instance Connect Works](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/Connect-using-EC2-Instance-Connect.html)
- [OpenSSH Official Documentation & Troubleshooting Guide](https://www.openssh.com/)
