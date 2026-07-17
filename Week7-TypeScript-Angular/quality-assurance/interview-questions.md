# Technical Interview Questions: Week 7 (TypeScript & Angular)

This document contains a comprehensive bank of 75 technical interview questions designed to test trainee retention of Week 7's concepts (TypeScript advanced features and Angular architecture basics).

---

## Section 1: Beginner Questions (Foundational Recall) - 70% (52 Questions)

### Q1: What is the main difference between an abstract class and a standard class?
**Keywords:** Instantiation, extends, blueprint
<details>
<summary>Reveal Answer</summary>
An abstract class cannot be instantiated directly using the `new` keyword. It serves strictly as a base blueprint to be extended by subclasses.
</details>

---

### Q2: How do you declare an abstract method in TypeScript?
**Keywords:** abstract keyword, signature, no body
<details>
<summary>Reveal Answer</summary>
Use the `abstract` keyword before the method name, providing only its parameter and return type signatures, without any code body curly braces: `abstract getDetails(): string;`.
</details>

---

### Q3: What is the default access modifier for class members in TypeScript?
**Keywords:** public, default, anywhere
<details>
<summary>Reveal Answer</summary>
All class members are `public` by default, meaning they can be accessed from anywhere inside or outside the class.
</details>

---

### Q4: Explain the difference between `private` and `protected` modifiers.
**Keywords:** subclass, visibility, inheritance
<details>
<summary>Reveal Answer</summary>
`private` members are accessible only within the class where they are declared. `protected` members are accessible within the class and any subclasses extending it.
</details>

---

### Q5: What does the `readonly` modifier enforce?
**Keywords:** reassign, constructor, initialization
<details>
<summary>Reveal Answer</summary>
It prevents a property from being reassigned after its initial assignment (either in its declaration or inside the class constructor).
</details>

---

### Q6: How does TypeScript's compile-time privacy differ from JavaScript's runtime privacy?
**Keywords:** compile-time, runtime, hash symbol
<details>
<summary>Reveal Answer</summary>
TypeScript's `private` keyword is only checked at compile-time and is stripped from output JavaScript files. JavaScript's native `#` prefix private fields are enforced at runtime by browser engines.
</details>

---

### Q7: What is the Parameter Property Shorthand?
**Keywords:** constructor parameters, visibility modifier, bind
<details>
<summary>Reveal Answer</summary>
It is a syntax shortcut where prefixing a constructor parameter with an access modifier (like `public` or `private`) automatically declares, initializes, and binds the property to the class instance.
</details>

---

### Q8: What does the `Partial<T>` utility type do?
**Keywords:** optional, properties, question mark
<details>
<summary>Reveal Answer</summary>
It constructs a new type shape with all properties of the original type `T` set to optional (`?`).
</details>

---

### Q9: What does the `Required<T>` utility type do?
**Keywords:** mandatory, optional, absolute
<details>
<summary>Reveal Answer</summary>
It constructs a new type shape with all properties of the original type `T` set to mandatory, removing any optional flag indicators.
</details>

---

### Q10: What does the `Readonly<T>` utility type do?
**Keywords:** readonly, frozen, mutations
<details>
<summary>Reveal Answer</summary>
It constructs a new type shape where all properties of type `T` are marked as read-only, preventing reassignment mutations.
</details>

---

### Q11: What is the difference between `Pick<T, K>` and `Omit<T, K>`?
**Keywords:** include, exclude, keys
<details>
<summary>Reveal Answer</summary>
`Pick` constructs a type by selecting a set of keys `K` from `T`. `Omit` constructs a type by removing a set of keys `K` from `T`.
</details>

---

### Q12: What is the purpose of the `Record<K, V>` utility type?
**Keywords:** key-value, dictionary, map
<details>
<summary>Reveal Answer</summary>
It constructs an object type whose keys are of type `K` and whose values are of type `V`, commonly used for lookup dictionaries.
</details>

---

