# Advanced Classes in TypeScript: A Comprehensive Guide

## Learning Objectives
- Design and implement abstract classes, detailing their architectural role.
- Configure abstract properties and methods in base definitions.
- Implement interfaces to enforce strict object shape contracts.
- Resolve inheritance hierarchy challenges in dynamic class definitions.
- Write generic class models that preserve type safety across operations.
- Contrast classes (runtime constructs) with type aliases and interfaces (compile-time boundaries).
- Detail class-to-class structural comparisons under structural subtyping rules.

---

## Why This Matters
For developers transitioning from Java, object-oriented concepts like inheritance, encapsulation, and polymorphism are second nature. However, JavaScript’s native prototype-based inheritance model lacks static type checking, private visibility enforcement, and contract compilation warnings. This makes large-scale applications prone to runtime failures. 

TypeScript bridges this gap by introducing enterprise-grade object-oriented constructs (abstract classes, interfaces, and generics) on top of standard JavaScript classes. Because Angular architectures are built entirely on TypeScript classes (from components to services and custom decorators), mastering these advanced class mechanisms is crucial for building robust, scalable full-stack applications.

---

## The Concept

### 1. The Anatomy of Modern Classes in TypeScript
A class in TypeScript compiles down to a JavaScript constructor function under the hood, but it introduces compile-time validation checking.
- **Fields**: Must be declared with their types at the class level before they can be assigned inside the constructor.
- **Constructor**: The initialization method invoked during instantiation with the `new` keyword.

```typescript
class DatabaseConfig {
    host: string;
    port: number;

    constructor(host: string, port: number) {
        this.host = host;
        this.port = port;
    }
}
```

### 2. Abstract Classes: Blueprints for Inheritance
An **abstract class** is a base class that cannot be instantiated directly. Instead, it defines common behavior and abstract signatures that subclasses must implement. It is declared using the `abstract` keyword.

- **Non-Abstract Methods**: Can include standard JavaScript logic, which is inherited by all subclasses.
- **Abstract Methods**: Contain only a signature (no code body). Any class extending the abstract class **must** provide the concrete implementation for these methods, or it will fail compilation.

```typescript
abstract class HttpController {
    // Shared helper method inherited by all controllers
    protected sendResponse(data: any): string {
        return JSON.stringify({ status: "success", data });
    }

    // Every concrete subclass must implement this method
    abstract handleRequest(request: any): void;
}
```

### 3. Implementing Interfaces
In TypeScript, you can force a class to conform to a specific type shape using the **`implements`** keyword.
- A class can implement multiple interfaces (separated by commas).
- If the class misses a property or method declared in the interface, the TypeScript compiler will throw a compilation error.

```typescript
interface Serializable {
    toJSON(): string;
}

interface Loggable {
    log(message: string): void;
}

class UserSession implements Serializable, Loggable {
    constructor(public id: string) {}

    toJSON(): string {
        return JSON.stringify({ userId: this.id });
    }

    log(message: string): void {
        console.log(`[Session ${this.id}] ${message}`);
    }
}
```

### 4. Generics in Classes
Generics allow a class to operate on dynamic data types while maintaining strict type-safety. Instead of using `any` (which disables type checking), we declare a type variable (like `<T>`) that is bound during instantiation.

- **Constraints (`extends`)**: You can restrict the types a generic class can accept by using the `extends` keyword in the generic parameter declaration.

```typescript
// Enforce that type T must contain an id property
interface Identifiable {
    id: number;
}

class CacheService<T extends Identifiable> {
    private cache = new Map<number, T>();

    public set(item: T): void {
        this.cache.set(item.id, item);
    }

    public get(id: number): T | undefined {
        return this.cache.get(id);
    }
}
```

### 5. Classes vs. Types and Interfaces
Understanding what exists at runtime versus what is stripped during compilation is key to debugging:
- **Classes**: Are **runtime constructs**. The TypeScript compiler transpiles classes into JavaScript constructor functions. They take up memory space in the final bundle and can be checked using the `instanceof` operator at runtime.
- **Interfaces / Type Aliases**: Are strictly **compile-time constructs**. They are completely stripped from the output JavaScript file. You cannot check them at runtime (e.g., `value instanceof MyInterface` will throw a runtime reference error).

