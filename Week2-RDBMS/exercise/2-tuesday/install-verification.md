# Lab: Local Environment Installation and Verification

## Learning Objectives
- Install and configure PostgreSQL Server on local workstations.
- Install and configure DBeaver Database Client.
- Establish secure network connections between client interfaces and database engines.
- Execute diagnostic SQL queries to verify system health.

---

## Setup Instructions
1. Download the PostgreSQL installer matching your operating system version.
2. Download the DBeaver Community Edition installer.
3. Install PostgreSQL using the default port `5432` and set a secure administrative password (remember this password!).
4. Install DBeaver on the same machine.

---

## Step-by-Step Tasks

### Task 1: Establish Local Connection in DBeaver
1. Open DBeaver.
2. Select **Database** -> **New Database Connection** from the top menu.
3. Select **PostgreSQL** from the database list and click Next.
4. Input connection details:
   - Host: `localhost`
   - Port: `5432`
   - Database: `postgres`
   - Username: `postgres`
   - Password: *[Your Installation Password]*
5. Click **Test Connection**. DBeaver may request to download drivers; click download to proceed.
6. Once the connection verification is successful, click **Finish**.

---

### Task 2: Execute Diagnostics
1. Right-click your active `postgres` connection block in the Database Navigator panel.
2. Select **SQL Editor** -> **Open SQL Console**.
3. Write and execute the following query:
   ```sql
   SELECT version();
   ```
4. Observe the results panel. It should return a text string containing the operating system, compilation environment, and exact PostgreSQL version details (e.g. *PostgreSQL 14.2 on x86_64...*).

---

## Definition of Done
-   Successful diagnostic verification screenshot showing the DBeaver console displaying the results of `SELECT version();`.
-   The local connection is saved and visible in your DBeaver connection list.
