# Packages and Imports in Java

## Learning Objectives
- Define the purpose of packages as namespaces and directory structures.
- Write package declarations that align with Java naming conventions.
- Map package paths to their physical file directories.
- Import external classes using both single-type imports and wildcard imports.
- Resolve naming collisions using Fully Qualified Class Names (FQN).
- Identify which packages (like `java.lang`) are imported automatically.

---

## Why This Matters
If you were writing a simple application with only one or two files, organization wouldn't matter. But enterprise systems contain thousands of Java classes. Without organization, you would quickly run into two major problems:
1. **Name Collisions**: You might want to create a class named `User`, but a library you imported already has a class named `User`.
2. **Unmaintainable Code**: Finding a specific file in a folder containing thousands of scripts would be impossible.

**Packages** solve both problems by acting as namespaces (like folders on your hard drive) that keep your code clean, modular, and organized. Imports allow you to easily link these files together.

---

## The Concept

### 1. What is a Package?
A package in Java is a grouping of related classes, interfaces, and sub-packages. Conceptually, you can think of a package as a folder in a computer directory tree.

#### Naming Conventions (The Reverse Domain Standard)
To ensure that package names are globally unique, the industry standard is to use the **reverse of your company's internet domain name** as the prefix. Package names are always written in **all lowercase**:

*   If your company domain is `infosys.com` and you are working on the billing module of a logistics app, your package name would be:
    `package com.infosys.logistics.billing;`

#### The `package` Statement
To place a Java class inside a package, you must include a `package` statement at the **very top** of your source file. It must be the first line of code (ignoring comments and blank lines):

```java
// File: src/com/infosys/utils/MathHelper.java
package com.infosys.utils;

public class MathHelper {
    // Code...
}
```

#### Directory Mapping Rules
Java compilers enforce a strict rule: **the physical directory path of the source file must match the package structure exactly**.
*   Class name: `com.infosys.utils.MathHelper`
*   Physical directory on disk: `src/com/infosys/utils/MathHelper.java` (using forward or backward slashes depending on the OS).

---

### 2. Imports
If you want to use a class that resides in a different package, you have to refer to it. You can do this in two ways:

#### A. Fully Qualified Name (FQN)
You can type out the complete package name path every time you reference the class:
```java
com.infosys.utils.MathHelper helper = new com.infosys.utils.MathHelper();
```
*Pitfall*: This makes your code long, cluttered, and difficult to read.

#### B. The `import` Statement
To avoid typing the FQN repeatedly, you place an `import` statement below the `package` declaration but above the `class` declaration:

```java
package com.infosys.billing;

import com.infosys.utils.MathHelper; // Single-type import

public class Invoice {
    public void process() {
        MathHelper helper = new MathHelper(); // Clean reference
    }
}
```

#### Wildcard Imports (`*`)
You can import all classes within a specific package using an asterisk wildcard:
```java
import java.util.*; // Imports ArrayList, Scanner, Arrays, etc.
```
> [!WARNING]
> Wildcard imports do *not* import sub-packages (e.g. `import java.util.*` does not import classes inside `java.util.concurrent`). Additionally, they can cause **naming collisions**.

---

### Naming Collisions: How to Resolve Them
If you import two packages that contain classes with the exact same name, the compiler will get confused.

For example, both `java.util` and `java.sql` contain a class named `Date`. If you write:
```java
import java.util.*;
import java.sql.*;

public class Demo {
    Date today; // COMPILER ERROR: Reference to 'Date' is ambiguous
}
```

To resolve this conflict, you must bypass the import statement for at least one of the classes and write out its **Fully Qualified Name** directly in the code:
```java
java.util.Date utilDate = new java.util.Date(); // Uses utility date
java.sql.Date sqlDate = new java.sql.Date(timestamp); // Uses SQL database date
```

---

### Automatic Imports (`java.lang`)
You do not need to write imports for every class you use. Java automatically imports the **`java.lang`** package into every source file. This is why you can use classes like `System`, `String`, `Math`, and standard Exceptions without declaring imports.

---

## Code Example
Here is a structured source file demonstrating package organization, standard imports, and resolving a name collision:

```java
package com.infosys.practice; // 1. Package Declaration must be first

// 2. Imports (below package, above class)
import java.util.Arrays; 
import java.util.Scanner; 

public class PackageDemo {
    public static void main(String[] args) {
        // Using imported Scanner class
        Scanner scanner = new Scanner(System.in);
        
        // Using automatically imported java.lang.Math class (no import required)
        double squareRoot = Math.sqrt(25.0);
        System.out.println("Square Root: " + squareRoot);

        // 3. Resolving a Date name collision using Fully Qualified Names
        java.util.Date currentDate = new java.util.Date();
        System.out.println("Current Date: " + currentDate);
    }
}
```

---

## Summary
- A **package** is a grouping of classes that acts as a namespace and maps directly to the physical directory path.
- Package names use **reversed domain names** in all lowercase (e.g. `package com.company.project`).
- **`import` statements** allow referencing classes from other packages without using long qualified names.
- **Wildcard imports (`*`)** import all classes in a package but can trigger naming conflicts.
- **Fully Qualified Names (FQN)** are required to resolve classes that have identical names across different packages (e.g., `java.util.Date` vs. `java.sql.Date`).
- The **`java.lang`** package is automatically imported by the compiler.

---

## Additional Resources
- [Java Packages and Imports - Oracle Docs](https://docs.oracle.com/javase/tutorial/java/package/index.html)
- [Naming Packages - Java Conventions](https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html)
- [Understanding Packages and Imports in Java - Baeldung](https://www.baeldung.com/java-packages)
