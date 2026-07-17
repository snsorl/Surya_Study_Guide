# Collaborative Lab: Shopping Cart Communication (Pair Programming)

## Thursday Protocol: Pair Programming
This is a collaborative pair programming lab. Pair up with a partner.
- **Partner A (Driver)**: Focuses on building the `ProductGrid` parent container.
- **Partner B (Navigator)**: Focuses on building the `ProductCard` child component.
- Swap roles halfway through when implementing the dynamic cart calculations.

---

## Scenario
We are building the interface for our shopping cart page. The `ProductGrid` parent container holds the master inventory state, a list of items added to the cart, and the total cost. The parent loops and renders a list of `ProductCard` child components, passing down product data. Clicking "Add to Cart" on a card emits an action up to the parent, updating the shopping cart.

---

## Core Tasks

### Task 1: Scaffolding the Components
Inspect the templates in your `starter_code/` folder:
- [product-grid.component.ts](file:///c:/Learning/INF-JFSD/content/Week7-TypeScript-Angular/exercise/4-thursday/starter_code/product-grid.component.ts) (Parent)
- [product-card.component.ts](file:///c:/Learning/INF-JFSD/content/Week7-TypeScript-Angular/exercise/4-thursday/starter_code/product-card.component.ts) (Child)

### Task 2: Implement @Input Data Passing (Down Flow)
- Configure `ProductCardComponent` to accept a `product` input:
  ```typescript
  @Input() product!: Product;
  ```
- Update the parent's template to loop through products and bind each child element:
  ```html
  <app-product-card 
    *ngFor="let item of products" 
    [product]="item">
  </app-product-card>
  ```

### Task 3: Implement @Output Event Emissions (Up Flow)
- Configure `ProductCardComponent` to declare an `addToCart` event emitter:
  ```typescript
  @Output() addToCart = new EventEmitter<Product>();
  ```
- Emit the product payload when the user clicks the card's button.
- Update the parent's template to listen for `addToCart` events and call its local handler:
  ```html
  <app-product-card 
    [product]="item" 
    (addToCart)="handleAddToCart($event)">
  </app-product-card>
  ```

### Task 4: Complete Cart State Calculations
- Implement `handleAddToCart(product: Product)` inside the parent:
  - Add the item to a `cart` array.
  - Recalculate the total price.

---

## Definition of Done
The project is complete when:
- The parent dashboard renders the card grid correctly.
- Clicking "Add to Cart" on a card adds the product to the parent's cart list and updates the cart's running total automatically.
