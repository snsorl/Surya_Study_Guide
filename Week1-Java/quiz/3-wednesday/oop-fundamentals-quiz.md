# Weekly Knowledge Check: Week 1-Java – Wednesday (OOP Fundamentals)

---

## Part 1: Multiple Choice

### Q1. What is the correct term for a class that cannot be instantiated and may contain abstract methods?
- [ ] A) Interface
- [ ] B) Abstract class
- [ ] C) Final class
- [ ] D) Static class

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Abstract class

**Explanation:** An abstract class is declared with `abstract` and cannot be directly instantiated; it may have abstract methods that subclasses must implement.

**Why others are wrong:**
- A) An interface can also have abstract methods but is not a class — it uses `interface` keyword.
- C) A `final` class cannot be subclassed, but it *can* be instantiated.
- D) There is no `static class` at the top level in Java.
</details>

---

### Q2. What does `String s1 == s2` compare when `s1` and `s2` are both `String` objects?
- [ ] A) The content (characters) of the strings
- [ ] B) The memory addresses (references) of the objects
- [ ] C) The lengths of the strings
- [ ] D) The hash codes of the strings

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The memory addresses (references) of the objects

**Explanation:** `==` for reference types compares object identity (same heap address), not content. Use `.equals()` for content comparison.

**Why others are wrong:**
- A) `.equals()` compares content.
- C) `.length()` compares lengths.
- D) `.hashCode()` compares hash codes, not `==`.
</details>

---

### Q3. Which of the following best describes the `is-a` relationship in OOP?
- [ ] A) Composition — one object contains another
- [ ] B) Inheritance — a subclass is a specialized type of the superclass
- [ ] C) Encapsulation — hiding data inside a class
- [ ] D) Polymorphism — one method with multiple behaviors

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Inheritance — a subclass is a specialized type of the superclass

**Explanation:** The "is-a" relationship is modeled by inheritance: `Dog is-a Animal`.

**Why others are wrong:**
- A) Composition represents "has-a" relationships.
- C) Encapsulation is about data hiding.
- D) Polymorphism is about behavior, not classification.
</details>

---

### Q4. Which keyword is used to implement an interface in Java?
- [ ] A) `extends`
- [ ] B) `implements`
- [ ] C) `inherits`
- [ ] D) `uses`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `implements`

**Explanation:** A class uses `implements` to fulfill the contract of an interface.

**Why others are wrong:**
- A) `extends` is used for class-to-class or interface-to-interface inheritance.
- C) `inherits` is not a Java keyword.
- D) `uses` is not a Java keyword.
</details>

---

### Q5. In Java, autoboxing refers to:
- [ ] A) Automatically converting a `String` to a number
- [ ] B) Automatically wrapping a primitive value into its corresponding Wrapper class
- [ ] C) Automatically casting one reference type to another
- [ ] D) Automatically generating getter and setter methods

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Automatically wrapping a primitive value into its corresponding Wrapper class

**Explanation:** Autoboxing converts `int` → `Integer`, `double` → `Double`, etc. automatically.

**Why others are wrong:**
- A) `Integer.parseInt()` converts String to number; autoboxing doesn't involve Strings.
- C) That is casting.
- D) That is a Lombok or IDE feature, not autoboxing.
</details>

---

### Q6. What is a `static` field in Java?
- [ ] A) A field that cannot be modified
- [ ] B) A field shared by all instances of a class
- [ ] C) A field accessible only within the declaring method
- [ ] D) A field that is automatically initialized to `null`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A field shared by all instances of a class

**Explanation:** `static` fields belong to the class itself; all instances share the same value.

**Why others are wrong:**
- A) That describes `final`.
- C) That describes a local variable.
- D) All reference fields default to `null`, not just static ones.
</details>

---

### Q7. What is the primary advantage of using an interface over an abstract class?
- [ ] A) Interfaces can contain constructors
- [ ] B) A class can implement multiple interfaces but extend only one class
- [ ] C) Interfaces allow field declarations with any access modifier
- [ ] D) Interfaces execute faster at runtime

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) A class can implement multiple interfaces but extend only one class

