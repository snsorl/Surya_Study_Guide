# Exercise: Visibility Modifier Experiments

## Objective
Observe and document how Java access visibility modifiers (`public`, `protected`, `private`, and default package-private) enforce encapsulation barriers across packages.

---

## Prerequisites
- Completed Tuesday's reading materials on visibility modifiers and package boundaries.

---

## Step-by-Step Instructions

### Step 1: Create Package A and the Target Class
1.  Open your IntelliJ project.
2.  Inside the `src` folder, create a new package structure named **`com.cohort.alpha`**.
3.  Inside `com.cohort.alpha`, create a Java class named `AccessHolder`.
4.  Add four methods to `AccessHolder` with different access modifiers:
    ```java
    package com.cohort.alpha;

    public class AccessHolder {
        public void publicMethod() {
            System.out.println("Public accessed.");
        }

        protected void protectedMethod() {
            System.out.println("Protected accessed.");
        }

        void defaultMethod() {
            System.out.println("Default (package-private) accessed.");
        }

        private void privateMethod() {
            System.out.println("Private accessed.");
        }
        
        public void testInternalAccess() {
            // Step 1.1: Verify all 4 can be accessed from inside the class
            privateMethod(); // Works
        }
    }
    ```

---

### Step 2: Test Access from Within the Same Package
1.  Create a second class named `PackageNeighbor` inside the same package (`com.cohort.alpha`).
2.  Add a `main` method.
3.  Instantiate `AccessHolder` and attempt to call all 4 methods:
    ```java
    AccessHolder holder = new AccessHolder();
    holder.publicMethod();
    holder.protectedMethod();
    holder.defaultMethod();
    // holder.privateMethod(); // Can you compile this?
    ```
4.  Comment out the line that fails and write a code comment explaining why it failed.

---

### Step 3: Test Access from a Different Package
1.  Inside the `src` folder, create a second package named **`com.cohort.beta`**.
2.  Inside `com.cohort.beta`, create a class named `PackageOutsider`.
3.  Add a `main` method.
4.  Import `com.cohort.alpha.AccessHolder`.
5.  Instantiate `AccessHolder` and attempt to call all 4 methods:
    *   Identify which methods compile and which methods fail with compiler errors.
    *   Document the compiler errors (e.g. `method has protected access in AccessHolder`) in code comments next to each commented-out line.

---

## Definition of Done
- Two packages (`com.cohort.alpha` and `com.cohort.beta`) are declared.
- The `AccessHolder` class defines 4 methods matching the access modifiers.
- The `PackageNeighbor` class successfully invokes the public, protected, and default methods but fails to compile private method calls.
- The `PackageOutsider` class compiles successfully only when calling the public method, with comments documenting the compiler error logs for protected, default, and private method attempts.
