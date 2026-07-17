# Angular Tooling and IDE Configurations

## Learning Objectives
- Configure VS Code with essential extensions for Angular development.
- Install and configure Chrome DevTools extensions for Angular debugging.
- Use npm scripts inside `package.json` to customize development workflows.

---

## Why This Matters
Writing Angular code involves editing TypeScript classes, HTML templates, and CSS files in tandem. Without the right development tools, you will lose time hunting for typos in template variables or manually restarting build scripts. Configuring your IDE with syntax highlighting, code formatting, and debugging tools helps you write clean code and debug issues quickly.

---

## The Concept

### 1. IDE Setup (VS Code Extensions)
For the best Angular development experience in VS Code, install these official extensions:
- **Angular Language Service**: Provides autocomplete, syntax highlighting, definition navigation, and inline type validation inside your HTML templates.
- **ESLint**: Highlights formatting and coding standard warnings in your TypeScript files.
- **Prettier**: Auto-formats your code files on save to ensure consistent styling.

### 2. Browser Debugging Tools (Angular DevTools)
To inspect your application's state directly in the browser, install the **Angular DevTools** extension (available for Chrome and Firefox).
Once installed, it adds a new tab to your browser's Developer Tools (`F12`), allowing you to:
- Inspect the active component tree and view their variables and properties in real-time.
- Track change detection performance and identify bottlenecks.
- Directly update component states in the console to test UI updates.

### 3. Custom NPM Scripts
You can customize or chain development commands using the `scripts` object in your project's `package.json` file:

```json
"scripts": {
  "start": "ng serve --port 5000",
  "build": "ng build --configuration production",
  "audit-and-test": "npm audit && ng test --watch=false"
}
```
Run these scripts in your terminal using the npm run syntax (e.g. `npm run audit-and-test`).

---

## Summary
- Install the **Angular Language Service** extension in VS Code for autocomplete and template validation.
- Use **Angular DevTools** in your browser's inspect menu to view component states and debug issues.
- Customize your build and test workflows using **npm scripts** in your `package.json` file.

---

## Additional Resources
- [VS Code: Angular Language Service Extension](https://marketplace.visualstudio.com/items?itemName=Angular.ng-template)
- [Chrome Web Store: Angular DevTools Extension](https://chrome.google.com/webstore/detail/angular-devtools/iennhofdpljhodbednicjoiianapocgo)
