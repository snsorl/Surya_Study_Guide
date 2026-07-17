# Type Coercion and Equality in JavaScript

## Learning Objectives
- Define implicit and explicit type coercion.
- Contrast loose equality (`==`) and strict equality (`===`).
- List the 6 falsy values in JavaScript.
- Avoid common coercion traps in condition comparisons.

---

## Why This Matters
JavaScript is a dynamically typed language. If you add a number and a string, instead of throwing a compilation error like Java, JavaScript dynamically converts one of the variables to match the other. This automatic conversion is called **implicit coercion**. While coercion can write shorter code, it often introduces unexpected bugs in financial operations, validation logic, or database checkouts. Mastering coercion and using strict equality checks is critical to writing predictable frontend systems.

---

## The Concept

### 1. Implicit vs. Explicit Coercion
- **Explicit Coercion**: This is when a developer intentionally converts a value from one type to another using built-in constructors.
  ```javascript
  let input = "42";
  let count = Number(input); // Explicit conversion to number
  ```
- **Implicit Coercion**: This is when JavaScript automatically converts a type under the hood during operation execution.
  ```javascript
  let sum = "The answer is: " + 42; // "The answer is: 42" (number coerced to string)
  let result = "10" - 2;            // 8 (string coerced to number)
  ```
  *Rule of Thumb:* The `+` operator prefers **string concatenation** if one operand is a string. Mathematical operators like `-`, `*`, `/`, and `%` prefer **numeric math**, coercing strings to numbers.

### 2. Loose vs. Strict Equality
- **Loose Equality (`==`)**: Compares two values for equality *after* coercing their types to match.
  ```javascript
  "5" == 5; // true (string "5" is coerced to number 5)
  ```
- **Strict Equality (`===`)**: Compares both the **value** and the **type**. If the types are different, it immediately returns `false` without coercion.
  ```javascript
  "5" === 5; // false (different types)
  ```
  *Recommendation:* Always default to strict equality (`===` and `!==`) to ensure type boundaries are protected.

### 3. Truthy and Falsy Values
When values are evaluated inside conditional tests (like `if` statements), they are implicitly coerced to a boolean.
In JavaScript, there are exactly **6 Falsy Values** (evaluate to `false` when coerced):
1. `false`
2. `0` (and `-0`, `0n`)
3. `""` (empty string)
4. `null`
5. `undefined`
6. `NaN` (Not-a-Number)

**Every other value** is considered **Truthy** (evaluates to `true` when coerced), including:
- `"0"` (string containing zero character)
- `"false"` (string containing text)
- `[]` (empty array)
- `{}` (empty object)
- `function(){}` (empty function)

---

## Code Examples

### Coercion Traps to Avoid

#### Trap 1: Adding vs Subtracting Strings
```javascript
console.log("5" + 2); // "52" (Concatenation)
console.log("5" - 2); // 3 (Numeric subtraction)
```

#### Trap 2: Loose Equality Surprises
Loose equality uses complex internal algorithm rules (the Abstract Equality Comparison Algorithm) that yield highly non-intuitive results:
```javascript
console.log([] == false); // true
console.log("" == false); // true
console.log(null == undefined); // true
console.log(null === undefined); // false
```
*Why this happens:* When comparing an array to a boolean, both are coerced to numbers. `[]` becomes `0`, `false` becomes `0`, and `0 == 0` evaluates to `true`.

#### Trap 3: The Danger of Falsy Checks
Checking if a value exists using a generic falsy check can accidentally reject valid inputs like number `0` or empty string `""`:
```javascript
function processScore(score) {
    // Dangerous check! If score is 0, it is falsy, triggering the default
    if (!score) {
        score = 50; 
    }
    return score;
}

console.log(processScore(0)); // Returns 50 instead of 0!
```
*Correct Remediation:* Use strict checks against `null` or `undefined` (or use the nullish coalescing operator `??` which we will learn on a future day):
```javascript
function processScoreSecure(score) {
    if (score === null || score === undefined) {
        score = 50;
    }
    return score;
}
```

---

## Summary
- **Explicit coercion** is developer-directed; **implicit coercion** occurs automatically under the hood.
- **Loose equality (`==`)** coerces types before checking values; **strict equality (`===`)** compares both type and value.
- There are exactly 6 falsy values in JS: `false`, `0`, `""`, `null`, `undefined`, and `NaN`.
- Empty arrays `[]` and empty objects `{}` are truthy.

---

## Additional Resources
- [MDN Web Docs: Type coercion](https://developer.mozilla.org/en-US/docs/Glossary/Type_coercion)
- [You Don't Know JS: Types & Grammar (Chapter 4: Coercion)](https://github.com/getify/You-Dont-Know-JS/blob/1st-ed/types%20%26%20grammar/ch4.md)
