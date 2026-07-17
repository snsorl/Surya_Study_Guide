# Exercise: Modeling Domains with TypeScript

## Core Tasks
Build a typed e-commerce data model using interfaces, type aliases, union types, and type-safe calculations.

1. Create a script named `ecommerce.ts` in your workspace.
2. Define the following types and interfaces:
   - **`Product`** (Interface):
     - `id`: readonly number
     - `name`: string
     - `price`: number
     - `category`: string
   - **`OrderStatus`** (Union literal type): `"PENDING" | "SHIPPED" | "DELIVERED" | "CANCELLED"`
   - **`Order`** (Type Alias):
     - `orderId`: string
     - `items`: Product[]
     - `status`: OrderStatus
     - `discountPercent?`: number (optional field)
3. Implement a function **`calculateOrderTotal(order: Order): number`** that:
   - Sums the price of all items in the array.
   - Applies the optional `discountPercent` (if present) to compute the final total.
   - Returns the final number.
4. Implement a function **`updateOrderStatus(order: Order, newStatus: OrderStatus): Order`** that returns a new order object with the updated status.
5. Compile using `tsc` and execute the output to verify type-safe execution.

---

## Technical Guidelines
- Enable `strict: true` in your typescript configurations.
- Ensure your calculations handle optional values safely (e.g., checking if `order.discountPercent !== undefined`).

---

## Definition of Done
- TypeScript source code compiles cleanly without errors or warnings.
- The `calculateOrderTotal` function correctly calculates totals, applying percentage deductions when the optional field is provided.
- Any attempt to set status to invalid values (like `"PAID"` or `"refunded"`) is caught by compiler checks at compile-time.
