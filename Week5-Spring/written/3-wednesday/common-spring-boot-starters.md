# Common Spring Boot Starters

## Learning Objectives
- Define the purpose of Spring Boot Starter dependencies.
- Identify the most common starter dependencies used in backend development.
- Describe the primary libraries imported by each starter (Web, Data JPA, Test, Security, Actuator).
- Explain how transitive dependency resolution works in Maven or Gradle.

---

## Why This Matters
Adding library dependencies to Java projects used to require substantial manual configuration. If you wanted to build a secure web application with database access, you had to locate, import, and test dozens of individual packages. You also had to verify that the versions of these libraries were compatible. Spring Boot **Starters** solve this. They function as comprehensive build-dependency descriptors, allowing you to import related sets of libraries with a single, verified configuration block.

---

## The Concept

### What are Starter Dependencies?
**Starters** are a set of convenient dependency descriptors that you can include in your application. They act as "one-stop shops" for all the Spring and related technologies you need to build a specific type of application, without having to search for sample code and copy dependency declarations.

For example, instead of manually declaring Maven blocks for Spring MVC, Jackson (JSON parser), Logback, and Tomcat, you simply import `spring-boot-starter-web`.

```
                        [ spring-boot-starter-web ]
                                     │
       ┌──────────────────┬──────────┴──────────┬──────────────────┐
       ▼                  ▼                     ▼                  ▼
[ spring-webmvc ]  [ tomcat-embed ]  [ jackson-databind ]  [ spring-boot-starter-logging ]
```

---

### The Big Five: Common Starters and What They Include

Below are the five most frequently used starters in enterprise Spring Boot projects:

#### 1. `spring-boot-starter-web`
-   **Purpose:** Build web applications, including RESTful web services, using Spring MVC. Uses Tomcat as the default embedded container.
-   **Key Libraries Imported:**
    -   `spring-web` and `spring-webmvc` (core web framework).
    -   `tomcat-embed-core` (embedded web server).
    -   `jackson-databind` (converts Java objects to/from JSON automatically).

#### 2. `spring-boot-starter-data-jpa`
-   **Purpose:** Use Java Persistence API (JPA) with Hibernate to interact with SQL databases.
-   **Key Libraries Imported:**
    -   `hibernate-core` (the ORM provider engine).
    -   `spring-data-jpa` (repository abstraction layer).
    -   `spring-jdbc` (core JDBC helper support).

#### 3. `spring-boot-starter-test`
-   **Purpose:** Test Spring Boot applications with common testing frameworks. Added with `test` scope.
-   **Key Libraries Imported:**
    -   `junit-jupiter` (JUnit 5 engine).
    -   `mockito-core` and `mockito-junit-jupiter` (mocking framework).
    -   `assertj-core` (fluent assertions).
    -   `spring-test` (Spring context integration testing support).

#### 4. `spring-boot-starter-security`
-   **Purpose:** Implement authentication, authorization, and protection against common web vulnerabilities (CSRF, XSS).
-   **Key Libraries Imported:**
    -   `spring-security-core` (core security engine).
    -   `spring-security-config` (configuration utilities).
    -   `spring-security-web` (filters for HTTP requests).

#### 5. `spring-boot-starter-actuator`
-   **Purpose:** Expose production-ready metrics, health checks, and application information.
-   **Key Libraries Imported:**
    -   `spring-boot-actuator` (core metric engine).
    -   `micrometer-core` (observability framework).

---

## Code Example: Maven Starter Declaration

Here is how these starters are declared inside your project's `pom.xml` file. Note that you do not need to specify version tags, as they are inherited from the Spring Boot parent POM.

```xml
<dependencies>
    <!-- Web capabilities -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JPA Persistence capabilities -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Observability & Monitoring capabilities -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- Test execution capabilities (only loaded during test phase) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## Summary
-   **Starters** are dependency descriptors that bundle related libraries together to simplify dependency management.
-   **`spring-boot-starter-web`** imports the Spring MVC framework, Tomcat server, and Jackson JSON mapper.
-   **`spring-boot-starter-data-jpa`** imports Hibernate and Spring Data repository libraries.
-   **`spring-boot-starter-test`** imports JUnit, Mockito, AssertJ, and Spring Test frameworks.
-   Version numbers are managed by the Spring Boot parent POM, ensuring compatibility across all imported libraries.

---

## Additional Resources
-   [Spring Boot Documentation: Starters List](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter)
-   [Baeldung: Guide to Spring Boot Starters](https://www.baeldung.com/spring-boot-starters)
-   [Maven Guide: Introduction to Transitive Dependencies](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Transitive_Dependencies)
