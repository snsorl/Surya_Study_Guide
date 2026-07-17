# Angular HttpClient and REST APIs

## Learning Objectives
- Inject and configure Angular's `HttpClient` service.
- Execute HTTP requests (`GET`, `POST`, `PUT`, `DELETE`).
- Type HTTP responses using TypeScript interfaces.
- Intercept errors using RxJS `catchError` pipelines.
- Add authorization tokens to requests using `HttpInterceptor`.

---

## Why This Matters
To build full-stack web applications, your frontend must communicate with your backend database (like a Spring Boot REST API). While standard JavaScript provides `fetch()`, writing clean error handling and security configurations manually is tedious. Angular provides the **`HttpClient`** service. It handles JSON parsing automatically, types API responses, manages asynchronous requests using RxJS streams, and intercepts requests to inject security headers.

---

## The Concept

### 1. Setup and Injection
To use the client, import **`HttpClientModule`** in your root module. Then, inject `HttpClient` into your service constructor:

```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface Task { id: number; title: string; }

@Injectable({ providedIn: 'root' })
export class TaskService {
  private apiUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) {}

  // 1. GET Request (Typed response)
  getTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl);
  }

  // 2. POST Request
  createTask(title: string): Observable<Task> {
    return this.http.post<Task>(this.apiUrl, { title });
  }
}
```

### 2. Error Handling using RxJS
The `HttpClient` returns RxJS **Observables**. To handle API errors (like server downtime or invalid inputs), pipe your request through the RxJS **`catchError`** operator:

```typescript
import { catchError, throwError } from 'rxjs';

getTasksSafe(): Observable<Task[]> {
  return this.http.get<Task[]>(this.apiUrl).pipe(
    catchError(error => {
      console.error('API Error occurred:', error);
      return throwError(() => new Error('Failed to load tasks. Please try again.'));
    })
  );
}
```

### 3. Injecting Headers with HttpInterceptors
To send authentication tokens (like a JWT) with every API request, you do not want to write headers manually in every service method. Instead, use an **`HttpInterceptor`** to automatically intercept outgoing requests and inject headers globally:

```typescript
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';

export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('auth_token');
    
    if (token) {
      // Clone request and inject Authorization header
      const cloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next.handle(cloned);
    }
    return next.handle(req);
  }
}
```

---

## Summary
- Register **`HttpClientModule`** and inject **`HttpClient`** to fetch data.
- Type API responses using generic parameters (e.g. **`http.get<Task[]>()`**).
- Handle HTTP errors inside request streams using the RxJS **`catchError`** operator.
- Inject authorization tokens or headers globally using an **`HttpInterceptor`**.

---

## Additional Resources
- [Angular Docs: HttpClient Guide](https://angular.dev/guide/http/making-requests)
- [RxJS Docs: CatchError API](https://rxjs.dev/api/index/function/catchError)
