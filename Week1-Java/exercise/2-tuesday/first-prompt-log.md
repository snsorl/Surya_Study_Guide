# Exercise: Your First Prompt Log Journal

## Objective
Establish metacognitive prompt-engineering habits by creating your first prompt log entry. You will prompt an AI to generate a Java method, critique the initial code, refine the instructions, and log the results.

---

## Prerequisites
- Completed Tuesday's reading materials on AI pair programming, prompting techniques, and prompt logs.

---

## Step-by-Step Instructions

### Step 1: Select the Coding Target
You need to generate a Java utility method that checks if a given `String` is a valid palindrome (reads the same forwards and backwards, ignoring casing and non-alphanumeric characters, e.g., `"Racecar"` is a palindrome).

---

### Step 2: Write the Initial Prompt
1.  Open your AI coding assistant panel in IntelliJ (or your browser interface).
2.  Write an **imperfect, basic prompt** requesting the palindrome method.
    *   *Example*: *"Write a Java method to check if a string is a palindrome."*
3.  Copy and save the AI's generated code output.

---

### Step 3: Audit and Identify Flaws
Inspect the AI's code. Answer the following questions:
- What happens if the input string is `null`? Does the code crash with a `NullPointerException`?
- What happens if the input contains spaces, punctuation, or mixed casing? (e.g. `"A man, a plan, a canal: Panama"`). Does the code evaluate this correctly as a palindrome?
- Did the AI use complex streams or libraries that you have not studied yet?

---

### Step 4: Write the Refined Prompt
1.  Formulate a **refined prompt** incorporating constraints to resolve the identified flaws.
    *   *Example*: *"Act as a secure Java compiler. Write a public static method named isPalindrome that takes a String. Constraints: 1. If the input is null, return false. 2. Remove all non-alphanumeric characters and ignore character casing. 3. Do not use advanced streams, use simple character iteration."*
2.  Review and copy the new code output.

---

### Step 5: Save the Prompt Log
Create a Markdown file named **`prompt-log-palindromes.md`** inside your project directory. Populate it using the template below.

---

## Prompt Log Template
Copy this format into your `prompt-log-palindromes.md` file:

```markdown
# AI Prompt Log: Palindrome Checker

## 1. Initial Attempt
- **Context**: Palindrome validation algorithm.
- **Prompt**: [Insert your initial prompt here]
- **Generated Code**:
```java
// [Paste initial code output here]
```

## 2. Critique & Evaluation
- **Security Check**: [Does it crash on null? Yes/No]
- **Logic Constraints**: [Does it handle casing and punctuation? Yes/No]
- **Redundancy/Complexity**: [Does it use advanced streams?]

## 3. Refined Attempt
- **Revised Prompt**: [Insert your refined prompt containing constraints]
- **Refined Code**:
```java
// [Paste refined code output here]
```

## 4. Final Evaluation
- [Briefly explain why the refined code is safer and more production-ready]
```

---

## Definition of Done
- A Markdown file named `prompt-log-palindromes.md` exists in your workspace.
- The log details the initial prompt, code output, critique, refined prompt, and final code.
- The final code successfully checks for null values, handles capitalization/punctuation, and is documented.
