# Exercise: AI Code Review & Feedback Integration

## Objective
Establish professional software engineering code-review patterns by submitting your complete Week 1 projects to an AI model, auditing the AI's structural critique suggestions, and refactoring your codebase accordingly.

---

## Prerequisites
- Completed the Week 1 exercises, including the Friday [preventing-blind-acceptance.md](../../written/5-friday/preventing-blind-acceptance.md) reading.

---

## Step-by-Step Instructions

### Step 1: Prepare Code Submission
Identify your best-coded program from Week 1 (e.g. the `TaskManager` collections manager or the `HRApp` encapsulation profiles). Ensure the code compiles locally.

---

### Step 2: Formulate the AI Code Review Prompt
1.  Open your AI assistant panel in IntelliJ.
2.  Submit your entire class content along with a **structured code-review prompt**:
    *   *Prompt Pattern*:
        ```text
        Act as a Principal Java Architect. Audit the following Java class. Provide a code review covering:
        1. Readability: Are comments clear? Are variables named consistently?
        2. Performance: Are there redundant memory allocations or nested O(N^2) loops?
        3. Exception Safety: Does it guard against nulls, division-by-zero, or bounds violations?
        4. Encapsulation: Is data properly hidden?
        
        Provide constructive feedback points and a refactored version of the class resolving any vulnerabilities found.
        
        Here is the code:
        [Paste your Java Class code here]
        ```

---

### Step 3: Audit the AI Feedback
Analyze the AI's suggestions. Ask yourself:
- Did the AI identify a genuine logical error or reference vulnerability?
- Did it suggest clean additions (like adding `@Override` annotations or final declarations)?
- **Crucial check**: Did it change your class behavior or introduce syntax errors? Paste the AI's modified code into a separate file and verify it compiles without warnings.

---

### Step 4: Refactor and Log Changes
Create a Markdown file named **`code-review-audit.md`** in your project directory. Record:
1.  **AI Critique Points**: List the key points suggested by the AI.
2.  **Implementation Decisions**: Explain which AI suggestions you chose to accept (e.g., adding `final` to immutable parameters) and which you rejected (explaining why).
3.  **Refactored Code Diff**: Document the final code changes.

---

## Definition of Done
- A Markdown file named `code-review-audit.md` is committed inside your project directory.
- The review audit details accepted/rejected AI suggestions.
- The reviewed Java class compiles successfully, incorporating safe refactored changes (like parameter guards or variable adjustments).
