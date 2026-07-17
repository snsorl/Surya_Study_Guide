# Angular CLI Command Reference

## Learning Objectives
- Use generation commands (`ng generate`) to scaffold Angular components, services, and modules.
- Compile production bundles using the build command (`ng build`).
- Execute local unit tests using the test CLI command (`ng test`).
- Understand how `angular.json` configures CLI behavior.

---

## Why This Matters
Manually creating files for Angular components—which requires creating a TypeScript class, an HTML template, a CSS stylesheet, and a testing spec file, and then registering them in a module—is tedious and prone to typos. The **Angular CLI** automates these tasks. Using a single generate command, it creates all necessary files with boilerplate code and registers them in your modules automatically.

---

## The Concept

### 1. Generating Code Artifacts (`ng generate`)
The generate command (abbreviated as `ng g`) scaffolds boilerplate code files following official style guidelines:

- **Generate a Component**:
  ```bash
  ng generate component my-component
  # Shortcut: ng g c my-component
  ```
  This creates a folder with four files (TS, HTML, CSS, Spec) and registers the component in the nearest parent module.
- **Generate a Service**:
  ```bash
  ng g service my-service
  ```
  This creates a reusable service class with the `@Injectable` decorator.
- **Generate a Module**:
  ```bash
  ng g module my-module
  ```

### 2. Building for Production (`ng build`)
When you are ready to deploy your application to a production server, compile your project:
```bash
ng build
```
This transpiles TypeScript to JavaScript, optimizes assets, and outputs static HTML, CSS, and JS files to the `dist/` directory.

### 3. Testing (`ng test`)
Angular projects are configured with testing tools (Karma and Jasmine) out of the box:
```bash
ng test
```
This runs the unit tests defined in your `.spec.ts` files, opening a browser window to display test results in real-time.

---

## Summary Command Reference

| Action | Command | Shortcut |
|---|---|---|
| **Create Project** | `ng new <name>` | — |
| **Start Server** | `ng serve` | — |
| **Generate Component** | `ng generate component <name>` | `ng g c <name>` |
| **Generate Service** | `ng generate service <name>` | `ng g s <name>` |
| **Build Project** | `ng build` | — |
| **Run Unit Tests** | `ng test` | — |

---

## Additional Resources
- [Angular CLI Command Reference docs](https://angular.dev/reference/cli)
- [Angular Docs: Schematics overview](https://angular.dev/guide/schematics)
