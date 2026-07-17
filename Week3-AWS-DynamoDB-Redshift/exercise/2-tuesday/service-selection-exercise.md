# Exercise: AWS Storage Service Selection

**Exercise Mode:** Mode B: Conceptual / Scenario Analysis
**Topics Covered:** Amazon RDS, DynamoDB, Amazon S3, Amazon Redshift

---

## Scenario
You are the Lead Cloud Solutions Architect at an enterprise software firm. The business stakeholders have proposed three new applications. Your task is to evaluate the requirements and determine which AWS database or storage service matches each workload. 

Your choices of target services are:
- **Amazon RDS** (Relational Database Service)
- **Amazon DynamoDB** (NoSQL Key-Value/Document Store)
- **Amazon S3** (Object Storage)
- **Amazon Redshift** (Cloud Data Warehouse / OLAP)

---

## Part A: The Scenarios

### Scenario 1: E-Commerce Transactional Engine (OLTP)
- **Description:** A core banking/checkout transactional system with a fixed schema. It must process customer purchases, handle payment transactions with strong ACID consistency, and execute table joins (e.g. associating Customers, Orders, and Payment Tables).
- **Service Choice:** _[Your Choice]_
- **Architectural Justification:** Explain your choice in 3–5 sentences. Discuss consistency, schema rigidity, relationship support, and join capability.

### Scenario 2: Smart-Home IoT Sensor Streams
- **Description:** A tracking system for millions of smart-home IoT devices. Every device sends a status heartbeat payload every 10 seconds. The data structure is dynamic and changes from device to device (e.g. temperature, humidity, error codes, light status). The system requires sub-second ingestion rates and key-value query lookups by device ID.
- **Service Choice:** _[Your Choice]_
- **Architectural Justification:** Explain your choice in 3–5 sentences. Discuss write scalability, schema flexibility, performance under high throughput, and cost.

### Scenario 3: Quarterly Sales Analytics Reports
- **Description:** A monthly and quarterly business analytics system. It compiles historical purchase data from different sales channels over the past 5 years (containing billions of rows) to generate complex analytical queries (e.g. group sales by region, calculate rolling monthly totals). The operations are analytical (OLAP), not operational (OLTP).
- **Service Choice:** _[Your Choice]_
- **Architectural Justification:** Explain your choice in 3–5 sentences. Discuss column-oriented storage, parallel processing, OLTP vs. OLAP architecture, and query times on large datasets.

---

## Part B: Architectural Reflection

1. In cloud solutions, we often say **"use the right tool for the job."** What is the operational and financial risk of selecting a relational database like RDS for Scenario 2 (IoT stream)?
2. Why is Amazon S3 considered "object storage" rather than a database? When would you choose S3 over a database service?

---

## Deliverables
Create a markdown or text file named `service_selection_justification.md` containing your answers and justifications for Parts A and B.
