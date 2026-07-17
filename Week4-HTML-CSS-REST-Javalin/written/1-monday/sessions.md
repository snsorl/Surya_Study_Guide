# Session Management in Javalin

## Learning Objectives
- Manage stateful sessions in Javalin using `ctx.sessionAttribute()`.
- Compare stateful (session-based) and stateless (token-based) application designs.
- Explain the risks of session hijacking and session management failures.
- Preview stateless authentication concepts using JSON Web Tokens (JWT).

---

## Why This Matters
By default, HTTP is stateless, meaning the server forgets who the client is as soon as a request completes. To build applications where users can log in, view persistent shopping carts, or save preferences, you must implement state management. While stateful session attributes are quick and easy to configure for simple backend-rendered apps, modern distributed systems often favor stateless token-based designs. Understanding how sessions work—and their architectural trade-offs—is critical to selecting the correct security and scaling patterns for your applications.

---

## The Concept

### Managing Sessions in Javalin
Javalin provides native session management capabilities. These are wrapper layers around Jetty's standard servlet session handling mechanisms. When a user requests to establish session state:

1.  The server generates a unique **Session ID** (a long string of characters).
2.  The server stores this Session ID in an in-memory data store on the server, mapping it to custom session attributes.
3.  The server returns the Session ID to the client's browser inside a cookie named `JSESSIONID` in the `Set-Cookie` response header.
4.  The browser automatically attaches this cookie to every subsequent request, enabling the server to recognize the user.

```java
// Set session attribute
ctx.sessionAttribute("currentUser", username);

// Get session attribute
String user = ctx.sessionAttribute("currentUser");

// Invalidate session (logout)
ctx.req().getSession().invalidate();
```

### Stateful vs. Stateless Design
There are two main paradigms for keeping track of user state:

#### 1. Stateful Design (Session-Based)
The server stores session information in memory or in a shared database. The client only stores the Session ID in a cookie.
-   **Pros**: Highly secure because actual session payload data resides on the server; easy to invalidate sessions instantly from the server.
-   **Cons**: Scaling is difficult. If you scale to three server instances, you must implement sticky sessions or a shared database (like Redis) so that server 2 can access sessions created on server 1.

#### 2. Stateless Design (Token-Based)
The server does not store session records. Instead, when a user logs in, the server generates an encrypted/signed token containing the session data (like a JWT) and sends it to the client. The client sends this token in the `Authorization` header of subsequent requests. The server validates the token cryptographically on each request.
-   **Pros**: Extremely scalable. Any server instance can validate the token without database queries or shared memory.
-   **Cons**: Tokens are harder to revoke before their natural expiration date.

### JWT Preview
JSON Web Tokens (JWT) are a modern standard for stateless authentication. Instead of storing data on the server database, the user data is stored inside a base64-encoded JSON object that is signed by the server's private key.
-   *Note*: We will cover token-based authentication and JWT implementations in depth in a later module. For now, understand that JWTs eliminate server-side session memory entirely.

---

## Code Example

Here is a simple example showing how to track login state in Javalin using stateful session attributes.

```java
import io.javalin.Javalin;
import io.javalin.http.UnauthorizedResponse;

public class SessionApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        // Login endpoint (establishes session)
        app.post("/login", ctx -> {
            String username = ctx.queryParam("username");
            if (username == null || username.isBlank()) {
                ctx.status(400).result("Username required");
                return;
            }
            
            // Save username in server session state
            ctx.sessionAttribute("currentUser", username);
            ctx.result("Welcome, " + username + "! Session established.");
        });

        // Protected endpoint (reads session)
        app.get("/dashboard", ctx -> {
            // Retrieve session attribute
            String username = ctx.sessionAttribute("currentUser");
            
            if (username == null) {
                // Return 401 if session state is missing
                throw new UnauthorizedResponse("You must log in first.");
            }
            
            ctx.result("Dashboard details for: " + username);
        });

        // Logout endpoint (invalidates session)
        app.post("/logout", ctx -> {
            // Invalidate Jetty's underlying HTTP session
            ctx.req().getSession().invalidate();
            ctx.result("Logged out successfully.");
        });
    }
}
```

---

## Summary
-   Javalin utilizes Jetty's embedded session handling, managed via **`ctx.sessionAttribute()`**.
-   **Stateful design** maps a client cookie `JSESSIONID` to backend memory; it is secure but introduces scaling challenges.
-   **Stateless design** bypasses server session stores using client-provided tokens (like **JWTs**) which are verified cryptographically.
-   Securing session tokens and cookies is a vital responsibility when designing backend APIs.

---

## Additional Resources
-   [OWASP Session Management Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html)
-   [Javalin Documentation: Request Context](https://javalin.io/documentation#context)
-   [Introduction to JSON Web Tokens (JWT.io)](https://jwt.io/introduction)
