# Logging with Logback and SLF4J

## Learning Objectives
- Differentiate between the SLF4J logging facade and the Logback logging framework.
- Configure log outputs using a `logback.xml` configuration file.
- Describe the five main logging levels (TRACE, DEBUG, INFO, WARN, ERROR) and when to use each.
- Implement structured logging formatting to output logs as console strings or files.

---

## Why This Matters
Using `System.out.println()` for application logging is a major anti-pattern in professional Java development. Standard system output streams cannot be easily formatted, redirected to files, configured with different severity levels, or automatically rotated based on size. Using SLF4J and Logback provides a standard, robust logging infrastructure. It allows you to control exactly what details get output during development (like database queries) versus what gets tracked in production (like error traces), all without modifying your source code.

---

## The Concept

### SLF4J vs. Logback
-   **SLF4J (Simple Logging Facade for Java)**: SLF4J is not a logging implementation itself. It is a logging *facade* or abstraction layer. It defines log interface signatures (`Logger.info()`, `Logger.error()`) that your application code calls.
-   **Logback**: Logback is the direct *implementation* of the SLF4J interfaces. It handles writing the actual logs to the console, files, or network streams.

By coding to SLF4J interfaces, you decouple your codebase from the underlying logging library, allowing you to swap Logback for another implementation (like Log4j2) in the future without changing your code.

### Log Levels
Logging frameworks organize messages by severity levels. A level controls the verbosity of log outputs. The standard levels, from least severe (most verbose) to most severe (least verbose), are:

1.  **TRACE**: Extremely fine-grained information. Used rarely, typically to step through complex algorithms.
2.  **DEBUG**: Information useful for debugging the application flow during local development (e.g., SQL statements executed, session state dumps).
3.  **INFO**: Coarse-grained informational messages that highlight the progress of the application at a high level (e.g., "Server started on port 8080", "Database connection established").
4.  **WARN**: Indicates potentially harmful situations or unexpected conditions that do not block execution but should be inspected (e.g., "Deprecated API accessed", "Connection timeout retried").
5.  **ERROR**: Errors that prevent the application from processing a specific request or operating normally (e.g., Database connection failure, unhandled controller exception).

When you set the root logger level (e.g., to `INFO`), all messages with a level equal to or higher than `INFO` (INFO, WARN, ERROR) will be logged. Lower-level logs (DEBUG, TRACE) will be ignored.

### Logback Configuration (`logback.xml`)
Logback is configured via a file named `logback.xml` placed in the project's root resources directory (`src/main/resources`). It defines two main components:
-   **Appenders**: Destinations where log entries are sent (e.g., Console, File, Rolling File, Email).
-   **Loggers**: Configurations that map packages/classes to specific log levels and appenders.

---

## Code Example

### 1. Typical `logback.xml` Configuration
Create this file inside `src/main/resources/logback.xml`:

```xml
<configuration>

    <!-- Console Appender -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- Standard layout showing timestamp, thread, log level, class name, and message -->
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- File Appender (Optional, for writing logs to a file) -->
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/application.log</file>
        <append>true</append>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Set root logging level to INFO for all appenders -->
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>

    <!-- Set package-specific level (e.g., show debug logs only for our app code) -->
    <logger name="com.example" level="DEBUG" />

</configuration>
```

### 2. Using SLF4J in Java Code
```java
package com.example;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggedApp {
    // Obtain a logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(LoggedApp.class);

    public static void main(String[] args) {
        logger.info("Initializing Javalin application...");
        
        try {
            Javalin app = Javalin.create().start(8080);
            logger.info("Javalin started successfully on port 8080.");

            app.get("/items/{id}", ctx -> {
                String id = ctx.pathParam("id");
                
                // Logging at DEBUG level for local developer context
                logger.debug("Processing request for item ID: {}", id);

                if ("unknown".equals(id)) {
                    // Logging a warning
                    logger.warn("Request made for non-existent item: {}", id);
                    ctx.status(404).result("Item not found");
                    return;
                }

                ctx.result("Item data: " + id);
            });
            
        } catch (Exception e) {
            // Log full exception and error stack securely
            logger.error("Failed to start the application server", e);
        }
    }
}
```

---

## Summary
-   **SLF4J** is a logging facade API, while **Logback** is the logging engine that outputs the messages.
-   Logging levels from lowest to highest: **TRACE < DEBUG < INFO < WARN < ERROR**.
-   Logback settings are managed via **`logback.xml`** under `src/main/resources`, configuring appenders and log levels.
-   Use **parameterized log arguments** (`{}`) in SLF4J instead of string concatenation to improve performance and code readability.

---

## Additional Resources
-   [Logback Official Documentation](https://logback.qos.ch/documentation.html)
-   [SLF4J Official Manual](https://www.slf4j.org/manual.html)
-   [Baeldung: Guide to Logback](https://www.baeldung.com/logback)
