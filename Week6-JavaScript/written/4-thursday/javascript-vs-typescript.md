# JavaScript vs. TypeScript: Comparison Reference

## Learning Objectives
- Compare JavaScript and TypeScript side-by-side.
- Identify compile-time type-safety features.
- Evaluate the overhead of TypeScript compilation.
- Choose between JavaScript and TypeScript based on project scale, team expertise, and tooling constraints.

---

## Why This Matters
For small frontend scripts, JavaScript is lightweight and runs without any build compilation setup. However, as web projects grow to hundreds of files, dynamic type resolution becomes a maintenance bottleneck. This guide compares JavaScript and TypeScript side-by-side, helping you make informed architectural decisions on when to choose TypeScript for enterprise application frontends.

---

## The Concept

### 1. Side-by-Side Comparison

| Feature | JavaScript | TypeScript |
|---|---|---|
| **Typing Model** | Dynamic (resolved at runtime) | Static (declared and checked at compile-time) |
| **Interfaces** | Not supported (uses duck typing) | Supported (declares strict object shapes) |
| **Access Modifiers** | ES2022 private fields (`#`) | Standard modifiers (`public`, `private`, `protected`) |
| **Error Detection** | Runtime (during execution in browser) | Compile-time (during compilation check) |
| **Decorators** | Experimental | Supported (widely used in Angular/NestJS) |
| **Tooling Setup** | Zero configuration needed | Requires compiler installation and `tsconfig.json` |
| **Output Target** | Runs natively in browser/Node | Must be compiled to JS before execution |

### 2. Core Language Features in Detail
- **Static Typing**: TypeScript validates that types match declarations. This provides auto-complete (intellisense) inside IDEs.
- **Interfaces**: TypeScript interfaces declare class and object property contracts that must be satisfied. They do not generate any code in the compiled JS output.
- **Access Modifiers**: In Java, we protect class properties using `private`. TypeScript provides compile-time access modifiers (`public`, `private`, `protected`) to prevent access to internal properties.

### 3. When to Choose TypeScript
Choose **TypeScript** when:
- Building large-scale applications with multiple developers.
- Implementing complex business domains with strict data schemas.
- Using modern frameworks like Angular or NestJS which require TypeScript.
- The project calls for safe code refactoring.

Choose **JavaScript** when:
- Creating small scripts, rapid prototypes, or simple utilities.
- The project lacks a compilation pipeline setup.

---

## Code Example

Below is a comparison showing how class access control is declared:

### TypeScript Class
```typescript
class Account {
    private balance: number; // Access modifier
    public accountNumber: string;

    constructor(accountNumber: string, initialBalance: number) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public getBalance(): number {
        return this.balance;
    }
}

const myAccount = new Account("12345", 500);
// myAccount.balance = 1000; 
// Type Error: Property 'balance' is private and only accessible within class 'Account'.
```

### Transpiled JavaScript Class
```javascript
class Account {
    constructor(accountNumber, initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance; // Note: 'private' modifier is stripped out!
    }
    getBalance() {
        return this.balance;
    }
}
```
*Note:* The compiled JavaScript class does not have runtime enforcement of the `private` variable (unless ES2022 `#` syntax is used). Access modifiers are compile-time safety checks.

---

## Summary
- TypeScript adds **static typing, interfaces, and compile-time modifiers** to JavaScript.
- Type checks are run during compilation and **do not exist at runtime**.
- Choose TypeScript for **large-scale enterprise applications** to ensure safety, and choose JavaScript for lightweight, compilation-free scripting.

---

## Additional Resources
- [TypeScript Docs: TypeScript for JavaScript Programmers](https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes.html)
- [Microsoft Learn: Introduction to TypeScript](https://learn.microsoft.com/en-us/training/modules/typescript-get-started/)
