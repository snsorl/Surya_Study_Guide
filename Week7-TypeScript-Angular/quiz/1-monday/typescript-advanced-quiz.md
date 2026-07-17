# Practice Quiz: Advanced TypeScript Features

## Part 1: Multiple Choice (MCQ)

### 1. Which of the following is true about abstract classes in TypeScript?
- [ ] A) Abstract classes can be instantiated directly using the `new` keyword.
- [ ] B) They can contain only abstract method signatures, not concrete method bodies.
- [ ] C) Abstract methods within an abstract class must contain no code body.
- [ ] D) Subclasses extending abstract classes are not required to implement abstract methods.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Abstract methods within an abstract class must contain no code body.

**Explanation:** Abstract methods contain only their parameter and return type signatures. 
- **Why others are wrong:**
  - A) Abstract classes cannot be instantiated directly; they are blueprints to be extended.
  - B) Abstract classes can contain concrete methods with complete method bodies.
  - D) Any concrete subclass that extends an abstract class must implement all abstract methods to compile.
</details>

---

### 2. Given a class `Product`, which utility type converts all its properties to optional?
- [ ] A) `Omit<Product, All>`
- [ ] B) `Partial<Product>`
- [ ] C) `Readonly<Product>`
- [ ] D) `Exclude<Product>`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Partial<Product>`

**Explanation:** The `Partial<T>` utility type constructs a type with all properties of T set to optional.
- **Why others are wrong:**
  - A) `Omit` removes specific keys, it doesn't make them optional.
  - C) `Readonly` makes properties read-only, they remain required unless already marked optional.
  - D) `Exclude` is used to filter out types from unions, not object properties.
</details>

---

### 3. Which keyword is used to declare that a class must satisfy the contract defined by an interface?
- [ ] A) `extends`
- [ ] B) `implements`
- [ ] C) `interface`
- [ ] D) `inherits`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `implements`

**Explanation:** In TypeScript, the `implements` keyword checks that a class conforms to the structural contract defined by an interface.
- **Why others are wrong:**
  - A) `extends` is used for class-to-class or interface-to-interface inheritance.
  - C) `interface` is used to define the interface shape, not link it to a class.
  - D) `inherits` is not a valid TypeScript keyword.
</details>

---

### 4. What is the transpilation output of a TypeScript `interface` in the output JavaScript bundle?
- [ ] A) It compiles into a JavaScript prototype constructor function.
- [ ] B) It compiles into a JavaScript helper class.
- [ ] C) It is completely stripped out during compilation (zero runtime footprint).
- [ ] D) It compiles into a metadata map registry.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) It is completely stripped out during compilation (zero runtime footprint).

**Explanation:** Interfaces are compile-time constructs used by the TypeScript compiler to validate shapes. They have no runtime existence in JavaScript.
- **Why others are wrong:**
  - A & B) TypeScript classes transpile into prototype constructor functions or ES6 classes, not interfaces.
  - D) JavaScript does not have metadata registries for interfaces.
</details>

---

### 5. What does the parameter property shorthand `constructor(private name: string)` accomplish?
- [ ] A) Declares a parameter but does not initialize it.
- [ ] B) Declares a private property, binds it as a constructor parameter, and initializes it automatically.
- [ ] C) Restricts the class from being instantiated with arguments.
- [ ] D) Declares a private class method named `name`.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Declares a private property, binds it as a constructor parameter, and initializes it automatically.

**Explanation:** Writing an access modifier directly on a constructor parameter instructs TypeScript to generate the backing field and perform the assignment (e.g. `this.name = name`) automatically.
- **Why others are wrong:**
  - A) The property is fully initialized to the argument value.
  - C) It allows class instantiation with arguments normally.
  - D) It declares an instance field property, not a method.
</details>

---

## Part 2: True / False

### 6. A protected property can be accessed from outside the class instance in non-derived code files.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Protected properties can only be accessed within the base class itself and any subclasses derived from it. They cannot be accessed by external consumers.
</details>

---

### 7. TypeScript access modifiers (`public`, `private`, `protected`) are strictly enforced at runtime by browser engines.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Access modifiers are compile-time checks enforced by the TypeScript compiler. Once transpiled into JavaScript, these modifiers are removed, and all properties become standard public keys unless ES2022 private fields (`#`) are used.
</details>

---

## Part 3: Code Prediction

### 8. What is the type of variable `userData` in the following code block?
```typescript
interface User {
  id: number;
  name: string;
  email: string;
}
type UserPatch = Omit<User, "id">;
const userData: UserPatch = { name: "Alice", email: "alice@example.com" };
```
- [ ] A) `{ id: number; name: string; email: string; }`
- [ ] B) `{ name: string; email: string; }`
- [ ] C) `{ id?: number; name?: string; email?: string; }`
- [ ] D) `string`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `{ name: string; email: string; }`

**Explanation:** The `Omit<User, "id">` utility type constructs a new type shape by selecting all properties from `User` except for the `"id"` property.
</details>

---

### 9. What is the output of the following code?
```typescript
class Point {
    constructor(public x: number, public y: number) {}
}
class Coordinates {
    constructor(public x: number, public y: number) {}
}
const p: Point = new Coordinates(5, 10);
console.log(p.x);
```
- [ ] A) Compile Error: Types Point and Coordinates are incompatible
- [ ] B) `undefined`
- [ ] C) `5`
- [ ] D) `10`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `5`

**Explanation:** TypeScript utilizes structural subtyping (duck typing). Since both classes have the exact same shape properties (`x` and `y` numbers), they are compatible, and the code compiles and logs `5`.
</details>

---

## Part 4: Fill-in-the-Blank

### 10. The utility type used to extract the return value structure of a function type declaration is `___________<T>`.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `ReturnType`

**Explanation:** The built-in `ReturnType<T>` utility type extracts and returns the output return signature of a function type declaration.
</details>
