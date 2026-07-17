# Decorators in TypeScript

## Learning Objectives
- Define Decorators and explain their meta-programming role.
- Enable decorator compilation in `tsconfig.json` (`experimentalDecorators`).
- Implement Class, Method, Property, and Parameter decorators.
- Describe how Angular leverages `@Component` and `@Injectable` decorators to bind metadata.
- Design Custom Decorator Factories.

---

## Why This Matters
If you have written Java code, you are familiar with Annotations (like `@RestController`, `@Autowired`). Annotations do not modify code behavior directly; they attach metadata that frameworks use at compile-time or runtime. In TypeScript, this meta-programming feature is called **Decorators**. Since Angular uses decorators (like `@Component`, `@Directive`, `@Injectable`) to wire up dependencies and configuration metadata, understanding how decorators operate is essential for writing Angular code.

---

## The Concept

### 1. Enabling Decorators in tsconfig.json
TypeScript decorators are currently an experimental feature (though standard in Angular). To compile them, you must explicitly enable them in your `tsconfig.json` compiler options:

```json
{
  "compilerOptions": {
    "experimentalDecorators": true,
    "emitDecoratorMetadata": true
  }
}
```

### 2. What is a Decorator?
A decorator is a special function that can be attached to a class declaration, method, accessor, property, or parameter. It uses the `@expression` syntax, where the expression must evaluate to a function that is called at runtime with details about the decorated declaration.

#### Decorator Types:
- **Class Decorators**: Modify or replace the class constructor definition.
- **Method Decorators**: Intercept method calls, modify method descriptors, or add logging.
- **Property Decorators**: Monitor or modify property access.
- **Parameter Decorators**: Inject parameters into constructors or methods.

### 3. Decorator Factories
A decorator factory is a function that returns the actual decorator function. This allows you to pass custom configuration parameters to the decorator:

```typescript
function Logger(prefix: string) {
    return function(target: Function) {
        console.log(`${prefix}: decorating class ${target.name}`);
    };
}

@Logger("API_CONTROLLER")
class AuthController {}
```

### 4. How Angular Uses Decorators
Angular uses decorators to turn standard classes into framework components:
- **`@Component`**: Attaches HTML templates and CSS styles to a class, registering it in Angular's rendering engine.
- **`@Injectable`**: Injects class dependencies automatically using Angular's Dependency Injection system.

---

## Code Examples

### Implementing a Method Logger Decorator
Below is a method decorator that logs arguments and returns values during execution:

```typescript
function LogExecution(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    const originalMethod = descriptor.value;

    // Redefining the method descriptor
    descriptor.value = function(...args: any[]) {
        console.log(`[Log] Calling method '${propertyKey}' with args:`, args);
        const result = originalMethod.apply(this, args);
        console.log(`[Log] Method '${propertyKey}' returned:`, result);
        return result;
    };
}

class Calculator {
    @LogExecution
    add(a: number, b: number): number {
        return a + b;
    }
}

// Verification usage:
const calc = new Calculator();
calc.add(5, 7);
// Console Output:
// [Log] Calling method 'add' with args: [5, 7]
// [Log] Method 'add' returned: 12
```

---

## Summary
- Enable decorators in your compilation pipeline using **`experimentalDecorators: true`**.
- Decorators use the **`@decorator`** syntax and run automatically during class registration.
- **Decorator factories** are functions that return decorators, allowing configuration inputs.
- Angular uses decorators to inject metadata and register classes as **Components** or **Services**.

---

## Additional Resources
- [TypeScript Docs: Decorators handbook](https://www.typescriptlang.org/docs/handbook/decorators.html)
- [Angular Docs: Introduction to Components](https://angular.dev/guide/components)
