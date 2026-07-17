# Advanced SPL Commands: Stats Functions, Eval Logic, Rex Extraction, and Lookups

## Learning Objectives
- Apply advanced statistical functions within `stats` commands (`count`, `dc`, `avg`, `min`, `max`).
- Write conditional logic inside `eval` commands using `if` and `case` functions.
- Write Regular Expressions (`rex`) to extract fields from raw log text dynamically.
- Group related log lines using the `transaction` command.
- Enrich log events with external database records using `lookup`.

---

## Why This Matters
While standard SPL commands (like `table` or basic `stats`) are useful, they are limited when analyzing unstructured logs. 

For example, what if your logs contain values that Splunk does not extract automatically, such as a transaction ID embedded in raw text? What if you need to calculate the number of *unique* users (distinct count) rather than total requests? Or what if you want to group a user's entire login, search, and checkout journey across different log files?

Mastering advanced SPL commands—including regex extraction, conditional evaluations, transactions, and lookups—is key to extracting detailed insights from your log data.

---

## The Concept

### 1. Advanced Stats Functions
When using the `stats` command, you can use diverse functions to analyze data:
- **`count`**: Calculates the total number of matching events.
- **`dc` (Distinct Count)**: Calculates the number of *unique* values in a field (e.g., `dc(user_id)` returns the count of unique active users, ignoring duplicate actions).
- **`avg` / `min` / `max` / `sum`**: Calculates numerical averages, bounds, or totals.

---

### 2. Conditional Logic in `eval`
You can evaluate logical conditions to populate new fields:
- **`if(condition, true_value, false_value)`**: Returns a value based on a boolean check.
- **`case(cond1, val1, cond2, val2, ...)`**: Evaluates multiple conditions sequentially, returning the value of the first match.

---

### 3. Dynamic Regex Extraction (`rex`)
If your log format changes or includes untracked values, you can use the **`rex`** command with regular expressions to extract values on the fly:
- Syntax: `| rex field=_raw "Pattern(?<extracted_field_name>Regex_Rule)"`
- This parses the raw log line, extracts the matching text, and saves it as a new field you can query in downstream pipes.

---

### 4. Transactions vs. Stats
- **`transaction`**: Groups related events into a single multi-line event based on shared identifiers (like a `transaction_id` or session token) within a max time span. This is excellent for tracing step-by-step transaction lifecycles, but is computationally heavy.
- **`stats`**: Preferred for general aggregations because it is much faster on large datasets.

---

### 5. Lookups
Enriches your log data with external metadata. E.g., if your logs only record a dry `user_id="101"`, you can use a lookup to match that ID against a CSV database file, automatically adding fields like `username="John Doe"` or `department="Engineering"` to your search results.

---

## Code Examples and Walkthroughs

### 1. Regular Expression Field Extraction (`rex`)
Suppose your application writes unstructured error logs like this:
`2026-07-16 15:02:00 [thread-1] WARN  - Payment authorization failed. code=PAY-404, amount=29.99`

You can write a regex query to extract the error code and amount dynamically during your search:

```splunk
# Extract error code and amount fields from raw text logs
index="project3_prod" "Payment authorization failed"
| rex field=_raw "code=(?<err_code>[A-Z0-9\-]+), amount=(?<amt>[0-9\.]+)"
# Now you can use these dynamically extracted fields in downstream pipes
| stats sum(amt) as "Total Failed Volume" by err_code
```

---

### 2. Conditional Evaluation using `case`
This query categorizes API response times into SLA tiers (Excellent, Acceptable, Critical) based on latency thresholds:

```splunk
# Categorize request latencies into SLA tiers
index="project3_prod" sourcetype="tomcat:access"
| eval SLA_Status = case(
    duration_ms <= 200, "Excellent",
    duration_ms > 200 AND duration_ms <= 1000, "Acceptable",
    duration_ms > 1000, "Critical Failure"
  )
| stats count by SLA_Status
```

---

### 3. Tracing a Session using `transaction`
This query groups all log lines associated with a specific user session ID, tracing the user's workflow within a 15-minute window:

```splunk
# Group events by session ID to trace workflow
index="project3_prod" sourcetype="springboot:logs"
| transaction session_id maxspan=15m
| table duration, eventcount, _time
```

- `duration`: Calculated automatically by the transaction command, showing the elapsed time between the first and last log lines in the session.
- `eventcount`: Displays the total number of log events grouped within the transaction.

---

## Summary
- Use **`dc` (distinct count)** within stats to count unique values, ignoring duplicates.
- The **`rex`** command uses regular expressions to extract custom fields from raw text logs dynamically.
- Use **`eval`** with **`if`** and **`case`** functions to apply conditional logic to your search results.
- **`transaction`** groups related log lines into single multi-line events, while **`lookup`** enriches logs with external database metadata.

---

## Additional Resources
- [Splunk Reference: Detailed stats command functions](https://docs.splunk.com/Documentation/Splunk/latest/SearchReference/CommonStatsFunctions)
- [Regular Expression Syntax and rex Command Guide](https://docs.splunk.com/Documentation/Splunk/latest/SearchReference/Rex)
- [Splunk Documentation: How to use lookup tables to enrich data](https://docs.splunk.com/Documentation/Splunk/latest/SearchTutorial/UseLookups)
