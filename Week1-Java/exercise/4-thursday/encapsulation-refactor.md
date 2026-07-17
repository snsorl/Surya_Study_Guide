# Exercise: Legacy Encapsulation Refactoring

## Objective
Refactor a legacy Java class containing unprotected public variables to utilize private fields, public JavaBean getters/setters, and value validation guard logic without breaking existing compile references.

---

## Prerequisites
- Completed Thursday's reading materials on Encapsulation, JavaBean standards, and data protection logic.

---

## The Legacy Code (Unsecured)
Below is a class currently active in a legacy HR program. Notice that variables are marked `public`, allowing external classes to assign corrupt inputs (like negative salaries or null names) directly.

```java
public class EmployeeProfile {
    public String employeeId;
    public String name;
    public double monthlySalary;

    public EmployeeProfile(String employeeId, String name, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.monthlySalary = salary;
    }
}
```

---

## Step-by-Step Instructions

### Step 1: Initialize Legacy Code
1.  In IntelliJ, create a class named `EmployeeProfile` inside your project's source folder.
2.  Paste the legacy code snippet.

---

### Step 2: Implement Data Protection Barriers
Refactor the class to enforce the following rules:
1.  **Mark fields private**: Change `public` access modifiers on `employeeId`, `name`, and `monthlySalary` to **`private`**.
2.  **Make `employeeId` Read-Only**: Write a public getter for `employeeId` but **do not write a setter**. The ID can only be set via the constructor.
3.  **Encapsulate `name`**: Write a getter and setter. Validate that the name input is not null and not empty before updating.
4.  **Encapsulate `monthlySalary`**: Write a getter and setter. Validate that the salary is greater than or equal to `0.0`. If a negative value is passed, print a console error and leave the salary variable unchanged.

---

### Step 3: Verify the Refactored Object
1.  Create a class named `HRApp` with a `main` method.
2.  Instantiate `EmployeeProfile` and verify that calling getters works.
3.  Test validation guards:
    *   Attempt to set salary to `-5000.00` via the setter. Verify the system blocks the update and keeps the original value.
    *   Attempt to set name to `null`. Verify the update is blocked.

---

## Definition of Done
- The `EmployeeProfile` fields are declared `private`.
- The class is refactored to use standard JavaBean getters and setters.
- The `employeeId` is configured as a Read-Only property.
- Validation logic inside setters successfully protects fields from null names or negative salaries.
- `HRApp` compiles and executes, demonstrating that invalid modifications are safely rejected at runtime.
