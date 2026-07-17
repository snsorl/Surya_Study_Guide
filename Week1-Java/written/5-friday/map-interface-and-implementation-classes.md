# The Map Interface

## Learning Objectives
- Store, retrieve, and update key-value pairs using the `java.util.Map` interface.
- Compare the execution characteristics of `HashMap`, `LinkedHashMap`, and `TreeMap`.
- Navigate Map collections using the `keySet()`, `values()`, and `entrySet()` views.
- Explain the `NullPointerException` constraint on `TreeMap` keys.
- Analyze the time complexity (`O(1)` vs `O(log N)`) of key map operations.

---

## Why This Matters
Lists and Sets are useful for single-value collections, but in business software, you constantly need to look up data based on an identifier. 

For example, in a database system, you search for a student profile using their unique ID (e.g. `"STU-101"`). If you store student objects inside a `List`, searching requires looping through the entire list matching IDs manually. For a large company with 100,000 employees, this is incredibly slow. The **`Map`** interface solves this. It stores data as key-value pairings, allowing you to fetch records instantly by key without searching. Choosing the right Map class—**`HashMap`**, **`LinkedHashMap`**, or **`TreeMap`**—allows you to customize ordering and performance.

---

## The Concept

### Interface Mechanics
*   **Keys**: Must be **unique**. If you insert a key-value pair with a key that already exists, the old value is overwritten silently.
*   **Values**: Do **not** need to be unique; multiple different keys can point to the same value.

```
                          [ MAP STRUCTURE ]
                   Key (Unique)    =====>   Value (Can duplicate)
                   "STU-101"       =====>   "Alice"
                   "STU-202"       =====>   "Bob"
                   "STU-303"       =====>   "Alice"
```

---

### The Three Key Map Implementations

#### 1. `HashMap` (The Default Choice)
Backed by a hash table.
*   **Ordering**: Completely **Unordered** (based on key hash codes).
*   **Performance (`O(1)`)**: Near-instant lookup, insertion, and deletion speeds.
*   **Nulls**: Allows one `null` key and multiple `null` values.

#### 2. `LinkedHashMap` (Insertion-Ordered Map)
Hash table backed with a running doubly-linked list.
*   **Ordering**: **Insertion-Ordered**. It preserves the sequence in which keys were added.
*   **Performance**: Slightly slower than `HashMap` due to pointer tracking.
*   **Nulls**: Allows one `null` key and multiple `null` values.

#### 3. `TreeMap` (Sorted Map)
Backed by a Red-Black Tree.
*   **Ordering**: **Sorted**. Automatically sorts the **keys** in natural ascending order (alphabetic or numeric).
*   **Performance (`O(log N)`)**: Slower operations due to constant tree rebalancing.
*   **Nulls**: **Does not allow null keys.** It throws a `NullPointerException` because keys must be compared. (Multiple null values are allowed).

---

### Navigating Map Elements
You cannot iterate through a Map directly using a standard for-each loop because it does not implement `Iterable`. Instead, you use one of three helper views:
1.  **`keySet()`**: Returns a `Set` of all keys (used to loop through keys).
2.  **`values()`**: Returns a `Collection` of all values.
3.  **`entrySet()`**: Returns a `Set` of **`Map.Entry<K, V>`** objects. Each entry object holds both the key and the value. This is the **most efficient way** to loop through a Map:

```java
for (Map.Entry<String, String> entry : map.entrySet()) {
    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
}
```

---

### Performance Comparison Matrix

| Feature | HashMap | LinkedHashMap | TreeMap |
| :--- | :--- | :--- | :--- |
| **Backing Structure** | Hash Table | Hash Table + Linked List | Red-Black Tree |
| **Time Complexity** | **`O(1)`** (Constant) | `O(1)` | **`O(log N)`** (Logarithmic) |
| **Key Ordering** | Unordered | Insertion Order | Natural Sorted Order |
| **Null Key Support** | Yes (Allows one) | Yes (Allows one) | **No** (Throws `NullPointerException`) |

---

## Code Example: Map Behavior Analysis
The following program populates a `HashMap`, a `LinkedHashMap`, and a `TreeMap` with matching entries, and demonstrates Map navigation and null constraints:

```java
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapComparisonDemo {

    public static void populateAndPrint(Map<String, String> map, String label) {
        // Attempting additions
        map.put("USD", "United States Dollar");
        map.put("EUR", "Euro");
        map.put("GBP", "Great British Pound");
        map.put("EUR", "Eurozone Shared Currency"); // Overwrites old value

        System.out.println(label + " state after additions:");
        System.out.println("   Total currencies: " + map.size()); // 3
        System.out.println("   Elements:         " + map);
        
        // Lookup by Key (O(1) on HashMap)
        System.out.println("   Lookup EUR:       " + map.get("EUR")); 
        System.out.println("---------------------------------");
    }

    public static void main(String[] args) {
        System.out.println("=== 1. HashMap (Unordered, O(1)) ===");
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put(null, "Missing Currency"); // Allows null key
        populateAndPrint(hashMap, "HashMap");

        System.out.println("\n=== 2. LinkedHashMap (Insertion Order, O(1)) ===");
        Map<String, String> linkedMap = new LinkedHashMap<>();
        linkedMap.put(null, "Missing Currency"); // Allows null key
        populateAndPrint(linkedMap, "LinkedHashMap");

        System.out.println("\n=== 3. TreeMap (Sorted by Keys, O(log N)) ===");
        Map<String, String> treeMap = new TreeMap<>();
        populateAndPrint(treeMap, "TreeMap"); // Sorted: [EUR, GBP, USD]

        System.out.println("\n=== 4. Efficient Navigation using entrySet() ===");
        // Loop through key-value pairs simultaneously
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            System.out.println("   Code: " + entry.getKey() + " -> Name: " + entry.getValue());
        }

        System.out.println("\n=== 5. Testing Null Key Constraints on TreeMap ===");
        try {
            System.out.println("Attempting to insert null key into TreeMap...");
            treeMap.put(null, "Crashes System");
        } catch (NullPointerException e) {
            System.out.println("[CRASH PREVENTED] Caught NullPointerException! TreeMap keys cannot be null.");
        }
    }
}
```

---

## Summary
- A **`Map`** stores key-value pairs. Keys must be unique; values can be duplicated.
- **`HashMap`** is the default choice, offering **`O(1)`** speed but no ordering.
- **`LinkedHashMap`** maintains insertion sequence.
- **`TreeMap`** automatically sorts keys in natural ascending order at **`O(log N)`** complexity.
- **`TreeMap` forbids null keys**, while `HashMap` and `LinkedHashMap` allow one.
- Loop through maps efficiently using **`map.entrySet()`** to access keys and values in one step.

---

## Additional Resources
- [The Map Interface - Oracle Docs](https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html)
- [HashMap vs TreeMap vs LinkedHashMap - Baeldung](https://www.baeldung.com/java-hashmap-vs-treemap-vs-linkedhashmap)
- [Understanding Map entrySet() Loop - GeeksforGeeks](https://www.geeksforgeeks.org/map-entry-interface-java-example/)