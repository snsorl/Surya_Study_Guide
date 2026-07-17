# Method Overriding

## Learning Objectives
- Define the rules of Method Overriding and implement valid subclass overrides.
- Apply the **`@Override`** annotation to enforce compiler checks.
- Describe the rules governing covariant return types and visibility modifications during overriding.
- Identify methods that cannot be overridden (final, static, private).
- Invoke parent implementations inside overridden methods using **`super`**.

---

## Why This Matters
While inheritance allows a subclass to reuse parent variables and methods, child classes often need to customize parent behaviors. For example, in an HR application, the parent class `Employee` might have a method named `calculateBonus()`. A `Manager` subclass inherits this, but a manager's bonus is calculated using a different formula than a standard employee's. 

If you could not override methods, you would have to write unique method names for every class—like `calculateEmployeeBonus()` and `calculateManagerBonus()`. This breaks polymorphism. **Method Overriding** allows the subclass to redefine the parent method's behavior under the same name and parameters, enabling you to call `calculateBonus()` on any employee reference and have the system automatically execute the correct subclass formula.

---

## The Concept

### The Rules of Overriding
For a subclass method to successfully override a parent class method, it must satisfy these rules:

#### 1. Signature Match
The method in the subclass must have the **exact same name** and the **exact same parameter list** (types, count, and order) as the parent method. If the parameters differ, you have *overloaded* the method rather than overridden it.

#### 2. Access Modifier Compatibility
The subclass method **cannot make the access level more restrictive** than the parent method. It can, however, make it less restrictive (more public):
*   If parent method is `protected`, the override can be `protected` or `public`, but **not** default or `private`.
*   If parent method is `public`, the override **must** be `public`.

#### 3. Covariant Return Types
The return type of the subclass override must be the same as, or a subclass of (covariant to), the return type declared in the parent method.

```java
class Employee {
    public Employee getReports() { ... }
}

class Manager extends Employee {
    @Override
    public Manager getReports() { ... } // Valid: Manager is a subclass of Employee
}
```

---

### Non-Overridable Methods
*   **`private` Methods**: Are invisible outside their class, so a subclass cannot see them to override them.
*   **`final` Methods**: The keyword `final` explicitly blocks subclasses from overriding the method to protect core business logic.
*   **`static` Methods**: Belong to the class itself, not object instances. If you declare a static method in a subclass with the same signature as a static method in the parent class, you are **hiding** the parent method, not overriding it.

---

### The `@Override` Annotation
Always write **`@Override`** above your overridden methods. 
*   It is a metadata instruction to the compiler to check that the method signature matches a parent method.
*   If you make a typo (e.g. writing `calculatBonus` instead of `calculateBonus`), the compiler will throw an error immediately, preventing hard-to-find runtime bugs where your custom code is ignored.

---

### Calling Parent Implementations
If you want the subclass method to add new logic while preserving the parent's base logic, invoke the parent method using **`super.methodName()`**:

```java
@Override
public void displayDetails() {
    super.displayDetails(); // Run parent display logic
    System.out.println("Subclass specific details...");
}
```

---

## Code Example: Executive Bonus Overriding
The following program defines a base `Employee` class, an `Executive` subclass, and demonstrates valid overrides, compilation checks, and parent method calls:

```java
class Employee {
    protected String name;
    protected double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    // Overridable method
    public double calculateBonus() {
        return this.baseSalary * 0.05; // Standard 5% bonus
    }

    // Final method: cannot be overridden by any subclass
    public final void printCompanyPolicy() {
        System.out.println("[POLICY] Work hours are 9 AM to 5 PM.");
    }
}

class Executive extends Employee {
    private double stockOptionsValue;

    public Executive(String name, double baseSalary, double stockOptionsValue) {
        super(name, baseSalary);
        this.stockOptionsValue = stockOptionsValue;
    }

    // 1. Valid Override using @Override annotation
    @Override
    public double calculateBonus() {
        // 2. Accessing parent behavior using super reference
        double standardBonus = super.calculateBonus(); 
        // 3. Adding custom executive logic
        double performanceBonus = this.baseSalary * 0.10; // Extra 10%
        return standardBonus + performanceBonus + (stockOptionsValue * 0.02);
    }

    /*
    // ILLEGAL OVERRIDE: Cannot override final methods
    @Override
    public void printCompanyPolicy() {
        System.out.println("Flex hours allowed.");
    }
    // COMPILER ERROR: printCompanyPolicy() in Executive cannot override printCompanyPolicy() in Employee; overridden method is final
    */

    /*
    // ILLEGAL OVERRIDE: Cannot reduce visibility (public to private)
    @Override
    private double calculateBonus() {
        return 0.0;
    }
    // COMPILER ERROR: calculateBonus() in Executive cannot override calculateBonus() in Employee; attempting to assign weaker access privileges; was public
    */
}

public class OverridingDemo {
    public static void main(String[] args) {
        System.out.println("--- Standard Employee Bonus ---");
        Employee emp = new Employee("John Bob", 60000.0);
        System.out.println(emp.name + " Bonus: $" + emp.calculateBonus()); // $3000.00

        System.out.println("\n--- Executive Custom Bonus (Overridden) ---");
        Employee exec = new Executive("Alice Vance", 120000.0, 50000.0); // Upcasting
        // Runtime Polymorphism: executes Executive's overridden calculateBonus
        System.out.println(exec.name + " Bonus: $" + exec.calculateBonus()); // $3000(super) + $12000(perf) + $1000(stocks) = $19000.00
        
        exec.printCompanyPolicy(); // Invokes final parent method
    }
}
```

---

## Summary
- **Method Overriding** allows a subclass to provide a specific implementation of a parent method.
- The override must have the **exact same name and parameters** (signature).
- The access modifier **cannot be more restrictive** than the parent method.
- Return types must be the same or **covariant** (a subclass of the parent return type).
- Always use **`@Override`** to enforce compiler signature validation checks.
- Use **`super.methodName()`** to invoke parent logic inside the overridden method body.
- Methods marked **`final`, `static`, or `private` cannot be overridden**.

---

## Additional Resources
- [Overriding and Hiding Methods - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/IandI/override.html)
- [Method Overriding in Java - Baeldung](https://www.baeldung.com/java-method-overriding)
- [Method Overriding Guidelines - GeeksforGeeks](https://www.geeksforgeeks.org/method-overriding-in-java/)