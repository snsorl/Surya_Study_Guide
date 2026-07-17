# Cohort-Specific API Tooling Notes

## Learning Objectives
- Access and update local cohort-specific configurations for API development.
- Document workspace adjustments for specialized testing utilities.
- Maintain environment properties specific to individual trainee setups.

---

## Why This Matters
While standard protocols (like HTTP and REST) are universal, the exact tools used to inspect, run, and host APIs can vary across teams and training cohorts. Some groups utilize Visual Studio Code REST Client scripts, others favor Command-Line utilities, and some require custom environment variables to connect to corporate proxies. This document serves as a living placeholder to record modifications and custom setup steps agreed upon by your instructor and team.

---

## The Concept

### Cohort Tooling Adjustments
When building your full-stack applications, keep this file updated with instructions for any custom developer tools introduced during your cohort. Examples of topics to document here include:
-   **Alternate API Clients**: Documentation for tools like *Insomnia*, *Thunder Client*, or VS Code `http` request extensions.
-   **Local Proxy Configurations**: Settings required to run backend API servers behind firewall systems.
-   **Custom Database Clients**: Notes on integrating with graphical clients (like DBeaver or pgAdmin) to check backend data changes alongside API testing.

---

## Code Example

Here is an example format you can use to document a custom VS Code REST Client script, which acts as a lightweight alternative to Postman files.

```http
# Save this file as api-tests.http in your project root
# Send Request can be clicked directly inside VS Code with the REST Client extension.

@baseUrl = http://localhost:8080/api/v1

### Get all users
GET {{baseUrl}}/users
Accept: application/json

### Create a new user
POST {{baseUrl}}/users
Content-Type: application/json

{
  "username": "cohortmember",
  "email": "member@example.com"
}
```

---

## Summary
-   This page is a **placeholder** for custom tooling notes, proxy configurations, or testing scripts unique to your training group.
-   Update this file as you discover environment-specific solutions to API development hurdles.

---

## Additional Resources
-   [VS Code REST Client Extension](https://marketplace.visualstudio.com/items?itemName=humao.rest-client)
-   [Insomnia Rest Client](https://insomnia.rest/)
