# Exercise: MathUtils Method Implementation

## Objective
Apply method declarations, parameters passing, return statements, static contexts, and overloading to build a reusable utility class.

---

## Prerequisites
- Completed Tuesday's reading materials on method syntax, parameters, static contexts, and invocation.

---

## Step-by-Step Instructions

### Step 1: Create the Class
1.  In your IntelliJ project, create a new Java class named `MathUtils` inside the `src` folder.
2.  Do **not** add instance variables. This will be a pure utility class composed of static methods.

---

### Step 2: Implement 5 Static Methods
Write the following methods in your class:

#### 1. Factorial Calculator
- **Signature**: `public static long factorial(int n)`
- **Behavior**: Returns the product of all positive integers less than or equal to `n`. Returns `1` if `n <= 1`.

#### 2. Prime Number Validator
- **Signature**: `public static boolean isPrime(int n)`
- **Behavior**: Returns `true` if `n` is prime, and `false` otherwise. (Check if `n` is divisible by any integer between 2 and `Math.sqrt(n)`).

#### 3. Temperature Converter
- **Signature**: `public static double celsiusToFahrenheit(double celsius)`
- **Behavior**: Returns the temperature converted to Fahrenheit using formula: `(celsius * 9/5) + 32`.

#### 4. Array Maximum Value Searcher
- **Signature**: `public static int findMax(int[] arr)`
- **Behavior**: Returns the largest integer value in the array. Return `0` (or print warning) if array is empty or null.

#### 5. Overloaded Array Maximum Value Searcher (Double Precision)
- **Signature**: `public static double findMax(double[] arr)`
- **Behavior**: Overloads `findMax`. Returns the largest double value in the array.

---

### Step 3: Write the Runner Code
1.  Add a `main` method to `MathUtils`.
2.  Write code that calls each of the 5 static methods with test values and prints the outputs.
    *   *Example*: `System.out.println("Factorial of 5: " + MathUtils.factorial(5));`

---

## Definition of Done
- A compiled, running class named `MathUtils` exists.
- The class contains 5 static methods with the exact signatures specified.
- The compiler successfully resolves the overloaded `findMax` calls based on the argument array type (int vs double).
- The `main` method prints correct results for all test inputs.
