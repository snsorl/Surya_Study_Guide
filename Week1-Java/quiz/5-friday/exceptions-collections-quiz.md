# Weekly Knowledge Check: Week 1-Java – Friday (Exceptions & Collections Framework)

---

## Part 1: Multiple Choice

### Q1. Which of the following is a checked exception?
- [ ] A) `NullPointerException`
- [ ] B) `IOException`
- [ ] C) `ArithmeticException`
- [ ] D) `ArrayIndexOutOfBoundsException`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `IOException`

**Explanation:** Checked exceptions (like `IOException`) extend `Exception` (not `RuntimeException`) and must be declared with `throws` or caught.

**Why others are wrong:**
- A) `NullPointerException` extends `RuntimeException` — it is unchecked.
- C) `ArithmeticException` extends `RuntimeException` — it is unchecked.
- D) `ArrayIndexOutOfBoundsException` extends `RuntimeException` — it is unchecked.
</details>

---

### Q2. What is the correct hierarchy from broadest to narrowest?
- [ ] A) `Object` → `Exception` → `Throwable` → `RuntimeException`
- [ ] B) `Throwable` → `Error` → `Exception` → `RuntimeException`
- [ ] C) `Throwable` → `Exception` → `RuntimeException` → `Error`
- [ ] D) `Exception` → `Throwable` → `RuntimeException` → `Error`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Throwable` → `Error` → `Exception` → `RuntimeException`

**Explanation:** `Throwable` is the root; it has two branches: `Error` (JVM-level, unrecoverable) and `Exception` (recoverable), which further branches into `RuntimeException` (unchecked).

**Why others are wrong:**
- A) `Throwable` must come before `Exception`, not `Object`.
- C) `Error` is a sibling of `Exception`, not a child.
- D) `Exception` is a child of `Throwable`, not the other way.
</details>

---

### Q3. What does the `finally` block guarantee?
- [ ] A) It only runs if an exception is thrown
- [ ] B) It only runs if no exception is thrown
- [ ] C) It always runs after try/catch, whether or not an exception occurred
- [ ] D) It prevents exceptions from propagating

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) It always runs after try/catch, whether or not an exception occurred

**Explanation:** `finally` is designed for cleanup code that must run regardless of whether an exception was thrown.

**Why others are wrong:**
- A) `finally` runs even without exceptions.
- B) `finally` runs even when an exception IS thrown.
- D) `finally` does not suppress exceptions; `catch` handles them.
</details>

---

### Q4. Which `List` implementation provides O(1) index access but O(n) insertion in the middle?
- [ ] A) `LinkedList`
- [ ] B) `TreeSet`
- [ ] C) `ArrayList`
- [ ] D) `HashMap`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `ArrayList`

**Explanation:** `ArrayList` is backed by an array — random access is O(1), but inserting/removing in the middle requires shifting elements (O(n)).

**Why others are wrong:**
- A) `LinkedList` has O(n) for index access but O(1) for insertion/deletion at known nodes.
- B) `TreeSet` is a `Set`, not a `List`; it stores unique elements in sorted order.
- D) `HashMap` is a `Map`, not a `List`.
</details>

---

### Q5. Which `Map` implementation stores keys in sorted (natural) order?
- [ ] A) `HashMap`
- [ ] B) `LinkedHashMap`
- [ ] C) `TreeMap`
- [ ] D) `HashSet`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `TreeMap`

**Explanation:** `TreeMap` uses a Red-Black Tree internally to keep keys in natural sorted order.

**Why others are wrong:**
- A) `HashMap` has no guaranteed order.
- B) `LinkedHashMap` maintains insertion order, not sorted order.
- D) `HashSet` is not a `Map` implementation.
</details>

---

### Q6. What exception is thrown when an iterator's underlying collection is modified during iteration?
- [ ] A) `IndexOutOfBoundsException`
- [ ] B) `ConcurrentModificationException`
- [ ] C) `UnsupportedOperationException`
- [ ] D) `IllegalStateException`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `ConcurrentModificationException`

**Explanation:** If a collection is structurally modified while an iterator is active (without using the iterator's own `remove()`), a `ConcurrentModificationException` is thrown.

**Why others are wrong:**
- A) `IndexOutOfBoundsException` is for invalid index access on lists.
- C) `UnsupportedOperationException` is thrown when calling a mutating method on an unmodifiable collection.
- D) `IllegalStateException` is thrown if `remove()` is called before `next()`.
</details>

---

### Q7. To create a custom exception that does NOT need to be declared with `throws`, you should extend:
- [ ] A) `Throwable`
- [ ] B) `Exception`
- [ ] C) `RuntimeException`
- [ ] D) `Error`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `RuntimeException`

**Explanation:** Subclasses of `RuntimeException` are unchecked — callers are not forced to catch or declare them.

**Why others are wrong:**
- A) `Throwable` should not be extended directly; use `Exception` or `Error`.
- B) `Exception` creates a checked exception that must be declared.
- D) `Error` is for JVM-level unrecoverable failures like `OutOfMemoryError`.
</details>

---

### Q8. Which of the following reads the top-to-bottom order in a Java stack trace correctly?
- [ ] A) The root cause is at the bottom; the most recent call is at the top
- [ ] B) The root cause is at the top; the main method is at the bottom
- [ ] C) Stack traces are random — order doesn't matter
- [ ] D) The line that called `main()` is always first

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) The root cause is at the bottom; the most recent call is at the top

**Explanation:** Stack traces show the call stack from most recent (top) to oldest frame. The line that threw the exception is at the top; `main()` is at the bottom.

**Why others are wrong:**
- B) Root cause is at the top of the *exception message* but the thrower is the first frame, and main is the last.
- C) Stack traces are ordered by the call stack.
- D) `main()` is the deepest (last) frame in the trace.
</details>

---

## Part 2: True / False

### Q9. `try-with-resources` requires the resource to implement `AutoCloseable`.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Only objects that implement `AutoCloseable` (or its sub-interface `Closeable`) can be used in `try-with-resources`.
</details>

---

### Q10. `HashSet` allows duplicate elements.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** `HashSet` implements `Set`, which enforces uniqueness. Adding a duplicate element returns `false` and does not modify the set.
</details>

---

### Q11. A `HashMap` is synchronized (thread-safe) by default.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** `HashMap` is not thread-safe. Use `ConcurrentHashMap` or wrap with `Collections.synchronizedMap()` for concurrent access.
</details>

---

### Q12. You can have multiple `catch` blocks for a single `try` block.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Java allows multi-catch: different exception types handled by different catch blocks, or combined using the `|` operator in a single catch.
</details>

---

## Part 3: Code Prediction

### Q13. What is the output?
```java
import java.util.*;
public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5));
        Collections.sort(list);
        System.out.println(list);
    }
}
```
- [ ] A) `[3, 1, 4, 1, 5]`
- [ ] B) `[5, 4, 3, 1, 1]`
- [ ] C) `[1, 1, 3, 4, 5]`
- [ ] D) Compile error — `List` cannot be sorted

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `[1, 1, 3, 4, 5]`

**Explanation:** `Collections.sort()` sorts in ascending natural order. Note that duplicates (1, 1) are preserved because `List` allows them.

**Why others are wrong:**
- A) That is the original unsorted order.
- B) That is descending order.
- D) `ArrayList<Integer>` supports sorting via `Collections.sort`.
</details>

---

### Q14. What is the output of this code?
```java
public class Test {
    public static void main(String[] args) {
        try {
            int result = 10 / 0;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("Finally block");
        }
    }
}
```
- [ ] A) `Result: 0` then `Finally block`
- [ ] B) `Caught: / by zero` then `Finally block`
- [ ] C) `Caught: / by zero` only
- [ ] D) Program crashes with no output

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Caught: / by zero` then `Finally block`

**Explanation:** `10 / 0` throws `ArithmeticException`, caught by the catch block printing the message; then `finally` always runs.

**Why others are wrong:**
- A) Division by zero does not produce 0; it throws an exception.
- C) `finally` always executes.
- D) The exception is caught, so the program does not crash.
</details>

---

## Part 4: Fill-in-the-Blank

### Q15. The `Map` method that retrieves a value by key, returning `null` if the key does not exist, is called `_____`.
<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `get`

**Explanation:** `map.get(key)` returns the value associated with the key, or `null` if the key is absent. Use `getOrDefault(key, default)` to supply a fallback.
</details>
