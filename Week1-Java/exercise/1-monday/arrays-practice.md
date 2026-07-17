# Exercise: Array Iteration & Value Analysis

## Objective
Apply primitive single-dimensional arrays, loop iterations, and math operators to compile and analyze numeric datasets.

---

## Prerequisites
- Completed Monday's reading materials on arrays and looping algorithms (`for`, enhanced `for-each`).

---

## Step-by-Step Instructions

### Step 1: Create the Class and Declare the Array
1.  In IntelliJ, create a new class named `ArrayAnalyzer` in the `src` folder.
2.  Add a `main` method.
3.  Declare and initialize a single-dimensional `int` array of **capacity 10**.
4.  Populate the array index values with 10 random integers between **1 and 100**. You can do this manually or dynamically inside a loop.

---

### Step 2: Implement Calculation Loops
Write code to scan the populated array and compute the following metrics:
1.  **Sum**: The mathematical addition of all 10 values.
2.  **Average**: The floating-point decimal mean of the values (cast the sum to double to ensure precise decimal output!).
3.  **Minimum**: The smallest value inside the array.
4.  **Maximum**: The largest value inside the array.

*Constraint*: Do **not** use built-in Java library sorting methods (like `Arrays.sort()`). You must implement raw comparison logic inside standard loops.

---

### Step 3: Log the Results
1.  Print the original elements array values in a single line (e.g. `[25, 45, 12...]`).
2.  Print the calculated Sum, Average, Minimum, and Maximum.
3.  Review your loop limit bounds to guarantee the code does not throw an `ArrayIndexOutOfBoundsException`.

---

## Definition of Done
- A compiled, running class named `ArrayAnalyzer` exists.
- The program prints the populated array values.
- The calculations run correctly without using java utility sorting libraries.
- The output displays:
  *   The total sum of elements.
  *   The precise double-precision average value.
  *   The exact minimum and maximum bounds.
