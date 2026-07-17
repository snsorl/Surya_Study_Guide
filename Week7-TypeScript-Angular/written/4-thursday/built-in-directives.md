# Built-in Directives Reference

## Learning Objectives
- Review structural directives (`*ngIf`, `*ngFor`, `*ngSwitch`) and attribute directives (`ngClass`, `ngStyle`, `ngModel`).
- Identify syntax variations and edge cases for built-in directives.
- Use `index`, `first`, `last`, and `even`/`odd` variables inside `*ngFor` loops.
- Handle null checks inside `*ngIf` statements.

---

## Why This Matters
Directives are the core tool for adding layout structure and dynamic styling to Angular templates. While you may be familiar with basic usages, real-world templates require handling edge cases: alternating row colors in lists, accessing list indices, displaying custom messages for empty tables, and handling null checks safely. This reference sheet consolidates all built-in directives, syntax variants, and common edge cases.

---

## The Reference

### 1. Structural Directives

#### `*ngIf` Syntax Variants

- **Standard Null Check**:
  ```html
  <div *ngIf="userProfile">Welcome!</div>
  ```
- **Else Template**:
  ```html
  <div *ngIf="isLoaded; else loadingTmpl">Loaded Content</div>
  <ng-template #loadingTmpl><p>Loading...</p></ng-template>
  ```

#### `*ngFor` Context Variables
Inside an `*ngFor` loop, you can export local template variables to access details about the current loop iteration:
- **`index`**: The current item index (0-indexed).
- **`first` / `last`**: Booleans indicating if the current item is the first or last item in the list.
- **`even` / `odd`**: Booleans indicating if the current index is even or odd (useful for alternate-colored table rows).

```html
<tr *ngFor="let item of items; let idx = index; let isOdd = odd" 
    [class.striped-row]="isOdd">
  <td>{{ idx + 1 }}</td>
  <td>{{ item.name }}</td>
</tr>
```

#### `*ngSwitch` Grouping
`*ngSwitch` dynamically selects and renders elements matching a specific condition value. It is mapped using three directives:

```html
<div [ngSwitch]="userRole">
  <p *ngSwitchCase="'ADMIN'">Administrator Panel</p>
  <p *ngSwitchCase="'USER'">Standard Dashboard</p>
  <p *ngSwitchDefault>Guest Access Only</p>
</div>
```

### 2. Attribute Directives

#### `ngClass` Syntax Formats
- **Object Format** (Recommended for toggling multiple classes based on boolean conditions):
  ```html
  <div [ngClass]="{ 'active': isActive, 'disabled': isDisabled }">Content</div>
  ```
- **String Format**:
  ```html
  <div [ngClass]="'class-one class-two'">Content</div>
  ```
- **Array Format**:
  ```html
  <div [ngClass]="['class-one', 'class-two']">Content</div>
  ```

#### `ngStyle` Format
```html
<div [ngStyle]="{ 'color': fontColor, 'font-size.px': fontSize }">Content</div>
```

---

## Summary
- **`*ngIf`** displays elements dynamically, supporting `else` fallback templates.
- **`*ngFor`** exposes local variables like **`index`**, **`first`**, **`last`**, and **`even` / `odd`** to customize item layouts.
- **`*ngSwitch`** dynamically selects and displays one element matching a specific case value.
- **`ngClass`** dynamically applies CSS classes, supporting **objects, arrays, and strings** as inputs.

---

## Additional Resources
- [Angular Docs: Built-in Directives guide](https://angular.dev/guide/directives)
- [Angular Docs: NgForOf API](https://angular.dev/api/common/NgForOf)
