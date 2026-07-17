# Step-by-Step Guide: Creating and Managing a REST API in Amazon API Gateway

## Learning Objectives
- Design and create a REST API in the AWS Management Console.
- Configure resources and GET methods to route backend requests.
- Integrate API Gateway with an application backend running on an EC2 instance.
- Deploy the configured API definitions to a staging environment.
- Verify end-to-end connectivity using Postman or curl.

---

## Why This Matters
Exposing a Spring Boot application running on port `8080` directly to public IP addresses is not recommended for production environments. It forces clients to connect directly to the application server and exposes your backend port (`8080`) to the public web.

By placing Amazon API Gateway in front of your EC2 instance, you create a secure abstraction layer. Clients send requests to a clean API Gateway domain name, which forwards the request to the EC2 server behind the scenes. This guide walks you through setting up this architecture step-by-step.

---

## The Concept

### 1. The Deployment Architecture
In this guide, we will build a proxy architecture where public client requests flow through API Gateway to a Spring Boot backend running on an EC2 instance:

```
+--------------+                   +---------------------+                   +---------------------+
| PUBLIC CLIENT|                   |   AMAZON API        |                   |     AWS EC2         |
|  (Postman/   |==================>|     GATEWAY         |==================>|    INSTANCE         |
|   Browser)   |  HTTPS Request   |  - Proxy Endpoint   |   HTTP Forward    | - Spring Boot App   |
|              |                  |  - Auth/Validation  |                   |   Running on 8080   |
+--------------+                   +---------------------+                   +---------------------+
```

- **Client Target**: `https://{api-id}.execute-api.{region}.amazonaws.com/prod/tasks`
- **Internal Forward**: `http://{ec2-public-dns}:8080/tasks`

---

## Step-by-Step Implementation

### Step 1: Create the REST API Definition
1. Open the **AWS Management Console** and navigate to **API Gateway**.
2. Locate the **REST API** card (non-private) and click **Build**.
3. Under **Create new API**, select **New API**.
4. Configure the settings:
   - **API name**: `Project3-Gateway`
   - **Description**: `Production gateway routing for Project 3 backend`
   - **Endpoint Type**: **Regional** *(Regional endpoint hosts the API in the current AWS region. Edge-optimized APIs route traffic through CloudFront edge locations, which is ideal for globally distributed users).*
5. Click **Create API**.

---

### Step 2: Configure the API Resource and HTTP Method
1. Select the root path (`/`) in the **Resources** tree.
2. Click **Actions** (or **Create resource** in the new interface) and choose **Create Resource**.
3. Set the **Resource Name** to `tasks`. The **Resource Path** will automatically populate as `/tasks`.
4. Click **Create Resource**.
5. With the `/tasks` resource highlighted, click **Actions** (or **Create method**) and select **GET**. Click the checkmark to save.

---

### Step 3: Configure the EC2-Hosted Spring Boot Integration
Now, we point the GET method to the Spring Boot backend running on our EC2 instance:

1. In the method configuration panel, set **Integration Type** to **HTTP**.
2. Check **Use HTTP Proxy integration**. *(HTTP Proxy integration forwards the entire incoming request body, headers, and query parameters directly to the backend without modification, simplifying configuration).*
3. Set the **Endpoint URL** to the public DNS or public IP of your EC2 instance, including port 8080.
   - Example: `http://ec2-54-210-12-3.compute-1.amazonaws.com:8080/tasks`
4. Click **Save**.

```
                        Method Execution Panel
 +-------------------+      +-------------------+      +-------------------+
 |   METHOD REQUEST  |===>  |  INTEGRATION REQ. |===>  |   HTTP BACKEND    |
 | - Auth: None      |      | - Type: HTTP Proxy|      |   (EC2 Instance)  |
 | - Query Params    |      | - URL: EC2 Path   |      |   Port 8080       |
 +-------------------+      +-------------------+      +-------------------+
```

> [!IMPORTANT]
> For this connection to succeed, the **Security Group** attached to your EC2 instance must have an inbound rule allowing traffic on Port 8080. For production environments, you should restrict this inbound rule to allow traffic only from the IP range of your API Gateway.

---

### Step 4: Deploy the API to a Stage
Before the API endpoint can accept traffic, you must deploy it:

1. Click **Actions** (or **Deploy API**) in the resources panel.
2. Select **[New Stage]** for the Deployment Stage.
3. Configure the stage settings:
   - **Stage name**: `prod`
   - **Stage description**: `Production release environment`
4. Click **Deploy**.
5. The console will display an **Invoke URL** at the top of the screen. This is the root URL of your live API:
   - `https://abcde12345.execute-api.us-east-1.amazonaws.com/prod`

---

### Step 5: Verify Connectivity using Postman

To verify the setup, send a test request through the gateway:

1. Open **Postman** (or your local terminal).
2. Create a new **GET** request.
3. Construct the request URL by appending the resource path (`/tasks`) to the Invoke URL:
   - URL: `https://abcde12345.execute-api.us-east-1.amazonaws.com/prod/tasks`
4. Click **Send**.
5. **Expected Output**: A `200 OK` status code containing the JSON payload returned by your Spring Boot application (e.g., a list of tasks).

---

## Troubleshooting Common Setup Failures

### 1. `504 Gateway Timeout`
- **Error Description**: API Gateway did not receive a response from your EC2 instance within the 29-second execution limit.
- **Troubleshooting**: 
  - Verify that the Spring Boot application is running on the EC2 instance (`ps aux | grep java`).
  - Verify that the EC2 instance's security group allows inbound traffic on port 8080 from the public web or the API Gateway IP range.

### 2. `502 Bad Gateway`
- **Error Description**: API Gateway connected to the EC2 instance, but the instance returned an invalid response or dropped the connection.
- **Troubleshooting**: 
  - Verify that the Endpoint URL configured in your HTTP integration matches the path format expected by Spring Boot (e.g., checking for trailing slashes).

---

## Summary
- **Regional REST APIs** host resources and methods within a specific AWS region, providing control over client routing.
- **HTTP Proxy Integration** forwards incoming requests to backend web servers (like Spring Boot on EC2) without modification.
- **API Deployments** compile configuration settings, while **Stages** expose those deployments as live URLs.
- **Security Groups** must be configured to allow the EC2 instance to accept requests from the API Gateway.

---

## Additional Resources
- [AWS Guide: Set up an HTTP Proxy Integration in API Gateway](https://docs.aws.amazon.com/apigateway/latest/developerguide/setup-http-proxy-integration.html)
- [AWS Security: Control Access to REST APIs](https://docs.aws.amazon.com/apigateway/latest/developerguide/apigateway-control-access-to-api.html)
- [Postman Learning Center: Sending Requests](https://learning.postman.com/docs/sending-requests/requests/)
