# JSON: JavaScript Object Notation

## Learning Objectives
- Explain the syntax difference between JSON strings and JavaScript Object literals.
- Serialize objects to JSON strings using `JSON.stringify()`.
- Deserialize JSON strings into Java objects using `JSON.parse()`.
- List values that are ignored or raise errors during JSON serialization.

---

## Why This Matters
Web applications communicate by sending strings over the network. JavaScript Object Notation (JSON) has emerged as the standard format for exchanging data between client-side frontends and backend servers. Since we will fetch data from our Spring Boot REST APIs, we need to know how to serialize local data structures into JSON strings before dispatching, and deserialize incoming payloads back into JavaScript objects safely.

---

## The Concept

### 1. JSON Syntax Rules
Although JSON is based on JavaScript object literals, its syntax is much more strict:
- **Double Quotes Only**: All string values and object keys **must** be wrapped in double quotes (`"key": "value"`). Single quotes are invalid.
- **Allowed Data Types**: Only strings, numbers, booleans, arrays, nested objects, and `null` are allowed.
- **No Functions or Comments**: You cannot embed comments or executable functions inside a JSON file.
- **No Trailing Commas**: Trailing commas on the last property of an object or element of an array are invalid in JSON.

```json
{
  "name": "Jane",
  "age": 30,
  "isActive": true
}
```

### 2. Serialization and Deserialization

#### Serialization (`JSON.stringify`)
Converts a JavaScript object, array, or primitive value into a JSON-formatted string. Used when preparing payloads to send via HTTP POST/PUT requests:
```javascript
const userObj = { name: "John", age: 25 };
const jsonString = JSON.stringify(userObj); // '{"name":"John","age":25}'
```

#### Deserialization (`JSON.parse`)
Parses a JSON string and returns a matching JavaScript object structure. Used when processing JSON response bodies received from APIs:
```javascript
const rawJson = '{"title": "Buy milk", "completed": false}';
const task = JSON.parse(rawJson);
console.log(task.title); // "Buy milk"
```

### 3. Serialization Pitfalls
Not all JavaScript values can be converted to JSON. The following values behave differently during serialization:
- **`undefined`**, **Functions**, and **Symbols** are ignored or stripped out when they are object properties, or converted to `null` if they are elements in an array.
- **`NaN`** and **`Infinity`** are converted to `null`.
- **Circular References** (an object pointing to itself) cause `JSON.stringify` to throw a `TypeError`.

---

## Code Examples

### Handling Serialization Exceptions and Ignores
Let's see what happens to unsupported types during serialization:

```javascript
const buggyObject = {
    name: "Tester",
    action: function() { console.log("Run"); }, // Function
    secret: undefined,                         // Undefined
    scores: [100, undefined, NaN]              // Mixed array
};

const jsonResult = JSON.stringify(buggyObject);
console.log(jsonResult);
// Output: '{"name":"Tester","scores":[100,null,null]}'
// Note: 'action' and 'secret' are stripped out, while 'undefined' in the array becomes 'null'.

// Circular Reference Error
const parent = { name: "Parent" };
const child = { name: "Child", parent: parent };
parent.child = child; // Circular reference created

try {
    JSON.stringify(parent);
} catch (error) {
    console.error("Stringify failed:", error.message); 
    // Output: Stringify failed: Converting circular structure to JSON
}
```

---

## Summary
- JSON is a strict subset of JavaScript object literals requiring **double quotes** on keys and strings.
- Use **`JSON.stringify()`** to convert objects to strings, and **`JSON.parse()`** to convert strings back to objects.
- Functions, symbols, and `undefined` properties are **stripped** during serialization.
- Circular references cause `JSON.stringify` to throw a runtime error.

---

## Additional Resources
- [MDN Web Docs: JSON overview](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/JSON)
- [JavaScript.info: JSON methods, toJSON](https://javascript.info/json)
