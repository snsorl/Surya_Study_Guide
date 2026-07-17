# AI Knowledge Lookup and Explanation

## Learning Objectives
- Use AI coding assistants as interactive personal tutors to explain unfamiliar Java language concepts.
- Formulate structured queries utilizing educational strategies (analogies, side-by-side tables, minimal snippets).
- Retrieve syntax details and method APIs for standard Java libraries from LLMs.
- Implement a verification workflow using official Oracle documentation to validate AI claims.

---

## Why This Matters
When learning Java, you will constantly encounter unfamiliar APIs, nested library methods, and complex syntax structures (like static initializers or interface defaults). In the past, developers had to spend hours searching through forums, search engines, and index pages to locate code references. 

AI assistants change this. They act as interactive personal tutors that can explain complex concepts on demand. If a topic is confusing, you can ask the AI to explain it using a kitchen analogy, write the simplest possible code snippet to illustrate it, or build a comparison table. However, because AI engines are probabilistic, they can easily hallucinate incorrect information or suggest deprecated APIs. You must learn how to prompt for explanations effectively and verify the outputs against official specifications.

---

## The Concept

### Query Strategies for Concept Explanations
When asking an AI to explain a programming concept, do not just ask: *"What is static?"* 
Instead, instruct the AI to use specific educational strategies:

#### 1. Requesting Analogies
Analogies map unfamiliar concepts to familiar physical experiences:
> *Prompt*: *"Explain the relationship between Stack and Heap memory using a restaurant kitchen and food storage warehouse analogy."*

#### 2. Requesting Minimal Code Snippets
Ask for the absolute minimum code required to understand a concept, removing confusing boilerplate:
> *Prompt*: *"Show the absolute simplest class file demonstrating upcasting and downcasting in Java. Do not add comments or print statements, just show the core syntax."*

#### 3. Requesting Comparative Tables
Tables help you visualize distinctions between two similar technologies:
> *Prompt*: *"Create a Markdown comparison table comparing abstract classes vs interfaces in Java 17. Include columns for inheritance, constructors, variables, and methods."*

---

### Prompting for Syntax and Library Lookups
When looking up how to use a specific library (e.g., parsing dates, formatting numbers), formulate your prompt with three requirements:
*   **Target Language & Version**: (e.g., *"In Java 17..."*)
*   **The Target Operation**: (e.g., *"...how do I split a string by comma and trim whitespaces?"*)
*   **Library Constraints**: (e.g., *"...using only the standard java.lang library."*)

---

### The Verification Workflow
AI models are text predictors, not factual compilers. They can invent class names or suggest insecure, deprecated methods. **Always verify critical claims:**
1.  **Check Official Sources**: Cross-reference the AI's suggestions with the **Oracle Java SE API Documentation**.
2.  **Verify deprecation status**: Make sure the suggested methods do not trigger compiler deprecation warnings.
3.  **Run Scratch Tests**: Compile and run the code in a small scratch script first before adding it to your main codebase.

---

## Code Example: Number Formatting Lookup
Let's see an example of a knowledge lookup query and its verified implementation.

### The Lookup Query:
> *"In Java 17, how do I format a double value (representing money) to always display exactly two decimal places, padded with zeros if necessary? Show the two standard approaches using DecimalFormat and String.format, and explain their differences."*

### The AI's Explanation (Verified):
There are two primary ways to format decimals in Java:
1.  **`String.format()`**: Returns a formatted string. It is simple, thread-safe, and ideal for quick prints.
2.  **`java.text.DecimalFormat`**: A specialized formatter class. It is highly customizable (supports currency symbols, rounding modes) but is not thread-safe.

### The Verified Implementation:
Here is the clean, compilable program implementing and explaining both lookup solutions:

```java
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class FormattingLookupDemo {
    public static void main(String[] args) {
        double price1 = 125.5;   // Needs to display as 125.50
        double price2 = 99.999;  // Needs to round and display as 100.00
        double price3 = 0.7;     // Needs to display as 0.70

        System.out.println("=== Approach 1: Using String.format() ===");
        // The format specifier "%.2f" means:
        // %: start format specifier
        // .2: format to exactly two decimal places
        // f: format as a floating-point decimal
        System.out.println("Formatted price 1: " + String.format("%.2f", price1)); // "125.50"
        System.out.println("Formatted price 2: " + String.format("%.2f", price2)); // "100.00" (rounded up)
        System.out.println("Formatted price 3: " + String.format("%.2f", price3)); // "0.70"

        System.out.println("\n=== Approach 2: Using DecimalFormat ===");
        // The pattern "0.00" enforces:
        // 0: print digit, or pad with zero if digit is missing
        // .: decimal separator
        DecimalFormat formatter = new DecimalFormat("0.00");
        
        // Enforcing Rounding Mode explicitly (e.g. HALF_UP rounds 0.5 up)
        formatter.setRoundingMode(RoundingMode.HALF_UP);

        System.out.println("Formatted price 1: " + formatter.format(price1)); // "125.50"
        System.out.println("Formatted price 2: " + formatter.format(price2)); // "100.00"
        System.out.println("Formatted price 3: " + formatter.format(price3)); // "0.70"
    }
}
```

---

## Summary
- AI coding assistants function as interactive tutors to explain complex Java structures.
- Optimize concept prompts using **analogies**, requesting **minimal code examples**, and formatting comparisons inside **Markdown tables**.
- Build syntax lookups by specifying the **Java version** and **library constraints**.
- Always verify AI-suggested classes and methods against **Oracle SE Documentation** to avoid deprecated or hallucinated APIs.

---

## Additional Resources
- [Oracle Java SE 17 API Documentation - Official Spec](https://docs.oracle.com/en/java/javase/17/docs/api/index.html)
- [Baeldung Java decimal formatting tutorial](https://www.baeldung.com/java-decimal-format)
- [Prompting AI for Technical Concepts - Microsoft Developer Guides](https://developer.microsoft.com/)