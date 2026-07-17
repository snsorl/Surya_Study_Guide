# Challenge: Converting the Library Schema to DynamoDB

**Mode:** C — Hybrid (Design first, then implement)
**Estimated Time:** 3–4 hours
**Topics Covered:** Access pattern analysis, denormalization, single-table design, DynamoDB key modeling, AWS SDK for Java v2

---

## Scenario

**City Library Digital Systems** is modernising its borrowing platform. The existing system uses a PostgreSQL relational database with three tables. The engineering team has decided to migrate the borrowing module to DynamoDB because loan records are accessed almost exclusively by `member_id` and the team needs single-digit millisecond lookup times for self-service kiosk stations.

Your job as the **Data Architect** is to:
1. Analyse the existing relational schema and list the application's access patterns.
2. Design a DynamoDB single-table model that supports every access pattern **without using Scan**.
3. Document your design decisions in the worksheets provided in `templates/`.
4. Implement a Python seed script to populate your DynamoDB design with test data.

---

## Prerequisites

Review the following written content before starting:
- `written/3-wednesday/differences-from-relational-modeling.md`
- `written/3-wednesday/convert-relational-schema-to-dynamodb.md`
- `written/3-wednesday/key-concepts-tables-items-attributes.md`
- `written/3-wednesday/when-to-use-nosql-vs-sql.md`

---

## Part A — Understand the Existing Relational Schema (Design Phase)

### The Current PostgreSQL Schema

```sql
CREATE TABLE members (
    member_id   SERIAL PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    membership_tier  VARCHAR(20) DEFAULT 'STANDARD'  -- 'STANDARD', 'PREMIUM'
);

CREATE TABLE books (
    isbn        VARCHAR(13) PRIMARY KEY,
    title       VARCHAR(200) NOT NULL,
    author      VARCHAR(100) NOT NULL,
    genre       VARCHAR(50),
    total_copies  INT DEFAULT 1
);

CREATE TABLE loans (
    loan_id       SERIAL PRIMARY KEY,
    member_id     INT REFERENCES members(member_id),
    isbn          VARCHAR(13) REFERENCES books(isbn),
    loan_date     DATE NOT NULL,
    due_date      DATE NOT NULL,
    return_date   DATE         -- NULL if not yet returned
);
```

### Task A1 — List the Access Patterns

Before designing any DynamoDB tables, you must document every query the application needs to run. Open `templates/access-patterns-worksheet.md` and fill in **at least 6** access patterns.

Think about:
- What does the self-service kiosk need to look up?
- What does a librarian dashboard need?
- What reports does the library management system generate weekly?

Use the examples below as a starting point, then add your own:

| # | Access Pattern | SQL Equivalent |
|---|---|---|
| 1 | Fetch member profile by Member ID | `SELECT * FROM members WHERE member_id = ?` |
| 2 | List all active loans for a member | `SELECT * FROM loans WHERE member_id = ? AND return_date IS NULL` |
| 3 | _[Your pattern here]_ | |
| … | _[Continue for at least 6 total]_ | |

---

## Part B — Design the DynamoDB Single-Table Model (Design Phase)

### Task B1 — Key Design

Open `templates/key-design-worksheet.md`. Using the access patterns from Task A1:

1. Choose a **table name** for your single-table design.
2. Choose generic **Partition Key** (`PK`) and **Sort Key** (`SK`) names.
3. Define the **entity prefix conventions** for each entity type:
   - Example: Members use `MEMBER#` prefix, books use `BOOK#`, loans use `LOAN#`
4. Complete the key mapping table showing how each entity's PK and SK values map to the prefix scheme.

### Task B2 — Single-Table Layout Diagram

Open `templates/single-table-design.mermaid`. Complete the Mermaid diagram that shows how **at least 3 different item types** (Member, Book, Loan) coexist in the same table, including example PK/SK values and attribute columns.

### Task B3 — Access Pattern Verification

