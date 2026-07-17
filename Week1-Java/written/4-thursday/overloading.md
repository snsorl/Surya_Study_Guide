# Method Overloading

## Learning Objectives
- Define the rules of Method Overloading and identify valid overloaded signatures.
- Construct overloaded methods varying in parameter counts, types, or order.
- Differentiate between valid method overloading and compile-time duplicate method errors.
- Explain why changing only the return type or access modifier fails to overload a method.
- Explain how the compiler resolves overloaded calls at compile-time (Static Binding).

---

## Why This Matters
Sometimes you need to perform the same general logical operation but with different types or amounts of input data. For example, if you are writing a logging library, you might want to log a simple error message string, log an exception object, or log a code number. 

Without method overloading, you would have to give every method a unique name: `logMessage(String msg)`, `logException(Exception e)`, and `logCode(int code)`. This creates cognitive load for other developers, who must memorize three different names. Method overloading allows you to write a single, clean method name—`log()`—and let the Java compiler automatically map your call to the correct version based on the inputs you pass.

---

## The Concept

### The Rules of Overloading
For methods to be considered overloaded, they must reside within the **same class** and share the **exact same name**, but they must have **different parameter signatures**. 

You can vary the parameter signatures in three ways:

#### 1. Varying Parameter Count
Methods take different numbers of arguments:
```java
public void draw(int width) { }
public void draw(int width, int height) { } // Overloaded (different count)
```

#### 2. Varying Parameter Types
Methods take the same number of arguments, but of different data types:
```java
public void print(int value) { }
public void print(String value) { } // Overloaded (different types)
```

#### 3. Varying Parameter Order
Methods take the same types of arguments, but in a different order (only applies to methods with multiple parameters of different types):
```java
public void record(int id, String name) { }
public void record(String name, int id) { } // Overloaded (different order)
```

---

### What Does NOT Overload a Method?
To prevent duplicate method compilation failures, you must remember: **The return type and access modifiers are NOT part of the method signature.** 

If you try to overload a method by changing only the return type, the access modifier, or the `static` specifier, the compiler will throw a duplicate method error:

```java
// COMPILER ERROR PATHWAY:
public void calculate(int x) { }
// public int calculate(int x) { return x; } 
// ERROR: method calculate(int) is already defined in class!
```
*Reason*: When you call `calculate(10)`, the compiler has no way of knowing which method you intended to run, as the method invocation syntax is identical.

---

### Compile-Time Resolution (Static Binding)
Method overloading is resolved at **compile-time**. The compiler inspects the arguments passed at the call site and matches them to the available signatures. This binding is hardcoded directly into the compiled bytecode, meaning there is zero runtime lookup overhead.

---

## Code Example: Notification Dispatch System
The following program defines a `NotificationService` class containing overloaded `send` methods and demonstrates compile-time signature resolution:

```java
class NotificationService {

    // 1. Base Overload (Single String parameter)
    public void send(String message) {
        System.out.println("[ALERT] Sending message: " + message);
    }

    // 2. Overloaded by parameter Count
    public void send(String message, String recipient) {
        System.out.println("[DIRECT] Sending to " + recipient + ": " + message);
    }

    // 3. Overloaded by parameter Type
    public void send(int errorCode) {
        System.out.println("[SYS ERROR] Dispatching system code alert: #" + errorCode);
    }

    // 4. Overloaded by parameter Order (String, int)
    public void send(String message, int priority) {
        System.out.println("[PRIORITY " + priority + "] Message: " + message);
    }

    // 5. Overloaded by parameter Order (int, String)
    public void send(int priority, String message) {
        System.out.println("[IMPORTANT - Priority: " + priority + "] Message: " + message);
    }

    /*
    // ILLEGAL OVERLOAD: Changing return type only
    public int send(String message) {
        return 1;
    }
    // COMPILER ERROR: method send(String) is already defined
    */
}

public class OverloadingDemo {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();

        System.out.println("--- Dispatching Overloaded Calls ---");
        // Compiler maps calls statically at compile-time:
        service.send("System maintenance starting.");             // Matches 1
        service.send("Host connection lost", "Admin");             // Matches 2
        service.send(503);                                         // Matches 3
        service.send("Disk space warning", 2);                     // Matches 4
        service.send(1, "Server shutdown initiated");              // Matches 5
    }
}
```

---

## Summary
- **Method Overloading** allows multiple methods in the same class to share a name but have different parameter signatures.
- Overload signatures must vary by **parameter count, parameter type, or parameter order**.
- Changing only the **return type** or **access modifiers** is illegal and triggers a duplicate method compile error.
- Overloading is resolved at **compile-time (static binding)**, ensuring optimal runtime execution speed.

---

## Additional Resources
- [Method Overloading - W3Schools](https://www.w3schools.com/java/java_methods_overloading.asp)
- [Method Overloading Rules - GeeksforGeeks](https://www.geeksforgeeks.org/method-overloading-in-java/)
- [Overloading vs. Overriding in Java - Baeldung](https://www.baeldung.com/java-method-overload-vs-override)