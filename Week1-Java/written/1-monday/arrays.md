# Arrays in Java

## Learning Objectives
- Define what an array is and explain its fixed-size, contiguous memory nature.
- Declare, initialize, and modify both single-dimensional and multi-dimensional arrays.
- Access elements using zero-based indexing and determine array size using the `.length` property.
- Identify and prevent the common runtime crash: `ArrayIndexOutOfBoundsException`.
- Visualize array storage layouts in Stack and Heap memory.

---

## Why This Matters
Imagine you are building a student grading system for a class of 30 trainees. If you had to create a separate variable for each student's score (`score1`, `score2`, ..., `score30`), your code would be incredibly repetitive, difficult to read, and impossible to loop through. 

**Arrays** solve this problem. An array allows you to bundle multiple elements of the same data type under a single variable name. Because they are stored contiguously in memory, arrays are incredibly fast for lookup operations. Mastering arrays is the first step in learning how to structure, store, and process lists of data in Java.

---

## The Concept

### Defining an Array
An array is a container object that holds a **fixed number** of values of a **single data type**.

Key characteristics of Java arrays:
1. **Fixed Size**: Once an array is created, its size (length) cannot be changed. If you need a larger container, you must create a new array and copy the elements over.
2. **Homogeneous**: All elements in the array must be of the exact same data type (e.g., all `int`, all `double`, or all `String`).
3. **Index-Based**: Elements are accessed via their index. Java uses **zero-based indexing**, meaning the first element is at index `0`, the second at index `1`, and the last element is at index `length - 1`.

---

### Allocating and Initializing Arrays
Because arrays are reference types, they reside on the Heap, and we must allocate them using the `new` keyword.

#### 1. Declaration with Default Initialization
You specify the array size, and Java automatically fills it with default values depending on the data type (integers get `0`, floating-points get `0.0`, booleans get `false`, references get `null`):
```java
// Declares an array reference on the Stack and allocates space for 5 ints on the Heap
int[] scores = new int[5]; // Elements initialized to: [0, 0, 0, 0, 0]
```

#### 2. Literal Inline Initialization
If you already know the elements, you can initialize them directly:
```java
// Allocates an array of size 3 with specific values
int[] scores = { 90, 85, 95 }; 
```

---

### Accessing and Modifying Elements
You use square brackets `[]` alongside the index to retrieve or write values:
```java
int[] ages = new int[3];

ages[0] = 21; // Set first element
ages[1] = 30; // Set second element

System.out.println(ages[0]); // Prints 21
System.out.println(ages[2]); // Prints 0 (default value)
```

---

### Determining Array Size: The `.length` Property
You can find the number of elements in an array using its `.length` property. Note that `length` is a read-only variable property on arrays, **not** a method (do not append parentheses `()`):
```java
int[] items = { 10, 20, 30, 40 };
System.out.println(items.length); // Prints 4
```

---

### The `ArrayIndexOutOfBoundsException`
Since arrays have a fixed boundaries, attempting to access an index that does not exist (like a negative index or an index `>= length`) causes the JVM to throw a runtime error called an **`ArrayIndexOutOfBoundsException`**:

```java
int[] ratings = { 5, 4, 5 };
int rating = ratings[3]; // CRASH! Ratings only has indexes 0, 1, and 2.
```

To prevent this crash, make sure your index checks fall strictly between `0` and `length - 1`:
```java
int targetIndex = 3;
if (targetIndex >= 0 && targetIndex < ratings.length) {
    System.out.println(ratings[targetIndex]);
} else {
    System.out.println("Index is invalid.");
}
```

---

### Multi-Dimensional Arrays (Arrays of Arrays)
Java does not have true multi-dimensional arrays. Instead, it supports "arrays of arrays". The most common form is a **2D Array**, which behaves like a table with rows and columns.

#### 1. Declaration and Allocation
```java
// Allocates a 2-row by 3-column table
int[][] grid = new int[2][3]; 
```

#### 2. Inline Initialization
```java
int[][] matrix = {
    {1, 2, 3}, // Row 0
    {4, 5, 6}  // Row 1
};

// Accessing the number 5 (Row 1, Column 1)
System.out.println(matrix[1][1]); // Prints 5
```

---

## Code Example
Let's see 1D and 2D arrays, element access, and defensive boundary checking inside a runnable Java program:

```java
public class ArraysDemo {
    public static void main(String[] args) {
        // 1. Single-Dimensional Array
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        
        System.out.println("Total weekdays: " + daysOfWeek.length);
        System.out.println("First day: " + daysOfWeek[0]);
        System.out.println("Last day: " + daysOfWeek[daysOfWeek.length - 1]);

        // Modifying an element
        daysOfWeek[4] = "TGIF!";
        System.out.println("Updated Friday: " + daysOfWeek[4]);

        // 2. Multi-Dimensional Array (2D Array)
        int[][] multiplicationTable = {
            {1, 2, 3},
            {2, 4, 6},
            {3, 6, 9}
        };
        
        // Print matrix center cell
        System.out.println("Matrix center value: " + multiplicationTable[1][1]); // Row 1, Col 1

        // 3. Triggering/Avoiding Out of Bounds
        int indexToAccess = 5;
        if (indexToAccess >= 0 && indexToAccess < daysOfWeek.length) {
            System.out.println(daysOfWeek[indexToAccess]);
        } else {
            System.out.println("Error: Index " + indexToAccess + " is out of bounds for array length " + daysOfWeek.length);
        }
    }
}
```

---

## Summary
- An array is a fixed-size, homogeneous container object stored on the Heap.
- Java arrays use **zero-based indexing** (ranges from `0` to `length - 1`).
- The **`.length`** property returns the total capacity of the array.
- Attempting to access an index out of the array's boundaries throws an **`ArrayIndexOutOfBoundsException`**.
- **2D Arrays** are structured as arrays of arrays, useful for representing grids, coordinates, or tables.

---

## Additional Resources
- [Java Arrays Tutorial - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html)
- [Single and Multidimensional Arrays in Java - Baeldung](https://www.baeldung.com/java-arrays)
- [Java Arrays - W3Schools](https://www.w3schools.com/java/java_arrays.asp)
