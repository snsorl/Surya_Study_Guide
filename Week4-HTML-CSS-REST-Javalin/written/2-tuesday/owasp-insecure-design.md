# OWASP A04: Insecure Design

## Learning Objectives
- Define OWASP A04: Insecure Design and distinguish it from implementation defects.
- Explain the principles of Security-by-Design and Threat Modeling.
- Identify design-level vulnerabilities in business logic controls.
- Propose secure design alternatives to prevent exploitation.

---

## Why This Matters
You can write the most elegant, bug-free, perfectly unit-tested code, and still build an application that is fundamentally insecure. This occurs when security failures happen at the *design* stage rather than the *coding* stage. For example, if your e-commerce application allows users to request refunds without verifying they purchased the item, the code itself might run without throwing exceptions, but the business logic is broken. Understanding insecure design helps you think like an attacker early in the software development lifecycle, saving weeks of refactoring later.

---

## The Concept

### What is Insecure Design?
**Insecure Design** refers to vulnerabilities that arise because security requirements and defensive measures were not factored into the application's architecture from the beginning. 

It is important to separate design flaws from implementation defects:
-   **Implementation Defect**: The design was secure, but the developer wrote buggy code that introduced a vulnerability (e.g., forgetting to parameterize a query, leading to SQL Injection).
-   **Design Flaw**: The developer wrote the code exactly as designed, but the design itself lacked the logical checks to prevent abuse (e.g., allowing a user to set their password to blank, or failing to add rate limits to a password recovery endpoint).

A secure implementation cannot fix an insecure design.

### Security-by-Design Principles
To prevent insecure design, software teams must adopt a **Security-by-Design** philosophy. Core principles include:

1.  **Principle of Least Privilege**: Users and services should only have the minimum access rights necessary to perform their tasks.
2.  **Defense in Depth**: Do not rely on a single layer of security. If your firewall fails, your application authentication should still block access; if that fails, your database encryption should protect data.
3.  **Secure Defaults**: Out-of-the-box settings should always be secure. For example, new accounts should default to standard permissions, not administrator permissions.
4.  **Keep it Simple**: Complex security configurations are difficult to maintain and prone to configuration errors.

### Threat Modeling
Threat modeling is a structured process where developers identify potential threats, security gaps, and mitigations during the design phase. It asks four core questions:
1.  What are we building? (System architecture diagrams)
2.  What can go wrong? (Identify threat vectors like spoofing, tampering, or data leakage)
3.  What are we going to do about it? (Implement mitigations like encryption or authentication)
4.  Did we do a good job? (Verify and test)

### Examples of Insecure Design in APIs

#### Example 1: Missing Rate Limits
-   *The Design*: A password reset page sends a 6-digit PIN to a user's phone. The API validates the PIN.
-   *The Flaw*: The design does not limit the number of attempts a user can make to submit the PIN. An attacker can write a script to brute-force all 1,000,000 combinations (000000 to 999999) in minutes and hijack any account.
-   *Secure Redesign*: The system invalidates the PIN after 3 failed attempts, rate-limits submissions by IP address, and expires the PIN after 10 minutes.

#### Example 2: Insecure Direct Object References (IDOR) by Design
-   *The Design*: A clinic API retrieves patient reports using an auto-incrementing integer key: `/api/reports/104`.
-   *The Flaw*: The API assumes that knowing the ID is equivalent to being authorized to view the report. Anyone can guess `/api/reports/105` to view another patient's data.
-   *Secure Redesign*: The system uses non-guessable identifiers (like UUIDs) AND explicitly queries the session context to verify if the requesting user is the owner of the record before returning data.

---

## Code Example

Here is a conceptual illustration of an insecure business design in a banking application versus a secure design.

### Insecure Design: Transfer Funds
```java
// Insecure Fund Transfer Design
app.post("/transfer", ctx -> {
    int fromAccount = Integer.parseInt(ctx.formParam("fromAccount"));
    int toAccount = Integer.parseInt(ctx.formParam("toAccount"));
    double amount = Double.parseDouble(ctx.formParam("amount"));

    // VIOLATION: The design trusts the user-supplied 'fromAccount' input 
    // without validating if the logged-in user owns that account!
    bankService.moveMoney(fromAccount, toAccount, amount);
    ctx.result("Transfer successful.");
});
```

### Secure Design: Transfer Funds
```java
app.post("/transfer", ctx -> {
    // Get identity from secure session context, NOT from request body inputs
    User currentUser = ctx.sessionAttribute("currentUser");
    if (currentUser == null) {
        throw new UnauthorizedResponse("Must log in first.");
    }
    
    int toAccount = Integer.parseInt(ctx.formParam("toAccount"));
    double amount = Double.parseDouble(ctx.formParam("amount"));
    
    // Prevent invalid negative transfers
    if (amount <= 0.0) {
        throw new BadRequestResponse("Transfer amount must be positive.");
    }

    // Resolve owner account securely on the server
    int fromAccount = accountService.getPrimaryAccount(currentUser.getId());

    // Execute transfer
    bankService.moveMoney(fromAccount, toAccount, amount);
    ctx.result("Transfer completed.");
});
```

---

## Summary
-   **OWASP A04: Insecure Design** deals with logical gaps in system architecture, which differ from coding implementation bugs.
-   Applying **Security-by-Design** and **Threat Modeling** helps developers catch design-level issues before code is written.
-   Common API design flaws include missing rate limiting, insecure direct object references, and trusting client-provided identity variables.
-   Always authenticate, restrict scope using least privilege, validate logical parameters, and enforce secure business logic checks on the server.

---

## Additional Resources
-   [OWASP Top 10: Insecure Design](https://owasp.org/Top10/A04_2021-Insecure_Design/)
-   [OWASP Threat Modeling](https://owasp.org/www-community/Application_Threat_Modeling)
-   [CISA: Security by Design Principles](https://www.cisa.gov/resources-tools/resources/secure-by-design)
