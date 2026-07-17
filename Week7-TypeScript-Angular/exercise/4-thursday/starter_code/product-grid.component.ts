import { Component } from '@angular/core';

interface Product {
  id: number;
  name: string;
  price: number;
}

@Component({
  selector: 'app-product-grid',
  template: `
    <div class="cart-dashboard">
      <h2>Shopping Catalog</h2>
      
      <div class="split-layout">
        <!-- Display Product Cards -->
        <div class="catalog-grid">
          <!-- TODO: Loop through products and bind [product] input and (addToCart) output event -->
          <app-product-card></app-product-card>
        </div>

        <!-- Shopping Cart Summary Panel -->
        <div class="cart-summary">
          <h3>My Cart</h3>
          <p>Items: {{ cart.length }}</p>
          <ul>
            <li *ngFor="let item of cart">{{ item.name }} - \${{ item.price }}</li>
          </ul>
          <hr>
          <strong>Total: \${{ cartTotal }}</strong>
        </div>
      </div>
    </div>
  `
})
export class ProductGridComponent {
  public cart: Product[] = [];
  public cartTotal: number = 0;

  public products: Product[] = [
    { id: 1, name: "Mechanical Keyboard", price: 89.99 },
    { id: 2, name: "USB-C Hub Adapter", price: 34.99 },
    { id: 3, name: "Wireless Ergonomic Mouse", price: 49.99 }
  ];

  // TODO: Implement 'handleAddToCart(item: Product)' to update cart array and total
}
