# Lab: StringCalculator TDD Practice

## Learning Objectives
- Apply the Test-Driven Development (TDD) cycle (Red-Green-Refactor) to build logic.
- Construct unit tests using JUnit 5 assertions following the Arrange-Act-Assert (AAA) pattern.
- Implement robust input validation handling inside Java utility classes.

---

## Setup Instructions
1. Open your Java development IDE (IntelliJ IDEA).
2. Create a new package directory under `src/test/java` named `exercise`.
3. Create a test class named `StringCalculatorTest.java`.
4. Create your main implementation class under `src/main/java/exercise` named `StringCalculator.java`.

---

## Step-by-Step Tasks

### Task 1: Initialize Setup and First Fail (Red Phase)
Write a test in `StringCalculatorTest.java` that asserts the behavior of an `add` method. The method should accept a comma-separated string of numbers and return their sum.

```java
package exercise;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

    @Test
    public void testAddSimpleNumbers() {
        // Arrange
        StringCalculator calc = new StringCalculator();

        // Act
        int result = calc.add("5,3");

        // Assert
        assertEquals(8, result);
    }
}
```
*Note:* This will not compile. watch it fail in your IDE.

---

### Task 2: Implement Code to Pass (Green Phase)
Create the class `StringCalculator.java` and implement the minimal logic to make the test compile and pass:

```java
package exercise;

public class StringCalculator {
    public int add(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0;
        }
        String[] tokens = input.split(",");
        int sum = 0;
        for (String token : tokens) {
            sum += Integer.parseInt(token.trim());
        }
        return sum;
    }
}
```
Run your test. It should turn green!

---

### Task 3: Build Out Additional Calculations
Following the TDD cycle (write test first, run red, implement logic, run green, refactor), implement the following features:

1.  **Multiplication (`multiply(String input)`):**
    -   *Test Case:* Input `"2,3,4"` -> Expected Output `24`.
    -   *Edge Case:* Input `""` or `null` -> Expected Output `0`.
2.  **Division (`divide(String input)`):**
    -   *Test Case:* Input `"10,2"` (Divide first by subsequent numbers) -> Expected Output `5`.
    -   *Edge Case:* Divisor is zero (`"10,0"`) -> Assert that the method throws an `IllegalArgumentException` with the message: `"Division by zero is undefined."`.

---

## Definition of Done
-   All three methods (`add`, `multiply`, `divide`) are fully implemented in `StringCalculator.java`.
-   A complete test suite exists in `StringCalculatorTest.java` verifying standard cases, empty inputs, and error validation cases.
-   All JUnit tests compile and pass successfully in the IntelliJ runner.
