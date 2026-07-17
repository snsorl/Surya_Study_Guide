# Quiz: JavaScript Basics

## Part 1: Multiple Choice & True/False

### 1. What does the `typeof null` expression return in standard JavaScript?
- [ ] A) `"null"`
- [ ] B) `"undefined"`
- [ ] C) `"object"`
- [ ] D) `"error"`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `"object"`

**Explanation:** In JavaScript, `typeof null` returns `"object"`. This is a legacy bug in the original language implementation that was preserved for backward compatibility.
- **Why others are wrong:**
  - A) `"null"` is the intuitive expected value, but it is incorrect due to the legacy engine bug.
  - B) `"undefined"` is returned by `typeof undefined`.
  - D) `"error"` is not a valid output of the `typeof` operator.
</details>

---

### 2. Which of the following is NOT one of JavaScript's 7 primitive data types?
- [ ] A) Array
- [ ] B) Symbol
- [ ] C) BigInt
- [ ] D) String

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) Array

**Explanation:** Arrays are reference types (subclasses of the base Object), not primitive types. The 7 primitive types are string, number, bigint, boolean, undefined, null, and symbol.
- **Why others are wrong:**
  - B) Symbol is a primitive type introduced in ES6 for unique identifiers.
  - C) BigInt is a primitive type introduced in ES2020 for large integers.
  - D) String is a fundamental primitive type representing textual data.
</details>

---

### 3. What is the output of the expression `10 + "5"` in JavaScript?
- [ ] A) `15`
- [ ] B) `"105"`
- [ ] C) `NaN`
- [ ] D) Throws a TypeError

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `"105"`

**Explanation:** When using the `+` operator, if either operand is a string, JavaScript performs implicit string coercion and concatenates the two values.
- **Why others are wrong:**
  - A) `15` would be the output if both were numbers, or if subtraction (`10 - "5"`) was used.
  - C) `NaN` (Not-a-Number) occurs when mathematical conversions fail, but string concatenation is always valid.
  - D) JavaScript does not throw a compile or runtime error for mixing strings and numbers in addition.
</details>

---

### 4. What does the expression `5 == "5"` evaluate to?
- [ ] A) `true`
- [ ] B) `false`
- [ ] C) `undefined`
- [ ] D) `null`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) `true`

**Explanation:** The loose equality operator (`==`) performs implicit type coercion, converting the string `"5"` to the number `5` before comparison.
- **Why others are wrong:**
  - B) `false` is the result of strict equality (`5 === "5"`), which does not perform type coercion.
  - C) Comparisons always evaluate to booleans (`true` or `false`), never `undefined`.
  - D) Comparisons never return `null`.
</details>

---

### 5. Which array method mutates the original array in place?
- [ ] A) `slice`
- [ ] B) `map`
- [ ] C) `filter`
- [ ] D) `splice`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** D) `splice`

**Explanation:** The `splice` method mutates the original array by adding or removing elements at specified indices.
- **Why others are wrong:**
  - A) `slice` returns a shallow copy of a portion of an array and does not modify the original.
  - B) `map` returns a new array with transformed elements, leaving the original array intact.
  - C) `filter` returns a new array containing elements that pass the test, leaving the original array intact.
</details>

---

### 6. True or False: In JavaScript, an empty array (`[]`) evaluates to a falsy value.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** In JavaScript, all objects and arrays (including empty ones like `[]` or `{}`) are truthy. Only specific values (`false`, `0`, `""`, `null`, `undefined`, and `NaN`) are falsy.
- **Why others are wrong:**
  - A) While empty strings and the number 0 are falsy, objects and arrays are always truthy.
</details>

---

## Part 2: Code Predictions

### 7. What is printed to the console when this code is executed?
```javascript
console.log(myVar);
var myVar = 50;
```
- [ ] A) `50`
- [ ] B) `ReferenceError`
- [ ] C) `undefined`
- [ ] D) `null`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `undefined`

**Explanation:** Variables declared with `var` are hoisted to the top of their scope. However, only the declaration is hoisted, not the initialization value. Therefore, `myVar` exists but is `undefined` when logged.
- **Why others are wrong:**
  - A) `50` is only printed *after* the assignment line executes.
  - B) A `ReferenceError` is not thrown because `var` variables are hoisted (unlike `let` and `const`, which enter the Temporal Dead Zone).
  - D) The default uninitialized state of a hoisted variable is `undefined`, not `null`.
</details>

---

### 8. What is the output of the following array reduce operation?
```javascript
const numbers = [1, 2, 3];
const result = numbers.reduce((total, n) => total + n, 10);
console.log(result);
```
- [ ] A) `6`
- [ ] B) `16`
- [ ] C) `10`
- [ ] D) `[11, 12, 13]`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `16`

**Explanation:** The second parameter of `reduce` (`10`) is the initial value of `total`. The callback sums each number: $10 + 1 + 2 + 3 = 16$.
- **Why others are wrong:**
  - A) `6` is the result if no initial value was provided, defaulting to the first element (`1`).
  - C) `10` is only the starting point; the array elements are added to it.
  - D) `reduce` returns a single aggregated value, not a transformed array.
</details>

---

### 9. What is the output of the following scope check?
```javascript
if (true) {
    var a = "var-test";
    let b = "let-test";
}
console.log(a);
console.log(b);
```
- [ ] A) Logs `"var-test"` and then `"let-test"`
- [ ] B) Logs `"var-test"` and then throws a `ReferenceError` for `b`
- [ ] C) Throws a `ReferenceError` for `a`
- [ ] D) Logs `undefined` for both variables

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Logs `"var-test"` and then throws a `ReferenceError` for `b`

**Explanation:** `var` is function-scoped and leaks out of the block context (`if` statement), making `a` accessible. `let` is block-scoped, so `b` cannot be accessed outside the `{}` boundary, throwing a `ReferenceError`.
- **Why others are wrong:**
  - A) `b` is block-scoped and cannot be accessed outside the `if` block.
  - C) `a` is declared with `var`, which does not respect block boundaries, so it is successfully resolved.
  - D) `a` holds its assigned value, and accessing `b` crashes the script immediately instead of returning `undefined`.
</details>

---

### 10. Which array method is used to remove the first element from an array?
- [ ] A) `pop`
- [ ] B) `push`
- [ ] C) `shift`
- [ ] D) `unshift`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `shift`

**Explanation:** The `shift` method removes the first element from an array and shifts all remaining elements down one index.
- **Why others are wrong:**
  - A) `pop` removes the *last* element.
  - B) `push` adds an element to the *end*.
  - D) `unshift` adds an element to the *beginning*.
</details>
