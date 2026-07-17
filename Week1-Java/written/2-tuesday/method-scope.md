# Variable Scope in Java

## Learning Objectives
- Define variable scope and explain how it controls variable accessibility and lifespans.
- Identify the three primary scopes in Java: Local, Instance, and Class (Static).
- Trace the lifecycle (creation and destruction) of variables in Stack and Heap memory.
- Identify the logic bug of **Variable Shadowing** and explain how to resolve it using the **`this`** keyword.
- Apply block-scoping rules inside loops and conditional statements.

---

## Why This Matters
In programming, variables are not globally accessible. A variable created inside an `if` statement block does not exist outside that block. A variable passed to one method cannot be read by another method. This spatial and temporal boundaries are called **Variable Scope**.

If you do not understand scope, you will constantly face compile-time warnings like `"Cannot find symbol"` or, worse, logic bugs caused by **variable shadowing**. Shadowing occurs when you name a method parameter the same name as a class variable, causing the local parameter to hide the class variable. The compiler will not throw an error, but your program will silently fail to update your objects. Master variable scope to ensure data remains secure and flows predictably.

---

## The Concept

### The Three Scopes of Java

```
+-------------------------------------------------------------+
| Class Scope (Static Variables)                              |
| - Lifespan: Entire duration of application run              |
|                                                             |
|   +-------------------------------------------------------+ |
|   | Instance Scope (Fields)                               | |
|   | - Lifespan: Exists as long as object lives on Heap    | |
|   |                                                       | |
|   |   +-------------------------------------------------+ | |
|   |   | Local Scope (Method & Block variables)          | | |
|   |   | - Lifespan: Exists only during block execution  | | |
|   |   +-------------------------------------------------+ | |
|   +-------------------------------------------------------+ |
+-------------------------------------------------------------+
```

Java enforces three levels of variable scope:

#### 1. Local Scope (Stack-based)
*   **Where**: Variables declared inside a method, constructor, parameter list, or block of code (like `for` loops or `if` statements).
*   **Birth**: When the JVM enters the block of code and executes the declaration.
*   **Death**: Immediately when the JVM exits the block of code or method. The Stack frame is popped, and the memory is reclaimed.
*   **Access**: Only accessible within the specific block where declared.

#### 2. Instance Scope (Heap-based Fields)
*   **Where**: Variables declared inside a class but outside any method, **without** the `static` keyword.
*   **Birth**: When an object is instantiated using the `new` keyword.
*   **Death**: When the object has zero references pointing to it and is garbage collected from Heap memory.
*   **Access**: Accessible by all non-static methods inside the class.

#### 3. Class Scope (Static Fields)
*   **Where**: Variables declared inside a class using the **`static`** keyword.
*   **Birth**: When the JVM first loads the class into memory (startup).
*   **Death**: When the application shuts down.
*   **Access**: Accessible by all methods in the class, both static and non-static, and can be accessed externally using `ClassName.variableName`.

---

### Variable Shadowing Pitfall
Shadowing is a logic bug that occurs when a variable declared in an inner scope (like a local method parameter) has the **exact same name** as a variable in an outer scope (like an instance field). 

Inside that method, the local variable takes precedence, "shadowing" (hiding) the instance field. Any modifications to the variable name will update the local copy on the Stack, leaving the Heap object's field completely unchanged!

```java
public class Account {
    public int balance = 100; // Instance Field

    public void deposit(int balance) { // parameter 'balance' shadows field 'balance'
        balance = balance; // LOGIC BUG: This assigns the local variable to itself!
                           // The instance field remains 100!
    }
}
```

#### Resolution: The `this` Keyword
To tell the compiler you want to access the outer instance variable (field) instead of the local variable, prefix the variable name with **`this.`**. The keyword `this` acts as a reference pointer pointing to the current active object instance:

```java
public void deposit(int balance) {
    this.balance = balance; // 'this.balance' refers to the instance field; 'balance' refers to local parameter
}
```

---

## Code Example
Let's review local blocks, class variables, instance variables, shadowing, and resolution in a complete Java class:

```java
public class ScopeDemo {
    // 1. Class Scope (Static variable) - exists globally for duration of app
    public static String environment = "PRODUCTION";

    // 2. Instance Scope (Field) - exists as long as object instance lives on Heap
    public int score = 50;

    public void processScore(int score) {
        // 3. Parameter 'score' shadows instance field 'score'
        System.out.println("[DEBUG] Local parameter score value: " + score);
        System.out.println("[DEBUG] Shadowed Instance field value (pre-update): " + this.score);

        // This assigns the parameter value to the instance variable, resolving shadowing
        this.score = score;
        System.out.println("[DEBUG] Instance field value (post-update): " + this.score);

        // 4. Block Scope Variable (inside conditional block)
        if (score > 90) {
            int bonus = 10; // Born here
            System.out.println("Congratulations! Bonus points added: " + bonus);
            // 'bonus' dies here when exiting the if-block
        }

        // System.out.println("Bonus: " + bonus); 
        // COMPILER ERROR: Cannot find symbol 'bonus' (it no longer exists in scope!)
    }

    public static void main(String[] args) {
        // Accessing static class-level scope directly
        System.out.println("Running in environment: " + ScopeDemo.environment);

        // Instantiating object to establish instance scope
        ScopeDemo demo = new ScopeDemo();
        demo.processScore(95);
    }
}
```

---

## Summary
- **Local variables** exist only during method or block execution. They are stored on the Stack and deleted when the block exits.
- **Instance variables (fields)** represent object state, live on the Heap, and exist as long as the object lives.
- **Class variables (static fields)** are loaded once and exist for the entire application lifespan.
- **Variable Shadowing** happens when a local variable hides an instance variable of the same name.
- Resolve shadowing by using **`this.variableName`** to target the object instance field.

---

## Additional Resources
- [Variables Scope - Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/variables.html)
- [Java Scope Rules - GeeksforGeeks](https://www.geeksforgeeks.org/variable-scope-in-java/)
- [Understanding variable shadowing and this - Baeldung](https://www.baeldung.com/java-variable-shadowing-hiding)