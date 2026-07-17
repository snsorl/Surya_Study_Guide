# Unit Testing: Introduction to JUnit 5

## Learning Objectives
- Identify the role of JUnit 5 as a core testing library in modern Java development.
- Utilize primary JUnit 5 annotations (`@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`) to control test lifecycle execution.
- Implement standard assertions (such as `assertEquals`, `assertNotEquals`, `assertTrue`, `assertFalse`, `assertNull`, `assertNotNull`, and `assertThrows`).
- Explain the test execution lifecycle and how JUnit manages state isolation between tests.

---

## Why This Matters
In the software development lifecycle, manually verifying code execution (for instance, running an application and visually inspecting console printouts) is slow, prone to human error, and impossible to scale. As applications grow in size and start connecting to enterprise infrastructure—such as relational databases or external REST APIs—we need a reliable, repeatable, and automated way to verify that existing logic doesn't break when new features are added.

JUnit is the standard testing framework for the Java ecosystem. The vast majority of Java production systems rely on JUnit to automate testing suites within continuous integration pipelines. As a full-stack developer, being proficient with JUnit 5 enables you to write self-validating code, verify behavior instantly, and integrate seamlessly with modern build systems and deployment pipelines.

---

## The Concept

JUnit 5 consists of several different modules, but for our day-to-day work, we focus primarily on the **JUnit Jupiter** API, which provides the annotations and assertion engines used to declare and execute tests.

### 1. The JUnit Test Lifecycle & State Isolation
By default, JUnit creates a **new instance** of the test class for every single test case (i.e., every method annotated with `@Test`) it runs. 

Why does it do this? To ensure **state isolation**. If one test modifies the properties of the class under test or inserts mock data, we do not want that modified state spilling over and causing subsequent tests to fail or pass falsely. Each test starts with a clean slate.

To control what happens before and after tests run, JUnit provides lifecycle annotations.

### 2. Common JUnit 5 Lifecycle Annotations

-   **`@Test`**: Marks a method as a test case. JUnit will run this method automatically.
-   **`@BeforeEach`**: Run before *each* test method in the current class. Used to reset state, instantiate classes, or seed test data.
-   **`@AfterEach`**: Run after *each* test method. Used to clean up temporary resources, close connections, or reset mocks.
-   **`@BeforeAll`**: Run *once* before all tests in the class start. The annotated method must be declared `static`. Used for heavy, one-time setups (like starting an in-memory database server).
-   **`@AfterAll`**: Run *once* after all tests in the class complete. The annotated method must be declared `static`. Used to shut down one-time heavy resources.

The chronological execution order of a test class with two test cases looks like this:

```
[BeforeAll] (Once per class)
   |
   +---> [BeforeEach] (Before Test 1)
   |        |
   |        v
   |     [Test 1]
   |        |
   |        v
   +---> [AfterEach] (After Test 1)
   |
   +---> [BeforeEach] (Before Test 2)
   |        |
   |        v
   |     [Test 2]
   |        |
   |        v
   +---> [AfterEach] (After Test 2)
   |
[AfterAll] (Once per class)
```

### 3. JUnit 5 Assertions
Assertions are static helper methods provided by the `org.junit.jupiter.api.Assertions` class. They are used to compare the results returned by your code against expectations.

-   **`assertEquals(expected, actual)`**: Asserts that two values are equal.
-   **`assertNotEquals(unexpected, actual)`**: Asserts that two values are not equal.
-   **`assertTrue(condition)`**: Asserts that a boolean expression is true.
-   **`assertFalse(condition)`**: Asserts that a boolean expression is false.
-   **`assertNull(object)`**: Asserts that an object reference is null.
-   **`assertNotNull(object)`**: Asserts that an object reference is not null.
-   **`assertThrows(Exception.class, executable)`**: Asserts that executing a block of code throws a specific exception type. This is crucial for verifying error handling and validation logic.

---

## Code Example

Below is a complete JUnit 5 test class demonstrating the annotations, standard assertions, and the validation of exception throwing.

First, let's look at the class we are testing:

```java
// InventoryService.java
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private final List<String> items = new ArrayList<>();

    public void addItem(String item) {
        if (item == null || item.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        items.add(item);
    }

    public int getItemCount() {
        return items.size();
    }

    public void clearInventory() {
        items.clear();
    }
}
```

Now, let's write the JUnit 5 test class:

```java
// InventoryServiceTest.java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {

    private InventoryService service;

    @BeforeAll
    public static void setUpTestSuite() {
        System.out.println("Starting InventoryService tests...");
    }

    @BeforeEach
    public void setUp() {
        // Arrange: Re-initialize the service before each test to ensure isolation
        service = new InventoryService();
        System.out.println("Initialized clean InventoryService instance.");
    }

    @AfterEach
    public void tearDown() {
        // Clean up code if necessary
        service.clearInventory();
        System.out.println("Cleaned up after test case.");
    }

    @AfterAll
    public static void tearDownTestSuite() {
        System.out.println("All InventoryService tests complete.");
    }

    @Test
    public void testAddItem_ShouldIncreaseCount_WhenItemIsValid() {
        // Arrange
        String item = "Database Connector";

        // Act
        service.addItem(item);

        // Assert
        assertEquals(1, service.getItemCount(), "Inventory size should be 1 after adding an item.");
    }

    @Test
    public void testAddItem_ShouldThrowException_WhenItemIsNull() {
        // Arrange
        String nullItem = null;

        // Act & Assert
        // We assert that calling service.addItem(null) throws an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            service.addItem(nullItem);
        }, "Adding a null item should throw an IllegalArgumentException.");
    }
}
```

---

## Summary
- **JUnit 5** is the industry-standard unit testing framework for Java application code.
- By default, JUnit instantiates a new instance of your test class for each test case to preserve **state isolation**.
- Use **`@BeforeEach`** and **`@AfterEach`** to run setup/cleanup tasks before and after every individual test.
- Use **`assertThrows`** to verify that validation methods throw appropriate exceptions under failure scenarios.

---

## Additional Resources
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Baeldung: Guide to JUnit 5](https://www.baeldung.com/junit-5)
- [Software Testing Fundamentals: Test Lifecycle](https://softwaretestingfundamentals.com/)
