# Weekly Knowledge Check: Week 1-Java – Monday (Java Fundamentals & Git)

---

## Part 1: Multiple Choice

### Q1. Which component of the Java ecosystem is solely responsible for executing compiled bytecode?
- [ ] A) JDK
- [ ] B) JRE
- [ ] C) JVM
- [ ] D) IDE

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) JVM

**Explanation:** The Java Virtual Machine (JVM) reads `.class` bytecode and executes it on the host OS.

**Why others are wrong:**
- A) JDK is the full development kit (includes compiler, tools, JRE, and JVM) but is not the executor itself.
- B) JRE provides class libraries and hosts the JVM but is a superset container, not the executor.
- D) IDE is an editor; it has no role in execution at runtime.
</details>

---

### Q2. Which primitive type should you use to store the value `true` or `false`?
- [ ] A) `int`
- [ ] B) `char`
- [ ] C) `boolean`
- [ ] D) `byte`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `boolean`

**Explanation:** The `boolean` primitive holds exactly two values: `true` or `false`.

**Why others are wrong:**
- A) `int` stores 32-bit integers, not truth values.
- B) `char` stores a single Unicode character.
- D) `byte` is an 8-bit signed integer.
</details>

---

### Q3. What is the default value of an uninitialized `int` field in a Java class?
- [ ] A) `null`
- [ ] B) `undefined`
- [ ] C) `-1`
- [ ] D) `0`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** D) `0`

**Explanation:** Java initializes all numeric instance fields to `0` by default.

**Why others are wrong:**
- A) `null` is for reference types, not primitives.
- B) `undefined` does not exist in Java (it's a JavaScript concept).
- C) `-1` is not a default; the JVM always defaults to `0`.
</details>

---

### Q4. Which of the following correctly declares and initializes a single-dimensional integer array of size 5?
- [ ] A) `int arr = new int(5);`
- [ ] B) `int[] arr = new int[5];`
- [ ] C) `int arr[] = int[5];`
- [ ] D) `array<int> arr = new array<int>(5);`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `int[] arr = new int[5];`

**Explanation:** In Java, arrays are declared with `[]` and created with `new type[size]`.

**Why others are wrong:**
- A) `new int(5)` uses parentheses; arrays need square brackets.
- C) `int[5]` is not valid Java syntax for the right-hand side.
- D) Java does not use generic-style `array<int>` syntax.
</details>

---

### Q5. What does the `||` operator do when its first operand evaluates to `true`?
- [ ] A) Evaluates the second operand and returns the result
- [ ] B) Skips the second operand and returns `true` immediately (short-circuit)
- [ ] C) Throws a `ShortCircuitException`
- [ ] D) Converts both operands to integers and adds them

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Skips the second operand and returns `true` immediately (short-circuit)

**Explanation:** The `||` operator short-circuits: if the left side is `true`, the result is `true` and the right side is never evaluated.

**Why others are wrong:**
- A) This would describe `|` (non-short-circuit OR).
- C) No such exception exists.
- D) `||` is a logical operator, not arithmetic.
</details>

---

### Q6. What is the purpose of the `.gitignore` file?
- [ ] A) To delete files from the remote repository
- [ ] B) To specify files and directories Git should not track
- [ ] C) To list all contributors to the project
- [ ] D) To store commit messages

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) To specify files and directories Git should not track

**Explanation:** `.gitignore` patterns tell Git which files to exclude from tracking (e.g., build artifacts, IDE config).

**Why others are wrong:**
- A) `.gitignore` does not delete remote files.
- C) Contributors are listed in commits or CONTRIBUTORS files.
- D) Commit messages are stored in Git history, not `.gitignore`.
</details>

---

### Q7. Which Git command creates a snapshot of the staged changes?
- [ ] A) `git add`
- [ ] B) `git push`
- [ ] C) `git commit`
- [ ] D) `git status`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `git commit`

**Explanation:** `git commit` saves the staged snapshot to local history.

**Why others are wrong:**
- A) `git add` stages changes; it does not create a commit.
- B) `git push` uploads commits to a remote; commits must already exist.
- D) `git status` shows current working-tree state.
</details>

---

## Part 2: True / False

### Q8. The JDK includes the JRE, and the JRE includes the JVM.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** JDK ⊃ JRE ⊃ JVM — each is a superset of the next.
</details>

---

### Q9. A `double` primitive occupies 4 bytes of memory in Java.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** `double` is a 64-bit (8-byte) floating-point type. `float` is 4 bytes.
</details>

---

### Q10. In Java, a local variable declared inside a method has the same default value as an instance field.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Local variables are NOT automatically initialized; the compiler will error if you use one before assigning a value.
</details>

---

### Q11. `git rebase` rewrites commit history, while `git merge` creates a new merge commit.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** `rebase` replays commits on top of another branch (rewrites history), whereas `merge` adds a merge commit preserving both histories.
</details>

---

## Part 3: Code Prediction

### Q12. What is the output of this code?
```java
int x = 10;
int y = 3;
System.out.println(x % y);
```
- [ ] A) 3
- [ ] B) 1
- [ ] C) 0
- [ ] D) 3.33

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) 1

**Explanation:** `%` is the modulo operator; `10 / 3 = 3` remainder `1`.

**Why others are wrong:**
- A) 3 is the quotient, not the remainder.
- C) 0 would be the result only if 10 were divisible by 3.
- D) Integer division/modulo never produces a decimal.
</details>

---

### Q13. What does the following code print?
```java
int i = 0;
while (i < 3) {
    System.out.print(i + " ");
    i++;
}
```
- [ ] A) `0 1 2 3`
- [ ] B) `1 2 3`
- [ ] C) `0 1 2`
- [ ] D) Infinite loop

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `0 1 2`

**Explanation:** The loop runs while `i < 3`, printing 0, 1, 2 then stopping when `i` becomes 3.

**Why others are wrong:**
- A) `3` is never printed because the condition `i < 3` is false when `i == 3`.
- B) The loop starts at `i = 0`, not `1`.
- D) `i++` ensures the loop terminates.
</details>

---

## Part 4: Fill-in-the-Blank

### Q14. The command to stage all modified files in the current directory is `git _____ .`
<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `add`

**Explanation:** `git add .` stages all changes in the current directory for the next commit.
</details>

---

### Q15. In Java, the keyword `_____` is used to prevent a class from being subclassed.
<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `final`

**Explanation:** A `final` class cannot be extended. Example: `public final class String`.
</details>
