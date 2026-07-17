# OWASP A02: Cryptographic Failures & Password Hashing

## Learning Objectives
- Define Cryptographic Failures (OWASP Top 10 Category A02).
- Identify common cryptographic vulnerabilities, including plaintext password storage and weak hashing algorithms (MD5, SHA-1).
- Implement secure password hashing using BCrypt or Argon2.
- Configure HTTPS enforcement and secure key management in Spring Boot applications.

---

## Why This Matters
If an attacker breaches your database and your application stores user passwords in plaintext, the attacker instantly gains access to every user account. To make matters worse, since users frequently reuse passwords across different websites, a breach on your system can compromise their accounts on other platforms. Storing passwords securely requires using one-way cryptographic hashing. However, using old or weak algorithms (like MD5 or SHA-1) is no longer safe; modern GPUs can crack millions of MD5 hashes per second. Mastering secure password hashing using BCrypt or Argon2 is a fundamental requirement for securing user data.

---

## The Concept

### What are Cryptographic Failures?
**Cryptographic Failures** (previously known as "Sensitive Data Exposure") occur when an application does not properly protect data in transit (over the network) or at rest (in storage).

Common cryptographic failures include:
1.  **Plaintext Storage**: Storing passwords, credit cards, or PII in clear text in databases or logs.
2.  **Weak Algorithms**: Using outdated hashing or encryption algorithms (MD5, SHA-1, DES).
3.  **Key Management Failures**: Hardcoding encryption keys in source code or committing configuration files containing secrets to Git repositories.
4.  **No HTTPS**: Transmitting sensitive data over unencrypted HTTP channels.

---

### Secure Hashing: One-Way and Slow
Encryption is two-way: you encrypt data using a key, and can decrypt it back using the same key.
Password hashing is **one-way**: you run a password through a hashing algorithm to generate a hash string. The hash cannot be reversed to reveal the original password. When a user logs in, the application hashes the incoming password and compares it against the stored hash.

To protect against brute-force attacks, password hashing algorithms must be **intentionally slow**.
-   **BCrypt**: Uses a configurable cost factor (work factor) to determine how many hashing rounds to execute. This slows down brute-force attacks, making them computationally expensive.
-   **Argon2**: The winner of the Password Hashing Competition. It allows developers to configure memory usage, time complexity, and thread counts to resist GPU/ASIC hardware cracking attacks.

```
Incoming Password ──► [ BCrypt Hashing (with Salt) ] ──► Compares to ──► [ Database Hash ]
```

#### The Role of Salt
A **Salt** is a random string added to the password before hashing. Salting ensures that even if two users choose the exact same password (e.g. `Password123`), their database hash entries are completely different. This protects against **Rainbow Table** attacks (pre-computed lists of common password hashes).

---

### Key Management Best Practices
-   Never commit database passwords or encryption keys to GitHub.
-   Use environment variables or secure vault services (like HashiCorp Vault or AWS Secrets Manager) to load credentials at runtime.
-   In Spring Boot, reference environment variables in `application.yml` using fallback defaults:
    ```yaml
    app:
      security:
        encryption-key: ${ENCRYPTION_KEY:fallbackDeveloperKeyOnly}
    ```

---

## Code Example: BCrypt Hashing with Spring Security

Spring Security provides the `PasswordEncoder` interface. The default standard implementation is **`BCryptPasswordEncoder`**.

### 1. Declare the Password Encoder Bean
```java
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Enforce a work factor strength of 12 (default is 10)
        return new BCryptPasswordEncoder(12);
    }
}
```

### 2. Hash Passwords on User Registration
```java
package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String rawPassword) {
        // SECURE: Hash the password before saving to database
        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword); // Never store rawPassword!

        return userRepository.save(user);
    }
}
```

### 3. Verify Passwords during Login
```java
package com.example.service;

public class LoginService {
    // ... repository and passwordEncoder references

    public boolean authenticate(String username, String incomingRawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }

        // SECURE: Use matches() to compare the raw incoming password against the hashed DB password
        // NEVER compare hashes using simple string equality checking (e.g. hashA == hashB)
        // because matches() handles timing-attack vulnerabilities.
        return passwordEncoder.matches(incomingRawPassword, user.getPasswordHash());
    }
}
```

---

## Summary
-   **Cryptographic Failures** (OWASP A02) expose sensitive data through plaintext storage or weak algorithms.
-   **BCrypt** and **Argon2** are slow, one-way hashing algorithms that resist brute-force and GPU cracking attacks.
-   **Salts** prevent rainbow table attacks by ensuring identical passwords produce completely different hash strings.
-   Manage secrets securely by using environment variables instead of hardcoding keys in source files.

---

## Additional Resources
-   [OWASP Top 10: Cryptographic Failures (A02)](https://owasp.org/Top10/A02_2021-Cryptographic_Failures/)
-   [Spring Security Reference Guide: Password Storage](https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html)
-   [Baeldung: Spring Security Password Hashing Guide](https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt)
