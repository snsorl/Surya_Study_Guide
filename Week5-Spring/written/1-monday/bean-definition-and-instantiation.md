# Bean Definition and Instantiation

## Learning Objectives
- Compare the three ways to define beans: XML, `@Bean` methods, and stereotype annotations (`@Component`).
- Understand bean naming conventions and override mechanisms in Spring.
- Explain how beans are instantiated using static and instance factory methods.
- Implement and use the `FactoryBean` interface for complex object creation.

---

## Why This Matters
As you build larger Spring applications, you will encounter classes that you cannot modify (third-party library classes), complex initialization logic that requires conditional parameters, or legacy components. You cannot simply put `@Component` on a class that lives inside a read-only JAR file. Understanding the different ways to define and instantiate beans—especially through factory methods and `FactoryBean` implementations—allows you to bring any Java object under Spring's control, regardless of where the class was defined.

---

## The Concept

### Three Ways to Define Beans

#### 1. XML Configuration
-   **Method:** Declare beans in `applicationContext.xml` using the `<bean>` tag.
-   **Pros:** Centralized configuration; does not require modifying java source code.
-   **Cons:** Verbose, no compile-time safety, harder to maintain.

#### 2. Java Configuration (`@Bean`)
-   **Method:** Declare methods returning objects inside `@Configuration` classes, annotated with `@Bean`.
-   **Pros:** Compile-time safety; ideal for instantiating classes from third-party libraries (e.g. Gson, DataSource) where you cannot modify the source code to add annotations.
-   **Cons:** Requires manual writing of configuration classes.

#### 3. Stereotype Annotations (`@Component` / Class-Path Scanning)
-   **Method:** Annotate the Java class directly with `@Component`, `@Service`, or `@Repository` and enable component scanning.
-   **Pros:** Rapid development, highly cohesive (configuration is right next to the code it configures).
-   **Cons:** Cannot be used for compiled third-party classes.

---

### Bean Naming
Every bean in the Spring container must have a unique name (or ID).
-   **Stereotype (`@Component`):** If no name is specified, Spring defaults to the class name in decapitalized camelCase (e.g., `OrderService` becomes `orderService`). You can override this using `@Component("myCustomName")`.
-   **JavaConfig (`@Bean`):** Defaults to the method name. You can override this using `@Bean(name = "myCustomName")`.
-   **XML:** Set using the `id` or `name` attribute on the `<bean>` tag.

---

### Instantiation Options

#### 1. Standard Constructor Instantiation
Spring calls the constructor of the class (using reflection) to instantiate the bean.

#### 2. Factory Method Instantiation
Sometimes, a class is designed to be instantiated via static factory methods rather than constructors.
-   **XML:** Use the `factory-method` attribute.
    ```xml
    <bean id="clientService" class="com.example.ClientService" factory-method="createInstance"/>
    ```
-   **JavaConfig:** Simply call the factory method inside the `@Bean` method.
    ```java
    @Bean
    public ClientService clientService() {
        return ClientService.createInstance();
    }
    ```

#### 3. FactoryBean Interface
For highly complex instantiation logic that requires substantial setup code, Spring provides the `FactoryBean<T>` interface. A `FactoryBean` is a bean that acts as a factory for exposing other beans.

When you register a class implementing `FactoryBean` in the container:
-   Requesting the bean by its name (e.g., `&myBean`) returns the factory bean itself.
-   Requesting the bean normally (e.g., `myBean`) returns the object created by the factory's `getObject()` method.

---

## Code Example

Let's look at how to implement a custom `FactoryBean` to instantiate a complex service class.

### The Target Class (To be Instantiated)
```java
package com.example;

// Suppose this is a third-party or complex class that requires customized setup
public class EncryptionCipher {
    private final String algorithm;
    private final int keySize;

    public EncryptionCipher(String algorithm, int keySize) {
        this.algorithm = algorithm;
        this.keySize = keySize;
    }

    public void encrypt(String data) {
        System.out.println("Encrypting data using " + algorithm + " (" + keySize + " bits)");
    }
}
```

### The FactoryBean Implementation
```java
package com.example;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("cipher")
public class EncryptionCipherFactoryBean implements FactoryBean<EncryptionCipher> {

    @Override
    public EncryptionCipher getObject() throws Exception {
        // Complex instantiation logic goes here
        String defaultAlgo = "AES";
        int secureKeySize = 256;
        return new EncryptionCipher(defaultAlgo, secureKeySize);
    }

    @Override
    public Class<?> getObjectType() {
        return EncryptionCipher.class;
    }

    // Indicates if the object returned by getObject() is a singleton
    @Override
    public boolean isSingleton() {
        return true;
    }
}
```

### Testing the FactoryBean
```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // This returns the EncryptionCipher object produced by the FactoryBean
        EncryptionCipher cipher = context.getBean("cipher", EncryptionCipher.class);
        cipher.encrypt("SensitiveData");

        // This returns the factory bean itself (using the '&' prefix)
        Object factoryObj = context.getBean("&cipher");
        System.out.println("Factory Bean Class: " + factoryObj.getClass().getName());
    }
}
```

---

## Summary
-   Beans can be defined using **XML (`<bean>`)**, **JavaConfig (`@Bean`)**, or **Stereotype Annotations (`@Component`)**.
-   Bean names default to decapitalized class names or method names, but can be overridden.
-   Spring can instantiate beans via constructors, static factory methods, or instance factory methods.
-   The **`FactoryBean<T>`** interface allows developers to write custom, complex initialization logic that integrates seamlessly into Spring's container lifecycle.

---

## Additional Resources
-   [Spring Documentation: Instantiating Beans](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-bean.html)
-   [Baeldung: Spring FactoryBean Interface](https://www.baeldung.com/spring-factorybean)
-   [StackOverflow: Factory Method vs FactoryBean in Spring](https://stackoverflow.com/questions/42240974/what-is-the-difference-between-factory-method-and-factorybean-in-spring)
