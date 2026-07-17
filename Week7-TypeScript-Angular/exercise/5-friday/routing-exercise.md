# Lab: Integrating Routing and Guards

## Objectives
- Set up route tables using the Angular Router.
- Configure default redirects and catch-all wildcard routes.
- Link components inside templates using `routerLink`.
- Implement a functional `CanActivate` authentication guard.

---

## Scenario
We need to add routing configurations to our dynamic shopping cart application. The user should be able to navigate between three separate views: Home (welcome catalog grid), Cart (selected summary list), and Product Details (`/products/:id`). Additionally, the Cart page must be protected, redirecting anonymous users back to the Login view.

---

## Core Tasks

### Task 1: Routing Configuration Mappings
Create or edit your `AppRoutingModule` file:
- Define routes:
  - `/home`: Points to `HomeComponent`
  - `/cart`: Points to `CartComponent` (Protected)
  - `/products/:id`: Points to `ProductDetailComponent`
  - `/login`: Points to `LoginComponent`
  - Unmatched URLs (`**`): Redirect to `/home`

### Task 2: Implement Navigation Links
Update your layout navigation headers in `app.component.html`:
- Replace standard `href` anchors with Angular's `routerLink` directive:
  ```html
  <a routerLink="/home" routerLinkActive="active-tab">Catalog</a>
  <a routerLink="/cart" routerLinkActive="active-tab">My Cart</a>
  ```
- Ensure `<router-outlet></router-outlet>` is mounted inside the main view panel.

### Task 3: Write the Auth Guard
Create a functional guard file at `auth.guard.ts`:
- Check if the key `auth_token` is present in `localStorage`.
- If true, allow entry by returning `true`.
- If false, inject the `Router` and return a redirect tree to `/login`.
- Register this guard on the `/cart` route:
  ```typescript
  { path: 'cart', component: CartComponent, canActivate: [authGuard] }
  ```

---

## Definition of Done
The exercise is complete when:
- Clicking navigation links swaps views instantly without page reloads.
- Attempting to access `http://localhost:4200/cart` without a token in storage automatically redirects the browser back to `/login`.
