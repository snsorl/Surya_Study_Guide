# Exercise: The Robust Division Calculator

## Objective
Build an interactive console division calculator that applies multiple catch blocks, custom unchecked exceptions, and validation guards to handle arithmetic errors and input formatting anomalies safely.

---

## Prerequisites
- Completed Friday's reading materials on exception classes, throw/throws syntax, catch routing, and resource management.

---

## Step-by-Step Instructions

### Step 1: Create the Custom Exception
1.  In IntelliJ, create a new package named `com.cohort.exceptions`.
2.  Create a custom exception class named **`InvalidInputException`** that extends **`RuntimeException`**.
3.  Add a constructor taking a `String message` parameter and routing it using `super(message)`.

---

### Step 2: Implement the Calculator Method
1.  Create a class named `DivisionCalculator`.
2.  Write a method with the following signature:
    ```java
    public static int divide(String numeratorStr, String denominatorStr) throws InvalidInputException
    ```
3.  Inside `divide()`:
    *   Verify if either string argument is null or empty. If so, throw a new `InvalidInputException("Input arguments cannot be null or empty.")`.
    *   Attempt to parse both parameters to integers. If parsing fails, catch the resulting `NumberFormatException` and throw a new `InvalidInputException("Inputs must be valid integers. Parsing failed.")`.
    *   Attempt the integer division: `int result = numerator / denominator;`. If the denominator is `0`, catch the resulting `ArithmeticException` and throw a new `InvalidInputException("Division by zero is mathematically undefined.")`.
    *   Return the division result.

---

### Step 3: Write the Try-Catch Runner Code
1.  Add a `main` method to `DivisionCalculator`.
2.  Write a try-catch block executing `divide()` against the following test matrices:
    *   `divide("100", "5")` -> Expected: `20`
    *   `divide("100", "0")` -> Expected: custom error thrown
    *   `divide("abc", "5")` -> Expected: custom error thrown
    *   `divide(null, "5")` -> Expected: custom error thrown
3.  Catch the custom `InvalidInputException` and print the exception's message inside the catch block.
4.  Implement a `finally` block that prints: `"[CALCULATOR] Execution cycle complete."` to verify that cleanup instructions run regardless of whether the division succeeded or failed.

---

## Definition of Done
- The custom exception `InvalidInputException` extends `RuntimeException`.
- The `divide` method correctly catches system anomalies (`NumberFormatException`, `ArithmeticException`) and transforms them into your custom exception type.
- The `main` method runs all four test inputs safely without crashing the JVM.
- The console displays details of the blocked input errors.
- The `finally` block executes and prints to the console on every test run.
