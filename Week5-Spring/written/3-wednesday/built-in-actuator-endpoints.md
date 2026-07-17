# Built-in Actuator Endpoints Reference

## Learning Objectives
- Query and analyze the output of core Actuator endpoints.
- Explain the role of `HealthIndicators` in calculating system status.
- Query specific system statistics using parameters on the `/metrics` endpoint.
- Understand the diagnostic data returned by `/env` and `/beans` endpoints.

---

## Why This Matters
Adding Actuator to your dependencies is only the first step. To monitor your application effectively, you need to know how to query its endpoints, parse the JSON payloads they return, and query specific metrics like JVM memory utilization or request rates. This guide details the structure, query parameters, and JSON payloads of the most common built-in Actuator endpoints, preparing you to diagnose issues in production environments.

---

## The Concept

### Endpoint Details and Structures

---

### 1. `/actuator/health`
Displays application health status. It queries a collection of **`HealthIndicators`** (e.g. `PingHealthIndicator`, `DiskSpaceHealthIndicator`, `DataSourceHealthIndicator`) and aggregates their status.
-   **Aggregated Status Rule**: If any indicator returns `DOWN`, the global status is marked as `DOWN` (a single point of failure model).

#### Sample JSON Response (Detailed View):
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 499638910976,
        "free": 218903829504,
        "threshold": 10485760
      }
    }
  }
}
```

---

### 2. `/actuator/info`
Displays general information about the application. The payload is empty by default. You configure this endpoint by adding properties in `application.yml` under the `info.` prefix.

#### Configuration Example:
```yaml
info:
  app:
    name: "Billing Portal"
    version: "2.1.0-SNAPSHOT"
  author: "Infosys ADM Team"
```

---

### 3. `/actuator/metrics`
Exposes system statistics. Calling `/actuator/metrics` returns a list of all tracked metric names (such as `jvm.memory.used`, `http.server.requests`, `process.cpu.usage`).

#### Querying Specific Metrics:
To query a specific metric, append the metric name to the URL path:
-   URL: `http://localhost:8080/actuator/metrics/jvm.memory.used`

#### Sample JSON Response:
```json
{
  "name": "jvm.memory.used",
  "measurements": [
    {
      "statistic": "VALUE",
      "value": 118392816
    }
  ]
}
```

---

### 4. `/actuator/env`
Exposes all configuration properties available to the Spring environment, including:
-   JVM System properties.
-   OS environment variables.
-   Properties loaded from `application.properties` or `application.yml`.
*Security Warning:* Highly sensitive keys should be masked using `management.endpoint.env.keys-to-sanitize` to prevent leaking database credentials or API keys.

---

### 5. `/actuator/beans`
Exposes a complete registry of all Spring beans defined in the `ApplicationContext`, including their scope, type, dependencies, and aliases. This is useful for debugging wiring and bean naming issues.

---

## Summary
-   **`/health`** aggregates status checks from multiple `HealthIndicators` like DB, disk space, and cache.
-   **`/info`** exposes custom build data populated from `application.yml`.
-   **`/metrics`** allows you to query specific JVM and application performance metrics using path parameters.
-   **`/env`** and **`/beans`** provide detailed runtime environment and container dependency graph diagnostics.

---

## Additional Resources
-   [Spring Boot Documentation: Actuator Endpoints List](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints)
-   [Baeldung: Custom Health Indicators in Spring Boot](https://www.baeldung.com/spring-boot-custom-health-indicator)
-   [Prometheus Official Site: Monitoring Architectures](https://prometheus.io/)
