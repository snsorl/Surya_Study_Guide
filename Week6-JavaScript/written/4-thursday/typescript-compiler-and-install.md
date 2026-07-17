# The TypeScript Compiler and Configurations

## Learning Objectives
- Install the TypeScript compiler globally or locally using npm.
- Compile TypeScript files to JavaScript using the `tsc` CLI command.
- Initialize and configure a `tsconfig.json` file.
- Explain the key `compilerOptions` properties (`target`, `strict`, `outDir`, `rootDir`, `module`).

---

## Why This Matters
Writing TypeScript code is only half the battle. To run your code in a web browser, it must be compiled (transpiled) into standard JavaScript. The **TypeScript Compiler (`tsc`)** manages this process. By configuring the compiler options in a `tsconfig.json` file, you can control the compilation process, targeting specific JavaScript language versions (like ES5 or ES6) and adjusting type checks.

---

## The Concept

### 1. Installation
TypeScript is installed using the Node Package Manager (**npm**).
- **Global Installation**:
  ```bash
  npm install -g typescript
  ```
  This makes the `tsc` compiler command available system-wide.
- **Local Project Installation**:
  ```bash
  npm install --save-dev typescript
  ```
  This is the recommended approach for team projects, ensuring everyone compiles using the same local TypeScript version.

### 2. Compilation CLI (`tsc`)
Once installed, use the `tsc` command:
- **Compile a Single File**:
  ```bash
  tsc App.ts
  ```
  This creates a matching `App.js` file in the same directory.
- **Compile Project-Wide**:
  If a `tsconfig.json` exists in the folder, running `tsc` without arguments compiles the entire project based on the configuration settings:
  ```bash
  tsc
  ```
- **Watch Mode (`-w`)**:
  Monitors files for changes and automatically recompiles them on save:
  ```bash
  tsc -w
  ```

### 3. tsconfig.json Configuration
The `tsconfig.json` file resides in the project root directory, defining compilation boundaries and compiler options. You can generate a default file using the init command:
```bash
tsc --init
```

Key configuration properties inside `compilerOptions`:
- **`target`**: The target JavaScript version for the output files (e.g. `"es6"`, `"es2022"`, `"es5"`). This allows you to write modern ES6+ code while compiling down to older ES5 code for legacy browser support.
- **`strict`**: Enables strict type-checking checks. When set to `true`, the compiler flags implicit `any` assignments, null references, and type omissions.
- **`rootDir`**: The root directory containing your `.ts` source files (typically `"./src"`).
- **`outDir`**: The output directory where compiled `.js` files are saved (typically `"./dist"` or `"./build"`).
- **`module`**: The module system for the output code (e.g. `"commonjs"`, `"esnext"`).

---

## Code Example

Below is a clean, standard configuration template for a Node-based TypeScript project:

### Sample `tsconfig.json`
```json
{
  "compilerOptions": {
    "target": "es2022",                          /* Output JS version target */
    "module": "commonjs",                        /* Output module standard */
    "rootDir": "./src",                          /* Locate TS source folder */
    "outDir": "./dist",                          /* Location for output JS */
    "strict": true,                              /* Enable strict type checking */
    "esModuleInterop": true,                     /* Compatibility imports helper */
    "skipLibCheck": true,                        /* Skip library check performance */
    "forceConsistentCasingInFileNames": true     /* Avoid folder naming bugs */
  },
  "include": ["src/**/*"]                        /* Compile only files in src */
}
```

---

## Summary
- Install the TypeScript compiler CLI using npm (**`npm install -g typescript`**).
- Transpile files using the **`tsc`** command.
- Set up project-wide compilation options using a **`tsconfig.json`** configuration file.
- Configure target JS versions using **`target`** and specify input/output directories using **`rootDir`** and **`outDir`**.
- Enable **`strict: true`** to catch type mismatches, implicit variables, and null reference bugs.

---

## Additional Resources
- [TypeScript Docs: TSConfig Reference](https://www.typescriptlang.org/tsconfig)
- [TypeScript Docs: Compiler options list](https://www.typescriptlang.org/docs/handbook/compiler-options.html)
