# Event Emitters in Angular

## Learning Objectives
- Instantiate and configure the `EventEmitter<T>` class.
- Emit structured data payloads to parent components using `.emit()`.
- Compare `EventEmitter` with RxJS `Subject` and understand when to use each.
- Handle event binding parameters inside templates.

---

## Why This Matters
For child components to notify parent components of user interactions, they need a structured messaging system. It is not enough to say "a button was clicked"; we often need to pass payload data, such as a product ID or a text input string. In Angular, we implement this using the **`EventEmitter`** class. Understanding how to configure event payloads and run emit calls is key to building interactive components.

---

## The Concept

### 1. What is an EventEmitter?
An `EventEmitter<T>` is a helper class used in components to emit custom events using the `@Output` decorator. The generic parameter `<T>` defines the type of payload data the event will emit:

```typescript
@Output() deleteRequested = new EventEmitter<number>(); // Emits a number payload
```

### 2. Emitting Payloads
To trigger the event, call the emitter's **`.emit()`** method, passing your payload data as an argument:

```typescript
confirmDelete(itemId: number) {
    this.deleteRequested.emit(itemId); // Fires event with payload
}
```

### 3. Parent Capture (`$event`)
In the parent component's template, bind to the custom event name in parentheses. Access the emitted payload using the reserved parameter **`$event`**:

```html
<!-- Parent Template: -->
<app-item-row (deleteRequested)="onDeleteItem($event)"></app-item-row>
```
The parent's controller method receives this payload as a typed parameter matching the emitter's generic type:
```typescript
// Parent Controller:
onDeleteItem(id: number) {
    console.log("Deleting item ID:", id);
}
```

### 4. EventEmitter vs. RxJS Subject
- **`EventEmitter`**: Designed strictly for component-to-component event routing inside templates using `@Output`. Do not use it inside services for general state sharing.
- **RxJS `Subject` / `BehaviorSubject`**: Designed for general asynchronous data streaming, messaging, and sharing state across services.

---

## Summary
- Instantiate **`EventEmitter<T>`** to declare custom child outputs.
- Trigger events and pass payload data using the **`.emit(value)`** method.
- Access emitted payloads inside parent templates using the **`$event`** variable.
- Use **`EventEmitter`** strictly for parent-child template binding, and **RxJS Subjects** for general state management across services.

---

## Additional Resources
- [Angular Docs: EventEmitter API](https://angular.dev/api/core/EventEmitter)
- [RxJS Docs: Subjects Guide](https://rxjs.dev/guide/subject)
