# Interview Questions: Week 1 – Java Full Stack
> **Format:** 70% Beginner · 25% Intermediate · 5% Advanced | 15–20 questions per day | Hidden Answer Pattern

---

## Day 1 – Monday: Java Fundamentals, Primitives, Operators, Arrays, Git

### Beginner (70%)

**Q1. What does JVM stand for and what is its role?**
**Keywords:** Java Virtual Machine, bytecode, platform-independent
<details>
<summary>Click to Reveal Answer</summary>

The Java Virtual Machine (JVM) interprets compiled `.class` bytecode and executes it on the host operating system. It is what makes Java "Write Once, Run Anywhere" — the same bytecode runs on any JVM regardless of the OS.
</details>

---

**Q2. Explain the difference between JDK, JRE, and JVM.**
**Keywords:** JDK, JRE, JVM, compiler, runtime
<details>
<summary>Click to Reveal Answer</summary>

- **JVM** — executes bytecode.
- **JRE** — JVM + class libraries needed to run Java apps.
- **JDK** — JRE + compiler (`javac`) + development tools (debugger, javadoc). Developers need the JDK; end users only need the JRE.
</details>

---

**Q3. Name all 8 primitive data types in Java.**
**Keywords:** byte, short, int, long, float, double, char, boolean
<details>
<summary>Click to Reveal Answer</summary>

`byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`.
</details>

---

**Q4. What is the difference between a primitive type and a reference type?**
**Keywords:** stack, heap, value, reference, null
<details>
<summary>Click to Reveal Answer</summary>

Primitive types store their **value** directly on the stack. Reference types store a **reference** (memory address) on the stack that points to an object on the heap. Primitives cannot be `null`; reference variables can.
</details>

---

**Q5. What is the default value of an `int` field in a Java class?**
**Keywords:** default, zero, instance field, uninitialized
<details>
<summary>Click to Reveal Answer</summary>

`0`. Java initializes all instance fields to default values: `0` for numeric types, `false` for `boolean`, `'\u0000'` for `char`, and `null` for reference types.
</details>

---

**Q6. Explain short-circuit evaluation with `&&` and `||`.**
**Keywords:** short-circuit, false, true, second operand
<details>
<summary>Click to Reveal Answer</summary>

- `&&` — if the left operand is `false`, the right operand is **never evaluated** (result is already false).
- `||` — if the left operand is `true`, the right operand is **never evaluated** (result is already true).
This is useful for null-safety: `if (obj != null && obj.getValue() > 0)`.
</details>

---

**Q7. How do you declare and initialize a one-dimensional array of 5 integers in Java?**
**Keywords:** array, new, int[], square brackets, declaration
<details>
<summary>Click to Reveal Answer</summary>

```java
int[] arr = new int[5];        // declares and allocates (all zeros)
int[] arr = {1, 2, 3, 4, 5};  // declares and initializes with values
```
</details>

---

**Q8. What is the difference between `for`, `while`, and `do-while` loops?**
**Keywords:** condition, pre-check, post-check, iteration
<details>
<summary>Click to Reveal Answer</summary>

- `for` — best when the number of iterations is known; initializer, condition, and update are in one line.
- `while` — condition checked **before** each iteration; may never run if condition is false at start.
- `do-while` — condition checked **after** each iteration; always runs **at least once**.
</details>

---

**Q9. What is a Javadoc comment and how is it written?**
**Keywords:** `/** */`, documentation, `@param`, `@return`
<details>
<summary>Click to Reveal Answer</summary>

A Javadoc comment starts with `/**` and ends with `*/`. It describes the purpose of a class, method, or field and can include tags like `@param`, `@return`, and `@throws`. The `javadoc` tool generates HTML documentation from these comments.
</details>

---

**Q10. What is the purpose of `git add` vs. `git commit`?**
**Keywords:** staging area, snapshot, index, working tree
<details>
<summary>Click to Reveal Answer</summary>

- `git add` moves changes from the **working tree** to the **staging area** (index).
- `git commit` takes a **snapshot** of everything in the staging area and saves it to local history.
You must `add` before you can `commit`.
</details>

---

**Q11. What is a `.gitignore` file used for?**
**Keywords:** untracked, exclude, build artifacts, IDE files
<details>
<summary>Click to Reveal Answer</summary>

`.gitignore` lists file patterns that Git should **not track**. Common entries include `target/` (Maven build output), `.idea/` (IntelliJ config), and `*.class` (compiled bytecode).
</details>

---

### Intermediate (25%)

