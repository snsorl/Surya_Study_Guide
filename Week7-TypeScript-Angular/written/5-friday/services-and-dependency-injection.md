# Services and Dependency Injection

## Learning Objectives
- Define the role of Services in isolating business logic.
- Annotate service classes using the `@Injectable()` decorator.
- Explain Angular's Hierarchical Dependency Injection system.
- Register services as singletons using `providedIn: 'root'`.

---

## Why This Matters
Components should be lightweight, focusing purely on rendering layouts and capturing user actions. If you write data fetching logic, validation algorithms, or auth tokens directly inside component classes, your code will become cluttered and hard to test. To keep your code clean, you should move this shared logic into **Services** and inject them into components using Angular's built-in **Dependency Injection (DI)** system.

---

## The Concept

### 1. What is a Service?
A service is a TypeScript class that handles business logic, shares state across components, or communicates with backend APIs. Services are marked with the `@Injectable()` decorator:

```typescript
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root' // Registers the service globally as a singleton
})
export class ConfigService {
  private apiUrl = "http://localhost:8080/api";

  getApiUrl(): string {
    return this.apiUrl;
  }
}
```

### 2. Dependency Injection (DI)
Dependency Injection is a design pattern where a class requests its dependencies from an external system rather than instantiating them manually using the `new` keyword.
To inject a service into a component, declare it as a parameter in the component's constructor using the parameter property shorthand:

```typescript
@Component({ selector: 'app-list' })
export class ListComponent {
  // Angular automatically instantiates and injects the singleton service
  constructor(private config: ConfigService) {
    console.log("API Endpoint:", this.config.getApiUrl());
  }
}
```

### 3. Hierarchical DI & Provider Scopes
You can configure where your services are instantiated:
- **`providedIn: 'root'`**: The service is registered globally. Angular instantiates a single instance (singleton) and shares it across all components, saving memory.
- **Component Scope**: If you list a service in a component's `providers` array, Angular creates a new, separate instance of that service for every instance of the component, isolating state:
  ```typescript
  @Component({
      selector: 'app-widget',
      providers: [WidgetLocalStateService] // Component-scoped instance
  })
  ```

---

## Summary
- **Services** centralize business logic, state sharing, and API calls outside of component classes.
- The **`@Injectable()`** decorator marks a class as available for dependency injection.
- **`providedIn: 'root'`** registers the service globally as a singleton.
- Inject services into components by declaring them as parameters in the component's **constructor**.

---

## Additional Resources
- [Angular Docs: Introduction to Dependency Injection](https://angular.dev/guide/di)
- [Angular Docs: Hierarchical Dependency Injection](https://angular.dev/guide/di/di-in-action)
