# Exercise: Scaffolding Project 2 with Spring Boot & JPA

## Objective
Scaffold the foundational architecture for your main team-based project (Project 2) using the Spring Boot framework. You will establish JPA entity mappings reflecting a relational database schema, create JpaRepository layers, write service interface stubs, configure PostgreSQL database connections inside a dev profile, and verify connectivity using Actuator health checks.

---

## Scenario
You are entering the architecture phase of Project 2. Rather than writing raw database connection helper classes, your team will configure the Spring Boot and Spring Data JPA ecosystem as the backbone. You need to model entities representing core database entities (such as Users, Accounts, and Transactions) and establish repository layers so that features are immediately developable in parallel sprints.

---

## References
- [Project Automation & Work Day Guide](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/5-friday/project-automation-work-day.md)
- [JPA Mappings & Entity Relations](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/4-thursday/annotations.md)
- [Spring Environments](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/3-wednesday/spring-environments.md)

---

## Core Tasks

### 1. Initialize Project 2 Structure
- Scaffold a new Spring Boot application via Spring Initializr using Java 17/21, Maven/Gradle, and Spring Boot 3.3.x.
- Add required dependencies: Spring Web, Spring Data JPA, PostgreSQL Driver, Lombok, Spring Boot Actuator, and Validation.
- Configure packages: `com.project2.core.model`, `com.project2.core.repository`, `com.project2.core.service`, `com.project2.core.controller`.

### 2. JPA Entity Modeling
Define the domain model according to the relational schema requirements. Add proper JPA mappings:
- **`User` Entity**: `id` (GeneratedValue), `username` (unique, nullable=false), `email`, `@OneToMany` relation to `Account`.
- **`Account` Entity**: `id`, `accountNumber` (unique), `balance` (BigDecimal), `@ManyToOne` relation back to `User`, `@OneToMany` relation to `Transaction` (representing history).
- **`Transaction` Entity**: `id`, `amount`, `timestamp`, `type` (Enum or String), `@ManyToOne` relation to the associated `Account`.

### 3. Create JpaRepositories
- Create `UserRepository` extending `JpaRepository<User, Long>`.
- Create `AccountRepository` extending `JpaRepository<Account, Long>`.
- Create `TransactionRepository` extending `JpaRepository<Transaction, Long>`.

### 4. Service Interfaces and Stubs
- Define service layer interfaces:
  - `UserService`: declaring user operations (`registerUser`, `getUserById`).
  - `AccountService`: declaring account operations (`createAccount`, `getBalance`).
  - `TransactionService`: declaring transaction actions (`transferFunds`, `getTransactionHistory`).
- Implement stub service classes (annotated with `@Service`) returning mock data to enable endpoint binding prior to full database logic implementation.

### 5. PostgreSQL Database Configuration
- Rename properties to `application.yml`.
- Configure connection credentials:
  ```yaml
  spring:
    datasource:
      url: jdbc:postgresql://localhost:5432/project2_db
      username: pguser
      password: pgpassword
      driver-class-name: org.postgresql.Driver
    jpa:
      hibernate:
        ddl-auto: update
      show-sql: true
      properties:
        hibernate:
          dialect: org.hibernate.dialect.PostgreSQLDialect
  ```
- Ensure a local PostgreSQL server contains the target database `project2_db`.
- Run the application. Look at the hibernate startup logging logs: verify the tables are automatically created/synchronized in PostgreSQL.

---

## Definition of Done
- The Spring Boot application boots on port 8080 without dialect or driver error loops.
- Navigating to local PostgreSQL CLI (or pgAdmin) shows that database tables (`user_table`, `account`, `transaction`) were correctly mapped and initialized.
- Requesting `/actuator/health` returns `{"status":"UP"}` confirming connection to the database.
