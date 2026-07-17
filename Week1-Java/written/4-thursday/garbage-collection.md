# JVM Garbage Collection

## Learning Objectives
- Describe how the JVM manages Heap memory automatically using the Garbage Collector (GC).
- Identify when an object becomes eligible for garbage collection (zero reference rule).
- Explain the deprecation of the `finalize()` method and specify modern alternative patterns.
- Diagnose and prevent common sources of memory leaks in Java.

---

## Why This Matters
In low-level programming languages like C or C++, developers must manually manage memory. Every time they allocate a block of memory to store an object, they must write code to free that memory when they are finished. If they forget, the unused objects remain in memory, slowly consuming the system's resources until the server runs out of space and crashes. This is called a **memory leak**. 

Java prevents this by using automatic **Garbage Collection**. The JVM runs a background thread that monitors Heap memory and automatically deletes objects that are no longer accessible by your application. However, garbage collection is not magic. If you write bad code that keeps references to dead objects active (e.g., storing them inside static lists), the Garbage Collector cannot clean them up. Understanding GC rules is essential to building high-performance, leak-free applications.

---

## The Concept

### What is Garbage Collection?
The Garbage Collector is an automatic memory management utility in the Java Virtual Machine (JVM). Its primary responsibility is to reclaim memory occupied by objects that are no longer being used by the application, freeing up space for new allocations.

*   **Stack vs. Heap Cleanup**: Stack frames are cleaned up automatically as methods exit. Heap objects, however, can live longer and require the GC to scan the memory tree to identify orphaned objects.

---

### Eligibility for Garbage Collection
An object on the Heap becomes eligible for garbage collection the moment it has **zero active references pointing to it**.

There are three common ways an object loses all references:

#### Scenario A: Reassignment to `null`
When you set a reference variable to `null`, you cut its connection to the Heap object:
```java
DataBlock block = new DataBlock(1); // Object created on Heap
block = null; // Memory connection cut. Object 1 is now eligible for GC.
```

#### Scenario B: Reassignment to a different Object
Pointing the reference variable to a new object orphans the original object:
```java
DataBlock block = new DataBlock(1);
block = new DataBlock(2); // block now points to Object 2. Object 1 is orphaned and eligible for GC.
```

#### Scenario C: Scope Termination (Method Exits)
When a local variable is declared inside a method, it is popped off the Stack when the method finishes. If that variable was the only reference to a Heap object, that object is now orphaned.

---

### The Deprecation of `finalize()`
In older versions of Java, the `Object` class provided a method named `finalize()`. Subclasses could override this method to perform cleanup operations (like closing files or database connections) right before the GC deleted the object.
*   **The Problem**: The JVM does not guarantee *when* or even *if* the Garbage Collector will run. If you rely on `finalize()` to close database connections, your application can run out of connections and crash before the GC ever runs. Additionally, `finalize()` introduced security holes and degraded GC performance.
*   **The Modern Solution**: The `finalize()` method was **deprecated in Java 9**. Today, we enforce resource cleanup using the **`AutoCloseable`** interface and the **Try-with-Resources** pattern.

---

### What Causes Memory Leaks in Java?
A memory leak occurs when objects that are no longer needed by the program remain referenced. Because a reference exists, the GC is forced to keep them in memory.
*   **Static Collections**: Variables marked `static` live for the entire duration of the application. If you add objects to a static `List` or `Map` and forget to clear them, they will never be garbage collected.
*   **Unclosed Resources**: Open file streams or database connection sockets that are never closed can leak memory handles.

---

## Code Example: Memory Lifecycles
The following program defines a simple `DataBlock` class and demonstrates reference terminations, scope exits, and the limits of requesting manual collection:

```java
class DataBlock {
    int id;

    public DataBlock(int id) {
        this.id = id;
        System.out.println("[MEM-ALLOC] Allocated DataBlock: #" + id);
    }

    // Note: finalize is deprecated and shown here for educational critique only
    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() throws Throwable {
        System.out.println("[GC-CALLBACK] DataBlock #" + id + " has been deleted from memory.");
    }
}

public class GarbageCollectionDemo {

    public static void runLocalScope() {
        System.out.println("--- Inside runLocalScope method ---");
        // 'localBlock' is allocated on the Stack
        DataBlock localBlock = new DataBlock(101);
        // Method exits; 'localBlock' is popped off the Stack. 
        // Heap object 101 becomes eligible for GC.
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Reassignment to Null ===");
        DataBlock b1 = new DataBlock(1);
        b1 = null; // b1 points to null; Object 1 is eligible for GC

        System.out.println("\n=== 2. Reassignment to Another Object ===");
        DataBlock b2 = new DataBlock(2);
        b2 = new DataBlock(3); // Object 2 is orphaned, block points to Object 3

        System.out.println("\n=== 3. Method Scope Terminations ===");
        runLocalScope(); // Object 101 is created, and then orphaned on method exit

        System.out.println("\n=== 4. Requesting JVM Garbage Collection ===");
        // System.gc() is a SUGGESTION to the JVM to run. 
        // The JVM decides if and when it actually executes collection.
        System.gc(); 

        // Pause thread briefly to allow JVM time to process system gc callbacks if it chooses to
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=== End of Program ===");
    }
}
```

---

## Summary
- **Garbage Collection (GC)** is an automatic background JVM process that cleans up unused Heap memory.
- An object becomes eligible for GC when its reference count drops to **zero**.
- References are severed when variables are set to **`null`**, **reassigned** to other objects, or when local method **scopes terminate**.
- The **`finalize()`** method is deprecated due to security vulnerabilities and execution delays. Use **Try-with-Resources** instead.
- Prevent memory leaks by cleaning up **static collections** and closing system files/databases.

---

## Additional Resources
- [Java Garbage Collection Basics - Oracle Technical Articles](https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html)
- [How JVM Garbage Collection Works - Baeldung](https://www.baeldung.com/jvm-garbage-collectors)
- [Java Memory Leaks and How to Avoid Them - GeeksforGeeks](https://www.geeksforgeeks.org/memory-leak-in-java-and-how-to-avoid-it/)