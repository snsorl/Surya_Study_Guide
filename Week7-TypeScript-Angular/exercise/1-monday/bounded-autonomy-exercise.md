# Design Challenge: AI Governance & Bounded Autonomy

## Scenario
Your development team is introducing AI coding assistants (like Github Copilot and Gemini) to accelerate Angular feature delivery. However, without guidelines, code reviews are getting flooded with buggy generated code, security vulnerabilities (like inline CSS style hacks or unsafe DOM injections), and unvalidated routing tables. 

To resolve this, you must write a **Bounded Autonomy Charter** that sets clear, strict boundaries for AI usage in your team.

---

## Deliverables

Create a markdown file named `bounded-autonomy-charter.md` containing the following sections:

### 1. The Autonomous Decision List
List 3-4 tasks the AI can execute autonomously without pre-approval or developer reviews (e.g. code formatting, test mocks).

### 2. The Conditional Review List
Identify 3-4 tasks the AI can draft but **must** go through human review and validation gates before merging (e.g. routing configs, data models, API integrations).

### 3. The Blocked List
List 2-3 tasks where AI usage is forbidden (e.g. database schema migrations, security authentication credentials).

### 4. Explicit Handoff Rules (Stop Conditions)
Define at least 3 concrete stop triggers where a developer must stop using AI and manually complete the task (e.g., repeating compilation errors, test failure loops).

---

## Definition of Done
Your charter is complete when:
- All four sections are detailed and address Angular-specific development concerns.
- You provide clear reasons *why* each boundary was set.
