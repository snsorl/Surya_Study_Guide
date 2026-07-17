# Method Declaration and Syntax

## Learning Objectives
- Define what a method is and explain how it supports the DRY (Don't Repeat Yourself) principle.
- Dissect a method declaration into its individual components: modifiers, return type, name, parameters, and body.
- Differentiate between void methods and value-returning methods.
- Write compilable method bodies with correct return statements and type matching.
- Troubleshoot common method syntax and compilation errors.

---

## Why This Matters
In your first days of programming, you wrote all your code sequentially inside the `main` method. While this works for simple exercises, it quickly becomes unmanageable as applications scale. If you copy and paste the same block of code five times to perform a tax calculation in different parts of your program, you violate the **DRY (Don't Repeat Yourself)** principle. If the tax rate changes, you must find and update all five places, increasing the risk of code inconsistencies and bugs.

**Methods** solve this. A method is a named, self-contained block of code that performs a single, specific task. By separating your program into independent methods, you make your code modular, easier to test, and reusable. In enterprise Java systems, writing clean, focused methods is the primary way we organize application backends.

---

## The Concept

### What is a Method?
A method is a block of statements grouped together to perform an operation. You can think of a method as a black box: you pass input data (arguments) into it, it processes that data, and it outputs a result (return value) or performs an action (like logging or updating a database).

In Java, **every method must belong to a class**. Unlike languages like Python or C++, you cannot have loose, standalone functions floating outside class containers.

---

### Dissecting Method Declaration Syntax
To declare a method, you must write its signature and its body. Let's break down the general structure:

```java
modifier returnType methodName(parameterList) {
    // Method Body (statements)
    return value; // Required if returnType is not void
}
```

```
   public static      int      calculateSum  (int firstNum, int secondNum) { ... }
   \___________/     \___/     \__________/  \___________________________/
     Modifiers      Return        Method               Parameter
                     Type          Name                  List
```

Let's analyze each of these components in detail:

#### 1. Modifiers
Modifiers define the access level and architectural characteristics of the method:
*   **Access Modifiers** (e.g., `public`, `private`): Define which other classes are allowed to invoke this method. We will study this in depth later today, but generally, `public` means anyone can use it, and `private` restricts usage to the class itself.
*   **Specifier Modifiers** (e.g., `static`): Define how the method relates to the class. A method marked **`static`** belongs to the class itself and can be called directly without instantiating an object. Methods without `static` are instance methods and require a `new` object to run.

#### 2. Return Type
The return type specifies the data type of the value the method sends back to the code that called it.
*   **Primitive Return Types**: Can be any primitive (e.g., `int`, `double`, `boolean`). The method *must* return a value that is compatible with this type.
*   **Reference Return Types**: Can be any object or array (e.g., `String`, `int[]`).
*   **Void (`void`)**: If the method performs an action (like printing to a console) but does not produce a value that needs to be returned to the program, use the keyword `void`.

#### 3. Method Name
This is the identifier used to invoke the method. Java enforces **camelCase** naming conventions for methods. 
*   **Naming Rule**: Start with a verb in lowercase, and capitalize the first letter of each subsequent word (e.g., `processPayment`, `getUserData`, `isValidPassword`). Avoid generic names like `doStuff` or `run`.

#### 4. Parameter List
The parameter list, enclosed in parentheses `()`, defines the data types and names of the inputs the method expects to receive.
*   It acts as a placeholder for data that will be passed into the method.
*   If a method needs multiple inputs, separate them with commas: `(int age, String name)`.
*   If a method requires no inputs, leave the parentheses empty: `()`.

#### 5. Method Body
The method body, enclosed in curly braces `{ }`, contains the actual Java statements that execute when the method is invoked.

---

### The Return Statement and Type Matching
If a method declares a non-void return type, it must execute a `return` statement before exiting. The value returned must match the declared return type or be convertible to it (via implicit casting).

```java
public static double getDiscount() {
    return 10; // Compiles: int 10 is implicitly cast to double 10.0
}

public static int getAge() {
    // return "25"; // COMPILER ERROR: Type mismatch, String cannot be converted to int
    return 25;
}
```

#### Multiple Return Paths
If your method contains conditional branches (`if-else` or `switch`), **every possible path** must execute a return statement. If there is a branch where a return is missed, the compiler will fail with: `"Missing return statement"`.

```java
// COMPILER ERROR PATHWAY:
public static String evaluateScore(int score) {
    if (score >= 50) {
        return "Pass";
    }
    // Error! If score is less than 50, the method has no return statement!
}

// CORRECT PATHWAY:
public static String evaluateScoreCorrect(int score) {
    if (score >= 50) {
        return "Pass";
    } else {
        return "Fail"; // Every execution path is covered
    }
}
```

---

## Code Example
Let's look at a class that implements void methods, math calculators with multiple return paths, and demonstrates error-handling returns:

```java
public class MethodSyntaxDemo {

    /**
     * Prints a standardized header to the console.
     * Since the return type is void, no return statement is required.
     */
    public static void printHeader() {
        System.out.println("=================================");
        System.out.println("     SYSTEM DIAGNOSTIC UTILITY   ");
        System.out.println("=================================");
        // return; // Optional in void methods; can be used to exit early
    }

    /**
     * Calculates the tax amount based on status.
     * Illustrates multiple return statements inside conditional branches.
     * 
     * @param income The gross income to calculate tax for
     * @param isBusiness True if client is a corporate entity
     * @return The double tax value
     */
    public static double calculateTax(double income, boolean isBusiness) {
        // Guard check: if input is negative, return 0.0 immediately
        if (income <= 0) {
            System.out.println("Warning: Negative or zero income. No tax calculated.");
            return 0.0;
        }

        if (isBusiness) {
            return income * 0.15; // 15% business tax
        } else {
            return income * 0.20; // 20% personal tax
        }
    }

    public static void main(String[] args) {
        // 1. Invoking the void method
        printHeader();

        // 2. Invoking the value-returning method with business status
        double businessTax = calculateTax(50000.0, true);
        System.out.println("Business Tax due: $" + businessTax);

        // 3. Invoking the value-returning method with personal status
        double personalTax = calculateTax(80000.0, false);
        System.out.println("Personal Tax due: $" + personalTax);

        // 4. Testing the guard check logic path
        double zeroTax = calculateTax(-1500.0, false);
        System.out.println("Invalid Tax value: $" + zeroTax);
    }
}
```

---

## Summary
- A **method** is a named, structured collection of statements designed for code reuse and modularity.
- Modifiers like **`public static`** define who can call the method and if it requires an instantiated object.
- Value-returning methods must specify a **return type** and execute a **`return`** statement on all logic paths.
- **`void`** methods do not return values and do not require `return` statements unless exiting early.
- Always use **camelCase** verb-based names (e.g. `calculateSalary`) for method names.

---

## Additional Resources
- [Defining Methods - Oracle Java Documentation](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html)
- [Java Methods Reference - W3Schools](https://www.w3schools.com/java/java_methods.asp)
- [Declaring Methods in Java - GeeksforGeeks](https://www.geeksforgeeks.org/methods-in-java/)