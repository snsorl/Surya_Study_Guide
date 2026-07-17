# Search Processing Language (SPL) Fundamentals: Pipelines, Stats, and Command Operations

## Learning Objectives
- Explain the role of the pipe (`|`) operator in Splunk queries.
- Apply core SPL commands: `search`, `table`, `stats`, `eval`, `where`, `sort`, `dedup`, and `rename`.
- Rename fields and deduplicate query results.
- Write common SPL patterns to analyze application logs.

---

## Why This Matters
Filtering logs in Splunk is useful, but raw text logs do not provide business intelligence. 

For example, you might want to calculate the total number of errors per hour, measure the average execution time of your REST endpoints, or find the top 10 most active users in your system.

**Search Processing Language (SPL)** solves this. It is a powerful query language that allows you to filter, transform, format, and aggregate log data. By mastering SPL commands like `stats`, `eval`, and pipes, you can convert raw logs into dashboards and performance alerts.

---

## The Concept

### 1. The Pipe Concept in SPL
Splunk queries use the **pipe (`|`)** operator, similar to Unix shells.
- The query before the pipe acts as a filter, retrieving events from disk.
- The resulting events are passed through the pipe to the next command, which transforms or formats the data.
- You can chain multiple pipes together to build complex data processing pipelines.

```
       Initial Filter stage                   First Pipe Stage                     Second Pipe Stage
+--------------------------------+   +--------------------------------+   +--------------------------------+
|  index="prod" status=500       |=> |  stats count by endpoint       |=> |  sort - count                  |
|  - Pulls matching raw events   | | |  - Aggregates count of errors  | | |  - Sorts endpoints by error    |
|    from disk                   |   |    for each unique endpoint    |   |    frequency (highest first)   |
+--------------------------------+   +--------------------------------+   +--------------------------------+
```

---

### 2. Core SPL Commands Directory

- **`search`**: Filters events. Usually used at the start of the query, but can be used after pipes to filter aggregated results.
- **`table`**: Formats specific fields into rows and columns, discarding raw text logs.
- **`rename`**: Changes a field's display name (e.g., `rename duration_ms as "Response Time (ms)"`).
- **`dedup`**: Removes duplicate events based on specified fields, keeping only the most recent match.
- **`sort`**: Sorts results by fields. Use `-` before the field name for descending order (highest first) and `+` for ascending.
- **`stats`**: Calculates statistics over matching events (e.g., `count`, `sum`, `avg`, `min`, `max`).
- **`eval`**: Evaluates mathematical or logical expressions and saves the result in a new field (e.g., converting milliseconds to seconds).
- **`where`**: Filters results using logical operators, similar to a SQL `WHERE` clause.

---

## Code Examples and Walkthroughs

### 1. Common SPL Analysis Patterns

#### Pattern A: Find the Top 5 Slowest API Endpoints
This query calculates the average response time for each unique API endpoint, sorts them from slowest to fastest, and limits the output to the top 5 results:

```splunk
# Calculate average latency by endpoint and show the top 5 slowest
index="project3_prod" sourcetype="tomcat:access"
| stats avg(duration_ms) as "Average Latency (ms)" by uri_path
| sort - "Average Latency (ms)"
| head 5
```

Let's break down this query:
- `stats avg(duration_ms) as "Average Latency (ms)" by uri_path`: Calculates the average value of `duration_ms` for each unique `uri_path`, renaming the output column to "Average Latency (ms)".
- `sort - "Average Latency (ms)"`: Sorts the resulting table in descending order (slowest first).
- `head 5`: Limits the output to the first 5 rows.

#### Pattern B: Count Errors per Hour (Timechart)
To visualize how error rates fluctuate over time (e.g., to create a dashboard line chart):

```splunk
# Count error events in 1-hour blocks
index="project3_prod" level="ERROR"
| timechart span=1h count by logger
```

- **`timechart`**: A specialized stats command that automatically groups events by time intervals (`span=1h`) and formats the data for charts.

---

### 2. Formatting Fields using `eval` and `rename`
This query converts execution duration from milliseconds to seconds, rounds the result, and renames the fields for a user report:

```splunk
# Convert duration, round the result, and format the table
index="project3_prod" sourcetype="springboot:logs"
| eval duration_sec = round(duration_ms / 1000, 2)
| rename duration_sec as "Duration (Seconds)", api_endpoint as "REST Route"
| table _time, "REST Route", "Duration (Seconds)", status
```

---

## Summary
- Splunk queries use the **pipe (`|`)** operator to pass data through a sequence of filtering and transforming commands.
- Use **`stats`** to calculate metrics (like `count` or `avg`), and **`timechart`** to group data by time intervals for visual graphs.
- **`eval`** calculates new fields dynamically, and **`rename`** configures clean column names.
- Use **`dedup`** to remove duplicate events and keep only the latest entries.

---

## Additional Resources
- [Splunk Search Processing Language (SPL) Command Reference Directory](https://docs.splunk.com/Documentation/Splunk/latest/SearchReference/WhatsInThisManual)
- [Splunk Tutorial: Learning to use statistical transformation commands](https://docs.splunk.com/Documentation/Splunk/latest/SearchTutorial/Aboutstaticticalcommands)
- [Splunk Wiki: Tips and Tricks for Optimizing SPL Search Speeds](https://wiki.splunk.com/Community:SPL_Optimization)
