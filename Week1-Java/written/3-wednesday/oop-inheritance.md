# OOP Inheritance

## Learning Objectives
- Explain the concept of Inheritance and apply the "is-a" relationship validation rule.
- Extend parent classes (superclasses) using the **`extends`** keyword.
- Execute **Constructor Chaining** by calling parent constructors using **`super()`**.
- Access parent class methods and variables from within a child class using the **`super`** keyword.
- Describe the architectural constraints of single inheritance in Java.

---

## Why This Matters
As your software grows, you will design classes that share duplicate variables and behaviors. For example, in an enterprise human resources application, a `Manager` class, a `Trainee` class, and a `Contractor` class all require variables like `name`, `employeeId`, and `emailAddress`, and behaviors like `login()` and `printPaystub()`. 

If you copy and paste these fields into all three files, you violate the DRY (Don't Repeat Yourself) principle. If you decide to rename `employeeId` to `userId`, you must edit every file. **Inheritance** resolves this duplication. It allows you to create a parent class (`Employee`) containing all shared fields, and have child classes (`Manager`, `Trainee`) inherit them automatically. This makes your codebase clean, centralized, and easy to scale.

---

## The Concept

### The "is-a" Relationship
Inheritance represents an **"is-a"** relationship between a superclass (parent) and a subclass (child):
*   A `Manager` **is-an** `Employee`.
*   A `Laptop` **is-a** `Device`.
*   A `SavingsAccount` **is-a** `BankAccount`.

If you cannot frame a relationship as an "is-a" statement, inheritance is the wrong design tool. For example, a `Library` contains `Books`, but a `Library` *is-not-a* `Book`. This is a composition relationship ("has-a"), not inheritance.

---

### The `extends` Keyword
To establish inheritance in Java, use the **`extends`** keyword in the class signature:

```java
public class Employee {
    String name;
}

public class Manager extends Employee {
    // Manager automatically inherits the 'name' field from Employee!
    double bonus;
}
```

---

### Constructor Chaining and the `super` Keyword
When you instantiate a child object, **the parent object state must be initialized first**. This memory synchronization is managed by **Constructor Chaining**:
*   The child class constructor must call the parent constructor using **`super()`** on the **absolute first line** of its body.
*   If you do not write a call to `super()`, the Java compiler automatically inserts a hidden call to the parameterless `super()` default constructor.
*   **The Compiler Trap**: If the parent class only defines a parameterized constructor (e.g. `public Employee(String name)`) and does not define a parameterless constructor, the compiler's implicit `super()` call will fail, throwing a compile error: *"Constructor Employee in class Employee cannot be applied to given types"*. You must write the call to `super(name)` explicitly.

```java
public class Manager extends Employee {
    double bonus;

    public Manager(String name, double bonus) {
        super(name); // Call to parent constructor - MUST be first statement!
        this.bonus = bonus;
    }
}
```

---

### Accessing Parent Behavior: `super.method()`
If a child class overrides or extends a parent method, you can invoke the parent's base logic from inside the child method using the **`super.methodName()`** prefix:

```java
public void printCard() {
    super.printCard(); // Run parent printCard logic
    System.out.println("Bonus: $" + bonus); // Append child-specific details
}
```

---

### Single Inheritance Rule
Java classes only support **single inheritance**. A class can only inherit directly from one parent class:
```java
// public class Manager extends Employee, Director { } // COMPILER ERROR: Multiple inheritance forbidden!
```
This constraint prevents the **Diamond Problem**—a compilation ambiguity where a child class inherits conflicting implementations of the same method from two different parent classes.

---

## Code Example: Employee Hierarchy Setup
The following class models an employee hierarchy, showcasing class inheritance, constructor chaining using `super()`, and accessing parent methods using the `super` reference:

```java
// Parent Superclass
class Employee {
    // Fields inherited by all subclasses
    protected String name;
    protected double baseSalary;

    // Constructor (No default parameterless constructor available)
    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = (baseSalary >= 0.0) ? baseSalary : 0.0;
        System.out.println("[SUPERCLASS] Employee constructor initialized for: " + name);
    }

    public void printEmployeeDetails() {
        System.out.print("Name: " + name + " | Base Salary: $" + baseSalary);
    }
}

// Child Subclass
class Manager extends Employee {
    private double bonus; // Child-specific field

    // Constructor demonstrating chaining
    public Manager(String name, double baseSalary, double bonus) {
        // 1. Explicit invocation of parent constructor. Must be first statement!
        super(name, baseSalary); 
        this.bonus = (bonus >= 0.0) ? bonus : 0.0;
        System.out.println("[SUBCLASS] Manager constructor initialized.");
    }

    // Overriding preview (Customizing parent details)
    @Override
    public void printEmployeeDetails() {
        // 2. Invoke parent print logic using super prefix
        super.printEmployeeDetails(); 
        // 3. Print child-specific bonus fields
        System.out.println(" | Manager Bonus: $" + bonus);
    }

    public void runManagerReport() {
        // Manager can access protected parent fields directly
        System.out.println("[REPORT] Manager " + name + " is generating system reports.");
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        System.out.println("--- Instantiating Subclass Manager ---");
        // Creating the child object automatically runs the parent constructor first
        Manager mgr = new Manager("Alice Vance", 95000.0, 15000.0);

        System.out.println("\n--- Triggering Overridden Details Method ---");
        mgr.printEmployeeDetails(); // Outputs combined Employee + Manager variables

        System.out.println("\n--- Triggering Subclass-Specific Behavior ---");
        mgr.runManagerReport();
    }
}
```

---

## Summary
- **Inheritance** establishes a parent-child hierarchy between classes using the **`extends`** keyword.
- Validate inheritance design using the **"is-a"** relationship rule (e.g. `Dog` is-an `Animal`).
- Subclass constructors must trigger the parent constructor using **`super(args)`** as the absolute first statement in the body.
- Use **`super.methodName()`** to invoke parent implementations inside overridden subclass methods.
- Java classes enforce **single inheritance** to prevent implementation conflicts (the Diamond Problem).

---

## Additional Resources
- [Java Inheritance Tutorial - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html)
- [Inheritance in Java - W3Schools](https://www.w3schools.com/java/java_inheritance.asp)
- [The Diamond Problem and Java Solutions - GeeksforGeeks](https://www.geeksforgeeks.org/multiple-inheritance-in-java/)