# The Object Class

## Learning Objectives
- Describe the role of `java.lang.Object` as the implicit root parent of all Java classes.
- Override the **`toString()`** method to provide human-readable representations of object states.
- Override the **`equals(Object)`** method to enforce logical value equality instead of reference identity.
- Explain the contract between `equals()` and **`hashCode()`** and prevent hashing logic bugs.

---

## Why This Matters
In Java, you do not write code in isolation. Every class you declare, whether it is a custom class like `Customer` or a built-in framework class, implicitly extends **`java.lang.Object`**. If you write `class Customer { }`, the compiler compiles it as `class Customer extends Object { }`. 

Because `Object` is the root ancestor of every class, all objects inherit default methods like `toString()`, `equals()`, and `hashCode()`. However, the default implementations are generic and often unhelpful. For example, printing a custom object outputs its class name followed by a random hex code (e.g. `Customer@3a4a82`), and comparing two customer objects with the same ID returns `false` because they reside in different memory addresses. Mastering how to override these methods correctly is key to ensuring your classes work seamlessly with print statements, logs, and database lookups.

---

## The Concept

### Inherited Core Methods of `java.lang.Object`

#### 1. The `toString()` Method
The default implementation of `toString()` returns:
```text
getClass().getName() + '@' + Integer.toHexString(hashCode())
```
This is unreadable. You override this method to return a clear, human-readable summary of the object's instance fields. The compiler automatically triggers `toString()` whenever you print an object or concatenate it with a String.

```java
@Override
public String toString() {
    return "User[id=" + id + ", name=" + name + "]";
}
```

---

#### 2. The `equals(Object obj)` Method
The default implementation checks for **Reference Identity (`==`)**—meaning it only returns `true` if both variables point to the exact same memory address on the Heap.

```java
public boolean equals(Object obj) {
    return (this == obj); // Default implementation
}
```

To compare objects based on their **logical values** (e.g. comparing two `Customer` objects by their unique ID), you must override `equals(Object obj)` following this standard checklist:
1.  **Identity Check**: Check if the comparison object is the exact same reference pointer (`this == obj`). If yes, return `true` immediately.
2.  **Null Check**: Check if the comparison object is `null`. If yes, return `false`.
3.  **Class Matching**: Verify if the objects are of the exact same class (`getClass() != obj.getClass()`). If not, return `false`.
4.  **Downcast and Compare**: Downcast the generic `Object obj` to your class type and compare the core identifying fields (e.g. `id == other.id`).

---

#### 3. The `hashCode()` Method and the Contract
The `hashCode()` method returns an integer representation of the object's memory location or content hash. In Java, **`equals()` and `hashCode()` are bound by a strict mathematical contract:**

> [!IMPORTANT]
> **The equals/hashCode Contract**: If two objects are equal according to the `equals(Object)` method, they **must** return the exact same integer hash code from the `hashCode()` method. 

If you override `equals()` but forget to override `hashCode()`, two objects with the same ID will return different hash codes. When you try to store and retrieve these objects from hash-based collections (like `HashSet` or `HashMap`), the collection will lose your data, leading to severe logical bugs.

---

## Code Example: Client Record Comparison
The following program defines a custom `Client` record class, showcasing the implementation of `toString()`, `equals()`, and `hashCode()`:

```java
import java.util.Objects;

class Client {
    private int clientId;
    private String name;

    public Client(int clientId, String name) {
        this.clientId = clientId;
        this.name = name;
    }

    // 1. Overriding toString to output a clean data summary
    @Override
    public String toString() {
        return "Client[ID=" + clientId + ", Name='" + name + "']";
    }

    // 2. Overriding equals using the standard security checklist
    @Override
    public boolean equals(Object obj) {
        // Step A: Check reference identity
        if (this == obj) {
            return true;
        }
        // Step B: Check null reference
        if (obj == null) {
            return false;
        }
        // Step C: Check class type matching
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        // Step D: Downcast and compare identifying variables
        Client other = (Client) obj;
        return this.clientId == other.clientId && 
               Objects.equals(this.name, other.name); // Safe null-tolerant compare
    }

    // 3. Overriding hashCode to match equals() contract
    @Override
    public int hashCode() {
        // Generates combined hash based on fields checked in equals()
        return Objects.hash(this.clientId, this.name); 
    }
}

public class ObjectClassDemo {
    public static void main(String[] args) {
        Client c1 = new Client(101, "Alice");
        Client c2 = new Client(101, "Alice");
        Client c3 = c1; // Shared pointer reference

        System.out.println("--- 1. Verification of toString() ---");
        // System.out.println automatically triggers the overridden toString() method
        System.out.println("Client 1 representation: " + c1);

        System.out.println("\n--- 2. Comparing with == (Reference check) ---");
        System.out.println("c1 == c2 (Different heap address): " + (c1 == c2)); // false
        System.out.println("c1 == c3 (Same heap address):      " + (c1 == c3)); // true

        System.out.println("\n--- 3. Comparing with .equals() (Value check) ---");
        System.out.println("c1.equals(c2): " + c1.equals(c2)); // true (attributes match)

        System.out.println("\n--- 4. Checking the hashCode() Contract ---");
        System.out.println("Client 1 HashCode: " + c1.hashCode());
        System.out.println("Client 2 HashCode: " + c2.hashCode());
        System.out.println("Are hash codes identical? " + (c1.hashCode() == c2.hashCode())); // true
    }
}
```

---

## Summary
- **`java.lang.Object`** is the implicit root of the entire Java class hierarchy.
- Override **`toString()`** to replace default system hex prints with readable variable summaries.
- Override **`equals()`** to compare object contents logically instead of checking reference memory locations.
- **The equals/hashCode Contract**: Equal objects must return identical hash codes. Always override both methods together.
- Use **`Objects.hash(fields...)`** to generate safe hash code values matching your equality checks.

---

## Additional Resources
- [The Object Class Documentation - Oracle Docs](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html)
- [Overriding equals and hashCode in Java - Baeldung](https://www.baeldung.com/java-equals-hashcode-tostring)
- [Java Object equals() Method - W3Schools](https://www.w3schools.com/java/ref_object_equals.asp)