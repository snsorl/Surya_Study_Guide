# Understanding package.json and npm

## Learning Objectives
- Explain the role of the `package.json` file in a JavaScript/TypeScript project.
- Initialize new projects using the `npm init` command.
- Install dependencies using `npm install` and explain the difference between `dependencies` and `devDependencies`.
- Define and execute custom NPM scripts.
- Explain the role of the `package-lock.json` lock file.

---

## Why This Matters
In Java projects, we use Maven (`pom.xml`) or Gradle to manage third-party libraries, configure compile scripts, and run test suites. In JavaScript and TypeScript, the standard project manager is the Node Package Manager (**npm**), and the configuration file is **`package.json`**. Understanding how to declare dependencies, configure build scripts, and lock dependency versions is essential for managing frontend packages.

---

## The Concept

### 1. Initializing a Project (`npm init`)
To start a new project managed by npm, run the initialize command in your project directory:
```bash
npm init
```
This prompts you for configurations (name, version, license) and generates a `package.json` file. To accept all defaults instantly, use the yes flag:
```bash
npm init -y
```

### 2. Dependency Management: regular vs. devDependencies
When you install a package using npm, it is added to the `package.json` file. Dependencies are split into two categories:

- **`dependencies`**: Libraries required for the application to **run in production** (e.g. `lodash`, web routers).
  - Install command: `npm install <package-name>`
- **`devDependencies`**: Libraries required only for **local development and build steps** (e.g. `typescript`, compiler tools, test runners). These are stripped out when deploying a production build.
  - Install command: `npm install --save-dev <package-name>` (or `npm install -D <package-name>`)

### 3. NPM Scripts
The `scripts` block in `package.json` defines custom command-line aliases. Instead of typing long compiler paths, you run short aliases using `npm run <script-name>`:

```json
"scripts": {
  "build": "tsc",
  "start": "node dist/app.js",
  "dev": "tsc -w"
}
```
*Execution:* Running `npm run build` executes the `tsc` compiler command.

### 4. The Lock File (`package-lock.json`)
When you run `npm install`, npm automatically creates a `package-lock.json` file.
- It locks the exact version of every dependency and sub-dependency installed.
- This ensures that when other team members clone the repository and run `npm install`, they get the exact same library versions, preventing bugs caused by minor version mismatches.

---

## Code Example

Below is a standard `package.json` structure for a TypeScript project:

### Sample `package.json`
```json
{
  "name": "todo-app-frontend",
  "version": "1.0.0",
  "description": "TypeScript Todo API client",
  "main": "dist/app.js",
  "scripts": {
    "build": "tsc",
    "dev": "tsc -w",
    "start": "node dist/app.js"
  },
  "dependencies": {
    "uuid": "^9.0.0"
  },
  "devDependencies": {
    "typescript": "^5.1.3",
    "@types/node": "^20.2.5"
  },
  "author": "Trainee Developer",
  "license": "MIT"
}
```

---

## Summary
- **`package.json`** is the project configuration file for managing dependencies and scripts in the Node ecosystem.
- Initialize a new project using the **`npm init -y`** command.
- **`dependencies`** are required for production; **`devDependencies`** are only needed during development.
- Execute command-line aliases defined in the `scripts` block using **`npm run <script-name>`**.
- **`package-lock.json`** locks the exact versions of dependencies installed, ensuring build consistency across developer machines.

---

## Additional Resources
- [NPM Documentation: package.json](https://docs.npmjs.com/creating-a-package-json-file)
- [NPM Documentation: npm install CLI](https://docs.npmjs.com/cli/v9/commands/npm-install)
