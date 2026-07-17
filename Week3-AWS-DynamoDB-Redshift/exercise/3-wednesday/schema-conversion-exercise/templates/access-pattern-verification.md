# Worksheet: Access Pattern → DynamoDB Operation Mapping

**Exercise:** Library Schema Conversion to DynamoDB
**Part:** B3 — Access Pattern Verification

---

## Instructions

For each access pattern you identified in `access-patterns-worksheet.md`, specify the exact DynamoDB operation that fulfils it. Use the format below.

**Operation types:**
- `GetItem` — retrieves a single item by exact PK + SK
- `Query` — retrieves multiple items sharing a partition key; supports SK range conditions
- `Scan` — full table sweep (justify any use of Scan — it should be rare)
- `Query + GSI` — indicates a Global Secondary Index is needed (flag only; no implementation required)

---

## Pattern Verification Table

| # | Access Pattern | DynamoDB Operation | Key Expression | GSI Needed? | Notes |
|---|---|---|---|---|---|
| 1 | Fetch member profile by Member ID | `GetItem` | `PK = "MEMBER#M-001", SK = "METADATA"` | No | Single-item exact lookup |
| 2 | List all active loans for a member | `Query` + `FilterExpression` | `PK = "MEMBER#M-001" AND SK BEGINS_WITH "LOAN#"` | No | Filter `return_date` attribute_not_exists |
| 3 | _[Your pattern]_ | | | | |
| 4 | _[Your pattern]_ | | | | |
| 5 | _[Your pattern]_ | | | | |
| 6 | _[Your pattern]_ | | | | |
| 7 | _(optional)_ | | | | |
| 8 | _(optional)_ | | | | |

---

## GSI Design (Stretch Challenge — Optional)

If any of your access patterns required a GSI, describe the index design here.

| GSI Name | GSI Partition Key | GSI Sort Key | Use Case |
|---|---|---|---|
| _example: `due-date-index`_ | _`due_date`_ | _`PK`_ | _Query all overdue loans system-wide_ |

---

## Reflection Questions

**Q1: Which access patterns are efficiently served by your single-table design without any additional indexes?**

_[Your answer here]_

---

**Q2: Are there any patterns where a Scan would have been the "easy" but wrong choice?
Which one, and why is a GSI a better solution?**

_[Your answer here]_

---

**Q3: What is the advantage of using `SK BEGINS_WITH "LOAN#"` over using a `FilterExpression` on an `entity_type` attribute?**

_[Your answer here]_
