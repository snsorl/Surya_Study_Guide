# OOP Polymorphism

## Learning Objectives
- Define Polymorphism and explain its value in writing flexible, extensible code.
- Contrast Compile-Time Polymorphism (Static Binding) with Runtime Polymorphism (Dynamic Binding).
- Explain the mechanics of **Dynamic Method Dispatch** (how the JVM resolves method calls at runtime).
- Write a clean, polymorphic Java application using parent references to manage child objects dynamically.

---

## Why This Matters
Polymorphism literally translates to "many forms." It is the pillar of OOP that enables you to write software that is open for extension but closed for modification. 

Consider a payment gateway system. If you write separate payment methods for every single provider—such as `processCreditCard(CreditCard cc, double amount)` and `processPayPal(PayPal pp, double amount)`—your core application is tightly coupled. If your company adds Apple Pay tomorrow, you have to write a new method and modify your entire check-out coordinator. 

With polymorphism, you define a generic parent interface/class named `PaymentMethod` and write a single, clean coordinator method: `processPayment(PaymentMethod method, double amount)`. You can pass *any* subclass of `PaymentMethod` into it, and Java will automatically match the correct payment logic at runtime. You can add infinite new payment methods without changing a single line of your checkout coordinator class.

---

## The Concept

### The Two Types of Polymorphism
Java implements polymorphism in two distinct phases:

```
                          POLYMORPHISM
                         /            \
       [ Compile-Time ]                  [ Runtime ]
       - Static Binding                  - Dynamic Binding
       - Method Overloading              - Method Overriding
       - Resolved by compiler            - Resolved by JVM at runtime
```

#### 1. Compile-Time Polymorphism (Static Binding)
This is achieved via **Method Overloading** (methods in the same class sharing a name but having different parameter signatures).
*   **Mechanic**: The compiler looks at the number and types of arguments you pass during compilation and binds the call to the matching method signature immediately.

#### 2. Runtime Polymorphism (Dynamic Binding)
This is achieved via **Method Overriding** (a subclass providing its own specific implementation of a method declared in its parent superclass).
*   **Mechanic**: The compiler only checks if the parent class has the method declaration. The actual decision of which subclass code block to execute is deferred to the JVM at runtime, based on the actual object created on the Heap.

---

### Upcasting and Dynamic Method Dispatch
To understand runtime polymorphism, you must understand how Java resolves method calls on upcasted references.

```java
PaymentProcessor processor = new PayPalProcessor();
processor.process(150.00);
```

#### The Stack vs. Heap Boundary:
*   **The Reference Type (Stack)**: The variable `processor` is declared as a `PaymentProcessor`. The compiler looks at this class to decide if the method call `process(150.00)` is valid. If `PaymentProcessor` does not declare a `process` method, the code fails to compile.
*   **The Object Type (Heap)**: The actual object constructed in memory is a `PayPalProcessor`. When the program runs, the JVM looks at the actual heap object and executes the overridden `process` method inside `PayPalProcessor`.

This process of looking up the method implementation on the actual Heap object at runtime is called **Dynamic Method Dispatch**.

---

## Code Example: Payment Integration System
The following program defines a base `PaymentProcessor` class, `CreditCardProcessor` and `PayPalProcessor` subclasses, and demonstrates compile-time checks and dynamic polymorphic array loops:

```java
// Base Parent Class
class PaymentProcessor {
    public void process(double amount) {
        System.out.println("[BASE] Processing generic payment of $" + amount);
    }
}

// Subclass A
class CreditCardProcessor extends PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("[CREDIT CARD] Charging transaction fee + processing $" + amount);
    }
}

// Subclass B
class PayPalProcessor extends PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("[PAYPAL] Connecting to secure API & transfering $" + amount);
    }
}

// Check-out Coordinator
public class PolymorphismDemo {
    
    // Poly-functional method: accepts any subclass of PaymentProcessor
    public static void executeCheckout(PaymentProcessor processor, double amount) {
        // Compiler checks: Does PaymentProcessor have process(double)? Yes.
        // JVM execution: Look up actual object type on the Heap and run its process method.
        processor.process(amount); 
    }

    public static void main(String[] args) {
        System.out.println("--- 1. Polymorphic Method Invocations ---");
        PaymentProcessor card = new CreditCardProcessor(); // Upcasting
        PaymentProcessor paypal = new PayPalProcessor();   // Upcasting

        executeCheckout(card, 250.00);   // Executes CreditCard override
        executeCheckout(paypal, 120.00); // Executes PayPal override

        System.out.println("\n--- 2. Batch Processing via Polymorphic Arrays ---");
        // An array of parent reference types holding diverse subclass instances
        PaymentProcessor[] transactions = new PaymentProcessor[3];
        transactions[0] = new CreditCardProcessor();
        transactions[1] = new PayPalProcessor();
        transactions[2] = new PaymentProcessor(); // Generic base fallback

        double invoiceAmount = 50.00;
        for (PaymentProcessor tx : transactions) {
            // Dynamic Method Dispatch executes the correct logic for each array element
            tx.process(invoiceAmount);
        }
    }
}
```

---

## Summary
- **Polymorphism** allows objects of different classes to respond to the same method call in their own way.
- **Compile-Time Polymorphism** uses method overloading and is bound statically during compilation.
- **Runtime Polymorphism** uses method overriding and is bound dynamically during execution.
- **Dynamic Method Dispatch** determines the method execution at runtime based on the **Heap object type**, while the **Stack reference type** controls compiler visibility.
- Polymorphism reduces architectural coupling, allowing you to write highly extensible frameworks.

---

## Additional Resources
- [Polymorphism Tutorial - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html)
- [Dynamic Method Dispatch in Java - GeeksforGeeks](https://www.geeksforgeeks.org/dynamic-method-dispatch-java-runtime-polymorphism/)
- [Understanding Static and Dynamic Binding - Baeldung](https://www.baeldung.com/)