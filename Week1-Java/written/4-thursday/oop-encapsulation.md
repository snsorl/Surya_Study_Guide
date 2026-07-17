# OOP Encapsulation

## Learning Objectives
- Define Encapsulation and explain how it protects class data integrity.
- Implement data hiding by declaring fields `private` and exposing them via `public` accessors (getters/setters).
- Apply standard **JavaBean** naming conventions for instance fields.
- Configure properties as Read-Only or Write-Only.
- Write validation guard logic inside setter methods to prevent invalid object states.

---

## Why This Matters
If you declare class variables as `public`, any external class in your application can modify them directly without restrictions:

```java
BankAccount account = new BankAccount();
account.balance = -50000.00; // Data corruption! 
```
There is no database warning, no logic check, and no log entry generated. The system's data state is instantly corrupted. 

**Encapsulation** protects against this. Encapsulation is the practice of hiding the internal variables of a class (marking them `private`) and forcing all external classes to interact with the data through controlled public methods (getters and setters). Inside these methods, you can write validation code—like checking if a balance is positive or if an email address contains an "@" symbol. This guarantees that your object's internal state remains valid, secure, and accurate.

---

## The Concept

### Implementing Encapsulation
To fully encapsulate a class, follow these steps:
1.  Declare all instance variables (fields) as **`private`**.
2.  Provide a public **Getter** method to read the variable value.
3.  Provide a public **Setter** method to write/update the variable value.

```
                  [ EXTERNAL CLIENT CLASS ]
                              │
             Calls public setter: setEmail("bob@corp.com")
                              ▼
                [ ENCAPSULATED CLASS: User ]
             - Field: private String email;
             - Logic: Check if string has '@'
             - Result: State updated safely on Heap
```

---

### JavaBean Naming Conventions
To ensure compatibility with Java frameworks, accessors follow strict naming conventions:
*   **Variable**: `private String department;`
    *   **Getter**: `public String getDepartment() { return this.department; }`
    *   **Setter**: `public void setDepartment(String department) { this.department = department; }`
*   **Boolean Variable**: `private boolean active;`
    *   **Getter**: `public boolean isActive() { return this.active; }` // Note the "is" prefix
    *   **Setter**: `public void setActive(boolean active) { this.active = active; }`

---

### Read-Only and Write-Only States
Encapsulation allows you to control which fields can be modified or read by external classes:
*   **Read-Only Property**: Implement a getter method, but **do not write a setter method**. Once constructed, the field cannot be changed. (e.g. `accountNumber`).
*   **Write-Only Property**: Implement a setter method, but **do not write a getter method**. (e.g., `password`).

---

## Code Example: User Account Encapsulation
The following `UserAccount` class encapsulates account configurations, enforcing read-only variables, write-only credentials, and validation rules:

```java
class UserAccount {
    // 1. Private fields (Data Hiding)
    private final String accountNumber; // Read-Only (assigned once in constructor)
    private String email;               // Read-Write (with validation check)
    private String passwordHash;        // Write-Only (no getter provided)

    // Constructor initializes state
    public UserAccount(String accountNumber, String email, String password) {
        this.accountNumber = accountNumber;
        setEmail(email);       // Utilize setter for initial validation
        setPassword(password); // Utilize setter for initial validation
    }

    // 2. Read-Only Accessor (Getter only, no Setter)
    public String getAccountNumber() {
        return this.accountNumber;
    }

    // 3. Read-Write Accessor with validation checks
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        // Enforcing validation constraints: must not be null and must contain "@"
        if (email != null && email.contains("@")) {
            this.email = email.trim();
        } else {
            System.out.println("Warning: Invalid email format skipped: " + email);
        }
    }

    // 4. Write-Only Accessor (Setter only, no Getter)
    public void setPassword(String password) {
        // Enforcing security check: password must be at least 6 characters long
        if (password != null && password.length() >= 6) {
            this.passwordHash = "HASHED_" + password.hashCode(); // Simulating hashing
            System.out.println("[SECURITY] Password updated and encrypted.");
        } else {
            System.out.println("Error: Password must be at least 6 characters long.");
        }
    }
}

public class EncapsulationDemo {
    public static void main(String[] args) {
        System.out.println("--- Creating Encapsulated Account ---");
        UserAccount user = new UserAccount("ACC-909", "john@corp.com", "myPass123");

        System.out.println("\n--- 1. Testing Read-Only Property ---");
        System.out.println("Account Number: " + user.getAccountNumber());
        // user.accountNumber = "ACC-001"; // COMPILER ERROR: accountNumber has private access in UserAccount
        // (There is no setAccountNumber method, so it cannot be modified)

        System.out.println("\n--- 2. Testing Write-Only Property ---");
        user.setPassword("newSecurePass99");
        // System.out.println("Password: " + user.getPasswordHash()); 
        // COMPILER ERROR: getPasswordHash does not exist! The property is write-only.

        System.out.println("\n--- 3. Testing Setter Validation Guard ---");
        System.out.println("Current Email: " + user.getEmail());
        
        // Attempting an invalid update
        user.setEmail("bad_email_no_at_sign"); // Triggers warning log
        System.out.println("Email post-invalid update: " + user.getEmail()); // Remains "john@corp.com"

        // Attempting a valid update
        user.setEmail("john.doe@corp.com");
        System.out.println("Email post-valid update:   " + user.getEmail()); // Successfully changed
    }
}
```

---

## Summary
- **Encapsulation** bundles variables and methods within a class, shielding data from external corruption.
- Mark all class variables as **`private`** to restrict direct access.
- Expose data safely using **`public` getter and setter** methods.
- Follow **JavaBean conventions** (e.g. `getSalary()`, `isPremium()`).
- Omit setters to create **Read-Only** properties; omit getters to create **Write-Only** properties.
- Add **validation checks** inside setters to protect the data integrity of your objects.

---

## Additional Resources
- [Java Encapsulation Guide - W3Schools](https://www.w3schools.com/java/java_encapsulation.asp)
- [Encapsulation in Java - GeeksforGeeks](https://www.geeksforgeeks.org/encapsulation-in-java/)
- [JavaBeans Specification - Oracle Documentation](https://docs.oracle.com/javase/tutorial/javabeans/)