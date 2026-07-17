# Weekly Knowledge Check: Week 1-Java – Tuesday (Methods & AI Pair Programming)

---

## Part 1: Multiple Choice

### Q1. What is the correct syntax to declare a method that returns an integer and takes no parameters?
- [ ] A) `void getNumber() {}`
- [ ] B) `int getNumber() {}`
- [ ] C) `getNumber() int {}`
- [ ] D) `return int getNumber() {}`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `int getNumber() {}`

**Explanation:** Java method signature: `returnType methodName(parameters) { body }`.

**Why others are wrong:**
- A) `void` means no return value; this method returns an int.
- C) Return type must come before the method name.
- D) `return` is a statement inside a method, not part of the signature.
</details>

---

### Q2. Which access modifier allows access only within the same class?
- [ ] A) `public`
- [ ] B) `protected`
- [ ] C) `private`
- [ ] D) Default (no modifier)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `private`

**Explanation:** `private` restricts visibility to the declaring class only.

**Why others are wrong:**
- A) `public` is visible everywhere.
- B) `protected` allows same package + subclasses.
- D) Default (package-private) allows access within the same package.
</details>

---

### Q3. What happens if a non-void method does not contain a `return` statement on all code paths?
- [ ] A) It returns `null` automatically
- [ ] B) It returns `0` for numeric types
- [ ] C) A compile-time error occurs
- [ ] D) A runtime NullPointerException is thrown

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) A compile-time error occurs

**Explanation:** Java requires all code paths in a non-void method to return a value; missing returns are caught at compile time.

**Why others are wrong:**
- A) Only reference-typed methods could theoretically return null, but the compiler will reject missing returns.
- B) Java does not insert default return values.
- D) The code will not even compile, so no runtime error can occur.
</details>

---

### Q4. Which of the following is an example of method overloading?
- [ ] A) Two methods in different classes with the same name and same parameters
- [ ] B) Two methods in the same class with the same name but different parameter lists
- [ ] C) A subclass redefining a method from its superclass
- [ ] D) Using `@Override` on a method

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Two methods in the same class with the same name but different parameter lists

**Explanation:** Overloading = same name, different signatures (number/type of parameters).

**Why others are wrong:**
- A) Methods in different classes with the same name are not overloading.
- C) A subclass redefining a superclass method is *overriding*, not overloading.
- D) `@Override` is used for overriding.
</details>

---

### Q5. In Java, what is the scope of a variable declared inside a `for` loop?
- [ ] A) The entire class
- [ ] B) The entire method
- [ ] C) Only within the `for` loop block
- [ ] D) The entire file

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Only within the `for` loop block

**Explanation:** Variables declared in a loop initializer or loop body are local to that block and destroyed when the loop ends.

**Why others are wrong:**
- A) Class-level scope is for fields, not loop variables.
- B) Method-level scope requires declaring the variable in the method body before the loop.
- D) File scope does not exist in Java; only package, class, method, and block scopes exist.
</details>

---

### Q6. What is the key difference between zero-shot and few-shot prompting when working with AI coding assistants?
- [ ] A) Zero-shot uses examples; few-shot does not
- [ ] B) Few-shot provides examples; zero-shot gives no examples
- [ ] C) They are identical but used in different programming languages
- [ ] D) Zero-shot works offline; few-shot requires the internet

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Few-shot provides examples; zero-shot gives no examples

**Explanation:** Zero-shot prompting asks the AI directly with no examples. Few-shot includes 1–3 input/output examples to guide the model.

**Why others are wrong:**
- A) This reverses the definitions.
- C) Both techniques are model-agnostic and language-agnostic.
- D) Network availability has nothing to do with prompting style.
</details>

---

### Q7. Which section of a well-structured AI prompt describes what format the output should take?
- [ ] A) Context
- [ ] B) Task
- [ ] C) Output specification
- [ ] D) Persona

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Output specification

**Explanation:** The output specification tells the AI the expected format (e.g., "return a JSON object", "write a Java method with Javadoc").

**Why others are wrong:**
- A) Context provides background; it doesn't specify output format.
- B) Task describes *what* to do, not how to format results.
- D) Persona defines the AI's role (e.g., "You are a senior Java developer").
</details>

---

## Part 2: True / False

### Q8. A `void` method can contain a `return;` statement (with no value).
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** `return;` in a void method exits the method early without returning a value — this is valid Java.
</details>

---

### Q9. `protected` members of a class are accessible to subclasses in any package.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** `protected` grants access to the same package AND to subclasses regardless of package.
</details>

---

### Q10. You can call an instance method without creating an object if the method is declared `static`.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Static methods are called on the class itself: `ClassName.method()` — no instance needed.
</details>

---

### Q11. AI-generated code should always be accepted as-is because it is written by a large language model.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** AI models can hallucinate APIs, introduce bugs, or miss business requirements. All AI output must be read, understood, and tested before acceptance.
</details>

---

## Part 3: Code Prediction

### Q12. What does the following code print?
```java
public class Scope {
    static int x = 5;
    public static void main(String[] args) {
        int x = 10;
        System.out.println(x);
    }
}
```
- [ ] A) 5
- [ ] B) 10
- [ ] C) Compile error
- [ ] D) 15

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) 10

**Explanation:** The local variable `x = 10` shadows the class field `x = 5` inside `main`.

**Why others are wrong:**
- A) The class field is shadowed by the local variable.
- C) Shadowing is legal in Java.
- D) There is no addition happening.
</details>

---

### Q13. What is the output?
```java
public class Overload {
    static void show(int n) { System.out.println("int: " + n); }
    static void show(double n) { System.out.println("double: " + n); }
    public static void main(String[] args) {
        show(3);
    }
}
```
- [ ] A) `double: 3.0`
- [ ] B) `int: 3`
- [ ] C) Compile error — ambiguous call
- [ ] D) `int: 3.0`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `int: 3`

**Explanation:** The literal `3` is an `int` literal, so Java picks the most specific match: `show(int)`.

**Why others are wrong:**
- A) Widening would only happen if no `int` version existed.
- C) The call is not ambiguous; `int` is the exact match.
- D) `int` is not printed with a decimal.
</details>

---

## Part 4: Fill-in-the-Blank

### Q14. In Java, the keyword `_____` is used inside a constructor to call another constructor in the same class.
<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `this`

**Explanation:** `this(args)` as the first statement in a constructor delegates to an overloaded constructor in the same class.
</details>

---

### Q15. A prompt log entry should record the original prompt, the AI's output, your _____  of that output, and any revised prompt.
<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** evaluation / critique / assessment (any synonym accepted)

**Explanation:** Keeping an evaluation step ensures critical thinking about AI output quality and drives continuous prompt improvement.
</details>
