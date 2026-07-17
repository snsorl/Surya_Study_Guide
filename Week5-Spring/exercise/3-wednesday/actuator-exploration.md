# Exercise: Observability with Spring Boot Actuator

## Objective
Expose, analyze, and customize production-grade monitoring endpoints using Spring Boot Actuator. You will update endpoint exposure settings inside your configuration files, query JVM metrics, and implement a custom Java class implementing `InfoContributor` to display trainee diagnostics.

---

## Scenario
In an enterprise cloud setup, developers do not log onto servers to check app health or run thread dumps. Instead, automated monitoring systems poll dedicated API paths. You are tasked with opening these paths for a development environment, verifying JVM memory performance metrics, and creating a custom information gateway that exposes build versioning and trainee identities for automated diagnostics.

---

## References
- [Spring Boot Actuator Overview](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/spring-boot-actuator-overview.md)
- [Built-In Actuator Endpoints](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/built-in-actuator-endpoints.md)
- [Reference: Actuator Configuration](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/reference-spring-boot-actuator.md)
- [Guided Coding: Enabling Endpoints](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/guided-coding-enabling-endpoints.md)

---

## Core Tasks

### 1. Expose Actuator Endpoints
- Open your `application.yml` or `application.properties`.
- Add configuration settings to expose all web endpoints:
  - In YAML:
    ```yaml
    management:
      endpoints:
        web:
          exposure:
            include: "*"
    ```
  - In properties:
    ```properties
    management.endpoints.web.exposure.include=*
    ```
- Re-run the application and verify that requesting `http://localhost:8080/actuator` returns a list of active endpoint links (e.g. `/beans`, `/env`, `/mappings`, `/metrics`).

### 2. Query System Metrics
- Open a terminal and run `curl http://localhost:8080/actuator/metrics` to view the list of trackable metrics.
- Navigate to the metric url: `http://localhost:8080/actuator/metrics/jvm.memory.used`.
- Identify the current memory footprint in the JSON output body.

### 3. Build a Custom InfoContributor
- Create a Java class named `TraineeInfoContributor` implementing the `InfoContributor` interface:
  ```java
  import org.springframework.boot.actuate.info.Info;
  import org.springframework.boot.actuate.info.InfoContributor;
  import org.springframework.stereotype.Component;
  import java.util.HashMap;
  import java.util.Map;

  @Component
  public class TraineeInfoContributor implements InfoContributor {
      @Override
      public void contribute(Info.Builder builder) {
          Map<String, Object> traineeDetails = new HashMap<>();
          traineeDetails.put("name", "Your Name");
          traineeDetails.put("cohort", "INF-JFSD-2026");
          traineeDetails.put("role", "Developer Trainee");

          builder.withDetail("trainee", traineeDetails);
      }
  }
  ```
- Ensure the class is annotated with `@Component` so Spring Boot registers it.
- Request `http://localhost:8080/actuator/info` in your browser. Verify the trainee details are visible.

---

## Definition of Done
- All Actuator endpoints are queryable over HTTP.
- Querying `http://localhost:8080/actuator/metrics/jvm.memory.used` succeeds and yields an active metric value.
- Requesting `http://localhost:8080/actuator/info` yields the custom trainee data:
  ```json
  {
    "trainee": {
      "name": "Your Name",
      "cohort": "INF-JFSD-2026",
      "role": "Developer Trainee"
    }
  }
  ```
