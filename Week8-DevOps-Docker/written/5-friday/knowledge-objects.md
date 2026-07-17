# Splunk Knowledge Objects: Saved Searches, Aliases, Event Types, and the Common Information Model (CIM)

## Learning Objectives
- Describe the types of Splunk Knowledge Objects: Saved Searches, Aliases, Tags, Event Types, and Lookups.
- Configure Field Aliases to normalize fields across different log formats.
- Group related logs using Event Types and Tags.
- Explain the role of the Common Information Model (CIM) in enterprise log normalization.

---

## Why This Matters
Different applications often write log outputs in different formats:
- Apache Nginx logs might save client IP addresses as `clientip`.
- Tomcat application servers might save them as `c_ip`.
- Spring Boot logback outputs might write them as `remote_address`.

If you write a search query or build a security dashboard to audit network traffic, you would have to write complex queries that account for all these different field names.

**Splunk Knowledge Objects** solve this. They allow you to define **Field Aliases** to normalize fields (mapping `clientip`, `c_ip`, and `remote_address` to a single standard field name like `src_ip`), simplifying your queries, reports, and dashboards.

---

## The Concept

### 1. Types of Knowledge Objects
Splunk provides several objects to enrich and organize data:
- **Saved Searches**: Search queries saved for reuse. They can be scheduled to run periodically to generate reports or trigger alerts.
- **Field Aliases**: Maps alternative names to existing fields, helping normalize different log formats.
- **Tags**: Human-readable labels assigned to field-value pairs (e.g., tagging `status_code=404` or `status_code=500` as `web_error`).
- **Event Types**: Groups related search results under a single static category name, making them queryable (e.g., defining an event type `auth_failed` that groups all SSH and active database login failures).
- **Lookups**: Link external CSV files or databases to enrich log data with metadata.

---

### 2. Normalizing Fields: The Common Information Model (CIM)
The **Common Information Model (CIM)** is a standardized schema of field names and event definitions.
- By configuring your log inputs to conform to CIM standards, you ensure that different data sources (e.g., AWS CloudTrail logs, Cisco router logs, and Linux audit logs) use the exact same field names for similar metrics (like IP addresses, usernames, and action statuses).
- This standardization allows you to use pre-built dashboards and security compliance tools (like Splunk Enterprise Security) out-of-the-box.

```
       RAW LOG SOURCES (Unstructured)                 CIM STANDARDIZED FIELDS
+------------------------------------------+       +----------------------------+
|  Nginx Log: "clientip=192.168.1.100"     |======>|                            |
|                                          |       |                            |
|  Tomcat Log: "c_ip=192.168.1.100"        |======>|  src_ip = 192.168.1.100    |
|                                          |       |                            |
|  Spring Log: "ip_addr=192.168.1.100"     |======>|                            |
+------------------------------------------+       +----------------------------+
                                                    (Normalized using Aliases)
```

---

## Code Examples and Walkthroughs

### 1. Defining Field Aliases in `props.conf`
While you can configure field aliases in the Splunk Web UI, they are stored under the hood in your Splunk deployment's configuration files (typically `props.conf`). This example demonstrates how to normalize client IP address fields from different log sources:

```ini
# /opt/splunk/etc/system/local/props.conf

# 1. Alias rule for Nginx access logs
[nginx:access]
FIELDALIAS-nginx_ip = clientip AS src_ip

# 2. Alias rule for Tomcat access logs
[tomcat:access]
FIELDALIAS-tomcat_ip = c_ip AS src_ip

# 3. Alias rule for Spring Boot logback outputs
[springboot:logs]
FIELDALIAS-spring_ip = ip_addr AS src_ip
```

Once configured, you can query client IP addresses across all three log sources using a single field name:

```splunk
# Query all access attempts from a specific IP across different log types
index="project3_prod" src_ip="192.168.1.100"
| table _time, host, sourcetype, src_ip
```

---

### 2. Creating and Querying Event Types
You can group log patterns into an **Event Type** in `eventtypes.conf`:

```ini
# /opt/splunk/etc/system/local/eventtypes.conf
[project3_database_errors]
search = index=project3_prod sourcetype=postgres OR sourcetype=mysql "connection failed" OR "deadlock detected"
```

Once defined, you can query these PostgreSQL and MySQL errors using a simple `eventtype` filter:

```splunk
# Search for all database connection and deadlock issues
eventtype="project3_database_errors"
| stats count by host
```

---

## Summary
- **Splunk Knowledge Objects** enrich log data and normalize field names across different formats.
- **Field Aliases** map custom log fields to a single standardized name (e.g., mapping `c_ip` to `src_ip`).
- **Event Types** group complex queries into static, queryable labels.
- The **Common Information Model (CIM)** defines a standardized schema of field names to enable out-of-the-box dashboards and compliance reports.

---

## Additional Resources
- [Splunk Knowledge Manager Manual: Managing Knowledge Objects](https://docs.splunk.com/Documentation/Splunk/latest/Knowledge/Aboutknowledgeobjects)
- [Splunk Common Information Model (CIM) Reference Manual](https://docs.splunk.com/Documentation/CIM/latest/User/Overview)
- [Props.conf Configuration File Reference Guide](https://docs.splunk.com/Documentation/Splunk/latest/Admin/Propsconf)
