# Static Members

## Learning Objectives
- Declare and apply static variables and static methods inside Java classes.
- Contrast the physical memory and access boundaries of the Static (Class) context vs. the Instance (Object) context.
- Explain the execution timing and purpose of **Static Initialization Blocks**.
- Troubleshoot the compiler error: *"Non-static field cannot be referenced from static context."*

---

## Why This Matters
By default, variables and methods inside a class are tied to individual objects. For example, if you have a `User` class with a `username` variable, every single object instance you create will hold its own distinct username string. 

However, sometimes you need data or utility operations that do not belong to individual instances, but are shared globally by the entire class. For example, if you want to keep track of the total number of users registered in your application, storing a `totalUsers` counter inside every user object makes no sense—each object would only know about itself. Instead, you need a shared counter that exists in a single location in memory. In Java, this class-level sharing is achieved using the **`static`** keyword.

---

## The Concept

### Static vs. Instance Contexts

```
                       CLASS: Trainee
                 [ Static: maxLimit = 100 ]
                 [ Static: traineeCount = 3 ]
                           /   │   \
            ┌─────────────r    │    ──────────────┐
            ▼                  ▼                  ▼
      Instance 1           Instance 2           Instance 3
   [ name="Alice" ]     [ name="Bob" ]     [ name="Charlie" ]
```

*   **Instance Members**: Belong to individual object instances. They are allocated on the **Heap** when an object is instantiated. Every object has its own separate copy of these variables.
*   **Static Members**: Belong to the class itself. Only **one copy** is loaded into JVM memory (specifically in the Method Area) when the class is first run. All object instances of the class share this single copy.

---

### Static Variables
A static variable (also called a class variable) is declared with the `static` keyword:
```java
public static int totalObjects = 0;
```
If Instance A updates `totalObjects` to `5`, Instance B will read `5` instantly, because they point to the exact same memory location.

---

### Static Methods
Static methods belong to the class and are invoked using the class name (e.g. `Math.abs(-50)`).
*   **The Crucial Restriction**: Because a static method runs without any active object instance in scope, it **cannot access non-static (instance) variables or invoke non-static methods directly**.
*   **No `this` Keyword**: The keyword `this` represents the active object instance. Because there is no object instance in static context, using `this` inside a static method triggers a compile-time error.

```java
public class Counter {
    int count = 0; // Instance variable

    public static void increment() {
        // count++; // COMPILER ERROR: Non-static field 'count' cannot be referenced from a static context
    }
}
```

---

### Static Initializer Blocks
A static initializer block is a segment of code that runs **only once** when the class is first loaded into memory by the JVM, before any constructors run or static methods are called.
*   **Use Case**: Initializing complex static variables (e.g., establishing a database URL, loading a configuration profile from a file).

```java
public class Config {
    static String dbUrl;

    // Static block runs first
    static {
        dbUrl = "jdbc:postgresql://localhost:5432/main_db";
        System.out.println("Static Block Executed: Config loaded.");
    }
}
```

---

## Code Example: Trainee Roster Management
The following program defines a `Trainee` class that uses static variables to count total instances, static blocks to configure limits, and demonstrates compile-time restrictions:

```java
class Trainee {
    // 1. Instance Field: Each trainee has a unique name
    String name;

    // 2. Static Fields: Shared globally across the entire class
    static int traineeCount;
    static int maxLimit;

    // 3. Static Initialization Block - runs exactly once when class is loaded
    static {
        traineeCount = 0;
        maxLimit = 3; // Enforcing class capacity limit
        System.out.println("[STATIC BLOCK] Class Trainee loaded. Capacity initialized to " + maxLimit);
    }

    // Constructor
    public Trainee(String name) {
        this.name = name;
        traineeCount++; // Incrementing the shared global counter
        System.out.println("[CONSTRUCTOR] Created Trainee instance: " + name);
    }

    // 4. Static Method: acts on class-level shared data
    public static void printRosterStatus() {
        System.out.println("[STATIC METHOD] Total Trainees Registered: " + traineeCount + " / " + maxLimit);
        
        // System.out.println("Trainee Name: " + name); 
        // COMPILER ERROR: Non-static field 'name' cannot be referenced from static context
    }
}

public class StaticDemo {
    public static void main(String[] args) {
        System.out.println("--- Starting Main Method ---");
        // Class-level static method can be called BEFORE any objects are created
        Trainee.printRosterStatus(); 

        System.out.println("\n--- Creating Trainee Instances ---");
        Trainee t1 = new Trainee("Alice");
        Trainee t2 = new Trainee("Bob");
        Trainee t3 = new Trainee("Charlie");

        System.out.println("\n--- Checking Shared Status ---");
        // Calling static method via Class reference
        Trainee.printRosterStatus(); 

        // Modifying class-level limit updates it for everyone
        Trainee.maxLimit = 10;
        System.out.println("New Limit accessed via instance t1: " + t1.maxLimit); // Warning, but works
        System.out.println("New Limit accessed via Class:       " + Trainee.maxLimit); // Recommended way
    }
}
```

---

## Summary
- **Static members** belong to the class itself and exist in a single memory location. **Instance members** belong to individual object instances on the Heap.
- **Static methods** cannot reference instance variables or call instance methods directly, as they run without a `this` reference.
- **Static initialization blocks** run once when the JVM first loads the class, making them ideal for setting up global configurations.
- Invoke static members using the class name prefix: `ClassName.methodName()`.

---

## Additional Resources
- [Understanding Class Members - Oracle Java SE Tutorials](https://docs.oracle.com/javase/tutorial/java/javaOO/classvars.html)
- [The static keyword in Java - GeeksforGeeks](https://www.geeksforgeeks.org/static-keyword-java/)
- [Static Blocks vs Constructors in Java - Baeldung](https://www.baeldung.com/)