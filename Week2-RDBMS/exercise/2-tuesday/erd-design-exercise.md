# Challenge: Library ERD Design

## Learning Objectives
- Design relational entity structures using Entity-Relationship Diagrams (ERDs).
- Apply cardinality notation rules to map business constraints.
- Formulate schemas featuring primary keys and logical referencing attributes.
- Resolve many-to-many relationship structures using junction tables.

---

## Setup Instructions
1. Open a collaborative sketching tool (such as Draw.io or Lucidchart) or use the Mermaid diagram format inside your markdown editor.
2. Review the business description below carefully to isolate entity candidates and key relations.

---

## The Scenario: Library Loans Management
You are tasked with designing a database schema for a community library.
-   The library tracks **Books**. Each book has a unique registration code, a title, an author, and a publishing year.
-   The library has **Members**. Each member has a unique membership card number, a name, an email address, and a sign-up date.
-   Members borrow books. This activity is recorded as a **Loan**. A loan tracks which member borrowed which book, the date it was checked out, and the due date. A member can borrow multiple books over time. A single book can be borrowed many times over its lifespan.

---

## Deliverables
1.  **Entity Mapping:** Identify the primary attributes and keys for the candidate entities: `BOOKS`, `MEMBERS`, and `LOANS`.
2.  **Cardinality Diagram:** Construct a Mermaid ERD (or graphic layout) showing the relations:
    -   Link `MEMBERS` to `LOANS` with appropriate cardinality.
    -   Link `BOOKS` to `LOANS` with appropriate cardinality.
3.  **Key Justification:** Write a brief justification explaining:
    -   What serves as the primary identifier (PK) for each entity.
    -   How you resolved the relationship between `MEMBERS` and `BOOKS`.

---

## Definition of Done
-   A completed `library-erd.mermaid` (or image) saved in your local workspace.
-   The ERD maps attributes, PK markers, and FK indicators for all tables.
-   The cardinality lines represent the business boundaries accurately (e.g. one member can have zero-to-many loans).
