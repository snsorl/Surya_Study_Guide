# Guided Coding: Annotation-Based Configuration

## Learning Objectives
- Configure an application using annotation classpath scanning.
- Use `@Repository` and `@Service` stereotype annotations to declare beans.
- Apply `@Autowired` constructor injection to wire class hierarchies automatically.
- Resolve multiple bean conflicts using `@Qualifier`.

---

## Why This Matters
While writing manual `@Bean` declarations in a configuration class gives you full control, it becomes tedious as your application grows to hundreds of classes. Annotation-based configuration leverages component scanning to discover and wire beans automatically. In this guided coding tutorial, we will refactor our Product Management application to use component scanning, stereotype annotations, and autowiring, demonstrating how to handle multiple implementations of the same interface using `@Qualifier`.

---

## Guided Walkthrough

### Step 1: Define the Database Interface
We will create a `DataStore` interface. We want to support both SQL storage and Memory storage.

```java
package com.example.store;

public interface DataStore {
    String readData(int key);
}
```

### Step 2: Implement Stereotype Repositories
We implement two separate classes for the `DataStore` interface, decorating them with `@Repository`.

#### SQL Store (`SqlDataStore.java`)
```java
package com.example.store;

import org.springframework.stereotype.Repository;

@Repository("sqlStore") // Name the bean explicitly
public class SqlDataStore implements DataStore {
    @Override
    public String readData(int key) {
        return "SQL_DATA_ROW_" + key;
    }
}
```

#### Cache Store (`CacheDataStore.java`)
```java
package com.example.store;

import org.springframework.stereotype.Repository;

@Repository("cacheStore") // Name the bean explicitly
public class CacheDataStore implements DataStore {
    @Override
    public String readData(int key) {
        return "CACHE_HIT_ROW_" + key;
    }
}
```

### Step 3: Implement the Service Layer with `@Autowired` and `@Qualifier`
Create `InventoryService.java` and annotate it with `@Service`. We need to inject the SQL storage bean explicitly, avoiding conflict with the caching storage bean.

```java
package com.example.service;

import com.example.store.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final DataStore databaseStore;

    // Use @Qualifier to specify we want "sqlStore" instead of "cacheStore"
    @Autowired
    public InventoryService(@Qualifier("sqlStore") DataStore databaseStore) {
        this.databaseStore = databaseStore;
    }

    public String fetchProduct(int key) {
        return "Retrieved: " + databaseStore.readData(key);
    }
}
```

### Step 4: Write the Scan Configuration Class (`AppConfig.java`)
Unlike previous guides, we do not write individual `@Bean` methods. We only write a configuration shell annotated with `@ComponentScan` to scan our project packages.

```java
package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example") // Scans com.example and sub-packages
public class AppConfig {
    // Empty config shell. Beans are registered automatically via scanning.
}
```

### Step 5: Bootstrap the System
Create `MainApp.java` to test the annotation scanning context.

```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.config.AppConfig;
import com.example.service.InventoryService;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Starting Scan Bootstrap...");
        
        ApplicationContext context = 
                new AnnotationConfigApplicationContext(AppConfig.class);

        // Fetch scanned service bean
        InventoryService service = context.getBean(InventoryService.class);
        
        // Execute operation
        String result = service.fetchProduct(2025);
        System.out.println(result);
    }
}
```

### Trace Log Output
Running this code produces:
```text
Starting Scan Bootstrap...
Retrieved: SQL_DATA_ROW_2025
```
Because of `@Qualifier("sqlStore")`, Spring bypassed the `CacheDataStore` bean and successfully injected the `SqlDataStore` implementation.

---

## Summary
-   Component scanning automatically registers classes marked with stereotypes (`@Repository`, `@Service`, `@Component`).
-   Use **`@ComponentScan(basePackages = "...")`** to activate automated classpath scanning.
-   Use **`@Autowired`** on constructors to request dependency wiring.
-   Use **`@Qualifier("beanName")`** to select the correct implementation when an interface has multiple bean candidates in the container.

---

## Additional Resources
-   [Spring Documentation: Component Scanning](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-classpath-scanning.html)
-   [Baeldung: Guide to Spring Component Scanning](https://www.baeldung.com/spring-component-scanning)
-   [Baeldung: Spring Autowiring and Qualifiers](https://www.baeldung.com/spring-autowire)
