# Introduction to Object-Oriented Programming (OOP)

## Learning Objectives
- Differentiate between the procedural programming paradigm and the object-oriented programming paradigm.
- Explain the core benefits of OOP: modularity, reusability, and maintenance at scale.
- Describe the **Four Pillars of OOP** (Encapsulation, Inheritance, Polymorphism, and Abstraction) using analogies.
- Contrast a procedural code structure with an object-oriented code structure in Java.

---

## Why This Matters
When programming first emerged, code was structured sequentially: a program was a series of step-by-step instructions executed from top to bottom (known as procedural programming). While this approach is easy to follow for tiny scripts, it collapses under its own weight as projects grow. 

In a large procedural application, you might have hundreds of functions and thousands of variables scattered across a codebase. Because there are no boundaries, any function can modify any variable, leading to unpredictable bugs. If you update a customer's address in one place, another block of code might overwrite it. 

**Object-Oriented Programming (OOP)** solves this by bundling data and the methods that operate on that data into self-contained units called **objects**. OOP mimics how we view the physical world, making software systems modular, organized, and easier to secure and maintain.

---

## The Concept

### The Paradigm Shift: Procedural vs. Object-Oriented

#### Procedural Paradigm (Action-Oriented)
Focuses on writing a sequential series of functions or procedures to process passive data. 
*   **The Model**: `Data + Functions = Program`
*   **Drawback**: Data is separated from functions. As the program grows, managing globally accessible variables becomes difficult.

#### Object-Oriented Paradigm (Entity-Oriented)
Focuses on defining entities (objects) that own their data and expose controlled methods to interact with it.
*   **The Model**: `Objects (Data + Behavior) = Program`
*   **Advantage**: Data is protected inside object boundaries. Changes to an object's internal implementation do not break external parts of the application.

---

### The Four Pillars of OOP
OOP is built on four fundamental design principles. You will study each of these in depth over the coming days, but you must know their conceptual definitions and analogies today:

```
                  +-----------------------------------+
                  |        THE 4 PILLARS OF OOP       |
                  +-----------------------------------+
                   /        |               |        \
  [ Encapsulation ]  [ Inheritance ]  [ Polymorphism ]  [ Abstraction ]
   (Data Protection)   (Code Reuse)     (Many Forms)     (Interface/API)
```

#### 1. Encapsulation (Data Shielding)
*   **Definition**: Bundling a class's variables (state) and methods (behavior) together, and restricting direct access to the variables from the outside world.
*   **Analogy**: A medical pill capsule. The active medicine ingredients are encapsulated inside the shell, protecting them from contamination and preventing patients from taking raw chemical powders directly.

#### 2. Inheritance (Relationship Hierarchy)
*   **Definition**: Creating a parent-child relationship between classes, allowing a new class (subclass) to inherit the properties and methods of an existing class (superclass).
*   **Analogy**: Genetics. A child inherits basic characteristics (eye color, hair) from their parent but can also develop unique skills (playing piano). In software, a `Truck` inherits variables like `engineSize` and `mileage` from a parent `Vehicle` class.

#### 3. Polymorphism (Dynamic Forms)
*   **Definition**: The ability of different objects to respond to the exact same method call in their own unique way.
*   **Analogy**: The "Play" button. Pressing "Play" on a CD player spins a disc and plays audio. Pressing "Play" on a DVD player reads a laser and renders a movie. The action is the same ("Play"), but the execution depends on the device.

#### 4. Abstraction (Complexity Reduction)
*   **Definition**: Hiding the complex internal implementation details of a class and exposing only the essential features through a simple, user-friendly interface.
*   **Analogy**: A television remote. The remote hides a complex circuit board and radio frequency signals. The user only needs to interact with simple buttons like "Power" and "Volume Up."

---

## Code Example: Procedural vs. Object-Oriented Java
Let's see how a simple banking feature is modeled in both paradigms.

### The Procedural Way
In procedural style, we keep database variables loose and pass them to functions. Notice how anyone can modify the balance variable directly without validation checks:

```java
public class ProceduralBank {
    public static void main(String[] args) {
        // Passive data variables stored loosely
        String accountOwner = "Alice";
        double balance = 500.00;

        // Perform deposit via procedural function
        balance = deposit(balance, 150.00);
        System.out.println(accountOwner + " has balance: $" + balance);

        // LOGIC BUG: No protection! Anyone can change balance directly to invalid values
        balance = -10000.00; 
        System.out.println("Corrupted balance: $" + balance);
    }

    public static double deposit(double currentBalance, double amount) {
        if (amount > 0) {
            return currentBalance + amount;
        }
        return currentBalance;
    }
}
```

---

### The Object-Oriented Way
In the OOP style, we create a `BankAccount` class. The variable `balance` is marked `private` (encapsulated). It cannot be modified directly; it can only be modified through the `deposit` method, which contains validation checks:

```java
class BankAccount {
    // 1. Private variables (Encapsulation) - cannot be accessed directly
    private String owner;
    private double balance;

    // Constructor to initialize state
    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        }
    }

    // 2. Controlled access method
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount; // Protected update
        } else {
            System.out.println("Error: Deposit amount must be positive.");
        }
    }

    // Getter to retrieve value safely
    public double getBalance() {
        return this.balance;
    }

    public String getOwner() {
        return this.owner;
    }
}

public class OopBank {
    public static void main(String[] args) {
        // Instantiate the object
        BankAccount account = new BankAccount("Alice", 500.00);

        // Interacting with the object via its public methods
        account.deposit(150.00);
        System.out.println(account.getOwner() + " has balance: $" + account.getBalance());

        // account.balance = -10000.00; // COMPILER ERROR: balance has private access in BankAccount!
    }
}
```

---

## Summary
- The **procedural paradigm** organizes code around functions acting on separate data variables.
- The **object-oriented paradigm (OOP)** groups data (fields) and behaviors (methods) together into **objects**.
- OOP supports modularity and scale by partitioning code into independent class structures.
- The 4 pillars of OOP are **Encapsulation** (shielding details), **Inheritance** (reusing code), **Polymorphism** (implementing distinct behaviors), and **Abstraction** (presenting clean interfaces).

---

## Additional Resources
- [Object-Oriented Programming Concepts - Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/java/concepts/)
- [Procedural vs OOP: What is the Difference? - GeeksforGeeks](https://www.geeksforgeeks.org/difference-between-procedural-and-object-oriented-programming/)
- [The Four Pillars of OOP in Java - Baeldung](https://www.baeldung.com/)