### Q13: What does the `Exclude<T, U>` utility type do?
**Keywords:** unions, filter, remove
<details>
<summary>Reveal Answer</summary>
It filters out all union members of `T` that are assignable to type `U`.
</details>

---

### Q14: What does the `Extract<T, U>` utility type do?
**Keywords:** unions, shared, select
<details>
<summary>Reveal Answer</summary>
It extracts all union members of `T` that are also assignable to type `U`.
</details>

---

### Q15: What does the `NonNullable<T>` utility type do?
**Keywords:** null, undefined, filter
<details>
<summary>Reveal Answer</summary>
It removes `null` and `undefined` from the type `T`.
</details>

---

### Q16: What does the `ReturnType<T>` utility type do?
**Keywords:** function, return signature, extract
<details>
<summary>Reveal Answer</summary>
It extracts the return type signature of a function type declaration `T`.
</details>

---

### Q17: What is structural subtyping?
**Keywords:** duck typing, shape compatibility, properties
<details>
<summary>Reveal Answer</summary>
It is a type-checking system where type compatibility is determined by the shape of the properties and methods, rather than by nominal inheritance names.
</details>

---

### Q18: What is a tuple in TypeScript?
**Keywords:** array, index types, fixed length
<details>
<summary>Reveal Answer</summary>
A tuple is a specialized array type where the types of elements at specific index positions are predefined and fixed.
</details>

---

### Q19: What is the purpose of a decorator?
**Keywords:** meta-programming, annotations, runtime edit
<details>
<summary>Reveal Answer</summary>
A decorator is a special declaration that can be attached to classes, methods, accessors, properties, or parameters to modify their behavior at runtime.
</details>

---

### Q20: What setting must be enabled in `tsconfig.json` to support decorators?
**Keywords:** experimentalDecorators, compile, settings
<details>
<summary>Reveal Answer</summary>
You must set `"experimentalDecorators": true` under compiler options in `tsconfig.json`.
</details>

---

### Q21: What is a Single Page Application (SPA)?
**Keywords:** single index, routing, dynamic swap
<details>
<summary>Reveal Answer</summary>
An SPA is a web application that loads a single HTML shell index page and updates the user interface dynamically via client-side routing, without full page refreshes.
</details>

---

### Q22: What is the purpose of the Angular CLI?
**Keywords:** command line, scaffolding, build, serve
<details>
<summary>Reveal Answer</summary>
It is a command-line tool used to initialize, develop, scaffold, test, lint, and build Angular applications.
</details>

---

### Q23: What command starts the Angular development server?
**Keywords:** ng serve, port 4200, local host
<details>
<summary>Reveal Answer</summary>
The command `ng serve` compiles the app and hosts it locally (by default at `http://localhost:4200`).
</details>

---

### Q24: What is the role of an `@NgModule` in Angular?
**Keywords:** compilation context, declarations, imports
<details>
<summary>Reveal Answer</summary>
An `@NgModule` defines the compilation context for a group of components, directives, pipes, and services, specifying which files are declarations, imports, and exports.
</details>

---

### Q25: What decorator is used to define an Angular component class?
**Keywords:** @Component, metadata, selector
<details>
<summary>Reveal Answer</summary>
The `@Component` decorator, which attaches template HTML, stylesheet styles, and tag selector metadata to a controller class.
</details>

---

### Q26: What is the role of the component selector?
**Keywords:** HTML tag, mount selector, target element
<details>
<summary>Reveal Answer</summary>
The selector defines the custom HTML tag name (e.g. `<app-list>`) that Angular looks for in parent templates to render the component.
</details>

---

### Q27: What is interpolation in Angular templates?
**Keywords:** double curly braces, template expression, string conversion
<details>
<summary>Reveal Answer</summary>
Interpolation uses double curly braces `{{ value }}` to render class property values as strings inside HTML templates.
</details>

---

### Q28: What is property binding in Angular templates?
**Keywords:** square brackets, attribute source, class property
<details>
<summary>Reveal Answer</summary>
Property binding uses square brackets `[property]="value"` to flow property data one-way from the component class to an HTML element property.
</details>

