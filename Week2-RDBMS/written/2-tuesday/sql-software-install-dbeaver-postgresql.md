# Database Tooling Setup: PostgreSQL and DBeaver

## Learning Objectives
- Differentiate between a Database Server (RDBMS) and a Database Client (GUI Tool).
- Install PostgreSQL and verify its execution as a local system service.
- Install DBeaver Community Edition and connect it to a local PostgreSQL instance.
- Troubleshoot common connection parameters (host, port, credentials).

---

## Why This Matters
To build database applications, we need the correct software environment. However, many beginners confuse the database engine itself with the visual program they use to look at data. 

In professional environments:
-   **PostgreSQL** runs silently in the background as a server process (often on a remote cloud server), managing files on disk and listening for incoming SQL commands.
-   **DBeaver** is a client application that runs on your local workstation, allowing you to visually explore tables and write queries without using command-line prompts.

Setting up this environment on your workstation simulates a professional workflow. Learning how to establish connection configurations (such as hostnames, ports, and credentials) prepares you to configure database connections in enterprise Java applications.

---

## The Concept

### 1. PostgreSQL (The RDBMS Server)
PostgreSQL is a powerful, open-source object-relational database system. When installed, it runs as a service (or daemon) on your computer. It listens for incoming SQL queries on a default network port: **5432**.

### 2. DBeaver (The Client GUI)
DBeaver is a multi-platform database tool for developers. It uses **JDBC drivers** to communicate with database servers. Once configured, DBeaver sends SQL scripts to PostgreSQL, receives the results, and displays them as tables.

### 3. Core Connection Parameters
To connect any client (like DBeaver or a Java app) to a database server, you must specify five connection parameters:
-   **Host / Server:** The address where the database server is running. For local installations, this is **`localhost`** or the IP address **`127.0.0.1`**.
-   **Port:** The network port the database service is listening on. For PostgreSQL, the default is **`5432`**.
-   **Database Name:** The specific database namespace you want to connect to. The installation default database is **`postgres`**.
-   **Username:** The database user account. The default administrative user is **`postgres`**.
-   **Password:** The password set during the database server installation.

---

## Step-by-Step Installation Guide

### Step 1: Installing PostgreSQL (Windows)

1.  **Download:** Navigate to the [official PostgreSQL downloads page](https://www.postgresql.org/download/windows/) and download the interactive installer for Windows.
2.  **Run Installer:** Double-click the downloaded executable.
3.  **Select Components:** In the setup wizard, ensure the following components are selected:
    -   PostgreSQL Server
    -   pgAdmin 4 (an alternative GUI client)
    -   Command Line Tools
4.  **Data Directory:** Accept the default path for the data files.
5.  **Set Password:** You will be prompted to enter a password for the database superuser (`postgres`). **Do not forget this password.** You will need it to connect later.
6.  **Port Selection:** Accept the default port `5432`.
7.  **Locale:** Select the default locale.
8.  **Complete Installation:** Click next to complete the setup. PostgreSQL is now running as a background service.

---

### Step 2: Installing DBeaver Community Edition

1.  **Download:** Go to the [DBeaver download page](https://dbeaver.io/download/) and download the Windows Installer for DBeaver Community Edition.
2.  **Install:** Run the installer and click through the prompts, selecting the default options. 
3.  **Launch:** Start DBeaver from your Start Menu.

---

### Step 3: Connecting DBeaver to PostgreSQL

1.  **Create Connection:** In DBeaver, select **Database** -> **New Database Connection** from the top menu, or click the plug icon in the toolbar.
2.  **Select PostgreSQL:** Choose **PostgreSQL** from the list of database types and click **Next**.
3.  **Enter Connection Settings:**
    -   **Host:** `localhost`
    -   **Port:** `5432`
    -   **Database:** `postgres`
    -   **Username:** `postgres`
    -   **Password:** Enter the password you created during the PostgreSQL installation.
4.  **Download Drivers:** Click the **Test Connection** button. If you are running DBeaver for the first time, it will prompt you to download the required PostgreSQL JDBC driver files. Click **Download**.
5.  **Verify Connection:** If the settings are correct, you will see a "Connected" success window. Click **OK**, then click **Finish**.
6.  **Write Queries:** Expand the database explorer tree on the left, right-click on the connection, and select **SQL Editor** -> **Open SQL Console**. You can now execute query commands.

---

## Troubleshooting Common Errors

-   **"Connection Refused" (Error Code 08001):** The PostgreSQL service is not running locally. Open the Windows Services application (`services.msc`), find **postgresql-x64-XX**, and click **Start**.
-   **"Invalid Password" (Error Code 28P01):** The password you typed in DBeaver does not match the password you set during installation. Double-check your spelling.
-   **"Port 5432 in Use":** Another program (perhaps a previous install) is using port 5432. You may need to stop the conflicting program or reinstall PostgreSQL using a different port (such as 5433).

---

## Summary
-   **PostgreSQL** is the database server engine; **DBeaver** is the graphical database client used to run queries.
-   Clients connect over TCP/IP using the default PostgreSQL port **5432**.
-   Connecting requires a host (`localhost`), port, database name, username, and password.
-   JDBC drivers are required for client programs to send SQL queries to the database server.

---

## Additional Resources
-   [PostgreSQL Official Installation Guide](https://www.postgresql.org/download/)
-   [DBeaver Official Wiki and Docs](https://dbeaver.io/docs/)
-   [PostgreSQL JDBC Driver Documentation](https://jdbc.postgresql.org/)
