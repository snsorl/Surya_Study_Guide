# Guided Coding: Tracing the Bean Dependency Graph

## Learning Objectives
- Design and build a complete multi-layered Spring application (Controller $\rightarrow$ Service $\rightarrow$ Repository).
- Configure all components using modern JavaConfig styles.
- Trace the container's dependency resolution graph at application startup.
- Verify object identities using logging and unit checks.

---

## Why This Matters
Enterprise software is organized in layers. Controllers handle HTTP/REST inputs, Services enforce business logic, and Repositories interact with the database. Understanding how these layers stack up and pass data is the cornerstone of backend development. In this guided coding walk-through, we will build a complete, multi-layered "Product Inventory Management" console application, trace how Spring resolves the dependency graph, and inspect bean instantiation order.

---

## The Concept

### The Dependency Graph
Our application consists of three layered beans:
1.  `ProductRepository` (Data Access Layer) - Simulates database records.
2.  `ProductService` (Business Layer) - Resolves inventory checks and depends on the Repository.
3.  `ProductController` (Presentation Layer) - Acts as the entry point, handles client inputs, and depends on the Service.

```
                  [ ProductController ]
                           │
                           ▼ (depends on)
                   [ ProductService ]
                           │
                           ▼ (depends on)
                  [ ProductRepository ]
```

---

## Guided Walkthrough

### Step 1: Create the Data Access Layer (`ProductRepository.java`)
This class simulates data storage using an in-memory map.

```java
package com.example.repository;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private final Map<Integer, String> inventory = new HashMap<>();

    public ProductRepository() {
        System.out.println("[DI TRACE] 1. ProductRepository Instantiated.");
        // Seed dummy database records
        inventory.put(101, "Java Development Laptop");
        inventory.put(102, "Mechanical Keyboard");
    }

    public String findProductNameById(int id) {
        return inventory.getOrDefault(id, "Unknown Product");
    }
}
```

### Step 2: Create the Business Layer (`ProductService.java`)
This class implements business logic. It relies on the repository, which is injected via constructor injection.

```java
package com.example.service;

import com.example.repository.ProductRepository;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        System.out.println("[DI TRACE] 2. ProductService Instantiated. Injecting Repository.");
        this.productRepository = productRepository;
    }

    public String getFormattedProductDetails(int productId) {
        String name = productRepository.findProductNameById(productId);
        return "Product ID: " + productId + " | Title: " + name.toUpperCase();
    }
}
```

### Step 3: Create the Presentation Layer (`ProductController.java`)
The controller orchestrates requests. It depends on the service layer.

```java
package com.example.controller;

import com.example.service.ProductService;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        System.out.println("[DI TRACE] 3. ProductController Instantiated. Injecting Service.");
        this.productService = productService;
    }

    public void renderProductPage(int id) {
        System.out.println("\n--- Client Page Render Output ---");
        String output = productService.getFormattedProductDetails(id);
        System.out.println(output);
        System.out.println("---------------------------------");
    }
}
```

### Step 4: Write the Configuration Class (`InventoryConfig.java`)
We register these beans in a JavaConfig class. Notice how Spring must resolve the dependency chain: Repository must be created first, then passed to Service, then passed to Controller.

```java
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.example.controller.ProductController;

@Configuration
public class InventoryConfig {

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository();
    }

    @Bean
    public ProductService productService(ProductRepository repository) {
        return new ProductService(repository);
    }

    @Bean
    public ProductController productController(ProductService service) {
        return new ProductController(service);
    }
}
```

### Step 5: Run and Trace Container Startup
Create the execution class `InventoryApp.java` to start the context.

```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.config.InventoryConfig;
import com.example.controller.ProductController;

public class InventoryApp {
    public static void main(String[] args) {
        System.out.println("=== Starting Spring Bootstrap ===");
        
        // Starts the context and resolves dependencies
        ApplicationContext context = 
                new AnnotationConfigApplicationContext(InventoryConfig.class);

        System.out.println("=== Spring Container Ready ===");

        // Fetch controller bean and request product details
        ProductController controller = context.getBean(ProductController.class);
        controller.renderProductPage(101);
    }
}
```

### Trace Log Output
When running the application, you will see the constructors firing sequentially:
```text
=== Starting Spring Bootstrap ===
[DI TRACE] 1. ProductRepository Instantiated.
[DI TRACE] 2. ProductService Instantiated. Injecting Repository.
[DI TRACE] 3. ProductController Instantiated. Injecting Service.
=== Spring Container Ready ===

--- Client Page Render Output ---
Product ID: 101 | Title: JAVA DEVELOPMENT LAPTOP
---------------------------------
```

---

## Summary
-   Layered architectures are modeled in Spring by linking Controller $\rightarrow$ Service $\rightarrow$ Repository.
-   Spring automatically determines the correct instantiation order by analyzing the dependency arguments.
-   Singleton beans are eagerly constructed in bottom-up dependency order (Leaf nodes first, Root node last) at container startup.

---

## Additional Resources
-   [Spring Documentation: AnnotationConfigApplicationContext](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-java-config.html)
-   [Baeldung: Controller, Service, and Repository patterns in Spring](https://www.baeldung.com/spring-mvc-annotations)
-   [GeeksforGeeks: Spring Controller to Repository Flow](https://www.geeksforgeeks.org/spring-mvc-framework-tutorial/)
