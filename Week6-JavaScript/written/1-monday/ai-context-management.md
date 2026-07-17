# AI Context Management in Software Engineering

## Learning Objectives
- Define AI context windows and explain the problem of context drift.
- Determine when to start a new chat session to maximize AI code generation quality.
- Re-establish project context efficiently in new sessions.
- Write effective system prompt templates containing roles, target constraints, and schemas.

---

## Why This Matters
As software engineers, we use AI coding assistants to accelerate development, help refactor code, and write tests. However, AI chat interfaces have a limited "context window". As a conversation grows long, the AI begins to "forget" details from earlier in the chat, hallucinate solutions, or drift from initial constraints. Managing your AI conversation context is a critical engineering skill that ensures the code generated is clean, secure, and matches your application architecture.

---

## The Concept

### 1. Context Windows and Context Drift
Every LLM has a **context window**, which is the maximum number of tokens (words/characters) it can process at one time. When a chat grows long:
- **Token Overflow**: Older messages are trimmed or ignored.
- **Context Drift**: The AI becomes distracted by previous debugging attempts or throwaway code blocks, causing it to generate subpar solutions or reintroduce resolved bugs.

```
Long Chat History:
[System Prompt] -> [File Code] -> [Debug Chat 1] -> [Debug Chat 2] -> [Drift Zone]
                                                                          │
                                         AI starts forgetting constraints ◄┘
```

### 2. When to Start a New Chat Session
To maintain high-quality code generation, you should start a new chat session when:
- **Topic Shift**: You are moving to a new feature or day's task (e.g. switching from writing SQL queries to designing CSS layouts).
- **Debugging Loop**: You have been trying to fix the same error for more than 4-5 prompts. The history is likely polluted with dead-end attempts.
- **Hallucinations**: The AI begins generating classes or methods that do not exist, or ignores direct formatting rules.

### 3. Re-establishing Context Efficiently
When opening a new chat, do not paste your entire codebase. Instead, pass a structured "Context Anchor":
1. **The Goal**: A clear, 1-2 sentence description of what you are building.
2. **The Tech Stack**: List dependencies (e.g., Spring Boot 3.3, Java 17, PostgreSQL).
3. **The Schema/Types**: Paste only the database schema or the TypeScript interfaces involved.
4. **The Target Code**: Paste only the specific class or function you want to refactor.

### 4. Structuring Prompts (Role-Constraint-Format)
To get the best results, structure your initial prompt using the **RCF Pattern**:
- **Role**: Define the AI's role (e.g., *"You are an expert Spring Boot Security Auditor"*).
- **Constraints**: List boundaries (e.g., *"Do not use legacy WebSecurityConfigurerAdapter; enforce strict BCrypt password hashing"*).
- **Format**: Specify the output structure (e.g., *"Provide only the Java configuration class block, followed by a short markdown checklist"*).

---

## Code Example

Below is a prompt template demonstrating how to start a new AI session with clean context when refactoring code:

```text
[Role]
You are a Staff Frontend Engineer specializing in modern clean-code JavaScript.

[Context]
I am refactoring a legacy customer search routine. Here is the active dataset structure:
const customers = [{ id: 1, name: "Alice", active: true, purchases: [100, 200] }];

[Task]
Write a function named `getActiveHighSpenders(customerList)` that:
1. Filters customers who are active.
2. Identifies if the sum of their purchases is greater than 150.
3. Returns an array of customer names.

[Constraints]
- Use only ES6+ let/const and array methods (filter, map, reduce).
- Do not use manual `for` loops.
- Do not include any HTML scaffolding.
```

---

## Summary
- Long conversations pollute the AI's memory, leading to **context drift** and hallucinations.
- Start a new chat when changing tasks or when caught in a debugging loop.
- Use the **Role-Constraint-Format (RCF)** pattern to structure prompts.
- Re-establish context in new chats using minimal, focused context anchors rather than dumping entire files.

---

## Additional Resources
- [Google Cloud: Prompt engineering guide](https://cloud.google.com/vertex-ai/docs/generative-ai/multimodal/introduction-prompt-design)
- [Anthropic: Prompt engineering best practices](https://docs.anthropic.com/en/docs/build-with-claude/prompt-engineering/overview)
