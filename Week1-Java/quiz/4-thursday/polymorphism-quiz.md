# Weekly Knowledge Check: Week 1-Java – Thursday (Polymorphism, Encapsulation & Advanced OOP)

---

## Part 1: Multiple Choice

### Q1. What type of polymorphism is resolved at compile time in Java?
- [ ] A) Runtime polymorphism (dynamic dispatch)
- [ ] B) Compile-time polymorphism (method overloading)
- [ ] C) Interface polymorphism
- [ ] D) Covariant polymorphism

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Compile-time polymorphism (method overloading)

**Explanation:** Overloaded methods are resolved by the compiler based on argument types at compile time.

**Why others are wrong:**
- A) Runtime polymorphism (overriding) is resolved by the JVM at runtime.
- C) Interfaces enable polymorphism but it is still a runtime concept.
- D) Covariant return types are a feature of overriding, not a type of polymorphism.
</details>

---

### Q2. What is the contract of the `hashCode()` method defined by `java.lang.Object`?
- [ ] A) Two objects that are equal via `==` must have the same hash code
- [ ] B) Two objects that are equal via `.equals()` must have the same hash code
- [ ] C) All objects in the same class must have the same hash code
- [ ] D) `hashCode()` must always return a positive integer

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Two objects that are equal via `.equals()` must have the same hash code

**Explanation:** The contract: if `a.equals(b)` is true, then `a.hashCode() == b.hashCode()` must also be true.

**Why others are wrong:**
- A) `==` checks identity; two distinct but logically equal objects may fail `==` but still need equal hash codes.
- C) Objects of the same class can have different hash codes (usually based on field values).
- D) Hash codes can be negative.
</details>

---

### Q3. Which rule MUST be followed when overriding a method?
- [ ] A) The overriding method must have a different return type
- [ ] B) The overriding method must be less accessible than the original
- [ ] C) The overriding method must have the same name and parameter list
- [ ] D) The overriding method cannot throw any exceptions

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) The overriding method must have the same name and parameter list

**Explanation:** Overriding requires matching method signature (same name and parameter types).

**Why others are wrong:**
- A) The return type must be the same or a covariant (subtype); it cannot be completely different.
- B) Access cannot be *more restrictive* when overriding; it can be same or broader.
- D) Overriding methods can throw the same or narrower checked exceptions.
</details>

---

### Q4. What is encapsulation in Java?
- [ ] A) Running the same code in multiple threads simultaneously
- [ ] B) Hiding internal state with private fields and exposing controlled access via public methods
- [ ] C) Creating multiple classes with the same name in different packages
- [ ] D) Linking method calls together in a chain

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Hiding internal state with private fields and exposing controlled access via public methods

**Explanation:** Encapsulation uses `private` fields + public getters/setters to protect object state.

**Why others are wrong:**
- A) That describes concurrency/threading.
- C) That is the purpose of packages, not encapsulation.
- D) That describes method chaining.
</details>

---

### Q5. Which of the following correctly defines a getter for a private `name` field?
- [ ] A) `private String getName() { return name; }`
- [ ] B) `public String getName() { return name; }`
- [ ] C) `public void getName() { return name; }`
- [ ] D) `static String getName() { return name; }`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `public String getName() { return name; }`

**Explanation:** A getter should be `public` (accessible from outside), return the field's type, and use the `getFieldName()` naming convention.

**Why others are wrong:**
- A) `private` would make the getter inaccessible from outside — defeating the purpose.
- C) `void` cannot have a `return name;` statement.
- D) `static` would be incorrect for an instance field getter.
</details>

---

### Q6. What is the purpose of the `@Override` annotation?
- [ ] A) It marks a method as final
- [ ] B) It asks the compiler to verify the method is overriding a superclass method
- [ ] C) It makes a method static
- [ ] D) It generates getter and setter code automatically

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) It asks the compiler to verify the method is overriding a superclass method

**Explanation:** `@Override` is a compile-time safety net — if the method signature doesn't match a superclass/interface method, the compiler will error.

**Why others are wrong:**
- A) `final` prevents overriding; `@Override` doesn't make a method final.
- C) `static` keyword makes a method static.
- D) Lombok or IDE features handle getter/setter generation, not `@Override`.
</details>

---

