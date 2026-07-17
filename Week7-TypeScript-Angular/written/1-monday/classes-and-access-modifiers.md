# Access Modifiers and Parameter Shorthand: A Comprehensive Guide

## Learning Objectives
- Restrict property and method visibility using access modifiers (`public`, `private`, `protected`, `readonly`).
- Explain the difference between TypeScript's compile-time access modifiers and JavaScript's runtime-enforced private fields (`#`).
- Simplify constructor definitions using the Parameter Property Shorthand.
- Trace how access modifiers are transpiled into standard JavaScript code.
- Prevent mutations on class variables using the `readonly` modifier.

---

## Why This Matters
Encapsulation is a core principle of object-oriented programming. In enterprise applications, components and services should only expose properties and methods that are intended for public use, hiding internal implementation details.

In standard JavaScript, all class properties were historically public, making internal state vulnerable to unintended modifications from outside code. TypeScript introduces access modifiers (`public`, `private`, `protected`, and `readonly`) to enforce strict encapsulation at compile-time. Understanding how these compile-time rules differ from JavaScript's native runtime private fields (`#`) is key to writing secure, maintainable code.

---

## The Concept

### 1. The Access Modifier Hierarchy
TypeScript provides three visibility modifiers and one immutability modifier:

```
VISIBILITY HIERARCHY
┌─────────────┬────────────────────────────────────────────────────────┐
│ Modifier    │ Access Scope                                           │
├─────────────┼────────────────────────────────────────────────────────┤
│ public      │ Accessible from anywhere (default value).              │
│ protected   │ Accessible only within the class and its subclasses.   │
│ private     │ Accessible only within the class where it is declared.  │
│ readonly    │ Can only be assigned value inside the constructor.      │
└─────────────┴────────────────────────────────────────────────────────┘
```

#### The `public` Modifier (Default)
If you do not specify a modifier, properties and methods are public by default, meaning they can be read or modified from anywhere in your codebase:
```typescript
class Task {
    title: string; // Implicitly public
}
```

#### The `private` Modifier
Properties marked as `private` can only be accessed within the class itself. External classes or subclasses cannot read or write to them.
```typescript
class AccountService {
    private apiKey: string = "SECRET-KEY";

    public fetchAccountData() {
        // Allowed inside the class
        return this.apiKey;
    }
}

const service = new AccountService();
// console.log(service.apiKey); // Compile Error: Property 'apiKey' is private.
```

#### The `protected` Modifier
`protected` properties behave like `private` properties, but remain accessible within subclasses that extend the parent class:
```typescript
class Controller {
    protected endpoint: string = "/api/v1";
}

class UserController extends Controller {
    public fetchUsers() {
        console.log(`Calling path: ${this.endpoint}/users`); // Allowed
    }
}
```

#### The `readonly` Modifier
The `readonly` modifier prevents properties from being reassigned after they have been initialized (either directly in their declaration or inside the constructor):
```typescript
class User {
    readonly id: number;

    constructor(id: number) {
        this.id = id; // Allowed inside the constructor
    }

    updateId() {
        // this.id = 42; // Compile Error: Cannot assign to 'id' because it is a read-only property.
    }
}
```

### 2. Compile-Time vs. Runtime Privacy
- **TypeScript `private` (Compile-Time)**: This is a compile-time check. The TypeScript compiler verifies that you do not access the property from outside the class and throws an error if you do. However, once transpiled into standard JavaScript, the `private` keyword is removed, and the property becomes a regular public property at runtime.
- **JavaScript `#` (Runtime)**: This is a runtime-enforced private field introduced in ES2022. It uses the `#` prefix (e.g. `#balance`). This privacy is enforced directly by the JavaScript runtime (browser engine). Attempting to access it outside the class throws a runtime syntax error.

```typescript
class BalanceTracker {
    private balanceA: number = 0; // Compile-time check (removed in output JS)
    #balanceB: number = 0;        // Runtime check (enforced by browser engine)
}
```

### 3. Parameter Property Shorthand
Normally, declaring class properties requires a three-step process: declaring the field, writing constructor parameters, and assigning values.
TypeScript provides a shorthand that lets you declare, initialize, and bind properties directly in the constructor signature by prefixing parameters with an access modifier:

```typescript
// Traditional Way:
class UserServiceOld {
    private http: HttpClient;
    public apiPath: string;

    constructor(http: HttpClient, apiPath: string) {
        this.http = http;
        this.apiPath = apiPath;
    }
}

// Shorthand Way:
class UserServiceNew {
    // Declares, initializes, and binds 'http' and 'apiPath' automatically
    constructor(private http: HttpClient, public apiPath: string) {}
}
```
This shorthand is widely used in Angular to inject dependencies into components and services.

---

## Code Examples and Walkthroughs

### 1. Access Modifiers in Inheritance Trees
```typescript
class Employee {
    constructor(
        public name: string,
        protected department: string,
        private salary: number
    ) {}

    public printSalarySlip(): void {
        // Private properties can only be accessed within the base class
        console.log(`Salary details for ${this.name}: $${this.salary}`);
    }
}

class Manager extends Employee {
    constructor(name: string, department: string, salary: number) {
        super(name, department, salary);
    }

    public changeManagerDepartment(newDept: string): void {
        this.department = newDept; // Allowed (protected)
        console.log(`Department updated to: ${this.department}`);
        
        // this.salary = 100000; // Compile Error: Property 'salary' is private and only accessible within class 'Employee'.
    }
}

// Verification usage:
const mgr = new Manager("Alice", "Engineering", 95000);
mgr.changeManagerDepartment("Product Operations");
mgr.printSalarySlip(); // Outputs salary successfully via public method
```

### 2. Runtime Privacy using ES2022 Private Fields
```typescript
class BankAccount {
    #balance: number = 0; // ES2022 private field

    constructor(initialDeposit: number) {
        this.#balance = initialDeposit;
    }

    public deposit(amount: number): void {
        this.#balance += amount;
        console.log(`Deposit successful. Current balance: $${this.#balance}`);
    }

    public getBalance(): number {
        return this.#balance;
    }
}

// Verification usage:
const account = new BankAccount(500);
account.deposit(200);
console.log("Current Balance:", account.getBalance()); // 700
// console.log(account.#balance); // Syntax/Compile Error: Property '#balance' is not accessible outside class.
```

---

## Summary
- **`public`** (default) properties are accessible from anywhere.
- **`private`** properties are accessible only within the class where they are declared.
- **`protected`** properties are accessible within the class and its subclasses.
- **`readonly`** properties cannot be reassigned after they have been initialized.
- TypeScript access modifiers are **compile-time checks only**; JavaScript **`#` private fields** are enforced by browser runtimes.
- Use **Parameter Property Shorthand** in constructors to declare and initialize instance properties in a single line.

---

## Additional Resources
- [TypeScript Docs: Parameter properties](https://www.typescriptlang.org/docs/handbook/2/classes.html#parameter-properties)
- [MDN Web Docs: Private class features](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes/Private_class_fields)
- [TypeScript Handbook: Readonly Modifier](https://www.typescriptlang.org/docs/handbook/2/classes.html#readonly)
