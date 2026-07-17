# Route Guards in Angular

## Learning Objectives
- Describe the purpose of Route Guards.
- Identify the different guard types (`CanActivate`, `CanDeactivate`).
- Implement a route authorization guard.
- Write functional route guards (Angular 14+).

---

## Why This Matters
Some pages in your application (like user settings or checkout screens) should not be public. If a user is not logged in, they should be redirected back to the login screen. Conversely, if a user has half-filled a form, we want to warn them before they navigate away so they do not lose their inputs. In Angular, we manage these route access rules using **Route Guards**.

---

## The Concept

### 1. Guard Types
- **`CanActivate`**: Determines if a user can navigate *to* a route (e.g. checking authentication).
- **`CanDeactivate`**: Determines if a user can navigate *away* from the current route (e.g. checking for unsaved changes).

### 2. Functional Guards (Modern Standard)
In modern Angular (v14+), guards are written as simple functions instead of heavy class services. These functions can inject other services using Angular's global `inject()` function:

```typescript
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true; // Navigation allowed
  }

  // Redirect to login page and deny navigation
  return router.createUrlTree(['/login']);
};
```

### 3. Registering Guards
To apply a guard to a route, add it to the route's configuration array inside your routing module:

```typescript
const routes: Routes = [
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    canActivate: [authGuard] // Apply auth checks here
  }
];
```

---

## Summary
- **Route Guards** control access to application routes based on conditions (like authentication).
- **`CanActivate`** checks if a user can enter a route; **`CanDeactivate`** checks if a user can leave it.
- Modern Angular uses **functional guards**, which are simple functions that can inject services and return booleans or redirect paths.

---

## Additional Resources
- [Angular Docs: Router Guards reference](https://angular.dev/guide/routing/common-router-tasks#protecting-routes)
- [Angular Docs: Functional Guards API](https://angular.dev/api/router/CanActivateFn)