---

### Q29: What is event binding in Angular templates?
**Keywords:** parentheses, user trigger, handler callback
<details>
<summary>Reveal Answer</summary>
Event binding uses parentheses `(event)="handler()"` to flow actions one-way from the HTML template to invoke component class methods.
</details>

---

### Q30: What is two-way data binding in Angular templates?
**Keywords:** banana-in-a-box, ngModel, bidirectional sync
<details>
<summary>Reveal Answer</summary>
Two-way binding uses the banana-in-a-box syntax `[(ngModel)]="value"` to synchronize data bi-directionally between class properties and input forms.
</details>

---

### Q31: What is a structural directive in Angular?
**Keywords:** asterisk prefix, add remove, DOM tree
<details>
<summary>Reveal Answer</summary>
A structural directive modifies the structure of the DOM tree by dynamically adding or removing elements (prefixed with an asterisk like `*ngIf` and `*ngFor`).
</details>

---

### Q32: What is an attribute directive in Angular?
**Keywords:** style behavior, existing element, attributes
<details>
<summary>Reveal Answer</summary>
An attribute directive modifies the styling or behavior of an existing DOM element without altering the DOM tree structure.
</details>

---

### Q33: Explain the difference between `*ngIf` and `[hidden]`.
**Keywords:** element destruction, display none, DOM memory
<details>
<summary>Reveal Answer</summary>
`*ngIf` completely adds or removes the element in the DOM tree, whereas `[hidden]` toggles a CSS `display: none` style on the element, keeping it in memory.
</details>

---

### Q34: What is the purpose of `trackBy` in an `*ngFor` loop directive?
**Keywords:** rendering performance, identity tracking, redraws optimization
<details>
<summary>Reveal Answer</summary>
`trackBy` optimizes list rendering performance by tracking elements by a unique identifier, preventing Angular from redrawing the entire DOM list when items change.
</details>

---

### Q35: What directive is used to render multiple conditional template structures?
**Keywords:** ngSwitch, ngSwitchCase, ngSwitchDefault
<details>
<summary>Reveal Answer</summary>
The `[ngSwitch]` directive, which evaluates an expression and renders nested template components marked with `*ngSwitchCase`.
</details>

---

### Q36: What is a Pipe in Angular?
**Keywords:** formatting filters, view transforms, formatting data
<details>
<summary>Reveal Answer</summary>
A pipe is a template formatter used to transform raw data values into user-friendly display formats (like currency or date strings) inside templates.
</details>

---

### Q37: How do you declare a custom pipe class?
**Keywords:** @Pipe decorator, PipeTransform, transform method
<details>
<summary>Reveal Answer</summary>
Decorate a class with the `@Pipe` decorator, implement the `PipeTransform` interface, and write the `transform()` method logic.
</details>

---

### Q38: What is the difference between a pure and an impure pipe?
**Keywords:** change detection, input arguments, re-evaluation triggers
<details>
<summary>Reveal Answer</summary>
A pure pipe only re-evaluates when its input argument references change. An impure pipe re-runs on every change detection cycle, regardless of input changes.
</details>

---

### Q39: What is `ViewEncapsulation` in Angular styling?
**Keywords:** styles isolation, css scope, emulation
<details>
<summary>Reveal Answer</summary>
`ViewEncapsulation` configures style isolation boundaries for component styling rules (options include `Emulated`, `None`, and `ShadowDom`).
</details>

---

### Q40: What are lifecycle hooks in Angular?
**Keywords:** component execution cycle, lifecycle interface, execution checkpoints
<details>
<summary>Reveal Answer</summary>
Lifecycle hooks are callback methods that run at specific checkpoints during a component's lifecycle (creation, updates, destruction).
</details>

---

### Q41: Explain the purpose of `ngOnInit`.
**Keywords:** initialization, fetching data, property binds complete
<details>
<summary>Reveal Answer</summary>
`ngOnInit` executes once after Angular completes binding the component's initial input properties, making it the ideal hook for data-fetching.
</details>

---

