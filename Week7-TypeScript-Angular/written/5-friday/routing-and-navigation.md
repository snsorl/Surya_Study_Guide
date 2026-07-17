# Angular Routing and Navigation

## Learning Objectives
- Set up routes using `RouterModule.forRoot()`.
- Configure path mapping and use wildcard routes (`**`) to catch unmatched URLs.
- Render components dynamically using the `<router-outlet>` directive.
- Navigate between pages using the `routerLink` directive.
- Perform programmatic navigation using the `Router` service.

---

## Why This Matters
Traditional web applications reload the entire page when navigating between links, causing screen flashes and lag. In a Single Page Application (SPA), we want instant page transitions. The **Angular Router** intercepts browser URL changes and swaps displayed components dynamically without page reloads. Understanding how to register route tables, link pages, and handle fallback pages is key to building complex applications.

---

## The Concept

### 1. Registering Routes (`RouterModule`)
Routes are registered in a route table—an array of objects mapping URL paths to component classes:

```typescript
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home.component';
import { AboutComponent } from './about.component';
import { PageNotFoundComponent } from './page-not-found.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }, // Redirect default route
  { path: '**', component: PageNotFoundComponent }     // Wildcard catch-all route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

### 2. Mounting Views: `<router-outlet>`
The `<router-outlet>` directive acts as a placeholder tag in your main layout. When the user navigates to a route, Angular instantiates the corresponding component and renders it directly below this tag:

```html
<!-- app.component.html -->
<header><h1>App Header</h1></header>

<main>
  <!-- Active component gets rendered here -->
  <router-outlet></router-outlet>
</main>
```

### 3. Navigation Links (`routerLink`)
Do not use standard href tags (`<a href="/about">`) for navigation: they force the browser to reload the entire application. Instead, use the **`routerLink`** directive:

```html
<!-- Safe navigation within the SPA -->
<a routerLink="/about" routerLinkActive="active-tab">About Us</a>
```
The `routerLinkActive` directive automatically applies the specified CSS class when its link's path matches the active browser URL.

### 4. Programmatic Navigation
If you need to navigate the user after executing code (like after a successful form submission), inject the **`Router`** service into your class and call its `.navigate()` method:

```typescript
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({ selector: 'app-login' })
export class LoginComponent {
  constructor(private router: Router) {}

  onLoginSuccess() {
    this.router.navigate(['/home']); // Navigates user programmatically
  }
}
```

---

## Summary
- Configure routes in an array mapping paths to components, using **`**`** as a wildcard fallback.
- The **`<router-outlet>`** directive acts as the DOM rendering container for the active route.
- Use **`routerLink`** instead of standard `href` tags to navigate without triggering page reloads.
- Inject the **`Router`** service to trigger navigation programmatically in your TypeScript code.

---

## Additional Resources
- [Angular Docs: Routing and Navigation](https://angular.dev/guide/routing/common-router-tasks)
- [Angular Docs: Router Link API](https://angular.dev/api/router/RouterLink)
