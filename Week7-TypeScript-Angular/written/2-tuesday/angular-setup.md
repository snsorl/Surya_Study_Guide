# Angular CLI Setup and Installation

## Learning Objectives
- Install the Angular CLI globally using npm.
- Verify Angular installations using `ng version`.
- Initialize a new Angular application using `ng new`.
- Run local development servers using `ng serve`.
- Explore the default file structure of an Angular workspace.

---

## Why This Matters
To build modern, professional frontend applications, developers need tooling to compile TypeScript, bundle stylesheet assets, run unit test containers, and serve local files during development. The **Angular CLI (Command Line Interface)** is the official command-line utility that handles these setup, compilation, and code generation steps, letting you focus on writing code instead of configuring build pipelines.

---

## The Concept

### 1. Installation and Verification
The Angular CLI requires **Node.js** (and npm) installed on your machine.
- **Global Installation**:
  ```bash
  npm install -g @angular/cli
  ```
- **Verification**:
  ```bash
  ng version
  ```
  This command displays version details for Angular CLI, Node.js, and your operating system.

### 2. Initializing a Project (`ng new`)
To generate a new Angular workspace, run:
```bash
ng new my-todo-app
```
During initialization, the CLI prompts you:
1. *Which stylesheet format would you like to use?* (Select **CSS**).
2. *Would you like to enable Server-Side Rendering (SSR) and SSG/Prerendering?* (Select **No** for basic client-side apps).

### 3. Serving the Application (`ng serve`)
Once created, navigate to the project directory and start the local development server:
```bash
cd my-todo-app
ng serve
```
By default, the application is compiled in memory and hosted at **`http://localhost:4200`**. The server automatically watches your files and reloads the browser when you save changes.

---

## Workspace Directory Overview
Here are the key directories generated in a new workspace:
- **`angular.json`**: Workspace configuration settings for the CLI.
- **`package.json`**: Lists external library dependencies and npm script hooks.
- **`tsconfig.json`**: TypeScript compiler options.
- **`src/`**: The core source code directory.
  - `main.ts`: The entry point script that bootstraps the Angular application.
  - `index.html`: The single HTML page layout container.
  - `app/`: Folder containing core modules, components, and services.

---

## Summary
- Install the Angular CLI globally using **`npm install -g @angular/cli`**.
- Generate new projects with **`ng new <app-name>`**.
- Compile and run local development environments on port 4200 using **`ng serve`**.
- The **`src/`** directory contains your main application files, with **`main.ts`** serving as the bootstrap entry point.

---

## Additional Resources
- [Angular Docs: Angular CLI Installation](https://angular.dev/tools/cli/setup)
- [Angular Docs: CLI Command Reference](https://angular.dev/tools/cli)
