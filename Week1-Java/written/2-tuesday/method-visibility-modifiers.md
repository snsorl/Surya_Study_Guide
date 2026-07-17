# Method Visibility Modifiers

## Learning Objectives
- Identify and compare Java's four visibility levels: `public`, `protected`, package-private (default), and `private`.
- Apply access modifiers to methods to enforce data encapsulation and information hiding.
- Describe package boundaries and explain the limitations of default access.
- Select the appropriate visibility level to secure internal business logic.

---

## Why This Matters
When building enterprise software, security and code stability are top priorities. If every method in your application is globally visible, external code can run internal routines incorrectly. 

For example, in a banking application, the `BankAccount` class must expose a public method for `withdraw()`. However, the internal methods like `verifyAtmSignature()`, `updateAccountDatabase()`, and `decrementBalance()` must remain hidden from outside classes. If an external client class could invoke `decrementBalance()` directly, they could bypass all authentication checks. Access modifiers provide the secure boundaries (encapsulation) that shield your core logic from unauthorized modifications.

---

## The Concept

### The Four Access Levels
Java has four access levels determined by three keywords:

```
Private <------- Default (Package-Private) <------- Protected <------- Public
(Most Restrictive)                                                 (Least Restrictive)
```

#### 1. Public (`public`)
Methods declared with the `public` modifier are visible to **all classes** in the entire application, regardless of what package they reside in (provided the class package is imported).
*   *Use Case*: The primary interface methods (APIs) of your class.

#### 2. Protected (`protected`)
Methods declared with the `protected` modifier are visible to all classes in the **same package**, plus any **subclasses** of the parent class, even if those subclasses are located in different packages.
*   *Use Case*: Base framework methods that should be customized by children.

#### 3. Default (Package-Private - No Keyword)
If you do not specify any access modifier keyword, Java automatically assigns the default visibility. The method is visible **only to classes in the same package**. It is invisible to any class outside that package, even subclasses.
*   *Use Case*: Package-level utilities or helper tools that should not leak out of the module.

#### 4. Private (`private`)
Methods declared with the `private` modifier are visible **only within the class where they are declared**. They cannot be seen or called by any other class, even if it is in the same package.
*   *Use Case*: Internal helper logic, validation calculations, and variables.

---

### Visibility Modifier Matrix

| Modifier | Same Class | Same Package | Subclass (Diff Package) | World (Diff Package) |
| :--- | :---: | :---: | :---: | :---: |
| **`public`** | Yes | Yes | Yes | Yes |
| **`protected`** | Yes | Yes | Yes | No |
| *(default)* | Yes | Yes | No | No |
| **`private`** | Yes | No | No | No |

---

## Code Example
Let's see access levels illustrated in a mockup system where a security console manages login states:

```java
// Save this file under: src/com/infosys/security/SecurityGateway.java
package com.infosys.security;

public class SecurityGateway {

    // 1. Public: Accessible from anywhere
    public void executeLoginFlow(String username, String password) {
        System.out.println("Initiating secure login flow...");
        
        // Calling private and package-private helper methods inside the same class is safe
        if (verifyCredentials(username, password)) {
            logActivity("User " + username + " logged in successfully.");
            System.out.println("Access GRANTED.");
        } else {
            System.out.println("Access DENIED.");
        }
    }

    // 2. Default (Package-Private): Accessible only within com.infosys.security package
    void logActivity(String event) {
        System.out.println("[AUDIT LOG] " + event);
    }

    // 3. Private: Accessible ONLY within SecurityGateway class
    private boolean verifyCredentials(String user, String pass) {
        // Simulating simple credentials validation check
        return "admin".equals(user) && "p@ss123".equals(pass);
    }
}
```

Now, let's see what happens if we attempt to invoke these methods from outside the package boundaries:

```java
// Save this file under: src/com/infosys/client/AppRunner.java
package com.infosys.client;

import com.infosys.security.SecurityGateway; // Import class from external package

public class AppRunner {
    public static void main(String[] args) {
        SecurityGateway gateway = new SecurityGateway();

        // A. Compiles fine: public method is globally accessible
        gateway.executeLoginFlow("admin", "p@ss123");

        // B. COMPILER ERROR: logActivity() has default access and cannot be accessed from outside package
        // gateway.logActivity("Manual audit trigger"); 

        // C. COMPILER ERROR: verifyCredentials() has private access in SecurityGateway
        // boolean isMatch = gateway.verifyCredentials("admin", "p@ss123"); 
    }
}
```

---

## Summary
- Access modifiers enforce **information hiding** by restricting who can call class methods.
- **`private`** methods are only callable inside the same class file.
- **Default access** (no keyword) restricts visibility to classes inside the **same package**.
- **`protected`** allows package access and subclass access in other packages.
- **`public`** methods are globally accessible across all packages.
- A good design rule of thumb is: **Default to `private`**, and only open visibility to `public` when external access is explicitly required.

---

## Additional Resources
- [Access Control Guide - Oracle Java SE Tutorials](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)
- [Java Access Modifiers - GeeksforGeeks](https://www.geeksforgeeks.org/access-modifiers-java/)
- [Understanding Encapsulation and Access Boundaries - Baeldung](https://www.baeldung.com/java-access-modifiers)