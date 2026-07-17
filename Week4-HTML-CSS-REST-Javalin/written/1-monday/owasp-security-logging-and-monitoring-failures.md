# OWASP A09: Security Logging and Monitoring Failures

## Learning Objectives
- Define OWASP A09: Security Logging and Monitoring Failures.
- Identify key security events that must be logged.
- Explain why proactive monitoring and alerting are critical for API security.
- Describe how insufficient logging enables security breaches and delays incident response.

---

## Why This Matters
According to safety statistics, the average time to detect a data breach is over 200 days. During this time, attackers typically remain inside target networks, extracting data or escalating privileges. This delay is almost always caused by insufficient logging and monitoring. As a backend developer, writing code that works under normal conditions is only half the battle. You must also write code that exposes suspicious activity. If you fail to log failed login attempts, privilege escalation actions, or validation failures, you leave your application open to undetected abuse.

---

## The Concept

### What is OWASP A09?
**Security Logging and Monitoring Failures** (formerly known as Insufficient Logging & Monitoring) occurs when an application does not record key events, or when the records are not monitored in a way that allows teams to detect and respond to active threats in a timely manner.

Without adequate logging and monitoring:
-   Attacks cannot be detected in real-time.
-   Post-incident forensics are impossible because there are no logs to reconstruct the timeline of the breach.
-   The organization remains blind to vulnerability scanners, brute-force attacks, or credential stuffing.

### What Events Should Be Logged?
An application should log security-significant events with enough context to identify the source, but without exposing sensitive user secrets.

#### Events to Log:
-   **Authentication & Authorization**: Successful logins, failed login attempts (especially multiple failures for the same user or IP), and password resets.
-   **Access Failures**: 401 Unauthorized or 403 Forbidden events on sensitive API endpoints.
-   **Input Validation Failures**: Parameter tampering, SQL injection payloads, or cross-site scripting attempts.
-   **Critical Changes**: Changes to account balances, privilege level updates (e.g., granting admin roles), deletion of accounts, or bulk export of records.
-   **System Errors**: 500 Internal Server Errors, database connectivity issues, and system startup/shutdown sequences.

#### What NOT to Log (PII & Secrets):
Logging sensitive information (known as **log leakage**) is itself a severe security issue. Never write these to logs:
-   Passwords, PINs, or security questions/answers.
-   Credit card numbers, CVVs, or bank account details.
-   API keys, session tokens, or JWT authorization secrets.
-   Protected Health Information (PHI) or personally identifiable information (PII) like SSNs where not explicitly legally required and protected.

### The Importance of Active Monitoring
Logs are useless if they simply sit on a server's hard drive. Proactive monitoring involves:
-   **Centralization**: Sending logs from all application instances to a centralized log management server (e.g., Splunk, Elasticsearch/Kibana, Datadog).
-   **Alerting**: Setting up rules to notify engineering teams immediately if anomalies occur (e.g., "Alert if an IP encounters more than 20 failed login attempts in 1 minute").
-   **Log Protection**: Ensuring logs are write-only or stored in a way that attackers cannot delete them to cover their tracks.

---

## Code Example

Here is a conceptual example of a Javalin backend handler that logs security-sensitive events versus a vulnerable implementation that logs nothing or logs too much.

### Vulnerable Implementation (Logs Nothing)
```java
app.post("/login", ctx -> {
    String username = ctx.formParam("username");
    String password = ctx.formParam("password");
    boolean success = authService.authenticate(username, password);
    if (!success) {
        ctx.status(401).result("Login failed");
        return;
    }
    ctx.sessionAttribute("user", username);
});
```
*Why it's bad*: If an attacker runs a brute-force script against this endpoint, there is no audit trail. The security team will have no idea that millions of passwords were tried.

### Vulnerable Implementation (Log Leakage)
```java
app.post("/login", ctx -> {
    String username = ctx.formParam("username");
    String password = ctx.formParam("password");
    // VIOLATION: Logging raw password!
    System.out.println("User login attempt: " + username + " with password: " + password);
});
```
*Why it's bad*: Passwords are now stored in plain text in the system logs, which are often read by operations engineers, security tools, and third-party systems.

### Secure Implementation
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static void login(io.javalin.http.Context ctx) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        
        boolean success = authService.authenticate(username, password);
        
        if (!success) {
            // Log security event with IP address and username, but NO password
            logger.warn("Security Alert: Failed login attempt for user='{}' from IP='{}'", 
                        username, ctx.ip());
            ctx.status(401).result("Authentication failed");
            return;
        }
        
        logger.info("Security Audit: User '{}' logged in successfully from IP='{}'", 
                    username, ctx.ip());
        ctx.sessionAttribute("user", username);
    }
}
```

---

## Summary
-   **OWASP A09** deals with the inability of an application to record, monitor, and alert on malicious behavior.
-   Always log authentication events, access controls, input violations, and errors.
-   **Never log secrets** like passwords, JWTs, or credit cards.
-   Logs must be centralized and monitored with automated alerts to detect attacks as they happen.

---

## Additional Resources
-   [OWASP Top 10: Security Logging and Monitoring Failures](https://owasp.org/Top10/A09_2021-Security_Logging_and_Monitoring_Failures/)
-   [OWASP Logging Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Logging_Cheat_Sheet.html)
-   [Nist Guide to Computer Security Log Management](https://csrc.nist.gov/publications/detail/sp/800-92/final)
