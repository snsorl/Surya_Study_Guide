# Wednesday Knowledge Check: Spring Boot & Observability

## Part 1: Multiple Choice & Objective Questions

### 1. What core mechanism does Spring Boot use to configure beans automatically based on dependencies present in the project's classpath?
- [ ] A) Compilation-time bytecode injection.
- [ ] B) XML schemas downloaded dynamically from GitHub.
- [ ] C) Auto-Configuration (via `@EnableAutoConfiguration` and `@Conditional` annotations).
- [ ] D) In-memory JVM class file mutation.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Auto-Configuration (via `@EnableAutoConfiguration` and `@Conditional` annotations).

**Explanation:** Spring Boot uses class scanning and classpath evaluations to run auto-configuration classes. If a class like `DataSource` is present on the classpath, Spring Boot automatically configures an in-memory database bean using default properties, unless overridden by the developer.
- **Why others are wrong:**
  - A) Spring Boot does not inject bytecode during compilation; beans are registered in memory at runtime.
  - B) Spring Boot auto-configuration relies on Java config classes within starter JARs, not external XML schemas.
  - D) Spring Boot does not dynamically mutate compile classes; it registers bean definitions dynamically.
</details>

---

### 2. Which three annotations are bundled inside the `@SpringBootApplication` meta-annotation?
- [ ] A) `@Configuration`, `@ComponentScan`, and `@EnableAutoConfiguration`
- [ ] B) `@Component`, `@Autowired`, and `@Qualifier`
- [ ] C) `@Service`, `@Repository`, and `@Controller`
- [ ] D) `@EnableTransactionManagement`, `@EnableWebMvc`, and `@EnableCaching`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `@Configuration`, `@ComponentScan`, and `@EnableAutoConfiguration`

**Explanation:** `@SpringBootApplication` is a convenience annotation that registers a configuration class, enables component scanning under the package of the annotated class, and enables Spring Boot's auto-configuration engine.
- **Why others are wrong:**
  - B) These are core dependency injection annotations, not application bootstrap level annotations.
  - C) These are business layer stereotype annotations.
  - D) These enable specific features (transactions, MVC, caching) but do not configure project-wide bootstrapping.
</details>

---

### 3. What is the role of a Spring Boot "starter" dependency in Maven/Gradle?
- [ ] A) It compiles the application into an executable installer script.
- [ ] B) It acts as a descriptor that groups related dependencies into a single, cohesive starter dependency block.
- [ ] C) It initializes the database schema during local tests.
- [ ] D) It acts as a runtime proxy container for servlets.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It acts as a descriptor that groups related dependencies into a single, cohesive starter dependency block.

**Explanation:** Spring Boot starters (like `spring-boot-starter-web`) simplify dependency management. Instead of manually listing individual libraries (Spring MVC, Jackson, Tomcat, Validation), you import one starter, and Maven resolves all transitive dependencies automatically.
- **Why others are wrong:**
  - A) Starters are simple XML/POM dependency definitions; they do not compile executable systems.
  - C) Schema initialization is handled by JPA/flyway configs, not starter dependency wrappers.
  - D) Servlet processing is handled by embedded engines (like Jetty/Tomcat) included inside starters.
</details>

---

### 4. When using Project Lombok, which annotation is a shorthand combination that bundles `@Getter`, `@Setter`, `@RequiredArgsConstructor`, `@ToString`, and `@EqualsAndHashCode`?
- [ ] A) `@Builder`
- [ ] B) `@Value`
- [ ] C) `@Data`
- [ ] D) `@NoArgsConstructor`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `@Data`

**Explanation:** `@Data` is a Lombok convenience annotation that automatically generates getters, setters, standard toString, equals, and hashCode, and a constructor for all final fields.
- **Why others are wrong:**
  - A) `@Builder` implements the builder design pattern for class instantiation.
  - B) `@Value` is used for immutable data objects (generates getters, but no setters, and makes fields private final).
  - D) `@NoArgsConstructor` generates a zero-argument constructor, which is not bundled inside `@Data` if final fields are present.
</details>

---

### 5. What is the primary purpose of the Spring Boot Actuator module?
- [ ] A) To compile the application code into machine-native binaries.
- [ ] B) To provide production-ready application monitoring, metrics, and health observability endpoints.
- [ ] C) To automatically run database transactions in parallel threads.
- [ ] D) To manage user credentials and secure web route endpoints.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) To provide production-ready application monitoring, metrics, and health observability endpoints.

