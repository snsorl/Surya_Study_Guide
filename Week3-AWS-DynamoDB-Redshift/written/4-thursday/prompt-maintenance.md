# AI Prompt Maintenance for Database Engineering

## Learning Objectives
- Define a prompt maintenance strategy for database engineering workflows.
- Audit accumulated prompts to identify obsolete syntax and outdated database features.
- Build a catalog of reusable, version-anchored database prompt templates.
- Implement security validation rules for sharing prompt templates in development teams.

## Why This Matters
As database engineering teams adopt generative AI to write queries, design schemas, and optimize database settings, they accumulate a large collection of prompts. If these prompts are not maintained, developers can reuse prompts that contain outdated parameters or deprecated API styles, resulting in errors when provisioning databases or running scripts.

Prompt maintenance addresses this by treating prompts as code (PromptOps). By auditing accumulated prompts, archiving outdated entries, and building templates with version variables, database teams can ensure that their AI interactions produce secure, syntactically correct, and cost-effective database scripts.

## The Concept

### The Life Cycle of a Database Prompt
Like database schemas and code libraries, database engineering prompts have a life cycle:

```
+-------------------------------------------------------------+
|                     1. Creation (Draft)                      |
|  - Write prompt for schema design or query optimization    |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                     2. Validation (Test)                    |
|  - Verify AI script works in local database containers      |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                     3. Archiving / Tuning                   |
|  - Update prompt templates for engine version changes      |
|  - Archive deprecated CLI parameter prompts                 |
+-------------------------------------------------------------+
```

---

### Key Maintenance Tasks

#### 1. Auditing Accumulated Prompts
Review prompt logs to identify:
- **Deprecated Syntax**: Prompts containing older AWS CLI v1 syntax or deprecated database parameters.
- **Security Vulnerabilities**: Prompts that generate default, insecure configurations (e.g., wildcard permission policies or open database ports).
- **Redundant Entries**: Duplicate prompts that can be simplified into a single template.

#### 2. Archiving Outdated Entries
Move outdated prompts to an archive folder. This prevents developers from copying and pasting outdated prompts into active development pipelines.

#### 3. Building Reusable, Parameterized Templates
Convert successful prompts into reusable templates by replacing hardcoded values with variables (e.g., `[DATABASE_ENGINE]`, `[TABLE_NAME]`, `[PRIMARY_KEY]`). This ensures consistency and enforces parameters (like version numbers and security rules) across the team.

## Reusable Prompt Templates

Here are three templates optimized for database engineering workflows.

### Template 1: Table Creation Schema Design
Use this template to generate table creation scripts for a specific database engine.

```text
Role: [DATABASE_ENGINE] Database Administrator
Task: Generate a table creation script based on the provided entity description and access patterns.
Database Engine Version: [VERSION]
Entity Description:
[ENTITY_DETAILS]

Access Patterns:
[LIST_OF_ACCESS_PATTERNS]

Constraints and Requirements:
1. Use exact data types optimized for [DATABASE_ENGINE] version [VERSION].
2. Include all primary and foreign key constraints.
3. Apply default values for audit metadata columns (e.g., created_at, updated_at).
4. Do not include insecure configurations or open network permissions.
```

### Template 2: Query Optimization (Index Tuning)
Use this template to optimize slow queries by analyzing execution plans.

```text
Role: [DATABASE_ENGINE] Performance Analyst
Task: Optimize the provided query using the database execution plan.
Database Engine Version: [VERSION]
Target Query:
[SQL_QUERY]

EXPLAIN Execution Plan:
[EXPLAIN_ANALYZE_OUTPUT]

Requirements:
1. Identify database scan bottlenecks (e.g., sequential scans on large tables).
2. Recommend index structures (single-column, composite, or covering indexes) to optimize performance.
3. Provide the SQL commands to create the recommended indexes.
4. Explain the expected performance improvements and write tradeoffs.
```

### Template 3: Data Migration (Seeding and Formatting)
Use this template to generate test data in the correct format for your target database.

```text
Role: Database Test Engineer
Task: Generate test seed data for the specified table.
Target Database: [DATABASE_ENGINE]
Table Name: [TABLE_NAME]
Primary Key Schema: [PRIMARY_KEY_SCHEMA]

Generate [RECORD_COUNT] realistic test records. 
Output Format: [OUTPUT_FORMAT] (e.g., DynamoDB JSON or standard SQL INSERT statements)

Constraints:
1. Every record must contain the exact primary key attributes.
2. Ensure primary key values are unique to prevent collisions.
3. Validate data type formats (e.g., numbers must be formatted correctly for [OUTPUT_FORMAT]).
```

## Summary
- **Prompt maintenance** treats prompts as code (PromptOps), auditing and archiving entries to prevent configuration errors.
- Audit logs to remove **deprecated syntax**, **security risks**, and **duplicate prompts**.
- Build **reusable, parameterized prompt templates** using version anchors to enforce security and consistency.
- Maintain prompt libraries alongside database schema files to keep developer workflows synchronized.

## Additional Resources
- [PromptOps: Applying DevOps Practices to Prompts - O'Reilly Guide](https://www.oreilly.com/library/view/devops-for-genai/9781098167905/)
- [AWS database Administration Automation Guide](https://aws.amazon.com/blogs/database/category/database-administration/)
- [SQL Prompt Optimization and Version Control Best Practices](https://www.red-gate.com/simple-talk/databases/sql-server/t-sql-programming-sql-server/sql-code-reviews-and-maintenance/)
