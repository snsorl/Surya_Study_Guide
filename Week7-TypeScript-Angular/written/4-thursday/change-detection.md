# Angular Change Detection Strategies

## Learning Objectives
- Describe Angular's change detection mechanism and the role of Zone.js.
- Compare the `Default` and `OnPush` change detection strategies.
- Optimize components using the `OnPush` strategy.
- Apply immutable data structures to trigger change detection cleanly.
- Use the `AsyncPipe` to automatically trigger updates.

---

## Why This Matters
As web applications grow to display hundreds of elements, running change detection checks on every mouse click or keystroke can slow down performance. By default, Angular checks all components on every user interaction. To keep your application running smoothly, you can switch performance-critical components to the **`OnPush`** strategy. This tells Angular to only check the component when its input property references change, saving CPU cycles.

---

## The Concept

### 1. How Default Change Detection Works (Zone.js)
Angular uses a library called **Zone.js** to monitor asynchronous browser tasks (like clicks, timeouts, and HTTP requests). Whenever an async task completes, Zone.js triggers a change detection cycle.
- **Default Strategy**: Angular checks all components in the tree from top to bottom, comparing current template values with previous ones. On large pages, this constant dirty-checking can cause lag.

### 2. The OnPush Strategy
You can configure a component to use the `OnPush` strategy in its `@Component` decorator settings:

```typescript
@Component({
  selector: 'app-user-row',
  templateUrl: './user-row.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush // Enables OnPush optimization
})
export class UserRowComponent {
  @Input() user!: User;
}
```

Under `OnPush`, Angular only checks and updates the component in three scenarios:
1. **An `@Input` property's value reference changes**.
2. An event listener bound inside the component template fires (e.g. a click event).
3. An Observable bound using the **`AsyncPipe`** emits a new value.

### 3. The Importance of Immutability
Under the `OnPush` strategy, Angular checks if an `@Input` has changed using **strict equality comparison (`===`)**. If you mutate an object's property directly (like `user.name = 'Bob'`), the object's memory reference remains the same, so Angular skips change detection, and the UI does not update.
To trigger updates, you must treat data as **immutable**: creating a new object reference when mutating properties:

```typescript
// Unsafe under OnPush (will NOT trigger UI updates):
this.currentUser.name = "Bob"; 

// Safe (creates a new object reference, triggering updates):
this.currentUser = { ...this.currentUser, name: "Bob" };
```

---

## Summary
- **Default Change Detection** checks the entire component tree on every user interaction, monitored by **Zone.js**.
- Use **`ChangeDetectionStrategy.OnPush`** to optimize performance by telling Angular to only check the component when its inputs actually change.
- When using `OnPush`, always treat data as **immutable**, creating new object references to trigger UI updates.

---

## Additional Resources
- [Angular Docs: Change Detection guide](https://angular.dev/best-practices/runtime-performance)
- [Angular Docs: OnPush Strategy API](https://angular.dev/api/core/ChangeDetectionStrategy)
