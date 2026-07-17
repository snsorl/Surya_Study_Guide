# AI Prompt Maintenance: JS & TS Workflows

## Learning Objectives
- Perform prompt maintenance to keep AI conversations clean.
- Identify and archive resolved debugging prompts.
- Update project context templates with active types and schemas.
- Optimize prompts to handle TypeScript compile-time errors.

---

## Why This Matters
As you transition from JavaScript to TypeScript, the types of questions you ask AI change. Instead of asking about dynamic coercion quirks, you ask about type definitions, build errors, or compiler warnings. To keep your AI interactions productive and avoid context drift, you must practice prompt maintenance: archiving resolved chats, updating templates with active types, and optimizing prompt instructions.

---

## The Concept

### 1. What is Prompt Maintenance?
Prompt maintenance is the practice of reviewing, cleaning, and updating the prompts and code context you provide to AI assistants over a project's lifecycle. As the code evolves, your context anchors must be updated to prevent the AI from generating outdated or incompatible code.

### 2. Archiving Debugging Prompts
Once a compile error (e.g. `Type 'X' is not assignable to type 'Y'`) is resolved:
- **Close the Chat**: Do not continue using the same chat session. The debugging history contains incorrect code snippets that will pollute future generations.
- **Log the Fix**: Save the resolved type definitions or compiler configurations in a local reference log for future use.

### 3. Updating Context Templates
When starting a new feature sprint:
1. Update your context anchors with your latest TypeScript `interface` declarations.
2. Remove deprecated JavaScript schemas.
3. Explicitly state the compiler target settings (e.g., *"target: ES2022, strict: true"*).

---

## Code Example

Below is a prompt maintenance template showing how to prompt an AI to resolve a TypeScript compiler error:

```text
[Role]
You are a Staff TypeScript Developer.

[Project Context]
- Compiler options: strict: true, target: ES2022
- Types declared:
  interface User { id: number; email: string | null; }

[Compile Error]
The compiler throws: "Type 'null' is not assignable to type 'string'" on line:
const contactEmail: string = user.email;

[Task]
Explain why this error occurs under 'strictNullChecks: true' and show the 3 best refactoring patterns to resolve it (e.g., Union type, Type assertion, Nullish coalescing).
```

---

## Summary
- Prompt maintenance keeps AI conversations focused, preventing **context drift**.
- Start a new chat session once a bug is resolved to clear buggy code blocks from the AI's history.
- Update your context anchors with your active TypeScript **interfaces** and compiler configurations.
- Use explicit compiler error descriptions inside prompts to guide the AI to accurate solutions.

---

## Additional Resources
- [Google Cloud: Prompt engineering guides](https://cloud.google.com/vertex-ai/docs/generative-ai/multimodal/introduction-prompt-design)
- [TypeScript Docs: Compiler errors reference](https://www.typescriptlang.org/docs/handbook/2/everyday-types.html)
