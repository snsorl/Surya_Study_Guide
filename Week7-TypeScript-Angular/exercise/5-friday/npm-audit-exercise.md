# Lab: Scanning Dependencies with npm audit

## Objectives
- Scan project directories for outdated and vulnerable dependencies using `npm audit`.
- Audit package lockfiles and evaluate severity metrics.
- Fix packages using automated clean commands.
- Review vulnerability reports in CVE databases.

---

## Scenario
As a security requirement, our application must protect against OWASP Top 10 vulnerabilities (specifically *A06: Vulnerable and Outdated Components*). You need to run security audits on your dependencies, identify package vulnerabilities, and implement patch upgrades safely.

---

## Core Tasks

### Task 1: Audit Scan Execution
1. Open the terminal and navigate to your project directory.
2. Run the security scanner:
   ```bash
   npm audit
   ```
3. Examine the output report detailing:
   - Total number of vulnerabilities.
   - Severity distribution levels (Low, Moderate, High, Critical).

### Task 2: Create a Security Report
Create a markdown report named `vulnerability-report.md` detailing:
1. **The Vulnerable Package**: The name of the package and version range affected.
2. **The Risk Description**: The CVE identifier and exploit description (e.g. Prototype Pollution, ReDoS).
3. **The Clean Upgrade Path**: The command required to perform patch fixes.

### Task 3: Execute Repairs
1. Execute the automated update tool:
   ```bash
   npm audit fix
   ```
2. If vulnerabilities persist that require breaking major upgrades, run:
   ```bash
   npm audit fix --force
   ```
   *Warning:* Evaluate compatibility settings before running force updates.

---

## Definition of Done
The exercise is complete when:
- You have resolved all moderate-to-critical vulnerabilities.
- Running `npm audit` returns a clean status report: *"found 0 vulnerabilities"*.
