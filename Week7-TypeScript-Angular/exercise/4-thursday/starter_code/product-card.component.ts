import { Component, Input, Output, EventEmitter } from '@angular/core';

interface Product {
  id: number;
  name: string;
  price: number;
}

@Component({
  selector: 'app-product-card',
  template: `
    <div class="card">
      <!-- TODO: Render product name and price using interpolation -->
      <h4>Product Name</h4>
      <p>Price: \$0.00</p>
      
      <!-- TODO: Bind button click to emit product data -->
      <button class="btn btn-add">Add to Cart</button>
    </div>
  `
})
export class ProductCardComponent {
  // TODO: Add `@Input() product` annotation

  // TODO: Add `@Output() addToCart` event emitter annotation

  // TODO: Implement trigger method
}