**Q12. You have `int x = 5; int y = 2;`. What is `x / y` vs. `x % y`? Why is the result different from `5 ÷ 2 = 2.5`?**
**Keywords:** integer division, truncation, modulo, remainder
**Hint:** Think about what integer division does to the decimal part.
<details>
<summary>Click to Reveal Answer</summary>

- `x / y` → `2` (integer division truncates the decimal, it does NOT round).
- `x % y` → `1` (remainder after dividing 5 by 2).

Result is `2` not `2.5` because both operands are `int`; to get `2.5`, cast to `double` first: `(double) x / y`.
</details>

---

**Q13. When would you choose a `do-while` loop over a `while` loop?**
**Keywords:** at least once, menu, user input, post-check
**Hint:** Think of programs that always need to show something before checking a condition.
<details>
<summary>Click to Reveal Answer</summary>

Use `do-while` when the body **must execute at least once** regardless of the condition — for example, a menu that must be shown to the user before they can input a choice. The condition is checked after the first run, so the block always executes initially.
</details>

---

**Q14. Explain the difference between `git merge` and `git rebase`. When should you prefer each?**
**Keywords:** history, linear, merge commit, rebase, shared branch
**Hint:** Think about what each one does to commit history.
<details>
<summary>Click to Reveal Answer</summary>

- `git merge` — creates a **merge commit** that combines two histories; preserves the full branching structure.
- `git rebase` — **replays** commits from one branch onto another, producing a linear history with no merge commit.

Use `merge` for shared/public branches (preserves history). Use `rebase` for local cleanup before merging a feature branch (creates cleaner linear history). **Never rebase a public shared branch** — it rewrites history for others.
</details>

---

### Advanced (5%)

**Q15. Describe how the JVM's Just-In-Time (JIT) compiler improves performance over pure bytecode interpretation.**
**Keywords:** JIT, hotspot, native code, compilation, interpretation
<details>
<summary>Click to Reveal Answer</summary>

The JVM initially interprets bytecode line-by-line (slow). The **JIT compiler** monitors execution and identifies **hotspots** — frequently executed code paths. It compiles those hotspots to **native machine code** at runtime, eliminating repeated interpretation overhead. Subsequent calls to those code paths run at near-native speed. This is why Java applications often get **faster over time** as the JIT warms up.
</details>

---

## Day 2 – Tuesday: Methods, Access Modifiers, Scope, AI Pair Programming

### Beginner (70%)

**Q16. What are the four components of a Java method signature?**
**Keywords:** return type, method name, parameter list, access modifier
<details>
<summary>Click to Reveal Answer</summary>

`[access modifier] [return type] [method name]([parameter list])`
Example: `public int add(int a, int b)`
</details>

---

**Q17. What is the difference between `public`, `private`, `protected`, and package-private access modifiers?**
**Keywords:** visibility, package, subclass, everywhere, class only
<details>
<summary>Click to Reveal Answer</summary>

| Modifier | Same Class | Same Package | Subclass | Everywhere |
|---|---|---|---|---|
| `private` | ✅ | ❌ | ❌ | ❌ |
| package-private (default) | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |
</details>

---

**Q18. What does a `void` return type mean?**
**Keywords:** void, no return value, return statement
<details>
<summary>Click to Reveal Answer</summary>

A `void` method does not return any value to the caller. It may contain `return;` (with no value) to exit early, but it cannot have `return someValue;`.
</details>

---

**Q19. What is method overloading?**
**Keywords:** same name, different parameters, compile-time, signature
<details>
<summary>Click to Reveal Answer</summary>

Method overloading is defining multiple methods **in the same class** with the **same name** but **different parameter lists** (different types, number, or order of parameters). The compiler selects the correct version at compile time based on arguments passed.
</details>

---

**Q20. What is the scope of a variable declared inside a method?**
**Keywords:** local variable, method scope, stack frame, destroyed
<details>
<summary>Click to Reveal Answer</summary>

