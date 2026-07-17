# Exercise: Security Code Review – OWASP Broken Access Control

## Objective
Audit a legacy Javalin REST API source code snippet to identify three distinct Broken Access Control vulnerabilities (OWASP Top 10 - A01). You will compile a security report detailing the vulnerable endpoints, the nature of the security flaws, a Proof of Concept (PoC) exploit scenario for each, and the concrete changes required to secure them.

---

## Scenario
You are working as a security engineer auditing an online academic portal. The grading backend was built quickly and contains severe access control flaws. A student logging into the portal can view, alter, or delete records belonging to other students or administrators because the system relies solely on clients sending "honest" inputs. You must audit this source code and outline how to secure it using proper server-side authentication and authorization context verification.

---

## References
- [OWASP Broken Access Control in Spring REST](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/2-tuesday/owasp-broken-access-control.md)

---

## Code Snippet to Audit

Analyze the following endpoints defined in a legacy grading API:

```java
import io.javalin.Javalin;
import io.javalin.http.Context;

public class GradingApi {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        // Retrieve a specific grade report
        app.get("/grades/{reportId}", GradingApi::getGradeReport);

        // Update a student's grade score (Instructor only)
        app.put("/grades/{reportId}", GradingApi::updateGradeScore);

        // Delete a student grade record
        app.delete("/grades/delete", GradingApi::deleteGradeRecord);
    }

    private static void getGradeReport(Context ctx) {
        String reportId = ctx.pathParam("reportId");
        // Fetches from database and returns grade report JSON
        GradeReport report = Database.findReportById(reportId);
        ctx.json(report);
    }

    private static void updateGradeScore(Context ctx) {
        String reportId = ctx.pathParam("reportId");
        int newScore = Integer.parseInt(ctx.queryParam("score"));
        String role = ctx.header("X-User-Role"); // Expecting Admin or Instructor

        if (role != null && (role.equals("Instructor") || role.equals("Admin"))) {
            Database.updateScore(reportId, newScore);
            ctx.status(200).result("Grade updated successfully.");
        } else {
            ctx.status(403).result("Access Denied");
        }
    }

    private static void deleteGradeRecord(Context ctx) {
        String reportId = ctx.queryParam("reportId");
        String userId = ctx.header("X-User-Id"); // Logged-in user's ID
        
        // Delete record from database
        Database.deleteReport(reportId);
        ctx.status(200).result("Record deleted.");
    }
}
```

---

## Core Tasks

### 1. Identify the Vulnerabilities
Examine the three endpoints above carefully. Identify three distinct Broken Access Control vulnerabilities (such as Insecure Direct Object References, parameter tampering, header spoofing, or missing access checks).

### 2. Document the Security Audit Report
Write a markdown report (`security-audit-report.md`) detailing each vulnerability with the following structure:
- **Vulnerability name & Endpoint route**: Define the HTTP method and path.
- **Flaw Description**: Explain why the code is unsafe.
- **Proof of Concept (PoC) Exploit**: Walk through how an attacker could exploit this endpoint using terminal tools (like `curl`) or Postman.
- **Remediation Plan**: Detail how to fix it on the backend. Provide secure code examples or security config concepts (e.g. leveraging user identity context, validating path params against session principal, signature verification).

---

## Definition of Done
Your submitted audit report must clearly cover:
- **Flaw 1 (GET `/grades/{reportId}`)**: Lack of owner-matching validation (IDOR).
- **Flaw 2 (PUT `/grades/{reportId}`)**: Reliance on user-provided request headers (`X-User-Role`) for authorization checks (Privilege Escalation via Header Spoofing).
- **Flaw 3 (DELETE `/grades/delete`)**: Missing authentication and route validation checks altogether; any requests (even anonymous ones) can wipe database records.
- Comprehensive code snippets showing how to implement correct access controls.
