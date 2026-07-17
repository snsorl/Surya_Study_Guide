# Cohort Tooling Notes: JavaScript Development

## Learning Objectives
- Map browser developer console utilities to local diagnostic tasks.
- Enable code validation using basic linters.
- Use local server packages to host static assets during development.

---

## Why This Matters
Different cohorts utilize various tooling workflows (some write plain HTML files hosted locally, others utilize bundlers, while others run scripts via Node.js). This guide serves as a central reference placeholder for your specific cohort's local tool integrations, lint configurations, and debugging setups.

---

## The Concept

### 1. Static Asset Hosting
When building frontend web code that queries backend APIs, opening HTML files directly from the local file system (using paths like `file:///C:/...`) can cause security warnings, CORS blocks, or path resolution errors in the browser.
To resolve this, we use a simple HTTP local server.

- **Option A: VS Code Live Server**: A popular editor extension that spins up a local server on port 5500 with automatic browser reloading.
- **Option B: Node.js http-server**:
  If Node.js is installed on your machine, you can run a local web server instantly from the command line:
  ```bash
  npx http-server -p 3000
  ```

### 2. Browser Diagnostic Utilities
- **`console.log()`**: Prints general messages.
- **`console.dir()`**: Displays an interactive list of properties of a JavaScript object (very useful for inspecting DOM elements as raw JS objects, rather than HTML tags).
- **`console.table()`**: Renders arrays of objects as a table.
- **`debugger`**: Adding the `debugger;` keyword to your JavaScript code acts as a breakpoint. If browser DevTools are open, execution pauses on this line, allowing you to step through code execution.

---

## Summary
- Avoid opening HTML files directly from local files; host them using a local development server like **Live Server** or **http-server** to prevent CORS path blocks.
- Use **`console.dir()`** to inspect DOM nodes as raw JavaScript objects.
- Add the **`debugger;`** keyword to trigger browser breakpoints and trace variable states step-by-step.
