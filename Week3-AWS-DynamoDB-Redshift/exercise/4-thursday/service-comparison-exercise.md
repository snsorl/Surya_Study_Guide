# Exercise: Database Service Comparison (PostgreSQL vs. DynamoDB vs. Redshift)

**Exercise Mode:** Mode B: Conceptual / Scenario Analysis
**Topics Covered:** PostgreSQL (RDS), DynamoDB, Amazon Redshift

---

## Scenario
You have been hired as a Cloud Consulting Specialist by **RetailCorp**. They are architecting their next-generation retail ecosystem and need recommendations on which database to select for three of their core systems. 

Your options are:
1. **Amazon RDS PostgreSQL** (Relational OLTP)
2. **Amazon DynamoDB** (NoSQL Key-Value/Document Store)
3. **Amazon Redshift** (Columnar OLAP Data Warehouse)

For each system scenario below, choose the correct database service and write a **one-paragraph (4–6 sentences) justification** addressing:
- Data structure (relational vs. non-relational).
- Access patterns and query speed requirements (joins, latency).
- Scaling needs (millions of users vs. gigabytes/terabytes of analytical reports).
- Write and read workload characteristics.

---

## Business Scenarios

### Scenario 1: Shopping Cart & Session State Manager
- **System Description:** This service stores active user shopping carts, session variables, and temporary click history as users browse the online store. The system handles millions of concurrent shoppers. Every click or product addition is a write, and every page render is a read. Speed is critical; cart lookups must complete in single-digit milliseconds. The cart schema is simple (a list of items) and does not require complex relations or table joins.
- **Service Recommendation:** _[PostgreSQL / DynamoDB / Redshift]_
- **Justification:**

---

### Scenario 2: Regulatory Audit and Ledger System
- **System Description:** This system processes financial payouts, ledger balances, and user account profiles. It handles sensitive user balances, updates records across multiple accounts (e.g. subtracting a balance from one user and adding it to another), and requires strict ACID compliance. The schema is highly relational, involving deep joins across customers, billing tables, and country-level transaction registers.
- **Service Recommendation:** _[PostgreSQL / DynamoDB / Redshift]_
- **Justification:**

---

### Scenario 3: Recommendation Engine Training Warehouse
- **System Description:** A backend data lake component that compiles all historical customer orders, review comments, and item views over the past 10 years. Business analysts run machine learning models and large aggregate reports (e.g., finding the top 5 product categories bought by customers who also bought accessories). It queries massive datasets (100+ TB) but only runs queries periodically (non-real-time).
- **Service Recommendation:** _[PostgreSQL / DynamoDB / Redshift]_
- **Justification:**

---

## Deliverables
Create a document named `database_architecture_recommendation.md` with your answers for the three scenarios.
