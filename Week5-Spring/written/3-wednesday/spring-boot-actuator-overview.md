# Spring Boot Actuator: Production-Ready Observability

## Learning Objectives
- Explain the role of Spring Boot Actuator in enterprise application monitoring.
- Describe what "observability" means in modern cloud-native systems.
- Identify the most common built-in Actuator endpoints (`health`, `info`, `metrics`).
- Address key security concerns associated with exposing system metrics publicly.

---

## Why This Matters
Building, testing, and running an application locally is only half the battle. Once an application is deployed to production, it functions inside a black box. If the application slows down, runs out of memory, or loses its database connection, you need to know immediately before users notice a service outage. **Spring Boot Actuator** provides production-ready observability out of the box. It exposes system endpoints that monitoring tools (like Prometheus, Grafana, or AWS CloudWatch) can query to track application health, memory usage, and runtime environment status.

---

## The Concept

### What is Observability?
**Observability** is the ability to measure the internal states of a system by analyzing its external outputs. In software, we measure this using three types of telemetry data, often referred to as the **Three Pillars of Observability**:

```
                  ┌─────────────────────────┐
                  │ Pillars of Observability│
                  └────────────┬────────────┘
         ┌─────────────────────┼─────────────────────┐
         ▼                     ▼                     ▼
     [ Metrics ]            [ Logs ]             [ Traces ]
 (Numeric Telemetry)    (String Events)     (Request Paths)
```

1.  **Metrics**: Numeric telemetry values (such as CPU load, memory utilization, or request counts).
2.  **Logs**: Text records of events that occurred at specific times.
3.  **Traces**: The path of a request as it moves through a distributed system.

---

### What is Spring Boot Actuator?
**Spring Boot Actuator** is a sub-project within the Spring Boot ecosystem that adds monitoring and management capabilities to your application. When added as a dependency, it automatically exposes a set of HTTP REST endpoints (under the default prefix `/actuator`) that provide diagnostics and system statistics.

#### Core Built-in Endpoints:
-   **`/actuator/health`**: Shows application health status (e.g., `UP`, `DOWN`). It checks database connectivity, disk space availability, and caching services.
-   **`/actuator/info`**: Displays custom application information (e.g., git commit hashes, build numbers, or team details).
-   **`/actuator/metrics`**: Exposes JVM statistics (e.g., memory utilization, thread states, garbage collection counts) and HTTP request counts.
-   **`/actuator/env`**: Exposes active environment variables and property settings.

---

### Security Considerations for Actuator Endpoints
Actuator endpoints expose sensitive system details. For example, `/actuator/env` can leak database passwords or security keys, while `/actuator/shutdown` allows remote shutdown of the application.

To secure Actuator:
1.  **Restrict Exposures**: By default, Spring Boot only exposes `/health` and `/info` over HTTP. Never expose all endpoints (`*`) in production unless they are protected.
2.  **Integrate Spring Security**: Ensure that administrative endpoints (like `/env`, `/beans`, `/metrics`) are restricted to administrators.
3.  **Use a Separate Port**: You can configure Actuator to run on a separate internal port, blocking it from public internet traffic.

---

## Code Example: Actuator Maven Setup

To enable Actuator, add the dependency to your project's `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Basic Properties Setup (`application.yml`)
To configure which endpoints are exposed over HTTP:

```yaml
management:
  endpoints:
    web:
      exposure:
        # Best Practice: Expose only what is needed
        include: "health,info,metrics"
  endpoint:
    health:
      # Shows detailed checks (e.g., db connection status)
      show-details: when_authorized
```

---

## Summary
-   **Spring Boot Actuator** adds monitoring and diagnostic endpoints to production applications.
-   **Observability** relies on metrics, logs, and traces to monitor application state.
-   Core endpoints include **`/health`** (system status), **`/info`** (build details), and **`/metrics`** (JVM statistics).
-   Actuator endpoints must be secured using **Spring Security** or internal port routing to prevent leaking database credentials and environment variables.

---

## Additional Resources
-   [Spring Boot Documentation: Actuator Production-Ready Features](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
-   [Baeldung: Spring Boot Actuator Guide](https://www.baeldung.com/spring-boot-actuator)
-   [OWASP: Securing Spring Boot Actuator Endpoints](https://owasp.org/www-project-cheat-sheets/cheatsheets/Spring_Boot_Actuator_Security_Cheat_Sheet.html)
