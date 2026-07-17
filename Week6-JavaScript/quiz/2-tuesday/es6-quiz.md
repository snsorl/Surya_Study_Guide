# Quiz: ES6 Features & OOP

## Part 1: Multiple Choice & True/False

### 1. What does strict mode do when you attempt to assign a value to an undeclared variable?
- [ ] A) Automatically creates a new global variable.
- [ ] B) Ignores the assignment silently.
- [ ] C) Throws a `ReferenceError` at runtime.
- [ ] D) Prompts the user with a warning dialog.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Throws a `ReferenceError` at runtime.

**Explanation:** In strict mode (`"use strict"`), assigning a value to an undeclared variable throws a `ReferenceError`, forcing developers to declare variables using `let`, `const`, or `var`.
- **Why others are wrong:**
  - A) Creating a global variable is the behavior of non-strict mode.
  - B) Assignments never fail silently in strict mode; they throw errors.
  - D) JavaScript runtimes do not prompt users with dialogs for reference issues.
</details>

---

### 2. How do arrow functions resolve the value of the `this` keyword?
- [ ] A) Dynamically, based on how the function is invoked.
- [ ] B) Lexically, inheriting the `this` context from their enclosing scope.
- [ ] C) Always bind `this` to the global `window` object.
- [ ] D) Set `this` to `null` by default.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Lexically, inheriting the `this` context from their enclosing scope.

**Explanation:** Arrow functions do not define their own `this` binding. Instead, they capture the `this` value of their enclosing scope when they are declared.
- **Why others are wrong:**
  - A) Dynamic binding based on invocation is how standard function declarations behave.
  - C) They only bind to the global window if their enclosing lexical scope was global.
  - D) They do not default to `null`.
</details>

---

### 3. Which keyword is used to call a parent class constructor inside a subclass constructor?
- [ ] A) `parent`
- [ ] B) `super`
- [ ] C) `this`
- [ ] D) `extends`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `super`

**Explanation:** In ES6 classes, `super()` is invoked inside the subclass constructor to execute the parent class constructor and initialize inherited properties.
- **Why others are wrong:**
  - A) `parent` is not a keyword in JavaScript class definitions.
  - C) `this` refers to the current instance, but cannot invoke parent constructors.
  - D) `extends` is used in the class declaration line (e.g. `class A extends B`), not inside the constructor body.
</details>

---

### 4. Which of the following is a syntax constraint of template literals?
- [ ] A) They must be enclosed in single quotes ('').
- [ ] B) They must be enclosed in backticks (\`\`).
- [ ] C) Expressions inside placeholders must use parentheses `$(expr)`.
- [ ] D) They do not support multi-line structures.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) They must be enclosed in backticks (\`\`).

**Explanation:** Template literals are declared using backticks (\`\`). They support string interpolation using `${expression}` placeholders.
- **Why others are wrong:**
  - A) Single quotes define standard string literals.
  - C) The interpolation placeholder uses curly braces `${}`, not parentheses `$()`.
  - D) They natively support multi-line structures.
</details>

---

## Part 2: Code Predictions

### 5. What does the following code print?
```javascript
const obj = {
    value: 42,
    getValue: () => {
        return this.value;
    }
};
console.log(obj.getValue());
```
- [ ] A) `42`
- [ ] B) `undefined`
- [ ] C) `TypeError`
- [ ] D) `NaN`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `undefined`

**Explanation:** Arrow functions capture `this` lexically. Because `obj` is an object literal (not a function scope), the enclosing scope is the global scope (or module scope). Therefore, `this` refers to the global object/module context, which does not contain `value`, returning `undefined`.
- **Why others are wrong:**
  - A) To print `42`, `getValue` must be declared as a standard method function (e.g., `getValue() { return this.value; }`).
  - C) Arrow functions are valid inside object definitions and do not throw a TypeError on execution.
  - D) No mathematical operation was executed, so it does not evaluate to `NaN`.
</details>

---

### 6. What does this class declaration evaluate to when checked with `typeof`?
```javascript
class Person {}
console.log(typeof Person);
```
- [ ] A) `"class"`
- [ ] B) `"object"`
- [ ] C) `"function"`
- [ ] D) `"undefined"`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `"function"`

**Explanation:** Under the hood, ES6 classes are syntactic sugar over prototype constructor functions. The type of a class constructor is `"function"`.
- **Why others are wrong:**
  - A) `"class"` is not a valid output of the `typeof` operator.
  - B) Objects are instances created *from* classes (`new Person()`), but the class definition itself is a constructor function.
  - D) The class is defined, so its type is resolved.
</details>

---

### 7. What is the output of attempting to mutate a property of a `const` object?
```javascript
const user = { name: "Alice" };
user.name = "Bob";
console.log(user.name);
```
- [ ] A) `"Alice"` (ignores mutation silently)
- [ ] B) `"Bob"`
- [ ] C) `TypeError: Assignment to constant variable`
- [ ] D) `SyntaxError`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `"Bob"`

**Explanation:** Declaring an object with `const` protects the variable identifier reference from being reassigned to a new object, but does not freeze the object's properties. Therefore, mutations are allowed.
- **Why others are wrong:**
  - A) Mutations are not ignored; they update the property in memory.
  - C) A `TypeError` is only thrown if you try to reassign the entire variable pointer (e.g., `user = { name: "Bob" }`).
  - D) The syntax is entirely valid.
</details>

---

### 8. Which method returns a new function with its `this` context permanently bound to a specific object?
- [ ] A) `call`
- [ ] B) `apply`
- [ ] C) `bind`
- [ ] D) `resolve`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `bind`

**Explanation:** The `bind` method returns a copy of the target function with its `this` reference permanently locked to the object passed as the argument.
- **Why others are wrong:**
  - A) `call` invokes the function immediately.
  - B) `apply` invokes the function immediately with array arguments.
  - D) `resolve` is not a standard binding method.
</details>
