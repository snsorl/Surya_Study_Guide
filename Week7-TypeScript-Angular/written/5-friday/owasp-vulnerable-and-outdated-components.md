# OWASP A06: Vulnerable and Outdated Components

## Learning Objectives
- Define the risks associated with OWASP A06: Vulnerable and Outdated Components.
- Use `npm audit` to detect dependency vulnerabilities in client projects.
- Describe strategies to patch, update, and manage dependencies.
- Identify how to locate vulnerabilities inside CVE databases.

---

## Why This Matters
Modern web applications rely on dozens of external packages (like Angular libraries, styling frameworks, or parsing utilities). If you build your application using an outdated library that has a known security vulnerability, hackers can exploit it to steal data or compromise users. The **OWASP Top 10** identifies this risk as **A06: Vulnerable and Outdated Components**. As developers, we must monitor and update our dependency trees to keep our applications secure.

---

## The Concept

### 1. The Risk
- **Dependency Chains**: Installing a single library often pulls in dozens of sub-dependencies. If any sub-dependency contains a vulnerability, your application is exposed.
- **Vulnerabilities**: Common package bugs include Cross-Site Scripting (XSS) in HTML templates, Prototype Pollution in object parsers, and Denial of Service (DoS) in text parsers.

### 2. Auditing Dependencies (`npm audit`)
The npm package manager includes built-in security auditing tools. Whenever you install packages or run an audit command, it scans your dependency tree against the GitHub Advisory Database:

```bash
# Run security scan
npm audit
```
This command outputs a detailed report listing:
- Vulnerable packages.
- Severity levels (Low, Moderate, High, Critical).
- The dependency path showing which library pulled it in.
- The recommended patch version.

### 3. Patching and Resolving Issues
- **Automated Fixes**: To automatically install patched versions of vulnerable dependencies, run:
  ```bash
  npm audit fix
  ```
- **Major Updates**: If a vulnerability requires updating to a new major version, run:
  ```bash
  npm audit fix --force
  ```
  *Caution:* Major version updates can introduce breaking changes, so always run your unit tests to verify behavior afterward.
- **Manual Overrides**: If a sub-dependency is abandoned and cannot be updated automatically, you can force npm to resolve to a patched version using the `overrides` field in your `package.json` file.

---

## Summary
- **OWASP A06** highlights the security risks of building applications using outdated or vulnerable libraries.
- Run **`npm audit`** to scan your project's dependency tree for known security vulnerabilities.
- Resolve vulnerabilities using **`npm audit fix`** to install safe patch versions automatically.
- Always review major updates carefully, as they can introduce breaking changes that require code adjustments.

---

## Additional Resources
- [OWASP: Vulnerable and Outdated Components guide](https://owasp.org/Top10/A06_2021-Vulnerable_and_Outdated_Components/)
- [GitHub Advisory Database Search Tool](https://github.com/advisories)
