# Quiz: TypeScript Tooling & Types

## Part 1: Multiple Choice & True/False

### 1. What does the TypeScript Compiler (`tsc`) output after a successful compilation?
- [ ] A) Native binary machine code.
- [ ] B) Standard browser-executable JavaScript.
- [ ] C) An optimized database schema.
- [ ] D) TypeScript type declaration files only.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Standard browser-executable JavaScript.

**Explanation:** TypeScript compiler (`tsc`) transpiles `.ts` files into standard `.js` files, stripping out all compile-time type annotations.
- **Why others are wrong:**
  - A) TypeScript does not compile to binary machine code.
  - C) It is a language compiler, not a database migration script.
  - D) While it can generate declaration files (`.d.ts`), its primary output is executable JavaScript.
</details>

---

### 2. How does the `tsconfig.json` option `"strict": true` affect compilation?
- [ ] A) It disables all type-checking constraints.
- [ ] B) It compiles code to ES5 target version standards.
- [ ] C) It enables strict type-checking checks, catching implicit `any` assignments.
- [ ] D) It formats variables using double quotes.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) It enables strict type-checking checks, catching implicit `any` assignments.

**Explanation:** The `"strict": true` flag enables a suite of strict type-checking behaviors, including catching implicit `any` assignments, strict null checks, and strict class initialization rules.
- **Why others are wrong:**
  - A) It enables, rather than disables, type-checking constraints.
  - B) Target version standards are configured using the `"target"` option.
  - D) It does not format code syntax styles.
</details>

---

### 3. What is a key difference between a Type Alias (`type`) and an Interface (`interface`)?
- [ ] A) Only interfaces can describe object shapes.
- [ ] B) Interfaces support declaration merging; type aliases do not.
- [ ] C) Type aliases are validated at runtime; interfaces are not.
- [ ] D) Interfaces cannot be extended.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Interfaces support declaration merging; type aliases do not.

**Explanation:** If you declare two interfaces with the same name, TypeScript automatically merges their properties. Type aliases do not support this and will throw a duplicate identifier error.
- **Why others are wrong:**
  - A) Both can describe object shapes.
  - C) Neither exists at runtime; both are compile-time annotations.
  - D) Interfaces are extended using the `extends` keyword.
</details>

---

### 4. Which TypeScript operator is used to assert that a value is non-null?
- [ ] A) `?`
- [ ] B) `!`
- [ ] C) `as`
- [ ] D) `satisfies`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `!`

**Explanation:** The non-null assertion operator (`!`) tells the compiler to treat a value as non-null and non-undefined, bypassing strict null checks.
- **Why others are wrong:**
  - A) The `?` operator denotes *optional* properties or safe navigation.
  - C) The `as` keyword is used for general type assertions.
  - D) The `satisfies` operator validates object shapes without widening types.
</details>

---

## Part 2: Code Predictions

### 5. What happens when compiling the following TypeScript code?
```typescript
let age: number = 25;
age = "twenty-five";
```
- [ ] A) It compiles successfully without errors.
- [ ] B) The compiler throws a type error at compile-time.
- [ ] C) It throws a TypeError at runtime in the browser.
- [ ] D) The variable is automatically converted to a string.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The compiler throws a type error at compile-time.

**Explanation:** TypeScript enforces static typing. Because `age` is declared as a `number`, assigning a `string` to it violates type compatibility, causing the compiler to flag an error.
- **Why others are wrong:**
  - A) Type violations block clean compilation.
  - C) Type checking is a compile-time feature; browser runtimes do not perform this validation.
  - D) TypeScript does not insert type-coercion logic.
</details>

---

### 6. What is the type of the `status` property in this discriminated union?
```typescript
interface Success { status: "SUCCESS"; data: string; }
interface Failure { status: "FAILURE"; error: Error; }
type Response = Success | Failure;
```
- [ ] A) `string`
- [ ] B) Literal union: `"SUCCESS" | "FAILURE"`
- [ ] C) `boolean`
- [ ] D) `any`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Literal union: `"SUCCESS" | "FAILURE"`

**Explanation:** The `status` property uses literal types (`"SUCCESS"` and `"FAILURE"`). When combined in the union type `Response`, the `status` acts as the discriminator tag, limited to those two exact values.
- **Why others are wrong:**
  - A) It is narrower than a general `string`; the compiler will block other string assignments.
  - C) It is not a boolean type.
</details>

---

### 7. What is the output of the compile check for the following code under strict null checks?
```typescript
let name: string = "Alice";
name = null;
```
- [ ] A) Compiles cleanly.
- [ ] B) The compiler throws a type error.
- [ ] C) `name` is converted to an empty string.
- [ ] D) `name` is converted to `"null"`.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The compiler throws a type error.

**Explanation:** Under `strictNullChecks: true`, `null` and `undefined` are treated as distinct types. To assign `null` to a string variable, you must declare it as a union type: `string | null`.
- **Why others are wrong:**
  - A) It fails compilation due to the missing union declaration.
  - C) No runtime string conversions occur.
</details>

---

### 8. Which Node package is used to execute TypeScript files directly in memory?
- [ ] A) `tsc`
- [ ] B) `ts-node`
- [ ] C) `nodemon`
- [ ] D) `npm`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `ts-node`

**Explanation:** `ts-node` transpiles TypeScript files in memory and runs them directly, bypassing the two-step `tsc` compile and `node` execute process.
- **Why others are wrong:**
  - A) `tsc` only transpiles files to disk; it does not execute them.
  - C) `nodemon` is a file watcher that restarts the process on save.
  - D) `npm` is the package manager.
</details>

---

### 9. What does the following code print?
```typescript
interface User { id: number; email?: string; }
const user: User = { id: 1 };
console.log(user.email);
```
- [ ] A) `""`
- [ ] B) `null`
- [ ] C) `undefined`
- [ ] D) Throws a runtime error

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `undefined`

**Explanation:** The `email` property is marked as optional (`?`). Since it was omitted during object initialization, accessing it returns `undefined`.
- **Why others are wrong:**
  - A) Optional properties do not default to empty strings.
  - B) They do not default to `null` unless explicitly assigned.
  - D) Accessing missing properties on objects is valid in JavaScript and returns `undefined` instead of throwing an error.
</details>

---

### 10. How does the `satisfies` operator differ from a standard type assertion (`as`)?
- [ ] A) `satisfies` modifies the runtime object structure.
- [ ] B) `satisfies` validates object structures without widening the inferred type.
- [ ] C) `satisfies` is supported in browser runtimes.
- [ ] D) There is no difference.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `satisfies` validates object structures without widening the inferred type.

**Explanation:** The `satisfies` operator validates that an object matches a specific type or interface contract, but **preserves** the most specific inferred type of the object properties.
- **Why others are wrong:**
  - A) It is a compile-time operator and does not alter runtime values.
  - C) It is compile-time only, and is stripped from the compiled JS.
</details>