### Q7. When using AI for task planning, what is the most important step after receiving an AI-generated plan?
- [ ] A) Immediately implement all steps without review
- [ ] B) Validate the plan against actual project requirements
- [ ] C) Ask the AI to rewrite the plan in a different programming language
- [ ] D) Share the plan on social media before coding

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Validate the plan against actual project requirements

**Explanation:** AI-generated plans may be generic or miss domain-specific constraints; always verify against real requirements before implementation.

**Why others are wrong:**
- A) Blind implementation of AI plans is a known anti-pattern ("blind acceptance").
- C) Language is irrelevant to plan validation.
- D) This is not relevant to software development.
</details>

---

### Q8. What does `upcasting` mean in the context of Java polymorphism?
- [ ] A) Converting a primitive to a wrapper class
- [ ] B) Assigning a subclass object to a superclass reference variable
- [ ] C) Calling a method defined in a subclass via a superclass reference
- [ ] D) Converting a `double` to an `int`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Assigning a subclass object to a superclass reference variable

**Explanation:** `Animal a = new Dog();` — a `Dog` object is upcast to an `Animal` reference. This is implicit and always safe.

**Why others are wrong:**
- A) That is autoboxing.
- C) That is a result of polymorphic dispatch, not the definition of upcasting.
- D) That is narrowing primitive conversion.
</details>

---

## Part 2: True / False

### Q9. Overloaded methods can differ only by return type.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Java does NOT allow overloading based on return type alone. At least one parameter must differ (type, count, or order).
</details>

---

### Q10. A `private` method can be overridden by a subclass.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** `private` methods are not visible to subclasses; a method with the same name in a subclass is a new method, not an override.
</details>

---

### Q11. `toString()`, `equals()`, and `hashCode()` are all methods inherited from `java.lang.Object`.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Every Java class implicitly extends `Object`, which provides `toString()`, `equals()`, `hashCode()`, and others.
</details>

---

### Q12. Garbage collection in Java is triggered by the programmer calling `System.gc()`.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** `System.gc()` only *suggests* garbage collection; the JVM decides when to actually run it. GC is non-deterministic.
</details>

---

## Part 3: Code Prediction

### Q13. What is the output?
```java
class Animal {
    String speak() { return "..."; }
}
class Dog extends Animal {
    @Override
    String speak() { return "Woof"; }
}
class Cat extends Animal {
    @Override
    String speak() { return "Meow"; }
}
public class Test {
    static void makeSound(Animal a) {
        System.out.println(a.speak());
    }
    public static void main(String[] args) {
        makeSound(new Dog());
        makeSound(new Cat());
    }
}
```
- [ ] A) `...` then `...`
- [ ] B) `Woof` then `Meow`
- [ ] C) `Woof` then `Woof`
- [ ] D) Compile error — cannot pass `Dog` where `Animal` is expected

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Woof` then `Meow`

**Explanation:** Runtime polymorphism: although the parameter type is `Animal`, the JVM dispatches to the actual object's overridden `speak()` method.

**Why others are wrong:**
- A) The base class `...` is never reached because overridden methods take precedence.
- C) `Cat.speak()` returns "Meow", not "Woof".
- D) Upcasting `Dog` to `Animal` is implicit and legal.
</details>

---

### Q14. What does the following setter do wrong?
```java
public class Person {
    private int age;
    public void setAge(int age) {
        age = age;
    }
}
```
- [ ] A) The method is not accessible
- [ ] B) The parameter shadows the field — the field is never updated (`this.age = age` is needed)
- [ ] C) The return type should be `int`
- [ ] D) The method name violates JavaBean convention

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The parameter shadows the field — the field is never updated (`this.age = age` is needed)

**Explanation:** Without `this.age`, `age = age` assigns the local parameter to itself; the instance field is never modified.

**Why others are wrong:**
- A) The method is `public`, so it is accessible.
- C) Setters should be `void`.
- D) `setAge` follows the correct JavaBean naming convention.
</details>

---

## Part 4: Fill-in-the-Blank

### Q15. The three-step review before accepting AI-generated code is: read it, _____ it, and test it.
<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** understand

**Explanation:** The 3-step rule prevents "blind acceptance": you must read the code, understand what it does, and test it before merging into your project.
</details>