**Explanation:** Java does not support multiple class inheritance, but a class can implement many interfaces — enabling flexible design.

**Why others are wrong:**
- A) Interfaces cannot have constructors.
- C) Interface fields are implicitly `public static final`.
- D) Runtime speed is determined by JVM optimizations, not by interface vs abstract class.
</details>

---

### Q8. When using AI to debug a NullPointerException, what information should you always provide in your prompt?
- [ ] A) Only the stack trace
- [ ] B) Only the line number
- [ ] C) The stack trace, the relevant code snippet, and the expected behavior
- [ ] D) The file size and project name

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) The stack trace, the relevant code snippet, and the expected behavior

**Explanation:** AI needs sufficient context — what went wrong (trace), where it happened (code), and what you expected — to provide accurate fixes.

**Why others are wrong:**
- A) Stack trace alone lacks the code context.
- B) Line number alone is insufficient.
- D) File size and project name are irrelevant to debugging.
</details>

---

## Part 2: True / False

### Q9. An abstract class can have both abstract and non-abstract (concrete) methods.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Abstract classes can mix abstract methods (no body) and concrete methods (with body), unlike pure interfaces.
</details>

---

### Q10. In Java, `String` objects are mutable — you can change their content after creation.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** `String` is immutable in Java. Operations like `concat()` or `+` return new `String` objects; the original is unchanged.
</details>

---

### Q11. A class can extend multiple abstract classes in Java.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Java supports single inheritance for classes. A class can only `extend` one class (abstract or concrete).
</details>

---

### Q12. `Integer.parseInt("42")` returns an `int` primitive value.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** `Integer.parseInt(String)` parses the string and returns a primitive `int`.
</details>

---

## Part 3: Code Prediction

### Q13. What is the output of the following code?
```java
abstract class Shape {
    abstract double area();
    void describe() { System.out.println("I am a shape with area: " + area()); }
}
class Circle extends Shape {
    double radius;
    Circle(double r) { this.radius = r; }
    @Override
    double area() { return Math.PI * radius * radius; }
}
public class Test {
    public static void main(String[] args) {
        Shape s = new Circle(1);
        s.describe();
    }
}
```
- [ ] A) `I am a shape with area: 0.0`
- [ ] B) `I am a shape with area: 3.141592653589793`
- [ ] C) Compile error — cannot call `area()` from `Shape`
- [ ] D) Runtime error — abstract method called

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `I am a shape with area: 3.141592653589793`

**Explanation:** At runtime, `area()` is dispatched to `Circle.area()`, which calculates `π × 1² = π`.

**Why others are wrong:**
- A) `area()` is not 0.0 because `Circle.area()` is called, not a default value.
- C) The method is abstract in `Shape` but concrete in `Circle`; the call is valid.
- D) The object is a `Circle`, not an abstract `Shape`, so the method has a body.
</details>

---

### Q14. What does this code print?
```java
String s1 = new String("hello");
String s2 = new String("hello");
System.out.println(s1 == s2);
System.out.println(s1.equals(s2));
```
- [ ] A) `true` then `true`
- [ ] B) `false` then `true`
- [ ] C) `true` then `false`
- [ ] D) `false` then `false`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `false` then `true`

**Explanation:** `new String(...)` always creates a new object on the heap, so `==` compares different references (false). `.equals()` compares the character content ("hello" == "hello"), so it returns true.

**Why others are wrong:**
- A) `s1 == s2` is false because they are different objects.
- C) `.equals()` is true because content is identical.
- D) `.equals()` checks content, not identity.
</details>

---

## Part 4: Fill-in-the-Blank

### Q15. To call a superclass constructor from a subclass, you use the keyword `_____` as the first statement in the subclass constructor.
<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `super`

**Explanation:** `super(args)` must be the first statement in a subclass constructor to invoke the parent class's constructor.
</details>