For **each access pattern** in your list, specify the **DynamoDB operation** that fulfils it:
- Operation: `GetItem`, `Query`, or (justified) `Scan`
- Key expression: `PK = ? AND SK = ?` or `PK = ? AND SK BEGINS_WITH ?`
- Is a Global Secondary Index (GSI) needed? (You don't need to implement one — just flag it)

Document this in `templates/access-pattern-verification.md`.

---

## Part C — Implement Seed Data (Implementation Phase)

### Task C1 — Seed the Table

Open `starter_code/SeedLibrary.java`. Implement the `seedAll()` method to insert the following records into your DynamoDB table:

**Members (3 records):**
| member_id | full_name | email | membership_tier |
|---|---|---|---|
| M-001 | Alice Nguyen | alice@library.org | PREMIUM |
| M-002 | Ben Carter | ben@library.org | STANDARD |
| M-003 | Clara Diaz | clara@library.org | PREMIUM |

**Books (4 records):**
| isbn | title | author | genre |
|---|---|---|---|
| 978-0-06-112008-4 | To Kill a Mockingbird | Harper Lee | FICTION |
| 978-0-7432-7356-5 | The Great Gatsby | F. Scott Fitzgerald | FICTION |
| 978-0-14-028329-7 | The Clean Coder | Robert C. Martin | TECHNICAL |
| 978-0-201-63361-0 | Design Patterns | Gang of Four | TECHNICAL |

**Loans (5 records — mix of active and returned):**
| loan_id | member_id | isbn | loan_date | due_date | return_date |
|---|---|---|---|---|---|
| LN-1001 | M-001 | 978-0-06-112008-4 | 2026-07-01 | 2026-07-15 | `null` (active) |
| LN-1002 | M-001 | 978-0-14-028329-7 | 2026-06-15 | 2026-06-29 | 2026-06-28 |
| LN-1003 | M-002 | 978-0-7432-7356-5 | 2026-07-05 | 2026-07-19 | `null` (active) |
| LN-1004 | M-003 | 978-0-201-63361-0 | 2026-07-10 | 2026-07-24 | `null` (active) |
| LN-1005 | M-002 | 978-0-06-112008-4 | 2026-05-01 | 2026-05-15 | 2026-05-14 |

### Task C2 — Verify with Query

After seeding, implement `verifyMemberLoans(String memberId)` in `starter_code/SeedLibrary.java` to verify access pattern #2: **"List all active loans for a member"**.

Run a **Query** (not Scan) against your single-table design. Print the loan_id, isbn, and due_date for each active loan found.

---

## Deliverables

When you are done, your submission must include all of the following:

### Design Artifacts (in `templates/`)
- [ ] `access-patterns-worksheet.md` — Minimum 6 access patterns documented.
- [ ] `key-design-worksheet.md` — Completed table name, PK/SK names, and entity prefix mapping.
- [ ] `single-table-design.mermaid` — Completed diagram showing at least 3 entity types coexisting.
- [ ] `access-pattern-verification.md` — Each access pattern mapped to a DynamoDB operation.

### Implementation Artifacts (in `starter_code/`)
- [ ] `SeedLibrary.java` — Seeds all Members, Books, and Loans into your DynamoDB design.
- [ ] `SeedLibrary.java` — `verifyMemberLoans()` uses Query to list active loans for a member.

---

## Definition of Done

- [ ] **Minimum 6 access patterns** documented before any key design decisions were made.
- [ ] Your single-table design uses **generic PK/SK names** (not `member_id` or `isbn`).
- [ ] All 3 entity types (Member, Book, Loan) can be stored and retrieved from a **single table**.
- [ ] Access pattern #2 is fulfilled by a **Query operation**, not a Scan.
- [ ] The Mermaid diagram renders correctly and shows example PK/SK values for all 3 entity types.
- [ ] `SeedLibrary.java` runs without errors and inserts all 12 records (3 members + 4 books + 5 loans).

---

## Stretch Challenges (Optional)

1. **Overdue Loans:** How would you design a GSI to query all overdue loans across all members without a Scan? Describe the GSI design in a new section of `access-pattern-verification.md`.
2. **Book Lookup:** Design and implement a Query that retrieves a book's full details plus all of its currently active loans. What PK/SK combination makes this possible?
3. **Denormalization:** Modify your Loan records to embed the book `title` and member `full_name` directly inside the loan item. Write a paragraph in `key-design-worksheet.md` explaining the read/write tradeoff.

---

## Submission

Push your completed work to your GitHub repository in the folder `week3/library-schema-conversion/`. Your repository must contain the completed `templates/` and `starter_code/` directories (including `SeedLibrary.java` and `pom.xml`).
