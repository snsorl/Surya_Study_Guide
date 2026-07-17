# OWASP A07: Identification and Authentication Failures

## Learning Objectives
- Define the OWASP A07 security category and its common vulnerabilities.
- Identify common authentication attack vectors: credential stuffing, weak passwords, session fixation, and missing Multi-Factor Authentication (MFA).
- Configure remediation patterns in Java Spring Security to prevent credential stuffing and brute-force attacks.
- Detail secure session management and JWT signature verification best practices.

---

## Why This Matters
Authentication is the gatekeeper of your application. If your authentication system is flawed, attackers can compromise user accounts, steal private profiles, and access administrative dashboards.

In the OWASP Top 10, **Identification and Authentication Failures (A07)** covers vulnerabilities that allow attackers to bypass login forms or hijack active user sessions. As you deploy Spring Boot REST APIs to production cloud platforms, protecting authentication systems with robust security configurations, session management, and rate limiting is critical.

---

## The Concept

### 1. Common Authentication Vulnerabilities

#### Credential Stuffing
Attackers use automated scripts to test millions of leaked username/password combinations (obtained from past breaches of other websites) against your application's login endpoint. Because users frequently reuse passwords across multiple sites, a percentage of these attempts succeed.

#### Session Fixation
An attacker generates a valid session ID on your web application and forces the victim's browser to use it (often via URL parameters or custom headers). When the victim logs in, the server elevates the privileges of that session ID. Because the attacker already knows the session ID, they can access the authenticated session.

#### Missing Multi-Factor Authentication (MFA)
Relying solely on passwords leaves accounts vulnerable to phishing, keylogging, and credential stuffing. MFA adds a required secondary verification code (e.g., TOTP or SMS code), securing accounts even if the password is leaked.

---

### 2. Spring Security Remediation Patterns
Spring Security provides built-in mechanisms to mitigate these risks:
- **Session Migration**: Configured via `.sessionFixation().newSession()` or `migrateSession()`. When a user authenticates, Spring Security automatically discards the old session ID, generates a new one, and copies the session attributes to prevent session fixation.
- **Login Rate Limiting**: Implementing IP and username-based throttling to block brute-force scripts.
- **JWT (JSON Web Token) Security**: Verifying signatures, checking token expiration times, and using cryptographically strong keys (like HMAC-SHA512) for stateless APIs.

---

## Code Examples and Walkthroughs

### 1. Mitigating Session Fixation in Spring Security
Configure session fixation protection within your security filter chain:

```java
package com.example.project3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Note: Enable CSRF for session-based cookie frontends
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                // Session Fixation Protection: Creates a new session on authentication
                .sessionFixation(fixation -> fixation.newSession())
                // Restrict a user to a single active concurrent session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
            );
            
        return http.build();
    }
}
```

### 2. Secure JWT Validation Filter
When using stateless tokens, you must validate the token signature and verify it has not expired on every request:

```java
package com.example.project3.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "YourUltraSecretCryptographicallySecureKeyForHMAC512MustBeAtLeast512BitsLong!!!";

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                
                // Parse and validate the JWT signature and expiration
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                Date expiration = claims.getExpiration();

                // Double check expiration time
                if (username != null && expiration.after(new Date())) {
                    UsernamePasswordAuthenticationToken auth = 
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                // If validation fails (expired or invalid signature), clear security context
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

---

## Summary
- **OWASP A07** focuses on flaws in credentials validation, session tracking, and user identification.
- **Session Fixation** is mitigated by forcing session recreation upon login (`newSession()` or `migrateSession()`).
- **Credential Stuffing** and brute-force attacks are mitigated by rate limiting, password strength verification, and MFA.
- **JWT Verification** requires checking token expiration, validating signature keys, and rejecting malformed tokens before granting resource access.

---

## Additional Resources
- [OWASP A07:2021 Identification and Authentication Failures](https://owasp.org/Top10/A07_2021-Identification_and_Authentication_Failures/)
- [Spring Security Reference Manual: Session Management](https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html)
- [RFC 7519: JSON Web Token (JWT) Standard](https://datatracker.ietf.org/doc/html/rfc7519)
