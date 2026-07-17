# Exercise: Inventory Management using Python OOP & Collections

## Objectives
- Declare Python classes with instance attributes and custom string formatting representations (`__str__`).
- Organize records inside dictionary tables for quick ID lookups.
- Maintain sets to isolate unique categories or status IDs.
- Write list comprehensions to build filtered query subsets.

---

## The Scenario
You are building the core data manager for a warehouse application. You need to model inventory items, retrieve products instantly using dictionary lookup IDs, track which items are completely out of stock using sets, and query categories dynamically.

---

## Instructions

1. Create a script named `inventory_manager.py`.
2. Define a **`Product`** class:
   - **`__init__(self, name, price, quantity)`**: Initializes the product properties.
   - **`__str__(self)`**: Returns a string representation format: `[Name] - $Price (Qty: Quantity)`. E.g., `Keyboard - $89.90 (Qty: 5)`.
3. Define an **`Inventory`** class:
   - **`__init__(self)`**: Initializes the store data:
     - `self.products = {}` (Dictionary mapping `product_id` -> `Product` object).
     - `self.out_of_stock = set()` (Set containing IDs of products whose quantity is 0).
   - **`add_product(self, product_id, product)`**: Adds an item to `self.products`. If the product quantity is 0, add the `product_id` to the `out_of_stock` set.
   - **`get_product(self, product_id)`**: Returns the Product object from the dictionary, or `None` if missing.
   - **`update_stock(self, product_id, new_quantity)`**: Updates the quantity of the target product.
     - If the new quantity is 0, add the ID to the `out_of_stock` set.
     - If the quantity is greater than 0, remove the ID from the `out_of_stock` set if it was present.
   - **`filter_by_price(self, min_price, max_price)`**: Returns a list of Product objects whose price falls between `min_price` and `max_price` (inclusive) using a **list comprehension**.
4. Test your Classes:
   - Instantiate an `Inventory` object.
   - Add three products:
     - ID `"P100"`: Laptop, price 1200.00, quantity 2.
     - ID `"P101"`: Mouse, price 25.00, quantity 0.
     - ID `"P102"`: Keyboard, price 85.00, quantity 10.
   - Call `update_stock("P100", 0)` (Laptop is sold out).
   - Call `update_stock("P101", 5)` (Mouse restocked).
   - Filter items with prices between `10.00` and `100.00`.

---

## Definition of Done
Your lab is complete when:
- Running `python inventory_manager.py` prints the expected states:
  - The `out_of_stock` set must contain *only* `{"P100"}` (since P101 was restocked and P100 became out of stock).
  - The price filter query returns the Mouse and Keyboard objects.
- All product lookups and additions are handled via dictionary key actions, not iterative list loops.
