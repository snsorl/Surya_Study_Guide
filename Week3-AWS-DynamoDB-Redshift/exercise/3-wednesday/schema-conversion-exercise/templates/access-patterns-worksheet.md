# Worksheet: Access Pattern Analysis

**Exercise:** Library Schema Conversion to DynamoDB
**Part:** A — Design Phase (Complete BEFORE designing any keys)

---

## Instructions

List every query the application needs to run against the database.
Work through the three perspectives below before filling in the table.

**Perspective 1 — Self-Service Kiosk (Member-facing)**
- What does a member look up when they walk up to the kiosk?

**Perspective 2 — Librarian Dashboard (Staff-facing)**
- What does a librarian search for, update, or report on daily?

**Perspective 3 — Weekly System Reports (Management-facing)**
- What aggregated statistics does library management review each week?

---

## Access Pattern Table

Document at least **6 access patterns**. Be specific about the lookup key(s) and the direction of any sort key range queries.

| # | Access Pattern Description | Query Keys | Expected Volume | Operation Type |
|---|---|---|---|---|
| 1 | Fetch member profile by Member ID | `member_id` (exact) | High — every kiosk login | GetItem |
| 2 | List all **active** loans for a member | `member_id` (exact) + filter active | High — every kiosk view | Query + Filter |
| 3 | | | | |
| 4 | | | | |
| 5 | | | | |
| 6 | | | | |
| 7 | _(optional)_ | | | |
| 8 | _(optional)_ | | | |

---

## Design Constraints Identified

After completing the table, answer these questions:

1. **Most frequent lookup key:** _Which attribute appears most often as the primary lookup key?_

2. **Range queries needed:** _Which access patterns require a sort-key range query (e.g., loans by date)?_

3. **Entity relationships to embed:** _Which attributes from related entities should be denormalized (copied) into an item to avoid multiple queries?_

---

## Notes / Design Decisions

_Record any tradeoffs or questions that came up while defining access patterns._
