# Class Members and Constructors

## Learning Objectives
- Identify and declare the core members of a class: fields, methods, and constructors.
- Write overloaded constructors to initialize object states under different data conditions.
- Describe the rules of the JVM **Default Constructor** and explain when it is suppressed.
- Apply the **`this`** keyword to resolve variable shadowing and invoke sibling constructors.

---

## Why This Matters
When you write classes to model real-world concepts (like a bank account, a product in an online store, or an active user), you need a reliable way to set up the data values of that object at the exact moment it is created. 

If you instantiate a `Product` object and then require three separate lines of code in another class to manually assign `product.id = 101`, `product.name = "Cup"`, and `product.price = 5.99`, you create fragile, unsafe code. If you forget to set the price, your catalog breaks. **Constructors** solve this by enforcing initialization rules. They guarantee that an object cannot be instantiated in memory without passing the required configuration values, ensuring your objects always begin their lifecycles in a valid state.

---

## The Concept

### Class Members
A class contains three primary types of members:
1.  **Fields (Variables)**: Define the data state of the object.
2.  **Constructors**: Special code blocks used to initialize new objects.
3.  **Methods**: Define the actions or behaviors the object can perform.

---

### Understanding Constructors
A constructor looks like a method, but it is structurally distinct:
*   **The Naming Rule**: The constructor name must match the **exact case-sensitive name of the Class**.
*   **No Return Type**: Constructors **do not declare any return type**, not even `void`. If you add a return type, Java compiles it as a standard method rather than a constructor, which leads to instantiation failures.

```java
public class Book {
    // 1. Fields
    String title;

    // 2. Constructor (No return type, matches class name)
    public Book(String title) {
        this.title = title;
    }
}
```

---

### The Default Constructor
If you do not write any constructors for your class, the Java compiler automatically inserts a hidden, parameterless **default constructor** during compilation:

```java
public Book() {
    // Embedded automatically if no constructors are declared
}
```
This default constructor initializes primitive fields to `0` or `false`, and reference fields to `null`.

> [!WARNING]
> **If you declare a custom constructor with parameters, the compiler does NOT generate the default constructor.** If external classes attempt to run `new Book()` without passing arguments, the code will fail to compile. If you still need a parameterless constructor, you must write it manually.

---

### Constructor Overloading
Like methods, constructors can be overloaded by declaring different parameter signatures. This allows you to construct objects using different amounts of initial data.

---

### Sibling Constructor Invocation: `this()`
Inside an overloaded constructor, you can call another constructor in the same class (a sibling constructor) using the **`this(arguments)`** syntax. This reduces code duplication.
*   **The Rule**: The call to `this()` **must be the absolute first statement** in the constructor body.

```java
public Book(String title) {
    this(title, "Unknown Author"); // Calls the two-parameter constructor
}

public Book(String title, String author) {
    this.title = title;
    this.author = author;
}
```

---

## Code Example: Product Inventory Setup
The following class defines a `Product` catalog item, showcasing fields, overloaded constructors, sibling constructor chaining using `this()`, and method implementations:

```java
class Product {
    // 1. Instance Fields (State)
    private int id;
    private String name;
    private double price;

    // 2. Constructor A: Parameterless Custom Constructor
    public Product() {
        // Chaining to Constructor C using this()
        this(0, "GENERIC-PRODUCT", 0.0);
        System.out.println("[CONSTRUCTOR] Parameterless constructor invoked.");
    }

    // 3. Constructor B: Single Name Parameter Constructor
    public Product(int id, String name) {
        // Chaining to Constructor C
        this(id, name, 0.99); 
        System.out.println("[CONSTRUCTOR] ID & Name constructor invoked.");
    }

    // 4. Constructor C: Full Parameterized Constructor (The core initialization point)
    public Product(int id, String name, double price) {
        // 'this.name' refers to instance field; 'name' refers to local parameter
        this.id = id;
        this.name = name;
        this.price = (price >= 0.0) ? price : 0.0; // Validation logic
        System.out.println("[CONSTRUCTOR] Full parameters constructor invoked.");
    }

    // 5. Instance Method (Behavior)
    public void displayProductInfo() {
        System.out.println("Product ID: " + id + " | Name: " + name + " | Price: $" + price);
    }
}

public class MembersDemo {
    public static void main(String[] args) {
        System.out.println("--- Instantiating Product 1 (Full parameters) ---");
        Product p1 = new Product(101, "Coffee Maker", 89.99);
        p1.displayProductInfo();

        System.out.println("\n--- Instantiating Product 2 (Partial parameters) ---");
        Product p2 = new Product(202, "Notebook"); // Chains to constructor C
        p2.displayProductInfo();

        System.out.println("\n--- Instantiating Product 3 (Default parameters) ---");
        Product p3 = new Product(); // Chains to constructor C
        p3.displayProductInfo();
    }
}
```

---

## Summary
- A class contains **fields** (state), **methods** (behavior), and **constructors** (initialization).
- Constructors match the class name exactly and have **no return type**.
- If no constructors are written, the compiler generates a hidden **default constructor**. Writing a custom constructor suppresses this.
- Use **`this(args)`** to chain constructors within the same class. This call must be the first line of the constructor body.
- Use **`this.fieldName`** to resolve variable shadowing between parameters and instance fields.

---

## Additional Resources
- [Providing Constructors for Your Classes - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html)
- [Java Constructors - W3Schools](https://www.w3schools.com/java/java_constructors.asp)
- [Understanding Sibling Constructor Chaining - Baeldung](https://www.baeldung.com/)