# Lab: Dynamic User Profile Bindings

## Objectives
- Implement One-Way Data Binding (Interpolation and Property binding).
- Implement Event Binding to capture user actions.
- Synchronize form inputs with component states using Two-Way Data Binding (`ngModel`).
- Import `FormsModule` into module declarations.

---

## Scenario
We need to create a profile card editor dashboard. Users should be able to type their name, bio, and choose an avatar URL in a form, with their updates rendering in a preview card in real-time.

---

## Core Tasks

### Task 1: Module Configurations
1. Open your `AppModule` class definition file (`app.module.ts`).
2. Import **`FormsModule`** from `@angular/forms`:
   ```typescript
   import { FormsModule } from '@angular/forms';
   ```
3. Add `FormsModule` to your module's `imports` array.

### Task 2: Implement Component Logic
Navigate to `starter_code/user-profile.component.ts`:
- Define properties for:
  - `username` (string)
  - `bio` (string)
  - `avatarUrl` (string)
  - `themeColor` (string)
  - `isFormLocked` (boolean)
- Implement methods:
  - `resetForm()`: Resets fields to default values.
  - `toggleFormLock()`: Swaps the `isFormLocked` boolean state.

### Task 3: Build the Binding Template
Write the HTML template to implement:
1. **Interpolation**: Render the user's name and bio in the preview card.
2. **Property Binding**: Bind `avatarUrl` to an image `src` property, and bind `isFormLocked` to form input `disabled` attributes.
3. **Event Binding**: Bind click events to the reset and lock buttons.
4. **Two-Way Binding**: Bind form inputs to component properties using `[(ngModel)]`.

---

## Definition of Done
The exercise is complete when:
- Typing in the input fields immediately updates the text in the preview card.
- Clicking the "Lock" button disables all form inputs.
- Clicking "Reset" clears the form fields and updates the preview card back to defaults.
