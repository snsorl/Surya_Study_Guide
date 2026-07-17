# Lab Exercise: Launching and Configuring an AWS EC2 Instance

## Learning Objectives
- Provision a virtual AWS EC2 instance (`t3.micro`).
- Configure security group firewall rules for SSH (port `22`) and Spring Boot API (port `8080`).
- Establish an interactive SSH console session from a local terminal.
- Install the Java JRE runtime and execute a compiled Spring Boot JAR.
- Test endpoint connectivity from Postman.

---

## The Scenario
Your development team needs to deploy the Spring Boot REST API for **Project 3** to a cloud environment. Instead of running on your local machine, the application must run on a secure, virtual AWS EC2 server. You must set up the instance, install Java, configure firewall security rules, run the application, and verify public access.

---

## Tasks

### Task 1: Provision the EC2 Instance
1.  Log in to the **AWS Management Console**.
2.  Navigate to **EC2 > Instances** and click **Launch Instances**.
3.  Configure the instance settings:
    *   **Name**: `project3-api-sandbox`
    *   **Application and OS Image**: `Amazon Linux 2023 AMI` (Free tier eligible)
    *   **Instance Type**: `t3.micro` (or `t2.micro` based on account region)
    *   **Key Pair**: Create a new key pair named `project3-key`. Download the `.pem` file and save it to your local machine (`~/.ssh/project3-key.pem`).
4.  Launch the instance and wait for the status to show **Running**.

---

### Task 2: Configure the Security Group
By default, EC2 instances block incoming connections. You must open firewall ports:
1.  On the EC2 instances page, select your instance and click the **Security** tab.
2.  Click the security group link to edit rules.
3.  Click **Edit Inbound Rules** and add these rules:
    *   **Rule 1**: SSH | Port `22` | Source: `My IP` (Restricts console access to your machine).
    *   **Rule 2**: Custom TCP | Port `8080` | Source: `Anywhere-IPv4` (`0.0.0.0/0`) (Exposes the Spring Boot port).
4.  Save the changes.

---

### Task 3: Establish SSH Connection and Install Java
1.  Open a terminal on your local machine and set secure file permissions on your key file:
    ```bash
    chmod 400 ~/.ssh/project3-key.pem
    ```
2.  Connect to your EC2 instance using its Public IP:
    ```bash
    ssh -i ~/.ssh/project3-key.pem ec2-user@<YOUR-EC2-PUBLIC-IP>
    ```
3.  Update system packages and install Java 17:
    ```bash
    sudo yum update -y
    sudo yum install java-17-amazon-corretto-headless -y
    ```
4.  Verify the installation:
    ```bash
    java -version
    ```

---

### Task 4: Run the Application and Verify
1.  From your local terminal, upload the compiled JAR file to the EC2 instance using `scp`:
    ```bash
    scp -i ~/.ssh/project3-key.pem target/project3-api-1.0.0.jar ec2-user@<YOUR-EC2-PUBLIC-IP>:~/app.jar
    ```
2.  In your SSH terminal, run the application:
    ```bash
    java -jar ~/app.jar
    ```
3.  On your local machine, open **Postman** and send a `GET` request to:
    `http://<YOUR-EC2-PUBLIC-IP>:8080/actuator/health`

---

## Definition of Done
- The EC2 instance is active and running.
- Inbound security group rules allow SSH and port 8080 traffic.
- Postman returns a `200 OK` status with `{"status":"UP"}` from the health endpoint.

---

## Troubleshooting Tips
- **SSH Connection Timeout**: Confirm your inbound security group rule allows port `22` traffic from your IP.
- **Port 8080 Connection Denied**: Verify the Spring Boot application started successfully in your SSH terminal and is listening on port `8080`.
