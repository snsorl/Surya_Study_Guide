# Lab: Setting Up Your First Angular Application

## Objectives
- Install the Angular CLI utility and verify local system configurations.
- Scaffold a new workspace application using the CLI.
- Run a local development environment.
- Create a standalone `HelloWorld` component and mount it inside the parent DOM shell.

---

## Core Tasks

### Task 1: Environment Installation and Setup
1. Verify Node.js is installed on your workstation.
2. Install the Angular CLI globally:
   ```bash
   npm install -g @angular/cli
   ```
3. Verify your CLI version:
   ```bash
   ng version
   ```

### Task 2: Create Your Project Workspace
1. Initialize a new project named `trainee-app`:
   ```bash
   ng new trainee-app --style=css --routing=false
   ```
   *(Select CSS for styles and No for SSR when prompted).*
2. Open the project folder in your code editor and examine the directory:
   - Identify `angular.json` (workspace settings) and `src/main.ts` (bootstrap entry).

### Task 3: Create and Mount Your First Component
1. Start the development server:
   ```bash
   ng serve
   ```
   Verify the default page renders at `http://localhost:4200`.
2. Open a separate terminal and generate a new component named `hello-world`:
   ```bash
   ng generate component hello-world
   ```
3. Open `hello-world.component.html` and write:
   ```html
   <div class="hello-card">
     <h3>Hello World from Angular!</h3>
     <p>Component successfully compiled and mounted.</p>
   </div>
   ```
4. Mount this new component by adding its selector tag to `app.component.html`:
   ```html
   <app-hello-world></app-hello-world>
   ```

---

## Definition of Done
The lab is complete when:
- The local server compiles and runs without warnings.
- Navigating to `http://localhost:4200` displays your `HelloWorld` component text successfully in the browser.
