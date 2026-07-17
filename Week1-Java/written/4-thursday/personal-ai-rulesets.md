# Personal AI Rulesets

## Learning Objectives
- Formulate a personal ruleset for using AI coding assistants responsibly during software training.
- Identify the warning signs of "AI Dependency" and explain its impact on learning.
- Establish strict boundaries for when to write code manually vs. when to prompt an assistant.
- Document design decisions and code changes to ensure independent mastery of programming concepts.

---

## Why This Matters
AI coding assistants are highly productive, but they present a massive trap for student developers. Because the AI can generate a working method in seconds, it is easy to fall into a passive habit of using the AI to solve every homework assignment, class lab, or compile error. 

If you do this, you bypass the cognitive struggle of debugging and writing code. This struggle is how your brain builds the mental pathways of a programmer. When you enter a corporate job, you will face technical interviews where AI tools are blocked, or complex bugs that AI cannot solve. If you have built an AI dependency, you will struggle to perform. Creating a **Personal AI Ruleset** enforces the discipline needed to use AI as an educational multiplier rather than a learning crutch.

---

## The Concept

### The Risk of AI Dependency
AI dependency occurs when you cannot write basic algorithms, resolve syntax errors, or plan program structures without an active AI prompt window open.
*   **The Symptom**: Coping with errors by immediately copy-pasting them to the AI, rather than reading the stack trace.
*   **The Consequence**: Shallow learning. You get the correct answer for the lab, but you do not build the problem-solving skills needed to pass technical job evaluations.

---

### Enforcing Your Ruleset
To maximize your learning speed, establish a strict personal ruleset. Consider incorporating these boundaries:

```
                  +------------------------------------+
                  |    YOUR PERSONAL AI RULESET CARD   |
                  +------------------------------------+
                  | 1. THE 10-MINUTE TIMER             |
                  |    Attempt to debug manually first.|
                  |                                    |
                  | 2. NO BLIND GHOST COMPLETIONS      |
                  |    Read and explain before Tab.    |
                  |                                    |
                  | 3. SIMPLICITY CONSTRAINTS          |
                  |    Ban advanced, unstudied APIs.   |
                  +------------------------------------+
```

#### 1. The 10-Minute Debugging Rule
When you encounter a compiler error or a runtime crash, **do not copy-paste it to the AI immediately**. Set a timer for 10 minutes. Use this time to:
*   Read the stack trace to locate the file and line number.
*   Check your local notes, lecture slides, and official docs.
*   Add diagnostic print statements (`System.out.println`) to trace variable states.

If the timer expires and you are still blocked, only then prompt the AI to explain the error.

#### 2. The Explanatory Principle
Never click `Tab` to accept a code completion or copy a method unless you can explain exactly what every variable, conditional, loop, and API call does to a teammate or instructor. If the AI suggests a library class you haven't studied yet (e.g. `java.util.stream`), instruct the AI to rewrite the code using simpler structures you understand.

#### 3. Enforcing Negative Constraints
When learning core language mechanics, explicitly block the AI from using shortcuts:
*   *Prompt constraint*: *"Do not use Java Streams, lambdas, or collections. Write this logic using only primitive arrays and standard nested loops."*

---

## Code Example: Self-Correction vs. Blind Acceptance

### Scenario: The developer needs to write a method that removes duplicate values from an array.

#### The Undisciplined Approach (Blind Acceptance):
The developer prompts the AI: *"Write a method to remove duplicates from an int array."*
The AI suggests:
```java
public static int[] removeDuplicates(int[] arr) {
    return java.util.Arrays.stream(arr).distinct().toArray();
}
```
*The Result*: The developer accepts the suggestion because it compiles. However, the developer has no idea how Java Streams work under the hood, hasn't practiced index shifting, and doesn't learn how to manipulate array bounds manually.

---

#### The Disciplined Approach (Following a Ruleset):
The developer attempts to write it using primitive arrays and loops first. They write this initial code:

```java
// Developer's Manual Attempt:
public static int[] removeDuplicates(int[] arr) {
    int[] temp = new int[arr.length];
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
        boolean isDup = false;
        for (int j = 0; j < count; j++) {
            if (arr[i] == temp[j]) {
                isDup = true;
                break;
            }
        }
        if (!isDup) {
            temp[count] = arr[i];
            count++;
        }
    }
    return temp; // Logic flaw: temp array size matches original, padded with trailing zeros!
}
```

The developer notices that the output contains trailing zeros (e.g. input `[1, 2, 2, 3]` returns `[1, 2, 3, 0]`). They attempt to fix it, but are stuck on resizing arrays. They prompt the AI:

> *"I am practicing array manipulation. I wrote this method to remove duplicates: [insert code]. It works, but it returns trailing zeros because the output array is not resized to 'count'. How do I copy elements to a new array of size 'count' using standard array indexing?"*

The AI responds with the resize logic:

```java
// Verified, Safe Final Code:
public class ArrayUtilities {

    /**
     * Removes duplicate values from an array without using external collection classes.
     * Demonstrates manual array copying for resizing.
     */
    public static int[] removeDuplicates(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int[] temp = new int[arr.length];
        int uniqueCount = 0;

        for (int i = 0; i < arr.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < uniqueCount; j++) {
                if (arr[i] == temp[j]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                temp[uniqueCount] = arr[i];
                uniqueCount++;
            }
        }

        // Resizing using standard array replication:
        int[] result = new int[uniqueCount];
        for (int i = 0; i < uniqueCount; i++) {
            result[i] = temp[i]; // Copy only unique values
        }
        return result;
    }

    public static void main(String[] args) {
        int[] scores = {10, 20, 20, 30, 10};
        int[] uniqueScores = removeDuplicates(scores);
        System.out.println("Cleaned array: " + java.util.Arrays.toString(uniqueScores)); // [10, 20, 30]
    }
}
```

---

## Summary
- **AI dependency** slows down real skill development by bypassing critical problem-solving struggles.
- Enforce the **10-minute rule**: attempt manual debugging before prompting an LLM.
- **The Explanatory Principle**: never accept code completions you cannot explain line-by-line.
- Use **negative constraints** inside prompts to force the AI to suggest basic, un-abstracted code syntax during your learning phase.

---

## Additional Resources
- [The Importance of Self-Explanation in Programming Education - ACM Portal](https://dl.acm.org/)
- [Developing Code Quality Discipline - Martin Fowler Guides](https://martinfowler.com/)
- [Best Practices for AI Assisted Learning - Google Developer Education](https://developer.google.com/)