# Monday Knowledge Check: Spring Core & IoC Fundamentals

## Part 1: Multiple Choice & Objective Questions

### 1. What is the fundamental difference between Inversion of Control (IoC) and Dependency Injection (DI)?
- [ ] A) IoC is the specific coding technique, whereas DI is the broad architectural pattern.
- [ ] B) IoC is the abstract design principle (delegating control), whereas DI is a concrete implementation of that principle (passing dependencies).
- [ ] C) IoC only applies to XML configuration, whereas DI only applies to Java-based Annotation configuration.
- [ ] D) There is no difference; the terms are entirely synonymous and interchangeable in software design.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) IoC is the abstract design principle (delegating control), whereas DI is a concrete implementation of that principle (passing dependencies).

**Explanation:** Inversion of Control is the general concept of transferring the control of objects and execution flows from the programmer to a framework. Dependency Injection is the specific mechanism by which Spring implements IoC by injecting dependent objects into a class at runtime.
- **Why others are wrong:**
  - A) The reverse is true; IoC is the design principle and DI is the design pattern/technique.
  - C) Both XML and Java configurations support both IoC and DI principles.
  - D) They are related but not identical; DI is a subset of IoC (other examples of IoC include Event Loops or Template Method pattern).
</details>

---

### 2. Which concrete Spring container class is used to bootstrap an application using Java-based `@Configuration` classes?
- [ ] A) `ClassPathXmlApplicationContext`
- [ ] B) `FileSystemXmlApplicationContext`
- [ ] C) `AnnotationConfigApplicationContext`
- [ ] D) `XmlWebApplicationContext`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) `AnnotationConfigApplicationContext`

**Explanation:** `AnnotationConfigApplicationContext` is the concrete implementation of the `ApplicationContext` interface designed to accept `@Configuration` annotated classes or scan base packages.
- **Why others are wrong:**
  - A) `ClassPathXmlApplicationContext` parses XML configuration files located in the classpath.
  - B) `FileSystemXmlApplicationContext` loads XML configuration files from the local file system.
  - D) `XmlWebApplicationContext` is used specifically in legacy web applications configured with web-level XML.
</details>

---

### 3. Which type of Dependency Injection is recommended by the Spring team for mandatory dependencies to ensure immutability and prevent null references?
- [ ] A) Field Injection (using `@Autowired` directly on fields)
- [ ] B) Setter Injection (using setter methods)
- [ ] C) Constructor Injection
- [ ] D) Interface Injection

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** C) Constructor Injection

**Explanation:** Constructor injection is preferred because it allows dependencies to be declared as `final` (ensuring immutability), ensures a class cannot be instantiated without its mandatory components, and simplifies unit testing with plain mock injections.
- **Why others are wrong:**
  - A) Field injection makes unit testing difficult (requires reflection) and hides dependencies from developers.
  - B) Setter injection is recommended only for optional dependencies that can be reconfigured or changed later.
  - D) Interface injection is not natively supported or utilized as a common pattern in the Spring Core framework.
</details>

---

### 4. What is the default bean scope in a Spring IoC container?
- [ ] A) Prototype
- [ ] B) Singleton
- [ ] C) Request
- [ ] D) Session

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) Singleton

**Explanation:** By default, Spring beans are created as Singletons, meaning the IoC container instantiates only one instance of the bean per container instance, caching it for all subsequent requests.
- **Why others are wrong:**
  - A) Prototype scope creates a new object instance every single time the bean is requested.
  - C) Request scope is web-specific and creates a bean instance per HTTP request.
  - D) Session scope is web-specific and creates a bean instance per HTTP session.
</details>

---

### 5. (True/False) When a Prototype-scoped bean is injected into a Singleton-scoped bean, a new instance of the Prototype bean is created every time a method is called on the Singleton bean.
- [ ] A) True
- [ ] B) False

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) False

**Explanation:** Because the Singleton bean is instantiated only once during container startup, its dependencies are also injected only once. Consequently, the same Prototype bean instance is reused inside the Singleton bean for the rest of its lifecycle unless specialized techniques like lookup method injection or scoped proxies are used.
- **Why it is false:** The prototype bean is indeed injected, but because it happens only *once* during the Singleton's instantiation phase, subsequent calls to the singleton will call the same injected prototype reference.
</details>

---

### 6. Which stereotype annotation is semantically designated for class elements interacting directly with database operations (Data Access Objects)?
- [ ] A) `@Component`
- [ ] B) `@Service`
- [ ] C) `@Controller`
- [ ] D) `@Repository`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** D) `@Repository`

