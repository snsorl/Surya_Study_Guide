# Spring Boot Environments and Profiles

## Learning Objectives
- Compare the structure and benefits of `application.properties` and `application.yml` configurations.
- Create and manage profile-specific properties files (e.g. `application-dev.yml` vs `application-prod.yml`).
- Use the `@Profile` annotation to conditionally load beans.
- Activate specific configuration profiles through system properties, environment variables, and build tools.

---

## Why This Matters
Software systems run in different environments. During development, you use a local database (like an in-memory database or a local PostgreSQL container). During testing, QA runs integration tests. In production, you connect to highly secure, distributed database clusters. Hardcoding configurations (like URLs, credentials, or API rates) for different environments inside your codebase is dangerous. Spring Profiles solve this. They allow you to define environment-specific configurations and select which environment settings to load dynamically at startup.

---

## The Concept

### Properties vs. YAML Configuration
Spring Boot supports two configuration file formats to define application settings, both located under `src/main/resources/`.

#### 1. `application.properties` (Flat File)
Uses standard key-value pairs. Simple to read, but leads to repetitive keys.
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/devdb
spring.datasource.username=postgres
spring.datasource.password=secret
```

#### 2. `application.yml` (YAML - Hierarchical)
Uses indentation to represent hierarchies. More readable and less repetitive.
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/devdb
    username: postgres
    password: secret
```

---

### Spring Profiles
A **Profile** is a logical grouping of configurations. You can create different properties files for each environment using the naming pattern `application-{profile}.properties` or `application-{profile}.yml`.

```
[ application.yml ] (Common settings)
       │
       ├─► [ application-dev.yml ]  (Local DB, verbose logging)
       │
       └─► [ application-prod.yml ] (Secure DB, warning logging only)
```

#### Naming Conventions:
-   `application.yml`: Contains global configurations loaded in all environments.
-   `application-dev.yml`: Configurations applied when the `dev` profile is active.
-   `application-prod.yml`: Configurations applied when the `prod` profile is active.

---

### Conditional Bean Loading with `@Profile`
You can prevent specific classes from instantiating unless a specific profile is active. This is useful for loading mock email senders during development, but real SMS gateways in production.

```java
@Component
@Profile("dev") // Instantiated only if the 'dev' profile is active
public class MockEmailService implements EmailService { ... }
```

---

### Activating Profiles
To activate a specific profile, use one of the following methods at startup:

#### 1. In `application.properties` / `application.yml`:
```yaml
spring:
  profiles:
    active: dev
```

#### 2. JVM System Argument (Terminal Execution):
```bash
java -jar myapp.jar -Dspring.profiles.active=prod
```

#### 3. OS Environment Variable (Cloud/Docker Deployments):
Set the environment variable `SPRING_PROFILES_ACTIVE`.
```bash
export SPRING_PROFILES_ACTIVE=prod
```

---

## Code Example: Conditionally Loading a Data Seeder

This example demonstrates how to seed fake database records only when running in the development profile.

### The Seeder Service
```java
package com.example.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // Tells Spring to completely ignore this bean in production
public class DevDataSeeder implements CommandLineRunner {

    public DevDataSeeder() {
        System.out.println("[CONFIG] Running in Dev Profile. Initializing database seeder...");
    }

    @Override
    public void run(String... args) throws Exception {
        // Seeding database
        System.out.println("[DB] Seeding 10 dummy customer records...");
    }
}
```

### The Production Alternative (Real Service)
```java
package com.example.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod") // Only loads in production
public class ProductionVerifier implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("[SECURITY] Verifying database connection health parameters...");
    }
}
```

---

## Summary
-   **Properties** files use key-value format; **YAML** files use indentation to represent hierarchical properties.
-   **Spring Profiles** separate configuration settings according to environment (e.g. dev, test, prod).
-   Use **`application-{profile}.yml`** to define environment-specific properties.
-   Use **`@Profile("name")`** to restrict bean loading to active environments.
-   Activate profiles at startup using the **`-Dspring.profiles.active`** system property or **`SPRING_PROFILES_ACTIVE`** environment variable.

---

## Additional Resources
-   [Spring Boot Documentation: Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)
-   [Baeldung: Spring Profiles Guide](https://www.baeldung.com/spring-profiles)
-   [Baeldung: Properties vs. YAML configuration](https://www.baeldung.com/spring-boot-yaml-vs-properties)
