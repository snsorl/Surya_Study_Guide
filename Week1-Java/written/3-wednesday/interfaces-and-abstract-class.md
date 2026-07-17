# Interfaces and Abstract Classes

## Learning Objectives
- Compare and contrast abstract classes and interfaces.
- Declare abstract classes containing both abstract and concrete methods.
- Implement interfaces containing abstract and default methods.
- Apply multiple interface implementation to bypass class single inheritance limits.
- Design a polymorphic application using abstract parent types and interface contracts.

---

## Why This Matters
Sometimes you want to create a base template for a group of related classes, but you do not want anyone to instantiate that base template directly. For example, in a logistics application, you might define a class named `Vehicle`. However, creating a raw "Vehicle" object makes no sense—what engine does it have? How many wheels? Only concrete vehicles like `Truck` or `Motorcycle` should actually exist in memory. 

To enforce this architectural constraint, Java provides two tools: **Abstract Classes** and **Interfaces**. 
*   An abstract class is used to model a shared identity (e.g., a `Truck` *is-a* `Vehicle`).
*   An interface is used to model a shared capability (e.g., a `Truck` *can-be-driven*, so it implements `Drivable`).

Mastering when to use abstract classes and interfaces allows you to write highly extensible frameworks where new concrete classes can be added later without rewriting existing codebase utilities.

---

## The Concept

### 1. Abstract Classes (Identity Templates)
An abstract class is declared with the `abstract` keyword. It represents a partial implementation of a class.
*   **No Instantiation**: You cannot instantiate an abstract class using `new` (e.g. `new Vehicle()` throws a compiler error).
*   **Abstract Methods**: Methods declared with the `abstract` keyword have no body (no curly braces, ended with a semicolon). Subclasses *must* override and implement all inherited abstract methods.
*   **State and Constructors**: Abstract classes can have instance variables (with any access modifiers like `private` or `protected`) and constructors. These constructors are executed during subclass instantiation via `super()` chaining.

---

### 2. Interfaces (Capability Contracts)
An interface, declared with the `interface` keyword, is a formal contract defining a set of behaviors a class must support.
*   **Multiple Implementations**: A class can implement **multiple interfaces** (e.g. `class Truck implements Drivable, GPSLocatable`). This allows you to bypass the single-inheritance restriction of classes.
*   **Implicit Modifiers**: Interface methods are implicitly `public abstract`, and variables are implicitly `public static final` (constants), even if you omit the keywords.
*   **Default Methods (Java 8+)**: Interfaces can declare methods with bodies using the **`default`** keyword. Implementing classes automatically inherit these default behaviors without being forced to override them. This allows you to add features to interfaces without breaking existing implementing classes.

---

### Abstract Class vs. Interface Matrix

| Feature | Abstract Class | Interface |
| :--- | :--- | :--- |
| **Pillar Focus** | Represents **Identity** ("is-a"). | Represents **Capability** ("can-do"). |
| **Inheritance** | A class can `extend` only one abstract class. | A class can `implement` multiple interfaces. |
| **Fields** | Can have instance variables of any access level. | Variables must be `public static final` constants. |
| **Constructors** | Yes, has constructors. | No, cannot have constructors. |
| **Methods** | Can have abstract, concrete, private, or protected methods. | Public abstract, or public `default` methods (Java 8+). |

---

## Code Example: Vehicle Logistics System
The following program defines a `Drivable` interface (capability), a `Vehicle` abstract class (identity), and a concrete `Truck` class that integrates both structures:

```java
// 1. Interface defining a Capability Contract
interface Drivable {
    // Implicitly: public static final int MIN_SPEED = 0;
    int MIN_SPEED = 0; 

    // Abstract method (no body)
    void accelerate(); 

    // Default method (contains body, inherited automatically)
    default void soundHorn() {
        System.out.println("[INTERFACE HORN] Beep! Beep!");
    }
}

// 2. Abstract Class defining a Shared Identity Template
abstract class Vehicle {
    protected String model;

    // Abstract classes can have constructors
    public Vehicle(String model) {
        this.model = model;
    }

    // Concrete method: inherited directly by children
    public void fuelStatus() {
        System.out.println("[VEHICLE] Fuel levels are normal.");
    }

    // Abstract method: must be implemented by concrete subclasses
    public abstract void displayDetails();
}

// 3. Concrete Subclass extending parent and implementing contract
class Truck extends Vehicle implements Drivable {
    private double payloadCapacity;

    public Truck(String model, double payloadCapacity) {
        super(model); // Constructor chaining to abstract class
        this.payloadCapacity = payloadCapacity;
    }

    // Implementing abstract method from Vehicle
    @Override
    public void displayDetails() {
        System.out.println("Truck Model: " + model + " | Payload Capacity: " + payloadCapacity + " tons");
    }

    // Implementing abstract method from Drivable interface
    @Override
    public void accelerate() {
        System.out.println("[TRUCK] Heavy engine accelerating slowly. Speed increasing...");
    }
}

// Execution Class
public class AbstractionDemo {
    public static void main(String[] args) {
        System.out.println("--- Instantiating Concrete Truck ---");
        Truck myTruck = new Truck("Volvo FH16", 25.0);

        // Accessing inherited, implemented, and default methods
        myTruck.displayDetails(); // Abstract class method implemented
        myTruck.fuelStatus();     // Abstract class concrete method inherited
        myTruck.accelerate();     // Interface method implemented
        myTruck.soundHorn();      // Interface default method inherited

        System.out.println("\n--- Polymorphic Interface Assignability ---");
        // We can assign the Truck object to a reference of the Drivable interface
        Drivable driverRef = myTruck;
        driverRef.accelerate(); 
        // driverRef.fuelStatus(); // COMPILER ERROR: Drivable interface does not declare fuelStatus()
    }
}
```

---

## Summary
- **Abstract classes** represent shared identity ("is-a"). They cannot be instantiated, but can have instance variables and constructors.
- **Interfaces** represent shared capability ("can-do"). They support multiple implementation, bypassing single inheritance rules.
- Concrete subclasses must implement all abstract methods from both parent abstract classes and interfaces.
- **`default`** interface methods provide code implementation directly in interfaces, facilitating retrofitting features safely.

---

## Additional Resources
- [Abstract Methods and Classes - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [Interfaces Guide - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)
- [Abstract Class vs Interface in Java - Baeldung](https://www.baeldung.com/java-interface-vs-abstract-class)