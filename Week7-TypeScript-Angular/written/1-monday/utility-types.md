# TypeScript Utility Types Reference: A Comprehensive Guide

## Learning Objectives
- Map and transform existing type shapes using built-in utility types.
- Convert type properties using `Partial<T>`, `Required<T>`, and `Readonly<T>`.
- Extract subsets of properties using `Pick<T, K>` and `Omit<T, K>`.
- Map key-value definitions using `Record<K, V>`.
- Narrow type selections using `Exclude<T, U>` and `Extract<T, U>`.
- Retrieve return signatures using `ReturnType<T>`.
- Parse constructor parameter types using `Parameters<T>` and `ConstructorParameters<T>`.

---

## Why This Matters
As your frontend code scales, writing duplicate type interfaces for different variations of the same entity (e.g. creating one interface for a full `Todo` object, another for a partial `Todo` update payload, and another for a read-only `Todo` display) becomes a maintenance burden. 

If you manually duplicate these interfaces, updating the main model requires updating all of its variants, increasing the risk of code mismatch. TypeScript resolves this by providing **Utility Types** to dynamically transform existing type signatures, keeping your code DRY (Don't Repeat Yourself).

---

## The Concept

### 1. Property Modifiers

#### `Partial<T>`
Constructs a type with all properties of `T` set to optional (`?`).
- *Use Case:* Typings for form updates or HTTP PATCH payloads, where the user might only modify a subset of fields.

```typescript
interface UserProfile {
    id: number;
    email: string;
    bio: string;
}

// Equivalent to: { id?: number; email?: string; bio?: string; }
type PartialProfile = Partial<UserProfile>;
```

#### `Required<T>`
Constructs a type with all properties of `T` set to mandatory.
- *Use Case:* Ensuring all optional properties are defined after default values are applied.

```typescript
// Equivalent to: { id: number; email: string; bio: string; }
type CompleteProfile = Required<PartialProfile>;
```

#### `Readonly<T>`
Constructs a type with all properties of `T` set to read-only, preventing reassignment.
- *Use Case:* Freezing configuration settings or initial state values.

```typescript
// Equivalent to: { readonly id: number; readonly email: string; readonly bio: string; }
type FrozenProfile = Readonly<UserProfile>;
```

### 2. Property Subsets

#### `Pick<T, K>`
Constructs a type by picking a specific set of keys `K` from type `T`.
- *Use Case:* Extracting a subset of properties for display components.

```typescript
// Equivalent to: { email: string; }
type EmailOnly = Pick<UserProfile, "email">;
```

#### `Omit<T, K>`
Constructs a type by removing a specific set of keys `K` from type `T`.
- *Use Case:* Removing sensitive database fields (like passwords or auto-generated IDs) before sending data to an API.

```typescript
// Equivalent to: { email: string; bio: string; }
type ProfileWithoutId = Omit<UserProfile, "id">;
```

### 3. Key-Value Maps

#### `Record<K, V>`
Constructs an object type whose keys are `K` and whose values are `V`.
- *Use Case:* Typing lookup dictionaries, routing tables, or configuration maps.

```typescript
type Role = "ADMIN" | "USER" | "GUEST";

// Equivalent to: { ADMIN: string[]; USER: string[]; GUEST: string[]; }
const rolePermissions: Record<Role, string[]> = {
    ADMIN: ["read", "write", "delete"],
    USER: ["read", "write"],
    GUEST: ["read"]
};
```

### 4. Union Selection

#### `Exclude<T, U>`
Excludes types from union `T` that are assignable to `U`.
- *Use Case:* Filtering out specific values from a union type.

```typescript
type Status = "ACTIVE" | "INACTIVE" | "DELETED";

// Equivalent to: "ACTIVE" | "INACTIVE"
type LiveStatus = Exclude<Status, "DELETED">;
```

#### `Extract<T, U>`
Extracts types from union `T` that are assignable to `U`.
- *Use Case:* Retrieving the common subsets of two union types.

```typescript
// Equivalent to: "INACTIVE"
type CommonStatus = Extract<Status, "INACTIVE" | "PENDING">;
```

#### `NonNullable<T>`
Excludes `null` and `undefined` from type `T`.

```typescript
type RawData = string | null | undefined;

// Equivalent to: string
type CleanData = NonNullable<RawData>;
```

### 5. Function Return Typing

#### `ReturnType<T>`
Extracts the return type of a function type `T`.
- *Use Case:* Typing variables that hold values returned by third-party library functions.

```typescript
function buildConfig() {
    return { host: "localhost", port: 8080 };
}

// Equivalent to: { host: string; port: number; }
type AppConfig = ReturnType<typeof buildConfig>;
```

---

## Code Examples and Walkthroughs

### 1. Form Payloads Transformations (DTOs)
Let's see how utility types prevent duplication when modeling database records and API requests:

```typescript
interface Todo {
    id: number;
    title: string;
    description: string;
    completed: boolean;
    createdDate: Date;
    lastModifiedBy: string;
}

// 1. Omit system-generated fields for creation payloads
type CreateTodoDto = Omit<Todo, "id" | "createdDate" | "lastModifiedBy">;

// 2. Make all fields optional for update (PATCH) operations
type UpdateTodoDto = Partial<CreateTodoDto>;

// 3. Pick specific fields for a summary display card
type TodoSummary = Pick<Todo, "title" | "completed">;

class TodoService {
    private mockDb: Todo[] = [];

    // Accepts CreateTodoDto, dynamically adding system fields
    public saveTodo(dto: CreateTodoDto): Todo {
        const newTodo: Todo = {
            ...dto,
            id: Date.now(),
            createdDate: new Date(),
            lastModifiedBy: "System_Process"
        };
        this.mockDb.push(newTodo);
        return newTodo;
    }

    // Accepts UpdateTodoDto to perform partial updates
    public updateTodo(id: number, patch: UpdateTodoDto): Todo | undefined {
        const todo = this.mockDb.find(item => item.id === id);
        if (todo) {
            Object.assign(todo, patch);
        }
        return todo;
    }
}
```

### 2. Typing Dynamic Routing Configurations
Let's use `Record` and `Pick` to type an application routing configuration:

```typescript
interface PageComponent {
    title: string;
    template: string;
    controller: Function;
}

type AllowedRoutes = "/home" | "/dashboard" | "/settings";

// Enforce that only allowed routes are keys, and their values are PageComponents
const routeConfig: Record<AllowedRoutes, PageComponent> = {
    "/home": { title: "Welcome", template: "<h1>Home</h1>", controller: () => {} },
    "/dashboard": { title: "Admin", template: "<h1>Dashboard</h1>", controller: () => {} },
    "/settings": { title: "Settings", template: "<h1>Settings</h1>", controller: () => {} }
};
```

---

## Summary
- **`Partial<T>`** makes all properties optional, and **`Omit<T, K>`** removes specific keys, making them ideal for modeling API requests (DTOs).
- Use **`Pick<T, K>`** to extract a subset of properties for UI display components.
- Use **`Record<K, V>`** to type key-value dictionaries and lookup maps.
- Use **`Exclude<T, U>`** and **`Extract<T, U>`** to filter or retrieve subsets of union types.
- **`ReturnType<T>`** extracts the return type of a function signature.

---

## Additional Resources
- [TypeScript Docs: Utility Types Reference](https://www.typescriptlang.org/docs/handbook/utility-types.html)
- [TypeScript Handbook: Mapped Types](https://www.typescriptlang.org/docs/handbook/2/mapped-types.html)
- [TypeScript Deep Dive: Utility Types Section](https://basarat.gitbook.io/typescript/type-system/utility-types)
