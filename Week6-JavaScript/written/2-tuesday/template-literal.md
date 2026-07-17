# Template Literals in ES6+

## Learning Objectives
- Define string literals using backtick (\`\`) syntax.
- Interpolate variables and evaluations dynamically inside strings using `${expression}`.
- Create multi-line strings without manually writing newline escape characters (`\n`).
- Explain the basic concept of Tagged Template Literals.

---

## Why This Matters
Historically, inserting variables into strings in JavaScript required verbose string concatenation with the `+` operator. For instance, generating complex HTML layouts dynamically in code resulted in messy, hard-to-read code. ES6 template literals solved this by introducing string interpolation and native multi-line formatting. This is vital for DOM manipulation tasks where we construct UI layouts directly in code.

---

## The Concept

### 1. Backticks and Expression Interpolation
Template literals use backticks (\`\`) instead of standard single ('') or double ("") quotes. Inside a template literal, you can embed any valid JavaScript expression (variables, mathematical operators, or function calls) inside a placeholder block: `${expression}`.

```javascript
let name = "Alice";
let greeting = `Hello, ${name}!`; // "Hello, Alice!"
```

### 2. Multi-line Strings
Standard strings cannot span multiple lines without concatenation or adding `\n` characters:
```javascript
// Old Way:
let codeOld = "function test() {\n" + 
              "  return 0;\n" +
              "}";
```
Template literals support multi-line structures natively. Any whitespace or newlines inside the backticks are preserved:
```javascript
// New Way:
let codeNew = `function test() {
  return 0;
}`;
```

### 3. Tagged Templates (Overview)
A tagged template is an advanced form of template literal where you prefix the literal with a function name (a "tag"). The tag function parses the string fragments and expressions, allowing you to run custom security validation, string escaping, or translation logic:

```javascript
// Tag function definition
function secure(strings, ...values) {
    // Custom sanitization logic can run here
    return strings[0] + values[0].toUpperCase() + strings[1];
}

let user = "admin";
let message = secure`Current user: ${user}`; // "Current user: ADMIN"
```

---

## Code Examples

### Expressions Interpolation
You can place operations and function calls inside `${}` placeholders:

```javascript
let price = 100;
let tax = 0.08;

console.log(`Total Price: $${(price * (1 + tax)).toFixed(2)}`);
// Output: "Total Price: $108.00"
```

### HTML Layout Construction
This is highly useful for building HTML structures dynamically before inserting them into the DOM:

```javascript
let user = { name: "Bob", role: "Developer" };

let cardMarkup = `
<div class="user-card">
    <h3>${user.name}</h3>
    <span class="badge">${user.role.toUpperCase()}</span>
</div>
`;

console.log(cardMarkup);
```

---

## Summary
- Template literals are declared using **backticks (\`\`)**.
- Embed expressions, variables, or functions dynamically using **`${expression}`**.
- Multi-line layouts are natively supported and preserve formatting spaces and newlines.
- **Tagged templates** allow custom preprocessing functions to filter or alter template literal values.

---

## Additional Resources
- [MDN Web Docs: Template literals](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Template_literals)
- [JavaScript.info: String formatting](https://javascript.info/string)
