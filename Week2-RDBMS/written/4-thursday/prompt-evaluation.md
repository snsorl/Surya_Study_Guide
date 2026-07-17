# Formal Prompt Evaluation and Scoring

## Learning Objectives
- Explain the role of systematic evaluation in prompt engineering workflows.
- Construct a formal Prompt Evaluation Matrix utilizing specific scoring criteria.
- Evaluate AI code completions across four core metrics: Syntax Accuracy, Logic/Completeness, Formatting Compliance, and Hallucination Risk.
- Utilize diagnostic scores to iteratively refine and optimize prompt instructions.

---

## Why This Matters
When using AI to generate code, it is easy to fall into the trap of subjective evaluation: you run a prompt, glance at the code, and if it "looks okay," you accept it. 

However, in professional software engineering, subjective reviews are not sufficient. An AI-generated SQL script might look correct but contain subtle logical flaws (like off-by-one errors in index configurations or incorrect constraint names) that are only caught when the system fails under heavy traffic.

To integrate AI tools safely and reliably, you must establish a **Formal Prompt Evaluation** framework. By scoring AI outputs against objective, measurable criteria (such as syntax compliance, logical completeness, and security risk), you can determine if a prompt is ready for production, track AI quality over time, and systematically isolate which instructions need refinement.

---

## The Concept

### 1. What is Prompt Evaluation?
**Prompt Evaluation** is the process of testing and scoring the outputs of a prompt template against a set of predefined criteria. This is similar to writing unit tests for your application code; it ensures that your prompts consistently produce safe, accurate, and correct results.

### 2. The Four Core Evaluation Metrics

To score code generation tasks, developers evaluate four key areas:

1.  **Syntax Accuracy (Execution Check):** Does the generated code compile and run without syntax errors?
2.  **Logic and Completeness (Requirement Check):** Does the code fulfill all requirements specified in the prompt? Did it cover edge cases, validation rules, and structural boundaries correctly?
3.  **Formatting & Constraint Compliance (Structure Check):** Did the model follow formatting instructions (e.g., returning only raw SQL, matching a JSON schema, or avoiding emojis)?
4.  **Hallucination Risk (API Verification):** Did the model invent non-existent APIs, libraries, framework annotations, or SQL syntax?

---

## The Prompt Evaluation Matrix

To standardize evaluations, teams use a 5-point scoring scale across these metrics:

| Score | Rating | Criteria |
|---|---|---|
| **5** | Excellent | Perfect execution. Zero errors. Fully compliant with all constraints and formatting rules. |
| **4** | Good | Executes correctly, but formatting has minor issues, or a minor optimization was missed. |
| **3** | Acceptable | Runs, but requires manual adjustments by the developer to fix minor logical gaps. |
| **2** | Poor | Fails to compile or has major security/logical gaps. Requires significant rewriting. |
| **1** | Failed | Completions are completely incorrect, highly hallucinated, or refuse to generate. |

---

## Code Example: Scoring an AI-Generated DAO

Suppose we prompt an AI to generate a Java JDBC method that retrieves a customer by ID.

### The AI's Output:
```java
public Customer getCustomer(int id, Connection conn) throws SQLException {
    String sql = "SELECT * FROM customers WHERE id = " + id; // Hardcoded string concatenation!
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    if (rs.next()) {
        return new Customer(rs.getInt(1), rs.getString(2)); // Brittle column indices!
    }
    return null;
}
```

---

### The Evaluation Entry:

Below is how the developer documents the evaluation of this completion:

-   **Prompt ID:** PR-JDBC-001 (Fetch Customer)
-   **Model:** Gemini 3.5 Flash

| Metric | Score | Diagnostic Observations |
|---|---|---|
| **Syntax Accuracy** | **5/5** | Code compiles and executes correctly without syntax errors. |
| **Logic & Completeness** | **3/5** | Fails to handle database `NULL` mapping, and uses brittle column indices (`getInt(1)`) instead of column names, creating future schema maintenance bugs. |
| **Formatting Compliance**| **5/5** | Returned only the Java method as requested. No conversational fluff or emojis. |
| **Security & Safety** | **1/5** | **CRITICAL FAILURE:** Used string concatenation to build the SQL statement, introducing a severe SQL Injection vulnerability. |
| **Hallucination Risk** | **5/5** | Used valid, standard JDBC imports and classes. No API hallucinations. |

-   **Overall Status:** **REJECTED (Score: 3.8/5, Security Fail)**.
-   **Remediation Action:** Refine prompt instructions to explicitly demand: *"You must use PreparedStatement and bind parameters to prevent SQL injection. You must retrieve ResultSet values using column name strings, not index integers."*

---

## Summary
-   **Prompt Evaluation** systematically scores AI outputs against objective metrics rather than subjective glances.
-   The four primary metrics are **Syntax Accuracy, Logic/Completeness, Formatting Compliance, and Hallucination/Security Risk**.
-   Use a **5-point evaluation matrix** to document and track prompt quality over time.
-   Identify and analyze low-scoring metrics to **iteratively refine** prompt instructions for production readiness.

---

## Additional Resources
-   [Evaluating LLM Outputs - Prompt Engineering Guide](https://www.promptingguide.ai/development/evaluating)
-   [Google Cloud: Evaluating Language Model Quality](https://cloud.google.com/vertex-ai/docs/generative-ai/evaluate-models)
-   [OWASP Database Security Coding Standards](https://cheatsheetseries.owasp.org/cheatsheets/Secure_Coding_Cheat_Sheet.html)
