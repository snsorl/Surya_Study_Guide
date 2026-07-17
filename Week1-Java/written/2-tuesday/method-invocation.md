# Method Invocation

## Learning Objectives
- Invoke static methods correctly within the same class and from external classes.
- Explain the key conceptual and architectural differences between static and instance methods.
- Instantiate objects and invoke instance methods using dot notation.
- Design and execute chained method calls (Method Chaining) for clean API design.

---

## Why This Matters
Declaring a method is like designing a machine; it is useless until you turn it on. In Java, turning a method on is called **method invocation** (or calling a method). 

Depending on whether a method has the **`static`** keyword in its signature, you invoke it in two fundamentally different ways. 
- Static methods are like public utilities (e.g., standard math operations). You can run them instantly.
- Instance methods are tied to specific data records (e.g., retrieving a specific student's grade or a specific customer's profile). You must build the object container first before calling the method. 

Knowing how and when to use each invocation style is essential for using Java libraries and starting to build your own object architectures.

---

## The Concept

### 1. Invoking Static Methods
Static methods belong to the class itself. They do not require you to construct an object instance to run them, making them highly memory-efficient for utility calculations.

#### Invocation Syntax:
*   **From Within the Same Class**: Simply type the method name and pass arguments:
    ```java
    int sum = addNumbers(5, 10);
    ```
*   **From an External Class**: You must prefix the method name with the Class name:
    ```java
    double root = Math.sqrt(144.0); // Invokes the static 'sqrt' method on class 'Math'
    ```

---

### 2. Invoking Instance Methods
Instance methods represent behaviors that act on the internal data (state) of a specific object. They cannot run unless that object exists in memory.

#### Invocation Workflow:
1. **Instantiate**: Create the object instance on the Heap using the **`new`** keyword:
   ```java
   Scanner input = new Scanner(System.in);
   ```
2. **Invoke**: Call the method using the object reference variable, a **dot (`.`)**, and the method name:
   ```java
   String text = input.nextLine(); // Invoking 'nextLine' on 'input' object
   ```

---

### Static vs. Instance Execution Contexts
*   **Static Context**: Exists globally in the Method Area of the JVM. It has no access to `this` or instance-level variables, because it runs independently of any objects.
*   **Instance Context**: Exists on the Heap. It has access to the specific object's fields and can call other static and instance methods freely.

---

### 3. Method Chaining
Method chaining is a design pattern that allows you to execute multiple operations sequentially in a single line of code, moving from left to right. This works because each method in the chain returns an object reference that the next method uses as its caller.

```java
// How the compiler processes chaining:
String result = " Java ".trim().concat("17").toLowerCase();
// 1. " Java ".trim() executes and returns "Java"
// 2. "Java".concat("17") executes and returns "Java17"
// 3. "Java17".toLowerCase() executes and returns "java17"
```

Chaining makes code descriptive and readable, resembling natural statements rather than multiple lines of reassignments.

---

## Code Example
The following class declares static utility calculations and instance methods, and demonstrates how to invoke both styles, including scanner parsing and string method chaining:

```java
class StringTools {
    // Static method: acts on the input parameters directly
    public static String reverse(String text) {
        if (text == null) return null;
        return new StringBuilder(text).reverse().toString();
    }

    // Instance method: simulates an active formatting tool
    public void printWelcome(String name) {
        System.out.println("[TOOLS] Welcome to the workspace, " + name);
    }
}

public class InvocationDemo {
    public static void main(String[] args) {
        // --- 1. Invoking Static Methods ---
        // A. Same Class Static Call
        int calculation = sum(25, 35);
        System.out.println("Same-class static sum: " + calculation);

        // B. External Class Static Call
        String reversed = StringTools.reverse("IntelliJ");
        System.out.println("External static reverse: " + reversed); // Prints "JilletnI"

        // --- 2. Invoking Instance Methods ---
        // A. Instantiate the tool object
        StringTools tools = new StringTools();
        // B. Invoke the instance method on the object reference
        tools.printWelcome("Alice");

        // --- 3. Method Chaining Demo ---
        String text = "  learn object-oriented java  ";
        // Chain: Strip spaces -> Replace java with programming -> Capitalize everything
        String formattedText = text.strip().replace("java", "programming").toUpperCase();
        System.out.println("Chained result: '" + formattedText + "'");
    }

    // Static helper method declared in the same class
    public static int sum(int a, int b) {
        return a + b;
    }
}
```

---

## Summary
- **Static methods** belong to the class itself. Invoke them directly using the class name (e.g. `Math.max()`).
- **Instance methods** require an object instance to be created on the Heap first. Invoke them using **dot notation** on the object reference (e.g. `scanner.nextInt()`).
- **Method Chaining** combines multiple method calls into a single sequence by returning object references from each intermediate method.

---

## Additional Resources
- [Static vs Instance Methods in Java - GeeksforGeeks](https://www.geeksforgeeks.org/static-methods-vs-instance-methods-in-java/)
- [Java dot notation explained - W3Schools](https://www.w3schools.com/)
- [Understanding Method Chaining - Baeldung](https://www.baeldung.com/)