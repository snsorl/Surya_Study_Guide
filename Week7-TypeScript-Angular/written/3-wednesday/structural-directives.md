# Structural Directives in Angular

## Learning Objectives
- Define Structural Directives and explain how they modify the DOM layout.
- Implement conditional templates using `*ngIf` and `else` blocks.
- Loop through collections using `*ngFor` with `trackBy` optimizations.
- Render dynamic components using `*ngSwitch`.
- Contrast `<ng-template>` and `<ng-container>` wrappers.

---

## Why This Matters
When rendering search results, user dashboards, or shopping carts, we need layout controls: hiding elements if data is loading, repeating card grids for each product in a list, and showing different statuses based on database states. In Angular, we manage these DOM modifications using **Structural Directives**. Understanding how to optimize lists with `trackBy` and structure templates cleanly using wrappers prevents visual rendering lag.

---

## The Concept

### 1. What is a Structural Directive?
A structural directive modifies the DOM structure by adding, removing, or manipulating elements. They are easily identified by the **asterisk prefix `*`** (e.g. `*ngIf`, `*ngFor`):

```html
<!-- If truthy, the element is added to the DOM; if falsy, it is completely removed -->
<div *ngIf="isLoggedIn">Welcome Back!</div>
```

### 2. Conditional Rendering (`*ngIf` with Else)
You can define an alternative template to show when your conditional expression resolves to falsy using the `else` keyword pointing to an `<ng-template>` reference variable:

```html
<div *ngIf="user; else guestTemplate">
  <p>User: {{ user.name }}</p>
</div>

<ng-template #guestTemplate>
  <p>Please log in.</p>
</ng-template>
```

### 3. List Iteration (`*ngFor` with `trackBy`)
To loop through arrays and render elements, use `*ngFor`.
*Performance Warning:* By default, if the list data changes, Angular will tear down and recreate all DOM elements in the list. To optimize this, use the **`trackBy`** helper method. This tells Angular to only update elements whose unique identifier (like `id`) has changed, preventing performance lag on large lists:

```html
<ul>
  <li *ngFor="let item of items; trackBy: trackById">
    {{ item.name }}
  </li>
</ul>
```
In your TypeScript class, define the helper method:
```typescript
trackById(index: number, item: any): number {
    return item.id;
}
```

### 4. Layout Wrappers: `<ng-template>` vs. `<ng-container>`
- **`<ng-template>`**: Defines a template block that is **not rendered** in the DOM by default. It must be called programmatically (like by an `*ngIf` else reference).
- **`<ng-container>`**: A structural grouping wrapper that does not generate a physical tag in the output DOM tree. This is useful when you want to group elements under a directive without polluting your CSS styles with extra nested `<div>` tags:

```html
<!-- Valid: groups elements under *ngIf without adding a wrapper tag to the DOM -->
<ng-container *ngIf="isActive">
  <h3>Active Session</h3>
  <button>Log Out</button>
</ng-container>
```

---

## Summary
- **Structural Directives (`*`)** modify the layout of the DOM tree by adding or removing elements.
- Use **`<ng-template>`** to declare template blocks that are hidden by default.
- Use **`<ng-container>`** to group elements under a structural directive without adding wrapper tags to the DOM.
- Use the **`trackBy`** optimization inside **`*ngFor`** to prevent unnecessary DOM redraws on lists.

---

## Additional Resources
- [Angular Docs: Structural Directives](https://angular.dev/guide/directives/structural-directives)
- [Angular Docs: TrackBy Reference](https://angular.dev/api/common/NgForOf#change-propagation)
