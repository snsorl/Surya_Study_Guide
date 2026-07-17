# Exercise: Integrating Structured Logging

## Objective
Add the Logback logging framework to your Javalin Hello API project. Configure it to record transaction timelines to both the console and a local rolling log file, mapping request details and routing exceptions.

---

## Scenario
To avoid the security failures highlighted in OWASP A09 (Security Logging and Monitoring Failures), your team requires detailed request trails. You must configure SLF4J and Logback to track incoming HTTP routes and log application crashes without leaking secrets.

---

## Core Tasks

### 1. Dependency Transition
- Replace the basic `slf4j-simple` dependency in your build configurations with the robust `logback-classic` implementation.
- Add Jackson mapper dependencies to support JSON serialization formatting.
- Reference: [logging-with-logback.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/1-monday/logging-with-logback.md)

### 2. Logback Configuration File
- Create a file named `logback.xml` in your project's `src/main/resources` folder.
- Configure two Appenders:
  1. A **ConsoleAppender** to display formatted log rows in the IDE.
  2. A **FileAppender** or **RollingFileAppender** mapping logs to a local file target at `logs/app.log`.
- Set the root logging level threshold to `INFO`.

### 3. Implement Request Middleware Logger
- Register a global `before` or `after` handler in your Javalin app instance.
- Log the incoming HTTP request details (e.g. Method and Path) at the `INFO` level using parameterized SLF4J arguments (`{}`), not string concatenation.
- Reference: [owasp-security-logging-and-monitoring-failures.md](file:///c:/Learning/INF-JFSD/content/Week4-HTML-CSS-REST-Javalin/written/1-monday/owasp-security-logging-and-monitoring-failures.md)

### 4. Implement Exception Mapper Logger
- Map runtime exceptions globally in your app.
- When an exception is caught, log the error message at the `ERROR` level and return a structured JSON response body with HTTP 500 status.
- Add an endpoint `/error-test` that intentionally throws a `RuntimeException` to test your handler.

---

## Definition of Done
- A `logback.xml` exists in the classpath resources folder.
- Accessing any route prints an INFO log trace containing request variables in the console.
- A directory named `logs/` is generated locally containing `app.log`.
- Accessing `/error-test` returns `500 Internal Server Error` with JSON error details, and records an ERROR log trace containing the stack.
