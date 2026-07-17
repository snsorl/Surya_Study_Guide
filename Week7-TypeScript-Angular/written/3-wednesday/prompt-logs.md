# Wednesday Prompt Log: Directives and Pipes

This prompt log documents the AI prompts used to generate custom Angular directives and pipes, along with validations of template syntax accuracy.

---

## Prompt 1: Custom Attribute Directive
*Objective:* Generate a custom attribute directive that applies a border styling to elements on hover.

### Prompt
```text
Generate an Angular directive named appHoverBorder.
It should bind to an element's style.border property.
On mouseenter, apply '2px solid blue'.
On mouseleave, reset the border style to 'none'.
Use @Directive, @HostBinding, and @HostListener.
Follow Angular module declaration conventions.
```

### AI Response Evaluation
- **Syntax Check**: The generated code used `@Directive({ selector: '[appHoverBorder]' })` correctly.
- **Hook Verification**: The generated code used `@HostBinding('style.border') borderStyle: string = 'none'` and two `@HostListener` callbacks (`mouseenter`, `mouseleave`) correctly.
- **Accuracy**: The generated code compiles without warnings and does not cause styling leaks.

---

## Prompt 2: Custom Formatting Pipe
*Objective:* Generate a custom pipe to format file sizes from raw bytes to human-readable strings.

### Prompt
```text
Write a custom Angular pipe named fileSize.
It should take a number (bytes) as input and return a string (e.g. "1.5 MB").
Implement the PipeTransform interface.
Support KB, MB, and GB, rounding to two decimal places.
The pipe must be pure.
```

### AI Response Evaluation
- **Syntax Check**: The generated code used `@Pipe({ name: 'fileSize', pure: true })` correctly.
- **Implementation**: The class implements `PipeTransform` and provides a valid `transform(bytes: number): string` method.
- **Accuracy**: Mathematical division logic was verified against mock byte arrays and outputs correct values (e.g. `1024` -> `1.00 KB`).

---

## Best Practices for Generating Angular UI Code
When using AI to generate directives, templates, or pipes:
1. **Request Pure Pipes by Default**: If the pipe's calculation is computationally heavy, make sure it is pure so it only runs when its input references change.
2. **Verify Host Selectors**: Ensure custom directive selectors are enclosed in square brackets `[selectorName]` so they are applied as attributes rather than HTML elements.
3. **Explicitly request cleanups**: If the generated directive sets event listeners, verify that they are cleaned up or bound using `@HostListener` rather than manual DOM manipulation.
