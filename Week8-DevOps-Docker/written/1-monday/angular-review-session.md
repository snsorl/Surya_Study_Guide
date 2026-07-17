# Angular Consolidation & Review Guide

## Learning Objectives
- Consolidate and review key Angular design patterns (routing, HTTP clients, route guards, and component communication).
- Prepare for technical interviews with typical Angular architecture questions.
- Establish a quick-reference checklist for Project 3 implementation.
- Understand how Angular frontends integrate within the larger DevOps and cloud ecosystem.

---

## Why This Matters
As you transition into Week 8—focusing on DevOps, cloud deployments, and containerization—you must ensure your application code is stable, optimized, and ready for production pipelines. Monday’s transition begins the shift from coding to operating, and a solid frontend forms the user-facing layer of your architecture. If your Angular application lacks proper route security, handles HTTP requests inefficiently, or fails to structure component communication cleanly, these issues will amplify when deployed behind API Gateways, reverse proxies, and inside Docker containers.

---

## The Concept

### 1. Key Angular Architecture Patterns
Angular applications are structured around core structural pillars that work in tandem to create robust Single Page Applications (SPAs).

#### Component Communication
Components form the user interface, but they rarely exist in isolation. Managing data flow between them is crucial:
- **Parent to Child (`@Input`)**: Passing configuration and state down the hierarchy.
- **Child to Parent (`@Output` and `EventEmitter`)**: Bubbling events and updates back up to parent components.
- **Unrelated Components (Shared Services)**: Leveraging RxJS `BehaviorSubject` or `Subject` within a singleton service to share state globally across the application.

#### HTTP Client & Service Layer
Angular's `HttpClient` (from `@angular/common/http`) encapsulates asynchronous HTTP traffic:
- Built on top of **RxJS Observables**, allowing powerful stream operators like `map`, `catchError`, and `switchMap`.
- Always decouple HTTP calls from components by placing them in dedicated `@Injectable` service classes to promote separation of concerns.

#### Routing & Route Guards
The Angular Router maps browser paths to specific components.
- **Lazy Loading**: Splitting the application into feature modules loaded dynamically only when the route is visited, minimizing the initial bundle size.
- **Route Guards**: Implementing `CanActivate`, `CanActivateChild`, or `CanDeactivate` interfaces to secure client-side views based on user authentication or authorization states (e.g., verifying a JWT is present).

---

## Code Examples and Walkthroughs

### 1. Decoupled Component Communication via Shared Service
Instead of chain-linking `@Input` and `@Output` through multiple component levels, a shared RxJS-based service acts as an event bus.

```typescript
// state.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StateService {
  // BehaviorSubject stores the current state and emits it to new subscribers
  private userSessionSubject = new BehaviorSubject<string | null>(null);
  
  // Expose as an Observable to prevent direct mutation outside the service
  public userSession$: Observable<string | null> = this.userSessionSubject.asObservable();

  constructor() {}

  // Update session state
  public setSession(username: string): void {
    this.userSessionSubject.next(username);
  }

  // Clear session state
  public clearSession(): void {
    this.userSessionSubject.next(null);
  }
}
```

```typescript
// dashboard.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { StateService } from './state.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  template: `
    <div class="dashboard-container" *ngIf="currentUser; else noUser">
      <h2>Welcome, {{ currentUser }}!</h2>
      <button (click)="logout()">Logout</button>
    </div>
    <ng-template #noUser>
      <p>Please log in to view the dashboard.</p>
    </ng-template>
  `
})
export class DashboardComponent implements OnInit, OnDestroy {
  currentUser: string | null = null;
  private sessionSub!: Subscription;

  constructor(private stateService: StateService) {}

  ngOnInit(): void {
    this.sessionSub = this.stateService.userSession$.subscribe(user => {
      this.currentUser = user;
    });
  }

  logout(): void {
    this.stateService.clearSession();
  }

  ngOnDestroy(): void {
    if (this.sessionSub) {
      this.sessionSub.unsubscribe();
    }
  }
}
```

### 2. Route Guard Implementation for Authentication
Ensure routes are protected on the client side before a page is rendered.

```typescript
// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private router: Router) {}

  canActivate(): boolean | UrlTree {
    const token = localStorage.getItem('jwt_token');
    
    if (token) {
      // Basic check: If token is present, allow access
      return true;
    }

    // Redirect to login if token is missing
    return this.router.parseUrl('/login');
  }
}
```

Configure the route guard within your routing module:
```typescript
// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { LoginComponent } from './login.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { 
    path: 'dashboard', 
    component: DashboardComponent, 
    canActivate: [AuthGuard] // Safeguard route
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
```

---

## Common Angular Interview Questions

### Q1: What is the difference between a `Subject` and a `BehaviorSubject` in RxJS?
- **`Subject`**: Does not hold a default value. Late subscribers will only receive data emitted *after* they subscribed.
- **`BehaviorSubject`**: Requires an initial value. Upon subscribing, it immediately emits the current stored value to the observer.

### Q2: What are Angular Route Guards and what interfaces do they implement?
Route Guards execute logic before navigating to a route to determine if the transition is allowed. Key interfaces:
- `CanActivate`: Decides if a route can be navigated to.
- `CanActivateChild`: Decides if child routes of a route can be navigated to.
- `CanDeactivate`: Decides if the user can navigate *away* from the current route (e.g., warning them of unsaved changes).

### Q3: Why should you unsubscribe from Observables, and what are the best ways to do so?
Unsubscribing prevents memory leaks, especially when components are frequently initialized and destroyed.
- **Manual unsubscribe**: Store the `Subscription` reference and call `.unsubscribe()` in `ngOnDestroy()`.
- **`takeUntil` operator**: Automatically complete the stream when a lifecycle trigger emits.
- **`async` pipe**: Use `{{ obs$ | async }}` in templates. Angular handles subscription lifecycle cleanup automatically.

---

## Project 3 Reference Checklist
Use this quick checklist to ensure your frontend meets production guidelines before deployment:
- [ ] **Decoupled API URL:** The base API URL configuration must use environment properties rather than hardcoded string paths (e.g., `environment.apiUrl` from `src/environments/environment.prod.ts`).
- [ ] **Error Handling:** Implement an `HttpInterceptor` to globally intercept network requests, catch `401 Unauthorized` or `500 Server Error` issues, and display notifications or redirect to login.
- [ ] **Unsubscribe Check:** Scan all components for subscriptions to ensure they are cleared via the `async` pipe or explicit cleanups in `ngOnDestroy`.
- [ ] **Route Guard Protection:** Ensure all dashboard, profile, and administrative routes are associated with an active guard definition.

---

## Additional Resources
- [Angular Routing & Navigation Guide](https://angular.io/guide/router)
- [RxJS Subject Documentation](https://rxjs.dev/guide/subject)
- [Angular Interceptors Reference](https://angular.io/guide/http#intercepting-requests-and-responses)
