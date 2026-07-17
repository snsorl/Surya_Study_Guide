# Reference Casting

## Learning Objectives
- Distinguish between Upcasting (Widening) and Downcasting (Narrowing) reference types.
- Explain why Upcasting is always safe and handled implicitly by the compiler.
- Describe the cause of a `ClassCastException` during downcasting.
- Apply the `instanceof` operator to execute safe downcasting.

---

## Why This Matters
As you build class inheritance hierarchies, you will frequently write methods that process generic parent classes. For example, you might write a method `printDeviceSpecs(Device d)` that accepts any device—laptops, smartwatches, or phones. 

When you pass a `Laptop` object into this method, Java automatically performs **Upcasting**, treating it as a generic `Device`. This is highly beneficial because you don't have to write separate methods for every device type. However, if that method needs to trigger an action specific to laptops—like `compileProgram()`—you cannot do it using the generic `Device` reference. You must cast the reference back down to a `Laptop`. This process, called **Downcasting**, is risky. If you try to cast a `Phone` object into a `Laptop` reference, your program will crash. Mastering casting and safety checks is crucial to building robust, polymorphic applications.

---

## The Concept

### Upcasting vs. Downcasting

```
             [ Superclass: Device ]
                   ▲         │
      Upcasting    │         │  Downcasting
     (Implicit,    │         │  (Explicit,
      Always Safe) │         ▼  Needs safety check)
              [ Subclass: Laptop ]
```

#### 1. Upcasting (Implicit Widening)
Upcasting is casting a subclass reference to a superclass reference type. 
*   **The Rule**: It happens **automatically** (implicitly) and is always safe. A `Laptop` *is-a* `Device`, so referencing a laptop as a device requires no special instructions.
*   **Limitation**: Once upcasted, you can only access the fields and methods defined in the *superclass*. The subclass-specific methods are hidden from the compiler.

```java
Laptop laptop = new Laptop();
Device device = laptop; // Upcasting (implicit)
// device.runLaptopDiagnostics(); // COMPILER ERROR: Device does not have this method!
```

#### 2. Downcasting (Explicit Narrowing)
Downcasting is casting a superclass reference back to a subclass reference type.
*   **The Rule**: You must perform downcasting **manually** (explicitly) using parentheses: `(Subclass)`.
*   **Risk**: If the underlying Heap object is not actually an instance of that subclass, the JVM will throw a **`ClassCastException`** and crash the application.

```java
Device device = new Phone();
Laptop laptop = (Laptop) device; // CRASH! A Phone is not a Laptop. Throws ClassCastException.
```

---

### Safe Casting using `instanceof`
To prevent `ClassCastException` crashes, always check the identity of the heap object using the **`instanceof`** operator before downcasting. The `instanceof` operator returns `true` if the object is an instance of the specified class.

```java
if (device instanceof Laptop) {
    Laptop laptop = (Laptop) device; // Safe cast
    laptop.compileCode();
} else {
    System.out.println("Warning: Device is not a Laptop.");
}
```

---

## Code Example: Device Hierarchy Casting
The following program defines a `Device` superclass, `Laptop` and `Phone` subclasses, and demonstrates upcasting, downcasting failures, and safe downcasting checks:

```java
// Parent Superclass
class Device {
    String model;

    public void powerOn() {
        System.out.println(model + " is powering on...");
    }
}

// Subclass A
class Laptop extends Device {
    public Laptop(String model) {
        this.model = model;
    }

    public void compileCode() {
        System.out.println("[LAPTOP] Compiling Java code on " + model + "...");
    }
}

// Subclass B
class Phone extends Device {
    public Phone(String model) {
        this.model = model;
    }

    public void makeCall(String number) {
        System.out.println("[PHONE] Dialing " + number + " from " + model + "...");
    }
}

// Execution Class
public class CastingDemo {
    
    // Poly-functional method receiving generic Device references
    public static void runDeviceDiagnostic(Device dev) {
        // 1. Upcasting allows this method to call powerOn on any Device subclass
        dev.powerOn(); 

        // dev.compileCode(); 
        // COMPILER ERROR: Device class does not declare compileCode() method.

        // 2. Safe Downcasting checks using instanceof
        if (dev instanceof Laptop) {
            // Explicit cast
            Laptop myLaptop = (Laptop) dev; 
            myLaptop.compileCode();
        } else if (dev instanceof Phone) {
            Phone myPhone = (Phone) dev;
            myPhone.makeCall("555-0199");
        } else {
            System.out.println("Unknown device category.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Safe Processing ===");
        Laptop macbook = new Laptop("MacBook Pro");
        Phone iphone = new Phone("iPhone 15");

        runDeviceDiagnostic(macbook); // Laptop gets upcasted to Device
        runDeviceDiagnostic(iphone);  // Phone gets upcasted to Device

        System.out.println("\n=== 2. Unsafe Casting Crash Demo ===");
        // Assigning a Phone object to a generic Device reference (Upcast)
        Device genericDevice = new Phone("Pixel 8");

        try {
            System.out.println("Attempting to downcast Phone reference to Laptop...");
            Laptop crashedLaptopRef = (Laptop) genericDevice; // Unsafe downcast!
            crashedLaptopRef.compileCode();
        } catch (ClassCastException e) {
            System.out.println("[CRASH PREVENTED] Caught ClassCastException: " + e.getMessage());
            System.out.println("Reason: A Pixel 8 Phone cannot be narrowed down to a Laptop.");
        }
    }
}
```

---

## Summary
- **Upcasting** is casting a child to a parent reference. It is implicit, safe, and hides subclass-specific fields.
- **Downcasting** is casting a parent back to a child reference. It is explicit, risky, and can throw a **`ClassCastException`**.
- The **`instanceof`** operator checks the actual Heap identity of an object.
- **Always wrap downcasts** in an `instanceof` conditional block to prevent runtime crashes.

---

## Additional Resources
- [Java Inheritance and Casting - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
- [Object Upcasting and Downcasting in Java - Baeldung](https://www.baeldung.com/java-upcasting-downcasting)
- [Understanding instanceof Operator - GeeksforGeeks](https://www.geeksforgeeks.org/instanceof-operator-java/)