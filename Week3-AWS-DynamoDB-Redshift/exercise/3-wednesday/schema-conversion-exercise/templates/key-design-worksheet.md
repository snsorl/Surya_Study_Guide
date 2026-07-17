# Worksheet: DynamoDB Key Design

**Exercise:** Library Schema Conversion to DynamoDB
**Part:** B1 — Key Design (Complete AFTER finishing the access patterns worksheet)

---

## Step 1: Table Configuration

| Field | Your Answer |
|---|---|
| **DynamoDB Table Name** | _(e.g., `library-system`)_ |
| **Partition Key (PK) Attribute Name** | _(e.g., `PK`)_ |
| **Sort Key (SK) Attribute Name** | _(e.g., `SK`)_ |
| **Billing Mode** | PAY_PER_REQUEST |

---

## Step 2: Entity Prefix Conventions

Define the key value prefix for each entity type. Prefixes allow different entity types to coexist in the same table without key collisions.

| Entity Type | PK Prefix | SK Prefix / Pattern | Example PK Value | Example SK Value |
|---|---|---|---|---|
| Member | `MEMBER#` | `METADATA` | `MEMBER#M-001` | `METADATA` |
| Book | | | | |
| Loan (under member) | | | | |
| _(Add more if needed)_ | | | | |

---

## Step 3: Item-Level Key Mapping

Show exactly how each entity's attributes map to the PK and SK values. Fill in the table for **all 3 entities**.

### Member Item
| Attribute | DynamoDB Key/Field | Example Value |
|---|---|---|
| `member_id` | PK | `MEMBER#M-001` |
| Entity type marker | SK | `METADATA` |
| `full_name` | Attribute | `Alice Nguyen` |
| `email` | Attribute | `alice@library.org` |
| `membership_tier` | Attribute | `PREMIUM` |

### Book Item
| Attribute | DynamoDB Key/Field | Example Value |
|---|---|---|
| `isbn` | _?_ | _?_ |
| Entity type marker | _?_ | _?_ |
| `title` | Attribute | _?_ |
| `author` | Attribute | _?_ |
| `genre` | Attribute | _?_ |

### Loan Item
| Attribute | DynamoDB Key/Field | Example Value |
|---|---|---|
| `member_id` | _?_ | _?_ |
| `loan_id` | _?_ | _?_ |
| `isbn` | Attribute | _?_ |
| `loan_date` | Attribute | _?_ |
| `due_date` | Attribute | _?_ |
| `return_date` | Attribute | `null` if active |

---

## Step 4: Design Rationale

Answer the following questions in 1–2 sentences each:

**Q1: Why use generic key names (`PK` and `SK`) instead of specific names like `member_id`?**

_[Your answer here]_

---

**Q2: For access pattern #2 ("List all active loans for a member"), what PK value do you use in the `Query` call?**

_[Your answer here]_

---

**Q3: What sort key prefix allows you to retrieve only Loan items for a member (and not the member's METADATA record)?**

_[Your answer here]_

---

**Q4: If you embedded `book_title` inside each Loan item (denormalization), what are the write tradeoffs if a book's title changes?**

_[Your answer here]_