---

## Code Examples and Walkthroughs

### 1. Concrete Abstract Class Hierarchy
Let's build a structured database driver system using abstract classes:

```typescript
abstract class DatabaseDriver {
    protected connectionString: string;

    constructor(connectionString: string) {
        this.connectionString = connectionString;
    }

    // Shared method inherited by database drivers
    public logConnection(): void {
        console.log(`Connection established to endpoint: ${this.connectionString}`);
    }

    // Abstract method signatures defining subclass contracts
    abstract connect(): boolean;
    abstract executeQuery(sql: string): any[];
}

class PostgresDriver extends DatabaseDriver {
    // Implement Postgres-specific connection logic
    connect(): boolean {
        console.log("Initializing PostgreSQL client pool...");
        this.logConnection();
        return true;
    }

    // Implement Postgres-specific query logic
    executeQuery(sql: string): any[] {
        console.log(`[Postgres] Executing query: ${sql}`);
        return [{ id: 1, name: "Database Row" }];
    }
}

// Verification usage:
const pgDriver = new PostgresDriver("postgresql://localhost:5432/mydb");
pgDriver.connect();
const results = pgDriver.executeQuery("SELECT * FROM users");
console.log("Postgres Query Results:", results);
```

### 2. Reusable Generic Data Structures
Let's build a generic repository class to manage application records:

```typescript
interface UserRecord {
    id: number;
    email: string;
    isActive: boolean;
}

interface ProductRecord {
    id: number;
    price: number;
    sku: string;
}

class Repository<T extends { id: number }> {
    private storage: T[] = [];

    public insert(record: T): void {
        this.storage.push(record);
        console.log(`Record inserted. Total records: ${this.storage.length}`);
    }

    public find(id: number): T | undefined {
        return this.storage.find(item => item.id === id);
    }

    public getAll(): T[] {
        return [...this.storage];
    }
}

// Verification usage:
const userRepo = new Repository<UserRecord>();
userRepo.insert({ id: 1, email: "john@example.com", isActive: true });
console.log("Found User:", userRepo.find(1)?.email);

const productRepo = new Repository<ProductRecord>();
productRepo.insert({ id: 99, price: 29.99, sku: "PROD-101" });
console.log("Found Product Price:", productRepo.find(99)?.price);
```

### 3. Understanding Structural Subtyping (Duck Typing)
TypeScript's type system is structural, not nominal. This means that if two classes have the same properties and methods, they are considered compatible, even if they do not inherit from the same base class:

```typescript
class Point2D {
    constructor(public x: number, public y: number) {}
}

class Location2D {
    constructor(public x: number, public y: number) {}
}

// Valid assignment under structural subtyping:
const coordinates: Point2D = new Location2D(10, 20); // Compiles successfully!
```

---

## Summary
- **Abstract classes** serve as base class blueprints, defining shared behavior and abstract signatures that subclasses must implement.
- Use the **`implements`** keyword to force classes to conform to interface contracts.
- **Generics (`<T>`)** enable reusable, type-safe class configurations, and can be restricted using **`extends`** constraints.
- **Classes** are compiled into JavaScript runtime constructors; **Interfaces and Types** are compile-time annotations that are stripped from the output.
- TypeScript uses **structural subtyping**, evaluating type compatibility based on the shape of properties and methods rather than nominal inheritance names.

---

## Additional Resources
- [TypeScript Docs: Classes handbook](https://www.typescriptlang.org/docs/handbook/2/classes.html)
- [TypeScript Handbook: Generic Classes](https://www.typescriptlang.org/docs/handbook/2/generics.html#generic-classes)
- [TypeScript Deep Dive: Classes Section](https://basarat.gitbook.io/typescript/future-javascript/classes)
