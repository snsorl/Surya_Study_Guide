# The Data Access Object (DAO) Pattern

## Learning Objectives
- Explain the purpose and structural goals of the Data Access Object (DAO) design pattern.
- Construct clean separation between application business logic and persistence logic.
- Implement the interface-and-implementation structure for a DAO component.
- Build standard CRUD (Create, Read, Update, Delete) methods in a Java JDBC DAO class.

---

## Why This Matters
As you build larger enterprise systems, the volume of your code grows. If you mix your database query code, transaction settings, and SQL strings directly inside your business calculations or REST controller classes, you create a massive, tangled codebase known as **spaghetti code**.

If you mix logic:
-   You cannot test business logic (like checking if a discount is valid) without having a live database connected.
-   If you decide to switch your database from PostgreSQL to a cloud-based database like DynamoDB, you will have to rewrite code in every single file of your application.
-   Debugging SQL errors requires searching through business algorithms.

The **Data Access Object (DAO)** pattern resolves this. By isolating all database interaction logic into specialized, decoupled DAO classes, you create a clean, maintainable architecture. Your business services remain clean, focusing strictly on business calculations, while the DAOs focus strictly on persistence.

---

## The Concept

### 1. What is the DAO Design Pattern?
The **DAO pattern** is an architectural pattern that abstracts all database access behind a clean interface. 

The application is structured into three distinct layers:
1.  **Entity (Model):** A simple Java class (often a POJO - Plain Old Java Object) that represents a table row (e.g., a `Product` class with `id`, `name`, and `price` fields).
2.  **DAO Interface:** Defines the contract of database operations (the CRUD methods) without specifying how they are executed.
3.  **DAO Implementation:** The class that implements the interface and executes the actual SQL queries (e.g., using JDBC to talk to PostgreSQL).

```
+-------------------------------------------------------------+
|               Service Layer (Business Logic)                |
+-------------------------------------------------------------+
                              |
                              | (Calls interface methods)
                              v
+-------------------------------------------------------------+
|                     DAO Interface (Contract)                |
+-------------------------------------------------------------+
                              |
                              | (Implemented by)
                              v
+-------------------------------------------------------------+
|             DAO Implementation (JDBC / SQL Engine)          |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                      PostgreSQL Database                    |
+-------------------------------------------------------------+
```

### 2. Why Use an Interface?
By defining a `ProductDAO` interface and calling *only* the interface from your service classes, you decouple your code. 
If you want to run unit tests without a database, you can write a `MockProductDAOImpl` that stores products in a temporary Java memory list. You can swap the implementations in your service layer instantly without modifying a single line of business logic.

---

## Code Example: Full DAO Implementation

Let's implement a complete DAO for an e-commerce product catalog.

### Step 1: The Entity (Product.java)
A simple container class representing a database row.

```java
public class Product {
    private int id;
    private String name;
    private double price;

    public Product() {}

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
```

---

### Step 2: The Interface (ProductDAO.java)
Defines the persistence capabilities of the application.

```java
import java.util.List;

public interface ProductDAO {
    void create(Product product);
    Product findById(int id);
    List<Product> findAll();
    void update(Product product);
    void delete(int id);
}
```

---

### Step 3: The JDBC Implementation (ProductDAOImpl.java)
Executes the actual SQL commands, utilizing the `ConnectionFactory` singleton we created earlier.

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void create(Product product) {
        String sql = "INSERT INTO products (product_id, name, price) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ? WHERE product_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

---

## Summary
-   The **DAO (Data Access Object)** pattern separates persistence mechanics from business logic.
-   It uses an **Interface + Implementation** structure to decouple services from the underlying database driver.
-   **Entities** are simple Java POJOs that mirror database table columns.
-   Decoupling with DAOs makes applications testable, modular, and easy to maintain.

---

## Additional Resources
-   [Oracle: The Data Access Object Design Pattern](https://www.oracle.com/java/technologies/design-patterns-catalog.html)
-   [Baeldung: Guide to the DAO Pattern in Java](https://www.baeldung.com/java-dao-pattern)
-   [Design Patterns: Elements of Reusable Object-Oriented Software](https://en.wikipedia.org/wiki/Design_Patterns)
