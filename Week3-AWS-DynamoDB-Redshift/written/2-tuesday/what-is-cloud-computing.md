# What is Cloud Computing?

## Learning Objectives
- Explain the NIST definition of cloud computing and its five essential characteristics.
- Compare on-premises infrastructure deployments with cloud deployments.
- Differentiate between Public, Private, and Hybrid cloud deployment models.
- Identify when to recommend specific cloud deployment models based on compliance and infrastructure requirements.

## Why This Matters
For decades, hosting applications required managing physical systems: buying server racks, configuring climate control, installing redundant power grids, and setting up network switches. This on-premises model required significant capital investments and operations overhead.

Cloud computing changed this model by converting physical infrastructure into virtual, software-defined services. Instead of waiting weeks to buy and install physical hardware, developers can rent compute, storage, and databases on-demand. Understanding what cloud computing is, and how its deployment models (public, private, hybrid) differ, is essential for choosing where and how to deploy secure, enterprise-grade applications.

## The Concept

### The NIST Definition of Cloud Computing
The National Institute of Standards and Technology (NIST) defines cloud computing as:

> "A model for enabling ubiquitous, convenient, on-demand network access to a shared pool of configurable computing resources (e.g., networks, servers, storage, applications, and services) that can be rapidly provisioned and released with minimal management effort or service provider interaction."

According to NIST, a true cloud computing service must exhibit **five essential characteristics**:

1. **On-Demand Self-Service**: Users can provision computing capabilities (such as server time or storage) automatically, without requiring human interaction with the service provider.
2. **Broad Network Access**: Services are available over the network and accessed through standard mechanisms using diverse client platforms (e.g., laptops, tablets, mobile phones).
3. **Resource Pooling**: The provider's computing resources are pooled to serve multiple consumers using a multi-tenant model. Physical and virtual resources are dynamically assigned and reassigned according to consumer demand, without the customer needing to know the exact physical location of the resources.
4. **Rapid Elasticity**: Capabilities can be elastically provisioned and released to scale rapidly outward and inward commensurate with demand. To the consumer, the capabilities available for provisioning often appear to be unlimited.
5. **Measured Service**: Cloud systems automatically control and optimize resource use by leveraging a metering capability (e.g., storage, processing, bandwidth, active user accounts). Resource usage can be monitored, controlled, and reported, providing transparency for both the provider and consumer.

### On-Premises vs. Cloud Computing
A comparison of traditional on-premises infrastructure and cloud computing highlights key operational differences:

| Operational Metric | On-Premises Infrastructure | Cloud Computing |
|---|---|---|
| **Financial Expense** | **CAPEX (Capital Expenditure)**: Large upfront payments for hardware, facilities, and setup. | **OPEX (Operating Expenditure)**: Small, recurring payments for resources consumed. |
| **Setup Speed** | Weeks or months for hardware delivery, mounting, and software installation. | Minutes or seconds via API calls or web console clicks. |
| **Scalability** | Hard scaling limits. Requires buying and installing more physical hardware to handle peak traffic. | Dynamic scaling. Automatically provision or terminate resources based on real-time demand. |
| **Maintenance** | Dedicated operations teams must manage hardware replacements, cabling, cooling, and security. | The cloud provider handles all physical hardware maintenance, patching, and data center security. |

### Cloud Deployment Models
When deploying applications to the cloud, organizations choose between three main deployment models:

#### 1. Public Cloud
Owned and operated by a third-party cloud service provider (e.g., AWS, Azure, Google Cloud). Resources are shared with other customers (tenants), but each customer's data and configurations are logically isolated.
- *Pros*: Highly scalable, cost-effective (no hardware to purchase), high availability.
- *Cons*: Less control over the underlying physical hardware; compliance constraints in highly regulated industries can make data residency tracking complex.

#### 2. Private Cloud
Infrastructure is dedicated to a single organization. It can be hosted physically at the organization’s on-premises data center or managed by a third party off-site.
- *Pros*: Complete control over hardware, high security, and strict data residency compliance.
- *Cons*: High upfront cost (CAPEX), limited elasticity compared to public clouds, requires dedicated IT operations staff.

#### 3. Hybrid Cloud
Combines public and private clouds, allowing data and applications to be shared between them. For example, an enterprise might host its database containing sensitive customer information in a private cloud for compliance, but run its front-end web application in the public cloud to take advantage of elastic scaling.
- *Pros*: Offers flexibility, compliance adherence, and optimization of legacy investments.
- *Cons*: Complex to design, configure, and maintain network connectivity and security between the environments.

## Code Examples

Developers interact with cloud services using Software-Defined Infrastructure (Infrastructure as Code, or IaC). Below is a declarative template (using AWS CloudFormation) that defines an elastic, on-demand compute server, illustrating the concept of software-defined infrastructure.

### AWS CloudFormation Template (IaC)
This configuration provisions a virtual EC2 server in the public cloud without needing physical hardware configuration:

```yaml
AWSTemplateFormatVersion: '2010-09-09'
Description: 'Provision a standard EC2 web server in the public cloud'

Resources:
  PublicWebServer:
    Type: 'AWS::EC2::Instance'
    Properties:
      # Use an official AWS operating system image
      ImageId: 'ami-0c55b159cbfafe1f0' 
      # Define the instance size (compute CPU and RAM class)
      InstanceType: 't2.micro'
      # Tag the instance for cost-tracking
      Tags:
        - Key: 'Name'
          Value: 'EnterprisePublicWebServer'
        - Key: 'Environment'
          Value: 'Production'
```

Deploying this resource is done via a single command:
```bash
aws cloudformation create-stack --stack-name webserver-stack --template-body file://webserver.yaml
```

## Summary
- **Cloud computing** is the on-demand delivery of computing resources over the internet with pay-as-you-go pricing.
- NIST defines five essential characteristics of cloud computing: **On-demand self-service**, **broad network access**, **resource pooling**, **rapid elasticity**, and **measured service**.
- The cloud shifts infrastructure costs from **CAPEX** (upfront capital) to **OPEX** (recurring operating costs).
- **Public clouds** share resources among tenants; **private clouds** dedicate hardware to a single customer; **hybrid clouds** link public and private architectures for flexibility.

## Additional Resources
- [NIST Special Publication 800-145: Definition of Cloud Computing](https://csrc.nist.gov/publications/detail/sp/800-145/final)
- [AWS Cloud Economics - Transitioning CAPEX to OPEX](https://aws.amazon.com/economics/)
- [Microsoft Azure - Public vs Private vs Hybrid Cloud Guide](https://azure.microsoft.com/en-us/resources/cloud-computing-dictionary/what-is-hybrid-cloud/)
