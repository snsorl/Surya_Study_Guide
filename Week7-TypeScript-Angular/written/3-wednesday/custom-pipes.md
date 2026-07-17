# Creating Custom Pipes

## Learning Objectives
- Write custom pipes in Angular using the `@Pipe` decorator.
- Implement the `PipeTransform` interface and its `transform()` method.
- Pass configuration parameters to custom pipes.
- Explain the performance differences between Pure and Impure pipes.

---

## Why This Matters
While Angular's built-in pipes handle basic formatting, they cannot solve business-specific requirements, such as masking credit card numbers (e.g. `**** **** **** 1234`), truncating long blog posts with a configurable character limit, or formatting raw address strings. Creating custom pipes allows you to wrap these reuseable formatting functions into clean selectors that can be applied to any template expression.

---

## The Concept

### 1. Structure of a Custom Pipe
A custom pipe is a class decorated with `@Pipe` that implements the `PipeTransform` interface:

```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'maskText' // The selector name used in templates
})
export class MaskTextPipe implements PipeTransform {
  // The transform method takes the input value and optional configuration parameters
  transform(value: string, maskChar: string = '*'): string {
    return value.replace(/./g, maskChar);
  }
}
```

### 2. Pure vs. Impure Pipes
- **Pure Pipes (Default)**: Angular only executes the pipe's `transform` method if it detects a change in the input value reference (or its parameters). This is highly performant and preserves CPU cycles.
- **Impure Pipes**: Angular executes the pipe on every change detection cycle, regardless of whether the inputs have changed. This can cause severe performance issues and should be avoided unless you are monitoring internal object state mutations.

---

## Code Examples

### Implementing a Truncate Pipe
Below is a custom pipe that truncates strings with a configurable character limit:

```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 20, trail: string = '...'): string {
    if (!value) return '';
    return value.length > limit ? value.substring(0, limit) + trail : value;
  }
}
```
*Template Usage:*
```html
<!-- Truncates text to 10 characters and appends '...' -->
<p>{{ postDescription | truncate:10:'...' }}</p>
```

---

## Summary
- Custom pipes are classes decorated with **`@Pipe`** that implement **`PipeTransform`**.
- The **`transform()`** method contains the formatting logic and returns the formatted value.
- By default, custom pipes are **pure**, meaning they only execute when their input reference changes, preserving page performance.

---

## Additional Resources
- [Angular Docs: Custom Pipes Guide](https://angular.dev/guide/pipes/custom-pipes)
- [Angular Docs: PipeTransform API](https://angular.dev/api/core/PipeTransform)
