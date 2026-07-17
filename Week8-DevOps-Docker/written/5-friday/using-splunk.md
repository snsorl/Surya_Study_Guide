# Using Splunk: Interface Navigation, Data Inputs, and Forwarder Architecture

## Learning Objectives
- Navigate the Splunk Web UI (Home, Search & Reporting, Dashboards, Alerts).
- Configure Splunk Data Inputs using File Monitors and the HTTP Event Collector (HEC).
- Explain the role of the Splunk Index.
- Detail the Universal Forwarder architecture.

---

## Why This Matters
Installing Splunk is only the first step. To use it, you must configure how logs get from your servers to the Splunk indexing engine.

For example, if you deploy your Spring Boot application inside a Docker container on AWS, you must configure a data input method to forward its logs to Splunk.

This guide walks you through Splunk's user interface, data ingestion methods (like File Monitors and the **HTTP Event Collector**), index structures, and forwarder configurations.

---

## The Concept

### 1. Splunk Web UI Tour
The Splunk Web interface is organized into several workspaces:
- **Home**: The landing page displaying accessible applications and search shortcuts.
- **Search & Reporting**: The primary workspace where you write Search Processing Language (SPL) queries, filter logs, and analyze event timelines.
- **Dashboards**: The workspace for building, viewing, and configuring visual graphs and reports.
- **Alerts**: Configures automated notifications (like emails or Slack webhooks) triggered when search queries match specific conditions (e.g., error rates exceeding 10%).

---

### 2. Splunk Data Inputs

To ingest logs, you configure data input endpoints:

#### File Monitors
Splunk monitors specific log files on the host filesystem (e.g., `/var/log/nginx/access.log`). When the application writes a new log line, Splunk reads and indexes it instantly.

#### HTTP Event Collector (HEC)
An HTTP API endpoint that accepts raw text or structured JSON log events over connection ports (typically `8088`). Applications (like Spring Boot using custom log appenders) send logs directly to Splunk using HTTP POST requests, avoiding local file storage.

---

### 3. Splunk Index Concept
An **Index** is the database table of the Splunk ecosystem.
- When configuring data inputs, you specify a target index (e.g., `index="project3_prod"`).
- Separating data into distinct indexes improves query performance and allows you to configure access controls (e.g., restricting access to security audit indexes to administrators).

---

### 4. Splunk Forwarder Architecture
In production systems, log collectors run as separate lightweight applications on your servers:

```
  EC2 HOST (Production Server)                  SPLUNK CLUSTER
+-----------------------------+
|  SPRING BOOT APP            |
|  - Writes to file.log       |
|                             |
|  UNIVERSAL FORWARDER (UF)   |  HTTPS
|  - Reads file.log           |=======>  +-------------------------+
|  - Compresses & encrypts    |  Port    |     SPLUNK INDEXER      |
|  - Routes data over network |  9997    |   (Stores & parses)     |
+-----------------------------+          +-------------------------+
```

- **Universal Forwarder (UF)**: A lightweight command-line utility installed on the application server. It consumes minimal CPU and RAM, monitoring target log files and forwarding the raw data to the Splunk indexer over port `9997`.
- **Heavy Forwarder (HF)**: A full-scale Splunk instance configured as a forwarder. It parses, filters, and routes data before sending it to the indexer (e.g., masking credit card numbers or removing duplicate events before data leaves your network).

---

## Code Examples and Walkthroughs

### 1. Enabling the HTTP Event Collector (HEC) in Splunk Console
To configure Splunk to accept logs directly from a Spring Boot application over HTTP:

1. Open the **Splunk Web UI** and navigate to **Settings > Data Inputs**.
2. Select **HTTP Event Collector**.
3. Click **Global Settings** in the top right.
4. Set **All Tokens** to **Enabled**, choose a port (default: `8088`), and click **Save**.
5. Click **New Token** and complete the wizard:
   - **Name**: `project3-springboot-token`
   - **Source name**: `http:springboot`
   - **Default Index**: Select or create a dedicated index (e.g., `project3_logs`).
6. Click **Submit** to generate the **HEC Token** (a unique UUID, e.g., `12345678-abcd-1234-abcd-1234567890ab`).

---

### 2. Testing HEC Ingestion using Curl
Once the HEC token is created, verify it can accept log events using a standard HTTP request:

```bash
# Send a test log payload to Splunk HEC
curl -k https://localhost:8088/services/collector/event \
    -H "Authorization: Splunk 12345678-abcd-1234-abcd-1234567890ab" \
    -d '{"event": "Hello Splunk, this is a test log event from our REST API!", "sourcetype": "manual-test"}'

# Expected Output:
# {"text":"Success","code":0}
```

---

## Summary
- The **Splunk Web UI** provides workspaces for **Search & Reporting**, building **Dashboards**, and managing **Alerts**.
- Ingest logs using **File Monitors** (for local files) or the **HTTP Event Collector** (for direct HTTP API posting).
- Splunk organizes data into **Indexes** to optimize query performance and secure access controls.
- **Universal Forwarders** run as lightweight agents on application hosts, forwarding logs to centralized indexers.

---

## Additional Resources
- [Splunk Documentation: Navigating the Search & Reporting App](https://docs.splunk.com/Documentation/Splunk/latest/Search/GetstartedwithSearch)
- [AWS Guide: Setting up HTTP Event Collector (HEC) on Splunk](https://docs.splunk.com/Documentation/Splunk/latest/Data/UsetheHTTPEventCollector)
- [Splunk Universal Forwarder Installation and Configuration Guide](https://docs.splunk.com/Documentation/Forwarder/latest/WD/Abouttheuniversalforwarder)
