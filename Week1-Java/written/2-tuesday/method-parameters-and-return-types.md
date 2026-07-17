# Method Parameters and Return Types

## Learning Objectives
- Differentiate between method parameters (declared variables) and method arguments (passed values).
- Explain Java's strict **Pass-by-Value** evaluation strategy.
- Diagram how primitive types and reference types are handled in Stack and Heap memory during method calls.
- Write methods that safely modify or protect reference inputs like arrays.
- Return arrays or objects from value-returning methods.

---

## Why This Matters
When working in a professional team, you will write methods that process collections of data, edit user profiles, or calculate financial matrices. A lack of understanding of how Java passes variables to methods can cause catastrophic, silent bugs. 

For instance, if you write a method to print an array of values, and you modify that array's values inside the method, those changes persist after the method finishes. This is because Java handles primitives and reference variables differently under its **Pass-by-Value** model. Understanding memory flow is the boundary between writing buggy, erratic programs and writing predictable, clean software.

---

## The Concept

### Parameters vs. Arguments
It is common for developers to use these terms interchangeably, but they represent distinct concepts:
*   **Parameters**: The variable placeholders declared in the method signature. They define the type and name of data the method accepts.
*   **Arguments**: The actual data values you pass into the method when you call it.

```java
// 'a' and 'b' are parameters
public static void printDifference(int a, int b) { 
    System.out.println(a - b);
}

public static void main(String[] args) {
    int x = 50;
    // 'x' and '20' are arguments
    printDifference(x, 20); 
}
```

---

### Pass-by-Value in Depth
Java uses **Pass-by-Value** exclusively. This means that whenever you invoke a method, Java copies the value of each argument and assigns it to the method's corresponding parameter variable. The method receives a duplicate of the data, not a direct connection to the caller's variable container.

The practical behavior differs depending on whether you pass a primitive type or a reference type:

---

### 1. Passing Primitive Types
When you pass a primitive variable (like `int`, `double`, `boolean`), the value stored in the variable is copied directly. 
- The method parameter is a completely independent variable stored in a new Stack frame.
- Any changes made to the parameter inside the method body **have no effect** on the caller's original variable.

```
CALLER STACK FRAME                      METHOD STACK FRAME
[ value = 10 ] ===(Copy raw value)===>  [ num = 10 ]
                                        [ num = 999 ] (num changes, value remains 10)
```

---

### 2. Passing Reference Types (e.g., Arrays)
When you pass a reference variable (like an array or object), the value stored in the variable is a **memory address (pointer)**. Under pass-by-value, Java copies this memory address.
- Both the caller's variable and the method parameter hold the **same memory address pointer**.
- Both variables point to the **same object on the Heap**.

This leads to two distinct behaviors:

#### Behavior A: Modifying Object State (Side-Effects)
If you modify the contents of the object/array (using indexes or setters), the changes **will affect** the caller's original object because both references point to the same Heap data.

```
STACK                                            HEAP
[ pricesRef (0x77) ] ===(Points to)===> [ [10, 20] (0x77) ]
[ arrParameter (0x77) ] =================/
```

#### Behavior B: Reassigning the Reference Variable
If you reassign the parameter reference to a new object (using `new`), the parameter pointer simply updates to a new address. The caller's original variable continues to point to the original address.

```java
public static void modifyArray(int[] arr) {
    arr[0] = 99; // Modifies the shared heap object (caller sees this!)
    arr = new int[]{50, 60}; // Reassigns local parameter pointer (caller does NOT see this!)
}
```

---

### Returning References
Just as you can pass reference variables, you can return them. When returning a reference type (like an array), Java returns the **memory address** of the array created inside the method:

```java
public static int[] buildEmptyArray(int size) {
    int[] newArr = new int[size];
    return newArr; // Returns the reference pointer (address) to the new array
}
```

---

## Code Example
Let's see a complete program highlighting the differences between passing primitives, modifying references, and reassigning references:

```java
import java.util.Arrays;

public class ParametersDemo {

    /**
     * Attempts to modify a primitive variable.
     * Since Java is pass-by-value, the caller's original variable remains unchanged.
     */
    public static void attemptPrimitiveChange(int score) {
        System.out.println("   [Inside method] Original parameter score: " + score);
        score = 100; // Modifying the local Stack copy
        System.out.println("   [Inside method] Updated parameter score: " + score);
    }

    /**
     * Modifies elements of the array passed to it.
     * Since the address is copied, changes to the index are seen by the caller.
     */
    public static void modifyArrayContent(int[] numbers) {
        if (numbers != null && numbers.length > 0) {
            numbers[0] = 999; // Modifying the shared heap object
            System.out.println("   [Inside method] Array updated: " + Arrays.toString(numbers));
        }
    }

    /**
     * Reassigns the parameter variable to a new array.
     * The caller's reference variable will still point to the original array.
     */
    public static void attemptArrayReassignment(int[] numbers) {
        System.out.println("   [Inside method] Original pointer: " + Arrays.toString(numbers));
        numbers = new int[]{5, 6, 7}; // Pointing parameter to a new heap location
        System.out.println("   [Inside method] Reassigned pointer: " + Arrays.toString(numbers));
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Primitive Passing ===");
        int localScore = 50;
        System.out.println("Before call: " + localScore);
        attemptPrimitiveChange(localScore);
        System.out.println("After call: " + localScore); // Prints 50

        System.out.println("\n=== 2. Reference Content Modification ===");
        int[] localArray = {1, 2, 3};
        System.out.println("Before call: " + Arrays.toString(localArray));
        modifyArrayContent(localArray);
        System.out.println("After call: " + Arrays.toString(localArray)); // Prints [999, 2, 3]

        System.out.println("\n=== 3. Reference Reassignment ===");
        System.out.println("Before call: " + Arrays.toString(localArray));
        attemptArrayReassignment(localArray);
        System.out.println("After call: " + Arrays.toString(localArray)); // Prints [999, 2, 3] (not [5, 6, 7])
    }
}
```

---

## Summary
- **Parameters** are local variables in the method signature; **arguments** are the actual values passed during invocation.
- Java is strictly **pass-by-value**.
- For **primitives**, the method receives a copy of the raw data. The caller's variable is completely safe from edits.
- For **references**, the method receives a copy of the heap memory address. Modifying the object's properties or elements changes the shared heap data.
- **Reassigning** a reference parameter variable inside a method changes its local pointer only, leaving the caller's pointer unchanged.

---

## Additional Resources
- [Is Java Pass-by-Value or Pass-by-Reference? - Baeldung](https://www.baeldung.com/java-pass-by-value-or-pass-by-reference)
- [Arguments and Parameters Reference - Oracle Java SE Docs](https://docs.oracle.com/javase/tutorial/java/javaOO/arguments.html)
- [Java Parameter Passing Guide - GeeksforGeeks](https://www.geeksforgeeks.org/parameter-passing-in-java/)