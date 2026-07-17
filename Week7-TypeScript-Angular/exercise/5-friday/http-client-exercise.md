# Lab: Full-Stack HttpClient Integration

## Objectives
- Inject and configure the `HttpClient` service inside Angular.
- Fetch database records from a Spring Boot REST API.
- Implement error boundary logs using RxJS `catchError`.
- Register an HTTP Interceptor to inject security headers globally.

---

## Scenario
Instead of utilizing static arrays in memory, we need our shopping cart application to fetch live product records from a Spring Boot database API endpoint. You need to write a service to perform the get calls, build error handlers to alert users if the server goes down, and write an interceptor that automatically attaches an Authorization header to outgoing requests.

---

## Core Tasks

### Task 1: API Service Call
Create a product service (`product.service.ts`):
- Inject `HttpClient`.
- Write `getProducts(): Observable<Product[]>` targeting `GET 'http://localhost:8080/api/products'`.
- Pipe the request through RxJS `catchError` to handle server connection failures gracefully.

### Task 2: UI Async Rendering
Update your component to load products via the service:
- Set up an Observable property:
  ```typescript
  products$!: Observable<Product[]>;
  ```
- Initialize it inside `ngOnInit`:
  ```typescript
  this.products$ = this.productService.getProducts();
  ```
- Update the HTML template using the **`async`** pipe:
  ```html
  <div *ngIf="products$ | async as items; else loading">
    <app-product-card *ngFor="let item of items" [product]="item"></app-product-card>
  </div>
  ```

### Task 3: Token Injection Interceptor
Open `starter_code/http-interceptor.ts`:
- Retrieve the JWT token string from storage: `localStorage.getItem('auth_token')`.
- If found, clone the request and set the `Authorization: Bearer <token>` header.
- Register this interceptor inside your module's `providers` array.

---

## Definition of Done
The exercise is complete when:
- The catalog page successfully renders product data fetched from the Spring Boot API.
- Outgoing API calls in the browser's Network inspector contain the `Authorization: Bearer <token>` header automatically.