A local variable exists only within the **method** (or the specific block `{}` it's declared in). It is created when the block is entered and destroyed when it exits. It cannot be accessed from outside the method.
</details>

---

**Q21. What is a prompt log and why is it important in AI pair programming?**
**Keywords:** prompt log, context, output, evaluation, iteration
<details>
<summary>Click to Reveal Answer</summary>

A prompt log is a personal record of: the **context** (what you were trying to do), the **prompt** sent to the AI, the **AI's output**, your **evaluation** of that output, and any **revised prompt** used. It builds self-awareness about prompt quality and creates a personal knowledge base for improving AI interactions.
</details>

---

**Q22. What is zero-shot prompting?**
**Keywords:** zero-shot, no examples, direct instruction, LLM
<details>
<summary>Click to Reveal Answer</summary>

Zero-shot prompting is asking an AI model to perform a task **without providing any examples**. You rely entirely on the model's pre-trained knowledge. Example: "Write a Java method that reverses a string."
</details>

---

**Q23. What is few-shot prompting and when should you use it?**
**Keywords:** few-shot, examples, pattern, in-context learning
<details>
<summary>Click to Reveal Answer</summary>

Few-shot prompting provides **1–3 input/output examples** before the actual request, so the AI learns the pattern from context. Use it when the format or style of the output is non-standard or when zero-shot produces poor results.
</details>

---

**Q24. What does `this` refer to in a Java instance method?**
**Keywords:** this, current object, instance, reference
<details>
<summary>Click to Reveal Answer</summary>

`this` refers to the **current instance** of the class — the object on which the method was called. It is commonly used to disambiguate between an instance field and a local parameter with the same name: `this.name = name;`.
</details>

---

**Q25. What is variable shadowing in Java?**
**Keywords:** shadowing, local variable, field, overrides, same name
<details>
<summary>Click to Reveal Answer</summary>

Shadowing occurs when a **local variable** (or parameter) has the **same name** as an instance field. Inside the method, the local variable takes precedence and "shadows" the field. Use `this.fieldName` to explicitly access the field.
</details>

---

### Intermediate (25%)

**Q26. You are asked to create a method that can accept either one, two, or three integers and return their sum. How do you design this in Java?**
**Keywords:** overloading, varargs, multiple signatures
**Hint:** Consider both method overloading and varargs.
<details>
<summary>Click to Reveal Answer</summary>

**Option 1 — Overloading:**
```java
int sum(int a) { return a; }
int sum(int a, int b) { return a + b; }
int sum(int a, int b, int c) { return a + b + c; }
```
**Option 2 — Varargs (more flexible):**
```java
int sum(int... nums) {
    int total = 0;
    for (int n : nums) total += n;
    return total;
}
```
Varargs is preferred when the number of arguments is truly variable.
</details>

---

**Q27. When reviewing AI-generated Java code, what specific things should you check before accepting it?**
**Keywords:** correctness, edge cases, compilation, test, blind acceptance
**Hint:** Think about what could go wrong with generated code.
<details>
<summary>Click to Reveal Answer</summary>

1. **Does it compile?** — AI sometimes generates syntactically incorrect code.
2. **Does it match requirements?** — AI may misunderstand the task.
3. **Edge cases** — null inputs, empty arrays, boundary values.
4. **Security** — SQL injection, unclosed resources, etc.
5. **Efficiency** — Is the algorithm unnecessarily slow?
6. **Tests** — Run unit tests to verify behavior.

Never accept AI code without reading, understanding, and testing it.
</details>

---

**Q28. Explain why `private` access is preferred for class fields in most cases.**
**Keywords:** encapsulation, controlled access, validation, getter, setter
**Hint:** Think about what could happen if external code modifies a field directly.
<details>
<summary>Click to Reveal Answer</summary>

`private` fields enforce **encapsulation** — external code cannot directly modify internal state. By routing access through getters and setters, you can:
- **Validate** values before assignment (e.g., reject negative age).
- **Change** the internal representation later without breaking external code.
- **Add** logging or computed logic when a value changes.
</details>

---

### Advanced (5%)

**Q29. Explain how Java resolves which overloaded method to call when the argument types don't exactly match any signature (e.g., passing a `byte` where both `int` and `long` versions exist).**
**Keywords:** widening, most specific, promotion, overload resolution
<details>
<summary>Click to Reveal Answer</summary>

Java uses **widening primitive conversion** — it promotes the argument to the most specific (narrowest) matching type. `byte` → `short` → `int` → `long`. If `int` and `long` versions both exist, the compiler picks `int` because it's the narrowest applicable type. If multiple widening paths exist with the same specificity, the compiler raises an **ambiguous method call** compile error.
</details>

---

**Q30. A method annotated with `@Deprecated` still compiles and runs. Why does Java keep deprecated methods instead of removing them?**
**Keywords:** backward compatibility, @Deprecated, binary compatibility, API
<details>
<summary>Click to Reveal Answer</summary>

Java enforces **backward compatibility** — removing a method from a public API would break existing compiled code that calls it. `@Deprecated` signals that the method is planned for removal in a future version and developers should migrate to the replacement. The method still works but the compiler emits a warning. Actual removal happens in a major version (with advance notice in the Javadoc).
</details>

---

## Day 3 – Wednesday: OOP Fundamentals, Classes, Inheritance, Interfaces

### Beginner (70%)

**Q31. What is the difference between a class and an object?**
**Keywords:** blueprint, instance, template, new, heap
<details>
<summary>Click to Reveal Answer</summary>

A **class** is a blueprint or template that defines fields and methods. An **object** is a concrete instance of a class, created with `new` and stored on the heap. You can create many objects from one class.
</details>

---

**Q32. What are the four pillars of OOP?**
**Keywords:** encapsulation, inheritance, polymorphism, abstraction
<details>
<summary>Click to Reveal Answer</summary>

1. **Encapsulation** — hiding internal state, exposing via methods.
2. **Inheritance** — a subclass inherits fields/methods from a superclass.
3. **Polymorphism** — one interface, many implementations.
4. **Abstraction** — hiding complexity, showing only essentials.
</details>

---

**Q33. What keyword is used to create a subclass in Java?**
**Keywords:** extends, superclass, subclass, inheritance
<details>
<summary>Click to Reveal Answer</summary>

`extends`. Example: `class Dog extends Animal {}`. Java supports single class inheritance only (one superclass per class).
</details>

---

**Q34. What is an abstract class?**
**Keywords:** abstract, cannot instantiate, abstract method, subclass
<details>
<summary>Click to Reveal Answer</summary>

An abstract class is declared with the `abstract` keyword and **cannot be instantiated directly**. It may contain abstract methods (no body) that subclasses must implement, as well as concrete methods (with body).
</details>

---

**Q35. What is the difference between an abstract class and an interface?**
**Keywords:** interface, abstract class, multiple, implements, default method
<details>
<summary>Click to Reveal Answer</summary>

| | Abstract Class | Interface |
|---|---|---|
| Instantiatable | No | No |
| Multiple inheritance | No (one only) | Yes (implement many) |
| Fields | Any type | `public static final` only |
| Constructor | Yes | No |
| Method bodies | Yes | Only `default`/`static` (Java 8+) |

Use abstract classes for shared state/behavior; use interfaces to define contracts a class must fulfill.
</details>

---

**Q36. What is autoboxing in Java?**
**Keywords:** autoboxing, primitive, wrapper, Integer, automatic
<details>
<summary>Click to Reveal Answer</summary>

Autoboxing is the **automatic conversion** of a primitive to its corresponding wrapper class (e.g., `int` → `Integer`). Unboxing is the reverse. This happens automatically when primitives are used where objects are expected (e.g., in collections).
```java
List<Integer> list = new ArrayList<>();
list.add(42);  // 42 (int) is autoboxed to Integer
```
</details>

---

**Q37. What does `String.equals()` do differently than `==` for strings?**
**Keywords:** content, reference, equality, equals, String pool
<details>
<summary>Click to Reveal Answer</summary>

`==` compares **references** (are these the same object in memory?). `.equals()` compares **content** (do these two strings have the same characters?). Two `String` objects created with `new String("hello")` will have `==` return `false` but `.equals()` return `true`.
</details>

---

**Q38. What is a `static` field and how does it differ from an instance field?**
**Keywords:** static, shared, class-level, instance, one copy
<details>
<summary>Click to Reveal Answer</summary>

A `static` field belongs to the **class** and is **shared across all instances**. Changing it in one object changes it for all. An instance field belongs to each individual object and has a separate value per object.
</details>

---

**Q39. What is the `this` keyword used for in a constructor?**
**Keywords:** this, constructor chaining, field disambiguation, this()
<details>
<summary>Click to Reveal Answer</summary>

In a constructor, `this` is used for two things:
1. **Disambiguate** field vs. parameter: `this.name = name;`
2. **Constructor chaining**: `this(arg1, arg2)` calls another constructor in the same class (must be the first statement).
</details>

---

**Q40. How does AI help with knowledge lookup when learning Java?**
**Keywords:** documentation, explanation, API, validation, critical evaluation
<details>
<summary>Click to Reveal Answer</summary>

AI can explain concepts, show API usage, compare alternatives, and provide code examples — much faster than searching documentation. However, you must **critically validate** AI answers against official Java documentation, as AI can occasionally produce outdated or incorrect information (hallucinations).
</details>

---

### Intermediate (25%)

**Q41. Design a simple `Shape` hierarchy with a `Circle` and `Rectangle`. What would you make abstract and why?**
**Keywords:** abstract, area(), perimeter(), override, hierarchy
**Hint:** Think about what all shapes share vs. what is unique to each shape.
<details>
<summary>Click to Reveal Answer</summary>

```java
abstract class Shape {
    abstract double area();
    abstract double perimeter();
    void describe() { System.out.println("Area: " + area()); }
}
class Circle extends Shape {
    double radius;
    Circle(double r) { this.radius = r; }
    @Override double area() { return Math.PI * radius * radius; }
    @Override double perimeter() { return 2 * Math.PI * radius; }
}
class Rectangle extends Shape {
    double width, height;
    Rectangle(double w, double h) { this.width = w; this.height = h; }
    @Override double area() { return width * height; }
    @Override double perimeter() { return 2 * (width + height); }
}
```
`area()` and `perimeter()` are abstract because **the formula differs per shape**. `describe()` is concrete because all shapes print the area the same way.
</details>

---

**Q42. Why must you override `hashCode()` whenever you override `equals()`?**
**Keywords:** contract, hashCode, equals, HashSet, HashMap
**Hint:** Think about how hash-based collections use these two methods together.
<details>
<summary>Click to Reveal Answer</summary>

The Java contract states: if `a.equals(b)` is `true`, then `a.hashCode() == b.hashCode()` **must** also be true. Hash-based collections (`HashMap`, `HashSet`) first bucket objects by `hashCode()`, then use `equals()` to find the exact match. If you override `equals()` without `hashCode()`, two logically equal objects may land in different buckets, causing the collection to behave incorrectly (e.g., duplicate entries in a `Set`).
</details>

---

**Q43. When would you choose an interface over an abstract class for designing a type hierarchy?**
**Keywords:** multiple inheritance, contract, stateless, implementation
**Hint:** Think about what a class needs to "be" vs. what it needs to "do".
<details>
<summary>Click to Reveal Answer</summary>

Choose an **interface** when:
- You need **multiple inheritance** (a class can implement many interfaces).
- You are defining a **capability/contract** (e.g., `Flyable`, `Serializable`) without shared state.
- The types are unrelated but share a behavior.

Choose an **abstract class** when:
- Subclasses **share common state** (fields) and common concrete behavior.
- You need a **constructor** to enforce initialization.
- The relationship is a true "is-a" hierarchy.
</details>

---

### Advanced (5%)

**Q44. Explain the String Pool in Java and why `new String("hello") == "hello"` evaluates to `false`.**
**Keywords:** String pool, interning, heap, literal, new
<details>
<summary><b>Click to Reveal Answer</b></summary>

Java maintains a **String Pool** (part of the heap) for string literals. When you write `"hello"`, Java places it in the pool and reuses the same object for all identical literals. When you use `new String("hello")`, you force creation of a **new object on the heap outside the pool**, with a different reference. Hence `==` (reference comparison) returns `false`. Use `.equals()` for content comparison, or `String.intern()` to explicitly add a string to the pool.
</details>

---

## Day 4 – Thursday: Polymorphism, Encapsulation, Object Class, GC, AI Rulesets

### Beginner (70%)

**Q45. What is runtime polymorphism in Java?**
**Keywords:** dynamic dispatch, override, JVM, runtime, reference
<details>
<summary>Click to Reveal Answer</summary>

Runtime polymorphism (dynamic dispatch) means the JVM decides **which overridden method to call at runtime**, based on the actual object type — not the reference type. Example: `Animal a = new Dog(); a.sound();` calls `Dog.sound()` at runtime, even though the reference is `Animal`.
</details>

---

**Q46. What is the difference between method overloading and method overriding?**
**Keywords:** overloading, overriding, compile-time, runtime, signature
<details>
<summary>Click to Reveal Answer</summary>

| | Overloading | Overriding |
|---|---|---|
| Location | Same class | Subclass |
| Signature | **Different** parameters | **Same** signature |
| Resolved at | Compile time | Runtime |
| Keyword | None needed | `@Override` recommended |
</details>

---

**Q47. What is encapsulation and why is it important?**
**Keywords:** private, getter, setter, data hiding, validation
<details>
<summary>Click to Reveal Answer</summary>

Encapsulation bundles data (fields) and methods that operate on that data into a class, and **restricts direct access** to fields using `private`. Getters and setters provide controlled access. This protects object integrity (e.g., preventing invalid state), enables validation, and allows internal implementation changes without breaking external code.
</details>

---

**Q48. What is the `@Override` annotation and why should you use it?**
**Keywords:** @Override, compile-time, safety, typo, superclass
<details>
<summary>Click to Reveal Answer</summary>

`@Override` tells the compiler "this method is intended to override a superclass/interface method." If no matching method exists in the parent, the compiler throws an error — catching accidental typos that would otherwise create a new method silently instead of overriding.
</details>

---

**Q49. List three methods inherited from `java.lang.Object` that are commonly overridden.**
**Keywords:** toString, equals, hashCode, Object, override
<details>
<summary>Click to Reveal Answer</summary>

1. `toString()` — returns a string representation of the object.
2. `equals(Object o)` — checks logical equality.
3. `hashCode()` — returns integer hash code (must be consistent with `equals`).
</details>

---

**Q50. What does garbage collection do in Java?**
**Keywords:** GC, unreachable, heap, memory, automatic
<details>
<summary>Click to Reveal Answer</summary>

Garbage Collection (GC) automatically identifies and frees heap memory occupied by objects that are **no longer reachable** (no live reference points to them). The JVM runs GC non-deterministically; developers cannot force it (only suggest via `System.gc()`). This prevents most memory leaks without manual memory management.
</details>

---

**Q51. What is a personal AI ruleset and why should a developer create one?**
**Keywords:** ruleset, when to prompt, document, discipline, oversight
<details>
<summary>Click to Reveal Answer</summary>

A personal AI ruleset is a documented set of rules defining **when** to use AI (e.g., "use AI for boilerplate, not for business logic"), **what to document** (prompt + result + evaluation), and **what never to accept blindly**. It prevents over-reliance on AI and builds disciplined, reviewable AI usage habits.
</details>

---

**Q52. What does `upcasting` mean in Java?**
**Keywords:** upcast, superclass reference, subclass object, implicit, safe
<details>
<summary>Click to Reveal Answer</summary>

Upcasting assigns a **subclass object to a superclass reference**: `Animal a = new Dog();`. It is **implicit and always safe** because a `Dog` IS-AN `Animal`. Through the `Animal` reference, only `Animal` methods are accessible (not Dog-specific ones) unless the method is overridden.
</details>

---

**Q53. What is `downcasting` and when can it fail?**
**Keywords:** downcast, ClassCastException, instanceof, explicit cast
<details>
<summary>Click to Reveal Answer</summary>

Downcasting converts a superclass reference back to a subclass type: `Dog d = (Dog) a;`. It is **explicit** and can throw `ClassCastException` at runtime if the object is not actually an instance of the target type. Always check with `instanceof` first: `if (a instanceof Dog) { Dog d = (Dog) a; }`.
</details>

---

**Q50b. What is the `final` keyword used for in Java (for variables, methods, and classes)?**
**Keywords:** final, immutable, prevent override, prevent subclassing
<details>
<summary>Click to Reveal Answer</summary>

- `final` **variable** — value cannot be changed after assignment (effectively a constant).
- `final` **method** — cannot be overridden in subclasses.
- `final` **class** — cannot be subclassed (e.g., `String`, `Integer`).
</details>

---

### Intermediate (25%)

**Q54. Refactor this code to enforce proper encapsulation:**
```java
public class Student {
    public String name;
    public int age;
}
```
**Keywords:** private, getter, setter, validation, encapsulation
**Hint:** Consider what validation makes sense for `age`.
<details>
<summary>Click to Reveal Answer</summary>

```java
public class Student {
    private String name;
    private int age;

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        this.name = name;
    }

    public int getAge() { return age; }
    public void setAge(int age) {
        if (age < 0 || age > 150) throw new IllegalArgumentException("Invalid age: " + age);
        this.age = age;
    }
}
```
</details>

---

**Q55. Explain how you would use AI task planning to break down building a `Library` management system into subtasks. What must you validate after receiving the AI plan?**
**Keywords:** task planning, subtasks, validate, requirements, blind acceptance
**Hint:** AI plans can be too generic — how do you make them project-specific?
<details>
<summary>Click to Reveal Answer</summary>

Ask the AI: "Break down a Java Library management system into implementation subtasks." The AI may return steps like: define entities, implement repositories, add services, etc. **Validate by:**
1. Checking that every AI subtask maps to an actual requirement.
2. Adding domain-specific subtasks the AI missed (e.g., "handle overdue fines").
3. Verifying the suggested sequence is feasible (dependencies in the right order).
4. Testing each subtask individually, not just the final product.
</details>

---

**Q56. Describe a scenario where overriding `toString()` on a custom class is essential. Show the implementation.**
**Keywords:** toString, debug, logging, readable, override
<details>
<summary>Click to Reveal Answer</summary>

Without `toString()`, `System.out.println(myObj)` prints something like `com.example.Book@6d06d69c`. By overriding, you get meaningful output:
```java
public class Book {
    private String title;
    private String author;
    Book(String title, String author) { this.title = title; this.author = author; }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "'}";
    }
}
// System.out.println(new Book("Clean Code", "Martin")) → Book{title='Clean Code', author='Martin'}
```
This is invaluable for debugging and logging.
</details>

---

### Advanced (5%)

**Q57. Explain what happens in memory when `Animal a = new Dog(); Dog d = (Dog) a;` is executed. Why doesn't the JVM forget that `a` was actually a `Dog`?**
**Keywords:** reference, object type, runtime type, JVM, class metadata
<details>
<summary>Click to Reveal Answer</summary>

When `new Dog()` is executed, the JVM creates a `Dog` object on the heap. The object carries a pointer to its **class metadata** (`Dog.class`) — this never changes. When assigned to `Animal a`, only the **reference type** changes; the object itself is still a `Dog`. The JVM always knows the actual runtime type via the class metadata. `instanceof` checks this metadata. Downcasting `(Dog) a` is safe because the JVM can verify at runtime that the object's class is indeed `Dog` (or a subclass of it).
</details>

---

## Day 5 – Friday: Exceptions, Collections Framework, Iterators, AI Issue Tracking

### Beginner (70%)

**Q58. What is the difference between checked and unchecked exceptions?**
**Keywords:** checked, unchecked, RuntimeException, must handle, compile time
<details>
<summary>Click to Reveal Answer</summary>

- **Checked exceptions** extend `Exception` (not `RuntimeException`). They **must** be either caught with `try-catch` or declared with `throws`. Examples: `IOException`, `SQLException`.
- **Unchecked exceptions** extend `RuntimeException`. The compiler does not require you to handle them. Examples: `NullPointerException`, `ArrayIndexOutOfBoundsException`.
</details>

---

**Q59. What is the purpose of the `finally` block?**
**Keywords:** finally, cleanup, always runs, resource, close
<details>
<summary>Click to Reveal Answer</summary>

`finally` contains **cleanup code** that always executes after the `try` block, regardless of whether an exception was thrown or caught. It is commonly used to close resources (files, database connections). `try-with-resources` is the modern alternative for `AutoCloseable` resources.
</details>

---

**Q60. How do you create a custom exception in Java?**
**Keywords:** extends, Exception, RuntimeException, constructor, custom
<details>
<summary>Click to Reveal Answer</summary>

Extend `Exception` (checked) or `RuntimeException` (unchecked):
```java
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
```
Throw it with `throw new InsufficientFundsException("Balance too low");`.
</details>

---

**Q61. What is the difference between `ArrayList` and `LinkedList`?**
**Keywords:** ArrayList, LinkedList, O(1), O(n), random access, insertion
<details>
<summary>Click to Reveal Answer</summary>

| | `ArrayList` | `LinkedList` |
|---|---|---|
| Backing structure | Dynamic array | Doubly-linked list |
| Random access (`get`) | O(1) | O(n) |
| Insert/delete (middle) | O(n) — shift required | O(1) at known node |
| Memory | Less overhead | More (stores next/prev pointers) |

Use `ArrayList` for frequent reads; use `LinkedList` for frequent insertions/deletions at ends.
</details>

---

**Q62. What is a `Set` and how does it differ from a `List`?**
**Keywords:** Set, unique, no duplicates, List, ordered, allows duplicates
<details>
<summary>Click to Reveal Answer</summary>

- `List` — ordered collection, **allows duplicates**, elements accessible by index.
- `Set` — collection that **guarantees uniqueness**; no duplicates allowed; most implementations do not guarantee order (except `LinkedHashSet` and `TreeSet`).
</details>

---

**Q63. When would you use a `Map` instead of a `List`?**
**Keywords:** Map, key-value, lookup, O(1), association
<details>
<summary>Click to Reveal Answer</summary>

Use a `Map` when you need **key-value associations** and fast lookup by key. Example: storing student records by student ID (`Map<Integer, Student>`). `HashMap` provides O(1) average lookup by key, whereas finding an element in a `List` by value is O(n).
</details>

---

**Q64. What does the `Iterator` pattern allow you to do?**
**Keywords:** Iterator, traverse, hasNext, next, remove, sequential
<details>
<summary>Click to Reveal Answer</summary>

An `Iterator` allows you to **sequentially traverse** a collection without exposing its internal structure. Key methods:
- `hasNext()` — returns `true` if more elements exist.
- `next()` — returns the next element.
- `remove()` — safely removes the last element returned (avoids `ConcurrentModificationException`).
</details>

---

**Q65. What is a `ConcurrentModificationException` and how do you avoid it?**
**Keywords:** ConcurrentModificationException, iterator, structural modification, remove
<details>
<summary>Click to Reveal Answer</summary>

Thrown when a collection is **structurally modified** (add/remove) while iterating with a `for-each` or `Iterator`. Avoid by:
1. Using `Iterator.remove()` instead of `Collection.remove()` during iteration.
2. Collecting items to remove, then removing after the loop.
3. Using thread-safe collections like `CopyOnWriteArrayList`.
</details>

---

**Q66. What is `try-with-resources` and what interface must a resource implement to use it?**
**Keywords:** try-with-resources, AutoCloseable, close, automatic, resource leak
<details>
<summary>Click to Reveal Answer</summary>

`try-with-resources` automatically calls `close()` on declared resources when the try block exits (normally or via exception). The resource class must implement `AutoCloseable` (or its sub-interface `Closeable`).
```java
try (FileReader fr = new FileReader("file.txt")) {
    // use fr
} // fr.close() called automatically
```
</details>

---

**Q67. How do you use AI for issue tracking and resolution?**
**Keywords:** AI, bug, stack trace, context, fix suggestion, log
<details>
<summary>Click to Reveal Answer</summary>

Provide the AI with: the **stack trace**, the **code snippet** causing the issue, and the **expected vs. actual behavior**. AI can suggest root causes and fixes. Log each issue, the AI suggestion, and the final resolution. Track whether AI suggestions were accurate to improve future prompting.
</details>

---

### Intermediate (25%)

**Q68. You need to store a unique list of employee names that preserves insertion order. Which collection do you choose and why?**
**Keywords:** LinkedHashSet, insertion order, unique, Set
**Hint:** Think about which Set implementation maintains insertion order.
<details>
<summary>Click to Reveal Answer</summary>

`LinkedHashSet` — it implements `Set` (enforces uniqueness) and uses a linked list internally to maintain **insertion order**. `HashSet` would lose order; `TreeSet` would sort alphabetically rather than preserve insertion order.
</details>

---

**Q69. Implement a method that reads integers from a `List`, removes all negative values, and returns the remaining sum. Handle potential `null` input.**
**Keywords:** Iterator, remove, null check, sum, defensive programming
**Hint:** Use `Iterator.remove()` to avoid ConcurrentModificationException.
<details>
<summary>Click to Reveal Answer</summary>

```java
public int sumPositives(List<Integer> numbers) {
    if (numbers == null) throw new IllegalArgumentException("Input list cannot be null");
    Iterator<Integer> it = numbers.iterator();
    int sum = 0;
    while (it.hasNext()) {
        int val = it.next();
        if (val < 0) {
            it.remove();  // safe removal during iteration
        } else {
            sum += val;
        }
    }
    return sum;
}
```
</details>

---

**Q70. Design a custom checked exception `InsufficientStockException` for an inventory system. Where in the call stack should it be caught?**
**Keywords:** checked, constructor, throw, catch, service layer
**Hint:** Think about which layer has enough context to handle a stock shortage.
<details>
<summary>Click to Reveal Answer</summary>

```java
public class InsufficientStockException extends Exception {
    private final int requested;
    private final int available;
    public InsufficientStockException(int requested, int available) {
        super("Requested: " + requested + ", Available: " + available);
        this.requested = requested;
        this.available = available;
    }
    public int getRequested() { return requested; }
    public int getAvailable() { return available; }
}
```
Catch it at the **service or controller layer** where you can return a meaningful user response (e.g., HTTP 409 Conflict), not deep in the repository where context is limited.
</details>

---

### Advanced (5%)

**Q71. Explain how `HashMap` handles hash collisions internally and what changes Java 8 introduced to improve worst-case performance.**
**Keywords:** collision, chaining, treeify, threshold, O(n), O(log n)
<details>
<summary>Click to Reveal Answer</summary>

When two keys hash to the same bucket, `HashMap` uses **chaining** — storing colliding entries as a linked list in that bucket. In the worst case (many collisions), lookup degrades to O(n). **Java 8** introduced **treeification**: when a bucket's chain exceeds **8 entries** (and the table has at least 64 buckets), the chain is converted to a **Red-Black Tree**, reducing worst-case lookup to **O(log n)**. The tree reverts to a linked list if entries drop below 6.
</details>

---

*I have generated the Interview Question Bank for Week 1 (Java Full Stack) with 71 questions following the 70-25-5 distribution. Please review.*
