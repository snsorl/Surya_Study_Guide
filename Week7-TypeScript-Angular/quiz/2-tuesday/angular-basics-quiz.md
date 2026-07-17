# Practice Quiz: Angular Workspace Basics & Data Bindings

## Part 1: Multiple Choice (MCQ)

### 1. Which configuration file defines build configurations, assets directories, and execution scripts for an Angular project?
- [ ] A) `package.json`
- [ ] B) `angular.json`
- [ ] C) `tsconfig.json`
- [ ] D) `webpack.config.js`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `angular.json`

**Explanation:** `angular.json` is the workspace configuration file for the Angular CLI. It controls builds, tests, styles, and script bundles.
- **Why others are wrong:**
  - A) `package.json` manages third-party package dependencies and generic scripts.
  - C) `tsconfig.json` contains TypeScript compiler rules.
  - D) Webpack configurations are encapsulated by the CLI, so `webpack.config.js` is not exposed by default.
</details>

---

### 2. Which type of data binding flows data from the template back to the component class (capturing user interactions)?
- [ ] A) Interpolation `{{ value }}`
- [ ] B) Property Binding `[disabled]`
- [ ] C) Event Binding `(click)`
- [ ] D) Two-Way Binding `[(ngModel)]`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Event Binding `(click)`

**Explanation:** Event Binding flows events from the view (HTML element) back to the component controller to invoke handler methods.
- **Why others are wrong:**
  - A & B) Interpolation and Property Binding flow data one-way from class variables down to the HTML template.
  - D) Two-Way Binding flows data in both directions simultaneously.
</details>

---

### 3. To use two-way data binding `[(ngModel)]` in an Angular component, which module must be imported in the `@NgModule` configuration?
- [ ] A) `HttpClientModule`
- [ ] B) `CommonModule`
- [ ] C) `FormsModule`
- [ ] D) `RouterModule`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `FormsModule`

**Explanation:** The `[(ngModel)]` directive is declared inside the `@angular/forms` package, meaning `FormsModule` must be imported to parse it.
- **Why others are wrong:**
  - A) `HttpClientModule` handles HTTP network requests.
  - B) `CommonModule` contains basic directives like `*ngIf` and `*ngFor`.
  - D) `RouterModule` manages routing table configurations.
</details>

---

### 4. What is the default port for local development using the `ng serve` command?
- [ ] A) `8080`
- [ ] B) `3000`
- [ ] C) `4200`
- [ ] D) `5000`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `4200`

**Explanation:** By default, Angular's development web server hosts the application at `http://localhost:4200`.
- **Why others are wrong:**
  - A) `8080` is the default port for Spring Boot.
  - B) `3000` is common for Node/Express servers.
  - D) `5000` is often used by Flask or ASP.NET.
</details>

---

### 5. In the `@Component` decorator, which property specifies the CSS stylesheet paths for styling boundaries?
- [ ] A) `templateUrl`
- [ ] B) `selector`
- [ ] C) `styleUrls`
- [ ] D) `stylesList`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `styleUrls`

**Explanation:** The `styleUrls` metadata property takes an array of file paths containing external CSS rules.
- **Why others are wrong:**
  - A) `templateUrl` points to the external HTML template file.
  - B) `selector` defines the HTML tag selector name.
  - D) `stylesList` is not a valid component metadata parameter.
</details>

---

## Part 2: True / False

### 6. A Single Page Application (SPA) reloads the entire page container from the server on every tab route change.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** SPAs load a single HTML shell index and update sections dynamically using client-side routing, preventing full page refreshes.
</details>

---

### 7. The `@NgModule` decorator defines the compilation context for components, directives, and pipes.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** `@NgModule` groups related elements together to coordinate bootstrapping compilation targets.
</details>

---

## Part 3: Code Prediction

### 8. What value is displayed inside the input box when the component renders?
```typescript
@Component({
  selector: 'app-test',
  template: `<input type="text" [value]="pageTitle">`
})
export class TestComponent {
  pageTitle = "Spring Boot Admin";
}
```
- [ ] A) `pageTitle`
- [ ] B) `Spring Boot Admin`
- [ ] C) An empty input box
- [ ] D) `undefined`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `Spring Boot Admin`

**Explanation:** The property binding syntax `[value]="pageTitle"` binds the input's native value property to the component variable's string contents.
</details>

---

### 9. What is the result of clicking the button in this template?
```typescript
@Component({
  selector: 'app-counter',
  template: `<button (click)="increment(2)">Click</button>`
})
export class CounterComponent {
  count = 0;
  increment(step: number) {
    this.count += step;
  }
}
```
- [ ] A) Compilation failure
- [ ] B) `count` remains `0`
- [ ] C) `count` becomes `2`
- [ ] D) Returns a reference error

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `count` becomes `2`

**Explanation:** The event binding statement executes the `increment(2)` handler on the component, updating `count` to `2`.
</details>

---

## Part 4: Fill-in-the-Blank

### 10. The Angular CLI command used to compile a component shell is `ng ___________ component <name>`.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `generate` (or `g`)

**Explanation:** The command `ng generate component` (or `ng g c`) generates the TypeScript, HTML, CSS, and spec test files for a new component.
</details>
