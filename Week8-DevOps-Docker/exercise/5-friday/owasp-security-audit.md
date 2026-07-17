# Lab Exercise: Docker Compose Security Auditing and Remediation (OWASP A05)

## Learning Objectives
- Audit Docker Compose configurations for security vulnerabilities.
- Identify common OWASP A05 Security Misconfigurations.
- Implement container hardening rules (non-root execution, read-only filesystems, dropping capabilities).
- Verify and test configuration security patches.

---

## The Scenario
Before deploying Project 3 to production, your team must perform a security audit. Running containers with default settings is a common risk: it can allow process privileges to be elevated or host filesystems to be modified.

In this exercise, you will audit your Docker Compose configuration file against an **OWASP A05 Security Misconfiguration** checklist, document at least 3 vulnerabilities, patch the configuration files, and verify the security fixes.

---

## The Security Audit Checklist
Audit your `docker-compose.yml` file against these questions:
1.  **Non-Root Context**: Does the service run as a secure, non-root user? (Check if the `USER` keyword is missing in the Dockerfile, or if `user:` settings are missing in the Compose service definition).
2.  **Filesystem Write Permissions**: Is the container root filesystem writable? (Check if `read_only: true` is missing).
3.  **Privilege Configuration**: Does the container run with standard host capabilities, or does it prevent privilege escalation? (Check if `no-new-privileges` or `cap_drop` rules are missing).
4.  **Credential Safety**: Are database credentials hardcoded in the configuration file?
5.  **Exposed Ports**: Are internal ports (like PostgreSQL port `5432`) exposed to the public host network unnecessarily?

---

## Tasks

### Task 1: Perform the Security Audit
1.  Open your project's `docker-compose.yml` file.
2.  Create a report named `docker_security_audit.md` in the exercise folder.
3.  Identify and document at least 3 security misconfigurations, explaining the **Vulnerability**, the **Risk Level** (High, Medium, Low), and the **Impact** (what an attacker could do).

---

### Task 2: Remediate the Vulnerabilities
Harden your Docker Compose configuration by applying these security rules:
1.  **Configure Read-Only Storage**: Make the container root filesystem read-only. Mount temporary RAM volumes (`tmpfs`) to folders that require write permissions (like `/tmp` or `/run`):
    ```yaml
    read_only: true
    tmpfs:
      - /tmp:rw,noexec,nosuid
    ```
2.  **Drop System Capabilities**: Strip default system administration privileges from the container:
    ```yaml
    cap_drop:
      - ALL
    ```
3.  **Secure Ports**: Remove the database port mapping (`ports: - "5432:5432"`) to keep it private within the internal bridge network. Ensure only Nginx (port `80`) is exposed.
4.  **Prevent Privilege Elevation**: Add security configurations to prevent users from elevating container process privileges:
    ```yaml
    security_opt:
      - no-new-privileges:true
    ```

---

### Task 3: Verify and Test the Hardened Stack
1.  Launch the hardened stack:
    ```bash
    docker compose up -d
    ```
2.  Verify the container filesystem is read-only by attempting to write a test file inside the running API container:
    ```bash
    # This write command should fail and return a 'Read-only file system' error
    docker compose exec backend-api touch /test-write-file.txt
    ```
3.  Confirm the database port is inaccessible from your host machine:
    ```bash
    # This connection command should time out or fail
    nc -zv localhost 5432
    ```
4.  Document the verification command outputs and confirm the vulnerabilities are successfully patched in the `docker_security_audit.md` report.

---

## Definition of Done
- The file `docker_security_audit.md` is saved.
- At least 3 security vulnerabilities are documented and patched in the configuration files.
- The root container filesystem is verified as read-only.
- The PostgreSQL database is private, blocking connections from the host network.