**Explanation:** `@Repository` is a specialized form of `@Component` that not only registers the class as a Spring bean but also translates platform-specific database exceptions (e.g. SQL exceptions) into Spring's unified `DataAccessException` hierarchy.
- **Why others are wrong:**
  - A) `@Component` is a generic stereotype for any Spring-managed component.
  - B) `@Service` is designated for service layer classes containing business logic.
  - C) `@Controller` is used to define presentation controllers (Web MVC or REST).
</details>

---

### 7. What is the chronological order of execution for a Spring bean's lifecycle milestones?
- [ ] A) Instantiation -> Dependency Injection -> `@PostConstruct` -> `@PreDestroy`
- [ ] B) Dependency Injection -> Instantiation -> `@PostConstruct` -> `@PreDestroy`
- [ ] C) `@PostConstruct` -> Instantiation -> Dependency Injection -> `@PreDestroy`
- [ ] D) Instantiation -> `@PostConstruct` -> Dependency Injection -> `@PreDestroy`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** A) Instantiation -> Dependency Injection -> `@PostConstruct` -> `@PreDestroy`

**Explanation:** A bean is first instantiated via its constructor, then its dependencies are populated (injected). Only after the bean's properties are fully set does the `@PostConstruct` initialization hook run. Finally, when the container shuts down, `@PreDestroy` runs.
- **Why others are wrong:**
  - B) You cannot inject dependencies before the bean is instantiated.
  - C) `@PostConstruct` cannot execute until the bean exists and its dependencies are populated.
  - D) `@PostConstruct` will fail if it executes before dependencies are injected, as initialization logic often relies on injected dependencies.
</details>

---

### 8. How does `ApplicationContext` differ from `BeanFactory` regarding bean instantiation timing?
- [ ] A) `ApplicationContext` uses lazy loading for all beans, whereas `BeanFactory` uses eager loading.
- [ ] B) `ApplicationContext` uses eager loading for singleton beans by default, whereas `BeanFactory` uses lazy loading.
- [ ] C) Both interfaces use eager loading, but `BeanFactory` requires XML configuration.
- [ ] D) There is no difference; both instantiate beans eagerly.

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `ApplicationContext` uses eager loading for singleton beans by default, whereas `BeanFactory` uses lazy loading.

**Explanation:** `ApplicationContext` eagerly instantiates, wires, and validates all singleton beans during container startup. `BeanFactory` instantiates beans lazily, meaning objects are created only when `getBean()` is explicitly invoked.
- **Why others are wrong:**
  - A) The reverse is true.
  - C) `BeanFactory` uses lazy loading by default.
  - D) The instantiation strategy is a primary behavioral differentiator between the two container interfaces.
</details>

---

### 9. What is the correct Spring annotation syntax to inject a property value named `db.port` from an environment properties file?
- [ ] A) `@Value("#{db.port}")`
- [ ] B) `@Value("${db.port}")`
- [ ] C) `@Autowired("db.port")`
- [ ] D) `@Inject("db.port")`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `@Value("${db.port}")`

**Explanation:** The property placeholder syntax `${property.name}` is used with the `@Value` annotation to resolve key values from active properties files.
- **Why others are wrong:**
  - A) `#{...}` is the Spring Expression Language (SpEL) syntax, used for dynamic calculations or bean referencing, not direct property resolution.
  - C) `@Autowired` is used to wire bean references by type, not resolve properties.
  - D) `@Inject` is standard Java DI annotation (JSR-330) and does not parse property keys directly with raw strings.
</details>

---

### 10. Given the code below, what is printed when the container is bootstrapped?
```java
@Component
class ConnectionPool {
    public ConnectionPool() {
        System.out.print("C ");
    }
    @PostConstruct
    public void init() {
        System.out.print("I ");
    }
}
```
- [ ] A) `I C`
- [ ] B) `C I`
- [ ] C) `C`
- [ ] D) `I`

<details>
<summary><b>🔎 Click for Solution</b></summary>

**Correct Answer:** B) `C I`

**Explanation:** The class constructor runs first during bean instantiation (`C`), followed by dependency population, and then the `@PostConstruct` method runs to initialize the bean (`I`).
- **Why others are wrong:**
  - A) Constructor must execute before post-construct methods.
  - C) Both the constructor and `@PostConstruct` method run automatically during container startup.
  - D) `@PostConstruct` does not replace the constructor instantiation phase.
</details>
