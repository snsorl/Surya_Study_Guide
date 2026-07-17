# Exercise: AI Assisted Debugging

## Objective
Practice reading JVM Exception Stack Traces, diagnosing runtime crashes, prompting an AI assistant for diagnostic help, and auditing AI fixes to avoid the "Band-Aid" trap.

---

## Prerequisites
- Completed Wednesday's reading materials on memory references, casting, and AI debugging.

---

## The Lab Scenario
Below are three deliberately broken Java programs. Your task is to:
1.  Copy each snippet into a class in your IntelliJ project and compile/run it to observe the crash.
2.  Capture the console stack trace.
3.  Prompt your AI co-pilot with the code and stack trace to get a bug explanation and fix.
4.  Verify that the AI's fix is a root-cause solution (guards inputs, validates bounds) rather than a try-catch "Band-Aid".

---

## Broken Code Snippets

### Bug 1: The Null Roster Crash
```java
public class RosterApp {
    public static void main(String[] args) {
        String[] studentNames = new String[5];
        studentNames[0] = "Alice";
        studentNames[1] = "Bob";
        
        for (int i = 0; i < studentNames.length; i++) {
            // Triggers NullPointerException when reaching index 2
            if (studentNames[i].startsWith("A")) {
                System.out.println("Student: " + studentNames[i]);
            }
        }
    }
}
```

---

### Bug 2: The Incompatible Vehicle Downcast
```java
class Vehicle {}
class Car extends Vehicle {}
class Airplane extends Vehicle {}

public class AirportApp {
    public static void main(String[] args) {
        Vehicle myVehicle = new Airplane();
        
        // Triggers ClassCastException at runtime
        Car myCar = (Car) myVehicle; 
        System.out.println("Vehicle downcasted successfully.");
    }
}
```

---

### Bug 3: The Arithmetic Division Trap
```java
public class FinancialApp {
    public static double computeScore(int totalScore, int divisor) {
        return totalScore / divisor; // Triggers ArithmeticException if divisor is 0
    }

    public static void main(String[] args) {
        System.out.println("Processing report...");
        double value = computeScore(100, 0);
        System.out.println("Calculated Value: " + value);
    }
}
```

---

## Step-by-Step Instructions
1.  Initialize classes for each bug inside your project.
2.  Run the code and record the stack trace.
3.  Prompt the AI:
    *   *Prompt Pattern*: *"My Java program crashes with this stack trace: [insert trace]. Here is my code: [insert code]. Explain the root cause of the error and provide a clean fix that enforces boundary validation checks instead of catching the exception."*
4.  Apply the AI's code adjustments, verify that the program runs safely, and document the resolution in a file named `ai-debugging-log.md`.

---

## Definition of Done
- A Markdown file named `ai-debugging-log.md` is saved.
- All three broken classes are corrected in your project:
  *   `RosterApp` checks for null index elements before parsing.
  *   `AirportApp` uses `instanceof` check safeguards before casting.
  *   `FinancialApp` checks for zero inputs before executing arithmetic divisions.
- All programs run and terminate without throwing exceptions.
