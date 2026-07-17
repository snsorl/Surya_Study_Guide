# Classes vs. Objects

## Learning Objectives
- Differentiate between a Class (the template) and an Object (the memory instance).
- Declare a Java class with instance fields and methods.
- Explain the step-by-step memory allocation on the Stack and Heap when using the `new` keyword.
- Instantiate multiple distinct objects from the same class and trace their state changes independently.

---

## Why This Matters
To write object-oriented software, you must master the relationship between classes and objects. Think of a class as a blueprint for a smartphone, and an object as the physical phone you hold in your hand. The blueprint defines what features the phone will have (e.g. storage size, screen resolution) and what it can do (e.g. make calls, take photos). However, you cannot make a call using a paper blueprint. You must construct the physical phone (instantiation) first. 

Similarly, you can manufacture millions of phone objects from a single blueprint. Each phone will reside in a different location, belong to a different user, and hold different photos—but they all share the basic structure defined by the blueprint. In Java, writing classes sets up your data templates, while creating objects populates your active computer memory.

---

## The Concept

### The Blueprint vs. The Instance
*   **Class**: A user-defined data type that serves as the template or blueprint. It defines the names and types of variables (state) and methods (behavior) that will exist. A class definition occupies no heap memory space on its own; it is static template code.
*   **Object**: A concrete instance of a class. It is created dynamically in memory, occupies a specific address block on the Heap, and maintains its own private values for all variables defined by the class.

---

### Memory Allocation: Under the Hood of Instantiation
Let's analyze what happens in computer memory when you execute this line of code:

```java
Car myCar = new Car();
```

```
STACK                                            HEAP
[ myCar (0x805) ] ===(Points to address)===> [ Object of type Car (0x805) ]
                                               ├── color = null
                                               └── speed = 0
```

1.  **Reference Declaration (`Car myCar`)**: Java allocates a reference variable named `myCar` on the **Stack**. This variable is not the object itself; it is a container that will hold a memory address pointer.
2.  **Heap Allocation (`new`)**: The **`new`** keyword instructs the JVM to allocate a new block of physical memory on the **Heap** large enough to store all the instance variables defined in the `Car` class.
3.  **Initialization (`Car()`)**: The constructor is invoked to initialize the heap variables to their default values (e.g., numbers to `0`, object references to `null`) or custom values.
4.  **Reference Assignment (`=`)**: The memory address of the newly allocated Heap object (e.g., `0x805`) is returned and saved inside the Stack variable `myCar`.

---

### Operating on Multiple Instances
Because each object has its own Heap memory block, modifying variables on one object does not change variables on another, even if they were built from the exact same class:

```java
Car car1 = new Car();
Car car2 = new Car();

car1.color = "Red";
car2.color = "Blue"; // car1.color remains "Red"!
```

---

## Code Example: Employee Record Management
Let's see this in action by defining an `Employee` class template and instantiating separate employee records:

```java
// The Blueprint (Class Template)
class Employee {
    // 1. Instance Fields (State) - each object maintains its own copies
    String name;
    String department;
    double salary;

    // 2. Instance Methods (Behavior) - operations acting on the object's fields
    public void giveRaise(double percentage) {
        if (percentage > 0) {
            double increase = this.salary * (percentage / 100);
            this.salary += increase;
            System.out.println("[SALARY UPDATE] " + name + " received a " + percentage + "% raise (+ $" + increase + ")");
        }
    }

    public void printEmployeeCard() {
        System.out.println("---------------------------------");
        System.out.println("Employee: " + name);
        System.out.println("Dept:     " + department);
        System.out.println("Salary:   $" + salary);
        System.out.println("---------------------------------");
    }
}

// Main Execution Class
public class ClassObjectDemo {
    public static void main(String[] args) {
        // A. Declaring and instantiating Employee 1
        // 'emp1' variable is on Stack; points to Employee object 1 on Heap
        Employee emp1 = new Employee();
        emp1.name = "Alice Smith";
        emp1.department = "Engineering";
        emp1.salary = 85000.00;

        // B. Declaring and instantiating Employee 2
        // 'emp2' is a separate Stack variable pointing to Employee object 2 on Heap
        Employee emp2 = new Employee();
        emp2.name = "Bob Jones";
        emp2.department = "Marketing";
        emp2.salary = 60000.00;

        // C. Displaying original states
        System.out.println("Original Employee Cards:");
        emp1.printEmployeeCard();
        emp2.printEmployeeCard();

        // D. Triggering behavior updates on emp1 only
        emp1.giveRaise(10.0);

        // E. Verifying that changes to emp1 did NOT modify emp2's data
        System.out.println("\nPost-Raise Employee Cards:");
        emp1.printEmployeeCard(); // Salary is updated to $93500.00
        emp2.printEmployeeCard(); // Salary remains unchanged at $60000.00
    }
}
```

---

## Summary
- A **Class** is the static design blueprint; an **Object** is the dynamic, physical memory instance.
- The **`new`** keyword allocates memory on the **Heap** and returns a reference address.
- Object references are stored on the **Stack**, while the actual object values reside on the **Heap**.
- Objects maintain their **own private states**. Modifying variables on one instance has zero impact on other instances of the same class.

---

## Additional Resources
- [Classes and Objects Guide - Oracle Java SE Tutorials](https://docs.oracle.com/javase/tutorial/java/javaOO/classes.html)
- [Java Classes vs. Objects - W3Schools](https://www.w3schools.com/java/java_classes.asp)
- [Understanding Java Memory Allocation - GeeksforGeeks](https://www.geeksforgeeks.org/memory-allocation-in-java-heap-and-stack/)