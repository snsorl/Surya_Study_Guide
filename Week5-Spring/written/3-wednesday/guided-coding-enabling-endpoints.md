# Guided Coding: Exposing Observability & Info Contributors

## Learning Objectives
- Add Spring Boot Actuator capabilities to an active application.
- Expose specific Actuator endpoints using properties configurations.
- Customize the `/actuator/info` endpoint using the `InfoContributor` interface.
- Query Actuator endpoints from a browser and review their JSON outputs.

---

## Why This Matters
While default Actuator settings are useful, production systems require customization. For instance, build pipelines need to write specific metadata (like Git commit hashes, release dates, and authors) into the application package so that administrators can identify which version of the codebase is currently running. In this guide, we will add Actuator monitoring to our Student Directory application, expose metrics endpoints, and write a custom Java class to dynamically inject build details into `/actuator/info`.

---

## Guided Walkthrough

### Step 1: Add Actuator Dependency
Add the starter package inside the dependency block of your `pom.xml` file.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

---

### Step 2: Configure Endpoint Exposure (`application.yml`)
Expose the `health`, `info`, and `metrics` endpoints over HTTP. We will also enable detailed health checks so we can see database connectivity status.

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "health,info,metrics"
  endpoint:
    health:
      show-details: always # Shows detailed components (disk, db)
```

---

### Step 3: Implement a Custom InfoContributor (`config/BuildInfoContributor.java`)
Spring Boot exposes information about the application via `/actuator/info`. By implementing the **`InfoContributor`** interface, we can inject custom values dynamically at runtime.

Create `BuildInfoContributor.java` inside your configuration package:

```java
package com.infosys.studentdirectory.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component // Registers automatically with Actuator
public class BuildInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> buildDetails = new HashMap<>();
        buildDetails.put("version", "1.0.4-RELEASE");
        buildDetails.put("commitHash", "f7d2a93c");
        buildDetails.put("releaseDate", "2026-07-16");
        
        Map<String, Object> teamDetails = new HashMap<>();
        teamDetails.put("department", "Global Education Center");
        teamDetails.put("projectLead", "Instructor Jasdhir");

        // Inject mappings under custom namespaces
        builder.withDetail("build", buildDetails);
        builder.withDetail("team", teamDetails);
    }
}
```

---

### Step 4: Run the Application
Start your Spring Boot application locally. Tomcat will start on port 8080 by default.

---

### Step 5: Query and Verify the Endpoints

Open your browser or use a command line tool (like `curl`) to verify:

#### 1. Query the `/actuator/health` endpoint
-   **URL:** `http://localhost:8080/actuator/health`
-   **Expected Output:** Since `show-details: always` is enabled, you will see a detailed breakdown of disk space and DB connections.

#### 2. Query the `/actuator/info` endpoint
-   **URL:** `http://localhost:8080/actuator/info`
-   **Expected Output:**
    ```json
    {
      "build": {
        "version": "1.0.4-RELEASE",
        "commitHash": "f7d2a93c",
        "releaseDate": "2026-07-16"
      },
      "team": {
        "department": "Global Education Center",
        "projectLead": "Instructor Jasdhir"
      }
    }
    ```

#### 3. Query the `/actuator/metrics` endpoint
-   **URL:** `http://localhost:8080/actuator/metrics`
-   **Expected Output:** A list of available metric keys. To check JVM thread counts, query: `http://localhost:8080/actuator/metrics/jvm.threads.live`.

---

## Summary
-   Import **`spring-boot-starter-actuator`** to activate diagnostics.
-   Expose endpoints via properties using **`management.endpoints.web.exposure.include`**.
-   Implement the **`InfoContributor`** interface and implement `contribute()` to inject metadata into the `/info` endpoint dynamically.
-   Diagnose issues by analyzing the JSON outputs of Actuator endpoints.

---

## Additional Resources
-   [Spring Boot Documentation: Writing Custom InfoContributors](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints.info.writing-custom-info-contributors)
-   [Baeldung: Guide to Actuator Endpoints](https://www.baeldung.com/spring-boot-actuator)
-   [Baeldung: Custom Health Indicators in Spring Boot](https://www.baeldung.com/spring-boot-custom-health-indicator)
