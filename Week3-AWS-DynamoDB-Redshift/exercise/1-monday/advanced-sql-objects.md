# Exercise: Advanced SQL Objects (Views, Triggers, and Stored Procedures)

**Exercise Mode:** Mode A: Implementation (Code Lab)
**Target Engine:** PostgreSQL

---

## Scenario
You are working on the database backend for an online retailer. To ensure database integrity, security, and automation, you need to implement a data abstraction layer (View), audit automation (Trigger), and a maintenance script (Stored Procedure).

---

## Part A: Database Schema Setup
Create the base tables for this exercise:

```sql
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS customer_audit_log CASCADE;

CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT REFERENCES customers(customer_id) ON DELETE CASCADE,
    order_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL, -- 'PENDING', 'COMPLETED', 'ARCHIVED'
    total_amount DECIMAL(10, 2) DEFAULT 0.00
);

CREATE TABLE customer_audit_log (
    log_id SERIAL PRIMARY KEY,
    customer_id INT,
    old_name VARCHAR(100),
    new_name VARCHAR(100),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed initial data
INSERT INTO customers (full_name, email) VALUES 
('John Doe', 'john.doe@example.com'),
('Jane Smith', 'jane.smith@example.com');

INSERT INTO orders (order_id, customer_id, order_date, status, total_amount) VALUES
(5001, 1, '2026-07-01', 'PENDING', 250.00),
(5002, 1, '2026-07-10', 'COMPLETED', 120.00),
(5003, 2, '2026-07-12', 'PENDING', 45.00),
(5004, 2, '2026-06-15', 'COMPLETED', 300.00);
```

---

## Part B: Tasks

### Task 1: Create a View for "Active Orders"
Create a view named `v_active_orders` that retrieves all orders with a status of `'PENDING'`. 
- Include the `order_id`, `order_date`, `total_amount`, and the customer's `full_name` and `email` by joining the `customers` table.
- Test the view with a `SELECT * FROM v_active_orders;` statement.

### Task 2: Create a Customer Audit Trigger
Whenever a customer's name is updated in the `customers` table, you must log the old name and new name into `customer_audit_log`.
1. Write a PL/pgSQL function named `fn_log_customer_name_change()` that returns a `TRIGGER`.
2. Ensure the trigger only writes to `customer_audit_log` if the `full_name` is actually changed.
3. Bind the trigger to the `customers` table for `AFTER UPDATE` events.
4. Test your trigger by updating John Doe's name to "Johnathan Doe". Run a query on `customer_audit_log` to verify that the change was captured.

### Task 3: Create a Stored Procedure to Archive Old Orders
Create a stored procedure named `pr_archive_old_orders(cutoff_date DATE)` that:
1. Updates the `status` of all `'COMPLETED'` orders placed **before** the `cutoff_date` to `'ARCHIVED'`.
2. Uses an internal transaction block (`COMMIT`) to save changes.
3. Test your procedure by calling `CALL pr_archive_old_orders('2026-07-05');` and verify that order `5004` status changes to `'ARCHIVED'`, while order `5002` (dated 2026-07-10) remains `'COMPLETED'`.

---

## Deliverables
Save your SQL statements for the view, trigger, function, and stored procedure in a file named `advanced_sql_objects.sql` with comment explanations.
