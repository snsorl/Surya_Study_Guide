# Lab: Custom Currency Formatter Pipe

## Objectives
- Create a custom formatting pipe using the `@Pipe` decorator.
- Implement the `PipeTransform` interface and its `transform()` method.
- Pass configuration parameters to customize pipe formatting.
- Format numerical values as currency strings.

---

## Scenario
Although Angular provides a built-in `CurrencyPipe`, we want to implement a custom **`CurrencyFormatPipe`** to understand how custom transformations operate. The custom pipe should take a number value and output a formatted currency string, accepting dynamic options for:
1. **Currency Symbol** (e.g. `$`, `€`, `£`).
2. **Symbol Position** (whether to display the symbol before or after the number).

---

## Core Tasks

### Task 1: Scopes and Module Declarations
1. Create the pipe source file at `starter_code/currency-format.pipe.ts`.
2. Register the pipe class in your root module's `declarations` and `exports` arrays.

### Task 2: Implement the Transformation Logic
Complete the `transform` signature inside `starter_code/currency-format.pipe.ts`:
- The first parameter must be a `number` input.
- The second parameter should accept a string for the currency symbol (default to `'$'`).
- The third parameter should specify the symbol position (default to `'before'`).
- Return the formatted string.

```typescript
// Example outputs:
transform(29.99, '€', 'before') // Returns "€29.99"
transform(29.99, ' kr', 'after') // Returns "29.99 kr"
```

### Task 3: Apply the Pipe in Templates
Apply your custom pipe inside the `ProductList` template to format product price tags:
```html
<p>Price: {{ product.price | currencyFormat:'€':'before' }}</p>
```

---

## Definition of Done
The exercise is complete when:
- The product catalog template successfully compiles using the custom `currencyFormat` pipe.
- All product price tags display formatted currency strings matching the configured parameters.
