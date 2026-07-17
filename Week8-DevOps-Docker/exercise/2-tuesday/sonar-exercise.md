# Lab Exercise: Code Quality Auditing and Vulnerability Resolution with SonarLint

## Learning Objectives
- Install the SonarLint static analysis plugin in IntelliJ IDEA.
- Run static code scans on Java projects.
- Categorize analysis findings: Bugs, Code Smells, and Vulnerabilities.
- Resolve code security and quality violations.

---

## The Scenario
To prevent introducing bugs or security flaws to the production environment, your team enforces a quality check. Before code is merged, you must run static analysis to scan for issues.

In this exercise, you will install **SonarLint** in your IDE, scan the **Project 3** codebase, analyze the findings, and fix at least 3 high-severity issues.

---

## Tasks

### Task 1: Install SonarLint in IntelliJ IDEA
1. Open IntelliJ IDEA.
2. Navigate to **Settings (Preferences) > Plugins**.
3. Search for **SonarLint** in the marketplace tab and click **Install**.
4. Restart the IDE to complete the plugin setup.

---

### Task 2: Scan the Project 3 Codebase
1. Open the Project 3 Java codebase in IntelliJ.
2. In the project sidebar, right-click the root folder (or the `src` directory) and select **SonarLint > Analyze with SonarLint**.
3. Wait for the scan to finish and open the **SonarLint panel** at the bottom of the editor.

---

### Task 3: Audit and Categorize Findings
1. Review the generated list of findings.
2. Identify and analyze at least 5 distinct warnings.
3. Write a review log named `sonar_findings_log.md` in the exercise folder, documenting:
   - **File and Line Number** where the issue was found.
   - **Severity** (Blocker, Critical, Major, Minor).
   - **Category** (Bug, Code Smell, or Vulnerability).
   - **Description**: Explain *why* the warning is a risk (e.g., "Hardcoded password risks exposure in source control").

---

### Task 4: Resolve High-Severity Issues
1. Select at least 3 high-severity findings (focus on Blockers or Critical Vulnerabilities/Bugs, such as unhandled null pointers, hardcoded credentials, or SQL injection risks).
2. Fix the issues in the Java source files.
3. Re-run SonarLint on the modified files to verify that the warnings are resolved.
4. Document the before and after code changes for your fixes in the `sonar_findings_log.md` file.

---

## Definition of Done
- The SonarLint plugin is installed in IntelliJ.
- The Project 3 codebase is scanned, generating analysis reports.
- At least 3 high-severity issues are resolved in the Java code.
- The file `sonar_findings_log.md` is created, detailing findings and the resolved fixes.

---

## Troubleshooting Tips
- **No Findings Panel**: If the panel is missing, open it from the top menu: **View > Tool Windows > SonarLint**.
- **No Analysis Warnings**: If the scan returns zero issues, verify you scanned the active source directory (`src/main/java`) where application logic is defined.
