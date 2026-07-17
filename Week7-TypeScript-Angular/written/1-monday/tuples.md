# Tuples in TypeScript

## Learning Objectives
- Define and declare Tuple types in TypeScript.
- Write Labeled Tuples to improve type self-documentation.
- Configure Optional and Rest elements in tuple definitions.
- Use Tuples to return fixed structures from functions.

---

## Why This Matters
JavaScript arrays are dynamic: they can grow or shrink in size and contain any combination of data types. However, sometimes we need to model a data structure with a fixed number of elements of specific types (e.g. a coordinate pair `[latitude, longitude]`, or a status response `[successCode, dataPayload]`). In TypeScript, this specialized array is called a **Tuple**. Using tuples ensures that index positions hold expected data types, preventing runtime lookup errors.

---

## The Concept

### 1. What is a Tuple?
A tuple is an array type that knows exactly how many elements it contains and which types are stored at specific index positions:

```typescript
let coordinate: [number, number];
coordinate = [40.7128, -74.0060]; // Valid
// coordinate = ["New York", -74.0060]; // Compile Error: Type 'string' is not assignable to type 'number'.
```

### 2. Labeled Tuples
By default, tuple elements lack descriptive names: `[string, number]`. You can add labels to tuple elements to make your code self-documenting:

```typescript
// Labeled Tuple
type UserAgeTuple = [username: string, age: number];

const entry: UserAgeTuple = ["Alice", 28];
```

### 3. Optional and Rest Elements
- **Optional Tuple Elements (`?`)**: You can mark elements at the end of a tuple as optional:
  ```typescript
  type Point = [x: number, y: number, z?: number];
  const p1: Point = [10, 20]; // Valid
  const p2: Point = [10, 20, 30]; // Valid
  ```
- **Rest Elements (`...`)**: A tuple can contain a rest element representing a variable number of elements of a specific type:
  ```typescript
  type StringNumberTuple = [string, ...number[]];
  const item: StringNumberTuple = ["Scores", 90, 85, 95];
  ```

### 4. Returning Fixed Structures
Tuples are commonly used to return multiple values from a function, such as React's `useState` hook (`[state, setState]`) or database transaction wrappers:

```typescript
function getResponseState(): [statusCode: number, payload: string] {
    return [200, "Success"];
}
const [code, msg] = getResponseState(); // Destructuring
```

---

## Code Examples

### Labeled Tuples & Destructuring
```typescript
type GeoPoint = [latitude: number, longitude: number, locationName: string];

const centralPark: GeoPoint = [40.785091, -73.968285, "Central Park"];

// Accessing elements by index
console.log(`Latitude: ${centralPark[0]}`);

// Safely destructuring elements
const [lat, lng, name] = centralPark;
console.log(`${name} is located at: ${lat}, ${lng}`);
```

---

## Summary
- A **Tuple** is a specialized array type with a fixed number of elements and specific type bindings for each index.
- Use **Labeled Tuples** (`[label: type]`) to document the purpose of each position.
- Tuples support optional (`?`) and rest (`...`) elements.
- Tuples are ideal for returning **fixed-structure** payloads from functions.

---

## Additional Resources
- [TypeScript Docs: Tuple types handbook](https://www.typescriptlang.org/docs/handbook/2/objects.html#tuple-types)
- [TypeScript Handbook: Readonly Tuples](https://www.typescriptlang.org/docs/handbook/2/objects.html#readonly-tuple-types)
