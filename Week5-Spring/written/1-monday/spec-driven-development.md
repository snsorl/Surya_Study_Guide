# Specification-Driven Development (SDD)

## Learning Objectives
- Define Specification-Driven Development (SDD) and its benefits.
- Structure Behavior-Driven Development (BDD) features using the Given-When-Then format.
- Use technical specifications to write high-fidelity prompts for AI coding assistants.
- Use specifications as the objective source of truth for peer reviews and automated verification checks.

---

## Why This Matters
Many developers make the mistake of starting to write code before they truly understand what they are building. This leads to scope creep, design dead-ends, and brittle integrations. In the age of AI coding assistants, starting with a vague goal like "Write a Spring user endpoint" will yield generic, buggy code that does not fit your system. Specification-Driven Development solves this. By writing and refining a precise technical specification *before* you code, you can align teams, construct highly accurate AI prompts, and build automated validation test plans.

---

## The Concept

### What is Specification-Driven Development (SDD)?
**Specification-Driven Development (SDD)** is a software engineering practice where you define precise requirements, validation rules, and interface signatures (APIs) in written specifications *before* writing a single line of application source code. 

SDD shifts quality checks to the very start of the software lifecycle. Instead of guessing how components should talk to each other, you agree on the contracts (the specification) first.

```
[ Written Specification / Contract ]
                 │
        ┌────────┴────────┐
        ▼                 ▼
[ Application Code ] ◄── [ Automated Tests ]
```

### BDD-Style Feature Descriptions
A core technique within SDD is writing specifications in a **Behavior-Driven Development (BDD)** format. BDD focuses on defining user behavior through examples.

The standard BDD scenario structure uses the **Given-When-Then** format:
-   **Given**: The initial context or state of the system (pre-conditions).
-   **When**: The action or event that occurs (trigger).
-   **Then**: The expected outcome or assertion (post-conditions).

*Example Scenario:*
```gherkin
Scenario: Registering a user with a duplicate email
  Given the database contains a user with email "user@example.com"
  When a request is sent to create an account with email "user@example.com"
  Then the system should reject the request with a status code of 409 Conflict
  And the response body should contain the error message "Email already in use"
```

---

### Driving AI Prompting with Specifications
AI coding assistants are highly capable when given clear context and constraints. When you use SDD, your specification acts as the source of truth for your prompts.

#### The Bad Prompting Way:
> "Write a UserService class in Spring."
> *Result:* The AI guesses your database technology, naming conventions, validation rules, and architecture, forcing you to rewrite most of it.

#### The SDD Prompting Way:
Create a prompt structured like this:
1.  **Context:** The architecture of the application (e.g., Spring Boot, Maven, JPA).
2.  **Contract:** Provide the interface definition or database schema.
3.  **Behavior Spec:** Provide the BDD scenarios (Given-When-Then).
4.  **Constraints:** Explicit rules (e.g., "Use constructor injection, make the class final, do not use field injection").

---

### Specs as the Source of Truth for Reviews
During peer code reviews, a common issue is subjective arguments about style or architecture. SDD eliminates this ambiguity.

-   **Objective Verification**: Reviewers do not ask "Is this class written well?" They ask "Does this implementation satisfy the specification contracts and test scenarios?"
-   **Traceability**: Every line of code can be traced directly to a line in the specification. If code does not support a specification requirement, it is scope creep and should be removed.

---

## Example Template: Technical Feature Spec

Here is an example structure of a feature specification file that can be used to guide developers and drive AI prompting.

```markdown
# Feature: User Account Invoicing

## Overview
Automate monthly invoice generation for user accounts when they transition from trial to paid tier.

## API / Database Contract
- Interface: `InvoiceService`
- Database Entity Schema:
  - Table: `invoices`
  - Columns: `id` (UUID), `user_id` (bigint), `amount` (numeric), `status` (varchar)

## Scenarios (BDD)

### Scenario 1: Successful Invoice Generation
- **Given** a user account with ID 104 has completed its 30-day trial
- **And** the user has a registered payment card
- **When** the system runs the monthly invoice job
- **Then** a new invoice record should be created with a status of 'PENDING_PAYMENT'
- **And** the invoice amount should equal $29.99

### Scenario 2: Missing Payment Method
- **Given** a user account with ID 105 has completed its 30-day trial
- **And** the user has no registered payment card
- **When** the system runs the monthly invoice job
- **Then** the billing system should throw a 'PaymentMethodMissingException'
- **And** no invoice should be generated

## Technical Constraints
- The service implementation MUST use Spring's `@Transactional` boundary manager.
- Dependencies (such as `UserRepository` and `PaymentGateway`) must be wired via constructor injection.
```

---

## Summary
-   **Specification-Driven Development (SDD)** defines API contracts, architectures, and behaviors before writing source code.
-   **Given-When-Then** BDD-style scenarios express system expectations as clear user stories.
-   Technical specifications act as high-fidelity inputs for **AI prompts**, eliminating AI guesswork and reducing code churn.
-   Specs provide a logical, objective **source of truth** during peer code reviews and system validation phases.

---

## Additional Resources
-   [Martin Fowler: GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html)
-   [Cucumber Documentation: Writing BDD Scenarios](https://cucumber.io/docs/gherkin/reference/)
-   [Baeldung: Introduction to BDD](https://www.baeldung.com/cs/bdd-behavior-driven-development)
