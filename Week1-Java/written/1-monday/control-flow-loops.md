# Control Flow: Loops

## Learning Objectives
- Automate repetitive tasks using `for`, `while`, and `do-while` loops.
- Explain the key differences and select the correct loop structure for a given task.
- Iterate over array elements cleanly using the Enhanced `for-each` loop.
- Interrupt and adjust loop execution using `break` and `continue` statements.
- Identify, prevent, and troubleshoot infinite loops.

---

## Why This Matters
Computers excel at repeating operations. If you need to print a receipt for every transaction in a daily log, scan an array of a thousand prices to find the cheapest item, or run a game engine until the player clicks "Quit", you cannot write the code line-by-line. 

You need **loops**. Loops allow you to execute a block of code multiple times, controlled by a logical boundary. Choosing the correct loop structure (such as an index-safe `for-each` loop instead of a manual index loop) reduces boilerplate code and prevents critical bugs like "off-by-one" errors that lead to system crashes.

---

## The Concept

### 1. The Standard `for` Loop
The `for` loop is ideal when you **know in advance** how many times you need to repeat an execution (count-controlled).

#### Syntax:
```java
for (initialization; condition; update) {
    // Code to repeat
}
```
*   **Initialization**: Executed once before the loop begins. Usually declares a counter variable.
*   **Condition**: Evaluated before each iteration. If `true`, the loop body runs. If `false`, the loop exits.
*   **Update**: Executed at the end of each iteration, usually incrementing or decrementing the counter.

```java
for (int i = 0; i < 5; i++) {
    System.out.println("Iteration: " + i); // Prints values 0, 1, 2, 3, 4
}
```

---

### 2. The `while` Loop
The `while` loop evaluates a boolean condition *before* executing the loop body. It is ideal when you **do not know exactly how many iterations** are required, but you know the condition that should stop it (condition-controlled).

#### Syntax:
```java
while (condition) {
    // Code to repeat
}
```
```java
int balance = 100;
while (balance > 10) {
    balance -= 30; // Repeats until balance falls to 10 or below
}
```

---

### 3. The `do-while` Loop
Similar to the `while` loop, but with one key difference: it evaluates the condition **after** the loop body runs. This guarantees that the code block **always executes at least once**.

#### Syntax:
```java
do {
    // Code to repeat
} while (condition); // Note the required semicolon
```
```java
int userSelection;
do {
    userSelection = promptUserForMenuSelection();
} while (userSelection != 4); // Loop will prompt at least once
```

---

### 4. The Enhanced `for-each` Loop
Introduced in Java 5, the enhanced `for` loop (commonly called the `for-each` loop) provides a streamlined way to iterate over arrays and collections. It completely eliminates manual index handling, preventing off-by-one index mistakes.

#### Syntax:
```java
for (DataType variableName : arrayName) {
    // Use variableName
}
```
```java
int[] grades = { 95, 88, 91 };
for (int grade : grades) {
    System.out.println("Grade: " + grade);
}
```
> [!NOTE]
> Use the traditional `for` loop if you need to modify array elements, iterate backward, or access the index values. Use the `for-each` loop if you simply need to read or search elements sequentially.

---

### Loop Control: `break` and `continue`
You can override normal loop behavior using control keywords:

*   **`break`**: Immediately exits the enclosing loop.
*   **`continue`**: Skips the remaining code in the *current* iteration and jumps directly to the next loop check/increment.

```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue; // Skips printing 3
    }
    if (i == 5) {
        break;    // Exits the loop early before printing 5
    }
    System.out.println(i); // Prints 1, 2, 4
}
```

---

### The Danger of Infinite Loops
An infinite loop occurs when the loop condition always evaluates to `true`. This causes the program to hang, locking up system CPU resources until the process is forced to terminate.

```java
// Infinite loop bug: condition never becomes false because counter isn't updated
int counter = 0;
while (counter < 5) {
    System.out.println("Hello");
    // Forgot to write: counter++;
}
```

---

## Code Example
The following class illustrates all loop types and control statements:

```java
public class LoopsDemo {
    public static void main(String[] args) {
        // 1. Classic for loop vs. Enhanced for-each
        double[] transactionAmounts = {19.99, 4.50, 99.00, 120.00};
        
        System.out.println("--- Traditional For Loop ---");
        for (int i = 0; i < transactionAmounts.length; i++) {
            System.out.println("Index " + i + ": $" + transactionAmounts[i]);
        }

        System.out.println("\n--- Enhanced For-Each Loop ---");
        double totalSum = 0;
        for (double amount : transactionAmounts) {
            totalSum += amount;
        }
        System.out.println("Total Transaction Sum: $" + totalSum);

        // 2. While vs Do-While
        System.out.println("\n--- While Loop Demonstration ---");
        int count = 5;
        while (count < 5) {
            System.out.println("This will NOT print because 5 is not < 5");
        }

        System.out.println("\n--- Do-While Loop Demonstration ---");
        do {
            System.out.println("This WILL print once despite the false condition!");
        } while (count < 5);

        // 3. Break and Continue
        System.out.println("\n--- Break and Continue Demo ---");
        int[] scores = {10, 20, -5, 30, -99, 40}; // -99 is a stop flag
        for (int score : scores) {
            if (score < 0 && score != -99) {
                System.out.println("Skipping invalid score: " + score);
                continue; // Skip rest of loop, go to next score
            }
            if (score == -99) {
                System.out.println("Stop signal received. Exiting loop.");
                break; // Exit loop completely
            }
            System.out.println("Processing score: " + score);
        }
    }
}
```

---

## Summary
- **`for` loops** are best for counter-controlled sequences with known limits.
- **`while` loops** test conditions *before* running and are ideal when the number of loops is unknown.
- **`do-while` loops** test conditions *after* running, guaranteeing at least one execution.
- **Enhanced `for-each` loops** iterate through arrays without manual index trackers, preventing index out of bounds crashes.
- **`break`** exits loops early; **`continue`** skips the rest of the current iteration.
- Every loop must have a clear exit path to prevent CPU-hogging **infinite loops**.

---

## Additional Resources
- [The for Loop - Oracle Tutorials](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html)
- [The while and do-while Loops - Oracle Tutorials](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/while.html)
- [Enhanced for Loop Guide - Baeldung](https://www.baeldung.com/java-for-loop)
