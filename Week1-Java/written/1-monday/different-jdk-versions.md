# Choosing a JDK Version: LTS vs. Non-LTS Releases

## Learning Objectives
- Explain the modern six-month Java release cycle.
- Differentiate between Long-Term Support (LTS) and non-LTS Java releases.
- Understand the support lifetimes and stability implications of major LTS versions (Java 8, 11, 17, 21).
- Identify key factors that enterprise projects use to select a JDK version and distribution.

---

## Why This Matters
For the first two decades of Java's history, major versions were released years apart (e.g., Java 6 in 2006, Java 7 in 2011, and Java 8 in 2014). This slow schedule meant massive changes were bundled into single updates, making upgrades difficult and risky for companies. 

To address this, Oracle shifted Java to a predictable **six-month release schedule**. While this brings new features to developers faster, it creates a new challenge: how do enterprises choose a Java version that is modern enough to be productive but stable and supported enough to run critical production environments? 

As a professional developer, understanding the difference between LTS and non-LTS releases is essential for making sound technical recommendations for your projects.

---

## The Concept

### The Modern Six-Month Release Cycle
Since March 2018 (beginning with Java 10), Oracle releases a new version of Java every six months—specifically in **March** and **September**. 

Because of this rapid release rate, Java versions are categorized into two main groups to accommodate different business risk profiles:

1. **Long-Term Support (LTS) Releases**: Releases that are designated for multi-year support. They are designed for maximum stability, security, and predictability. Enterprise software is built almost exclusively on LTS releases. A new LTS version is released every **2 years** (recently changed from every 3 years).
2. **Non-LTS (Feature) Releases**: Releases that come out in between LTS versions. They introduce new language features, preview APIs, and performance enhancements. These releases are supported for **only six months**. As soon as the next Java version is released, the previous non-LTS version reaches End-of-Life (EOL) and receives no further security patches.

```
       Sept 2021           Sept 2023           Sept 2025
        [Java 17]           [Java 21]           [Java 25]
          (LTS)               (LTS)               (LTS)
            |                   |                   |
            +----- Supported ---+                   v
            |     for Years     |
            v                   v
   [Java 18] -> [Java 19] -> [Java 20]  (Non-LTS, supported for 6 months each)
```

---

### Major Java Versions in the Enterprise
While new versions are released rapidly, a few key versions hold significant market share in the industry:

| Java Version | Release Date | Type | Status & Industry Notes |
| :--- | :--- | :--- | :--- |
| **Java 8 (LTS)** | March 2014 | LTS | **Legacy Cornerstone**. Introduced Lambda Expressions and Streams. Despite its age, a large volume of legacy enterprise software still runs on Java 8 because migration off it can be complex. |
| **Java 11 (LTS)** | September 2018 | LTS | **Modern Baseline**. Introduced modularity (from Java 9/10 cleanup), a new HTTP Client, and local-variable type inference (`var`). Many enterprise systems migrated from Java 8 to Java 11. |
| **Java 17 (LTS)** | September 2021 | LTS | **Current Standard**. The target runtime for modern frameworks like Spring Boot 3.x. Features major garbage collection upgrades, Sealed Classes, and Records. |
| **Java 21 (LTS)** | September 2023 | LTS | **Next-Gen standard**. Features Virtual Threads (Project Loom) for high-concurrency lightweight networking, and pattern matching enhancements. |

---

### How Enterprises Choose a Java Version
When choosing a version for a new project, developers and system architects weigh several factors:

1. **Support Lifecycle**: Running unsupported software in production is a massive security risk. Companies choose LTS versions because vendors (like Oracle, Red Hat, or Amazon) guarantee security updates and bug fixes for 5 to 10+ years.
2. **Framework & Tooling Compatibility**: Frameworks (like Spring Boot, Hibernate) and build tools (like Maven, Gradle) lag slightly behind Java releases. A project cannot upgrade its Java version until all of its third-party libraries support it.
3. **Cloud and Container Infrastructure**: If you deploy to AWS Lambda or Google App Engine, you must wait until those platforms support the specific Java runtime.
4. **License and Costs**:
   - **Oracle JDK**: Oracle's commercial build. While free for development and personal use, it requires a paid license for production use in some versions.
   - **OpenJDK Distributions** (Temurin, Corretto, etc.): Free and open-source under the GNU General Public License (GPL). These distributions provide the exact same functionality without licensing fees, making them the default choice for modern cloud environments.

---

## Example Scenario: Choosing Java 17 vs Java 20
Imagine you are starting a new banking microservice project:

*   **Option A: Java 20 (Non-LTS)**. You get access to the latest syntax features. However, six months later when Java 21 is released, Java 20 will stop receiving security updates. Your team would be forced to upgrade the production system every six months to stay secure. This is unacceptable for most enterprise environments.
*   **Option B: Java 17 (LTS)**. You get a robust, stable language runtime with guaranteed security updates and support until at least 2029. Popular frameworks like Spring Boot 3.0 require Java 17 as a minimum, making it a safe, future-proof choice.

**Verdict**: The team should choose **Java 17** (or Java 21 if the stack supports it) to balance modern features with long-term stability.

---

## Summary
- Java releases a new version every **six months** (March and September) to deliver features incrementally.
- **LTS (Long-Term Support)** versions are released every two years and receive updates for years, making them the standard choice for enterprises.
- **Non-LTS** versions are supported for only six months and should not be used in critical production environments.
- Common LTS baselines in the industry today are **Java 8**, **Java 11**, **Java 17**, and **Java 21**.
- Open-source JDK distributions (like Eclipse Temurin or Amazon Corretto) are widely used in enterprise cloud systems to avoid licensing costs.

---

## Additional Resources
- [Adoptium (Eclipse Temurin) Support Lifecycle](https://adoptium.net/support/)
- [Oracle Java SE Support Roadmap](https://www.oracle.com/java/technologies/java-se-support-roadmap.html)
- [The Evolution of Java Release Cadence - InfoQ](https://www.infoq.com/articles/java-release-cadence-evolution/)
