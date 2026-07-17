# OWASP A01: Broken Access Control in Spring REST APIs

## Learning Objectives
- Define Broken Access Control (OWASP Top 10 Category A01).
- Identify common access control vulnerabilities in REST endpoints, including missing checks and Insecure Direct Object References (IDOR).
- Code defensively to protect endpoints using user context checks.
- Preview how Spring Security uses annotations (like `@PreAuthorize`) to enforce access boundaries.

---

## Why This Matters
Broken Access Control is the #1 security risk on the OWASP Top 10. Access control rules ensure that users cannot act outside of their intended permissions. When access control is broken, attackers can view, modify, or delete sensitive data belonging to other users, or even escalate their privileges to administrator status. As a backend developer building REST APIs with Spring, you are the gatekeeper. Security cannot rely solely on the frontend hiding buttons; you must enforce access controls on every single API endpoint.

---

## The Concept

### What is Broken Access Control?
**Broken Access Control** occurs when an application does not properly enforce boundaries between what authenticated users are allowed to do. 

Common examples of broken access control include:
1.  **Horizontal Privilege Escalation (IDOR)**: A user accesses resources belonging to another user of the same privilege tier (e.g., User A changes the URL ID parameter to view User B's invoice).
2.  **Vertical Privilege Escalation**: A standard user accesses administrative endpoints by calling the URL directly (e.g. hitting `/api/admin/users/delete` without administrative rights).
3.  **Missing Endpoint Protection**: Exposing REST endpoints to the internet without verifying if the caller is authenticated.

---

### Insecure Direct Object References (IDOR)
An **Insecure Direct Object Reference (IDOR)** is a subtype of broken access control where an application uses a direct database key or record identifier as a parameter in a REST URL, but fails to check if the authenticated caller actually owns that record.

```
Attacker User-123  ── GET /api/accounts/999 (User-999's Account) ──>  [ Spring Controller ]
                                                                             │
  [ Vulnerable Controller: Fetches and returns record 999 directly! ] <──────┘
```

#### Defending Against IDOR
To secure endpoints against IDOR, you must:
1.  Verify that the user is authenticated.
2.  Retrieve the authenticated user's ID from the secure session/token context (e.g., `Principal` or `Authentication` object).
3.  Compare the authenticated user's ID against the requested resource owner, or query the database using the user's ID as a filter constraint (e.g., `SELECT * FROM accounts WHERE id = ? AND owner_id = ?`).

---

### Enforcing Access Boundaries in Spring
While we can write manual conditional validation checks inside our controllers, the standard enterprise solution is to use **Spring Security**.

Spring Security provides declarative annotation-based checks that intercept execution flows before they reach your controller methods:

-   **`@PreAuthorize`**: Evaluates expression logic before invoking the method.
-   **`hasRole('ROLE_ADMIN')`**: Restricts access based on user authorization groups.
-   **`#userId == authentication.principal.id`**: Compares path parameters to the authenticated caller's identity.

```java
// Prevents horizontal access breaches
@GetMapping("/users/{userId}/profile")
@PreAuthorize("#userId == authentication.principal.id")
public UserProfile getProfile(@PathVariable String userId) {
    return profileService.getProfileByUserId(userId);
}
```

---

## Code Example: Fixing an IDOR Vulnerability

Let's look at a vulnerable endpoint and how to secure it using defensive programming.

### The Vulnerable Implementation (Horizontal Privilege Escalation)
```java
package com.example.vulnerable;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class VulnerableInvoiceController {
    private final InvoiceRepository invoiceRepository;

    public VulnerableInvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    // VULNERABLE: An attacker logged in as user '101' can request '/api/invoices/909'
    // to view user '909's invoice details!
    @GetMapping("/{invoiceId}")
    public Invoice getInvoice(@PathVariable int invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }
}
```

### The Secured Implementation (Defensive Ownership Verification)
```java
package com.example.secured;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/invoices")
public class SecuredInvoiceController {
    private final InvoiceRepository invoiceRepository;
    private final UserSessionContext userSessionContext; // Mock authenticated user accessor

    public SecuredInvoiceController(InvoiceRepository invoiceRepository, UserSessionContext userSessionContext) {
        this.invoiceRepository = invoiceRepository;
        this.userSessionContext = userSessionContext;
    }

    @GetMapping("/{invoiceId}")
    public Invoice getInvoice(@PathVariable int invoiceId) {
        // 1. Retrieve resource
        Invoice invoice = invoiceRepository.findById(invoiceId);
        if (invoice == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found.");
        }

        // 2. Fetch authenticated caller credentials
        int currentUserId = userSessionContext.getCurrentUserId();

        // 3. SECURE CHECK: Enforce record ownership boundary
        if (invoice.getOwnerUserId() != currentUserId) {
            // Throw 403 Forbidden to block access
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. You do not own this resource.");
        }

        return invoice;
    }
}
```

---

## Summary
-   **Broken Access Control** (OWASP A01) is the failure to enforce user permission boundaries on the server.
-   **IDOR (Insecure Direct Object Reference)** occurs when an application exposes raw database keys in URLs without verifying ownership.
-   Mitigate IDOR by querying data using the authenticated user's ID as a filter constraint or performing explicit ownership checks.
-   **Spring Security** provides annotations like `@PreAuthorize` to declaratively restrict methods based on user roles and identities.

---

## Additional Resources
-   [OWASP Top 10: Broken Access Control (A01)](https://owasp.org/Top10/A01_2021-Broken_Access_Control/)
-   [Spring Security Reference Guide: Method Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html)
-   [Baeldung: Guide to Spring Security Method Security](https://www.baeldung.com/spring-security-method-security)
