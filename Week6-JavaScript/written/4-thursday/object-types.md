# Object Types in TypeScript

## Learning Objectives
- Define object shapes using inline annotations.
- Declare optional properties using the `?` modifier.
- Enforce immutable fields using the `readonly` modifier.
- Build dynamic key-value maps using Index Signatures and the `Record<Keys, Type>` utility type.

---

## Why This Matters
JavaScript objects are dynamic bag structures where you can add, remove, or modify properties on the fly. In large-scale systems, this flexibility is a common source of bugs: if a function expects a property that is missing or misspelled, it crashes. In TypeScript, we declare object shapes explicitly, allowing the compiler to validate that objects contain expected properties and types.

---

## The Concept

### 1. Inline Object Annotations
You can define an object's shape inline by listing its properties and types:

```typescript
let user: { id: number; username: string };
user = { id: 101, username: "admin" }; // Matches shape
```

### 2. Optional Properties (`?`)
If a property is optional (e.g. a middle name or a discount code), append a question mark `?` after the property name:

```typescript
let product: { id: number; name: string; discountCode?: string };

product = { id: 1, name: "Soap" }; // Valid (discountCode is optional)
```

### 3. Readonly Properties
Annotate a property with `readonly` to prevent its value from being changed after the object is initialized:

```typescript
let customer: { readonly id: number; name: string };
customer = { id: 101, name: "John" };
// customer.id = 102; // Type Error: Cannot assign to 'id' because it is a read-only property.
```

### 4. Index Signatures
When building dynamic key-value maps (such as a dictionary of page translations or a cache of users), you might not know the property names in advance. An **Index Signature** defines the type of key and value allowed:

```typescript
// Any string key is allowed, and all values must be strings
let translationCache: { [key: string]: string };

translationCache = {
    "hello": "Hi",
    "goodbye": "Bye"
};
```

### 5. The `Record<Keys, Type>` Utility Type
`Record` is a built-in utility type that simplifies declaring index signatures. The syntax is: `Record<KeyType, ValueType>`.

```typescript
const userRoles: Record<string, string> = {
    "alice": "ADMIN",
    "bob": "DEVELOPER"
};
```

---

## Summary
- Annotate object shapes by defining property names and types.
- Append **`?`** to make a property optional.
- Use **`readonly`** to make specific object properties immutable.
- Use **Index Signatures** (`[key: string]: type`) or the **`Record<KeyType, ValueType>`** utility type to declare dynamic key-value maps.

---

## Additional Resources
- [TypeScript Docs: Object Types](https://www.typescriptlang.org/docs/handbook/2/objects.html)
- [TypeScript Docs: Utility Types (Record)](https://www.typescriptlang.org/docs/handbook/utility-types.html#recordkeys-type)
