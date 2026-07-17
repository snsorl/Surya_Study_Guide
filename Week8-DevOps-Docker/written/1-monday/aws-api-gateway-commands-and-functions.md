# Amazon API Gateway: Architectural Core and API Types

## Learning Objectives
- Compare the three API Gateway categories: REST APIs, HTTP APIs, and WebSocket APIs.
- Describe the relationships between API Resources, HTTP Methods, and Integrations.
- Detail API Gateway integration models: Lambda proxy, HTTP backend integration, and Mock integrations.
- Explain API version deployment lifecycle stages.
- Configure rate throttling and request quota profiles.

---

## Why This Matters
If you deploy multiple backend services (e.g., a Spring Boot service for inventory, a Node.js service for authentication, and an AI service for recommendations), exposing each service's IP address directly to the frontend is highly insecure and difficult to manage. The client would have to track multiple URLs, manage cross-origin request (CORS) rules on each host, and handle authentication repeatedly.

Amazon API Gateway serves as a single entry point for clients, routing requests to appropriate backend services. By handling routing, SSL termination, request throttling, and security validation in a centralized API Gateway layer, you simplify your backend services and protect them from distributed denial-of-service (DDoS) attacks.

---

## The Concept

### 1. API Types Comparison
Amazon API Gateway offers different API types to support varying architectural patterns:

#### REST APIs (Feature-Rich)
- **Description**: The original API Gateway protocol. Highly customizable.
- **Key Features**: Supports request/response mapping templates, built-in request validation, caching, custom authorizers, and direct AWS service integrations.
- **Use Case**: Enterprise APIs that require complex transformation rules, web application firewalls (WAF), or legacy protocol translations.

#### HTTP APIs (High-Performance, Low-Cost)
- **Description**: A streamlined, modern version of API Gateway designed for serverless architectures.
- **Key Features**: Up to 70% cheaper than REST APIs, with much lower latency. Native support for CORS, OIDC, and JWT authorization.
- **Use Case**: Modern microservices, serverless backends (AWS Lambda), and simple HTTP routing where mapping templates are not needed.

#### WebSocket APIs (Real-Time, Two-Way)
- **Description**: Persistent, bidirectional communication channels over a single TCP connection.
- **Key Features**: Manages connection state, allows the server to push updates to the client in real-time, and integrates route keys with backend endpoints.
- **Use Case**: Real-time applications, chat clients, financial tickers, and collaboration dashboards.

---

### 2. Resources, Methods, and Integration Types
Inside API Gateway, APIs are modeled as a tree structure of paths and operations:
- **Resource**: The URL path component (e.g., `/users` or `/orders/{id}`).
- **Method**: The HTTP action (e.g., `GET`, `POST`, `PUT`, `DELETE`).
- **Integration**: The backend target that API Gateway forwards the request to.

#### Integration Models
- **HTTP Integration (HTTP Proxy)**: API Gateway simply forwards the raw request directly to a backend HTTP endpoint (e.g., your Spring Boot application running on an EC2 instance).
- **Lambda Integration**: Forwards the request parameters directly to an AWS Lambda function.
- **Mock Integration**: API Gateway returns an API response directly without calling any backend. Excellent for testing CORS configurations or prototyping API definitions before coding the backend.

---

### 3. Lifecycle Management: Stages and Deployments
When you update resources or methods in API Gateway, the changes do not go live immediately.
- **Deployments**: A snapshot of your API configuration.
- **Stages**: A named reference to a deployment (e.g., `dev`, `staging`, `prod`). When you update your API, you deploy the changes to a specific stage, updating the URL endpoint without affecting other active stages.

---

### 4. Throttling and Quotas
To prevent APIs from being overwhelmed by traffic or malicious scripts, you configure rate limits:
- **Throttling (Rate Limits)**: Controls the rate of requests. It uses the **Token Bucket Algorithm**.
  - **Steady-state Rate**: The target average number of requests per second (RPS) allowed.
  - **Burst Limit**: The absolute maximum number of concurrent requests allowed in a brief millisecond window before returning a `429 Too Many Requests` status code.
- **Quotas**: Limits the total number of requests a user can make in a given timeframe (e.g., 10,000 requests per month).

---

## Code Examples and Walkthroughs

### 1. Provisioning a REST API and Resource via AWS CLI
The script below demonstrates how to configure an API Gateway REST API and define a `/health` resource route:

```bash
# 1. Create a new REST API definition
aws apigateway create-rest-api \
    --name "Project3-API-Gateway" \
    --description "Main gateway routing for Project 3 full-stack app" \
    --region us-east-1

# Record the generated id (e.g., abcde12345) and parentResourceId (e.g., xyz987)

# 2. Create a child resource '/health' under the root path
aws apigateway create-resource \
    --rest-api-id abcde12345 \
    --parent-id xyz987 \
    --path-part "health"

# Record the new resource id (e.g., h1h2h3)
```

```bash
# 3. Define a GET method on the '/health' resource
aws apigateway put-method \
    --rest-api-id abcde12345 \
    --resource-id h1h2h3 \
    --http-method GET \
    --authorization-type "NONE"

# 4. Integrate the GET method with a mock backend for health testing
aws apigateway put-integration \
    --rest-api-id abcde12345 \
    --resource-id h1h2h3 \
    --http-method GET \
    --type MOCK \
    --request-templates '{"application/json":"{\"statusCode\": 200}"}'

# 5. Create a stage deployment called 'prod' to make the endpoint live
aws apigateway create-deployment \
    --rest-api-id abcde12345 \
    --stage-name prod

# Verification:
# Test the mocked health endpoint in your browser or curl client:
curl https://abcde12345.execute-api.us-east-1.amazonaws.com/prod/health
# Output: {"statusCode": 200}
```

---

## Summary
- **REST APIs** offer full-featured middleware features; **HTTP APIs** provide fast, cost-effective routing; **WebSocket APIs** support real-time, stateful communication.
- **Resources** and **Methods** represent the endpoint tree, which routes traffic to backend **Integrations** (Lambda, HTTP endpoints, or Mocks).
- **Stages** act as environment labels (e.g., `dev`, `prod`), mapping API Gateway URLs to specific configuration snapshots.
- **Throttling** (rate/burst limits) prevents backend services from crashing under high traffic loads.

---

## Additional Resources
- [AWS Guide: What is Amazon API Gateway?](https://docs.aws.amazon.com/apigateway/latest/developerguide/welcome.html)
- [Comparing REST APIs and HTTP APIs](https://docs.aws.amazon.com/apigateway/latest/developerguide/http-api-vs-rest.html)
- [AWS API Gateway Throttling Settings Explained](https://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-request-throttling.html)
