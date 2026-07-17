# Cohort-Specific Tooling: Internal GitLab Instances, Proxies, and Registry Configuration

## Learning Objectives
- Configure Git clients to interact with internal, self-hosted GitLab servers.
- Configure corporate network proxies for package managers (Maven, NPM).
- Authenticate with custom container registries (private Nexus or Artifactory instances).
- Troubleshoot SSL certificate issues in private enterprise networks.

---

## Why This Matters
While public SaaS platforms (like GitLab.com or Docker Hub) are standard for learning, large enterprises rarely host proprietary code on public networks. They run **self-hosted, private GitLab instances** behind corporate firewalls.

In these environments, developer workstations and CI runners must access resources through secure network proxies. If you do not know how to configure system proxy variables, import SSL root certificates, or authenticate with private package registries, your build tools will fail to resolve dependencies.

---

## The Concept

### 1. Internal GitLab Instances and SSH Key Mappings
Private GitLab installations run on custom domain names (e.g., `https://gitlab.internal.company.com`).
- **Access Limits**: These servers are only accessible when connected to the company's Virtual Private Network (VPN) or from physical office locations.
- **SSH Keys**: You must configure your local Git client to use specific SSH key pairs when connecting to internal servers, separate from your public GitHub configuration.

---

### 2. Working with Corporate Network Proxies
Enterprise networks route all outbound internet traffic through secure proxies. These proxies intercept network calls, scan for malware, and block unauthorized endpoints.
To download package dependencies (like Maven JARs or npm packages), you must configure your build tools to route traffic through the proxy endpoints.

```
       Developer Terminal (Local Workstation)
+-------------------------------------------------+
|  Commands: mvn compile, npm install             |
|                                                 |
|  Configuration Required:                        |
|  - HTTP_PROXY = http://proxy.company.com:8080   |
|  - HTTPS_PROXY = http://proxy.company.com:8080  |
+------------------------t------------------------+
                         |
                         v Routes traffic
+-------------------------------------------------+
|              CORPORATE PROXY SERVER             |
|  - Inspects SSL traffic                         |
|  - Validates endpoints against safety list      |
+------------------------t------------------------+
                         |
                         v Fetches dependencies
+-------------------------------------------------+
|                PUBLIC INTERNET                  |
|  (Maven Central Registry, NPM Public Registry)  |
+-------------------------------------------------+
```

---

### 3. Private Package and Container Registries
Enterprises often mirror public repositories locally using tools like **JFrog Artifactory** or **Sonatype Nexus**.
- **Caching**: The local registry mirrors and caches public dependencies (like JUnit JAR files) so developers can download them quickly within the internal network.
- **Private Artifacts**: A repository for publishing proprietary company code libraries and Docker images securely.

---

## Code Examples and Walkthroughs

### 1. Configuring Proxies for Maven (`settings.xml`)
If your Spring Boot project cannot resolve dependencies behind an enterprise proxy, configure the proxy settings in your local Maven directory (`~/.m2/settings.xml`):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- ~/.m2/settings.xml -->
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <proxies>
    <proxy>
      <id>corporate-network-proxy</id>
      <active>true</active>
      <protocol>http</protocol>
      <host>proxy.company.com</host>
      <port>8080</port>
      <!-- Optional: Configure proxy credentials if required -->
      <!-- <username>my_username</username> -->
      <!-- <password>my_password</password> -->
      <nonProxyHosts>localhost|127.0.0.1|*.internal.company.com</nonProxyHosts>
    </proxy>
  </proxies>
</settings>
```

---

### 2. Importing Private SSL Certificates (Java Keystore)
Corporate proxies often intercept SSL traffic by signing connections with self-signed company root certificates. If your Java environment throws an `SSLHandshakeException`, you must import the company's root certificate into Java's local Truststore (Keystore):

```bash
# Import the corporate root certificate into Java's standard truststore
# (Replace keytool path and file paths with your target system values)
sudo keytool -importcert \
    -alias corporate-root-ca \
    -file /opt/certificates/company-ca.crt \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit \
    -noprompt

# Verification:
# List the keystore aliases to confirm the certificate has been imported:
keytool -list -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit | grep corporate-root-ca
```

---

## Summary
- **Internal GitLab Instances** run on private domains and require VPN access and custom SSH key mappings.
- **Network Proxies** require configuring proxy host environment variables and port rules for local build tools.
- **Private Registries** (Nexus, Artifactory) serve as secure mirrors for public dependencies and repositories.
- **SSL Handshake Errors** are resolved by importing private CA certificates into Java's keystore files.

---

## Additional Resources
- [Apache Maven: Configuring Proxies Reference](https://maven.apache.org/guides/mini/guide-proxies.html)
- [Managing Java SSL Certificates and Truststore Files](https://docs.oracle.com/en/java/javase/17/security/oracle-jdk-security-developer-guide.html)
- [Git Configuration for Custom Corporate SSH Settings](https://git-scm.com/docs/git-config)
