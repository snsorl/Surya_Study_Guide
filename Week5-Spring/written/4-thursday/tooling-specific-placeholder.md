# Tooling Specific Notes: Enterprise Spring Framework

## Learning Objectives
- Configure enterprise Java development kits (JDKs) and environment path variables.
- Configure enterprise database drivers and database tool connections inside IDE environments.
- Troubleshoot local classpath issues and project sync compilation blocks.

---

## Why This Matters
Building Spring applications requires a suite of local tooling—such as Maven build systems, specific JDK runtimes, database connections, and IDE plugins. If your local tools are not configured correctly (e.g. your IDE targets JDK 11 while your Maven project requires JDK 17), you will encounter compile errors and project sync failures before you can even run your code. Having a clear checklist for configuring and verifying local tool chains is essential to maintaining developer productivity.

---

## The Concept

### Developer Toolchain Checklist

To build and run Spring Boot applications, verify that your machine has the following tools installed and configured:

```
[ JDK 17 / JDK 21 ] ──► [ Maven / Gradle Build ] ──► [ IDE (IntelliJ IDEA) ] ──► [ database (PostgreSQL) ]
```

---

### Core Tooling Configurations

#### 1. Java Development Kit (JDK 17+)
Ensure your system path environment references JDK 17 or higher.
-   **Verification (Terminal):**
    ```bash
    java -version
    javac -version
    ```
-   **Configuring in IDE:** Ensure the project SDK setting in your IDE matches your system's Java version. In IntelliJ, configure this in **Project Structure (Ctrl+Alt+Shift+S) $\rightarrow$ Project $\rightarrow$ SDK**.

#### 2. Apache Maven Build System
Maven is the standard tool used to compile, package, and execute Spring applications.
-   **Verification (Terminal):**
    ```bash
    mvn -version
    ```
-   **Maven Lifecycle Actions:**
    -   `mvn clean`: Deletes the target directory (clears compiled classes).
    -   `mvn compile`: Compiles the source code.
    -   `mvn test`: Runs unit tests.
    -   `mvn package`: Compiles and packages compiled code into executable JAR files.

#### 3. Database Connection Tooling (IntelliJ Database Tool)
Instead of switching between your IDE and terminal to query tables, you can connect to PostgreSQL directly from IntelliJ:
1.  Open the **Database** tool window (usually on the right sidebar).
2.  Click the **`+`** icon $\rightarrow$ **Data Source** $\rightarrow$ **PostgreSQL**.
3.  Enter host details (`localhost`), port (`5432`), user (`postgres`), password, and database name.
4.  Click **Test Connection** (IntelliJ will download database drivers automatically if they are missing).

---

## Summary
-   Ensure **JDK 17+** and **Maven** are installed and correctly configured in your system environment path.
-   Match your **IDE SDK settings** to the Java compiler target version specified in `pom.xml`.
-   Use **Maven commands** (`clean`, `compile`, `package`) to manage the application build lifecycle.
-   Configure the **IntelliJ Database tool** to inspect database tables and query SQL data without leaving the editor.

---

## Additional Resources
-   [OpenJDK Download Site](https://openjdk.org/)
-   [Apache Maven Official Download Page](https://maven.apache.org/download.cgi)
-   [IntelliJ IDEA: Database Tool Window Guide](https://www.jetbrains.com/help/idea/database-tool-window.html)
