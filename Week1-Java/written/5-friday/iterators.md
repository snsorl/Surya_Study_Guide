# Iterators

## Learning Objectives
- Explain the design pattern behind the `java.util.Iterator` interface.
- Traverse Collections safely using the `hasNext()`, `next()`, and `remove()` methods.
- Diagnose and prevent the **`ConcurrentModificationException`** crash.
- Differentiate between read-only traversal (for-each) and read-write traversal (Iterator).

---

## Why This Matters
Traversing a collection to print its values or find an element is one of the most basic tasks in programming. Java provides a clean syntax for this: the `for-each` loop (`for (String item : list)`). 

However, if you try to delete elements from your collection while looping through it (for example, removing expired users from a list), your application will crash immediately with a **`ConcurrentModificationException`**. This happens because you are modifying the structure of the collection while the loop is actively reading it, causing memory pointer offsets. To mutate a collection safely during traversal, you must bypass the standard loop syntax and use an **`Iterator`**.

---

## The Concept

### The `ConcurrentModificationException` Pitfall
Under the hood, when you write a standard `for-each` loop, the Java compiler automatically translates it into an implicit Iterator loop:

```java
// What you write:
for (String item : list) {
    if (item.equals("Delete")) {
        list.remove(item); // CRASH! Direct collection modification inside active loop!
    }
}
```

#### Why it crashes:
The collection maintains an internal counter named **`modCount`** that tracks every structural modification (adds, removes). The loop's implicit iterator keeps a copy of this count (**`expectedModCount`**). 
If you bypass the iterator and call `list.remove(item)` directly on the collection, the collection's `modCount` changes, but the iterator's `expectedModCount` does not. During the next loop iteration, the iterator checks these values, detects they are out of sync, and throws a `ConcurrentModificationException` to prevent memory corruption.

---

### The `Iterator` Interface Solution
An `Iterator` is an object that acts as a cursor walking through a collection. It exposes three primary methods:
*   **`hasNext()`**: Returns `true` if the collection has more elements to visit.
*   **`next()`**: Returns the next element and advances the cursor.
*   **`remove()`**: **Safely deletes** the last returned element from the underlying collection, synchronization-updating the modification counters.

```
                      [ ITERATOR CURSOR ]
                               │
               Has next? ──────┼──────> [ YES ]
                               │           │
                               ▼           ▼
                      Retrieve element via next()
                               │
                       Needs removal?
                            /     \
                       [ YES ]   [ NO ]
                         /         \
            Safe remove()           Loop next
```

---

## Code Example: Safe Element Deletion
The following program defines a list of product codes, demonstrates a loop crash, and illustrates how to perform safe element removals using an explicit `Iterator`:

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {
    public static void main(String[] args) {
        // Populating initial list of product codes
        List<String> codes = new ArrayList<>();
        codes.add("PRD-101");
        codes.add("TEMP-99");
        codes.add("PRD-202");
        codes.add("TEMP-88");
        codes.add("PRD-303");

        System.out.println("=== 1. The For-Each Loop Modification Crash ===");
        try {
            System.out.println("Attempting to delete temporary codes using for-each...");
            for (String code : codes) {
                if (code.startsWith("TEMP")) {
                    codes.remove(code); // Unsafe modification!
                }
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("[CRASH PREVENTED] Caught ConcurrentModificationException!");
            System.out.println("Reason: Direct collection removal bypassed active loop tracking.");
        }

        System.out.println("\nOriginal list state: " + codes); // [PRD-101, PRD-202, TEMP-88, PRD-303] (Partially corrupted)

        System.out.println("\n=== 2. Safe Deletion using Explicit Iterator ===");
        // Retrieve the iterator from the collection instance
        Iterator<String> iterator = codes.iterator();

        while (iterator.hasNext()) {
            // A. Retrieve the element
            String code = iterator.next(); 
            
            // B. Check condition
            if (code.startsWith("TEMP")) {
                // C. Safely remove element via the iterator
                iterator.remove(); 
                System.out.println("[ITERATOR] Safely removed element: " + code);
            }
        }

        System.out.println("\nFinal verified list state: " + codes); // [PRD-101, PRD-202, PRD-303] (Clean!)
    }
}
```

---

## Summary
- Modifying a collection (adding or removing) inside a standard **`for-each` loop** triggers a **`ConcurrentModificationException`** runtime crash.
- An **`Iterator`** is a design pattern that provides a safe cursor for traversing and modifying collections in place.
- Use **`iterator.hasNext()`** to check bounds, **`iterator.next()`** to retrieve items, and **`iterator.remove()`** to delete items safely.
- The `iterator.remove()` method updates internal modification counters to keep stack states in sync.

---

## Additional Resources
- [The Iterable and Iterator Interfaces - Oracle Docs](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html)
- [Java Iterator Reference - W3Schools](https://www.w3schools.com/java/java_iterator.asp)
- [How to Avoid ConcurrentModificationException - Baeldung](https://www.baeldung.com/java-concurrentmodificationexception)