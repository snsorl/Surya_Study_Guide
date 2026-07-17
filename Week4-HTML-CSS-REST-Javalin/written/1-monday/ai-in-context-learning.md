# AI: In-Context Learning

## Learning Objectives
- Define In-Context Learning (ICL) in the context of Large Language Models (LLMs).
- Explain how the AI's context window affects output quality and accuracy.
- Prepare and provide relevant codebase context (class signatures, method stubs, configurations, error messages) when prompting AI tools.
- Critique the "garbage in, garbage out" paradigm of AI assistance.

---

## Why This Matters
As you build more complex applications, you will rely on AI coding assistants to write boilerplate, brainstorm designs, or troubleshoot errors. However, generic prompts like "fix my Javalin database error" are highly likely to produce generic, incorrect code. LLMs are not telepathic; they do not have access to your workspace unless you explicitly provide it. By learning to provide rich, structured context, you will get highly accurate, tailormade answers that compile immediately on the first attempt, saving hours of debugging time.

---

## The Concept

### What is In-Context Learning (ICL)?
**In-Context Learning** is a capability of modern Large Language Models where they learn to perform a task simply by being shown examples, constraints, or environment details in their prompt. Unlike traditional machine learning, which requires modifying the model's weights (fine-tuning), ICL works purely within the immediate conversation.

By feeding the model relevant code snippets, logs, or file structures, you "teach" the AI about your specific codebase for the duration of that session.

### The Context Window and Output Quality
Every LLM has a **Context Window**—the total amount of text (measured in tokens) the model can read and write at one time. 
-   **If you provide too little context**: The AI has to guess, leading to "hallucinations" (generating fake classes, incorrect variables, or obsolete APIs).
-   **If you provide too much irrelevant context**: The AI can get lost in the noise, diluting its attention and lowering the precision of its answers.

The goal is to provide **high-density context**: only the code, logs, and files that are directly related to the problem you want to solve.

### Prompting with Runtime Context
When requesting code generations or debugging help from an AI, you should structure your prompt to include four context blocks:

1.  **The Goal**: What you want to accomplish (e.g., "Create a Javalin GET handler for users").
2.  **The Environment**: The frameworks and versions you are using (e.g., "Javalin 6.1.3, Java 17, Maven").
3.  **The Code Context**: Class signatures, schemas, or method stubs. Do not paste thousands of lines of code; paste class outlines, database tables, or DTO contracts.
4.  **The Error/Logs** (If debugging): The exact stack trace or error response returned.

---

## Code Example

Here is an example illustrating a bad, context-poor prompt vs. a high-context prompt that utilizes In-Context Learning principles.

### Bad Prompt (Low Context)
> "Write a Javalin endpoint to get all tasks from my database."

*Result*: The AI will invent database connection utilities, model objects, and endpoints that do not match your project architecture. You will have to rewrite most of it.

### Better Prompt (Rich Context - ICL)
> I need to implement a Javalin GET route to retrieve all tasks from the database. 
> Here is my project stack and code details:
> 
> **Environment**:
> - Java 17, Javalin 6.1.3, Jackson mapper
> 
> **Database Table**:
> ```sql
> CREATE TABLE tasks (
>     id SERIAL PRIMARY KEY,
>     title VARCHAR(255) NOT NULL,
>     completed BOOLEAN DEFAULT FALSE
> );
> ```
> 
> **Task DTO**:
> ```java
> public class Task {
>     private int id;
>     private String title;
>     private boolean completed;
>     // getters/setters omitted for brevity
> }
> 
> **TaskRepository Stub**:
> ```java
> public class TaskRepository {
>     // Returns all tasks from DB
>     public List<Task> getAllTasks() { ... }
> }
> ```
> 
> Please write the GET endpoint `/api/tasks` inside a controller handler method. Use `TaskRepository` to fetch the tasks and return them in JSON format.

*Result*: The AI will output a clean, accurate Javalin handler that uses your exact `TaskRepository` structure, uses standard Jackson serialization, and integrates with your existing codebase without modifications.

---

## Summary
-   **In-Context Learning** is the process of teaching an AI about your code system within the prompt itself.
-   Providing **high-density, relevant context** prevents AI hallucinations and yields accurate, compilation-ready code.
-   Always provide **framework versions, model interfaces, database schemas**, and **exact stack traces** when asking an AI to write or debug code.

---

## Additional Resources
-   [Google Cloud: What is In-Context Learning?](https://cloud.google.com/discover/what-is-in-context-learning)
-   [Prompt Engineering Guide: In-Context Learning](https://www.promptingguide.ai/research/in-context-learning)
-   [OpenAI Cookbook: Prompt Engineering Best Practices](https://cookbook.openai.com/articles/techniques_for_improving_reliability)
