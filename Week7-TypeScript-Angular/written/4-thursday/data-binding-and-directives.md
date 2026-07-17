# Data Binding and Custom Directives

## Learning Objectives
- Describe how data binding and directives interact.
- Pass input data to custom directives using property binding.
- Bind component properties to custom directive outputs.
- Build custom directives that support two-way data binding.

---

## Why This Matters
Custom directives are not just for static changes like adding a border color on hover. To build robust, interactive features, custom directives need to receive inputs (like a custom tooltip text string or a maximum input length limit) and notify the parent component when events occur (like a validation state change). Understanding how property and event bindings map to custom directives allows you to build highly interactive, reusable UI components.

---

## The Concept

### 1. Passing Inputs to Directives
A custom directive can accept input values from parent templates using the `@Input` decorator. You can bind to these input properties using standard square brackets `[]` property binding:

```typescript
// Custom Directive:
import { Directive, Input, ElementRef, OnInit } from '@angular/core';

@Directive({
  selector: '[appTooltip]'
})
export class TooltipDirective implements OnInit {
  @Input('appTooltip') text: string = ''; // Property can be bound by parents

  constructor(private el: ElementRef) {}

  ngOnInit() {
    // Sets the element's HTML title attribute to our tooltip text input
    this.el.nativeElement.title = this.text;
  }
}
```
*Template Usage:*
```html
<!-- Binds parent's 'currentTooltipMsg' value to the directive's input -->
<button [appTooltip]="currentTooltipMsg">Hover Me</button>
```

### 2. Binding to Custom Outputs
Just like components, custom directives can emit events up to parent components using `@Output` and `EventEmitter`:

```typescript
// Custom Directive:
@Directive({ selector: '[appDoubleTap]' })
export class DoubleTapDirective {
  @Output() doubleTapped = new EventEmitter<void>();

  @HostListener('dblclick') onDoubleClick() {
    this.doubleTapped.emit(); // Emits event up
  }
}
```
*Template Usage:*
```html
<div appDoubleTap (doubleTapped)="handleDoubleTap()">Double Click Me</div>
```

### 3. Custom Two-Way Data Binding
To implement custom two-way data binding on a directive, use a property/event naming convention: a property name (e.g. `value`) and a corresponding event emitter named **`<propertyName>Change`** (e.g. `valueChange`):

```typescript
@Directive({ selector: '[appToggleState]' })
export class ToggleStateDirective {
  @Input() state: boolean = false;
  @Output() stateChange = new EventEmitter<boolean>();

  @HostListener('click') onClick() {
    this.state = !this.state;
    this.stateChange.emit(this.state); // Emits new value up
  }
}
```
*Template Usage:*
```html
<!-- The banana-in-a-box syntax automatically binds 'isActive' in both directions -->
<button [(appToggleState)]="isActive">Toggle Status</button>
```

---

## Summary
- Pass inputs to custom directives using the **`@Input`** decorator and **`[property]="value"`** property binding.
- Emit actions from directives up using **`@Output`** decorators and **`(event)="action()"`** event binding.
- Enable custom two-way data binding on directives by pairing an `@Input() property` with an `@Output() propertyChange` event emitter.

---

## Additional Resources
- [Angular Docs: Directives Overview](https://angular.dev/guide/directives)
- [Angular Docs: Custom Directive Inputs](https://angular.dev/guide/directives/attribute-directives#passing-values-into-an-attribute-directive-with-an-input-binding)
