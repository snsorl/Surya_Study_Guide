# OWASP A08: Software and Data Integrity Failures in CI/CD and Dependency Management

## Learning Objectives
- Define the OWASP A08:2021 Software and Data Integrity Failures category.
- Explain the mechanics of a software supply-chain attack (e.g., the SolarWinds incident).
- Identify vulnerabilities in build pipelines: unverified dependencies and auto-updates without signature checking.
- Configure GitLab Dependency Scanning to audit third-party packages.
- Apply security practices to lock down build tool dependency verification hashes.

---

## Why This Matters
Modern applications are not built from scratch; they rely heavily on third-party libraries (packages). A standard Spring Boot application might import hundreds of Maven dependencies, and an Angular app can import thousands of npm packages.

If an attacker compromises one of these third-party libraries, publishes a malicious update, or tampers with your automated build servers, they can inject malicious code directly into your production application.

**Software and Data Integrity Failures (A08)** covers these risks. Learning how to verify dependencies, run security scanners, and protect your build pipelines is key to securing your software supply chain.

---

## The Concept

### 1. What are Software and Data Integrity Failures?
This vulnerability occurs when code, data, or libraries are imported without verifying their integrity:
- **Unverified Dependencies**: Importing libraries from public registries without verifying they have not been tampered with.
- **Insecure CI/CD Pipelines**: Allowing write access to your pipeline configuration files, enabling attackers to modify build scripts.
- **Auto-Updates without Signatures**: Allowing applications to download code updates automatically over unencrypted connections without verifying cryptographic signatures.

---

### 2. Supply-Chain Attacks (The SolarWinds Model)
In a **supply-chain attack**, hackers do not target your production servers directly. Instead, they compromise a third-party tool or software library that your build servers download automatically:
1.  **Infiltration**: Attackers compromise the development environment or build servers of a trusted vendor (like SolarWinds).
2.  **Code Injection**: They inject a back-door trojan into the vendor's source code.
3.  **Compilation**: The vendor compiles and signs the package, unaware of the hidden malware.
4.  **Distribution**: The malicious update is pushed to public registries or update servers.
5.  **Compromise**: Your build servers download the signed, malicious update automatically, deploying the malware directly onto your servers.

---

### 3. Remediation: GitLab Dependency Scanning
To protect your application from vulnerable dependencies, you should integrate automated scanners into your CI/CD pipeline:
- **Software Composition Analysis (SCA)**: Scanners read your project's dependency lockfiles (e.g., `pom.xml` or `package-lock.json`) and audit the packages against database records of known vulnerabilities (CVEs).
- **GitLab Dependency Scanning**: A built-in GitLab CI analyzer template that runs SCA scans automatically on every pipeline build, reporting vulnerabilities directly in the Merge Request.

---

## Code Examples and Walkthroughs

### 1. Locking Down Dependencies in Maven
To prevent dependency confusion and tampering attacks, verify the cryptographic checksum hashes of downloaded Maven JAR files. Configure a strict verification ruleset in your project configuration:

```xml
<!-- pom.xml snippet enforcing checksum policy -->
<repositories>
  <repository>
    <id>central</id>
    <name>Maven Central</name>
    <url>https://repo.maven.apache.org/maven2</url>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
    <releases>
      <enabled>true</enabled>
      <!-- Checksum Policy Fail: Blocks build if downloaded file hash does not match registry registry metadata -->
      <checksumPolicy>fail</checksumPolicy>
    </releases>
  </repository>
</repositories>
```

---

### 2. Integrating Dependency Scanning in GitLab CI/CD
To run automated dependency security audits on every code push, include GitLab's pre-configured security scanner template in your pipeline file:

```yaml
# .gitlab-ci.yml (Integrated dependency scanning)
stages:
  - test

# Import the official GitLab Dependency Scanning template
include:
  - template: Jobs/Dependency-Scanning.gitlab-ci.yml

# The imported template adds the dependency_scanning job to your test stage automatically.
# You can customize scanner parameters using global variables:
variables:
  # Enable scanning of Gradle, Maven, and npm projects in parallel
  SECURE_LOG_LEVEL: "info"
  DS_EXCLUDED_PATHS: "spec, test, tmp"
  # Force scanner to fail the build if CRITICAL vulnerabilities are detected
  DS_SEVERITY_THRESHOLD: "CRITICAL"
```

---

## Summary
- **OWASP A08** focuses on vulnerabilities introduced by unverified third-party libraries and insecure build pipelines.
- **Supply-Chain attacks** target software dependencies and build servers to bypass your security controls.
- Protect your project by enforcing **dependency checksum verification** (blocking builds if package hashes mismatch).
- Integrate **SCA scanners** (like GitLab Dependency Scanning templates) to audit package dependencies for vulnerabilities automatically on every push.

---

## Additional Resources
- [OWASP A08:2021 Software and Data Integrity Failures Guide](https://owasp.org/Top10/A08_2021-Software_and_Data_Integrity_Failures/)
- [Understanding GitLab Dependency Scanning Tooling](https://docs.gitlab.com/ee/user/application_security/dependency_scanning/)
- [Securing the Software Supply Chain: NIST Guidelines and Frameworks](https://www.nist.gov/itl/executive-order-14028/software-supply-chain-security)
