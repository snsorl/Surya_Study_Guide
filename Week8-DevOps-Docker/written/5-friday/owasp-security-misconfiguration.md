# OWASP A05: Security Misconfiguration in Cloud and Container Architectures

## Learning Objectives
- Define the OWASP A05:2021 Security Misconfiguration category.
- Identify common misconfiguration vectors: default credentials, public S3 buckets, unnecessary open ports, and missing HTTP security headers.
- Analyze container security risks: privileged containers and writable filesystems.
- Apply a security hardening checklist to Spring Boot and Docker configurations.

---

## Why This Matters
Security is not just about writing clean application code. Even if your Java application has zero programming bugs, your system remains vulnerable if your deployment environment is configured poorly.

For example, leaving default administrative passwords active on your database, exposing database ports (like `5432` or `3306`) to the public internet, leaving Amazon S3 buckets publicly readable, or running Docker containers in privileged mode creates major security risks.

**Security Misconfiguration (A05)** covers these flaws. Learning how to identify and remediate configuration vulnerabilities is key to protecting your cloud infrastructure.

---

## The Concept

### 1. Common Security Misconfigurations

#### Default Credentials and Open Ports
Exposing application database admin pages (like pgAdmin or database ports) to the public internet with default credentials allows attackers to take control of your data.

#### Open Cloud Storage (Public S3 Buckets)
S3 buckets are private by default. However, developers often configure bucket access permissions incorrectly when trying to resolve sharing issues, leaving user profile pictures, payment invoices, or backup files publicly downloadable.

#### Missing HTTP Security Headers
Web servers should send security headers to protect users from browser-based attacks:
- **`Content-Security-Policy` (CSP)**: Restricts where the browser can load scripts, images, and stylesheets from, preventing Cross-Site Scripting (XSS).
- **`Strict-Transport-Security` (HSTS)**: Forces the browser to connect exclusively using encrypted HTTPS connections.
- **`X-Content-Type-Options: nosniff`**: Prevents the browser from executing files as scripts unless explicitly declared.

---

### 2. Container Misconfiguration Risks
Running containers with default settings is a common risk in DevOps environments:
- **Privileged Containers (`--privileged` flag)**: Grants the container process root access to the host machine's hardware devices. If an attacker compromises the container, they gain control of the host server.
- **Writable Root Filesystem**: Allowing containers to write to their local filesystem enables attackers to download and execute malicious scripts or malware inside the container.

---

## Code Examples and Walkthroughs

### 1. Spring Security Header Hardening
Configure Spring Security to enforce HTTP security headers:

```java
package com.example.project3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityHardeningConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .headers(headers -> headers
                // 1. Force HTTP Strict Transport Security (HSTS)
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000) // 1 year
                )
                // 2. Prevent clickjacking attacks using X-Frame-Options
                .frameOptions(frame -> frame.deny())
                // 3. Prevent MIME-sniffing
                .contentTypeOptions(config -> {})
                // 4. Configure Referrer Policy
                .referrerPolicy(referrer -> referrer
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER)
                )
                // 5. Enforce Content Security Policy (CSP)
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self' https://trustedscripts.com; object-src 'none';")
                )
            );
        return http.build();
    }
}
```

---

### 2. Remediating Docker Misconfigurations (Hardening Checklist)
Apply these configuration rules to your Docker containers in production:

```yaml
# docker-compose.prod.yml (Hardened configuration)
services:
  app-service:
    image: my-secure-app:1.0.0
    container_name: hardened-app
    # 1. Strip root capabilities from the container
    cap_drop:
      - ALL
    # 2. Run the container root filesystem as read-only
    read_only: true
    # 3. Mount temporary RAM volumes for required writable directories
    tmpfs:
      - /tmp:rw,noexec,nosuid
      - /run:rw,noexec,nosuid
    # 4. Limit CPU and RAM resources
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
    # 5. Prevent execution privileges from being elevated
    security_opt:
      - no-new-privileges:true
```

---

## Summary
- **OWASP A05** focuses on flaws in credentials management, port exposures, cloud bucket permissions, and HTTP headers.
- Harden web applications by configuring security headers like **CSP**, **HSTS**, and **X-Frame-Options**.
- **Secure Docker containers** by dropping system capabilities (`cap_drop`), using **read-only filesystems**, and limiting container resource allocations.
- **Never run containers in privileged mode** in production environments.

---

## Additional Resources
- [OWASP A05:2021 Security Misconfiguration Guide](https://owasp.org/Top10/A05_2021-Security_Misconfiguration/)
- [Spring Security Documentation: Controlling HTTP Security Headers](https://docs.spring.io/spring-security/reference/servlet/exploits/headers.html)
- [Docker Security: Restricting Capabilities and System Call Interceptions](https://docs.docker.com/engine/security/capabilities/)
