# Introduction to Javalin

## Learning Objectives
- Define Javalin and explain its core features.
- Compare the architecture and design philosophy of Javalin with Spring MVC.
- Set up a Javalin project using Maven or Gradle dependencies.

---

## Why This Matters
For years, Java backend developers relied on massive, complex frameworks like Spring or enterprise Java EE to build web services. While powerful, these frameworks often introduce significant overhead, extensive configuration, and magical behaviors due to reflection and dependency injection. Javalin offers a lightweight, modern alternative. By learning Javalin, you will gain a clear, un-abstracted understanding of routing, HTTP handling, and servlet containers. It is the perfect framework for building lightweight REST APIs and microservices quickly without code generation or magical annotations.

---

## The Concept

### What is Javalin?
Javalin is a lightweight, simple web framework for Java and Kotlin. It is not an MVC framework; it is focused entirely on providing a developer-friendly API for handling HTTP requests. Javalin is built on top of **Jetty**, a highly performant and mature embedded web server and servlet container.

Key features of Javalin include:
-   **No Magic**: No annotations, no dependency injection containers, and no reflection-based routing. You write plain, readable Java code.
-   **Lightweight**: Excellent performance with minimal memory footprint and fast startup times (typically less than a second).
-   **Interoperable**: First-class support for both Java and Kotlin.
-   **Modern Routing**: Simple lambdas for mapping endpoints to HTTP methods and paths.
-   **Embedded Server**: Comes with an embedded Jetty server pre-configured.

### Javalin vs. Spring MVC
While both frameworks are used to build web applications in the Java ecosystem, they have fundamentally different philosophies:

| Feature | Javalin | Spring MVC / Spring Boot |
|---|---|---|
| **Design Philosophy** | Lightweight, library-style, explicit code. | Opinionated, full-featured framework ecosystem. |
| **Routing** | Explicit lambdas (`app.get("/path", ctx -> ...)`). | Annotation-driven (`@GetMapping("/path")`). |
| **Startup Time** | Extremely fast (typically < 500ms). | Slower (dependency injection setup & component scanning). |
| **Dependencies** | Minimal dependencies. | Massive ecosystem of dependencies and transitive libraries. |
| **Magic / Abstraction**| Minimal. What you write is what runs. | High. Extensive use of reflection, proxies, and AOP. |
| **Learning Curve** | Low. Standard Java skills are sufficient. | High. Must learn Spring annotations, bean lifecycles, and configuration options. |

### Project Setup

#### Maven Configuration
To add Javalin to a Maven project, add the following dependency to your `pom.xml`. Javalin uses SLF4J for logging, so we also add a basic logger configuration like `slf4j-simple`:

```xml
<dependencies>
    <!-- Javalin Core -->
    <dependency>
        <groupId>io.javalin</groupId>
        <artifactId>javalin</artifactId>
        <version>6.1.3</version>
    </dependency>
    
    <!-- SLF4J Simple Logger (For console logging) -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>2.0.12</version>
    </dependency>
    
    <!-- Jackson (Optional, for JSON serialization) -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.17.0</version>
    </dependency>
</dependencies>
```

#### Gradle Configuration
For a Gradle project, add the following to your `build.gradle` file:

```groovy
dependencies {
    implementation 'io.javalin:javalin:6.1.3'
    implementation 'org.slf4j:slf4j-simple:2.0.12'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.0'
}
```

---

## Code Example
Creating a simple "Hello World" application in Javalin requires just a single main class and a few lines of code.

```java
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        // Create a new Javalin instance and start it on port 8080
        Javalin app = Javalin.create()
                .start(8080);

        // Define a simple GET endpoint
        app.get("/", ctx -> ctx.result("Hello World from Javalin!"));
    }
}
```

When you run this Java program:
1.  Javalin initializes and starts the embedded Jetty web server on port 8080.
2.  If you visit `http://localhost:8080` in your web browser, Jetty intercepts the GET request, runs the lambda handler, and returns the response body `"Hello World from Javalin!"`.

---

## Summary
-   **Javalin** is a lightweight, annotation-free web framework built on the Jetty servlet container.
-   Unlike Spring MVC, Javalin relies on **explicit routing** and lambdas rather than annotations and reflection, making it simple to debug and understand.
-   To use Javalin, you only need to include the `javalin` dependency and a logging framework (like `slf4j-simple` or `logback`) in your Maven or Gradle build files.

---

## Additional Resources
-   [Official Javalin Documentation](https://javalin.io/documentation)
-   [Javalin GitHub Repository](https://github.com/javalin/javalin)
-   [Jetty Web Server](https://eclipse.dev/jetty/)
