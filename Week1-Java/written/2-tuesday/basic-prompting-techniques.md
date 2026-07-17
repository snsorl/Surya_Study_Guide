# Basic Prompting Techniques

## Learning Objectives
- Define Prompt Engineering and explain its role in professional software development.
- Construct prompts incorporating the 4 core components: Role, Context, Task, and Constraints.
- Contrast **Zero-Shot Prompting** with **Few-Shot Prompting** using Java examples.
- Apply negative constraints to prevent AI assistants from using unapproved packages or complex API patterns.

---

## Why This Matters
When using AI tools, the quality of the code you get out is directly proportional to the clarity and detail of the prompt you put in. This skill is called **Prompt Engineering**. 

If you prompt an AI with a generic query like: *"Write a method to calculate average score,"* you will get a generic, poorly targeted block of code that might use libraries you haven't imported or formatting styles that violate your company's guidelines. However, if you write a structured, constraint-driven prompt, the AI will generate precise, production-ready code that matches your existing codebase's style. Learning how to prompt effectively is a core engineering multiplier.

---

## The Concept

### What is a Prompt?
A prompt is the set of natural language instructions and code snippets you feed into an LLM. An effective coding prompt does not just ask for a feature; it sets a stage. A professional prompt incorporates four core components:

```
+------------------------------------------------------------+
| 1. ROLE: Defines who the AI is (e.g. "Senior Java Dev")   |
| 2. CONTEXT: Explains environment (e.g. "Java 17, Array")   |
| 3. TASK: Defines action (e.g. "Write findIndex method")   |
| 4. CONSTRAINTS: Defines limits (e.g. "No streams, trim")   |
+------------------------------------------------------------+
```

1.  **Role (Persona)**: Instructing the AI how to act (e.g., *"Act as a senior Java developer specializing in secure backend API validation."*). This primes the model to output production-grade code rather than academic snippets.
2.  **Context**: Providing background details (e.g., *"We are writing a class inside package com.infosys.pricing. We are using Java 17 and working with raw arrays."*).
3.  **Task**: The specific programming action required (e.g., *"Create a static method that calculates the median value of a sorted array."*).
4.  **Constraints**: Boundaries the AI must follow (e.g., *"Do not use Java streams or external collections. Use standard loops only."*).

---

### Zero-Shot Prompting
**Zero-Shot Prompting** means asking the AI to perform a task without providing any examples of the expected code structure, inputs, or outputs. You rely entirely on the model's pre-trained public data.
*   **Best for**: Common, standardized algorithms (e.g., bubble sort, calculating a factorial, reversing a string).
*   **Limitation**: Often generates code containing styles, naming conventions, or libraries that do not match your project.

---

### Few-Shot Prompting
**Few-Shot Prompting** means providing **one or more reference examples** of the input formats, output formats, or programming conventions you expect inside the prompt. This anchors the model's prediction engine, forcing it to output code that perfectly mimics your project's patterns.
*   **Best for**: Custom string formatting, adhering to specific corporate exception styles, or matching custom class patterns.

---

## Code Example: Zero-Shot vs. Few-Shot Comparison
Let's see how the generated code differs when we apply Zero-Shot vs. Few-Shot techniques for a student grade formatting utility.

### Case A: The Zero-Shot Approach
*   **The Prompt**:
    > *"Write a Java method to print a student's score and status."*
*   **The AI Output**:
    ```java
    public static void printStudent(String name, int score) {
        String status = (score >= 60) ? "Pass" : "Fail";
        System.out.println("Student: " + name + " | Score: " + score + " | Status: " + status);
    }
    ```
*   **Critique**: The code is syntactically fine, but the output format is generic and does not match our report constraints.

---

### Case B: The Few-Shot Approach
*   **The Prompt**:
    > *"Act as a backend reporting tool. Write a Java method named printStudentReport. The output must match our reporting standards. Here are two examples of our required output format:*
    >
    > *Example Input 1: name = \"Alice\", score = 95, passed = true*
    > *Example Output 1:*
    > *[STUDENT REPORT]*
    > *ID: Alice*
    > *Score: 095/100*
    > *Status: APPROVED*
    >
    > *Example Input 2: name = \"Bob\", score = 8, passed = false*
    > *Example Output 2:*
    > *[STUDENT REPORT]*
    > *ID: Bob*
    > *Score: 008/100*
    > *Status: REJECTED*
    >
    > *Write a Java method that accepts name, score, and passed status, and prints the report matching this exact structure."*

*   **The AI Output**:
    ```java
    public class ReportUtility {

        /**
         * Formats and prints a student score report matching report standards.
         * Leverages decimal formatting to pad scores to three digits.
         */
        public static void printStudentReport(String name, int score, boolean passed) {
            // Guard check for invalid name input
            String cleanName = (name == null) ? "UNKNOWN" : name.trim();
            
            // Format score to be 3-digit padded (e.g. 008, 095)
            String formattedScore = String.format("%03d", score);
            
            // Map boolean status to APPROVED/REJECTED terms
            String status = passed ? "APPROVED" : "REJECTED";

            System.out.println("[STUDENT REPORT]");
            System.out.println("ID: " + cleanName);
            System.out.println("Score: " + formattedScore + "/100");
            System.out.println("Status: " + status);
        }

        public static void main(String[] args) {
            printStudentReport("Alice", 95, true);
            printStudentReport("Bob", 8, false);
        }
    }
    ```

*   **Critique**: The Few-Shot prompt anchored the AI to use `String.format("%03d", score)` to pad the scores and match the uppercase status mapping precisely, saving you from manual editing.

---

## Summary
- **Prompt Engineering** is the practice of crafting descriptive inputs (prompts) to get optimal code outputs from LLMs.
- A high-quality prompt contains: **Role, Context, Task, and Constraints**.
- **Zero-Shot Prompting** provides no reference templates, which is suitable for standard math/logical helpers but risky for specific formats.
- **Few-Shot Prompting** includes reference examples of inputs and outputs to enforce styling consistency.
- **Negative Constraints** (e.g., *"Do not use Java Streams"*) prevent the AI from suggesting unapproved APIs.

---

## Additional Resources
- [Prompt Engineering Guide - DAIR.AI](https://www.promptingguide.ai/)
- [Few-Shot Prompting Explained - Baeldung on CS](https://www.baeldung.com/cs/few-shot-prompting)
- [How to Prompt AI for Java Code - Microsoft Learn](https://learn.microsoft.com/)