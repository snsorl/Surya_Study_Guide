# Knowledge Check: Splunk Observability, OWASP Security, and AI Governance

This quiz evaluates your understanding of Splunk search syntax, Logback connections, OWASP cloud security vulnerabilities, software supply chain attacks, and regulatory AI compliance frameworks.

---

## Questions

### 1. In Search Processing Language (SPL), what is the function of the pipe (`|`) operator?
- [ ] A) It acts as an OR operator.
- [ ] B) It sends the output of the preceding search command as the input to the next command.
- [ ] C) It deletes matching events from the database.
- [ ] D) It opens a network connection port to the client.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It sends the output of the preceding search command as the input to the next command.

**Explanation:** The pipe operator (`|`) chains commands in SPL, passing the filtered results from the left to the command on the right for transformation, formatting, or aggregation.
- **Why others are wrong:**
  - A) OR operators are declared using the `OR` keyword.
  - C) Deleting events requires specific administrative commands (like `delete`), not pipes.
  - D) Port connections are configured in the settings, not in SPL queries.
</details>

---

### 2. What type of Splunk forwarder runs as a lightweight background agent on your application server, consuming minimal CPU and memory to forward raw log files?
- [ ] A) Heavy Forwarder (HF)
- [ ] B) Universal Forwarder (UF)
- [ ] C) Search Head
- [ ] D) Syslog Daemon

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Universal Forwarder (UF)

**Explanation:** The Universal Forwarder (UF) is a lightweight agent installed on application servers to monitor log files and forward raw data to the central Splunk indexer.
- **Why others are wrong:**
  - A) Heavy Forwarders are full Splunk instances that parse and filter data before forwarding.
  - C) Search Heads coordinate search queries and render dashboards, but do not collect logs on application servers.
  - D) Syslog is a standard logging protocol, not a Splunk forwarder component.
</details>

---

### 3. What Splunk ingestion endpoint allows applications to send structured JSON log events directly to the indexer over HTTP (typically port 8088)?
- [ ] A) Universal Forwarder
- [ ] B) TCP Input Port
- [ ] C) HTTP Event Collector (HEC)
- [ ] D) File Monitor

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) HTTP Event Collector (HEC)

**Explanation:** The HTTP Event Collector (HEC) is an API endpoint that allows applications to send logs directly to Splunk using HTTP POST requests.
- **Why others are wrong:**
  - A) and D) monitor local files, rather than accepting direct HTTP calls.
  - B) TCP Input accepts raw TCP socket traffic, not structured HTTP POST requests.
</details>

---

### 4. What SPL command is used to extract custom fields from raw log text dynamically using Regular Expressions?
- [ ] A) `eval`
- [ ] B) `stats`
- [ ] C) `rex`
- [ ] D) `rename`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `rex`

**Explanation:** The `rex` command extracts fields from raw log text on the fly using regular expressions (e.g., `| rex field=_raw "code=(?<error_code>\d+)"`).
- **Why others are wrong:**
  - A) `eval` calculates new values from existing fields, but does not parse raw text using regex.
  - B) `stats` aggregates statistics over matching events.
  - D) `rename` changes field display names.
</details>

---

### 5. What is the role of "Field Aliases" in the Splunk Common Information Model (CIM)?
- [ ] A) To encrypt sensitive log fields.
- [ ] B) To normalize different log formats by mapping alternative field names (like `clientip` or `c_ip`) to a single standard field name (like `src_ip`).
- [ ] C) To schedule automated email reports.
- [ ] D) To delete duplicate logs from the index cache.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) To normalize different log formats by mapping alternative field names (like `clientip` or `c_ip`) to a single standard field name (like `src_ip`).

**Explanation:** Field Aliases map multiple field variations from different log sources to a single, standardized name. This allows you to write queries that work across all log types.
- **Why others are wrong:**
  - A) Aliases normalize field names, but do not encrypt data.
  - C) Reports are managed via Saved Searches.
  - D) De-duplication is handled by the `dedup` command.
</details>

---

### 6. Which OWASP Top 10 category includes vulnerabilities like leaving default administrative credentials active, leaving Amazon S3 buckets public, or exposing database ports to the internet?
- [ ] A) A01 Broken Access Control
- [ ] B) A05 Security Misconfiguration
- [ ] C) A08 Software and Data Integrity Failures
- [ ] D) A10 Server-Side Request Forgery

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A05 Security Misconfiguration

**Explanation:** Security Misconfiguration (A05) covers flaws like default credentials, unnecessary open ports, public cloud storage buckets, and missing security headers.
- **Why others are wrong:**
  - A), C), and D) are other OWASP categories covering access control, software integrity, and SSRF flaws.
</details>

---

### 7. What type of supply-chain attack targets the software build pipeline by injecting malicious code into third-party libraries that build servers download automatically?
- [ ] A) Brute-Force Attack
- [ ] B) SolarWinds-style Supply Chain Attack
- [ ] C) Cross-Site Scripting (XSS)
- [ ] D) SQL Injection

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) SolarWinds-style Supply Chain Attack

**Explanation:** Supply chain attacks target upstream dependencies, build environments, or vendor updates, injecting malware into trusted updates distributed to downstream users.
- **Why others are wrong:**
  - A) Brute-Force tries combinations to guess credentials.
  - C) XSS runs malicious scripts in user browsers.
  - D) SQL Injection targets database query vulnerabilities.
</details>

---

### 8. What is the role of Software Composition Analysis (SCA) tools in secure build pipelines?
- [ ] A) To encrypt the final Docker image.
- [ ] B) To scan project dependencies (e.g., from `pom.xml`) for known vulnerabilities against CVE databases.
- [ ] C) To manage cloud server scaling.
- [ ] D) To check code formatting rules.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) To scan project dependencies (e.g., from `pom.xml`) for known vulnerabilities against CVE databases.

**Explanation:** SCA tools audit third-party libraries and dependencies used in your application, flagging packages with known security vulnerabilities.
- **Why others are wrong:**
  - A) SCA scans packages, but does not encrypt images.
  - C) Scaling is handled by orchestrators (like ASGs).
  - D) Code formatting is checked by linters.
</details>

---

### 9. Under the EU AI Act risk classification system, how are AI systems used in employment recruitment or critical infrastructure categorized?
- [ ] A) Unacceptable Risk (Banned)
- [ ] B) High Risk (Strict compliance and audits required)
- [ ] C) Limited Risk (Basic transparency notices only)
- [ ] D) Minimal Risk (No requirements)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) High Risk (Strict compliance and audits required)

**Explanation:** AI systems used in critical sectors (like employment, education, or infrastructure) are classified as High Risk. They must undergo strict risk assessments, maintain logging, and ensure human oversight.
- **Why others are wrong:**
  - A) Unacceptable Risk covers banned systems (like social scoring).
  - C) Limited Risk covers basic chatbots, requiring only transparency notifications.
  - D) Minimal Risk covers video games or spam filters, which face no restrictions.
</details>

---

### 10. True or False: For compliance and auditability in AI applications, you should maintain audit log tables recording the timestamp, user ID, model ID, temperature, and sanitized prompt text for every AI transaction.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) True

**Explanation:** AI Governance frameworks require detailed transaction logging to ensure auditable, transparent AI usage and to monitor compliance metrics (like PII redactions).
- **Why others are wrong:**
  - B) This is incorrect because transaction logging is key to meeting AI governance standards.
</details>
