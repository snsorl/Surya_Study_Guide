# Value and Reference Types in Memory

## Learning Objectives
- Compare and contrast Stack and Heap memory in terms of structure, speed, and lifecycle.
- Classify variables as either value (primitive) types or reference (object) types.
- Trace memory layouts (Stack and Heap) during parameter passing in Java.
- Describe the memory mechanics of modifying vs. reassigning reference arguments inside methods.

---

## Why This Matters
One of the most common sources of bugs for junior Java developers is a misunderstanding of how Java manages memory under the hood. 

For example, if you write a method that updates a customer's address or changes the price of an item in a cart, you might notice that sometimes the changes are visible outside the method, and sometimes they are not. This is not random; it is governed by the strict rules of **Stack and Heap memory allocation**. By mastering how value types and reference types behave in memory, you can write highly efficient, predictable code, troubleshoot memory leaks, and gain a clear mental model of how your objects live and die at runtime.

---

## The Concept

### Stack vs. Heap Memory
Java divides its runtime memory into two primary zones:

```
STACK                                            HEAP
- Stores local primitive variables               - Stores all objects and arrays
- Stores reference pointers (addresses)          - Managed by the Garbage Collector
- Fast access, small capacity                    - Slower access, large capacity
- Cleans up when block/method exits              - Exists as long as references exist
```

| Feature | Stack Memory | Heap Memory |
| :--- | :--- | :--- |
| **Data Stored** | Local primitives and reference address variables. | All objects, arrays, and class instances. |
| **Access Speed**| Extremely fast (managed directly by the CPU). | Slower (requires dereferencing pointers). |
| **Lifecycle** | Tied directly to the active method call frame. | Exists until cleaned up by the Garbage Collector. |
| **Capacity** | Fixed, relatively small (risks `StackOverflow`). | Dynamic, large (risks `OutOfMemoryError`). |

---

### Value Types (Primitives) vs. Reference Types
*   **Value Types (Primitives)**: The variable container on the Stack holds the **raw value** directly (e.g. `int age = 25`).
*   **Reference Types**: The variable container on the Stack holds a **memory address (reference pointer)** that points to the actual data object allocated on the Heap (e.g. `int[] scores = new int[3]`).

---

### Parameter Passing: Memory Mechanics
Because Java is strictly **Pass-by-Value**, passing an argument to a method copies the data stored on the Stack:

#### Scenario A: Passing Primitives
When you pass a primitive variable:
1. Java copies the raw value (e.g., `45`) into the method parameter variable.
2. The parameter lives on a separate Stack frame.
3. Modifying the parameter variable does **not** change the caller's variable.

#### Scenario B: Passing References (The Modification Side-Effect)
When you pass a reference variable:
1. Java copies the memory address pointer (e.g., `0x909`) into the method parameter.
2. Both the caller variable and the parameter variable now point to the **same object on the Heap**.
3. If you modify the fields of that object (e.g., `user.name = "Bob"`), the change is **visible** to the caller because the shared Heap object was modified.

#### Scenario C: Passing References (Reassignment)
If you reassign the parameter reference inside the method (e.g., `user = new User()`):
1. The parameter variable's pointer on the Stack is updated to point to a new Heap address (e.g., `0x505`).
2. The caller's original variable continues to point to the original Heap address (`0x909`).
3. The connection is broken; the caller **does not see** the reassignment.

---

## Code Example: Coordinate Memory Analysis
The following program defines a custom `Coordinate` class and demonstrates primitive value passing, reference attribute updates, and reference reassignments:

```java
class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class MemoryDemo {

    /**
     * Attempts to swap primitive integers.
     * Since primitives are passed by value, this has zero effect on the caller's variables.
     */
    public static void attemptPrimitiveSwap(int val1, int val2) {
        System.out.println("   [Inside method] Pre-swap: val1=" + val1 + ", val2=" + val2);
        int temp = val1;
        val1 = val2;
        val2 = temp;
        System.out.println("   [Inside method] Post-swap: val1=" + val1 + ", val2=" + val2);
    }

    /**
     * Modifies the attributes of the passed coordinate object.
     * Since the reference pointer is copied, the caller's Heap object is updated.
     */
    public static void shiftCoordinate(Coordinate point, int dx, int dy) {
        point.x += dx; // Modifying shared heap object state
        point.y += dy;
        System.out.println("   [Inside method] Shifted point: " + point);
    }

    /**
     * Attempts to reassign the coordinate object reference.
     * This breaks the local parameter's link to the caller's object.
     */
    public static void attemptReassign(Coordinate point) {
        System.out.println("   [Inside method] Pre-reassign pointer contents: " + point);
        // Pointing the local Stack parameter to a new Heap address
        point = new Coordinate(999, 999); 
        System.out.println("   [Inside method] Post-reassign pointer contents: " + point);
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Primitive Parameter Passing ===");
        int x = 10;
        int y = 20;
        System.out.println("Before swap call: x=" + x + ", y=" + y);
        attemptPrimitiveSwap(x, y);
        System.out.println("After swap call:  x=" + x + ", y=" + y); // Unchanged: 10, 20

        System.out.println("\n=== 2. Reference Modifying Passing ===");
        // 'home' is allocated on Stack, pointing to Address 0x888 on Heap
        Coordinate home = new Coordinate(5, 5);
        System.out.println("Before shift call: " + home);
        shiftCoordinate(home, 3, 4); // Shares 0x888 address
        System.out.println("After shift call:  " + home); // Changed: (8, 9)

        System.out.println("\n=== 3. Reference Reassignment Passing ===");
        System.out.println("Before reassign call: " + home);
        attemptReassign(home);
        System.out.println("After reassign call:  " + home); // Unchanged: (8, 9) (still points to 0x888)
    }
}
```

---

## Summary
- **Stack memory** stores active execution frames, local primitives, and reference address pointers. Access is fast and cleanup is immediate.
- **Heap memory** stores all objects and arrays. It is managed by JVM Garbage Collection.
- **Primitives** hold data values directly on the Stack; **References** store memory addresses pointing to objects on the Heap.
- **Pass-by-value** copies primitive values (original is safe) and reference pointers (original heap data is shared and mutable).
- Reassigning a reference parameter inside a method only modifies the local Stack frame pointer, leaving the caller's original reference unchanged.

---

## Additional Resources
- [Java Stack and Heap Memory - Baeldung](https://www.baeldung.com/java-stack-heap)
- [Passing Information to a Method - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/javaOO/arguments.html)
- [Java Memory Management - GeeksforGeeks](https://www.geeksforgeeks.org/memory-management-in-java/)