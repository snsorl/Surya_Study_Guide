# ES6 Classes

## Learning Objectives
- Declare JavaScript classes containing constructor methods.
- Define instance methods, static methods, getters, and setters.
- Implement inheritance using `extends` and `super()`.
- Explain how ES6 classes serve as syntactic sugar over prototypes.

---

## Why This Matters
For developers transitioning from Java, JavaScript's prototype-based OOP model can feel unfamiliar. To bridge this gap, ES6 introduced **Classes** in 2015. Classes provide a clean, familiar syntax for defining objects, inheritance, and static methods. However, classes do not change JavaScript's underlying execution model. Under the hood, they are still translated to prototypes. Understanding this mapping is key to writing clean object-oriented frontend code without misconceptions.

---

## The Concept

### 1. Declaring Classes
An ES6 class is declared using the `class` keyword. It typically includes:
- **`constructor()`**: A special method used to initialize object properties. It runs automatically when the object is instantiated via `new`.
- **Instance Methods**: Declared inside the class without the `function` keyword.
- **Getters/Setters**: Intercept property reads and writes to add validation or transform data.
- **Static Methods**: Declared with the `static` keyword. They belong to the class blueprint itself, not individual object instances.

```javascript
class Product {
    constructor(name, price) {
        this._name = name;
        this.price = price;
    }

    // Instance Method
    getDetails() {
        return `${this._name} costs $${this.price}`;
    }

    // Getter
    get name() {
        return this._name.toUpperCase();
    }

    // Static Method
    static compare(prodA, prodB) {
        return prodA.price - prodB.price;
    }
}
```

### 2. Inheritance (`extends` and `super`)
Inheritance allows a subclass to inherit properties and methods from a parent class.
- **`extends`**: Used to establish the subclass relationship.
- **`super()`**: Invokes the parent class constructor. If a subclass defines a constructor, it **must** call `super()` before accessing `this`.

```javascript
class Electronic extends Product {
    constructor(name, price, warrantyMonths) {
        super(name, price); // Invokes Product constructor
        this.warrantyMonths = warrantyMonths;
    }
    
    // Method override
    getDetails() {
        return `${super.getDetails()} (Warranty: ${this.warrantyMonths}m)`;
    }
}
```

### 3. Classes under the Hood: Prototype Sugar
ES6 classes are **syntactic sugar** over prototypes. They do not introduce a new object model.
- When you define a class, JavaScript compiles it into a constructor function.
- Methods defined inside the class are automatically added to the constructor's `prototype` object.
- `extends` sets up the prototype chain link (`Object.setPrototypeOf`) under the hood.

```javascript
console.log(typeof Product); // "function"
console.log(Product.prototype.getDetails); // [Function: getDetails]
```

---

## Code Examples

### Complete OOP Inheritance Tracing
```javascript
class Person {
    constructor(firstName, lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    get fullName() {
        return `${this.firstName} ${this.lastName}`;
    }
}

class Employee extends Person {
    constructor(firstName, lastName, role) {
        super(firstName, lastName);
        this.role = role;
    }

    introduce() {
        return `Hi, I am ${this.fullName}, working as a ${this.role}.`;
    }
}

const emp = new Employee("Alice", "Smith", "Architect");
console.log(emp.introduce()); // "Hi, I am Alice Smith, working as a Architect."
console.log(emp instanceof Employee); // true
console.log(emp instanceof Person);   // true
```

---

## Summary
- Declare classes using the **`class`** keyword, wrapping properties and methods in a clean structure.
- **Instance methods** are shared on the prototype, while **static methods** belong to the class constructor.
- Use **`extends`** to inherit from parent classes and invoke **`super()`** to run parent constructors.
- Classes do not change JavaScript's runtime behavior; they are **syntactic sugar** over prototypes.

---

## Additional Resources
- [MDN Web Docs: Classes reference](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes)
- [JavaScript.info: Classes](https://javascript.info/classes)
