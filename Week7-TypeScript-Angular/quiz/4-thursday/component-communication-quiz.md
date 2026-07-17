# Practice Quiz: Component Communication

## Part 1: Multiple Choice (MCQ)

### 1. Which decorator property is declared on a child component to receive data passed down from a parent container?
- [ ] A) `@Output()`
- [ ] B) `@Input()`
- [ ] C) `@ViewChild()`
- [ ] D) `@Injectable()`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `@Input()`

**Explanation:** `@Input()` designates that a child component property can receive values from its parent via property binding.
- **Why others are wrong:**
  - A) `@Output()` binds event emitters to send actions up to a parent.
  - C) `@ViewChild()` retrieves direct class references to child component nodes, it's not the standard property-down binding.
  - D) `@Injectable()` registers services for dependency injection.
</details>

---

### 2. What class is used in conjunction with the `@Output()` decorator to trigger events that parent components can listen for?
- [ ] A) `Subject`
- [ ] B) `Observable`
- [ ] C) `EventEmitter`
- [ ] D) `EventRegistry`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `EventEmitter`

**Explanation:** `EventEmitter` extends RxJS Subject to emit custom events containing parameter payloads from child components.
- **Why others are wrong:**
  - A & B) `Subject` and `Observable` are generic RxJS streams used for asynchronous subscriptions but are not directly linked to `@Output` declarations.
  - D) `EventRegistry` is not a valid Angular API class name.
</details>

---

### 3. Under the `ChangeDetectionStrategy.OnPush` strategy, when does Angular execute verification checks on the component?
- [ ] A) On every mouse move event.
- [ ] B) When an `@Input()` property reference changes, or an event is fired inside the component.
- [ ] C) On every interval timer execution.
- [ ] D) Only during bootstrapping setup.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) When an `@Input()` property reference changes, or an event is fired inside the component.

**Explanation:** `OnPush` change detection skips component check sweeps unless input object references change, or component event listeners emit, optimizing rendering speed.
- **Why others are wrong:**
  - A & C) Default change detection checks components on timers and interactions; `OnPush` disables these checks to save processing overhead.
  - D) It checks the component dynamically during runtime, not just at bootstrapping.
</details>

---

### 4. Which decorator allows a parent component to query and access the class properties of a child nested inside its template view?
- [ ] A) `@Input()`
- [ ] B) `@Output()`
- [ ] C) `@ViewChild()`
- [ ] D) `@ContentChild()`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `@ViewChild()`

**Explanation:** `@ViewChild()` queries the template view DOM to return the first matching component, element reference, or directive class instance.
- **Why others are wrong:**
  - A & B) `@Input` and `@Output` establish data and event flow boundaries, not direct component node queries.
  - D) `@ContentChild()` queries content projected into the component via `<ng-content>`, not component view nodes.
</details>

---

## Part 2: True / False

### 5. To support `OnPush` change detection updates successfully, parent components should mutate object properties directly (e.g. `user.name = 'Bob'`) rather than creating new object references.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Since `OnPush` evaluates input modifications using shallow reference checks (triple equals `===`), mutating properties directly in memory will NOT trigger change checks. The parent must create a new object reference (e.g. using object spread `{ ...user, name: 'Bob' }`).
</details>

---

### 6. Event binding from a child to a parent (using parentheses) implements a unidirectional data flow from parent down to child.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Unidirectional data flow goes down from parent to child via `@Input` property bindings. Emitters notify the parent of events going back up the component tree.
</details>

---

## Part 3: Code Prediction

### 7. What is logged to the console when the child component button is clicked?
```typescript
// Parent template has:
// <app-child (notify)="handleNotify($event)"></app-child>
// handleNotify(val: string) { console.log(val); }

@Component({
  selector: 'app-child',
  template: `<button (click)="submit()">Send</button>`
})
export class ChildComponent {
  @Output() notify = new EventEmitter<string>();
  submit() {
    this.notify.emit("Transaction_Complete");
  }
}
```
- [ ] A) `Send`
- [ ] B) `Transaction_Complete`
- [ ] C) `notify`
- [ ] D) Nothing (Reference Error)

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Transaction_Complete`

**Explanation:** The child component emits the string `"Transaction_Complete"` via the `@Output()` emitter, which is intercepted by the parent event listener and logged.
</details>

---

## Part 4: Fill-in-the-Blank

### 8. To pass data dynamically to components constructed at runtime, Angular uses the helper class `___________`.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `ViewContainerRef` (or `ComponentFactoryResolver`)

**Explanation:** `ViewContainerRef` provides an insertion point to dynamically instantiate and attach components at runtime.
</details>
