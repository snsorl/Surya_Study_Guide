# Creating Reports and Dashboards in Splunk: Visualizations, Panels, and XML Schemas

## Learning Objectives
- Save and schedule Search Processing Language (SPL) queries as Reports.
- Configure automated Alerts triggered by search query anomalies.
- Build interactive Dashboards using diverse panel visualization types (charts, tables, single-value indicators).
- Modify dashboard layouts using Splunk Simple XML configuration.

---

## Why This Matters
While writing search queries in the search bar is useful for troubleshooting active incidents, it is inefficient for daily operations monitoring. Executives, project managers, and operations teams cannot write raw SPL commands every day to check system health.

You must build **Dashboards** and **Reports**. Dashboards present real-time system performance (like error rates, database latency, and concurrent user counts) in clear, visual graphs. Scheduled reports automate auditing by running queries and emailing summaries directly to stakeholders.

---

## The Concept

### 1. Saved Searches and Scheduled Reports
- **Saved Search**: A query saved for reuse, appearing in your search dropdown lists.
- **Scheduled Report**: A saved search configured to run automatically on a cron schedule. It can generate reports (e.g., a list of all database deadlocks over the last 24 hours) and email them to developers or archive them.

---

### 2. Building Interactive Dashboards
A **Dashboard** is a container for visual panels. When building dashboards, you choose the appropriate visualization type for each metric:
- **Single Value**: Displays a single metric (e.g., "Active Users: 1,420"). Best for high-priority status indicators.
- **Line/Column Charts**: Shows changes in metrics over time (e.g., CPU utilization trends).
- **Pie Charts**: Displays resource distribution (e.g., database connection allocations by database name).
- **Table**: Displays detailed, structured rows (e.g., a list of the top 10 slowest API queries).

---

### 3. Dashboard Configuration: Simple XML
Splunk stores dashboard layouts in **Simple XML** format:
- The XML schema defines rows, columns, panels, and visualization properties.
- While you can build dashboards using the visual GUI editor, editing the Simple XML directly is useful for fine-tuning layouts, configuring advanced CSS parameters, or backing up dashboards.

```
       Visual Dashboard GUI (User Interface)
+-------------------------------------------------+
|  [Panel: Error Count]     [Panel: Response Time]|
|  |  Single Value: 42  |     |  Line Chart: 34ms | |
+------------------------t------------------------+
                         | Renders from XML Definition
                         v
       Splunk Configuration Directory (Simple XML)
+-------------------------------------------------+
|  <dashboard version="1.1">                      |
|    <row>                                        |
|      <panel>                                    |
|        <single>                                 |
|          <search><query>index=prod...</query></search>
+-------------------------------------------------+
```

---

## Code Examples and Walkthroughs

### 1. Defining a Dashboard using Simple XML
This Simple XML configuration builds a dashboard containing a row with a single-value panel (counting 5xx server errors) and a column chart (monitoring latency trends over the last hour):

```xml
<!-- /opt/splunk/etc/apps/search/local/data/ui/views/system_health.xml -->
<dashboard version="1.1" theme="dark">
  <label>Project 3 System Performance Dashboard</label>
  <description>Real-time monitoring of Spring Boot and ECS compute metrics</description>
  
  <row>
    <!-- Panel 1: Single Value Indicator for Server Errors -->
    <panel>
      <single>
        <title>Server HTTP 5xx Errors (Last Hour)</title>
        <search>
          <query>index="project3_prod" status>=500 | stats count</query>
          <earliest>-1h@h</earliest>
          <latest>now</latest>
        </search>
        <option name="drilldown">none</option>
        <option name="colorMode">block</option>
        <option name="useColors">true</option>
        <!-- Set panel color to red if count is greater than 5 -->
        <option name="rangeColors">["0x53a051","0xd93f3c"]</option>
        <option name="rangeValues">[5]</option>
      </single>
    </panel>

    <!-- Panel 2: Column Chart for Response Latency Trends -->
    <panel>
      <chart>
        <title>Average Latency (ms) by API Route</title>
        <search>
          <query>
            index="project3_prod" sourcetype="tomcat:access"
            | timechart span=5m avg(duration_ms) by uri_path
          </query>
          <earliest>-1h@h</earliest>
          <latest>now</latest>
        </search>
        <option name="charting.chart">column</option>
        <option name="charting.chart.stackMode">stacked</option>
        <option name="charting.legend.placement">right</option>
      </chart>
    </panel>
  </row>
</dashboard>
```

---

## Summary
- **Reports** save and automate search queries; **Scheduled Reports** run queries on time boundaries and email summaries.
- **Alerts** monitor search logs and trigger notifications (email, Slack webhooks) when anomalies occur.
- **Dashboards** display metrics visually using single-value, chart, and table panels.
- Edit the **Simple XML schema** directly to configure advanced layouts, colors, and thresholds.

---

## Additional Resources
- [Splunk Documentation: Designing Reports and Scheduled Alerts](https://docs.splunk.com/Documentation/Splunk/latest/Report/Aboutreports)
- [Splunk XML Reference Guide: Dashboard Creation Manual](https://docs.splunk.com/Documentation/Splunk/latest/Viz/PanelreferenceforSimpleXML)
- [Splunk Web UI Guide: Building Dashboards and Visualizations](https://docs.splunk.com/Documentation/Splunk/latest/Viz/AboutCreatingDashboards)