### Q42: Explain the purpose of `ngOnDestroy`.
**Keywords:** lifecycle destruction, clean up, memory leaks prevention
<details>
<summary>Reveal Answer</summary>
`ngOnDestroy` executes right before a component is destroyed, making it the ideal hook for cleaning up active subscriptions and timers.
</details>

---

### Q43: What decorator defines an input boundary in child components?
**Keywords:** @Input(), data passed down, property bindings
<details>
<summary>Reveal Answer</summary>
The `@Input()` property decorator, which allows parent components to flow data down to the child.
</details>

---

### Q44: What decorator defines an output event boundary in child components?
**Keywords:** @Output(), event emissions up, trigger events
<details>
<summary>Reveal Answer</summary>
The `@Output()` property decorator, which allows child components to emit event payloads up to the parent using an `EventEmitter`.
</details>

---

### Q45: What is the default Change Detection Strategy in Angular?
**Keywords:** Default strategy, check always, dirty checks sweep
<details>
<summary>Reveal Answer</summary>
The default strategy is `ChangeDetectionStrategy.Default`, which checks the entire component tree on every change detection cycle.
</details>

---

### Q46: What is the `OnPush` Change Detection Strategy?
**Keywords:** ChangeDetectionStrategy.OnPush, reference comparison checks, skip sweeps
<details>
<summary>Reveal Answer</summary>
It is an optimized change detection strategy where Angular only checks a component if its `@Input` properties receive new references, or an internal event fires.
</details>

---

### Q47: What does the Angular RouterOutlet directive serve as?
**Keywords:** router-outlet, placeholders tags, routing targets
<details>
<summary>Reveal Answer</summary>
`<router-outlet>` acts as a placeholder tag where the Angular Router mounts the active route's component dynamically.
</details>

---

### Q48: How do you perform client-side link navigation in Angular templates?
**Keywords:** routerLink, templates click, prevent refreshes
<details>
<summary>Reveal Answer</summary>
Use the `routerLink` directive on elements (like `<a routerLink="/path">`) to trigger navigation without browser page refreshes.
</details>

---

### Q49: What is a Route Guard in Angular?
**Keywords:** path protection, intercept routing, boolean check
<details>
<summary>Reveal Answer</summary>
A guard is an interface or function that intercepts routing requests to check if navigation should be allowed (e.g. checking authentication).
</details>

---

### Q50: How do you declare a singleton service provider?
**Keywords:** @Injectable(), providedIn root, dependency injections
<details>
<summary>Reveal Answer</summary>
Decorate a service class using `@Injectable({ providedIn: 'root' })`, which registers a single shared instance of the service application-wide.
</details>

---

### Q51: What package module must be imported to configure REST API integrations?
**Keywords:** HttpClientModule, network services, imports register
<details>
<summary>Reveal Answer</summary>
You must import `HttpClientModule` in your root module configuration class.
</details>

---

### Q52: What command audits npm dependencies for vulnerability risks?
**Keywords:** npm audit, dependency scans, CVE security warnings
<details>
<summary>Reveal Answer</summary>
Running `npm audit` scans the project's dependency tree against vulnerability databases.
</details>

---

## Section 2: Intermediate Questions (Scenario / Application) - 25% (19 Questions)

### Q53: Under what condition does a subclass extending an abstract class fail to compile?
**Keywords:** compile error, missing implementations, abstract abstract methods
<details>
<summary>Reveal Answer</summary>
If the concrete subclass fails to implement any of the abstract methods declared in the parent abstract class, the compiler will throw an error.
</details>

---

### Q54: Why does compiler validation fail when assigning an object containing an extra property to a strictly typed interface reference?
**Keywords:** excess property checking, literal assignment check, type safety checks
<details>
<summary>Reveal Answer</summary>
When assigning an object literal directly to a typed interface, TypeScript performs "excess property checking." If the object literal has properties not declared in the interface, it throws a compilation error.
</details>

---

