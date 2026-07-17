# Exercise: Bootstrapping a Spring Boot Application

## Objective
Create and bootstrap a production-ready Spring Boot project using Spring Initializr. You will configure the project with essential starters, integrate Project Lombok to eliminate boilerplates, implement profile-specific properties inside `application.yml`, and verify the local server health using Spring Boot Actuator.

---

## Scenario
As a developer on a cloud team, you need to spin up new microservices quickly. Rather than manually wiring Maven dependencies, web server containers, and database drivers, you will utilize Spring Boot's opinionated auto-configuration ecosystem to bootstrap a backend microservice in minutes, setting up environment-specific configuration profiles from day one.

---

## References
- [Overview of Spring Boot](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/overview-of-spring-boot.md)
- [Using Spring Initializr](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/using-spring-initializr.md)
- [Auto-Configuration in Spring Boot](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/auto-configuration.md)
- [Common Spring Boot Starters](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/common-spring-boot-starters.md)
- [Lombok Reference](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/lombok.md)
- [Spring Environments & Profiles](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/spring-environments.md)

---

## Core Tasks

### 1. Scaffolding on Spring Initializr
- Visit [start.spring.io](https://start.spring.io/).
- Configure your project with the following properties:
  - **Project**: Maven Project
  - **Language**: Java
  - **Spring Boot**: 3.3.x (latest stable)
  - **Packaging**: Jar
  - **Java**: 17 or 21
- Add the following dependencies:
  - **Spring Web**
  - **Spring Data JPA**
  - **PostgreSQL Driver**
  - **H2 Database** (for development fallback)
  - **Lombok**
  - **Spring Boot DevTools**
  - **Spring Boot Actuator**
- Generate and download the project zip, unzip it, and open it in your IDE.

### 2. Implement a Lombok-Annotated Class
- Create a data entity class named `Customer`.
- Use Lombok annotations (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`) to automatically generate getters, setters, constructors, `toString()`, and a builder pattern. Verify that your IDE does not flag compilation issues (ensure Lombok plugin/annotation processing is enabled).

### 3. Configure Profile-Based application.yml
- Rename `src/main/resources/application.properties` to `application.yml`.
- Configure the YAML structure to define:
  - A default profile setting the application name: `spring.application.name=CustomerService`.
  - A `dev` profile using the in-memory H2 database (`jdbc:h2:mem:devdb`), displaying SQL commands, and letting Hibernate update schemas automatically.
  - A `prod` profile using PostgreSQL connection settings (configured via placeholder values matching typical docker environments).
  - Explicitly set the active profile to `dev`.

### 4. Verify Server Health
- Boot the Spring Boot application from your IDE.
- Look at the terminal logs to identify:
  - Which profile is active.
  - What port the application is listening on (default `8080`).
- Open a browser or terminal and request `http://localhost:8080/actuator/health` to confirm the application status is `UP`.

---

## Definition of Done
- The Spring Boot application starts without initialization errors.
- The logs print: `The following 1 profile is active: "dev"`.
- Accessing `http://localhost:8080/actuator/health` returns:
  ```json
  {"status":"UP"}
  ```
- The `Customer` class resolves constructor, getter, and setter invocations in code without manual boilerplate declarations.
