# Bootstrapping: Hello Angular

## Learning Objectives
- Trace the initialization and bootstrap flow of an Angular application.
- Locate the main entry files (`index.html`, `main.ts`, `app.module.ts`, `app.component.ts`).
- Explain how change detection is triggered in Angular.

---

## Why This Matters
When you run `ng serve`, the app launches, and the browser displays your homepage. But how does this happen? What is the relationship between the single `index.html` file and your TypeScript component classes? Tracing this initialization flow is key to understanding how Angular mounts your components, manages rendering lifecycles, and triggers change detection.

---

## The Concept: The Bootstrap Chain

When a user visits your Angular site, the browser executes the following bootstrap chain:

```
[ index.html ] (Browser loads container shell with <app-root>)
       │
[ main.ts ] (Entry point compiled by CLI starts execution)
       │
[ AppModule ] (main.ts bootstraps the root NgModule)
       │
[ AppComponent ] (AppModule bootstraps the root Component)
       │
[ DOM rendering ] (<app-root> is replaced by app.component.html markup)
```

### 1. The Container Shell (`index.html`)
The main entry page of your application. Inside the `<body>`, it contains a custom placeholder tag:
```html
<app-root>Loading...</app-root>
```
At startup, the browser renders this tag as plain text until the Angular scripts load and replace it with your compiled component templates.

### 2. The Script Entry Point (`main.ts`)
The first script file executed by the browser. It bootstraps (initializes) the application by loading the root module:
```typescript
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
```

### 3. The Root Module (`app.module.ts`)
The root module (**AppModule**) compiles your components and lists the root component to load first using the `bootstrap` array:
```typescript
@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule],
  bootstrap: [AppComponent] // Bootstraps root component
})
export class AppModule { }
```

### 4. The Root Component (`app.component.ts`)
The root component (**AppComponent**) contains the metadata and templates for the main application shell:
```typescript
@Component({
  selector: 'app-root', // Matches the tag in index.html
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'My App';
}
```
The Angular engine instantiates `AppComponent` and replaces the `<app-root>` tag in `index.html` with the contents of `app.component.html`.

---

## Summary
- **`index.html`** serves as the initial HTML container shell, containing the `<app-root>` placeholder.
- **`main.ts`** is the entry point script that bootstraps the root module (**`AppModule`**).
- **`AppModule`** bootstraps the root component (**`AppComponent`**), which replaces the `<app-root>` tag in the DOM with its template.

---

## Additional Resources
- [Angular Docs: Bootstrapping guide](https://angular.dev/guide/bootstrapping)
- [Angular Docs: Workspace structure](https://angular.dev/tools/cli/workspace-structure)
