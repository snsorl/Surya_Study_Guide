# Collaborative Lab: Pair Programming a Flask Inventory REST API

## Objectives
- Collaborate in pairs using Driver/Navigator roles to build a Flask REST API.
- Implement full CRUD HTTP endpoints (`GET`, `POST`, `PUT`, `DELETE`).
- Write custom exception handlers in Flask to map code errors to JSON status responses.
- Implement context manager transactions to guard database records.

---

## Role Assignment and Setup
Find a partner. Assign initial roles:
*   **Partner A (Data Layer Specialist):** Writes the data structures, custom exception class (`InventoryError`), and the mock database context manager using `__enter__` and `__exit__` to copy/rollback/commit states.
*   **Partner B (API Route Specialist):** Declares the Flask app, registers endpoint decorators, reads JSON inputs, invokes the data transaction, and maps exception handles to HTTP codes.

*Note on Rotation:* You must swap roles after completing the `GET` and `POST` endpoints. The Driver becomes the Navigator, and the Navigator becomes the Driver.

---

## Technical Specifications

### Endpoints to Implement

| Method | Endpoint | Description | Expected Status |
| :--- | :--- | :--- | :--- |
| **GET** | `/products` | Fetch all products. | `200 OK` |
| **GET** | `/products/<id>` | Fetch single product. | `200 OK` or `404 Not Found` |
| **POST** | `/products` | Create a new product. | `201 Created` or `400 Bad Request` |
| **PUT** | `/products/<id>` | Update price/quantity. | `200 OK` or `404 Not Found` |
| **DELETE**| `/products/<id>` | Delete product. | `200 OK` or `404 Not Found` |

---

## Instructions

### 1. Data Layer Configuration (Partner A)
Create a file named `db_store.py` that implements transaction boundaries:
- Copy the in-memory database dictionary during `__enter__`.
- Rollback changes if an exception occurs during `__exit__`.
- Commit changes if no exceptions are thrown.
- Create a custom exception class: `class InventoryError(Exception)`.

### 2. API Routes Configuration (Partner B)
Create a file named `app.py`:
- Import Flask, `request`, `jsonify`, `InventoryError`, and the database store context manager.
- Implement the five CRUD endpoints.
- Wrap requests to verify JSON content headers:
  ```python
  if not request.is_json:
      return jsonify({"error": "Content-Type must be application/json"}), 400
  ```
- Map `InventoryError` to return a `400 Bad Request` or `404 Not Found` error.

---

## Testing Verification
Run your API locally:
```bash
python app.py
```
Open Postman or run curl commands from your terminal to verify validation, insertion, retrieval, update, and deletion responses.

---

## Definition of Done
Your collaborative project is complete when:
- Running `python app.py` starts the local Flask server on port 5000.
- All five API endpoints (GET all, GET single, POST new, PUT update, DELETE) are tested and return correct JSON payloads and HTTP status codes.
- Database changes are safely discarded if an exception is raised inside a store transaction block.
- Both partners have actively written code (demonstrated via commits in your shared repository).
