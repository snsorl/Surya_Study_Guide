# Lab: Dynamic Product Catalog Layouts with Directives

## Objectives
- Apply structural directives (`*ngIf`, `*ngFor`) to render list states dynamically.
- Use `*ngSwitch` to choose and render element sub-blocks based on value checks.
- Implement conditional styling toggles using `ngClass`.
- Optimize iteration rendering using the `trackBy` performance helper.

---

## Scenario
We need to build a Product Catalog management dashboard. It should loop through a list of products and display cards with color-coded status badges based on inventory levels ("In Stock", "Low Stock", "Out of Stock"). If the inventory list is empty, it should display an empty state banner.

---

## Core Tasks

### Task 1: Component Controller Setup
In your Angular application workspace, create or update your `ProductList` component class:
- Declare a `products` array containing objects with shape:
  ```typescript
  interface Product {
    id: number;
    name: string;
    price: number;
    status: 'IN_STOCK' | 'LOW_STOCK' | 'OUT_OF_STOCK';
    description: string;
  }
  ```
- Implement the `trackBy` helper method:
  ```typescript
  trackByProductId(index: number, item: Product): number {
      return item.id;
  }
  ```

### Task 2: Implement the Markup Template
Write the HTML template structure to support:
1. **Empty State (`*ngIf`)**: If `products.length === 0`, display a container stating: *"No products match your search. Check back later!"*
2. **Loop Iteration (`*ngFor` with `trackBy`)**: Render a grid of product cards, utilizing the `trackByProductId` optimization method.
3. **Badge Rendering (`*ngSwitch`)**: Inside each card, display an inventory status badge:
   - For `'IN_STOCK'`, render a badge saying: *"Available"*
   - For `'LOW_STOCK'`, render a badge saying: *"Hurry, Low Stock!"*
   - For `'OUT_OF_STOCK'`, render a badge saying: *"Sold Out"*
4. **Conditional Styling (`ngClass`)**: Use `ngClass` to apply background colors to the badges based on the product status:
   - Green for `IN_STOCK`.
   - Orange for `LOW_STOCK`.
   - Red for `OUT_OF_STOCK`.

---

## Definition of Done
The exercise is complete when:
- The product grid displays all mock items with correct color-coded status badges.
- Deleting all items from the array dynamically hides the grid and displays the empty state container automatically.
