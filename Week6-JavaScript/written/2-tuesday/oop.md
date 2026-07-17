# JavaScript OOP: Prototypes and Inheritance

## Learning Objectives
- Describe the prototype-based inheritance model of JavaScript.
- Build objects using Constructor Functions.
- Trace search queries through the Prototype Chain.
- Create objects with explicit prototypes using `Object.create()`.
- Use `hasOwnProperty()` to distinguish between instance properties and inherited properties.

---

## Why This Matters
Java utilizes class-based Object-Oriented Programming, where classes act as blueprints and objects are instantiated from them. Inheritance occurs when one class extends another. JavaScript is **prototype-based**. In JavaScript, objects inherit directly from other objects via a prototype chain. Understanding how prototypes, constructor functions, and lookup chains work is essential for managing memory efficiently and understanding how class abstractions operate under the hood in JavaScript.

---

## The Concept

### 1. Prototype-Based OOP
Every object in JavaScript has an internal link to another object called its **prototype**.
- When you request a property or method from an object, JavaScript first checks the object itself.
- If the property is missing, it searches the object's prototype.
- This search continues up the prototype chain until it either finds the property or reaches `null` (the end of the chain), returning `undefined`.

```
[ object ] --(link: __proto__)--> [ prototype object ] --(__proto__)--> [ Object.prototype ] --> null
```

### 2. Constructor Functions
Before ES6 classes, constructor functions were used to instantiate new objects. By convention, constructor function names are capitalized:

```javascript
function User(name) {
    this.name = name;
}
```
When invoked with the `new` keyword, the engine:
1. Creates a new empty object.
2. Binds `this` to the new object.
3. Points the new object's `__proto__` to the constructor's `prototype` property.
4. Returns the object.

To prevent duplicating methods in memory for every instance, methods are added to the constructor's `prototype`:
```javascript
User.prototype.sayHello = function() {
    return "Hi, I am " + this.name;
};
```

### 3. Object Creation with `Object.create()`
`Object.create(proto)` creates a new empty object and sets its internal prototype link (`__proto__`) to the object passed as the argument. This allows you to configure prototype inheritance links directly:

```javascript
const personProto = {
    greet: function() { return "Hello!"; }
};

const student = Object.create(personProto);
student.name = "John";
console.log(student.greet()); // "Hello!" (inherited)
```

### 4. Instance Properties vs. Inherited Properties (`hasOwnProperty`)
- **Instance Properties**: Properties declared directly on the object itself.
- **Inherited Properties**: Properties that exist on prototype parents in the lookup chain.
- Use **`hasOwnProperty(prop)`** to check if a property is an instance property:

```javascript
console.log(student.hasOwnProperty("name"));  // true
console.log(student.hasOwnProperty("greet")); // false (inherited from personProto)
```

---

## Code Examples

### Tracing Prototype Inheritance
```javascript
// 1. Constructor Function
function Animal(name) {
    this.name = name;
}

// 2. Add shared method to Prototype
Animal.prototype.speak = function() {
    console.log(`${this.name} makes a noise.`);
};

// 3. Instantiate object
let dog = new Animal("Rex");

dog.speak(); // "Rex makes a noise." (found on prototype)
console.log(dog.hasOwnProperty("name"));  // true
console.log(dog.hasOwnProperty("speak")); // false (inherited)
```

---

## Summary
- JavaScript uses **prototype-based inheritance**, where objects inherit properties and methods directly from other objects.
- Constructor functions are invoked with `new` to instantiate object structures.
- Define shared methods on the constructor's **`prototype`** property to prevent duplicating function objects in memory.
- Use `Object.create(proto)` to explicitly link prototype chains.
- **`hasOwnProperty(prop)`** checks if a property exists directly on the object, ignoring inherited prototype properties.

---

## Additional Resources
- [MDN Web Docs: Inheritance and the prototype chain](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Inheritance_and_the_prototype_chain)
- [JavaScript.info: Prototypes, inheritance](https://javascript.info/prototypes)
