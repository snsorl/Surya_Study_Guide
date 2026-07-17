# Practice Quiz: Routing, HttpServices & Vulnerability Scanning

## Part 1: Multiple Choice (MCQ)

### 1. Which module must be imported to configure route mapping endpoints inside the application?
- [ ] A) `HttpClientModule`
- [ ] B) `RouterModule.forRoot(routes)`
- [ ] C) `CommonModule`
- [ ] D) `BrowserModule`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `RouterModule.forRoot(routes)`

**Explanation:** `RouterModule.forRoot()` configures routes at the root level of the application, importing router directives like `routerLink` and initializing routing services.
- **Why others are wrong:**
  - A) `HttpClientModule` configures API call services.
  - C) `CommonModule` provides base structural directives.
  - D) `BrowserModule` configures browser execution targets.
</details>

---

### 2. Which guard interface is implemented to verify if a user has sufficient authentication permissions before loading a route component?
- [ ] A) `CanDeactivate`
- [ ] B) `CanLoad`
- [ ] C) `Resolve`
- [ ] D) `CanActivate`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** D) `CanActivate`

**Explanation:** `CanActivate` checks route entry rules. If it evaluates to `false`, access is blocked, and the router cancels navigation.
- **Why others are wrong:**
  - A) `CanDeactivate` prompts users before navigating away (e.g. warning on unsaved forms).
  - B) `CanLoad` prevents lazy loading module bundles before checks.
  - C) `Resolve` pre-fetches data before rendering route templates.
</details>

---

### 3. Which class is injected inside services to communicate with external databases and REST APIs?
- [ ] A) `HttpHandler`
- [ ] B) `HttpClient`
- [ ] C) `HttpModule`
- [ ] D) `HttpInterceptor`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `HttpClient`

**Explanation:** `HttpClient` provides request methods (GET, POST, PUT, DELETE) and parses typed responses using generic parameters.
- **Why others are wrong:**
  - A) `HttpHandler` dispatches requests to backend servers but is not directly used for making application API calls.
  - C) `HttpModule` is not a valid modern Angular class.
  - D) `HttpInterceptor` intercepts and edits requests globally, it doesn't execute calls directly.
</details>

---

### 4. What must you do to modify request headers inside an `HttpInterceptor`?
- [ ] A) Mutate the `req.headers` object directly.
- [ ] B) Set property fields inside the constructor.
- [ ] C) Clone the request using `req.clone({ setHeaders: ... })`.
- [ ] D) Call `http.post()` with headers as parameters.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Clone the request using `req.clone({ setHeaders: ... })`.

**Explanation:** Outgoing HTTP request objects in Angular are immutable. To modify headers, you must clone the request using `.clone()`.
- **Why others are wrong:**
  - A) Mutating `req.headers` directly throws a runtime error.
  - B & D) Constructors and standard POST parameter calls do not operate on interceptor instances.
</details>

---

### 5. Which command line scanner identifies outdated packages and security vulnerabilities in package dependencies?
- [ ] A) `npm check`
- [ ] B) `npm audit`
- [ ] C) `npm update`
- [ ] D) `npm verify`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `npm audit`

**Explanation:** `npm audit` checks dependencies against public vulnerability databases and generates security reports.
- **Why others are wrong:**
  - A & D) `check` and `verify` are not valid npm vulnerability scanning commands.
  - C) `npm update` upgrades packages but does not audit security profiles.
</details>

---

## Part 2: True / False

### 6. Wildcard routes (`path: '**'`) should be declared at the top of the route configuration array.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** False

**Explanation:** Angular matches routes sequentially using a first-match-wins strategy. If you place a wildcard route at the beginning of the list, it will catch all navigation attempts, preventing other routes from matching. It must always be the last entry in your route table.
</details>

---

### 7. Modern route guards (since Angular 14+) can be implemented as simple functions rather than dedicated classes.
- [ ] True
- [ ] False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** True

**Explanation:** Angular supports functional route guards (`CanActivateFn`), allowing you to write guards directly as helper functions.
</details>

---

## Part 3: Code Prediction

### 8. What is the output of this service fetch request?
```typescript
interface Ticket { id: number; title: string; }
// Inside service:
getTicket() {
  return this.http.get<Ticket>('/api/ticket');
}
```
- [ ] A) A `Ticket` object.
- [ ] B) An `Observable<Ticket>` stream.
- [ ] C) The raw JSON string.
- [ ] D) `Promise<Ticket>`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) An `Observable<Ticket>` stream.

**Explanation:** Angular's `HttpClient` methods return RxJS Observables that emit data values once subscribed to.
</details>

---

### 9. What happens if a route guard returns a `UrlTree` object?
- [ ] A) Navigation is blocked, and a runtime warning is logged.
- [ ] B) The router redirects to the route specified by the `UrlTree`.
- [ ] C) Compilation fails.
- [ ] D) The application crashes.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) The router redirects to the route specified by the `UrlTree`.

**Explanation:** If a guard returns a `UrlTree`, Angular intercepts navigation and redirects the user to the path defined in the tree (e.g. redirecting to `/login` if unauthorized).
</details>

---

## Part 4: Fill-in-the-Blank

### 10. The RxJS operator used to catch and handle HTTP request failures inside service pipelines is `___________`.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** `catchError`

**Explanation:** The `catchError` operator intercepts failed Observables, allowing you to run error logging handlers and return throwError streams.
</details>
