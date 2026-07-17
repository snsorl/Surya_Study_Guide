# Lab: Implementing CHECK, DEFAULT, and CASCADE Constraints

## Learning Objectives
- Enforce schema business boundaries using `CHECK` constraints.
- Automate temporal and text metadata using `DEFAULT` constraints.
- Implement cascading actions (`ON DELETE CASCADE`) to clean orphan rows automatically.
- Analyze database error logs returned when constraint rules are violated.

---

## Setup Instructions
1. Open DBeaver and connect to your local PostgreSQL database.
2. Open a new SQL Console editor.
3. Review your existing Library schema tables (`books`, `members`, `loans`).

---

## Step-by-Step Tasks

### Task 1: Refactor Schema Constraints (DDL)
Recreate or alter your library tables to include the following constraint rules:

1.  **`books` Table:**
    -   Add a `CHECK` constraint validating that the publishing year (`published_year`) falls between `1450` (the invention of the printing press) and the current year.
2.  **`members` Table:**
    -   Add a `DEFAULT` constraint to a new `membership_status` column set to `'ACTIVE'`.
    -   Add a `CHECK` constraint restricting status to the categories: `('ACTIVE', 'SUSPENDED', 'EXPIRED')`.
3.  **`loans` Table:**
    -   Configure the foreign key linking to the `books` table to use **`ON DELETE CASCADE`**.
    -   Configure the foreign key linking to the `members` table to use **`ON DELETE RESTRICT`**.

---

### Task 2: Verify CHECK and DEFAULT Constraints
Write and execute testing queries:
-   **Insert Valid Row:** Insert a book published in `2005` without specifying a membership status for the member. Verify that the member is automatically set to `'ACTIVE'`.
-   **Insert Invalid CHECK Row:** Try to insert a book published in `1200`. Verify that the database rejects the command with a `check constraint` error.
-   **Insert Invalid Status Row:** Try to set a member's status to `'BLOCKED'`. Verify the error rejection.

---

### Task 3: Verify ON DELETE Behaviors
Test how deletions cascade across tables:

1.  **Test CASCADE:** Delete a book that currently has active loans. Verify that the book is deleted, and the referencing loan records inside the `loans` table are **deleted automatically** by the cascading trigger.
2.  **Test RESTRICT:** Try to delete a member who currently has active loans. Verify that the database **blocks the deletion** due to the restrict parameter.

---

## Definition of Done
-   A complete DDL refactoring script that successfully updates table constraint structures.
-   SQL verification logs showing valid inserts, blocked updates, and cascading delete actions.
-   No orphaned loans remain on disk after a book is deleted.