### Q55: How do you declare an update payload type using utility types for an entity containing `id` (readonly) and `title` (required)?
**Keywords:** Omit, Partial, composition
<details>
<summary>Reveal Answer</summary>
First, use `Omit` to strip the `id` field, then apply `Partial` to make the remaining fields optional: `type UpdateDto = Partial<Omit<Entity, "id">>;`.
</details>

---

### Q56: How can you write a method decorator that times function execution duration?
**Keywords:** descriptor.value, apply, original method wrapper
<details>
<summary>Reveal Answer</summary>
Override `descriptor.value` inside the decorator function, capturing start/end timestamps around the execution of the original method using `originalMethod.apply(this, args)`.
</details>

---

### Q57: Why does `[(ngModel)]` fail to compile if you create a fresh Angular workspace app and write it in a template?
**Keywords:** Template parse error, FormsModule missing, imports array register
<details>
<summary>Reveal Answer</summary>
Because the `ngModel` directive is declared inside the `FormsModule`. You must explicitly import `FormsModule` and add it to the `@NgModule` imports array.
</details>

---

### Q58: Why does a standard button click event binding fail to update a list if the component uses `OnPush` and the parent mutates the array?
**Keywords:** change detection skip, mutation in-place, reference comparison check
<details>
<summary>Reveal Answer</summary>
Under `OnPush` change detection, Angular checks the component only when its `@Input` property reference changes. Mutating the array in place does not change the array's reference, so the update is skipped.
</details>

---

### Q59: How do you implement a fallback route when a user enters a invalid URL path?
**Keywords:** wildcard path, redirectTo redirect, route table last entry
<details>
<summary>Reveal Answer</summary>
Add a wildcard route mapping to the end of the routes array: `{ path: '**', redirectTo: '/home' }`.
</details>

---

### Q60: How does the `async` pipe prevent application memory leaks?
**Keywords:** observable stream subscribe, template rendering, automatic unsubscribe
<details>
<summary>Reveal Answer</summary>
The `async` pipe automatically subscribes to an Observable in the template and unsubscribes when the component is destroyed, preventing memory leaks from open subscriptions.
</details>

---

### Q61: What is the role of an HTTP Interceptor in securing applications?
**Keywords:** clone requests, intercept HTTP headers, authorization token injection
<details>
<summary>Reveal Answer</summary>
It intercepts all outgoing HTTP requests, cloning them to append authorization headers (like Bearer tokens) before forwarding them to the server.
</details>

---

### Q62: When should you use a Custom Pipe instead of a component method to format template display strings?
**Keywords:** pure pipe caching, render performance cycles, CPU processing saving
<details>
<summary>Reveal Answer</summary>
Use a custom pipe when performance matters. Pure pipes cache their output values and only re-run when their inputs change, whereas component methods run on every change detection cycle.
</details>

---

### Q63: You are receiving a CORS error when fetching data from your Spring Boot API in Angular. How do you resolve it?
**Keywords:** @CrossOrigin backend annotation, gateway filters, domain whitelist mappings
<details>
<summary>Reveal Answer</summary>
You must configure CORS on the backend API (e.g. adding `@CrossOrigin(origins = "http://localhost:4200")` to your Spring Boot controllers) to whitelist the Angular domain.
</details>

---

### Q64: What is the difference between `constructor` initialization and the `ngOnInit` lifecycle hook?
**Keywords:** constructor javascript engine, property input bindings ready, lifecycle mount
<details>
<summary>Reveal Answer</summary>
The class `constructor` is called by the JavaScript engine when the instance is created. `ngOnInit` is run by Angular after the component's `@Input` properties are fully initialized.
</details>

---

### Q65: How do you pass route parameters (like an entity ID) to a dynamic detail view path?
**Keywords:** colon route placeholder, ActivatedRoute injection, paramMap subscription
<details>
<summary>Reveal Answer</summary>
Configure the route with a colon parameter placeholder (e.g., `path: 'product/:id'`), inject the `ActivatedRoute` service in the component, and subscribe to `route.paramMap` to read the ID.
</details>

---

