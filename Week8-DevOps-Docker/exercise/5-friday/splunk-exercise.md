# Lab Exercise: Log Ingestion, SPL Querying, and Custom Dashboarding with Splunk HEC

## Learning Objectives
- Configure Spring Boot log routing to a Splunk HTTP Event Collector (HEC).
- Write custom Search Processing Language (SPL) queries to analyze server error patterns.
- Design real-time alert trigger thresholds based on system anomalies.
- Build visual system performance dashboards using Simple XML layout panels.

---

## The Scenario
Your operations team needs to monitor the health and performance of the Project 3 API in production. You must configure the application's Java Logback settings to stream logs over HTTP to a Splunk HEC endpoint, run queries to analyze HTTP errors, set up error alerts, and build a system health dashboard.

---

## Tasks

### Task 1: Configure Logback Ingestion to Splunk HEC
1.  Add the Splunk logging helper library dependency to your project's Maven configuration file (`pom.xml`):
    ```xml
    <dependency>
        <groupId>com.splunk.logging</groupId>
        <artifactId>splunk-library-javalog</artifactId>
        <version>1.11.8</version>
    </dependency>
    ```
2.  Create a `logback-spring.xml` file in your application's `src/main/resources/` directory and configure the HEC appender:
    ```xml
    <appender name="SPLUNK" class="com.splunk.logging.HttpEventCollectorLogbackAppender">
        <url>http://localhost:8088/services/collector</url>
        <token>12345678-abcd-1234-abcd-1234567890ab</token> <!-- Replace with your active HEC token -->
        <index>project3_logs</index>
        <source>springboot-api</source>
        <sourcetype>log4j</sourcetype>
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>%level [%thread] %logger{36} - %msg</pattern>
        </layout>
        <disableCertificateValidation>true</disableCertificateValidation>
    </appender>
    ```
3.  Start your Spring Boot application and verify logs are being sent by checking the Splunk console:
    `index="project3_logs"`

---

### Task 2: Search and Analyze Server Errors (SPL)
Navigate to the **Search & Reporting** app in Splunk and write queries to analyze errors:
1.  Write a query that displays all server errors (level `ERROR` or status `5xx`) that occurred in the last hour:
    ```splunk
    index="project3_logs" (level="ERROR" OR status>=500)
    ```
2.  Write a query that calculates the total number of errors and the average response latency (ms) for each unique API path:
    ```splunk
    index="project3_logs" sourcetype="tomcat:access"
    | stats count, avg(duration_ms) as avg_latency by uri_path
    | sort - avg_latency
    ```

---

### Task 3: Configure an Error Rate Alert
Configure an automated email/web alert in Splunk:
1.  Write an SPL query that calculates the error rate percentage:
    ```splunk
    index="project3_logs"
    | eval is_error = if(level="ERROR" OR status>=500, 1, 0)
    | stats sum(is_error) as error_count, count as total_count
    | eval error_rate = (error_count / total_count) * 100
    ```
2.  Click **Save As > Alert** in the top right.
3.  Configure the alert:
    *   **Title**: `High Error Rate Alert`
    *   **Alert Type**: Scheduled (runs every 15 minutes).
    *   **Trigger Condition**: Trigger if the custom field `error_rate` is **greater than 10**.
    *   **Trigger Action**: Select "Add to Triggered Alerts" (or configure a mock webhook).

---

### Task 4: Build a 3-Panel Dashboard
1.  Navigate to **Dashboards** and click **Create New Dashboard**.
2.  Switch to the **Source** view (Simple XML mode) and import configurations to create three panels:
    *   **Panel 1 (Single Value)**: Displays the total error count for the last 60 minutes.
    *   **Panel 2 (Column Chart)**: Displays the average latency (ms) grouped by API path.
    *   **Panel 3 (Table)**: Lists the top 5 slowest API requests.
3.  Save the dashboard and verify that all panels display active log data.

---

## Definition of Done
- Spring Boot log messages are ingested into your Splunk index.
- SPL queries successfully return error counts and response latencies.
- The `High Error Rate Alert` is saved in the Splunk console.
- A 3-panel dashboard displays active system health visualizations.
