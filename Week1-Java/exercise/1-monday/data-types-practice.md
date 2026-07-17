# Exercise: Primitives and Reference Variables

## Objective
Apply your understanding of Java's 8 primitive data types, basic type casting (implicit and explicit), and how reference variables store object locations in memory.

---

## Prerequisites
- Completed Monday's reading materials on primitive types, memory allocations, and operators.
- Created and verified your IntelliJ Java project.

---

## Step-by-Step Instructions

### Step 1: Declare the 8 Primitives
1.  Open your IntelliJ project.
2.  Create a new Java class named `PrimitivePractice` inside the `src` folder.
3.  Add a `main` method to the class.
4.  Declare and initialize one variable for **each** of the 8 primitive data types using appropriate values:
    *   `byte`, `short`, `int`, `long`
    *   `float`, `double`
    *   `char`
    *   `boolean`
5.  Print all 8 variables to the console with clear labels.

---

### Step 2: Practice Casting (Implicit & Explicit)
1.  **Implicit Cast (Widening)**:
    *   Declare a `byte` variable named `smallVal` and assign it a value of `50`.
    *   Declare an `int` variable named `largeVal` and assign `smallVal` to it directly. Print both values to show the conversion compiles without issues.
2.  **Explicit Cast (Narrowing)**:
    *   Declare a `double` variable named `decimalVal` and assign it a value of `99.99`.
    *   Declare an `int` variable named `truncatedVal`. Try to assign `decimalVal` to it directly. Notice the IDE compiler warning!
    *   Correct the warning by adding an explicit cast operator: `(int) decimalVal`.
    *   Print both values and note in a comment what happened to the decimal digits after the cast.

---

### Step 3: Reference Variable Mutation Lab
1.  Create a primitive integer `a = 10` and copy it: `int b = a;`. Mutate `b = 20`. Print both to show that `a` remains `10` (values are copied).
2.  Declare an integer array (reference type):
    ```java
    int[] firstArray = new int[]{10, 20, 30};
    ```
3.  Declare a second array variable and assign the first to it:
    ```java
    int[] secondArray = firstArray;
    ```
4.  Modify the first element of the second array:
    ```java
    secondArray[0] = 999;
    ```
5.  Print `firstArray[0]` and `secondArray[0]` to the console. Explain in code comments why changing `secondArray` affected `firstArray`.

---

## Definition of Done
- A compiled, running class named `PrimitivePractice` exists.
- The console prints variables for all 8 primitive types.
- The cast demo executes safely, showing the truncated value of `99.99` is `99`.
- The reference variable mutation runs, printing `999` for both arrays.
- Explanatory comments are included in the source file describing stack value copies vs. heap memory pointer references.