### Q66: Why is `providedIn: 'root'` preferred over registering services inside local component `providers` arrays?
**Keywords:** singleton services instance, memory share pool, tree shaking builds
<details>
<summary>Reveal Answer</summary>
Using `providedIn: 'root'` registers a single shared instance of the service application-wide and allows the service to be tree-shaken (removed from the bundle if unused).
</details>

---

### Q67: What does `npm audit fix --force` execute, and what risk does it introduce?
**Keywords:** breaking version upgrades, major package updates, build breaks compatibility
<details>
<summary>Reveal Answer</summary>
It automatically updates vulnerable dependencies to their latest versions, even if they introduce breaking major version updates. This runs the risk of breaking compile and build setups.
</details>

---

### Q68: How do you handle REST API errors in a component service subscription?
**Keywords:** catchError operator, throwError stream, UI error warnings display
<details>
<summary>Reveal Answer</summary>
Use the RxJS `catchError` operator in the service pipeline to intercept failures, and check the error status to display user-friendly messages.
</details>

---

### Q69: Explain the difference between `CanActivate` and `CanDeactivate` route guards.
**Keywords:** enter route, leave route, permission checks
<details>
<summary>Reveal Answer</summary>
`CanActivate` checks if a user is allowed to enter a route. `CanDeactivate` checks if a user is allowed to leave the current route (e.g., checking for unsaved changes).
</details>

---

### Q70: How do you pass data up from a child component to its parent?
**Keywords:** @Output event emitter, notify parent callback, event bindings template
<details>
<summary>Reveal Answer</summary>
Declare an `@Output` property with an `EventEmitter` inside the child. In the parent template, bind to the custom event and handle the emitted data payload.
</details>

---

### Q71: How do you enforce strict type-safety on raw database JSON outputs?
**Keywords:** interface cast type, typed get HttpClient method, generics signatures
<details>
<summary>Reveal Answer</summary>
Pass a generic type parameter to the `HttpClient` get method (e.g., `http.get<Product>(url)`), which typed-casts the output stream.
</details>

---

## Section 3: Advanced Questions (Deep Dive System Cases) - 5% (4 Questions)

### Q72: Explain how Angular's dependency injection system resolves dependencies hierarchically.
**Keywords:** injector tree bubble, component level providers, root module singletons
<details>
<summary>Reveal Answer</summary>
Angular searches for providers hierarchically up the component tree. If a service is provided in a component's local `providers` array, that component (and its children) get a dedicated instance of the service. If not found locally, search bubbles up to the module injectors, and finally to the root injector to find the singleton provider.
</details>

---

### Q73: Compare Emulated, None, and ShadowDom styles encapsulation.
**Keywords:** view encapsulation modes, host custom attribute, shadow root boundaries
<details>
<summary>Reveal Answer</summary>
- `Emulated` (default) scopes styling to the component by appending custom attributes to compiled DOM nodes.
- `None` applies styles globally, making them affect the entire page.
- `ShadowDom` uses native browser Shadow DOM boundaries to fully isolate styles from the rest of the application.
</details>

---

### Q74: Why does a child component using `OnPush` change detection fail to update its UI when a parent pushes an item to a bound array parameter?
**Keywords:** mutation reference check bypass, shallow comparison checks, reference re-allocations
<details>
<summary>Reveal Answer</summary>
Because array mutations (like `.push()`) modify the array's contents in place, preserving the same array reference in memory. Since `OnPush` only checks inputs using shallow reference comparison, it does not detect the modification, skipping component updates. To resolve this, you must re-allocate the array reference (e.g. `this.items = [...this.items, newItem]`).
</details>

---

### Q75: How do you handle security vulnerabilities in packages that cannot be resolved by standard npm audits?
**Keywords:** package overrides configs, lockfile resolution mappings, dependency replacements
<details>
<summary>Reveal Answer</summary>
If `npm audit fix` cannot resolve a vulnerability, you can manually override the package mapping by declaring resolution versions inside the `overrides` block in `package.json`, forcing dependencies to use safe, patched versions.
</details>
