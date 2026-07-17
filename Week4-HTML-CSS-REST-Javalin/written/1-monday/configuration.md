# Configuring Javalin

## Learning Objectives
- Configure a Javalin application instance with custom settings.
- Enable Cross-Origin Resource Sharing (CORS) for frontend-backend communication.
- Set up Javalin to serve static files (HTML, CSS, JS) from the resource folder.
- Understand how to register plugins in Javalin.

---

## Why This Matters
A raw, unconfigured Javalin application runs on a default port and does not share resources with other origins by default. When building a full-stack application where your frontend (HTML/CSS/JS) is running on a different port or origin than your backend API, CORS restrictions will block request transmissions. Configuring your Javalin server to allow CORS, serve static frontend assets, and use plugins is essential to establishing a smooth developer experience and ensuring your backend can be successfully consumed by your frontend.

---

## The Concept

### The Javalin Config Block
Javalin configuration is done when creating the server instance. We pass a configuration consumer lambda into the `Javalin.create()` method. 

```java
Javalin app = Javalin.create(config -> {
    // Configuration options go here
});
```

Using this config object, you can set server properties, register plugins, enable request logging, specify routing defaults, and define security parameters.

### CORS Configuration
Cross-Origin Resource Sharing (CORS) is a security mechanism enforced by browsers. If your frontend app running on `http://localhost:3000` attempts to fetch data from your backend API running on `http://localhost:8080`, the browser blocks it unless the server responds with headers indicating that the origin is allowed.

In Javalin, CORS is configured via the built-in plugins configuration:

```java
Javalin app = Javalin.create(config -> {
    config.bundledPlugins.enableCors(cors -> {
        // Allow requests from all origins (useful for local development)
        cors.addRule(it -> {
            it.anyHost();
        });
        
        // Or restrict to a specific origin
        // cors.addRule(it -> {
        //     it.allowHost("http://localhost:3000");
        // });
    });
});
```

### Serving Static Files
If you are building a simple app where the server itself hosts the frontend code, you can tell Javalin to serve files from your project's resource folders.

```java
Javalin app = Javalin.create(config -> {
    // Serve files located in src/main/resources/public under the root URL "/"
    config.staticFiles.add("/public");
});
```
With this setup, if a user accesses `http://localhost:8080/index.html`, Javalin looks inside `src/main/resources/public/index.html` and serves it.

### Common Plugins
Javalin allows registering custom plugins or using bundled plugins to extend functionality. Some common bundles are:
-   `config.bundledPlugins.enableDevLogging()`: Logs extensive detail about incoming requests and outgoing responses (useful for local debugging, but should be disabled in production).
-   `config.bundledPlugins.enableRouteOverview("/routes")`: Generates an HTML overview page mapping all registered API routes.

---

## Code Example

Here is a complete example class demonstrating a fully configured Javalin application entry point.

```java
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class ConfiguredApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            // 1. Enable Developer Request/Response Logging
            config.bundledPlugins.enableDevLogging();

            // 2. Enable CORS rules
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> {
                    rule.anyHost(); // Allow all origins
                });
            });

            // 3. Serve Static Files from the classpath (src/main/resources/static)
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";                   // Web path prefix
                staticFiles.directory = "/static";              // Resource folder path
                staticFiles.location = Location.CLASSPATH;      // Resource location type
            });

            // 4. Generate Route Overview endpoint
            config.bundledPlugins.enableRouteOverview("/routes");
        });

        // Start the application on port 7070
        app.start(7070);

        // A sample endpoint
        app.get("/api/hello", ctx -> ctx.result("Hello from the API!"));
    }
}
```

---

## Summary
-   Javalin is configured by passing a configuration block into the `Javalin.create()` builder.
-   **CORS** rules must be enabled on the backend to allow frontend client applications running on different ports or domains to consume API endpoints.
-   **Static Files** configuration enables hosting frontend HTML, CSS, and JS assets directly from Javalin, bypassing the need for separate hosting during simple deployments.
-   Built-in plugins like **Dev Logging** and **Route Overview** speed up developer debugging cycles during API building.

---

## Additional Resources
-   [Javalin Documentation: Configuration](https://javalin.io/documentation#configuration)
-   [Javalin Documentation: Static Files](https://javalin.io/documentation#static-files)
-   [MDN Web Docs: Cross-Origin Resource Sharing (CORS)](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
