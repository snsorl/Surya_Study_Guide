# Amazon EC2 Auto Scaling: Maintaining Application Availability and Performance

## Learning Objectives
- Explain the purpose and business benefits of EC2 Auto Scaling.
- Define the configuration components of an Auto Scaling Group: Launch Templates and Capacity Boundaries (min, max, desired).
- Differentiate between various scaling policies: Target Tracking, Step Scaling, and Scheduled Scaling.
- Detail how Amazon CloudWatch alarms trigger Auto Scaling adjustments.
- Compare EC2 Instance status health checks with Application Load Balancer (ALB) health checks.

---

## Why This Matters
Application traffic fluctuates constantly. A Spring Boot API might handle millions of requests during normal business hours, but experience near-zero traffic at 3:00 AM. If you deploy your application on a fixed set of EC2 instances, you face a double penalty: you either over-provision compute and waste money, or you under-provision and risk system crashes due to resource exhaustion under high load.

Amazon EC2 Auto Scaling solves this by automatically adjusting the number of EC2 instances in response to actual application demand. It ensures your infrastructure dynamically shrinks and expands, maximizing resource utilization while maintaining consistent performance and uptime.

---

## The Concept

### 1. What is an Auto Scaling Group (ASG)?
An **Auto Scaling Group** is a collection of EC2 instances managed as a logical unit. The ASG dynamically adjusts its instance count to match current load metrics.

```
       +-------------------------------------------------------+
       |                  AUTO SCALING GROUP                   |
       |                                                       |
       |  Min: 2  <====>  Desired: 3  <====>  Max: 6           |
       |                                                       |
       |  +------------+   +------------+   +------------+     |
       |  |  Instance  |   |  Instance  |   |  Instance  |     |
       |  |  Running   |   |  Running   |   |  Running   |     |
       |  +------------+   +------------+   +------------+     |
       +-------------------------------------------------------+
```

### 2. Core Configuration Components
To configure an ASG, you must define two things: **what** to launch, and **where/how many** to launch.

#### Launch Templates (What to Launch)
A **Launch Template** replaces legacy "Launch Configurations." It defines the configuration blueprint for instances built by the ASG:
- The base OS image (AMI ID).
- The instance type (e.g., `t3.micro`).
- Key pairs, security groups, and storage volumes (EBS).
- User Data scripts (shell scripts executed automatically on boot to download code, install updates, and start service scripts).

#### Group Capacity Boundaries (How Many to Launch)
An ASG operates within strict size boundaries:
- **Min Capacity**: The lower limit of running instances. The ASG will never scale below this number, even if load is zero.
- **Max Capacity**: The upper limit of running instances. It acts as a safety budget cap, ensuring scaling events cannot exceed expected resource costs.
- **Desired Capacity**: The target number of instances the ASG should run *right now*. When first initialized, the ASG launches this exact count.

---

### 3. Scaling Policies
How does the ASG know when to add or remove instances? It uses scaling policies:
- **Target Tracking Scaling**: You select a target metric (e.g., "Keep average CPU usage at 50%"). The ASG automatically handles the math to scale up or down to keep that metric stable. This is the recommended policy for general web workloads.
- **Step Scaling**: Increases or decreases instance counts in steps based on the magnitude of metric violation. For example: "If CPU is between 60% and 75%, add 1 instance. If CPU is above 75%, add 3 instances."
- **Scheduled Scaling**: Adjusts instance limits based on time patterns. Excellent when traffic spikes are highly predictable, such as scaling up ahead of a major retail event or scaling down over weekends.

---

### 4. CloudWatch Alarms: The Scaling Trigger
AWS uses **Amazon CloudWatch** to collect system metrics (CPU utilization, network traffic, custom app logs).
1.  **Metric Collection**: Running instances feed metrics to CloudWatch.
2.  **Alarm Trigger**: You define a threshold (e.g., "CPU utilization > 80% for 3 consecutive minutes"). If met, CloudWatch fires an alarm.
3.  **ASG Action**: The ASG intercepts the alarm and executes the associated scaling policy (e.g., "Launch 1 additional instance").

---

### 5. ASG Health Checks
To maintain high availability, an ASG continuously monitors instance health. If an instance is flagged as unhealthy, the ASG terminates it and launches a fresh replacement.
- **EC2 Health Checks (Default)**: Monitors the virtual machine's status. If the physical host hypervisor fails or the instance operating system hangs, the EC2 status checks fail.
- **ELB (Elastic Load Balancer) Health Checks**: Monitors application status. The load balancer makes periodic HTTP requests to a target endpoint on the instance (e.g., `/health`). If the Spring Boot process crashes or runs out of database connections, `/health` will return a `500` error or time out. The load balancer flags the instance as unhealthy, prompting the ASG to replace it.

---

## Code Examples and Walkthroughs

### 1. Defining a Target Tracking scaling policy via CLI
Below is the configuration pattern used to establish a target tracking scaling policy for an existing Auto Scaling Group:

```json
// target-tracking-cpu.json
{
  "TargetValue": 60.0,
  "PredefinedMetricSpecification": {
    "PredefinedMetricType": "ASGAverageCPUUtilization"
  }
}
```

```bash
# Register the target tracking policy with an Auto Scaling Group
aws autoscaling put-scaling-policy \
    --policy-name cpu-target-tracking-policy \
    --auto-scaling-group-name project3-web-asg \
    --policy-type TargetTrackingScaling \
    --target-tracking-configuration file://target-tracking-cpu.json

# The CLI returns policy details including the CloudWatch alarm metadata created automatically.
```

### 2. Simulating User Data in a Launch Template
A Launch Template's User Data contains bash commands to run at boot. Here is a typical script to install Java 17 and pull a Spring Boot app:

```bash
#!/bin/bash
# 1. Update OS package lists
yum update -y

# 2. Install OpenJDK 17
yum install java-17-amazon-corretto-devel -y

# 3. Download the Spring Boot executable JAR from AWS S3
# (Note: Requires the EC2 Instance Profile IAM role to have S3 read permissions)
aws s3 cp s3://project3-deployments-bucket/api.jar /opt/api.jar

# 4. Create a systemd service file to run the Java app cleanly in the background
cat <<EOF > /etc/systemd/system/springboot-api.service
[Unit]
Description=Spring Boot API Application
After=network.target

[Service]
User=root
ExecStart=/usr/bin/java -jar /opt/api.jar --spring.profiles.active=prod
SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

# 5. Reload systemd config, enable and start the service
systemctl daemon-reload
systemctl enable springboot-api
systemctl start springboot-api
```

---

## Summary
- **EC2 Auto Scaling** maintains application performance by scaling instance fleets dynamically according to workloads.
- **Launch Templates** record deployment blueprints (AMI, networking, security groups, and User Data script instructions).
- **Capacity Parameters** (min, max, desired) form the operational guardrails of an Auto Scaling Group.
- **CloudWatch Alarms** monitor performance metrics and trigger scaling policies automatically.
- **ELB Health Checks** verify that applications are functional at the HTTP layer, not just that the virtual machine is powered on.

---

## Additional Resources
- [AWS documentation: What is Amazon EC2 Auto Scaling?](https://docs.aws.amazon.com/autoscaling/ec2/userguide/what-is-amazon-ec2-autoscaling.html)
- [AWS Guide: EC2 Launch Templates](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-launch-templates.html)
- [AWS Guide: Monitor Auto Scaling Groups using CloudWatch](https://docs.aws.amazon.com/autoscaling/ec2/userguide/as-scale-based-on-demand.html)
