# Tooling-Specific Placeholder: Environment and AI Configuration

## Learning Objectives
- Configure environment variables on local workstations to store database credentials securely.
- Connect IntelliJ IDEA database tools to a local PostgreSQL server.
- Configure AI-assisted coding extensions (such as Gemini Code Assist) within local IDE workspaces.
- Secure local development configurations by preventing keys leak to source control.

---

## Why This Matters
To succeed in this cohort, you must have your local workstation configured correctly. While we discuss database engineering concepts theoretically, your day-to-day development work happens inside your IDE (IntelliJ IDEA) and database client (DBeaver).

If you do not set up these tools using the specific patterns designated for this cohort—such as using environment variable injections instead of static config files—your code will fail our automated grading systems and continuous integration build scripts. 

Furthermore, configuring your local AI code assistants to respect our code templates ensures that you generate compliant, standard, and secure Java and SQL scripts.

---

## The Concept

This document summarizes the specific configurations and integrations required for this cohort's local development environment.

### 1. Environment Variable Configuration
To support the connection factories we designed this week, you must register database credentials directly in your operating system's environment variables. This keeps them out of the source code.

Register the following variables:
-   `DB_URL`: `jdbc:postgresql://localhost:5432/postgres`
-   `DB_USER`: `postgres`
-   `DB_PASS`: *[Your PostgreSQL installation password]*

### 2. IntelliJ Database Tools Integration
While DBeaver is our primary database explorer client, IntelliJ IDEA Ultimate Edition contains a built-in Database Tool Window. Connecting it directly to PostgreSQL allows you to write queries, inspect schemas, and verify table mappings without leaving your IDE code editor.

### 3. AI Code Assistant Integration (Gemini Code Assist)
Our cohort utilizes Gemini Code Assist within the IDE. To get the best code generation outputs:
-   **Context Indexing:** Ensure your local workspace is indexed. This allows the AI assistant to read your `ConnectionFactory.java` and suggest JDBC code that utilizes it instead of writing raw `DriverManager` blocks.
-   **Output Constraints:** Always specify Java and database versions within your prompt logs to avoid syntax hallucinations (e.g., instructing the AI to use modern Java 17 features and PostgreSQL 14 syntax).

---

## Practical Setup Guide

### Step 1: Setting Environment Variables (Windows)

1.  Press `Win + R`, type `sysdm.cpl`, and press Enter.
2.  Go to the **Advanced** tab and click **Environment Variables**.
3.  Under **User Variables**, click **New** to add each variable:
    -   Variable: `DB_URL` | Value: `jdbc:postgresql://localhost:5432/postgres`
    -   Variable: `DB_USER` | Value: `postgres`
    -   Variable: `DB_PASS` | Value: *[Your Password]*
4.  Click **OK** to save. **Restart IntelliJ IDEA** to load the new variables into the IDE process environment.

---

### Step 2: Connecting IntelliJ IDEA to PostgreSQL

1.  On the right-side panel of IntelliJ, click the **Database** tool tab.
2.  Click the `+` icon -> **Data Source** -> **PostgreSQL**.
3.  In the General settings tab:
    -   Set Host: `localhost` | Port: `5432` | Database: `postgres` | User: `postgres`.
    -   Set Authentication type to **User & Password** and enter your password.
4.  Click **Test Connection**. If prompted to download drivers, click **Download**.
5.  Click **OK**. You can now view tables and write SQL queries inside your IDE SQL Console.

---

### Step 3: Run Configuration Environment Mapping
When running your JUnit 5 tests or main Java application inside IntelliJ, you must map the environment variables into the run configurations:

1.  Select **Run** -> **Edit Configurations** from the top menu.
2.  Under your target configuration, locate the **Environment variables** field.
3.  Click the folder icon and add your variables:
    `DB_URL=jdbc:postgresql://localhost:5432/postgres;DB_USER=postgres;DB_PASS=SecurePass99!`
4.  Click **Apply** and run the application.

---

## Summary
-   Configure user variables (`DB_URL`, `DB_USER`, `DB_PASS`) on your local OS to manage database connections.
-   Connect **IntelliJ Database Tools** to simplify data inspection and schema updates during Java development.
-   Ensure **IDE environment variables** are correctly mapped in your Run configurations so your JDBC code can connect to PostgreSQL during local execution.

---

## Additional Resources
-   [IntelliJ IDEA: Database Tool Window Guide](https://www.jetbrains.com/help/idea/database-tool-window.html)
-   [Gemini Code Assist in IntelliJ IDEA Setup](https://cloud.google.com/gemini/docs/code-assist/write-code-assistant-jetbrains)
-   [Windows Environment Variables Configuration Guide](https://www.oracle.com/content/dam/cn/zh/products/database/windows-environment-variables.pdf)
