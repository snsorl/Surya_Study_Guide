# AI-Assisted Test-Driven Development (AI TDD)

## Learning Objectives
- Leverage generative AI to scaffold unit test stubs and structures.
- Construct prompts that direct AI to suggest boundary values and edge cases for a given behavior.
- Use AI to audit existing test suites and identify gaps in test coverage.
- Analyze the technical and logical risks of accepting AI-generated test code without manual validation.

---

## Why This Matters
One of the most common reasons developers skip unit testing or neglect TDD is the time required to write "boilerplate" setup and assertion code. Writing tests first can feel slow when you are manually typing out every mock configuration and every boundary assertion. 

Generative AI is an incredibly powerful catalyst for this process. By utilizing AI as an assistant, you can generate comprehensive test files in seconds, brainstorm edge cases that you might have overlooked, and identify logic holes in your classes. 

However, this speed comes with severe risks. If you blindly accept AI-generated tests without verifying their assertions, you risk creating a false sense of security. An AI can easily write a test that passes but asserts the wrong logic, or worse, hallucinate a test structure that ignores critical failure paths. In enterprise development, verifying the correctness of your tests is just as important as writing the production code.

---

## The Concept

AI-Assisted Test-Driven Development is about using AI to execute the repetitive, cognitive, and structural tasks of testing, while keeping the human developer in control as the validator.

### 1. Generating Test Stubs
When starting a new feature, you can describe the requirements to an AI and ask it to generate the JUnit 5 skeleton. This saves you from typing out package imports, test annotations, and method declarations.

**Best Practice:** Define the method signature and expected behaviors in detail, then prompt the AI to generate tests adhering to the Arrange-Act-Assert (AAA) structure.

### 2. Brainstorming Edge Cases and Boundary Values
Humans are naturally biased to write "happy path" tests—verifying that the code works under perfect conditions. AI excels at analyzing a method's parameters and suggesting logical exceptions:
- **Numeric parameters:** What if the input is `0`, negative numbers, `Integer.MAX_VALUE`, or floating-point precision differences?
- **String parameters:** What if the input is `null`, an empty string `""`, whitespaces `"   "`, or special characters?
- **Collection parameters:** What if a list is empty, contains null elements, or has duplicates?

### 3. Reviewing Test Coverage
You can paste your production code alongside your test code into an AI and ask: *"What logic paths are not covered by these tests?"* The AI will parse the conditional statements (if-else, switch, try-catch) and outline exactly which branches are missing assertions.

### 4. Critical Risks of Relying on AI Tests
- **The "Passing Test" Illusion:** AI can generate tests that run and turn green (pass), but contain incorrect assertions. For example, it might write `assertEquals(0, result)` when the correct business requirement should be `assertEquals(1, result)`. If you do not verify the assertion, you may commit buggy code that your test suite actively approves.
- **Hallucinated Framework APIs:** LLMs can occasionally generate testing methods or annotations that do not exist in the version of JUnit or Mockito you are using, leading to compiler errors.
- **Context Windows & Mocking Oversimplification:** For complex classes with database configurations, AI may oversimplify the setup or mock objects incorrectly, masking real integration issues that will fail in production.

---

## Code Example

Let's look at how to prompt an AI to generate edge cases for a specific method.

### The Target Method
Imagine you have this method, which calculates the discount for a customer order:

```java
public double calculateDiscount(double orderTotal, int loyaltyPoints) {
    if (orderTotal < 0) {
        throw new IllegalArgumentException("Total cannot be negative.");
    }
    if (loyaltyPoints >= 100) {
        return orderTotal * 0.15; // 15% discount
    } else if (loyaltyPoints >= 50) {
        return orderTotal * 0.10; // 10% discount
    }
    return 0.0;
}
```

### The Prompt to the AI
```
You are an expert QA Engineer. Generate a JUnit 5 test class for the calculateDiscount method above.
Follow these rules:
1. Apply the Arrange-Act-Assert pattern.
2. Group tests by functionality and include happy paths, negative boundaries, and edge cases.
3. Assert exceptions using assertThrows.
4. Do not use any emojis in your response.
```

### The AI's Output (Verified by Developer)
The developer reviews the generated test structure, compiles it, and validates that the logic matches expectations:

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountCalculatorTest {

    private DiscountCalculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new DiscountCalculator();
    }

    // --- Happy Paths ---
    @Test
    public void testCalculateDiscount_ShouldReturnFifteenPercent_WhenPointsAreOneHundred() {
        // Arrange
        double total = 200.00;
        int points = 100;
        double expected = 30.00;

        // Act
        double actual = calculator.calculateDiscount(total, points);

        // Assert
        assertEquals(expected, actual, 0.001);
    }

    // --- Edge Cases / Boundaries ---
    @Test
    public void testCalculateDiscount_ShouldReturnTenPercent_WhenPointsAreExactlyFifty() {
        // Arrange
        double total = 100.00;
        int points = 50; // Exact boundary for 10%
        double expected = 10.00;

        // Act
        double actual = calculator.calculateDiscount(total, points);

        // Assert
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testCalculateDiscount_ShouldThrowException_WhenTotalIsNegative() {
        // Arrange
        double negativeTotal = -5.00;
        int points = 10;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateDiscount(negativeTotal, points);
        });
    }
}
```

---

## Summary
- AI is a powerful accelerator for TDD, helping developers generate test structures, find edge cases, and analyze coverage.
- **Edge cases** to request from AI include empty strings, nulls, negative numbers, and boundary values.
- **Blind acceptance** of AI code is dangerous; developers must verify that assertions match business requirements.
- The developer remains the final validator—ensure tests compile, run, and represent correct logic.

---

## Additional Resources
- [OWASP Prompt Engineering Guide for Developers](https://owasp.org/www-project-top-10-for-large-language-model-applications/)
- [Google AI Guide: Designing Prompts for Code Generation](https://ai.google/resources/)
- [JUnit 5 Dynamic Tests Documentation](https://junit.org/junit5/docs/current/user-guide/#writing-tests-dynamic-tests)
