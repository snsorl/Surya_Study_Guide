# Troubleshooting and Debugging in IntelliJ IDEA

## Learning Objectives
- Explain the limitations of "print debugging" (`System.out.println`) vs. using an interactive debugger.
- Set breakpoints in IntelliJ IDEA to pause program execution.
- Navigate execution paths using Step Over (`F8`), Step Into (`F7`), Step Out (`Shift+F8`), and Resume (`F9`).
- Inspect and monitor memory states using the Variables Pane and Watches.
- Trace variables step-by-step inside loop structures to isolate logical bugs.

---

## Why This Matters
As a developer, you will spend more time debugging existing code than writing new code. When a program outputs the wrong results, beginners often litter their code with statements like `System.out.println("Here")` or `System.out.println("x is " + x)`. This approach (known as "print debugging") is slow, requires constant code cleanup, and does not let you interact with the running program.

Professional developers use the **IDE Debugger**. The debugger allows you to freeze time. You can pause your program mid-execution, look inside Stack and Heap memory to see exactly what values variables hold, and walk through your code line-by-line as the JVM executes it. Master debugging, and you will solve complex logic errors in minutes rather than hours.

---

## The Concept

### 1. What is a Breakpoint?
A **breakpoint** is a marker you place on a line of source code. It tells the JVM: *"Execute the program normally, but pause right before executing this specific line."*

#### Setting a Breakpoint in IntelliJ:
1. Open a Java file in the editor.
2. Click in the **gutter** (the gray vertical strip to the left of the line numbers).
3. A red circle will appear, and the line will be highlighted. This indicates a breakpoint is active.

To remove a breakpoint, simply click the red circle again.

```
  13 |     int total = 0;
● 14 |     for (int i = 0; i < 5; i++) {   <--- Breakpoint set here
  15 |         total += i;
```

---

### 2. Running the Debugger
To start the program in debug mode, do not click the standard "Run" play arrow. Instead:
- Click the **Bug Icon** (`▶` with a small bug on it) in the top-right toolbar.
- Alternatively, right-click inside your class and select **Debug 'ClassName.main()'**.

When the program hits your breakpoint, IntelliJ will pause execution, highlight the line in blue, and open the **Debug Tool Window** at the bottom of the screen.

---

### 3. Stepping Through Code
Once paused, you navigate the program step-by-step using these controls (found in the Debug toolbar or via keyboard shortcuts):

| Control | Shortcut (Win/Linux) | Shortcut (macOS) | Action |
| :--- | :--- | :--- | :--- |
| **Step Over** | `F8` | `F8` | Executes the highlighted line and moves to the next line in the current method. If the line contains a method call, it runs the method in the background without entering it. |
| **Step Into** | `F7` | `F7` | If the highlighted line contains a method, the debugger jumps inside that method so you can step through its internal lines. |
| **Step Out** | `Shift + F8` | `Shift + F8` | Finishes executing the current method and returns you to the caller method line. |
| **Resume Program**| `F9` | `Option + Cmd + R`| Resumes normal execution. The program will run until it hits another breakpoint or finishes. |

---

### 4. Inspecting Memory States

#### The Variables Pane
Located at the bottom of the Debugger window, this pane automatically lists every variable currently in scope on the Stack, alongside its current runtime value. IntelliJ also prints variable values inline directly next to the code lines in the editor window.

#### Watches
If you are debugging a complex block with many variables and only care about one specific value (or a comparison expression like `balance < 10`), you can add a **Watch**:
1. Click the **`+`** (plus) icon in the Watches tab.
2. Type the variable name or expression (e.g., `i * 10`).
3. The IDE will evaluate and display that value dynamically with every step you take.

---

## Practical Debugging Example
Let's look at a class containing a logical bug. We want to calculate the sum of numbers from 1 to 5 (which should equal 15), but our code prints the wrong value.

```java
public class DebuggingDemo {
    public static void main(String[] args) {
        int target = 5;
        int sum = 0;

        // Bug: We want to sum numbers 1 through 5, but our loop is incorrect.
        for (int i = 1; i < target; i++) { 
            sum += i;
        }

        System.out.println("The sum is: " + sum); // Prints: The sum is: 10
    }
}
```

### Walkthrough of the Debug Session:
1. **Set a Breakpoint**: Click the gutter on line 7 (`for (int i = 1; i < target; i++)`).
2. **Start Debugging**: Click the bug icon. The program pauses at line 7.
3. **Inspect Initial Variables**: In the variables pane, you see `target = 5` and `sum = 0`.
4. **Step Over (`F8`)**:
   - Step once: The loop initiates. In the variables pane, you see `i = 1`.
   - Step again: `sum += i` executes. `sum` changes to `1`.
   - Keep stepping `F8` and watch `i` and `sum` increment.
5. **Spot the Bug**:
   - On the 4th iteration, `i` becomes `4`, and `sum` becomes `10` (`1+2+3+4`).
   - You press `F8` again. Instead of incrementing `i` to `5` and adding it, the loop terminates!
   - Looking at the loop condition `i < target` (which evaluates to `4 < 5`), you realize that when `i` reaches `5`, the check is `5 < 5` (false).
   - **The Fix**: Change the condition to `i <= target` to include the number 5.

---

## Summary
- A **breakpoint** pauses the JVM right before a line of code is executed.
- The **debugger** lets you view the exact values of variables on the Stack in real-time.
- **Step Over (`F8`)** moves to the next line in the current method.
- **Step Into (`F7`)** enters inside a method call.
- **Step Out (`Shift+F8`)** exits the current method.
- **Watches** let you track specific variables or expressions dynamically.

---

## Additional Resources
- [IntelliJ IDEA Debugger Basics - JetBrains Guide](https://www.jetbrains.com/help/idea/debugging-code.html)
- [How to debug a Java Application in IntelliJ - YouTube Tutorial](https://www.youtube.com/watch?v=s5R_9k3qDsc)
- [Baeldung: Guide to IntelliJ Debugger](https://www.baeldung.com/intellij-idea-debugging)
