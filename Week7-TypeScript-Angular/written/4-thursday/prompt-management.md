# Prompt Management for Angular Generations

## Learning Objectives
- Build a reusable prompt library for generating Angular code assets.
- Categorize prompts by Angular artifact types (Components, Services, Pipes, Guards).
- Define key details to include in AI prompts to ensure compiled output is correct.

---

## Why This Matters
AI models excel at generating code templates. However, if your prompt is vague (e.g. `"Write a task list component in Angular"`), the AI may generate legacy code, omit dependency imports, miss typescript types, or fail to export properties correctly, causing build errors. Implementing structured **Prompt Management** ensures you write specific, detailed prompts that generate correct, compile-ready code assets for your application.

---

## The Concept

To generate high-quality Angular code, your prompts should include five key details:
1. **Framework Context**: Specify Angular's framework standard and version constraints (e.g. modern Angular v14+ with Standalone component settings).
2. **Artifact Definition**: State the artifact type (e.g. Component, Service, Pipe, Guard).
3. **Data Schemas**: Define typescript interfaces and entity fields.
4. **Behavior Rules**: State what methods and events the component should handle.
5. **Output Constraints**: Specify code formatting rules, import statements, and style configurations.

---

## The Prompt Library

### 1. Component Generator Template
```text
Role: Expert Angular developer.
Task: Generate an Angular component named TaskList.
Version: Angular v14+ Standalone component format.
State Variables:
  - tasks: Array of Task objects. Task schema: { id: number, title: string, completed: boolean }
Methods:
  - toggleTask(id: number): Void (swaps completed boolean)
  - deleteTask(id: number): Void (filters the tasks array)
Template details:
  - Use *ngFor to loop through tasks. Render a list.
  - Implement a checkmark button bound to (click)="toggleTask(task.id)".
  - Use ngClass to apply 'strike-through' styling to completed tasks.
Constraints: No inline styles. Exclude unit tests. Output complete, compiled typescript and HTML template strings.
```

### 2. Service Generator Template
```text
Role: Expert Angular developer.
Task: Generate an Angular Service class named TaskService.
Injectable Configuration: Root provider (@Injectable({ providedIn: 'root' })).
Dependencies: Inject HttpClient.
Methods:
  - getTasks(): Returns Observable<Task[]> fetching from GET '/api/tasks'
  - createTask(task: Omit<Task, 'id'>): Returns Observable<Task> posting to POST '/api/tasks'
Constraints: Add error handling using RxJS catchError. Output typescript only with correct import paths.
```

---

## Summary
- Use a structured **Prompt Library** to ensure generated Angular assets compile cleanly and follow design guidelines.
- Always specify **Angular version requirements, standalone settings, and data schemas** in your prompts.
- Outline specific **methods, event triggers, and layout directives** to guide AI generation.

---

## Additional Resources
- [Angular Docs: Style Guide guidelines](https://angular.dev/style-guide)
- [Learn: Prompt Engineering techniques](https://developers.google.com/machine-learning/resources/prompt-eng)
