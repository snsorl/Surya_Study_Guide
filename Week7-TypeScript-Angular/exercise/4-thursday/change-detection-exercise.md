# Lab: Optimizing Product Cards with OnPush

## Objectives
- Apply the `OnPush` Change Detection Strategy to components.
- Explain the role of data immutability when triggering UI updates.
- Refactor array methods to return new object references.

---

## Scenario
To optimize rendering performance, we want to convert the `ProductCard` component to use the **`OnPush`** change detection strategy. In this lab, you will update the component configuration and refactor your parent's state methods to ensure UI updates trigger correctly.

---

## Core Tasks

### Task 1: Enable OnPush Strategy
Open `product-card.component.ts`:
- Import `ChangeDetectionStrategy` from `@angular/core`.
- In the `@Component` decorator, configure:
  ```typescript
  changeDetection: ChangeDetectionStrategy.OnPush
  ```

### Task 2: Verify and Fix State Mutation Issues
If you run the application now, clicking "Add to Cart" may fail to update the child product card's status styling because the parent is mutating object properties directly in memory.
- Open your parent `ProductGridComponent` class.
- Verify that your list update methods return **new object references** rather than mutating existing ones:

```typescript
// Unsafe (will NOT trigger OnPush updates):
product.price = newPrice;

// Safe (creates new reference, triggering OnPush updates):
this.products = this.products.map(item => {
  if (item.id === product.id) {
    return { ...item, price: newPrice };
  }
  return item;
});
```

---

## Definition of Done
The lab is complete when:
- The `ProductCard` component compiles using the `OnPush` change detection strategy.
- Modifying a product's price in the parent dashboard successfully updates the child card's display in the browser.
