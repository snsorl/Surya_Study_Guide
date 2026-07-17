# Collaborative Lab: TypeScript Migration

## Overview & Roles (Thursday Protocol)
This is a **Pair Programming** collaborative exercise. Establish your roles before coding:
- **Driver (Writes Code)**: Focused on writing syntax, declaring types, and fixing compiler warnings.
- **Navigator (Guides Design)**: Focuses on type-system structure, interface layouts, and testing correctness.
*Recommendation:* Switch roles after completing Task 2.

---

## Core Tasks

### 1. Initialize TypeScript Environment
Together, install and configure a local compiler environment:
```bash
npm init -y
npm install --save-dev typescript
npx tsc --init
```
In your `tsconfig.json`, ensure strict compilation features are enabled:
```json
"compilerOptions": {
  "target": "es2022",
  "module": "commonjs",
  "strict": true,
  "outDir": "./dist"
}
```

### 2. Declare Object Interfaces
Create `types.ts`. Define the structure contract matching a student record:
- Declare a **`Student`** interface containing `id` (readonly number), `name` (string), `score` (number), and `active` (boolean).

### 3. Migrating and Annotating Code
Rename `student-filter.js` to `student-filter.ts` (or copy it to a new TS file) and import your type definitions.
- Annotate function parameters: ensure all lists are explicitly typed as `Student[]` arrays.
- Annotate function return values (e.g. `string[]`, `boolean`, `number`).
- **Observe Compiler Catching Errors**: Intentionally inject mismatched values into your datasets (such as a string for a score or missing active status attributes) and verify that the compiler highlights these mistakes.

### 4. Project Compilation
Compile the project using the compiler:
```bash
npx tsc
```
Run the compiled JavaScript output using Node to confirm correctness:
```bash
node dist/student-filter.js
```

---

## Definition of Done (Collaborative Verification)
- A local `tsconfig.json` exists with `strict: true` and `outDir: "./dist"`.
- A dedicated `types.ts` defines the `Student` interface.
- All function parameters and return types in `student-filter.ts` are explicitly typed. No `any` keywords remain in the code.
- Running `npx tsc` compiles the source files without type errors or warnings.
- The compiled JS file outputs correct values when executed in Node.