**Explanation:** Actuator exposes HTTP endpoints (like `/actuator/health`, `/actuator/metrics`, `/actuator/env`) allowing developers and monitoring systems to inspect application health, trace configuration environment properties, and collect performance metrics.
- **Why others are wrong:**
  - A) Compilation is managed by compilers/Maven.
  - C) Thread pooling is managed by TaskExecutors.
  - D) Route authentication is managed by Spring Security.
</details>

---

### 6. By default, which Actuator endpoints are exposed over Web (HTTP) in a standard Spring Boot application?
- [ ] A) All endpoints (health, info, beans, env, mappings)
- [ ] B) Only the `/health` endpoint
- [ ] C) No endpoints are exposed for security reasons
- [ ] D) Only the `/info` endpoint

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Only the `/health` endpoint

**Explanation:** For security reasons, only the `/health` endpoint is exposed over HTTP by default. To expose other endpoints, you must configure `management.endpoints.web.exposure.include` in your application properties.
- **Why others are wrong:**
  - A) Exposing all endpoints by default is a security risk as they leak environmental variables and configurations.
  - C) `/health` is exposed by default.
  - D) `/info` is shut off by default in newer Spring Boot versions.
</details>

---

### 7. How do you activate a specific profile named `dev` when starting a Spring Boot application via terminal properties?
- [ ] A) `java -jar app.jar --spring.profiles.active=dev`
- [ ] B) `java -jar app.jar --active.profile=dev`
- [ ] C) `java -jar app.jar --profile=dev`
- [ ] D) `java -jar app.jar --set.profile:dev`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `java -jar app.jar --spring.profiles.active=dev`

**Explanation:** Spring Boot parses the command line argument `spring.profiles.active` to set the active profile, which overrides properties configured inside `application.yml` or defaults.
- **Why others are wrong:**
  - B, C, D) These are incorrect property names that will not be resolved by Spring's environment property sources.
</details>

---

### 8. Which of the following features is provided by `spring-boot-devtools`?
- [ ] A) Automatic compilation of Java source code to Docker images.
- [ ] B) Automatic restart of the application container whenever files on the classpath change.
- [ ] C) Automated encryption of database connection strings.
- [ ] D) Automated backup of local PostgreSQL database tables.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Automatic restart of the application container whenever files on the classpath change.

**Explanation:** DevTools monitors classpath files. If a file is recompiled, DevTools triggers a fast restart of the Spring context, saving developers from manually restarting the server. It also enables LiveReload in the browser and disables template caching.
- **Why others are wrong:**
  - A) Docker images are built via Maven plugins or buildpacks, not DevTools.
  - C) Connections are encrypted using libraries like Jasypt.
  - D) Database backups are managed by database systems, not Java development tools.
</details>

---

### 9. If a bean is annotated with `@Profile("prod")`, what happens to that bean during application startup in the `dev` profile?
- [ ] A) The bean is registered in the context but disabled.
- [ ] B) The application throws an exception because the bean cannot be loaded.
- [ ] C) The bean is ignored and not registered in the application context.
- [ ] D) The bean is instantiated but its properties are set to null.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) The bean is ignored and not registered in the application context.

**Explanation:** `@Profile` acts as a conditional registration rule. If the active profile does not match the value inside the `@Profile` annotation, the container ignores the bean definition during startup.
- **Why others are wrong:**
  - A) Active beans are either registered or not; they cannot exist in a "registered but disabled" state.
  - B) No exception is thrown; the bean is simply skipped.
  - D) Instantiation does not occur because no definition for the bean is placed in the context registry.
</details>

---

### 10. Consider the Lombok-annotated class below:
```java
@Builder
public class Product {
    private String name;
    private double price;
}
```
Which is the correct code syntax to instantiate this class using the builder pattern?
- [ ] A) `Product p = new Product.Builder().name("Soap").price(2.0).build();`
- [ ] B) `Product p = Product.builder().name("Soap").price(2.0).build();`
- [ ] C) `Product p = Product.build().name("Soap").price(2.0);`
- [ ] D) `Product p = Product.newProduct().name("Soap").price(2.0).build();`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Product p = Product.builder().name("Soap").price(2.0).build();`

**Explanation:** Lombok's `@Builder` annotation generates a static method `builder()` in the target class that returns the builder utility class, which chaining methods and compiles with `build()`.
- **Why others are wrong:**
  - A) The builder class cannot be directly instantiated using `new Product.Builder()` in standard Lombok setups.
  - C) `Product.build()` is not the correct entry point name.
  - D) `newProduct()` is not a standard Lombok builder pattern name.
</details>
