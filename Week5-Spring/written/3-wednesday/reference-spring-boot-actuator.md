# Reference: Configuring Spring Boot Actuator

## Learning Objectives
- Configure endpoint exposure rules in `application.yml` or `application.properties`.
- Customize the base path and port configuration for Actuator endpoints.
- Secure Actuator endpoints using properties configurations.
- Implement a custom `HealthIndicator` bean.

---

## Why This Matters
Spring Boot Actuator defaults to secure, restrictive settings: only `/health` and `/info` are exposed over HTTP, and detailed diagnostics are hidden. In a local development environment, you might want to expose more endpoints (like `/env` or `/beans`) to debug problems, while in production you must restrict them. Having a clean, structured properties reference guide allows you to configure Actuator exposure, security rules, and ports quickly.

---

## Configuration Reference: `application.yml`

Below is a structured template for configuring Actuator settings in your application.

```yaml
management:
  # 1. Customize Actuator Network Bindings
  server:
    port: 8081 # Run Actuator on a separate port for security
    address: 127.0.0.1 # Bind only to localhost (blocks external public queries)

  # 2. Customize Endpoints Base Path
  endpoints:
    web:
      base-path: /manage # Changes endpoint prefix from '/actuator' to '/manage'
      exposure:
        include: "health,info,metrics,prometheus" # Endpoints to expose over HTTP
        exclude: "env,shutdown" # Endpoints to explicitly block
        
  # 3. Configure Specific Endpoint Options
  endpoint:
    health:
      show-details: when_authorized # Choices: never, always, when_authorized
      roles: "ROLE_ADMIN" # Role required to view health details
      probes:
        enabled: true # Enable Kubernetes Liveness and Readiness probes
        
    shutdown:
      enabled: false # Best Practice: Keep remote application shutdown disabled
      
    env:
      keys-to-sanitize: "password,secret,key,token,.*credentials.*" # Mask sensitive values
```

---

## Implementing a Custom `HealthIndicator`

Sometimes, you need to check a custom resource (like a third-party billing API or a local file directory) to determine if your application is healthy. You can do this by implementing the **`HealthIndicator`** interface.

### The Custom Implementation:
Spring scans for any bean implementing `HealthIndicator` and automatically registers it under `/actuator/health`. The bean's class name suffix (e.g. `BillingApiHealthIndicator` becomes `billingApi`) determines the key name in the JSON output.

```java
package com.example.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class BillingApiHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Run a custom check: Ping the external billing gateway API
        boolean isBillingApiUp = checkBillingApiEndpoint();

        if (isBillingApiUp) {
            return Health.up()
                    .withDetail("billingServiceUrl", "https://api.gateway.com/pay")
                    .withDetail("latencyMs", 42)
                    .build(); // Status: UP
        } else {
            return Health.down()
                    .withDetail("error", "Connection timed out to Billing Service.")
                    .build(); // Status: DOWN
        }
    }

    private boolean checkBillingApiEndpoint() {
        try {
            URL url = new URL("https://api.gateway.com/health");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.connect();
            return connection.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
```

---

## Summary
-   Configure Actuator HTTP exposures using **`management.endpoints.web.exposure.include`**.
-   Improve security by changing the Actuator port via **`management.server.port`** and binding it to localhost.
-   Enable **`show-details: when_authorized`** to hide sensitive system configurations from anonymous public users.
-   Implement the **`HealthIndicator`** interface to hook custom check logic directly into `/actuator/health`.

---

## Additional Resources
-   [Spring Boot Documentation: Actuator Configuration Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.actuator)
-   [Baeldung: Custom Health Indicators](https://www.baeldung.com/spring-boot-custom-health-indicator)
-   [Micrometer Registry Prometheus Support Guide](https://micrometer.io/docs/registry/prometheus)
