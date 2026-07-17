# Database Triggers in PostgreSQL

## Learning Objectives
- Define the role of database triggers in maintaining data integrity and automation.
- Create trigger functions and apply them to tables using the `CREATE TRIGGER` command.
- Explain the execution differences between BEFORE, AFTER, and INSTEAD OF triggers.
- Contrast row-level and statement-level triggers.
- Design practical trigger-based solutions for audit logging and calculated column synchronization.

## Why This Matters
Modern enterprise databases are not passive storage structures. To maintain consistency, databases must react dynamically to changes. For example, when a customer updates their password, a security requirement might demand that we record the change in an audit log. When an order item is added, the order's total balance column should automatically recalculate. 

While these actions can be handled in application-level code (e.g., in a Java service layer), doing so risks inconsistency if multiple applications access the same database. A bug in one microservice could result in missing audit logs or miscalculated totals. Database triggers solve this by executing logic directly in the database engine, guaranteeing that validation, auditing, and derived calculations occur regardless of which application initiated the modification.

## The Concept

### What is a Database Trigger?
A trigger is a database object associated with a table. It is set to fire automatically in response to specific events: inserts, updates, or deletes. In PostgreSQL, creating a trigger is a two-step process:
1. **Define a Trigger Function**: Write a PL/pgSQL function that returns a special type `TRIGGER` and defines the operations to execute.
2. **Create the Trigger**: Associate the function with a target table and define the trigger event (e.g., `BEFORE INSERT`).

### Trigger Events and Timing
Triggers can be executed at three different execution timings:

- **BEFORE**: Fires *before* the SQL statement's changes are applied to the table. This is ideal for validating inputs, formatting values, or automatically populating columns before they are written.
- **AFTER**: Fires *after* the SQL statement's changes have been successfully written to the table. This is used for operations that depend on the data being successfully committed, such as writing to an audit log or updating records in another table.
- **INSTEAD OF**: Used exclusively on views. Because views are often read-only, an `INSTEAD OF` trigger intercept updates to the view and route them to the underlying physical tables.

### Row vs. Statement Level Triggers
When creating a trigger, you must specify how often it executes during a multi-row update:

1. **Row-Level (FOR EACH ROW)**:
   The trigger fires once for *every individual row* modified by the SQL statement. If an `UPDATE` statement modifies 100 rows, a row-level trigger executes 100 times. Row-level triggers have access to two special variables:
   - `NEW`: Contains the proposed new row data for INSERT and UPDATE operations.
   - `OLD`: Contains the existing row data before UPDATE and DELETE operations.

2. **Statement-Level (FOR EACH STATEMENT)**:
   The trigger fires exactly *once per SQL statement*, regardless of how many rows are affected (even if zero rows are updated). This is useful for high-performance aggregate updates or notification systems where you only need to react to the event itself, not the individual records.

## Code Examples

### Setting up the Schema
```sql
CREATE TABLE inventory (
    item_id INT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE inventory_audit_log (
    log_id SERIAL PRIMARY KEY,
    item_id INT NOT NULL,
    action_type VARCHAR(10) NOT NULL,
    old_quantity INT,
    new_quantity INT,
    changed_by VARCHAR(50) DEFAULT CURRENT_USER,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Example 1: BEFORE UPDATE Trigger (Updating Metadata Columns)
We want to ensure that whenever an item's details are modified, the `last_updated` column is automatically set to the current time.

```sql
-- Step 1: Define the trigger function
CREATE OR REPLACE FUNCTION fn_sync_last_updated()
RETURNS TRIGGER AS $$
BEGIN
    -- Update the last_updated column on the row currently being processed
    NEW.last_updated := NOW();
    
    -- In BEFORE triggers, you must return the modified NEW record
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Step 2: Bind the trigger to the table
CREATE TRIGGER trg_inventory_update_timestamp
BEFORE UPDATE ON inventory
FOR EACH ROW
EXECUTE FUNCTION fn_sync_last_updated();
```

### Example 2: AFTER UPDATE Trigger (Audit Logging)
We want to log any changes made to inventory levels. Because logging should only occur if the update succeeds, we use an `AFTER UPDATE` trigger.

```sql
-- Step 1: Define the audit function
CREATE OR REPLACE FUNCTION fn_log_inventory_changes()
RETURNS TRIGGER AS $$
BEGIN
    -- Only log if the quantity actually changed
    IF OLD.quantity IS DISTINCT FROM NEW.quantity THEN
        INSERT INTO inventory_audit_log (item_id, action_type, old_quantity, new_quantity)
        VALUES (NEW.item_id, 'UPDATE', OLD.quantity, NEW.quantity);
    END IF;
    
    -- In AFTER triggers, the return value is ignored, but returning NEW is standard practice
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Step 2: Bind the trigger to the table
CREATE TRIGGER trg_inventory_audit
AFTER UPDATE ON inventory
FOR EACH ROW
EXECUTE FUNCTION fn_log_inventory_changes();
```

### Testing the Triggers
Let's see these triggers in action.

```sql
-- Insert initial data
INSERT INTO inventory (item_id, item_name, quantity) 
VALUES (1, 'USB Cable', 50);

-- Query the table to see initial state
SELECT * FROM inventory;

-- Update the quantity
UPDATE inventory 
SET quantity = 45 
WHERE item_id = 1;

-- Check if BEFORE trigger updated last_updated, and if AFTER trigger created an audit record
SELECT * FROM inventory;
SELECT * FROM inventory_audit_log;
```

## Summary
- A **trigger** executes code automatically in response to `INSERT`, `UPDATE`, or `DELETE` events.
- **BEFORE** triggers modify or validate data before it is saved; **AFTER** triggers execute log operations after data is saved; **INSTEAD OF** triggers redirect view modifications.
- **Row-level triggers** (`FOR EACH ROW`) execute for every modified record and have access to the `NEW` and `OLD` records.
- **Statement-level triggers** (`FOR EACH STATEMENT`) execute once per query and are optimized for batch notifications or logging that does not require column-level inspection.

## Additional Resources
- [PostgreSQL Documentation on CREATE TRIGGER](https://www.postgresql.org/docs/current/sql-createtrigger.html)
- [PostgreSQL PL/pgSQL Trigger Functions](https://www.postgresql.org/docs/current/plpgsql-trigger.html)
- [Database Trigger Best Practices - SQLShack Guide](https://www.sqlshack.com/database-trigger-best-practices/)
