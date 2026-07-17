# Pair Programming: Collaborative Spring Data JPA Order Management

## Objective
Collaborate in developer pairs to build an end-to-end, transaction-managed Order Processing application using Spring Data JPA. You will structure JPA Entities, write custom Query Methods (Property Expressions), implement business logic with transactional boundaries (`@Transactional`), and expose REST endpoints with Bean Validation.

---

## Scenario
You are part of a software development squad building a high-throughput e-commerce inventory API. If a customer places an order, the system must write the order, subtract stock from the warehouse inventory, and create an audit log. If the stock is insufficient, the entire operation must roll back to avoid mismatched quantities. You will implement this system collaboratively to master transaction management and database entity relation mappings.

---

## References
- [JpaRepository vs CrudRepository](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/4-thursday/jparepository-vs-crud-repository.md)
- [Property Expressions & Query Naming](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/4-thursday/property-expressions.md)
- [JPA Annotations Reference](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/4-thursday/annotations.md)
- [ACID Properties & Transactions](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/4-thursday/acid-properties-of-transactions.md)
- [@Transactional Annotation](file:///c:/Learning/INF-JFSD/content/Week5-Spring/written/4-thursday/transactional.md)

---

## The Collaborative Protocol
- **Developer Roles**:
  - **The Driver**: Sits at the keyboard, typing code, compiling, and running local verification scripts.
  - **The Navigator**: Reviews requirements, checks code logic for security flaws, acts as human-linter, and researches documentation references.
- **The Shift Rule**: Partners must exchange roles (swap keyboard driver) every **45 minutes**.
- **Role Swap Task**:
  - **Phase 1 (First 45m)**: Partner A is Driver, Partner B is Navigator. You will implement the `Order` Entity, `OrderRepository`, and basic database mappings.
  - **Phase 2 (Next 45m)**: Partner B is Driver, Partner A is Navigator. You will implement the transactional `OrderService` and `OrderController` endpoints.
  - **Phase 3 (Next 45m)**: Swap roles again to implement the second related Entity, `OrderItem` (mapping `@OneToMany` / `@ManyToOne` relationships).

---

## Core Tasks

### 1. Database Mappings & Repository (Phase 1)
- Create an `Order` Entity with fields: `id` (Auto-generated), `customerName`, `status` (Enum or String), and `orderDate`.
- Annotate fields using standard JPA (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`).
- Create `OrderRepository` extending `JpaRepository`.
- Implement a custom property expression query method: `List<Order> findByStatusAndOrderDateAfter(String status, LocalDateTime date)`.

### 2. Transactional Service & REST API (Phase 2)
- Create `OrderService` and annotate with `@Service`.
- Create a method `public Order placeOrder(Order order)` annotated with `@Transactional`.
- Inside the method:
  - Save the `Order` record.
  - *Simulate Stock Check*: If the order details exceed stock limits (e.g. quantity > 5), throw a custom runtime exception (`InsufficientStockException`).
  - Demonstrate that throwing a runtime exception inside a `@Transactional` block automatically rolls back the database insert (verify that the order record is not committed to the database).
- Create `OrderController` annotated with `@RestController` exposing:
  - `POST /orders` – to place an order.
  - `GET /orders` – to retrieve all orders.

### 3. Entity Relationships (Phase 3)
- Create an `OrderItem` entity containing: `id`, `productName`, `quantity`, `price`, and a `@ManyToOne` relationship to the parent `Order` entity using `@JoinColumn`.
- Update the `Order` entity to have a `List<OrderItem> items` property with `@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)`.
- Test that saving an `Order` containing a list of `OrderItem`s cascade-saves all items automatically in a single transaction.

---

## Definition of Done (Deliverables)
By the end of this collaborative session, the pair must deliver:
- A GitHub repository link containing the code.
- Verified logs demonstrating the transactional rollback:
  - Sending a POST payload with valid items saves records to the database.
  - Sending a POST payload that triggers `InsufficientStockException` returns an error status, and no records are saved (verify database primary key sequence skips the rolled-back index).
- A text file named `swap-log.txt` noting the timestamps of each role swap.
